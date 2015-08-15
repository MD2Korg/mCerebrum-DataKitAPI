package org.md2k.datakitapi.datatype.data;

import java.io.Serializable;

/**
 * Created by smh on 6/7/2015.
 */
public class DataTypeByteArray extends  DataType implements Serializable{
    byte[] sample;

    public DataTypeByteArray(long timestamp, byte[] sample) {
        super(timestamp);
        this.sample=sample;
    }
    public byte[] getSample(){
        return sample;
    }
}
