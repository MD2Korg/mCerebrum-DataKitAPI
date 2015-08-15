package org.md2k.datakitapi.listener;

/**
 * Created by smhssain on 6/4/2015.
 */
public interface ReceiveCallback<R> {
    public abstract void onReceive(R receive);
}
