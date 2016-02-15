package org.md2k.datakitapi.source.datasource;

import android.os.Parcel;
import android.os.Parcelable;

import org.md2k.datakitapi.status.Status;


/*
 * Copyright (c) 2015, The University of Memphis, MD2K Center
 * - Syed Monowar Hossain <monowar.hossain@gmail.com>
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
public class DataSourceClient implements Parcelable {
    private int ds_id;
    DataSource dataSource;
    private Status status;

    public DataSourceClient(int ds_id, DataSource dataSource, Status status) {
        this.ds_id = ds_id;
        this.dataSource = dataSource;
        this.status = status;
    }

    protected DataSourceClient(Parcel in) {
        ds_id = in.readInt();
        dataSource = in.readParcelable(DataSource.class.getClassLoader());
        status = in.readParcelable(Status.class.getClassLoader());
    }

    public static final Creator<DataSourceClient> CREATOR = new Creator<DataSourceClient>() {
        @Override
        public DataSourceClient createFromParcel(Parcel in) {
            return new DataSourceClient(in);
        }

        @Override
        public DataSourceClient[] newArray(int size) {
            return new DataSourceClient[size];
        }
    };

    public int getDs_id() {
        return ds_id;
    }

    public Status getStatus() {
        return status;
    }

    public DataSource getDataSource() {
        return dataSource;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(ds_id);
        dest.writeParcelable(dataSource, flags);
        dest.writeParcelable(status, flags);
    }
}
