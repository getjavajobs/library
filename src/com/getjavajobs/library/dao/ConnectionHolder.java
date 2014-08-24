package com.getjavajobs.library.dao;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Класс - хранилище соединений.
 */
public class ConnectionHolder {
	
	private static ConnectionHolder instance;
	private Lock commanLock;
	private Condition commanCondition;
	private Connection connection;
	private AtomicReference<Connection> connectionStore;
	
	private ConnectionHolder() {
		try {
			Properties props = new Properties();
			props.load(new FileInputStream("jdbc.properties"));
			String url = props.getProperty("jdbc.url");
			String username = props.getProperty("jdbc.username");
			String password = props.getProperty("jdbc.password");
			
			this.connection = DriverManager.getConnection(url, username, password);
			this.commanLock = new ReentrantLock();
			this.commanCondition = commanLock.newCondition();
			this.connectionStore = new AtomicReference<>(connection);	// Хранилище соединения.

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	// Получить экземпляр класса ConnectionHolder.
	public static ConnectionHolder getInstance() {
		if (instance == null) {
			instance = new ConnectionHolder();
		}
		return instance;
	} 
	
	// Получить соединение, если возможно. Если соединение занято - обратившийся поток будет ожидать
	// освобождения соединения.
	public Connection getConnection() {
	/*	commanLock.lock();
		try {
			Connection storedConnection = connectionStore.getAndSet(null);	// Забираем из хранилища то, что там лежит и устанавливаем null.
			if (storedConnection == null) {
				commanCondition.await(); // Ожидаем, когда вернут соединение обратно.
				storedConnection = connectionStore.getAndSet(null);
			}
			return storedConnection;
	
			return connection;
		} catch (InterruptedException ex) {
			ex.printStackTrace();
		} finally {
			commanLock.unlock();
		}
		return null;
	*/
		return connection;
	}
	
	// Освободить соединение.
	public void releaseConnection(Connection con) {
		commanLock.lock();
		try {
			connectionStore.set(con);	// Кладем соединение обратно в хранилище.
			commanCondition.signal();	
		} finally {
			commanLock.unlock();
		}
	}
}