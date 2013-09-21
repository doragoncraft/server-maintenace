 package me.carl230690.servermaintenance;
 
 import java.io.File;
 import java.util.logging.Level;
 import java.util.logging.Logger;
 import org.bukkit.Server;
 import org.bukkit.command.PluginCommand;
 import org.bukkit.configuration.file.FileConfiguration;
 import org.bukkit.configuration.file.FileConfigurationOptions;
 import org.bukkit.plugin.PluginDescriptionFile;
 import org.bukkit.plugin.PluginManager;
 import org.bukkit.plugin.java.JavaPlugin;
 
 public class Main extends JavaPlugin
 {
   public final Logger logger = Logger.getLogger("Minecraft");
   public static Main plugin;
   protected ListStore AllowedPlayers;
   protected Logger log;
   protected UpdateChecker updateChecker;
   public boolean versionDiff = false;
 
   public void onEnable()
   {
     loadConfiguration();
     loadAllowedPlayers();
     PluginManager pm = getServer().getPluginManager();
     getCommand("maintenance").setExecutor(new CommandMaintenance(this));
     getCommand("servermaintenance").setExecutor(new CommandMaintenance(this));
     getCommand("m").setExecutor(new CommandMaintenance(this));
     getCommand("sm").setExecutor(new CommandMaintenance(this));
     pm.registerEvents(new PlayerListener(this), this);
     pm.registerEvents(new MOTDListener(this), this);
     pm.addPermission(new Permissions().all);
     pm.addPermission(new Permissions().bypass);
     pm.addPermission(new Permissions().playeradd);
     pm.addPermission(new Permissions().playerremove);
     pm.addPermission(new Permissions().playerlist);
     pm.addPermission(new Permissions().playeralll);
     pm.addPermission(new Permissions().toggle);
     pm.addPermission(new Permissions().update);
     PluginDescriptionFile pdf = getDescription();
     this.logger.info("[" + pdf.getName() + "] Has Been Enabled!");
     this.log = getLogger();
     if (getConfig().getBoolean("CheckForUpdates")) {
       this.log.info("Checking for updates...");
       try {
         this.updateChecker = new UpdateChecker(this, "https://github.com/doragoncraft/server-maintenace");
         if (this.updateChecker.updateNeeded()) {
           this.log.info("============================================");
           this.log.log(Level.INFO, "A new version is avaible! Version {0}! Current Version: {1}", new Object[] { this.updateChecker.getVersion(), getDescription().getVersion() });
           this.log.log(Level.INFO, "Download It At: {0}", this.updateChecker.getLink());
           this.log.log(Level.INFO, "Changelog: \n{0}", this.updateChecker.getChageLog());
           this.log.info("============================================");
           this.versionDiff = true;
         } else {
           this.log.info("Your ServerMaintenance is up to date.");
           this.versionDiff = false;
         }
       }
       catch (Exception localException)
       {
      }
     }
   }
 
   private void loadAllowedPlayers()
   {
     String pluginFolder = getDataFolder().getAbsolutePath();
     new File(pluginFolder).mkdirs();
     this.AllowedPlayers = new ListStore(new File(pluginFolder + File.separator + "allowed-players.txt"));
     this.AllowedPlayers.load();
   }
 
   private void loadConfiguration()
   {
     File folder = new File(getDataFolder(), null);
     if (!folder.exists()) {
       folder.mkdir();
     }
     getConfig().options().copyDefaults(true);
     saveConfig();
   }
 
   public void onDisable()
   {
     saveConfig();
     PluginDescriptionFile pdfFile = getDescription();
     this.logger.info("[" + pdfFile.getName() + "] Has Been Disabled!");
     PluginManager pm = getServer().getPluginManager();
     pm.removePermission(new Permissions().all);
     pm.removePermission(new Permissions().bypass);
     pm.removePermission(new Permissions().playeradd);
     pm.removePermission(new Permissions().playerremove);
     pm.removePermission(new Permissions().playerlist);
     pm.removePermission(new Permissions().playeralll);
     pm.removePermission(new Permissions().toggle);
     pm.removePermission(new Permissions().update);
   }
 }