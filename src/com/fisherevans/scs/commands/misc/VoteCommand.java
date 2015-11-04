package com.fisherevans.scs.commands.misc;

import com.fisherevans.scs.SudoCraftSuite;
import com.fisherevans.scs.commands.SudoCommand;
import com.fisherevans.scs.votes.Vote;
import com.fisherevans.scs.votes.VoteType;
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
    Player player = requirePlayer(commandSender);
    if(player == null)
      return false;
    if(args.length == 0) {
      player.sendMessage(BAD_ARG_MSG);
      return true;
    }
    VoteType type;
    if(args[0].equalsIgnoreCase("sun")) type = VoteType.WeatherSun;
    else if(args[0].equalsIgnoreCase("rain")) type = VoteType.WeatherRain;
    else if(args[0].equalsIgnoreCase("storm")) type = VoteType.WeatherStorm;
    else if(args[0].equalsIgnoreCase("day")) type = VoteType.TimeDay;
    else if(args[0].equalsIgnoreCase("night")) type = VoteType.TimeNight;
    else {
      player.sendMessage(BAD_ARG_MSG);
      return true;
    }
    getPlugin().vote(type, player.getName());
    return true;
  }
}
