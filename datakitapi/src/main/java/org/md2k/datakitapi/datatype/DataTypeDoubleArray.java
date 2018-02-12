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

import java.nio.ByteBuffer;

/**
 * This class creates <code>DataType</code> objects for samples that have double data types in an array.
 */
public class DataTypeDoubleArray extends  DataType implements Parcelable{

    /**
     * The data point collected from the data source.
     */
    double[] sample;

    /**
     * Constructor
     */
    public DataTypeDoubleArray(){}

    /**
     * Constructor
     *
     * @param timestamp The timestamp for when the data was collected.
     * @param sample The data point sampled from the data source.
     */
    public DataTypeDoubleArray(long timestamp, double[] sample) {
        super(timestamp);
        this.sample = sample;
    }

    /**
     * Constructor
     *
     * @param dateTime The timestamp for when the data was collected.
     * @param sample The data point collected from the data source.
     */
    public DataTypeDoubleArray(long dateTime, double sample) {
        this(dateTime, new double[1]);
        this.sample[0] = sample;
    }

    /**
     * Constructs a <code>DataTypeDoubleArray</code> object from a <code>Parcel</code>.
     *
     * @param in Parceled <code>DataTypeDoubleArray</code> object.
     */
    protected DataTypeDoubleArray(Parcel in) {
        super(in);
        sample = in.createDoubleArray();

    }

    /**
     * Writes the <code>DataTypeDoubleArray</code> to a parcel.
     *
     * @param dest  The parcel to which the application should be written.
     * @param flags Additional flags about how the object should be written.
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeDoubleArray(sample);
    }

    /**
     * @return Always returns 0.
     */
    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * @return The the value of the sample.
     */
    public double[] getSample(){
        return sample;
    }


    /**
     * Constructs a new byte array from the calling <code>DataTypeDoubleArray</code>.
     *
     * @return The resulting byte array.
     */
    public byte[] toRawBytes() {
        byte[] data = new byte[sample.length * 8];

        for (int i = 0; i < sample.length; i++) {
            ByteBuffer.wrap(data, i * 8, 8).putDouble(sample[i]);
        }
        return data;
    }

    /**
     * Constructs a <code>DataTypeDoubleArray</code> from a byte array.
     *
     * @param timestamp The timestamp for when the data was collected.
     * @param data The byte array to construct from.
     * @return The new <code>DataTypeDoubleArray</code>.
     */
    static public DataTypeDoubleArray fromRawBytes(long timestamp, byte[] data) {

        double[] sample = new double[data.length / 8];
        for (int i = 0; i < sample.length; i++) {
            sample[i] = ByteBuffer.wrap(data, i * 8, 8).getDouble();
        }
        return new DataTypeDoubleArray(timestamp, sample);
    }

    /**
     * <code>Creator</code> for <code>DataTypeDouble</code> objects.
     */
    public static final Creator<DataTypeDoubleArray> CREATOR = new Creator<DataTypeDoubleArray>() {

        /**
         * Creates a new <code>DataTypeDouble</code> object from a <code>Parcel</code>.
         *
         * @param in The parcel holding the data type.
         * @return The constructed <code>DataTypeDouble</code> object
         */
        @Override
        public DataTypeDoubleArray createFromParcel(Parcel in) {
            return new DataTypeDoubleArray(in);
        }

        /**
         * Creates a new array of the specified size for <code>DataTypeDouble</code> objects.
         *
         * @param size The size of the new <code>DataTypeDouble</code> array.
         * @return The <code>DataTypeDouble</code> array.
         */
        @Override
        public DataTypeDoubleArray[] newArray(int size) {
            return new DataTypeDoubleArray[size];
        }
    };
}
