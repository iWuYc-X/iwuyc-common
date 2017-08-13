package com.iwuyc.tools.commons.math;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;

import com.iwuyc.tools.commons.basic.StringUtils;

public class Range
{
    private static class BoundaryNumber extends BigDecimal
    {

        private static final long serialVersionUID = 2910914454149571890L;
        private boolean isMax;
        private String name;

        public BoundaryNumber(String val, boolean isMax)
        {
            super("0");
            this.name = val;
            this.isMax = isMax;
        }

        @Override
        public int compareTo(BigDecimal val)
        {
            if (isMax)
            {
                return 1;
            }
            return -1;
        }

        @Override
        public String toString()
        {
            return "BoundaryNumber [name=" + name + "]";
        }

    }

    private static class RangeItem
    {

        private BigDecimal min;
        private BigDecimal max;

        private byte flag = 0;

        @Override
        public String toString()
        {
            return "Range [min=" + min + ", max=" + max + ", flag=" + flag + "]";
        }

        private boolean judg(BigDecimal number)
        {
            int compareMin = this.min.compareTo(number);
            if (compareMin > 0)
            {
                return false;
            }
            else if (0 == compareMin)
            {
                return 0 != (this.flag & CONTAIN_LEAF);
            }
            int compareMax = this.max.compareTo(number);
            if (compareMax < 0)
            {
                return false;
            }
            else if (0 == compareMax)
            {
                return 0 != (this.flag & CONTAIN_RIGHT);
            }
            return true;
        }
    }

    private final static byte CONTAIN_LEAF = 2;// 10
    private final static byte CONTAIN_RIGHT = 1;// 01
    private static final BigDecimal MAX = new BoundaryNumber("max", true);
    private static final BigDecimal MIN = new BoundaryNumber("min", false);

    public static Range compiler(String rangeStr)
    {
        Range rootRange = new Range();
        String[] rangeStrArr = rangeStr.split("[|]{1}");
        RangeItem rangeItem = null;
        for (String rangeStrItem : rangeStrArr)
        {
            rangeStrItem = rangeStrItem.trim();
            if (StringUtils.isEmpty(rangeStrItem))
            {
                continue;
            }
            rangeItem = itemCompiler(rangeStrItem);
            rootRange.ranges.add(rangeItem);
        }
        return rootRange;
    }

    private Collection<RangeItem> ranges = new ArrayList<>();

    private static RangeItem itemCompiler(String rangeStr)
    {
        RangeItem range = new RangeItem();

        StringBuilder sb = new StringBuilder(rangeStr);

        int firstStartIndex = sb.indexOf("[");
        if (firstStartIndex < 0)
        {
            firstStartIndex = sb.indexOf("(");
        }
        else
        {
            range.flag |= Range.CONTAIN_LEAF;
        }

        int splitFlagIndex = sb.indexOf(",");

        int secondEndIndex = sb.indexOf("]");
        if (secondEndIndex < 0)
        {
            secondEndIndex = sb.indexOf(")");
        }
        else
        {
            range.flag |= Range.CONTAIN_RIGHT;
        }

        String firstNumStr = sb.substring(firstStartIndex + 1, splitFlagIndex).trim();
        String secondNumStr = sb.substring(splitFlagIndex + 1, secondEndIndex).trim();

        range.min = builderBigDecimal(firstNumStr);
        range.max = builderBigDecimal(secondNumStr);
        return range;
    }

    private static BigDecimal builderBigDecimal(String numStr)
    {
        if ("max".equals(numStr))
        {
            return MAX;
        }
        else if ("min".equals(numStr))
        {
            return MIN;
        }
        return new BigDecimal(numStr);
    }

    /**
     * 判断一个数字是否在范围内。
     * 
     * @param num
     *            待判断的数字
     * @return 如果在范围内，则返回true，否则返回false。
     */
    public boolean inRange(Number num)
    {
        BigDecimal number = new BigDecimal(String.valueOf(num));
        return this.inRange(number);
    }

    /**
     * 判断一个数字是否在范围内。
     * 
     * @param number
     *            待判断的数字
     * @return 如果在范围内，则返回true，否则返回false。
     */
    public boolean inRange(BigDecimal number)
    {
        for (RangeItem range : ranges)
        {
            if (range.judg(number))
            {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString()
    {
        return "Range [ranges=" + ranges + "]";
    }
}
