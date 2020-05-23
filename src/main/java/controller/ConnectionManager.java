package controller;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager {

    private Connection connection;
    private String url;


    {
        try(
                FileReader fileReader = new FileReader("./src/main/resources/txt/path.txt");
                BufferedReader bufferedReader = new BufferedReader(fileReader)
        ){

                 url = bufferedReader.readLine();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private ConnectionManager(){


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
