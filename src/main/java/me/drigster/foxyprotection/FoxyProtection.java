package me.drigster.foxyprotection;

import me.drigster.foxyprotection.blocks.PowerCore;
import me.drigster.foxyprotection.commands.RemoveCoreCommand;
import me.drigster.foxyprotection.commands.SpawnCoreCommand;
import me.drigster.foxyprotection.commands.TextInputCommand;
import me.drigster.foxyprotection.events.CoreEvents;
import me.drigster.foxyprotection.events.PrivateEvents;
import me.drigster.foxyprotection.files.Data;
import me.drigster.foxyprotection.files.Localization;
import me.drigster.foxyprotection.guis.TextInputGui;
import me.drigster.foxyprotection.managers.BlockManager;
import me.drigster.foxyprotection.managers.ItemManager;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class FoxyProtection extends JavaPlugin {

    @Override
    public void onEnable() {
        PluginManager pm = getServer().getPluginManager();

        getConfig().options().copyDefaults(true);
        saveDefaultConfig();
        Data.init();
        Localization.init();

        getCommand("spawncore").setExecutor(new SpawnCoreCommand());
        getCommand("removecore").setExecutor(new RemoveCoreCommand());
        getCommand("textinput").setExecutor(new TextInputCommand());

        pm.registerEvents(new PrivateEvents(), this);
        pm.registerEvents(new CoreEvents(), this);
        pm.registerEvents(new TextInputGui(), this);

        BlockManager.init();
        ItemManager.init();
    }
}
