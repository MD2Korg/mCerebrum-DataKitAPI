/*
 * Copyright (c) 2018, The University of Memphis, MD2K Center of Excellence
 *
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * * Redistributions of source code must retain the above copyright notice, this
 * list of conditions and the following disclaimer.
 *
 * * Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following disclaimer in the documentation
 * and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.md2k.datakitapi.time;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * This class handles all date and time conversions for this application.
 */
public class DateTime {

    /** Integer representing a millisecond. <p>Set to 1.</p> */
    public static int MILLISECOND = 1;

    /** Integer representing a nanosecond. <p>Set to 2.</p> */
    public static int NANOSECOND = 2;

    /** Number of milliseconds in a second */
    public final static long SECOND_IN_MILLIS = 1000L;

    /** Number of milliseconds in a minute */
    public final static long MINUTE_IN_MILLIS = 60L * 1000L;

    /** Number of milliseconds in an hour */
    public final static long HOUR_IN_MILLIS = 60L * 60 * 1000L;

    /** Number of milliseconds in a day */
    public final static long DAY_IN_MILLIS = 24 * 60 * 60 * 1000L;


    /**
     * @return The current time in milliseconds.
     */
    private static long getDateTimeMillis() {
        return System.currentTimeMillis();
    }

    /**
     * @return The current time in nanoseconds.
     */
    private static long getDateTimeNanos() {
        return System.nanoTime();
    }

    /**
     * @return The current time in milliseconds.
     */
    public static long getDateTime() {
        return getDateTimeMillis();
    }

    /**
     * @return The amount of time in milliseconds to add to UTC to get standard time in this time zone.
     */
    public static long getTimeZoneOffset(){
        return TimeZone.getDefault().getRawOffset();
    }

    /**
     * @return The amount of time to be added to local standard time to get local wall clock time.
     */
    public static long getDayLightSavingOffset(){
        TimeZone timeZone = TimeZone.getDefault();
        return timeZone.getDSTSavings();
    }

    /**
     * @return Whether the current date is in daylight savings time.
     */
    public static boolean isDayLightSavingNow(){
        TimeZone timeZone = TimeZone.getDefault();
        return timeZone.inDaylightTime(new Date());
    }

    /**
     * Returns a string in the format: Locale, hour:minute:second.
     *
     * @param timestamp The timestamp to convert.
     * @return The converted timestamp.
     */
    public static String convertTimestampToTimeStr(long timestamp){
        timestamp = timestamp/1000;
        long hour = timestamp/(60*60);
        timestamp = timestamp-(hour*60*60);
        long minute = timestamp/(60);
        long second = timestamp-(minute*60);
        String timeStr = "";
        timeStr = timeStr+String.format(Locale.getDefault(), "%02d:%02d:%02d",hour,minute,second);
        return timeStr;
    }

    /**
     * Returns a string in the format: MM-dd-yyyy HH:mm:ss.SSS.
     *
     * @param timestamp The timestamp to convert.
     * @return The converted timestamp.
     */
    public static String convertTimeStampToDateTime(long timestamp){
        try {
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(timestamp);
            SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss.SSS", Locale.getDefault());
            Date currenTimeZone = calendar.getTime();
            return sdf.format(currenTimeZone);
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * Returns a date time string in the provided format.
     *
     * @param timestamp The timestamp to convert.
     * @param format The format the string to adhere to.
     * @return A date time string in the given format.
     */
    public static String convertTimeStampToDateTime(long timestamp, String format){
        try {
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(timestamp);
            SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.getDefault());
            Date currenTimeZone = calendar.getTime();
            return sdf.format(currenTimeZone);
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * Returns the number of milliseconds in today so far.
     *
     * @param time The time of the day.
     * @return The number of milliseconds since the beginning of the day till the input time.
     */
    public static long getTodayAtInMilliSecond(String time){
        String[] s = time.split(":");
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(s[0]));
        calendar.set(Calendar.MINUTE, Integer.parseInt(s[1]));
        calendar.set(Calendar.SECOND, Integer.parseInt(s[2]));
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTimeInMillis();
    }

    /**
     * Converts a time value from hours, minutes, and seconds into milliseconds.
     *
     * @param time The time to convert to milliseconds.
     * @return The time converted into milliseconds.
     */
    public static long getTimeInMillis(String time){
        String timeTemp = time;
        long timeValue;
        if(time.startsWith("-") || time.startsWith("+"))
            timeTemp = time.substring(1);
        String[] s = timeTemp.split(":");
        timeValue = Long.parseLong(s[0]) * HOUR_IN_MILLIS + Long.parseLong(s[1]) * MINUTE_IN_MILLIS
                + Long.parseLong(s[2]) * SECOND_IN_MILLIS;
        if(time.startsWith("-"))
            timeValue = -timeValue;
        return timeValue;
    }

    /**
     * Extracts the day of the week from the given timestamp.
     *
     * @param timestamp Timestamp to extract the day of week from.
     * @return The day of the week.
     */
    public static int getDayOfWeek(long timestamp) {
        Calendar calender=Calendar.getInstance();
        calender.setTimeInMillis(timestamp);
        return calender.get(Calendar.DAY_OF_WEEK);
    }
}
