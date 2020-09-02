import fr.fireblaim.fireupdaterlib.FireUpdaterLib;
import fr.fireblaim.fireupdaterlib.gui_updater.GuiUpdater;
import fr.fireblaim.fireupdaterlib.utils.MinecraftDirGenerator;

public class TestUpdater {

    public static void main(String[] args) {
        FireUpdaterLib.startUpdate("http://mysuperwebsite.com/", MinecraftDirGenerator.createDir("testupdater"));
        
    }

}
