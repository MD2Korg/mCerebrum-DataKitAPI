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

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Handler;

import org.md2k.datakitapi.datatype.DataType;
import org.md2k.datakitapi.datatype.DataTypeDoubleArray;
import org.md2k.datakitapi.datatype.DataTypeLong;
import org.md2k.datakitapi.datatype.RowObject;
import org.md2k.datakitapi.exception.DataKitException;
import org.md2k.datakitapi.exception.DataKitNotFoundException;
import org.md2k.datakitapi.messagehandler.OnConnectionListener;
import org.md2k.datakitapi.messagehandler.OnReceiveListener;
import org.md2k.datakitapi.source.datasource.DataSourceBuilder;
import org.md2k.datakitapi.source.datasource.DataSourceClient;
import org.md2k.datakitapi.status.Status;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Handles exception checks and handles outside method calls for <code>DataKitAPIExecute</code>.
 */
public class DataKitAPI {
    private static final String TAG = DataKitAPI.class.getSimpleName();
    private static final int BUFFER_SIZE = 1<<13; //8 KB
    private static DataKitAPI instance = null;
    DataKitAPIExecute dataKitAPIExecute;

    /** Android context */
    Context context;

    /** Handler for runnable messages */
    Handler handler;

    /**
     * Maximum time in milliseconds to wait when transmitting high-frequency data
     *
     * <p>
     *     Default is 1000 milliseconds.
     * </p>
     */
    private static final long SYNC_TIME_HF = 1000;


    /** HashMap for temporarily storing high frequency data. */
    HashMap<Integer, HFBuffer> hmHFBuffer;

    /** Embedded class for creating high frequency buffer objects. */
    class HFBuffer {

        /** ArrayList of data collected from the data source. */
        ArrayList<DataTypeDoubleArray> data;

        /** Size of the ArrayList. */
        int size;

        /** Constructor
         *
         * <p>
         *     Creates a <code>HFBuffer</code> object containing an ArrayList of size 0.
         * </p>
         */
        HFBuffer() {
            data = new ArrayList<>();
            size = 0;
        }
    }


    /**
     * Constructor
     *
     * <p>
     *     Calls the <code>DataKitAPIExecute</code> constructor and creates a new <code>Handler</code>
     *     and a new <code>HFBuffer</code>.
     * </p>
     *
     * @param context Android context
     */
    private DataKitAPI(Context context) {
        this.context = context;
        dataKitAPIExecute = new DataKitAPIExecute(context);
        handler = new Handler();
        hmHFBuffer = new HashMap<>();
    }

    /**
     * Returns an instance of <code>DataKitAPI</code>.
     *
     * <p>
     *     If an instance does not already exist, one is created.
     * </p>
     *
     * @param context Android context
     * @return An instance of <code>DataKitAPI</code>
     */
    public static DataKitAPI getInstance(Context context) {
        if (instance == null) {
            synchronized (DataKitAPI.class) {
                if (instance == null)
                    instance = new DataKitAPI(context.getApplicationContext());
            }
        }
        return instance;
    }

    /**
     * Checks if Data Kit is connected.
     *
     * @return Whether Data Kit is connected.
     */
    public boolean isConnected() {
        return !(dataKitAPIExecute == null || !dataKitAPIExecute.isConnected());
    }

    /**
     * @param callerOnConnectionListener
     * @throws DataKitException
     */
    public synchronized void connect(OnConnectionListener callerOnConnectionListener) throws DataKitException {
        if (!isInstalled(context, Constants.PACKAGE_NAME)) {
            throw new DataKitNotFoundException(new Status(Status.ERROR_NOT_INSTALLED));
        } else if (isConnected()) callerOnConnectionListener.onConnected();
        else {
            dataKitAPIExecute.connect(callerOnConnectionListener);
            handler.postDelayed(runnableSyncHF,SYNC_TIME_HF);
        }
    }

    /**
     * Returns an ArrayList of <code>DataSourceClient</code> objects that match the given
     * <code>DataSourceBuilder</code>.
     *
     * @param dataSourceBuilder
     * @return An ArrayList of <code>DataSourceClient</code> objects.
     * @throws DataKitException Thrown if Data Kit is not connected, the builder is null, or the
     *                          clients are null.
     */
    public synchronized ArrayList<DataSourceClient> find(DataSourceBuilder dataSourceBuilder) throws DataKitException {

        if (!dataKitAPIExecute.isConnected())
            throw new DataKitNotFoundException(new Status(Status.ERROR_BOUND));

        if (dataSourceBuilder == null)
            throw new DataKitException(new Status(Status.DATA_INVALID).getStatusMessage());

        ArrayList<DataSourceClient> dataSourceClients = dataKitAPIExecute.find(dataSourceBuilder).await();

        if (dataSourceClients == null || !dataKitAPIExecute.isConnected())
            throw new DataKitNotFoundException(new Status(Status.ERROR_BOUND));

        else return dataSourceClients;
    }

    /**
     * @param dataSourceClient
     * @param dataType Type of data to insert.
     * @throws DataKitException Thrown if Data Kit is not connected and if the <code>DataSourceClient</code>
     *                          or the <code>DataType</code> are null.
     */
    public synchronized void insert(DataSourceClient dataSourceClient, DataType dataType) throws DataKitException {

        if (!dataKitAPIExecute.isConnected())
            throw new DataKitNotFoundException(new Status(Status.ERROR_BOUND));

        if (dataSourceClient == null || dataType == null)
            throw new DataKitException(new Status(Status.DATA_INVALID).getStatusMessage());

        else {
            DataType[] dataTypes=new DataType[]{dataType};
            dataKitAPIExecute.insert(dataSourceClient, dataTypes);
        }
    }
    public synchronized void setSummary(DataSourceClient dataSourceClient, DataType dataType) throws DataKitException {
        if (!dataKitAPIExecute.isConnected())
            throw new DataKitNotFoundException(new Status(Status.ERROR_BOUND));
        if (dataSourceClient == null || dataType == null)
            throw new DataKitException(new Status(Status.DATA_INVALID).getStatusMessage());
        else {
            dataKitAPIExecute.setSummary(dataSourceClient, dataType);
        }
    }

    public synchronized void insert(DataSourceClient dataSourceClient, DataType[] dataTypes) throws DataKitException {
        if (!dataKitAPIExecute.isConnected())
            throw new DataKitNotFoundException(new Status(Status.ERROR_BOUND));
        if (dataSourceClient == null || dataTypes == null)
            throw new DataKitException(new Status(Status.DATA_INVALID).getStatusMessage());
        else dataKitAPIExecute.insert(dataSourceClient, dataTypes);
    }
    public synchronized void insertHighFrequency(final DataSourceClient dataSourceClient, final DataTypeDoubleArray[] dataType) throws DataKitException {
        if (!dataKitAPIExecute.isConnected())
            throw new DataKitNotFoundException(new Status(Status.ERROR_BOUND));
        if (dataSourceClient == null || dataType == null)
            throw new DataKitException(new Status(Status.DATA_INVALID).getStatusMessage());
        else {
            for (DataTypeDoubleArray aDataType : dataType)
                addToBuffer(dataSourceClient.getDs_id(), aDataType);
        }
    }

    public synchronized void insertHighFrequency(final DataSourceClient dataSourceClient, final DataTypeDoubleArray dataType) throws DataKitException {
        if (!dataKitAPIExecute.isConnected())
            throw new DataKitNotFoundException(new Status(Status.ERROR_BOUND));
        if (dataSourceClient == null || dataType == null)
            throw new DataKitException(new Status(Status.DATA_INVALID).getStatusMessage());
        else
            addToBuffer(dataSourceClient.getDs_id(), dataType);
    }

    synchronized void addToBuffer(int ds_id, DataTypeDoubleArray dataTypeDoubleArray) {
        HFBuffer hfBuffer;
        if (hmHFBuffer.containsKey(ds_id))
            hfBuffer = hmHFBuffer.get(ds_id);
        else
            hfBuffer = new HFBuffer();

        if(hfBuffer.size+dataTypeDoubleArray.getSample().length*8>=BUFFER_SIZE){
            syncHFData(ds_id);
            hfBuffer=hmHFBuffer.get(ds_id);
        }
        hfBuffer.data.add(dataTypeDoubleArray);
        hfBuffer.size += dataTypeDoubleArray.getSample().length * 8;
        hmHFBuffer.put(ds_id, hfBuffer);
    }

    synchronized void syncHFData(int ds_id) {
        HFBuffer hfBuffer = hmHFBuffer.get(ds_id);
        if (hfBuffer.size == 0) return;
        DataTypeDoubleArray[] dataTypeDoubleArrays = new DataTypeDoubleArray[hfBuffer.data.size()];
        for (int i = 0; i < hfBuffer.data.size(); i++)
            dataTypeDoubleArrays[i] = hfBuffer.data.get(i);
        hfBuffer.data.clear();
        hfBuffer.size = 0;
        hmHFBuffer.put(ds_id, hfBuffer);
        try {
            dataKitAPIExecute.insertHighFrequency(ds_id, dataTypeDoubleArrays);
        } catch (DataKitException ignored) {
        }
    }

    synchronized void syncHFDataAll() {
        for (Map.Entry<Integer, HFBuffer> entry : hmHFBuffer.entrySet())
            syncHFData(entry.getKey());
    }

    public synchronized DataSourceClient register(final DataSourceBuilder dataSourceBuilder) throws DataKitException {
        if (!dataKitAPIExecute.isConnected())
            throw new DataKitNotFoundException(new Status(Status.ERROR_BOUND));
        if (dataSourceBuilder == null)
            throw new DataKitException(new Status(Status.DATA_INVALID).getStatusMessage());
        DataSourceClient dataSourceClient = dataKitAPIExecute.register(dataSourceBuilder).await();
        if (dataSourceClient == null || !dataKitAPIExecute.isConnected())
            throw new DataKitNotFoundException(new Status(Status.ERROR_BOUND));
        else return dataSourceClient;
    }

    public synchronized Status unregister(final DataSourceClient dataSourceClient) throws DataKitException {
        if (!dataKitAPIExecute.isConnected())
            throw new DataKitNotFoundException(new Status(Status.ERROR_BOUND));
        if (dataSourceClient == null)
            throw new DataKitException(new Status(Status.DATA_INVALID).getStatusMessage());
        Status status = dataKitAPIExecute.unregister(dataSourceClient).await();
        if (status == null || !dataKitAPIExecute.isConnected())
            throw new DataKitNotFoundException(new Status(Status.ERROR_BOUND));
        else return status;
    }

    public synchronized ArrayList<DataType> query(final DataSourceClient dataSourceClient, final int last_n_sample) throws DataKitException {
        if (!dataKitAPIExecute.isConnected())
            throw new DataKitNotFoundException(new Status(Status.ERROR_BOUND));
        if (dataSourceClient == null || last_n_sample == 0)
            throw new DataKitException(new Status(Status.DATA_INVALID).getStatusMessage());
        ArrayList<DataType> dataTypes = dataKitAPIExecute.query(dataSourceClient, last_n_sample).await();
        if (dataTypes == null || !dataKitAPIExecute.isConnected())
            throw new DataKitNotFoundException(new Status(Status.ERROR_BOUND));
        else return dataTypes;
    }

    public synchronized ArrayList<DataType> query(DataSourceClient dataSourceClient, long starttimestamp, long endtimestamp) throws DataKitException {
        if (!dataKitAPIExecute.isConnected())
            throw new DataKitNotFoundException(new Status(Status.ERROR_BOUND));
        if (dataSourceClient == null || starttimestamp > endtimestamp)
            throw new DataKitException(new Status(Status.DATA_INVALID).getStatusMessage());
        ArrayList<DataType> dataTypes = dataKitAPIExecute.query(dataSourceClient, starttimestamp, endtimestamp).await();
        if (dataTypes == null || !dataKitAPIExecute.isConnected())
            throw new DataKitNotFoundException(new Status(Status.ERROR_BOUND));
        else return dataTypes;
    }

    public synchronized ArrayList<RowObject> queryFromPrimaryKey(DataSourceClient dataSourceClient, long lastSyncedKey, int limit) throws DataKitException {
        if (!dataKitAPIExecute.isConnected())
            throw new DataKitNotFoundException(new Status(Status.ERROR_BOUND));
        if (dataSourceClient == null)
            throw new DataKitException(new Status(Status.DATA_INVALID).getStatusMessage());
        ArrayList<RowObject> rowObjects = dataKitAPIExecute.queryFromPrimaryKey(dataSourceClient, lastSyncedKey, limit).await();
        if (rowObjects == null || !dataKitAPIExecute.isConnected())
            throw new DataKitNotFoundException(new Status(Status.ERROR_BOUND));
        else return rowObjects;
    }

    public synchronized DataTypeLong querySize() throws DataKitException {
        if (!dataKitAPIExecute.isConnected())
            throw new DataKitNotFoundException(new Status(Status.ERROR_BOUND));
        DataTypeLong dataTypeLong = dataKitAPIExecute.querySize().await();
        if (dataTypeLong == null || !dataKitAPIExecute.isConnected())
            throw new DataKitNotFoundException(new Status(Status.ERROR_BOUND));
        else return dataTypeLong;
    }

    /**
     * Subscribes the given <code>DataSourceClient</code> to the given <code>OnReceiveListener</code>.
     *
     * @param dataSourceClient The <code>DataSourceClient</code> to subscribe.
     * @param onReceiveListener The message handler <code>DataSourceClient</code> is subscribing to.
     * @throws DataKitException Thrown when <code>DataKit</code> isn't connected, when
     *                          <code>DataSourceClient</code> or <code>onReceiveListener</code> is null.
     */
    public synchronized void subscribe(DataSourceClient dataSourceClient, OnReceiveListener onReceiveListener)
            throws DataKitException {
        if (!dataKitAPIExecute.isConnected())
            throw new DataKitNotFoundException(new Status(Status.ERROR_BOUND));
        if (dataSourceClient == null || onReceiveListener == null)
            throw new DataKitException(new Status(Status.DATA_INVALID).getStatusMessage());
        Status status = dataKitAPIExecute.subscribe(dataSourceClient, onReceiveListener);
        if (status == null || !dataKitAPIExecute.isConnected())
            throw new DataKitNotFoundException(new Status(Status.ERROR_BOUND));
    }

    /**
     * Unsubscribes the given <code>DataSourceClient</code> from <code>DataKit</code>.
     *
     * @param dataSourceClient The <code>DataSourceClient</code> to unsubscribe.
     * @return The new status
     * @throws DataKitException Thrown when <code>DataKit</code> is not connected or
     *                          <code>dataSourceClient</code> is null.
     */
    public synchronized Status unsubscribe(DataSourceClient dataSourceClient) throws DataKitException {
        if (!dataKitAPIExecute.isConnected())
            throw new DataKitNotFoundException(new Status(Status.ERROR_BOUND));
        if (dataSourceClient == null)
            throw new DataKitException(new Status(Status.DATA_INVALID).getStatusMessage());
        Status status = dataKitAPIExecute.unsubscribe(dataSourceClient.getDs_id()).await();
        if (status == null || !dataKitAPIExecute.isConnected())
            throw new DataKitNotFoundException(new Status(Status.ERROR_BOUND));
        else return status;
    }

    /**
     * Disconnects <code>DataKit</code> by removing any remaining messages from the Handler, syncing
     * all the data and calls <code>DataKitAPIExecute.disconnect()</code>.
     */
    public synchronized void disconnect() {
        if (dataKitAPIExecute.isConnected()) {
            handler.removeCallbacks(runnableSyncHF);
            syncHFDataAll();
            dataKitAPIExecute.disconnect();
        }
    }

    /**
     * Checks the <code>PackageManager</code> for the given package to see if it is installed.
     *
     * @param context Android context
     * @param packageName Name of the package to check for.
     * @return Whether the desired package is installed.
     */
    private boolean isInstalled(Context context, String packageName) {
        PackageManager pm = context.getPackageManager();
        try {
            pm.getPackageInfo(packageName, PackageManager.GET_ACTIVITIES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    Runnable runnableSyncHF = new Runnable() {

        /**
         * Initiates the high-frequency data transmit procedure.
         */
        @Override
        public void run() {
            syncHFDataAll();
            handler.postDelayed(runnableSyncHF, SYNC_TIME_HF);
        }
    };
}
