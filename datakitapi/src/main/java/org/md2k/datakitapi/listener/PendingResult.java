package org.md2k.datakitapi.listener;

import java.util.concurrent.TimeUnit;

/**
 * Created by smhssain on 6/4/2015.
 */
public interface PendingResult<R> {
    abstract R await();

    abstract void setResultCallback(ResultCallback<R> callback);
}
