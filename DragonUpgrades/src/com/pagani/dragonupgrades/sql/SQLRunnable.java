package com.pagani.dragonupgrades.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class SQLRunnable {

	private Connection connection;
	private SQL sql;
	private SQLResult action;

	public SQLRunnable(Connection connection, SQL sql, SQLResult action) {
		this.connection = connection;
		this.sql = sql;
		this.action = action;
	}

	public Connection getConnection() {
		return connection;
	}

	public SQL getSQL() {
		return sql;
	}

	public void run() {
		try (PreparedStatement statement = connection.prepareStatement(sql.getSQL())) {
			sql.applyObjects(statement);

			try (ResultSet result = statement.executeQuery()) {
				action.process(result);
			}
		} catch (java.sql.SQLException ex) {
			throw new SQLException("Erro ao executar o SQL (" + sql.getSQL() + ")", ex);
		}
	}

}
