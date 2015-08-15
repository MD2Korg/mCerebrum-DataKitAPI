package org.md2k.datakitapi.datasource;

import java.util.HashMap;

/**
 * Created by smh on 5/17/2015.
 */
public class PlatformBuilder {
        public String type=null;
        public String id=null;
        public String description=null;
        public HashMap<String,String> metadata=new HashMap<>();
        public PlatformBuilder setType(String type){this.type = type; return this; }
        public PlatformBuilder setId(String id){this.id = id; return this; }
        public PlatformBuilder setDescription(String description){this.description = description; return this; }
        public PlatformBuilder setMetadata(String key, String value){this.metadata.put(key,value); return this; }
        public Platform build() {
            return new Platform(this);
        }
}
