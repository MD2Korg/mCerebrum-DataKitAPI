/*
 * Copyright (c) 2018, The University of Memphis, MD2K Center of Excellence
 *
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
import android.os.Parcelable;
import android.os.RemoteException;
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
import org.md2k.datakitapi.source.METADATA;
import org.md2k.datakitapi.source.application.Application;
import org.md2k.datakitapi.source.application.ApplicationBuilder;
import org.md2k.datakitapi.source.datasource.DataSource;
import org.md2k.datakitapi.source.datasource.DataSourceBuilder;
import org.md2k.datakitapi.source.datasource.DataSourceClient;
import org.md2k.datakitapi.status.Status;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * Executes calls to <code>DataKit</code>.
 */
class DataKitAPIExecute {
    /** Time in milliseconds that a thread should sleep while waiting for other processes to terminate. */
    public static final int THREAD_SLEEP_MILLI = 1000;
    private static final String TAG = DataKitAPIExecute.class.getSimpleName();
    private boolean isConnected;

    /** Session identifier. <p>Default is -1.</p> */
    int sessionId = -1;

    /** ArrayList of <code>RowObject</code>s matching the primary key query. */
    private ArrayList<RowObject> queryPrimaryKeyData;

    /** Size of a query. */
    private DataTypeLong querySizeData;

    /** ArrayList of data types matching the query. */
    private ArrayList<DataType> queryData;

    /** Status after a data source has been unregistered. */
    private Status unregisterData;

    /** Status after a data source has been unsubscribed. */
    private Status unsubscribeData;

    /** ArrayList of <code>DataSourceClient</code>s to be found. */
    private ArrayList<DataSourceClient> findData;

    /** The registered <code>DataSourceClient</code>. */
    private DataSourceClient registerData;

    /** Status after a data source that been subscribed. */
    private Status subscribeData;

    /** Listens for messages from remote threads */
    HandlerThread threadRemoteListener;

    /** Handles incoming messages. */
    IncomingHandler incomingHandler;

    /** Semaphore that waits for receipt of a permit. */
    private Semaphore semaphoreReceive;

    /** Contains <code>ds_id</code>, <code>DataType</code> pairs.
     * <p>
     *     Contains all currently subscribed <code>DataType</code>s.
     *     <code>OnReceiveListener</code> takes a <code>DataType</code> parameter.
     * </p>
     */
    private HashMap<Integer, OnReceiveListener> ds_idOnReceiveListenerHashMap;

    /** Android context. */
    private Context context;

    /** Receives callbacks from bind and unbind invocations. */
    private ServiceConnection connection;

    /** Handles outbound messages. */
    private Messenger sendMessenger = null;

    /** Processes invocation replies. */
    private Messenger replyMessenger = null;

    /** Callback interface that listens for <code>DataKit</code> connections. */
    private OnConnectionListener onConnectionListener;

    /** Wait time for <code>semaphoreReceive</code> in milliseconds.
     *
     * <p>
     *     Default is 30,000 milliseconds.
     * </p>
     */
    private static final long WAIT_TIME = 30000;

    /** Whether <code>DataKit</code> is being disconnected or not. */
    private boolean isDisconnecting;


    /**
     * Constructor
     *
     * <p>
     *     Default values for the fields are as follows:
     *     <ul>
     *         <li><code>isConnected</code> is false </li>
     *         <li><code>sessionId</code> is -1</li>
     *         <li><code>sendMessenger</code> is null</li>
     *         <li><code>isDisconnecting</code> is false</li>
     *         <li><code>ds_idOnReceiveListenerHashMap</code> a new HashMap</li>
     *     </ul>
     * </p>
     *
     * @param context Android context
     */
    public DataKitAPIExecute(Context context) {
        this.context = context;
        isConnected = false;
        sessionId = -1;
        sendMessenger = null;
        isDisconnecting = false;
        ds_idOnReceiveListenerHashMap = new HashMap<>();
    }

    /**
     * @return Whether <code>DataKit</code> is connected or not.
     */
    public boolean isConnected() {
        return sendMessenger != null && isConnected && sessionId != -1;
    }

    /**
     * Creates a new <code>HandlerThread</code>, assigns it's <code>looper</code> to a new
     * <code>IncomingHandler</code> which is then passed to a new <code>Messenger</code>.
     */
    private void createThreadRemoteListener() {
        threadRemoteListener = new HandlerThread("MyHandlerThread");
        threadRemoteListener.start();

        incomingHandler = new IncomingHandler(threadRemoteListener.getLooper());

        this.replyMessenger = new Messenger(incomingHandler);
    }

    /**
     * Starts a remote service and binds <code>replyMessenger</code> to it.
     *
     * <p>
     *     The service that starts is determined by <code>Constants.PACKAGE_NAME</code>, which defaults
     *     to <code>"org.md2k.datakit"</code>, and <code>Constants.SERVICE_NAME</code>, which defaults
     *     to <code>"org.md2k.datakit.ServiceDataKit"</code>.
     * </p>
     *
     * @throws DataKitNotFoundException Thrown if <code>DataKit</code> is disconnected.
     */
    private void startRemoteService() throws DataKitNotFoundException {
        Intent intent = new Intent();
        intent.setClassName(Constants.PACKAGE_NAME, Constants.SERVICE_NAME);

        this.connection = new RemoteServiceConnection();

        intent.putExtra("name", context.getPackageName());
        intent.putExtra("messenger", this.replyMessenger);

        if (!context.bindService(intent, this.connection, Context.BIND_AUTO_CREATE)) {
            disconnect();
            throw new DataKitNotFoundException(new Status(Status.ERROR_BOUND));
        }
    }


    /**
     * Attempts to connect the caller to <code>DataKit</code>.
     *
     * <p>
     *     If <code>DataKit</code> is disconnecting, the thread should sleep for 1000 milliseconds.
     *     This is continually tried until <code>DataKit</code> is not disconnecting.
     *
     *     When trying to connect the following occurs:
     *     <ol>
     *         <li>The <code>onConnectionListener</code> is updated.</li>
     *         <li>The <code>ds_idOnReceiveListenerHashMap</code> is cleared.</li>
     *         <li>A new <code>sessionId</code> is randomly generated.</li>
     *         <li>A new semaphore is created with 0 permits and a true fairness setting.</li>
     *         <li><code>createThreadRemoteListener()</code> is called.</li>
     *         <li><code>startRemoteService()</code> is called.</li>
     *     </ol>
     * </p>
     *
     * @param onConnectionListener Callback interface listening for connection verification.
     * @throws DataKitException Is ignored.
     */
    protected void connect(OnConnectionListener onConnectionListener) throws DataKitException {
        try {
            while (isDisconnecting) {
                try {
                    Thread.sleep(THREAD_SLEEP_MILLI);
                } catch (Exception ignored) {}
            }
            this.onConnectionListener = onConnectionListener;
            ds_idOnReceiveListenerHashMap.clear();
            sessionId = new Random().nextInt();
            semaphoreReceive = new Semaphore(0, true);
            createThreadRemoteListener();
            startRemoteService();
        } catch (Exception ignored) {
        }

    }

    /**
     * Locks the thread.
     */
    void lock() {
        try {
            if (!isConnected) return;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Unlocks the thread.
     */
    void unlock() {}

    /**
     * Disconnects the caller from <code>DataKit</code>.
     *
     * <p>
     *     When disconnecting the following occurs:
     *     <ol>
     *         <li><code>isConnected</code> is set to false.</li>
     *         <li><code>isDisconnecting</code> is set to true.</li>
     *         <li><code>sessionId</code> is set to -1.</li>
     *         <li><code>ds_idOnReceiveListenerHashMap</code> is cleared.</li>
     *         <li>If <code>threadRemoteListener</code> is not null and alive then it calls
     *         <code>quitSafely()</code></li>
     *         <li>If <code>threadRemoteListener</code> is null and <code>incomingHandler</code>
     *         is null, then remaining callbacks are removed.</li>
     *         <li><code>threadRemoteListener</code> and <code>incomingHandler</code> are then set
     *         to null.</li>
     *         <li>The remote service is unbound and <code>isDisconnecting</code> is set to false.</li>
     *     </ol>
     * </p>
     */
    public void disconnect() {
        isConnected = false;
        isDisconnecting = true;
        sessionId = -1;
        ds_idOnReceiveListenerHashMap.clear();

        if (threadRemoteListener != null && threadRemoteListener.isAlive())
            threadRemoteListener.quitSafely();

        if (threadRemoteListener != null && incomingHandler != null)
            incomingHandler.removeCallbacks(threadRemoteListener);

        threadRemoteListener = null;
        incomingHandler = null;

        try {
            context.unbindService(connection);
            Thread.sleep(THREAD_SLEEP_MILLI);
        } catch (Exception ignored) {}

        isDisconnecting = false;
    }


    /**
     * Constructs a message and sends it to <code>DataKit</code>.
     *
     * @param bundle
     * @param messageType Type of message being sent.
     * @throws RemoteException Thrown when the message is not sent successfully
     */
    private void prepareAndSend(Bundle bundle, int messageType) throws RemoteException {
        Message message = Message.obtain(null, 0, 0, 0);
        message.what = messageType;
        message.arg1 = sessionId;
        message.setData(bundle);
        message.replyTo = replyMessenger;
        sendMessenger.send(message);
    }


    /**
     * Registers the desired <code>DataSourceClient</code> with <code>DataKit</code>.
     *
     * @param dataSourceBuilder Builder object of the data source to register.
     * @return The registered <code>DataSourceClient</code>.
     * @throws DataKitException
     */
    public PendingResult<DataSourceClient> register(final DataSourceBuilder dataSourceBuilder) throws DataKitException {
        PendingResult<DataSourceClient> pendingResult = new PendingResult<DataSourceClient>() {
            @Override
            public DataSourceClient await() {
                try {
                    registerData = null;
                    lock();
                    DataSource dataSource = prepareDataSource(dataSourceBuilder);
                    Bundle bundle = new Bundle();
                    bundle.putParcelable(DataSource.class.getSimpleName(), dataSource);
                    prepareAndSend(bundle, MessageType.REGISTER);
                    semaphoreReceive.tryAcquire(WAIT_TIME, TimeUnit.MILLISECONDS);
                } catch (Exception e) {
                    registerData = null;
                } finally {
                    unlock();
                }
                return registerData;
            }
        };
        return pendingResult;
    }

    /**
     * Unsubscribes the given data source identifier from <code>DataKit</code>.
     *
     * @param ds_id Data source identifier to unsubscribe.
     * @return The status of the application after the data source is unsubscribed.
     * @throws DataKitException Thrown if the <code>context</code> or it's package name are null.
     */
    public PendingResult<Status> unsubscribe(final int ds_id) throws DataKitException {
        PendingResult<Status> pendingResult = new PendingResult<Status>() {
            @Override
            public Status await() {
                try {
                    unsubscribeData = null;
                    lock();
                    ds_idOnReceiveListenerHashMap.remove(ds_id);
                    Bundle bundle = new Bundle();
                    bundle.putInt(Constants.RC_DSID, ds_id);
                    if (context == null || context.getPackageName() == null)
                        throw new Exception("abc");
                    bundle.putString(Constants.PACKAGE_NAME, context.getPackageName());
                    prepareAndSend(bundle, MessageType.UNSUBSCRIBE);
                    semaphoreReceive.tryAcquire(WAIT_TIME, TimeUnit.MILLISECONDS);
                } catch (Exception e) {
                    unsubscribeData = null;
                } finally {
                    unlock();
                }
                return unsubscribeData;
            }
        };
        return pendingResult;
    }

    /**
     * Unregisters the given data source from <code>DataKit</code>.
     *
     * @param dataSourceClient Data source to unregister.
     * @return The status of the application after the data source is unregistered.
     * @throws DataKitException
     */
    public PendingResult<Status> unregister(final DataSourceClient dataSourceClient) throws DataKitException {
        PendingResult<Status> pendingResult = new PendingResult<Status>() {
            @Override
            public Status await() {
                try {
                    unregisterData = null;
                    lock();
                    Bundle bundle = new Bundle();
                    bundle.putInt(Constants.RC_DSID, dataSourceClient.getDs_id());
                    prepareAndSend(bundle, MessageType.UNREGISTER);
                    semaphoreReceive.tryAcquire(WAIT_TIME, TimeUnit.MILLISECONDS);
                } catch (Exception e) {
                    unregisterData = null;
                } finally {
                    unlock();
                }
                return unregisterData;
            }
        };
        return pendingResult;
    }

    /**
     * Subscribes the given data source to <code>DataKit</code>.
     *
     * @param dataSourceClient Data source to subscribe to <code>DataKit</code>.
     * @param onReceiveListener Callback listening for receipt of the subscription.
     * @return The status of the application after the data source is subscribed.
     * @throws DataKitException
     */
    public Status subscribe(final DataSourceClient dataSourceClient, OnReceiveListener onReceiveListener) throws DataKitException {
        try {
            subscribeData = null;
            lock();
            ds_idOnReceiveListenerHashMap.put(dataSourceClient.getDs_id(), onReceiveListener);

            Bundle bundle = new Bundle();
            bundle.putInt(Constants.RC_DSID, dataSourceClient.getDs_id());
            bundle.putString(Constants.PACKAGE_NAME, context.getPackageName());
            prepareAndSend(bundle, MessageType.SUBSCRIBE);

            semaphoreReceive.tryAcquire(WAIT_TIME, TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            Log.e(TAG, "Subscribe error..." + dataSourceClient.getDs_id());
            subscribeData = null;
        } finally {
            unlock();
        }
        return subscribeData;
    }


    /**
     * Finds the desired data sources in the database.
     *
     * @param dataSourceBuilder Builder object for the desired data source
     * @return ArrayList of <code>DataSourceClient</code> objects.
     * @throws DataKitException
     */
    public PendingResult<ArrayList<DataSourceClient>> find(final DataSourceBuilder dataSourceBuilder) throws DataKitException {
        PendingResult<ArrayList<DataSourceClient>> pendingResult = new PendingResult<ArrayList<DataSourceClient>>() {
            @Override
            public ArrayList<DataSourceClient> await() {
                try {
                    findData = null;
                    lock();
                    final DataSource dataSource = dataSourceBuilder.build();
                    Bundle bundle = new Bundle();
                    bundle.putParcelable(DataSource.class.getSimpleName(), dataSource);
                    prepareAndSend(bundle, MessageType.FIND);
                    semaphoreReceive.tryAcquire(WAIT_TIME, TimeUnit.MILLISECONDS);
                } catch (Exception e) {
                    findData = null;
                } finally {
                    unlock();
                }
                return findData;
            }
        };
        return pendingResult;
    }

    /**
     * Queries the database for samples from the given data source during the given time frame.
     *
     * @param dataSourceClient Data source of the samples
     * @param starttimestamp Beginning of the desired time frame.
     * @param endtimestamp End of the desired time frame.
     * @return An ArrayList of data types matching the query.
     * @throws DataKitException
     */
    public PendingResult<ArrayList<DataType>> query(final DataSourceClient dataSourceClient,
                                                    final long starttimestamp,
                                                    final long endtimestamp) throws DataKitException {
        return new PendingResult<ArrayList<DataType>>() {
            @Override
            public ArrayList<DataType> await() {
                try {
                    queryData = null;
                    lock();

                    Bundle bundle = new Bundle();
                    bundle.putInt(Constants.RC_DSID, dataSourceClient.getDs_id());
                    bundle.putLong(Constants.RC_STARTTIMESTAMP, starttimestamp);
                    bundle.putLong(Constants.RC_ENDTIMESTAMP, endtimestamp);
                    prepareAndSend(bundle, MessageType.QUERY);

                    semaphoreReceive.tryAcquire(WAIT_TIME, TimeUnit.MILLISECONDS);
                } catch (Exception e) {
                    queryData = null;
                } finally {
                    unlock();
                }
                return queryData;
            }
        };
    }

    /**
     * Queries the database for the latest number of samples from the desired data source.
     *
     * @param dataSourceClient Data source of the desired samples.
     * @param last_n_sample Last n samples, n being a nonzero positive integer.
     * @return An ArrayList of data types matching the query.
     * @throws DataKitException
     */
    public PendingResult<ArrayList<DataType>> query(final DataSourceClient dataSourceClient,
                                                    final int last_n_sample) throws DataKitException {

        return new PendingResult<ArrayList<DataType>>() {
            @Override
            public ArrayList<DataType> await() {
                try {
                    queryData = null;
                    lock();

                    Bundle bundle = new Bundle();
                    bundle.putInt(Constants.RC_DSID, dataSourceClient.getDs_id());
                    bundle.putInt(Constants.RC_LAST_N_SAMPLE, last_n_sample);
                    prepareAndSend(bundle, MessageType.QUERY);

                    semaphoreReceive.tryAcquire(WAIT_TIME, TimeUnit.MILLISECONDS);
                } catch (Exception e) {
                    queryData = null;
                } finally {
                    unlock();
                }
                return queryData;
            }
        };
    }

    /**
     * Queries the database for certain rows.
     *
     * @param dataSourceClient Data source of desired samples
     * @param lastSyncedValue Key of the desired row.
     * @param limit Number of rows to return.
     * @return An ArrayList of rows from the database.
     * @throws DataKitException
     */
    public PendingResult<ArrayList<RowObject>> queryFromPrimaryKey(final DataSourceClient dataSourceClient,
                                                                   final long lastSyncedValue,
                                                                   final int limit) throws DataKitException {
        return new PendingResult<ArrayList<RowObject>>() {
            @Override
            public ArrayList<RowObject> await() {
                try {
                    queryPrimaryKeyData = null;
                    lock();

                    Bundle bundle = new Bundle();
                    bundle.putInt(Constants.RC_DSID, dataSourceClient.getDs_id());
                    bundle.putLong(Constants.RC_LAST_KEY, lastSyncedValue);
                    bundle.putInt(Constants.RC_LIMIT, limit);
                    prepareAndSend(bundle, MessageType.QUERYPRIMARYKEY);

                    semaphoreReceive.tryAcquire(WAIT_TIME, TimeUnit.MILLISECONDS);
                } catch (Exception e) {
                    queryPrimaryKeyData = null;
                } finally {
                    unlock();
                }
                return queryPrimaryKeyData;
            }
        };
    }

    /**
     * Determines the size of the query in number of columns.
     *
     * @return The size of the query.
     * @throws DataKitException
     */
    public PendingResult<DataTypeLong> querySize() throws DataKitException {
        return new PendingResult<DataTypeLong>() {
            @Override
            public DataTypeLong await() {
                try {
                    querySizeData = null;
                    lock();

                    Bundle bundle = new Bundle();
                    prepareAndSend(bundle, MessageType.QUERYSIZE);

                    semaphoreReceive.tryAcquire(WAIT_TIME, TimeUnit.MILLISECONDS);
                } catch (Exception e) {
                    querySizeData = null;
                } finally {
                    unlock();
                }
                return querySizeData;
            }
        };
    }


    /**
     * Bundles a data source and it's samples and sends it to <code>DataKit</code>.
     *
     * @param dataSourceClient Data source to insert.
     * @param dataTypes Array of data types to insert.
     * @throws DataKitException
     */
    public void insert(final DataSourceClient dataSourceClient, final DataType[] dataTypes) throws DataKitException {
        try {
            lock();
            Bundle bundle = new Bundle();
            bundle.putParcelableArray(DataType.class.getSimpleName(), dataTypes);
            bundle.putInt(Constants.RC_DSID, dataSourceClient.getDs_id());
            prepareAndSend(bundle, MessageType.INSERT);
        } catch (Exception e) {
            throw new DataKitException(e.getCause());
        } finally {
            unlock();
        }
    }

    /**
     * Set the summary of the given data source.
     *
     * @param dataSourceClient Data source whose summary is being set.
     * @param dataType
     * @throws DataKitException
     */
    public void setSummary(final DataSourceClient dataSourceClient, final DataType dataType) throws DataKitException {
        try {
            lock();
            Bundle bundle = new Bundle();
            bundle.putParcelable(DataType.class.getSimpleName(), dataType);
            bundle.putParcelable(Constants.RC_DATASOURCE_CLIENT, dataSourceClient);
            prepareAndSend(bundle, MessageType.SUMMARY);
        } catch (Exception e) {
            throw new DataKitException(e.getCause());
        } finally {
            unlock();
        }
    }

    /**
     * Bundles high frequency samples and sends them to <code>DataKit</code>.
     *
     * @param ds_id Data source identifier.
     * @param dataTypes Array of high frequency samples.
     * @throws DataKitException
     */
    public void insertHighFrequency(int ds_id, final DataTypeDoubleArray[] dataTypes) throws DataKitException {
        try {
            lock();
            Bundle bundle = new Bundle();
            bundle.putParcelableArray(DataTypeDoubleArray.class.getSimpleName(), dataTypes);
            bundle.putInt(Constants.RC_DSID, ds_id);
            prepareAndSend(bundle, MessageType.INSERT_HIGH_FREQUENCY);
        } catch (Exception e) {
            throw new DataKitException(e.getCause());
        } finally {
            unlock();
        }
    }

    /**
     * Configures and builds the <code>Application</code> of the <code>DataSource</code>.
     *
     * @param dataSourceBuilder Builder for the desired data source.
     * @return The desired data source.
     */
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

        Application application = applicationBuilder.setId(context.getPackageName())
                                                    .setMetadata(METADATA.VERSION_NAME, versionName)
                                                    .setMetadata(METADATA.VERSION_NUMBER, String.valueOf(versionNumber))
                                                    .build();
        dataSourceBuilder = dataSourceBuilder.setApplication(application);
        return dataSourceBuilder.build();
    }

    /**
     * Nested class for managing the remote service connection.
     */
    private class RemoteServiceConnection implements ServiceConnection {
        /**
         * Creates a new <code>Messenger</code> for the connected service.
         *
         * @param component Identifier for the desired component.
         * @param binder For binding the service's <code>Messenger</code> to the caller.
         */
        @Override
        public void onServiceConnected(ComponentName component, IBinder binder) {
            sendMessenger = new Messenger(binder);
            isConnected = true;
            onConnectionListener.onConnected();
        }

        /**
         * Sets the service's <code>Messenger</code> to null.
         *
         * @param component Identifier for the desired component.
         */
        @Override
        public void onServiceDisconnected(ComponentName component) {
            sendMessenger = null;
            isConnected = false;
        }
    }

    /**
     * Nested class for handling incoming messages.
     */
    private class IncomingHandler extends Handler {

        /**
         * Constructor
         *
         * @param looper Looper to be bound to this handler.
         */
        IncomingHandler(Looper looper) {
            super(looper);
        }

        /**
         * Determines what code to execute based on the incoming message's type.
         *
         * <p>
         *     The message types and their implications are as follows:
         *     <ul>
         *         <ul>Break without setting variables:
         *             <li><code>INTERNAL_ERROR</code></li>
         *             <li><code>INSERT</code></li>
         *             <li><code>INSERT_HIGH_FREQUENCY</code></li>
         *         </ul>
         *         <ul>Set their corresponding variables to the appropriate <code>DataType</code>,
         *         <code>Status</code>, or null.
         *             <li><code>REGISTER</code> -- <code>registerData</code></li>
         *             <li><code>UNREGISTER</code> -- <code>unregisterData</code></li>
         *             <li><code>SUBSCRIBE</code> -- <code>subscribeData</code></li>
         *             <li><code>UNSUBSCRIBE</code> -- <code>unsubscribeData</code></li>
         *             <li><code>FIND</code> -- <code>findData</code></li>
         *             <li><code>QUERY</code> -- <code>queryData</code></li>
         *             <li><code>QUERYSIZE</code> -- <code>querySizeData</code></li>
         *             <li><code>QUERYPRIMARYKEY</code> -- <code>queryPrimaryKey</code></li>
         *         </ul>
         *         <code>SUBSCRIBED_DATA</code> populates <code>ds_idOnReceiveListenerHashMap</code>
         *         with all of the currently subscribed <code>DataTypes</code>.
         *     </ul>
         * </p>
         *
         * @param msg Received message.
         */
        @Override
        public void handleMessage(Message msg) {
            if (!isConnected()) {
                return;
            }
            msg.getData().setClassLoader(Status.class.getClassLoader());
            int curSessionId = msg.arg1;
            switch (msg.what) {
                case MessageType.INTERNAL_ERROR:
                    break;

                case MessageType.REGISTER:
                    msg.getData().setClassLoader(DataSourceClient.class.getClassLoader());
                    if (curSessionId != sessionId)
                        registerData = null;
                    else
                        registerData = msg.getData().getParcelable(DataSourceClient.class.getSimpleName());

                    semaphoreReceive.release();
                    break;

                case MessageType.UNREGISTER:
                    msg.getData().setClassLoader(Status.class.getClassLoader());
                    if (curSessionId != sessionId)
                        unregisterData = null;
                    else
                        unregisterData = msg.getData().getParcelable(Status.class.getSimpleName());

                    semaphoreReceive.release();
                    break;

                case MessageType.SUBSCRIBE:
                    msg.getData().setClassLoader(DataType.class.getClassLoader());
                    if (curSessionId != sessionId)
                        subscribeData = null;
                    else
                        subscribeData = msg.getData().getParcelable(Status.class.getSimpleName());

                    semaphoreReceive.release();
                    break;

                case MessageType.UNSUBSCRIBE:
                    msg.getData().setClassLoader(Status.class.getClassLoader());
                    if (curSessionId != sessionId)
                        unsubscribeData = null;
                    else
                        unsubscribeData = msg.getData().getParcelable(Status.class.getSimpleName());

                    semaphoreReceive.release();
                    break;

                case MessageType.FIND:
                    msg.getData().setClassLoader(DataSourceClient.class.getClassLoader());
                    if (curSessionId != sessionId)
                        findData = null;
                    else
                        findData = msg.getData().getParcelableArrayList(DataSourceClient.class.getSimpleName());

                    semaphoreReceive.release();
                    break;

                case MessageType.INSERT:
                    break;

                case MessageType.INSERT_HIGH_FREQUENCY:
                    break;

                case MessageType.QUERY:
                    msg.getData().setClassLoader(DataType.class.getClassLoader());
                    if (curSessionId != sessionId)
                        queryData = null;
                    else
                        queryData = msg.getData().getParcelableArrayList(DataType.class.getSimpleName());

                    semaphoreReceive.release();
                    break;

                case MessageType.QUERYSIZE:
                    msg.getData().setClassLoader(DataType.class.getClassLoader());
                    if (curSessionId != sessionId)
                        querySizeData = null;
                    else
                        querySizeData = msg.getData().getParcelable(DataTypeLong.class.getSimpleName());

                    semaphoreReceive.release();
                    break;

                case MessageType.QUERYPRIMARYKEY:
                    msg.getData().setClassLoader(RowObject.class.getClassLoader());
                    if (curSessionId != sessionId)
                        queryPrimaryKeyData = null;
                    else
                        queryPrimaryKeyData = msg.getData().getParcelableArrayList(RowObject.class.getSimpleName());

                    semaphoreReceive.release();
                    break;

                case MessageType.SUBSCRIBED_DATA:
                    try {
                        msg.getData().setClassLoader(DataType[].class.getClassLoader());
                        Parcelable[] parcelables = msg.getData().getParcelableArray(DataType.class.getSimpleName());
                        assert parcelables != null;
                        int ds_id = msg.getData().getInt(Constants.RC_DSID, -1);

                        if (sessionId != -1 && ds_id != -1 && ds_idOnReceiveListenerHashMap.containsKey(ds_id)) {
                            for (Parcelable parcelable : parcelables)
                                ds_idOnReceiveListenerHashMap.get(ds_id).onReceived((DataType) parcelable);
                        }
                    } catch (Exception ignored) {}
                    break;
            }
        }
    }
}
