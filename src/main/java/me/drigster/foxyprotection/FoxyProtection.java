package me.drigster.foxyprotection;

import me.drigster.foxycore.FoxyCore;
import me.drigster.foxyprotection.commands.GiveCoreCommand;
import me.drigster.foxyprotection.commands.RemoveCoreCommand;
import me.drigster.foxyprotection.commands.SpawnCoreCommand;
import me.drigster.foxyprotection.events.CoreEvents;
import me.drigster.foxyprotection.events.PrivateEvents;
import me.drigster.foxyprotection.files.Data;
import me.drigster.foxyprotection.files.Localization;
import me.drigster.foxyprotection.guis.PowerCoreGui;
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

        FoxyCore.setPlugin(this);
        FoxyCore.setLocalization(Localization.get());

        getCommand("spawncore").setExecutor(new SpawnCoreCommand());
        getCommand("removecore").setExecutor(new RemoveCoreCommand());
        getCommand("givecore").setExecutor(new GiveCoreCommand());

        pm.registerEvents(new PrivateEvents(), this);
        pm.registerEvents(new CoreEvents(), this);
        pm.registerEvents(new PowerCoreGui(), this);

        BlockManager.init();
        ItemManager.init();
    }

    @Override
    public void onDisable() {
        Data.save();
    }
}
