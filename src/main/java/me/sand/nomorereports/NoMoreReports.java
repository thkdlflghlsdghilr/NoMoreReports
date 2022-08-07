package me.sand.nomorereports;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;

public final class NoMoreReports extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        // Plugin startup logic
        Bukkit.getServer().getPluginManager().registerEvents(this, this);
        Bukkit.getLogger().info("NoMoreReports by Sand and Barrulik");

    }
    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {
        e.setCancelled(true);  // this makes microsoft sad :P
        getServer().broadcastMessage("<" + e.getPlayer().getName() + "> " + e.getMessage());
    }

    @EventHandler
    public void CommandModifier(PlayerCommandPreprocessEvent event) {
        Player player = event.getPlayer();
        String[] args = event.getMessage().split(" ");
        System.out.println(Arrays.toString(args));
        if (args.length < 2) return;
        switch (args[0]) {
            case "/msg":
            case "/tell":
            case "/w":
                event.setCancelled(true); // this makes microsoft sad :P
                switch (args[1]) {
                    case "@p":
                    case "@s":
                        player.sendMessage(ChatColor.GRAY + ChatColor.ITALIC.toString() + player.getName() + " whispers to you: " + String.join(" ", Arrays.asList(args).subList(2, args.length)));
                        player.sendMessage(ChatColor.GRAY + ChatColor.ITALIC.toString() + "You whisper to " + player.getName() + ": " + String.join(" ", Arrays.asList(args).subList(2, args.length)));
                        break;
                    case "@a":
                        for (Player p : Bukkit.getServer().getOnlinePlayers()) {
                            p.sendMessage(ChatColor.GRAY + ChatColor.ITALIC.toString() + player.getName() + " whispers to you: " + String.join(" ", Arrays.asList(args).subList(2, args.length)));
                            player.sendMessage(ChatColor.GRAY + ChatColor.ITALIC.toString() + "You whisper to " + p.getName() + ": " + String.join(" ", Arrays.asList(args).subList(2, args.length)));
                        }
                        break;
                    case "@r":
                        Player random = Bukkit.getServer().getOnlinePlayers().toArray(new Player[0])[(int) (Math.random() * Bukkit.getServer().getOnlinePlayers().size())];
                        random.sendMessage(ChatColor.GRAY + ChatColor.ITALIC.toString() + player.getName() + " whispers to you: " + String.join(" ", Arrays.asList(args).subList(2, args.length)));
                        player.sendMessage(ChatColor.GRAY + ChatColor.ITALIC.toString() + "You whisper to " + random.getName() + ": " + String.join(" ", Arrays.asList(args).subList(2, args.length)));
                        break;
                    case "@e":
                        for (Entity e : player.getWorld().getEntities()) {
                            if (e instanceof Player) {
                                ((Player) e).sendMessage(ChatColor.GRAY + ChatColor.ITALIC.toString() + player.getName() + " whispers to you: " + String.join(" ", Arrays.asList(args).subList(2, args.length)));
                                player.sendMessage(ChatColor.GRAY + ChatColor.ITALIC.toString() + "You whisper to " + e.getName() + ": " + String.join(" ", Arrays.asList(args).subList(2, args.length)));
                            }
                        }
                        break;
                    default:
                        Player p = getServer().getPlayer(args[1]);
                        if (p != null) {
                            p.sendMessage(ChatColor.GRAY + ChatColor.ITALIC.toString() + player.getName() + " whispers to you: " + String.join(" ", Arrays.asList(args).subList(2, args.length)));
                            player.sendMessage(ChatColor.GRAY + ChatColor.ITALIC.toString() + "You whisper to " + p.getName() + ": " + String.join(" ", Arrays.asList(args).subList(2, args.length)));
                        } else {
                            player.sendMessage(ChatColor.RED + "No player was found");
                        }
                        break;
                }
                break;
            case "/say":
                event.setCancelled(true); // this makes microsoft sad :P
                getServer().broadcastMessage("[" + player.getName() + "] " + String.join(" ", Arrays.asList(args).subList(1, args.length)));
                break;
            case "/me":
                event.setCancelled(true); // this makes microsoft sad :P
                getServer().broadcastMessage("* " + player.getName() + " " + String.join(" ", Arrays.asList(args).subList(1, args.length)));
                break;
            default:
                return;
        }
    }
}
