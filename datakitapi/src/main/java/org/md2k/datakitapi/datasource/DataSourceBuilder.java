package org.md2k.datakitapi.datasource;

import java.util.HashMap;

/**
 * Created by smh on 5/17/2015.
 */
public class DataSourceBuilder {
        public String type=null;
        public String id=null;
        public String description=null;
        public HashMap<String,String> metadata=new HashMap<>();
        public DataSource[] lineage=null;
        public String dataType=null;
        public Platform platform=null;
        public PlatformApp platformApp=null;
        public Application application=null;
        public boolean persistent=true;
        public DataSourceBuilder setType(String type){this.type = type; return this; }
        public DataSourceBuilder setId(String id){this.id = id; return this; }
        public DataSourceBuilder setDescription(String description){this.description = description; return this; }
        public DataSourceBuilder setMetadata(String key, String value){this.metadata.put(key,value); return this; }
        public DataSourceBuilder setLineage(DataSource[] lineage){this.lineage = lineage; return this; }
        public DataSourceBuilder setDataType(String dataType){this.dataType = dataType; return this; }
        public DataSourceBuilder setPlatform(Platform platform){this.platform = platform; return this; }
        public DataSourceBuilder setPlatformApp(PlatformApp platformApp){this.platformApp = platformApp; return this; }
        public DataSourceBuilder setApplication(Application application){this.application = application; return this; }
        public DataSourceBuilder setPersistent(boolean persistent){this.persistent = persistent; return this; }
        public DataSource build() {
            return new DataSource(this);
        }
}
