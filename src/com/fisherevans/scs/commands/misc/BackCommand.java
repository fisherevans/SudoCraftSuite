package com.fisherevans.scs.commands.misc;

import com.fisherevans.scs.SudoCraftSuite;
import com.fisherevans.scs.commands.SudoCommand;
import com.fisherevans.scs.util.BukkitUtil;
import org.bukkit.ChatColor;
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
    Player player = requirePlayer(commandSender);
    if(player == null)
      return false;
    if(BukkitUtil.successfulTeleport(getPlugin(), player, getPlugin().getPlayerCache(player).getLastTeleportLocation()) == false)
      player.sendMessage(ChatColor.DARK_GRAY + "You've never teleported before.");
    return true;
  }
}
