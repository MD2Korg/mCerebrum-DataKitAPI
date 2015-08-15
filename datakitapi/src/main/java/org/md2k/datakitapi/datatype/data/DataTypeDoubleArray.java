package org.md2k.datakitapi.datatype.data;

import java.io.Serializable;

/**
 * Created by smh on 6/7/2015.
 */
public class DataTypeDoubleArray extends  DataType implements Serializable{
    double[] sample;

    public DataTypeDoubleArray(long timestamp, double[] sample) {
        super(timestamp);
        this.sample=sample;
    }
    public double[] getSample(){
        return sample;
    }
}
