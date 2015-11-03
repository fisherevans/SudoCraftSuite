package com.fisherevans.scs;

import com.fisherevans.scs.commands.*;
import com.fisherevans.scs.util.BasicLocation;
import com.fisherevans.scs.util.ConfigUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by h13730 on 11/2/2015.
 */
public class SudoCraftSuite extends JavaPlugin {
  private Map<String, BasicLocation> _playerBacks;

  private Map<String, Map<String, BasicLocation>> _playerHomes;
  private String _defaultHomeName;

  private Map<String, Map<String, Long>> _tpRequests;
  private Integer _tpTimeout;

  private ChatListener _chatListener = new ChatListener(this);
  private String _chatFormat;

  private Map<Vote.Type, Vote> _votes;
  private Integer _voteTimeout;
  private Double _voteRequired;

  private Map<String, BasicLocation> _warps;

  private DiamondBlockListener _diamondListener = new DiamondBlockListener(this);
  private Integer _diamondDelay;

  @Override
  public void onEnable() {
    getCommand("sethome").setExecutor(new SetHomeCommand(this));
    getCommand("delhome").setExecutor(new DelHomeCommand(this));
    getCommand("home").setExecutor(new HomeCommand(this));
    getCommand("homes").setExecutor(new HomesCommand(this));

    getCommand("back").setExecutor(new BackCommand(this));

    getCommand("tpr").setExecutor(new TPRCommand(this));
    getCommand("tpa").setExecutor(new TPACommand(this));

    getCommand("vote").setExecutor(new VoteCommand(this));

    getCommand("builder").setExecutor(new BuilderCommand(this));

    getCommand("spawn").setExecutor(new SpawnCommand(this));
    getCommand("setspawn").setExecutor(new SetSpawnCommand(this));

    getCommand("delwarp").setExecutor(new DelWarpCommand(this));
    getCommand("setwarp").setExecutor(new SetWarpCommand(this));
    getCommand("warps").setExecutor(new WarpsCommand(this));
    getCommand("warp").setExecutor(new WarpCommand(this));

    _playerBacks = new HashMap<>();
    _playerHomes = new HashMap<>();
    _tpRequests = new HashMap<>();
    _votes = new HashMap<>();
    _warps = new HashMap<>();

    File homeFile = new File(Constants.CONFIG_FILE);
    if(homeFile.exists()) {
      YamlConfiguration config = YamlConfiguration.loadConfiguration(homeFile);
      ConfigUtil.loadMultiLocationMap(config.getConfigurationSection(Constants.CONFIG_KEY_HOMES), _playerHomes);
      ConfigUtil.loadLocationMap(config.getConfigurationSection(Constants.CONFIG_KEY_BACKS), _playerBacks);
      ConfigUtil.loadLocationMap(config.getConfigurationSection(Constants.CONFIG_KEY_WARPS), _warps);
      _tpTimeout = config.getInt(Constants.CONFIG_KEY_TP_TIMEOUT);
      _defaultHomeName = config.getString(Constants.CONFIG_KEY_HOMES_DEFAULT_NAME);
      _chatFormat = config.getString(Constants.CONFIG_CHAT_FORMAT);
      _voteRequired = config.getDouble(Constants.CONFIG_VOTE_REQUIRED);
      _voteTimeout = config.getInt(Constants.CONFIG_VOTE_TIMEOUT);
      _diamondDelay = config.getInt(Constants.CONFIG_DIAMOND_DELAY);
    }

    if(_tpTimeout == null) _tpTimeout = Constants.DEFAULT_TP_TIMEOUT;
    if(_defaultHomeName == null) _defaultHomeName = Constants.DEFAULT_HOMES_NAME;
    if(_chatFormat == null) _chatFormat = Constants.DEFAULT_CHAT_FORMAT;
    if(_voteRequired == null) _voteRequired = Constants.DEFAULT_VOTE_REQUIRED;
    if(_voteTimeout == null) _voteTimeout = Constants.DEFAULT_VOTE_TIMEOUT;
    if(_diamondDelay == null) _diamondDelay = Constants.DEFAULT_DIAMOND_DELAY;

    Bukkit.getPluginManager().registerEvents(_chatListener, this);
    Bukkit.getPluginManager().registerEvents(_diamondListener, this);
  }

  @Override
  public void onDisable() {
    File homeFile = new File(Constants.CONFIG_FILE);
    YamlConfiguration config = YamlConfiguration.loadConfiguration(homeFile);

    ConfigUtil.saveMultiLocationMap(config.createSection(Constants.CONFIG_KEY_HOMES), _playerHomes);
    ConfigUtil.saveLocationMap(config.createSection(Constants.CONFIG_KEY_BACKS), _playerBacks);
    ConfigUtil.saveLocationMap(config.createSection(Constants.CONFIG_KEY_WARPS), _warps);

    config.set(Constants.CONFIG_KEY_TP_TIMEOUT, _tpTimeout);
    config.set(Constants.CONFIG_KEY_HOMES_DEFAULT_NAME, _defaultHomeName);
    config.set(Constants.CONFIG_CHAT_FORMAT, _chatFormat);
    config.set(Constants.CONFIG_VOTE_REQUIRED, _voteRequired);
    config.set(Constants.CONFIG_VOTE_TIMEOUT, _voteTimeout);
    config.set(Constants.CONFIG_DIAMOND_DELAY, _diamondDelay);
    try {
      config.save(homeFile);
    } catch (IOException e) {
      getLogger().severe("Failed to save config! " + e.getLocalizedMessage());
    }
  }

  public boolean deletePlayerHome(String name, String home) {
    if(!_playerHomes.containsKey(name))
      return false;
    return _playerHomes.get(name).remove(home) != null;
  }

  public BasicLocation getPlayerHome(String name, String home) {
    if(!_playerHomes.containsKey(name))
      return null;
    return _playerHomes.get(name).get(home);
  }

  public Map<String, BasicLocation> getPlayerHomes(String name) {
    return _playerHomes.get(name);
  }

  public void setPlayerHome(String name, String home, BasicLocation location) {
    if(!_playerHomes.containsKey(name))
      _playerHomes.put(name, new HashMap<String, BasicLocation>());
    _playerHomes.get(name).put(home, location);
  }

  public BasicLocation getPlayerBack(String username) {
    return _playerBacks.get(username);
  }

  public void setPlayerBack(String username, BasicLocation location) {
    _playerBacks.put(username, location);
  }

  public boolean deleteWarp(String name) {
    return _warps.remove(name) != null;
  }

  public BasicLocation getWarp(String name) {
    return _warps.get(name);
  }

  public void setWarp(String name, BasicLocation location) {
    _warps.put(name, location);
  }

  public Map<String, BasicLocation> getWarps() {
    return _warps;
  }

  public void addTPRequest(Player from, Player to) {
    if (!_tpRequests.containsKey(from.getName()))
      _tpRequests.put(from.getName(), new HashMap<String, Long>());
    _tpRequests.get(from.getName()).put(to.getName(), System.currentTimeMillis());
    to.sendMessage(ChatColor.BLUE + from.getName() + ChatColor.DARK_GRAY + " has requested to teleport to your location.");
    to.sendMessage(ChatColor.DARK_GRAY + "Use " + ChatColor.BLUE + "/tpa " + from.getName() + ChatColor.DARK_GRAY + " to accept, or " + ChatColor.BLUE + "/tpr " + from.getName() + ChatColor.DARK_GRAY + " to reject.");
    from.sendMessage(ChatColor.DARK_GRAY + "Your teleport request has been sent to " + ChatColor.BLUE + to.getName());
  }

  public boolean acceptTPRequest(Player from, Player to) {
    if(!_tpRequests.containsKey(from.getName())) return false;
    Long requestTime = _tpRequests.get(from.getName()).remove(to.getName());
    if(requestTime == null || System.currentTimeMillis() - requestTime < _tpTimeout*1000) return false;
    from.sendMessage(ChatColor.BLUE + to.getName() + ChatColor.DARK_GRAY + " has accepted your teleport request.");
    to.sendMessage(ChatColor.BLUE + from.getName() + ChatColor.DARK_GRAY + " has been teleported to your location.");
    from.teleport(to.getLocation());
    return true;
  }

  public void vote(Vote.Type type, String name) {
    Vote vote = _votes.get(type);
    if(vote == null || System.currentTimeMillis() - vote.getLastVote() > _voteTimeout*1000) {
      vote = new Vote(this, type);
      _votes.put(type, vote);
    }
    if(vote.vote(name))
      _votes.remove(type);
  }

  public Integer getTPTimeout() {
    return _tpTimeout;
  }

  public String getDefaultHomeName() {
    return _defaultHomeName;
  }

  public String getChatFormat() {
    return _chatFormat;
  }

  public Double getVoteRequired() {
    return _voteRequired;
  }

  public Integer getDiamondDelay() {
    return _diamondDelay;
  }
}
