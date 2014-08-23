package com.getjavajobs.library.ui.dialogs;

import com.getjavajobs.library.exceptions.ServiceException;
import com.getjavajobs.library.model.Employee;
import com.getjavajobs.library.services.EmployeeService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * ������ ��� ���������� ���������� � ����������.
 */
public class UpdateEmployeeDialogUI extends AbstractDialogUI {

	private static final String dialogTitle = "Update reader information in library database";
	private JTextField[] textFields;	// ����� ��������� �����
	
	public UpdateEmployeeDialogUI(final JFrame parentFrame, final JTable employeesTable, final EmployeeService employeeService, final Employee employeeInfo) {
		super(parentFrame, dialogTitle);
		dialogFrame.setSize(dialogWidth, dialogHeight + 40); 
		
		textFields = new JTextField[5];
		for (int i = 0; i < textFields.length; ++i) {
			textFields[i] = new JTextField("");
		}
		
		// ��������� ��� ���������� �������.
		textFields[0].setText(employeeInfo.getEmployeeName());
		textFields[1].setText(employeeInfo.getLastName());
		textFields[2].setText(employeeInfo.getFatherName());
		textFields[3].setText(employeeInfo.getBirthDate());
		textFields[4].setText(employeeInfo.getEmployeePosition());
		
		/* ���� ��� ����� */
		addLabeledTextField(textFields[0], "Employee name:         ");
		addLabeledTextField(textFields[1], "Last name:                   ");
		addLabeledTextField(textFields[2], "Father name:               ");
		addLabeledTextField(textFields[3], "Birth date:                    ");
		addLabeledTextField(textFields[4], "Employee position:    ");
		
		/* ������ "�������� ���������� � ����������" */
		addButton("Update employee information", new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (isTextFieldsFilled(textFields)) {	// ���� ��������� ��� ��������� ����.
					// ������ ���� �� ����������.
					employeeInfo.setEmployeeName(textFields[0].getText());
					employeeInfo.setLastName(textFields[1].getText());
					employeeInfo.setFatherName(textFields[2].getText());
					employeeInfo.setBirthDate(textFields[3].getText());
					employeeInfo.setEmployeePosition(textFields[4].getText());
	
					// ������� �������� � ����.
					try {
						employeeService.update(employeeInfo);
						clearTextFields(textFields);
						JOptionPane.showMessageDialog(dialogFrame, "Employee information succsessfully updated!", "Success!", JOptionPane.INFORMATION_MESSAGE);
						
						// ������ � ������� ����������.
						DefaultTableModel model = (DefaultTableModel) employeesTable.getModel();
						
						model.setValueAt(employeeInfo.getEmployeeName(), employeesTable.getSelectedRow(), 1);
						model.setValueAt(employeeInfo.getLastName(), employeesTable.getSelectedRow(), 2);
						model.setValueAt(employeeInfo.getFatherName(), employeesTable.getSelectedRow(), 3);
						model.setValueAt(employeeInfo.getBirthDate(), employeesTable.getSelectedRow(), 4);
						model.setValueAt(employeeInfo.getEmployeePosition(), employeesTable.getSelectedRow(), 5);
											
						dialogFrame.dispose();
						parentFrame.setEnabled(true);
						parentFrame.setVisible(true);
					} catch (ServiceException ex) {
						JOptionPane.showMessageDialog(dialogFrame, ex.getMessage(), "Updating error", JOptionPane.ERROR_MESSAGE);
						System.out.println(ex);
					}
				}
			}
		});
		
		finalActions(parentFrame);
	}
	
}
