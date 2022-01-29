package me.drigster.foxyprotection.guis;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class TextInputGui implements Listener {

    private static Inventory gui;

    public static void openNewGui(Player p){

        generateGui();

        p.openInventory(gui);

    }

    @EventHandler
    public void guiClickEvent(InventoryClickEvent e){

        ItemStack item = e.getCurrentItem();

        if(e.getClickedInventory() == null){
            return;
        }
        if(!e.getClickedInventory().equals(gui)){
            return;
        }

        if(e.getSlot() == 2){
            if(item != null){
                Bukkit.broadcastMessage(e.getSlot() + "");
                ItemMeta itemMeta = item.getItemMeta();
                String input = itemMeta.getDisplayName();
                Player player = (Player) e.getWhoClicked();
                player.sendMessage(input);
            }
        }

        e.setCancelled(true);
    }

    public static void generateGui(){

        gui = Bukkit.createInventory(null, InventoryType.ANVIL, ChatColor.DARK_AQUA + "TextInput");

        ItemStack paper = new ItemStack(Material.PAPER);
        ItemMeta paperMeta = paper.getItemMeta();
        paperMeta.setDisplayName("Введите текст");
        paper.setItemMeta(paperMeta);

        ItemStack[] menuItems = {paper};
        gui.setContents(menuItems);

    }
}
