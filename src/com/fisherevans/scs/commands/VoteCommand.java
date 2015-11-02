package com.fisherevans.scs.commands;

import com.fisherevans.scs.SudoCraftSuite;
import com.fisherevans.scs.Vote;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by h13730 on 11/2/2015.
 */
public class VoteCommand extends SudoCommand {
  private static final String BAD_ARG_MSG = ChatColor.DARK_GRAY + "Please supply a valid vote type. Usage: " + ChatColor.BLUE + "/vote [sun|rain|storm|day|night]";

  public VoteCommand(SudoCraftSuite plugin) {
    super(plugin);
  }

  @Override
  public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
    Player player = getPlayer(commandSender);
    if(player == null)
      return false;
    if(args.length == 0) {
      player.sendMessage(BAD_ARG_MSG);
      return true;
    }
    Vote.Type type;
    if(args[0].equalsIgnoreCase("sun")) type = Vote.Type.WeatherSun;
    else if(args[0].equalsIgnoreCase("rain")) type = Vote.Type.WeatherRain;
    else if(args[0].equalsIgnoreCase("storm")) type = Vote.Type.WeatherStorm;
    else if(args[0].equalsIgnoreCase("day")) type = Vote.Type.TimeDay;
    else if(args[0].equalsIgnoreCase("night")) type = Vote.Type.TimeNight;
    else {
      player.sendMessage(BAD_ARG_MSG);
      return true;
    }
    getPlugin().vote(type, player.getName());
    return true;
  }
}
