package com.pagani.manopla.sql;

import com.google.gson.Gson;
import com.pagani.manopla.Main;
import com.pagani.manopla.objeto.ManoplaUser;

public class AtlasStorage {

	public static MySQL mySQLConnection = Main.mySQLconnection;;

	public static void loadUser(String user) {
		if (!Main.cache.containsKey(user)) {
			mySQLConnection.runSQL(new AtlasSQLRunnable(mySQLConnection.getConnection(), new AtlasSQL("SELECT * FROM `manopla` WHERE `jogador` = ?", user), (result)-> {
				ManoplaUser consumerPlayer = null;
				if (result.next()) {
					consumerPlayer = decode(result.getString("json"), ManoplaUser.class);
				} else {
					consumerPlayer = new ManoplaUser(user);
					mySQLConnection.runUpdate(new AtlasSQLUpdateRunnable(mySQLConnection.getConnection(), new AtlasSQL("INSERT INTO `manopla` (`jogador`, `json`) VALUES (?,?)", user, encode(consumerPlayer))), true);
				}
				Main.cache.put(user, consumerPlayer);
			}), true);
		}

	}
	
	public static void unLoadUser(String user) {
		if(Main.cache.containsKey(user)) {
			mySQLConnection.runUpdate(new AtlasSQLUpdateRunnable(mySQLConnection.getConnection(), new AtlasSQL("UPDATE `manopla` SET `json` = ? WHERE `jogador` = ?;", encode(Main.cache.get(user)),user)), true);
			Main.cache.remove(user);
		}else {
			Object o = new Object();
			mySQLConnection.runUpdate(new AtlasSQLUpdateRunnable(mySQLConnection.getConnection(), new AtlasSQL("UPDATE `manopla` SET `json` = ? WHERE `jogador` = ?;", encode(o),user)), true);
		}
	}
	
	public static void savePlayer(String user,Object o, boolean asynchronously) {
		mySQLConnection.runUpdate(new AtlasSQLUpdateRunnable(mySQLConnection.getConnection(), new AtlasSQL("UPDATE `manopla` SET `json` = ? WHERE `jogador` = ?;", encode(o),user)), asynchronously);
	}
	
	public static  String encode(Object classz) {
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