package org.md2k.datakitapi.datatype;

import junit.framework.TestCase;

/**
 * Copyright (c) 2015, The University of Memphis, MD2K Center
 * - Timothy Hnat <twhnat@memphis.edu>
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
public class DataTypeTest extends TestCase {

    public void testBooleanToJSON() throws Exception {
        DataTypeBoolean testcase = new DataTypeBoolean(12345L, true);
        String result = testcase.toJSON();
        String correctResult = "{\"sample\":true,\"dateTime\":12345}";
        assertEquals(result,correctResult);
    }

    public void testFloatToJSON() throws Exception {
        DataTypeFloat testcase = new DataTypeFloat(123456789L, 1.234f);
        String result = testcase.toJSON();
        String correctResult = "{\"sample\":1.234,\"dateTime\":123456789}";
        assertEquals(result,correctResult);
    }

    public void testIntToJSON() throws Exception {
        DataTypeInt testcase = new DataTypeInt(123456789L, 567);
        String result = testcase.toJSON();
        String correctResult = "{\"sample\":567,\"dateTime\":123456789}";
        assertEquals(result,correctResult);
    }

    public void testLongToJSON() throws Exception {
        DataTypeLong testcase = new DataTypeLong(123456789L, 987654321L);
        String result = testcase.toJSON();
        String correctResult = "{\"sample\":987654321,\"dateTime\":123456789}";
        assertEquals(result,correctResult);
    }

    public void testStringToJSON() throws Exception {
        DataTypeString testcase = new DataTypeString(123456789L, "This is a test");
        String result = testcase.toJSON();
        String correctResult = "{\"sample\":\"This is a test\",\"dateTime\":123456789}";
        assertEquals(result,correctResult);
    }

    public void testByteArrayToJSON() throws Exception {
        byte[] sample = new byte[]{1,2,3,4,5,6,7,8,9,10};
        DataTypeByteArray testcase = new DataTypeByteArray(123456789L, sample);
        String result = testcase.toJSON();
        String correctResult = "{\"sample\":[1,2,3,4,5,6,7,8,9,10],\"dateTime\":123456789}";
        assertEquals(result,correctResult);
    }

    public void testDoubleArrayToJSON() throws Exception {
        double[] sample = new double[]{1,2,3,4,5,6,7,8,9,10};
        DataTypeDoubleArray testcase = new DataTypeDoubleArray(123456789L, sample);
        String result = testcase.toJSON();
        String correctResult = "{\"sample\":[1.0,2.0,3.0,4.0,5.0,6.0,7.0,8.0,9.0,10.0],\"dateTime\":123456789}";
        assertEquals(result,correctResult);
    }

    public void testFloatArrayToJSON() throws Exception {
        float[] sample = new float[]{1,2,3,4,5,6,7,8,9,10};
        DataTypeFloatArray testcase = new DataTypeFloatArray(123456789L, sample);
        String result = testcase.toJSON();
        String correctResult = "{\"sample\":[1.0,2.0,3.0,4.0,5.0,6.0,7.0,8.0,9.0,10.0],\"dateTime\":123456789}";
        assertEquals(result,correctResult);
    }

    public void testIntArrayToJSON() throws Exception {
        int[] sample = new int[]{1,2,3,4,5,6,7,8,9,10};
        DataTypeIntArray testcase = new DataTypeIntArray(123456789L, sample);
        String result = testcase.toJSON();
        String correctResult = "{\"sample\":[1,2,3,4,5,6,7,8,9,10],\"dateTime\":123456789}";
        assertEquals(result,correctResult);
    }


}