package org.md2k.datakitapi;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;

import org.md2k.datakitapi.datasource.DataSource;
import org.md2k.datakitapi.datasource.DataSourceClient;
import org.md2k.datakitapi.datatype.MessageType;
import org.md2k.datakitapi.datatype.data.DataType;
import org.md2k.datakitapi.listener.OnConnectionListener;
import org.md2k.datakitapi.listener.OnRegistrationListener;
import org.md2k.datakitapi.listener.PendingResult;
import org.md2k.datakitapi.listener.ReceiveCallback;
import org.md2k.datakitapi.listener.ResultCallback;
import org.md2k.datakitapi.status.Status;
import java.util.ArrayList;

/**
 * Created by smhssain on 5/11/2015.
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
    Object lock = new Object();
    DataSourceClient dataSourceClient = null;
    ArrayList<DataSourceClient> dataSourceClients;
    ArrayList<DataType> dataTypes;
    Status status;

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
        if (context.bindService(intent, this.connection, Context.BIND_AUTO_CREATE) == false) {
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

    public PendingResult<DataSourceClient> register(final DataSource dataSource) {
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
        return unregister_subscribe_unsubscribe(dataSourceClient, MessageType.UNSUBSCRIBE);
    }
    public void subscribe(final DataSourceClient dataSourceClient, ReceiveCallback<Integer> receiveCallback) {
//        return unregister_subscribe_unsubscribe(dataSourceClient,MessageType.SUBSCRIBE);
    }

    public PendingResult<Status> subscribe(final DataSourceClient dataSourceClient) {
        return unregister_subscribe_unsubscribe(dataSourceClient,MessageType.SUBSCRIBE);
    }
    public PendingResult<Status> unregister(final DataSourceClient dataSourceClient) {
        return unregister_subscribe_unsubscribe(dataSourceClient,MessageType.UNREGISTER);
    }

    private PendingResult<Status> unregister_subscribe_unsubscribe(final DataSourceClient dataSourceClient, final int messageType) {
        PendingResult<Status> pendingResult = new PendingResult<Status>() {
            @Override
            public Status await() {
                Thread t = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Bundle bundle = new Bundle();
                        bundle.putInt("ds_id", dataSourceClient.getDs_id());
                        prepareAndSend(bundle,messageType);
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
        return pendingResult;
    }

    public PendingResult<ArrayList<DataSourceClient>> find(final DataSource dataSource) {
        PendingResult<ArrayList<DataSourceClient>> pendingResult = new PendingResult<ArrayList<DataSourceClient>>() {
            @Override
            public ArrayList<DataSourceClient> await() {
                Thread t = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Bundle bundle = new Bundle();
                        bundle.putSerializable(DataSource.class.getSimpleName(), dataSource);
                        prepareAndSend(bundle,MessageType.FIND);
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
        final PendingResult<ArrayList<DataType>> pendingResult = new PendingResult<ArrayList<DataType>>() {
            @Override
            public ArrayList<DataType> await() {
                Thread t = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Bundle bundle = new Bundle();
                        bundle.putInt("ds_id", dataSourceClient.getDs_id());
                        bundle.putLong("starttimestamp", starttimestamp);
                        bundle.putLong("endtimestamp", endtimestamp);
                        prepareAndSend(bundle,MessageType.FIND);
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
        return pendingResult;
    }

    public void insert(DataSourceClient dataSourceClient, DataType dataType) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(DataType.class.getSimpleName(), dataType);
        bundle.putInt("ds_id", dataSourceClient.getDs_id());
        prepareAndSend(bundle, MessageType.INSERT);
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
                case MessageType.UNSUBSCRIBE:
                case MessageType.UNREGISTER:
                    status = (Status) msg.getData().getSerializable(Status.class.getSimpleName());
                    break;
                case MessageType.QUERY:
                    dataTypes = (ArrayList<DataType>) msg.getData().getSerializable(DataType.class.getSimpleName());
                    break;
                case MessageType.INSERT:
                    // no incoming message for INSERT
                    break;
            }
            synchronized (lock) {
                lock.notifyAll();
            }
        }
    }
}
