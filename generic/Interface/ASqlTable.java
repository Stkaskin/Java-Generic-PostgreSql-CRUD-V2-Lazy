package com.asstnavi.generic.Interface;

import com.asstnavi.generic.Interface.ISqlTable;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ASqlTable
{
    public String TableName() default "";
}
