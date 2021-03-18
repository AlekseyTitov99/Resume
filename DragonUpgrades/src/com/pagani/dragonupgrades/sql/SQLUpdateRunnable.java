package com.pagani.dragonupgrades.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class SQLUpdateRunnable {

	private Connection connection;
	private SQL sql;

	public SQLUpdateRunnable(Connection connection, SQL sql) {
		this.connection = connection;
		this.sql = sql;
	}

	public Connection getConnection() {
		return connection;
	}

	public void run() {
		try (PreparedStatement statement = connection.prepareStatement(sql.getSQL())) {
			sql.applyObjects(statement);
			
			statement.executeUpdate();
		} catch (java.sql.SQLException ex) {
			throw new SQLException("[AtlasSQL] Erro ao executar a query: "+sql.getSQL());
		}
	}

}
