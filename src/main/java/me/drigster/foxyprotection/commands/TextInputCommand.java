package me.drigster.foxyprotection.commands;

import me.drigster.foxyprotection.guis.TextInputGui;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TextInputCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player p){
            TextInputGui.openNewGui(p);
        }

        return true;
    }
}
