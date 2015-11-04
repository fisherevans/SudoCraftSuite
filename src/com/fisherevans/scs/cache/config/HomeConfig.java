package com.fisherevans.scs.cache.config;

import com.fisherevans.scs.cache.CacheObject;
import org.bukkit.configuration.ConfigurationSection;

/**
 * Created by h13730 on 11/4/2015.
 */
public class HomeConfig implements CacheObject {
  private static final String YML_DEFAULT_HOME_NAME = "default-name";

  private String _defaultName = "default";

  public String getDefaultName() {
    return _defaultName;
  }

  @Override
  public void load(ConfigurationSection section) {
    section.set(YML_DEFAULT_HOME_NAME, _defaultName);
  }

  @Override
  public void save(ConfigurationSection section) {
    if(section.contains(YML_DEFAULT_HOME_NAME))
      _defaultName = section.getString(YML_DEFAULT_HOME_NAME);
  }
}
