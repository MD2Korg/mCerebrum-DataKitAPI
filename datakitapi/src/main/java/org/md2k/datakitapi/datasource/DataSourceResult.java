package org.md2k.datakitapi.datasource;

import org.md2k.datakitapi.status.Status;
import org.md2k.datakitapi.status.StatusCodes;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by smhssain on 4/20/2015.
 */
public class DataSourceResult extends Object implements Serializable {
//    private int statusCode;
    private Status status;
    ArrayList<DataSource> dataSources;

    ArrayList<DataSource> getDataSources() {
        return dataSources;
    }

    public DataSourceResult() {
        setStatus(StatusCodes.NOT_FOUND);
        dataSources = new ArrayList<DataSource>();
    }

    public Status getStatus() {
//        return statusCode;
        return status;
    }

    public void setStatus(int statusCode) {
//        this.statusCode=statusCode;
       String str= new String(StatusCodes.generateStatusString(statusCode));
       status = new Status(statusCode,str);
    }
    public void addDataSource(DataSource dataSource) {
        dataSources.add(dataSource);
    }
}
