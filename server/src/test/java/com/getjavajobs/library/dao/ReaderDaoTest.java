package com.getjavajobs.library.dao;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.getjavajobs.library.model.Reader;
import com.getjavajobs.library.services.ReaderService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:library-context.xml")
public class ReaderDaoTest {

	@Autowired
	private ReaderDao readerDao;
	@Autowired
	private ApplicationContext applicationContext;
	@Autowired
	private ReaderService readerService;

	@Test
	public void testGetAll() {
		BookDao bean = applicationContext.getBean(BookDao.class);
		System.out.println(bean.getClass());
		System.out.println(readerService);
		List<Reader> readers = readerDao.getAll();
		
		assertEquals(2, readers.size());
		assertEquals("1RN", readers.get(0).getFirstName());
	}
}
