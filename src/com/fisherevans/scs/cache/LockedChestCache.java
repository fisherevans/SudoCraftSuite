package com.fisherevans.scs.cache;

import com.fisherevans.scs.util.YMLUtil;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by h13730 on 11/6/2015.
 */
public class LockedChestCache implements CacheObject {
  private static final String YML_LOCATION = "location";
  private static final String YML_OWNER_UUID = "owner-uuid";
  private static final String YML_MEMBER_UUIDS = "members";

  private Location _location;
  private String _ownerUUID;
  private Set<String> _members;

  public LockedChestCache() {
    _members = new HashSet<String>();
  }

  public String getOwnerUUID() {
    return _ownerUUID;
  }

  public void setOwnerUUID(String ownerUUID) {
    _ownerUUID = ownerUUID;
  }

  public Set<String> getMembers() {
    return _members;
  }

  public boolean canAccess(String uuid, String username) {
    return uuid.equals(_ownerUUID) || _members.contains(uuid) || _members.contains(username);
  }

  public Location getLocation() {
    return _location;
  }

  public void setLocation(Location location) {
    if(_location == null) {
      _location = location;
      _location.setPitch(0);
      _location.setYaw(0);
    } else {
      _location.setX(location.getBlockX());
      _location.setY(location.getBlockY());
      _location.setZ(location.getBlockZ());
    }
  }

  @Override
  public void load(ConfigurationSection section) {
    _ownerUUID = section.getString(YML_OWNER_UUID);
    _members = new HashSet<>(section.getStringList(YML_MEMBER_UUIDS));
    _location = YMLUtil.getLocation(section, YML_LOCATION);
  }

  @Override
  public void save(ConfigurationSection section) {
    section.set(YML_OWNER_UUID, _ownerUUID);
    section.set(YML_MEMBER_UUIDS, new ArrayList<>(_members));
    YMLUtil.setLocation(section, YML_LOCATION, _location);
  }

  public static String getKey(Location location) {
    return location.getWorld().getName() + "." + location.getBlockX() + "." + location.getBlockY() + "." + location.getBlockZ();
  }

  public static Location getLocationFromKey(String key) {
    String[] split = key.split(".");
    Location location = new Location(
        Bukkit.getServer().getWorld(split[0]),
        Integer.parseInt(split[1]),
        Integer.parseInt(split[2]),
        Integer.parseInt(split[3])
    );
    return location;
  }
}
