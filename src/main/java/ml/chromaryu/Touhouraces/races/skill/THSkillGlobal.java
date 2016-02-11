package ml.chromaryu.Touhouraces.races.skill;

/**
 * Created by chroma on 16/02/12.
 */

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Bat;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;
import ml.chromaryu.Touhouraces.THRPlugin;

public class THSkillGlobal implements Listener {

    public static void global_chat(Player pl, final Plugin plugin, final AsyncPlayerChatEvent event) {
        String format = event.getFormat();
        if (THRPlugin.plugin.getConfig().contains("user." + pl.getUniqueId())) {
            boolean existrace = false;
            String inforace = "";
            for (String race : THRPlugin.plugin.getConfig().getConfigurationSection("race").getKeys(false)) {
                if (race.toLowerCase().contains(THRPlugin.plugin.getConfig().getString("user." + pl.getUniqueId() + ".race"))) {
                    existrace = true;
                    inforace = race;
                    break;
                }
            }
            if (existrace) {
                String race = THRPlugin.plugin.getConfig().getString("race." + inforace + ".display.tag");
                event.setFormat("§f[" + race + "§f]" + format);
            } else {
                String race = THRPlugin.plugin.getConfig().getString("user." + pl.getUniqueId() + ".race");
                event.setFormat("§f[" + race + "§f]" + format);
            }
        }
    }

    public static void global_join(Player pl, final Plugin plugin, final PlayerJoinEvent event) {
    /*メタ初期付与*/
        MetadataValue casted = new FixedMetadataValue(THRPlugin.plugin, false);
        pl.setMetadata("casting", casted);
        MetadataValue usingmagic = new FixedMetadataValue(THRPlugin.plugin, false);
        pl.setMetadata("using-magic", usingmagic);
        MetadataValue spilituse = new FixedMetadataValue(THRPlugin.plugin, 0);
        pl.setMetadata("spilituse", spilituse);
	/*新規登録*/
        if (THRPlugin.plugin.getConfig().contains("user." + pl.getUniqueId()) == false) {
            THRPlugin.plugin.getConfig().set("user." + pl.getUniqueId() + ".name", pl.getName());
            THRPlugin.plugin.getConfig().set("user." + pl.getUniqueId() + ".point", 0);
            THRPlugin.plugin.getConfig().set("user." + pl.getUniqueId() + ".race", "kedama");
            THRPlugin.plugin.getConfig().set("user." + pl.getUniqueId() + ".spilit", 0);
            THRPlugin.plugin.saveConfig();
        }

        THRPlugin.plugin.getConfig().set("user." + pl.getUniqueId() + ".spilit", 0);
        THRPlugin.plugin.saveConfig();
    }

    public static void global_quit(Player pl, final Plugin plugin, final PlayerQuitEvent event) {
        for (LivingEntity bat : pl.getWorld().getEntitiesByClass(Bat.class)) {
            if (bat.hasMetadata("invincible")) {
                if (pl.hasMetadata("batman")) {
                    if (((MetadataValue) pl.getMetadata("batman").get(0)).asString().toString().contains(((MetadataValue) bat.getMetadata("invincible").get(0)).asString().toString())) {
                        bat.removeMetadata("invincible", plugin);
                        bat.damage(1000.0D);
                    }
                }
            }
        }
        if (pl.hasMetadata("batman")) {
            pl.removeMetadata("batman", plugin);
        }
        if (pl.hasMetadata("casting")) {
            pl.removeMetadata("casting", plugin);
        }
        if (pl.hasMetadata("using-magic")) {
            pl.removeMetadata("using-magic", plugin);
        }
        if (pl.hasMetadata("satorin0")) {
            pl.removeMetadata("satorin0", plugin);
        }
        if (pl.getGameMode() == GameMode.SPECTATOR) {
            pl.setGameMode(GameMode.SURVIVAL);
        }
        if (pl.hasMetadata("freeze")) {
            pl.removeMetadata("freeze", plugin);
        }
    }

    //リスポーン体力調整グローバル
    public static void global_respawnhealth(Player pl, final Plugin plugin, final PlayerRespawnEvent event) {
        pl.setMaxHealth(100D);
    }

    //霊力調整グローバル
    public static void global_charge_mana(Player pl, final Plugin plugin, final PlayerInteractEvent event) {
        Material dust_is_ok = pl.getItemInHand().getType();
        if (pl.getMetadata("spilituse").get(0).asDouble() != 0) {
            MetadataValue spilituse = new FixedMetadataValue(THRPlugin.plugin, 0);
            pl.setMetadata("spilituse", spilituse);
            pl.sendMessage(THRPlugin.thrpre + ChatColor.WHITE + "霊力ノーマル");
        } else {
            if (dust_is_ok == Material.SUGAR) {
                MetadataValue spilituse = new FixedMetadataValue(THRPlugin.plugin, 5);
                pl.setMetadata("spilituse", spilituse);
                pl.sendMessage(THRPlugin.thrpre + ChatColor.AQUA + "霊力消費小");
            } else if (dust_is_ok == Material.SULPHUR) {
                MetadataValue spilituse = new FixedMetadataValue(THRPlugin.plugin, 15);
                pl.setMetadata("spilituse", spilituse);
                pl.sendMessage(THRPlugin.thrpre + ChatColor.DARK_GRAY + "霊力消費大");
            } else if (dust_is_ok == Material.GLOWSTONE_DUST) {
                MetadataValue spilituse = new FixedMetadataValue(THRPlugin.plugin, -10);
                pl.setMetadata("spilituse", spilituse);
                pl.sendMessage(THRPlugin.thrpre + ChatColor.YELLOW + "霊力回復中");
            }
        }
    }

    public static void global_no_ninngen(Player pl, final Plugin plugin, final PlayerInteractEntityEvent event) {
        pl.sendMessage(THRPlugin.thrpre + ChatColor.GRAY + "このニンゲンは何を話しているんだろう・・・");
        pl.closeInventory();
        event.setCancelled(true);
    }

    public static void global_no_mana_attack(Player pl, final Plugin plugin, final EntityDamageByEntityEvent event) {
        event.setDamage(event.getDamage() / 2D);
        if (pl.isSneaking()) {
            pl.sendMessage(THRPlugin.thrpre + ChatColor.RED + pl.getName() + "貴方は霊力再生モードの為本気を出せません！");
        }
    }

    public static void global_no_mana_damaged(Player pl, final Plugin plugin, final EntityDamageByEntityEvent event) {
        event.setDamage(event.getDamage() * 2D);
        if (pl.isSneaking()) {
            pl.sendMessage(THRPlugin.thrpre + ChatColor.RED + pl.getName() + "貴方は霊力再生モードの為非常に柔いです！");
        }
    }
}
