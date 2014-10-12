package com.getjavajobs.library.services.validators;

import com.getjavajobs.library.model.Employee;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;
@Component
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
