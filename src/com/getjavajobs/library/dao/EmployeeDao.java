package com.getjavajobs.library.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.getjavajobs.library.exceptions.DAOException;
import com.getjavajobs.library.model.Employee;

public class EmployeeDao {

	public Employee add(Employee employee){

		Connection con = ConnectionHolder.getInstance().getConnection();
		boolean success = true;
		String query = "INSERT INTO Employees"
				+ " (name,surname,patronymic,dateofbirth,position)"
				+ " VALUES (?,?,?,?,?)";

		try (PreparedStatement prep = con.prepareStatement(query)) {
			prep.setString(1, employee.getName());
			prep.setString(2, employee.getSurname());
			prep.setString(3, employee.getSurname());
			prep.setDate(4, new java.sql.Date(employee.getDateOfBirth()
					.getTime()));
			prep.setString(5, employee.getPosition());
			prep.executeUpdate();
		} catch (SQLException e) {
			success = false;
			throw new DAOException(e);
		} finally {
			try {
				if (success) {
					con.commit();
				} else {
					con.rollback();
				}
			} catch (SQLException e) {
				throw new DAOException(e);
			}
		}
		return employee;
	}

	public void delete(int id) {
		Connection con = ConnectionHolder.getInstance().getConnection();
		boolean success = true;
		String query = "DELETE FROM Employees WHERE id = ?";

		try (PreparedStatement prep = con.prepareStatement(query)) {
			prep.setInt(1, id);
			prep.executeUpdate();
		} catch (SQLException e) {
			success = false;
			throw new DAOException(e);
		} finally {
			try {
				if (success) {
					con.commit();
				} else {
					con.rollback();
				}
			} catch (SQLException e) {

			}
		}
	}

	public Employee get(int id) {
		Connection con = ConnectionHolder.getInstance().getConnection();
		boolean success = true;
		String query = "SELECT * FROM Employees WHERE id = ?";

		Employee employee = new Employee();
		try (PreparedStatement prep = con.prepareStatement(query)) {
			prep.setInt(1, id);
			ResultSet result = prep.executeQuery();
			result.next();
			employee.setId(result.getInt("id"));
			employee.setName(result.getString("name"));
			employee.setSurname(result.getString("surname"));
			employee.setPatronymic(result.getString("patronymic"));
			employee.setDateOfBirth(new Date(result.getDate("dateofbirth").getTime()));
			employee.setPosition(result.getString("position"));

		} catch (SQLException e) {
			success = false;
			throw new DAOException(e);
		} finally {
			try {
				if (success) {
					con.commit();
				} else {
					con.rollback();
				}
			} catch (SQLException e) {
				throw new DAOException(e);
			}

		}

		return employee;
	}

	public Employee update(Employee employee){
		Connection con = ConnectionHolder.getInstance().getConnection();
		boolean success = true;
		String query = "UPDATE Employee SET " +
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
		}catch(SQLException e){
			success = false;
			throw new DAOException(e);
		}finally{
			try{
				if(success){
					con.commit();
				}else{
					con.rollback();
				}
			}catch(SQLException e){
				throw new DAOException(e);
			}
		}
		return employee;
		
	}
	
	public List<Employee> getAll() {

		Connection con = ConnectionHolder.getInstance().getConnection();
		boolean success = true;

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

		} catch (SQLException e) {
			success = false;
			throw new DAOException(e);
		} finally {
			try {
				if (success) {
					con.commit();
				} else {
					con.rollback();
				}
			} catch (SQLException e) {
				throw new DAOException(e);
			}
		}
		return employees;

	}

}
