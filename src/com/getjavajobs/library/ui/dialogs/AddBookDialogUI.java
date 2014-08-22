package com.getjavajobs.library.ui.dialogs;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import com.getjavajobs.library.exceptions.ServiceException;
import com.getjavajobs.library.model.Author;
import com.getjavajobs.library.model.Book;
import com.getjavajobs.library.model.Publisher;
import com.getjavajobs.library.services.AuthorService;
import com.getjavajobs.library.services.BookService;
import com.getjavajobs.library.services.PublisherService;

/**
 * Диалог для добавления книг в библиотеку.
 */
public class AddBookDialogUI extends AbstractDialogUI {

	private static final String dialogTitle = "Add new book into library database";
	private JTextField[] textFields;	// Набор текстовых полей
	
	public AddBookDialogUI(final JFrame parentFrame, final JTable booksTable, final BookService bookService, 
			final AuthorService authorService, final PublisherService publisherService) {
		super(parentFrame, dialogTitle);
		dialogFrame.setSize(dialogWidth, dialogHeight + 40); 
		
		textFields = new JTextField[4];
		for (int i = 0; i < textFields.length; ++i) {
			textFields[i] = new JTextField("");
		}
		
		/* Поля для ввода */
		addLabeledTextField(textFields[0], "Book name:          ");
		
		/* Выпадающие списки авторов и издателей */
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
		
		/* Поля для ввода */
		addLabeledTextField(textFields[1], "Publish year:        ");
		addLabeledTextField(textFields[2], "Page count:          ");
		addLabeledTextField(textFields[3], "Price:                     ");
		
		/* Кнопка "Добавить новую книгу" */
		addButton("Add new book", new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (isTextFieldsFilled(textFields)) {	// Если заполнены все текстовые поля.
					if ((authorsList.getItemCount() != 0) && (publishersList.getItemCount() != 0)) {	// Если в выпадающих списках что-то есть.
						
						// Из выпадающих списков вытягиваем значения авторов и издателей.
						Author author = (Author) authorsList.getSelectedItem();
						Publisher publisher = (Publisher) publishersList.getSelectedItem();
						
						// Создаем Book.
						Book newBook = new Book(textFields[0].getText(), author, publisher, Integer.parseInt(textFields[1].getText()), Integer.parseInt(textFields[2].getText()), Double.parseDouble(textFields[3].getText()));
					
						// Пытаемся добавить его в базу.
						try {
							bookService.add(newBook);
							clearTextFields(textFields);
							JOptionPane.showMessageDialog(dialogFrame, "New book succsessfully added!", "Success!", JOptionPane.INFORMATION_MESSAGE);
							
							/**
							 * Добавляем запись в таблицу, которая находится на parentFrame.
							 */
							DefaultTableModel model = (DefaultTableModel) booksTable.getModel();
							model.addRow(new Object[]{ new Integer(newBook.getId()), 
									newBook.getName(),
									newBook.getAuthor(), // хз, что из этого получится.
									newBook.getPublisher(),
									new Integer(newBook.getYear()),
									new Integer(newBook.getPagesNumber()),
									new Double(newBook.getPrice()),
									"Free",			 	// статус
									"Issue",
									"Return",
									"Prolong",		// кнопки.
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