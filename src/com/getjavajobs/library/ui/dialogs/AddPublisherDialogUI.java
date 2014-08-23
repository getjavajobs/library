package com.getjavajobs.library.ui.dialogs;

import com.getjavajobs.library.exceptions.ServiceException;
import com.getjavajobs.library.model.Publisher;
import com.getjavajobs.library.services.PublisherService;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * ������ ��� ���������� ������ ������������.
 */
public class AddPublisherDialogUI extends AbstractDialogUI {

	private static final String dialogTitle = "Add new publisher into library database";
	private JTextField[] textFields;	// ����� ��������� �����
	
	public AddPublisherDialogUI(final JFrame parentFrame, final PublisherService publisherService) {
		super(parentFrame, dialogTitle);
		
		textFields = new JTextField[5];
		for (int i = 0; i < textFields.length; ++i) {
			textFields[i] = new JTextField("");
		}
		
		/* ���� ��� ����� */
		addLabeledTextField(textFields[0], "Publisher name:          ");
		addLabeledTextField(textFields[1], "City:                                 ");
		addLabeledTextField(textFields[2], "Phone number:            ");
		addLabeledTextField(textFields[3], "E-mail:                            ");
		addLabeledTextField(textFields[4], "Web page:                    ");
		
		/* ���������� ������ ������������ � ���� ������ */
		addButton("Add new publisher", new ActionListener() {	
			@Override
			public void actionPerformed(ActionEvent e) {
				if (isTextFieldsFilled(textFields)) {	// ���� ��� ���� ��������� --> �� �������� ��������� �� ������.
					Publisher newPublisher = new Publisher(textFields[0].getText(), textFields[1].getText(), textFields[2].getText(), 
							textFields[3].getText(),textFields[4].getText());
					try {
						publisherService.add(newPublisher);
						clearTextFields(textFields);
						JOptionPane.showMessageDialog(dialogFrame, "New publisher succsessfully added!", "Success!", JOptionPane.INFORMATION_MESSAGE);
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
