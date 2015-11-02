package com.fisherevans.scs.util;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import java.util.Map;

/**
 * Created by h13730 on 11/2/2015.
 */
public class BasicLocation {
  private Double _x, _y, _z;
  private String _world;

  public BasicLocation(Double x, Double y, Double z, String world) {
    _x = x;
    _y = y;
    _z = z;
    _world = world;
  }

  public Double getX() {
    return _x;
  }

  public void setX(Double x) {
    _x = x;
  }

  public Double getY() {
    return _y;
  }

  public void setY(Double y) {
    _y = y;
  }

  public Double getZ() {
    return _z;
  }

  public void setZ(Double z) {
    _z = z;
  }

  public String getWorld() {
    return _world;
  }

  public void setWorld(String world) {
    _world = world;
  }

  public Location toBukkitLocation() {
    return new Location(Bukkit.getWorld(getWorld()), getX(), getY(), getZ());
  }

  public static BasicLocation fromPlayer(Player player) {
    Location location = player.getLocation();
    return new BasicLocation(
        location.getX(),
        location.getY(),
        location.getZ(),
        location.getWorld().getName()
    );
  }
}
