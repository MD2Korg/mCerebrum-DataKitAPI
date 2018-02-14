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

package org.md2k.datakitapi.source;

/**
 * This class defines all of the metadata string constants.
 */
public class METADATA {

    /** Name of the data */
    public static final String NAME = "NAME";

    /** Description of what the data is. */
    public static final String DESCRIPTION = "DESCRIPTION";

    /** Minimum possible value of the data. */
    public static final String MIN_VALUE = "MIN_VALUE";

    /** Maximum possible value of the data. */
    public static final String MAX_VALUE = "MAX_VALUE";

    /** Sampling frequency of the data. */
    public static final String FREQUENCY = "FREQUENCY";

    /** The data's unit of measure. */
    public static final String UNIT = "UNIT";

    /** Data type of the data. */
    public static final String DATA_TYPE = "DATA_TYPE";

    /** Presentation format of the data. */
    public static final String DATA_FORMAT = "DATA_FORMAT";

    /** Operating system of the device used to collect the data. */
    public static final String OPERATING_SYSTEM = "OPERATING_SYSTEM";

    /** Manufacturer of the device used to collect the data. */
    public static final String MANUFACTURER = "MANUFACTURER";

    /** Model of the device used to collect the data. */
    public static final String MODEL = "MODEL";

    /** Operating system version name of the device used to collect the data. */
    public static final String VERSION_NAME="VERSION_NAME";

    /** Operating system version number of the device used to collect the data. */
    public static final String VERSION_NUMBER="VERSION_NUMBER";

    /** Firmware version of the device used to collect the data. */
    public static final String VERSION_FIRMWARE="VERSION_FIRMWARE";

    /** Version number of the hardware used to collect the data. */
    public static final String VERSION_HARDWARE="VERSION_HARDWARE";

    /** Identification number of the device used to collect the data. */
    public static final String DEVICE_ID="DEVICE_ID";

    /** Designates a descriptive statistic. */
    public static final String DESCRIPTIVESTATISTICS = "STATISTIC";
}
