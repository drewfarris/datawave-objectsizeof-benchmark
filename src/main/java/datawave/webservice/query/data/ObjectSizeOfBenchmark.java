/*
 * Copyright (c) 2014, Oracle America, Inc.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *  * Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 *
 *  * Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the distribution.
 *
 *  * Neither the name of Oracle nor the names of its contributors may be used
 *    to endorse or promote products derived from this software without
 *    specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF
 * THE POSSIBILITY OF SUCH DAMAGE.
 */

package datawave.webservice.query.data;

import datawave.webservice.query.data.ObjectSizeOf;
import org.openjdk.jmh.annotations.Benchmark;

import java.util.ArrayList;
import java.util.List;

public class ObjectSizeOfBenchmark {

    @Benchmark
    public void testMethod() {
        List<Object> list = new ArrayList<Object>(10);
        list.add(new Long(1));
        list.add(new Double(1));
        int overhead = 8;
        int arrayoverhead = 12;
        int reference = 4;
        int intsize = 4;
        int numbersize = 16;
        long size = ObjectSizeOf.Sizer.roundUp(overhead + intsize + intsize + reference) + ObjectSizeOf.Sizer.roundUp(arrayoverhead + 10 * reference)
                + numbersize + numbersize;
        ObjectSizeOf.Sizer.getObjectSize(list);

        PrimitiveObject testPrimitive = new PrimitiveObject();
        size = numbersize;
        ObjectSizeOf.Sizer.getObjectSize(testPrimitive);

        ObjectSizeOf testSized = new SizedObject();
        size = ObjectSizeOf.Sizer.roundUp(testSized.sizeInBytes());
        ObjectSizeOf.Sizer.getObjectSize(testSized);

        RecursiveObject recursiveObject = new RecursiveObject();
        recursiveObject.o = recursiveObject;
        size = ObjectSizeOf.Sizer.roundUp(overhead + reference);
        ObjectSizeOf.Sizer.getObjectSize(recursiveObject);
    }

    public static class PrimitiveObject {
        private long value = 0;
    }

    public static class SizedObject implements ObjectSizeOf {
        @Override
        public long sizeInBytes() {
            return ObjectSizeOf.Sizer.roundUp(20);
        }

    }

    public static class RecursiveObject {
        public Object o;
    }
}
