package me.drigster.foxyprotection.managers;

import me.drigster.foxyprotection.items.GuiElements;
import me.drigster.foxyprotection.items.PowerCoreItem;

public class ItemManager {

    public static void init() {
        PowerCoreItem.init();
        GuiElements.init();
    }

}
