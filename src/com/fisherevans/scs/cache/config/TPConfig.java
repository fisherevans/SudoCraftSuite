package com.fisherevans.scs.cache.config;

import com.fisherevans.scs.cache.CacheObject;
import org.bukkit.configuration.ConfigurationSection;

/**
 * Created by h13730 on 11/4/2015.
 */
public class TPConfig implements CacheObject {
  private static final String YML_TIMEOUT = "timeout";

  private Integer _timeout = 45;

  public Integer getTimeout() {
    return _timeout;
  }

  @Override
  public void load(ConfigurationSection section) {
    section.set(YML_TIMEOUT, _timeout);
  }

  @Override
  public void save(ConfigurationSection section) {
    if(section.contains(YML_TIMEOUT))
      _timeout = section.getInt(YML_TIMEOUT);
  }
}
