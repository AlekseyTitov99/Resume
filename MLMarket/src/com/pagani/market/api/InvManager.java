package com.pagani.market.api;

import org.bukkit.entity.Player;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.stream.Collectors;

public class InvManager {

    public static void openCategory(Player p, Category c) {
        String nome = "";
        switch (c.getType()) {
            case ARMADURAS:
                nome = "Armaduras";
                break;
            case ARMAS:
                nome = "Armas";
                break;
            case LIVROS:
                nome = "Livros";
                break;
            case POCOES:
                nome = "Poções";
                break;
            case FERRAMENTAS:
                nome = "Ferramentas";
                break;
            case MINERIOS:
                nome = "Minérios";
                break;
            case PROTECAO:
                nome = "Proteção";
                break;
            case SPAWNERS:
                nome = "Spawners";
                break;
            case REDSTONE:
                nome = "Redstone";
                break;
        }
        LinkedList<Item> items = c.getItemsInCategory().stream().sorted(Comparator.comparing(Item::getId).reversed()).collect(Collectors.toCollection(LinkedList::new));
        Scroller scroller = Scroller.builder().withName(nome).setCategoryType(c.getType()).withItems(items).build();
        scroller.open(p);
    }
}