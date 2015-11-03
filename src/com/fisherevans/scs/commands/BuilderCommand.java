package com.fisherevans.scs.commands;

import com.fisherevans.scs.SudoCraftSuite;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;
import ru.tehkode.permissions.bukkit.PermissionsEx;

/**
 * Created by h13730 on 11/3/2015.
 */
public class BuilderCommand extends SudoCommand {
  public BuilderCommand(SudoCraftSuite parentPlugin) {
    super(parentPlugin);
  }

  @Override
  public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
    if (args.length == 0) {
      commandSender.sendMessage(ChatColor.DARK_GRAY + "Please specify a user. Example: " + ChatColor.BLUE + "/builder RonPaul");
      return true;
    }
    String builderUser = args[0];
    if (builderUser.equals(commandSender.getName())) {
      commandSender.sendMessage(ChatColor.DARK_GRAY + "You can't 'builder' yourself.");
      return true;
    }
    Player builder = Bukkit.getPlayer(builderUser);
    if (builder == null) {
      commandSender.sendMessage(ChatColor.BLUE + builderUser + ChatColor.DARK_GRAY + " is not a player that is logged on.");
      return true;
    }
    try {
      if(PermissionsEx.getUser(builder).inGroup("guest")) {
        PermissionsEx.getUser(builder).setGroups(new String[]{"builder"});
        commandSender.sendMessage(ChatColor.BLUE + builderUser + ChatColor.DARK_GRAY + " has been made a builder");
        builder.sendMessage(ChatColor.BLUE + commandSender.getName() + ChatColor.DARK_GRAY + " has made you a builder");
      } else {
        commandSender.sendMessage(ChatColor.BLUE + builderUser + ChatColor.DARK_GRAY + " is not a guest");
      }
    } catch(Exception e) {
      commandSender.sendMessage(ChatColor.DARK_GRAY + "There was an error while setting " + builderUser + "'s group");
      getPlugin().getLogger().warning("Failed to set builder status for " + builderUser + " - " + e.getLocalizedMessage());
    }
    return true;
  }
}
