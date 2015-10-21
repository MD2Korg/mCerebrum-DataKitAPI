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
import android.os.RemoteException;

import org.md2k.datakitapi.messagehandler.OnReceiveListener;
import org.md2k.datakitapi.source.application.Application;
import org.md2k.datakitapi.source.application.ApplicationBuilder;
import org.md2k.datakitapi.source.datasource.DataSource;
import org.md2k.datakitapi.source.datasource.DataSourceBuilder;
import org.md2k.datakitapi.source.datasource.DataSourceClient;
import org.md2k.datakitapi.messagehandler.MessageType;
import org.md2k.datakitapi.datatype.DataType;
import org.md2k.datakitapi.messagehandler.OnConnectionListener;
import org.md2k.datakitapi.messagehandler.OnRegistrationListener;
import org.md2k.datakitapi.messagehandler.PendingResult;
import org.md2k.datakitapi.messagehandler.ResultCallback;
import org.md2k.datakitapi.status.Status;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Copyright (c) 2015, The University of Memphis, MD2K Center
 * - Syed Monowar Hossain <monowar.hossain@gmail.com>
 * All rights reserved.
 * <p/>
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * <p/>
 * * Redistributions of source code must retain the above copyright notice, this
 * list of conditions and the following disclaimer.
 * <p/>
 * * Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following disclaimer in the documentation
 * and/or other materials provided with the distribution.
 * <p/>
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
public class DataKitApi {
    private static final String TAG = DataKitApi.class.getSimpleName();
    private Context context;
    private ServiceConnection connection;//receives callbacks from bind and unbind invocations
    private boolean isBound = false;
    private Messenger sendMessenger = null;
    private Messenger replyMessenger = null; //invocation replies are processed by this Messenger
    private OnConnectionListener mListener;
    private OnRegistrationListener onRegistrationListener;
    Intent intent;
    final Object lock = new Object();
    DataSourceClient dataSourceClient = null;
    ArrayList<DataSourceClient> dataSourceClients;
    ArrayList<DataType> dataTypes;
    DataType dataType;
    Status status;
    //    OnReceiveListener onReceiveListener;
    HashMap<Integer, OnReceiveListener> ds_idOnReceiveListenerHashMap = new HashMap<>();

    public DataKitApi(Context context) {
        this.context = context;
        isBound = false;
    }

    public boolean connect(OnConnectionListener listener) {
        intent = new Intent();
        intent.setClassName("org.md2k.datakit", "org.md2k.datakit.ServiceDataKit");
        this.connection = new RemoteServiceConnection();
        mListener = listener;
        HandlerThread thread = new HandlerThread("MyHandlerThread");
        thread.start();
        IncomingHandler incomingHandler = new IncomingHandler(thread.getLooper());
        this.replyMessenger = new Messenger(incomingHandler);
        if (!context.bindService(intent, this.connection, Context.BIND_AUTO_CREATE)) {
            thread.quit();
            context.unbindService(connection);
            return false;
        }
        return true;
    }

    public void disconnect() {
        if (isBound) {
            context.unbindService(connection);
            isBound = false;
        }
    }

    private boolean prepareAndSend(Bundle bundle, int messageType) {
        Message message = Message.obtain(null, 0, 0, 0);
        message.what = messageType;

        message.setData(bundle);
        message.replyTo = replyMessenger;
        try {
            sendMessenger.send(message);
            return true;
        } catch (RemoteException e) {
            return false;
        }
    }

    public PendingResult<DataSourceClient> register(DataSourceBuilder dataSourceBuilder) {
        final DataSource dataSource = prepareDataSource(dataSourceBuilder);

        PendingResult<DataSourceClient> pendingResult = new PendingResult<DataSourceClient>() {
            @Override
            public DataSourceClient await() {
                Thread t = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Bundle bundle = new Bundle();
                        bundle.putSerializable(DataSource.class.getSimpleName(), dataSource);
                        prepareAndSend(bundle, MessageType.REGISTER);
                    }
                });
                t.start();
                synchronized (lock) {
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                return dataSourceClient;
            }

            @Override
            public void setResultCallback(ResultCallback<DataSourceClient> callback) {

            }
        };
        return pendingResult;
    }

    public PendingResult<Status> unsubscribe(final DataSourceClient dataSourceClient) {
        ds_idOnReceiveListenerHashMap.remove(dataSourceClient.getDs_id());
        return unregister_unsubscribe(dataSourceClient, MessageType.UNSUBSCRIBE);
    }

    public boolean subscribe(final DataSourceClient dataSourceClient, OnReceiveListener onReceiveListener) {
        ds_idOnReceiveListenerHashMap.put(dataSourceClient.getDs_id(), onReceiveListener);
//        this.onReceiveListener = onReceiveListener;
//        onReceiveListener.onReceived()
        Bundle bundle = new Bundle();
        bundle.putInt("ds_id", dataSourceClient.getDs_id());
        return prepareAndSend(bundle, MessageType.SUBSCRIBE);
    }

    public PendingResult<Status> unregister(final DataSourceClient dataSourceClient) {
        return unregister_unsubscribe(dataSourceClient, MessageType.UNREGISTER);
    }

    private PendingResult<Status> unregister_unsubscribe(final DataSourceClient dataSourceClient, final int messageType) {
        PendingResult<Status> pendingResult = new PendingResult<Status>() {
            @Override
            public Status await() {
                Thread t = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Bundle bundle = new Bundle();
                        bundle.putInt("ds_id", dataSourceClient.getDs_id());
                        prepareAndSend(bundle, messageType);
                    }
                });
                t.start();
                synchronized (lock) {
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                return status;
            }

            @Override
            public void setResultCallback(ResultCallback<Status> callback) {
                callback.onResult(status);
            }
        };
        return pendingResult;
    }

    private DataSource prepareDataSource(DataSourceBuilder dataSourceBuilder) {
        String versionNumber = null;
        int versionCode = 0;
        try {
            PackageInfo pInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            versionNumber = pInfo.versionName;
            versionCode = pInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        ApplicationBuilder applicationBuilder;
        if (dataSourceBuilder.build().getApplication() == null)
            applicationBuilder = new ApplicationBuilder();
        else
            applicationBuilder = new ApplicationBuilder(dataSourceBuilder.build().getApplication());
        Application application = applicationBuilder.setType(context.getPackageName()).setMetadata("version_number", versionNumber).setMetadata("version_code", String.valueOf(versionCode)).build();
        dataSourceBuilder = dataSourceBuilder.setApplication(application);
        return dataSourceBuilder.build();
    }

    public PendingResult<ArrayList<DataSourceClient>> find(DataSourceBuilder dataSourceBuilder) {
        final DataSource dataSource = dataSourceBuilder.build();

        PendingResult<ArrayList<DataSourceClient>> pendingResult = new PendingResult<ArrayList<DataSourceClient>>() {
            @Override
            public ArrayList<DataSourceClient> await() {
                Thread t = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Bundle bundle = new Bundle();
                        bundle.putSerializable(DataSource.class.getSimpleName(), dataSource);
                        prepareAndSend(bundle, MessageType.FIND);
                    }
                });
                t.start();
                synchronized (lock) {
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                return dataSourceClients;
            }

            @Override
            public void setResultCallback(ResultCallback<ArrayList<DataSourceClient>> callback) {

            }
        };
        return pendingResult;
    }

    public PendingResult<ArrayList<DataType>> query(final DataSourceClient dataSourceClient, final long starttimestamp, final long endtimestamp) {
        return new PendingResult<ArrayList<DataType>>() {
            @Override
            public ArrayList<DataType> await() {
                Thread t = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Bundle bundle = new Bundle();
                        bundle.putInt("ds_id", dataSourceClient.getDs_id());
                        bundle.putLong("starttimestamp", starttimestamp);
                        bundle.putLong("endtimestamp", endtimestamp);
                        prepareAndSend(bundle, MessageType.QUERY);
                    }
                });
                t.start();
                synchronized (lock) {

                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                return dataTypes;
            }

            @Override
            public void setResultCallback(ResultCallback<ArrayList<DataType>> callback) {

            }
        };
    }

    public PendingResult<ArrayList<DataType>> query(final DataSourceClient dataSourceClient, final int last_n_sample) {
        return new PendingResult<ArrayList<DataType>>() {
            @Override
            public ArrayList<DataType> await() {
                Thread t = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Bundle bundle = new Bundle();
                        bundle.putInt("ds_id", dataSourceClient.getDs_id());
                        bundle.putInt("last_n_sample", last_n_sample);
                        prepareAndSend(bundle, MessageType.QUERY);
                    }
                });
                t.start();
                synchronized (lock) {
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                return dataTypes;
            }

            @Override
            public void setResultCallback(ResultCallback<ArrayList<DataType>> callback) {

            }
        };
    }

    public PendingResult<Status> insert(final DataSourceClient dataSourceClient, final DataType dataType) {
        return new PendingResult<Status>() {
            @Override
            public Status await() {
                Thread t = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Bundle bundle = new Bundle();
                        bundle.putSerializable(DataType.class.getSimpleName(), dataType);
                        bundle.putInt("ds_id", dataSourceClient.getDs_id());
                        prepareAndSend(bundle, MessageType.INSERT);
                    }
                });
                t.start();
                synchronized (lock) {

                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                return status;
            }

            @Override
            public void setResultCallback(ResultCallback<Status> callback) {

            }
        };
    }

    private class RemoteServiceConnection implements ServiceConnection {
        @Override
        public void onServiceConnected(ComponentName component, IBinder binder) {
            sendMessenger = new Messenger(binder);
            isBound = true;
            mListener.onConnected();
        }

        @Override
        public void onServiceDisconnected(ComponentName component) {
            sendMessenger = null;
            isBound = false;
        }
    }

    private class IncomingHandler extends Handler {
        IncomingHandler(Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MessageType.REGISTER:
                    dataSourceClient = (DataSourceClient) msg.getData().getSerializable(DataSourceClient.class.getSimpleName());
                    break;
                case MessageType.FIND:
                    dataSourceClients = (ArrayList<DataSourceClient>) msg.getData().getSerializable(DataSourceClient.class.getSimpleName());
                    break;
                case MessageType.SUBSCRIBE:
                    msg.getData().getSerializable(DataType.class.getSimpleName());
                    return;
                case MessageType.UNSUBSCRIBE:
                case MessageType.UNREGISTER:
                    status = (Status) msg.getData().getSerializable(Status.class.getSimpleName());
                    break;
                case MessageType.QUERY:
                    dataTypes = (ArrayList<DataType>) msg.getData().getSerializable(DataType.class.getSimpleName());
                    break;
                case MessageType.INSERT:
                    status = (Status) msg.getData().getSerializable(Status.class.getSimpleName());
                    break;
                case MessageType.SUBSCRIBED_DATA:
                    dataType = (DataType) msg.getData().getSerializable(DataType.class.getSimpleName());
                    int ds_id = msg.getData().getInt("ds_id");
                    if (ds_idOnReceiveListenerHashMap.containsKey(ds_id))
                        ds_idOnReceiveListenerHashMap.get(ds_id).onReceived(dataType);
                    return;
            }
            synchronized (lock) {
                lock.notify();
            }
        }
    }
}
