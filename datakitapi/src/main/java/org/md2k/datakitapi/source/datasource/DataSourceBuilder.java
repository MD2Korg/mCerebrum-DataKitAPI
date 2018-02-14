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

package org.md2k.datakitapi.source.datasource;

import android.util.Log;

import org.md2k.datakitapi.source.application.Application;
import org.md2k.datakitapi.source.platform.Platform;
import org.md2k.datakitapi.source.platformapp.PlatformApp;
import org.md2k.datakitapi.source.AbstractObjectBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Builder class for <code>DataSource</code> objects
 */
public class DataSourceBuilder extends AbstractObjectBuilder {

    /** Platform application the data source collects data from. */
    public Platform platform = null;

    /**  */
    public PlatformApp platformApp = null;

    /** Android application */
    public Application application = null;

    /** Whether the data source is persistent. */
    public boolean persistent = true;

    /** List of data descriptors as sets of tuples
     *
     * <p>
     *     The tuples describe the individual values of the data points.
     * </p>
     */
    public ArrayList<HashMap<String,String>> dataDescriptors=null;


    /**
     * Constructor
     */
    public DataSourceBuilder() {}

    /**
     * Constructs a new <code>DataSourceBuilder</code> from a <code>DataSource</code> object.
     *
     * @param dataSource The object to build from.
     */
    public DataSourceBuilder(DataSource dataSource) {
        this.metadata = new HashMap<>();
        if(dataSource.getMetadata()!= null) {
            for (Map.Entry<String, String> entry : dataSource.getMetadata().entrySet()) {
                this.metadata.put(entry.getKey(), entry.getValue());
            }
        }
        this.type = dataSource.getType();
        this.id = dataSource.getId();
        this.platform = dataSource.getPlatform();
        this.platformApp = dataSource.getPlatformApp();
        this.application = dataSource.getApplication();
        this.persistent = dataSource.isPersistent();
        this.dataDescriptors = dataSource.getDataDescriptors();
    }

    /**
     * Sets the type of the <code>DataSourceBuilder</code>.
     *
     * @param type Type of the desired object.
     * @return The changed builder object.
     */
    public DataSourceBuilder setType(String type) {
        super.setType(type);
        return this;
    }

    /**
     * Sets the Id of the <code>DataSourceBuilder</code>.
     *
     * @param id Id of the desired object.
     * @return The changed builder object.
     */
    public DataSourceBuilder setId(String id) {
        super.setId(id);
        return this;
    }

    /**
     * Sets the metadata of the <code>DataSourceBuilder</code>.
     *
     * @param key   Key of the key, value pair associated with the metadata.
     * @param value Value of the key, value pair associated with the metadata.
     * @return The changed builder object.
     */
    public DataSourceBuilder setMetadata(String key, String value) {
        super.setMetadata(key, value);
        return this;
    }

    /**
     * Sets the metadata hashMap of the <code>DataSourceBuilder</code>.
     *
     * @param metadata The metadata as a HashMap.
     * @return The changed builder object.
     */
    public DataSourceBuilder setMetadata(HashMap<String, String> metadata) {
        super.setMetadata(metadata);
        return this;
    }

    /**
     * Sets the <code>Platform</code> of the <code>DataSourceBuilder</code>.
     *
     * @param platform The platform the data source collects data from.
     * @return The changed builder object.
     */
    public DataSourceBuilder setPlatform(Platform platform) {
        this.platform = platform;
        return this;
    }

    /**
     * Sets the <code>PlatformApp</code> of the <code>DataSourceBuilder</code>.
     *
     * @param platformApp
     * @return The changed builder object.
     */
    public DataSourceBuilder setPlatformApp(PlatformApp platformApp) {
        this.platformApp = platformApp;
        return this;
    }

    /**
     * Sets the <code>Application</code> of the <code>DataSourceBuilder</code>.
     *
     * @param application
     * @return The changed builder object.
     */
    public DataSourceBuilder setApplication(Application application) {
        this.application = application;
        return this;
    }

    /**
     * Sets the <code>Persistent</code> of the <code>DataSourceBuilder</code>.
     *
     * @param persistent Whether the data source is persistent or not.
     * @return The changed builder object.
     */
    public DataSourceBuilder setPersistent(boolean persistent) {
        this.persistent = persistent;
        return this;
    }

    /**
     * Sets the <code>DataDescriptors</code> of the <code>DataSourceBuilder</code>.
     *
     * @param dataDescriptors
     * @return The changed builder object.
     */
    public DataSourceBuilder setDataDescriptors(ArrayList<HashMap<String,String>> dataDescriptors){
        this.dataDescriptors = dataDescriptors;
        return this;
    }

    /**
     * Builds a new <code>DataSource</code> object from the specifications of this builder object.
     *
     * @return The completed <code>DataSource</code> object.
     */
    public DataSource build() {
        return new DataSource(this);
    }
}
