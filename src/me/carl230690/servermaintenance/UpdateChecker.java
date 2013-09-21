 package me.carl230690.servermaintenance;
 
 import java.io.InputStream;
 import java.net.MalformedURLException;
 import java.net.URL;
 import javax.xml.parsers.DocumentBuilderFactory;
 import org.w3c.dom.Document;
 import org.w3c.dom.Node;
 import org.w3c.dom.NodeList;
 
 public class UpdateChecker
 {
   private Main plugin;
   private URL filesFeed;
   private String version;
   private String link;
   private String ChangeLog;
 
   public UpdateChecker(Main plugin, String url)
   {
     this.plugin = plugin;
     try
     {
       this.filesFeed = new URL(url);
     } catch (MalformedURLException e) {
       e.printStackTrace();
     }
   }
 
   public boolean updateNeeded()
   {
     try {
       InputStream input = this.filesFeed.openConnection().getInputStream();
       Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(input);
 
       Node latestFile = document.getElementsByTagName("item").item(0);
       NodeList children = latestFile.getChildNodes();
 
       this.version = children.item(1).getTextContent().replaceAll("[a-zA-Z, ]", "");
       this.link = children.item(3).getTextContent();
       this.ChangeLog = children.item(5).getTextContent().replaceAll("<p>", "").replaceAll("</p>", "").replaceAll("</br>", "").replaceAll("<br>", "").replaceAll("<ul>", "").replaceAll("<li>", "").replaceAll("</ul>", "").replaceAll("</li>", "");
 
       if (!this.plugin.getDescription().getVersion().equals(this.version))
         return true;
     }
     catch (Exception e)
     {
       e.printStackTrace();
     }
 
     return false;
   }
   public String getVersion() {
     return this.version;
  }
   public String getLink() {
     return this.link;
   }
   public String getChageLog() {
     return this.ChangeLog;
   }
 }
