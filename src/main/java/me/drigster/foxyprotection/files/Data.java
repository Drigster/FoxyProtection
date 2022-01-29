package me.drigster.foxyprotection.files;

import me.drigster.foxyprotection.FoxyProtection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;

public class Data {

    static Plugin plugin = FoxyProtection.getPlugin(FoxyProtection.class);
    private static File file;
    private static FileConfiguration dataFile;

    public static void init(){
        setup();
        save();
    }

    public static void setup(){
        file = new File(plugin.getDataFolder(), "Data.yml");

        if(!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e){
                //
            }
        }
        dataFile = YamlConfiguration.loadConfiguration(file);
        addDefaults();
    }

    public static FileConfiguration get(){
        return dataFile;
    }

    public static void save(){
        try {
            dataFile.save(file);
        } catch (IOException e){
            System.out.println("File save error\n" + e.getMessage());
        }
    }

    public static void reload(){
        dataFile = YamlConfiguration.loadConfiguration(file);
    }

    public static void addDefaults(){
        dataFile.options().copyDefaults(true);
    }
}
