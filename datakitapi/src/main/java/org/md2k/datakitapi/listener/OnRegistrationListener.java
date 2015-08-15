package org.md2k.datakitapi.listener;

import org.md2k.datakitapi.datasource.DataSourceClient;

public interface OnRegistrationListener {
    public abstract void onRegistered(DataSourceClient dataSourceClient);
}
