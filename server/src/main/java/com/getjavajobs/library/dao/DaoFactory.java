package com.getjavajobs.library.dao;

import java.util.HashMap;
import java.util.Map;

import com.getjavajobs.library.model.Author;
import com.getjavajobs.library.model.Book;
import com.getjavajobs.library.model.Borrow;
import com.getjavajobs.library.model.Employee;
import com.getjavajobs.library.model.Genre;
import com.getjavajobs.library.model.Publisher;
import com.getjavajobs.library.model.Reader;

public class DaoFactory {

	private static Map daos = new HashMap() {
		{
			put(Book.class, new BookDao());
			put(Author.class, new AuthorDao());
			put(Borrow.class, new BorrowDao());
			put(Employee.class, new EmployeeDao());
			put(Genre.class, new GenreDao());
			put(Publisher.class, new PublisherDao());
			put(Reader.class, new ReaderDao());
		}
	};

	public static AuthorDao getAuthorDao() {
		return (AuthorDao) daos.get(Author.class);
	}

	public static BookDao getBookDao() {
		return (BookDao) daos.get(Book.class);
	}
	
	public static BorrowDao getBorrowDao() {
		return (BorrowDao) daos.get(Borrow.class);
	}
	
	public static EmployeeDao getEmployeeDao() {
		return (EmployeeDao) daos.get(Employee.class);
	}
	
	public static GenreDao getGenreDao() {
		return (GenreDao) daos.get(Genre.class);
	}
	
	public static PublisherDao getPublisherDao() {
		return (PublisherDao) daos.get(Publisher.class);
	}
	
	public static ReaderDao getReaderDao() {
		return (ReaderDao) daos.get(Reader.class);
	}
}
