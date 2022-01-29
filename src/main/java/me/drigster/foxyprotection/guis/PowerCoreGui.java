package me.drigster.foxyprotection.guis;

import me.drigster.foxyprotection.items.GuiElements;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class PowerCoreGui implements Listener {

    private static Inventory gui;

    public static void openNewGui(Player p){

        generateGui();

        p.openInventory(gui);

    }

    @EventHandler
    public void guiClickEvent(InventoryClickEvent e){

        if(e.getClickedInventory() == null){
            return;
        }
        if(!e.getClickedInventory().equals(gui)){
            return;
        }

        e.setCancelled(true);

        switch (e.getSlot()){
        }
    }

    public static void generateGui(){

        gui = Bukkit.createInventory(null, InventoryType.HOPPER, ChatColor.DARK_AQUA + "Converter");

        ItemStack blank = GuiElements.blank;
        ItemStack withdrawIcon = GuiElements.withdraw;
        ItemStack convertIcon = GuiElements.convert;

        ItemStack[] menuItems = {blank, withdrawIcon, blank, convertIcon, blank};
        gui.setContents(menuItems);

    }

}
