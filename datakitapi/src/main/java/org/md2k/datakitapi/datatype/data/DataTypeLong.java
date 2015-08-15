package org.md2k.datakitapi.datatype.data;

import java.io.Serializable;

/**
 * Created by smh on 6/7/2015.
 */
public class DataTypeLong extends  DataType implements Serializable{
    long sample;

    public DataTypeLong(long timestamp, long sample) {
        super(timestamp);
        this.sample=sample;
    }
}
