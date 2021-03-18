package com.pagani.auth;

import com.warrenstrange.googleauth.GoogleAuthenticator;
import com.warrenstrange.googleauth.GoogleAuthenticatorKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.*;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;

public class Main extends JavaPlugin implements Listener {

    public static Storage storage;

    public static ArrayList<String> auths = new ArrayList<String>();

    @Override
    public void onEnable(){
        saveDefaultConfig();
        storage = new Storage(this);
        getServer().getPluginManager().registerEvents(this,this);
        this.saveConfig();
    }

    @EventHandler
    public void onJoin(final PlayerJoinEvent e){
        if (e.getPlayer().isOp()){
            if (storage.hasRegister(e.getPlayer().getName())){
                auths.add(e.getPlayer().getName());
                e.getPlayer().sendMessage("§aSeja bem vindo, digite o código de segurança do aplicativo para continuar no servidor");
            } else {
                GoogleAuthenticator googleAuthenticator = new GoogleAuthenticator();
                GoogleAuthenticatorKey googleAuthenticatorKey =googleAuthenticator.createCredentials();
                Player p = e.getPlayer();
                p.sendMessage("§aCrie sua conta no Google Authenticator ou Microsoft Authenticator para se logar como staff.");
                p.sendMessage("§e§lOBS:§e N\u00e3o desinstale este aplicativo de seu celular pois pode perder as credenciais j\u00e1 existentes dentro do servidor.");
                p.sendMessage("§e§lDUVIDAS: §eEntre em contato com um membro da equipe.");
                p.sendMessage("§e§lOBS:§e Verifique se o hor\u00e1rio de seu celular est\u00e1 correto com o hor\u00e1rio de Bras\u00edlia.");
                p.sendMessage("§aSua Secret ID: §e" + googleAuthenticatorKey.getKey());
                p.sendMessage("§aDigite o c\u00f3digo no chat para se autenticar.");
                storage.register(e.getPlayer().getName(),googleAuthenticatorKey.getKey());
                auths.add(e.getPlayer().getName());
                return;
            }
        }
    }

    @EventHandler
    public void onMove(PlayerMoveEvent e){
        if (auths.contains(e.getPlayer().getName())){
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onExecute(PlayerCommandPreprocessEvent e){
        if (auths.contains(e.getPlayer().getName())){
            e.setCancelled(true);
        }
    }

    public boolean autorize(String string,int code){
        GoogleAuthenticator googleAuthenticator = new GoogleAuthenticator();
        boolean right = googleAuthenticator.authorize(string,code);
        return right;
    }

    @EventHandler
    public void onAsync(AsyncPlayerChatEvent e){
        if (auths.contains(e.getPlayer().getName())){
            e.setCancelled(true);
            if (storage.hasRegister(e.getPlayer().getName())) {
                String code = storage.getIdentifier(e.getPlayer().getName());
                try {
                    Integer integer = Integer.valueOf(e.getMessage());
                    boolean fez = autorize(code,integer);
                    if (fez){
                        e.getPlayer().sendMessage("§aCódigo correto, acesso liberado.");
                        auths.remove(e.getPlayer().getName());
                        TitleAPI.sendTitle(e.getPlayer(),20,20,20,"§aAcesso permítido.","§7Seja bem vindo " + e.getPlayer().getName() + "!");
                    } else {
                        e.getPlayer().sendMessage("§cCódigo incorreto.");
                    }
                } catch (NumberFormatException ef) {
                    e.getPlayer().sendMessage("§cCódigo incorreto.");
                }
            } else {
                GoogleAuthenticator googleAuthenticator = new GoogleAuthenticator();
                GoogleAuthenticatorKey googleAuthenticatorKey =googleAuthenticator.createCredentials();
                e.getPlayer().sendMessage("§7Seu código de registro é: §b" + googleAuthenticatorKey.getKey());
                storage.register(e.getPlayer().getName(),googleAuthenticatorKey.getKey());
                return;
            }
        }
    }


    @EventHandler
    public void onBreak(PlayerTeleportEvent e){
        if (auths.contains(e.getPlayer().getName())){
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e){
        if (auths.contains(e.getPlayer().getName())){
            auths.remove(e.getPlayer().getName());
        }
    }

    @EventHandler
    public void onBreak(BlockBreakEvent e){
        if (auths.contains(e.getPlayer().getName())){
            e.setCancelled(true);
        }
    }


    @EventHandler
    public void onBreak(BlockPlaceEvent e){
        if (auths.contains(e.getPlayer().getName())){
            e.setCancelled(true);
        }
    }

}