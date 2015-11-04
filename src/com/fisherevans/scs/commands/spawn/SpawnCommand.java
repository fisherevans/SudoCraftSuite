package com.fisherevans.scs.commands.spawn;

import com.fisherevans.scs.SudoCraftSuite;
import com.fisherevans.scs.commands.SudoCommand;
import com.fisherevans.scs.util.BukkitUtil;
import com.fisherevans.scs.util.CommandUtil;
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
    Player player = requirePlayer(commandSender);
    String worldName = CommandUtil.getFirstArgument(args, "world");
    World world = Bukkit.getWorld(worldName);
    if(world == null)
      player.sendMessage(ChatColor.DARK_GRAY + "There is no world named " + ChatColor.BLUE + worldName);
    else
      BukkitUtil.successfulTeleport(getPlugin(), player, world.getSpawnLocation());
    return true;
  }
}
