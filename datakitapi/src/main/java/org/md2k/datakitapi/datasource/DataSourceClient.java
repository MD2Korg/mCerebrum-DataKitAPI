package org.md2k.datakitapi.datasource;

import org.md2k.datakitapi.status.Status;

import java.io.Serializable;

/**
 * Created by smhssain on 4/20/2015.
 */
public class DataSourceClient extends Object implements Serializable {
    private int ds_id;
    DataSource dataSource;
    private Status status;

    public DataSourceClient(int ds_id, DataSource dataSource, Status status) {
        this.ds_id = ds_id;
        this.dataSource = dataSource;
        this.status = status;
    }

    public int getDs_id() {
        return ds_id;
    }

    public Status getStatus() {
        return status;
    }

    public DataSource getDataSource() {
        return dataSource;
    }
}
