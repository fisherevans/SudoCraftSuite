package com.fisherevans.scs.commands.home;

import com.fisherevans.scs.SudoCraftSuite;
import com.fisherevans.scs.commands.SudoCommand;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Map;

/**
 * Created by h13730 on 11/2/2015.
 */
public class HomesCommand extends SudoCommand {
  public HomesCommand(SudoCraftSuite parentPlugin) {
    super(parentPlugin);
  }

  @Override
  public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
    Player player = requirePlayer(commandSender);
    if(player == null)
      return false;
    Map<String, Location> homes = getPlugin().getPlayerCache(player).getHomes();
    if(homes == null || homes.size() == 0) {
      player.sendMessage(ChatColor.DARK_GRAY + "Your don't have any saved homes");
    } else {
      player.sendMessage(ChatColor.DARK_GRAY + "Your currently set homes:");
      for(String home:homes.keySet()) {
        String message = ChatColor.DARK_GRAY + " - " + ChatColor.BLUE + home + ChatColor.DARK_GRAY;
        Location location = homes.get(home);
        message += String.format(" X:%.1f, Y:%.1f, Z:%.1f, World:%s", location.getX(), location.getY(), location.getZ(), location.getWorld().getName());
        player.sendMessage(message);
      }
    }
    return true;
  }
}
