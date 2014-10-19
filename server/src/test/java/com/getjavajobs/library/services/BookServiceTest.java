package com.getjavajobs.library.services;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.getjavajobs.library.model.Book;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:library-context.xml")
public class BookServiceTest {

	@Autowired
	private BookService bookService;
	
	@Test
	public void testGetAll() throws Exception {
		List<Book> books = bookService.getAll();
		/// NO HIBERNATE SESSION ANY MORE
		assertEquals(9, books.size());
	}

}
