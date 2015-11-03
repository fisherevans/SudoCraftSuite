package com.fisherevans.scs;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by h13730 on 11/3/2015.
 */
public class DiamondBlockListener implements Listener {
  private static Map<String, DiamondMessage> messages = new HashMap<>();
  private SudoCraftSuite _plugin;

  public DiamondBlockListener(SudoCraftSuite plugin) {
    _plugin = plugin;
  }

  @EventHandler
  public void onBlockBreak(BlockBreakEvent event) {
    if (event.getBlock().getType() == Material.DIAMOND_ORE) {
      String name = event.getPlayer().getName();
      int count = 1;
      if(messages.containsKey(name)) {
        count += messages.get(name).count;
        messages.get(name).task.cancel();
      }
      DiamondMessage message = new DiamondMessage(count, name);
      message.task = message.runTaskLater(_plugin, _plugin.getDiamondDelay()*20);
      modify(name, message);
    }
  }

  private static synchronized DiamondMessage modify(String name, DiamondMessage message) {
    if(message == null) { // remove
      return messages.remove(name);
    } else { // add
      messages.put(name, message);
      return null;
    }
  }

  public static class DiamondMessage extends BukkitRunnable {
    public final String name;
    public final int count;
    public BukkitTask task = null;

    public DiamondMessage(int count, String name) {
      this.name = name;
      this.count = count;
    }

    @Override
    public void run() {
      modify(name, null);
      String blue = count + " diamond";
      if(count != 1)
        blue += "s";
      Bukkit.getServer().broadcastMessage(ChatColor.DARK_GRAY + name + " found " + ChatColor.AQUA + blue + ChatColor.DARK_GRAY + "!");
    }
  }
}
