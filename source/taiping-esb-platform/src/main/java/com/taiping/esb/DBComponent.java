package com.taiping.esb;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.mule.api.lifecycle.Callable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

public class DBComponent {
	
	@Autowired
	private DataSource datasource;
	
	public void setDataSource(DataSource datasource) {
		this.datasource = datasource;
	}
	
	protected List<Map<String, Object>> queryForList(String sql, Object ... params) throws SQLException {
		Connection conn = null;
    	JdbcTemplate jdbcTemplate;
    	try {
    		jdbcTemplate = new JdbcTemplate(datasource);
    		return jdbcTemplate.queryForList(sql, params);
    	} finally {
    		if (conn != null) {
    			conn.close();
    		}
    	}
	}
	
	protected void insert(String sql, Object ... params) throws SQLException {
		Connection conn = null;
    	JdbcTemplate jdbcTemplate;
    	try {
    		jdbcTemplate = new JdbcTemplate(datasource);
    		jdbcTemplate.update(sql, params);
    	} finally {
    		if (conn != null) {
    			conn.close();
    		}
    	}
	}
}
