package org.md2k.datakitapi.source;

import android.os.Parcel;
import android.os.Parcelable;

import org.md2k.datakitapi.Constants;
import org.md2k.datakitapi.source.datasource.DataSourceBuilder;

import java.util.HashMap;

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
public class AbstractObject implements Parcelable{
    protected String type = null;
    protected String id = null;
    protected HashMap<String, String> metadata = null;
    public AbstractObject(){

    }

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

    public static final Creator<AbstractObject> CREATOR = new Creator<AbstractObject>() {
        @Override
        public AbstractObject createFromParcel(Parcel in) {
            return new AbstractObject(in);
        }

        @Override
        public AbstractObject[] newArray(int size) {
            return new AbstractObject[size];
        }
    };

    public String getType() {
        return type;
    }

    public String getId() {
        return id;
    }

    public HashMap<String, String> getMetadata() {
        return metadata;
    }

    public DataSourceBuilder toDataSourceBuilder() {
        DataSourceBuilder dataSourceBuilder = new DataSourceBuilder();
        dataSourceBuilder.setType(type).setId(id).setMetadata(metadata);
        return dataSourceBuilder;
    }

    public AbstractObject(AbstractObjectBuilder abstractObjectBuilder) {
        this.type = abstractObjectBuilder.type;
        this.id = abstractObjectBuilder.id;
        this.metadata = abstractObjectBuilder.metadata;
    }

    @Override
    public int describeContents() {
        return 0;
    }

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
}
