/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author spitlord
 */
public class SessionConnection {

    private static Connection connection;
    private static final String URL = "jdbc:mysql://mysql4.cs.stonybrook.edu/mkoshkin";
    private static final String USERNAME = "mkoshkin";
    private static final String PASSWORD = "109924038";

    public static Connection getConnection() throws SQLException {

        if (connection == null) {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        }
        return connection;
    }

}
