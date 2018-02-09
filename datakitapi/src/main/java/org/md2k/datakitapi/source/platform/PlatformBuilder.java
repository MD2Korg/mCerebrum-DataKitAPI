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

package org.md2k.datakitapi.source.platform;

import org.md2k.datakitapi.source.AbstractObjectBuilder;

import java.util.HashMap;
import java.util.Map;

/**
 * Builder class for platform objects.
 */
public class PlatformBuilder extends AbstractObjectBuilder{

    /**
     * Constructor
     */
    public PlatformBuilder() {}

    /**
     * Constructs a builder object from an existing platform object.
     *
     * @param platform The object to build from.
     */
    public PlatformBuilder(Platform platform) {
        this.metadata=new HashMap<>();
        if(platform.getMetadata()!=null) {
            for (Map.Entry<String, String> entry : platform.getMetadata().entrySet()) {
                this.metadata.put(entry.getKey(), entry.getValue());
            }
        }
        this.type = platform.getType();
        this.id = platform.getId();
    }

    /**
     * Sets the type of the builder object.
     *
     * @param type Type of the desired object.
     * @return The changed builder object.
     */
    public PlatformBuilder setType(String type) {
        super.setType(type);
        return this;
    }

    /**
     * Sets the id of the builder object.
     *
     * @param id Id of the desired object.
     * @return The changed builder object.
     */
    public PlatformBuilder setId(String id) {
        super.setId(id);
        return this;
    }

    /**
     * Sets the metadata of the builder object
     *
     * @param key   Key of the key, value pair associated with the metadata.
     * @param value Value of the key, value pair associated with the metadata.
     * @return The changed builder object.
     */
    public PlatformBuilder setMetadata(String key, String value) {
        super.setMetadata(key, value);
        return this;
    }

    /**
     * Creates a platform object using the specifications of this builder object.
     *
     * @return The completed platform object.
     */
    public Platform build() { return new Platform(this); }
}
