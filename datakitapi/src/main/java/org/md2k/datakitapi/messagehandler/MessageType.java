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

package org.md2k.datakitapi.messagehandler;

/**
 * Defines integer value constants used to denote message types.
 *
 * <p>
 *     Message types are:
 *     <ul>
 *         <li><code>CONNECT</code></li>
 *         <li><code>DISCONNECT</code></li>
 *         <li><code>REGISTER</code></li>
 *         <li><code>UNREGISTER</code></li>
 *         <li><code>SUBSCRIBE</code></li>
 *         <li><code>INSERT</code></li>
 *         <li><code>QUERY</code></li>
 *         <li><code>FIND</code></li>
 *         <li><code>SUBSCRIBED_DATA</code></li>
 *         <li><code>INTERNAL_ERROR</code></li>
 *         <li><code>QUERYPRIMARYKEY</code></li>
 *         <li><code>INSERT_HIGH_FREQUENCY</code></li>
 *         <li><code>QUERYSIZE</code></li>
 *         <li><code>SUMMARY</code></li>
 *     </ul>
 * </p>
 */
public class MessageType {

    /** Default is 1 */
    public static final int CONNECT = 1;

    /** Default is 2 */
    public static final int DISCONNECT = 2;

    /** Default is 3 */
    public static final int REGISTER = 3;

    /** Default is 4 */
    public static final int UNREGISTER = 4;

    /** Default is 5 */
    public static final int SUBSCRIBE = 5;

    /** Default is 6 */
    public static final int UNSUBSCRIBE = 6;

    /** Default is 7 */
    public static final int INSERT = 7;

    /** Default is 8 */
    public static final int QUERY = 8;

    /** Default is 9 */
    public static final int FIND = 9;

    /** Default is 10 */
    public static final int SUBSCRIBED_DATA = 10;

    /** Default is 11 */
    public static final int INTERNAL_ERROR = 11;

    /** Default is 12 */
    public static final int QUERYPRIMARYKEY = 12;

    /** Default is 13 */
    public static final int INSERT_HIGH_FREQUENCY = 13;

    /** Default is 14 */
    public static final int QUERYSIZE = 14;

    /** Default is 15 */
    public static final int SUMMARY = 15;
}
