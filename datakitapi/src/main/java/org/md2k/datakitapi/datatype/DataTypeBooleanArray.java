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

/**
 * This class creates <code>DataType</code> objects for samples that have boolean data types in an array.
 */
public class DataTypeBooleanArray extends DataType implements Parcelable {

    /**
     * The data point collected from the data source.
     */
    boolean[] sample;

    /**
     * Constructor
     */
    public DataTypeBooleanArray(){}

    /**
     * Constructor
     *
     * @param timestamp The timestamp for when the data was collected.
     * @param sample The data point sampled from the data source.
     */
    public DataTypeBooleanArray(long timestamp, boolean[] sample) {
        super(timestamp);
        this.sample = sample;
    }

    /**
     * Constructs a <code>DataTypeBooleanArray</code> object from a <code>Parcel</code>.
     *
     * @param in Parceled <code>DataTypeBooleanArray</code> object.
     */
    protected DataTypeBooleanArray(Parcel in) {
        super(in);
        sample = in.createBooleanArray();
    }

    /**
     * Writes the <code>DataTypeBooleanArray</code> to a parcel.
     *
     * @param dest  The parcel to which the application should be written.
     * @param flags Additional flags about how the object should be written.
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeBooleanArray(sample);
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
    public boolean[] getSample() {
        return sample;
    }

    /**
     * <code>Creator</code> for <code>DataTypeBooleanArray</code> objects.
     */
    public static final Creator<DataTypeBooleanArray> CREATOR = new Creator<DataTypeBooleanArray>() {

        /**
         * Creates a new <code>DataTypeBooleanArray</code> object from a <code>Parcel</code>.
         *
         * @param in The parcel holding the data type.
         * @return The constructed <code>DataTypeBooleanArray</code> object
         */
        @Override
        public DataTypeBooleanArray createFromParcel(Parcel in) {
            return new DataTypeBooleanArray(in);
        }

        /**
         * Creates a new array of the specified size for <code>DataTypeBooleanArray</code> objects.
         *
         * @param size The size of the new <code>DataTypeBooleanArray</code> array.
         * @return The <code>DataTypeBooleanArray</code> array.
         */
        @Override
        public DataTypeBooleanArray[] newArray(int size) {
            return new DataTypeBooleanArray[size];
        }
    };
}
