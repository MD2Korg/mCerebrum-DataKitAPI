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

package org.md2k.datakitapi;

/**
 * This class provides common constant string values used across the application.
 */
public class Constants {

    /**
     * Name of the service.
     * <p>
     *     <code>"org.md2k.datakit.ServiceDataKit"</code>
     * </p>
     */
    public static final String SERVICE_NAME = "org.md2k.datakit.ServiceDataKit";

    /**
     * Name of the package.
     * <p>
     *     <code>"org.md2k.datakit"</code>
     * </p>
     */
    public static final String PACKAGE_NAME = "org.md2k.datakit";

    /**
     * Data source identifier.
     * <p>
     *     <code>"ds_id"</code>
     * </p>
     */
    public static final String RC_DSID = "ds_id";

    /**
     * Starting timestamp
     * <p>
     *     <code>"starttimestamp"</code>
     * </p>
     */
    public static final String RC_STARTTIMESTAMP = "starttimestamp";

    /**
     * Ending timestamp
     * <p>
     *     <code>"endtimestamp"</code>
     * </p>
     */
    public static final String RC_ENDTIMESTAMP = "endtimestamp";

    /**
     * Last n samples synced to <code>DataKit</code>.
     * <p>
     *     <code>"last_n_sample"</code>
     * </p>
     */
    public static final String RC_LAST_N_SAMPLE="last_n_sample";

    /**
     * Number of rows to return from <code>DataKit</code>.
     * <p>
     *     <code>"limit"</code>
     * </p>
     */
    public static final String RC_LIMIT = "limit";

    /**
     * Last key synced to <code>DataKit</code>.
     * <p>
     *     <code>"last_key"</code>
     * </p>
     */
    public static final String RC_LAST_KEY="last_key";

    /**
     * Session identifier
     * <p>
     *     <code>"session_id"</code>
     * </p>
     */
    public static final String RC_SESSION_ID="session_id";

    /**
     * Data source client
     * <p>
     *     <code>"data_source_client"</code>
     * </p>
     */
    public static final String RC_DATASOURCE_CLIENT = "data_source_client";
}
