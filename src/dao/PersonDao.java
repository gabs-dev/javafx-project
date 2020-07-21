package dao;

import jdbc.exception.DbException;
import jdbc.ConnectionFactory;
import model.Person;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PersonDao implements IDao<Person> {

    private Connection conn;

    public PersonDao() {
        this.conn = ConnectionFactory.getConnection();
    }

    @Override
    public void add(Person obj) {
        PreparedStatement st = null;
        String sql = "INSERT INTO person (name, email, password, photo) " +
                "VALUES (?, ?, ?, ?);";

        try {
            conn.setAutoCommit(false);

            st = conn.prepareStatement(sql);
            st.setString(1, obj.getName());
            st.setString(2, obj.getEmail());
            st.setString(3, obj.getPassword());
            st.setString(4, obj.getPhoto());
            st.execute();

            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                conn.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
                throw new DbException(e1.getMessage());
            }
            throw new DbException(e.getMessage());
        } finally {
            ConnectionFactory.closeStatement(st);
            ConnectionFactory.closeConnection();
        }
    }

    @Override
    public void update(Person obj) {
        PreparedStatement st = null;
        String sql = "UPDATE person " +
                "SET name = ?, email = ?, password = ?, photo = ? " +
                "WHERE id = ?;";

        try {
            conn.setAutoCommit(false);

            st = conn.prepareStatement(sql);
            st.setString(1, obj.getName());
            st.setString(2, obj.getEmail());
            st.setString(3, obj.getPassword());
            st.setString(4, obj.getPhoto());
            st.setLong(5, obj.getId());
            st.execute();

            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                conn.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
                throw new DbException(e1.getMessage());
            }
            throw new DbException(e.getMessage());
        } finally {
            ConnectionFactory.closeStatement(st);
            ConnectionFactory.closeConnection();
        }
    }

    @Override
    public void delete(Person obj) {
        PreparedStatement st = null;
        String sql = "DELETE FROM person WHERE id = ?;";

        try {
            conn.setAutoCommit(false);

            st = conn.prepareStatement(sql);
            st.setLong(1, obj.getId());
            st.execute();

            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                conn.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
                throw new DbException(e1.getMessage());
            }
            throw new DbException(e.getMessage());
        } finally {
            ConnectionFactory.closeStatement(st);
            ConnectionFactory.closeConnection();
        }
    }

    @Override
    public List<Person> findAll() {
        PreparedStatement st = null;
        ResultSet rs = null;
        List<Person> list = new ArrayList<>();
        String sql = "SELECT * FROM person";
        try {
            st = conn.prepareStatement(sql);
            rs = st.executeQuery();

            while(rs.next()) {
                Person p = instantiatePerson(rs);
                list.add(p);
            }

            return list;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DbException(e.getMessage());
        } finally {
            ConnectionFactory.closeStatement(st);
            ConnectionFactory.closeResultSet(rs);
            ConnectionFactory.closeConnection();
        }
    }

    public Person findByEmail(String email) {
        PreparedStatement st = null;
        ResultSet rs = null;
        Person p = null;
        String sql = "SELECT * FROM person WHERE email = ?";
        try {
            st = conn.prepareStatement(sql);
            st.setString(1, email);
            rs = st.executeQuery();

            while (rs.next()) {
                p = instantiatePerson(rs);
            }

            return p;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DbException(e.getMessage());
        } finally {
            ConnectionFactory.closeStatement(st);
            ConnectionFactory.closeResultSet(rs);
            ConnectionFactory.closeConnection();
        }
    }

    private Person instantiatePerson(ResultSet rs) throws SQLException {
        Person obj = new Person();
        obj.setId(rs.getLong("id"));
        obj.setName(rs.getString("name"));
        obj.setEmail(rs.getString("email"));
        obj.setPassword(rs.getString("password"));
        obj.setPhoto(rs.getString("photo"));
        return obj;
    }

}
