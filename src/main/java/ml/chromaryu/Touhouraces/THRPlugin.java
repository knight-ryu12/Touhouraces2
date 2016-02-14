package ml.chromaryu.Touhouraces;

/**
 * Created by chroma on 16/02/11.
 */

import java.io.File;
import java.util.logging.Logger;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import ml.chromaryu.Touhouraces.races.THRaces;
import ml.chromaryu.Touhouraces.races.command.THCommand;
import ml.chromaryu.Touhouraces.races.schedule.THSchedule;

/*import com.shampaggon.crackshot.CSDirector; */
/*import com.shampaggon.crackshot.events.WeaponDamageEntityEvent; */

public class THRPlugin extends JavaPlugin implements Listener
{
    public static Logger logger = Logger.getLogger("Minecraft");
    public static THRPlugin plugin;
    public static String touhouraces = ChatColor.WHITE + "[" + ChatColor.RED + "THR" + ChatColor.WHITE + "] " + ChatColor.RESET;
    public static String thrpre = "[§cTHR§f] ";
    public static PluginDescriptionFile pdfFile = plugin.getDescription();
    private static File pluginDir = new File("plugins", "THRPlugin");
    public static File configfile = new File(pluginDir, "config.yml");
    public static FileConfiguration conf = YamlConfiguration.loadConfiguration(configfile);

    public void onDisable(){
        logger.info("[THR] Plugin Successfully Disabled!");
        saveConfig();
    }

    public void onEnable()
    {
        logger.info("[THR]" + pdfFile.getVersion() + " Has Successfully Been Enabled!");

        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(this, this);
        saveDefaultConfig();
        registerTHRaces();
        registerTHCommand();
        THRPlugin plugin0 = this;
        THSchedule schedule = new THSchedule();
        String thpre0 = THRPlugin.thrpre;
        schedule.run1(plugin0,thpre0);
        schedule.run2(plugin0,thpre0);
        schedule.run3(plugin0,thpre0);
    }

    public void registerTHRaces()
    {
        getServer().getPluginManager().registerEvents(new THRaces(), plugin);
    }
    public void registerTHCommand()
    {
        getCommand("touhouraces").setExecutor(new THCommand());
        getCommand("thr").setExecutor(new THCommand());
    }

}

