package ml.chromaryu.Touhouraces.races;

/**
 * Created by chroma on 16/02/12.
 */

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByBlockEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.plugin.Plugin;
import ml.chromaryu.Touhouraces.THRPlugin;
import ml.chromaryu.Touhouraces.races.skill.THSkillGlobal;
import ml.chromaryu.Touhouraces.races.skill.THSkillNNG;
import ml.chromaryu.Touhouraces.races.skill.THSkillYUZ;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.metadata.FixedMetadataValue;

public class THRaces implements Listener {
    ///アクション
    public void chat(final AsyncPlayerChatEvent event) {
        //グローバル
        Player pl = event.getPlayer();
        Plugin plugin = THRPlugin.plugin;
        THSkillGlobal.global_chat(pl, plugin, event);
    }

    public void quit(final PlayerQuitEvent event) {
        //グローバル
        Player pl = event.getPlayer();
        Plugin plugin = THRPlugin.plugin;
        THSkillGlobal.global_quit(pl, plugin, event);
    }

    public void join(final PlayerJoinEvent event) {
        //グローバル
        Player pl = event.getPlayer();
        Plugin plugin = THRPlugin.plugin;
        THSkillGlobal.global_join(pl, plugin, event);
    }

    public void respawn(final PlayerRespawnEvent event) {
        //グローバル
        Player pl = event.getPlayer();
        Plugin plugin = THRPlugin.plugin;
        THSkillGlobal.global_respawnhealth(pl, plugin, event);
    }

    public void move(final PlayerMoveEvent event) {
        Plugin plugin = THRPlugin.plugin;
        Player pl = event.getPlayer();
        String race = THRPlugin.plugin.getConfig().getString("user." + pl.getUniqueId() + ".race").toString();
        //グローバル
        //人魚
        if (race.contains("ninngen") && THRPlugin.plugin.getConfig().getDouble("user." + pl.getUniqueId() + ".spilit") > 20.0D) {
            THSkillYUZ.ninngyo_swimming(pl, plugin, event);
        }
    }

    public void interactentity(final PlayerInteractEntityEvent event) {
        //グローバル
        Plugin plugin = THRPlugin.plugin;
        Player pl = event.getPlayer();
        String race = THRPlugin.plugin.getConfig().getString("user." + pl.getUniqueId() + ".race").toString();
        if (race.contains("ninngen") == false && race.contains("mazyo") == false && race.contains("houraizin") == false && race.contains("gennzinnsin") == false && race.contains("sibito") == false && race.contains("sennninn") == false) {
            THSkillGlobal.global_no_ninngen(pl, plugin, event);
        }
    }

    public void interact(final PlayerInteractEvent event) {
        final Plugin plugin = THRPlugin.plugin;
        final Player pl = event.getPlayer();
        Material handitem = pl.getItemInHand().getType();
        String race = THRPlugin.plugin.getConfig().getString("user." + pl.getUniqueId() + ".race").toString();
        //魔女
        if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            //グローバル
            if (pl.isSneaking()) THSkillGlobal.global_charge_mana(pl, plugin, event);
            if (race.contains("mazyo")) {
                if (THRPlugin.plugin.getConfig().getDouble("user." + pl.getUniqueId() + ".spilit") > 20.0D && handitem == Material.STICK) {
                    if (((MetadataValue) pl.getMetadata("casting").get(0)).asBoolean()) {
                        pl.sendMessage(THRPlugin.thrpre + ChatColor.RED + "他の魔法を詠唱中です！");
                    } else if (((MetadataValue) pl.getMetadata("using-magic").get(0)).asBoolean()) {
                        pl.sendMessage(THRPlugin.thrpre + ChatColor.RED + "他の魔法を使用中です！");
                    } else {
                        MetadataValue casting = new FixedMetadataValue(plugin, Boolean.valueOf(true));
                        pl.setMetadata("casting", casting);
                        THRPlugin.plugin.getConfig().set("user." + pl.getUniqueId() + ".spilit", THRPlugin.plugin.getConfig().getDouble("user." + pl.getUniqueId() + ".spilit") - 20);
                        THRPlugin.plugin.getServer().getScheduler().scheduleSyncDelayedTask(THRPlugin.plugin, new Runnable() {
                            public void run() {
                                THSkillNNG.mazyo_heal(pl, plugin, event);
                                MetadataValue casting = new FixedMetadataValue(plugin, Boolean.valueOf(false));
                                pl.setMetadata("casting", casting);
                            }
                        }, 20L);
                    }
                }
                if (THRPlugin.plugin.getConfig().getDouble("user." + pl.getUniqueId() + ".spilit") > 25.0D && handitem == Material.WOOD_SWORD) {
                    if (((MetadataValue) pl.getMetadata("casting").get(0)).asBoolean()) {
                        pl.sendMessage(THRPlugin.thrpre + ChatColor.RED + "他の魔法を詠唱中です！");
                    } else if (((MetadataValue) pl.getMetadata("using-magic").get(0)).asBoolean()) {
                        pl.sendMessage(THRPlugin.thrpre + ChatColor.RED + "他の魔法を使用中です！");
                    } else {
                        MetadataValue casting = new FixedMetadataValue(plugin, Boolean.valueOf(true));
                        pl.setMetadata("casting", casting);
                        THRPlugin.plugin.getConfig().set("user." + pl.getUniqueId() + ".spilit", THRPlugin.plugin.getConfig().getDouble("user." + pl.getUniqueId() + ".spilit") - 25);
                        THRPlugin.plugin.getServer().getScheduler().scheduleSyncDelayedTask(THRPlugin.plugin, new Runnable() {
                            public void run() {
                                THSkillNNG.mazyo_wind(pl, plugin, event);
                                MetadataValue casting = new FixedMetadataValue(plugin, Boolean.valueOf(false));
                                pl.setMetadata("casting", casting);
                            }
                        }, 10L);
                    }
                }
                if (THRPlugin.plugin.getConfig().getDouble("user." + pl.getUniqueId() + ".spilit") > 35.0D && handitem == Material.STONE_SWORD) {
                    if (((MetadataValue) pl.getMetadata("casting").get(0)).asBoolean()) {
                        pl.sendMessage(THRPlugin.thrpre + ChatColor.RED + "他の魔法を詠唱中です！");
                    } else if (((MetadataValue) pl.getMetadata("using-magic").get(0)).asBoolean()) {
                        pl.sendMessage(THRPlugin.thrpre + ChatColor.RED + "他の魔法を使用中です！");
                    } else {
                        MetadataValue casting = new FixedMetadataValue(plugin, Boolean.valueOf(true));
                        pl.setMetadata("casting", casting);
                        THRPlugin.plugin.getConfig().set("user." + pl.getUniqueId() + ".spilit", THRPlugin.plugin.getConfig().getDouble("user." + pl.getUniqueId() + ".spilit") - 35);
                        THRPlugin.plugin.getServer().getScheduler().scheduleSyncDelayedTask(THRPlugin.plugin, new Runnable() {
                            public void run() {
                                THSkillNNG.mazyo_dirt(pl, plugin, event);
                                MetadataValue casting = new FixedMetadataValue(plugin, Boolean.valueOf(false));
                                pl.setMetadata("casting", casting);
                            }
                        }, 60L);
                    }
                }
                if (THRPlugin.plugin.getConfig().getDouble("user." + pl.getUniqueId() + ".spilit") > 30.0D && handitem == Material.IRON_SWORD) {
                    if (((MetadataValue) pl.getMetadata("casting").get(0)).asBoolean()) {
                        pl.sendMessage(THRPlugin.thrpre + ChatColor.RED + "他の魔法を詠唱中です！");
                    } else if (((MetadataValue) pl.getMetadata("using-magic").get(0)).asBoolean()) {
                        pl.sendMessage(THRPlugin.thrpre + ChatColor.RED + "他の魔法を使用中です！");
                    } else {
                        MetadataValue casting = new FixedMetadataValue(plugin, Boolean.valueOf(true));
                        pl.setMetadata("casting", casting);
                        THRPlugin.plugin.getConfig().set("user." + pl.getUniqueId() + ".spilit", THRPlugin.plugin.getConfig().getDouble("user." + pl.getUniqueId() + ".spilit") - 30);
                        THRPlugin.plugin.getServer().getScheduler().scheduleSyncDelayedTask(THRPlugin.plugin, new Runnable() {
                            public void run() {
                                THSkillNNG.mazyo_fire(pl, plugin, event);
                                MetadataValue casting = new FixedMetadataValue(plugin, Boolean.valueOf(false));
                                pl.setMetadata("casting", casting);
                            }
                        }, 20L);
                    }
                }
                if (THRPlugin.plugin.getConfig().getDouble("user." + pl.getUniqueId() + ".spilit") > 50.0D && handitem == Material.DIAMOND_SWORD) {
                    if (((MetadataValue) pl.getMetadata("casting").get(0)).asBoolean()) {
                        pl.sendMessage(THRPlugin.thrpre + ChatColor.RED + "他の魔法を詠唱中です！");
                    } else if (((MetadataValue) pl.getMetadata("using-magic").get(0)).asBoolean()) {
                        pl.sendMessage(THRPlugin.thrpre + ChatColor.RED + "他の魔法を使用中です！");
                    } else {
                        MetadataValue casting = new FixedMetadataValue(plugin, Boolean.valueOf(true));
                        pl.setMetadata("casting", casting);
                        THRPlugin.plugin.getConfig().set("user." + pl.getUniqueId() + ".spilit", THRPlugin.plugin.getConfig().getDouble("user." + pl.getUniqueId() + ".spilit") - 50);
                        THRPlugin.plugin.getServer().getScheduler().scheduleSyncDelayedTask(THRPlugin.plugin, new Runnable() {
                            public void run() {
                                THSkillNNG.mazyo_water(pl, plugin, event);
                                MetadataValue casting = new FixedMetadataValue(plugin, Boolean.valueOf(false));
                                pl.setMetadata("casting", casting);
                            }
                        }, 50L);
                    }
                }
                if (THRPlugin.plugin.getConfig().getDouble("user." + pl.getUniqueId() + ".spilit") > 70.0D && handitem == Material.GOLD_SWORD) {
                    if (((MetadataValue) pl.getMetadata("casting").get(0)).asBoolean()) {
                        pl.sendMessage(THRPlugin.thrpre + ChatColor.RED + "他の魔法を詠唱中です！");
                    } else if (((MetadataValue) pl.getMetadata("using-magic").get(0)).asBoolean()) {
                        pl.sendMessage(THRPlugin.thrpre + ChatColor.RED + "他の魔法を使用中です！");
                    } else {
                        MetadataValue casting = new FixedMetadataValue(plugin, Boolean.valueOf(true));
                        pl.setMetadata("casting", casting);
                        THRPlugin.plugin.getConfig().set("user." + pl.getUniqueId() + ".spilit", THRPlugin.plugin.getConfig().getDouble("user." + pl.getUniqueId() + ".spilit") - 70);
                        THRPlugin.plugin.getServer().getScheduler().scheduleSyncDelayedTask(THRPlugin.plugin, new Runnable() {
                            public void run() {
                                THSkillNNG.mazyo_thunder(pl, plugin, event);
                                MetadataValue casting = new FixedMetadataValue(plugin, Boolean.valueOf(false));
                                pl.setMetadata("casting", casting);
                            }
                        }, 70L);
                    }
                }
            }
        }
        if (event.getAction() == Action.LEFT_CLICK_AIR || event.getAction() == Action.LEFT_CLICK_BLOCK) {
            //妖獣人魚獣人
            if (race.contains("youzuu") || race.contains("ninngyo") || race.contains("zyuuzin")) {
                if (THRPlugin.plugin.getConfig().getDouble("user." + pl.getUniqueId() + ".spilit") > 15.0D && handitem == Material.FISHING_ROD) {
                    if (((MetadataValue) pl.getMetadata("casting").get(0)).asBoolean()) {
                        pl.sendMessage(THRPlugin.thrpre + ChatColor.RED + "他の魔法を詠唱中です！");
                    } else if (((MetadataValue) pl.getMetadata("using-magic").get(0)).asBoolean()) {
                        pl.sendMessage(THRPlugin.thrpre + ChatColor.RED + "他の魔法を使用中です！");
                    } else {
                        MetadataValue casting = new FixedMetadataValue(plugin, Boolean.valueOf(true));
                        pl.setMetadata("casting", casting);
                        THRPlugin.plugin.getConfig().set("user." + pl.getUniqueId() + ".spilit", THRPlugin.plugin.getConfig().getDouble("user." + pl.getUniqueId() + ".spilit") - 15);
                        THRPlugin.plugin.getServer().getScheduler().scheduleSyncDelayedTask(THRPlugin.plugin, new Runnable() {
                            public void run() {
                                THSkillYUZ.youzyuu_summon_wolf(pl, plugin, event);
                                MetadataValue casting = new FixedMetadataValue(plugin, Boolean.valueOf(false));
                                pl.setMetadata("casting", casting);
                            }
                        }, 30L);
                    }
                }
            }
            //式
            if (race.contains("siki") && THRPlugin.plugin.getConfig().getDouble("user." + pl.getUniqueId() + ".spilit") > 20.0 && handitem == Material.FISHING_ROD) {
                if (((MetadataValue) pl.getMetadata("casting").get(0)).asBoolean()) {
                    pl.sendMessage(THRPlugin.thrpre + ChatColor.RED + "他の魔法を詠唱中です！");
                } else if (((MetadataValue) pl.getMetadata("using-magic").get(0)).asBoolean()) {
                    pl.sendMessage(THRPlugin.thrpre + ChatColor.RED + "他の魔法を使用中です！");
                } else {
                    MetadataValue casting = new FixedMetadataValue(plugin, Boolean.valueOf(true));
                    pl.setMetadata("casting", casting);
                    THRPlugin.plugin.getConfig().set("user." + pl.getUniqueId() + ".spilit", THRPlugin.plugin.getConfig().getDouble("user." + pl.getUniqueId() + ".spilit") - 20);
                    THRPlugin.plugin.getServer().getScheduler().scheduleSyncDelayedTask(THRPlugin.plugin, new Runnable() {
                        public void run() {
                            THSkillYUZ.siki_summon_ocerot(pl, plugin, event);
                            MetadataValue casting = new FixedMetadataValue(plugin, Boolean.valueOf(false));
                            pl.setMetadata("casting", casting);
                        }
                    }, 40L);
                }
            }
            //妖獣全て
            if (race.contains("youzyuu") || race.contains("zyuuzin") || race.contains("ninngyo") || race.contains("siki")) {
                if (THRPlugin.plugin.getConfig().getDouble("user." + pl.getUniqueId() + ".spilit") > 30.0D && handitem == Material.BOW) {
                    if (((MetadataValue) pl.getMetadata("casting").get(0)).asBoolean()) {
                        pl.sendMessage(THRPlugin.thrpre + ChatColor.RED + "他の魔法を詠唱中です！");
                    } else if (((MetadataValue) pl.getMetadata("using-magic").get(0)).asBoolean()) {
                        pl.sendMessage(THRPlugin.thrpre + ChatColor.RED + "他の魔法を使用中です！");
                    } else {
                        MetadataValue casting = new FixedMetadataValue(plugin, Boolean.valueOf(true));
                        pl.setMetadata("casting", casting);
                        THRPlugin.plugin.getConfig().set("user." + pl.getUniqueId() + ".spilit", THRPlugin.plugin.getConfig().getDouble("user." + pl.getUniqueId() + ".spilit") - 30);
                        THRPlugin.plugin.getServer().getScheduler().scheduleSyncDelayedTask(THRPlugin.plugin, new Runnable() {
                            public void run() {
                                THSkillYUZ.youzyu_gainenergy(pl, plugin, event);
                            }
                        }, 0L);
                    }
                }
            }
        }
    }

    public void damagebyentity(final EntityDamageByEntityEvent event) {
        Plugin plugin = THRPlugin.plugin;
        if (event.getDamager() instanceof Player) {
            Player pl = (Player) event.getDamager();
            String race = THRPlugin.plugin.getConfig().getString("user." + pl.getUniqueId() + ".race").toString();
            //死人
            if (race.contains("sibito") && THRPlugin.plugin.getConfig().getInt("user." + pl.getUniqueId() + ".split") > 20)
                THSkillNNG.sibito_deadattack(pl, plugin, event);
            if (race.contains("gennzinnsin") && THRPlugin.plugin.getConfig().getInt("user." + pl.getUniqueId() + ".split") > 20)
                THSkillNNG.gennzinnsin_luckyattack(pl, plugin, event);
            //グローバル
            if (THRPlugin.plugin.getConfig().getInt("user." + pl.getUniqueId() + ".split") <= 20)
                THSkillGlobal.global_no_mana_attack(pl, plugin, event);
        }
        if (event.getEntity() instanceof Player) {
            Player pl = (Player) event.getEntity();
            String race = THRPlugin.plugin.getConfig().getString("user." + pl.getUniqueId() + ".race").toString();
            //蓬莱人
            if (race.contains("houraizin") && THRPlugin.plugin.getConfig().getInt("user." + pl.getUniqueId() + ".split") > 20)
                THSkillNNG.houraizin_reverselife_Entity(pl, plugin, event);
            //グローバル
            if (THRPlugin.plugin.getConfig().getInt("user." + pl.getUniqueId() + ".split") <= 20)
                THSkillGlobal.global_no_mana_damaged(pl, plugin, event);
        }
    }

    public void damagebyblock(final EntityDamageByBlockEvent event) {
        Plugin plugin = THRPlugin.plugin;
        Player pl = (Player) event.getDamager();
        String race = THRPlugin.plugin.getConfig().getString("user." + pl.getUniqueId() + ".race").toString();
        //蓬莱人
        if (race.contains("houraizin") && THRPlugin.plugin.getConfig().getInt("user." + pl.getUniqueId() + ".split") > 20)
            THSkillNNG.houraizin_reverselife_block(pl, plugin, event);
    }

    @SuppressWarnings("deprecation")
    public void togglesneak(final PlayerToggleSneakEvent event) {
        Plugin plugin = THRPlugin.plugin;
        Player pl = event.getPlayer();
        //仙人
        if (THRPlugin.plugin.getConfig().getString("user." + pl.getUniqueId() + ".race").toString().contains("sennnin")) {
            if ((!pl.isOnGround()) && (pl.isSneaking()) && THRPlugin.plugin.getConfig().getDouble("user." + pl.getUniqueId() + ".spilit") >= 20.0D) {
                THSkillNNG.sennnin_passthough(pl, plugin, event);
            }
        }
    }
}
