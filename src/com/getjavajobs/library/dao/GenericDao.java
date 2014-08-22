package com.getjavajobs.library.dao;

import java.util.List;

public interface GenericDao<T> {

	T add(T obj);

	void delete(int id);

	T get(int id);

	T update(T obj);

	List<T> getAll();

}
