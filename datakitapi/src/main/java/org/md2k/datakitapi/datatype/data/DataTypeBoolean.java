package org.md2k.datakitapi.datatype.data;

import java.io.Serializable;

/**
 * Created by smh on 6/7/2015.
 */
public class DataTypeBoolean extends  DataType implements Serializable{
    boolean sample;

    public DataTypeBoolean(long timestamp, boolean sample) {
        super(timestamp);
        this.sample=sample;
    }
}
