package org.md2k.datakitapi;

import android.content.Context;
import android.content.pm.PackageManager;

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

/*
 * Copyright (c) 2016, The University of Memphis, MD2K Center
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
public class DataKitAPI {
    private static final String TAG = DataKitAPI.class.getSimpleName();
    private static DataKitAPI instance = null;
    DataKitAPIExecute dataKitAPIExecute;
    Context context;

    private DataKitAPI(Context context) {
        this.context = context;
        dataKitAPIExecute = new DataKitAPIExecute(context);
    }

    public static DataKitAPI getInstance(Context context) {
        if (instance == null) {
            instance = new DataKitAPI(context.getApplicationContext());
        }
        return instance;
    }

    public boolean isConnected() {
        return !(dataKitAPIExecute == null || !dataKitAPIExecute.isConnected());
    }

    public void connect(OnConnectionListener callerOnConnectionListener) throws DataKitException {
        if (!isInstalled(context, Constants.PACKAGE_NAME)) {
            throw new DataKitNotFoundException(new Status(Status.ERROR_NOT_INSTALLED));
        }else if(isConnected()) callerOnConnectionListener.onConnected();
        else
            dataKitAPIExecute.connect(callerOnConnectionListener);
    }

    public ArrayList<DataSourceClient> find(DataSourceBuilder dataSourceBuilder) throws DataKitException {
        if(!dataKitAPIExecute.isConnected() || dataSourceBuilder == null)
            throw new DataKitNotFoundException(new Status(Status.ERROR_BOUND));
        ArrayList<DataSourceClient> dataSourceClients = dataKitAPIExecute.find(dataSourceBuilder).await();
        if(dataSourceClients==null)
            throw new DataKitNotFoundException(new Status(Status.ERROR_BOUND));
        else return dataSourceClients;
    }

    public void insert(DataSourceClient dataSourceClient, DataType dataType) throws DataKitException {
        if(!dataKitAPIExecute.isConnected() || dataSourceClient == null || dataType == null)
            throw new DataKitNotFoundException(new Status(Status.ERROR_BOUND));
        else dataKitAPIExecute.insert(dataSourceClient, dataType);
    }

    public void insertHighFrequency(final DataSourceClient dataSourceClient, final DataTypeDoubleArray dataType) throws DataKitException {
        if(!dataKitAPIExecute.isConnected() || dataSourceClient == null || dataType == null)
            throw new DataKitNotFoundException(new Status(Status.ERROR_BOUND));
        else dataKitAPIExecute.insertHighFrequency(dataSourceClient, dataType);
    }

    public DataSourceClient register(final DataSourceBuilder dataSourceBuilder) throws DataKitException {
        if(!dataKitAPIExecute.isConnected() || dataSourceBuilder==null)
            throw new DataKitNotFoundException(new Status(Status.ERROR_BOUND));
        DataSourceClient dataSourceClient = dataKitAPIExecute.register(dataSourceBuilder).await();
        if(dataSourceClient==null)
            throw new DataKitNotFoundException(new Status(Status.ERROR_BOUND));
        else return dataSourceClient;
    }

    public Status unregister(final DataSourceClient dataSourceClient) throws DataKitException {
        if(!dataKitAPIExecute.isConnected() || dataSourceClient == null)
            throw new DataKitNotFoundException(new Status(Status.ERROR_BOUND));
        Status status = dataKitAPIExecute.unregister(dataSourceClient).await();
        if(status==null)
            throw new DataKitNotFoundException(new Status(Status.ERROR_BOUND));
        else return status;
    }

    public ArrayList<DataType> query(final DataSourceClient dataSourceClient, final int last_n_sample) throws DataKitException {
        if(!dataKitAPIExecute.isConnected() || dataSourceClient == null || last_n_sample == 0)
            throw new DataKitNotFoundException(new Status(Status.ERROR_BOUND));
        ArrayList<DataType> dataTypes =  dataKitAPIExecute.query(dataSourceClient, last_n_sample).await();
        if(dataTypes==null)
            throw new DataKitNotFoundException(new Status(Status.ERROR_BOUND));
        else return dataTypes;
    }

    public ArrayList<DataType> queryHFlastN(DataSourceClient dataSourceClient, int last_n_sample) throws DataKitException {
        if(!dataKitAPIExecute.isConnected()  || dataSourceClient == null || last_n_sample == 0)
            throw new DataKitNotFoundException(new Status(Status.ERROR_BOUND));
        ArrayList<DataType> dataTypes =  dataKitAPIExecute.queryHFlastN(dataSourceClient, last_n_sample).await();
        if(dataTypes==null)
            throw new DataKitNotFoundException(new Status(Status.ERROR_BOUND));
        else return dataTypes;
    }

    public ArrayList<DataType> query(DataSourceClient dataSourceClient, long starttimestamp, long endtimestamp) throws DataKitException {
        if(!dataKitAPIExecute.isConnected()  || dataSourceClient == null || starttimestamp > endtimestamp)
            throw new DataKitNotFoundException(new Status(Status.ERROR_BOUND));
        ArrayList<DataType> dataTypes = dataKitAPIExecute.query(dataSourceClient, starttimestamp, endtimestamp).await();
        if(dataTypes==null)
            throw new DataKitNotFoundException(new Status(Status.ERROR_BOUND));
        else return dataTypes;
    }

    public ArrayList<RowObject> queryFromPrimaryKey(DataSourceClient dataSourceClient, long lastSyncedKey, int limit) throws DataKitException {
        if(!dataKitAPIExecute.isConnected() || dataSourceClient == null)
            throw new DataKitNotFoundException(new Status(Status.ERROR_BOUND));
        ArrayList<RowObject> rowObjects =  dataKitAPIExecute.queryFromPrimaryKey(dataSourceClient, lastSyncedKey, limit).await();
        if(rowObjects==null)
            throw new DataKitNotFoundException(new Status(Status.ERROR_BOUND));
        else return rowObjects;
    }

    public ArrayList<RowObject> queryHFFromPrimaryKey(DataSourceClient dataSourceClient, long lastSyncedKey, int limit) throws DataKitException {
        if(!dataKitAPIExecute.isConnected() || dataSourceClient == null)
            throw new DataKitNotFoundException(new Status(Status.ERROR_BOUND));
        ArrayList<RowObject> rowObjects = dataKitAPIExecute.queryHFFromPrimaryKey(dataSourceClient, lastSyncedKey, limit).await();
        if(rowObjects==null)
            throw new DataKitNotFoundException(new Status(Status.ERROR_BOUND));
        else return rowObjects;
    }

    public DataTypeLong querySize() throws DataKitException {
        if(!dataKitAPIExecute.isConnected())
            throw new DataKitNotFoundException(new Status(Status.ERROR_BOUND));
        DataTypeLong dataTypeLong= dataKitAPIExecute.querySize().await();
        if(dataTypeLong==null)
            throw new DataKitNotFoundException(new Status(Status.ERROR_BOUND));
        else return dataTypeLong;
    }

    public void subscribe(DataSourceClient dataSourceClient, OnReceiveListener onReceiveListener) throws DataKitException {
        if(!dataKitAPIExecute.isConnected()  || dataSourceClient == null || onReceiveListener == null)
            throw new DataKitNotFoundException(new Status(Status.ERROR_BOUND));
        Status status = dataKitAPIExecute.subscribe(dataSourceClient, onReceiveListener);
        if(status==null)
            throw new DataKitNotFoundException(new Status(Status.ERROR_BOUND));
    }

    public Status unsubscribe(DataSourceClient dataSourceClient) throws DataKitException {
        if(!dataKitAPIExecute.isConnected() || dataSourceClient==null)
            throw new DataKitNotFoundException(new Status(Status.ERROR_BOUND));
        Status status = dataKitAPIExecute.unsubscribe(dataSourceClient).await();
        if(status==null)
            throw new DataKitNotFoundException(new Status(Status.ERROR_BOUND));
        else return status;
    }

    public void disconnect() {
        dataKitAPIExecute.disconnect();
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

}
