package com.getjavajobs.library.ui.dialogs;

import com.getjavajobs.library.exceptions.ServiceException;
import com.getjavajobs.library.model.Author;
import com.getjavajobs.library.services.AuthorService;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * ������ ���������� ������ ������.
 */
public class AddAuthorDialogUI extends AbstractDialogUI {

	private static final String dialogTitle = "Add new author into library database";
	private JTextField[] textFields;	// ����� ��������� �����
	
	public AddAuthorDialogUI(final JFrame parentFrame, final AuthorService authorService) {
		super(parentFrame, dialogTitle);
		
		textFields = new JTextField[5];
		for (int i = 0; i < textFields.length; ++i) {
			textFields[i] = new JTextField("");
		}
		
		/* ���� ��� ����� */
		addLabeledTextField(textFields[0], "Author name:          ");
		addLabeledTextField(textFields[1], "Last name:              ");
		addLabeledTextField(textFields[2], "Father name:          ");
		addLabeledTextField(textFields[3], "Birth date:               ");
		addLabeledTextField(textFields[4], "Country:                  ");
		
		/* ���������� ������ ������ � ���� ������ */
		addButton("Add new author", new ActionListener() {	
			@Override
			public void actionPerformed(ActionEvent e) {
				if (isTextFieldsFilled(textFields)) {	// ���� ��� ���� ��������� --> �� �������� ��������� �� ������.
					Author newAuthor = new Author(textFields[0].getText(), textFields[1].getText(), textFields[2].getText(), 
							textFields[3].getText(),textFields[4].getText());
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