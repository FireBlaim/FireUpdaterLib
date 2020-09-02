package fr.fireblaim.fireupdaterlib.utils;

import java.io.File;

public class MinecraftDirGenerator {

    public static File createDir(String name) {
        String propertyOS = System.getProperty("os.name").toLowerCase();

        if (propertyOS.contains("win"))
            return new File(System.getProperty("user.home") + "\\AppData\\Roaming\\." + name);
        else if (propertyOS.contains("mac"))
            return new File(System.getProperty("user.home") + "/Library/Application Support/" + name);
        else
            return new File(System.getProperty("user.home") + "/." + name);
    }

}
