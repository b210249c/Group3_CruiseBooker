package com.group3.cruisebookingsystem;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class DatabaseManager {

    private Connection connection;

    public DatabaseManager() {
        try {
            // Loading JDBC Driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            //TODO: Step 1 Provide the URL to the database with your own username and password
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/cruise_booking", "root", "Jka83788#");
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
    }
    public Connection getConnection() {
        return connection;
    }
}

