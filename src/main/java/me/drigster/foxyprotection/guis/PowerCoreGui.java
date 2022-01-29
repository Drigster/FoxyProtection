package me.drigster.foxyprotection.guis;

import me.drigster.foxyprotection.files.Data;
import me.drigster.foxyprotection.items.GuiElements;
import me.drigster.foxyprotection.managers.MessageManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Server;
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
        if(!e.getClickedInventory().equals(gui)){
            return;
        }

        e.setCancelled(true);
    }

    public static void generateGui(String id){
        ConfigurationSection core = Data.get().getConfigurationSection("cores." + id);

        String name = core.getString("name");
        Player owner = Bukkit.getPlayer(UUID.fromString(core.getString("owner")));
        List<String> users = core.getStringList("users");

        gui = Bukkit.createInventory(null, 18, ChatColor.DARK_AQUA + name);

        ItemStack blank = GuiElements.blank;

        ItemStack paper = new ItemStack(Material.PAPER);
        ItemMeta paperMeta = paper.getItemMeta();
        paperMeta.setDisplayName("Информация");
        List<String> paperLore = new ArrayList<>();
        paperLore.add("Владелец: " + owner.getName());
        paperLore.add("Пользователи: ");
        for (String userId : users) {
            Player user = Bukkit.getPlayer(UUID.fromString(userId));
            paperLore.add("  -" + user.getName());
        }
        paperMeta.setLore(paperLore);
        paper.setItemMeta(paperMeta);

        ItemStack[] menuItems = {blank, blank, blank, blank, paper, blank, blank, blank, blank,
                                 blank, blank, blank, blank, blank, blank, blank, blank, blank};
        gui.setContents(menuItems);
    }

}
