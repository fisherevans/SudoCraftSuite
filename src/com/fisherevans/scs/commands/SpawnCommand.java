package com.fisherevans.scs.commands;

import com.fisherevans.scs.SudoCraftSuite;
import com.fisherevans.scs.util.BasicLocation;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by h13730 on 11/2/2015.
 */
public class SpawnCommand extends SudoCommand {
  public SpawnCommand(SudoCraftSuite parentPlugin) {
    super(parentPlugin);
  }

  @Override
  public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
    Player player = getPlayer(commandSender);
    if(player == null)
      return false;
    String name;
    if(args.length == 0)
      name = "world";
    else
      name = args[0];
    World world = Bukkit.getWorld(name);
    if(world == null)
      player.sendMessage(ChatColor.DARK_GRAY + "There is no world named " + ChatColor.BLUE + name);
    else
      teleport(BasicLocation.fromBukkitLocation(world.getSpawnLocation()), player);
    return true;
  }
}
