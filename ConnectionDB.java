/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BUS;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnectionDB {
    private final String db = "CLOTHESHOP";
    private final String user = "sang";
    private final String password = "123456";

    public Connection getConnection() {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            String connectionUrl = String.format(
                    "jdbc:sqlserver://localhost:1433;encrypt=false;databaseName=%s;user=%s;password=%s", db, user,
                    password);
            Connection con = DriverManager.getConnection(connectionUrl);
            return con;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ResultSet executeQuery (String sql) {
        Connection con = getConnection();
        Statement sttm = null;
        try {
            sttm = con.createStatement();
            ResultSet rs = sttm.executeQuery(sql);
            return rs;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean executeUpdate (String sql) {
        Connection con = getConnection();
        try {
            Statement sttm = con.createStatement();
            int rowEffect = sttm.executeUpdate(sql);
            return rowEffect != 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

    }
}
