package jdbc;

import jdbc.exception.DbException;

import java.sql.*;

public class ConnectionFactory {

    private static Connection conn = null;

    public static Connection getConnection() {
        if (conn == null) {
            try {
                String user = "postgres";
                String password = "1234567";
                String databaseName = "javafx-project";
                String serverAddress = "localhost";

                conn = DriverManager.getConnection("jdbc:postgresql://" + serverAddress +
                        "/" + databaseName, user, password);
            } catch (SQLException e) {
                throw new DbException(e.getMessage());
            }
        }
        return conn;
    }

    public static void closeConnection() {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                throw new DbException(e.getMessage());
            }
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
