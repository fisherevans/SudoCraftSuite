package com.fisherevans.scs.util;

import com.fisherevans.scs.SudoCraftSuite;
import com.fisherevans.scs.cache.Cache;
import com.fisherevans.scs.cache.CacheObject;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * Created by h13730 on 11/4/2015.
 */
public class YMLUtil {
  private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

  private static final String YML_LOCATION_X = "x";
  private static final String YML_LOCATION_Y = "y";
  private static final String YML_LOCATION_Z = "z";
  private static final String YML_LOCATION_WORLD = "world";
  private static final String YML_LOCATION_PITCH = "pitch";
  private static final String YML_LOCATION_YAW = "yaw";

  public static Date getDate(ConfigurationSection section, String key) {
    String dateString = section.getString(key);
    if(dateString == null)
      return null;
    try {
      return DATE_FORMAT.parse(dateString);
    } catch (ParseException e) {
      return null;
    }
  }

  public static void setDate(ConfigurationSection section, String key, Date date) {
    String dateString = date == null ? null : DATE_FORMAT.format(date);
    section.set(key, dateString);
  }

  public static Location getLocation(ConfigurationSection section, String key) {
    ConfigurationSection locationSection = section.getConfigurationSection(key);
    if(locationSection == null)
      return null;
    Double x = locationSection.getDouble(YML_LOCATION_X);
    Double y = locationSection.getDouble(YML_LOCATION_Y);
    Double z = locationSection.getDouble(YML_LOCATION_Z);
    String world = locationSection.getString(YML_LOCATION_WORLD);
    Double yaw = locationSection.getDouble(YML_LOCATION_YAW);
    Double pitch = locationSection.getDouble(YML_LOCATION_PITCH);
    Location location = new Location(Bukkit.getServer().getWorld(world), x, y, z);
    if(pitch != null) location.setPitch(pitch.floatValue());
    if(yaw != null) location.setYaw(yaw.floatValue());
    return location;
  }

  public static void getLocationMap(ConfigurationSection rootSection, String key, Map<String, Location> locationMap) {
    locationMap.clear();
    ConfigurationSection section = rootSection.getConfigurationSection(key);
    if(section == null)
      return;
    for(String subKey:section.getKeys(false)) {
      locationMap.put(subKey, getLocation(section, subKey));
    }
  }

  public static void setLocation(ConfigurationSection section, String key, Location location) {
    if(location == null)
      return;
    ConfigurationSection locationSection = section.createSection(key);
    locationSection.set(YML_LOCATION_X, location.getX());
    locationSection.set(YML_LOCATION_Y, location.getY());
    locationSection.set(YML_LOCATION_Z, location.getZ());
    locationSection.set(YML_LOCATION_WORLD, location.getWorld().getName());
    locationSection.set(YML_LOCATION_PITCH, location.getPitch());
    locationSection.set(YML_LOCATION_YAW, location.getYaw());
  }

  public static void setLocationMap(ConfigurationSection rootSection, String key, Map<String, Location> locationMap) {
    ConfigurationSection section = rootSection.createSection(key);
    for(String subKey:locationMap.keySet()) {
      setLocation(section, subKey, locationMap.get(subKey));
    }
  }

  public static <T extends CacheObject> void getCacheObjectMap(ConfigurationSection rootSection, String key, Map<String, T> objectMap, Class<T> objectClass) {
    objectMap.clear();
    ConfigurationSection section = rootSection.getConfigurationSection(key);
    for(String subKey:section.getKeys(false)) {
      try {
        T object = objectClass.getConstructor(String.class).newInstance(subKey);
        object.load(section.getConfigurationSection(subKey));
        objectMap.put(subKey, object);
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }

  public static <T extends CacheObject> void setCacheObjectMap(ConfigurationSection rootSection, String key, Map<String, T> objectMap) {
    ConfigurationSection section = rootSection.createSection(key);
    for(String subKey:objectMap.keySet()) {
      objectMap.get(subKey).save(section.createSection(subKey));
    }
  }
}
