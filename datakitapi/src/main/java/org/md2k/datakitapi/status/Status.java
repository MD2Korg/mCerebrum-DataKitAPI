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

/**
 * This class creates a <code>status</code> object that contains details about the current
 * operational status of the application.
 *
 * <p>
 *     Possible statuses and their keys are:
 *      <ul>
 *         <li><code>SUCCESS</code>, key = 0</li>
 *         <li><code>DATASOURCE_EXIST</code>, key = 1</li>
 *         <li><code>DATASOURCE_MULTIPLE_EXIST</code>, key = 2</li>
 *         <li><code>DATASOURCE_NOT_EXIST</code>, key = 3</li>
 *         <li><code>DATASOURCE_INVALID</code>, key = 4</li>
 *         <li><code>DATASOURCE_ACTIVE</code>, key = 5</li>
 *         <li><code>INTERNAL_ERROR</code>, key = -3</li>
 *         <li><code>ALREADY_SUBSCRIBED</code>, key = 7</li>
 *         <li><code>ERROR_NOT_INSTALLED</code>, key = -1</li>
 *         <li><code>ERROR_BOUND</code>, key = -2</li>
 *         <li><code>DATA_INVALID</code>, key = 8</li>
 *     </ul>
 * </p>
 */
public class Status extends Object implements Parcelable {

    /** Integer value associated with the various statuses of <code>DataKitAPI</code>. */
    int statusCode;

    /** Message that describes the status of the application. */
    String statusMessage;

    /** Key is 0. */
    public static final int SUCCESS = 0;

    /** Key is 1. */
    public static final int DATASOURCE_EXIST = 1;

    /** Key is 2. */
    public static final int DATASOURCE_MULTIPLE_EXIST = 2;

    /** Key is 3. */
    public static final int DATASOURCE_NOT_EXIST = 3;

    /** Key is 4. */
    public static final int DATASOURCE_INVALID = 4;

    /** Key is 5. */
    public static final int DATASOURCE_ACTIVE = 5;

    /** Key is -3. */
    public static final int INTERNAL_ERROR = -3;

    /** Key is 7. */
    public static final int ALREADY_SUBSCRIBED = 7;

    /** Key is -1. */
    public static final int ERROR_NOT_INSTALLED = -1;

    /** Key is -2. */
    public static final int ERROR_BOUND = -2;

    /** Key is 8. */
    public static final int DATA_INVALID = 8;

    /** HashMap used to store the key, value pairs for each status. */
    private static Map<Integer, String> constantNames = null;

    /**
     * Constructor
     *
     * @param statusCode The <code>statusCode</code> for this particular <code>status</code>.
     *                   Also determines the <code>statusMessage</code>.
     */
    public Status(int statusCode){
        this.statusCode = statusCode;
        this.statusMessage = getStatusCodeString(statusCode);
    }

    /**
     * Constructor
     *
     * @param statusCode The <code>statusCode</code> for this particular <code>status</code>.
     * @param statusMessage The <code>statusMessage</code> for this particular <code>status</code>.
     */
    public Status(int statusCode, String statusMessage) {
        this.statusCode = statusCode;
        this.statusMessage = statusMessage;
    }

    /**
     * Constructs a <code>Status</code> object from a <code>Parcel</code>.
     *
     * @param in Parceled <code>DataType</code> object.
     */
    protected Status(Parcel in) {
        statusCode = in.readInt();
        statusMessage = in.readString();
    }

    /**
     * Writes the <code>status</code> to a parcel.
     *
     * @param dest The parcel to which the application should be written.
     * @param flags Additional flags about how the object should be written.
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(statusCode);
        dest.writeString(statusMessage);
    }

    /**
     * @return Always returns 0.
     */
    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * Returns the message associated with the calling <code>status</code>.
     *
     * @return The message for the status.
     */
    public String getStatusMessage(){
        return statusMessage;
    }


    /**
     * Returns the <code>status</code> object itself.
     *
     * @return The calling <code>status</code> object.
     */
    public Status getStatus(){
        return this;
    }


    /**
     * Returns the <code>statusCode</code> as an integer.
     *
     * @return The <code>statusCode</code> for the calling <code>status</code>.
     */
    public int getStatusCode() {
        return statusCode;
    }

    /**
     * Returns the <code>statusMessage</code> associated with the given <code>statusCode</code>.
     *
     * <p>
     *     If the hashMap is not populated, this method will first populate the hashMap with the
     *     following list of statuses.
     *     <ul>
     *         <li><code>SUCCESS</code>, key = 0</li>
     *         <li><code>DATASOURCE_EXIST</code>, key = 1</li>
     *         <li><code>DATASOURCE_MULTIPLE_EXIST</code>, key = 2</li>
     *         <li><code>DATASOURCE_NOT_EXIST</code>, key = 3</li>
     *         <li><code>DATASOURCE_INVALID</code>, key = 4</li>
     *         <li><code>DATASOURCE_ACTIVE</code>, key = 5</li>
     *         <li><code>INTERNAL_ERROR</code>, key = -3</li>
     *         <li><code>ALREADY_SUBSCRIBED</code>, key = 7</li>
     *         <li><code>ERROR_NOT_INSTALLED</code>, key = -1</li>
     *         <li><code>ERROR_BOUND</code>, key = -2</li>
     *         <li><code>DATA_INVALID</code>, key = 8</li>
     *     </ul>
     * </p>
     *
     * @param statusCode The <code>statusCode</code> for this particular <code>status</code>.
     * @return The <code>statusMessage</code> associated with the given <code>statusCode</code>.
     */
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

    /**
     * Generates a string describing the status.
     *
     * @param statusCode The <code>statusCode</code> for this particular <code>status</code>.
     * @return The <code>statusMessage</code> for this particualr <code>status</code>.
     */
    public static String generateStatusString(int statusCode) {
        return getStatusCodeString(statusCode);
    }

    /**
     * <code>Creator</code> for <code>Status</code> objects.
     */
    public static final Creator<Status> CREATOR = new Creator<Status>() {

        /**
         * Creates a new <code>Status</code> object from a <code>Parcel</code>.
         *
         * @param in The parcel holding the status.
         * @return The constructed <code>Status</code> object.
         */
        @Override
        public Status createFromParcel(Parcel in) {
            return new Status(in);
        }

        /**
         * Creates a new array of the specified size for <code>Status</code> objects.
         *
         * @param size The size of the new <code>Status</code> array.
         * @return The <code>Status</code> array.
         */
        @Override
        public Status[] newArray(int size) {
            return new Status[size];
        }
    };
}
