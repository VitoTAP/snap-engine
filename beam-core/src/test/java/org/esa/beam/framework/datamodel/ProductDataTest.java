/*
 * $Id: ProductDataTest.java,v 1.1.1.1 2006/09/11 08:16:51 norman Exp $
 *
 * Copyright (C) 2002 by Brockmann Consult (info@brockmann-consult.de)
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by the
 * Free Software Foundation. This program is distributed in the hope it will
 * be useful, but WITHOUT ANY WARRANTY; without even the implied warranty
 * of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
package org.esa.beam.framework.datamodel;

import junit.framework.TestCase;


public class ProductDataTest extends TestCase {

    public ProductDataTest(String name) {
        super(name);
    }

    public void testGetElemSizeInBytes() {
        assertEquals(1, ProductData.getElemSize(ProductData.TYPE_ASCII));
        assertEquals(4, ProductData.getElemSize(ProductData.TYPE_FLOAT32));
        assertEquals(8, ProductData.getElemSize(ProductData.TYPE_FLOAT64));
        assertEquals(2, ProductData.getElemSize(ProductData.TYPE_INT16));
        assertEquals(4, ProductData.getElemSize(ProductData.TYPE_INT32));
        assertEquals(1, ProductData.getElemSize(ProductData.TYPE_INT8));
        assertEquals(2, ProductData.getElemSize(ProductData.TYPE_UINT16));
        assertEquals(4, ProductData.getElemSize(ProductData.TYPE_UINT32));
        assertEquals(1, ProductData.getElemSize(ProductData.TYPE_UINT8));
        assertEquals(4, ProductData.getElemSize(ProductData.TYPE_UTC));

        int unsupportedDataType =
                ProductData.TYPE_ASCII + ProductData.TYPE_FLOAT32 +
                ProductData.TYPE_FLOAT64 + ProductData.TYPE_INT16 +
                ProductData.TYPE_INT32 + ProductData.TYPE_INT8 +
                ProductData.TYPE_UINT16 + ProductData.TYPE_UINT32 +
                ProductData.TYPE_UINT8 + ProductData.TYPE_UTC;
        try {
            ProductData.getElemSize(unsupportedDataType);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
        }
    }

    public void testIsFloatingPointType() {
        assertEquals(false, ProductData.isFloatingPointType(ProductData.TYPE_ASCII));
        assertEquals(true, ProductData.isFloatingPointType(ProductData.TYPE_FLOAT32));
        assertEquals(true, ProductData.isFloatingPointType(ProductData.TYPE_FLOAT64));
        assertEquals(false, ProductData.isFloatingPointType(ProductData.TYPE_INT16));
        assertEquals(false, ProductData.isFloatingPointType(ProductData.TYPE_INT32));
        assertEquals(false, ProductData.isFloatingPointType(ProductData.TYPE_INT8));
        assertEquals(false, ProductData.isFloatingPointType(ProductData.TYPE_UINT16));
        assertEquals(false, ProductData.isFloatingPointType(ProductData.TYPE_UINT32));
        assertEquals(false, ProductData.isFloatingPointType(ProductData.TYPE_UINT8));
        assertEquals(false, ProductData.isFloatingPointType(ProductData.TYPE_UTC));
    }

    public void testIsIntType() {
        assertEquals(false, ProductData.isIntType(ProductData.TYPE_ASCII));
        assertEquals(false, ProductData.isIntType(ProductData.TYPE_FLOAT32));
        assertEquals(false, ProductData.isIntType(ProductData.TYPE_FLOAT64));
        assertEquals(true, ProductData.isIntType(ProductData.TYPE_INT16));
        assertEquals(true, ProductData.isIntType(ProductData.TYPE_INT32));
        assertEquals(true, ProductData.isIntType(ProductData.TYPE_INT8));
        assertEquals(true, ProductData.isIntType(ProductData.TYPE_UINT16));
        assertEquals(true, ProductData.isIntType(ProductData.TYPE_UINT32));
        assertEquals(true, ProductData.isIntType(ProductData.TYPE_UINT8));
        assertEquals(false, ProductData.isIntType(ProductData.TYPE_UTC));
    }

    public void testIsUIntType() {
        assertEquals(false, ProductData.isUIntType(ProductData.TYPE_ASCII));
        assertEquals(false, ProductData.isUIntType(ProductData.TYPE_FLOAT32));
        assertEquals(false, ProductData.isUIntType(ProductData.TYPE_FLOAT64));
        assertEquals(false, ProductData.isUIntType(ProductData.TYPE_INT16));
        assertEquals(false, ProductData.isUIntType(ProductData.TYPE_INT32));
        assertEquals(false, ProductData.isUIntType(ProductData.TYPE_INT8));
        assertEquals(true, ProductData.isUIntType(ProductData.TYPE_UINT16));
        assertEquals(true, ProductData.isUIntType(ProductData.TYPE_UINT32));
        assertEquals(true, ProductData.isUIntType(ProductData.TYPE_UINT8));
        assertEquals(false, ProductData.isUIntType(ProductData.TYPE_UTC));
    }

    public void testStaticGetType() {
        assertEquals(ProductData.TYPE_ASCII, ProductData.getType(ProductData.TYPESTRING_ASCII));
        assertEquals(ProductData.TYPE_FLOAT32, ProductData.getType(ProductData.TYPESTRING_FLOAT32));
        assertEquals(ProductData.TYPE_FLOAT64, ProductData.getType(ProductData.TYPESTRING_FLOAT64));
        assertEquals(ProductData.TYPE_INT16, ProductData.getType(ProductData.TYPESTRING_INT16));
        assertEquals(ProductData.TYPE_INT32, ProductData.getType(ProductData.TYPESTRING_INT32));
        assertEquals(ProductData.TYPE_INT8, ProductData.getType(ProductData.TYPESTRING_INT8));
        assertEquals(ProductData.TYPE_UINT16, ProductData.getType(ProductData.TYPESTRING_UINT16));
        assertEquals(ProductData.TYPE_UINT32, ProductData.getType(ProductData.TYPESTRING_UINT32));
        assertEquals(ProductData.TYPE_UINT8, ProductData.getType(ProductData.TYPESTRING_UINT8));
        assertEquals(ProductData.TYPE_UTC, ProductData.getType(ProductData.TYPESTRING_UTC));
        assertEquals(ProductData.TYPE_UNDEFINED, ProductData.getType("any other string"));
    }


    public void testUInt16() {
        final ProductData data = ProductData.createInstance(ProductData.TYPE_UINT16, 1);
        long testValue;
        long trueValue;

        testValue = Short.MAX_VALUE + 3;
        data.setElemUInt(testValue);
        trueValue = data.getElemUInt();
        assertEquals(testValue, trueValue);

        testValue = Short.MAX_VALUE - 3;
        data.setElemUInt(testValue);
        trueValue = data.getElemUInt();
        assertEquals(testValue, trueValue);
    }

    public void testUInt32() {
        final ProductData data = ProductData.createInstance(ProductData.TYPE_UINT32, 1);
        long testValue, trueValue;

        testValue = Integer.MAX_VALUE + 3L;
        data.setElemUInt(testValue);
        trueValue = data.getElemUInt();
        assertEquals(testValue, trueValue);

        testValue = Integer.MAX_VALUE - 3L;
        data.setElemUInt(testValue);
        trueValue = data.getElemUInt();
        assertEquals(testValue, trueValue);
    }

    public void testUInt32Array() {

        final long intMax = Integer.MAX_VALUE;

        final ProductData data = ProductData.createInstance(new long[]{
            1L,
            intMax / 2,
            intMax + 1L,
            intMax + intMax / 2,
        });

        assertEquals(ProductData.TYPE_UINT32, data.getType());
        assertEquals(1L, data.getElemUIntAt(0));
        assertEquals(intMax / 2, data.getElemUIntAt(1));
        assertEquals(intMax + 1L, data.getElemUIntAt(2));
        assertEquals(intMax + intMax / 2, data.getElemUIntAt(3));
    }

    // @todo 1 nf/** - add tests for Byte, Short,, Int, etc
}
