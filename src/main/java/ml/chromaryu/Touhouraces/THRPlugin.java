package ml.chromaryu.Touhouraces;

/**
 * Created by chroma on 16/02/11.
 */

import java.io.File;
import java.io.IOException;
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
        THSchedule schedule = new THSchedule() ;


        getServer().getScheduler().scheduleSyncRepeatingTask(THRPlugin.plugin,schedule.run1(), 0L, 22L);
        getServer().getScheduler().scheduleSyncRepeatingTask(THRPlugin.plugin,schedule.run2(), 0L, 33L);
        getServer().getScheduler().scheduleSyncRepeatingTask(THRPlugin.plugin,schedule.run3(), 0L, 44L);
    }

    public void registerTHRaces()
    {
        getServer().getPluginManager().registerEvents(new THRaces(), THRPlugin.plugin);
    }
    public void registerTHCommand()
    {
        getCommand("touhouraces").setExecutor(new THCommand());
        getCommand("thr").setExecutor(new THCommand());
    }


}

