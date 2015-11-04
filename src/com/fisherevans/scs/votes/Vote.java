package com.fisherevans.scs.votes;

import com.fisherevans.scs.SudoCraftSuite;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;

import java.util.*;

/**
 * Created by h13730 on 11/2/2015.
 */
public class Vote {
  private SudoCraftSuite _plugin;
  private VoteType _type;
  private Set<String> _votes;
  private Long _lastVote;

  public Vote(SudoCraftSuite plugin, VoteType type) {
    _plugin = plugin;
    _type = type;
    _votes = new HashSet<>();
    _lastVote = System.currentTimeMillis();
  }

  public boolean vote(String name) {
    if(_votes.contains(name)) {
      Bukkit.getPlayer(name).sendMessage(ChatColor.DARK_GRAY + "You've already voted to change the " + _type.aspect + " to " + ChatColor.BLUE + _type.value);
      return false;
    }
    _lastVote = System.currentTimeMillis();
    _votes.add(name);
    cleanVotes();
    int neededStill = (int)Math.ceil(((double)Bukkit.getOnlinePlayers().size()*_plugin.getCache().getConfig().getVote().getRequiredPercentage())) - _votes.size();
    if(neededStill > 0) {
      _plugin.getServer().broadcastMessage(ChatColor.BLUE + name + ChatColor.DARK_GRAY + " votes for " + ChatColor.BLUE + _type.value + ChatColor.DARK_GRAY + ". Still need " + ChatColor.BLUE.toString() + neededStill + ChatColor.DARK_GRAY + " vote" + (neededStill == 1 ? "" : "s") + ".");
      return false;
    }
    _plugin.getServer().broadcastMessage(ChatColor.BLUE + name + ChatColor.DARK_GRAY + " passed the vote! Changing the " + _type.aspect + " to " + ChatColor.BLUE + _type.value);
    for(World world:Bukkit.getWorlds()) {
      switch(_type) {
        case WeatherSun:
          if(world.getEnvironment().equals(World.Environment.NORMAL)) {
            world.setStorm(false);
            world.setWeatherDuration(0);
          }
          break;
        case WeatherRain:
          if(world.getEnvironment().equals(World.Environment.NORMAL)) {
            world.setStorm(false);
            world.setWeatherDuration(1200 + (int)(Math.random()*1200));
          }
          break;
        case WeatherStorm:
          if(world.getEnvironment().equals(World.Environment.NORMAL)) {
            world.setStorm(true);
            world.setWeatherDuration(1200 + (int)(Math.random()*1200));
          }
          break;
        case TimeDay:
          world.setTime(0);
          break;
        case TimeNight:
          world.setTime(13000L);
          break;
      }
    }
    return true;
  }

  private void cleanVotes() {
    List<String> badVotes = new ArrayList<>();
    for(String name:_votes) {
      if(Bukkit.getPlayer(name) == null)
        badVotes.add(name);
    }
    _votes.removeAll(badVotes);
  }

  public Long getLastVote() {
    return _lastVote;
  }
}
