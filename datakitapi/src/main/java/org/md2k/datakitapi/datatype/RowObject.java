package org.md2k.datakitapi.datatype;

/*
 * Copyright (c) 2016, The University of Memphis, MD2K Center
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

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.JsonArray;

public class RowObject implements Parcelable{
    public static final Creator<RowObject> CREATOR = new Creator<RowObject>() {
        @Override
        public RowObject createFromParcel(Parcel in) {
            return new RowObject(in);
        }

        @Override
        public RowObject[] newArray(int size) {
            return new RowObject[size];
        }
    };
    public DataType data;
    public long rowKey;

    public RowObject(long aLong, DataType dt) {
        rowKey = aLong;
        this.data = copyDataTypeObject(dt);
    }

    public RowObject(RowObject rowObject) {
        rowKey = rowObject.rowKey;
        this.data = copyDataTypeObject(rowObject.data);
    }

    protected RowObject(Parcel in) {
        data = in.readParcelable(DataType.class.getClassLoader());
        rowKey = in.readLong();
    }

    private DataType copyDataTypeObject(DataType dt) {
        if (dt instanceof DataTypeBoolean) {
            return new DataTypeBoolean(dt.getDateTime(), ((DataTypeBoolean) dt).getSample());
        }
        if (dt instanceof DataTypeBooleanArray) {
            return new DataTypeBooleanArray(dt.getDateTime(), ((DataTypeBooleanArray) dt).getSample());
        }
        if (dt instanceof DataTypeByte) {
            return new DataTypeByte(dt.getDateTime(), ((DataTypeByte) dt).getSample());
        }
        if (dt instanceof DataTypeByteArray) {
            return new DataTypeByteArray(dt.getDateTime(), ((DataTypeByteArray) dt).getSample());
        }
        if (dt instanceof DataTypeDouble) {
            return new DataTypeDouble(dt.getDateTime(), ((DataTypeDouble) dt).getSample());
        }
        if (dt instanceof DataTypeDoubleArray) {
            return new DataTypeDoubleArray(dt.getDateTime(), ((DataTypeDoubleArray) dt).getSample());
        }
        if (dt instanceof DataTypeFloat) {
            return new DataTypeFloat(dt.getDateTime(), ((DataTypeFloat) dt).getSample());
        }
        if (dt instanceof DataTypeFloatArray) {
            return new DataTypeFloatArray(dt.getDateTime(), ((DataTypeFloatArray) dt).getSample());
        }
        if (dt instanceof DataTypeInt) {
            return new DataTypeInt(dt.getDateTime(), ((DataTypeInt) dt).getSample());
        }
        if (dt instanceof DataTypeIntArray) {
            return new DataTypeIntArray(dt.getDateTime(), ((DataTypeIntArray) dt).getSample());
        }
        if (dt instanceof DataTypeJSONObject) {
            return new DataTypeJSONObject(dt.getDateTime(), ((DataTypeJSONObject) dt).getSample());
        }
        if (dt instanceof DataTypeJSONObjectArray) {
            return new DataTypeJSONObjectArray(dt.getDateTime(), ((DataTypeJSONObjectArray) dt).getSample());
        }
        if (dt instanceof DataTypeLong) {
            return new DataTypeLong(dt.getDateTime(), ((DataTypeLong) dt).getSample());
        }
        if (dt instanceof DataTypeLongArray) {
            return new DataTypeLongArray(dt.getDateTime(), ((DataTypeLongArray) dt).getSample());
        }
        if (dt instanceof DataTypeString) {
            return new DataTypeString(dt.getDateTime(), ((DataTypeString) dt).getSample());
        }
        if (dt instanceof DataTypeStringArray) {
            return new DataTypeStringArray(dt.getDateTime(), ((DataTypeStringArray) dt).getSample());
        }
        return dt;
    }

    public DataType toArrayForm() {
        if (this.data instanceof DataTypeBoolean) {
            return new DataTypeBooleanArray(this.data.getDateTime(), new boolean[]{((DataTypeBoolean) this.data).getSample()});
        }
        if (this.data instanceof DataTypeByte) {
            return new DataTypeByteArray(this.data.getDateTime(), new byte[]{((DataTypeByte) this.data).getSample()});
        }
        if (this.data instanceof DataTypeDouble) {
            return new DataTypeDoubleArray(this.data.getDateTime(), new double[]{((DataTypeDouble) this.data).getSample()});
        }
        if (this.data instanceof DataTypeFloat) {
            return new DataTypeFloatArray(this.data.getDateTime(), new float[]{((DataTypeFloat) this.data).getSample()});
        }
        if (this.data instanceof DataTypeInt) {
            return new DataTypeIntArray(this.data.getDateTime(), new int[]{((DataTypeInt) this.data).getSample()});
        }
        if (this.data instanceof DataTypeLong) {
            return new DataTypeLongArray(this.data.getDateTime(), new long[]{((DataTypeLong) this.data).getSample()});
        }
        if (this.data instanceof DataTypeString) {
            return new DataTypeStringArray(this.data.getDateTime(), new String[]{((DataTypeString) this.data).getSample()});
        }
        if (this.data instanceof DataTypeJSONObject) {

            JsonArray jo = new JsonArray();
            jo.add(((DataTypeJSONObject) this.data).getSample());
            return new DataTypeJSONObjectArray(this.data.getDateTime(), jo);
        }
        return this.data;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(data, flags);
        dest.writeLong(rowKey);
    }
}
