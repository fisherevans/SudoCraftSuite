package com.fisherevans.scs.util;

import com.fisherevans.scs.SudoCraftSuite;
import org.bukkit.Location;
import org.bukkit.entity.Player;

/**
 * Created by h13730 on 11/4/2015.
 */
public class BukkitUtil {
  public static boolean successfulTeleport(SudoCraftSuite plugin, Player player, Location location) {
    if(location == null)
      return false;
    player.teleport(location);
    return true;
  }
}
