package com.getjavajobs.library.ui;

import com.getjavajobs.library.exceptions.ServiceException;
import com.getjavajobs.library.model.*;
import com.getjavajobs.library.services.*;
import com.getjavajobs.library.ui.dialogs.*;
import com.getjavajobs.library.ui.tables.ButtonColumn;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.List;


/**
 * ������� ����� UI.
 */
public class LibraryUI {
	
	public static void main(String[] args) {
		new LibraryUI();
	}
	
	/* ������� � ��������� ����� */
	private static final int windowWidth = 1200;
	private static final int windowHeight = 800;	
	private static final int startXPosition = 500;
	private static final int startYPosition = 100;
	
	/* ��������� ������ */
	private static final int tableWidth = 1000;
	private static final int tableHeight = 500;
	private static final String[] booksTableColumnNames = {
		"Book ID", "Book name", "Author", "Publisher", "Publish year", "Page count", "Price", "Status", "", "", "Actions", "", ""
	};
	private static final String[] readersTableColumnNames = {
		"Reader ID", "Last name", "Reader name", "Address", "Passport number", "Phone number", "", "Actions" 
	};
	private static final String[] employeesTableColumnNames = {
		"Employee ID", "Employee name", "Last name", "Father name", "Birth date", "Employee position", "", "Actions" 
	};

	private static JTable booksTable = null;
	private static JPanel booksTablePanel = null;
	private static JTable readersTable = null;
	private static JPanel readersTablePanel = null;
	private static JTable employeesTable = null;
	private static JPanel employeesTablePanel = null;
	
	/* �������� �������� ��� ������ � ������� */
	private static final AuthorService authorService = new AuthorService();
	private static final BookService bookService = new BookService();
	private static final BorrowService borrowService = new BorrowService();
	private static final EmployeeService employeeService = new EmployeeService();
	private static final PublisherService publisherService = new PublisherService();
	private static final ReaderService readerService = new ReaderService();
	
	/* ������ */
	private static final Cursor handCursor = new Cursor(Cursor.HAND_CURSOR);
	
	public LibraryUI() {
		final JFrame mainFrame = new JFrame("Library project");
		mainFrame.setSize(windowWidth, windowHeight);
		mainFrame.setLocation(startXPosition, startYPosition);
		mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
			
		booksTableConfiguration(mainFrame);		// ������������ ������� ����.
		booksTablePanel.setVisible(true);
		
		leftPanelConfiguration(mainFrame);		// ������������ ����� ������� ������.
		rightPanelConfiguration(mainFrame);		// ������������ ������ ������� ������.
		
		mainFrame.setVisible(true);
	}
	
	/**
	 * ������������ ������� �����������.
	 */
	private void employeesTableConfiguration(final JFrame mainFrame) {
		JPanel tablePanel = new JPanel(new BorderLayout());
		tablePanel.setPreferredSize(new Dimension(tableWidth, tableHeight));
		
		// ������� ������ ��������.
		DefaultTableModel model = new DefaultTableModel(employeesTableColumnNames, 0) {
			private static final long serialVersionUID = 1L;

			// ������� ������ �� ������ ������� ��������� ��-������ �������������� ������ �������. (��� ������-�������).
			@SuppressWarnings({ "unchecked", "rawtypes" })
			@Override
            public Class getColumnClass(int column) {
                return getValueAt(0, column).getClass();
            }
			
			@Override
        	public boolean isCellEditable(int row, int column) {
        		return (column > 5);
        	}
		};
		
		// ��������� ����������� �� ��.
		try {
			List<Employee> employeesList = employeeService.getAll();
			int rowCount = employeesList.size();
			if (rowCount != 0) {
				for (int i = 0; i < rowCount; ++i) {
					Employee anotherEmployee = employeesList.get(i);
					model.addRow(new Object[] {
							// ������
							new Integer(anotherEmployee.getId()),
							anotherEmployee.getEmployeeName(),
							anotherEmployee.getLastName(),
							anotherEmployee.getFatherName(),
							anotherEmployee.getBirthDate(),
							anotherEmployee.getEmployeePosition(),
							
							// ������
							"Update",
							"Remove"
					});
				}
			}
		} catch (ServiceException ex) {
			JOptionPane.showMessageDialog(mainFrame, ex.getMessage(), "Getting employees error", JOptionPane.ERROR_MESSAGE);
			System.out.println(ex);
		}
		
		employeesTable = new JTable(model);		// ������������� ����������� ������.
		
		// ������������� ������� � ��������.
		configureUpdateEmployeeButtonColumn(mainFrame, employeesTable, 6);
		configureRemoveEmployeeButtonColumn(mainFrame, employeesTable, 7);
		
		// ��������� ���������.
		employeesTable.setCursor(new Cursor(Cursor.HAND_CURSOR));
		employeesTable.setPreferredSize(new Dimension(tableWidth, tableHeight));
		employeesTable.getTableHeader().setReorderingAllowed(false);	// ����� ��������� �������������� ��������.
		JScrollPane scrollPane = new JScrollPane(employeesTable);		// ����� �������� ��������� �����. 
		tablePanel.add(scrollPane);	
		
		tablePanel.add(new JLabel("Employees table"), BorderLayout.NORTH);
		employeesTablePanel = tablePanel;
		//mainFrame.add(tablePanel, BorderLayout.SOUTH);
	}
	
	/**
	 * ������������ ������� � �������� "�������� ���������� � ��������".
	 */
	private void configureUpdateEmployeeButtonColumn(final JFrame mainFrame, final JTable table, int columnIndex) {
		new ButtonColumn(table, columnIndex, new ActionListener() {
			// �������� ��� ������� �� ������.
			@Override
			public void actionPerformed(ActionEvent e) {
				TableModel tm = table.getModel();
				int selectedRowIndex = table.getSelectedRow();

				Employee selectedEmployee = new Employee(
						(Integer) tm.getValueAt(selectedRowIndex, 0), 
						(String) tm.getValueAt(selectedRowIndex, 1), 
						(String) tm.getValueAt(selectedRowIndex, 2), 
						(String) tm.getValueAt(selectedRowIndex, 3), 
						(String) tm.getValueAt(selectedRowIndex, 4), 
						(String) tm.getValueAt(selectedRowIndex, 5));
							
				AbstractDialogUI updateDialog = new UpdateEmployeeDialogUI(mainFrame, table, employeeService, selectedEmployee);
				mainFrame.setEnabled(false);
				updateDialog.setVisible();
			}
		});
	}
	
	/**
	 * ������������ ������� � �������� "������� ���������� � ��������".
	 */
	private void configureRemoveEmployeeButtonColumn(final JFrame mainFrame, final JTable table, int columnIndex) {
		new ButtonColumn(table, columnIndex, new ActionListener() {
			// �������� ��� ������� �� ������.
			@Override
			public void actionPerformed(ActionEvent e) {
				TableModel tm = table.getModel();
				int selectedRowIndex = table.getSelectedRow();
				int employeeID = (Integer) tm.getValueAt(selectedRowIndex, 0);

				// ������� �� ���� ������
				try {
					employeeService.delete(employeeID);
					JOptionPane.showMessageDialog(mainFrame, "Employee successfully removed!", "Success!", JOptionPane.INFORMATION_MESSAGE);
					
					/*
					 * TODO �������� � ��������� ��������� ������
					 */
					DefaultTableModel dtm = (DefaultTableModel) table.getModel();
					if (table.getRowCount() != 1) {
						dtm.removeRow(selectedRowIndex);
					} else {
						dtm.addRow(new Object[]{""});
						dtm.removeRow(selectedRowIndex);
						dtm.removeRow(selectedRowIndex);
					}
				} catch (ServiceException ex) {
					JOptionPane.showMessageDialog(mainFrame, ex.getMessage(), "Removing error", JOptionPane.ERROR_MESSAGE);
					System.out.println(ex);
				}
			}
		});
	}
	
	/**
	 * ������������ ������� ���������.
	 */
	private void readersTableConfiguration(final JFrame mainFrame) {
		JPanel tablePanel = new JPanel(new BorderLayout());
		tablePanel.setPreferredSize(new Dimension(tableWidth, tableHeight));
		
		// ������� ������ ��������.
		DefaultTableModel model = new DefaultTableModel(readersTableColumnNames, 0) {
			private static final long serialVersionUID = 1L;

			// ������� ������ �� ������ ������� ��������� ��-������ �������������� ������ �������. (��� ������-�������).
			@SuppressWarnings({ "unchecked", "rawtypes" })
			@Override
            public Class getColumnClass(int column) {
                return getValueAt(0, column).getClass();
            }
			
			@Override
        	public boolean isCellEditable(int row, int column) {
        		return (column > 5);
        	}
		};
		
		// ��������� ��������� �� ��.
		try {
			List<Reader> readersList = readerService.getAll();
			int rowCount = readersList.size();
			if (rowCount != 0) {
				for (int i = 0; i < rowCount; ++i) {
					Reader anotherReader = readersList.get(i);
					model.addRow(new Object[] {
							// ������
							new Integer(anotherReader.getId()),
							anotherReader.getLastName(),
							anotherReader.getReaderName(),
							anotherReader.getAddress(),
							anotherReader.getPassportNumber(),
							anotherReader.getPhoneNumber(),
							
							// ������
							"Update",
							"Remove"
					});
				}
			}
		} catch (ServiceException ex) {
			JOptionPane.showMessageDialog(mainFrame, ex.getMessage(), "Getting readers error", JOptionPane.ERROR_MESSAGE);
			System.out.println(ex);
		}
		
		readersTable = new JTable(model);		// ������������� ����������� ������.
		
		// ������������� ������� � ��������.
		configureUpdateReaderButtonColumn(mainFrame, readersTable, 6);
		configureRemoveReaderButtonColumn(mainFrame, readersTable, 7);
		
		// ��������� ���������.
		readersTable.setCursor(new Cursor(Cursor.HAND_CURSOR));
		readersTable.setPreferredSize(new Dimension(tableWidth, tableHeight));
		readersTable.getTableHeader().setReorderingAllowed(false);	// ����� ��������� �������������� ��������.
		JScrollPane scrollPane = new JScrollPane(readersTable);		// ����� �������� ��������� �����. 
		tablePanel.add(scrollPane);	
		
		tablePanel.add(new JLabel("Readers table"), BorderLayout.NORTH);
		readersTablePanel = tablePanel;
		//mainFrame.add(tablePanel, BorderLayout.SOUTH);
	}
	
	/**
	 * ������������ ������� � �������� "�������� ���������� � ��������".
	 */
	private void configureUpdateReaderButtonColumn(final JFrame mainFrame, final JTable table, int columnIndex) {
		new ButtonColumn(table, columnIndex, new ActionListener() {
			// �������� ��� ������� �� ������.
			@Override
			public void actionPerformed(ActionEvent e) {
				TableModel tm = table.getModel();
				int selectedRowIndex = table.getSelectedRow();

				Reader selectedReader = new Reader(
						(Integer) tm.getValueAt(selectedRowIndex, 0), 
						(String) tm.getValueAt(selectedRowIndex, 1), 
						(String) tm.getValueAt(selectedRowIndex, 2), 
						(String) tm.getValueAt(selectedRowIndex, 3), 
						(String) tm.getValueAt(selectedRowIndex, 4), 
						(String) tm.getValueAt(selectedRowIndex, 5));
							
				AbstractDialogUI updateDialog = new UpdateReaderDialogUI(mainFrame, table, readerService, selectedReader);
				mainFrame.setEnabled(false);
				updateDialog.setVisible();
			}
		});
	}
	
	/**
	 * ������������ ������� � �������� "������� ���������� � ��������".
	 */
	private void configureRemoveReaderButtonColumn(final JFrame mainFrame, final JTable table, int columnIndex) {
		new ButtonColumn(table, columnIndex, new ActionListener() {
			// �������� ��� ������� �� ������.
			@Override
			public void actionPerformed(ActionEvent e) {
				TableModel tm = table.getModel();
				int selectedRowIndex = table.getSelectedRow();
				int readerID = (Integer) tm.getValueAt(selectedRowIndex, 0);

				// ������� �� ���� ������
				try {
					readerService.delete(readerID);
					JOptionPane.showMessageDialog(mainFrame, "Reader successfully removed!", "Success!", JOptionPane.INFORMATION_MESSAGE);
					
					/*
					 * TODO �������� � ��������� ��������� ������
					 */
					DefaultTableModel dtm = (DefaultTableModel) table.getModel();
					if (table.getRowCount() != 1) {
						dtm.removeRow(selectedRowIndex);
					} else {
						dtm.addRow(new Object[]{""});
						dtm.removeRow(selectedRowIndex);
						dtm.removeRow(selectedRowIndex);
					}
				} catch (ServiceException ex) {
					JOptionPane.showMessageDialog(mainFrame, ex.getMessage(), "Removing error", JOptionPane.ERROR_MESSAGE);
					System.out.println(ex);
				}
			}
		});
	}
	
	/**
	 * ������������ ������� ����.
	 */
	private void booksTableConfiguration(final JFrame mainFrame) {
		JPanel tablePanel = new JPanel(new BorderLayout());
		tablePanel.setPreferredSize(new Dimension(tableWidth, tableHeight));
		
		// ������� ������ �������.
		DefaultTableModel model = new DefaultTableModel(booksTableColumnNames, 0) {
			private static final long serialVersionUID = 1L;

			// ������� ������ �� ������ ������� ��������� ��-������ �������������� ������ �������. (��� ������-�������).
			@SuppressWarnings({ "unchecked", "rawtypes" })
			@Override
            public Class getColumnClass(int column) {
                return getValueAt(0, column).getClass();
            }
			
			@Override
        	public boolean isCellEditable(int row, int column) {
        		return (column > 5);
        	}
		};
		
		try {
			/*
			 * ��������� ������ ���� �� ���� ������.
			 */
			List<Book> booksList = bookService.getAll();
			int rowCount = booksList.size();
			if (rowCount != 0) {
				// ���� ������ �� ���� --> ��������� ������� �������.
				List<Borrow> borrowsList = borrowService.getAll();
				int borrowsCount = borrowsList.size();
				Date todayDate = new Date();
				
				for (int i = 0; i < rowCount; ++i) {
					Book anotherBook = booksList.get(i);
					String status = "Free";
					for (int j = 0; j < borrowsCount; ++j) {
						Borrow borrow = borrowsList.get(i);
						if (borrow.getBook().getId() == anotherBook.getId()) {	// ���� � ������ ����� ����� ����� - ��� ���� ������, ���� ����������
							if (todayDate.compareTo(borrow.getDateOfReturn()) > 0) {
								status = "Overdue";
							} else {
								status = "Issued";
							}
						}
					}
					
					// ������� ����� �������.
					model.addRow(new Object[] {
							// ������
							new Integer(anotherBook.getId()),
							anotherBook.getName(),
							anotherBook.getAuthor(),
							anotherBook.getPublisher(),
							new Integer(anotherBook.getYear()),
							new Integer(anotherBook.getPagesNumber()),
							new Double(anotherBook.getPrice()),
							status,
							// ������
							"Issue",
							"Return",
							"Prolong",
							"Update",
							"Remove"
					});
				}
			}
		} catch (ServiceException ex) {
			JOptionPane.showMessageDialog(mainFrame, ex.getMessage(), "Getting books error", JOptionPane.ERROR_MESSAGE);
			System.out.println(ex);
		}
		
		booksTable = new JTable(model);		// ������������� ����������� ������.
		
		// ������������� ������� � ��������.
		configureIssueBookButtonColumn(mainFrame, booksTable, 8);
		configureReturnBookButtonColumn(mainFrame, booksTable, 9);
		configureProlongBookButtonColumn(mainFrame, booksTable, 10);
		configureUpdateBookButtonColumn(mainFrame, booksTable, 11);
		configureRemoveBookButtonColumn(mainFrame, booksTable, 12);
		
		// ��������� ���������.
		booksTable.setCursor(new Cursor(Cursor.HAND_CURSOR));
		booksTable.setPreferredSize(new Dimension(tableWidth, tableHeight));
		booksTable.getTableHeader().setReorderingAllowed(false);	// ����� ��������� �������������� ��������.
		JScrollPane scrollPane = new JScrollPane(booksTable);		// ����� �������� ��������� �����. 
		tablePanel.add(scrollPane);	
		
		tablePanel.add(new JLabel("Books table"), BorderLayout.NORTH);
		booksTablePanel = tablePanel;
		mainFrame.add(tablePanel, BorderLayout.SOUTH);
	}
	
	/**
	 * ������������ ������� � �������� "������� �����".
	 */
	private void configureRemoveBookButtonColumn(final JFrame mainFrame, final JTable table, int columnIndex) {
		new ButtonColumn(table, columnIndex, new ActionListener() {
			// �������� ��� ������� �� ������.
			@Override
			public void actionPerformed(ActionEvent e) {
				TableModel tm = table.getModel();
				int selectedRowIndex = table.getSelectedRow();
				int bookID = (Integer) tm.getValueAt(selectedRowIndex, 0);
				
				// ������� �� ���� ������ �����.
				try {
					bookService.delete(bookID);
					JOptionPane.showMessageDialog(mainFrame, "Book successfully removed!", "Success!", JOptionPane.INFORMATION_MESSAGE);
					
					/*
					 * TODO �������� � ��������� ��������� ������
					 */
					DefaultTableModel dtm = (DefaultTableModel) table.getModel();
					if (table.getRowCount() != 1) {
						dtm.removeRow(selectedRowIndex);
					} else {
						dtm.addRow(new Object[]{""});
						dtm.removeRow(selectedRowIndex);
						dtm.removeRow(selectedRowIndex);
					}
				} catch (ServiceException ex) {
					JOptionPane.showMessageDialog(mainFrame, ex.getMessage(), "Removing error", JOptionPane.ERROR_MESSAGE);
					System.out.println(ex);
				} 

			}
		});
		
	}
	
	/**
	 * ������������ ������� � �������� "�������� ������ � �����".
	 */
	private void configureUpdateBookButtonColumn(final JFrame mainFrame, final JTable table, int columnIndex) {
		new ButtonColumn(table, columnIndex, new ActionListener() {
			// �������� ��� ������� �� ������.
			@Override
			public void actionPerformed(ActionEvent e) {
				/**
				 * ���������� ������ � ���������� ������� ���������, ����������� � ����������� �� ��������� �����.
				 */
				TableModel tm = table.getModel();
				int selectedRowIndex = table.getSelectedRow();

				Book selectedBook = new Book(
						(Integer) tm.getValueAt(selectedRowIndex, 0),	// id
						(String) tm.getValueAt(selectedRowIndex, 1),	// name
						(Author) tm.getValueAt(selectedRowIndex, 2),	// author
						(Publisher) tm.getValueAt(selectedRowIndex, 3),	// publisher
						(Integer) tm.getValueAt(selectedRowIndex, 4),	// publisher year
						(Integer) tm.getValueAt(selectedRowIndex, 5),	// page count
						(Double) tm.getValueAt(selectedRowIndex, 6)		// price
						);
				
				AbstractDialogUI updateDialog = new UpdateBookDialogUI(mainFrame, table, bookService, authorService, publisherService, selectedBook);
				mainFrame.setEnabled(false);
				updateDialog.setVisible();
			}
		});
	}
	
	/**
	 * ������������ ������� � �������� "�������� ������ �����".
	 */
	private void configureProlongBookButtonColumn(final JFrame mainFrame, final JTable table, int columnIndex) {
		new ButtonColumn(table, columnIndex, new ActionListener() {
			// �������� ��� ������� �� ������.
			@Override
			public void actionPerformed(ActionEvent e) {
				TableModel tm = table.getModel();
				int selectedRowIndex = table.getSelectedRow();
				int bookID = (Integer) tm.getValueAt(selectedRowIndex, 0);
				
				if (tm.getValueAt(selectedRowIndex, 7).equals("Overdue")) {	// ��������, ������ ���� ����� ����-�� ������ � ��� ����������.
					
					try {
						Borrow bookBorrow = borrowService.get(bookID);
						bookBorrow.setDateOfReturn(new Date());	// ������ ���� �����������.
						borrowService.update(bookBorrow);
						JOptionPane.showMessageDialog(mainFrame, "Book successfully prolonged!", "Success!", JOptionPane.INFORMATION_MESSAGE);
						
						// ������ ������ � ������ � ������.
						tm.setValueAt("Issued", selectedRowIndex, 7);
					} catch (ServiceException ex) {
						JOptionPane.showMessageDialog(mainFrame, ex.getMessage(), "Book prolonging error", JOptionPane.ERROR_MESSAGE);
						System.out.println(ex);
					}
				}
			}
		});
	}
	
	/**
	 * ������������ ������� � �������� "������� �����".
	 */
	private void configureReturnBookButtonColumn(final JFrame mainFrame, final JTable table, int columnIndex) {
		new ButtonColumn(table, columnIndex, new ActionListener() {
			// �������� ��� ������� �� ������.
			@Override
			public void actionPerformed(ActionEvent e) {
				TableModel tm = table.getModel();
				int selectedRowIndex = table.getSelectedRow();
				int bookID = (Integer) tm.getValueAt(selectedRowIndex, 0);
				
				if (!tm.getValueAt(selectedRowIndex, 7).equals("Free")) {	// ��������, ������ ���� ����� ����-�� ������ ��� ��� ����������.
					try {
						borrowService.remove(bookID);
						JOptionPane.showMessageDialog(mainFrame, "Book successfully returned!", "Success!", JOptionPane.INFORMATION_MESSAGE);
						
						// ������ ������ � ������ � ������.
						tm.setValueAt("Free", selectedRowIndex, 7);
					} catch (ServiceException ex) {
						JOptionPane.showMessageDialog(mainFrame, ex.getMessage(), "Book returning error", JOptionPane.ERROR_MESSAGE);
						System.out.println(ex);
					}
				}
			}
		});
	}
	
	/**
	 * ������������ ������� � �������� "������ �����".
	 */
	private void configureIssueBookButtonColumn(final JFrame mainFrame, final JTable table, int columnIndex) {
		new ButtonColumn(table, columnIndex, new ActionListener() {
			// �������� ��� ������� �� ������.
			@Override
			public void actionPerformed(ActionEvent e) {
				/**
				 * ���������� ������ � ���������� ������� ���������, ����������� � ����������� �� ��������� �����.
				 */
				TableModel tm = table.getModel();
				int selectedRowIndex = table.getSelectedRow();
				if (tm.getValueAt(selectedRowIndex, 7).equals("Free")) {		// ������ ���� ����� "��������".
					Book selectedBook = new Book(
							(Integer) tm.getValueAt(selectedRowIndex, 0),	// id
							(String) tm.getValueAt(selectedRowIndex, 1),	// name
							(Author) tm.getValueAt(selectedRowIndex, 2),	// author
							(Publisher) tm.getValueAt(selectedRowIndex, 3),	// publisher
							(Integer) tm.getValueAt(selectedRowIndex, 4),	// publisher year
							(Integer) tm.getValueAt(selectedRowIndex, 5),	// page count
							(Double) tm.getValueAt(selectedRowIndex, 6)		// price
							);
					
					AbstractDialogUI issueDialog = new IssueBookDialogUI(mainFrame, table, readerService, employeeService, borrowService, selectedBook);
					mainFrame.setEnabled(false);
					issueDialog.setVisible();
				}
			}
		});
	}
	
	/**
	 * ������������ ����� ������ � ��������.
	 */
	private void leftPanelConfiguration(final JFrame mainFrame) {
		JPanel leftPanel = new JPanel();

		/**
		 * ��������� ������ 'Add new book'.
		 */
		
		JButton addBookButton = new JButton("Add new book");
		addBookButton.setCursor(handCursor);
		addBookButton.addActionListener(new ActionListener() {
			// ��� ������� �� ������ 'Add new book'.
			@Override
			public void actionPerformed(ActionEvent e) {
				final AddBookDialogUI addBookDialog = new AddBookDialogUI(mainFrame, booksTable, bookService, authorService, publisherService);
				mainFrame.setEnabled(false);
				addBookDialog.setVisible();
			}
		});
		leftPanel.add(addBookButton);
		
		addDialogButton(mainFrame, leftPanel, new AddPublisherDialogUI(mainFrame, publisherService), "Add new publisher");	// Add new publisher
		addDialogButton(mainFrame, leftPanel, new AddAuthorDialogUI(mainFrame, authorService), "Add new author");			// Add new author
		addDialogButton(mainFrame, leftPanel, new AddReaderDialogUI(mainFrame, readerService), "Add new reader");			// Add new reader
		addDialogButton(mainFrame, leftPanel, new AddEmployeeDialogUI(mainFrame, employeeService), "Add new employee");		// Add new employee

		mainFrame.add(leftPanel, BorderLayout.WEST);
	}
	
	/**
	 * ������������ ������ ������ � ��������.
	 */
	private void rightPanelConfiguration(final JFrame mainFrame) {
		JPanel rightPanel = new JPanel();
		
		/**
		 * ��������� ������ 'Open readers table'.
		 */
		JButton openReadersButton = new JButton("Open readers table");
		openReadersButton.setCursor(handCursor);
		openReadersButton.addActionListener(new ActionListener() {
				
			@Override
			public void actionPerformed(ActionEvent e) {
				if (((JButton)e.getSource()).getText().equals("Open readers table")) {
					readersTableConfiguration(mainFrame);
					mainFrame.remove(booksTablePanel);
					mainFrame.add(readersTablePanel, BorderLayout.SOUTH);
					((JButton)e.getSource()).setText("Open books table");
				} else {
					booksTableConfiguration(mainFrame);
					mainFrame.remove(readersTablePanel);
					mainFrame.add(booksTablePanel, BorderLayout.SOUTH);
					((JButton)e.getSource()).setText("Open readers table");
				}
			}
		});
		
		/**
		 * ��������� ������ 'Open employees table'.
		 */
		JButton openEmployeeButton = new JButton("Open employees table");
		openEmployeeButton.setCursor(handCursor);
		openEmployeeButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if (((JButton)e.getSource()).getText().equals("Open employees table")) {
					employeesTableConfiguration(mainFrame);
					mainFrame.remove(booksTablePanel);
					mainFrame.add(employeesTablePanel, BorderLayout.SOUTH);
					((JButton)e.getSource()).setText("Open books table");
				} else {
					booksTableConfiguration(mainFrame);
					mainFrame.remove(employeesTablePanel);
					mainFrame.add(booksTablePanel, BorderLayout.SOUTH);
					((JButton)e.getSource()).setText("Open employees table");
				}
				
			}
		});
		
		rightPanel.add(openReadersButton);
		rightPanel.add(openEmployeeButton);
		
		mainFrame.add(rightPanel, BorderLayout.EAST);
	}
	
	/**
	 * �������� ������ � ������� �������.
	 */
	private void addDialogButton(final JFrame mainFrame, final JPanel panel, final AbstractDialogUI dialog, String buttonText) {
		JButton button = new JButton(buttonText);
		button.setCursor(handCursor);
		button.addActionListener(new ActionListener() {	
			@Override
			public void actionPerformed(ActionEvent e) {
				mainFrame.setEnabled(false);
				dialog.setVisible();
			}
		});
		panel.add(button);
	}
	
}