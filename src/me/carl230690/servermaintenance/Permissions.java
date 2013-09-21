 package me.carl230690.servermaintenance;
 
 import org.bukkit.permissions.Permission;
 
 public class Permissions
 {
   public Permission all = new Permission("servermaintenance.*");
   public Permission toggle = new Permission("servermaintenance.toggle");
   public Permission bypass = new Permission("servermaintenance.bypass");
   public Permission playerlist = new Permission("servermaintenance.player.list");
   public Permission playeralll = new Permission("servermaintenance.player.*");
   public Permission playeradd = new Permission("servermaintenance.player.add");
   public Permission playerremove = new Permission("serversmaintenance.player.remove");
   public Permission reloadconfig = new Permission("serversmaintenance.reload");
   public Permission update = new Permission("serversmaintenance.update");
 }