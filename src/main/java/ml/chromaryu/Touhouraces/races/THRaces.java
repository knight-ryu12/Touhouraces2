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
import ml.chromaryu.Touhouraces.THPlugin;
import ml.chromaryu.Touhouraces.races.skill.THSkillGlobal;
import ml.chromaryu.Touhouraces.races.skill.THSkillNNG;
import ml.chromaryu.Touhouraces.races.skill.THSkillYUZ;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.metadata.FixedMetadataValue;

public class THRaces implements Listener
{
    ///アクション
    public void chat(final AsyncPlayerChatEvent event)
    {
        //グローバル
        Player pl = event.getPlayer();
        Plugin plugin = THPlugin.plugin;
        THSkillGlobal.global_chat(pl, plugin , event);
    }
    public void quit(final PlayerQuitEvent event)
    {
        //グローバル
        Player pl = event.getPlayer();
        Plugin plugin = THPlugin.plugin;
        THSkillGlobal.global_quit(pl, plugin , event);
    }
    public void join(final PlayerJoinEvent event)
    {
        //グローバル
        Player pl = event.getPlayer();
        Plugin plugin = THPlugin.plugin;
        THSkillGlobal.global_join(pl, plugin , event);
    }

    public void respawn(final PlayerRespawnEvent event)
    {
        //グローバル
        Player pl = event.getPlayer();
        Plugin plugin = THPlugin.plugin;
        THSkillGlobal.global_respawnhealth(pl, plugin , event);
    }
    public void move(final PlayerMoveEvent event)
    {
        Plugin plugin = THPlugin.plugin;
        Player pl = event.getPlayer();
        String race = THPlugin.plugin.getConfig().getString("user." + pl.getUniqueId() + ".race").toString();
        //グローバル
        //人魚
        if (race.contains("ninngen") && THPlugin.plugin.getConfig().getDouble("user." + pl.getUniqueId() + ".spilit") > 20.0D)
        {
            THSkillYUZ.ninngyo_swimming(pl, plugin , event);
        }
    }

    public void interactentity(final PlayerInteractEntityEvent event)
    {
        //グローバル
        Plugin plugin = THPlugin.plugin;
        Player pl = event.getPlayer();
        String race = THPlugin.plugin.getConfig().getString("user." + pl.getUniqueId() + ".race").toString();
        if (race.contains("ninngen") == false && race.contains("mazyo") == false  && race.contains("houraizin") == false  && race.contains("gennzinnsin") == false  && race.contains("sibito") == false  && race.contains("sennninn") == false )
        {
            THSkillGlobal.global_no_ninngen(pl, plugin , event);
        }
    }
    public void interact(final PlayerInteractEvent event)
    {
        final Plugin plugin = THPlugin.plugin;
        final Player pl = event.getPlayer();
        Material handitem = pl.getItemInHand().getType();
        String race = THPlugin.plugin.getConfig().getString("user." + pl.getUniqueId() + ".race").toString();
        //魔女
        if (event.getAction() == Action.RIGHT_CLICK_AIR ||event.getAction() ==  Action.RIGHT_CLICK_BLOCK)
        {
            //グローバル
            if (pl.isSneaking()) THSkillGlobal.global_charge_mana(pl, plugin , event);
            if (race.contains("mazyo"))
            {
                if (THPlugin.plugin.getConfig().getDouble("user." + pl.getUniqueId() + ".spilit") > 20.0D && handitem == Material.STICK)
                {
                    if (((MetadataValue)pl.getMetadata("casting").get(0)).asBoolean())
                    {
                        pl.sendMessage(THPlugin.thrpre + ChatColor.RED + "他の魔法を詠唱中です！");
                    }
                    else if (((MetadataValue)pl.getMetadata("using-magic").get(0)).asBoolean())
                    {
                        pl.sendMessage(THPlugin.thrpre + ChatColor.RED + "他の魔法を使用中です！");
                    }
                    else
                    {
                        MetadataValue casting = new FixedMetadataValue(plugin, Boolean.valueOf(true));
                        pl.setMetadata("casting", casting);
                        THPlugin.plugin.getConfig().set("user." + pl.getUniqueId() + ".spilit",THPlugin.plugin.getConfig().getDouble("user." + pl.getUniqueId() + ".spilit") - 20);
                        THPlugin.plugin.getServer().getScheduler().scheduleSyncDelayedTask(THPlugin.plugin, new Runnable()
                        {
                            public void run()
                            {
                                THSkillNNG.mazyo_heal(pl, plugin , event);
                                MetadataValue casting = new FixedMetadataValue(plugin, Boolean.valueOf(false));
                                pl.setMetadata("casting", casting);
                            }
                        }, 20L);
                    }
                }
                if (THPlugin.plugin.getConfig().getDouble("user." + pl.getUniqueId() + ".spilit") > 25.0D && handitem == Material.WOOD_SWORD)
                {
                    if (((MetadataValue)pl.getMetadata("casting").get(0)).asBoolean())
                    {
                        pl.sendMessage(THPlugin.thrpre + ChatColor.RED + "他の魔法を詠唱中です！");
                    }
                    else if (((MetadataValue)pl.getMetadata("using-magic").get(0)).asBoolean())
                    {
                        pl.sendMessage(THPlugin.thrpre + ChatColor.RED + "他の魔法を使用中です！");
                    }
                    else
                    {
                        MetadataValue casting = new FixedMetadataValue(plugin, Boolean.valueOf(true));
                        pl.setMetadata("casting", casting);
                        THPlugin.plugin.getConfig().set("user." + pl.getUniqueId() + ".spilit",THPlugin.plugin.getConfig().getDouble("user." + pl.getUniqueId() + ".spilit") - 25);
                        THPlugin.plugin.getServer().getScheduler().scheduleSyncDelayedTask(THPlugin.plugin, new Runnable()
                        {
                            public void run()
                            {
                                THSkillNNG.mazyo_wind(pl, plugin , event);
                                MetadataValue casting = new FixedMetadataValue(plugin, Boolean.valueOf(false));
                                pl.setMetadata("casting", casting);
                            }
                        }, 10L);
                    }
                }
                if (THPlugin.plugin.getConfig().getDouble("user." + pl.getUniqueId() + ".spilit") >35.0D && handitem == Material.STONE_SWORD)
                {
                    if (((MetadataValue)pl.getMetadata("casting").get(0)).asBoolean())
                    {
                        pl.sendMessage(THPlugin.thrpre + ChatColor.RED + "他の魔法を詠唱中です！");
                    }
                    else if (((MetadataValue)pl.getMetadata("using-magic").get(0)).asBoolean())
                    {
                        pl.sendMessage(THPlugin.thrpre + ChatColor.RED + "他の魔法を使用中です！");
                    }
                    else
                    {
                        MetadataValue casting = new FixedMetadataValue(plugin, Boolean.valueOf(true));
                        pl.setMetadata("casting", casting);
                        THPlugin.plugin.getConfig().set("user." + pl.getUniqueId() + ".spilit",THPlugin.plugin.getConfig().getDouble("user." + pl.getUniqueId() + ".spilit") - 35);
                        THPlugin.plugin.getServer().getScheduler().scheduleSyncDelayedTask(THPlugin.plugin, new Runnable()
                        {
                            public void run()
                            {
                                THSkillNNG.mazyo_dirt(pl, plugin , event);
                                MetadataValue casting = new FixedMetadataValue(plugin, Boolean.valueOf(false));
                                pl.setMetadata("casting", casting);
                            }
                        }, 60L);
                    }
                }
                if (THPlugin.plugin.getConfig().getDouble("user." + pl.getUniqueId() + ".spilit") > 30.0D && handitem == Material.IRON_SWORD)
                {
                    if (((MetadataValue)pl.getMetadata("casting").get(0)).asBoolean())
                    {
                        pl.sendMessage(THPlugin.thrpre + ChatColor.RED + "他の魔法を詠唱中です！");
                    }
                    else if (((MetadataValue)pl.getMetadata("using-magic").get(0)).asBoolean())
                    {
                        pl.sendMessage(THPlugin.thrpre + ChatColor.RED + "他の魔法を使用中です！");
                    }
                    else
                    {
                        MetadataValue casting = new FixedMetadataValue(plugin, Boolean.valueOf(true));
                        pl.setMetadata("casting", casting);
                        THPlugin.plugin.getConfig().set("user." + pl.getUniqueId() + ".spilit",THPlugin.plugin.getConfig().getDouble("user." + pl.getUniqueId() + ".spilit") - 30);
                        THPlugin.plugin.getServer().getScheduler().scheduleSyncDelayedTask(THPlugin.plugin, new Runnable()
                        {
                            public void run()
                            {
                                THSkillNNG.mazyo_fire(pl, plugin , event);
                                MetadataValue casting = new FixedMetadataValue(plugin, Boolean.valueOf(false));
                                pl.setMetadata("casting", casting);
                            }
                        }, 20L);
                    }
                }
                if (THPlugin.plugin.getConfig().getDouble("user." + pl.getUniqueId() + ".spilit") > 50.0D && handitem == Material.DIAMOND_SWORD)
                {
                    if (((MetadataValue)pl.getMetadata("casting").get(0)).asBoolean())
                    {
                        pl.sendMessage(THPlugin.thrpre + ChatColor.RED + "他の魔法を詠唱中です！");
                    }
                    else if (((MetadataValue)pl.getMetadata("using-magic").get(0)).asBoolean())
                    {
                        pl.sendMessage(THPlugin.thrpre + ChatColor.RED + "他の魔法を使用中です！");
                    }
                    else
                    {
                        MetadataValue casting = new FixedMetadataValue(plugin, Boolean.valueOf(true));
                        pl.setMetadata("casting", casting);
                        THPlugin.plugin.getConfig().set("user." + pl.getUniqueId() + ".spilit",THPlugin.plugin.getConfig().getDouble("user." + pl.getUniqueId() + ".spilit") - 50);
                        THPlugin.plugin.getServer().getScheduler().scheduleSyncDelayedTask(THPlugin.plugin, new Runnable()
                        {
                            public void run()
                            {
                                THSkillNNG.mazyo_water(pl, plugin , event);
                                MetadataValue casting = new FixedMetadataValue(plugin, Boolean.valueOf(false));
                                pl.setMetadata("casting", casting);
                            }
                        }, 50L);
                    }
                }
                if (THPlugin.plugin.getConfig().getDouble("user." + pl.getUniqueId() + ".spilit") > 70.0D && handitem == Material.GOLD_SWORD)
                {
                    if (((MetadataValue)pl.getMetadata("casting").get(0)).asBoolean())
                    {
                        pl.sendMessage(THPlugin.thrpre + ChatColor.RED + "他の魔法を詠唱中です！");
                    }
                    else if (((MetadataValue)pl.getMetadata("using-magic").get(0)).asBoolean())
                    {
                        pl.sendMessage(THPlugin.thrpre + ChatColor.RED + "他の魔法を使用中です！");
                    }
                    else
                    {
                        MetadataValue casting = new FixedMetadataValue(plugin, Boolean.valueOf(true));
                        pl.setMetadata("casting", casting);
                        THPlugin.plugin.getConfig().set("user." + pl.getUniqueId() + ".spilit",THPlugin.plugin.getConfig().getDouble("user." + pl.getUniqueId() + ".spilit") - 70);
                        THPlugin.plugin.getServer().getScheduler().scheduleSyncDelayedTask(THPlugin.plugin, new Runnable()
                        {
                            public void run()
                            {
                                THSkillNNG.mazyo_thunder(pl, plugin , event);
                                MetadataValue casting = new FixedMetadataValue(plugin, Boolean.valueOf(false));
                                pl.setMetadata("casting", casting);
                            }
                        }, 70L);
                    }
                }
            }
        }
        if (event.getAction() == Action.LEFT_CLICK_AIR ||event.getAction() ==  Action.LEFT_CLICK_BLOCK)
        {
            //妖獣人魚獣人
            if (race.contains("youzuu") || race.contains("ninngyo") ||race.contains("zyuuzin"))
            {
                if (THPlugin.plugin.getConfig().getDouble("user." + pl.getUniqueId() + ".spilit") > 15.0D && handitem == Material.FISHING_ROD)
                {
                    if (((MetadataValue)pl.getMetadata("casting").get(0)).asBoolean())
                    {
                        pl.sendMessage(THPlugin.thrpre + ChatColor.RED + "他の魔法を詠唱中です！");
                    }
                    else if (((MetadataValue)pl.getMetadata("using-magic").get(0)).asBoolean())
                    {
                        pl.sendMessage(THPlugin.thrpre + ChatColor.RED + "他の魔法を使用中です！");
                    }
                    else
                    {
                        MetadataValue casting = new FixedMetadataValue(plugin, Boolean.valueOf(true));
                        pl.setMetadata("casting", casting);
                        THPlugin.plugin.getConfig().set("user." + pl.getUniqueId() + ".spilit",THPlugin.plugin.getConfig().getDouble("user." + pl.getUniqueId() + ".spilit") - 15);
                        THPlugin.plugin.getServer().getScheduler().scheduleSyncDelayedTask(THPlugin.plugin, new Runnable()
                        {
                            public void run()
                            {
                                THSkillYUZ.youzyuu_summon_wolf(pl, plugin , event);
                                MetadataValue casting = new FixedMetadataValue(plugin, Boolean.valueOf(false));
                                pl.setMetadata("casting", casting);
                            }
                        }, 30L);
                    }
                }
            }
            //式
            if (race.contains("siki") && THPlugin.plugin.getConfig().getDouble("user." + pl.getUniqueId() + ".spilit") > 20.0 && handitem == Material.FISHING_ROD)
            {
                if (((MetadataValue)pl.getMetadata("casting").get(0)).asBoolean())
                {
                    pl.sendMessage(THPlugin.thrpre + ChatColor.RED + "他の魔法を詠唱中です！");
                }
                else if (((MetadataValue)pl.getMetadata("using-magic").get(0)).asBoolean())
                {
                    pl.sendMessage(THPlugin.thrpre + ChatColor.RED + "他の魔法を使用中です！");
                }
                else
                {
                    MetadataValue casting = new FixedMetadataValue(plugin, Boolean.valueOf(true));
                    pl.setMetadata("casting", casting);
                    THPlugin.plugin.getConfig().set("user." + pl.getUniqueId() + ".spilit",THPlugin.plugin.getConfig().getDouble("user." + pl.getUniqueId() + ".spilit") - 20);
                    THPlugin.plugin.getServer().getScheduler().scheduleSyncDelayedTask(THPlugin.plugin, new Runnable()
                    {
                        public void run()
                        {
                            THSkillYUZ.siki_summon_ocerot(pl, plugin , event);
                            MetadataValue casting = new FixedMetadataValue(plugin, Boolean.valueOf(false));
                            pl.setMetadata("casting", casting);
                        }
                    }, 40L);
                }
            }
            //妖獣全て
            if (race.contains("youzyuu") || race.contains("zyuuzin") || race.contains("ninngyo") || race.contains("siki"))
            {
                if (THPlugin.plugin.getConfig().getDouble("user." + pl.getUniqueId() + ".spilit") > 30.0D && handitem == Material.BOW)
                {
                    if (((MetadataValue)pl.getMetadata("casting").get(0)).asBoolean())
                    {
                        pl.sendMessage(THPlugin.thrpre + ChatColor.RED + "他の魔法を詠唱中です！");
                    }
                    else if (((MetadataValue)pl.getMetadata("using-magic").get(0)).asBoolean())
                    {
                        pl.sendMessage(THPlugin.thrpre + ChatColor.RED + "他の魔法を使用中です！");
                    }
                    else
                    {
                        MetadataValue casting = new FixedMetadataValue(plugin, Boolean.valueOf(true));
                        pl.setMetadata("casting", casting);
                        THPlugin.plugin.getConfig().set("user." + pl.getUniqueId() + ".spilit",THPlugin.plugin.getConfig().getDouble("user." + pl.getUniqueId() + ".spilit") - 30);
                        THPlugin.plugin.getServer().getScheduler().scheduleSyncDelayedTask(THPlugin.plugin, new Runnable()
                        {
                            public void run()
                            {
                                THSkillYUZ.youzyu_gainenergy(pl, plugin , event);
                            }
                        },0L);
                    }
                }
            }
        }
    }
    public void damagebyentity(final EntityDamageByEntityEvent event)
    {
        Plugin plugin = THPlugin.plugin;
        if (event.getDamager() instanceof Player)
        {
            Player pl = (Player) event.getDamager();
            String race = THPlugin.plugin.getConfig().getString("user." + pl.getUniqueId() + ".race").toString();
            //死人
            if (race.contains("sibito") && THPlugin.plugin.getConfig().getInt("user." + pl.getUniqueId() + ".split") > 20)THSkillNNG.sibito_deadattack(pl, plugin, event);
            if (race.contains("gennzinnsin") && THPlugin.plugin.getConfig().getInt("user." + pl.getUniqueId() + ".split") > 20)THSkillNNG.gennzinnsin_luckyattack(pl, plugin, event);
            //グローバル
            if (THPlugin.plugin.getConfig().getInt("user." + pl.getUniqueId() + ".split") <= 20) THSkillGlobal.global_no_mana_attack(pl, plugin , event);
        }
        if (event.getEntity() instanceof Player)
        {
            Player pl = (Player) event.getEntity();
            String race = THPlugin.plugin.getConfig().getString("user." + pl.getUniqueId() + ".race").toString();
            //蓬莱人
            if (race.contains("houraizin") && THPlugin.plugin.getConfig().getInt("user." + pl.getUniqueId() + ".split") > 20)THSkillNNG.houraizin_reverselife_Entity(pl, plugin, event);
            //グローバル
            if (THPlugin.plugin.getConfig().getInt("user." + pl.getUniqueId() + ".split") <= 20) THSkillGlobal.global_no_mana_damaged(pl, plugin , event);
        }
    }

    public void damagebyblock(final EntityDamageByBlockEvent event)
    {
        Plugin plugin = THPlugin.plugin;
        Player pl = (Player) event.getDamager();
        String race = THPlugin.plugin.getConfig().getString("user." + pl.getUniqueId() + ".race").toString();
        //蓬莱人
        if (race.contains("houraizin") && THPlugin.plugin.getConfig().getInt("user." + pl.getUniqueId() + ".split") > 20)THSkillNNG.houraizin_reverselife_block(pl, plugin, event);
    }
    @SuppressWarnings("deprecation")
    public void togglesneak(final PlayerToggleSneakEvent event)
    {
        Plugin plugin = THPlugin.plugin;
        Player pl = event.getPlayer();
        //仙人
        if (THPlugin.plugin.getConfig().getString("user." + pl.getUniqueId() + ".race").toString().contains("sennnin")) {
            if ((!pl.isOnGround()) && (pl.isSneaking()) && THPlugin.plugin.getConfig().getDouble("user." + pl.getUniqueId() + ".spilit") >= 20.0D)
            {
                THSkillNNG.sennnin_passthough(pl, plugin, event);
            }
        }
    }
}
