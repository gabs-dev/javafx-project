package dao;

import exception.DbException;
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

    public void add(Person p) {
        PreparedStatement st = null;

        try {
            String sql = "INSERT INTO person (name, email, password) " +
                    "VALUES (?, ?, ?);";

            st = conn.prepareStatement(sql);

            st.setString(1, p.getName());
            st.setString(2, p.getEmail());
            st.setString(3, p.getPassword());

            st.execute();
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            ConnectionFactory.closeStatement(st);
        }
    }

}
