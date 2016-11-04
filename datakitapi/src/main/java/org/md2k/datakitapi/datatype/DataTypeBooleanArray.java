package org.md2k.datakitapi.datatype;

import android.os.Parcel;
import android.os.Parcelable;

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
public class DataTypeBooleanArray extends DataType implements Parcelable {
    public static final Creator<DataTypeBooleanArray> CREATOR = new Creator<DataTypeBooleanArray>() {
        @Override
        public DataTypeBooleanArray createFromParcel(Parcel in) {
            return new DataTypeBooleanArray(in);
        }

        @Override
        public DataTypeBooleanArray[] newArray(int size) {
            return new DataTypeBooleanArray[size];
        }
    };
    boolean[] sample;
    public DataTypeBooleanArray(long timestamp, boolean[] sample) {
        super(timestamp);
        this.sample = new boolean[sample.length];
        System.arraycopy(sample, 0, this.sample, 0, sample.length);
    }

    public DataTypeBooleanArray(DataTypeBooleanArray dt) {
        super(dt);
        this.sample = new boolean[dt.sample.length];
        System.arraycopy(dt.sample, 0, this.sample, 0, sample.length);
    }

    public DataTypeBooleanArray(){}

    protected DataTypeBooleanArray(Parcel in) {
        super(in);
        sample = in.createBooleanArray();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeBooleanArray(sample);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public boolean[] getSample() {
        return sample;
    }
}
