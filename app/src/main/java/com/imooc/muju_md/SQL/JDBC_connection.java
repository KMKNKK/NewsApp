package com.imooc.muju_md.SQL;

import com.imooc.muju_md.gson.News;
import com.imooc.muju_md.gson.NewsList;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JDBC_connection {
    private Connection con;
    private Statement st;
    private ResultSet rs;

    public JDBC_connection(String DatabaseName) {
        String driver_class = "com.mysql.jdbc.Driver" ;
        String driver_url = "jdbc:mysql://10.0.2.2:3306/newsapp" ;
        try {
            Class.forName(driver_class);
            con = DriverManager.getConnection("jdbc:mysql://10.0.2.2:3305/newsapp", "root", "root");
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    protected void destory(){
        try
        {
            con.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public NewsList getResultSet(String Sql) {
        try {
            st = (Statement) con.createStatement();
            rs = st.executeQuery(Sql);
            NewsList N = new NewsList();
            N.newsList.clear();
            while(rs.next()){
                News n = new News(rs.getString(1),rs.getString(2),rs.getString(3));
                N.newsList.add(n);
            }


            st.close();
            return N;

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    public boolean findResultSet(String Sql){
        try {
            st = (Statement) con.createStatement();
            rs = st.executeQuery(Sql);

            if(rs.next()) {
                rs.close();
                st.close();
                return true;
            }
            return false;
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return false;
    }

    public void insertMySql(String Sql) {
        try {
            st = (Statement) con.createStatement();
            st.executeUpdate(Sql);
            st.close();
            return ;
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return ;
    }

    public void deleteMySql(String Sql) {
        try {
            st = (Statement) con.createStatement();
            st.executeUpdate(Sql);                               //？？？？？？？
            st.close();
            return ;
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return ;
    }
}
