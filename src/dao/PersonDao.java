package dao;

import jdbc.exception.DbException;
import jdbc.ConnectionFactory;
import model.Person;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PersonDao {

    private Connection conn;

    public PersonDao() {
        this.conn = ConnectionFactory.getConnection();
    }

    public void add(Person obj) {
        PreparedStatement st = null;

        try {
            conn.setAutoCommit(false);

            String sql = "INSERT INTO person (name, email, password) " +
                    "VALUES (?, ?, ?);";

            st = conn.prepareStatement(sql);

            st.setString(1, obj.getName());
            st.setString(2, obj.getEmail());
            st.setString(3, obj.getPassword());

            st.execute();
            conn.commit();
        } catch (SQLException e) {
            try {
                conn.rollback();
            } catch (SQLException e1) {
                throw new DbException(e1.getMessage());
            }
            throw new DbException(e.getMessage());
        } finally {
            ConnectionFactory.closeStatement(st);
            ConnectionFactory.closeConnection();
        }
    }

    public void update(Person obj) {
        PreparedStatement st = null;

        try {
            conn.setAutoCommit(false);

            String sql = "UPDATE person " +
                    "SET name = ?, email = ?, password = ? " +
                    "WHERE id = ?;";

            st = conn.prepareStatement(sql);

            st.setString(1, obj.getName());
            st.setString(2, obj.getEmail());
            st.setString(3, obj.getPassword());
            st.setLong(4, obj.getId());

            st.execute();
            conn.commit();
        } catch (SQLException e) {
            try {
                conn.rollback();
            } catch (SQLException e1) {
                throw new DbException(e1.getMessage());
            }
            throw new DbException(e.getMessage());
        } finally {
            ConnectionFactory.closeStatement(st);
            ConnectionFactory.closeConnection();
        }
    }

}
