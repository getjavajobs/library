package com.getjavajobs.library.dao;

import com.getjavajobs.library.dao.AuthorDao;
import com.getjavajobs.library.dao.BookDao;
import com.getjavajobs.library.dao.ConnectionHolder;
import com.getjavajobs.library.dao.PublisherDao;
import com.getjavajobs.library.exceptions.DAOException;
import com.getjavajobs.library.model.Author;
import com.getjavajobs.library.model.Book;
import com.getjavajobs.library.model.Genre;
import com.getjavajobs.library.model.Publisher;
import org.junit.*;

import java.io.*;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Created by Vlad on 26.08.2014.
 */



class FakeConnectionHolder extends ConnectionHolder{
    static ConnectionHolder instance;
    private Connection connection;
    public FakeConnectionHolder(int i){
        super(i);
        try {
           connection = DriverManager.getConnection("jdbc:mysql://localhost:3306", "root", "384233");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static ConnectionHolder getInstance() {
        if (instance==null){
            instance=new FakeConnectionHolder(0);
        }
        return instance;
    }

    @Override
    public Connection getConnection() {
        return connection;
    }

    @Override
    public void releaseConnection(Connection con) {

    }
}

public class BookDaoTest {

    static ConnectionHolder connectionHolder;
    AuthorDao authorDao = new FakeAuthorDao();
    PublisherDao publisherDao = new FakePublisherDao();
    GenreDao genreDao = new FakeGenreDao();
    BookDao bookDao = new BookDao();
    @BeforeClass
    public static void setUpBeforeClass() throws Throwable {

        connectionHolder = FakeConnectionHolder.getInstance();
        /*
        Queue<Connection> connectionQueue = new ArrayDeque<>();
        connectionQueue.add(DriverManager.getConnection("jdbc:mysql://localhost:3306", "root", "384233"));
        Field field =connectionHolder.getClass().getDeclaredField("connectionStore");
        field.setAccessible(true);
        field.set(connectionHolder, connectionQueue);
        */
    }
    @Before
    public void setUp() throws Throwable {
        Connection connection = connectionHolder.getConnection();
        Statement statement = connection.createStatement();
        executeDBScripts("src//test//fakedb.sql", statement);
        statement.close();
        connectionHolder.releaseConnection(connection);
        bookDao.setConnectionHolder(connectionHolder);
        bookDao.setAuthorDao(authorDao);
        bookDao.setPublisherDao(publisherDao);
        bookDao.setGenreDao(genreDao);

    }
    @After
    public void tearDown() throws Throwable{
        Connection connection = connectionHolder.getConnection();
        Statement statement = connection.createStatement();
        statement.execute("DROP DATABASE bookdaotest;");
        statement.close();
        connectionHolder.releaseConnection(connection);
    }

    @Test
    public void testCRUD() throws Throwable{
        Book book1 = new Book();
        book1.setName("book1");
        book1.setAuthor(authorDao.get(1));
        book1.setPublisher(publisherDao.get(1));
        List<Genre> genreList1 = new ArrayList<>();
        genreList1.add(genreDao.get(1));
        book1.setGenreList(genreList1);

        Book book2 = new Book();
        book2.setName("book2");
        book2.setAuthor(authorDao.get(1));
        book2.setPublisher(publisherDao.get(1));
        List<Genre> genreList2 = new ArrayList<>();
        genreList2.add(genreDao.get(1));
        genreList2.add(genreDao.get(2));
        book2.setGenreList(genreList2);

        book1 = bookDao.add(book1);
        book2 = bookDao.add(book2);
        assertEquals(1, book1.getId());
        assertEquals(2, book2.getId());

        book2 = bookDao.get(2);
        assertEquals("book2", book2.getName());
        assertEquals(2, book2.getGenreList().size());

        book1 = bookDao.get(1);
        book1.setName("newnameofbook1");
        book1.getGenreList().add(genreDao.get(2));
        book1=bookDao.update(book1);

        assertEquals("newnameofbook1", book1.getName().toLowerCase());
        assertEquals(2, book1.getGenreList().size());


        bookDao.delete(1);
        Book emptyBook = bookDao.get(1);
        assertNull(emptyBook);

        bookDao.add(book1);
        Collection<Book> books = bookDao.getAll();
        assertEquals(2, books.size());
    }

    @Test(expected = DAOException.class)
    public void incorrectAdd() throws DAOException {
        Book book = new Book();
        book.setName("book1");
        book.setAuthor(authorDao.get(1));
        Publisher publisher = new Publisher();
        publisher.setId(2);
        book.setPublisher(publisher);
        bookDao.add(book);

    }

    @Test(expected = DAOException.class)
    public void incorrectUpdate() throws DAOException{
        Book book = new Book();
        book.setName("book1");
        book.setAuthor(authorDao.get(1));
        book.setPublisher(publisherDao.get(1));
        List<Genre> genreList = new ArrayList<>();
        genreList.add(genreDao.get(1));
        book.setGenreList(genreList);
        book.setId(10);
        book = bookDao.update(book);
    }


    private static boolean executeDBScripts (String aSQLScriptFilePath, Statement stmt)throws Exception{
        boolean isScriptExecuted = false;
        String str="";
        StringBuffer sb = new StringBuffer();
        BufferedReader in = new BufferedReader(new FileReader(aSQLScriptFilePath));
        while ((str = in.readLine()) != null) {
            sb.append(str + "\n");
            if (sb.toString().contains(";")) {
                stmt.execute(sb.toString());
                sb = new StringBuffer();
            }

        }
        isScriptExecuted = true;
        in.close();
        return isScriptExecuted;
    }


    private class FakePublisherDao extends PublisherDao{
        @Override
        public Publisher get(int id) {
            if(id == 1){
                Publisher publisher = new Publisher();
                publisher.setId(1);
                return publisher;
            }
            return null;
        }
    }
    private class FakeAuthorDao extends AuthorDao{
        @Override
        public Author get(int id){
            if(id==1){
                Author author = new Author();
                author.setId(1);
                return author;
            }
            return null;
        }
    }
    private class FakeGenreDao extends GenreDao{
        @Override
        public Genre get(int id){
            if(id==1){
                Genre genre = new Genre();
                genre.setId(1);
                genre.setGenreType("first genre");
                return genre;
            }
            if(id==2){
                Genre genre = new Genre();
                genre.setId(2);
                genre.setGenreType("second genre");
                return genre;
            }
            return null;
        }
    }
}
