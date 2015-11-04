package com.fisherevans.scs.cache;

import com.fisherevans.scs.util.YMLUtil;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.craftbukkit.libs.jline.internal.Configuration;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by h13730 on 11/4/2015.
 */
public class PlayerCache implements CacheObject {
  private static final String YML_LAST_USERNAME = "last-username";
  private static final String YML_LAST_LOGIN = "last-login";
  private static final String YML_LAST_TELEPORT_LOCATION = "last-successfulTeleport-location";
  private static final String YML_HOMES = "homes";

  private String _uuid;
  private String _lastUsername;
  private Date _lastLogin;
  private Location _lastTeleportLocation;
  private Map<String, Location> _homes;

  public PlayerCache(String uuid) {
    _uuid = uuid;
    _homes = new HashMap<>();
  }

  public Map<String, Location> getHomes() {
    return _homes;
  }

  public String getUUID() {
    return _uuid;
  }

  public void setUUID(String uuid) {
    _uuid = uuid;
  }

  public String getLastUsername() {
    return _lastUsername;
  }

  public void setLastUsername(String lastUsername) {
    _lastUsername = lastUsername;
  }

  public Date getLastLogin() {
    return _lastLogin;
  }

  public void setLastLogin(Date lastLogin) {
    _lastLogin = lastLogin;
  }

  public Location getLastTeleportLocation() {
    return _lastTeleportLocation;
  }

  public void setLastTeleportLocation(Location lastTeleportLocation) {
    _lastTeleportLocation = lastTeleportLocation;
  }

  @Override
  public void load(ConfigurationSection section) {
    _lastUsername = section.getString(YML_LAST_USERNAME);
    _lastLogin = YMLUtil.getDate(section, YML_LAST_LOGIN);
    _lastTeleportLocation = YMLUtil.getLocation(section, YML_LAST_TELEPORT_LOCATION);
    YMLUtil.getLocationMap(section, YML_HOMES, _homes);
  }

  @Override
  public void save(ConfigurationSection section) {
    section.set(YML_LAST_USERNAME, _lastUsername);
    YMLUtil.setDate(section, YML_LAST_LOGIN, _lastLogin);
    YMLUtil.setLocation(section, YML_LAST_TELEPORT_LOCATION, _lastTeleportLocation);
    YMLUtil.setLocationMap(section, YML_HOMES, _homes);
  }
}
