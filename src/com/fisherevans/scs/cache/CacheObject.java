package com.fisherevans.scs.cache;

import org.bukkit.configuration.ConfigurationSection;

/**
 * Created by h13730 on 11/4/2015.
 */
public interface CacheObject {
  public void load(ConfigurationSection section);
  public void save(ConfigurationSection section);
}
