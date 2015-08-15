package org.md2k.datakitapi.status;

import java.io.Serializable;

/**
 * Created by smhssain on 4/20/2015.
 */
public class Status extends Object implements Serializable {
    int statusCode;
    String statusMessage;

    public Status(int statusCode, String statusMessage) {
        this.statusCode = statusCode;
        this.statusMessage = statusMessage;
    }
    public Status(int statusCode){
        this.statusCode=statusCode;
        this.statusMessage= StatusCodes.getStatusCodeString(statusCode);
    }

    public String getStatusMessage(){
        return statusMessage;
    }
    public Status getStatus(){
        return this;
    }
    public int getStatusCode() {
        return statusCode;
    }
}
