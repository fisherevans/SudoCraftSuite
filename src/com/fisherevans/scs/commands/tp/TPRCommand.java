package com.fisherevans.scs.commands.tp;

import com.fisherevans.scs.SudoCraftSuite;
import com.fisherevans.scs.commands.SudoCommand;
import com.fisherevans.scs.util.CommandUtil;
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
    Player player = requirePlayer(commandSender);
    if(player == null)
      return false;
    String playerName = CommandUtil.getFirstArgument(args, "");
    if(playerName.length() == 0) {
      player.sendMessage(ChatColor.DARK_GRAY + "Please specify a user. Example: " + ChatColor.BLUE + "/tpr RonPaul");
      return true;
    }
    if(playerName.equals(player.getName())) {
      player.sendMessage(ChatColor.DARK_GRAY + "You can't teleport to yourself.");
      return true;
    }
    Player to = Bukkit.getPlayer(playerName);
    if(to == null) {
      player.sendMessage(ChatColor.BLUE + playerName + ChatColor.DARK_GRAY + " is not a player that is logged on.");
      return true;
    }
    getPlugin().addTPRequest(player, to);
    return true;
  }
}
