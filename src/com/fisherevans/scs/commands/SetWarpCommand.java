package com.fisherevans.scs.commands;

import com.fisherevans.scs.SudoCraftSuite;
import com.fisherevans.scs.util.BasicLocation;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by h13730 on 11/2/2015.
 */
public class SetWarpCommand extends SudoCommand {
  public SetWarpCommand(SudoCraftSuite parentPlugin) {
    super(parentPlugin);
  }

  @Override
  public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
    Player player = getPlayer(commandSender);
    if(player == null)
      return false;
    String name = args.length > 0 ? args[0] : "";
    if(name.length() == 0) {
      player.sendMessage(ChatColor.DARK_GRAY + "Specify a warp name, like so: " + ChatColor.BLUE + "/setwarp RonPaul");
    } else {
      getPlugin().setWarp(name, BasicLocation.fromPlayer(player));
      player.sendMessage(ChatColor.BLUE + name + ChatColor.DARK_GRAY + " warp set!");
    }
    return true;
  }
}
