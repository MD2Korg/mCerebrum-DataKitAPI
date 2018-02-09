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

package org.md2k.datakitapi.source.application;

import org.md2k.datakitapi.source.AbstractObjectBuilder;

/**
 * Builder class for <code>Application</code> objects.
 */
public class ApplicationBuilder extends AbstractObjectBuilder {
    /**
     * Constructor
     */
    public ApplicationBuilder(){}

    /**
     * Constructs a new <code>ApplicationBuilder</code> from an <code>Application</code>.
     *
     * @param application The application to be built
     */
    public ApplicationBuilder(Application application){
        this.metadata = application.getMetadata();
        this.type = application.getType();
        this.id = application.getId();
    }

    /**
     * Sets the type of the <code>ApplicationBuilder</code>.
     *
     * @param type The type of the application.
     * @return The <code>ApplicationBuilder</code> object with the new type.
     */
    public ApplicationBuilder setType(String type) {
        super.setType(type);
        return this;
    }

    /**
     * Sets the id of the <code>ApplicationBuilder</code>.
     *
     * @param id The id of the application
     * @return The <code>ApplicationBuilder</code> object with the new id.
     */
    public ApplicationBuilder setId(String id) {
        super.setId(id);
        return this;
    }

    /**
     * Sets the metadata of the <code>ApplicationBuilder</code>.
     *
     * @param key The key to the metadata.
     * @param value The value of the metadata.
     * @return The <code>ApplicationBuilder</code> object with the new metadata.
     */
    public ApplicationBuilder setMetadata(String key, String value) {
        super.setMetadata(key, value);
        return this;
    }

    /**
     * Returns a new <code>Application</code> object constructed from the calling <code>ApplicationBuilder</code>.
     *
     * @return The new <code>Application</code> object.
     */
    public Application build() {
        return new Application(this);
    }
}
