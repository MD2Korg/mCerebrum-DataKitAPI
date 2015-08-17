package org.md2k.datakitapi.source;

import java.io.Serializable;
import java.util.Map;

/**
 * Copyright (c) 2015, The University of Memphis, MD2K Center
 * - Syed Monowar Hossain <monowar.hossain@gmail.com>
 * All rights reserved.
 * <p/>
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * <p/>
 * * Redistributions of source code must retain the above copyright notice, this
 * list of conditions and the following disclaimer.
 * <p/>
 * * Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following disclaimer in the documentation
 * and/or other materials provided with the distribution.
 * <p/>
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
public abstract class AbstractObject implements Serializable{
    protected String type = null;
    protected String id = null;
    protected String description = null;
    protected Map<String, String> metadata = null;
    public String getType() {
        return type;
    }
    public String getId() {
        return id;
    }
    public String getDescription() {
        return description;
    }
    public Map<String, String> getMetadata() {
        return metadata;
    }
    public String toString() {
        String str = "type=" + type + ";id=" + id + ";description=" + description;
        if (metadata == null) str = str + ";metadata=null";
        else {
            str = str + ";metadata=";
            for (String s : metadata.keySet()) str = str + "(" + s + "->" + metadata.get(s) + ");";
        }
        return str;
    }
    public AbstractObject(AbstractObjectBuilder abstractObjectBuilder) {
        this.type = abstractObjectBuilder.type;
        this.id = abstractObjectBuilder.id;
        this.description = abstractObjectBuilder.description;
        this.metadata = abstractObjectBuilder.metadata;
    }

}
