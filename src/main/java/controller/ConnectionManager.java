package controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager {

    private Connection connection;

    private ConnectionManager(){
        String url = "jdbc:mysql://localhost/proiect_final_java1p";

        try {
            connection = DriverManager.getConnection(url,"root", "");
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    private static final class SingletonHolder{
        public static ConnectionManager INSTANCE = new ConnectionManager();
    }

    public static ConnectionManager getInstance(){
        return SingletonHolder.INSTANCE;
    }

    public Connection getConnection() {
        return connection;
    }
}
