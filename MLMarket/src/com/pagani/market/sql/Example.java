package com.pagani.market.sql;

public class Example {

    public MySQL mySQLConnection;

    public void a(String user) {
        mySQLConnection.runSQL(new AtlasSQLRunnable(mySQLConnection.getConnection(),
                new AtlasSQL("SELECT * FROM `nomeDaTabela` WHERE `jogador` = ?", user), (result) -> {
            if (result.next()) {
                String jsonObject = result.getString("json");
            }
        }), true);
    }
}
