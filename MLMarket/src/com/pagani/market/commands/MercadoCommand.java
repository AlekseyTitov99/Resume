package com.pagani.market.commands;

import com.pagani.market.Main;
import com.pagani.market.api.BaseHelp;
import com.pagani.market.api.Item;
import com.pagani.market.api.ItemMetadata;
import com.pagani.market.codificador.CodifyMaterialID;
import com.pagani.market.enums.CategoryType;
import com.pagani.market.listener.GeneralGuiHolder;
import com.pagani.market.menu.Mercado;
import com.pagani.market.objeto.User;
import com.pagani.market.sql.AtlasStorage;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class MercadoCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String lbl, String[] args) {
        if (sender instanceof Player) {
            if (cmd.getName().equals("mercado")) {
                Player p = (Player) sender;
                if (args.length == 0) {
                    sender.sendMessage("§aComandos Disponíveis:");
                    sender.sendMessage("§a/mercado abrir§7 -§8Abra o menu de categorias;");
                    sender.sendMessage("§a/mercado vender <preço>§7 - Coloque o item em sua mão à venda.");
                    sender.sendMessage("§a/mercado vender §7<preço> <jogador> §7 - Venda para um jogador específico.");
                    return true;
                }
                if (args.length == 1) {
                    if (args[0].equalsIgnoreCase("ver") || args[0].equalsIgnoreCase("abrir")) {
                        Mercado.onLoad(p);
                        return true;
                    }
                    if (args[0].equalsIgnoreCase("vender")) {
                        sender.sendMessage("§aComandos Disponíveis:");
                        sender.sendMessage("§a/mercado abrir§7 -§8Abra o menu de categorias;");
                        sender.sendMessage("§a/mercado vender <preço>§7 - Coloque o item em sua mão à venda.");
                        sender.sendMessage("§a/mercado vender §7<preço> <jogador> §7 - Venda para um jogador específico.");
                        return true;
                    }
                    return true;
                }
                if (args.length == 3) {
                    if (args[0].equalsIgnoreCase("vender")) {
                        try {
                            double price = Double.parseDouble(args[1]);
                            if (price <= 0){
                                p.sendMessage("");
                                p.sendMessage("§c Ops! Parece que você inseriu um valor inválido!");
                                p.sendMessage("");
                                return true;
                            }
                            String jogador = args[2];
                            Player c = Bukkit.getPlayerExact(jogador);
                            if (c == null){
                                p.sendMessage("");
                                p.sendMessage("§c Ops! Este jogador não está online..");
                                p.sendMessage("");
                                return true;
                            }
                            if (c.isOnline()) {
                                User user = Main.cache2.get(p.getName());
                                User user2 = Main.cache2.get(c.getName());
                                CategoryType categoryType = Mercado.canSell(p.getItemInHand());
                                if (categoryType == null) {
                                    p.sendMessage("");
                                    p.sendMessage("§c Ops! Este item não se encaixa em nenhuma categoria do");
                                    p.sendMessage("§c Mercado. Para ver as categorias disponíveis, utilize /mercado ver;");
                                    p.sendMessage("");
                                    return true;
                                }
                                long long2 = System.currentTimeMillis();
                                Item item = new Item();
                                ItemStack itemStack = p.getItemInHand();
                                Bukkit.getConsoleSender().sendMessage(String.valueOf(GeneralGuiHolder.getRepairCost(itemStack)));
                                item.setRealstack(BaseHelp.toBase64(p.getItemInHand().clone()));
                                item.setId(CodifyMaterialID.toOrganize(p.getItemInHand().getTypeId(), categoryType, p.getItemInHand().clone()));
                                ItemMeta itemMeta = itemStack.getItemMeta();
                                if (itemMeta.hasLore()) {
                                    List<String> arrayList = itemMeta.getLore();
                                    arrayList.add("§fVendedor: " + p.getName());
                                    DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
                                    arrayList.add("§fPreço: §7" + decimalFormat.format(price) + "§7 Coins");
                                    arrayList.add("");
                                    arrayList.add("§aClique para ver detalhes.");
                                    itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
                                    itemMeta.setLore(arrayList);
                                    itemStack.setItemMeta(itemMeta);
                                } else {
                                    List<String> arrayList = new ArrayList<>();
                                    arrayList.add("§fVendedor: §7" + p.getName());
                                    DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
                                    arrayList.add("§fPreço: §7" + decimalFormat.format(price) + "§7 Coins");
                                    arrayList.add("");
                                    arrayList.add("§aClique para ver detalhes.");
                                    itemMeta.setLore(arrayList);
                                    itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
                                    itemStack.setItemMeta(itemMeta);
                                }
                                itemStack = ItemMetadata.setMetadata(itemStack,"MercadoLong",long2);
                                item.setItemStack(BaseHelp.toBase64(itemStack));
                                item.setaLong(long2);
                                item.setSender(p.getName());
                                item.setValue(price);
                                item.setVendaprivada(true);
                                item.setVendeupraquem(c.getName());
                                ItemStack itemStack1 = itemStack.clone();
                                ItemMeta itemMeta1 = itemStack1.getItemMeta();
                                List<String> list = itemMeta1.getLore();
                                list.set(list.size() - 1, "§eClique para coletar a venda.");
                                itemMeta1.setLore(list);
                                itemStack1.setItemMeta(itemMeta1);
                                item.setGetBackItem(BaseHelp.toBase64(itemStack1));
                                user.getVendendo().add(item);
                                if (user2.getPessoal() == null) user2.setPessoal(new LinkedList<>());
                                user2.getPessoal().add(item);
                                p.setItemInHand(null);
                                DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
                                p.sendMessage("§aSeu item foi colocado à venda por: §7" + decimalFormat.format(price) + "§a.");
                                c.sendMessage("§aO jogador " + p.getName() + " botou um item em seu mercado privado.");
                                AtlasStorage.savePlayer(p.getName(), user, true);
                                AtlasStorage.savePlayer(c.getName(), user2, true);
                                return true;
                            } else {
                                p.sendMessage("§cO jogador necessita estar online para vender um item.");
                                return true;
                            }
                        } catch (NumberFormatException e) {
                            p.sendMessage("");
                            p.sendMessage("§c Ops! Parece que você inseriu um valor inválido!");
                            p.sendMessage("");
                            return true;
                        }
                    }
                    return true;
                }
                if (args.length == 2) {
                    if (args[0].equalsIgnoreCase("vender")) {
                        if (p.getItemInHand() == null || p.getItemInHand().getType() == Material.AIR) {
                            p.sendMessage("§cVocê precisa possuir um item na mão.");
                            return true;
                        }
                        try {
                            double price = Double.parseDouble(args[1]);
                            if (price <= 0){
                                p.sendMessage("");
                                p.sendMessage("§c Ops! Parece que você inseriu um valor inválido!");
                                p.sendMessage("");
                                return true;
                            }
                            CategoryType categoryType = Mercado.canSell(p.getItemInHand());
                            if (categoryType != null) {
                                if (Main.cache2.containsKey(p.getName())) {
                                    User user = Main.cache2.get(p.getName());
                                    Item item = new Item();
                                    item.setRealstack(BaseHelp.toBase64(p.getItemInHand().clone()));
                                    item.setId(CodifyMaterialID.toOrganize(p.getItemInHand().getTypeId(), categoryType, p.getItemInHand().clone()));
                                    ItemStack itemStack = p.getItemInHand();
                                    ItemMeta itemMeta = itemStack.getItemMeta();
                                    if (itemMeta.hasLore()) {
                                        List<String> arrayList = itemMeta.getLore();
                                        arrayList.add("§fVendedor: " + p.getName());
                                        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
                                        arrayList.add("§fPreço: §7" + decimalFormat.format(price) + "§7 Coins");
                                        arrayList.add("");
                                        arrayList.add("§aClique para ver detalhes.");
                                        itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
                                        itemMeta.setLore(arrayList);
                                        itemStack.setItemMeta(itemMeta);
                                    } else {
                                        List<String> arrayList = new ArrayList<>();
                                        arrayList.add("§fVendedor: §7" + p.getName());
                                        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
                                        arrayList.add("§fPreço: §7" + decimalFormat.format(price) + "§7 Coins");
                                        arrayList.add("");
                                        arrayList.add("§aClique para ver detalhes.");
                                        itemMeta.setLore(arrayList);
                                        itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
                                        itemStack.setItemMeta(itemMeta);
                                    }
                                    long long2 = System.currentTimeMillis();
                                    itemStack = ItemMetadata.setMetadata(itemStack,"MercadoLong",long2);
                                    item.setItemStack(BaseHelp.toBase64(itemStack));
                                    item.setSender(p.getName());
                                    item.setValue(Double.parseDouble(args[1]));
                                    item.setaLong(long2);
                                    Bukkit.getConsoleSender().sendMessage("§cLong: " + item.getaLong());
                                    ItemStack itemStack1 = itemStack.clone();
                                    ItemMeta itemMeta1 = itemStack1.getItemMeta();
                                    List<String> list = itemMeta1.getLore();
                                    list.set(list.size() - 1, "§eClique para coletar a venda.");
                                    itemMeta1.setLore(list);
                                    itemStack1.setItemMeta(itemMeta1);
                                    item.setGetBackItem(BaseHelp.toBase64(itemStack1));
                                    DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
                                    p.sendMessage("§aSeu item foi colocado à venda por: §7" + decimalFormat.format(price) + "§a.");
                                    Main.cache.get(categoryType).getItemsInCategory().add(item);
                                    user.getVendendo().add(item);
                                    p.setItemInHand(null);
                                    AtlasStorage.savePlayer(user.getUser(), user, true);
                                    return true;
                                }
                            } else {
                                p.sendMessage("");
                                p.sendMessage("§c Ops! Este item não se encaixa em nenhuma categoria do");
                                p.sendMessage("§c Mercado. Para ver as categorias disponíveis, utilize /mercado ver;");
                                p.sendMessage("");
                                return true;
                            }
                        } catch (NumberFormatException e) {
                            p.sendMessage("");
                            p.sendMessage("§c Ops! Parece que você inseriu um valor inválido!");
                            p.sendMessage("");
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
}