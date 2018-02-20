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

package org.md2k.datakitapi.source.datasource;

/**
 * Provides constant values for indicating the type of the data source.
 */
public class DataSourceType {

    /** Accelerometer sensor <p><code>"ACCELEROMETER"</code></p> */
    public static final String ACCELEROMETER = "ACCELEROMETER";

    /** Gyroscope sensor <p><code>"GYROSCOPE"</code></p> */
    public static final String GYROSCOPE = "GYROSCOPE";

    /** Compass sensor <p><code>"COMPASS"</code></p> */
    public static final String COMPASS = "COMPASS";

    /** Ambient light sensor <p><code>"AMBIENT_LIGHT"</code></p> */
    public static final String AMBIENT_LIGHT = "AMBIENT_LIGHT";

    /** Pressure sensor <p><code>"PRESSURE"</code></p> */
    public static final String PRESSURE = "PRESSURE";

    /** Proximity sensor <p><code>"PROXIMITY"</code></p> */
    public static final String PROXIMITY = "PROXIMITY";

    /** Location sensor <p><code>"LOCATION"</code></p> */
    public static final String LOCATION = "LOCATION";

    /** Geofence <p><code>"GEOFENCE"</code></p> */
    public static final String GEOFENCE = "GEOFENCE";

    /** Distance sensor <p><code>"DISTANCE"</code></p> */
    public static final String DISTANCE = "DISTANCE";

    /** Heart rate sensor <p><code>"HEART_RATE"</code></p> */
    public static final String HEART_RATE = "HEART_RATE";

    /** Speed sensor <p><code>"SPEED"</code></p> */
    public static final String SPEED = "SPEED";

    /** Step count sensor <p><code>"STEP_COUNT"</code></p> */
    public static final String STEP_COUNT = "STEP_COUNT";

    /** Pace sensor <p><code>"PACE"</code></p> */
    public static final String PACE = "PACE";

    /** Motion type inference <p><code>"MOTION_TYPE"</code></p> */
    public static final String MOTION_TYPE = "MOTION_TYPE";

    /** Ultraviolet radiation sensor <p><code>"ULTRA_VIOLET_RADIATION"</code></p> */
    public static final String ULTRA_VIOLET_RADIATION = "ULTRA_VIOLET_RADIATION";

    /** Band contact sensor <p><code>"BAND_CONTACT"</code></p> */
    public static final String BAND_CONTACT = "BAND_CONTACT";

    /** Calorie burn sensor <p><code>"CALORIE_BURN"</code></p>  */
    // TODO: Fix spelling of calorie.
    public static final String CALORY_BURN = "CALORY_BURN";

    /** Electrocardiography sensor <p><code>"ECG"</code></p>*/
    public static final String ECG = "ECG";

    /** Respiration sensor <p><code>"RESPIRATION"</code></p> */
    public static final String RESPIRATION = "RESPIRATION";

    /** Respiration baseline <p><code>"RESPIRATION_BASELINE"</code></p> */
    public static final String RESPIRATION_BASELINE = "RESPIRATION_BASELINE";

    /** Respiration sensor (Raw measurement) <p><code>"RESPIRATION_RAW"</code></p> */
    public static final String RESPIRATION_RAW = "RESPIRATION_RAW";

    /** Altimeter sensor <p><code>"ALTIMETER"</code></p> */
    public static final String ALTIMETER = "ALTIMETER";

    /** Air pressure sensor <p><code>"AIR_PRESSURE"</code></p> */
    public static final String AIR_PRESSURE = "AIR_PRESSURE";

    /** Interval between R points on an ECG <p><code>"RR_INTERVAL"</code></p> */
    public static final String RR_INTERVAL = "RR_INTERVAL";


    /** Ambient temperature sensor <p><code>"AMBIENT_TEMPERATURE"</code></p> */
    public static final String AMBIENT_TEMPERATURE = "AMBIENT_TEMPERATURE";

    /** Skin temperature sensor <p><code>"SKIN_TEMPERATURE"</code></p> */
    public static final String SKIN_TEMPERATURE = "SKIN_TEMPERATURE";

    /** Battery usage <p><code>"BATTERY"</code></p> */
    public static final String BATTERY = "BATTERY";

    /** CPU usage <p><code>"CPU"</code></p> */
    public static final String CPU = "CPU";

    /** Memory usage <p><code>"MEMORY"</code></p> */
    public static final String MEMORY = "MEMORY";

    /** Autosense device <p><code>"AUTOSENSE"</code></p> */
    public static final String AUTOSENSE = "AUTOSENSE";

    /** X axis of the accelerometer <p><code>"ACCELEROMETER_X"</code></p> */
    public static final String ACCELEROMETER_X = "ACCELEROMETER_X";

    /** Y axis of the accelerometer <p><code>"ACCELEROMETER_Y"</code></p> */
    public static final String ACCELEROMETER_Y = "ACCELEROMETER_Y";

    /** Z axis of the accelerometer <p><code>"ACCELEROMETER_Z"</code></p> */
    public static final String ACCELEROMETER_Z = "ACCELEROMETER_Z";

    /** X axis of the gyroscope <p><code>"GYROSCOPE_X"</code></p> */
    public static final String GYROSCOPE_X = "GYROSCOPE_X";

    /** Y axis of the gyroscope <p><code>"GYROSCOPE_Y"</code></p> */
    public static final String GYROSCOPE_Y = "GYROSCOPE_Y";

    /** Z axis of the gyroscope <p><code>"GYROSCOPE_Z"</code></p> */
    public static final String GYROSCOPE_Z = "GYROSCOPE_Z";

    /** Ecological Momentary Assessment <p><code>"EMA"</code></p> */
    public static final String EMA = "EMA";

    /** Status <p><code>"STATUS"</code></p> */
    public static final String STATUS = "STATUS";

    /** Log <p><code>"LOG"</code></p> */
    public static final String LOG = "LOG";

    /** Timezone <p><code>"TIMEZONE"</code></p> */
    public static final String TIMEZONE = "TIMEZONE";

    /** Privacy <p><code>"PRIVACY"</code></p> */
    public static final String PRIVACY = "PRIVACY";

    /** Study information <p><code>"STUDY_INFO"</code></p> */
    public static final String STUDY_INFO = "STUDY_INFO";

    /** User information <p><code>"USER_INFO"</code></p> */
    public static final String USER_INFO = "USER_INFO";

    /** Sleep time <p><code>"SLEEP"</code></p> */
    public static final String SLEEP = "SLEEP";

    /** Wakeup time <p><code>"WAKEUP"</code></p> */
    public static final String WAKEUP = "WAKEUP";

    /** Day start <p><code>"DAY_START"</code></p> */
    public static final String DAY_START = "DAY_START";

    /** Day end <p><code>"DAY_END"</code></p> */
    public static final String DAY_END = "DAY_END";

    /** Probability of experiencing stress <p><code>"STRESS_PROBABILITY"</code></p> */
    public static final String STRESS_PROBABILITY = "STRESS_PROBABILITY";

    /** Stress label <p><code>"STRESS_LABEL"</code></p> */
    public static final String STRESS_LABEL = "STRESS_LABEL";

    /** Study start <p><code>"STUDY_START"</code></p> */
    public static final String STUDY_START = "STUDY_START";

    /** Study end <p><code>"STUDY_END"</code></p> */
    public static final String STUDY_END = "STUDY_END";

    /** Study activity <p><code>"STUDY_ACTIVITY"</code></p> */
    public static final String STRESS_ACTIVITY = "STRESS_ACTIVITY";

    /** Feature vector for cStress <p><code>"CSTRESS_FEATURE_VECTOR"</code></p> */
    public static final String CSTRESS_FEATURE_VECTOR = "CSTRESS_FEATURE_VECTOR";

    /** cStress data RIP quality <p><code>"ORG_MD2K_CSTRESS_DATA_RIP_QUALITY"</code></p> */
    public static final String ORG_MD2K_CSTRESS_DATA_RIP_QUALITY = "ORG_MD2K_CSTRESS_DATA_RIP_QUALITY";

    /** cStress data ECG quality <p><code>"ORG_MD2K_CSTRESS_DATA_ECG_QUALITY"</code></p> */
    public static final String ORG_MD2K_CSTRESS_DATA_ECG_QUALITY = "ORG_MD2K_CSTRESS_DATA_ECG_QUALITY";

    /** cStress episode classification <p><code>"ORG_MD2K_CSTRESS_EPISODE_CLASSIFICATON"</code></p> */
    public static final String ORG_MD2K_CSTRESS_STRESS_EPISODE_CLASSIFICATION
            = "ORG_MD2K_CSTRESS_STRESS_EPISODE_CLASSIFICATION";

    /** cStress episode array classification <p><code>"ORG_MD2K_CSTRESS_EPISODE_ARRAY_CLASSIFICATION_FULL_EPISODE"</code></p> */
    public static final String ORG_MD2K_CSTRESS_STRESS_EPISODE_ARRAY_CLASSIFICATION_FULL_EPISODE
            = "ORG_MD2K_CSTRESS_STRESS_EPISODE_ARRAY_CLASSIFICATION_FULL_EPISODE";

    /** cStress episode start <p><code>"ORG_MD2K_CSTRESS_EPISODE_START"</code></p> */
    public static final String ORG_MD2K_CSTRESS_STRESS_EPISODE_START = "ORG_MD2K_CSTRESS_STRESS_EPISODE_START";

    /** cStress episode peak <p><code>"ORG_MD2K_CSTRESS_EPISODE_PEAK"</code></p> */
    public static final String ORG_MD2K_CSTRESS_STRESS_EPISODE_PEAK = "ORG_MD2K_CSTRESS_STRESS_EPISODE_PEAK";

    /** cStress episode end <p><code>"ORG_MD2K_CSTRESS_EPISODE_END"</code></p> */
    public static final String ORG_MD2K_CSTRESS_STRESS_EPISODE_END = "ORG_MD2K_CSTRESS_STRESS_EPISODE_END";

    /** cStress feature vector RIP <p><code>"CSTRESS_FEATURE_VECTOR_RIP"</code></p> */
    public static final String CSTRESS_FEATURE_VECTOR_RIP = "CSTRESS_FEATURE_VECTOR_RIP";

    /** Stress RIP probability <p><code>"STRESS_RIP_PROBABILITY"</code></p> */
    public static final String STRESS_RIP_PROBABILITY = "STRESS_RIP_PROBABILITY";

    /** Stress RIP label <p><code>"STRESS_RIP_LABEL"</code></p> */
    public static final String STRESS_RIP_LABEL = "STRESS_RIP_LABEL";

    /** Accelerometer activity <p><code>"ACCELEROMETER_ACTIVITY_DEVIATION"</code></p> */
    public static final String ACTIVITY = "ACCELEROMETER_ACTIVITY_DEVIATION";

    /** Probability of the user taking a puff <p><code>"PUFF_PROBABILITY"</code></p> */
    public static final String PUFF_PROBABILITY = "PUFF_PROBABILITY";

    /** Puff Label <p><code>"PUFF_LABEL"</code></p> */
    public static final String PUFF_LABEL = "PUFF_LABEL";

    /** Puffmarker feature vector <p><code>"PUFFMARKER_FEATURE_VECTOR"</code></p> */
    public static final String PUFFMARKER_FEATURE_VECTOR = "PUFFMARKER_FEATURE_VECTOR";

    /** Puffmarker smoking episode <p><code>"PUFFMARKER_SMOKING_EPISODE"</code></p> */
    public static final String PUFFMARKER_SMOKING_EPISODE = "PUFFMARKER_SMOKING_EPISODE";


    /** Request of a notification <p><code>"NOTIFICATION_REQUEST"</code></p> */
    public static final String NOTIFICATION_REQUEST = "NOTIFICATION_REQUEST";

    /** Acknowledgement of a notification <p><code>"NOTIFICATION_ACKNOWLEDGE"</code></p> */
    public static final String NOTIFICATION_ACKNOWLEDGE = "NOTIFICATION_ACKNOWLEDGE";

    /** Notification response <p><code>"NOTIFICATION_RESPONSE"</code></p> */
    public static final String NOTIFICATION_RESPONSE = "NOTIFICATION_RESPONSE";

    /** Data quality <p><code>"DATA_QUALITY"</code></p> */
    public static final String DATA_QUALITY = "DATA_QUALITY";

    /** Data variance <p><code>"DATA_VARIANCE"</code></p> */
    public static final String DATA_VARIANCE = "DATA_VARIANCE";


    /** Type of day <p><code>"TYPE_OF_DAY"</code></p> */
    public static final String TYPE_OF_DAY = "TYPE_OF_DAY";

    /** Event <p><code>"EVENT"</code></p> */
    public static final String EVENT = "EVENT";

    /** Incentive <p><code>"INCENTIVE"</code></p> */
    public static final String INCENTIVE = "INCENTIVE";


    /** Blood pressure <p><code>"BLOOD_PRESSURE"</code></p> */
    public static final String BLOOD_PRESSURE = "BLOOD_PRESSURE";

    /** Weight <p><code>"WEIGHT"</code></p> */
    public static final String WEIGHT = "WEIGHT";


    /** Brushing pressure <p><code>"ORALB_PRESSURE"</code></p> */
    public static final String ORALB_PRESSURE = "ORALB_PRESSURE";

    /** Connection to the toothbrush <p><code>"ORALB_CONNECTION"</code></p> */
    public static final String ORALB_CONNECTION = "ORALB_CONNECTION";

    /** Brushing sector <p><code>"ORALB_SECTOR"</code></p> */
    public static final String ORALB_SECTOR = "ORALB_SECTOR";

    /** Brushing mode <p><code>"ORALB_BURSHING_MODE"</code></p> */
    public static final String ORALB_BRUSHING_MODE = "ORALB_BRUSHING_MODE";

    /** Brushing state <p><code>"ORALB_BRUSHING_STATE"</code></p> */
    public static final String ORALB_BRUSHING_STATE = "ORALB_BRUSHING_STATE";

    /** Time spent brushing <p><code>"ORALB_BRUSHING_TIME"</code></p> */
    public static final String ORALB_BUSHING_TIME = "ORALB_BUSHING_TIME";


    /** Activity type <p><code>"ACTIVITY_TYPE"</code></p> */
    public static final String ACTIVITY_TYPE = "ACTIVITY_TYPE";

    /** LED <p><code>"LED"</code></p> */
    public static final String LED = "LED";

    /** Sequence number <p><code>"SEQUENCE_NUMBER"</code></p> */
    public static final String SEQUENCE_NUMBER = "SEQUENCE_NUMBER";


    /** Smoking sensor <p><code>"SMOKING"</code></p> */
    public static final String SMOKING = "SMOKING";

    /** Raw <p><code>"RAW"</code></p> */
    public static final String RAW = "RAW";

    /** Post_quit <p><code>"POST_QUIT"</code></p> */
    public static final String POST_QUIT = "POST_QUIT";

    /** Pre-quit <p><code>"PRE_QUIT"</code></p> */
    public static final String PRE_QUIT = "PRE_QUIT";

    /** Beacon <p><code>"BEACON"</code></p> */
    public static final String BEACON = "BEACON";

    /** Data quality summary for the minute <p><code>"DATA_QUALITY_SUMMARY_MINUTE"</code></p> */
    public static final String DATA_QUALITY_SUMMARY_MINUTE = "DATA_QUALITY_SUMMARY_MINUTE";

    /** Data quality summary for the hour <p><code>"DATA_QUALITY_SUMMARY_HOUR"</code></p> */
    public static final String DATA_QUALITY_SUMMARY_HOUR = "DATA_QUALITY_SUMMARY_HOUR";

    /** Data quality summary for the day <p><code>"DATA_QUALITY_SUMMARY_DAY"</code></p> */
    public static final String DATA_QUALITY_SUMMARY_DAY = "DATA_QUALITY_SUMMARY_DAY";

    /** Galvanic skin response sensor <p><code>"GALVANIC_SKIN_RESPONSE"</code></p> */
    public static final String GALVANIC_SKIN_RESPONSE = "GALVANIC_SKIN_RESPONSE";

    /** Touchscreen sensor <p><code>"TOUCH_SCREEN"</code></p> */
    public static final String TOUCH_SCREEN = "TOUCH_SCREEN";

    /** Work annotation <p><code>"WORK_ANNOTATION"</code></p> */
    public static final String WORK_ANNOTATION = "WORK_ANNOTATION";



    /** Is screen on <p><code>"CU_IS_SCREEN_ON"</code></p><p> For data integration from Cornell </p> */
    public static final String CU_IS_SCREEN_ON = "CU_IS_SCREEN_ON";

    /** Audio feature <p><code>"CU_AUDIO_FEATURE"</code></p><p> For data integration from Cornell </p> */
    public static final String CU_AUDIO_FEATURE = "CU_AUDIO_FEATURE";

    /** Audio energy <p><code>"CU_AUDIO_ENERGY"</code></p><p> For data integration from Cornell </p> */
    public static final String CU_AUDIO_ENERGY = "CU_AUDIO_ENERGY";

    /** Audio inference <p><code>"CU_AUDIO_INFERENCE"</code></p><p> For data integration from Cornell </p> */
    public static final String CU_AUDIO_INFERENCE = "CU_AUDIO_INFERENCE";


    /** App usage <p><code>"CU_APPUSAGE"</code></p><p> For data integration from Cornell </p> */
    public static final String CU_APPUSAGE = "CU_APPUSAGE";


    /** SMS number <p><code>"CU_SMS_NUMBER"</code></p><p> For data integration from Cornell </p> */
    public static final String CU_SMS_NUMBER = "CU_SMS_NUMBER";

    /** SMS type <p><code>"CU_SMS_NUMBER"</code></p><p> For data integration from Cornell </p> */
    public static final String CU_SMS_TYPE = "CU_SMS_NUMBER";

    /** SMS length <p><code>"CU_SMS_LENGTH"</code></p><p> For data integration from Cornell </p> */
    public static final String CU_SMS_LENGTH = "CU_SMS_LENGTH";


    /** Call number <p><code>"CU_CALL_NUMBER"</code></p><p> For data integration from Cornell </p> */
    public static final String CU_CALL_NUMBER = "CU_CALL_NUMBER";

    /** Call type <p><code>"CU_CALL_TYPE"</code></p><p> For data integration from Cornell </p> */
    public static final String CU_CALL_TYPE = "CU_CALL_TYPE";

    /** Call duration <p><code>"CU_CALL_DURATION"</code></p><p> For data integration from Cornell </p> */
    public static final String CU_CALL_DURATION = "CU_CALL_DURATION";

    /** Label <p><code>"LABEL</code></p><p> For data integration from Cornell </p> */
    public static String LABEL = "LABEL";

    public static final String MAGNETOMETER = "MAGNETOMETER";
    public static final String QUATERNION = "QUATERNION";
    public static final String MAGNETOMETER_SENSITIVITY = "MAGNETOMETER_SENSITIVITY";
    public static final String ORALB_BRUSHING_TIME = "ORALB_BRUSHING_TIME";
    public static final String ORALB_BRUSH_ACCELEROMETER = "ORALB_BRUSH_ACCELEROMETER";
}
