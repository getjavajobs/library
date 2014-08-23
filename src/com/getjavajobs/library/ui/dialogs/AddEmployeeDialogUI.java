package com.getjavajobs.library.ui.dialogs;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import com.getjavajobs.library.exceptions.ServiceException;
import com.getjavajobs.library.model.Employee;
import com.getjavajobs.library.services.EmployeeService;

/**
 * Диалог добавления сотрудника.
 */
public class AddEmployeeDialogUI extends AbstractDialogUI {

	private static final String dialogTitle = "Add new employee into library database";
	private JTextField[] textFields;	// Набор текстовых полей
	
	public AddEmployeeDialogUI(final JFrame parentFrame, final EmployeeService employeeService) {
		super(parentFrame, dialogTitle);
		
		textFields = new JTextField[5];
		for (int i = 0; i < textFields.length; ++i) {
			textFields[i] = new JTextField("");
		}
		
		/* Поля для ввода */
		addLabeledTextField(textFields[0], "Employee name:         ");
		addLabeledTextField(textFields[1], "Last name:                   ");
		addLabeledTextField(textFields[2], "Father name:               ");
		addLabeledTextField(textFields[3], "Birth date:                    ");
		addLabeledTextField(textFields[4], "Employee position:    ");
		
		/* Добавление нового автора в базу данных */
		addButton("Add new employee", new ActionListener() {	
			@Override
			public void actionPerformed(ActionEvent e) {
				if (isTextFieldsFilled(textFields)) {	// Если все поля заполнены --> не выдастся сообщение об ошибке.
					Employee newEmployee = new Employee();
					newEmployee.setName(textFields[0].getText());
					newEmployee.setSurname(textFields[1].getText());
					newEmployee.setPatronymic(textFields[2].getText());
					DateFormat formatter = new SimpleDateFormat("d MMM yyyy");
					Date date = null;
					try {
						date = formatter.parse(textFields[3].getText());
					} catch (ParseException e1) {
						e1.printStackTrace();
					}
					newEmployee.setDateOfBirth(date);
					newEmployee.setPosition(textFields[4].getText());
										
					try {
						employeeService.add(newEmployee);
						clearTextFields(textFields);
						JOptionPane.showMessageDialog(dialogFrame, "New reader succsessfully added!", "Success!", JOptionPane.INFORMATION_MESSAGE);
					} catch (ServiceException ex) {
						JOptionPane.showMessageDialog(dialogFrame, ex.getMessage(), "Adding error", JOptionPane.ERROR_MESSAGE);
						System.out.println(ex);
					}
				}				
			}
		});
		
		finalActions(parentFrame);
	}
	
}
