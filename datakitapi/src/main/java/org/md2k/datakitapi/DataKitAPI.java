package org.md2k.datakitapi;

import android.content.Context;

import org.md2k.datakitapi.datatype.DataType;
import org.md2k.datakitapi.datatype.DataTypeDoubleArray;
import org.md2k.datakitapi.datatype.DataTypeLong;
import org.md2k.datakitapi.datatype.RowObject;
import org.md2k.datakitapi.exception.DataKitException;
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
        if(dataKitAPIExecute==null) return false;
        return dataKitAPIExecute.isBound;
    }

    public void connect(OnConnectionListener callerOnConnectionListener) throws DataKitException {
        dataKitAPIExecute.connect(callerOnConnectionListener);
    }

    public ArrayList<DataSourceClient> find(DataSourceBuilder dataSourceBuilder) throws DataKitException {
        return dataKitAPIExecute.find(dataSourceBuilder).await();
    }

    public void insert(DataSourceClient dataSourceClient, DataType data) throws DataKitException {
        dataKitAPIExecute.insert(dataSourceClient, data);
    }

    public void insertHighFrequency(DataSourceClient dataSourceClient, DataTypeDoubleArray data) throws DataKitException {
        dataKitAPIExecute.insertHighFrequency(dataSourceClient, data);
    }

    public DataSourceClient register(DataSourceBuilder dataSourceBuilder) throws DataKitException {
        return dataKitAPIExecute.register(dataSourceBuilder).await();
    }

    public Status unregister(DataSourceClient dataSourceClient) throws DataKitException {
        return dataKitAPIExecute.unregister(dataSourceClient).await();
    }

    public ArrayList<DataType> query(DataSourceClient dataSourceClient, int last_n_sample) throws DataKitException {
        return dataKitAPIExecute.query(dataSourceClient, last_n_sample).await();
    }

    public ArrayList<DataType> queryHFlastN(DataSourceClient dataSourceClient, int last_n_sample) throws DataKitException {
        return dataKitAPIExecute.queryHFlastN(dataSourceClient, last_n_sample).await();
    }

    public ArrayList<DataType> query(DataSourceClient dataSourceClient, long starttimestamp, long endtimestamp) throws DataKitException {
        return dataKitAPIExecute.query(dataSourceClient, starttimestamp, endtimestamp).await();
    }

    public ArrayList<RowObject> queryFromPrimaryKey(DataSourceClient dataSourceClient, long lastSyncedKey, int limit) throws DataKitException {
        return dataKitAPIExecute.queryFromPrimaryKey(dataSourceClient, lastSyncedKey, limit).await();
    }

    public ArrayList<RowObject> queryHFFromPrimaryKey(DataSourceClient dataSourceClient, long lastSyncedKey, int limit) throws DataKitException {
        return dataKitAPIExecute.queryHFFromPrimaryKey(dataSourceClient, lastSyncedKey, limit).await();
    }

    public DataTypeLong querySize() throws DataKitException {
        return dataKitAPIExecute.querySize().await();
    }

    public void subscribe(DataSourceClient dataSourceClient, OnReceiveListener onReceiveListener) throws DataKitException {
        dataKitAPIExecute.subscribe(dataSourceClient, onReceiveListener);
    }

    public Status unsubscribe(DataSourceClient dataSourceClient) throws DataKitException {
        return dataKitAPIExecute.unsubscribe(dataSourceClient).await();
    }

    public void disconnect() {
        dataKitAPIExecute.disconnect();
    }

}
