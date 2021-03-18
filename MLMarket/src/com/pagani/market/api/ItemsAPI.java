package com.pagani.market.api;

import com.pagani.market.enums.HistoricEnum;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.IOException;
import java.text.DecimalFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

public class ItemsAPI {

    public static String getHistoricItem(Item item, String comprador, HistoricEnum historicEnum) {
        ItemStack itemStack = BaseHelp.fromBase64(item.getRealstack());
        ItemMeta itemMeta = itemStack.getItemMeta();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("HH:mm");

        LocalDateTime triggerTime =
                LocalDateTime.ofInstant(Instant.ofEpochMilli(item.getaLong()),
                        TimeZone.getTimeZone("America/Recife").toZoneId());
        if (historicEnum.equals(HistoricEnum.VENDA)) {
            if (itemMeta.hasLore()) {
                List<String> lore = itemMeta.getLore();
                lore.add("");
                lore.add("§7Vendedor: §e" + item.getSender());
                lore.add("§7Comprador: §e" + comprador);
                DecimalFormat decimalFormat = new DecimalFormat("###,###,###,##");
                lore.add("§7Preço: §e" + decimalFormat.format(item.getValue()) + (item.getValue() == 1 ? " Coin" : " Coins"));
                lore.add("§aData: §7" + formatter.format(triggerTime) + " às " + formatter2.format(triggerTime));
                itemMeta.setLore(lore);
            } else {
                List<String> lore = new ArrayList<>();
                lore.add("");
                lore.add("§7Vendedor: §e" + item.getSender());
                lore.add("§7Comprador: §e" + comprador);
                DecimalFormat decimalFormat = new DecimalFormat("###,###,###,##");
                lore.add("§7Preço: §e" + decimalFormat.format(item.getValue()) + (item.getValue() == 1 ? " Coin" : " Coins"));
                lore.add("§aData: §7" + formatter.format(triggerTime) + " às " + formatter2.format(triggerTime));
                itemMeta.setLore(lore);
            }
        }
        if (historicEnum.equals(HistoricEnum.COMPRA)) {
            if (itemMeta.hasLore()) {
                List<String> lore = itemMeta.getLore();
                lore.add("");
                lore.add("§7Vendedor: §e" + item.getSender());
                lore.add("§7Comprador: §e" + comprador);
                DecimalFormat decimalFormat = new DecimalFormat("###,###,###,##");
                lore.add("§7Preço: §e" + decimalFormat.format(item.getValue()) + (item.getValue() == 1 ? " Coin" : " Coins"));
                lore.add("§aData: §7" + formatter.format(triggerTime) + " às " + formatter2.format(triggerTime));
                itemMeta.setLore(lore);
            } else {
                List<String> lore = new ArrayList<>();
                lore.add("");
                lore.add("§7Vendedor: §e" + item.getSender());
                lore.add("§7Comprador: §e" + comprador);
                DecimalFormat decimalFormat = new DecimalFormat("###,###,###,##");
                lore.add("§7Preço: §e" + decimalFormat.format(item.getValue()) + (item.getValue() == 1 ? " Coin" : " Coins"));
                lore.add("§aData: §7" + formatter.format(triggerTime) + " às " + formatter2.format(triggerTime));
                itemMeta.setLore(lore);
            }
        }
        itemStack.setItemMeta(itemMeta);
        return BaseHelp.toBase64(itemStack);
    }
}