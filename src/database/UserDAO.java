package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserDAO {

    // =========================================
    // REGISTER USER
    // =========================================

    public boolean registerUser(
            String fullName,
            String username,
            String password) {

        String sql =
                "INSERT INTO users " +
                "(full_name, username, password) " +
                "VALUES (?, ?, ?)";

        try (
                Connection connection =
                        DatabaseConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setString(1, fullName);
            statement.setString(2, username);
            statement.setString(3, password);

            statement.executeUpdate();

            return true;

        } catch (Exception e) {

            System.out.println(
                    "Registration failed!"
            );

            e.printStackTrace();

            return false;
        }
    }

    // =========================================
    // LOGIN USER
    // =========================================

    public boolean loginUser(
            String username,
            String password) {

        String sql =
                "SELECT * FROM users " +
                "WHERE username = ? " +
                "AND password = ?";

        try (
                Connection connection =
                        DatabaseConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setString(1, username);
            statement.setString(2, password);

            ResultSet resultSet =
                    statement.executeQuery();

            return resultSet.next();

        } catch (Exception e) {

            System.out.println(
                    "Login failed!"
            );

            e.printStackTrace();

            return false;
        }
    }
}