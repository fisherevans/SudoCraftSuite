package com.fisherevans.scs.commands;

import com.fisherevans.scs.SudoCraftSuite;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by h13730 on 11/2/2015.
 */
public class TPRCommand extends SudoCommand {
  public TPRCommand(SudoCraftSuite plugin) {
    super(plugin);
  }

  @Override
  public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
    Player player = getPlayer(commandSender);
    if(player == null)
      return false;
    if(args.length == 0) {
      player.sendMessage(ChatColor.DARK_GRAY + "Please specify a user. Example: " + ChatColor.BLUE + "/tpr RonPaul");
      return true;
    }
    String toUser = args[0];
    if(toUser.equals(player.getName())) {
      player.sendMessage(ChatColor.DARK_GRAY + "You can't teleport to yourself.");
      return true;
    }
    Player to = Bukkit.getPlayer(toUser);
    if(to == null) {
      player.sendMessage(ChatColor.BLUE + toUser + ChatColor.DARK_GRAY + " is not a player that is logged on.");
      return true;
    }
    getPlugin().addTPRequest(player, to);
    return true;
  }
}
