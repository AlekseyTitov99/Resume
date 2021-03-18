package com.dungeons.system.SQL;

import com.dungeons.system.Main;
import com.dungeons.system.objeto.DungeonUser;
import com.google.gson.Gson;


public class AtlasStorage {

	public static MySQL mySQLConnection = Main.mySQLConnection;

	public static void loadUser(String user) {
		if (!Main.cacheuser.containsKey(user)) {
			mySQLConnection.runSQL(new AtlasSQLRunnable(mySQLConnection.getConnection(), new AtlasSQL("SELECT * FROM `dungeon` WHERE `jogador` = ?", user), (result) -> {
				DungeonUser consumerPlayer = null;
				if (result.next()) {
					consumerPlayer = decode(result.getString("json"), DungeonUser.class);
				} else {
					consumerPlayer = new DungeonUser(user);
					mySQLConnection.runUpdate(new AtlasSQLUpdateRunnable(mySQLConnection.getConnection(), new AtlasSQL("INSERT INTO `dungeon` (`jogador`, `json`) VALUES (?,?)", user, encode(consumerPlayer))), true);
				}
				Main.cacheuser.put(user, consumerPlayer);
			}), true);
		}
	}

	public static void saveNumber(int user) {
		mySQLConnection.runSQL(new AtlasSQLRunnable(mySQLConnection.getConnection(), new AtlasSQL("SELECT * FROM `partys` WHERE `jogador` = ?", "number"), (result) -> {
			if (result.next()) {
				mySQLConnection.runUpdate(new AtlasSQLUpdateRunnable(mySQLConnection.getConnection(), new AtlasSQL("UPDATE `partys` SET `json` = ? WHERE `jogador` = ?;",user, "number")), true);
			} else {
				mySQLConnection.runUpdate(new AtlasSQLUpdateRunnable(mySQLConnection.getConnection(), new AtlasSQL("INSERT INTO `partys` (`jogador`, `json`) VALUES (?,?)", "number", user)), true);
			}
		}), true);
	}

	public static void unLoadUser(String user) {
		if (Main.cacheuser.containsKey(user)) {
			mySQLConnection.runUpdate(new AtlasSQLUpdateRunnable(mySQLConnection.getConnection(), new AtlasSQL("UPDATE `dungeon` SET `json` = ? WHERE `jogador` = ?;", encode(Main.cacheuser.get(user)), user)), true);
			Main.cacheuser.remove(user);
		}
	}


	public static void savePlayer(String user, Object o, boolean asynchronously) {
		mySQLConnection.runUpdate(new AtlasSQLUpdateRunnable(mySQLConnection.getConnection(), new AtlasSQL("UPDATE `dungeon` SET `json` = ? WHERE `jogador` = ?;", encode(o), user)), asynchronously);
	}

	public static String encode(Object classz) {
		Gson gson = new Gson();
		String objectencoded = "";
		try {
			objectencoded = gson.toJson(classz);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return objectencoded;
	}

	public static <T> T decode(String encoded, Class<T> classz) {
		Gson gson = new Gson();
		T f = null;
		try {
			f = gson.fromJson(encoded, classz);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return f;
	}
}