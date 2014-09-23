package com.getjavajobs.library.services.validators;

import com.getjavajobs.library.model.Employee;

import java.util.regex.Pattern;

public class EmployeeValidator {
	private static final String COMMON_PATTERN = "[a-zA-Z�-��-�]{1,50}";
	private Pattern pattern;

	public EmployeeValidator() {
	}

	public boolean validate(Employee employee) {

		pattern = Pattern.compile(COMMON_PATTERN);

		return pattern.matcher(employee.getName()).matches()
				&& pattern.matcher(employee.getSurname()).matches()
				&& pattern.matcher(employee.getPatronymic()).matches();
	}
}