package com.fisherevans.scs.commands;

import com.fisherevans.scs.SudoCraftSuite;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by h13730 on 11/2/2015.
 */
public class WarpCommand extends SudoCommand {
  public WarpCommand(SudoCraftSuite parentPlugin) {
    super(parentPlugin);
  }

  @Override
  public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
    Player player = getPlayer(commandSender);
    if(player == null)
      return false;
    String name = args.length > 0 ? args[0] : getPlugin().getDefaultHomeName();
    if(!teleport(getPlugin().getWarp(name), player)) {
      player.sendMessage(ChatColor.DARK_GRAY + "There is no warp called " + ChatColor.BLUE + name);
      return true;
    }
    return true;
  }
}
