package com.fisherevans.scs.commands.tp;

import com.fisherevans.scs.SudoCraftSuite;
import com.fisherevans.scs.commands.SudoCommand;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by h13730 on 11/2/2015.
 */
public class TPACommand extends SudoCommand {
  public TPACommand(SudoCraftSuite plugin) {
    super(plugin);
  }

  @Override
  public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
    Player player = requirePlayer(commandSender);
    if(player == null)
      return false;
    if(args.length == 0) {
      player.sendMessage(ChatColor.DARK_GRAY + "Please specify a user. Example: " + ChatColor.BLUE + "/tpa RonPaul");
      return true;
    }
    Player from = Bukkit.getPlayer(args[0]);
    if(from == null) {
      player.sendMessage(ChatColor.BLUE + from.getName() + ChatColor.DARK_GRAY + " is not a player that is logged on.");
      return true;
    }
    if(!getPlugin().acceptTPRequest(from, player)) {
      player.sendMessage(ChatColor.DARK_GRAY + "There is no active successfulTeleport request from " + ChatColor.BLUE + from.getName());
    }
    return true;
  }
}
