package com.asstnavi.generic.Enum;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public enum SortOrder
{
    ASCENDING(1, new Object[]{"asc", "1", "ascending", "ASCENDING", "ASC"}),
    DESCENDING(-1, new Object[]{"desc", "-1", "descending", "DESCENDING", "DESC"}),
    UNSORTED(0, new Object[]{"", null, "0", "unsorted", "UNSORTED"});

    private final Set<Object> values;
    private final Integer intValue;

    private SortOrder(int intValue, Object... values)
    {
        this.intValue = intValue;
        this.values = new HashSet(Arrays.asList(values));
    }

    public static SortOrder of(Object order)
    {
        SortOrder[] var1 = values();
        int var2 = var1.length;

        for (int var3 = 0; var3 < var2; ++var3)
        {
            SortOrder o = var1[var3];
            if (o.intValue.equals(order) || o.values.contains(order))
            {
                return o;
            }
        }

        throw new IllegalArgumentException("No SorderOrder matching value: " + order);
    }

    public boolean isAscending()
    {
        return this == ASCENDING;
    }

    public boolean isDescending()
    {
        return this == DESCENDING;
    }

    public boolean isUnsorted()
    {
        return this == UNSORTED;
    }

    public int intValue()
    {
        return this.intValue;
    }
}