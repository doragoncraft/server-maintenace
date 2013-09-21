 package me.carl230690.servermaintenance;
 
 import java.util.List;
 import org.bukkit.Bukkit;
 import org.bukkit.ChatColor;
 import org.bukkit.command.Command;
 import org.bukkit.command.CommandExecutor;
 import org.bukkit.command.CommandSender;
 import org.bukkit.entity.Player;
 
 public class CommandMaintenance
   implements CommandExecutor
 {
   private Main plugin;
   int seconds;
   int startCount;
   String noperm = ChatColor.RED + "You Do Not Have Permission To Perform This Command!";
 
   public CommandMaintenance(Main plugin)
   {
     this.plugin = plugin;
   }
 
   public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
   {
     if ((sender.hasPermission(new Permissions().bypass)) || (sender.hasPermission(new Permissions().all)) || (sender.hasPermission(new Permissions().playeradd)) || (sender.hasPermission(new Permissions().playeralll)) || (sender.hasPermission(new Permissions().playerlist)) || (sender.hasPermission(new Permissions().playerremove)) || (sender.hasPermission(new Permissions().toggle))) {
        if (args.length == 0) {
         sender.sendMessage(ChatColor.AQUA + "============== " + ChatColor.DARK_AQUA + ChatColor.BOLD + "Server Maintenance Help" + ChatColor.AQUA + "==============");
         sender.sendMessage(ChatColor.DARK_RED + "   -   " + ChatColor.AQUA + "/maintenance help " + ChatColor.DARK_AQUA + "Display This Menu");
         sender.sendMessage(ChatColor.DARK_RED + "   -   " + ChatColor.AQUA + "/maintenance toggle " + ChatColor.DARK_AQUA + "Toggle the maintenance mode!");
         sender.sendMessage(ChatColor.DARK_RED + "   -   " + ChatColor.AQUA + "/maintenance reload " + ChatColor.DARK_AQUA + "Reloads the configuration!");
         sender.sendMessage(ChatColor.DARK_RED + "   -   " + ChatColor.AQUA + "/maintenance player add <player> " + ChatColor.DARK_AQUA + " Add a player to the allowed-palyers.txt file!");
         sender.sendMessage(ChatColor.DARK_RED + "   -   " + ChatColor.AQUA + "/maintenance player remove <player> " + ChatColor.DARK_AQUA + "Removes a player from the allowed-palyers.txt file!");
         sender.sendMessage(ChatColor.AQUA + "=====================================================");
         return true;
       }
 
       if ((args[0].equalsIgnoreCase("player")) || (args[0].equalsIgnoreCase("p")))
       {
         if (args.length > 1)
         {
           if ((args[1].equalsIgnoreCase("add")) || (args[1].equalsIgnoreCase("allow"))) {
             if ((sender.hasPermission(new Permissions().playeralll)) || (sender.hasPermission(new Permissions().playeradd)) || (sender.hasPermission(new Permissions().all))) {
               this.plugin.AllowedPlayers.add(args[2]);
               this.plugin.logger.info(args[2] + " has been added to the allowed-players.txt list.");
               sender.sendMessage(ChatColor.GREEN + args[2] + " has been added to the allowed-players.txt list.");
               this.plugin.AllowedPlayers.save();
               return true;
             }
 
           }
           else if ((args[1].equalsIgnoreCase("remove")) || (args[1].equalsIgnoreCase("delete"))) {
             if ((sender.hasPermission(new Permissions().playeralll)) || (sender.hasPermission(new Permissions().playerremove)) || (sender.hasPermission(new Permissions().all))) {
               this.plugin.AllowedPlayers.remove(args[2]);
               this.plugin.logger.info(args[2] + " has been removed from the allowed-players.txt list.");
               sender.sendMessage(ChatColor.GREEN + args[2] + " has been removed from the allowed-players.txt list.");
               this.plugin.AllowedPlayers.save();
               return true;
             }
             sender.sendMessage(this.noperm);
           }
           else
           {
             sender.sendMessage(ChatColor.AQUA + "============== " + ChatColor.DARK_AQUA + ChatColor.BOLD + "Server Maintenance Help" + ChatColor.AQUA + "==============");
             sender.sendMessage(ChatColor.DARK_RED + "   -   " + ChatColor.AQUA + "/maintenance player add <player> " + ChatColor.DARK_AQUA + " Add a player to the allowed-palyers.txt file!");
             sender.sendMessage(ChatColor.DARK_RED + "   -   " + ChatColor.AQUA + "/maintenance player remove <player> " + ChatColor.DARK_AQUA + "Removes a player from the allowed-palyers.txt file!");
             sender.sendMessage(ChatColor.AQUA + "=====================================================");
           }
         }
         else
         {
           sender.sendMessage(ChatColor.AQUA + "============== " + ChatColor.DARK_AQUA + ChatColor.BOLD + "Server Maintenance Help" + ChatColor.AQUA + "==============");
           sender.sendMessage(ChatColor.DARK_RED + "   -   " + ChatColor.AQUA + "/maintenance player add <player> " + ChatColor.DARK_AQUA + " Add a player to the allowed-palyers.txt file!");
           sender.sendMessage(ChatColor.DARK_RED + "   -   " + ChatColor.AQUA + "/maintenance player remove <player> " + ChatColor.DARK_AQUA + "Removes a player from the allowed-palyers.txt file!");
           sender.sendMessage(ChatColor.AQUA + "=====================================================");
         }
       }
       else if ((args[0].equalsIgnoreCase("toggle")) || (args[0].equalsIgnoreCase("t"))) {
         if ((sender.hasPermission(new Permissions().toggle)) || (sender.hasPermission(new Permissions().all))) {
           if (this.plugin.getConfig().getBoolean("enabled")) {
             this.plugin.getConfig().set("enabled", Boolean.valueOf(false));
             this.plugin.getServer().broadcastMessage(this.plugin.getConfig().getString("BroadcastOnMaintenanceDisableMessage").replaceAll("&", "§"));
           } else {
             this.plugin.getConfig().set("enabled", Boolean.valueOf(true));
             this.plugin.getServer().broadcastMessage(this.plugin.getConfig().getString("BroadcastOnMaintenanceEnableMessage").replaceAll("&", "§"));
             for (Player player : this.plugin.getServer().getOnlinePlayers()) {
               if ((player.hasPermission(new Permissions().bypass)) || (this.plugin.AllowedPlayers.contains(player.getName()))) {
                 player.sendMessage("");
               } else {
                 String kickmsg = this.plugin.getConfig().getString("KickMessage").replaceAll("&", "§");
                 player.kickPlayer(kickmsg);
               }
             }
           }
           return true;
         }
 
         sender.sendMessage(this.noperm);
       }
       else if ((args[0].equalsIgnoreCase("reload")) || (args[0].equalsIgnoreCase("r"))) {
         if ((sender.hasPermission(new Permissions().all)) || (sender.hasPermission(new Permissions().reloadconfig))) {
           this.plugin.reloadConfig();
           sender.sendMessage(ChatColor.GREEN + "Configuration Reloaded!");
           return false;
         }
         sender.sendMessage(this.noperm);
       }
       else if ((args[0].equalsIgnoreCase("td")) || (args[0].equalsIgnoreCase("toggledelay"))) {
         if ((sender.hasPermission(new Permissions().toggle)) || (sender.hasPermission(new Permissions().all))) {
           if (args.length == 1) {
             sender.sendMessage(ChatColor.DARK_RED + "Usage: /maintenance toggledelay (delay time in seconds)");
           }
           else if (this.plugin.getConfig().getBoolean("enabled")) {
             this.plugin.getConfig().set("enabled", Boolean.valueOf(false));
             this.plugin.getServer().broadcastMessage(this.plugin.getConfig().getString("BroadcastOnMaintenanceDisableMessage").replaceAll("&", "§"));
           } else {
             try {
               this.startCount = Integer.parseInt(args[1]);
               long Count = this.startCount * 20;
               final List seconds = this.plugin.getConfig().getIntegerList("BroadcastTime");
               final int taskID = this.plugin.getServer().getScheduler().scheduleSyncRepeatingTask(this.plugin, new Runnable()
               {
                 public void run() {
                   String Seconds = String.valueOf(CommandMaintenance.this.startCount);
                   if ((seconds.contains(Integer.valueOf(CommandMaintenance.this.startCount))) && (CommandMaintenance.this.startCount > 1) && (CommandMaintenance.this.startCount < 60))
                     Bukkit.broadcastMessage(CommandMaintenance.this.plugin.getConfig().getString("BroadcastSecondsMessage").replaceAll("&", "§").replaceAll("%t", Seconds));
                   else if ((seconds.contains(Integer.valueOf(CommandMaintenance.this.startCount))) && (CommandMaintenance.this.startCount == 1))
                     Bukkit.broadcastMessage(CommandMaintenance.this.plugin.getConfig().getString("BroadcastSecondMessage").replaceAll("&", "§").replaceAll("%t", Seconds));
                   else if ((seconds.contains(Integer.valueOf(CommandMaintenance.this.startCount))) && (CommandMaintenance.this.startCount == 60)) {
                     Bukkit.broadcastMessage(CommandMaintenance.this.plugin.getConfig().getString("BroadcastMinuteMessage").replaceAll("&", "§").replaceAll("%t", "1"));
                   }
                   CommandMaintenance.this.startCount -= 1;
                 }
               }
               , 20L, 20L);
               this.plugin.getServer().getScheduler().scheduleSyncDelayedTask(this.plugin, new Runnable()
               {
                 public void run() {
                   Bukkit.getScheduler().cancelTask(taskID);
                   CommandMaintenance.this.plugin.getConfig().set("enabled", Boolean.valueOf(true));
                   CommandMaintenance.this.plugin.getServer().broadcastMessage(CommandMaintenance.this.plugin.getConfig().getString("BroadcastOnMaintenanceEnableMessage").replaceAll("&", "§"));
                   for (Player player : CommandMaintenance.this.plugin.getServer().getOnlinePlayers())
                     if ((player.hasPermission(new Permissions().bypass)) || (CommandMaintenance.this.plugin.AllowedPlayers.contains(player.getName()))) {
                       player.sendMessage("");
                     } else {
                       String kickmsg = CommandMaintenance.this.plugin.getConfig().getString("KickMessage").replaceAll("&", "§");
                       player.kickPlayer(kickmsg);
                     }
                 }
               }
               , Count + 20L);
             } catch (NumberFormatException e1) {
               sender.sendMessage("Needs To be a Number!");
             }
           }
         }
         else
         {
           sender.sendMessage(this.noperm);
         }
       }
       else
       {
         sender.sendMessage(ChatColor.AQUA + "============== " + ChatColor.DARK_AQUA + ChatColor.BOLD + "Server Maintenance Help" + ChatColor.AQUA + "==============");
         sender.sendMessage(ChatColor.DARK_RED + "   -   " + ChatColor.AQUA + "/maintenance help " + ChatColor.DARK_AQUA + "Display This Menu");
         sender.sendMessage(ChatColor.DARK_RED + "   -   " + ChatColor.AQUA + "/maintenance toggle " + ChatColor.DARK_AQUA + "Toggle the maintenance mode!");
         sender.sendMessage(ChatColor.DARK_RED + "   -   " + ChatColor.AQUA + "/maintenance toggledelay " + ChatColor.DARK_AQUA + "Toggle the maintenance mode with delay!");
         sender.sendMessage(ChatColor.DARK_RED + "   -   " + ChatColor.AQUA + "/maintenance reload " + ChatColor.DARK_AQUA + "Reloads the configuration!");
         sender.sendMessage(ChatColor.DARK_RED + "   -   " + ChatColor.AQUA + "/maintenance player add <player> " + ChatColor.DARK_AQUA + " Add a player to the allowed-palyers.txt file!");
         sender.sendMessage(ChatColor.DARK_RED + "   -   " + ChatColor.AQUA + "/maintenance player remove <player> " + ChatColor.DARK_AQUA + "Removes a player from the allowed-palyers.txt file!");
         sender.sendMessage(ChatColor.AQUA + "=====================================================");
       }
 
       return true;
     }
 
     sender.sendMessage(this.noperm);
 
     return true;
   }
 }
