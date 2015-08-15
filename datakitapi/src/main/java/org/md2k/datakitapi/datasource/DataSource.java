package org.md2k.datakitapi.datasource;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.io.StreamCorruptedException;
import java.util.HashMap;

/**
 * Created by smhssain on 4/30/2015.
 */
public class DataSource extends Object implements Serializable {
    private static final String TAG = DataSource.class.getSimpleName();
    private String type = null;
    private String id = null;
    private String description = null;
    private HashMap<String, String> metadata = null;

    private DataSource[] lineage = null;
    private String dataType = null;
    private Platform platform = null;
    private PlatformApp platformApp = null;
    private Application application = null;
    private boolean persistent = true;

    DataSource(DataSourceBuilder dataSourceBuilder) {
        this.type = dataSourceBuilder.type;
        this.id = dataSourceBuilder.id;
        this.description = dataSourceBuilder.description;
        this.metadata = dataSourceBuilder.metadata;
        this.lineage = dataSourceBuilder.lineage;
        this.dataType = dataSourceBuilder.dataType;
        this.platform = dataSourceBuilder.platform;
        this.platformApp = dataSourceBuilder.platformApp;
        this.application = dataSourceBuilder.application;
        this.persistent = dataSourceBuilder.persistent;
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

    public DataSource[] getLineage() {
        return lineage;
    }

    public String getDataType() {
        return dataType;
    }

    public Platform getPlatform() {
        return platform;
    }

    public PlatformApp getPlatformApp() {
        return platformApp;
    }

    public Application getApplication() {
        return application;
    }

    public boolean isPersistent() {
        return persistent;
    }

    public byte[] toBytes() {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutput out = null;
        try {
            out = new ObjectOutputStream(bos);
            out.writeObject(this);
            byte[] yourBytes = bos.toByteArray();
            return yourBytes;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException ex) {
                // ignore close exception
            }
            try {
                bos.close();
            } catch (IOException ex) {
                // ignore close exception
            }
        }
        return null;
    }

    public static DataSource fromBytes(byte[] dataSourceByteArray) {
        ByteArrayInputStream bis = new ByteArrayInputStream(dataSourceByteArray);
        DataSource dataSource = null;
        ObjectInput in = null;
        try {
            in = new ObjectInputStream(bis);
            dataSource = (DataSource) in.readObject();
            return dataSource;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (StreamCorruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                bis.close();
            } catch (IOException ex) {
                // ignore close exception
            }
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                // ignore close exception
            }
        }
        return null;
    }
}

