package com.getjavajobs.library.ui;

import java.awt.BorderLayout;
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
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import com.getjavajobs.library.exceptions.ServiceException;
import com.getjavajobs.library.model.*;
import com.getjavajobs.library.services.*;
import com.getjavajobs.library.ui.dialogs.*;
import com.getjavajobs.library.ui.tables.ButtonColumn;


/**
 * Главный класс UI.
 */
public class LibraryUI {
	
	public static void main(String[] args) {
		new LibraryUI();
	}
	
	/* Размеры и положение формы */
	private static final int windowWidth = 1200;
	private static final int windowHeight = 800;	
	private static final int startXPosition = 500;
	private static final int startYPosition = 100;
	
	/* Табличные данные */
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
	
	/* Загрузка сервисов для работы с данными */
	private static final AuthorService authorService = new AuthorService();
	private static final BookService bookService = new BookService();
	private static final BorrowService borrowService = new BorrowService();
	private static final EmployeeService employeeService = new EmployeeService();
	private static final PublisherService publisherService = new PublisherService();
	private static final ReaderService readerService = new ReaderService();
	
	/* Прочее */
	private static final Cursor handCursor = new Cursor(Cursor.HAND_CURSOR);
	
	public LibraryUI() {
		final JFrame mainFrame = new JFrame("Library project");
		mainFrame.setSize(windowWidth, windowHeight);
		mainFrame.setLocation(startXPosition, startYPosition);
		mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
			
		booksTableConfiguration(mainFrame);		// конфигурация таблицы книг.
		booksTablePanel.setVisible(true);
		
		leftPanelConfiguration(mainFrame);		// конфигурация левой верхней панели.
		rightPanelConfiguration(mainFrame);		// конфигурация правой верхней панели.
		
		mainFrame.setVisible(true);
	}
	
	/**
	 * Конфигурация таблицы сотрудников.
	 */
	private void employeesTableConfiguration(final JFrame mainFrame) {
		JPanel tablePanel = new JPanel(new BorderLayout());
		tablePanel.setPreferredSize(new Dimension(tableWidth, tableHeight));
		
		// Создаем модель таблички.
		DefaultTableModel model = new DefaultTableModel(employeesTableColumnNames, 0) {
			private static final long serialVersionUID = 1L;

			// Возврат класса от каждой колонки позволяет по-своему перерисовывать каждый столбец. (для кнопок-колонок).
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
		
		// Считываем сотрудников из БД.
		try {
			List<Employee> employeesList = employeeService.getAll();
			int rowCount = employeesList.size();
			if (rowCount != 0) {
				for (int i = 0; i < rowCount; ++i) {
					Employee anotherEmployee = employeesList.get(i);
					model.addRow(new Object[] {
							// Данные
							new Integer(anotherEmployee.getId()),
							anotherEmployee.getEmployeeName(),
							anotherEmployee.getLastName(),
							anotherEmployee.getFatherName(),
							anotherEmployee.getBirthDate(),
							anotherEmployee.getEmployeePosition(),
							
							// Кнопки
							"Update",
							"Remove"
					});
				}
			}
		} catch (ServiceException ex) {
			JOptionPane.showMessageDialog(mainFrame, ex.getMessage(), "Getting employees error", JOptionPane.ERROR_MESSAGE);
			System.out.println(ex);
		}
		
		employeesTable = new JTable(model);		// Устанавливаем построенную модель.
		
		// Конфигурируем столбцы с кнопками.
		configureUpdateEmployeeButtonColumn(mainFrame, employeesTable, 6);
		configureRemoveEmployeeButtonColumn(mainFrame, employeesTable, 7);
		
		// Последние установки.
		employeesTable.setCursor(new Cursor(Cursor.HAND_CURSOR));
		employeesTable.setPreferredSize(new Dimension(tableWidth, tableHeight));
		employeesTable.getTableHeader().setReorderingAllowed(false);	// чтобы запретить перетаскивание столбцов.
		JScrollPane scrollPane = new JScrollPane(employeesTable);		// чтобы появился табличный хедер. 
		tablePanel.add(scrollPane);	
		
		tablePanel.add(new JLabel("Employees table"), BorderLayout.NORTH);
		employeesTablePanel = tablePanel;
		//mainFrame.add(tablePanel, BorderLayout.SOUTH);
	}
	
	/**
	 * Конфигурация колонки с кнопками "Обновить информацию о читателе".
	 */
	private void configureUpdateEmployeeButtonColumn(final JFrame mainFrame, final JTable table, int columnIndex) {
		new ButtonColumn(table, columnIndex, new ActionListener() {
			// Действия при нажатии на кнопку.
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
	 * Конфигурация колонки с кнопками "Удалить информацию о читателе".
	 */
	private void configureRemoveEmployeeButtonColumn(final JFrame mainFrame, final JTable table, int columnIndex) {
		new ButtonColumn(table, columnIndex, new ActionListener() {
			// Действия при нажатии на кнопку.
			@Override
			public void actionPerformed(ActionEvent e) {
				TableModel tm = table.getModel();
				int selectedRowIndex = table.getSelectedRow();
				int employeeID = (Integer) tm.getValueAt(selectedRowIndex, 0);

				// Удаляем из базы данных
				try {
					employeeService.delete(employeeID);
					JOptionPane.showMessageDialog(mainFrame, "Employee successfully removed!", "Success!", JOptionPane.INFORMATION_MESSAGE);
					
					/*
					 * TODO ПРОБЛЕМА С УДАЛЕНИЕМ ПОСЛЕДНЕЙ СТРОКИ
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
	 * Конфигурация таблицы читателей.
	 */
	private void readersTableConfiguration(final JFrame mainFrame) {
		JPanel tablePanel = new JPanel(new BorderLayout());
		tablePanel.setPreferredSize(new Dimension(tableWidth, tableHeight));
		
		// Создаем модель таблички.
		DefaultTableModel model = new DefaultTableModel(readersTableColumnNames, 0) {
			private static final long serialVersionUID = 1L;

			// Возврат класса от каждой колонки позволяет по-своему перерисовывать каждый столбец. (для кнопок-колонок).
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
		
		// Считываем читателей из БД.
		try {
			List<Reader> readersList = readerService.getAll();
			int rowCount = readersList.size();
			if (rowCount != 0) {
				for (int i = 0; i < rowCount; ++i) {
					Reader anotherReader = readersList.get(i);
					model.addRow(new Object[] {
							// Данные
							new Integer(anotherReader.getId()),
							anotherReader.getLastName(),
							anotherReader.getReaderName(),
							anotherReader.getAddress(),
							anotherReader.getPassportNumber(),
							anotherReader.getPhoneNumber(),
							
							// Кнопки
							"Update",
							"Remove"
					});
				}
			}
		} catch (ServiceException ex) {
			JOptionPane.showMessageDialog(mainFrame, ex.getMessage(), "Getting readers error", JOptionPane.ERROR_MESSAGE);
			System.out.println(ex);
		}
		
		readersTable = new JTable(model);		// Устанавливаем построенную модель.
		
		// Конфигурируем столбцы с кнопками.
		configureUpdateReaderButtonColumn(mainFrame, readersTable, 6);
		configureRemoveReaderButtonColumn(mainFrame, readersTable, 7);
		
		// Последние установки.
		readersTable.setCursor(new Cursor(Cursor.HAND_CURSOR));
		readersTable.setPreferredSize(new Dimension(tableWidth, tableHeight));
		readersTable.getTableHeader().setReorderingAllowed(false);	// чтобы запретить перетаскивание столбцов.
		JScrollPane scrollPane = new JScrollPane(readersTable);		// чтобы появился табличный хедер. 
		tablePanel.add(scrollPane);	
		
		tablePanel.add(new JLabel("Readers table"), BorderLayout.NORTH);
		readersTablePanel = tablePanel;
		//mainFrame.add(tablePanel, BorderLayout.SOUTH);
	}
	
	/**
	 * Конфигурация колонки с кнопками "Обновить информацию о читателе".
	 */
	private void configureUpdateReaderButtonColumn(final JFrame mainFrame, final JTable table, int columnIndex) {
		new ButtonColumn(table, columnIndex, new ActionListener() {
			// Действия при нажатии на кнопку.
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
	 * Конфигурация колонки с кнопками "Удалить информацию о читателе".
	 */
	private void configureRemoveReaderButtonColumn(final JFrame mainFrame, final JTable table, int columnIndex) {
		new ButtonColumn(table, columnIndex, new ActionListener() {
			// Действия при нажатии на кнопку.
			@Override
			public void actionPerformed(ActionEvent e) {
				TableModel tm = table.getModel();
				int selectedRowIndex = table.getSelectedRow();
				int readerID = (Integer) tm.getValueAt(selectedRowIndex, 0);

				// Удаляем из базы данных
				try {
					readerService.delete(readerID);
					JOptionPane.showMessageDialog(mainFrame, "Reader successfully removed!", "Success!", JOptionPane.INFORMATION_MESSAGE);
					
					/*
					 * TODO ПРОБЛЕМА С УДАЛЕНИЕМ ПОСЛЕДНЕЙ СТРОКИ
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
	 * Конфигурация таблицы книг.
	 */
	private void booksTableConfiguration(final JFrame mainFrame) {
		JPanel tablePanel = new JPanel(new BorderLayout());
		tablePanel.setPreferredSize(new Dimension(tableWidth, tableHeight));
		
		// Создаем модель таблицы.
		DefaultTableModel model = new DefaultTableModel(booksTableColumnNames, 0) {
			private static final long serialVersionUID = 1L;

			// Возврат класса от каждой колонки позволяет по-своему перерисовывать каждый столбец. (для кнопок-колонок).
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
			 * Считываем список книг из базы данных.
			 */
			List<Book> booksList = bookService.getAll();
			int rowCount = booksList.size();
			if (rowCount != 0) {
				// Если список не пуст --> заполняем таблицу данными.
				List<Borrow> borrowsList = borrowService.getAll();
				int borrowsCount = borrowsList.size();
				Date todayDate = new Date();
				
				for (int i = 0; i < rowCount; ++i) {
					Book anotherBook = booksList.get(i);
					String status = "Free";
					for (int j = 0; j < borrowsCount; ++j) {
						Borrow borrow = borrowsList.get(i);
						if (borrow.getBook().getId() == anotherBook.getId()) {	// Если в списке выдач нашли книгу - она либо выдана, либо просрочена
							if (todayDate.compareTo(borrow.getDateOfReturn()) > 0) {
								status = "Overdue";
							} else {
								status = "Issued";
							}
						}
					}
					
					// Создаем новую строчку.
					model.addRow(new Object[] {
							// Данные
							new Integer(anotherBook.getId()),
							anotherBook.getName(),
							anotherBook.getAuthor(),
							anotherBook.getPublisher(),
							new Integer(anotherBook.getYear()),
							new Integer(anotherBook.getPagesNumber()),
							new Double(anotherBook.getPrice()),
							status,
							// Кнопки
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
		
		booksTable = new JTable(model);		// Устанавливаем построенную модель.
		
		// Конфигурируем столбцы с кнопками.
		configureIssueBookButtonColumn(mainFrame, booksTable, 8);
		configureReturnBookButtonColumn(mainFrame, booksTable, 9);
		configureProlongBookButtonColumn(mainFrame, booksTable, 10);
		configureUpdateBookButtonColumn(mainFrame, booksTable, 11);
		configureRemoveBookButtonColumn(mainFrame, booksTable, 12);
		
		// Последние установки.
		booksTable.setCursor(new Cursor(Cursor.HAND_CURSOR));
		booksTable.setPreferredSize(new Dimension(tableWidth, tableHeight));
		booksTable.getTableHeader().setReorderingAllowed(false);	// чтобы запретить перетаскивание столбцов.
		JScrollPane scrollPane = new JScrollPane(booksTable);		// чтобы появился табличный хедер. 
		tablePanel.add(scrollPane);	
		
		tablePanel.add(new JLabel("Books table"), BorderLayout.NORTH);
		booksTablePanel = tablePanel;
		mainFrame.add(tablePanel, BorderLayout.SOUTH);
	}
	
	/**
	 * Конфигурация колонки с кнопками "Удалить книгу".
	 */
	private void configureRemoveBookButtonColumn(final JFrame mainFrame, final JTable table, int columnIndex) {
		new ButtonColumn(table, columnIndex, new ActionListener() {
			// Действия при нажатии на кнопку.
			@Override
			public void actionPerformed(ActionEvent e) {
				TableModel tm = table.getModel();
				int selectedRowIndex = table.getSelectedRow();
				int bookID = (Integer) tm.getValueAt(selectedRowIndex, 0);
				
				// Удаляем из базы данных книгу.
				try {
					bookService.delete(bookID);
					JOptionPane.showMessageDialog(mainFrame, "Book successfully removed!", "Success!", JOptionPane.INFORMATION_MESSAGE);
					
					/*
					 * TODO ПРОБЛЕМА С УДАЛЕНИЕМ ПОСЛЕДНЕЙ СТРОКИ
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
	 * Конфигурация колонки с кнопками "Обновить данные о книге".
	 */
	private void configureUpdateBookButtonColumn(final JFrame mainFrame, final JTable table, int columnIndex) {
		new ButtonColumn(table, columnIndex, new ActionListener() {
			// Действия при нажатии на кнопку.
			@Override
			public void actionPerformed(ActionEvent e) {
				/**
				 * Показываем диалог с выпадающим списком читателей, сотрудников и информацией по выбранной книге.
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
	 * Конфигурация колонки с кнопками "Продлить выдачу книги".
	 */
	private void configureProlongBookButtonColumn(final JFrame mainFrame, final JTable table, int columnIndex) {
		new ButtonColumn(table, columnIndex, new ActionListener() {
			// Действия при нажатии на кнопку.
			@Override
			public void actionPerformed(ActionEvent e) {
				TableModel tm = table.getModel();
				int selectedRowIndex = table.getSelectedRow();
				int bookID = (Integer) tm.getValueAt(selectedRowIndex, 0);
				
				if (tm.getValueAt(selectedRowIndex, 7).equals("Overdue")) {	// Работаем, только если книга кому-то выдана и она просрочена.
					
					try {
						Borrow bookBorrow = borrowService.get(bookID);
						bookBorrow.setDateOfReturn(new Date());	// меняем дату возвращения.
						borrowService.update(bookBorrow);
						JOptionPane.showMessageDialog(mainFrame, "Book successfully prolonged!", "Success!", JOptionPane.INFORMATION_MESSAGE);
						
						// Меняем статус в строке с книгой.
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
	 * Конфигурация колонки с кнопками "Вернуть книгу".
	 */
	private void configureReturnBookButtonColumn(final JFrame mainFrame, final JTable table, int columnIndex) {
		new ButtonColumn(table, columnIndex, new ActionListener() {
			// Действия при нажатии на кнопку.
			@Override
			public void actionPerformed(ActionEvent e) {
				TableModel tm = table.getModel();
				int selectedRowIndex = table.getSelectedRow();
				int bookID = (Integer) tm.getValueAt(selectedRowIndex, 0);
				
				if (!tm.getValueAt(selectedRowIndex, 7).equals("Free")) {	// Работаем, только если книга кому-то выдана или она просрочена.
					try {
						borrowService.remove(bookID);
						JOptionPane.showMessageDialog(mainFrame, "Book successfully returned!", "Success!", JOptionPane.INFORMATION_MESSAGE);
						
						// Меняем статус в строке с книгой.
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
	 * Конфигурация колонки с кнопками "Выдать книгу".
	 */
	private void configureIssueBookButtonColumn(final JFrame mainFrame, final JTable table, int columnIndex) {
		new ButtonColumn(table, columnIndex, new ActionListener() {
			// Действия при нажатии на кнопку.
			@Override
			public void actionPerformed(ActionEvent e) {
				/**
				 * Показываем диалог с выпадающим списком читателей, сотрудников и информацией по выбранной книге.
				 */
				TableModel tm = table.getModel();
				int selectedRowIndex = table.getSelectedRow();
				if (tm.getValueAt(selectedRowIndex, 7).equals("Free")) {		// Только если книга "свободна".
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
	 * Конфигурация левой панели с кнопками.
	 */
	private void leftPanelConfiguration(final JFrame mainFrame) {
		JPanel leftPanel = new JPanel();

		/**
		 * Настройка кнопки 'Add new book'.
		 */
		
		JButton addBookButton = new JButton("Add new book");
		addBookButton.setCursor(handCursor);
		addBookButton.addActionListener(new ActionListener() {
			// При нажатии на кнопку 'Add new book'.
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
	 * Конфигурация правой панели с кнопками.
	 */
	private void rightPanelConfiguration(final JFrame mainFrame) {
		JPanel rightPanel = new JPanel();
		
		/**
		 * Настройка кнопки 'Open readers table'.
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
		 * Настройка кнопки 'Open employees table'.
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
	 * Добавить кнопку с вызовом диалога.
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