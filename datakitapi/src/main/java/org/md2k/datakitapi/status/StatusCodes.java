package org.md2k.datakitapi.status;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by smhssain on 4/20/2015.
 */
public class StatusCodes{
    public static final int SUCCESS = 0;
    public static final int EXISTS = 1;
    public static final int NOT_FOUND = 2;
    public static final int MULTIPLE_EXISTS = 3;
    public static final int INVALID_ENTRY=4;
    public static final int FAILED=5;
/*    public static final int SERVICE_VERSION_UPDATE_REQUIRED = 2;
    public static final int SERVICE_DISABLED = 3;
    public static final int SIGN_IN_REQUIRED = 4;
    public static final int INVALID_ACCOUNT = 5;
    public static final int RESOLUTION_REQUIRED = 6;
    public static final int NETWORK_ERROR = 7;
    public static final int INTERNAL_ERROR = 8;
    public static final int SERVICE_INVALID = 9;
    public static final int DEVELOPER_ERROR = 10;
    public static final int LICENSE_CHECK_FAILED = 11;
    public static final int ERROR = 13;
    public static final int INTERRUPTED = 14;
    public static final int TIMEOUT = 15;
    public static final int CANCELED = 16;
    public static final int INVALID_DATA=18;
    public static final int INVALID_DATASOURCE=19;
    public static final int DATABASE_ERROR=20;
*/
    private static Map<Integer, String> constantNames = null;

    public static String generateStatusString(int statusCode) {
        return getStatusCodeString(statusCode);
    }

    public static String getStatusCodeString(int statusCode) {
        String constNames = "Unknown";
        if (constantNames == null) {
            Map<Integer, String> cNames = new HashMap<Integer, String>();
            for (Field field : StatusCodes.class.getDeclaredFields()) {
                if ((field.getModifiers() & (Modifier.FINAL | Modifier.STATIC)) != 0 && int.class == field.getType()) {
                    // only record final static int fields
                    try {
                        cNames.put((Integer) field.get(null), field.getName());
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
            constantNames = cNames;
        }
        return constantNames.get(statusCode);
    }
}
