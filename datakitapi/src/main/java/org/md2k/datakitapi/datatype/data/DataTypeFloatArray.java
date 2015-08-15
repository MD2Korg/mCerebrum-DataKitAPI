package org.md2k.datakitapi.datatype.data;

import java.io.Serializable;

/**
 * Created by smh on 6/7/2015.
 */
public class DataTypeFloatArray extends  DataType implements Serializable{
    float[] sample;

    public DataTypeFloatArray(long timestamp, float[] sample) {
        super(timestamp);
        this.sample=sample;
    }
    public float[] getSample(){
        return sample;
    }
}
