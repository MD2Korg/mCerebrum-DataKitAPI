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

package org.md2k.datakitapi.status;

import android.os.Parcel;
import android.os.Parcelable;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

public class Status extends Object implements Parcelable {
    int statusCode;
    String statusMessage;
    public static final int SUCCESS = 0;
    public static final int DATASOURCE_EXIST = 1;
    public static final int DATASOURCE_MULTIPLE_EXIST = 2;
    public static final int DATASOURCE_NOT_EXIST = 3;
    public static final int DATASOURCE_INVALID = 4;
    public static final int DATASOURCE_ACTIVE = 5;
    public static final int INTERNAL_ERROR = -3;
    public static final int ALREADY_SUBSCRIBED = 7;
    public static final int ERROR_NOT_INSTALLED = -1;
    public static final int ERROR_BOUND = -2;
    public static final int DATA_INVALID = 8;

    private static Map<Integer, String> constantNames = null;

    protected Status(Parcel in) {
        statusCode = in.readInt();
        statusMessage = in.readString();
    }

    public static final Creator<Status> CREATOR = new Creator<Status>() {
        @Override
        public Status createFromParcel(Parcel in) {
            return new Status(in);
        }

        @Override
        public Status[] newArray(int size) {
            return new Status[size];
        }
    };

    public static String generateStatusString(int statusCode) {
        return getStatusCodeString(statusCode);
    }

    public static String getStatusCodeString(int statusCode) {
        String constNames = "Unknown";
        if (constantNames == null) {
            Map<Integer, String> cNames = new HashMap<>();
            for (Field field : Status.class.getDeclaredFields()) {
                if ((field.getModifiers() & (Modifier.FINAL | Modifier.STATIC)) != 0 && int.class == field.getType()) {
                    // only record final static int fields
                    try {
                        cNames.put((Integer) field.get(null), field.getName());
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
            constantNames = cNames;
        }
        return constantNames.get(statusCode);
    }

    public Status(int statusCode, String statusMessage) {
        this.statusCode = statusCode;
        this.statusMessage = statusMessage;
    }
    public Status(int statusCode){
        this.statusCode=statusCode;
        this.statusMessage= getStatusCodeString(statusCode);
    }
    public String getStatusMessage(){
        return statusMessage;
    }
    public Status getStatus(){
        return this;
    }
    public int getStatusCode() {
        return statusCode;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(statusCode);
        dest.writeString(statusMessage);
    }
}
