package com.asstnavi.generic.Service;

import com.asstnavi.domain.employee.AColumn;
import com.asstnavi.generic.Interface.ASqlTable;
import com.asstnavi.generic.Service.DataConverter.Primitives;


import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.*;

public class DataService
{
    DataService()
    {
        Locale.setDefault(Locale.ENGLISH);
    }

    public static <T> String getFieldAColumn(Class _class, String name)
    {

        Field[] fields = _class.getDeclaredFields();
        for (Field f : fields)
            if (f.getName().equalsIgnoreCase(name))
                return f.getAnnotation(AColumn.class).name();
        return null;

    }

    public static <T> Class getFieldAColumnClass(Class _class, String name)
    {
        Field[] fields = _class.getDeclaredFields();

        for (Field f : fields)
            if (f.getAnnotation(AColumn.class) != null)
                if (f.getAnnotation(AColumn.class).name().equalsIgnoreCase(name))
                    return f.getType();
        return null;

    }

    public static <T> Map<String, Object> ConvertDataForObjectList(T obj)
    {
        Locale.setDefault(Locale.ENGLISH);
        Map<String, Method> methods = getMethodsGet(obj);
        Field[] c = obj.getClass().getDeclaredFields();

        Map<String, Object> data_t = new HashMap<>();
        if (obj.getClass().getAnnotation(ASqlTable.class) == null)
        {
            System.err.println("Tablonun sql bağlantısı bulunaması / ASqlTable ");
            return null;
        }
        for (Field item : c)
        {
            //getAnnotation değilse veritabanı sutunu değildir
            Method obj2 = methods.get(item.getName().toLowerCase());
            if (obj2 != null && item.getAnnotation(AColumn.class) != null)
            {
                try
                {//item.getName() >item.getAnnotation(AColumn.class).name() olarak değiştirildi
                    //
                    data_t.put(item.getAnnotation(AColumn.class).name(), obj2.invoke(obj));
                } catch (IllegalAccessException e)
                {
                    throw new RuntimeException(e);
                } catch (InvocationTargetException e)
                {
                    throw new RuntimeException(e);
                }
            }
        }

        return data_t;
    }


    public static String getIdData(Object obj)
    {
        String dataId = "";
        try
        {
            dataId = obj.getClass().getMethod("getId").invoke(obj).toString();
        } catch (IllegalAccessException e)
        {
            e.printStackTrace();
        } catch (InvocationTargetException e)
        {
            e.printStackTrace();
        } catch (NoSuchMethodException e)
        {
            e.printStackTrace();
        }
        return dataId;
    }


    //Disabled Old Manuel Cast
    public static <T> String TableNameGet(T obj)
    {
        String TableName = "";
        try
        {
            TableName = obj.getClass().getMethod("TableName").invoke(obj).toString();
        } catch (IllegalAccessException e)
        {
            e.printStackTrace();
        } catch (InvocationTargetException e)
        {
            e.printStackTrace();
        } catch (NoSuchMethodException e)
        {
            e.printStackTrace();
        }
        return TableName;
    }


    public static <T> T ConvertData(T obj, Map<String, Object> data)
    {
        try
        {
            obj = (T) obj.getClass().newInstance();
        } catch (IllegalAccessException e)
        {
            e.printStackTrace();
        } catch (InstantiationException e)
        {
            e.printStackTrace();
        }
        //sonraki versiyonda komple mape dönüştürülecektir
        Field[] fields = obj.getClass().getDeclaredFields(); //System.out.println(item.get(obj).toString()+"");

        Map<String, Method> setmethods = setMethodsGet(obj);
        for (Field item : fields)
        {
            Method obj2 = setmethods.get(item.getName().toLowerCase());
            if (obj2 != null && item.getAnnotation(AColumn.class) != null)
            {
                try
                {
                    obj = setFieldWithMethodInvoke(obj, obj2, item, data.get(item.getAnnotation(AColumn.class).name().toLowerCase()));

                } catch (Exception e)
                {
                    e.printStackTrace();
                }

            }

        }
        //   documentIdSet(obj,  data.getId());
        return obj;
    }

    public static <T> ArrayList<T> ConvertData(T obj, ArrayList<Map<String, Object>> data)
    {
        ArrayList<T> allTArrayList = new ArrayList<>();
        for (Map<String, Object> snapshot : data)
        {
            allTArrayList.add(ConvertData(obj, snapshot));
        }
        return allTArrayList;
    }

    public static <T> T documentIdSet(T obj, String data)
    {


        try
        {
            obj.getClass().getMethod("setId", String.class).invoke(obj, data);
        } catch (IllegalAccessException e)
        {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e)
        {
            throw new RuntimeException(e);
        } catch (NoSuchMethodException e)
        {
            throw new RuntimeException(e);
        }

        return obj;


    }

    private static <T> T setFieldWithMethodInvoke(T obj, Method method, Field item, Object data)
    {
        try
        {
            if (data != null)
            {
                Object obj2 = null;
                obj2 = Primitives.Convert(item.getType(), data);
                method.invoke(obj, obj2);

            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return obj;
    }

    private static Map<String, Method> getMethodsGet(Object obj)
    {
        Method[] methods = obj.getClass().getDeclaredMethods();
        List<Method> methodList = new ArrayList<>();
        for (Method method : methods)
        {
            if (method.getName().toLowerCase(Locale.ENGLISH).startsWith("get") || method.getName().toLowerCase(Locale.ENGLISH).startsWith("is"))
                methodList.add(method);

        }

        /*
        List<Method> setMethods = Arrays.stream(PropertyUtils.getPropertyDescriptors(obj))
                .sequential()
                .map(PropertyDescriptor::getReadMethod)
                .collect(Collectors.toList());*/
        return methodToMap(methodList);

    }

    private static Map<String, Method> setMethodsGet(Object obj)
    {

        Method[] methods = obj.getClass().getDeclaredMethods();
        List<Method> methodList = new ArrayList<>();
        for (Method method : methods)
        {
            if (method.getName().toLowerCase(Locale.ENGLISH).startsWith("set"))
                methodList.add(method);

        }
     /*   List<Method> setMethods = Arrays.stream(PropertyUtils.getPropertyDescriptors(obj))
                .sequential()
                .map(PropertyDescriptor::getWriteMethod)
                .collect(Collectors.toList());*/

        return methodToMap(methodList);
    }

    private static Map<String, Method> methodToMap(List<Method> methods)
    {
        Map<String, Method> methodMap = new HashMap<>();
        for (Method method : methods)
        {
            if (method != null)
                if (!method.getName().substring(0, 2).toLowerCase(Locale.ROOT).equals("is"))
                    methodMap.put(method.getName().substring(3).toLowerCase(Locale.ROOT), method);
                else
                    methodMap.put(method.getName().substring(2).toLowerCase(Locale.ROOT), method);
        }
        return methodMap;
    }

    public static <T> T newInstance(T obj)
    {
        try
        {
            return (T) obj.getClass().newInstance();
        } catch (InstantiationException e)
        {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e)
        {
            throw new RuntimeException(e);
        }

    }


}
