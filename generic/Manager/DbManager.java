package com.asstnavi.generic.Manager;

import com.asstnavi.generic.Interface.ASqlTable;
import com.asstnavi.generic.Interface.ISqlTable;
import com.asstnavi.generic.Service.DataService;

import java.util.ArrayList;

// getName(manager) bir sonraki aşamada eğer getTableName yoksa >  getName(manager) çağıracak
public class DbManager
{
    ISqlTable manager;
    final String tableName;

    public String getTableName()
    {
        return tableName;
    }

    public DbManager(ISqlTable manager)
    {
        this.manager = manager;
        tableName=getName(manager);
    }


    public <T> int Add()
    {
        return new PostgreSqlManager().AddL(getName(manager), manager);
    }


    public <T> ArrayList<T> Get(String... whereCommands)
    {
        String wh = " where ";
        for (String item : whereCommands)
        {
            wh += item + " ";
        }

        String sql = "Select * from " + getName(manager) +
                " " + wh;

        return (ArrayList<T>) new PostgreSqlManager().get(manager, sql);

    }

    public <T> T GetFirst(String... whereCommands)
    {
        String wh = " where ";
        for (String item : whereCommands)
        {
            wh += item + " ";
        }

        String sql = "Select * from " + getName(manager) +
                " " + wh;
        ArrayList<ISqlTable> g = (ArrayList<ISqlTable>) new PostgreSqlManager().get(manager, sql);
        if (g != null && g.size() > 0)
            return (T) g.get(0);
        else
            return (T) DataService.newInstance(manager);

    }


    public <T> ArrayList<T> GetAll()
    {
        String sql = "Select * from " + getName(manager);

        return (ArrayList<T>) new PostgreSqlManager().get(manager, sql);
    }


    public <T> void Delete(String... whereCommandThenAndOrThenWherecommand)
    {
        if (whereCommandThenAndOrThenWherecommand != null && whereCommandThenAndOrThenWherecommand.length > 0)
            new PostgreSqlManager().Delete(getName(manager), whereCommandThenAndOrThenWherecommand);
        else
            new PostgreSqlManager().Delete(getName(manager), " id='" + DataService.getIdData(manager) + "'");
    }

    public <T> void Update()
    {
        new PostgreSqlManager().Update(getName(manager), manager);
    }

    public <T> void Update(T obj)
    {
        new PostgreSqlManager().Update(getName(manager), obj);
    }


    public String globalFilterRun(Object value)
    {
        String prop = "( Cast(id as TEXT) ILIKE '%" + value + "%' OR " +



                /*   " Cast(date as TEXT) ILIKE '%" + value + "%' OR " +*/
                " Cast(type as TEXT) ILIKE '%" + value + "%' ) ";

        return prop;
    }





    public int GetCountRows(String command)
    {
        return new PostgreSqlManager().GetRow("Select count(id) from " + getName(manager) + " " + command);
    }

    public <T> ArrayList<T> GetLazy(int first, int count)
    {
        return (ArrayList<T>) new PostgreSqlManager().getLazy(manager, getName(manager), first, count);
    }

    public <T> ArrayList<T> GetLazy(int first, int count, String fieldAndSort)
    {
        String sql = getName(manager) + " " + FilterString(first, count, fieldAndSort);


        return (ArrayList<T>) new PostgreSqlManager().getLazy(manager, sql);
    }

    public String getName(ISqlTable manager)
    {
        String tableName =manager.getClass().getAnnotation(ASqlTable.class).TableName();
        return tableName != null ?tableName:    getName(manager);

    }
    public String FilterString(int first, int count, String fieldAndSort) {
        return fieldAndSort + "  OFFSET ('" + first + "') ROWS FETCH NEXT ('" + (count * 7) + "') ROWS ONLY";
    }

    public Class getISqlTableClass()
    {
        return manager.getClass();
    }
}
