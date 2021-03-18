package com.pagani.market.sql;

import com.google.gson.Gson;
import com.pagani.market.Main;
import com.pagani.market.api.Category;
import com.pagani.market.api.HistoricItem;
import com.pagani.market.api.Item;
import com.pagani.market.enums.CategoryType;
import com.pagani.market.objeto.User;
import org.bukkit.Bukkit;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;

public class AtlasStorage {

    public static MySQL mySQLConnection = Main.mySQLConnection;
    ;

    public static void loadUser(String user) {
        if (!Main.cache2.containsKey(user)) {
            mySQLConnection.runSQL(new AtlasSQLRunnable(mySQLConnection.getConnection(),
                    new AtlasSQL("SELECT * FROM `mercadousers` WHERE `user` = ?", user), (result) -> {
                User consumerPlayer = null;
                if (result.next()) {
                    consumerPlayer = decode(result.getString("json"), User.class);
                    if (consumerPlayer.getVendendo() == null) consumerPlayer.setVendendo(new LinkedList<>());
                    if (consumerPlayer.getPessoal() == null) consumerPlayer.setPessoal(new LinkedList<>());
                } else {
                    consumerPlayer = new User(user);
                    mySQLConnection.runUpdate(new AtlasSQLUpdateRunnable(mySQLConnection.getConnection(),
                                    new AtlasSQL("INSERT INTO `mercadousers` (`user`, `json`) VALUES (?,?)", user, encode(consumerPlayer))),
                            true);
                }
                Main.cache2.put(user, consumerPlayer);
            }), true);
        }
    }

    public static void loadCategorias() {
        for (CategoryType value : CategoryType.values()) {
            mySQLConnection.runSQL(new AtlasSQLRunnable(mySQLConnection.getConnection(),
                    new AtlasSQL("SELECT * FROM `categoria` WHERE `usuario` = ?", value.name().toUpperCase()), (result) -> {
                Category consumerPlayer = null;
                if (result.next()) {
                    consumerPlayer = decode(result.getString("json"), Category.class);
                    if (consumerPlayer.getItemsInCategory() == null) consumerPlayer.setItems(new LinkedList<>());
                    if (consumerPlayer.getHistoricItems() == null) {
                        consumerPlayer.setHistoricItems(new LinkedList<>());
                    }
                    if (consumerPlayer.getHistoricItems().size() > 75){
                        consumerPlayer.getHistoricItems().clear();
                    }
                } else {
                    consumerPlayer = new Category(value);
                    if (consumerPlayer.getItemsInCategory() == null) consumerPlayer.setItems(new LinkedList<>());
                    if (consumerPlayer.getHistoricItems() == null) {
                        consumerPlayer.setHistoricItems(new LinkedList<>());
                    }
                    Bukkit.getConsoleSender().sendMessage("§3[Debug ArplexMarket] §bCriando objeto de categoria: " + value.name());
                    mySQLConnection.runUpdate(new AtlasSQLUpdateRunnable(mySQLConnection.getConnection(),
                                    new AtlasSQL("INSERT INTO `categoria` (`usuario`, `json`) VALUES (?,?)", value.name().toUpperCase(), encode(consumerPlayer))),
                            true);
                }
                Main.cache.put(value, consumerPlayer);
            }), false);
        }
    }

    public static void unLoadUser(String user) {
        if (Main.cache2.containsKey(user)) {
            User consumerPlayer = Main.cache2.get(user);
            if (consumerPlayer.getVendendo() == null) consumerPlayer.setVendendo(new LinkedList<>());
            if (consumerPlayer.getPessoal() == null) consumerPlayer.setPessoal(new LinkedList<>());
            mySQLConnection.runUpdate(new AtlasSQLUpdateRunnable(mySQLConnection.getConnection(), new AtlasSQL("UPDATE `mercadousers` SET `json` = ? WHERE `user` = ?;", encode(consumerPlayer), user)), true);
            Main.cache2.remove(user);
        }
    }

    public static void savePlayer(String user, Object o, boolean asynchronously) {
        mySQLConnection.runUpdate(new AtlasSQLUpdateRunnable(mySQLConnection.getConnection(), new AtlasSQL("UPDATE `mercadousers` SET `json` = ? WHERE `user` = ?;", encode(o), user)), asynchronously);
    }

    public static void saveCategory(String user, Object o, boolean asynchronously) {
        mySQLConnection.runUpdate(new AtlasSQLUpdateRunnable(mySQLConnection.getConnection(), new AtlasSQL("UPDATE `categoria` SET `json` = ? WHERE `usuario` = ?;", encode(o), user)), asynchronously);
    }

    public static void RemoveItemFromPessoalVenda(String user, Item item) {
        if (!Main.cache2.containsKey(user)) {
            mySQLConnection.runSQL(new AtlasSQLRunnable(mySQLConnection.getConnection(),
                    new AtlasSQL("SELECT * FROM `mercadousers` WHERE `user` = ?", user), (result) -> {
                User consumerPlayer = null;
                if (result.next()) {
                    consumerPlayer = decode(result.getString("json"), User.class);
                    if (consumerPlayer.getVendendo() == null) consumerPlayer.setVendendo(new LinkedList<>());
                    if (consumerPlayer.getPessoal() == null) consumerPlayer.setPessoal(new LinkedList<>());
                    consumerPlayer.getVendendo().remove(item);
                    AtlasStorage.savePlayer(user, consumerPlayer, true);
                    Bukkit.getConsoleSender().sendMessage("§c[Debug] §eTirei o item da venda privada do jogador: " + user);
                }
            }), true);
        }
    }
    public static void RemoveItemFromPessoalVendaAndExpire(String user, Item item) {
        if (!Main.cache2.containsKey(user)) {
            mySQLConnection.runSQL(new AtlasSQLRunnable(mySQLConnection.getConnection(),
                    new AtlasSQL("SELECT * FROM `mercadousers` WHERE `user` = ?", user), (result) -> {
                User consumerPlayer = null;
                if (result.next()) {
                    consumerPlayer = decode(result.getString("json"), User.class);
                    if (consumerPlayer.getVendendo() == null) consumerPlayer.setVendendo(new LinkedList<>());
                    if (consumerPlayer.getPessoal() == null) consumerPlayer.setPessoal(new LinkedList<>());
                    consumerPlayer.getVendendo().remove(item);
                    consumerPlayer.getExpirados().add(item);
                    AtlasStorage.savePlayer(user, consumerPlayer, true);
                    Bukkit.getConsoleSender().sendMessage("§c[Debug] §eTirei o item da venda privada do jogador: " + user);
                }
            }), true);
        }
    }

    public static void removeItemFromOtherPessoal(String user, Item item) {
        if (!Main.cache2.containsKey(user)) {
            mySQLConnection.runSQL(new AtlasSQLRunnable(mySQLConnection.getConnection(),
                    new AtlasSQL("SELECT * FROM `mercadousers` WHERE `user` = ?", user), (result) -> {
                User consumerPlayer = null;
                if (result.next()) {
                    consumerPlayer = decode(result.getString("json"), User.class);
                    if (consumerPlayer.getVendendo() == null) consumerPlayer.setVendendo(new LinkedList<>());
                    if (consumerPlayer.getPessoal() == null) consumerPlayer.setPessoal(new LinkedList<>());
                    consumerPlayer.getPessoal().remove(item);
                    AtlasStorage.savePlayer(user, consumerPlayer, true);
                    Bukkit.getConsoleSender().sendMessage("§c[Debug] §eTirei o item da venda privada do jogador: " + user);
                }
            }), true);
        }
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