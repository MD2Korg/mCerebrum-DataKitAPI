package org.md2k.datakitapi.datatype.data;

import java.io.Serializable;

/**
 * Created by smh on 6/7/2015.
 */
public class DataTypeIntArray extends  DataType implements Serializable{
    int[] sample;

    public DataTypeIntArray(long timestamp, int[] sample) {
        super(timestamp);
        this.sample=sample;
    }
}
