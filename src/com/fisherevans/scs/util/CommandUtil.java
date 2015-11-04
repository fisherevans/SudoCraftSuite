package com.fisherevans.scs.util;

import com.fisherevans.scs.SudoCraftSuite;

/**
 * Created by h13730 on 11/4/2015.
 */
public class CommandUtil {
  public static String getHomeArgument(SudoCraftSuite plugin, String[] args) {
    return getFirstArgument(args, plugin.getCache().getConfig().getHome().getDefaultName());
  }

  public static String getFirstArgument(String[] args, String def) {
    String home = args.length > 0 ? args[0] : def;
    return home;
  }
}
