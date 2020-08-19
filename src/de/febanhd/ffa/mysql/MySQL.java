package de.febanhd.ffa.mysql;

import java.sql.*;
import java.util.List;

public class MySQL {
    private String HOST = "";
    private String DATABASE = "";
    private String USER = "";
    private String PASSWORD = "";

    private Connection con;

    public MySQL(String HOST, String DATABASE, String USER, String PASSWORD) {
        this.HOST = HOST;
        this.DATABASE = DATABASE;
        this.USER = USER;
        this.PASSWORD = PASSWORD;

    }

    public MySQL(String HOST, String DATABASE, String USER) {
        this.HOST = HOST;
        this.DATABASE = DATABASE;
        this.USER = USER;
    }

    public void connect() {
        try {
            if (con != null) {
                return;
            }
            Class.forName("com.mysql.jdbc.Driver");


            con = DriverManager.getConnection("jdbc:mysql://" + HOST + ":3306/" + DATABASE + "?autoReconnect=true", USER, PASSWORD);
            System.out.println("MYSQL Verbindung steht!");

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    public void close() {
        try {
            if (con != null) {
                con.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean isConnected() {
        return con != null;
    }

    public void update(String sql, Object... objects) {
        if(this.con == null) return;
        try {
            PreparedStatement preparedStatement = this.con.prepareStatement(sql);
            for (int i = 0; i < objects.length; i++) {
                Object object = objects[i];
                preparedStatement.setObject(i + 1, object);
            }
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ResultSet query(String sql, Object... objects) {
        if(this.con == null) return null;
        try {
            PreparedStatement preparedStatement = this.con.prepareStatement(sql);
            for (int i = 0; i < objects.length; i++) {
                Object object = objects[i];
                preparedStatement.setObject(i + 1, object);
            }
            return preparedStatement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Connection getConnection() {
        return con;
    }

}
