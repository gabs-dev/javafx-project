package dao;

import jdbc.ConnectionFactory;
import jdbc.exception.DbException;
import model.Company;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CompanyDao implements IDao<Company> {

    private Connection conn;

    public CompanyDao() {
        this.conn = ConnectionFactory.getConnection();
    }

    @Override
    public void add(Company obj) {
        PreparedStatement st = null;
        String sql = "INSERT INTO company (name, email, cnpj) " +
                "VALUES (?, ?, ?);";

        try {
            conn.setAutoCommit(false);

            st = conn.prepareStatement(sql);
            st.setString(1, obj.getName());
            st.setString(2, obj.getEmail());
            st.setString(3, obj.getCnpj());
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
    public void update(Company obj) {
        PreparedStatement st = null;
        String sql = "UPDATE company " +
                "SET name = ?, email = ?, cnpj = ? " +
                "WHERE id = ?;";

        try {
            conn.setAutoCommit(false);

            st = conn.prepareStatement(sql);
            st.setString(1, obj.getName());
            st.setString(2, obj.getEmail());
            st.setString(3, obj.getCnpj());
            st.setLong(4, obj.getId());
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
    public void delete(Company obj) {
        PreparedStatement st = null;
        String sql = "DELETE FROM company WHERE id = ?;";

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
    public List<Company> findAll() {
        PreparedStatement st = null;
        ResultSet rs = null;
        List<Company> list = new ArrayList<>();
        String sql = "SELECT * FROM company";
        try {
            st = conn.prepareStatement(sql);
            rs = st.executeQuery();

            while(rs.next()) {
                Company c = instantiateCompany(rs);
                list.add(c);
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

    private Company instantiateCompany(ResultSet rs) throws SQLException {
        Company obj = new Company();
        obj.setId(rs.getLong("id"));
        obj.setName(rs.getString("name"));
        obj.setEmail(rs.getString("email"));
        obj.setCnpj(rs.getString("cnpj"));
        return obj;
    }

}
