package com.pagani.dragonupgrades.sql;

import com.google.gson.Gson;
import com.pagani.dragonupgrades.Main;
import com.pagani.dragonupgrades.object.User;

public class AtlasStorage {

	public static MySQL mySQLConnection = Main.mySQLconnection;;

	public static void loadFac(String user) {
		if (!Main.cache.containsKey(user)) {
			mySQLConnection.runSQL(new SQLRunnable(mySQLConnection.getConnection(), new SQL("SELECT * FROM `upgrades` WHERE `facs` = ?", user), (result)-> {
				User consumerPlayer = null;
				if (result.next()) {
					consumerPlayer = decode(result.getString("json"), User.class);
				} else {
					consumerPlayer = new User(user);
					mySQLConnection.runUpdate(new SQLUpdateRunnable(mySQLConnection.getConnection(), new SQL("INSERT INTO `upgrades` (`facs`, `json`) VALUES (?,?)", user, encode(consumerPlayer))), true);
				}
				Main.cache.put(user, consumerPlayer);
			}), true);
		}
	}

	public static void deleteFac(String user, boolean async) {
		mySQLConnection.runUpdate(new SQLUpdateRunnable(mySQLConnection.getConnection(), new SQL("DELETE FROM `upgrades` WHERE `facs` = ?;", user)), async);
	}

	public static void unLoadFac(String user) {
		if(Main.cache.containsKey(user)) {
			mySQLConnection.runUpdate(new SQLUpdateRunnable(mySQLConnection.getConnection(), new SQL("UPDATE `upgrades` SET `json` = ? WHERE `facs` = ?;", encode(Main.cache.get(user)),user)), true);
			Main.cache.remove(user);
		}else {
			Object o = new Object();
			mySQLConnection.runUpdate(new SQLUpdateRunnable(mySQLConnection.getConnection(), new SQL("UPDATE `upgrades` SET `json` = ? WHERE `facs` = ?;", encode(o),user)), true);
		}
	}
	
	public static void saveFac(String user,Object o, boolean asynchronously) {
		mySQLConnection.runUpdate(new SQLUpdateRunnable(mySQLConnection.getConnection(), new SQL("UPDATE `upgrades` SET `json` = ? WHERE `facs` = ?;", encode(o),user)), asynchronously);
	}

	private static final Gson gson = new Gson();

	public static  String encode(Object classz) {
		String objectencoded = "";
		try {
			objectencoded = gson.toJson(classz);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return objectencoded;
	}

	public static <T> T decode(String encoded, Class<T> classz) {
		T f = null;
		try {
			f = gson.fromJson(encoded, classz);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return f;
	}

}