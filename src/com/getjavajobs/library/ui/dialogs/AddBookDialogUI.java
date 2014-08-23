package com.getjavajobs.library.ui.dialogs;


import com.getjavajobs.library.exceptions.ServiceException;
import com.getjavajobs.library.model.Author;
import com.getjavajobs.library.model.Book;
import com.getjavajobs.library.model.Publisher;
import com.getjavajobs.library.services.AuthorService;
import com.getjavajobs.library.services.BookService;
import com.getjavajobs.library.services.PublisherService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * ������ ��� ���������� ���� � ����������.
 */
public class AddBookDialogUI extends AbstractDialogUI {

	private static final String dialogTitle = "Add new book into library database";
	private JTextField[] textFields;	// ����� ��������� �����
	
	public AddBookDialogUI(final JFrame parentFrame, final JTable booksTable, final BookService bookService, 
			final AuthorService authorService, final PublisherService publisherService) {
		super(parentFrame, dialogTitle);
		dialogFrame.setSize(dialogWidth, dialogHeight + 40); 
		
		textFields = new JTextField[4];
		for (int i = 0; i < textFields.length; ++i) {
			textFields[i] = new JTextField("");
		}
		
		/* ���� ��� ����� */
		addLabeledTextField(textFields[0], "Book name:          ");
		
		/* ���������� ������ ������� � ��������� */
		Object[] authorsData = null;
		Object[] publishersData = null;
		try {
			authorsData = authorService.getAll().toArray();
			publishersData = publisherService.getAll().toArray();
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		final JComboBox<Object> authorsList = addLabeledCombobox("Author:                   ", authorsData);
		final JComboBox<Object> publishersList = addLabeledCombobox("Publisher:              ", publishersData);
		
		/* ���� ��� ����� */
		addLabeledTextField(textFields[1], "Publish year:        ");
		addLabeledTextField(textFields[2], "Page count:          ");
		addLabeledTextField(textFields[3], "Price:                     ");
		
		/* ������ "�������� ����� �����" */
		addButton("Add new book", new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (isTextFieldsFilled(textFields)) {	// ���� ��������� ��� ��������� ����.
					if ((authorsList.getItemCount() != 0) && (publishersList.getItemCount() != 0)) {	// ���� � ���������� ������� ���-�� ����.
						
						// �� ���������� ������� ���������� �������� ������� � ���������.
						Author author = (Author) authorsList.getSelectedItem();
						Publisher publisher = (Publisher) publishersList.getSelectedItem();
						
						// ������� Book.
						Book newBook = new Book(textFields[0].getText(), author, publisher, Integer.parseInt(textFields[1].getText()), Integer.parseInt(textFields[2].getText()), Double.parseDouble(textFields[3].getText()));
					
						// �������� �������� ��� � ����.
						try {
							bookService.add(newBook);
							clearTextFields(textFields);
							JOptionPane.showMessageDialog(dialogFrame, "New book succsessfully added!", "Success!", JOptionPane.INFORMATION_MESSAGE);
							
							/**
							 * ��������� ������ � �������, ������� ��������� �� parentFrame.
							 */
							DefaultTableModel model = (DefaultTableModel) booksTable.getModel();
							model.addRow(new Object[]{ new Integer(newBook.getId()), 
									newBook.getName(),
									newBook.getAuthor(), // ��, ��� �� ����� ���������.
									newBook.getPublisher(),
									new Integer(newBook.getYear()),
									new Integer(newBook.getPagesNumber()),
									new Double(newBook.getPrice()),
									"Free",			 	// ������
									"Issue",
									"Return",
									"Prolong",		// ������.
									"Update",
									"Remove"});
							
						} catch (ServiceException ex) {
							JOptionPane.showMessageDialog(dialogFrame, ex.getMessage(), "Adding error", JOptionPane.ERROR_MESSAGE);
							System.out.println(ex);
						}
					} else {
						JOptionPane.showMessageDialog(dialogFrame, "There are no authors or publishers in database! Adding is not available.", "Text fields warning", JOptionPane.WARNING_MESSAGE);
					}
				}
				
			}
		});
		
		finalActions(parentFrame); 
	}
	
}