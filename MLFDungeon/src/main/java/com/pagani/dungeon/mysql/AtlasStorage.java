package com.pagani.dungeon.mysql;

import com.google.gson.Gson;
import com.pagani.dungeon.Main;
import com.pagani.dungeon.objetos.DungeonUser;
import com.pagani.dungeon.objetos.Party;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class AtlasStorage {

	public static MySQL mySQLConnection = Main.mySQLConnection;;

	public static void loadUser(String user) {
		if (!Main.cache.containsKey(user)) {
			mySQLConnection.runSQL(new AtlasSQLRunnable(mySQLConnection.getConnection(), new AtlasSQL("SELECT * FROM `dungeon` WHERE `jogador` = ?", user), (result)->{
				DungeonUser consumerPlayer = null;
				if(result.next()) {
					consumerPlayer = decode(result.getString("json"), DungeonUser.class);
					if (consumerPlayer == null){
						consumerPlayer = new DungeonUser(user);
					}
					if (consumerPlayer.getPartyname() == null){

					} else {
						if (!consumerPlayer.getPartyname().isEmpty() && !consumerPlayer.getPartyname().equalsIgnoreCase("")) {
							importParty(consumerPlayer.getPartyname(), true, consumerPlayer);
						}
					}
				}else {
					consumerPlayer = new DungeonUser(user);
					mySQLConnection.runUpdate(new AtlasSQLUpdateRunnable(mySQLConnection.getConnection(), new AtlasSQL("INSERT INTO `dungeon` (`jogador`, `json`) VALUES (?,?)", user,encode(consumerPlayer))), true);
				}
				Main.cache.put(user, consumerPlayer);
			}), true);
		}
	}

	public static void removeParty(String user) {
		if (!Main.cache.containsKey(user)) {
			mySQLConnection.runSQL(new AtlasSQLRunnable(mySQLConnection.getConnection(), new AtlasSQL("SELECT * FROM `dungeon` WHERE `jogador` = ?", user), (result)->{
				DungeonUser consumerPlayer = null;
				if(result.next()) {
					consumerPlayer = decode(result.getString("json"), DungeonUser.class);
					consumerPlayer.setPartyname("");
					consumerPlayer.setPartyuuid("");
					savePlayer(consumerPlayer.getUsername(),consumerPlayer,true);
					return;
				}
			}), true);
		}
	}

	public static void unLoadUser(String user) {
		if(Main.cache.containsKey(user)) {
			mySQLConnection.runUpdate(new AtlasSQLUpdateRunnable(mySQLConnection.getConnection(), new AtlasSQL("UPDATE `dungeon` SET `json` = ? WHERE `jogador` = ?;", encode(Main.cache.get(user)),user)), true);
			Main.cache.remove(user);
		}
	}

	public static void saveParty(Party upgradeObject) {
		new BukkitRunnable() {
			@Override
			public void run() {
				String object = encode(upgradeObject);
				File bruteFile = new File(Main.getPlugin(Main.class).getDataFolder(), "/storage/" + upgradeObject.getName() + ".yml");
				YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(bruteFile);
				yamlConfiguration.set("party", object);
				try {
					yamlConfiguration.save(bruteFile);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}.runTaskAsynchronously(Main.getPlugin(Main.class));
	}

	public static void deleteParty(String fac){
		File bruteFile = new File(Main.getPlugin(Main.class).getDataFolder(), "/storage/" + fac + ".yml");
		bruteFile.delete();
		Main.cacheparty.remove(fac);
	}

	public static void importParty(String fac, boolean async, DungeonUser p){
		if (fac == null || fac.equalsIgnoreCase("null") || fac.isEmpty()){
			return;
		}
		if (Main.cacheparty.containsKey(fac)){
			return;
		}
		if (async) {
			new BukkitRunnable() {
				@Override
				public void run() {
					File bruteFile = new File(Main.getPlugin(Main.class).getDataFolder(), "/storage/" + fac + ".yml");
					if (!bruteFile.exists()) {
						if (!p.getPartyname().equalsIgnoreCase("") && !p.getPartyname().isEmpty()){
							p.setPartyname("");
							p.setPartyuuid("");
							return;
						}
						try {
							bruteFile.createNewFile();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}// thanks, i was just afraid f yml keep on the memory yeah ill download it later 'im making a server'maybe ill make a showcase later on the devs group, its a good feature, like,
					// there´s protection blocks with durability, have you heard about obsidian breaker?, yeah the same, so i made a upgrade, gtg now my classes
					YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(bruteFile);
					Party upgradeObject = null;
					if (yamlConfiguration.getString("party") == null || yamlConfiguration.getString("party").isEmpty()) {
						upgradeObject = new Party(fac);
						saveParty(upgradeObject);
					} else {
						upgradeObject = decode(yamlConfiguration.getString("party"), Party.class);
						if (upgradeObject.getConvites() == null) upgradeObject.setConvites(new ArrayList<>());
					}
					Main.cacheparty.put(upgradeObject.getName(), upgradeObject);
				}
			}.runTaskAsynchronously(Main.getPlugin(Main.class));
		} else {
			File bruteFile = new File(Main.getPlugin(Main.class).getDataFolder(), "/storage/" + fac + ".yml");
			if (!bruteFile.exists()) {
				try {
					if (!p.getPartyname().equalsIgnoreCase("") && !p.getPartyname().isEmpty()){
						p.setPartyname("");
						return;
					}
					bruteFile.createNewFile();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(bruteFile);
			Party upgradeObject = null;
			if (yamlConfiguration.get("party") == null || yamlConfiguration.getString("party").isEmpty()) {
				upgradeObject = new Party(fac);
				saveParty(upgradeObject);
			} else {
				upgradeObject = decode(yamlConfiguration.getString("party"), Party.class);
				if (upgradeObject.getConvites() == null) upgradeObject.setConvites(new ArrayList<>());
			}
			Main.cacheparty.put(upgradeObject.getName(), upgradeObject);
		}
	}

	public static void savePlayer(String user,Object o, boolean asynchronously) {
		mySQLConnection.runUpdate(new AtlasSQLUpdateRunnable(mySQLConnection.getConnection(), new AtlasSQL("UPDATE `dungeon` SET `json` = ? WHERE `jogador` = ?;", encode(o),user)), asynchronously);
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
