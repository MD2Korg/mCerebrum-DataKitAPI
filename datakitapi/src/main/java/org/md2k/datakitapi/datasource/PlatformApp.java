package org.md2k.datakitapi.datasource;

import java.util.Map;

/**
 * Created by smhssain on 4/17/2015.
 */
public class PlatformApp extends Object{
    private static final String TAG = PlatformApp.class.getSimpleName();
    private String type=null;
    private String id=null;
    private String description=null;
    private Map<String,String> metadata=null;
    PlatformApp(PlatformAppBuilder platformAppBuilder){
        this.type=platformAppBuilder.type;
        this.id=platformAppBuilder.id;
        this.description=platformAppBuilder.description;
        this.metadata=platformAppBuilder.metadata;
    }

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
}
