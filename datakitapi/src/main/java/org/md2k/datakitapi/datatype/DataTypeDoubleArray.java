package org.md2k.datakitapi.datatype;

import android.os.Parcel;
import android.os.Parcelable;

import java.nio.ByteBuffer;

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
public class DataTypeDoubleArray extends  DataType implements Parcelable{
    public static final Creator<DataTypeDoubleArray> CREATOR = new Creator<DataTypeDoubleArray>() {
        @Override
        public DataTypeDoubleArray createFromParcel(Parcel in) {
            return new DataTypeDoubleArray(in);
        }

        @Override
        public DataTypeDoubleArray[] newArray(int size) {
            return new DataTypeDoubleArray[size];
        }
    };
    double[] sample;
    public DataTypeDoubleArray(long timestamp, double[] sample) {
        super(timestamp);
        this.sample=sample;
    }

    public DataTypeDoubleArray(){}

    public DataTypeDoubleArray(long dateTime, double sample) {
        this(dateTime, new double[1]);
        this.sample[0] = sample;
    }

    protected DataTypeDoubleArray(Parcel in) {
        super(in);
        sample = in.createDoubleArray();

    }

    static public DataTypeDoubleArray fromRawBytes(long timestamp, byte[] data) {

        double[] sample = new double[data.length / 8];
        for (int i = 0; i < sample.length; i++) {
            sample[i] = ByteBuffer.wrap(data, i * 8, 8).getDouble();
        }
        return new DataTypeDoubleArray(timestamp, sample);

    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeDoubleArray(sample);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public double[] getSample(){
        return sample;
    }

    public byte[] toRawBytes() {
        byte[] data = new byte[sample.length * 8];

        for (int i = 0; i < sample.length; i++) {
            ByteBuffer.wrap(data, i * 8, 8).putDouble(sample[i]);
        }

        return data;
    }

}
