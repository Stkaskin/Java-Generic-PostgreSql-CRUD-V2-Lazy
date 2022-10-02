package com.asstnavi.generic.LazyPack;

import com.asstnavi.generic.Enum.SortOrder;
import com.asstnavi.generic.Manager.PropertyManager;

import java.util.Map;

public class Sorter
{
    public String setOrderBy(Map<String, SortOrder> sortBy)
    {
        final String[] temp = {" "};
        temp[0] = " ";
        if (!sortBy.isEmpty())
        {            System.out.println("Sorder 1");
            sortBy.forEach((key, value) -> {
                temp[0] = " " + key + " " + (value == SortOrder.ASCENDING ? PropertyManager.ASCENDING : PropertyManager.DESCENDING);
            });      System.out.println("Sorder 2");
            temp[0] = " ORDER by " + temp[0];
        }
        System.out.println("Sorder 3");
        return temp[0];
    }
}
