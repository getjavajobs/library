package com.getjavajobs.library.dao;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Класс - хранилище соединений.
 * 
 * Это класс-синглетон.
 * Когда я отдал соединение с базой данных - должен "запомнить" этот факт. 
 * И если кому-то потребуется соединение, а оно уже занято - я должен ожидать,
 * когда оно освободиться.
 * 
 * Данную вещь я решил сделать через Lock->Condition. Мне это кажется довольно простым и логичным решением.
 * При взятии соединения (getConnection) -> await(), при вызове метода releaseConnection -> signal()
 * 
 */
public class ConnectionHolder {

	private static ConnectionHolder instance;
	private Connection connection;
	private Lock commanLock;
	private Condition commanCondition;
	private AtomicBoolean firstConnectionFlag;
	
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
			this.commanCondition.signal();		// соединение пока свободно.
			this.firstConnectionFlag = new AtomicBoolean();
			this.firstConnectionFlag.set(true);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static ConnectionHolder getInstance() {
		if (instance == null) {
			instance = new ConnectionHolder();
		}
		return instance;
	} 
	
	// Получить соединение.
	public Connection getConnection() {
		commanLock.lock();
		try {
			if (!firstConnectionFlag.get()) {
				commanCondition.await();	// Ожидаем освобождения соединения.
			}
			return connection;
		} catch (InterruptedException ex) {
			ex.printStackTrace();
		} finally {
			commanLock.unlock();
		}
		return null;
	}
	
	// Освободить соединение.
	// TODO Pool logic
	public void releaseConnection(Connection con) {
		commanLock.lock();
		try {
			firstConnectionFlag.set(false);
			commanCondition.signal();	// Соединение вновь свободно.
		} finally {
			commanLock.unlock();
		}
	}
}