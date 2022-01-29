package me.drigster.foxyprotection.managers;

import me.drigster.foxyprotection.FoxyProtection;
import me.drigster.foxyprotection.files.Localization;
import org.bukkit.configuration.Configuration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.List;

public class MessageManager {

    static Plugin plugin = FoxyProtection.getPlugin(FoxyProtection.class);
    static Configuration config = plugin.getConfig();

    public static void sendMessage(Player p, String code){
        String message = getLocalizedString(code);
        message = getLocalizedString("prefix") + message;

        p.sendMessage(message);
    }

    public static String getLocalizedString(String code){
        String localizedString = Localization.get().getString(code);
        if(localizedString == null){
            localizedString = Localization.get().getDefaults().getString(code);
            if(localizedString == null){
                localizedString = code;
                return localizedString;
            }
        }

        localizedString = localizedString.replace("&", "§");

        return localizedString;
    }

    public static String getLocalizedStringWithPlaceholder(String code, String placeholder, String filler){
        String string = getLocalizedString(code);
        string = string.replaceAll(placeholder, filler);
        return string;
    }

    public static String getLocalizedStringWithPlaceholder(String code, List<String> placeholders, List<String> fillers){
        String string = getLocalizedString(code);
        for (int i=0; i<placeholders.size(); i++)
        {
            String placeholder = placeholders.get(i);
            String filler = fillers.get(i);
            string = string.replaceAll(placeholder, filler);
        }
        return string;
    }

}
