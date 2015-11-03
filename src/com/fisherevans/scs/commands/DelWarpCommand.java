package com.fisherevans.scs.commands;

import com.fisherevans.scs.SudoCraftSuite;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by h13730 on 11/2/2015.
 */
public class DelWarpCommand extends SudoCommand {
  public DelWarpCommand(SudoCraftSuite parentPlugin) {
    super(parentPlugin);
  }

  @Override
  public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
    Player player = getPlayer(commandSender);
    if(player == null)
      return false;
    String name = args.length > 0 ? args[0] : "";
    if(name.length() == 0) {
      player.sendMessage(ChatColor.DARK_GRAY + "Specify a warp to delete, like so: " + ChatColor.BLUE + "/delwarp RonPaul");
    } else if(getPlugin().deleteWarp(name)) {
      player.sendMessage(ChatColor.DARK_GRAY + "The following warp has been deleted: " + ChatColor.BLUE + name);
    } else {
      player.sendMessage(ChatColor.DARK_GRAY + "There is no such warp, " + ChatColor.BLUE + name + ChatColor.DARK_GRAY + ", to be deleted.");
    }
    return true;
  }
}
