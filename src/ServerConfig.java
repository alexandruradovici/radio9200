import java.io.*;
import java.util.*;
import java.net.*;

class WebPages
{
	public static WebPage getPage (String s)
	{
		//System.out.println (s);
		if (s.equals ("") || s.equals("index.html"))
		{
			return new WebPageIndex ();
		}
		else
		if (s.equals("radio.html"))
		{
			return new WebPageRadio ();
		}
		else
		if (s.equals("listen.pls"))
		{
			return new WebPageListen ();
		}
		if (s.equals("about.html"))
		{
			return new WebPageAbout ();
		}
		else return new WebPageError ();
	}
}

public class ServerConfig
{
	public static String NAME = "Radio9200";
	public static String VERSION = "0.1";
	
	private static Server server;
	private static WebServerConfig webserver;
	
	private static ClientList clients = new ClientList ();
	private static String adminPassword = new String ("radio9200");
	private static int configPort = 9201;
	public static int maxConfigClients = 9;
	public static int MSG_MAX = 100;
	public static String startConsole = new String ("yes");
	public static String startServer = new String ("yes");
	public static String autoName = new String ("yes");
	public static String autoAddress = new String ("yes");
	public static String autoWebName = new String ("yes");
	public static String autoWebAddress = new String ("yes");
	
	public static String domainName = new String ("localhost");
	public static String ipAddress = new String ("127.0.0.1");
	public static String domainWebName = new String ("localhost");
	public static String ipWebAddress = new String ("127.0.0.1");
	
	private static TreeMap sources = new TreeMap();
	
	public static ClientList getClients ()
	{
		return clients;
	}
	
	public static boolean addSongSource (SongSource sng)
	{
		boolean res = false;
		if (sng!=null) 
		{
			try
			{
				if ((SongSource)sources.get (sng.getSourceID())==null) 
				{
					sources.put(sng.getSourceID(), sng);
					Messages.addMessage ("server", "Source "+sng.getSourceID()+" registered (Name: \""+sng.getSourceName()+"\", Type: \""+sng.getSongType()+"\")"); 
					res = true;
				}
			}
			catch (Exception ex)
			{
				Messages.addMessage ("server", "Failed to register a song source");
			}
		}
		return res;
	}
	
	public static SongSource getSoundSource (String name)
	{
		return (SongSource)sources.get (name);
	}
	
	public static void deleteSoundSource (String name)
	{
		SongSource song = null;
		if ((song=(SongSource)sources.remove (name))!=null) 
			Messages.addMessage ("server", "Source "+song.getSourceID()+" unregistered (Name: \""+song.getSourceName()+"\", Type: \""+song.getSongType()+"\")"); 
	}
	
	public static Vector getSongSources ()
	{
		Vector list = new Vector(0);
		Iterator i = sources.entrySet().iterator();
		while (i.hasNext())
		{
			String key = (String)((Map.Entry)i.next()).getKey();
			SongSource songs = (SongSource)sources.get (key);
			if (songs!=null) list.add (songs);
		}
		return list;
	}
	
	public static void setServer (Server s)
	{
		server = s;
	}
	
	public static Server getSterver ()
	{
		return server;
	}
	
	public static void setWebServer (WebServerConfig s)
	{
		webserver = s;
	}
	
	public static WebServerConfig getWebSterver ()
	{
		return webserver;
	}
	
	public static void setConfigPort (int p)
	{
		configPort = p;
	}
	
	public static int getConfigPort ()
	{
		return configPort;
	}
	
	public static void setAdminPassword (String s)
	{
		adminPassword = s;
	}
	
	public static String getAdminPassword ()
	{
		return adminPassword;
	}
	
	public static void shutdown ()
	{
		Messages.addMessage ("main", "Saving configuration");
		saveServerConfig ();
		Iterator i = sources.entrySet().iterator();
		while (i.hasNext())
		{
			String key = (String)((Map.Entry)i.next()).getKey();
			SongSource songs = (SongSource)sources.get (key);
			if (songs!=null) songs.down();
			i.remove ();
		}
		if (server!=null) server.down ();
		Messages.addMessage ("main", "Waiting 1.5 seconds for sources to stop");
		try { Thread.sleep (1500); } catch (Exception ex) {}
		Messages.addMessage ("main", "Shutting down...");
		Messages.saveMessagesToFile ("messages.log");
		System.exit (0);
	}
	
	public static void loadServerConfig (String configFile)
	{
		if (configFile.equals("")) configFile = "radio9200.cfg";
		try 
		{
			BufferedReader fin = new BufferedReader (new FileReader (configFile));
			String s = fin.readLine();
			while (s!=null)
			{
				s = Utile.allTrim(s);
				if (s.length()>0 && s.charAt(0)!=';')
				{
					StringTokenizer st = new StringTokenizer (s, "=");
					if (st.hasMoreTokens())
					{
						String option = st.nextToken();
						option = Utile.allTrim (option);
						String data = new String ("");
						data = s.substring (s.indexOf("=")+1);
						data = Utile.allTrim (data);
						if (option.equals("AudioPort"))
						{
							clients.setPort(Utile.strToInt (data, 9200));
						}
						if (option.equals("MaxClients"))
						{
							clients.setMaxClients(Utile.strToInt (data, 9));
						}
						if (option.equals("MaxMessages"))
						{
							ServerConfig.MSG_MAX = Utile.strToInt (data, 100);
						}
						if (option.equals("WebPort"))
						{
							configPort = Utile.strToInt (data, 9201);
						}
						if (option.equals("MaxWebClients"))
						{
							maxConfigClients = Utile.strToInt (data, 9);
						}
						if (option.equals("AudioPort"))
						{
							clients.setPort(Utile.strToInt (data, 9200));
						}
						if (option.equals("AdminPassword"))
						{
							adminPassword = new String (data);
						}
						if (option.equals("ServerName"))
						{
							autoName = new String ("no");
							domainName = new String (data);
						}
						if (option.equals("ServerIp"))
						{
							autoAddress = new String ("no");
							ipAddress = new String (data);
						}
						if (option.equals("WebServerName"))
						{
							autoWebName = new String ("no");
							domainWebName = new String (data);
						}
						if (option.equals("WebServerIp"))
						{
							autoWebAddress = new String ("no");
							ipWebAddress = new String (data);
						}
						if (option.equals("StartConsole"))
						{
							startConsole = new String (data);
						}
						if (option.equals("StartServer"))
						{
							startServer = new String (data);
						}
						if (option.equals("AddMp3Source"))
						{
							try 
							{
								SongSource source = new Mp3Songs ("","");
								if (source.loadSongs(data))
								{
									if (addSongSource (source))	source.start ();
								}
						    }
						    catch (Exception ex) 
						    {
						    }
						}
					}
				}
				s = fin.readLine();
			}
			fin.close ();
	    }
	    catch (Exception ex) 
	    {
	    	Messages.addMessage("main", "Couldn't load configuration file ("+configFile+"), using default settings");
	    }
	}
	
	public static void saveServerConfig ()
	{
		try 
		{
			PrintWriter fout = new PrintWriter (new FileWriter ("radio9200.cfg"));
			fout.println ("; Radio9200 Configuration File");
			fout.println ();
			fout.println ("; AudioPort - this is the port for the streaming data");
			fout.println ("AudioPort = "+clients.getPort());
			fout.println ();
			fout.println ("; ServerName - this is the server's domain name. Leave commented for");
			fout.println ("; autodetection.");
			if (autoName.equalsIgnoreCase("yes")) fout.print ("; ");
			fout.println ("ServerName = "+domainName);
			fout.println ();
			fout.println ("; ServerIp - this is the server's ip address. Leave commented for");
			fout.println ("; autodetection.");
			if (autoAddress.equalsIgnoreCase("yes")) fout.print ("; ");
			fout.println ("ServerIp = "+ipAddress);
			fout.println ();
			fout.println ("; MaxClients - this is the maximmum number of clients that the server will accept.");
			fout.println ("; Setting a too high number may reduce the server's performance.");
			fout.println ("MaxClients = "+clients.getMaxClients());
			fout.println ();
			fout.println ("; MaxMessages - this is the maximmum number of messages that the server will.");
			fout.println ("; accept for each song. Setting a too high number may cause problems.");
			fout.println ("MaxMessages = "+ServerConfig.MSG_MAX);
			fout.println ();
			fout.println ("; WebPort - this is the port for the HTTP/HTML interface (web)");
			fout.println ("WebPort = "+configPort);
			fout.println();
			fout.println ("; WebServerName - this is the web server's domain name. Leave commented for");
			fout.println ("; autodetection.");
			if (autoWebName.equalsIgnoreCase("yes")) fout.print ("; ");
			fout.println ("WebServerName = "+domainWebName);
			fout.println ();
			fout.println ("; WebServerIp - this is the web server's ip address. Leave commented for");
			fout.println ("; autodetection.");
			if (autoWebAddress.equalsIgnoreCase("yes")) fout.print ("; ");
			fout.println ("WebServerIp = "+ipWebAddress);
			fout.println ();
			fout.println ("; MaxWebClients - this is the maximmum number of clients that can access");
			fout.println ("; the web interface at once. Setting a too high number may reduce the server's");
			fout.println ("; performance.");
			fout.println ("MaxWebClients = "+maxConfigClients);
			fout.println ();
			fout.println ("; AdminPassword - this is the password for the web administration interface.");
			fout.println ("; The username is admin. The default password is radio9200");
			fout.println ("AdminPassword = "+adminPassword);
			fout.println ();
			fout.println ("; StartConsole - this specifies if the server's graphical interface should be started.");
			fout.println ("; options yes/no. Default is yes. Se ho if you want to use it as a background server.");
			fout.println ("StartConsole = "+startConsole);
			fout.println ();
			fout.println ("; StartServer - this specifies if the server should be started at startup");
			fout.println ("; options yes/no. Default is yes.");
			fout.println ("StartServer = "+startServer);
			fout.println ();
			fout.println ("; Song Sources List");
			fout.println ("; AddMp3Source - adds an mp3 song source");
			Iterator i = sources.entrySet().iterator();
			while (i.hasNext())
			{
				try
				{
					String key = (String)((Map.Entry)i.next()).getKey();
					SongSource songs = (SongSource)sources.get (key);
					if (((Mp3Songs)songs)!=null) fout.println ("AddMp3Source = "+songs.getFilename ());
				}
				catch (Exception e)
				{
				}
			}
			fout.println ();
			fout.flush ();
			fout.close ();
	    }
	    catch (Exception ex) 
	    {
	    	Messages.addMessage("main", "Couldn't load configuration file (radio9200.cfg), using default settings");
	    }
	}
}