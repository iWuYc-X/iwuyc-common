package com.iwuyc.tools.commons.util;

import java.math.BigInteger;

import org.junit.Test;

public class NumberUtilTest
{

    @Test
    public void testBigInteger()
    {
        BigInteger number = NumberUtil.parse("11111", BigInteger.class);
        System.out.println(number);
    }

    @Test
    public void testint()
    {
        int intNumber = NumberUtil.parse("11111", int.class);
        System.out.println(intNumber);

        Integer integerNumber = NumberUtil.parse("11111", Integer.class);
        System.out.println(integerNumber);
    }

    @Test
    public void testLong()
    {
        long longNumber = NumberUtil.parse("11111", long.class);
        System.out.println(longNumber);

        Long LongNumber = NumberUtil.parse("11111", Long.class);
        System.out.println(LongNumber);
    }

    @Test
    public void testByte()
    {
        byte byteNumber = NumberUtil.parse("111", byte.class);
        System.out.println(byteNumber);

        Byte ByteNumber = NumberUtil.parse("111", Byte.class);
        System.out.println(ByteNumber);
    }
}
