package com.fisherevans.scs.commands.home;

import com.fisherevans.scs.SudoCraftSuite;
import com.fisherevans.scs.commands.SudoCommand;
import com.fisherevans.scs.util.BukkitUtil;
import com.fisherevans.scs.util.CommandUtil;
import org.bukkit.ChatColor;
import org.bukkit.Location;
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
    Player player = requirePlayer(commandSender);
    if(player == null)
      return false;
    String homeName = CommandUtil.getHomeArgument(getPlugin(), args);
    Location home = getPlugin().getPlayer(player).getHomes().get(homeName);
    if(BukkitUtil.successfulTeleport(getPlugin(), player, home) == false)
      player.sendMessage(ChatColor.DARK_GRAY + "There is no home set for " + ChatColor.BLUE + home);
    return true;
  }
}
