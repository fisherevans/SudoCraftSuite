package com.fisherevans.scs.commands.warp;

import com.fisherevans.scs.SudoCraftSuite;
import com.fisherevans.scs.commands.SudoCommand;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

/**
 * Created by h13730 on 11/2/2015.
 */
public class DelWarpCommand extends SudoCommand {
  public DelWarpCommand(SudoCraftSuite parentPlugin) {
    super(parentPlugin);
  }

  @Override
  public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
    String name = args.length > 0 ? args[0] : "";
    if(name.length() == 0)
      commandSender.sendMessage(ChatColor.DARK_GRAY + "Specify a warp to delete, like so: " + ChatColor.BLUE + "/delwarp RonPaul");
    else if(getPlugin().getCache().getWarps().remove(name) == null)
      commandSender.sendMessage(ChatColor.DARK_GRAY + "There is no such warp, " + ChatColor.BLUE + name + ChatColor.DARK_GRAY + ", to be deleted.");
    else
      commandSender.sendMessage(ChatColor.DARK_GRAY + "The following warp has been deleted: " + ChatColor.BLUE + name);
    return true;
  }
}
