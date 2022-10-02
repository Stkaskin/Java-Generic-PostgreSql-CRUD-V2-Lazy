package com.asstnavi.generic.LazyPack;

import com.asstnavi.generic.Enum.MatchMode;
import com.asstnavi.generic.Interface.ISqlTable;
import com.asstnavi.generic.Manager.DbManager;
import com.asstnavi.generic.Manager.PropertyManager;
import com.asstnavi.generic.Service.DataConverter.Primitives;
import com.asstnavi.generic.Service.DataService;

import java.lang.reflect.Type;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Map;

public class Filter extends LazyService
{
    public String[] setFilterBy(Map<String, FilterModel> filterBy, String globalFilter, DbManager manager, LazyService service)
    {
        String[] strings = new String[2];
        strings[0] = " ";

        strings[1] = " ";

        String[] dateT = new String[1];
        if (!filterBy.isEmpty())
        {
            System.out.println("Filter 1");
            filterBy.forEach((key, value) -> {
                if (value.getValue().toString().length() > 0)
                {
                    System.out.println("Filter 2");
                    String valueField = key;
                    Object fieldValue = value.getValue();
                    String[] fieldItems;
                    if (StringArrayControl(fieldValue))
                    {
                        fieldItems = (String[]) fieldValue;
                    } else
                        fieldItems = new String[]{fieldValue.toString()};
                    if (value.getMatchMode() == MatchMode.EQUALS)
                    {
                        boolean control = false;
                        for (String item : fieldItems)
                        {
                            service.setFilter(true);
                            control = true;
                            strings[0] += " " + valueField + "='" + item.toString() + "' Or ";
                        }
                        if (control)
                        {
                            strings[0] = strings[0].substring(0, strings[0].length() - 4);
                            strings[0] += " And ";
                        }
                    }
                    if (value.getMatchMode() == MatchMode.BETWEEN)
                    {

                        Class _class = DataService.getFieldAColumnClass(manager.getISqlTableClass(), valueField);
                        if (_class != null)
                        {
                            strings[0] += " " + valueField + " BETWEEN '" +  fieldItems[0] +
                                    "' AND '" +  fieldItems[1] + "' And";
                            service.setFilter(true);
                        }
                     /*   else
                        {

                            service.setFilter( true);

                            strings[0] += " " + valueField + " BETWEEN '"+fieldItems[0] +"' and "+fieldItems[1]+"' And";
                        }*/


                    }
                    if (value.getMatchMode() == MatchMode.GLOBAL)
                    {
                        service.setFilterGlobal(true);
                        strings[1] = " AND " + manager.globalFilterRun(fieldValue);
                    } else if (value.getMatchMode() == MatchMode.STARTS_WITH)
                    {
                        //foreach ile güncellenecektir
                        //StringArrayControl kontrolü yapılacak gelen veri veya verileri sınıflayacak
                        //'% ... ' > olarak ayaralanacak
                        strings[0] += "   CAST(" + valueField + " AS TEXT) ILIKE '%" + fieldValue + "%' And ";
                        service.setFilter(true);
                    } else if (value.getMatchMode() == MatchMode.CONTAINS)
                    {

                        String val = " (";
                        boolean f_ = false;
                        for (String b_ : fieldItems)
                        {
                            val += " CAST(" + valueField + " AS TEXT) ILIKE '%" + b_ + "%' OR";
                            f_ = true;
                        }
                        if (f_)
                        {
                            val = val.substring(0, val.length() - 2) + ") And ";
                        }
                        strings[0] += val;
                        service.setFilter(true);
                    }
                }
            });

        }
        System.out.println("Filter 3");
        return strings;
    }

    private boolean StringArrayControl(Object obj)
    {
        try
        {

            if (obj.getClass().getTypeName().toString().equals("java.lang.String[]"))
                return true;
            else
                return false;

        } catch (Exception e)
        {
            return false;
        }


    }

}
