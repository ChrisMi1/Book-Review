package org.gnt.bookreview.repository;

import java.util.List;

public interface Repository<T,V> {
	List<T> findAll(); 
	T findById(V id); 
	void save(T obj); 
	void update(T obj);
	void delete(T obj); 
}
