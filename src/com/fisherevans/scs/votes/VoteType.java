package com.fisherevans.scs.votes;

/**
 * Created by h13730 on 11/4/2015.
 */
public enum VoteType {
  WeatherSun("weather", "Sun"),
  WeatherRain("weather", "Rain"),
  WeatherStorm("weather", "Thunder"),
  TimeDay("time", "Day"),
  TimeNight("time", "Night");

  public final String aspect, value;

  VoteType(String aspect, String value) {
    this.aspect = aspect;
    this.value = value;
  }
}
