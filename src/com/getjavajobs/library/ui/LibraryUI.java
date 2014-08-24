package com.getjavajobs.library.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import com.getjavajobs.library.dao.AuthorDao;
import com.getjavajobs.library.dao.BookDao;
import com.getjavajobs.library.dao.ConnectionHolder;
import com.getjavajobs.library.dao.PublisherDao;
import com.getjavajobs.library.exceptions.ServiceException;
import com.getjavajobs.library.model.*;
import com.getjavajobs.library.services.*;
import com.getjavajobs.library.ui.dialogs.*;
import com.getjavajobs.library.ui.tables.ButtonColumn;


/**
 * ??????? ????? UI.
 */
public class LibraryUI {
	
	public static void main(String[] args) {
		new LibraryUI();
        System.out.println("ww");
	}
	
	/* ??????? ? ????????? ????? */
	private static final int windowWidth = 1200;
	private static final int windowHeight = 800;	
	private static final int startXPosition = 500;
	private static final int startYPosition = 100;
	
	/* ????????? ?????? */
	private static final int tableWidth = 1000;
	private static final int tableHeight = 500;
	private static final String[] booksTableColumnNames = {
		"Book ID", "Book name", "Genres", "Author", "Publisher", "Publish year", "Page count", "Price", "Status", "", "", "Actions", "", ""
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
	
	/* ???????? ???????? ??? ?????? ? ??????? */
	private static final AuthorService authorService = new AuthorService(new AuthorDao());
	private static final BookService bookService = new BookService();
	private static final BorrowService borrowService = new BorrowService();
	private static final EmployeeService employeeService = new EmployeeService();
	private static final PublisherService publisherService = new PublisherService(new PublisherDao());
	private static final ReaderService readerService = new ReaderService();
	
	/* ?????? */
	private static final Cursor handCursor = new Cursor(Cursor.HAND_CURSOR);
	
	public LibraryUI() {
		final JFrame mainFrame = new JFrame("Library project");
		mainFrame.setSize(windowWidth, windowHeight);
		mainFrame.setLocation(startXPosition, startYPosition);
		mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        BookDao bookDao = new BookDao();
        bookDao.setConnectionHolder(ConnectionHolder.getInstance());
        bookService.setBookDao(bookDao);


		booksTableConfiguration(mainFrame);		// ???????????? ??????? ????.
		booksTablePanel.setVisible(true);
		
		leftPanelConfiguration(mainFrame);		// ???????????? ????? ??????? ??????.
		rightPanelConfiguration(mainFrame);		// ???????????? ?????? ??????? ??????.

		mainFrame.setVisible(true);
	}
	
	/**
	 * ???????????? ??????? ???????????.
	 */
	private void employeesTableConfiguration(final JFrame mainFrame) {
		JPanel tablePanel = new JPanel(new BorderLayout());
		tablePanel.setPreferredSize(new Dimension(tableWidth, tableHeight));
		
		// ??????? ?????? ????????.
		DefaultTableModel model = new DefaultTableModel(employeesTableColumnNames, 0) {
			private static final long serialVersionUID = 1L;

			// ??????? ?????? ?? ?????? ??????? ????????? ??-?????? ?????????????? ?????? ???????. (??? ??????-???????).
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
		
		// ????????? ??????????? ?? ??.
		try {
			List<Employee> employeesList = employeeService.getAll();
			int rowCount = employeesList.size();
			if (rowCount != 0) {
				for (int i = 0; i < rowCount; ++i) {
					Employee anotherEmployee = employeesList.get(i);
					model.addRow(new Object[] {
							
							// ??????
							new Integer(anotherEmployee.getId()),
							anotherEmployee.getName(),
							anotherEmployee.getSurname(),
							anotherEmployee.getPatronymic(),
							anotherEmployee.getDateOfBirth(),
							anotherEmployee.getPosition(),
							
							// ??????
							"Update",
							"Remove"
					});
					
					
				}
			}
		} catch (ServiceException ex) {
			JOptionPane.showMessageDialog(mainFrame, ex.getMessage(), "Getting employees error", JOptionPane.ERROR_MESSAGE);
			System.out.println(ex);
		}
		
		employeesTable = new JTable(model);		// ????????????? ??????????? ??????.
		
		// ????????????? ??????? ? ????????.
		configureUpdateEmployeeButtonColumn(mainFrame, employeesTable, 6);
		configureRemoveEmployeeButtonColumn(mainFrame, employeesTable, 7);
		
		// ????????? ?????????.
		employeesTable.setCursor(new Cursor(Cursor.HAND_CURSOR));
		employeesTable.setPreferredSize(new Dimension(tableWidth, tableHeight));
		employeesTable.getTableHeader().setReorderingAllowed(false);	// ????? ????????? ?????????????? ????????.
		JScrollPane scrollPane = new JScrollPane(employeesTable);		// ????? ???????? ????????? ?????. 
		tablePanel.add(scrollPane);	
		
		tablePanel.add(new JLabel("Employees table"), BorderLayout.NORTH);
		employeesTablePanel = tablePanel;
		//mainFrame.add(tablePanel, BorderLayout.SOUTH);
	}
	
	/**
	 * ???????????? ??????? ? ???????? "???????? ?????????? ? ????????".
	 */
	private void configureUpdateEmployeeButtonColumn(final JFrame mainFrame, final JTable table, int columnIndex) {
		new ButtonColumn(table, columnIndex, new ActionListener() {
			// ???????? ??? ??????? ?? ??????.
			@Override
			public void actionPerformed(ActionEvent e) {
				TableModel tm = table.getModel();
				int selectedRowIndex = table.getSelectedRow();

				Employee selectedEmployee = new Employee();
				selectedEmployee.setId((Integer) tm.getValueAt(selectedRowIndex, 0));
				selectedEmployee.setName((String) tm.getValueAt(selectedRowIndex, 1));
				selectedEmployee.setSurname((String) tm.getValueAt(selectedRowIndex, 2));
				selectedEmployee.setPatronymic((String) tm.getValueAt(selectedRowIndex, 3));
				selectedEmployee.setDateOfBirth((Date) tm.getValueAt(selectedRowIndex, 4));
				selectedEmployee.setPosition((String) tm.getValueAt(selectedRowIndex, 5));
											
				AbstractDialogUI updateDialog = new UpdateEmployeeDialogUI(mainFrame, table, employeeService, selectedEmployee);
				mainFrame.setEnabled(false);
				updateDialog.setVisible();
			}
		});
	}
	
	/**
	 * ???????????? ??????? ? ???????? "??????? ?????????? ? ????????".
	 */
	private void configureRemoveEmployeeButtonColumn(final JFrame mainFrame, final JTable table, int columnIndex) {
		new ButtonColumn(table, columnIndex, new ActionListener() {
			// ???????? ??? ??????? ?? ??????.
			@Override
			public void actionPerformed(ActionEvent e) {
				TableModel tm = table.getModel();
				int selectedRowIndex = table.getSelectedRow();
				int employeeID = (Integer) tm.getValueAt(selectedRowIndex, 0);

				// ??????? ?? ???? ??????
				try {
					employeeService.delete(employeeID);
					JOptionPane.showMessageDialog(mainFrame, "Employee successfully removed!", "Success!", JOptionPane.INFORMATION_MESSAGE);
					
					/*
					 * TODO ???????? ? ????????? ????????? ??????
					 */
					DefaultTableModel dtm = (DefaultTableModel) table.getModel();
					if (selectedRowIndex != table.getRowCount() - 1) {
						dtm.removeRow(selectedRowIndex);
					} else {
						dtm.removeRow(selectedRowIndex);
						dtm.fireTableStructureChanged();
						configureUpdateEmployeeButtonColumn(mainFrame, employeesTable, 6);
						configureRemoveEmployeeButtonColumn(mainFrame, employeesTable, 7);
					}
					
					
				} catch (ServiceException ex) {
					JOptionPane.showMessageDialog(mainFrame, ex.getMessage(), "Removing error", JOptionPane.ERROR_MESSAGE);
					System.out.println(ex);
				}
			}
		});
	}
	
	/**
	 * ???????????? ??????? ?????????.
	 */
	private void readersTableConfiguration(final JFrame mainFrame) {
		JPanel tablePanel = new JPanel(new BorderLayout());
		tablePanel.setPreferredSize(new Dimension(tableWidth, tableHeight));
		
		// ??????? ?????? ????????.
		DefaultTableModel model = new DefaultTableModel(readersTableColumnNames, 0) {
			private static final long serialVersionUID = 1L;

			// ??????? ?????? ?? ?????? ??????? ????????? ??-?????? ?????????????? ?????? ???????. (??? ??????-???????).
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
		
		// ????????? ????????? ?? ??.
		try {
			List<Reader> readersList = readerService.getAll();
			int rowCount = readersList.size();
			if (rowCount != 0) {
				for (int i = 0; i < rowCount; ++i) {
					Reader anotherReader = readersList.get(i);
					
					model.addRow(new Object[] {
							// ??????
							new Integer(anotherReader.getReaderId()),
							anotherReader.getSecondName(),
							anotherReader.getFirstName(),
							anotherReader.getAddress(),
							anotherReader.getPassport(),
							anotherReader.getPhone(),
							
							// ??????
							"Update",
							"Remove"
					});
				}
			}
		} catch (ServiceException ex) {
			JOptionPane.showMessageDialog(mainFrame, ex.getMessage(), "Getting readers error", JOptionPane.ERROR_MESSAGE);
			System.out.println(ex);
		}
		
		readersTable = new JTable(model);		// ????????????? ??????????? ??????.
		
		// ????????????? ??????? ? ????????.
		configureUpdateReaderButtonColumn(mainFrame, readersTable, 6);
		configureRemoveReaderButtonColumn(mainFrame, readersTable, 7);
		
		// ????????? ?????????.
		readersTable.setCursor(new Cursor(Cursor.HAND_CURSOR));
		readersTable.setPreferredSize(new Dimension(tableWidth, tableHeight));
		readersTable.getTableHeader().setReorderingAllowed(false);	// ????? ????????? ?????????????? ????????.
		JScrollPane scrollPane = new JScrollPane(readersTable);		// ????? ???????? ????????? ?????. 
		tablePanel.add(scrollPane);	
		
		tablePanel.add(new JLabel("Readers table"), BorderLayout.NORTH);
		readersTablePanel = tablePanel;
		//mainFrame.add(tablePanel, BorderLayout.SOUTH);
	}
	
	/**
	 * ???????????? ??????? ? ???????? "???????? ?????????? ? ????????".
	 */
	private void configureUpdateReaderButtonColumn(final JFrame mainFrame, final JTable table, int columnIndex) {
		new ButtonColumn(table, columnIndex, new ActionListener() {
			// ???????? ??? ??????? ?? ??????.
			@Override
			public void actionPerformed(ActionEvent e) {
				TableModel tm = table.getModel();
				int selectedRowIndex = table.getSelectedRow();

				Reader selectedReader = new Reader();
				selectedReader.setReaderId((Integer) tm.getValueAt(selectedRowIndex, 0));
				selectedReader.setSecondName((String) tm.getValueAt(selectedRowIndex, 1));
				selectedReader.setFirstName((String) tm.getValueAt(selectedRowIndex, 2));
				selectedReader.setAddress((String) tm.getValueAt(selectedRowIndex, 3));
				selectedReader.setPasport((String) tm.getValueAt(selectedRowIndex, 4));
				selectedReader.setPhone((String) tm.getValueAt(selectedRowIndex, 5));
											
				AbstractDialogUI updateDialog = new UpdateReaderDialogUI(mainFrame, table, readerService, selectedReader);
				mainFrame.setEnabled(false);
				updateDialog.setVisible();
			}
		});
	}
	
	/**
	 * ???????????? ??????? ? ???????? "??????? ?????????? ? ????????".
	 */
	private void configureRemoveReaderButtonColumn(final JFrame mainFrame, final JTable table, int columnIndex) {
		new ButtonColumn(table, columnIndex, new ActionListener() {
			// ???????? ??? ??????? ?? ??????.
			@Override
			public void actionPerformed(ActionEvent e) {
				TableModel tm = table.getModel();
				int selectedRowIndex = table.getSelectedRow();
				int readerID = (Integer) tm.getValueAt(selectedRowIndex, 0);

				// ??????? ?? ???? ??????
				try {
					readerService.delete(readerID);
					JOptionPane.showMessageDialog(mainFrame, "Reader successfully removed!", "Success!", JOptionPane.INFORMATION_MESSAGE);
					
					/*
					 * TODO ???????? ? ????????? ????????? ??????
					 */
					DefaultTableModel dtm = (DefaultTableModel) table.getModel();
					if (selectedRowIndex != table.getRowCount() - 1) {
						dtm.removeRow(selectedRowIndex);
					} else {
						dtm.removeRow(selectedRowIndex);
						dtm.fireTableStructureChanged();
						configureUpdateReaderButtonColumn(mainFrame, readersTable, 6);
						configureRemoveReaderButtonColumn(mainFrame, readersTable, 7);
					}
					
				} catch (ServiceException ex) {
					JOptionPane.showMessageDialog(mainFrame, ex.getMessage(), "Removing error", JOptionPane.ERROR_MESSAGE);
					System.out.println(ex);
				}
			}
		});
	}
	
	/**
	 * ???????????? ??????? ????.
	 */
	private void booksTableConfiguration(final JFrame mainFrame) {
		JPanel tablePanel = new JPanel(new BorderLayout());
		tablePanel.setPreferredSize(new Dimension(tableWidth, tableHeight));
		
		// ??????? ?????? ???????.
		DefaultTableModel model = new DefaultTableModel(booksTableColumnNames, 0) {
			private static final long serialVersionUID = 1L;

			// ??????? ?????? ?? ?????? ??????? ????????? ??-?????? ?????????????? ?????? ???????. (??? ??????-???????).
			@SuppressWarnings({ "unchecked", "rawtypes" })
			@Override
            public Class getColumnClass(int column) {
                return getValueAt(0, column).getClass();
            }
			
			@Override
        	public boolean isCellEditable(int row, int column) {
        		return (column > 8);
        	}
			
			
		};
		
		try {
			/*
			 * ????????? ?????? ???? ?? ???? ??????.
			 */	
			List<Book> booksList = bookService.getAll();
			int rowCount = booksList.size();
			if (rowCount != 0) {
				
				// ???? ?????? ?? ???? --> ????????? ??????? ???????.
				List<Borrow> borrowsList = borrowService.getAll();
				int borrowsCount = borrowsList.size();
				Date todayDate = new Date();
				
				for (int i = 0; i < rowCount; ++i) {
					Book anotherBook = booksList.get(i);
					String status = "Free";
					for (int j = 0; j < borrowsCount; ++j) {
						Borrow borrow = borrowsList.get(i);
						if (borrow.getBook().getId() == anotherBook.getId()) {	// ???? ? ?????? ????? ????? ????? - ??? ???? ??????, ???? ??????????
							if (todayDate.compareTo(borrow.getDateOfReturn()) > 0) {
								status = "Overdue";
							} else {
								status = "Issued";
							}
						}
					}
					
					// ??????? ????? ???????.
					model.addRow(new Object[] {
							// ??????
							new Integer(anotherBook.getId()),
							anotherBook.getName(),
							anotherBook.getGenreList(),
							anotherBook.getAuthor(),
							anotherBook.getPublisher(),
							new Integer(anotherBook.getYear()),
							new Integer(anotherBook.getPagesNumber()),
							new Double(anotherBook.getPrice()),
							status,
							// ??????
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
		
		booksTable = new JTable(model);		// ????????????? ??????????? ??????.
		
		// ????????????? ??????? ? ????????.
		configureIssueBookButtonColumn(mainFrame, booksTable, 9);
		configureReturnBookButtonColumn(mainFrame, booksTable, 10);
		configureProlongBookButtonColumn(mainFrame, booksTable, 11);
		configureUpdateBookButtonColumn(mainFrame, booksTable, 12);
		configureRemoveBookButtonColumn(mainFrame, booksTable, 13);
		
		// ????????? ?????????.
		booksTable.setCursor(new Cursor(Cursor.HAND_CURSOR));
		booksTable.setPreferredSize(new Dimension(tableWidth, tableHeight));
		booksTable.getTableHeader().setReorderingAllowed(false);	// ????? ????????? ?????????????? ????????.
		
		
		booksTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
			
			private static final long serialVersionUID = 1L;

			@Override
			public Component getTableCellRendererComponent(JTable table, Object value,
					boolean isSelected, boolean hasFocus, int row, int column) {
				
				if (column == 8) {
					switch ((String) value) {
					case "Free":
						setBackground(Color.GREEN);
						break;
						
					case "Issued":
						setBackground(Color.YELLOW);
						break;
						
					case "Overdue":
						setBackground(Color.RED);
						break;
					}
				} else {
					setBackground(table.getBackground());
				}
				
				setText(value.toString());
				if (value.getClass() == Author.class) {					
					Author author = (Author) value;

					// !!! ??? ??? ???? !!!
					this.setText(author.getSurname() + " " + 
							author.getName().substring(0, 1).toUpperCase() + "." + 
							author.getPatronymic().substring(0, 1).toUpperCase());
				} else if (value.getClass() == Publisher.class) {
					Publisher publisher = (Publisher) value;
					this.setText(publisher.getName() + ", " + publisher.getCity());	
				}
								
				return this;
			}
		});
		
		JScrollPane scrollPane = new JScrollPane(booksTable);		// ????? ???????? ????????? ?????. 
		tablePanel.add(scrollPane);	
		
		tablePanel.add(new JLabel("Books table"), BorderLayout.NORTH);
		booksTablePanel = tablePanel;
		mainFrame.add(tablePanel, BorderLayout.SOUTH);
	}
	
	/**
	 * ???????????? ??????? ? ???????? "??????? ?????".
	 */
	private void configureRemoveBookButtonColumn(final JFrame mainFrame, final JTable table, int columnIndex) {
		new ButtonColumn(table, columnIndex, new ActionListener() {
			// ???????? ??? ??????? ?? ??????.
			@Override
			public void actionPerformed(ActionEvent e) {
				TableModel tm = table.getModel();
				int selectedRowIndex = table.getSelectedRow();
				int bookID = (Integer) tm.getValueAt(selectedRowIndex, 0);
				
				// ??????? ?? ???? ?????? ?????.
				try {
					int reply = JOptionPane.showConfirmDialog(mainFrame, "Do you really want to remove this book from library database?", "Remove book", JOptionPane.YES_NO_OPTION);
					if (reply == JOptionPane.YES_OPTION) {
						bookService.delete(bookID);
						JOptionPane.showMessageDialog(mainFrame, "Book successfully removed!", "Success!", JOptionPane.INFORMATION_MESSAGE);
						
						/*
						 * TODO ???????? ? ????????? ????????? ??????
						 */
						DefaultTableModel dtm = (DefaultTableModel) table.getModel();
						if (selectedRowIndex != (table.getRowCount() - 1)) {	// ???? ?? ??????? ????????? ??????
							dtm.removeRow(selectedRowIndex);
						} else {
							dtm.removeRow(selectedRowIndex);
							dtm.fireTableStructureChanged();	// ???????, ??? ??????? ??????????
							configureIssueBookButtonColumn(mainFrame, booksTable, 9);
							configureReturnBookButtonColumn(mainFrame, booksTable, 10);
							configureProlongBookButtonColumn(mainFrame, booksTable, 11);
							configureUpdateBookButtonColumn(mainFrame, booksTable, 12);
							configureRemoveBookButtonColumn(mainFrame, booksTable, 13);
						}
						
						
					}
					
				} catch (ServiceException ex) {
					JOptionPane.showMessageDialog(mainFrame, ex.getMessage(), "Removing error", JOptionPane.ERROR_MESSAGE);
					System.out.println(ex);
				} 

			}
		});
		
	}
	
	/**
	 * ???????????? ??????? ? ???????? "???????? ?????? ? ?????".
	 */
	private void configureUpdateBookButtonColumn(final JFrame mainFrame, final JTable table, int columnIndex) {
		new ButtonColumn(table, columnIndex, new ActionListener() {
			// ???????? ??? ??????? ?? ??????.
			@SuppressWarnings("unchecked")
			@Override
			public void actionPerformed(ActionEvent e) {
				/**
				 * ?????????? ?????? ? ?????????? ??????? ?????????, ??????????? ? ??????????? ?? ????????? ?????.
				 */
				
				TableModel tm = table.getModel();
				int selectedRowIndex = table.getSelectedRow();

				Book selectedBook = new Book();
				selectedBook.setId((Integer) tm.getValueAt(selectedRowIndex, 0));
				selectedBook.setName((String) tm.getValueAt(selectedRowIndex, 1));
				selectedBook.setGenreList((List<Genre>) tm.getValueAt(selectedRowIndex, 2));
				selectedBook.setAuthor((Author) tm.getValueAt(selectedRowIndex, 3));
				selectedBook.setPublisher((Publisher) tm.getValueAt(selectedRowIndex, 4));
				selectedBook.setYear((Integer) tm.getValueAt(selectedRowIndex, 5));
				selectedBook.setPagesNumber((Integer) tm.getValueAt(selectedRowIndex, 6));
				selectedBook.setPrice((Double) tm.getValueAt(selectedRowIndex, 7));
				
				AbstractDialogUI updateDialog = new UpdateBookDialogUI(mainFrame, table, bookService, authorService, publisherService, selectedBook);
				mainFrame.setEnabled(false);
				updateDialog.setVisible();
			}
		});
	}
	
	/**
	 * ???????????? ??????? ? ???????? "???????? ?????? ?????".
	 */
	private void configureProlongBookButtonColumn(final JFrame mainFrame, final JTable table, int columnIndex) {
		new ButtonColumn(table, columnIndex, new ActionListener() {
			// ???????? ??? ??????? ?? ??????.
			@Override
			public void actionPerformed(ActionEvent e) {
				TableModel tm = table.getModel();
				int selectedRowIndex = table.getSelectedRow();
				int bookID = (Integer) tm.getValueAt(selectedRowIndex, 0);
				
				if (tm.getValueAt(selectedRowIndex, 7).equals("Overdue")) {	// ????????, ?????? ???? ????? ????-?? ?????? ? ??? ??????????.
					
					try {
						Borrow bookBorrow = borrowService.get(bookID);
						bookBorrow.setDateOfReturn(new Date());	// ?????? ???? ???????????.
						borrowService.update(bookBorrow);
						JOptionPane.showMessageDialog(mainFrame, "Book successfully prolonged!", "Success!", JOptionPane.INFORMATION_MESSAGE);
						
						// ?????? ?????? ? ?????? ? ??????.
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
	 * ???????????? ??????? ? ???????? "??????? ?????".
	 */
	private void configureReturnBookButtonColumn(final JFrame mainFrame, final JTable table, int columnIndex) {
		new ButtonColumn(table, columnIndex, new ActionListener() {
			// ???????? ??? ??????? ?? ??????.
			@Override
			public void actionPerformed(ActionEvent e) {
				TableModel tm = table.getModel();
				int selectedRowIndex = table.getSelectedRow();
				int bookID = (Integer) tm.getValueAt(selectedRowIndex, 0);
				
				if (!tm.getValueAt(selectedRowIndex, 7).equals("Free")) {	// ????????, ?????? ???? ????? ????-?? ?????? ??? ??? ??????????.
					try {
						borrowService.delete(bookID);
						JOptionPane.showMessageDialog(mainFrame, "Book successfully returned!", "Success!", JOptionPane.INFORMATION_MESSAGE);
						
						// ?????? ?????? ? ?????? ? ??????.
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
	 * ???????????? ??????? ? ???????? "?????? ?????".
	 */
	private void configureIssueBookButtonColumn(final JFrame mainFrame, final JTable table, int columnIndex) {
		new ButtonColumn(table, columnIndex, new ActionListener() {
			// ???????? ??? ??????? ?? ??????.
			@SuppressWarnings("unchecked")
			@Override
			public void actionPerformed(ActionEvent e) {
				/**
				 * ?????????? ?????? ? ?????????? ??????? ?????????, ??????????? ? ??????????? ?? ????????? ?????.
				 */
				TableModel tm = table.getModel();
				int selectedRowIndex = table.getSelectedRow();
				if (tm.getValueAt(selectedRowIndex, 7).equals("Free")) {		// ?????? ???? ????? "????????".
					Book selectedBook = new Book();
					selectedBook.setId((Integer) tm.getValueAt(selectedRowIndex, 0));
					selectedBook.setName((String) tm.getValueAt(selectedRowIndex, 1));
					selectedBook.setGenreList((List<Genre>) tm.getValueAt(selectedRowIndex, 2));
					selectedBook.setAuthor((Author) tm.getValueAt(selectedRowIndex, 3));
					selectedBook.setPublisher((Publisher) tm.getValueAt(selectedRowIndex, 4));
					selectedBook.setYear((Integer) tm.getValueAt(selectedRowIndex, 5));
					selectedBook.setPagesNumber((Integer) tm.getValueAt(selectedRowIndex, 6));
					selectedBook.setPrice((Double) tm.getValueAt(selectedRowIndex, 7));
					
					AbstractDialogUI issueDialog = new IssueBookDialogUI(mainFrame, table, readerService, employeeService, borrowService, selectedBook);
					mainFrame.setEnabled(false);
					issueDialog.setVisible();
				}
			}
		});
	}
	
	/**
	 * ???????????? ????? ?????? ? ????????.
	 */
	private void leftPanelConfiguration(final JFrame mainFrame) {
		JPanel leftPanel = new JPanel();

		/**
		 * ????????? ?????? 'Add new book'.
		 */
		
		JButton addBookButton = new JButton("Add new book");
		addBookButton.setCursor(handCursor);
		addBookButton.addActionListener(new ActionListener() {
			// ??? ??????? ?? ?????? 'Add new book'.
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
	 * ???????????? ?????? ?????? ? ????????.
	 */
	private void rightPanelConfiguration(final JFrame mainFrame) {
		JPanel rightPanel = new JPanel();
		
		/**
		 * ????????? ?????? 'Open readers table'.
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
		 * ????????? ?????? 'Open employees table'.
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
	 * ???????? ?????? ? ??????? ???????.
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