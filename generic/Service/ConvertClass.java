package com.asstnavi.generic.Service;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class ConvertClass
{
    Method setMethod;
    Method getMethod;
    Field field;

    public Method getSetMethod()
    {
        return setMethod;
    }

    public void setSetMethod(Method setMethod)
    {
        this.setMethod = setMethod;
    }

    public Method getGetMethod()
    {
        return getMethod;
    }

    public void setGetMethod(Method getMethod)
    {
        this.getMethod = getMethod;
    }

    public Field getField()
    {
        return field;
    }

    public void setField(Field field)
    {
        this.field = field;
    }
}
