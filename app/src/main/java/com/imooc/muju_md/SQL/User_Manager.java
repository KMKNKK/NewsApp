package com.imooc.muju_md.SQL;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by MYT on 2017/9/13.
 */

public class User_Manager {
    private String Username;
    private String Password;
    private String PictureUrl;
    private boolean isAdmin;

    public User_Manager(){
        isAdmin = false;
        Username = Password = PictureUrl =null;
    }

    public User_Manager(String Username, String Password){
        isAdmin = false;
        PictureUrl = null;
        this.Username = Username;
        this.Password = Password;
    }

    public boolean loginUser(){
        JDBC_connection dbc = new JDBC_connection("NewsAPP");
        String Sql = "select * from User_Manager where Username = '"+Username+"' and Password = '"+Password+"'";
        return dbc.findResultSet(Sql);
    }

    public boolean insertUser() {
        JDBC_connection dbc = new JDBC_connection("NewsAPP");
        String Sql = "select * from User_Manager where Username = '"+Username+"';";
        if(dbc.findResultSet(Sql))
            return false;
        Sql = "insert into User_Manager(Username,Password) values ('" +
                Username + "','" + Password + "'" + ");";
        dbc.insertMySql(Sql);
        return true;
    }

    public String getUsername(){
        return Username;
    }

    public String getPassword(){
        return Password;
    }

    public String getPictureUrl(){
        return PictureUrl;
    }

    public boolean getIsAdmin()
    {
        return isAdmin;
    }
}
