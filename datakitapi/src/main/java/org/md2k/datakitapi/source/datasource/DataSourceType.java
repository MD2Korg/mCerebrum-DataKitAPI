package org.md2k.datakitapi.source.datasource;

/*
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
public class DataSourceType {
    public static final String ACCELEROMETER = "ACCELEROMETER";
    public static final String GYROSCOPE = "GYROSCOPE";
    public static final String COMPASS = "COMPASS";
    public static final String AMBIENT_LIGHT = "AMBIENT_LIGHT";
    public static final String PRESSURE = "PRESSURE";
    public static final String PROXIMITY = "PROXIMITY";
    public static final String LOCATION = "LOCATION";
    public static final String GEOFENCE = "GEOFENCE";

    public static final String DISTANCE = "DISTANCE";
    public static final String HEART_RATE = "HEART_RATE";
    public static final String SPEED = "SPEED";
    public static final String STEP_COUNT = "STEP_COUNT";
    public static final String PACE = "PACE";
    public static final String MOTION_TYPE = "MOTION_TYPE";
    public static final String ULTRA_VIOLET_RADIATION = "ULTRA_VIOLET_RADIATION";
    public static final String BAND_CONTACT = "BAND_CONTACT";
    public static final String CALORY_BURN = "CALORY_BURN";
    public static final String ECG = "ECG";
    public static final String RESPIRATION = "RESPIRATION";
    public static final String RESPIRATION_BASELINE = "RESPIRATION_BASELINE";
    public static final String RESPIRATION_RAW = "RESPIRATION_RAW";
    public static final String ALTIMETER = "ALTIMETER";
    public static final String AIR_PRESSURE = "AIR_PRESSURE";
    public static final String RR_INTERVAL = "RR_INTERVAL";

    public static final String AMBIENT_TEMPERATURE = "AMBIENT_TEMPERATURE";
    public static final String SKIN_TEMPERATURE = "SKIN_TEMPERATURE";
    public static final String BATTERY = "BATTERY";
    public static final String CPU = "CPU";
    public static final String MEMORY = "MEMORY";
    public static final String AUTOSENSE = "AUTOSENSE";
    public static final String ACCELEROMETER_X = "ACCELEROMETER_X";
    public static final String ACCELEROMETER_Y = "ACCELEROMETER_Y";
    public static final String ACCELEROMETER_Z = "ACCELEROMETER_Z";
    public static final String GYROSCOPE_X = "GYROSCOPE_X";
    public static final String GYROSCOPE_Y = "GYROSCOPE_Y";
    public static final String GYROSCOPE_Z = "GYROSCOPE_Z";

    public static final String EMA = "EMA";
    public static final String STATUS = "STATUS";
    public static final String LOG="LOG";

    public static final String TIMEZONE = "TIMEZONE";
    public static final String PRIVACY = "PRIVACY";

    public static final String STUDY_INFO = "STUDY_INFO";
    public static final String USER_INFO = "USER_INFO";
    public static final String SLEEP = "SLEEP";
    public static final String WAKEUP = "WAKEUP";
    public static final String DAY_START="DAY_START";
    public static final String DAY_END="DAY_END";
    public static final String STRESS_PROBABILITY="STRESS_PROBABILITY";
    public static final String STRESS_LABEL="STRESS_LABEL";
    public static final String STUDY_START="STUDY_START";
    public static final String STUDY_END="STUDY_END";
    public static final String STRESS_ACTIVITY = "STRESS_ACTIVITY";
    public static final String CSTRESS_FEATURE_VECTOR = "CSTRESS_FEATURE_VECTOR";
    public static final String ORG_MD2K_CSTRESS_DATA_RIP_QUALITY = "ORG_MD2K_CSTRESS_DATA_RIP_QUALITY";
    public static final String ORG_MD2K_CSTRESS_DATA_ECG_QUALITY = "ORG_MD2K_CSTRESS_DATA_ECG_QUALITY";
    public static final String ORG_MD2K_CSTRESS_STRESS_EPISODE_CLASSIFICATION = "ORG_MD2K_CSTRESS_STRESS_EPISODE_CLASSIFICATION";
    public static final String ORG_MD2K_CSTRESS_STRESS_EPISODE_ARRAY_CLASSIFICATION_FULL_EPISODE="ORG_MD2K_CSTRESS_STRESS_EPISODE_ARRAY_CLASSIFICATION_FULL_EPISODE";
    public static final String ORG_MD2K_CSTRESS_STRESS_EPISODE_START="ORG_MD2K_CSTRESS_STRESS_EPISODE_START";
    public static final String ORG_MD2K_CSTRESS_STRESS_EPISODE_PEAK="ORG_MD2K_CSTRESS_STRESS_EPISODE_PEAK";
    public static final String ORG_MD2K_CSTRESS_STRESS_EPISODE_END="ORG_MD2K_CSTRESS_STRESS_EPISODE_END";
    public static final String CSTRESS_FEATURE_VECTOR_RIP = "CSTRESS_FEATURE_VECTOR_RIP";
    public static final String STRESS_RIP_PROBABILITY = "STRESS_RIP_PROBABILITY";
    public static final String STRESS_RIP_LABEL = "STRESS_RIP_LABEL";
    public static final String ACTIVITY = "ACCELEROMETER_ACTIVITY_DEVIATION";
    public static final String PUFF_PROBABILITY = "PUFF_PROBABILITY";
    public static final String PUFF_LABEL = "PUFF_LABEL";
    public static final String PUFFMARKER_FEATURE_VECTOR = "PUFFMARKER_FEATURE_VECTOR";
    public static final String PUFFMARKER_SMOKING_EPISODE = "PUFFMARKER_SMOKING_EPISODE";

    public static final String NOTIFICATION_REQUEST = "NOTIFICATION_REQUEST";
    public static final String NOTIFICATION_ACKNOWLEDGE = "NOTIFICATION_ACKNOWLEDGE";
    public static final String NOTIFICATION_RESPONSE = "NOTIFICATION_RESPONSE";
    public static final String DATA_QUALITY="DATA_QUALITY";
    public static final String DATA_VARIANCE="DATA_VARIANCE";


    public static final String TYPE_OF_DAY="TYPE_OF_DAY";
    public static final String EVENT="EVENT";
    public static final String INCENTIVE="INCENTIVE";

    public static final String BLOOD_PRESSURE="BLOOD_PRESSURE";
    public static final String WEIGHT="WEIGHT";

    public static final String ORALB_PRESSURE="ORALB_PRESSURE";
    public static final String ORALB_CONNECTION="ORALB_CONNECTION";
    public static final String ORALB_SECTOR="ORALB_SECTOR";
    public static final String ORALB_BRUSHING_MODE="ORALB_BRUSHING_MODE";
    public static final String ORALB_BRUSHING_STATE="ORALB_BRUSHING_STATE";
    public static final String ORALB_BUSHING_TIME="ORALB_BUSHING_TIME";

    public static final String ACTIVITY_TYPE="ACTIVITY_TYPE";
    public static final String LED="LED";
    public static final String SEQUENCE_NUMBER="SEQUENCE_NUMBER";

    public static final String SMOKING = "SMOKING";
    public static final String RAW = "RAW";
    public static final String POST_QUIT = "POST_QUIT";
    public static final String PRE_QUIT = "PRE_QUIT";
    public static final String BEACON = "BEACON";
    public static final String DATA_QUALITY_SUMMARY_MINUTE="DATA_QUALITY_SUMMARY_MINUTE";
    public static final String DATA_QUALITY_SUMMARY_HOUR="DATA_QUALITY_SUMMARY_HOUR";
    public static final String DATA_QUALITY_SUMMARY_DAY="DATA_QUALITY_SUMMARY_DAY";
    public static final String GALVANIC_SKIN_RESPONSE = "GALVANIC_SKIN_RESPONSE";
    public static final String TOUCH_SCREEN="TOUCH_SCREEN";
    public static final String WORK_ANNOTATION="WORK_ANNOTATION";
}
