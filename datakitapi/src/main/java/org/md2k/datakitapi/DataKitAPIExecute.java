package org.md2k.datakitapi;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Messenger;
import android.util.Log;

import org.md2k.datakitapi.datatype.DataType;
import org.md2k.datakitapi.datatype.DataTypeDoubleArray;
import org.md2k.datakitapi.datatype.DataTypeLong;
import org.md2k.datakitapi.datatype.RowObject;
import org.md2k.datakitapi.exception.DataKitException;
import org.md2k.datakitapi.exception.DataKitNotFoundException;
import org.md2k.datakitapi.messagehandler.MessageType;
import org.md2k.datakitapi.messagehandler.OnConnectionListener;
import org.md2k.datakitapi.messagehandler.OnReceiveListener;
import org.md2k.datakitapi.messagehandler.PendingResult;
import org.md2k.datakitapi.messagehandler.ResultCallback;
import org.md2k.datakitapi.source.METADATA;
import org.md2k.datakitapi.source.application.Application;
import org.md2k.datakitapi.source.application.ApplicationBuilder;
import org.md2k.datakitapi.source.datasource.DataSource;
import org.md2k.datakitapi.source.datasource.DataSourceBuilder;
import org.md2k.datakitapi.source.datasource.DataSourceClient;
import org.md2k.datakitapi.status.Status;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/*
 * Copyright (c) 2015, The University of Memphis, MD2K Center
 * - Syed Monowar Hossain <monowar.hossain@gmail.com>
 * - Timothy W. Hnat <twhnat@memphis.edu>
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * * Redistributions of source code must retain the above copyright notice, this
 * list of conditions and the following disclaimer.
 *
 * * Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following disclaimer in the documentation
 * and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
class DataKitAPIExecute {
    private static final String TAG = DataKitAPIExecute.class.getSimpleName();
    public boolean isBound = false;
    Status receivedStatus;
    Intent intent;
    ReentrantLock lock = new ReentrantLock();

    DataType subscribedData;
    ArrayList<RowObject> queryHFPrimaryKeyData;
    ArrayList<RowObject> queryPrimaryKeyData;
    DataTypeLong querySizeData;
    ArrayList<DataType> queryHFLastNData;
    ArrayList<DataType> queryData;
    Status unregisterData;
    Status unsubscribeData;
    ArrayList<DataSourceClient> findData;
    DataSourceClient registerData;
    Status subscribeData;

    final Condition registerCondition = lock.newCondition();
    final Condition findCondition = lock.newCondition();
    final Condition subscribeCondition = lock.newCondition();
    final Condition unsubscribeCondition = lock.newCondition();
    final Condition unregisterCondition = lock.newCondition();
    final Condition queryCondition = lock.newCondition();
    final Condition queryHFLastNCondition = lock.newCondition();
    final Condition querySizeCondition = lock.newCondition();
    final Condition queryPrimaryKeyCondition = lock.newCondition();
    final Condition queryHFPrimaryKeyCondition = lock.newCondition();


    HashMap<Integer, OnReceiveListener> ds_idOnReceiveListenerHashMap = new HashMap<>();
    private Context context;
    private ServiceConnection connection;//receives callbacks from bind and unbind invocations
    private Messenger sendMessenger = null;
    private Messenger replyMessenger = null; //invocation replies are processed by this Messenger
    private OnConnectionListener onConnectionListener;
    private static final long WAIT_TIME = 2000;


    final Condition conditionSubscribe = lock.newCondition();


    public DataKitAPIExecute(Context context) {
        this.context = context;
        isBound = false;
    }

    private boolean isInstalled(Context context, String packageName) {
        PackageManager pm = context.getPackageManager();
        try {
            pm.getPackageInfo(packageName, PackageManager.GET_ACTIVITIES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    protected void connect(OnConnectionListener onConnectionListener) throws DataKitException {
        if (isBound) {
            onConnectionListener.onConnected();
            return;
        }
        this.onConnectionListener = onConnectionListener;

        if (!isInstalled(context, "org.md2k.datakit")) {
            throw new DataKitNotFoundException(new Status(Status.ERROR_NOT_INSTALLED));
        }

        intent = new Intent();
        intent.setClassName("org.md2k.datakit", "org.md2k.datakit.ServiceDataKit");
        this.connection = new RemoteServiceConnection();
        HandlerThread thread = new HandlerThread("MyHandlerThread");
        thread.start();

        IncomingHandler incomingHandler = new IncomingHandler(thread.getLooper());
        this.replyMessenger = new Messenger(incomingHandler);
        intent.putExtra("name", context.getPackageName());
        intent.putExtra("messenger", this.replyMessenger);
        Log.d(TAG, "connect()..before bound...");

        if (!context.bindService(intent, this.connection, Context.BIND_AUTO_CREATE)) {
            Log.d(TAG, "bind fail...");
            thread.quit();
            context.unbindService(connection);
            isBound = false;
            throw new DataKitNotFoundException(new Status(Status.ERROR_BOUND));
        }

    }

    public void disconnect() {
        Log.d(TAG, "disconnect()...");
        if (isBound) {
            context.unbindService(connection);
            isBound = false;
        }
    }


    private void prepareAndSend(Bundle bundle, int messageType) throws Exception {
        Message message = Message.obtain(null, 0, 0, 0);
        message.what = messageType;

        message.setData(bundle);
        message.replyTo = replyMessenger;
        sendMessenger.send(message);
    }

    boolean isAlive() {
        return !(!isBound || sendMessenger == null);
    }

    public PendingResult<DataSourceClient> register(final DataSourceBuilder dataSourceBuilder) throws DataKitException {
        PendingResult<DataSourceClient> pendingResult = new PendingResult<DataSourceClient>() {
            @Override
            public DataSourceClient await() {
                try {
                    lock.tryLock(WAIT_TIME, TimeUnit.MILLISECONDS);
                    if (!isAlive() || dataSourceBuilder==null)
                        throw new DataKitNotFoundException(new Status(Status.ERROR_BOUND));
                    DataSource dataSource = prepareDataSource(dataSourceBuilder);
                    Bundle bundle = new Bundle();
                    bundle.putParcelable(DataSource.class.getSimpleName(), dataSource);
                    prepareAndSend(bundle, MessageType.REGISTER);
                    registerCondition.await(WAIT_TIME, TimeUnit.MILLISECONDS);
                } catch (Exception e) {
                    registerData = null;
                    registerCondition.signal();
                }
                lock.unlock();
                return registerData;
            }

            @Override
            public void setResultCallback(ResultCallback<DataSourceClient> callback) {

            }
        };
        return pendingResult;
    }

    public PendingResult<Status> unsubscribe(final DataSourceClient dataSourceClient) throws DataKitException {
        PendingResult<Status> pendingResult = new PendingResult<Status>() {
            @Override
            public Status await() {
                try {
                    lock.tryLock(WAIT_TIME, TimeUnit.MILLISECONDS);
                    if (!isAlive() || dataSourceClient==null)
                        throw new DataKitNotFoundException(new Status(Status.ERROR_BOUND));
                    ds_idOnReceiveListenerHashMap.remove(dataSourceClient.getDs_id());
                    Bundle bundle = new Bundle();
                    bundle.putInt("ds_id", dataSourceClient.getDs_id());
                    prepareAndSend(bundle, MessageType.UNSUBSCRIBE);
                    unsubscribeCondition.await(WAIT_TIME, TimeUnit.MILLISECONDS);
                } catch (Exception e) {
                    unsubscribeData = null;
                    unsubscribeCondition.signal();
                }
                lock.unlock();
                return unsubscribeData;
            }

            @Override
            public void setResultCallback(ResultCallback<Status> callback) {
                callback.onResult(unsubscribeData);
            }
        };
        return pendingResult;
    }

    public PendingResult<Status> unregister(final DataSourceClient dataSourceClient) throws DataKitException {
        PendingResult<Status> pendingResult = new PendingResult<Status>() {
            @Override
            public Status await() {
                try {
                    lock.tryLock(WAIT_TIME, TimeUnit.MILLISECONDS);
                    if (!isAlive() || dataSourceClient==null)
                        throw new DataKitNotFoundException(new Status(Status.ERROR_BOUND));
                    Bundle bundle = new Bundle();
                    bundle.putInt("ds_id", dataSourceClient.getDs_id());
                    prepareAndSend(bundle, MessageType.UNREGISTER);
                    unregisterCondition.await(WAIT_TIME, TimeUnit.MILLISECONDS);
                } catch (Exception e) {
                    unregisterData = null;
                    unregisterCondition.signal();
                }
                lock.unlock();
                return unregisterData;
            }

            @Override
            public void setResultCallback(ResultCallback<Status> callback) {
                callback.onResult(unregisterData);
            }
        };
        return pendingResult;
    }

    public Status subscribe(final DataSourceClient dataSourceClient, OnReceiveListener onReceiveListener) throws DataKitException {
        try {
            lock.tryLock(WAIT_TIME, TimeUnit.MILLISECONDS);
            if (!isAlive() || dataSourceClient==null || onReceiveListener==null)
                throw new DataKitNotFoundException(new Status(Status.ERROR_BOUND));
            ds_idOnReceiveListenerHashMap.put(dataSourceClient.getDs_id(), onReceiveListener);
            Bundle bundle = new Bundle();
            bundle.putInt("ds_id", dataSourceClient.getDs_id());
            prepareAndSend(bundle, MessageType.SUBSCRIBE);
            subscribeCondition.await(WAIT_TIME, TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            subscribeData=null;
            subscribeCondition.signal();
        }
        lock.unlock();
        return subscribeData;
    }


    public PendingResult<ArrayList<DataSourceClient>> find(final DataSourceBuilder dataSourceBuilder) throws DataKitException {
            PendingResult<ArrayList<DataSourceClient>> pendingResult = new PendingResult<ArrayList<DataSourceClient>>() {
                @Override
                public ArrayList<DataSourceClient> await() {
                    try {
                        lock.tryLock(WAIT_TIME, TimeUnit.MILLISECONDS);
                        if (!isAlive() || dataSourceBuilder==null)
                            throw new DataKitNotFoundException(new Status(Status.ERROR_BOUND));
                        final DataSource dataSource = dataSourceBuilder.build();
                        Bundle bundle = new Bundle();
                        bundle.putParcelable(DataSource.class.getSimpleName(), dataSource);
                        prepareAndSend(bundle, MessageType.FIND);
                        findCondition.await(WAIT_TIME, TimeUnit.MILLISECONDS);
                    } catch (Exception e) {
                        findData = null;
                        findCondition.signal();
                    }
                    lock.unlock();
                    return findData;
                }

                @Override
                public void setResultCallback(ResultCallback<ArrayList<DataSourceClient>> callback) {

                }
            };
            return pendingResult;
    }

    public PendingResult<ArrayList<DataType>> query(final DataSourceClient dataSourceClient, final long starttimestamp, final long endtimestamp) throws DataKitException {
            return new PendingResult<ArrayList<DataType>>() {
                @Override
                public ArrayList<DataType> await() {
                    try {
                        lock.tryLock(WAIT_TIME, TimeUnit.MILLISECONDS);
                        if (!isAlive() || dataSourceClient==null || starttimestamp>endtimestamp)
                            throw new DataKitNotFoundException(new Status(Status.ERROR_BOUND));
                        Bundle bundle = new Bundle();
                        bundle.putInt("ds_id", dataSourceClient.getDs_id());
                        bundle.putLong("starttimestamp", starttimestamp);
                        bundle.putLong("endtimestamp", endtimestamp);
                        prepareAndSend(bundle, MessageType.QUERY);
                        queryCondition.await(WAIT_TIME, TimeUnit.MILLISECONDS);
                    } catch (Exception e) {
                        queryData = null;
                        queryCondition.signal();
                    }
                    lock.unlock();
                    return queryData;
                }

                @Override
                public void setResultCallback(ResultCallback<ArrayList<DataType>> callback) {

                }
            };
    }

    public PendingResult<ArrayList<DataType>> query(final DataSourceClient dataSourceClient, final int last_n_sample) throws DataKitException {

            return new PendingResult<ArrayList<DataType>>() {
                @Override
                public ArrayList<DataType> await() {
                    try {
                        lock.tryLock(WAIT_TIME, TimeUnit.MILLISECONDS);
                        if (!isAlive() || dataSourceClient==null || last_n_sample==0)
                            throw new DataKitNotFoundException(new Status(Status.ERROR_BOUND));
                        Bundle bundle = new Bundle();
                        bundle.putInt("ds_id", dataSourceClient.getDs_id());
                        bundle.putInt("last_n_sample", last_n_sample);
                        prepareAndSend(bundle, MessageType.QUERY);
                        queryCondition.await(WAIT_TIME, TimeUnit.MILLISECONDS);
                    } catch (Exception e) {
                        queryData = null;
                        queryCondition.signal();
                    }
                    lock.unlock();
                    return queryData;
                }

                @Override
                public void setResultCallback(ResultCallback<ArrayList<DataType>> callback) {

                }
            };
    }

    public PendingResult<ArrayList<DataType>> queryHFlastN(final DataSourceClient dataSourceClient, final int last_n_sample) throws DataKitException {
            return new PendingResult<ArrayList<DataType>>() {
                @Override
                public ArrayList<DataType> await() {
                    try {
                        lock.tryLock(WAIT_TIME, TimeUnit.MILLISECONDS);
                        if (!isAlive() || dataSourceClient==null || last_n_sample==0)
                            throw new DataKitNotFoundException(new Status(Status.ERROR_BOUND));
                        Bundle bundle = new Bundle();
                        bundle.putInt("ds_id", dataSourceClient.getDs_id());
                        bundle.putInt("last_n_sample", last_n_sample);
                        prepareAndSend(bundle, MessageType.QUERYHFLASTN);
                        queryHFLastNCondition.await(WAIT_TIME, TimeUnit.MILLISECONDS);
                    } catch (Exception e) {
                        queryHFLastNData = null;
                        queryHFLastNCondition.signal();
                    }
                    lock.unlock();
                    return queryHFLastNData;
                }

                @Override
                public void setResultCallback(ResultCallback<ArrayList<DataType>> callback) {

                }
            };
    }

    public PendingResult<ArrayList<RowObject>> queryFromPrimaryKey(final DataSourceClient dataSourceClient, final long lastSyncedValue, final int limit) throws DataKitException {
        return new PendingResult<ArrayList<RowObject>>() {
            @Override
            public ArrayList<RowObject> await() {
                try {
                    lock.tryLock(WAIT_TIME, TimeUnit.MILLISECONDS);
                    if (!isAlive() || dataSourceClient==null)
                        throw new DataKitNotFoundException(new Status(Status.ERROR_BOUND));
                    Bundle bundle = new Bundle();
                    bundle.putInt("ds_id", dataSourceClient.getDs_id());
                    bundle.putLong("last_key", lastSyncedValue);
                    bundle.putInt("limit", limit);
                    prepareAndSend(bundle, MessageType.QUERYPRIMARYKEY);
                    queryPrimaryKeyCondition.await(WAIT_TIME, TimeUnit.MILLISECONDS);
                } catch (Exception e) {
                    queryPrimaryKeyData = null;
                    queryPrimaryKeyCondition.signal();
                }
                lock.unlock();
                return queryPrimaryKeyData;
            }

            @Override
            public void setResultCallback(ResultCallback<ArrayList<RowObject>> callback) {

            }
        };
    }

    public PendingResult<ArrayList<RowObject>> queryHFFromPrimaryKey(final DataSourceClient dataSourceClient, final long lastSyncedValue, final int limit) throws DataKitException {
        return new PendingResult<ArrayList<RowObject>>() {
            @Override
            public ArrayList<RowObject> await() {
                try {
                    lock.tryLock(WAIT_TIME, TimeUnit.MILLISECONDS);
                    if (!isAlive() || dataSourceClient==null)
                        throw new DataKitNotFoundException(new Status(Status.ERROR_BOUND));
                    Bundle bundle = new Bundle();
                    bundle.putInt("ds_id", dataSourceClient.getDs_id());
                    bundle.putLong("last_key", lastSyncedValue);
                    bundle.putInt("limit", limit);
                    prepareAndSend(bundle, MessageType.QUERYHFPRIMARYKEY);
                    queryHFPrimaryKeyCondition.await(WAIT_TIME, TimeUnit.MILLISECONDS);
                } catch (Exception e) {
                    queryHFPrimaryKeyData = null;
                    queryHFPrimaryKeyCondition.signal();
                }
                lock.unlock();
                return queryHFPrimaryKeyData;
            }

            @Override
            public void setResultCallback(ResultCallback<ArrayList<RowObject>> callback) {

            }
        };
    }

    public PendingResult<DataTypeLong> querySize() throws DataKitException {
        return new PendingResult<DataTypeLong>() {
            @Override
            public DataTypeLong await() {
                try {
                    lock.tryLock(WAIT_TIME, TimeUnit.MILLISECONDS);
                    if (!isAlive())
                        throw new DataKitNotFoundException(new Status(Status.ERROR_BOUND));
                    Bundle bundle = new Bundle();
                    prepareAndSend(bundle, MessageType.QUERYSIZE);
                    querySizeCondition.await(WAIT_TIME, TimeUnit.MILLISECONDS);
                } catch (Exception e) {
                    querySizeData = null;
                    querySizeCondition.signal();
                }
                lock.unlock();
                return querySizeData;
            }

            @Override
            public void setResultCallback(ResultCallback<DataTypeLong> callback) {

            }
        };
    }


    public void insert(final DataSourceClient dataSourceClient, final DataType dataType) throws DataKitException {
        try {
            if (!isAlive() || dataSourceClient==null || dataType==null)
                throw new DataKitNotFoundException(new Status(Status.ERROR_BOUND));
            Bundle bundle = new Bundle();
            bundle.putParcelable(DataType.class.getSimpleName(), dataType);
            bundle.putInt("ds_id", dataSourceClient.getDs_id());
            prepareAndSend(bundle, MessageType.INSERT);
        } catch (Exception e) {
            throw new DataKitException(e.getCause());
        }
    }

    public void insertHighFrequency(final DataSourceClient dataSourceClient, final DataTypeDoubleArray dataType) throws DataKitException {
        try {
            if (!isAlive() || dataSourceClient==null || dataType==null)
                throw new DataKitNotFoundException(new Status(Status.ERROR_BOUND));
            Bundle bundle = new Bundle();
            bundle.putParcelable(DataTypeDoubleArray.class.getSimpleName(), dataType);
            bundle.putInt("ds_id", dataSourceClient.getDs_id());
            prepareAndSend(bundle, MessageType.INSERT_HIGH_FREQUENCY);
        } catch (Exception e) {
            throw new DataKitException(e.getCause());
        }
    }
    private DataSource prepareDataSource(DataSourceBuilder dataSourceBuilder) {
        String versionName = null;
        int versionNumber = 0;
        try {
            PackageInfo pInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            versionName = pInfo.versionName;
            versionNumber = pInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        ApplicationBuilder applicationBuilder;
        if (dataSourceBuilder.build().getApplication() == null)
            applicationBuilder = new ApplicationBuilder();
        else
            applicationBuilder = new ApplicationBuilder(dataSourceBuilder.build().getApplication());
        Application application = applicationBuilder.setId(context.getPackageName()).setMetadata(METADATA.VERSION_NAME, versionName).setMetadata(METADATA.VERSION_NUMBER, String.valueOf(versionNumber)).build();
        dataSourceBuilder = dataSourceBuilder.setApplication(application);
        return dataSourceBuilder.build();
    }

    private class RemoteServiceConnection implements ServiceConnection {
        @Override
        public void onServiceConnected(ComponentName component, IBinder binder) {
            sendMessenger = new Messenger(binder);
            isBound = true;
            onConnectionListener.onConnected();
            Log.d(TAG, "onServiceConnected()...isBound=" + isBound);
        }

        @Override
        public void onServiceDisconnected(ComponentName component) {
            sendMessenger = null;
            isBound = false;
            Log.d(TAG, "onServiceDisconnected()...isBound=" + isBound);
        }
    }

    private class IncomingHandler extends Handler {
        IncomingHandler(Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(Message msg) {
            msg.getData().setClassLoader(Status.class.getClassLoader());
            receivedStatus = msg.getData().getParcelable(Status.class.getSimpleName());
            switch (msg.what) {
                case MessageType.INTERNAL_ERROR:
                    break;
                case MessageType.REGISTER:
                    lock.lock();
                    msg.getData().setClassLoader(DataSourceClient.class.getClassLoader());
                    registerData = msg.getData().getParcelable(DataSourceClient.class.getSimpleName());
                    registerCondition.signal();
                    lock.unlock();
                    break;
                case MessageType.FIND:
                    lock.lock();
                    msg.getData().setClassLoader(DataSourceClient.class.getClassLoader());
                    findData = msg.getData().getParcelableArrayList(DataSourceClient.class.getSimpleName());
                    findCondition.signal();
                    lock.unlock();
                    break;
                case MessageType.SUBSCRIBE:
                    lock.lock();
                    msg.getData().setClassLoader(DataType.class.getClassLoader());
                    subscribeData = msg.getData().getParcelable(Status.class.getSimpleName());
                    subscribeCondition.signal();
                    lock.unlock();
                    break;
                case MessageType.UNSUBSCRIBE:
                    lock.lock();
                    msg.getData().setClassLoader(Status.class.getClassLoader());
                    unsubscribeData = msg.getData().getParcelable(Status.class.getSimpleName());
                    unsubscribeCondition.signal();
                    lock.unlock();
                    break;
                case MessageType.UNREGISTER:
                    lock.lock();
                    msg.getData().setClassLoader(Status.class.getClassLoader());
                    unregisterData = msg.getData().getParcelable(Status.class.getSimpleName());
                    unregisterCondition.signal();
                    lock.unlock();
                    break;
                case MessageType.QUERY:
                    lock.lock();
                    msg.getData().setClassLoader(DataType.class.getClassLoader());
                    queryData = msg.getData().getParcelableArrayList(DataType.class.getSimpleName());
                    queryCondition.signal();
                    lock.unlock();
                    break;
                case MessageType.QUERYHFLASTN:
                    lock.lock();
                    msg.getData().setClassLoader(DataType.class.getClassLoader());
                    queryHFLastNData = msg.getData().getParcelableArrayList(DataType.class.getSimpleName());
                    queryHFLastNCondition.signal();
                    lock.unlock();
                    break;
                case MessageType.QUERYSIZE:
                    lock.lock();
                    msg.getData().setClassLoader(DataType.class.getClassLoader());
                    querySizeData = msg.getData().getParcelable(DataTypeLong.class.getSimpleName());
                    querySizeCondition.signal();
                    lock.unlock();
                    break;
                case MessageType.QUERYPRIMARYKEY:
                    lock.lock();
                    msg.getData().setClassLoader(RowObject.class.getClassLoader());
                    queryPrimaryKeyData = msg.getData().getParcelableArrayList(RowObject.class.getSimpleName());
                    queryPrimaryKeyCondition.signal();
                    lock.unlock();
                    break;
                case MessageType.QUERYHFPRIMARYKEY:
                    lock.lock();
                    msg.getData().setClassLoader(RowObject.class.getClassLoader());
                    queryHFPrimaryKeyData = msg.getData().getParcelableArrayList(RowObject.class.getSimpleName());
                    queryHFPrimaryKeyCondition.signal();
                    lock.unlock();
                    break;
                case MessageType.SUBSCRIBED_DATA:
                    msg.getData().setClassLoader(DataType.class.getClassLoader());
                    subscribedData = msg.getData().getParcelable(DataType.class.getSimpleName());
                    int ds_id = msg.getData().getInt("ds_id");
                    if (ds_idOnReceiveListenerHashMap.containsKey(ds_id))
                        ds_idOnReceiveListenerHashMap.get(ds_id).onReceived(subscribedData);
                    break;
                case MessageType.INSERT:
                    break;
                case MessageType.INSERT_HIGH_FREQUENCY:
                    break;
            }
        }
    }
}
