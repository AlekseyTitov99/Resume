package com.pagani.market.menu;

import com.pagani.market.Main;
import com.pagani.market.api.*;
import com.pagani.market.enums.CategoryType;
import com.pagani.market.holders.GuiHolder;
import com.pagani.market.holders.HistoricHolder;
import com.pagani.market.holders.PrivadoHolder;
import com.pagani.market.holders.VendaHolder;
import com.pagani.market.objeto.User;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class Mercado implements Listener {

    public static CategoryType canSell(ItemStack itemStack) {
        if (itemStack.getAmount() < 0) {
            return null;
        }
        Bukkit.getConsoleSender().sendMessage(itemStack.getType().name().toLowerCase());
        if (itemStack.getType().name().toLowerCase().endsWith("_axe") && !itemStack.containsEnchantment(Enchantment.DAMAGE_ALL) || itemStack.getType().name().toLowerCase().endsWith("_pickaxe") || itemStack.getType().name().toLowerCase().endsWith("_spade") || itemStack.getType().name().toLowerCase().endsWith("_hoe")){
        return CategoryType.FERRAMENTAS;
        }
        if (itemStack.getType() == Material.BEDROCK || itemStack.getType() == Material.OBSIDIAN || itemStack.getType() == Material.ENDER_STONE || itemStack.containsEnchantment(Enchantment.DURABILITY) && itemStack.getEnchantmentLevel(Enchantment.DURABILITY) == 200 && itemStack.getType() == Material.REDSTONE_BLOCK || itemStack.getType() == Material.SLIME_BLOCK || itemStack.getType() == Material.PRISMARINE){
            return CategoryType.PROTECAO;
        }
        if (itemStack.getType().name().toLowerCase().endsWith("sword") || itemStack.getType().name().toLowerCase().endsWith("bow") || itemStack.getType().name().toLowerCase().endsWith("_axe")) {
            return CategoryType.ARMAS;
        }
        if (itemStack.getType() == Material.DISPENSER || itemStack.getType() == Material.REDSTONE || itemStack.getType() == Material.REDSTONE || itemStack.getType() == Material.REDSTONE_COMPARATOR || itemStack.getType() == Material.REDSTONE_TORCH_OFF || itemStack.getType() == Material.REDSTONE_TORCH_ON ||
                itemStack.getType() == Material.TNT || itemStack.getType() == Material.REDSTONE_WIRE || itemStack.getType() == Material.DROPPER || itemStack.getType() == Material.DIODE || itemStack.getType() == Material.REDSTONE_BLOCK) {
            return CategoryType.REDSTONE;
        }
        if (itemStack.getType().name().toLowerCase().endsWith("potion") || itemStack.getType().name().toLowerCase().endsWith("golden_apple") || itemStack.getType().name().toLowerCase().endsWith("blaze_rod")
                || itemStack.getType().name().toLowerCase().endsWith("sugar") || itemStack.getType().name().toLowerCase().endsWith("powder") || itemStack.getType().name().toLowerCase().endsWith("stalk") || itemStack.getType() == Material.BEACON) {
            return CategoryType.POCOES;
        }
        if (itemStack.getType().name().toLowerCase().endsWith("enchanted_book")) {
            return CategoryType.LIVROS;
        }
        if (ItemMetadata.hasMetadata(itemStack,"ItemEspecialTARGET")){
            return CategoryType.ESPECIAIS;
        }
        if (itemStack.getType().name().toLowerCase().endsWith("_leggings") || itemStack.getType().name().toLowerCase().endsWith("_boots") || itemStack.getType().name().toLowerCase().endsWith("_chestplate") || itemStack.getType().name().toLowerCase().endsWith("_helmet")) {
            return CategoryType.ARMADURAS;
        }
        if (itemStack.getType() == Material.MONSTER_EGG && itemStack.getDurability() == 50 && !ItemMetadata.hasMetadata(itemStack,"ItemEspecial") || itemStack.getType() == Material.MOB_SPAWNER && ItemMetadata.hasMetadata(itemStack,"SpawnerType")){
            return CategoryType.SPAWNERS;
        }
        if (itemStack.getType().name().toLowerCase().endsWith("_ore") || itemStack.getType() == Material.GOLD_BLOCK || itemStack.getType() == Material.GOLD_INGOT || itemStack.getType() == Material.IRON_INGOT
                || itemStack.getType() == Material.COAL_BLOCK || itemStack.getType() == Material.LAPIS_BLOCK || itemStack.getType() == Material.DIAMOND_BLOCK || itemStack.getType() == Material.DIAMOND ||
                itemStack.getType() == Material.EMERALD_BLOCK || itemStack.getType() == Material.EMERALD || itemStack.getType() == Material.IRON_BLOCK ||
                itemStack.getType() == Material.INK_SACK && itemStack.getDurability() == (short) 4 || itemStack.getType() == Material.COAL || itemStack.getType() == Material.COAL_ORE) {
            return CategoryType.MINERIOS;
        }
        return null;
    }

    public static void loadCompra(Player player, ItemStack itemStack, Item item) {
        Inventory inventory = Bukkit.createInventory(new GuiHolder(null, item), 45, "Confirmação de Compra");
        inventory.setItem(13, itemStack);
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        inventory.setItem(30, new ItemBuilder(Material.PAPER).setName("§eInformações").setLore("§7Ao clicar em confirmar, serão debitados", "§e" + decimalFormat.format(item.getValue()) + " Coins§7 de sua conta.").toItemStack());
        inventory.setItem(31, new ItemBuilder(Material.STAINED_GLASS_PANE).setDurability((short) 5).setName("§aConfirmar").setLore("§7Clique aqui para confirmar a compra.").toItemStack());
        inventory.setItem(32, new ItemBuilder(Material.STAINED_GLASS_PANE).setDurability((short) 14).setName("§cCancelar").setLore("§7Clique aqui para cancelar a compra.").toItemStack());
        player.openInventory(inventory);
    }

    public static List<Integer> array = new ArrayList<>(Arrays.asList(10, 11, 12, 13, 14, 15, 16, 19, 20, 21, 22, 23, 24, 25, 28, 29, 30, 31, 32, 33, 34));

    public static void openSellsMarket(Player p) throws IOException {
        if (Main.cache2.containsKey(p.getName())) {
            User user = Main.cache2.get(p.getName());
            if (user.getVendendo().isEmpty()) {
                VendaHolder vendaHolder = new VendaHolder(user.getVendendo(), null);
                vendaHolder.setPagactual(0);
                Inventory inventory = Bukkit.createInventory(vendaHolder, 54, "Suas Vendas");
                inventory.setItem(22, new ItemBuilder(Material.WEB).setName("§cVázio").setLore("§7Você não tem nenhuma venda :(").toItemStack());
                inventory.setItem(49, getBackFlecha());
                vendaHolder.setInventory(inventory);
                vendaHolder.getPags().add(inventory);
                p.openInventory(vendaHolder.getInventory());
                return;
            }
            Inventory inventory = null;
            VendaHolder vendaHolder = new VendaHolder(user.getVendendo(), inventory);
            vendaHolder.setPagactual(0);
            int slot = 10;
            int pag = 1;
            int lastPag = (int) Math.ceil(user.getVendendo().size() / 21.0);
            Bukkit.getConsoleSender().sendMessage(String.valueOf(lastPag));
            for (Item item : user.getVendendo()) {
                if (slot == 10) {
                    inventory = Bukkit.createInventory(vendaHolder, 54, "Suas Vendas");
                    if (pag == 1) {
                        vendaHolder.setInventory(inventory);
                    }
                    if (pag > 1) {
                        inventory.setItem(18, new ItemBuilder(Material.ARROW).setName("§aPágina " + (pag - 1)).setLore("§7Clique para voltar", "§7de página.").toItemStack());
                    }
                    if (pag++ != lastPag) {
                        inventory.setItem(26, new ItemBuilder(Material.ARROW).setName("§aPágina " + pag).setLore("§7Clique para avançar", "§7de página.").toItemStack());
                    }
                    vendaHolder.getPags().add(inventory);
                    inventory.setItem(49, getBackFlecha());
                }
                inventory.setItem(slot, BaseHelp.fromBase64(item.getGetBackItem()));
                slot += ((slot == 34) ? -24 : ((slot == 16 || slot == 25) ? 3 : 1));
            }
            p.openInventory(vendaHolder.getInventory());
            p.updateInventory();
        }
    }

    public static ItemStack getBackFlecha() {
        ItemStack item = new ItemStack(Material.ARROW);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.GREEN + "Voltar");
        item.setItemMeta(meta);
        return item;
    }

    public static void organizeHistoric(HistoricHolder s,Player player,CategoryType category){
        s.getPags().clear();
        s.setPagactual(0);
        for (HistoricItem historicItem : s.getHistoricItems()) {
            if (historicItem.getId() == 0){
                historicItem.setId(BaseHelp.fromBase64(historicItem.getItem()).getTypeId());
            }
        }
        LinkedList<HistoricItem> historicItems = s.getHistoricItems().stream().filter(f -> f.getCategory() == category).collect(Collectors.toCollection(LinkedList::new));
        Inventory inventory = null;
        if (historicItems.isEmpty()){
            inventory = Bukkit.createInventory(s, 54, "Histórico do Mercado");
            inventory.setItem(22, new ItemBuilder(Material.WEB).setName("§cVázio").setLore("§7Não existem registros deste histórico.").toItemStack());
            ItemStack itemStack = new ItemStack(Material.HOPPER);
            List<String> lore = new ArrayList<>();
            lore.add("§7Veja apenas os itens que");
            lore.add("§7você deseja no leilão.");
            lore.add("");
            lore.add("§7 Todos os Itens");
            if (category != null) {
                if (category == CategoryType.ARMAS) {
                    lore.add("§b ▸ Armas");
                } else {
                    lore.add("§7 Armas");
                }
                if (category == CategoryType.ARMADURAS) {
                    lore.add("§b ▸ Armaduras");
                } else {
                    lore.add("§7 Armaduras");
                }
                if (category == CategoryType.FERRAMENTAS) {
                    lore.add("§b ▸ Ferramentas");
                } else {
                    lore.add("§7 Ferramentas");
                }
                if (category == CategoryType.MINERIOS) {
                    lore.add("§b ▸ Minérios");
                } else {
                    lore.add("§7 Minérios");
                }
                if (category == CategoryType.REDSTONE) {
                    lore.add("§b ▸ Redstone");
                } else {
                    lore.add("§7 Redstone");
                }
                if (category == CategoryType.ESPECIAIS) {
                    lore.add("§b ▸ Especiais");
                } else {
                    lore.add("§7 Especiais");
                }
                if (category == CategoryType.LIVROS) {
                    lore.add("§b ▸ Livros");
                } else {
                    lore.add("§7 Livros");
                }
                if (category == CategoryType.POCOES) {
                    lore.add("§b ▸ Poções");
                } else {
                    lore.add("§7 Poções");
                }
                if (category == CategoryType.PROTECAO){
                    lore.add("§b ▸ Proteção");
                } else {
                    lore.add("§7 Proteção");
                }
            } else {
                lore.add("§b ▸ Todos os Itens");
                lore.add("§7 Armas");
                lore.add("§7 Armaduras");
                lore.add("§7 Ferramentas");
                lore.add("§7 Minérios");
                lore.add("§7 Redstone");
                lore.add("§7 Especiais");
                lore.add("§7 Livros");
                lore.add("§7 Poções");
                lore.add("§7 Proteção");
            }
            lore.add("");
            lore.add("§aClique para alterar.");
            ItemMeta itemMeta = itemStack.getItemMeta();
            itemMeta.setDisplayName("§aFiltro");
            itemMeta.setLore(lore);
            itemStack.setItemMeta(itemMeta);
            inventory.setItem(49,itemStack);
            inventory.setItem(48,getBackFlecha());
            s.getPags().add(inventory);
            player.openInventory(inventory);
        }
        int pag = 1;
        final int lastPag = (int) Math.ceil(historicItems.size() / 21.0);
        int slot = 10;
        for (HistoricItem historicItem : historicItems) {
            if (slot == 10) {
                inventory = Bukkit.createInventory(s, 54, "Histórico do Mercado");
                if (pag > 1) {
                    inventory.setItem(18, new ItemBuilder(Material.ARROW).setName("§aPágina " + (pag - 1)).setLore("§7Clique para voltar", "§7de página.").toItemStack());
                }
                if (pag++ != lastPag) {
                    inventory.setItem(26, new ItemBuilder(Material.ARROW).setName("§aPágina " + pag).setLore("§7Clique para avançar", "§7de página.").toItemStack());
                }
                ItemStack itemStack = new ItemStack(Material.HOPPER);
                List<String> lore = new ArrayList<>();
                lore.add("§7Veja apenas os itens que");
                lore.add("§7você deseja no leilão.");
                lore.add("");
                lore.add("§7 Todos os Itens");
                if (category != null) {
                    if (category == CategoryType.ARMAS) {
                        lore.add("§b ▸ Armas");
                    } else {
                        lore.add("§7 Armas");
                    }
                    if (category == CategoryType.ARMADURAS) {
                        lore.add("§b ▸ Armaduras");
                    } else {
                        lore.add("§7 Armaduras");
                    }
                    if (category == CategoryType.FERRAMENTAS) {
                        lore.add("§b ▸ Ferramentas");
                    } else {
                        lore.add("§7 Ferramentas");
                    }
                    if (category == CategoryType.MINERIOS) {
                        lore.add("§b ▸ Minérios");
                    } else {
                        lore.add("§7 Minérios");
                    }
                    if (category == CategoryType.REDSTONE) {
                        lore.add("§b ▸ Redstone");
                    } else {
                        lore.add("§7 Redstone");
                    }
                    if (category == CategoryType.ESPECIAIS) {
                        lore.add("§b ▸ Especiais");
                    } else {
                        lore.add("§7 Especiais");
                    }
                    if (category == CategoryType.LIVROS) {
                        lore.add("§b ▸ Livros");
                    } else {
                        lore.add("§7 Livros");
                    }
                    if (category == CategoryType.POCOES) {
                        lore.add("§b ▸ Poções");
                    } else {
                        lore.add("§7 Poções");
                    }
                    if (category == CategoryType.PROTECAO){
                        lore.add("§b ▸ Proteção");
                    } else {
                        lore.add("§7 Proteção");
                    }
                } else {
                    lore.add("§b ▸ Todos os Itens");
                    lore.add("§7 Armas");
                    lore.add("§7 Armaduras");
                    lore.add("§7 Ferramentas");
                    lore.add("§7 Minérios");
                    lore.add("§7 Redstone");
                    lore.add("§7 Especiais");
                    lore.add("§7 Livros");
                    lore.add("§7 Poções");
                    lore.add("§7 Proteção");
                }
                lore.add("");
                lore.add("§aClique para alterar.");
                ItemMeta itemMeta = itemStack.getItemMeta();
                itemMeta.setDisplayName("§aFiltro");
                itemMeta.setLore(lore);
                itemStack.setItemMeta(itemMeta);
                inventory.setItem(49,itemStack);
                inventory.setItem(48,getBackFlecha());
                s.getPags().add(inventory);
            }
            inventory.setItem(slot, BaseHelp.fromBase64(historicItem.getItem()));
            slot += ((slot == 34) ? -24 : ((slot == 16 || slot == 25) ? 3 : 1));
        }
        player.openInventory(s.getPags().get(0));
        player.updateInventory();
    }

    public static void openHistoricInventory(Player player) throws IOException {
        Inventory inventory = null;
        int geral = Main.cache.get(CategoryType.ARMAS).getHistoricItems().size()
                + Main.cache.get(CategoryType.ARMADURAS).getHistoricItems().size() + Main.cache.get(CategoryType.POCOES).getHistoricItems().size()
                + Main.cache.get(CategoryType.MINERIOS).getHistoricItems().size() +
                Main.cache.get(CategoryType.FERRAMENTAS).getHistoricItems().size() + Main.cache.get(CategoryType.LIVROS).getHistoricItems().size() +
                Main.cache.get(CategoryType.PROTECAO).getHistoricItems().size() + Main.cache.get(CategoryType.SPAWNERS).getHistoricItems().size();
        if (geral == 0) {
            HistoricHolder historicHolder = new HistoricHolder();
            inventory = Bukkit.createInventory(historicHolder, 54, "Histórico do Mercado");
            inventory.setItem(22, new ItemBuilder(Material.WEB).setName("§cVázio").setLore("§7Você não recebeu nenhuma oferta ainda :)").toItemStack());
            ItemStack itemStack = new ItemStack(Material.HOPPER);
            List<String> lore = new ArrayList<>();
            lore.add("§7Veja apenas os itens que");
            lore.add("§7você deseja no leilão.");
            lore.add("");
            lore.add("§b ▸ Todos os Itens");
            lore.add("§7 Armas");
            lore.add("§7 Armaduras");
            lore.add("§7 Ferramentas");
            lore.add("§7 Minérios");
            lore.add("§7 Redstone");
            lore.add("§7 Especiais");
            lore.add("§7 Livros");
            lore.add("§7 Poções");
            lore.add("");
            lore.add("§aClique para alterar.");
            ItemMeta itemMeta = itemStack.getItemMeta();
            itemMeta.setDisplayName("§aFiltro");
            itemMeta.setLore(lore);
            itemStack.setItemMeta(itemMeta);
            inventory.setItem(49,itemStack);
            inventory.setItem(48, getBackFlecha());
            historicHolder.getPags().add(inventory);
            player.openInventory(inventory);
            return;
        }
        int pag = 1;
        final int lastPag = (int) Math.ceil(geral / 21.0);
        HistoricHolder historicHolder = new HistoricHolder();
        historicHolder.setPagactual(0);
        int slot = 10;
        for (CategoryType value : CategoryType.values()) {
            if (Main.cache.get(value).getHistoricItems().isEmpty()) {
                continue;
            } else {
                for (HistoricItem historicItem : Main.cache.get(value).getHistoricItems()){
                    if (slot == 10) {
                        inventory = Bukkit.createInventory(historicHolder, 54, "Histórico do Mercado");
                        if (pag > 1) {
                            inventory.setItem(18, new ItemBuilder(Material.ARROW).setName("§aPágina " + (pag - 1)).setLore("§7Clique para voltar", "§7de página.").toItemStack());
                        }
                        if (pag++ != lastPag) {
                            inventory.setItem(26, new ItemBuilder(Material.ARROW).setName("§aPágina " + pag).setLore("§7Clique para avançar", "§7de página.").toItemStack());
                        }
                        ItemStack itemStack = new ItemStack(Material.HOPPER);
                        List<String> lore = new ArrayList<>();
                        lore.add("§7Veja apenas os itens que");
                        lore.add("§7você deseja no leilão.");
                        lore.add("");
                        lore.add("§b ▸ Todos os Itens");
                        lore.add("§7 Armas");
                        lore.add("§7 Armaduras");
                        lore.add("§7 Ferramentas");
                        lore.add("§7 Minérios");
                        lore.add("§7 Redstone");
                        lore.add("§7 Especiais");
                        lore.add("§7 Livros");
                        lore.add("§7 Poções");
                        lore.add("");
                        lore.add("§aClique para alterar.");
                        ItemMeta itemMeta = itemStack.getItemMeta();
                        itemMeta.setDisplayName("§aFiltro");
                        itemMeta.setLore(lore);
                        itemStack.setItemMeta(itemMeta);
                        inventory.setItem(49,itemStack);
                        inventory.setItem(48,getBackFlecha());
                        historicHolder.getPags().add(inventory);
                    }
                    historicHolder.getHistoricItems().add(historicItem);
                    inventory.setItem(slot, BaseHelp.fromBase64(historicItem.getItem()));
                    slot += ((slot == 34) ? -24 : ((slot == 16 || slot == 25) ? 3 : 1));
                }
            }
        }
        player.openInventory(historicHolder.getPags().get(0));
        player.updateInventory();
    }

    public static void openPrivateMarket(Player player) throws IOException {
        if (Main.cache2.containsKey(player.getName())) {
            User user = Main.cache2.get(player.getName());
            Inventory inventory = null;
            if (user.getPessoal().isEmpty()) {
                inventory = Bukkit.createInventory(new PrivadoHolder(user.getPessoal(), new ArrayList<>(), 0, new LinkedList<>()), 54, "Mercado Privado");
                inventory.setItem(22, new ItemBuilder(Material.WEB).setName("§cVázio").setLore("§7Você não recebeu nenhuma oferta ainda :)").toItemStack());
                PrivadoHolder privadoHolder = (PrivadoHolder) inventory.getHolder();
                inventory.setItem(49, getBackFlecha());
                privadoHolder.setInventory(inventory);
                privadoHolder.getPags().add(inventory);
                player.openInventory(privadoHolder.getInventory());
                return;
            }
            int slot = 10;
            int pag = 1;
            final int lastPag = (int) Math.ceil(user.getPessoal().size() / 21.0);
            PrivadoHolder privadoHolder = new PrivadoHolder(user.getPessoal(), array, pag - 1, new LinkedList<>());
            privadoHolder.setPagactual(pag - 1);
            for (Item item : user.getPessoal()) {
                if (slot == 10) {
                    inventory = Bukkit.createInventory(privadoHolder, 54, "Mercado Privado");
                    if (pag == 1) {
                        privadoHolder.setInventory(inventory);
                    }
                    if (pag > 1) {
                        inventory.setItem(18, new ItemBuilder(Material.ARROW).setName("§aPágina " + (pag - 1)).setLore("§7Clique para voltar", "§7de página.").toItemStack());
                    }
                    if (pag++ != lastPag) {
                        inventory.setItem(26, new ItemBuilder(Material.ARROW).setName("§aPágina " + pag).setLore("§7Clique para avançar", "§7de página.").toItemStack());
                    }
                    inventory.setItem(49, getBackFlecha());
                    privadoHolder.getPags().add(inventory);
                }
                inventory.setItem(slot, BaseHelp.fromBase64(item.getItemStack()));
                slot += ((slot == 34) ? -24 : ((slot == 16 || slot == 25) ? 3 : 1));
            }
            if (privadoHolder.getPags().isEmpty()) {
                return;
            } else {
                player.openInventory(privadoHolder.getInventory());
                player.updateInventory();
            }
        }
        return;
    }

    @EventHandler
    public void onClick(InventoryClickEvent e) throws IOException {
        Player p = (Player) e.getWhoClicked();
        if (p.getOpenInventory().getTitle().equalsIgnoreCase("Categorias do Mercado")) {
            e.setCancelled(true);
            if (e.getCurrentItem() == null || e.getCurrentItem().getType() == Material.AIR) return;
            if (e.getRawSlot() == 10) {
                InvManager.openCategory(p, Main.cache.get(CategoryType.ARMAS));
                return;
            }
            if (e.getRawSlot() == 11) {
                InvManager.openCategory(p, Main.cache.get(CategoryType.ARMADURAS));
                return;
            }
            if (e.getRawSlot() == 12) {
                InvManager.openCategory(p, Main.cache.get(CategoryType.FERRAMENTAS));
                return;
            }
            if (e.getRawSlot() == 13) {
                InvManager.openCategory(p, Main.cache.get(CategoryType.LIVROS));
                return;
            }
            if (e.getRawSlot() == 14) {
                InvManager.openCategory(p, Main.cache.get(CategoryType.POCOES));
                return;
            }
            if (e.getRawSlot() == 21) {
                InvManager.openCategory(p, Main.cache.get(CategoryType.MINERIOS));
                return;
            }
            if (e.getRawSlot() == 15) {
                InvManager.openCategory(p, Main.cache.get(CategoryType.REDSTONE));
                return;
            }
            if (e.getRawSlot() == 19){
                InvManager.openCategory(p, Main.cache.get(CategoryType.PROTECAO));
            }
            if (e.getRawSlot() == 20){
                InvManager.openCategory(p, Main.cache.get(CategoryType.SPAWNERS));
            }
            if (e.getRawSlot() == 22){
                InvManager.openCategory(p, Main.cache.get(CategoryType.ESPECIAIS));
            }
            if (e.getRawSlot() == 39) {
                openSellsMarket(p);
                return;
            }
            if (e.getRawSlot() == 40) {
                openPrivateMarket(p);
                return;
            }
            if (e.getRawSlot() == 36) {
                Expired.openExpiredInv(p);
                return;
            }
            if (e.getRawSlot() == 41) {
                openHistoricInventory(p);
                return;
            }
        }
    }

    public static void onLoad(Player p) {
        if (Main.cache.isEmpty()) {
            p.sendMessage("§cAguarde, mercado carregando.");
            return;
        }
        Inventory inventory = Bukkit.createInventory(null, 45, "Categorias do Mercado");
        ItemStack armas = new ItemBuilder(Material.DIAMOND_SWORD).setName("§aArmas§8 (" + (Main.cache.get(CategoryType.ARMAS).getItemsInCategory().size() + "§8 itens)")).addItemFlag(ItemFlag.HIDE_ATTRIBUTES).addItemFlag(ItemFlag.HIDE_ATTRIBUTES).setLore("§7Itens que você encontra nesta", "§7categoria:", "", "§f▪ Espadas", "§f▪ Arcos", "§f▪ Machados de Combate", "", "§aClique para abrir!").toItemStack();
        armas.setAmount((Main.cache.get(CategoryType.ARMAS).getItemsInCategory().size() == 0 ? 0 : Main.cache.get(CategoryType.ARMAS).getItemsInCategory().size()));
        ItemStack armaduras = new ItemBuilder(Material.DIAMOND_CHESTPLATE).addItemFlag(ItemFlag.HIDE_ATTRIBUTES).addItemFlag(ItemFlag.HIDE_ATTRIBUTES).setName("§aArmaduras§8 (" + (Main.cache.get(CategoryType.ARMADURAS).getItemsInCategory().size() + "§8 itens)")).setLore("§7Itens que você encontra nesta", "§7categoria:", "", "§f▪ Armaduras em geral", "", "§aClique para abrir!").toItemStack();
        armaduras.setAmount((Main.cache.get(CategoryType.ARMADURAS).getItemsInCategory().size() == 0 ? 0 : Main.cache.get(CategoryType.ARMADURAS).getItemsInCategory().size()));
        ItemStack ferramentas = new ItemBuilder(Material.DIAMOND_PICKAXE).addItemFlag(ItemFlag.HIDE_ATTRIBUTES).addItemFlag(ItemFlag.HIDE_ATTRIBUTES).addEnchant(Enchantment.DIG_SPEED, 1).addItemFlag(ItemFlag.HIDE_ATTRIBUTES).addItemFlag(ItemFlag.HIDE_ENCHANTS).setName("§aFerramentas§8 (" + (Main.cache.get(CategoryType.FERRAMENTAS).getItemsInCategory().size() + "§8 itens)")).setLore("§7Itens que você encontra nesta", "§7categoria:", "", "§f▪ Picaretas",
                "§f▪ Pás"
                , "§f▪ Machados", "§f▪ Enxadas", "", "§aClique para abrir!").toItemStack();
        ferramentas.setAmount((Main.cache.get(CategoryType.FERRAMENTAS).getItemsInCategory().size() == 0 ? 0 : Main.cache.get(CategoryType.FERRAMENTAS).getItemsInCategory().size()));
        ItemStack livros = new ItemBuilder(Material.ENCHANTED_BOOK).setName("§aLivros§8 (" + (Main.cache.get(CategoryType.LIVROS).getItemsInCategory().size() + "§8 itens)")).addItemFlag(ItemFlag.HIDE_ATTRIBUTES).addItemFlag(ItemFlag.HIDE_ATTRIBUTES).setLore("§7Itens que você encontra nesta", "§7categoria:", "", "§f▪ Livros", "", "§aClique para abrir!").toItemStack();
        livros.setAmount((Main.cache.get(CategoryType.LIVROS).getItemsInCategory().size() == 0 ? 0 : Main.cache.get(CategoryType.LIVROS).getItemsInCategory().size()));
        ItemStack poções = new ItemBuilder(Material.GLASS_BOTTLE).setName("§aPoções §8(" + (Main.cache.get(CategoryType.POCOES).getItemsInCategory().size() + "§8 itens)")).addItemFlag(ItemFlag.HIDE_ATTRIBUTES).setLore("§7Itens que você encontra nesta", "§7categoria:", "", "§f▪ Qualquer tipo de poção", "", "§aClique para abrir!").toItemStack();
        poções.setAmount((Main.cache.get(CategoryType.POCOES).getItemsInCategory().size() == 0 ? 0 : Main.cache.get(CategoryType.POCOES).getItemsInCategory().size()));
        inventory.setItem(10, armas);
        inventory.setItem(11, armaduras);
        inventory.setItem(12, ferramentas);
        inventory.setItem(13, livros);
        inventory.setItem(14, poções);
        ItemStack minerios = new ItemBuilder(Material.DIAMOND_ORE).setName("§aMinérios §8(" + (Main.cache.get(CategoryType.MINERIOS).getItemsInCategory().size() + " itens)")).addItemFlag(ItemFlag.HIDE_ATTRIBUTES).setLore("§7Itens que você encontra nesta", "§7categoria:", "", "§f▪ Esmeralda", "§f▪ Bloco de Esmeralda", "§f▪ Diamante", "§f▪ Bloco de Diamante", "§f▪ Barra de Ouro", "§f▪ Bloco de Ouro", "§f▪ Barra de Ferro", "§f▪ Bloco de Ferro", "§f▪ Lápis-Lazúli", "§f▪ Bloco de Lápis-Lazúli", "", "§aClique para abrir!").toItemStack();
        minerios.setAmount((Main.cache.get(CategoryType.MINERIOS).getItemsInCategory().size()));
        inventory.setItem(21, minerios);
        ItemStack redstone = new ItemBuilder(Material.REDSTONE).addEnchant(Enchantment.DURABILITY, 1).addItemFlag(ItemFlag.HIDE_ENCHANTS).setLore("§7Itens que você encontra nesta", "§7categoria:", "", "§f▪ Dinamite", "§f▪ Redstone", "§f▪ Bloco de Redstone", "§f▪ Tocha de Redstone", "§f▪ Repetidor de Redstone", "§f▪ Comparador de Redstone",
                        "§f▪ Liberador", "§f▪ Ejetor", "", "§aClique para abrir.").setName("§aRedstone §8(" + (Main.cache.get(CategoryType.REDSTONE).getItemsInCategory().size() + " itens)")).toItemStack();
        redstone.setAmount((Main.cache.get(CategoryType.REDSTONE).getItemsInCategory().size() == 0 ? 0 : Main.cache.get(CategoryType.REDSTONE).getItemsInCategory().size()));
        inventory.setItem(15, redstone);

        ItemStack protecao = new ItemBuilder(Material.BEDROCK).addItemFlag(ItemFlag.HIDE_ENCHANTS).setLore("§7Itens que você encontra nesta", "§7categoria:", "", "§f▪ Blocos de proteção"
                , "", "§aClique para abrir.").setName("§aBlocos de Proteção §8(" + (Main.cache.get(CategoryType.PROTECAO).getItemsInCategory().size() + " itens)")).toItemStack();
        protecao.setAmount((Main.cache.get(CategoryType.PROTECAO).getItemsInCategory().size()));

        ItemStack geradores = new ItemBuilder(Material.MONSTER_EGG).setDurability((short)56).setLore("§7Itens que você encontra nesta", "§7categoria:", "", "§f▪ Qualquer Tipo de Gerador"
                ,"§f▪ Ovo de Creeper", "", "§aClique para abrir.").setName("§aGeradores e Creeper §8(" + (Main.cache.get(CategoryType.SPAWNERS).getItemsInCategory().size() + " itens)")).toItemStack();
        geradores.setAmount((Main.cache.get(CategoryType.SPAWNERS).getItemsInCategory().size()));

        inventory.setItem(19,protecao);
        inventory.setItem(20,geradores);

        ItemStack especiais = new ItemBuilder(Material.NETHER_STAR).addItemFlag(ItemFlag.HIDE_ENCHANTS).setLore("§7Itens que você encontra nesta", "§7categoria:", "", "§f▪ Qualquer item especial", "", "§aClique para abrir.").setName("§aItens Especiais §8(" + (Main.cache.get(CategoryType.ESPECIAIS).getItemsInCategory().size() + " itens)")).toItemStack();
        especiais.setAmount((Main.cache.get(CategoryType.ESPECIAIS).getItemsInCategory().size()));

        inventory.setItem(22,especiais);
        ItemStack informações = new ItemBuilder(Material.PAPER).setName("§eInformações").setLore("§7Aqui é um ambiente onde", "§7você consegue vender itens", "§7à seu preço onde todos", "§7podem acessar, e comprar.").addItemFlag(ItemFlag.HIDE_ATTRIBUTES).toItemStack();
        inventory.setItem(44, informações);
        ItemStack chest = new ItemBuilder(Material.CHEST).setName("§aMeus itens").setLore("§7Clique para ver os itens que você", "§7anunciou no mercado.", "", "§aClique para abrir.").addItemFlag(ItemFlag.HIDE_ATTRIBUTES).toItemStack();
        inventory.setItem(39, chest);
        ItemStack expirados = new ItemBuilder(Material.EMERALD).setName("§eMercado Pessoal").setLore("§7Clique para ver os itens que outros jogadores", "§7colocarem a venda exclusivamente para você.", "", "§aClique para abrir.").addItemFlag(ItemFlag.HIDE_ATTRIBUTES).toItemStack();
        inventory.setItem(40, expirados);
        ItemStack historico = new ItemBuilder(Material.BOOK).setName("§eHistórico do Mercado").setLore("§7Clique aqui para ver os itens que já", "§7foram para o histórico do mercado.", "", "§aClique para abrir.").addItemFlag(ItemFlag.HIDE_ATTRIBUTES).toItemStack();
        inventory.setItem(41, historico);
        ItemStack expiradostrue = new ItemBuilder(Material.SKULL_ITEM).setDurability((short) 3).setSkullOwner(p.getName()).setName("§eItens Expirados").setLore("§7Clique para ver os itens que expiraram", "§7de suas vendas", "", "§aClique para abrir.").addItemFlag(ItemFlag.HIDE_ATTRIBUTES).toItemStack();
        inventory.setItem(36, expiradostrue);
        p.openInventory(inventory);
        p.updateInventory();
    }

}