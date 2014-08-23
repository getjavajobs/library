package com.getjavajobs.library.ui.dialogs;

import com.getjavajobs.library.exceptions.ServiceException;
import com.getjavajobs.library.model.Employee;
import com.getjavajobs.library.services.EmployeeService;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * ������ ���������� ��������.
 */
public class AddEmployeeDialogUI extends AbstractDialogUI {

	private static final String dialogTitle = "Add new employee into library database";
	private JTextField[] textFields;	// ����� ��������� �����
	
	public AddEmployeeDialogUI(final JFrame parentFrame, final EmployeeService employeeService) {
		super(parentFrame, dialogTitle);
		
		textFields = new JTextField[5];
		for (int i = 0; i < textFields.length; ++i) {
			textFields[i] = new JTextField("");
		}
		
		/* ���� ��� ����� */
		addLabeledTextField(textFields[0], "Employee name:         ");
		addLabeledTextField(textFields[1], "Last name:                   ");
		addLabeledTextField(textFields[2], "Father name:               ");
		addLabeledTextField(textFields[3], "Birth date:                    ");
		addLabeledTextField(textFields[4], "Employee position:    ");
		
		/* ���������� ������ ������ � ���� ������ */
		addButton("Add new employee", new ActionListener() {	
			@Override
			public void actionPerformed(ActionEvent e) {
				if (isTextFieldsFilled(textFields)) {	// ���� ��� ���� ��������� --> �� �������� ��������� �� ������.
					Employee newEmployee = new Employee(textFields[0].getText(), textFields[1].getText(), textFields[2].getText(), 
							textFields[3].getText(),textFields[4].getText());
					
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
