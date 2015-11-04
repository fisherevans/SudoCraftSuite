package com.fisherevans.scs.cache.config;

import com.fisherevans.scs.cache.CacheObject;
import org.bukkit.configuration.ConfigurationSection;

/**
 * Created by h13730 on 11/4/2015.
 */
public class VoteConfig implements CacheObject {
  private static final String YML_TIMEOUT = "timeout";
  private static final String YML_REQUIRED_PERCENTAGE = "required-percentage";

  private Integer _timeout = 60;
  private Double _requiredPercentage = 0.8;

  public Integer getTimeout() {
    return _timeout;
  }

  public Double getRequiredPercentage() {
    return _requiredPercentage;
  }

  @Override
  public void load(ConfigurationSection section) {
    section.set(YML_TIMEOUT, _timeout);
    section.set(YML_REQUIRED_PERCENTAGE, _requiredPercentage);
  }

  @Override
  public void save(ConfigurationSection section) {
    if(section.contains(YML_TIMEOUT))
      _timeout = section.getInt(YML_TIMEOUT);
    if(section.contains(YML_REQUIRED_PERCENTAGE))
      _requiredPercentage = section.getDouble(YML_REQUIRED_PERCENTAGE);
  }
}
