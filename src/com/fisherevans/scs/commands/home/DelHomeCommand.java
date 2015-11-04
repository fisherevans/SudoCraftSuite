package com.fisherevans.scs.commands.home;

import com.fisherevans.scs.SudoCraftSuite;
import com.fisherevans.scs.commands.SudoCommand;
import com.fisherevans.scs.util.CommandUtil;
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
    Player player = requirePlayer(commandSender);
    if(player == null)
      return false;
    String homeName = CommandUtil.getHomeArgument(getPlugin(), args);
    if(getPlugin().getPlayer(player).getHomes().remove(homeName) == null)
      player.sendMessage(ChatColor.DARK_GRAY + "There is no such home, " + ChatColor.BLUE + homeName + ChatColor.DARK_GRAY + ", to be deleted.");
    else
      player.sendMessage(ChatColor.DARK_GRAY + "The following home has been deleted: " + ChatColor.BLUE + homeName);
    return true;
  }
}
