package fr.froz;

import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.MaxChangedBlocksException;
import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.bukkit.BukkitWorld;
import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import com.sk89q.worldedit.schematic.MCEditSchematicFormat;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;


public class Arena implements Listener {
    private Location loc, spawnPointRed, spawnPointBlue;
    private Player red, blue;
    private ArrayList playersList = new ArrayList();

    public Arena(Location l) {
        loc = l;
        spawnPointBlue = new Location(Bukkit.getWorld("world"), l.getX() - 54.5, l.getY() + 12.3, l.getZ() + 6.5);
        spawnPointBlue.setYaw(-90);
        spawnPointRed = new Location(Bukkit.getWorld("world"), l.getX() - 0.5, l.getY() + 12.3, l.getZ() + 6.5);
        spawnPointRed.setYaw(90);
        pasteSchem(l);


    }

    private void pasteSchem(Location loc) {
        WorldEditPlugin we = (WorldEditPlugin) Bukkit.getPluginManager().getPlugin("WorldEdit");
        File f = new File("plugins/IkaBrain/arena/arena.schematic");
        EditSession session = we.getWorldEdit().getEditSessionFactory().getEditSession(new BukkitWorld(loc.getWorld()), 1000000);
        try {
            MCEditSchematicFormat.getFormat(f).load(f).paste(session, new Vector(loc.getX(), loc.getY(), loc.getZ()), false);

        } catch (MaxChangedBlocksException
                | com.sk89q.worldedit.data.DataException | IOException e2) {
            e2.printStackTrace();
        }
    }

    public boolean isPlayer(Player p) {
        if ((red == p) || (blue == p)) {
            return true;
        } else {
            return false;
        }
    }

    public Location getLocation() {
        return loc;
    }

    public Location getRedSpawn() {
        return spawnPointRed;
    }

    public Location getBlueSpawn() {
        return spawnPointBlue;
    }

    public ArrayList getPlayers() {
        return playersList;
    }

    public void start() {
        Bukkit.getPluginManager().registerEvents(this, Bukkit.getPluginManager().getPlugin("wallsBattle"));

        red.setGameMode(GameMode.SURVIVAL);
        blue.setGameMode(GameMode.SURVIVAL);
        red.teleport(getRedSpawn());
        blue.teleport(getBlueSpawn());
    }

    public boolean addPlayer(Player p) {
        if (playersList.size() < 2) {
            playersList.add(p);
            if (red == null) {
                red = p;
                if (isPlayer(p)) {
                    p.sendMessage("true");
                }
                return true;
            } else {
                blue = p;
                if (isPlayer(p)) {
                    p.sendMessage("true");
                }
                return true;
            }
        } else {
            return false;
        }
    }

}
