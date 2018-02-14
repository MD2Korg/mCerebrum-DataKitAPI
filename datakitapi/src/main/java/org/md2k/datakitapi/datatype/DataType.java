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

import com.bluelinelabs.logansquare.annotation.JsonObject;

import org.md2k.datakitapi.time.DateTime;

/**
 * This class provides the methods that all <code>DataType</code> objects use.
 */
@JsonObject(fieldDetectionPolicy = JsonObject.FieldDetectionPolicy.NONPRIVATE_FIELDS)
public class DataType implements Parcelable{

    /** The timestamp for when the data was collected */
    long dateTime;

    /** The amount of time in milliseconds to add to UTC to get standard time in this time zone. */
    long offset;

    /**
     * Constructor
     */
    public DataType(){}

    /**
     * Constructs a <code>DataType</code> object with a <code>dataTime</code>.
     *
     * @param dateTime The timestamp for when the data was collected.
     */
    public DataType(long dateTime) {
        this.dateTime = dateTime;
        this.offset = DateTime.getTimeZoneOffset();
    }

    /**
     * Constructs a <code>DataType</code> object from a <code>Parcel</code>.
     *
     * @param in Parceled <code>DataType</code> object.
     */
    protected DataType(Parcel in) {
        dateTime = in.readLong();
        offset = in.readLong();
    }

    /**
     * Writes the <code>DataType</code> to a parcel.
     *
     * @param dest The parcel to which the application should be written.
     * @param flags Additional flags about how the object should be written.
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(dateTime);
        dest.writeLong(offset);
    }

    /**
     * @return Always returns 0.
     */
    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * @return The timestamp of the <code>DataType</code>.
     */
    public long getDateTime() {
        return dateTime;
    }

    /**
     * <code>Creator</code> for <code>DataType</code> objects.
     */
    public static final Creator<DataType> CREATOR = new Creator<DataType>() {

        /**
         * Creates a new <code>DataType</code> object from a <code>Parcel</code>.
         *
         * @param in The parcel holding the data type.
         * @return The constructed <code>DataType</code> object
         */
        @Override
        public DataType createFromParcel(Parcel in) {
            return new DataType(in);
        }

        /**
         * Creates a new array of the specified size for <code>DataType</code> objects.
         *
         * @param size The size of the new <code>DataType</code> array.
         * @return The <code>DataType</code> array.
         */
        @Override
        public DataType[] newArray(int size) {
            return new DataType[size];
        }
    };
}
