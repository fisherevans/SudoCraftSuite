package com.fisherevans.scs.cache;

import com.fisherevans.scs.cache.config.Config;
import com.fisherevans.scs.util.YMLUtil;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by h13730 on 11/4/2015.
 */
public class Cache implements CacheObject {
  private static final String YML_PLAYERS = "players";
  private static final String YML_WARPS = "warps";

  private Map<String, PlayerCache> _players;
  private Map<String, Location> _warps;

  private Config _config;

  public Cache() {
    _players = new HashMap<>();
    _warps = new HashMap<>();
    _config = new Config();
  }

  public Map<String, PlayerCache> getPlayers() {
    return _players;
  }

  public Map<String, Location> getWarps() {
    return _warps;
  }

  public Config getConfig() {
    return _config;
  }

  @Override
  public void load(ConfigurationSection section) {
    YMLUtil.getCacheObjectMap(section, YML_PLAYERS, _players, PlayerCache.class);
    YMLUtil.getLocationMap(section, YML_WARPS, _warps);
  }

  @Override
  public void save(ConfigurationSection section) {
    YMLUtil.setCacheObjectMap(section, YML_PLAYERS, _players);
    YMLUtil.setLocationMap(section, YML_WARPS, _warps);
  }
}
