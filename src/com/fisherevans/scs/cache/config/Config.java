package com.fisherevans.scs.cache.config;

import com.fisherevans.scs.cache.CacheObject;
import org.bukkit.configuration.ConfigurationSection;

/**
 * Created by h13730 on 11/4/2015.
 */
public class Config implements CacheObject {
  private static final String YML_CHAT = "chat";
  private static final String YML_HOME = "home";
  private static final String YML_MINE_NOTIFICATIONS = "mine-notification";
  private static final String YML_TP = "tp";
  private static final String YML_VOTE = "vote";

  private ChatConfig _chat;
  private HomeConfig _home;
  private MineNotificationsConfig _mineNotifications;
  private TPConfig _tp;
  private VoteConfig _vote;

  public Config() {
    _chat = new ChatConfig();
    _home = new HomeConfig();
    _mineNotifications = new MineNotificationsConfig();
    _tp = new TPConfig();
    _vote = new VoteConfig();
  }

  public ChatConfig getChat() {
    return _chat;
  }

  public HomeConfig getHome() {
    return _home;
  }

  public MineNotificationsConfig getMineNotifications() {
    return _mineNotifications;
  }

  public TPConfig getTP() {
    return _tp;
  }

  public VoteConfig getVote() {
    return _vote;
  }

  @Override
  public void load(ConfigurationSection section) {
    _chat.load(section.getConfigurationSection(YML_CHAT));
    _home.load(section.getConfigurationSection(YML_HOME));
    _mineNotifications.load(section.getConfigurationSection(YML_MINE_NOTIFICATIONS));
    _tp.load(section.getConfigurationSection(YML_TP));
    _vote.load(section.getConfigurationSection(YML_VOTE));
  }

  @Override
  public void save(ConfigurationSection section) {
    _chat.save(section.createSection(YML_CHAT));
    _home.save(section.createSection(YML_HOME));
    _mineNotifications.save(section.createSection(YML_MINE_NOTIFICATIONS));
    _tp.save(section.createSection(YML_TP));
    _vote.save(section.createSection(YML_VOTE));
  }
}
