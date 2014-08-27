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
 * ������ ��� ���������� ���������� � ��������
 */
public class UpdateReaderDialogUI extends AbstractDialogUI {

	private static final String dialogTitle = "Update reader information in library database";
	private JTextField[] textFields;	// ����� ��������� �����
	
	public UpdateReaderDialogUI(final JFrame parentFrame, final JTable readersTable, final ReaderService readerService, final Reader readerInfo) {
		super(parentFrame, dialogTitle);
		dialogFrame.setSize(dialogWidth, dialogHeight + 40); 
		
		textFields = new JTextField[5];
		for (int i = 0; i < textFields.length; ++i) {
			textFields[i] = new JTextField("");
		}
		
		// ��������� ��� ���������� �������.
		textFields[0].setText(readerInfo.getSecondName());
		textFields[1].setText(readerInfo.getFirstName());
		textFields[2].setText(readerInfo.getAddress());
		textFields[3].setText(readerInfo.getPassport());
		textFields[4].setText(readerInfo.getPhone());
		
		/* ���� ��� ����� */
		addLabeledTextField(textFields[0], "Last name:                ");
		addLabeledTextField(textFields[1], "Reader name:           ");
		addLabeledTextField(textFields[2], "Address:                    ");
		addLabeledTextField(textFields[3], "Passport number:   ");
		addLabeledTextField(textFields[4], "Phone number:        ");
		
		/* ������ "�������� ���������� � ��������" */
		addButton("Update reader information", new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (isTextFieldsFilled(textFields)) {	// ���� ��������� ��� ��������� ����.
					// ������ ���� �� ����������.
					readerInfo.setSecondName(textFields[0].getText());
					readerInfo.setFirstName(textFields[1].getText());
					readerInfo.setAddress(textFields[2].getText());
					readerInfo.setPassport(textFields[3].getText());
					readerInfo.setPhone(textFields[4].getText());
	
					// ������� �������� � ����.
					try {
						readerService.update(readerInfo);
						clearTextFields(textFields);
						JOptionPane.showMessageDialog(dialogFrame, "Reader information succsessfully updated!", "Success!", JOptionPane.INFORMATION_MESSAGE);
						
						// ������ � ������� ����������.
						DefaultTableModel model = (DefaultTableModel) readersTable.getModel();
						
						model.setValueAt(readerInfo.getSecondName(), readersTable.getSelectedRow(), 1);
						model.setValueAt(readerInfo.getFirstName(), readersTable.getSelectedRow(), 2);
						model.setValueAt(readerInfo.getAddress(), readersTable.getSelectedRow(), 3);
						model.setValueAt(readerInfo.getPassport(), readersTable.getSelectedRow(), 4);
						model.setValueAt(readerInfo.getPhone(), readersTable.getSelectedRow(), 5);
											
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