package com.fisherevans.scs.commands.warp;

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
public class WarpCommand extends SudoCommand {
  public WarpCommand(SudoCraftSuite parentPlugin) {
    super(parentPlugin);
  }

  @Override
  public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
    Player player = requirePlayer(commandSender);
    if(player == null)
      return false;
    String warpName = CommandUtil.getFirstArgument(args, "");
    Location warp = getPlugin().getCache().getWarps().get(warpName);
    if(BukkitUtil.successfulTeleport(getPlugin(), player, warp))
      player.sendMessage(ChatColor.DARK_GRAY + "There is no warp called " + ChatColor.BLUE + warpName);
    return true;
  }
}
