package com.iwuyc.tools.commons.util.collection;

/**
 * @author @Neil
 * @since @2017年10月15日
 */
public abstract class ArrayUtil {

    public static <T> boolean isEmpty(T[] arrays) {
        return arrayLength(arrays) == 0;
    }

    public static <T> boolean isNotEmpty(T[] arrays) {
        return !isEmpty(arrays);
    }

    public static boolean isEmpty(int[] arrays) {
        return null == arrays || 0 == arrays.length;
    }

    public static boolean isNotEmpty(int[] arrays) {
        return !isEmpty(arrays);
    }

    public static <T> int arrayLength(T[] arrays) {
        return null == arrays ? 0 : arrays.length;
    }
}
