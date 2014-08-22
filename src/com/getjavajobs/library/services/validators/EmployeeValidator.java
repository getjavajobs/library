package com.getjavajobs.library.services.validators;

import java.util.regex.Pattern;

import com.getjavajobs.library.model.Employee;

public class EmployeeValidator {
	private Employee employee;
	private static final String COMMON_PATTERN = "[a-zA-Zà-ÿÀ-ß]{1,50}";
	private Pattern pattern;

	public EmployeeValidator(Employee employee) {
		this.employee = employee;
	}

	public boolean validate() {

		pattern = Pattern.compile(COMMON_PATTERN);

		return pattern.matcher(employee.getName()).matches()
				&& pattern.matcher(employee.getSurname()).matches()
				&& pattern.matcher(employee.getPatronymic()).matches();
	}
}
