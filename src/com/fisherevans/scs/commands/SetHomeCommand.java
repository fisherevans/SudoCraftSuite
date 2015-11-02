package com.fisherevans.scs.commands;

import com.avaje.ebeaninternal.server.persist.Constant;
import com.fisherevans.scs.Constants;
import com.fisherevans.scs.SudoCraftSuite;
import com.fisherevans.scs.util.BasicLocation;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.metadata.MetadataValue;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by h13730 on 11/2/2015.
 */
public class SetHomeCommand extends SudoCommand {
  public SetHomeCommand(SudoCraftSuite parentPlugin) {
    super(parentPlugin);
  }

  @Override
  public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
    Player player = getPlayer(commandSender);
    if(player == null)
      return false;
    String home = args.length > 0 ? args[0] : getPlugin().getDefaultHomeName();
    getPlugin().setPlayerHome(player.getName(), home, BasicLocation.fromPlayer(player));
    player.sendMessage(ChatColor.BLUE + home + ChatColor.DARK_GRAY + " home set!");
    return true;
  }
}
