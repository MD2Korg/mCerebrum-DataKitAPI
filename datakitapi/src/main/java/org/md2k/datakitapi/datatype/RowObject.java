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

package org.md2k.datakitapi.datatype;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import org.json.JSONObject;


/**
 * Object representing a row from the <code>DataKit</code>database.
 */
public class RowObject implements Parcelable{

    /**
     * The <code>DataType</code> object for this <code>RowObject</code>.
     */
    public DataType data;

    /**
     * The key used to identify this <code>RowObject</code>.
     */
    public long rowKey;

    /**
     * Constructor
     */
    public RowObject(){}

    /**
     * Constructor
     *
     * @param aLong The <code>rowKey</code> used to identify the <code>RowObject</code>
     * @param dt The <code>DataType</code> object for this <code>RowObject</code>
     */
    public RowObject(long aLong, DataType dt) {
        rowKey = aLong;
        data = dt;
    }

    /**
     * Constructs a <code>RowObject</code> object from a <code>Parcel</code>.
     *
     * @param in Parceled <code>RowObject</code> object.
     */
    protected RowObject(Parcel in) {
        data = in.readParcelable(DataType.class.getClassLoader());
        rowKey = in.readLong();
    }

    /**
     * Writes the <code>RowObject</code> and it's key to a parcel.
     *
     * @param dest  The parcel to which the application should be written.
     * @param flags Additional flags about how the object should be written.
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(data, flags);
        dest.writeLong(rowKey);
    }

    /**
     * @return Always returns 0.
     */
    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * Creates <code>DataType</code> arrays based on the data type of the input.
     *
     * <p>
     *     For example: <code>DataTypeBoolean</code> objects return a <code>DataTypeBooleanArray</code> object.
     * </p>
     *
     * @return The resulting <code>DataType</code> object.
     */
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

    /**
     * Returns the calling <code>RowObject</code> as a string
     *
     * @return The resulting string.
     */
    public String csvString() {
        String result = String.valueOf(this.data.getDateTime());
        result += "," + String.valueOf(this.data.offset);
        
        if (this.data instanceof DataTypeBoolean) {
            result += "," + String.valueOf(((DataTypeBoolean) this.data).getSample());
        }
        if (this.data instanceof DataTypeBooleanArray) {
            DataTypeBooleanArray d = (DataTypeBooleanArray) this.data;
            for (boolean i : d.getSample()) {
                result += "," + String.valueOf(i);    
            }
        }
        if (this.data instanceof DataTypeByte) {
            result += "," + String.format("%02X", ((DataTypeByte) this.data).getSample());
        }
        if (this.data instanceof DataTypeByteArray) {
            DataTypeByteArray d = (DataTypeByteArray) this.data;
            for (byte i : d.getSample()) {
                result += "," + String.format("%02X", i);
            }
        }
        if (this.data instanceof DataTypeDouble) {
            result += "," + String.valueOf(((DataTypeDouble) this.data).getSample());
        }
        if (this.data instanceof DataTypeDoubleArray) {
            DataTypeDoubleArray d = (DataTypeDoubleArray) this.data;
            for (double i : d.getSample()) {
                result += "," + String.valueOf(i);
            }
        }
        if (this.data instanceof DataTypeFloat) {
            result += "," + String.valueOf(((DataTypeFloat) this.data).getSample());
        }
        if (this.data instanceof DataTypeFloatArray) {
            DataTypeFloatArray d = (DataTypeFloatArray) this.data;
            for (float i : d.getSample()) {
                result += "," + String.valueOf(i);
            }
        }
        if (this.data instanceof DataTypeInt) {
            result += "," + String.valueOf(((DataTypeInt) this.data).getSample());
        }
        if (this.data instanceof DataTypeIntArray) {
            DataTypeIntArray d = (DataTypeIntArray) this.data;
            for (int i : d.getSample()) {
                result += "," + String.valueOf(i);
            }
        }
        if (this.data instanceof DataTypeLong) {
            result += "," + String.valueOf(((DataTypeLong) this.data).getSample());
        }
        if (this.data instanceof DataTypeLongArray) {
            DataTypeLongArray d = (DataTypeLongArray) this.data;
            for (long i : d.getSample()) {
                result += "," + String.valueOf(i);
            }
        }
        if (this.data instanceof DataTypeString) {
            result += "," + ((DataTypeString) this.data).getSample();
        }
        if (this.data instanceof DataTypeStringArray) {
            DataTypeStringArray d = (DataTypeStringArray) this.data;
            for (String i : d.getSample()) {
                result += "," + i;
            }
        }
        if (this.data instanceof DataTypeJSONObject) {
            result += "," + ((DataTypeJSONObject) this.data).getSample().toString();
        }
        if (this.data instanceof DataTypeJSONObjectArray) {
            DataTypeJSONObjectArray d = (DataTypeJSONObjectArray) this.data;
            for (JsonElement i : d.getSample()) {
                result += "," + i.toString();
            }
        }
        return result;
    }

    /**
     * <code>Creator</code> for <code>RowObject</code> objects.
     */
    public static final Creator<RowObject> CREATOR = new Creator<RowObject>() {

        /**
         * Creates a new <code>RowObject</code> object from a <code>Parcel</code>.
         *
         * @param in The parcel holding the data type.
         * @return The constructed <code>RowObject</code> object
         */
        @Override
        public RowObject createFromParcel(Parcel in) {
            return new RowObject(in);
        }

        /**
         * Creates a new array of the specified size for <code>RowObject</code> objects.
         *
         * @param size The size of the new <code>RowObject</code> array.
         * @return The <code>RowObject</code> array.
         */
        @Override
        public RowObject[] newArray(int size) {
            return new RowObject[size];
        }
    };
}
