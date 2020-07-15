package jdbc;

import exception.DbException;

import java.sql.*;

public class ConnectionFactory {

    public static Connection getConnection() {
        try {
            String user = "postgres";
            String password = "1234567";
            String databaseName = "javafx-project";
            String serverAddress = "localhost";

            return DriverManager.getConnection("jdbc:postgresql://" + serverAddress +
                    "/" + databaseName, user, password);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void closeStatement(Statement st) {
        if (st != null) {
            try {
                st.close();
            } catch (SQLException e) {
                throw new DbException(e.getMessage());
            }
        }
    }

    public static void closeResultSet(ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                throw new DbException(e.getMessage());
            }
        }
    }

}
