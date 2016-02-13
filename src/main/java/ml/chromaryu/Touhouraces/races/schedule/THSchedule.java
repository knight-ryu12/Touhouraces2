package ml.chromaryu.Touhouraces.races.schedule;

/**
 * Created by chroma on 16/02/12.
 */

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.entity.Bat;
import org.bukkit.entity.Entity;
import org.bukkit.entity.IronGolem;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Ocelot;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowman;
import org.bukkit.entity.Wolf;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;
import ml.chromaryu.Touhouraces.THRPlugin;

public class THSchedule {
    private THRPlugin manager;
    private String touhouraces;
    public THSchedule(THRPlugin manager) {
        this.manager = manager;
    }
    public THSchedule(String touhouraces) {
        this.touhouraces = touhouraces;
    }
    public THSchedule() {
    }
    public Runnable run1() {
        for (final Player player : Bukkit.getOnlinePlayers()) {
            if (player.hasMetadata("batman")) {
                for (final LivingEntity bat : Bukkit.getWorld(manager.getConfig().getString("enableworld")).getEntitiesByClass(Bat.class)) {
                    if (bat.hasMetadata("invincible")) {
                        if (((MetadataValue) player.getMetadata("batman").get(0)).asString().toString().contains(((MetadataValue) bat.getMetadata("invincible").get(0)).asString().toString())) {
                            manager.getServer().getScheduler().scheduleSyncDelayedTask(this.manager, new Runnable() {
                                private Plugin manager;
                                private String touhouraces;

                                public void run() {
                                    player.teleport(bat);
                                    player.setGameMode(GameMode.SURVIVAL);
                                    MetadataValue usingmagic = new FixedMetadataValue(this.manager, Boolean.valueOf(false));
                                    player.setMetadata("using-magic", usingmagic);
                                    player.removeMetadata("batman", this.manager);
                                    player.sendMessage(this.touhouraces + ChatColor.RED + "バンプカモフラージュの効果が切れました");
                                    bat.removeMetadata("invincible", this.manager);
                                    bat.damage(1000.0D);
                                }
                            }, 100L);
                        }
                    }
                }
            }
            if ((manager.getConfig().getDouble("user." + player.getUniqueId() + ".spilit") < 100.0D) && (((MetadataValue) player.getMetadata("spilituse").get(0)).asDouble() == 0.0D)) {
                manager.getConfig().set("user." + player.getUniqueId() + ".spilit", Double.valueOf(manager.getConfig().getDouble("user." + player.getUniqueId() + ".spilit") + 5.0D));
                if (player.isSneaking()) {
                    player.sendMessage(this.touhouraces + ChatColor.GREEN + "霊力：" + ChatColor.LIGHT_PURPLE + manager.getConfig().getDouble(new StringBuilder("user.").append(player.getUniqueId()).append(".spilit").toString()));
                }
            } else if ((manager.getConfig().getDouble("user." + player.getUniqueId() + ".spilit") < 100.0D) && (((MetadataValue) player.getMetadata("spilituse").get(0)).asDouble() < 0.0D)) {
                manager.getConfig().set("user." + player.getUniqueId() + ".spilit", Double.valueOf(manager.getConfig().getDouble("user." + player.getUniqueId() + ".spilit") - ((MetadataValue) player.getMetadata("spilituse").get(0)).asDouble()));
                player.playSound(player.getLocation(), Sound.NOTE_PIANO, 1.0F, -1.0F);
                if (player.isSneaking()) {
                    player.sendMessage(this.touhouraces + ChatColor.GREEN + "霊力：" + ChatColor.LIGHT_PURPLE + manager.getConfig().getDouble(new StringBuilder("user.").append(player.getUniqueId()).append(".spilit").toString()));
                }
            } else if ((manager.getConfig().getDouble("user." + player.getUniqueId() + ".spilit") > 0.0D) && (((MetadataValue) player.getMetadata("spilituse").get(0)).asDouble() > 0.0D)) {
                manager.getConfig().set("user." + player.getUniqueId() + ".spilit", Double.valueOf(manager.getConfig().getDouble("user." + player.getUniqueId() + ".spilit") - ((MetadataValue) player.getMetadata("spilituse").get(0)).asDouble()));
                if (player.isSneaking()) {
                    player.sendMessage(this.touhouraces + ChatColor.GREEN + "霊力：" + ChatColor.LIGHT_PURPLE + manager.getConfig().getDouble(new StringBuilder("user.").append(player.getUniqueId()).append(".spilit").toString()));
                }
            }
            if (player.hasPermission("thr.skill")) {
                if (!player.hasMetadata("ignoreskill")) {
                    if ((player.hasMetadata("satorin0")) && (player.isSneaking())) {
                        Player dpl = Bukkit.getPlayer(((MetadataValue) player.getMetadata("satorin0").get(0)).asString());
                        if (dpl != null) {
                            player.sendMessage("名前:" + ((MetadataValue) player.getMetadata("satorin0").get(0)).asString());
                            player.sendMessage("体力:" + dpl.getHealth());
                            player.sendMessage("座標:" + dpl.getLocation().getBlockX() + "," + dpl.getLocation().getBlockY() + "," + dpl.getLocation().getBlockZ());
                        }
                    }
                }
                if ((manager.getConfig().getString("user." + player.getUniqueId() + ".race").toString().contains("youma")) || (manager.getConfig().getString("user." + player.getUniqueId() + ".race").toString().contains("kappa")) || (manager.getConfig().getString("user." + player.getUniqueId() + ".race").toString().contains("tenngu"))) {
                    if (!player.isDead()) {
                        if (player.getHealth() > player.getMaxHealth() - 2.0D) {
                            player.setHealth(player.getMaxHealth());
                        } else {
                            player.setHealth(2.0D + player.getHealth());
                        }
                    }
                } else if (manager.getConfig().getString("user." + player.getUniqueId() + ".race").toString().contains("kennyou")) {
                    if ((!player.isDead()) && (manager.getConfig().getDouble("user." + player.getUniqueId() + ".spilit") >= 10.0D) && (((MetadataValue) player.getMetadata("spilituse").get(0)).asDouble() > 0.0D)) {
                        if (player.getHealth() > player.getMaxHealth() - 5.0D) {
                            player.setHealth(player.getMaxHealth());
                        } else {
                            player.setHealth(5.0D + player.getHealth());
                        }
                    } else if (!player.isDead()) {
                        player.playSound(player.getLocation(), Sound.NOTE_BASS_GUITAR, 1.0F, 2.0F);
                    }
                } else if (!player.isDead()) {
                    if (player.getHealth() > player.getMaxHealth() - 1.0D) {
                        player.setHealth(player.getMaxHealth());
                    } else {
                        player.setHealth(1.0D + player.getHealth());
                    }
                }
            }
        }
        return null;
    }

    public Runnable run2() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            if ((manager.getConfig().getString("user." + player.getUniqueId() + ".race").toString().contains("youma")) || (manager.getConfig().getString("user." + player.getUniqueId() + ".race").toString().contains("kappa")) || (manager.getConfig().getString("user." + player.getUniqueId() + ".race").toString().contains("tenngu")) || (manager.getConfig().getString("user." + player.getUniqueId() + ".race").toString().contains("kennyou"))) {
                player.removePotionEffect(PotionEffectType.NIGHT_VISION);
                player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 2000, 0));
                if (manager.getConfig().getString("user." + player.getUniqueId() + ".race").toString().contains("kappa")) {
                    player.removePotionEffect(PotionEffectType.WATER_BREATHING);
                    player.addPotionEffect(new PotionEffect(PotionEffectType.WATER_BREATHING, 2000, 0));
                }
            }
            if ((manager.getConfig().getString("user." + player.getUniqueId() + ".race").toString().contains("akuma")) || (manager.getConfig().getString("user." + player.getUniqueId() + ".race").toString().contains("kyuuketuki")) || (manager.getConfig().getString("user." + player.getUniqueId() + ".race").toString().contains("oni"))) {
                player.removePotionEffect(PotionEffectType.ABSORPTION);
                player.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, 2000, 1));
            }
            if ((manager.getConfig().getString("user." + player.getUniqueId() + ".race").toString().contains("yousei")) || (manager.getConfig().getString("user." + player.getUniqueId() + ".race").toString().contains("satori")) || (manager.getConfig().getString("user." + player.getUniqueId() + ".race").toString().contains("kobito")) || (manager.getConfig().getString("user." + player.getUniqueId() + ".race").toString().contains("kibito"))) {
                player.removePotionEffect(PotionEffectType.JUMP);
                player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 2000, 1));
            }
            if ((manager.getConfig().getString("user." + player.getUniqueId() + ".race").toString().contains("youzyuu")) || (manager.getConfig().getString("user." + player.getUniqueId() + ".race").toString().contains("siki")) || (manager.getConfig().getString("user." + player.getUniqueId() + ".race").toString().contains("zyuuzin")) || (manager.getConfig().getString("user." + player.getUniqueId() + ".race").toString().contains("ninngyo"))) {
                if (!player.hasPotionEffect(PotionEffectType.SPEED)) {
                    player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 2000, 0));
                }
                if (manager.getConfig().getString("user." + player.getUniqueId() + ".race").toString().contains("ninngyo")) {
                    player.removePotionEffect(PotionEffectType.WATER_BREATHING);
                    player.addPotionEffect(new PotionEffect(PotionEffectType.WATER_BREATHING, 2000, 0));
                }
            }
            if ((manager.getConfig().getString("user." + player.getUniqueId() + ".race").toString().contains("zyuuzin")) && (player.getWorld().getTime() >= 16000L)) {
                if (!player.hasPotionEffect(PotionEffectType.SPEED)) {
                    player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 2000, 1));
                }
                player.removePotionEffect(PotionEffectType.JUMP);
                player.removePotionEffect(PotionEffectType.NIGHT_VISION);
                player.removePotionEffect(PotionEffectType.REGENERATION);
                player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 2000, 0));
                player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 2000, 0));
                player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 2000, 0));
            }
            if ((manager.getConfig().getString("user." + player.getUniqueId() + ".race").toString().contains("zyuuzin")) && (player.getWorld().getTime() >= 16000L) && (player.getWorld().getTime() < 16100L)) {
                player.sendMessage(this.touhouraces + ChatColor.RED + "あなたは獣の血を呼び覚ました！！");
                player.playSound(player.getLocation(), Sound.WOLF_DEATH, 1.0F, -1.0F);
            }
            if ((manager.getConfig().getString("user." + player.getUniqueId() + ".race").toString().contains("kyuuketuki")) && (player.getWorld().getTime() >= 14000L)) {
                player.removePotionEffect(PotionEffectType.NIGHT_VISION);
                player.removePotionEffect(PotionEffectType.INCREASE_DAMAGE);
                if (!player.hasPotionEffect(PotionEffectType.SPEED)) {
                    player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 2000, 1));
                }
                player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 2000, 0));
                player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 2000, 0));
                player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 2000, 1));
            } else if (manager.getConfig().getString("user." + player.getUniqueId() + ".race").toString().contains("kyuuketuki")) {
                player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 2000, 0));
            }
        }
        return null;
    }

    public Runnable run3() {
        for (Bat bat : Bukkit.getWorld(manager.getConfig().getString("enableworld")).getEntitiesByClass(Bat.class)) {
            if (bat.hasMetadata("invincible")) {
                List<Entity> entityforsyugorei = bat.getNearbyEntities(20.0D, 20.0D, 20.0D);
                for (Entity entity : entityforsyugorei) {
                    if ((entity instanceof Player)) {
                        bat.setVelocity(bat.getVelocity().add(new Vector(new Double(20.0D - (bat.getLocation().getX() - entity.getLocation().getX())).doubleValue() / 16.0D, 0.0D, new Double(20.0D - (bat.getLocation().getZ() - entity.getLocation().getZ())).doubleValue() / 16.0D)));
                        break;
                    }
                }
            }
        }
        for (final Snowman snowman : Bukkit.getWorld(manager.getConfig().getString("enableworld")).getEntitiesByClass(Snowman.class)) {
            if (snowman.hasMetadata("syugoreisnow")) {
                if (snowman.hasMetadata("syugoreitarget")) {
                    List<Entity> entityforsyugorei = snowman.getNearbyEntities(20.0D, 20.0D, 20.0D);
                    for (Entity entity : entityforsyugorei) {
                        if ((entity instanceof Player)) {
                            if (!((Player) entity).getName().toString().contains(((MetadataValue) snowman.getMetadata("syugoreitarget").get(0)).asString())) {
                                snowman.setTarget((LivingEntity) entity);
                                break;
                            }
                        }
                    }
                }
                manager.getServer().getScheduler().scheduleSyncDelayedTask(this.manager, new Runnable() {
                    public void run() {
                        snowman.damage(1000.0D);
                    }
                }, 300L);
            }
        }
        for (final IronGolem irongolem : Bukkit.getWorld(manager.getConfig().getString("enableworld")).getEntitiesByClass(IronGolem.class)) {
            if (irongolem.hasMetadata("syugoreiiron")) {
                if (irongolem.hasMetadata("syugoreitarget")) {
                    if (irongolem.getMetadata("syugoreitarget").get(0) != null) {
                        List<Entity> entityforsyugorei = irongolem.getNearbyEntities(20.0D, 20.0D, 20.0D);
                        for (Entity entity : entityforsyugorei) {
                            if ((entity instanceof Player)) {
                                if (!((Player) entity).getName().toString().contains(((MetadataValue) irongolem.getMetadata("syugoreitarget").get(0)).asString())) {
                                    irongolem.setTarget((LivingEntity) entity);
                                    break;
                                }
                            }
                        }
                    }
                }
                manager.getServer().getScheduler().scheduleSyncDelayedTask(this.manager, new Runnable() {
                    public void run() {
                        irongolem.damage(1000.0D);
                    }
                }, 300L);
            }
        }
        for (Wolf wolf : Bukkit.getWorld(manager.getConfig().getString("enableworld")).getEntitiesByClass(Wolf.class)) {
            if (wolf.hasMetadata("tamedwolf")) {
                if (wolf.hasMetadata("wolfowner")) {
                    String owner = ((MetadataValue) wolf.getMetadata("wolfowner").get(0)).asString();
                    for (Entity enemy : wolf.getNearbyEntities(10.0D, 10.0D, 10.0D)) {
                        if ((enemy instanceof LivingEntity)) {
                            if ((enemy instanceof Player)) {
                                if (!((Player) enemy).getUniqueId().toString().contains(owner)) {
                                    wolf.setTarget((LivingEntity) enemy);
                                    break;
                                }
                            } else if (!(enemy instanceof Wolf)) {
                                wolf.setTarget((LivingEntity) enemy);
                                break;
                            }
                        }
                    }
                }
            }
        }
        for (Ocelot cat : Bukkit.getWorld(manager.getConfig().getString("enableworld")).getEntitiesByClass(Ocelot.class)) {
            if (cat.hasMetadata("tamedcat")) {
                if (cat.hasMetadata("catowner")) {
                    String owner = ((MetadataValue) cat.getMetadata("catowner").get(0)).asString();
                    for (Entity enemy : cat.getNearbyEntities(10.0D, 10.0D, 10.0D)) {
                        if ((enemy instanceof LivingEntity)) {
                            if ((enemy instanceof Player)) {
                                if (!((Player) enemy).getUniqueId().toString().contains(owner)) {
                                    cat.setTarget((LivingEntity) enemy);
                                    cat.teleport((LivingEntity) enemy);
                                    break;
                                }
                            } else if (!(enemy instanceof Ocelot)) {
                                cat.setTarget((LivingEntity) enemy);
                                cat.teleport((LivingEntity) enemy);
                                break;
                            }
                        }
                    }
                }
            }
        }
        manager.saveConfig();
        return null;
    }

    public void run() {
        // TODO 自動生成されたメソッド・スタブ

    }
}
