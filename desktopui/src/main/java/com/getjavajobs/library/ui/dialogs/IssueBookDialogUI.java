package com.getjavajobs.library.ui.dialogs;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import com.getjavajobs.library.exceptions.ServiceException;
import com.getjavajobs.library.model.Book;
import com.getjavajobs.library.model.Borrow;
import com.getjavajobs.library.model.Employee;
import com.getjavajobs.library.model.Reader;
import com.getjavajobs.library.services.BorrowService;
import com.getjavajobs.library.services.EmployeeService;
import com.getjavajobs.library.services.ReaderService;

/**
 * ������ ��� ������ ����� ��������.
 */
public class IssueBookDialogUI extends AbstractDialogUI {

	private static final String dialogTitle = "Issue book to reader";
	private JTextField[] textFields;	// ����� ��������� �����
	
	public IssueBookDialogUI(final JFrame parentFrame, final JTable booksTable, final ReaderService readerService, 
			final EmployeeService employeeService, final BorrowService borrowService, final Book bookInfo) {
		super(parentFrame, dialogTitle);
		
		/* ���������� � ���������� ����� */
		addLabel(bookInfo.toString());
		
		/* ���������� ������ ��������� � ����������� */
		Object[] readersData = null;
		Object[] employeeData = null;
		try {
			readersData = readerService.getAll().toArray();
			employeeData = employeeService.getAll().toArray();
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		
		final JComboBox<Object> readersList = addLabeledCombobox("Reader:                   ", readersData);
		final JComboBox<Object> employeesList = addLabeledCombobox("Employee:              ", employeeData);
		
		/* ���� �� ������� �������� �����. */
		textFields = new JTextField[2];
		for (int i = 0; i < textFields.length; ++i) {
			textFields[i] = new JTextField("");
		}
		addLabeledTextField(textFields[0], "Date of borrowing: ");
		addLabeledTextField(textFields[1], "Date of returning: ");
		
		/**
		 * ������ ������ �����.
		 */
		addButton("Issue book", new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if (isTextFieldsFilled(textFields)) {	// ���� ��������� ��� ��������� ����.
					if ((readersList.getItemCount() != 0) && (employeesList.getItemCount() != 0)) {	// ���� � ���������� ������� ���-�� ����.
						
						// �� ���������� ������� ���������� �������� �������� � ����������.
						Reader reader = (Reader) readersList.getSelectedItem();
						Employee employee = (Employee) employeesList.getSelectedItem();
						
						// �������� �������� ����� �� �����.
						try {
							Borrow newBorrow = new Borrow();
							newBorrow.setBook(bookInfo);
							newBorrow.setReader(reader);
							newBorrow.setEmployee(employee);
							DateFormat formatter = new SimpleDateFormat("d MMM yyyy");
							try {
								Date date = formatter.parse(textFields[0].getText());
								newBorrow.setDateOfBorrow(date);
								date = formatter.parse(textFields[1].getText());
								newBorrow.setDateOfReturn(date);
							} catch (ParseException e1) {
								e1.printStackTrace();
							}
							
							borrowService.add(newBorrow);							
							JOptionPane.showMessageDialog(dialogFrame, "Book successfully issued!", "Success!", JOptionPane.INFORMATION_MESSAGE);
							
							/**
							 * ������ ������ � �������.
							 */
							DefaultTableModel model = (DefaultTableModel) booksTable.getModel();
							model.setValueAt("Issued", booksTable.getSelectedRow(), 7);		// ������ ������ �� "������".
							
						} catch (ServiceException ex) {
							JOptionPane.showMessageDialog(dialogFrame, ex.getMessage(), "Issuing error", JOptionPane.ERROR_MESSAGE);
							System.out.println(ex);
						}
						
					} else {
						JOptionPane.showMessageDialog(dialogFrame, "There are no readers or employees in database! Issuing is not available.", "Text fields warning", JOptionPane.WARNING_MESSAGE);
					}
				}
			}
		});
		
		finalActions(parentFrame);
	}
}