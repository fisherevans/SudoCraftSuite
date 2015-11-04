package com.fisherevans.scs.cache.config;

import com.fisherevans.scs.cache.CacheObject;
import org.bukkit.configuration.ConfigurationSection;

/**
 * Created by h13730 on 11/4/2015.
 */
public class MineNotificationsConfig implements CacheObject {
  private static final String YML_DIAMOND_DELAY = "diamond-delay";

  private Integer _diamondDelay = 5;

  public Integer getDiamondDelay() {
    return _diamondDelay;
  }

  @Override
  public void load(ConfigurationSection section) {
    section.set(YML_DIAMOND_DELAY, _diamondDelay);
  }

  @Override
  public void save(ConfigurationSection section) {
    if(section.contains(YML_DIAMOND_DELAY))
      _diamondDelay = section.getInt(YML_DIAMOND_DELAY);
  }
}
