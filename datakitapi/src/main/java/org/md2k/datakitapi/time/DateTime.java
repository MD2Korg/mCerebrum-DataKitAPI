package org.md2k.datakitapi.time;

import java.util.Date;
import java.util.TimeZone;

/**
 * Copyright (c) 2015, The University of Memphis, MD2K Center
 * - Syed Monowar Hossain <monowar.hossain@gmail.com>
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
    public static long getTimeZoneOffset(){
        TimeZone timeZone=TimeZone.getDefault();
        return timeZone.getRawOffset();
    }
    public static long getDayLightSavingOffset(){
        TimeZone timeZone=TimeZone.getDefault();
        return timeZone.getDSTSavings();
    }
    public static boolean isDayLightSavingNow(){
        TimeZone timeZone=TimeZone.getDefault();
        return timeZone.inDaylightTime(new Date());
    }
    public static String convertTimeToDayTimeStr(long timestamp){
        timestamp=timestamp/1000;
        long day=timestamp/(60*60*24);
        timestamp=timestamp-(day*60*60*24);
        long hour=timestamp/(60*60);
        timestamp=timestamp-(hour*60*60);
        long minute=timestamp/(60);
        long second=timestamp-(minute*60);
        String timeStr="";
        if(day!=0){
            timeStr=timeStr+String.format("%02d Day ",day);
        }
        if(timeStr.length()!=0 || hour!=0){
            timeStr=timeStr+String.format("%02d Hour ",hour);
        }
        if(timeStr.length()!=0 || minute!=0){
            timeStr=timeStr+String.format("%02d Minute ",minute);
        }
        if(timeStr.length()!=0 || minute!=0){
            timeStr=timeStr+String.format("%02d Second",second);
        }
        return timeStr;

    }

}
