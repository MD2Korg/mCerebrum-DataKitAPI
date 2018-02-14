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
package org.md2k.datakitapi.source.platformapp;

import android.os.Parcel;
import android.os.Parcelable;

import com.bluelinelabs.logansquare.annotation.JsonObject;

import org.md2k.datakitapi.source.AbstractObject;

/**
 * This class creates <code>PlatformApp</code> objects with information about wearable device
 * applications used as data sources.
 */
@JsonObject(fieldDetectionPolicy = JsonObject.FieldDetectionPolicy.NONPRIVATE_FIELDS)
public class PlatformApp extends AbstractObject implements Parcelable{
    /**
     * Constructor
     */
    public PlatformApp(){}

    /**
     * Constructor
     *
     * <p>
     *     This constructor is called from the <code>PlatformAppBuilder</code> class using the
     *     <code>build()</code> method.
     * </p>
     *
     * @param platformAppBuilder The builder object for this data source.
     */
    PlatformApp(PlatformAppBuilder platformAppBuilder){
        super(platformAppBuilder);
    }

    /**
     * Creates an <code>PlatformApp</code> object from a <code>Parcel</code>.
     *
     * @param in Parceled <code>PlatformApp</code> data
     */
    protected PlatformApp(Parcel in) {
        super(in);
    }

    /**
     * Writes the <code>PlatformApp</code> object to a <code>parcel</code>.
     *
     * @param dest  The parcel to which the application should be written.
     * @param flags Additional flags about how the object should be written.
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
    }

    /**
     * @return Always returns 0.
     */
    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * <code>Creator</code> for <code>PlatformApp</code> objects.
     */
    public static final Creator<PlatformApp> CREATOR = new Creator<PlatformApp>() {

        /**
         * Creates a new <code>DataSource</code> object from a <code>Parcel</code>.
         *
         * @param in The parcel holding the application.
         * @return The reconstructed <code>DataSource</code> object.
         */
        @Override
        public PlatformApp createFromParcel(Parcel in) {
            return new PlatformApp(in);
        }

        /**
         * Creates a new array of the specified size for <code>DataSource</code> objects.
         *
         * @param size The size of the new <code>DataSource</code> array.
         * @return The <code>DataSource</code> array.
         */
        @Override
        public PlatformApp[] newArray(int size) {
            return new PlatformApp[size];
        }
    };
}
