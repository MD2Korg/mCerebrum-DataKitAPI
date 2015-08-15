package org.md2k.datakitapi.datatype.data;

import java.io.Serializable;

/**
 * Created by smh on 6/7/2015.
 */
public class DataTypeString extends  DataType implements Serializable{
    String sample;

    public DataTypeString(long timestamp, String sample) {
        super(timestamp);
        this.sample=sample;
    }
}
