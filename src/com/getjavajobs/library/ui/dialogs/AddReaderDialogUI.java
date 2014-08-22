package com.getjavajobs.library.ui.dialogs;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import com.getjavajobs.library.exceptions.ServiceException;
import com.getjavajobs.library.model.Reader;
import com.getjavajobs.library.services.ReaderService;

/**
 * Диалог добавления читателя.
 */
public class AddReaderDialogUI extends AbstractDialogUI {

	private static final String dialogTitle = "Add new reader into library database";
	private JTextField[] textFields;	// Набор текстовых полей
	
	public AddReaderDialogUI(final JFrame parentFrame, final ReaderService readerService) {
		super(parentFrame, dialogTitle);
		
		textFields = new JTextField[5];
		for (int i = 0; i < textFields.length; ++i) {
			textFields[i] = new JTextField("");
		}
		
		/* Поля для ввода */
		addLabeledTextField(textFields[0], "Last name:                ");
		addLabeledTextField(textFields[1], "Reader name:           ");
		addLabeledTextField(textFields[2], "Address:                    ");
		addLabeledTextField(textFields[3], "Passport number:   ");
		addLabeledTextField(textFields[4], "Phone number:        ");
		
		/* Добавление нового автора в базу данных */
		addButton("Add new reader", new ActionListener() {	
			@Override
			public void actionPerformed(ActionEvent e) {
				if (isTextFieldsFilled(textFields)) {	// Если все поля заполнены --> не выдастся сообщение об ошибке.
					Reader newReader = new Reader(textFields[0].getText(), textFields[1].getText(), textFields[2].getText(), 
							textFields[3].getText(),textFields[4].getText());
					
					try {
						readerService.add(newReader);
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
