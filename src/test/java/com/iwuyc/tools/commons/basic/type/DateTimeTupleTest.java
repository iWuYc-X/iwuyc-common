package com.iwuyc.tools.commons.basic.type;

import org.junit.Test;

import java.time.temporal.ChronoUnit;

public class DateTimeTupleTest {
    @Test
    public void test1() {
        DateTimeTuple tuple = DateTimeTuple.create(1, ChronoUnit.SECONDS);
        System.out.println(tuple);
    }
}