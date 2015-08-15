package org.md2k.datakitapi.listener;

/**
 * Created by smhssain on 5/28/2015.
 */
public class ConnectionResult extends Object {
    public static final int API_UNAVAILABLE = 16;
    public static final int CANCELED = 13;
    public static final int DEVELOPER_ERROR = 10;
    public static final int INTERNAL_ERROR = 8;
    public static final int INTERRUPTED = 15;
    public static final int INVALID_ACCOUNT = 5;
    public static final int LICENSE_CHECK_FAILED = 11;
    public static final int NETWORK_ERROR = 7;
    public static final int RESOLUTION_REQUIRED = 6;
    public static final int SERVICE_DISABLED = 3;
    public static final int SERVICE_INVALID = 9;
    public static final int SERVICE_MISSING = 1;
    public static final int SERVICE_UPDATING = 18;
    public static final int SERVICE_VERSION_UPDATE_REQUIRED = 2;
    public static final int SIGN_IN_FAILED = 17;
    public static final int SIGN_IN_REQUIRED = 4;
    public static final int SUCCESS = 0;
    public static final int TIMEOUT = 14;

    private boolean success;
    private int statusCode;

    public ConnectionResult(int statusCode){
        this.statusCode=statusCode;
    }

    public int getErrorCode() {
        //TODO: get error code
        return 0;
    }

    public boolean isSuccess() {
        //TODO: issuccess()
        return true;
    }

}
