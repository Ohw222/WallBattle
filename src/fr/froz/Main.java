package fr.froz;


import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.logging.Logger;


public class Main extends JavaPlugin implements Listener {
    public static PluginManager pm = Bukkit.getPluginManager();
    public Boolean[] freeArena = {true, true, true, true, true};
    ArrayList<Player> qPlayer = new ArrayList();
    Logger log = Bukkit.getLogger();

    public static void getP() {

    }

    public static void unRegisterE(Listener l) {
        HandlerList.unregisterAll(l);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("init")) {
            Arena a = new Arena(new Location(Bukkit.getWorld("world"), Integer.parseInt(args[0]), Integer.parseInt(args[1]), Integer.parseInt(args[2])));
            Player p = (Player) sender;
            a.addPlayer(p);

            p.teleport(a.getRedSpawn());

            return true;
        }
        return false;
    }

    @Override
    public void onEnable() {
        log.info("wallsBattle plugin by MrFroz");
        Bukkit.getPluginManager().registerEvents(this, this);


    }

    @Override
    public void onDisable() {
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        qPlayer.add(e.getPlayer());
        e.getPlayer().sendMessage(ChatColor.WHITE + "" + ChatColor.BOLD + "Bienvenue sur wallsBattle dev Froz");
        e.getPlayer().sendMessage(String.valueOf(qPlayer.size()));
        if (qPlayer.size() == 2) {
            Arena arena;
            int inte = 0;
            boolean run = true;
            while (freeArena.length > inte && run) {
                if (freeArena[inte]) {
                    freeArena[inte] = false;
                    Arena a = new Arena(new Location(Bukkit.getWorld("world"), 500 * inte, 0, 0));
                    a.addPlayer(qPlayer.get(0));
                    a.addPlayer(qPlayer.get(1));
                    qPlayer = new ArrayList<>();
                    run = false;
                    a.start();
                }
                inte = inte + 1;


            }

        }
    }
}
