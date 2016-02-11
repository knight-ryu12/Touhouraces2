package ml.chromaryu.Touhouraces.races.skill;

/**
 * Created by chroma on 16/02/12.
 */
import java.util.List;

import net.minecraft.server.v1_8_R3.MathHelper;

import ml.chromaryu.Touhouraces.THPlugin;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByBlockEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.Vector;

public class THSkillNNG implements Listener {
    ////アクティブスキル系
    ///移動スキル系
    //仙人の壁抜け
    public static void sennnin_passthough(Player pl, final Plugin plugin, final PlayerToggleSneakEvent event)
    {
        float pitch = pl.getLocation().getPitch();
        float yaw = pl.getLocation().getYaw();
        Location warploc = new Location (pl.getWorld(),pl.getLocation().getX() + pl.getLocation().getDirection().getX() * 2,pl.getLocation().getY() + pl.getLocation().getDirection().getY() * 2,pl.getLocation().getZ() + pl.getLocation().getDirection().getZ() * 2);
        if (pl.getWorld().getBlockAt(warploc).getType() != Material.AIR)
        {
            pl.getWorld().playSound(pl.getLocation(), Sound.ENDERMAN_HIT, 2, 0);
        }
        else
        {
            pl.getWorld().playSound(pl.getLocation(), Sound.ENDERMAN_TELEPORT, 2, 1);
            pl.getWorld().playEffect(pl.getLocation(), Effect.COLOURED_DUST, 1, 5);
            warploc.setPitch(pitch);
            warploc.setYaw(yaw);
            pl.teleport(warploc);
            THPlugin.plugin.getConfig().set("user." + pl.getUniqueId() + ".spilit", THPlugin.plugin.getConfig().getDouble("user." + pl.getUniqueId() + ".spilit") - 20);
            THPlugin.plugin.saveConfig();
        }
    }
    //風魔法
    public static void mazyo_wind(Player pl, final Plugin plugin, final PlayerInteractEvent event){
        pl.sendMessage(THPlugin.thrpre + ChatColor.GREEN + "風の魔法を唱えた！");
        pl.getLocation().getWorld().playSound(pl.getLocation(), Sound.ENDERDRAGON_WINGS, 1.0F, 1.0F);
        pl.setVelocity(pl.getVelocity().add(new Vector(0.5D, 3.0D, 0.5D)));
        pl.setFallDistance(-40.0F);
        MetadataValue usingmagic = new FixedMetadataValue(plugin, Boolean.valueOf(true));
        pl.setMetadata("using-magic", usingmagic);
        plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable(){
            public void run(){
                Player pl = event.getPlayer();
                MetadataValue usingmagic = new FixedMetadataValue(plugin, Boolean.valueOf(false));
                pl.setMetadata("using-magic", usingmagic);
                pl.sendMessage(THPlugin.thrpre + ChatColor.BLUE + "詠唱のクールダウンが終わりました");
            }
        }, 100L);
    }
    ///攻撃スキル系
    //土魔法
    public static void mazyo_dirt(Player pl,final Plugin plugin, final PlayerInteractEvent event){
        pl.sendMessage(THPlugin.thrpre + ChatColor.YELLOW + "土の魔法を唱えた！");
        pl.getLocation().getWorld().playSound(pl.getLocation(), Sound.PISTON_EXTEND, 1.0F, -1.0F);
        List<Entity> enemys = pl.getNearbyEntities(12.0D, 12.0D, 12.0D);
        for (Entity enemy : enemys) {
            if (((enemy instanceof LivingEntity)) && (enemy.isOnGround())){
                ((LivingEntity)enemy).damage(25.0D);
                enemy.getLocation().getWorld().playSound(enemy.getLocation(), Sound.HORSE_HIT, 1.0F, 0.0F);
            }
        }
        MetadataValue usingmagic = new FixedMetadataValue(plugin, Boolean.valueOf(true));
        pl.setMetadata("using-magic", usingmagic);
        plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable(){
            public void run(){
                Player pl = event.getPlayer();
                MetadataValue usingmagic = new FixedMetadataValue(plugin, Boolean.valueOf(false));
                pl.setMetadata("using-magic", usingmagic);
                pl.sendMessage(THPlugin.thrpre + ChatColor.BLUE + "詠唱のクールダウンが終わりました");
            }
        }, 60L);
    }
    //火魔法
    public static void mazyo_fire(Player pl,final Plugin plugin, final PlayerInteractEvent event)
    {
        pl.sendMessage(THPlugin.thrpre + ChatColor.RED + "火の魔法を唱えた！");
        pl.getLocation().getWorld().playSound(pl.getLocation(), Sound.FIRE, 1, 0);
        Location location =pl.getEyeLocation();
        float pitch=location.getPitch() / 180.0F * 3.1415927F;
        float yaw=location.getYaw() / 180.0F * 3.1415927F ;
        double motX=-MathHelper.sin(yaw) * MathHelper.cos(pitch);
        double motZ=MathHelper.cos(yaw) * MathHelper.cos(pitch);
        double motY=-MathHelper.sin(pitch);
        Vector velocity=new Vector(motX,motY,motZ).multiply(2D);
        @SuppressWarnings("deprecation")
        Snowball snowball=pl.throwSnowball();
        MetadataValue shooter = new FixedMetadataValue(THPlugin.plugin, pl.getUniqueId().toString()) ;
        MetadataValue fireeffect = new FixedMetadataValue(THPlugin.plugin, 30D) ;
        snowball.setMetadata("fireffect", fireeffect);
        snowball.setMetadata("mazyo-fireball", shooter);
        snowball.setVelocity(velocity);
        snowball.setFireTicks(300);
        MetadataValue usingmagic = new FixedMetadataValue(THPlugin.plugin, true) ;
        pl.setMetadata("using-magic", usingmagic);
        THPlugin.plugin.getServer().getScheduler().scheduleSyncDelayedTask(THPlugin.plugin, new Runnable()
        {
            public void run()
            {
                Player pl = event.getPlayer();
                MetadataValue usingmagic = new FixedMetadataValue(plugin, false) ;
                pl.setMetadata("using-magic", usingmagic);
                pl.sendMessage(THPlugin.thrpre + ChatColor.BLUE + "詠唱のクールダウンが終わりました");
            }
        }
                , 80L);
    }
    public static void mazyo_thunder(Player pl, final Plugin plugin, final PlayerInteractEvent event)
    {
        pl.sendMessage(THPlugin.thrpre + ChatColor.DARK_PURPLE + "雷の魔法を唱えた！");
        Entity lightning1 = pl.getWorld().spawnEntity(event.getClickedBlock().getLocation().add(4D,0,0), EntityType.LIGHTNING);
        Entity lightning2 = pl.getWorld().spawnEntity(event.getClickedBlock().getLocation().add(-4D,0,0), EntityType.LIGHTNING);
        Entity lightning3 = pl.getWorld().spawnEntity(event.getClickedBlock().getLocation().add(0,0,4D), EntityType.LIGHTNING);
        Entity lightning4 = pl.getWorld().spawnEntity(event.getClickedBlock().getLocation().add(0,0,-4D), EntityType.LIGHTNING);
        MetadataValue lightningeffect = new FixedMetadataValue(THPlugin.plugin, 20D) ;
        MetadataValue shooter = new FixedMetadataValue(THPlugin.plugin, pl.getUniqueId().toString()) ;
        lightning1.setMetadata("lightningeffect", lightningeffect);
        lightning2.setMetadata("lightningeffect", lightningeffect);
        lightning3.setMetadata("lightningeffect", lightningeffect);
        lightning4.setMetadata("lightningeffect", lightningeffect);
        lightning1.setMetadata("shooter", shooter);
        lightning2.setMetadata("shooter", shooter);
        lightning3.setMetadata("shooter", shooter);
        lightning4.setMetadata("shooter", shooter);
        MetadataValue usingmagic = new FixedMetadataValue(THPlugin.plugin, true) ;
        pl.setMetadata("using-magic", usingmagic);
        THPlugin.plugin.getServer().getScheduler().scheduleSyncDelayedTask(THPlugin.plugin, new Runnable()
        {
            public void run()
            {
                Player pl = event.getPlayer();
                MetadataValue usingmagic = new FixedMetadataValue(plugin, false) ;
                pl.setMetadata("using-magic", usingmagic);
                pl.sendMessage(THPlugin.thrpre + ChatColor.BLUE + "詠唱のクールダウンが終わりました");
            }
        }
                , 180L);
    }
    ///回復魔法
    //人間共通・棒で自己回復//
    public static void mazyo_heal(Player pl, final Plugin plugin, final PlayerInteractEvent event)
    {
        MetadataValue casting = new FixedMetadataValue(plugin, true) ;
        pl.setMetadata("casting", casting);
        pl.sendMessage(THPlugin.thrpre + ChatColor.RED + "棒を構えた！");
        pl.getWorld().playSound(pl.getLocation(), Sound.ANVIL_LAND, 1, 1);
        pl.getWorld().playEffect(pl.getLocation(), Effect.WITCH_MAGIC, 1, 1);
        plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable()
        {
            public void run()
            {
                Player pl = event.getPlayer() ;
                pl.sendMessage(THPlugin.thrpre + ChatColor.YELLOW + "自己治癒を使った！");
                pl.getLocation().getWorld().playSound(pl.getLocation(), Sound.ORB_PICKUP, 1, 1);
                if (pl.getHealth() + 8D > pl.getMaxHealth())
                {
                    pl.setHealth(pl.getMaxHealth());

                }
                else
                {
                    pl.setHealth(pl.getHealth() + 8D);
                }
                pl.getLocation().getWorld().playSound(pl.getLocation(), Sound.LEVEL_UP, 1, 2);
                MetadataValue usingmagic = new FixedMetadataValue(plugin, true) ;
                pl.setMetadata("using-magic", usingmagic);
                plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable()
                {
                    public void run()
                    {
                        Player pl = event.getPlayer();
                        MetadataValue usingmagic = new FixedMetadataValue(plugin, false) ;
                        pl.setMetadata("using-magic", usingmagic);
                        pl.sendMessage(THPlugin.thrpre + ChatColor.BLUE + "詠唱のクールダウンが終わりました");
                    }
                }, 20L);
                MetadataValue casted = new FixedMetadataValue(plugin, false) ;
                pl.setMetadata("casting", casted);
            }
        });
    }
    public static void mazyo_water(Player pl, final Plugin plugin, final PlayerInteractEvent event)
    {
        pl.sendMessage(THPlugin.thrpre + ChatColor.GREEN + "水の魔法を唱えた！");
        pl.getLocation().getWorld().playSound(pl.getLocation(), Sound.MAGMACUBE_JUMP, 1, 0);
        List<Entity> enemys=pl.getNearbyEntities(8D, 8D, 8D);
        enemys.add(pl);
        for (Entity enemy : enemys)
        {
            if (enemy instanceof LivingEntity && enemy.isDead() == false)
            {
                if (((LivingEntity) enemy).getHealth() + 12D > ((LivingEntity) enemy).getMaxHealth())
                {
                    ((LivingEntity) enemy).setHealth(((LivingEntity) enemy).getMaxHealth());
                    enemy.getLocation().getWorld().playSound(enemy.getLocation(), Sound.LEVEL_UP, 1, 2);
                }
                else
                {
                    ((LivingEntity) enemy).setHealth(((LivingEntity) enemy).getHealth() + 12D);
                    enemy.getLocation().getWorld().playSound(enemy.getLocation(), Sound.LEVEL_UP, 1, 2);
                }
            }
        }
        MetadataValue usingmagic = new FixedMetadataValue(THPlugin.plugin, true) ;
        pl.setMetadata("using-magic", usingmagic);
        THPlugin.plugin.getServer().getScheduler().scheduleSyncDelayedTask(THPlugin.plugin, new Runnable()
        {
            public void run()
            {
                Player pl = event.getPlayer();
                MetadataValue usingmagic = new FixedMetadataValue(plugin, false) ;
                pl.setMetadata("using-magic", usingmagic);
                pl.sendMessage(THPlugin.thrpre + ChatColor.BLUE + "詠唱のクールダウンが終わりました");
            }
        }
                , 180L);
    }
    ////パッシブスキル系
    ///エンティティ専用
    public static void sibito_deadattack(Player pl, final Plugin plugin, final EntityDamageByEntityEvent event)
    {
        if (pl.getHealth() <= 20D)
        {
            event.setDamage(event.getDamage() + 3D);
            event.getDamager().getWorld().playSound(pl.getLocation(), Sound.ZOMBIE_PIG_HURT, 1, 1);
            event.getDamager().getWorld().playEffect(event.getEntity().getLocation(), Effect.TILE_BREAK, 49);
        }
    }
    public static void gennzinnsin_luckyattack(Player pl, final Plugin plugin, final EntityDamageByEntityEvent event)
    {
        if (Math.random() > 0.7 && THPlugin.plugin.getConfig().getInt("user." + pl.getUniqueId() + ".spilit") >= 5D)
        {
            THPlugin.plugin.getConfig().set(THPlugin.plugin.getConfig().getString("user." + pl.getUniqueId() + ".spilit"),THPlugin.plugin.getConfig().getInt("user." + pl.getUniqueId() + ".spilit") - 5D);
            event.setDamage(event.getDamage() + 5D);
            pl.getWorld().playSound(pl.getLocation(), Sound.SUCCESSFUL_HIT, 1, 1);
        }
    }
    ///エンティティ・ブロック兼用
    public static void houraizin_reverselife_Entity(Player pl, final Plugin plugin, final EntityDamageByEntityEvent event)
    {
        double reverse = Math.random();
        if (event.getDamage() >= pl.getHealth() && reverse > 0.6)
        {
            THPlugin.plugin.getConfig().set(THPlugin.plugin.getConfig().getString("user." + pl.getUniqueId() + ".spilit"),THPlugin.plugin.getConfig().getInt("user." + pl.getUniqueId() + ".spilit") - 30D);
            pl.setHealth(pl.getMaxHealth());
            pl.sendMessage(THPlugin.thrpre + ChatColor.AQUA + "貴方は不死の力を使い蘇った！！");
            pl.getWorld().playSound(pl.getLocation(), Sound.BLAZE_BREATH, 1, -1);
            event.setDamage(0D);
        }
    }
    public static void houraizin_reverselife_block(Player pl, final Plugin plugin, final EntityDamageByBlockEvent event)
    {
        double reverse = Math.random();
        if (event.getDamage() >= pl.getHealth() && reverse > 0.6)
        {
            THPlugin.plugin.getConfig().set(THPlugin.plugin.getConfig().getString("user." + pl.getUniqueId() + ".spilit"),THPlugin.plugin.getConfig().getInt("user." + pl.getUniqueId() + ".spilit") - 30D);
            pl.setHealth(pl.getMaxHealth());
            pl.sendMessage(THPlugin.thrpre + ChatColor.AQUA + "貴方は不死の力を使い蘇った！！");
            pl.getWorld().playSound(pl.getLocation(), Sound.BLAZE_BREATH, 1, -1);
            event.setDamage(0D);
        }
    }
}

