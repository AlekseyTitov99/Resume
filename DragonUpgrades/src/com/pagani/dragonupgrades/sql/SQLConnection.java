package com.pagani.dragonupgrades.sql;

import java.sql.Connection;

public interface SQLConnection {

	public Connection getConnection();

	public void openConnection();

	public void closeConnection();
	
}
