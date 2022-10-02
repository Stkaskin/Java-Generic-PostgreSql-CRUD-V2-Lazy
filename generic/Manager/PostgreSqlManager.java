package com.asstnavi.generic.Manager;


import com.asstnavi.common.ConnectionFactory;
import com.asstnavi.domain.employee.Employee;
import com.asstnavi.generic.Service.DataService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PostgreSqlManager
{
    public void CreateTables()
    {


    }

    /*   public Connection Connection() {
           Connection connection = null;
           try {
               Class.forName("org.postgresql.Driver");
               connection = DriverManager
                       .getConnection("jdbc:postgresql://localhost:5432/Test",
                               "postgres", "123");
           } catch (Exception e) {
               e.printStackTrace();
               //Log hata kayıt
              *//* System.err.println(e.getClass().getName()+": "+e.getMessage());
            System.exit(0);*//*
        }
        return connection;
    }*/
    public Connection Connection()
    {
        try
        {
            return ConnectionFactory.getInstance().getConnection();
        } catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
    }

    public void deleteTables()
    {

     /*   String SqlDelete = "DROP TABLE IF EXISTS   menu;DROP TABLE IF EXISTS  menudetail;DROP TABLE IF EXISTS  dayofmenudetail;DROP TABLE IF EXISTS  dayofmenu;DROP TABLE IF EXISTS  food;";
        Command(SqlDelete);*/
    }

    public int Add(String command)
    {

        Connection connection = Connection();
        Statement stmt = null;
        try
        {
            stmt = connection.createStatement();
            stmt.executeUpdate(command, Statement.RETURN_GENERATED_KEYS);
            ResultSet rs = stmt.getGeneratedKeys();
            int key = -1;
            if (rs.next())
            {
                key = rs.getInt(1);
            }
            stmt.close();
            connection.close();
            return key;

        } catch (SQLException e)
        {
            try
            {
                e.printStackTrace();
                stmt.close();
                connection.close();
            } catch (SQLException ex)
            {
                throw new RuntimeException(ex);
            }

            return -1;
        }
    }

    public static int Add = 1;
    public static int Update = 2;
    public static int Delete = 3;
    public static int Select = 4;


    public void Command(String command)
    {

        Connection connection = Connection();
        Statement stmt = null;
        try
        {
            stmt = connection.createStatement();
            stmt.executeUpdate(command);


            stmt.close();
            connection.close();


        } catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
    }

    public <T> ArrayList<T> Read(T obj, String command)
    {

        Connection connection = Connection();

        Statement stmt = null;

        try
        {
            stmt = connection.createStatement();
            ResultSet set = stmt.executeQuery(command);
            ArrayList<Map<String, Object>> maps = new ArrayList<>();
            Map<String, Object> map = new HashMap<>();
            while (set.next())
            {
                map = new HashMap<>();
                ResultSetMetaData setMetaData = set.getMetaData();
                for (int i = 1; i <= setMetaData.getColumnCount(); i++)
                {

                    map.put(setMetaData.getColumnName(i).toLowerCase(), set.getObject(i));
                }

                maps.add(map);

            }

            stmt.close();
            connection.close();
            return DataService.ConvertData(obj, maps);

        } catch (SQLException e)
        {
            throw new RuntimeException(e);
        }

    }

    //whereCommandThenAndOrThenWherecommand
    public <T> void Delete(String tableName, String... whereCommandThenAndOrThenWherecommand)
    {
        if (whereCommandThenAndOrThenWherecommand != null)
        {
            String q = "";
            for (String item : whereCommandThenAndOrThenWherecommand)
            {
                q += " " + item;
            }
            String sql = "DELETE FROM " + tableName + " WHERE " + q + ";";

            Command(sql);
        }
    }

    public <T> void DeleteL(String tableName, T obj, String... whereCommandThenAndOrThenWherecommand)
    {

        //   String id = DataService.getIdData(obj);
        // String data_ = PropertyManager.InsertDataSyntax(Read(obj, "Select * from " + tableName + " where id=" + id).get(0));
        //   new LogService().Delete(tableName, data_, id, Delete);
        Delete(tableName, whereCommandThenAndOrThenWherecommand);

    }


    public <T> void Update(String tableName, T obj)
    {
        String newData = PropertyManager.fieldToUpdateCommand(obj);
      //  String id = DataService.getIdData(obj);
      //  String oldaData = PropertyManager.InsertDataSyntax(Read(obj, "Select * from " + tableName + " where id=" + id));
        String sql = "Update " + tableName + " SET " + newData;
        //    new LogService().Update(newData, oldaData, tableName);
        Command(sql);
    }


    public <T> ArrayList<T> get(T obj, String sql)
    {
        return Read(obj, sql);
    }

    public <T> ArrayList<T> getLazy(T obj, String tableName, int first, int count)
    {
        String wh = " ORDER BY id OFFSET ('" + first + "') ROWS FETCH NEXT ('" + (count * 7) + "') ROWS ONLY";
        String sql = "Select * from " + tableName +
                " " + wh;
        return Read(obj, sql);
    }


    public <T> ArrayList<T> getLazy(T item, String sql)
    {
        sql = "Select * from " + sql;
        return Read(item, sql);
    }

    public int GetRow(String command)
    {
        Connection connection = Connection();
        Statement stmt = null;
        int count = 0;

        try
        {
            stmt = connection.createStatement();
            ResultSet set = stmt.executeQuery(command);
            while (set.next())
            {

                ResultSetMetaData setMetaData = set.getMetaData();
                count = set.getInt("count");

            }

            stmt.close();
            connection.close();
            return count;

        } catch (SQLException e)
        {
            throw new RuntimeException(e);
        } finally
        {

            try
            {
                stmt.close();
                connection.close();
            } catch (SQLException e)
            {
                throw new RuntimeException(e);
            }
        }


    }

    public void UpdateL()
    {
    }

    public <T> int AddL(String tableName, T obj)
    {
        // String data = PropertyManager.InsertDataSyntax(obj);
        //   new LogService().Add(data, tableName, Add);
        String sql = "Insert Into " + tableName + " " + PropertyManager.fieldToInsertCommand(obj);
        return Add(sql);
    }
}
