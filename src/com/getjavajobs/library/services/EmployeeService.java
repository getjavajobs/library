package com.getjavajobs.library.services;

import java.util.ArrayList;
import java.util.List;

import com.getjavajobs.library.dao.EmployeeDao;
import com.getjavajobs.library.exceptions.ServiceException;
import com.getjavajobs.library.model.Employee;
import com.getjavajobs.library.services.validators.EmployeeValidator;

public class EmployeeService {
	private EmployeeDao employeeDao = new EmployeeDao();
	private EmployeeValidator employeeValidator = new EmployeeValidator();
	
	public Employee add(Employee employee) throws ServiceException {
		if (employeeValidator.validate(employee)) {
			return employeeDao.add(employee);
		} else {
			throw new ServiceException();
		}
	}

	public Employee get(int id) throws ServiceException {
		return employeeDao.get(id);
		
	}

	public void delete(int id) throws ServiceException {
		employeeDao.delete(id);
	}

	public Employee update(Employee employee) throws ServiceException {
		if (employeeValidator.validate(employee)) {
			return employeeDao.update(employee);
		} else {
			throw new ServiceException();
		}

	}

	public List<Employee> getAll() throws ServiceException {
		List<Employee> employees = new ArrayList<>();
		return employees = employeeDao.getAll();
	}
}