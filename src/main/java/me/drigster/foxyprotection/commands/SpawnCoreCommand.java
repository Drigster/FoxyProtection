package me.drigster.foxyprotection.commands;

import me.drigster.foxyprotection.blocks.PowerCore;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SpawnCoreCommand implements CommandExecutor {


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player p){
            PowerCore.spawn(p.getLocation());
        }

        return true;
    }
}
