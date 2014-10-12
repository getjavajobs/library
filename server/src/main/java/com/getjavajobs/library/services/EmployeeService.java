package com.getjavajobs.library.services;

import com.getjavajobs.library.dao.EmployeeDao;
import com.getjavajobs.library.exceptions.ServiceException;
import com.getjavajobs.library.model.Employee;
import com.getjavajobs.library.services.validators.EmployeeValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class EmployeeService {
    @Autowired
	private EmployeeDao employeeDao;
    @Autowired
	private EmployeeValidator employeeValidator;
	
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

		return  employeeDao.getAll();
	}
}