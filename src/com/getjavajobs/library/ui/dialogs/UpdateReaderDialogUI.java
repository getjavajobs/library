package com.getjavajobs.library.ui.dialogs;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import com.getjavajobs.library.exceptions.ServiceException;
import com.getjavajobs.library.model.Reader;
import com.getjavajobs.library.services.ReaderService;

/**
 * Диалог для обновления информации о читателе
 */
public class UpdateReaderDialogUI extends AbstractDialogUI {

	private static final String dialogTitle = "Update reader information in library database";
	private JTextField[] textFields;	// Набор текстовых полей
	
	public UpdateReaderDialogUI(final JFrame parentFrame, final JTable readersTable, final ReaderService readerService, final Reader readerInfo) {
		super(parentFrame, dialogTitle);
		dialogFrame.setSize(dialogWidth, dialogHeight + 40); 
		
		textFields = new JTextField[5];
		for (int i = 0; i < textFields.length; ++i) {
			textFields[i] = new JTextField("");
		}
		
		// Заполняем уже имеющимися данными.
		textFields[0].setText(readerInfo.getLastName());
		textFields[1].setText(readerInfo.getReaderName());
		textFields[2].setText(readerInfo.getAddress());
		textFields[3].setText(readerInfo.getPassportNumber());
		textFields[4].setText(readerInfo.getPhoneNumber());
		
		/* Поля для ввода */
		addLabeledTextField(textFields[0], "Last name:                ");
		addLabeledTextField(textFields[1], "Reader name:           ");
		addLabeledTextField(textFields[2], "Address:                    ");
		addLabeledTextField(textFields[3], "Passport number:   ");
		addLabeledTextField(textFields[4], "Phone number:        ");
		
		/* Кнопка "Обновить информацию о читателе" */
		addButton("Update reader information", new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (isTextFieldsFilled(textFields)) {	// Если заполнены все текстовые поля.
					// Меняем поля на измененные.
					readerInfo.setLastName(textFields[0].getText());
					readerInfo.setReaderName(textFields[1].getText());
					readerInfo.setAddress(textFields[2].getText());
					readerInfo.setPassportNumber(textFields[3].getText());
					readerInfo.setPhoneNumber(textFields[4].getText());
	
					// Пробуем изменить в базе.
					try {
						readerService.update(readerInfo);
						clearTextFields(textFields);
						JOptionPane.showMessageDialog(dialogFrame, "Reader information succsessfully updated!", "Success!", JOptionPane.INFORMATION_MESSAGE);
						
						// Меняем в таблице информацию.
						DefaultTableModel model = (DefaultTableModel) readersTable.getModel();
						
						model.setValueAt(readerInfo.getLastName(), readersTable.getSelectedRow(), 1);
						model.setValueAt(readerInfo.getReaderName(), readersTable.getSelectedRow(), 2);
						model.setValueAt(readerInfo.getAddress(), readersTable.getSelectedRow(), 3);
						model.setValueAt(readerInfo.getPassportNumber(), readersTable.getSelectedRow(), 4);
						model.setValueAt(readerInfo.getPhoneNumber(), readersTable.getSelectedRow(), 5);
											
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