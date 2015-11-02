package com.fisherevans.scs.commands;

import com.fisherevans.scs.Constants;
import com.fisherevans.scs.SudoCraftSuite;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by h13730 on 11/2/2015.
 */
public class BackCommand extends SudoCommand {
  public BackCommand(SudoCraftSuite parentPlugin) {
    super(parentPlugin);
  }

  @Override
  public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
    Player player = getPlayer(commandSender);
    if(player == null)
      return false;
    if(!teleport(getPlugin().getPlayerBack(player.getName()), player)) {
      player.sendMessage(ChatColor.DARK_GRAY + "You've never teleported before.");
      return true;
    }
    return true;
  }
}
