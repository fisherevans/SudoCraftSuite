package com.fisherevans.scs.commands.misc;

import com.fisherevans.scs.SudoCraftSuite;
import com.fisherevans.scs.cache.PlayerCache;
import com.fisherevans.scs.commands.SudoCommand;
import com.fisherevans.scs.util.CommandUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.text.SimpleDateFormat;

/**
 * Created by h13730 on 11/4/2015.
 */
public class SeenCommand extends SudoCommand {
  private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("MMM d, K:mma' EST'");

  public SeenCommand(SudoCraftSuite plugin) {
    super(plugin);
  }

  @Override
  public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
    String playerName = CommandUtil.getFirstArgument(args, "");
    if(playerName.length() == 0) {
      commandSender.sendMessage(ChatColor.DARK_GRAY + "Please enter a valid player name");
    }
    for(Player player:Bukkit.getServer().getOnlinePlayers()) {
      if(player.getName().equalsIgnoreCase(playerName)) {
        commandSender.sendMessage(ChatColor.BLUE + playerName + ChatColor.DARK_GRAY + " is online RIGHT NOW!");
        commandSender.sendMessage(ChatColor.DARK_GRAY + "They are located at " +
            String.format("X:%.1f, Y:%.1f, Z:%.1f in " + ChatColor.BLUE + "%s",
                player.getLocation().getX(),
                player.getLocation().getY(),
                player.getLocation().getZ(),
                player.getLocation().getWorld().getName()));
        return true;
      }
    }
    PlayerCache playerCache = null, thisPlayerCache;
    for(String uuid:getPlugin().getCache().getPlayers().keySet()) {
      thisPlayerCache = getPlugin().getCache().getPlayers().get(uuid);
      if(playerName.equalsIgnoreCase(thisPlayerCache.getLastUsername())) {
        playerCache = thisPlayerCache;
        break;
      }
    }
    if(playerCache == null || playerCache.getLastLogin() == null) {
      commandSender.sendMessage(ChatColor.DARK_GRAY + "Sorry, there's no information on the player " + ChatColor.BLUE + playerName);
    } else {
      commandSender.sendMessage(ChatColor.BLUE + playerName + ChatColor.DARK_GRAY + " was last online on " + DATE_FORMAT.format(playerCache.getLastLogin()));
      if(playerCache.getLastLoginLocation() != null) {
        commandSender.sendMessage(ChatColor.DARK_GRAY + "They were located at " +
            String.format("X:%.1f, Y:%.1f, Z:%.1f in " + ChatColor.BLUE + "%s",
                playerCache.getLastLoginLocation().getX(),
                playerCache.getLastLoginLocation().getY(),
                playerCache.getLastLoginLocation().getZ(),
                playerCache.getLastLoginLocation().getWorld().getName()));

      }
    }
    return true;
  }
}
