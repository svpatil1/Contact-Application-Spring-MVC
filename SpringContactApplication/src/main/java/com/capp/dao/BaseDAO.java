package com.capp.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;
import javax.sql.DataSource;


abstract public class BaseDAO extends NamedParameterJdbcDaoSupport{
	@Autowired
	public void setDatasource2(DataSource ds) {
	
		super.setDataSource(ds);
		
	}
	
}