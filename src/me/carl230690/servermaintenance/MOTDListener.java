 package me.carl230690.servermaintenance;
 
 import java.util.List;
 import java.util.Random;
 import org.bukkit.event.EventHandler;
 import org.bukkit.event.EventPriority;
 import org.bukkit.event.Listener;
 import org.bukkit.event.server.ServerListPingEvent;
 
 public class MOTDListener
   implements Listener
 {
   private Main plugin;
 
   public MOTDListener(Main plugin)
   {
     this.plugin = plugin;
   }
 
   @EventHandler(priority=EventPriority.MONITOR)
   public void onServerListPing(ServerListPingEvent event)
   {
     if (!this.plugin.getConfig().getBoolean("enabled")) {
       List wm = this.plugin.getConfig().getStringList("WelcomeMOTD");
       int wr = new Random().nextInt(wm.size());
       String dm = (String)wm.get(wr);
       event.setMotd(dm.replace("&", "§"));
     } else {
       List mm = this.plugin.getConfig().getStringList("MaintenanceMOTD");
       int mr = new Random().nextInt(mm.size());
       String dmm = (String)mm.get(mr);
       event.setMotd(dmm.replace("&", "§"));
     }
   }
 }