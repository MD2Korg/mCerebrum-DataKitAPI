package org.md2k.datakitapi.datatype.data;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.io.StreamCorruptedException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by smh on 6/7/2015.
 */
public class DataType implements Serializable{
    long dateTime;

    public DataType(long dateTime) {
        this.dateTime = dateTime;
    }
    public long getStartDateTime() {
        return dateTime;
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

    public static DataType fromBytes(byte[] dataSourceByteArray) {
        ByteArrayInputStream bis = new ByteArrayInputStream(dataSourceByteArray);
        DataType dataSource = null;
        ObjectInput in = null;
        try {
            in = new ObjectInputStream(bis);
            dataSource = (DataType) in.readObject();
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
