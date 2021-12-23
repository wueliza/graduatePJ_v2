package com.project.graduatepj;

import android.annotation.SuppressLint;
import android.os.StrictMode;
import android.util.Log;

import java.sql.*;

public class mssql {

    private boolean _isOpened = false;
    public static Connection connect;
    Statement st;

    public boolean isOpened() {
        return _isOpened;
    }

    @SuppressLint("NewApi")
    Connection ConnectionHelper() {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                .permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Connection connection = null;
        String ConnectionURL = null;
        try {
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            ConnectionURL = "jdbc:jtds:sqlserver://106.105.167.136:1433/DataBase;user=guess;password=SQLserver;";
            connection = DriverManager.getConnection(ConnectionURL);
        } catch (SQLException se) {
            Log.e("ERRO", se.getMessage());
        } catch (ClassNotFoundException e) {
            Log.e("ERRO", e.getMessage());
        } catch (Exception e) {
            Log.e("ERRO", e.getMessage());
        }
        return connection;
    }

    public mssql() {

        //設定jdbc連結字串，請依你的SQL Server設定值修改

        try {
            connect = ConnectionHelper();

            if (connect.isClosed() == false) {
                _isOpened = true;
                System.out.println("connect ok");
            } else {
                _isOpened = false;
                System.out.println("connect fail");
            }


        } catch (Exception ex) {

            ex.printStackTrace();

        }

    }
}