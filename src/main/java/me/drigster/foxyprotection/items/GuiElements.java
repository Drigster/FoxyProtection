package me.drigster.foxyprotection.items;

import me.drigster.foxyprotection.files.Localization;
import me.drigster.foxyprotection.managers.MessageManager;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class GuiElements {
    public static ItemStack blank;
    public static ItemStack back;
    public static ItemStack rightArrow;
    public static ItemStack leftArrow;

    public static void init(){
        createRightArrow();
        createLeftArrow();
        createBlank();
        createBack();
    }

    private static void createBlank() {
        ItemStack item = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(MessageManager.getLocalizedString("words.background"));

        item.setItemMeta(meta);

        blank = item;
    }

    private static void createBack() {
        ItemStack item = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(MessageManager.getLocalizedString("words.back"));

        meta.setCustomModelData(4);
        item.setItemMeta(meta);

        back = item;
    }

    private static void createRightArrow() {
        ItemStack item = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(MessageManager.getLocalizedString("words.convert"));

        meta.setCustomModelData(1);
        item.setItemMeta(meta);

        rightArrow = item;
    }

    private static void createLeftArrow() {
        ItemStack item = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(MessageManager.getLocalizedString("words.convert"));

        meta.setCustomModelData(2);
        item.setItemMeta(meta);

        leftArrow = item;
    }

}