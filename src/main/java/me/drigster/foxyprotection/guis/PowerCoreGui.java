package me.drigster.foxyprotection.guis;

import me.drigster.foxycore.items.GuiElements;
import me.drigster.foxyprotection.blocks.PowerCore;
import me.drigster.foxyprotection.files.Data;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PowerCoreGui implements Listener {

    private static Inventory gui;

    public static void openNewGui(Player p, String id){
        generateGui(id);

        p.openInventory(gui);
    }

    @EventHandler
    public void guiClickEvent(InventoryClickEvent e){
        if(e.getClickedInventory() == null){
            return;
        }
        if(!e.getClickedInventory().equals(gui)) {
            return;
        }

        e.setCancelled(true);

        String id = gui.getMaxStackSize() + "";

        switch (e.getSlot()){
            case 5:
                PowerCore.upgrade(id, (Player)e.getWhoClicked());
        }
    }

    public static void generateGui(String id){
        ConfigurationSection core = Data.get().getConfigurationSection("cores." + id);

        String name = core.getString("name");
        Player owner = Bukkit.getPlayer(UUID.fromString(core.getString("owner")));
        List<String> users = core.getStringList("users");

        gui = Bukkit.createInventory(null, 18, ChatColor.DARK_AQUA + name);
        gui.setMaxStackSize(Integer.parseInt(id));

        ItemStack blank = GuiElements.blank;

        ItemStack info = GuiElements.info;
        ItemMeta infoMeta = info.getItemMeta();
        infoMeta.setDisplayName("Информация");
        List<String> paperLore = new ArrayList<>();
        paperLore.add("Уровень: " + core.getInt("tier"));
        paperLore.add("Владелец: " + owner.getName());
        paperLore.add("Пользователи: ");
        for (String userId : users) {
            Player user = Bukkit.getPlayer(UUID.fromString(userId));
            paperLore.add("  -" + user.getName());
        }
        infoMeta.setLore(paperLore);
        info.setItemMeta(infoMeta);

        ItemStack upgrade = GuiElements.upgrade.clone();
        ItemMeta upgradeMeta = upgrade.getItemMeta();
        List<String> upgradeLore = upgradeMeta.getLore();
        int tier = core.getInt("tier");
        switch (tier) {
            case 1 -> {
                upgradeLore.set(2, " " + ChatColor.DARK_AQUA + upgradeLore.get(2));
                upgradeLore.add("Цена улучшения: " + tier * 2);
            }
            case 2 -> {
                upgradeLore.set(3, " " + ChatColor.DARK_AQUA + upgradeLore.get(3));
                upgradeLore.add("Цена улучшения: " + tier * 2);
            }
            case 3 -> {
                upgradeLore.set(4, " " + ChatColor.DARK_AQUA + upgradeLore.get(4));
                upgradeLore.add("Цена улучшения: " + tier * 2);
            }
            case 4 -> {
                upgradeLore.set(5, " " + ChatColor.DARK_AQUA + upgradeLore.get(5));
                upgradeLore.add("Цена улучшения: " + tier * 2);
            }
            case 5 -> {
                upgradeLore.set(6, " " + ChatColor.DARK_AQUA + upgradeLore.get(6));
                upgradeLore.add("Цена улучшения: " + tier * 2);
            }
        }
        upgradeMeta.setLore(upgradeLore);
        upgrade.setItemMeta(upgradeMeta);

        ItemStack[] menuItems = {blank, blank, blank, info, blank, upgrade, blank, blank, blank,
                                 blank, blank, blank, blank, blank, blank, blank, blank, blank};

        gui.setContents(menuItems);
    }

}
