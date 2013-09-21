 package me.carl230690.servermaintenance;
 
 import org.bukkit.ChatColor;
 import org.bukkit.entity.Player;
 import org.bukkit.event.EventHandler;
 import org.bukkit.event.EventPriority;
 import org.bukkit.event.Listener;
 import org.bukkit.event.player.PlayerJoinEvent;
 import org.bukkit.event.player.PlayerLoginEvent;
 
 public class PlayerListener
   implements Listener
 {
   Main plugin;
 
   public PlayerListener(Main plugin)
   {
     this.plugin = plugin;
   }
 
   @EventHandler(priority=EventPriority.NORMAL)
   public void latePlayerJoin(PlayerJoinEvent event) {
     Player player = event.getPlayer();
     if (((player.hasPermission(new Permissions().bypass)) || (player.hasPermission(new Permissions().all)) || (this.plugin.AllowedPlayers.contains(event.getPlayer().getName()))) && 
       (this.plugin.getConfig().getBoolean("enabled"))) {
       player.sendMessage(ChatColor.RED + "Server is in maintenance mode");
     }
     if (((player.hasPermission(new Permissions().all)) || (player.hasPermission(new Permissions().update))) && 
       (this.plugin.versionDiff)) {
       player.sendMessage(ChatColor.GREEN + "A new version of ServerMaintenance is available.");
       player.sendMessage(ChatColor.GREEN + "It's recommended updating:)");
     }
   }
 
   @EventHandler(priority=EventPriority.HIGH)
   public void earlyPlayerJoin(PlayerLoginEvent event)
   {
     if ((this.plugin.getConfig().getBoolean("enabled")) && 
       (!event.getPlayer().hasPermission(new Permissions().bypass)) && (!this.plugin.AllowedPlayers.contains(event.getPlayer().getName())))
     {
       String kickmsg = this.plugin.getConfig().getString("KickMessage").replaceAll("&", "§");
       event.disallow(PlayerLoginEvent.Result.KICK_OTHER, kickmsg);
       for (Player player : this.plugin.getServer().getOnlinePlayers())
         if ((player.hasPermission(new Permissions().bypass)) || (this.plugin.AllowedPlayers.contains(player.getName())))
           player.sendMessage(ChatColor.AQUA + event.getPlayer().getName() + " Tried To Join The Server.");
     }
   }
 }