package com.pagani.manopla.sql;

import java.sql.Connection;

public interface AtlasSQLConnection {

	public Connection getConnection();

	public void openConnection();

	public void closeConnection();
	
}
