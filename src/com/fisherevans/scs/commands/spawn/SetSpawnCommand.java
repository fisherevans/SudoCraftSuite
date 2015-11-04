package com.fisherevans.scs.commands.spawn;

import com.fisherevans.scs.SudoCraftSuite;
import com.fisherevans.scs.commands.SudoCommand;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by h13730 on 11/2/2015.
 */
public class SetSpawnCommand extends SudoCommand {
  public SetSpawnCommand(SudoCraftSuite parentPlugin) {
    super(parentPlugin);
  }

  @Override
  public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
    Player player = requirePlayer(commandSender);
    if(player == null)
      return false;
    Location location = player.getLocation().add(0, 1, 0);
    player.getWorld().setSpawnLocation(location.getBlockX(), location.getBlockY(), location.getBlockZ());
    player.sendMessage(ChatColor.DARK_GRAY + "Spawn has been set to " + location.getBlockX() + ", " + location.getBlockY() + ", " + location.getBlockZ());
    return true;
  }
}
