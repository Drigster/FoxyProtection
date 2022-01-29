package me.drigster.foxyprotection.blocks;

import me.drigster.foxyprotection.FoxyProtection;
import me.drigster.foxyprotection.files.Data;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.*;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class PowerCore implements Listener {

    static Plugin plugin = FoxyProtection.getPlugin(FoxyProtection.class);

    public static void spawn(Location location){

        Block base = location.getBlock();
        base.setType(Material.END_PORTAL_FRAME);

        Block block = base.getLocation().add(0, 1, 0).getBlock();
        block.setType(Material.LIGHT);

        EnderCrystal core = (EnderCrystal) location.getWorld().spawnEntity(base.getLocation().add(0.5, 1,0.5), EntityType.ENDER_CRYSTAL);
        core.setShowingBottom(false);
        core.setFallDistance(0.0f);
        core.setCustomName("Power Core");
        core.setCustomNameVisible(true);
        core.setGravity(false);
        core.setInvulnerable(true);
        core.setPersistent(true);
        core.setVisualFire(false);
        core.setBeamTarget(core.getLocation().subtract(0, 3,0));

        int coreId = 0;

        if(Data.get().contains("nextId")){
            coreId = Data.get().getInt("nextId");
            Data.get().set("nextId", coreId + 1);
        }
        else {
            Data.get().set("nextId", 1);
        }

        base.setMetadata("coreId", new FixedMetadataValue(plugin, coreId));

        Data.get().set("cores." + coreId + ".name", "Power Core");
        Data.get().set("cores." + coreId + ".world", location.getWorld().getName());
        Data.get().set("cores." + coreId + ".x", location.getBlockX());
        Data.get().set("cores." + coreId + ".y", location.getBlockY());
        Data.get().set("cores." + coreId + ".z", location.getBlockZ());
        Data.save();
    }

    public static void remove(Chunk chunk){
        ConfigurationSection sec = Data.get().getConfigurationSection("cores");

        if(sec == null){
            return;
        }

        for(String key : sec.getKeys(false)){
            String world = Data.get().getString("cores." + key + ".world");
            int x = Data.get().getInt("cores." + key + ".x");
            int y = Data.get().getInt("cores." + key + ".y");
            int z = Data.get().getInt("cores." + key + ".z");

            if(chunk != Bukkit.getWorld(world).getBlockAt(x, y, z).getChunk()){
                continue;
            }

            Block core = Bukkit.getServer().getWorld(world).getBlockAt(x, y, z);
            Block light = core.getLocation().add(0, 1, 0).getBlock();
            Collection<Entity> entities = chunk.getWorld().getNearbyEntities(light.getLocation(), 1, 2 , 1);
            if(entities.size() > 0){
                Entity entity = entities.iterator().next();
                if(entity.getType().equals(EntityType.ENDER_CRYSTAL)){
                    entity.remove();
                }
            }
            core.setType(Material.AIR);
            light.setType(Material.AIR);

            Data.get().set("cores." + key, null);
            break;
        }
    }

    public static void load(){
        ConfigurationSection sec = Data.get().getConfigurationSection("cores");

        if(sec == null){
            return;
        }

        for(String key : sec.getKeys(false)){
            if(key.equals("nextId")){
                return;
            }
            String world = Data.get().getString("cores." + key + ".world");
            int x = Data.get().getInt("cores." + key + ".x");
            int y = Data.get().getInt("cores." + key + ".y");
            int z = Data.get().getInt("cores." + key + ".z");

            Block core = Bukkit.getServer().getWorld(world).getBlockAt(x, y, z);
            core.setMetadata("coreId", new FixedMetadataValue(plugin, Integer.parseInt(key)));
        }
    }
}
