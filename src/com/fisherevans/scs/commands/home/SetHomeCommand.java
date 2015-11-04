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
public class SetHomeCommand extends SudoCommand {
  public SetHomeCommand(SudoCraftSuite parentPlugin) {
    super(parentPlugin);
  }

  @Override
  public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
    Player player = requirePlayer(commandSender);
    if(player == null)
      return false;
    String homeName = CommandUtil.getHomeArgument(getPlugin(), args);
    getPlugin().getPlayerCache(player).getHomes().put(homeName, player.getLocation().add(0, 1, 0));
    player.sendMessage(ChatColor.BLUE + homeName + ChatColor.DARK_GRAY + " home set!");
    return true;
  }
}
