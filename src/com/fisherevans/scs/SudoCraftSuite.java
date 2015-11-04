package com.fisherevans.scs;

import com.fisherevans.scs.cache.Cache;
import com.fisherevans.scs.cache.PlayerCache;
import com.fisherevans.scs.commands.home.DelHomeCommand;
import com.fisherevans.scs.commands.home.HomeCommand;
import com.fisherevans.scs.commands.home.HomesCommand;
import com.fisherevans.scs.commands.home.SetHomeCommand;
import com.fisherevans.scs.commands.misc.BackCommand;
import com.fisherevans.scs.commands.misc.BuilderCommand;
import com.fisherevans.scs.commands.misc.VoteCommand;
import com.fisherevans.scs.commands.spawn.SetSpawnCommand;
import com.fisherevans.scs.commands.spawn.SpawnCommand;
import com.fisherevans.scs.commands.tp.TPACommand;
import com.fisherevans.scs.commands.tp.TPRCommand;
import com.fisherevans.scs.commands.warp.DelWarpCommand;
import com.fisherevans.scs.commands.warp.SetWarpCommand;
import com.fisherevans.scs.commands.warp.WarpCommand;
import com.fisherevans.scs.commands.warp.WarpsCommand;
import com.fisherevans.scs.listeners.ChatListener;
import com.fisherevans.scs.listeners.DiamondBlockListener;
import com.fisherevans.scs.listeners.PlayerListener;
import com.fisherevans.scs.util.BukkitUtil;
import com.fisherevans.scs.votes.Vote;
import com.fisherevans.scs.votes.VoteType;
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
  public static final String CONFIG_FILE = "plugins/SudoCraftSuite.yml";

  private PlayerListener _playerListener = new PlayerListener(this);
  private ChatListener _chatListener = new ChatListener(this);
  private DiamondBlockListener _diamondListener = new DiamondBlockListener(this);

  private Cache _cache;

  private Map<String, Map<String, Long>> _tpRequests;
  private Map<VoteType, Vote> _votes;

  @Override
  public void onEnable() {
    loadCommands();

    _cache = new Cache();

    _tpRequests = new HashMap<>();
    _votes = new HashMap<>();

    File homeFile = new File(CONFIG_FILE);
    if(homeFile.exists())
      _cache.load(YamlConfiguration.loadConfiguration(homeFile));

    Bukkit.getPluginManager().registerEvents(_playerListener, this);
    Bukkit.getPluginManager().registerEvents(_chatListener, this);
    Bukkit.getPluginManager().registerEvents(_diamondListener, this);

    getLogger().info(_cache.getPlayers().size() + " Players loaded.");
    getLogger().info(_cache.getWarps().size() + " Warps loaded.");
  }

  private void loadCommands() {
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
  }

  @Override
  public void onDisable() {
    File homeFile = new File(CONFIG_FILE);
    YamlConfiguration config = YamlConfiguration.loadConfiguration(homeFile);
    _cache.save(config);
    try {
      config.save(homeFile);
    } catch (IOException e) {
      getLogger().severe("Failed to save config! " + e.getLocalizedMessage());
    }
  }

  public Cache getCache() {
    return _cache;
  }

  public PlayerCache getPlayer(Player player) {
    String uuid = player.getUniqueId().toString();
    if(!_cache.getPlayers().containsKey(uuid)) {
      String name = player.getName();
      if(_cache.getPlayers().containsKey(name)) {
        PlayerCache playerCache = _cache.getPlayers().remove(name);
        playerCache.setUUID(uuid);
        playerCache.setLastUsername(name);
        _cache.getPlayers().put(uuid, playerCache);
      } else {
        _cache.getPlayers().put(uuid, new PlayerCache(uuid));
      }
    }
    return _cache.getPlayers().get(uuid);
  }

  public void addTPRequest(Player from, Player to) {
    if (!_tpRequests.containsKey(from.getName()))
      _tpRequests.put(from.getName(), new HashMap<String, Long>());
    _tpRequests.get(from.getName()).put(to.getName(), System.currentTimeMillis());
    to.sendMessage(ChatColor.BLUE + from.getName() + ChatColor.DARK_GRAY + " has requested to successfulTeleport to your location.");
    to.sendMessage(ChatColor.DARK_GRAY + "Use " + ChatColor.BLUE + "/tpa " + from.getName() + ChatColor.DARK_GRAY + " to accept.");
    from.sendMessage(ChatColor.DARK_GRAY + "Your successfulTeleport request has been sent to " + ChatColor.BLUE + to.getName());
  }

  public boolean acceptTPRequest(Player from, Player to) {
    if(!_tpRequests.containsKey(from.getName()))
      return false;
    Long requestTime = _tpRequests.get(from.getName()).remove(to.getName());
    if(requestTime == null || System.currentTimeMillis() - requestTime > _cache.getConfig().getTP().getTimeout()*1000)
      return false;
    from.sendMessage(ChatColor.BLUE + to.getName() + ChatColor.DARK_GRAY + " has accepted your successfulTeleport request.");
    to.sendMessage(ChatColor.BLUE + from.getName() + ChatColor.DARK_GRAY + " has been teleported to your location.");
    BukkitUtil.successfulTeleport(this, from, to.getLocation());
    return true;
  }

  public void vote(VoteType type, String name) {
    Vote vote = _votes.get(type);
    if(vote == null || System.currentTimeMillis() - vote.getLastVote() > _cache.getConfig().getVote().getTimeout()*1000) {
      vote = new Vote(this, type);
      _votes.put(type, vote);
    }
    if(vote.vote(name))
      _votes.remove(type);
  }
}
