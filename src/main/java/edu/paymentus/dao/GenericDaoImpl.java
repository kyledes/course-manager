package edu.paymentus.dao;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;

public abstract class GenericDaoImpl<T, Id extends Serializable> implements GenericDao<T, Id > {
	
	private String findById;
	private String findAll;
	private String update;
	private String insert;
	private String delete;
	private RowMapper<T> rowMapper;
	private JdbcTemplate jdbcTemplate;
	private Class<T> clazz;
	
	public void init(RowMapper<T> rowMapper, JdbcTemplate jdbcTemplate){
		this.rowMapper = rowMapper;
		this.jdbcTemplate = jdbcTemplate;
	}    
	
	public abstract String getFindByIdSql();
	public abstract String getFindAllSql();
	public abstract String getInsertSql();
	public abstract String getUpdateSql();
	public abstract String getDeleteSql();
	
	@Override
	public T findById(final Long id) {
		if (id == null) {
			return null;
		}

		Object[] params = { id };
		return (T) jdbcTemplate.queryForObject(getFindByIdSql(), rowMapper, params);
	}

	@Override
	public List<T> findAll() {
		return jdbcTemplate.query(getFindAllSql(), rowMapper);
	}

	@Override
	public void insert(Object [] args) {
		jdbcTemplate.update(getInsertSql(), args);
	}
	

	@Override
	public void update(Object [] args) {
		jdbcTemplate.update(getUpdateSql(), args);
		
	}

	@Override
	public void delete(Id id) {
		jdbcTemplate.update(getDeleteSql(), new Object[] { id});
	}
	
}
