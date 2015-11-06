package com.fisherevans.scs.cache;

import com.fisherevans.scs.cache.config.Config;
import com.fisherevans.scs.util.YMLUtil;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by h13730 on 11/4/2015.
 */
public class Cache implements CacheObject {
  private static final String YML_PLAYERS = "players";
  private static final String YML_WARPS = "warps";
  private static final String YML_LOCKED_CHESTS = "locked-chests";

  private Map<String, PlayerCache> _players;
  private Map<String, Location> _warps;
  private Map<Location, LockedChestCache> _lockedChests;

  private Config _config;

  public Cache() {
    _players = new HashMap<>();
    _warps = new HashMap<>();
    _lockedChests = new HashMap<>();
    _config = new Config();
  }

  public Map<String, PlayerCache> getPlayers() {
    return _players;
  }

  public Map<String, Location> getWarps() {
    return _warps;
  }

  public Map<Location, LockedChestCache> getLockedChests() {
    return _lockedChests;
  }

  public Config getConfig() {
    return _config;
  }

  @Override
  public void load(ConfigurationSection section) {
    YMLUtil.getCacheObjectMap(section, YML_PLAYERS, _players, PlayerCache.class);
    YMLUtil.getLocationMap(section, YML_WARPS, _warps);
    YMLUtil.getLockedChests(section, YML_LOCKED_CHESTS, _lockedChests);
  }

  @Override
  public void save(ConfigurationSection section) {
    YMLUtil.setCacheObjectMap(section, YML_PLAYERS, _players);
    YMLUtil.setLocationMap(section, YML_WARPS, _warps);
    YMLUtil.setLockedChests(section, YML_LOCKED_CHESTS, _lockedChests);
  }
}
