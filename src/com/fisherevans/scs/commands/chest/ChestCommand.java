package com.fisherevans.scs.commands.chest;

import com.fisherevans.scs.SudoCraftSuite;
import com.fisherevans.scs.cache.LockedChestCache;
import com.fisherevans.scs.commands.SudoCommand;
import com.fisherevans.scs.util.ChestUtil;
import com.fisherevans.scs.util.CommandUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by h13730 on 11/6/2015.
 */
public class ChestCommand extends SudoCommand {
  public ChestCommand(SudoCraftSuite plugin) {
    super(plugin);
  }

  @Override
  public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
    Player player = requirePlayer(commandSender);
    if(player == null)
      return true;
    Set<Material> materialSet = new HashSet<>();
    materialSet.add(Material.CHEST);
    Block block = player.getTargetBlock(materialSet, 10);
    if(block == null) {
      player.sendMessage(ChatColor.DARK_GRAY + "Please look a chest to lock it. It can't be more than 10 blocks away.");
      return true;
    }
    String action = CommandUtil.getFirstArgument(args, "");
    if(action.length() == 0) {
      player.sendMessage(ChatColor.DARK_GRAY + "Please specify an action, like: lock, add, remove");
      return true;
    }
    if(action.equals("lock"))
      ChestUtil.toggleChest(getPlugin(), block.getLocation(), player);
    else if(action.equals("add") || action.equals("remove")){
      if(args.length < 2)
        player.sendMessage(ChatColor.DARK_GRAY + "Please specify a user name as well.");
      else
        ChestUtil.modifyMember(getPlugin(), block.getLocation(), player, args[1], action.equals("add"));
    } else
      player.sendMessage(ChatColor.DARK_GRAY + "Please specify an action, like: lock, add, remove");
    return true;
  }
}
