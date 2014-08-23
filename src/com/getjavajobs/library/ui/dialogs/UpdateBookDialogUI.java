package com.getjavajobs.library.ui.dialogs;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import com.getjavajobs.library.exceptions.ServiceException;
import com.getjavajobs.library.model.Author;
import com.getjavajobs.library.model.Book;
import com.getjavajobs.library.model.Genre;
import com.getjavajobs.library.model.Publisher;
import com.getjavajobs.library.services.AuthorService;
import com.getjavajobs.library.services.BookService;
import com.getjavajobs.library.services.PublisherService;

/**
 * Диалог для обновления данных о книге.
 */
public class UpdateBookDialogUI extends AbstractDialogUI {

	private static final String dialogTitle = "Update book information in library database";
	private JTextField[] textFields;	// Набор текстовых полей
	
	public UpdateBookDialogUI(final JFrame parentFrame, final JTable booksTable, final BookService bookService, 
			final AuthorService authorService, final PublisherService publisherService, final Book bookInfo) {
		super(parentFrame, dialogTitle);
		dialogFrame.setSize(dialogWidth, dialogHeight + 40); 
		
		textFields = new JTextField[4];
		for (int i = 0; i < textFields.length; ++i) {
			textFields[i] = new JTextField("");
		}
		
		// Заполняем уже имеющимися данными.
		textFields[0].setText(bookInfo.getName());
		textFields[1].setText(new Integer(bookInfo.getYear()).toString());
		textFields[2].setText(new Integer(bookInfo.getPagesNumber()).toString());
		textFields[3].setText(new Double(bookInfo.getPrice()).toString());
		
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
		
		// Устанавливаем то, что есть и в ComboBox-ах.
		for (int i = 0; i < authorsData.length; ++i) {
			if (bookInfo.getAuthor().equals((Author) authorsData[i])) {
				authorsList.setSelectedIndex(i);
				break;
			}
		}
		
		for (int i = 0; i < publishersData.length; ++i) {
			if (bookInfo.getPublisher().equals((Publisher) publishersData[i])) {
				publishersList.setSelectedIndex(i);
				break;
			}
		}
		
		/* Поля для ввода */
		addLabeledTextField(textFields[1], "Publish year:        ");
		addLabeledTextField(textFields[2], "Page count:          ");
		addLabeledTextField(textFields[3], "Price:                     ");
		
		/* Кнопка "Обновить информацию о книге" */
		addButton("Update book information", new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (isTextFieldsFilled(textFields)) {	// Если заполнены все текстовые поля.
					// Из выпадающих списков вытягиваем значения авторов и издателей.
					Author author = (Author) authorsList.getSelectedItem();
					Publisher publisher = (Publisher) publishersList.getSelectedItem();

					// Меняем поля на измененные.
					bookInfo.setName(textFields[0].getText());
					bookInfo.setGenreList(new ArrayList<Genre>());	// TODO 
					bookInfo.setAuthor(author);
					bookInfo.setPublisher(publisher);
					bookInfo.setYear(Integer.parseInt(textFields[1].getText()));
					bookInfo.setPagesNumber(Integer.parseInt(textFields[2].getText()));
					bookInfo.setPrice(Double.parseDouble(textFields[3].getText()));
					
					// Пробуем изменить в базе.
					try {
						bookService.update(bookInfo);
						clearTextFields(textFields);
						JOptionPane.showMessageDialog(dialogFrame, "Book information succsessfully updated!", "Success!", JOptionPane.INFORMATION_MESSAGE);
						
						// Меняем в таблице информацию.
						DefaultTableModel model = (DefaultTableModel) booksTable.getModel();
						model.setValueAt(bookInfo.getName(), booksTable.getSelectedRow(), 1);
						model.setValueAt(bookInfo.getGenreList(), booksTable.getSelectedRow(), 2);
						model.setValueAt(bookInfo.getAuthor(), booksTable.getSelectedRow(), 3);
						model.setValueAt(bookInfo.getPublisher(), booksTable.getSelectedRow(), 4);
						model.setValueAt(new Integer(bookInfo.getYear()), booksTable.getSelectedRow(), 5);
						model.setValueAt(new Integer(bookInfo.getPagesNumber()), booksTable.getSelectedRow(), 6);
						model.setValueAt(new Double(bookInfo.getPrice()), booksTable.getSelectedRow(), 7);
						
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