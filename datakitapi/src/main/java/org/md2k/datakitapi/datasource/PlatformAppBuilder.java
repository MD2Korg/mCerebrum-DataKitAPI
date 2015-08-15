package org.md2k.datakitapi.datasource;

import java.util.HashMap;

/**
 * Created by smh on 5/17/2015.
 */
public class PlatformAppBuilder {
        public String type=null;
        public String id=null;
        public String description=null;
        public HashMap<String,String> metadata=new HashMap<>();
        public PlatformAppBuilder setType(String type){this.type = type; return this; }
        public PlatformAppBuilder setId(String id){this.id = id; return this; }
        public PlatformAppBuilder setDescription(String description){this.description = description; return this; }
        public PlatformAppBuilder setMetadata(String key, String value){this.metadata.put(key,value); return this; }
        public PlatformApp build() {
            return new PlatformApp(this);
        }
}
