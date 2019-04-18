/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import user.User;

/**
 *
 * @author spitlord
 */
public class Queries {

    public static boolean createUser(User user) {
        String query = " insert into Users (username, password, firstname, lastname, email)"
                + " values (?, ?, ?, ?, ?)";
        try {
            PreparedStatement preparedStmt = SessionConnection.getConnection().prepareStatement(query);
            preparedStmt.setString(1, user.getUsername());
            preparedStmt.setString(2, user.getPassword());
            preparedStmt.setString(3, user.getFirstName());
            preparedStmt.setString(4, user.getLastName());
            preparedStmt.setString(5, user.getEmail());
            preparedStmt.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean userLogin(String username, String password) {
        Statement stmt = null;
        try {
            String query = "SELECT * FROM Users WHERE "
                    + "username = " + "\"" + username + "\" and "
                    + "password = " + "\"" + password + "\";";
            stmt = SessionConnection.getConnection().createStatement();
            ResultSet rs = stmt.executeQuery(query);
            return rs.next();
        } catch (Exception e) {
        }
        return false;
    }

    public static boolean userExists(String username) {
        Statement stmt = null;
        String query = "SELECT * FROM Users WHERE "
                + "username = " + "\"" + username + "\";";
        try {
            stmt = SessionConnection.getConnection().createStatement();
            ResultSet rs = stmt.executeQuery(query);
            return rs.next();
        } catch (SQLException e) {
        }
        return false;
    }

}
