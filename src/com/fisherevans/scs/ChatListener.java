package com.fisherevans.scs;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by h13730 on 11/2/2015.
 */
public class ChatListener implements Listener {
  private SudoCraftSuite _plugin;
  private Map<String, String> _colorReplacers;

  public ChatListener(SudoCraftSuite plugin) {
    _plugin = plugin;
    _colorReplacers = new HashMap<>();
    for(ChatColor color:ChatColor.values()) {
      _colorReplacers.put("#" + color.name().toLowerCase(), color.toString());
    }
  }

  @EventHandler
  public void onPlayerChat(AsyncPlayerChatEvent event) {
    event.setCancelled(true);
    String message = _plugin.getChatFormat();
    message = message.replace("#name", event.getPlayer().getDisplayName());
    message = message.replace("#message", event.getMessage());
    for(String colorHash:_colorReplacers.keySet())
      message = message.replace(colorHash, _colorReplacers.get(colorHash));
    _plugin.getServer().broadcastMessage(message);
  }
}
