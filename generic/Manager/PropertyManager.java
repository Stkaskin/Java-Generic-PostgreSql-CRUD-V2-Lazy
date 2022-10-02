package com.asstnavi.generic.Manager;


import com.asstnavi.generic.Service.DataService;

import java.util.Locale;
import java.util.Map;

public class PropertyManager
{
    public static String ASCENDING = "asc";
    public static String DESCENDING = "desc";



    public static String fieldToInsertCommand(Object obj)
    { Locale.setDefault(Locale.ENGLISH);
        Map<String, Object> map = getFields(obj);
        if (map==null)
            return null;
        String mapNames = "";
        String mapValues = "";
        for (Map.Entry<String, Object> entry : map.entrySet())
        {
            if (!entry.getKey().equals("id"))
            {
                mapValues += "'" + entry.getValue() + "',";
                //postgreSqlde büyük harfli bir sütun bulunuyor ise " işareti şarttır
                //bu yüzden veriler kücük harfle girilmelidir Locale default En olarak seçilmeli

                mapNames += entry.getKey().toLowerCase() + ",";
            }
        }
        mapValues = mapValues.substring(0, mapValues.length() - 1);
        mapNames = mapNames.substring(0, mapNames.length() - 1);
        String com = " (" + mapNames + ")" + " values (" + mapValues + ")";
        return com;
    }

    public static String InsertDataSyntax(Object obj)
    {
        String com = " ";
        Map<String, Object> map = getFields(obj);
        if (map==null)
            return null;
        for (Map.Entry<String, Object> entry : map.entrySet())
        {

            com += entry.getKey() + "=" + "" + entry.getValue() + ",";


        }
        if (com.length() > 0)
            com = com.substring(0, com.length() - 1);

        return com;
    }

    public static String fieldToUpdateCommand(Object obj)
    {
        String com = " ";
        Map<String, Object> map = getFields(obj);
        if (map==null)
            return null;
        String idv = " where id=";
        for (Map.Entry<String, Object> entry : map.entrySet())
        {
            if (!entry.getKey().equals("id"))
            {
                com += entry.getKey() + "=" + "'" + entry.getValue() + "',";

            } else
                idv += entry.getValue();
        }
        if (com.length() > 0)
            com = com.substring(0, com.length() - 1);
        com += idv;
        return com;
    }

    public static Map<String, Object> getFields(Object obj)
    {
        return DataService.ConvertDataForObjectList(obj);
    }

}
