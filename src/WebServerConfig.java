import java.net.*;
import java.io.*;
import java.util.*;

class Decrypt 
{
	String s;
	private String a=new String("");
	private String b=new String("");
	private String c=new String("");
	private TreeMap tree;
	private Integer key=null;
	
	public Decrypt(String s)
	{
		this.s = s;
		tree=new TreeMap();
		tokanizare ();
	}
	
	private void tokanizare()
	{
		StringTokenizer st1=new StringTokenizer(s,"&");
		while (st1.hasMoreTokens())
		{
			a=st1.nextToken();
			StringTokenizer st2=new StringTokenizer(a,"=");
			int n2=st2.countTokens();
			if (n2>1)
			{
				b=st2.nextToken();
				c=st2.nextToken();
			}
			else
			{
				b=st2.nextToken();
				c=new String ("");
			}
			c=decriptare(c);
			if (c==null) c = new String ("");
			tree.put(b,c);
		}
	}
	private String decriptare(String s)
	{
		char caracterul;
		String x;
		StringBuffer sb=new StringBuffer(s);
		for(int i=0;i<sb.length();i++)
		{
			if (sb.charAt(i)=='%')
			{
				byte b = (byte)((transformare(sb.toString().toLowerCase().charAt(i+1)))*16+transformare(sb.toString().toLowerCase().charAt(i+2)));
				sb.setCharAt(i,(char)b);
				sb.replace(i+1,i+3,"");
			}
			if (sb.charAt (i)=='+')
			{
				sb.replace(i, i+1, " ");
			}
		}
		x=sb.toString();	
		return x;
		//System.out.println (tree.get(key));
	}
	
	private int transformare (char c)
	{
		int numar=0;
		switch(c)
		{
		case 'a': numar=10;
		break;
		case 'b': numar=11;
		break; 
		case 'c': numar=12;
		break;
		case 'd': numar=13;
		break;
		case 'e': numar=14;
		break;
		case 'f': numar=15;
		break;
		case '1': numar=1;
		break;
		case '2': numar=2;
		break;
		case '3': numar=3;
		break;
		case '4': numar=4;
		break;
		case '5': numar=5;
		break;
		case '6': numar=6;
		break;
		case '7': numar=7;
		break;
		case '8': numar=8;
		break;
		case '9': numar=9;
		break;
		}
		return numar;
	}
	
	public void setVar (String var, String val)
	{
		tree.put (var, val);
	}
	
	public String getVar (String s)
	{
		String res = (String)tree.get (s);
		return (res!=null)?res:null;
	}
}

// webpages

class WebPageStyle
{
	public static String getStyleSheet ()
	{
		return
		"<style>"+
			"A:link"+
			"{"+
				"font-family:arial;"+
				"font-size:14px;"+
				"font-weight:normal;"+
				"color:#4A7FC7;"+
				"text-decoration:none;"+
			"}"+
			"A:visited"+
			"{"+
				"font-family:arial;"+
				"font-size:14px;"+
				"font-weight:normal;"+
				"color:#4A7FC7;"+
				"text-decoration:none;"+
			"}"+
			"A:hover"+
			"{"+
				"font-family:arial;"+
				"font-size:14px;"+
				"font-weight:normal;"+
				"color:#4A7FC7;"+
				"background-color:#F5F5F5;"+
				"border-style:solid;"+
				"border-width:1px;"+
				"border-color:#BEBEBE;"+
			"}"+
			".pagina"+
			"{"+
				"margin-top:0px;"+
				"margin-bottom:0px;"+
				"margin-left:10px;"+
				"margin-right:10px;"+
				"background-color:#FFFFFF;"+
				"font-family:arial;"+
				"font-size:12px;"+
				"font-weight:normal;"+
				"color:#000000;"+
			"}"+
			".server"+
			"{"+
				"margin-top:10px;"+
				"margin-left:0px;"+
				"font-family:arial;"+
				"font-size:40px;"+
				"font-weight:bold;"+
				"color:#EC5D2B;"+
			"}"+
			".server2"+
			"{"+
				"margin-top:-10px;"+
				"margin-left:10px;"+
				"margin-right:-10px;"+
				"font-family:arial;"+
				"font-size:14px;"+
				"font-weight:normal;"+
				"font-:bold;"+
				"color:#093A82;"+
			"}"+
			".bara"+
			"{"+
				"margin-top:-2px;"+
				"margin-left:-10px;"+
				"color:#4A7FC7;"+
			"}"+
			"P"+
			"{"+
				"text-indent:0.5in;"+
			"}"+
			".tabel"+
			"{"+
				"background-color:#FFFFFF;"+
				"font-family:arial;"+
				"font-size:12px;"+
				"font-weight:normal;"+
				"color:#000000;"+
			"}"+
			".tabel_radio"+
			"{"+
				"border-style:solid;"+
				"border-width:1px;"+
				"border-color:#BEBEBE;"+
				"font-family:arial;"+
				"font-size:12px;"+
				"font-weight:normal;"+
				"color:#000000;"+
			"}"+
			".cap_tabel_radio"+
			"{"+
				"background-color:#F5F5F5;"+
				"font-family:arial;"+
				"font-size:12px;"+
				"font-weight:normal;"+
				"color:#000000;"+
			"}"+
			".tabel_radio_col_1"+
			"{"+
				"background-color:#C2D7FD;"+
				"font-family:arial;"+
				"font-size:12px;"+
				"font-weight:normal;"+
				"color:#000000;"+
			"}"+
			".tabel_radio_col_2"+
			"{"+
				"background-color:#FFEFDA;"+
				"font-family:arial;"+
				"font-size:12px;"+
				"font-weight:normal;"+
				"color:#000000;"+
			"}"+
		"</style>";
	}
}

class BaseWebPage implements WebPage
{
	protected Decrypt data;
	protected Decrypt cookies;
	
	public BaseWebPage ()
	{
		this ("", "");
	}
	
	public BaseWebPage (String data, String cookies)
	{
		setData (data);
		setCookies (cookies);
	}
	
	public void setCookies (String cookies)
	{
		this.cookies = new Decrypt (cookies);
	}
	
	public void setData (String data)
	{
		this.data = new Decrypt (data);
	}
	
	public String getPage ()
	{
		return "";
	}
	
	protected String serverLogo ()
	{
		return "<div class=\"server\" align=\"left\">Radio 9200</div>"+
			   "<div class=\"server2\" align=\"left\">MultiSource Audio Server</div>"+
			   "<hr class=\"bara\">";
	}
	
	protected String showMenu ()
	{
		return "<center><table class=\"tabel\" width=100% classpadding=0 cellspacing=0><tr>"+
			   "<td align=center valign=top><a href=index.html>&nbsp;Status&nbsp;</a></td>"+
			   "<td align=center valign=top><a href=radio.html>&nbsp;Radio&nbsp;Stations&nbsp;</a></td>"+
			   "<td align=center valign=top><a href=listen.pls>&nbsp;Listen&nbsp;</a></td>"+
			   "<td align=center valign=top><a href=admin.html>&nbsp;Server&nbsp;Admin&nbsp;</a></td>"+
			   "<td align=center valign=top><a href=about.html>&nbsp;About&nbsp;Radio&nbsp;9200&nbsp;</a></td>"+
			   "</tr></td></table></center>"+
			   "<hr class=\"bara\">";
	}
}

class WebPageIndex extends BaseWebPage
{
	public String getPage ()
	{
		String serverUrlName = new String ("");
		String serverUrl = new String ("");
		try 
		{
			serverUrlName = ServerConfig.domainName;
			serverUrl = ServerConfig.ipAddress;
		}
		catch (Exception ex) 
		{
		}
		String s = new String ("");
		s=s+"Content-type: text/html\n\n";
		s=s+"<html><head><title>Radio 9200 Server</title>"+WebPageStyle.getStyleSheet()+"</head>";
		s=s+"<body class=\"pagina\">"+serverLogo();
		s=s+showMenu ()+"<b>Status</b><br><br>";
		long luptime = (System.currentTimeMillis() - ServerConfig.getSterver().getStartTime())/1000;
		String uptime = ""+luptime/(24*3600)+" days, "+(luptime/(3600))%60+" hours, "+(luptime/60)%60+" minutes, "+luptime%60+" seconds";
		s=s+"<center><table class=\"tabel\" cellpadding=2 cellspacing=2>"+
			"<tr><td valign=top align=right><b>Server Name</b></td><td valign=top align=left>"+ServerConfig.NAME+"</td></tr>"+
			"<tr><td valign=top align=right><b>Version</b></td><td valign=top align=left>"+ServerConfig.VERSION+"</td></tr>"+
			"<tr><td valign=top align=right><b>Uptime</b></td><td valign=top align=left>"+uptime+"</td></tr>"+
			"<tr><td valign=top align=right><b>Listening at host</b></td><td valign=top align=left>"+serverUrlName+"</td></tr>"+
			"<tr><td valign=top align=right><b>Listening at address</b></td><td valign=top align=left>"+serverUrl+"</td></tr>"+
			"<tr><td valign=top align=right><b>Listening on port</b></td><td valign=top align=left>"+ServerConfig.getClients().getPort()+"</td></tr>"+
			"<tr><td valign=top align=right><b>RadioStations</b></td><td valign=top align=left>"+ServerConfig.getSongSources().size()+"</td></tr>"+
			"<tr><td valign=top align=right><b>Current clients</b></td><td valign=top align=left>"+ServerConfig.getClients().getClientsNr()+" / "+ServerConfig.getClients().getMaxClients()+"</td></tr>"+
			"</table></center>";
		s=s+"</body>";
		s=s+"</html>";
		return s;
	}
}

class WebPageRadio extends BaseWebPage
{
	public String getPage ()
	{
		String radio = data.getVar ("radio");
		String songid = data.getVar ("song");
		int page=0;
		try 
		{
			page = Integer.parseInt(data.getVar ("page"));
	    }
	    catch (Exception ex) 
	    {
	    }
		String serverUrlName = new String ("");
		String serverUrl = new String ("");
		try 
		{
			serverUrlName = ServerConfig.domainName;
			serverUrl = "http://"+ServerConfig.ipAddress+":"+ServerConfig.getClients().getPort()+"/";
		}
		catch (Exception ex) 
		{
		}
		String s = new String ("");
		s=s+"Content-type: text/html\n\n";
		if (radio!=null && songid!=null)
		{
			SongSource song = ServerConfig.getSoundSource (radio);
			if (song == null) radio = null;
			else
			{
				Song currentSong = null;
				boolean found = false;
				Song[] songs = song.getSongsList();
				for (int i=0;i<songs.length && !found;i++)
					if (songid.equalsIgnoreCase(songs[i].getID()))
					{
						found = true;
						currentSong = songs[i];
					}
				if (!found) songid = null;
				else
				{
					String mesaj = data.getVar("mesaj");
					if (mesaj!=null) 
					{
						mesaj.trim ();
						currentSong.addMessage(mesaj);
					}
					s=s+"<html><head><title>Radio 9200 Server - Messages</title>"+WebPageStyle.getStyleSheet()+"</head>";
					s=s+"<body class=\"pagina\">"+serverLogo();
					s=s+showMenu ()+"<b>Song Information</b><br><br>";
					SongTag tag = currentSong.getTag();
					s=s+"<center><table class=\"tabel\" cellpadding=2 cellspacing=2>"+
					"<tr><td valign=top align=right><b>Artist</b></td><td valign=top align=left>"+tag.getArtist()+"</td></tr>"+
					"<tr><td valign=top align=right><b>Title</b></td><td valign=top align=left>"+tag.getTitle()+"</td></tr>"+
					"<tr><td valign=top align=right><b>Album</b></td><td valign=top align=left>"+tag.getAlbum()+"</td></tr>"+
					"<tr><td valign=top align=right><b>Year</b></td><td valign=top align=left>"+tag.getYear()+"</td></tr>"+
					"<tr><td valign=top align=right><b>Genre</b></td><td valign=top align=left>"+tag.getGenreString()+"</td></tr>"+
					"<tr><td valign=top align=right><b>Comment</b></td><td valign=top align=left>"+tag.getComment()+"</td></tr>"+
					"<tr><td valign=top align=right><b>Messages</b></td><td valign=top align=left>"+currentSong.getMessagesList().length+"</td></tr>"+
					"<tr><td valign=top align=right><b>Playlist</b></td><td valign=top align=left><a href=radio.html?radio="+radio+">&nbsp;"+song.getSourceName()+"&nbsp;</a></td></tr>"+
					"</table>";
					s=s+"<br><br>";
					s=s+"<table class=\"tabel\" cellpadding=2 cellspacing=2>"+
						"<form action=\"radio.html\" method=GET><input type=hidden name=radio value=\""+radio+"\"><input type=hidden name=song value=\""+songid+"\">"+
						"<tr><td valign=middle align=left>Messages</td><td valign=middle align=left><input type=text name=mesaj maxlength=160></td></tr>"+
						"<tr><td colspan=2 valign=middle align=right><input type=submit value=\"Send\"></td></tr>"+
						"</table><br><br></center>";
					s=s+"<center><table class=\"tabel_radio\" width=100% cellpadding=3 cellspacing=2>";
					s=s+"<tr class=\"cap_tabel_radio\"><td valign=top align=center width=120><b><i>ID</i></b>";
					s=s+"<td valign=top align=center><b><i>Write Message</i></b></td></tr>";
					SongMessage[] messages = currentSong.getMessagesList();
					for (int l=page;l<messages.length && l<100;l++)
					{
						if (l%2==1) s=s+"<tr class=\"tabel_radio_col_1\">";
							else s=s+"<tr class=\"tabel_radio_col_2\">";
						s=s+"<td valign=top align=left>"+messages[l].getID()+"</td>";
						s=s+"<td valign=top align=left><i>"+messages[l].getMessage()+"</i></td></tr>";
					}
					s=s+"</table>";
					s=s+"<br>";
					s=s+"</body></html>";
				}
					
			}
		}
		if (radio!=null && songid==null)
		{
			SongSource song = ServerConfig.getSoundSource (radio);
			if (song == null) radio = null;
			else
			{
				int nrs = song.getSongsList().length;
				s=s+"<html><head><title>Radio 9200 Server - \""+song.getSourceName()+"\"</title>"+WebPageStyle.getStyleSheet()+"</head>";
				s=s+"<body class=\"pagina\">"+serverLogo();
				s=s+showMenu ()+"<b>Playlist Information</b><br><br>";
				s=s+"<center><table class=\"tabel\" cellpadding=2 cellspacing=2>"+
					"<tr><td valign=top align=right><b>Name</b></td><td valign=top align=left>"+song.getSourceName()+"</td></tr>"+
					"<tr><td valign=top align=right><b>ID</b></td><td valign=top align=left>"+song.getSourceID()+"</td></tr>"+
					"<tr><td valign=top align=right><b>Type</b></td><td valign=top align=left>"+song.getSongType()+"</td></tr>"+
					"<tr><td valign=top align=right><b>Quality (KBits)</b></td><td valign=top align=left>"+song.getKBits()+"</td></tr>"+
					"<tr><td valign=top align=right><b>Songs</b></td><td valign=top align=left>"+nrs+"</td></tr>"+
					"<tr><td valign=top align=right><b>Now Playing</b></td><td valign=top align=left><a href=radio.html?radio="+radio+"&song="+song.getCurrentSong().getID()+">&nbsp;"+song.getCurrentTitle()+"&nbsp;</a></td></tr>"+
					"<tr><td valign=top align=right><b>URL</b></td><td valign=top align=left><a href=listen.pls?id="+song.getSourceID()+">&nbsp;http://"+serverUrlName+":"+ServerConfig.getClients().getPort()+"/"+song.getSourceID()+"&nbsp;</a></td></tr>"+
					"<tr><td valign=top align=right><b>Address</b></td><td valign=top align=left><a href=listen.pls?id="+song.getSourceID()+">&nbsp;"+serverUrl+song.getSourceID()+"&nbsp;</a></td></tr>"+
					"</table></center>";
				s=s+"<br><br>";
				s=s+"<center>";
				String addr = "radio.html?radio="+radio+"&page=";
				s=s+"Goto playlist page<br>";
				int u = 1;
				if (nrs%30==0) u = 0;
				for (int l=0;l<nrs/30+u;l++) if (l!=page) s=s+"<a href="+addr+l+">"+(l+1)+"</a> ";
												else s=s+""+(l+1)+" ";
				s=s+"<br><br>";
				s=s+"<center><table class=\"tabel_radio\" width=100% cellpadding=3 cellspacing=2>";
				s=s+"<tr class=\"cap_tabel_radio\"><td valign=top align=center><b><i>Artist</i></b>";
				s=s+"<td valign=top align=center><b><i>Title</i></b></td>";
				s=s+"<td valign=top align=center><b><i>Album</i></b></td>";
				s=s+"<td width=40 valign=top align=center><b><i>Year</i></b></td>";
				s=s+"<td valign=top align=center><b><i>Genre</i></b></td>";
				s=s+"<td valign=top align=center><b><i>Comment</i></b></td>";
				s=s+"<td valign=top align=center><b><i>Message</i></b></td></tr>";
				Song[] songs = song.getSongsList();
				if (songs!=null)
				for (int l=page;l<songs.length && l<page+30;l++)
				{
					SongTag songTag = songs[l].getTag();
					if (l%2==1) s=s+"<tr class=\"tabel_radio_col_1\">";
						else s=s+"<tr class=\"tabel_radio_col_2\">";
					s=s+"<td valign=top align=left>"+songTag.getArtist()+"</td>";
					s=s+"<td valign=top align=left><i>"+songTag.getTitle()+"</i></td>";
					s=s+"<td valign=top align=left>"+songTag.getAlbum()+"</td>";
					s=s+"<td valign=top align=right>"+songTag.getYear()+"</td>";
					s=s+"<td valign=top align=left>"+songTag.getGenreString()+"</td>";
					s=s+"<td valign=top align=left>"+songTag.getComment()+"</td>";
					s=s+"<td valign=top align=center><a href=radio.html?radio="+radio+"&song="+songs[l].getID()+">&nbsp;Read&nbsp;</a>("+songs[l].getMessagesList().length+")</td></tr>";
				}
				s=s+"</table>";
				s=s+"<br>";
				s=s+"Goto playlist page<br>";
				for (int l=0;l<nrs/30+u;l++) if (l!=page) s=s+"<a href="+addr+l+">"+(l+1)+"</a> ";
												else s=s+""+(l+1)+" ";
				s=s+"<br><br>";
				s=s+"</center>";
				s=s+"</body>";
				s=s+"</html>";
			}
		}
		if (radio==null)
		{
			s=s+"<html><head><title>Radio 9200 Server - Radio Stations</title>"+WebPageStyle.getStyleSheet()+"</head>";
			s=s+"<body class=\"pagina\">"+serverLogo();
			s=s+showMenu ()+"<b>Playlists</b><br><br>";
			s=s+"<center><table class=\"tabel_radio\" width=100% cellpadding=3 cellspacing=2>";
			s=s+"<tr class=\"cap_tabel_radio\"><td valign=top align=center><b><i>Name</i></b>";
			s=s+"<td valign=top align=center><b><i>ID</i></b></td>";
			s=s+"<td width=30 valign=top align=center><b><i>Quality (KBits)</i></b></td>";
			s=s+"<td valign=top align=center><b><i>Songs</i></b></td>";
			s=s+"<td valign=top align=center><b><i>Current Song</i></b></td>";
			s=s+"<td valign=top align=center><b><i>Listen</i></b></td></tr>";
			Vector list = ServerConfig.getSongSources();
			for (int l=0;l<list.size();l++)
			{
				SongSource songs = (SongSource)list.elementAt(l);
				if (songs!=null)
				{
					if (l%2==1) s=s+"<tr class=\"tabel_radio_col_1\">";
						else s=s+"<tr class=\"tabel_radio_col_2\">";
					s=s+"<td valign=top align=left><a href=radio.html?radio="+songs.getSourceID()+">&nbsp;<b>"+songs.getSourceName()+"</b>&nbsp;</a></td>";
					s=s+"<td valign=top align=center>"+songs.getSourceID()+"</td>";
					s=s+"<td valign=top align=right>"+songs.getKBits()+"</td>";
					s=s+"<td valign=top align=right>"+songs.getSongsList().length+"</td>";
					s=s+"<td valign=top align=left><i>"+songs.getCurrentTitle()+"</i></td>";
					s=s+"<td valign=top align=center><a href=listen.pls?id="+songs.getSourceID()+">&nbsp;Listen&nbsp;</a></td></tr>";
				}
			}
			s=s+"</table></center>";
			s=s+"</body>";
			s=s+"</html>";
		}
		return s;
	}
}

class WebPageListen extends BaseWebPage
{
	public String getPage ()
	{
		String id = data.getVar ("id");
		Vector list = ServerConfig.getSongSources();
		String s = new String ("");
		s=s+"Content-type: audio/x-scpls\n";
		s=s+"Connection: close\n\n";
		s=s+"[playlist]\n";
		String serverUrl = new String ("");
		try 
		{
			serverUrl = "http://"+ServerConfig.ipAddress+":"+ServerConfig.getClients().getPort()+"/";
		}
		catch (Exception ex) 
		{
		}
		for (int i=0;i<list.size();i++)
		{
			SongSource songs = (SongSource)list.elementAt(i);
			if (songs!=null) if (id==null) 
								{
									s=s+"File"+(i+1)+"="+serverUrl;
									s=s+songs.getSourceID();
									s=s+"\n";
									s=s+"Title"+(i+1)+"="+songs.getSourceName()+"\n";
								}
								else if (id.equals(songs.getSourceID())) 
								{
									s=s+"File"+(i+1)+"="+serverUrl;
									s=s+songs.getSourceID();
									s=s+"\n";
									s=s+"Title"+(i+1)+"="+songs.getSourceName()+"\n";
								}
		}
		s=s+"NumberOfEntries="+list.size()+"\n";
		s=s+"Version=2\n";
		return s;
	}
}

class WebPageError extends BaseWebPage
{	
	public String getPage ()
	{
		String s = new String ("");
		s=s+"Content-type: text/html\n\n";
		s=s+"<html><head><title>Radio 9200 Server - Page not found</title>"+WebPageStyle.getStyleSheet()+"</head>";
		s=s+"<body class=\"pagina\">"+serverLogo()+showMenu()+"<b>Page not found</b><p>The page that you have requested does not exists.</body>";
		s=s+"</html>";
		return s;
	}
}

// servers

class TalkBrowser extends Thread
{
	private static int webclients=0;
	private final static int maxwebclients = 9;
	private static TreeMap logins = new TreeMap ();
	private Socket client;
	private WebPage page;
	
	// configs
	private String host;
	private String agent;
	
	public TalkBrowser (Socket clsocket)
	{
		client = clsocket;
		// configs
	}
	
	public void run ()
	{
//		serversConfig.addServer(this);
	if (webclients<ServerConfig.maxConfigClients)
	{
		webclients++;
		Messages.addMessage ("webclient", "New client connected");
		Messages.addMessage ("webclient", "Server has "+ServerConfig.getClients().getClientsNr()+" clients");
		try 
		{
			PrintWriter clout = new PrintWriter (client.getOutputStream(), true);
			DataOutputStream cldout = new DataOutputStream (client.getOutputStream());
			BufferedReader clin = new BufferedReader (new InputStreamReader (client.getInputStream()));
			String pageData = new String ("");
			String data = clin.readLine ();
			//System.out.println (data);
			if (data.substring(0, 3).equalsIgnoreCase("GET"))
			{
				StringTokenizer st = new StringTokenizer (data, " ?");
				if (st.countTokens()>1)
				{
					st.nextToken();
					StringBuffer page = new StringBuffer (st.nextToken());
					if (st.hasMoreTokens()) pageData = st.nextToken();
					if (page.length()>0 && page.charAt(0)=='/') page.delete (0,1);
					//System.out.println (source);
					this.page = WebPages.getPage (page.toString());
					//System.out.println (pageData);
					this.page.setData(pageData);
				}
			}
			if (page!=null)
			{
				String s=""+(char)clin.read();
				while (clin.ready()) 
				{
					s=s+(char)clin.read ();
					//String s = clin.readLine ();
					//if (!s.equals("")) System.out.println ("CLIENT: "+s);
				}
				//System.out.println (s);
				
				StringTokenizer st = new StringTokenizer (s, "\n\r");
				while (st.hasMoreTokens())
				{
					String linie = st.nextToken ();
					//System.out.println (linie);
					StringTokenizer cmd = new StringTokenizer (linie, ":");
					String prop = new String ("");
					String dat = new String ("");
					if (cmd.hasMoreTokens()) 
					{
						prop = cmd.nextToken();
						int p = linie.indexOf(":");
						dat = linie.substring(p+1);
					}
					if (dat.length()>0 && dat.charAt (0)==' ') dat = dat.substring(1);
					dat.trim();
					
					// Commands
					if (prop.equalsIgnoreCase("Cookie"))
					{
						page.setCookies (dat);
					}
					
					if (prop.equalsIgnoreCase("Host"))
					{
						host = dat;
					}
					
					if (prop.equalsIgnoreCase("User-Agent"))
					{
						agent = dat;
					}
					
				}
				
				Messages.addMessage ("webclient", "Host: "+host+", Agent: \""+agent+"\"");
				
				clout.println ("HTTP/1.1 200 OK");
				clout.println (page.getPage ());
				clout.flush ();
				
			}
			clout.flush();
			clout.close();
			clin.close();
			client.close();	
	    }
	    catch (Exception ex) 
	    {
	    	//Messages.addMessage ("client", "Connection error (from :"+host+")");
	    }
	    webclients--;
	    Messages.addMessage ("webclient", "Connection to "+host+" closed");
	    webclients--;
	 }
	}
	
	public void start ()
	{
		if (webclients<maxwebclients)
		{
			webclients++;
			super.start ();
		}
		else 
		{
			try {if (client!=null) client.close();} catch (Exception ex) {};
		}
	}
}

class WebPageAbout extends BaseWebPage
{
	public String getPage ()
	{
		String serverUrlName = new String ("");
		String serverUrl = new String ("");
		try 
		{
			serverUrlName = ServerConfig.domainName;
			serverUrl = ServerConfig.ipAddress;
		}
		catch (Exception ex) 
		{
		}
		String s = new String ("");
		s=s+"Content-type: text/html\n\n";
		s=s+"<html><head><title>Radio 9200 Server - About</title>"+WebPageStyle.getStyleSheet()+"</head>";
		s=s+"<body class=\"pagina\">"+serverLogo();
		s=s+showMenu ()+"<b>About</b><br><br>";
		s=s+"<center><table class=\"tabel\" cellpadding=2 cellspacing=2>"+
			"<tr><td valign=top align=right><b>Name</b></td><td valign=top align=left>"+ServerConfig.NAME+"</td></tr>"+
			"<tr><td valign=top align=right><b>Version</b></td><td valign=top align=left>"+ServerConfig.VERSION+"</td></tr>"+
			"<tr><td valign=top align=right><b>Server Type</b></td><td valign=top align=left>Multiplatform and Multisource Audio Streaming Server</td></tr>"+
			"<tr><td valign=top align=right><b>Created By</b></td><td valign=top align=left>Project Info</td></tr>"+
			"<tr><td valign=top align=right><b>Date</b></td><td valign=top align=left>Thursday, 24 June, 2004</td></tr>"+
			"<tr><td valign=top align=right><b>Server Programming</b></td><td valign=top align=left><a href=mailto:alex@photostorm.net>Alexandru RADOVICI</a></td></tr>"+
			"<tr><td valign=top align=right><b>Interface & WebData Programming</b></td><td valign=top align=left><a href=mailto:gunnar@photostorm.net>Costin Alexandru IONITA</a></td></tr>"+
			"<tr><td valign=top align=right><b>Interface Programming</b></td><td valign=top align=left><a href=mailto:outkast@photostorm.net>Tudor BERCEA</a></td></tr>"+
			"<tr><td valign=top align=right><b>Webpage</b></td><td valign=top align=left><a href=http://www.photostorm.net/radio9200>www.photostorm.net/radio9200</a></td></tr>"+
			"<tr><td valign=top align=right><b>Bugs and Suggestions</b></td><td valign=top align=left><a href=mailto:radio9200@photostorm.net>radio9200@photostorm.net</a></td></tr>"+
			"</table></center>";
		s=s+"</body>";
		s=s+"</html>";
		return s;
	}
}

public class WebServerConfig extends Thread
{
	private ServerSocket server;
	private Socket client;
	private int port;
	private boolean up = false;
	private boolean listen = true;
	
	public WebServerConfig (int port)
	{
		setPort (port);
	}
	
	public void setPort (int newPort)
	{
		port = newPort;
	}
	
	public void run()
	{
		try
		{
			server = new ServerSocket (port);
			up = true;
			try
			{
				if (ServerConfig.autoWebName.equalsIgnoreCase ("yes")) ServerConfig.domainWebName = InetAddress.getLocalHost().getHostName();
				if (ServerConfig.autoWebAddress.equalsIgnoreCase ("yes")) ServerConfig.ipWebAddress = InetAddress.getLocalHost().getHostAddress();
			}
			catch (Exception ex)
			{
			}
			server.setSoTimeout(1000);
			Messages.addMessage ("webserver", "Server strated");
			Messages.addMessage ("webserver", "Listening to port "+port+" at "+ServerConfig.domainWebName+" ("+ServerConfig.ipWebAddress+")");
			listen = true;
			while (listen) 
			{
				try
				{
					new TalkBrowser (server.accept()).start();
				}
				catch (SocketTimeoutException ex)
				{
				}
			}
			server.close();
		}
		catch (Exception ex)
		{
			//System.out.println (ex);
			Messages.addMessage ("webserver", "Error while listening to port "+port+", is is used by an other program.");
			//System.out.println ("Error: "+ex);
		}
		Messages.addMessage ("webserver", "Server stopped");
		up = false;
	}
	
	public boolean started()
	{
		return up;
	}
	
	public void down ()
	{
		listen = false;
	}
}