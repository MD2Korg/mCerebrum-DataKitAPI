package org.md2k.datakitapi.time;

/**
 * Created by smhssain on 5/29/2015.
 */
public class DateTime {
    public static int MILLISECOND = 1;
    public static int NANOSECOND = 2;

    private static long getDateTimeMillis() {
        return System.currentTimeMillis();
    }

    private static long getDateTimeNanos() {
        return System.nanoTime();
    }

    public static long getDateTime() {
        return getDateTimeMillis();
    }
}
