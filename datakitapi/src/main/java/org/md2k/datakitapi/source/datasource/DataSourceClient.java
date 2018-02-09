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

package org.md2k.datakitapi.source.datasource;

import android.os.Parcel;
import android.os.Parcelable;

import org.md2k.datakitapi.status.Status;

/**
 * Container object class for data sources.
 */
public class DataSourceClient implements Parcelable {

    /** Data source identifier. */
    private int ds_id;

    /** Data source for this client. */
    DataSource dataSource;

    /** Status of the <code>DataKitAPI</code> as indicated by the <code>Status</code> class. */
    private Status status;

    /**
     * Constructor
     *
     * @param ds_id Data source identifier.
     * @param dataSource Data source for this client.
     * @param status Status of the <code>DataKitAPI</code> as indicated by the <code>Status</code> class.
     */
    public DataSourceClient(int ds_id, DataSource dataSource, Status status) {
        this.ds_id = ds_id;
        this.dataSource = dataSource;
        this.status = status;
    }

    /**
     * Constructs a <code>DataSourceClient</code> object from a <code>Parcel</code>.
     *
     * @param in Parceled <code>DataSourceClient</code> object.
     */
    protected DataSourceClient(Parcel in) {
        ds_id = in.readInt();
        dataSource = in.readParcelable(DataSource.class.getClassLoader());
        status = in.readParcelable(Status.class.getClassLoader());
    }

    /**
     * Writes the <code>DataSourceClient</code> to a <code>parcel</code>.
     *
     * @param dest The parcel to which the application should be written.
     * @param flags Additional flags about how the object should be written.
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(ds_id);
        dest.writeParcelable(dataSource, flags);
        dest.writeParcelable(status, flags);
    }

    /**
     * @return Always returns 0.
     */
    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * @return The data source identifier.
     */
    public int getDs_id() {
        return ds_id;
    }

    /**
     * @return Status of the <code>DataKitAPI</code> as indicated by the <code>Status</code> class.
     */
    public Status getStatus() {
        return status;
    }

    /**
     * @return The data source of this client
     */
    public DataSource getDataSource() {
        return dataSource;
    }

    /**
     * <code>Creator</code> for <code>DataSourceClient</code> objects.
     */
    public static final Creator<DataSourceClient> CREATOR = new Creator<DataSourceClient>() {

        /**
         * Creates a new <code>DataSourceClient</code> object from a <code>Parcel</code>.
         *
         * @param in The parcel holding the application.
         * @return The reconstructed <code>DataSourceClient</code> object.
         */
        @Override
        public DataSourceClient createFromParcel(Parcel in) {
            return new DataSourceClient(in);
        }

        /**
         * Creates a new array of the specified size for <code>DataSourceClient</code> objects.
         *
         * @param size The size of the new <code>DataSourceClient</code> array.
         * @return The <code>DataSourceClient</code> array.
         */
        @Override
        public DataSourceClient[] newArray(int size) {
            return new DataSourceClient[size];
        }
    };
}
