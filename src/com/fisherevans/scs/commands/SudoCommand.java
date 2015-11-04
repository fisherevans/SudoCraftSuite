package com.fisherevans.scs.commands;

import com.fisherevans.scs.SudoCraftSuite;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by h13730 on 11/2/2015.
 */
public abstract class SudoCommand implements CommandExecutor {
  private SudoCraftSuite _plugin;

  public SudoCommand(SudoCraftSuite plugin) {
    _plugin = plugin;
  }

  public Player requirePlayer(CommandSender sender) {
    if(sender instanceof Player)
      return (Player) sender;
    else {
      sender.sendMessage("Only players can use this command!");
      return null;
    }
  }

  public SudoCraftSuite getPlugin() {
    return _plugin;
  }
}
