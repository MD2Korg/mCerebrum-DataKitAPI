package org.md2k.datakitapi.datasource;

import java.io.Serializable;
import java.util.HashMap;

/**
 * Created by smhssain on 4/17/2015.
 */
public class Platform extends Object implements Serializable{
    private static final String TAG = Platform.class.getSimpleName();
    private String type=null;
    private String id=null;
    private String description=null;
    private HashMap<String,String> metadata=null;
    Platform(PlatformBuilder platformBuilder){
        this.type=platformBuilder.type;
        this.id=platformBuilder.id;
        this.description=platformBuilder.description;
        this.metadata=platformBuilder.metadata;
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

    public HashMap<String, String> getMetadata() {
        return metadata;
    }
}
