package me.drigster.foxyprotection.events;

import me.drigster.foxyprotection.FoxyProtection;
import me.drigster.foxyprotection.blocks.PowerCore;
import me.drigster.foxyprotection.files.Data;
import me.drigster.foxyprotection.guis.PowerCoreGui;
import me.drigster.foxyprotection.items.PowerCoreItem;
import net.wesjd.anvilgui.AnvilGUI;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.*;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;

public class CoreEvents implements Listener {

    static Plugin plugin = FoxyProtection.getPlugin(FoxyProtection.class);

    private static boolean checkChunk(Chunk chunk){
        ConfigurationSection sec = Data.get().getConfigurationSection("cores");

        boolean check = false;

        if(sec != null) {
            for (String key : sec.getKeys(false)) {
                String world = Data.get().getString("cores." + key + ".world");
                int x = Data.get().getInt("cores." + key + ".x");
                int y = Data.get().getInt("cores." + key + ".y");
                int z = Data.get().getInt("cores." + key + ".z");
                int tier = Data.get().getInt("cores." + key + ".tier");

                Block core = Bukkit.getServer().getWorld(world).getBlockAt(x, y, z);
                Chunk coreChunk = core.getChunk();

                int size = tier * 2;

                if (chunk.getX() > coreChunk.getX() + size) {
                    check = false;
                    continue;
                }
                if (chunk.getX() < coreChunk.getX() - size) {
                    check = false;
                    continue;
                }
                if (chunk.getZ() > coreChunk.getZ() + size) {
                    check = false;
                    continue;
                }
                if (chunk.getZ() < coreChunk.getZ() - size) {
                    check = false;
                    continue;
                }

                check = true;
                break;
            }
        }

        return check;
    }

    @EventHandler
    private void onBlockInteract(PlayerInteractEvent e){
        if(e.getClickedBlock() == null){
            return;
        }
        if (checkChunk(e.getClickedBlock().getChunk())){
            if(e.getAction().equals(Action.RIGHT_CLICK_BLOCK)){
                if(e.getHand().equals(EquipmentSlot.HAND)){
                    switch (e.getClickedBlock().getType()){
                        case CHEST:
                        case HOPPER:
                        case POLISHED_BLACKSTONE_BUTTON:
                        case STONE_BUTTON:
                        case FURNACE:
                        case BARREL:
                        case SHULKER_BOX:
                        case DROPPER:
                        case DISPENSER:
                        case SMOKER:
                        case BLAST_FURNACE:
                        case BEACON:
                        case LEVER:
                            e.setCancelled(true);
                            break;
                        case END_PORTAL_FRAME:
                            List<MetadataValue> metaData = e.getClickedBlock().getMetadata("coreId");

                            if(!metaData.isEmpty()){
                                String coreId = metaData.get(0).asString();
                                ConfigurationSection core = Data.get().getConfigurationSection("cores." + coreId);

                                if(core != null){
                                    Player player = e.getPlayer();
                                    String name = core.getString("name");
                                    PowerCoreGui.openNewGui(player, coreId);
                                }
                            }
                        default:
                            break;
                    }
                }
            }
            else if(e.getAction().equals(Action.PHYSICAL)){
                switch (e.getClickedBlock().getType()){
                    case ACACIA_PRESSURE_PLATE:
                    case BIRCH_PRESSURE_PLATE:
                    case CRIMSON_PRESSURE_PLATE:
                    case DARK_OAK_PRESSURE_PLATE:
                    case JUNGLE_PRESSURE_PLATE:
                    case OAK_PRESSURE_PLATE:
                    case SPRUCE_PRESSURE_PLATE:
                    case WARPED_PRESSURE_PLATE:
                    case TRIPWIRE:
                        break;
                    default:
                        e.setCancelled(true);
                        break;
                }
            }
        }
        else {
            if(e.getItem() == null) {
                return;
            }
            if(e.getAction().equals(Action.RIGHT_CLICK_BLOCK)){
                if(e.getHand().equals(EquipmentSlot.HAND)){
                    if(e.getItem().equals(PowerCoreItem.core)){
                        e.setCancelled(true);
                        Player p = e.getPlayer();
                        Location location = e.getClickedBlock().getLocation().add(0, 1, 0);
                        new AnvilGUI.Builder()
                                .onComplete((player, text) -> {
                                    PowerCore.spawn(location, text, p);
                                    return AnvilGUI.Response.close();
                                })
                                .text("Територия")
                                .itemLeft(new ItemStack(Material.IRON_SWORD))
                                .title("Введите название:")
                                .plugin(plugin)
                                .open(p);
                    }
                }
            }
        }
    }

    @EventHandler
    private void onEntityInteract(PlayerInteractEntityEvent e){
        if (checkChunk(e.getRightClicked().getLocation().getChunk())){
            if(e.getHand().equals(EquipmentSlot.HAND)){
                switch (e.getRightClicked().getType()){
                    case MINECART_HOPPER:
                    case MINECART_CHEST:
                    case HORSE:
                    case SKELETON_HORSE:
                    case ZOMBIE_HORSE:
                    case DONKEY:
                    case LLAMA:
                    case TRADER_LLAMA:
                    case VILLAGER:
                        e.setCancelled(true);
                        break;
                    default:
                        break;
                }
            }
        }
    }

    @EventHandler
    private void onBlockBreak(BlockBreakEvent e){
        Block block = e.getBlock();
        Chunk chunk = block.getChunk();
        if (checkChunk(chunk)){
            e.setCancelled(true);

            Player player = e.getPlayer();
            player.sendMessage("Вы не можете ломать тут блоки");
        }
    }

    @EventHandler
    private void onBlockBurn(BlockBurnEvent e){
        Block block = e.getBlock();
        Chunk chunk = block.getChunk();
        if (checkChunk(chunk)){
            e.setCancelled(true);
        }
    }

    @EventHandler
    private void onBlockExplode(BlockExplodeEvent e){
        List<Block> blocks = e.blockList();
        List<Block> remove = new ArrayList<>();
        for (Block block : blocks) {
            Chunk chunk = block.getChunk();
            if (checkChunk(chunk)) {
                remove.add(block);
            }
        }
        blocks.removeAll(remove);
    }

    @EventHandler
    private void onBlockIgnite(BlockIgniteEvent e){
        Block block = e.getBlock();
        Chunk chunk = block.getChunk();
        if (checkChunk(chunk)){
            e.setCancelled(true);

            Player player = e.getPlayer();
            player.sendMessage("Вы не можете поджигать тут блоки");
        }
    }

    @EventHandler
    private void onBlockPlace(BlockPlaceEvent e){
        Block block = e.getBlock();
        Chunk chunk = block.getChunk();
        if (checkChunk(chunk)){
            e.setCancelled(true);

            Player player = e.getPlayer();
            player.sendMessage("Вы не можете ставть тут блоки");
        }
    }

    @EventHandler
    private void onEntityExplode(EntityExplodeEvent e){
        List<Block> blocks = e.blockList();
        List<Block> remove = new ArrayList<>();
        for (Block block : blocks) {
            Chunk chunk = block.getChunk();
            if (checkChunk(chunk)) {
                remove.add(block);
            }
        }
        blocks.removeAll(remove);
    }

    @EventHandler
    private void onDamage(EntityDamageEvent e){
        Location location = e.getEntity().getLocation();
        Chunk chunk = location.getChunk();
        if (checkChunk(chunk)){
            if(e.getEntity() instanceof Monster){
                return;
            }
            if(e.getEntity().getType().equals(EntityType.GHAST)){
                return;
            }
            if(e.getEntity().getType().equals(EntityType.SLIME)){
                return;
            }
            e.setCancelled(true);
        }
    }
}
