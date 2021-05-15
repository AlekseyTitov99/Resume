package com.pagani.dragonupgrades.features;

import com.massivecraft.factions.entity.MPlayer;
import com.pagani.dragonupgrades.Main;
import com.pagani.dragonupgrades.object.User;
import com.pagani.dragonupgrades.utils.Banner;
import com.pagani.dragonupgrades.utils.ItemBuilder;
import nuvemplugins.solaryeconomy.app.SolaryEconomy;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Beacon;
import org.bukkit.craftbukkit.v1_8_R3.block.CraftBeacon;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

public class Menu {

    public static void Open2(Player p){
        Inventory inventory = Bukkit.createInventory(null,45,"Tipos de Upgrade");
        double coin = SolaryEconomy.economia.getBalance(p.getName()).doubleValue();
        MPlayer mPlayer = MPlayer.get(p);
        if (Main.cache.containsKey(mPlayer.getFactionName())) {
            User user = Main.cache.get(mPlayer.getFactionName());
            ItemStack item1 = new ItemBuilder(Material.GOLD_INGOT).setName("§eNível I").setLore("§7Faça um upgrade da sua facção", "§7para receber várias vantagens.","§7listadas abaixo."
                    , "§fVantagens:", "", "§e ● §7Aumenta +1 vagas na facção;",
                    "§e ● §7Adiciona +3% a mais em toda venda de item na loja;",
                    "§e ● §7Adiciona +10% em drops de mobs;",
                    "§e ● §7Acrescenta +10% de xp em habilidades.", "", "§fPreço: §7200.000",
                    "", (coin >= 200000 && !user.isNivel1() ? "§cClique para comprar a evolução." : (user.isNivel1() ?  "§eVocê já possui essa evolução" : "§cVocê não possui dinheiro suficiente."))).addItemFlag(ItemFlag.HIDE_ATTRIBUTES).toItemStack();
            ItemStack item2 = new ItemBuilder(Material.DIAMOND_PICKAXE).setName("§eNível II").setLore("§7Faça um upgrade da sua facção", "§7para receber várias vantagens.","§7listadas abaixo."
                    , "§fVantagens:", "", "§e ● §7Aumenta +1 vagas na facção;",
                    "§e ● §7Adiciona +4% a mais em toda venda de item na loja;",
                    "§e ● §7Adiciona +15% em drops de mobs;",
                    "§e ● §7Acrescenta +20% de xp em habilidades.", "", "§fPreço:§7 400.000",
                    "", (coin >= 400000 && !user.isNivel2() ? "§cClique para comprar a evolução." : (user.isNivel2() ?  "§eVocê já possui essa evolução" : "§cVocê não possui dinheiro suficiente."))).addItemFlag(ItemFlag.HIDE_ATTRIBUTES).toItemStack();
            ItemStack item3 = new ItemBuilder(Material.DIAMOND_SWORD).setName("§eNível III").setLore("§7Faça um upgrade da sua facção", "§7para receber várias vantagens.","§7listadas abaixo."
                    , "§fVantagens:", "", "§e ● §7Aumenta +1 vagas na facção;",
                    "§e ● §7Adiciona +5% a mais em toda venda de item na loja;",
                    "§e ● §7Adiciona +20% em drops de mobs;",
                    "§e ● §7Acrescenta +30% de xp em habilidades.", "", "§fPreço: §7500.000",
                    "", (coin >= 500000 && !user.isNivel3() ? "§cClique para comprar a evolução." : (user.isNivel3() ?  "§eVocê já possui essa evolução" : "§cVocê não possui dinheiro suficiente."))).addItemFlag(ItemFlag.HIDE_ATTRIBUTES).toItemStack();
            ItemStack item4 = new ItemBuilder(Material.HAY_BLOCK).setName("§eNível IV").setLore("§7Faça um upgrade da sua facção", "§7para receber várias vantagens.","§7listadas abaixo."
                    , "§fVantagens:", "", "§e ● §7Aumenta +1 vagas na facção;",
                    "§e ● §7Adiciona +6% a mais em toda venda de item na loja;",
                    "§e ● §7Adiciona +25% em drops de mobs;",
                    "§e ● §7acrescenta +40% de xp em habilidades.","","§fPreço: §7650.000",
                    "",(coin >= 650000 && !user.isNivel4() ? "§cClique para comprar a evolução." : (user.isNivel4() ?  "§eVocê já possui essa evolução" : "§cVocê não possui dinheiro suficiente."))).addItemFlag(ItemFlag.HIDE_ATTRIBUTES).toItemStack();
            ItemStack item5 = new ItemBuilder(Material.DIAMOND).setName("§eNível V").setLore("§7Faça um upgrade da sua facção", "§7para receber várias vantagens.","§7listadas abaixo."
                    , "§fVantagens:", "", "§e ● §7Aumenta +1 vagas na facção;",
                    "§e ● §7Acrescenta +1 de poder em todos na facção;",
                    "§e ● §7Adiciona +8% a mais em toda venda de item na loja;",
                    "§e ● §7Adiciona +30% em drops de mobs;",
                    "§e ● §7Acrescenta +50% de xp em habilidades.","","§fPreço:§7 800.000",
                    "",(coin >= 800000 && !user.isNivel5() ? "§cClique para comprar a evolução." : (user.isNivel5() ?  "§eVocê já possui essa evolução" : "§cVocê não possui dinheiro suficiente."))).addItemFlag(ItemFlag.HIDE_ATTRIBUTES).toItemStack();
            ItemStack item6 = new ItemBuilder(Material.NETHER_STAR).setName("§eNível VI").setLore("§7Faça um upgrade da sua facção", "§7para receber várias vantagens.","§7listadas abaixo."
                    , "§fVantagens:", "", "§e ● §7Efeitos no claim, Pressa III;",
                    "§e ● §7Acrescenta +2 de poder em todos na facção;",
                    "§e ● §7Adiciona +10% a mais em toda venda de item na loja;",
                    "§e ● §7Adiciona +25% em drops de mobs;",
                    "§e ● §7Acrescenta +60% de xp em habilidades;",
                    "§e ● §7Acelera recuperação de poder em 1 minuto;",
                    "§e ● §7Chance de receber caixas em spawners.","","§fPreço: §71.000.000","",
                    (coin >= 1000000 && !user.isNivel6() ? "§cClique para comprar a evolução." : (user.isNivel6() ?  "§eVocê já possui essa evolução" : "§cVocê não possui dinheiro suficiente."))).addItemFlag(ItemFlag.HIDE_ATTRIBUTES).toItemStack();
            ItemStack item7 = new ItemBuilder(Material.MOB_SPAWNER).setName("§eNível VII").setLore("§7Faça um upgrade da sua facção", "§7para receber várias vantagens.","§7listadas abaixo."
                    , "§fVantagens:", "", "§e ● §7Aumenta +3 de poder em todos na facção;",
                    "§e ● §7Adiciona +0,6 a mais em toda venda de item na loja;",
                    "§e ● §7Adiciona +25% em drops de mobs;",
                    "§e ● §7Acrescenta +40% de xp em habilidades;",
                    "§e ● §7Efeitos no claim, Regeneração III;",
                    "§e ● §7Agora spawners funcionama mais rápido;",
                    "§e ● §7Spawners dropam 2x mais;",
                    "§e ● §7Chance de receber itens especiais em spawners.","","§fPreço: §71.250.000","",
                    (coin >= 1250000 && !user.isNivel7() ? "§cClique para comprar a evolução." : (user.isNivel7() ?  "§eVocê já possui essa evolução" : "§cVocê não possui dinheiro suficiente."))
            ).addItemFlag(ItemFlag.HIDE_ATTRIBUTES).toItemStack();
            if (Main.cache.containsKey(mPlayer.getFactionName())) {
                ItemStack item8 = new ItemBuilder(Banner.getRedBanner(mPlayer.getFactionTag())).setLore("§7Status geral dos níveis:", "",
                        (user.isNivel1() ? "§fNível I: §aConquistado." : "§fNível I: §cNão Conquistado."),
                        (user.isNivel2() ? "§fNível II: §aConquistado." : "§fNível II: §cNão Conquistado."),
                        (user.isNivel3() ? "§fNível III: §aConquistado." : "§fNível III: §cNão Conquistado."),
                        (user.isNivel4() ? "§fNível IV: §aConquistado." : "§fNível IV: §cNão Conquistado."),
                        (user.isNivel5() ? "§fNível V: §aConquistado." : "§fNível V: §cNão Conquistado."),
                        (user.isNivel6() ? "§fNível VI: §aConquistado." : "§fNível VI: §cNão Conquistado."),
                        (user.isNivel7() ? "§fNível VII: §aConquistado." : "§fNível VII: §cNão Conquistado.")
                ).setName("§eStatus sua facção").addItemFlag(ItemFlag.HIDE_ATTRIBUTES).toItemStack();
                inventory.setItem(29, item8);
            }
            inventory.setItem(10,item1);
            inventory.setItem(11,item2);
            inventory.setItem(12,item3);
            inventory.setItem(13,item4);
            inventory.setItem(14,item5);
            inventory.setItem(15,item6);
            inventory.setItem(16,item7);
        }
        p.openInventory(inventory);
    }
}
