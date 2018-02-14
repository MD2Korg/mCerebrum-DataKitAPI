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

package org.md2k.datakitapi.source;

import android.os.Parcel;
import android.os.Parcelable;

import org.md2k.datakitapi.Constants;
import org.md2k.datakitapi.source.datasource.DataSourceBuilder;

import java.util.HashMap;

/**
 * Superclass for the <code>DataSource</code>, <code>PlatformApp</code>, <code>Platform</code>, and
 * <code>Application</code> classes.
 */
public class AbstractObject implements Parcelable{

    /** Type of the <code>AbstractObject</code>. */
    protected String type = null;

    /** Id of the <code>AbstractObject</code>. */
    protected String id = null;

    /** HashMap of the object's metadata. */
    protected HashMap<String, String> metadata = null;


    /**
     * Constructor
     */
    public AbstractObject(){}

    /**
     * Constructor
     *
     * @param abstractObjectBuilder Builder for the desired <code>AbstractObject</code>.
     */
    public AbstractObject(AbstractObjectBuilder abstractObjectBuilder) {
        this.type = abstractObjectBuilder.type;
        this.id = abstractObjectBuilder.id;
        this.metadata = abstractObjectBuilder.metadata;
    }

    /**
     * Constructs a <code>AbstractObject</code> object from a <code>Parcel</code>.
     *
     * @param in Parceled <code>AbstractObject</code> object.
     */
    protected AbstractObject(Parcel in) {
        type = in.readString();
        id = in.readString();
        int size = in.readInt();
        if (size == -1) metadata = null;
        else {
            metadata = new HashMap<>();
            for (int i = 0; i < size; i++) {
                metadata.put(in.readString(), in.readString());
            }
        }
    }

    /**
     * @return The type of the <code>AbstractObject</code>.
     */
    public String getType() {
        return type;
    }


    /**
     * @return The id of the <code>AbstractObject</code>.
     */
    public String getId() {
        return id;
    }


    /**
     * @return The HashMap of metadata.
     */
    public HashMap<String, String> getMetadata() {
        return metadata;
    }

    /**
     * Creates a new <code>DataSourceBuilder</code> object.
     *
     * <p>
     *     Also sets the type, id, and metadata of the new <code>DataSourceBuilder</code>.
     * </p>
     *
     * @return The created <code>DataSourceBuilder</code> object.
     */
    public DataSourceBuilder toDataSourceBuilder() {
        DataSourceBuilder dataSourceBuilder = new DataSourceBuilder();
        dataSourceBuilder.setType(type).setId(id).setMetadata(metadata);
        return dataSourceBuilder;
    }

    /**
     * Writes the <code>AbstractObject</code> to a parcel.
     *
     * @param dest The parcel to which the application should be written.
     * @param flags Additional flags about how the object should be written.
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(type);
        dest.writeString(id);
        if (metadata == null)
            dest.writeInt(-1);
        else {
            dest.writeInt(metadata.size());
            for (HashMap.Entry<String, String> entry : metadata.entrySet()) {
                dest.writeString(entry.getKey());
                dest.writeString(entry.getValue());
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
     * <code>Creator</code> for <code>AbstractObject</code> objects.
     */
    public static final Creator<AbstractObject> CREATOR = new Creator<AbstractObject>() {

        /**
         * Creates a new <code>AbstractObject</code> object from a <code>Parcel</code>.
         *
         * @param in The parcel holding the data type.
         * @return The constructed <code>AbstractObject</code> object
         */
        @Override
        public AbstractObject createFromParcel(Parcel in) {
            return new AbstractObject(in);
        }

        /**
         * Creates a new array of the specified size for <code>AbstractObject</code> objects.
         *
         * @param size The size of the new <code>AbstractObject</code> array.
         * @return The <code>AbstractObject</code> array.
         */
        @Override
        public AbstractObject[] newArray(int size) {
            return new AbstractObject[size];
        }
    };
}
