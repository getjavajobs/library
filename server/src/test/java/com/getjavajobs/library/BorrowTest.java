package com.getjavajobs.library;

import com.getjavajobs.library.dao.BorrowDao;
import com.getjavajobs.library.exceptions.DAOException;
import com.getjavajobs.library.model.Borrow;
import com.getjavajobs.library.services.validators.BorrowValidator;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;

import static org.junit.Assert.*;

/**
 * Created by Глеб on 24.08.2014.
 */
public class BorrowTest {
    private static Borrow borrow;
    private static BorrowDao borrowDao;
    private static int id;

    @BeforeClass
    public static void onBefore() {
        borrow = new Borrow();
        borrowDao = new BorrowDao();
    }

    @Test
    public void testAdding() { //После добавления в БД, объекту должен присвоиться уникальный ID
        Borrow newBorrow = borrowDao.add(borrow);

        assertNotEquals(borrow.getBorrowId(), newBorrow.getBorrowId());

    }

    @Test
    public void testGetting() { //полученный Borrow должен проходить валидацию
        borrow = borrowDao.get(0);
        assertTrue(new BorrowValidator(borrow).validate());
    }



    @Test
    public void testUpdating() {
        Date oldDate = borrow.getDateOfReturn();
        Date newDate = new Date(oldDate.getTime() + 1000000);
        borrow.setDateOfReturn(newDate);
        Borrow updated = borrowDao.update(borrow);
        assertTrue(oldDate.before(updated.getDateOfReturn()));
    }

    @Test
    public void testRemoving() {//После удаления BorrowDao должен выбросить ошибку БД из-за отсутствия элемента
        Exception exc = new DAOException();
        borrowDao.delete(id);
        assertTrue(exc.getClass().equals(borrowDao.get(id).getClass()));
    }
    @Test
    public void testGetAll(){ //BorrowDao должен возвращать List
        assertEquals(ArrayList.class, borrowDao.getAll().getClass());
    }
}
