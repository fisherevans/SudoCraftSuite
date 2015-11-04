package com.fisherevans.scs.listeners;

import com.fisherevans.scs.SudoCraftSuite;
import com.fisherevans.scs.cache.PlayerCache;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

import java.util.Date;

/**
 * Created by h13730 on 11/4/2015.
 */
public class PlayerListener implements Listener {
  private SudoCraftSuite _plugin;

  public PlayerListener(SudoCraftSuite plugin) {
    _plugin = plugin;
  }

  @EventHandler
  public void join(PlayerJoinEvent event) {
    Player player = event.getPlayer();
    PlayerCache playerCache = _plugin.getPlayerCache(player);
    playerCache.setLastLogin(new Date());
    playerCache.setLastUsername(player.getName());
    playerCache.setLastLoginLocation(player.getLocation());
  }

  @EventHandler
  public void leave(PlayerQuitEvent event) {
    _plugin.getPlayerCache(event.getPlayer()).setLastLogin(new Date());
    _plugin.getPlayerCache(event.getPlayer()).setLastLoginLocation(event.getPlayer().getLocation());
  }

  @EventHandler
  public void teleport(PlayerTeleportEvent event) {
    _plugin.getPlayerCache(event.getPlayer()).setLastTeleportLocation(event.getFrom());
    _plugin.getPlayerCache(event.getPlayer()).setLastLoginLocation(event.getTo());
  }
}
