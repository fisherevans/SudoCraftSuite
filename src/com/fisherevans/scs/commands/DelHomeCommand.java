package com.fisherevans.scs.commands;

import com.fisherevans.scs.SudoCraftSuite;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by h13730 on 11/2/2015.
 */
public class DelHomeCommand extends SudoCommand {
  public DelHomeCommand(SudoCraftSuite parentPlugin) {
    super(parentPlugin);
  }

  @Override
  public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
    Player player = getPlayer(commandSender);
    if(player == null)
      return false;
    String home = args.length > 0 ? args[0] : getPlugin().getDefaultHomeName();
    if(getPlugin().deletePlayerHome(player.getName(), home)) {
      player.sendMessage(ChatColor.DARK_GRAY + "The following home has been deleted: " + ChatColor.BLUE + home);
    } else {
      player.sendMessage(ChatColor.DARK_GRAY + "There is no such home, " + ChatColor.BLUE + home + ChatColor.DARK_GRAY + ", to be deleted.");
    }
    return true;
  }
}
