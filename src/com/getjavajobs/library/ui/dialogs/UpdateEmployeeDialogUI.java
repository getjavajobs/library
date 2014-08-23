package com.getjavajobs.library.ui.dialogs;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import com.getjavajobs.library.exceptions.ServiceException;
import com.getjavajobs.library.model.Employee;
import com.getjavajobs.library.services.EmployeeService;

/**
 * Диалог для обновления информации о сотруднике.
 */
public class UpdateEmployeeDialogUI extends AbstractDialogUI {

	private static final String dialogTitle = "Update reader information in library database";
	private JTextField[] textFields;	// Набор текстовых полей
	
	public UpdateEmployeeDialogUI(final JFrame parentFrame, final JTable employeesTable, final EmployeeService employeeService, final Employee employeeInfo) {
		super(parentFrame, dialogTitle);
		dialogFrame.setSize(dialogWidth, dialogHeight + 40); 
		
		textFields = new JTextField[5];
		for (int i = 0; i < textFields.length; ++i) {
			textFields[i] = new JTextField("");
		}
		
		// Заполняем уже имеющимися данными.
		textFields[0].setText(employeeInfo.getName());
		textFields[1].setText(employeeInfo.getSurname());
		textFields[2].setText(employeeInfo.getPatronymic());
		textFields[3].setText(employeeInfo.getDateOfBirth().toString());
		textFields[4].setText(employeeInfo.getPosition());
		
		/* Поля для ввода */
		addLabeledTextField(textFields[0], "Employee name:         ");
		addLabeledTextField(textFields[1], "Last name:                   ");
		addLabeledTextField(textFields[2], "Father name:               ");
		addLabeledTextField(textFields[3], "Birth date:                    ");
		addLabeledTextField(textFields[4], "Employee position:    ");
		
		/* Кнопка "Обновить информацию о сотруднике" */
		addButton("Update employee information", new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (isTextFieldsFilled(textFields)) {	// Если заполнены все текстовые поля.
					// Меняем поля на измененные.
					employeeInfo.setName(textFields[0].getText());
					employeeInfo.setSurname(textFields[1].getText());
					employeeInfo.setPatronymic(textFields[2].getText());
					DateFormat formatter = new SimpleDateFormat("d MMM yyyy");
					Date date = null;
					try {
						date = formatter.parse(textFields[3].getText());
					} catch (ParseException e1) {
						e1.printStackTrace();
					}
					employeeInfo.setDateOfBirth(date);	
					employeeInfo.setPosition(textFields[4].getText());
	
					// Пробуем изменить в базе.
					try {
						employeeService.update(employeeInfo);
						clearTextFields(textFields);
						JOptionPane.showMessageDialog(dialogFrame, "Employee information succsessfully updated!", "Success!", JOptionPane.INFORMATION_MESSAGE);
						
						// Меняем в таблице информацию.
						DefaultTableModel model = (DefaultTableModel) employeesTable.getModel();
						
						model.setValueAt(employeeInfo.getName(), employeesTable.getSelectedRow(), 1);
						model.setValueAt(employeeInfo.getSurname(), employeesTable.getSelectedRow(), 2);
						model.setValueAt(employeeInfo.getPatronymic(), employeesTable.getSelectedRow(), 3);
						model.setValueAt(employeeInfo.getDateOfBirth(), employeesTable.getSelectedRow(), 4);
						model.setValueAt(employeeInfo.getPosition(), employeesTable.getSelectedRow(), 5);
											
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