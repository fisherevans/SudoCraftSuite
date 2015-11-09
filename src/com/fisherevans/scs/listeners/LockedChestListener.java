package com.fisherevans.scs.listeners;

import com.fisherevans.scs.SudoCraftSuite;
import com.fisherevans.scs.cache.LockedChestCache;
import com.fisherevans.scs.cache.PlayerCache;
import com.fisherevans.scs.util.ChestUtil;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Chest;
import org.bukkit.block.DoubleChest;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.PlayerInteractEvent;

/**
 * Created by h13730 on 11/6/2015.
 */
public class LockedChestListener implements Listener {
  private SudoCraftSuite _plugin;

  public LockedChestListener(SudoCraftSuite plugin) {
    _plugin = plugin;
  }

  @EventHandler
  public void onInventoryOpenEvent(InventoryOpenEvent e){
    if(e == null || e.getInventory() == null || e.getInventory().getHolder() == null)
      return;
    Location location = null;
    if(e.getInventory().getHolder().getClass() == Chest.class)
      location = ((Chest) e.getInventory().getHolder()).getLocation();
    if(e.getInventory().getHolder().getClass() == DoubleChest.class)
      location = ((DoubleChest) e.getInventory().getHolder()).getLocation();
    if(location == null)
      return;
    LockedChestCache chestCache = _plugin.getLockedChestCache(location);
    if(chestCache == null)
      return;
    if(!chestCache.canAccess(e.getPlayer().getUniqueId().toString(), e.getPlayer().getName())) { // can't access
      PlayerCache owner = ChestUtil.getOwnerAndValidate(_plugin, chestCache, location);
      if(owner != null) {
        e.getPlayer().sendMessage(ChatColor.DARK_GRAY + "You " + ChatColor.RED + "DO NOT "
            + ChatColor.DARK_GRAY + " have access to this chest. It's owned by "
            + ChatColor.BLUE + owner.getLastUsername() + ChatColor.DARK_GRAY + ".");
        e.setCancelled(true);
      }
    }
  }

  @EventHandler
  public void onInteractEvent(PlayerInteractEvent e) {
    if(e.getAction() != Action.LEFT_CLICK_BLOCK
        || !e.getPlayer().getItemInHand().getType().equals(Material.STICK)
        || !e.getClickedBlock().getType().equals(Material.CHEST))
      return;
    // now it's a chest, being HIT by a player, with a stick.
    ChestUtil.toggleChest(_plugin, e.getClickedBlock().getLocation(), e.getPlayer());
  }

  @EventHandler
  public void breakEvent(BlockBreakEvent e) {
    if(!e.getBlock().getType().equals(Material.CHEST))
      return;
    Location blockLocation = e.getBlock().getLocation();
    LockedChestCache chest = _plugin.getLockedChestCache(blockLocation);
    Player player = e.getPlayer();
    String uuid = player.getUniqueId().toString();
    if(chest == null)
      return;
    Location secondLocation = ChestUtil.getSecondLocation(blockLocation);
    PlayerCache owner = ChestUtil.getOwnerAndValidate(_plugin, chest, blockLocation);
    if(owner == null)
      return;
    if(!chest.getOwnerUUID().equals(uuid)) {
      ChestUtil.sendNotOwner(player, owner.getLastUsername());
      e.setCancelled(true);
      return;
    }
    _plugin.removeLockedChestCache(blockLocation);
    if(secondLocation == null) {
      player.sendMessage(ChatColor.DARK_GRAY + "This chest has been unlocked and destroyed.");
    } else if(chest.getLocation().equals(blockLocation)) {
      chest.setLocation(secondLocation);
      if(!_plugin.setLockedChestCache(chest))
        ChestUtil.sendError(player);
    }
  }
}
