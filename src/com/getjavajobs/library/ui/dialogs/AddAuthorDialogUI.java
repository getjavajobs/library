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
import com.getjavajobs.library.model.Author;
import com.getjavajobs.library.services.AuthorService;

/**
 * Диалог добавления нового автора.
 */
public class AddAuthorDialogUI extends AbstractDialogUI {

	private static final String dialogTitle = "Add new author into library database";
	private JTextField[] textFields;	// Набор текстовых полей
	
	public AddAuthorDialogUI(final JFrame parentFrame, final AuthorService authorService) {
		super(parentFrame, dialogTitle);
		
		textFields = new JTextField[5];
		for (int i = 0; i < textFields.length; ++i) {
			textFields[i] = new JTextField("");
		}
		
		/* Поля для ввода */
		addLabeledTextField(textFields[0], "Author name:          ");
		addLabeledTextField(textFields[1], "Last name:              ");
		addLabeledTextField(textFields[2], "Father name:          ");
		addLabeledTextField(textFields[3], "Birth date:               ");
		addLabeledTextField(textFields[4], "Country:                  ");
		
		/* Добавление нового автора в базу данных */
		addButton("Add new author", new ActionListener() {	
			@Override
			public void actionPerformed(ActionEvent e) {
				if (isTextFieldsFilled(textFields)) {	// Если все поля заполнены --> не выдастся сообщение об ошибке.
					Author newAuthor = new Author();
					newAuthor.setName(textFields[0].getText());
					newAuthor.setSurname(textFields[1].getText());
					newAuthor.setPatronymic(textFields[2].getText());
					DateFormat formatter = new SimpleDateFormat("d MMM yyyy");
					Date date = null;
					try {
						date = formatter.parse(textFields[3].getText());
					} catch (ParseException e1) {
						e1.printStackTrace();
					}
					newAuthor.setBirthDate(date);
					newAuthor.setBirthPlace(textFields[4].getText());
					
					try {
						authorService.add(newAuthor);
						clearTextFields(textFields);
						JOptionPane.showMessageDialog(dialogFrame, "New author succsessfully added!", "Success!", JOptionPane.INFORMATION_MESSAGE);
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