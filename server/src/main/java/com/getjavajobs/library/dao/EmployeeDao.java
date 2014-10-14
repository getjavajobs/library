package com.getjavajobs.library.dao;

import com.getjavajobs.library.exceptions.DAOException;
import com.getjavajobs.library.model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Repository
public class EmployeeDao {
    @Autowired
    JdbcTemplate jdbcTemplate;
	public Employee add(Employee employee) throws DAOException{

       // jdbcTemplate.update();

		Connection con = ConnectionHolder.getInstance().getConnection();
		boolean commit = false;
		String query = "INSERT INTO Employees"
				+ " (name,surname,patronymic,dateofbirth,position)"
				+ " VALUES (?,?,?,?,?)";

		try (PreparedStatement prep = con.prepareStatement(query)) {
			prep.setString(1, employee.getName());
			prep.setString(2, employee.getSurname());
			prep.setString(3, employee.getPatronymic());
			prep.setDate(4, new java.sql.Date(employee.getDateOfBirth()
					.getTime()));
			prep.setString(5, employee.getPosition());
			prep.executeUpdate();
			Statement stat = con.createStatement();
			ResultSet result = stat.executeQuery("Select last_insert_id()");
            result.next();
            int lastInsertedId = Integer.parseInt(result.getString("last_insert_id()"));
            employee.setId(lastInsertedId);
            if (!con.getAutoCommit()) {
                con.commit();
            }
            commit = true;
            return employee;

		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
            try {
                if (!commit && !con.getAutoCommit()) {
                    con.rollback();
                }
            } catch (SQLException e) {
                throw new DAOException(e);
            } finally {
                ConnectionHolder.getInstance().releaseConnection(con);
            }
        }
	}

	public void delete(int id) {
		Connection con = ConnectionHolder.getInstance().getConnection();
		boolean commit = false;
		String query = "DELETE FROM Employees WHERE id = ?";

		try (PreparedStatement prep = con.prepareStatement(query)) {
			prep.setInt(1, id);
			prep.executeUpdate();
			if (!con.getAutoCommit()) {
                con.commit();
            }
            commit = true;
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
            try {
                if (!commit && !con.getAutoCommit()) {
                    con.rollback();
                }
            } catch (SQLException e) {
                throw new DAOException(e);
            } finally {
                ConnectionHolder.getInstance().releaseConnection(con);
            }
        }
	}

	public Employee get(int id) {
		Connection con = ConnectionHolder.getInstance().getConnection();
		boolean commit = false;
		String query = "SELECT * FROM Employees WHERE id = ?";

		Employee employee = new Employee();
		try (PreparedStatement prep = con.prepareStatement(query)) {
			prep.setInt(1, id);
			ResultSet result = prep.executeQuery();
			
			if(result.next()){
			employee.setId(result.getInt("id"));
			employee.setName(result.getString("name"));
			employee.setSurname(result.getString("surname"));
			employee.setPatronymic(result.getString("patronymic"));
			employee.setDateOfBirth(new Date(result.getDate("dateofbirth").getTime()));
			employee.setPosition(result.getString("position"));
			}	
			return employee;
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
            ConnectionHolder.getInstance().releaseConnection(con);
        }
	}

	public Employee update(Employee employee){
		Connection con = ConnectionHolder.getInstance().getConnection();
		boolean commit = false;
		String query = "UPDATE Employees SET " +
                "name = ?, surname = ?, patronymic = ?,"
                + " dateofbirth = ?, position = ?" +
                "WHERE id = ?";
		try(PreparedStatement prep = con.prepareStatement(query)){
			prep.setString(1, employee.getName());
			prep.setString(2, employee.getSurname());
			prep.setString(3, employee.getPatronymic());
			prep.setDate(4, new java.sql.Date(employee.getDateOfBirth()
					.getTime()));
			prep.setString(5, employee.getPosition());
			prep.setInt(6, employee.getId());
			prep.executeUpdate();
			if (!con.getAutoCommit()) {
                con.commit();
            }
			commit = true;
            return employee;
		}catch(SQLException e){
			throw new DAOException(e);
		}finally {
            try {
                if (!commit && !con.getAutoCommit()) {
                    con.rollback();
                }
            } catch (SQLException e) {
                throw new DAOException(e);
            } finally {
                ConnectionHolder.getInstance().releaseConnection(con);
            }
        }      
		
	}
	
	public List<Employee> getAll() {

		Connection con = ConnectionHolder.getInstance().getConnection();
		List<Employee> employees = new ArrayList<>();
		String query = "SELECT * FROM Employees";
		try (PreparedStatement prep = con.prepareStatement(query)) {
			ResultSet result = prep.executeQuery();
			while (result.next()) {
				Employee employee = new Employee();
				employee.setId(result.getInt("id"));
				employee.setName(result.getString("name"));
				employee.setSurname(result.getString("surname"));
				employee.setPatronymic(result.getString("patronymic"));
				employee.setDateOfBirth(result.getDate("dateofbirth"));
				employee.setPosition(result.getString("position"));

				employees.add(employee);
			}
			return employees;
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			ConnectionHolder.getInstance().releaseConnection(con);
		}

	}

}