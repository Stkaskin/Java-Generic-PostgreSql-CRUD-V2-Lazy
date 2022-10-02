package com.asstnavi.generic.LazyPack;

import com.asstnavi.generic.Enum.SortOrder;
import com.asstnavi.generic.LazyPack.Filter;
import com.asstnavi.generic.LazyPack.FilterModel;
import com.asstnavi.generic.LazyPack.LazyToSql;
import com.asstnavi.generic.LazyPack.Sorter;
import com.asstnavi.generic.Manager.DbManager;
import com.asstnavi.generic.Manager.PropertyManager;


import java.util.List;
import java.util.Map;

public class LazyService
{
    boolean filter = false;
    boolean filterGlobal = false;

    public boolean isFilter()
    {
        return filter;
    }

    public void setFilter(boolean filter)
    {
        this.filter = filter;
    }

    public boolean isFilterGlobal()
    {
        return filterGlobal;
    }

    public void setFilterGlobal(boolean filterGlobal)
    {
        this.filterGlobal = filterGlobal;
    }

    public <T> List<T> FilterOperation(DbManager manager, int first, int pageSize, Map<String, SortOrder> sortBy, Map<String, FilterModel> filterBy, String globalFilter)
    {
        if ((sortBy.isEmpty() && filterBy.isEmpty()))
            return manager.GetLazy(first, pageSize);

        String globalFilterCommand = " ";
        String sortTemp = " ";
        String filterTemp = " ";
        String command = " ";
        System.out.println("Sorder Başladı");
        sortTemp = new Sorter().setOrderBy(sortBy);
        System.out.println("Filter Başladı");
        String[] values = new Filter().setFilterBy(filterBy, globalFilter, manager, this);
        filterTemp = values[0];
        globalFilterCommand = values[1];
        String[] commandArray = new LazyToSql().tableSqlCommand(filterTemp, globalFilterCommand, sortTemp, sortBy.isEmpty(), this);
        System.out.println("Veritabanı Sorgusu gönderildi");
        List<T> lazyList = manager.GetLazy(first, pageSize, commandArray[0]);
        System.out.println("işlem başarılı bir şekilde sonlandı");
        return lazyList;


    }


}
