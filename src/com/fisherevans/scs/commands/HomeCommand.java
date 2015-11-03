package com.fisherevans.scs.commands;

import com.fisherevans.scs.SudoCraftSuite;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by h13730 on 11/2/2015.
 */
public class HomeCommand extends SudoCommand {
  public HomeCommand(SudoCraftSuite parentPlugin) {
    super(parentPlugin);
  }

  @Override
  public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
    Player player = getPlayer(commandSender);
    if(player == null)
      return false;
    String home = args.length > 0 ? args[0] : getPlugin().getDefaultHomeName();
    if(!teleport(getPlugin().getPlayerHome(player.getName(), home), player)) {
      player.sendMessage(ChatColor.DARK_GRAY + "There is no home set for " + ChatColor.BLUE + home);
      return true;
    }
    return true;
  }
}
