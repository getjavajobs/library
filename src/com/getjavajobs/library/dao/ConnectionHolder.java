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
 * ����� - ��������� ����������.
 * 
 * ��� �����-���������.
 * ����� � ����� ���������� � ����� ������ - ������ "���������" ���� ����. 
 * � ���� ����-�� ����������� ����������, � ��� ��� ������ - � ������ �������,
 * ����� ��� ������������.
 * 
 * ������ ���� � ����� ������� ����� Lock->Condition. ��� ��� ������� �������� ������� � �������� ��������.
 * ��� ������ ���������� (getConnection) -> await(), ��� ������ ������ releaseConnection -> signal()
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
		//	this.commanCondition.signal();		// ���������� ���� ��������.
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
	
	// �������� ����������.
	public Connection getConnection() {
		commanLock.lock();
		try {
			if (!firstConnectionFlag.get()) {
				commanCondition.await();	// ������� ������������ ����������.
			}
			return connection;
		} catch (InterruptedException ex) {
			ex.printStackTrace();
		} finally {
			commanLock.unlock();
		}
		return null;
	}
	
	// ���������� ����������.
	// TODO Pool logic
	public void releaseConnection(Connection con) {
		commanLock.lock();
		try {
			firstConnectionFlag.set(false);
			commanCondition.signal();	// ���������� ����� ��������.
		} finally {
			commanLock.unlock();
		}
	}
}