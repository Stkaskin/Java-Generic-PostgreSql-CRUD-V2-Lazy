package com.asstnavi.generic.Interface;

import java.util.ArrayList;

public interface ISqlTableManager
{
    String tablename = "";

    String getTableName();

    void Create();


    <T> int Add(T obj);

    <T> ArrayList<T> Get(String... whereCommands);

    <T> ArrayList<T> GetAll();

    void Delete(String... whereCommandThenAndOrThenWherecommand);


    <T> void Update(T obj);
}

