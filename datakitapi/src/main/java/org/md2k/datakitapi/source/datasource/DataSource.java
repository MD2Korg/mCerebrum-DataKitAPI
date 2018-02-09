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

import com.bluelinelabs.logansquare.annotation.JsonObject;

import org.md2k.datakitapi.source.AbstractObject;
import org.md2k.datakitapi.source.application.Application;
import org.md2k.datakitapi.source.platform.Platform;
import org.md2k.datakitapi.source.platformapp.PlatformApp;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Creates <code>DataSource</code> objects containing information about the platform and application
 * the data was collected from.
 */
@JsonObject(fieldDetectionPolicy = JsonObject.FieldDetectionPolicy.NONPRIVATE_FIELDS)
public class DataSource extends AbstractObject implements Parcelable {

    private Platform platform = null;
    private PlatformApp platformApp = null;
    private Application application = null;
    private boolean persistent = true;
    private ArrayList<HashMap<String, String>> dataDescriptors = null;

    /**
     * Constructor
     */
    public DataSource(){
    }

    /**
     * Constructor
     *
     * <p>
     *     This constructor is called from the <code>ApplicationBuilder</code> class using the
     *     <code>build()</code> method.
     * </p>
     *
     * @param dataSourceBuilder The builder object for this data source.
     */
    DataSource(DataSourceBuilder dataSourceBuilder) {
        super(dataSourceBuilder);
        this.platform = dataSourceBuilder.platform;
        this.platformApp = dataSourceBuilder.platformApp;
        this.application = dataSourceBuilder.application;
        this.persistent = dataSourceBuilder.persistent;
        this.dataDescriptors = dataSourceBuilder.dataDescriptors;
    }

    /**
     * Creates an <code>DataSource</code> object from a <code>Parcel</code>.
     *
     * @param in Parceled data source data
     */
    protected DataSource(Parcel in) {
        super(in);
        platform = in.readParcelable(Platform.class.getClassLoader());
        platformApp = in.readParcelable(PlatformApp.class.getClassLoader());
        application = in.readParcelable(Application.class.getClassLoader());
        persistent = in.readByte() != 0;
        int size = in.readInt();
        if (size == -1) dataDescriptors = null;
        else {
            dataDescriptors = new ArrayList<>();
            for (int i = 0; i < size; i++) {
                HashMap<String, String> a;
                int size1 = in.readInt();
                if (size1 == -1)
                    a = null;
                else {
                    a = new HashMap<>();
                    for (int j = 0; j < size1; j++) {
                        a.put(in.readString(), in.readString());
                    }
                }
                dataDescriptors.add(a);
            }
        }
    }

    /**
     * Writes the <code>DataSource</code> object to a <code>Parcel</code>.
     *
     * @param dest  The parcel to which the application should be written.
     * @param flags Additional flags about how the object should be written.
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeParcelable(platform, flags);
        dest.writeParcelable(platformApp, flags);
        dest.writeParcelable(application, flags);
        dest.writeByte((byte) (persistent ? 1 : 0));
        if (dataDescriptors == null)
            dest.writeInt(-1);
        else {
            int size = dataDescriptors.size();
            dest.writeInt(size);
            for (int i = 0; i < size; i++) {
                if (dataDescriptors.get(i) == null) {
                    dest.writeInt(-1);
                } else {
                    dest.writeInt(dataDescriptors.get(i).size());
                    for (HashMap.Entry<String, String> entry : dataDescriptors.get(i).entrySet()) {
                        dest.writeString(entry.getKey());
                        dest.writeString(entry.getValue());
                    }
                }
            }
        }
    }

    /**
     * @return Always returns 0.
     */
    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * @return The platform the data source uses.
     */
    public Platform getPlatform() {
        return platform;
    }

    /**
     * @return The platform application the data source uses.
     */
    public PlatformApp getPlatformApp() {
        return platformApp;
    }

    /**
     * @return The application the data source uses.
     */
    public Application getApplication() {
        return application;
    }

    /**
     * @return Whether the data source is persistent or not.
     */
    public boolean isPersistent() {
        return persistent;
    }

    /**
     * @return An arrayList of hashMaps containing descriptors of the data.
     */
    public ArrayList<HashMap<String, String>> getDataDescriptors() {
        return dataDescriptors;
    }

    /**
     * Sets the fields of a <code>DataSourceBuilder</code> object based on the caller.
     *
     * @return A completed <code>DataSourceBuilder</code> object.
     */
    public DataSourceBuilder toDataSourceBuilder() {
        DataSourceBuilder dataSourceBuilder = super.toDataSourceBuilder();
        dataSourceBuilder = dataSourceBuilder
                .setPlatform(platform)
                .setPlatformApp(platformApp)
                .setApplication(application)
                .setPersistent(persistent)
                .setDataDescriptors(dataDescriptors);
        return dataSourceBuilder;
    }

    /**
     * <code>Creator</code> for <code>DataSource</code> objects.
     */
    public static final Creator<DataSource> CREATOR = new Creator<DataSource>() {

        /**
         * Creates a new <code>DataSource</code> object from a <code>Parcel</code>.
         *
         * @param in The parcel holding the application.
         * @return The reconstructed <code>DataSource</code> object.
         */
        @Override
        public DataSource createFromParcel(Parcel in) {
            return new DataSource(in);
        }

        /**
         * Creates a new array of the specified size for <code>DataSource</code> objects.
         *
         * @param size The size of the new <code>DataSource</code> array.
         * @return The <code>DataSource</code> array.
         */
        @Override
        public DataSource[] newArray(int size) {
            return new DataSource[size];
        }
    };
}

