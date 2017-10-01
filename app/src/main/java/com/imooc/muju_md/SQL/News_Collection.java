package com.imooc.muju_md.SQL;


import com.imooc.muju_md.gson.NewsList;

import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;

public class News_Collection {
    private String NewsName;
    private String NewsContent;
    private String NewsPictureURL;

    public News_Collection(String NewsName,String NewsContent,String NewsPictureURL) {
        this.NewsName = NewsName;
        this.NewsContent = NewsContent;
        this.NewsPictureURL = NewsPictureURL;
    }

    public boolean insertNewsCollection(String Username){
        JDBC_connection dbc = new JDBC_connection("NewsAPP");
        String Sql = "select * from News_Collection where NewsContent = '" +
                NewsContent + "' and Username = '" + Username + "';";
        if(dbc.findResultSet(Sql))
        {
            deleteNewsCollection(Username);
            return false;
        }
        System.out.println("DODODODOODODODODODO");
        SimpleDateFormat formatter = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
        Date curDate = new Date(System.currentTimeMillis());
        String str = formatter.format(curDate);
        Sql = "insert into News_Collection values('" +
                NewsName + "','" + NewsContent + "','" + NewsPictureURL + "','" +
                Username +"');";
        dbc.insertMySql(Sql);
        return true;
    }

    public boolean deleteNewsCollection(String Username){
        JDBC_connection dbc = new JDBC_connection("NewsAPP");

         String Sql = "delete from News_Collection where Newscontent = '" +
                NewsContent + "' and Username = '" + Username + "';";
        dbc.deleteMySql(Sql);
        return true;
    }

    public NewsList getNewsConllectionResultSet(String Username){
        JDBC_connection dbc = new JDBC_connection("NewsAPP");
        String Sql = "select * from News_Collection where Username = '" + Username + "';";
        return dbc.getResultSet(Sql);
    }
}
