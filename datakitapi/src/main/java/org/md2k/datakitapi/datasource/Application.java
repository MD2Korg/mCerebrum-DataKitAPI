package org.md2k.datakitapi.datasource;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by smhssain on 4/17/2015.
 */
public class Application extends Object implements Serializable{
    private static final String TAG = Application.class.getSimpleName();
    private String type=null;
    private String id=null;
    private String description=null;
    private Map<String,String> metadata=null;
/*    public void printAll(){
        if(metadata!=null) for(String s:metadata.keySet()) Log.d(TAG,"metadata:"+s+"->"+metadata.get(s));
    }
*/
    Application(ApplicationBuilder applicationBuilder){
        this.type=applicationBuilder.type;
        this.id=applicationBuilder.id;
        this.description=applicationBuilder.description;
        this.metadata=applicationBuilder.metadata;
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
