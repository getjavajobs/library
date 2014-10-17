package com.getjavajobs.library.dao;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.getjavajobs.library.model.Reader;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:library-context.xml")
public class ReaderDaoTest {

	@Autowired
	private ReaderDao readerDao;

	@Test
	public void testGetAll() {
		List<Reader> readers = readerDao.getAll();
		
		assertEquals(2, readers.size());
		assertEquals("1RN", readers.get(0).getFirstName());
	}
}
