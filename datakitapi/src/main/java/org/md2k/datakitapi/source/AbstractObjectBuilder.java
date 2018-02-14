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

package org.md2k.datakitapi.source;

import java.util.HashMap;

/**
 * Superclass for the <code>PlatformAppBuilder</code>, <code>PlatformBuilder</code>,
 * <code>DataSourceBuilder</code>, and <code>ApplicationBuilder</code> classes.
 */
public abstract class AbstractObjectBuilder {

    /** Desired <code>AbstractObject</code> type. */
    protected String type = null;

    /** Desired <code>AbstractObject</code> id. */
    protected String id = null;

    /** HashMap of the object's metadata. */
    protected HashMap<String,String> metadata = new HashMap<>();

    /**
     * Sets the <code>type</code> field of the builder object and then returns the object.
     *
     * @param type Type of the desired object
     * @return The changed builder object
     */
    public AbstractObjectBuilder setType(String type){this.type = type; return this; }

    /**
     * Sets the <code>id</code> field of the builder object and then returns the object.
     *
     * @param id Id of the desired object.
     * @return The changed builder object.
     */
    public AbstractObjectBuilder setId(String id){this.id = id; return this; }

    /**
     * Sets the <code>metadata</code> field of the builder object and then returns the object.
     *
     * @param key Key of the key, value pair associated with the metadata.
     * @param value Value of the key, value pair associated with the metadata.
     * @return The changed builder object.
     */
    public AbstractObjectBuilder setMetadata(String key, String value){
        this.metadata.put(key,value);
        return this;
    }

    /**
     * Sets the <code>metadata</code> field of the builder object and then returns the object.
     *
     * @param metadata The metadata as a HashMap.
     * @return The changed builder object.
     */
    public AbstractObjectBuilder setMetadata(HashMap<String,String> metadata){
        this.metadata = metadata;
        return this;
    }
}
