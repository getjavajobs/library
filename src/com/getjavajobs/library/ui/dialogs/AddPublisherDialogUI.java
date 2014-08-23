package com.getjavajobs.library.ui.dialogs;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import com.getjavajobs.library.exceptions.ServiceException;
import com.getjavajobs.library.model.Publisher;
import com.getjavajobs.library.services.PublisherService;

/**
 * Диалог для добавления нового издательства.
 */
public class AddPublisherDialogUI extends AbstractDialogUI {

	private static final String dialogTitle = "Add new publisher into library database";
	private JTextField[] textFields;	// Набор текстовых полей
	
	public AddPublisherDialogUI(final JFrame parentFrame, final PublisherService publisherService) {
		super(parentFrame, dialogTitle);
		
		textFields = new JTextField[5];
		for (int i = 0; i < textFields.length; ++i) {
			textFields[i] = new JTextField("");
		}
		
		/* Поля для ввода */
		addLabeledTextField(textFields[0], "Publisher name:          ");
		addLabeledTextField(textFields[1], "City:                                 ");
		addLabeledTextField(textFields[2], "Phone number:            ");
		addLabeledTextField(textFields[3], "E-mail:                            ");
		addLabeledTextField(textFields[4], "Web page:                    ");
		
		/* Добавление нового издательства в базу данных */
		addButton("Add new publisher", new ActionListener() {	
			@Override
			public void actionPerformed(ActionEvent e) {
				if (isTextFieldsFilled(textFields)) {	// Если все поля заполнены --> не выдастся сообщение об ошибке.
					Publisher newPublisher = new Publisher();
					newPublisher.setName(textFields[0].getText());
					newPublisher.setCity(textFields[1].getText());
					newPublisher.setPhoneNumber(textFields[2].getText());
					newPublisher.setEmail(textFields[3].getText());
					newPublisher.setSiteAddress(textFields[4].getText());
					
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
