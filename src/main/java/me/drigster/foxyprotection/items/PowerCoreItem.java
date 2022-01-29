package me.drigster.foxyprotection.items;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class PowerCoreItem {

    public static ItemStack core;

    public static void init(){
        createCore();
    }

    private static void createCore() {
        ItemStack item = new ItemStack(Material.LODESTONE);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("Ядро силы");
        List<String> lore = new ArrayList();
        lore.add("Позволяет приватить територию");
        meta.setLore(lore);

        item.setItemMeta(meta);

        core = item;
    }
}
