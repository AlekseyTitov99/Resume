package com.pagani.market.api;

import com.pagani.market.Main;
import com.pagani.market.enums.CategoryType;
import com.pagani.market.menu.Mercado;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.IOException;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * author: don't version: 2.0
 */

public class Scroller {

    static {
        JavaPlugin plugin = Main.getPlugin(Main.class);
        Bukkit.getPluginManager().registerEvents(new Listener() {
            @EventHandler
            public void onClick(InventoryClickEvent e) {
                if (e.getSlotType() == InventoryType.SlotType.OUTSIDE || e.getCurrentItem() == null) return;
                if (e.getInventory().getHolder() instanceof Holder) {
                    e.setCancelled(true);
                    Holder holder = (Holder) e.getInventory().getHolder();
                    Scroller scroller = holder.getScroller();
                    Player player = (Player) e.getWhoClicked();
                    if (scroller.getBackSlot() == e.getSlot()) {
                        scroller.getBackConsumer().accept(player);
                    } else if (scroller.getCustomItemActions().containsKey(e.getSlot())) {
                        scroller.getCustomItemActions().get(e.getSlot()).accept(player);
                    } else if (scroller.getNextPageSlot() == e.getSlot()) {
                        if (scroller.hasPage(holder.getPage() + 1)) {
                            scroller.open(player, holder.getPage() + 1);
                        }
                    } else if (scroller.getPreviousPageSlot() == e.getSlot()) {
                        if (scroller.hasPage(holder.getPage() - 1)) {
                            scroller.open(player, holder.getPage() - 1);
                        }
                    } else if (scroller.getAllowedSlots().contains(e.getSlot())) {
                        if (e.getCurrentItem().getType() != Material.AIR && e.getCurrentItem().getType() != Material.WEB) {
                            holder.getScroller().getOnChooseItem().accept(player, e.getCurrentItem());
                            for (Item item : scroller.getItems()) {
                                if (holder.getScroller().getPages().get(holder.getPage()).contains(e.getCurrentItem())) {
                                    for (Integer allowedSlot : holder.getScroller().getAllowedSlots()) {
                                        if (allowedSlot == e.getRawSlot() && holder.getScroller().getPages().get(holder.getPage()).getItem(allowedSlot).equals(BaseHelp.fromBase64(item.getItemStack()))) {
                                            if (Main.cache.get(scroller.getCategoryType()).getItemsInCategory().contains(item)) {
                                                if (item.getSender().equals(player.getName())) {
                                                    player.sendMessage("§cVocê não pode comprar itens de si mesmo.");
                                                    player.closeInventory();
                                                    return;
                                                }
                                                if (player.getInventory().firstEmpty() == -1) {
                                                    player.closeInventory();
                                                    player.sendMessage("§cSeu inventário está lotado.");
                                                    return;
                                                }
                                                Mercado.loadCompra(player, BaseHelp.fromBase64(item.getItemStack()), item);
                                                return;
                                            } else {
                                                player.sendMessage("§cParece que este item não está disponível.");
                                                player.closeInventory();
                                                return;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    } else if (scroller.getSlotbacktoinicial() == e.getRawSlot()) {
                        Mercado.onLoad(player);
                    }
                }
            }
        }, plugin);
    }


    private String name;
    private int size;
    private LinkedList<Item> items;
    private BiConsumer<Player, ItemStack> onChooseItem;
    private int nextPageSlot, previousPageSlot;
    private ItemStack nextPageItem, previousPageItem;
    private HashMap<Integer, ItemStack> customItems;
    private HashMap<Integer, Consumer<Player>> customItemActions;
    private List<Integer> allowedSlots;
    private HashMap<Integer, Inventory> pages;
    private HashMap<Inventory, Item> mapdecompra;
    private int backSlot;
    private int slotbacktoinicial = 49;
    private ItemStack backItem;
    private Consumer<Player> backConsumer;
    private CategoryType categoryType;

    private Scroller(Builder builder) {
        name = builder.name;
        size = builder.size;
        items = builder.items;
        onChooseItem = builder.onChooseItem;
        nextPageSlot = builder.nextPageSlot;
        previousPageSlot = builder.previousPageSlot;
        nextPageItem = builder.nextPageItem;
        previousPageItem = builder.previousPageItem;
        customItems = builder.customItems;
        customItemActions = builder.customItemActions;
        allowedSlots = builder.allowedSlots;
        backSlot = builder.backSlot;
        backItem = builder.backItem;
        backConsumer = builder.backConsumer;
        categoryType = builder.getCategoryType();
        this.pages = new HashMap<>();
        build();
    }

    public static Builder builder() {
        return new Builder();
    }

    private void build() {
        if (items.isEmpty()) {
            Inventory inventory = Bukkit.createInventory(new Holder(this, 1), size, name);
            if (backSlot != -1) {
                inventory.setItem(backSlot, backItem);
            }
            customItems.forEach(inventory::setItem);
            inventory.setItem(49, new ItemBuilder(Material.ARROW).setName("§cVoltar").setLore("§7Clique para voltar ao menu principal.").toItemStack());
            inventory.setItem(22, new ItemBuilder(Material.WEB).setName("§cVazio").setLore("§7No momento nenhum item está à venda.").toItemStack());
            pages.put(1, inventory);
            return;
        }
        List<LinkedList<Item>> lists = getPages(items, allowedSlots.size());
        int page = 1;
        for (List<Item> list : lists) {
            Inventory inventory = Bukkit.createInventory(new Holder(this, page), size, name);
            inventory.setItem(49, new ItemBuilder(Material.ARROW).setName("§cVoltar").setLore("§7Clique para voltar ao menu principal.").toItemStack());
            int slot = 0;
            for (Item it : list) {
                inventory.setItem(allowedSlots.get(slot), BaseHelp.fromBase64(it.getItemStack()));
                slot++;
            }
            customItems.forEach(inventory::setItem);
            inventory.setItem(previousPageSlot, editItem(previousPageItem.clone(), page - 1)); // se for a primeira página, não tem pra onde voltar
            inventory.setItem(nextPageSlot, editItem(nextPageItem.clone(), page + 1));
            if (backSlot != -1) inventory.setItem(backSlot, backItem);

            pages.put(page, inventory);
            page++;
        }
        pages.get(1).setItem(previousPageSlot, new ItemStack(Material.AIR)); // vai na primeira página e remove a flecha de ir pra trás
        pages.get(pages.size()).setItem(nextPageSlot, new ItemStack(Material.AIR)); // vai na última página e remove a flecha de ir pra frente
    }

    private ItemStack editItem(ItemStack item, int page) {
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(item.getItemMeta().getDisplayName().replace("<page>", page + ""));
        item.setItemMeta(meta);
        return item;
    }

    private <T> List<LinkedList<T>> getPages(LinkedList<Item> c, Integer pageSize) { // créditos a https://stackoverflow.com/users/2813377/pscuderi
        LinkedList<T> list = new LinkedList<>((Collection<? extends T>) c);
        if (pageSize == null || pageSize <= 0 || pageSize > list.size()) pageSize = list.size();
        int numPages = (int) Math.ceil((double) list.size() / (double) pageSize);
        List<LinkedList<T>> pages = new ArrayList<>(numPages);
        for (int pageNum = 0; pageNum < numPages; ) {
            LinkedList<T> items = new LinkedList<>(list.subList(pageNum * pageSize, Math.min(++pageNum * pageSize, list.size())));
            pages.add(items);
        }
        return pages;
    }


    public int getTotalPages() {
        return pages.size();
    }

    private boolean hasPage(int page) {
        return pages.containsKey(page);
    }

    public void open(Player player) {
        open(player, 1);
    }

    public void open(Player player, int page) {
        // player.closeInventory();
        player.openInventory(pages.get(page));
    }

    private BiConsumer<Player, ItemStack> getOnChooseItem() {
        return onChooseItem;
    }

    private int getNextPageSlot() {
        return nextPageSlot;
    }

    private int getPreviousPageSlot() {
        return previousPageSlot;
    }

    private List<Integer> getAllowedSlots() {
        return allowedSlots;
    }

    private HashMap<Integer, Consumer<Player>> getCustomItemActions() {
        return customItemActions;
    }

    public HashMap<Integer, Inventory> getPages() {
        return pages;
    }

    private int getBackSlot() {
        return backSlot;
    }

    private Consumer<Player> getBackConsumer() {
        return backConsumer;
    }

    public int getSlotbacktoinicial() {
        return slotbacktoinicial;
    }

    public void setSlotbacktoinicial(int slotbacktoinicial) {
        this.slotbacktoinicial = slotbacktoinicial;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(LinkedList<Item> items2) {
        this.items = items2;
    }

    public HashMap<Inventory, Item> getMapdecompra() {
        return mapdecompra;
    }

    public void setMapdecompra(HashMap<Inventory, Item> mapdecompra) {
        this.mapdecompra = mapdecompra;
    }

    public CategoryType getCategoryType() {
        return categoryType;
    }

    public void setCategoryType(CategoryType categoryType) {
        this.categoryType = categoryType;
    }

    public static final class Builder {

        private final static List<Integer> ALLOWED_SLOTS = Arrays.asList(10, 11, 12, 13, 14, 15, 16, 19, 20, 21, 22, 23, 24, 25, 28, 29, 30, 31, 32, 33, 34
                /*,37,38,39,40,41,42,43*/); // slots para caso o inventário tiver 6 linhas
        private String name;
        private int size;
        private LinkedList<Item> items;
        private BiConsumer<Player, ItemStack> onChooseItem;
        private int nextPageSlot;
        private int previousPageSlot;
        private ItemStack nextPageItem;
        private ItemStack previousPageItem;
        private int backSlot;
        private ItemStack backItem;
        private Consumer<Player> backConsumer;
        private HashMap<Integer, ItemStack> customItems;
        private HashMap<Integer, Consumer<Player>> customItemActions;
        private List<Integer> allowedSlots;
        private CategoryType categoryType3;

        private Builder() {
            this.name = "";
            this.size = 54;
            this.items = new LinkedList<>();
            this.onChooseItem = (player, item) -> {
            };
            this.nextPageSlot = 26;
            this.previousPageSlot = 18;
            this.customItems = new HashMap<>();
            this.customItemActions = new HashMap<>();
            this.allowedSlots = ALLOWED_SLOTS;
            this.backSlot = -1;
            this.backConsumer = player -> {
            };
            this.backItem = getBackFlecha();
            this.nextPageItem = getPageFlecha();
            this.previousPageItem = getPageFlecha();
            this.categoryType3 = getCategoryType();
        }

        public CategoryType getCategoryType() {
            return categoryType3;
        }

        public Builder setCategoryType(CategoryType categoryType3) {
            this.categoryType3 = categoryType3;
            return this;
        }

        private ItemStack getBackFlecha() {
            ItemStack item = new ItemStack(Material.ARROW);
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName(ChatColor.GREEN + "Voltar");
            item.setItemMeta(meta);
            return item;
        }

        private ItemStack getPageFlecha() {
            ItemStack item = new ItemStack(Material.ARROW);
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName(ChatColor.GREEN + "Página <page>");
            item.setItemMeta(meta);
            return item;
        }

        public Builder withBackItem(int backSlot, ItemStack backItem, Consumer<Player> player) {
            this.backItem = backItem;
            this.backSlot = backSlot;
            this.backConsumer = player;
            return this;
        }

        public Builder withBackItem(int backSlot, Consumer<Player> player) {
            this.backSlot = backSlot;
            this.backConsumer = player;
            return this;
        }

        public Builder withName(String name) {
            this.name = name;
            return this;
        }

        public Builder withSize(int size) {
            this.size = size;
            return this;
        }

        public Builder withItems(LinkedList<Item> items) {
            this.items = items;
            return this;
        }

        public Builder withOnChooseItem(BiConsumer<Player, ItemStack> onChooseItem) {
            this.onChooseItem = onChooseItem;
            return this;
        }

        public Builder withNextPageSlot(int nextPageSlot) {
            this.nextPageSlot = nextPageSlot;
            return this;
        }

        public Builder withPreviousPageSlot(int previousPageSlot) {
            this.previousPageSlot = previousPageSlot;
            return this;
        }

        public Builder withNextPageItem(ItemStack nextPageItem) {
            this.nextPageItem = nextPageItem;
            return this;
        }

        public Builder withPreviousPageItem(ItemStack previousPageItem) {
            this.previousPageItem = previousPageItem;
            return this;
        }

        public Builder withCustomItem(int slot, ItemStack item) {
            this.customItems.put(slot, item);
            return this;
        }

        public Builder withCustomItem(int slot, ItemStack item, Consumer<Player> action) {
            this.customItems.put(slot, item);
            this.customItemActions.put(slot, action);
            return this;
        }

        public Builder withAllowedSlots(List<Integer> allowedSlots) {
            this.allowedSlots = allowedSlots;
            return this;
        }

        public Scroller build() {
            return new Scroller(this);
        }
    }

    private static final class Holder implements InventoryHolder {

        private Scroller scroller;
        private int page;

        public Holder(Scroller scroller, int page) {
            this.scroller = scroller;
            this.page = page;
        }

        public int getPage() {
            return page;
        }

        public Scroller getScroller() {
            return scroller;
        }

        @Override
        public Inventory getInventory() {
            return null;
        }
    }

    private final List<ItemStack> ALL_MATERIALS = Stream.of(Material.values()).filter(Material::isBlock).filter(Material::isSolid).filter(material -> !material.isTransparent()).map(ItemStack::new).collect(Collectors.toList());
    private final List<ItemStack> TEN_MATERIALS = ALL_MATERIALS.stream().limit(10).collect(Collectors.toList());
}