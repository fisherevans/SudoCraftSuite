package com.fisherevans.scs.commands.warp;

import com.fisherevans.scs.SudoCraftSuite;
import com.fisherevans.scs.commands.SudoCommand;
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
    Player player = requirePlayer(commandSender);
    if(player == null)
      return false;
    String warpName = args.length > 0 ? args[0] : "";
    if(warpName.length() == 0) {
      player.sendMessage(ChatColor.DARK_GRAY + "Specify a warp name, like so: " + ChatColor.BLUE + "/setwarp RonPaul");
    } else {
      getPlugin().getCache().getWarps().put(warpName, player.getLocation().add(0, 1, 0));
      player.sendMessage(ChatColor.BLUE + warpName + ChatColor.DARK_GRAY + " warp set!");
    }
    return true;
  }
}
