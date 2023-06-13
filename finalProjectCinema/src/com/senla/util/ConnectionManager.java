package com.senla.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager {
    final static String projectDir = System.getProperty("user.dir");
    final static String connectionUrl = "jdbc:sqlite:" + projectDir + "/db/cinemaDB.db";

    private ConnectionManager() {

    }

    public static Connection open() {
        try {
            return DriverManager.getConnection(connectionUrl);
        } catch (SQLException e) {
            throw new RuntimeException("Подключение не удалось");
        }
    }

}
