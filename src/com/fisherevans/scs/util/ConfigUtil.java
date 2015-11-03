package com.fisherevans.scs.util;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.craftbukkit.libs.jline.internal.Configuration;

import javax.persistence.Basic;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by h13730 on 11/2/2015.
 */
public class ConfigUtil {

  public static void loadMultiLocationMap(ConfigurationSection superSection, Map<String, Map<String, BasicLocation>> locationMap) {
    if(superSection == null)
      return;
    for(String subKey:superSection.getKeys(false)) {
      ConfigurationSection subSection = superSection.getConfigurationSection(subKey);
      Map<String, BasicLocation> subLocationMap = new HashMap<>();
      loadLocationMap(subSection, subLocationMap);
      locationMap.put(subKey, subLocationMap);
    }
  }

  public static void loadLocationMap(ConfigurationSection section, Map<String, BasicLocation> locationMap) {
    if(section == null)
      return;
    for(String subKey:section.getKeys(false)) {
      ConfigurationSection subSection = section.getConfigurationSection(subKey);
      Double x = subSection.getDouble("x");
      Double y = subSection.getDouble("y");
      Double z = subSection.getDouble("z");
      String w = subSection.getString("w");
      locationMap.put(subKey, new BasicLocation(x, y, z, w));
    }
  }

  public static void saveMultiLocationMap(ConfigurationSection superSection, Map<String, Map<String, BasicLocation>> locationMap) {
    for(String subKey:locationMap.keySet()) {
      ConfigurationSection subSection = superSection.createSection(subKey);
      saveLocationMap(subSection, locationMap.get(subKey));
    }
  }

  public static void saveLocationMap(ConfigurationSection section, Map<String, BasicLocation> locationMap) {
    for(String subKey:locationMap.keySet()) {
      BasicLocation location = locationMap.get(subKey);
      ConfigurationSection subSection = section.createSection(subKey);
      subSection.set("x", location.getX());
      subSection.set("y", location.getY());
      subSection.set("z", location.getZ());
      subSection.set("w", location.getWorld());
    }
  }
}
