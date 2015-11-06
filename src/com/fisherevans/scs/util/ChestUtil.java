package com.fisherevans.scs.util;

import com.fisherevans.scs.SudoCraftSuite;
import com.fisherevans.scs.cache.LockedChestCache;
import com.fisherevans.scs.cache.PlayerCache;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

/**
 * Created by h13730 on 11/6/2015.
 */
public class ChestUtil {
  public static void modifyMember(SudoCraftSuite plugin, Location blockLocation, Player player, String member, boolean add) {
    String uuid = player.getUniqueId().toString();
    LockedChestCache chest = plugin.getLockedChestCache(blockLocation);
    PlayerCache owner = chest == null ? null : plugin.getCache().getPlayers().get(chest.getOwnerUUID());
    if(chest == null || owner == null) {
      player.sendMessage(ChatColor.DARK_GRAY + "The chest you're looking at isn't locked.");
    } else if(chest.getOwnerUUID().equals(uuid)) {
      PlayerCache playerCache = plugin.findPlayerCacheByUsername(member);
      if(playerCache == null) {
        player.sendMessage(ChatColor.BLUE + member + ChatColor.DARK_GRAY + " is not a known user on this server.");
        return;
      }
      boolean alreadyMember = chest.getMembers().contains(member);
      if(add) {
        if(alreadyMember)
          player.sendMessage(ChatColor.BLUE + member + ChatColor.DARK_GRAY + " is already a member.");
        else {
          chest.getMembers().add(member);
          player.sendMessage(ChatColor.BLUE + member + ChatColor.DARK_GRAY + " has been added as a member.");
        }
      } else {
        if(alreadyMember) {
          chest.getMembers().remove(member);
          player.sendMessage(ChatColor.BLUE + member + ChatColor.DARK_GRAY + " has been removed from this chest.");
        } else
          player.sendMessage(ChatColor.BLUE + member + ChatColor.DARK_GRAY + " isn't a member of this chest.");
      }
    } else { // not yours
      sendNotOwner(player, owner.getLastUsername());
    }
  }

  public static void toggleChest(SudoCraftSuite plugin, Location blockLocation, Player player) {
    String uuid = player.getUniqueId().toString();
    LockedChestCache chest = plugin.getLockedChestCache(blockLocation);
    PlayerCache owner = chest == null ? null : plugin.getCache().getPlayers().get(chest.getOwnerUUID());
    if(chest == null || owner == null) { // lock it
      lock(plugin, blockLocation, player, uuid);
    } else if(chest.getOwnerUUID().equals(uuid)) { // unlock
      plugin.removeLockedChestCache(blockLocation);
      player.sendMessage(ChatColor.DARK_GRAY + "This chest is now unlocked.");
    } else { // not yours
      sendNotOwner(player, owner.getLastUsername());
    }
  }

  public static PlayerCache getOwnerAndValidate(SudoCraftSuite plugin, LockedChestCache chest, Location location) {
    PlayerCache owner = plugin.getCache().getPlayers().get(chest.getOwnerUUID());
    if(owner == null)
      plugin.removeLockedChestCache(location);
    return owner;
  }

  private static void lock(SudoCraftSuite plugin, Location location, Player player, String uuid) {
    LockedChestCache chest = new LockedChestCache();
    chest.setOwnerUUID(uuid);
    if(plugin.setLockedChestCache(chest))
      player.sendMessage(ChatColor.DARK_GRAY + "This chest is now locked and is owned by you.");
    else
      sendError(player);
  }

  public static Location getSecondLocation(Location firstLocation) {
    Location dx, dz;
    for(int delta = -1;delta <= 1;delta++) {
      dx = new Location(firstLocation.getWorld(), firstLocation.getBlockX() + delta, firstLocation.getBlockY(), firstLocation.getBlockZ());
      if(dx.getBlock().getType().equals(Material.CHEST))
        return dx;
      if(delta == 0)
        continue;
      dz = new Location(firstLocation.getWorld(), firstLocation.getBlockX(), firstLocation.getBlockY(), firstLocation.getBlockZ() + delta);
      if(dz.getBlock().getType().equals(Material.CHEST))
        return dz;
    }
    return null;
  }

  public static void sendNotOwner(Player player, String owner) {
    player.sendMessage(ChatColor.DARK_GRAY + "You " + ChatColor.RED + "DO NOT "
        + ChatColor.DARK_GRAY + " own this chest. It's owned by "
        + ChatColor.BLUE + owner + ChatColor.DARK_GRAY + ".");
  }

  public static void sendError(Player player) {
    player.sendMessage(ChatColor.DARK_GRAY + "There was an " + ChatColor.RED + " error " + ChatColor.DARK_GRAY + "locking this chest!");
  }
}
