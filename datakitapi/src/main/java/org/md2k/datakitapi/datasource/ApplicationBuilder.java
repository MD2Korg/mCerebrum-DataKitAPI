package org.md2k.datakitapi.datasource;

import java.util.HashMap;

/**
 * Created by smh on 5/17/2015.
 */
public class ApplicationBuilder {
        public String type=null;
        public String id=null;
        public String description=null;
        public HashMap<String,String> metadata=new HashMap<>();
        public ApplicationBuilder setType(String type){this.type = type; return this; }
        public ApplicationBuilder setId(String id){this.id = id; return this; }
        public ApplicationBuilder setDescription(String description){this.description = description; return this; }
        public ApplicationBuilder setMetadata(String key, String value){this.metadata.put(key,value); return this; }
        public Application build() {
            return new Application(this);
        }
}
