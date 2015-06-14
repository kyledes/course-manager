package edu.paymentus.dao;

import java.io.Serializable;
import java.util.List;

public interface GenericDao<T, Id extends Serializable> {

	String getFindByIdSql();
	String getFindAllSql();
	String getInsertSql();
	String getUpdateSql();
	String getDeleteSql();
	
	T findById(Long Id);
	List<T> findAll();
	void insert (Object [] fields);
	void update (Object [] fields);
	void delete (Id id);
}
