package com.fisherevans.scs.cache.config;

import com.fisherevans.scs.cache.CacheObject;
import org.bukkit.configuration.ConfigurationSection;

/**
 * Created by h13730 on 11/4/2015.
 */
public class ChatConfig implements CacheObject {
  private static final String YML_FORMAT = "format";

  private String _format = "#white[#prefix#white] #gray#name#white: #message";

  public String getFormat() {
    return _format;
  }

  @Override
  public void load(ConfigurationSection section) {
    section.set(YML_FORMAT, _format);
  }

  @Override
  public void save(ConfigurationSection section) {
    if(section.contains(YML_FORMAT))
      _format = section.getString(YML_FORMAT);
  }
}
