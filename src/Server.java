import java.io.*;
import java.util.*;
import java.net.*;

class ClientList
{
	private static int port;
	private static int maxClients;
	private TreeMap hostAllow;
	private TreeMap hostDeny;
	private TreeMap clients;
	
	public ClientList ()
	{
		maxClients = 9;
		port = 9200;
		hostAllow = new TreeMap ();
		hostDeny = new TreeMap ();
		clients = new TreeMap ();
	}
	
	public ClientList (int port, int maxClients)
	{
		this ();
		this.port = port;
		this.maxClients = maxClients;
	}
	
	public void setPort (int p)
	{
		port = p;
	}
	
	public void setMaxClients (int nr)
	{
		maxClients = nr;
	}
	
	public int getMaxClients ()
	{
		return maxClients;
	}
	
	public String register (TalkClient client)
	{
		String id = null;
		if (clients.size() < maxClients && client!=null)
		{
			id = Utile.makeID();
			if (id!=null) 
			{
				clients.put (id, client);
				Messages.addMessage ("client", "Registering client "+id);
			}
		}
		if (clients.size()==maxClients) Messages.addMessage ("server", "Server is full ("+maxClients+" clients)");
		return id;
	}
	
	public void unRegister (String id)
	{
		clients.remove (id);
		Messages.addMessage ("client", "Unregistering client "+id);
	}
	
	public TreeMap getClients ()
	{
		return clients;
	}
	
	public int getClientsNr ()
	{
		return clients.size ();
	}
	
	public static int getPort ()
	{
		return port;
	}
}

class TalkClient extends Thread
{
	private Socket client;
	private SongSource source;
	
	// confifs
	private String id = null;
	private int metaint;
	private String host;
	private String agent;
	
	public TalkClient (Socket clsocket)
	{
		client = clsocket;
		// configs
		metaint = 0;
	}
	
	public void run ()
	{
//		serversConfig.addServer(this);
		Messages.addMessage ("client", "New client connected");
		Messages.addMessage ("client", "Server has "+ServerConfig.getClients().getClientsNr()+" clients");
		try 
		{
			PrintWriter clout = new PrintWriter (client.getOutputStream(), true);
			DataOutputStream cldout = new DataOutputStream (client.getOutputStream());
			BufferedReader clin = new BufferedReader (new InputStreamReader (client.getInputStream()));
			int quit = 1;
			String data = clin.readLine ();
			//System.out.println (data);
			if (data.substring(0, 3).equalsIgnoreCase("GET"))
			{
				StringTokenizer st = new StringTokenizer (data, " ");
				if (st.countTokens()>1)
				{
					st.nextToken();
					StringBuffer source = new StringBuffer (st.nextToken());
					if (source.length()>0 && source.charAt(0)=='/') source.delete (0,1);
					//System.out.println (source);
					this.source = ServerConfig.getSoundSource (source.toString());
				}
			}
			if (source!=null && (id=ServerConfig.getClients().register(this))!=null)
			{
				String s=""+(char)clin.read();
				while (clin.ready()) 
				{
					s=s+(char)clin.read ();
					//String s = clin.readLine ();
					//if (!s.equals("")) System.out.println ("CLIENT: "+s);
				}
				
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
					dat.trim ();
					
					// Commands
					if (prop.equalsIgnoreCase("Icy-MetaData"))
					{
						try 
						{
							metaint = Integer.parseInt(dat);
					    }
					    catch (Exception ex) 
					    {
					    	metaint = 0;
					    }
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
				
				Messages.addMessage ("client", "ID: "+id+", Host: "+host+", Agent: \""+agent+"\", "+", "+"Source: \""+source.getSourceName()+"\"");
				
				if (metaint>0) metaint = source.getPacketSize ();
				
				clout.println ("ICY 200 OK");
				clout.println ("Server:"+ServerConfig.NAME+" ("+ServerConfig.VERSION+")");
				clout.println ("icy-notice1:TestStream v0.1 Engine");
				clout.println ("icy-url:http://"+InetAddress.getLocalHost().getAddress()+":"+ClientList.getPort ());
				clout.println ("icy-name:"+source.getSourceName());
				clout.println ("icy-pub:1");
				clout.println ("icy-br:"+source.getKBits());
				clout.println ("icy-metaint:"+metaint);
				clout.println ("Content-type:"+source.getSongType ());
				clout.println ("");
				
				SongPosition pozitia = new SongPosition();
				String songTitle = new String ("");
				
				byte songdata[];
				
				while (client.isConnected() && ServerConfig.getSterver().started())
				{
					String text = source.getMessage (pozitia);
					songdata = source.getData(pozitia);
					if (songdata.length>0) 
					{
						cldout.write (songdata);
						if (metaint > 0)
						{
							if (text.equals (songTitle))
							{
								cldout.write ((byte)0);
							}
							else
							{
								songTitle = text;
								String metadata = "StreamTitle='"+text+"';";//+source.getCurrentTitle()+"';";
    							byte lmetadata = (byte)((metadata.length())/16+1);
    							cldout.write ((byte)lmetadata);
    							for (int k=0;k<metadata.length();k++) cldout.write ((byte)metadata.charAt(k));
    							for (int j=0;j<lmetadata*16-metadata.length();j++) cldout.write (0);
    						}
    					}
						cldout.flush ();
					}
					else sleep (10);
					cldout.flush ();
				}
				/*String[] lista;
				File f = new File (streams);
				if (f.isDirectory())
				{
    				lista = f.list ();
    			}
    			else
    			{
    				lista = new String[1];
    				lista[0] = f.getName();
    				streams = f.getParent();
    			}
    			//System.out.println (streams+lista[0]);
				int i=0;
				int t, t_=-1;
				while (quit != 1)
				try
    			{
    				t = (int)(Math.random()*lista.length);
    				stream = lista[t];
    				DataInputStream din = new DataInputStream (new FileInputStream (streams+"/"+stream));
    				byte[] date = new byte[3*1024];
    				String metadata = null;
    				byte lmetadata=0;
    				while (din.read (date, 0, 3*1024)>0)
    				{
    					cldout.write (date, 0, 3*1024);
    					cldout.flush ();
    					//System.out.println ("Bytes Sent: "+(i+1)*3*1024+"    \r");
    					i=(i+1)%5;
    					if (i==0)
    					{
    						if (t!=t_)
    						{
    							System.out.println ("SERVER: Streaming -> \""+lista[t]+"\"");
    							metadata = "StreamTitle='"+lista[t]+"';";
    							lmetadata = (byte)((metadata.length())/16+1);
    							cldout.write ((byte)lmetadata);
    							for (int k=0;k<metadata.length();k++) cldout.write ((byte)metadata.charAt(k));
    							for (int j=0;j<lmetadata*16-metadata.length();j++) cldout.write (0);
    							cldout.flush();
    							t_=t;
    							//clout.flush ();
    							//System.out.println ("Metadata Sent\r");
    							//System.out.println ("LMetadata: "+lmetadata);
    							//sleep (1000);
    						}
    						else
    						{
    							cldout.write ((byte)0);
    							cldout.flush();
    						}
    					}
    				}
    				din.close();
    			}
    			catch (Exception ex)
    			{
    				System.out.println ("SERVER: Streaming stopped");
    				//System.out.println ("Error: "+ex);
    				break;
    			}*/
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
	    Messages.addMessage ("client", "Connection to "+host+" closed (ID: "+id+")");
	    ServerConfig.getClients().unRegister (id);
	    Messages.addMessage ("client", "Server has "+ServerConfig.getClients().getClientsNr()+" clients");
	}
}

public class Server extends Thread
{
	private ServerSocket server;
	private Socket client;
	private int port;
	private boolean up = false;
	private boolean listen = true;
	private long starttime;
	
	public Server (int port)
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
			starttime = System.currentTimeMillis();
			server.setSoTimeout(1000);
			try
			{
				if (ServerConfig.autoName.equalsIgnoreCase ("yes")) ServerConfig.domainName = InetAddress.getLocalHost().getHostName();
				if (ServerConfig.autoAddress.equalsIgnoreCase ("yes")) ServerConfig.ipAddress = InetAddress.getLocalHost().getHostAddress();
			}
			catch (Exception ex)
			{
			}
			Messages.addMessage ("server", "Server started");
			Messages.addMessage ("server", "Listening to port "+port+" at "+ServerConfig.domainName+" ("+ServerConfig.ipAddress+")");
			listen = true;
			while (listen) 
			{
				try
				{
					new TalkClient (server.accept()).start();
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
			Messages.addMessage ("server", "Error while listening to port "+port+", is is used by an other program.");
			//System.out.println ("Error: "+ex);
		}
		Messages.addMessage ("server", "Server stopped");
		up = false;
	}
	
	public boolean started()
	{
		return up;
	}
	
	public long getStartTime ()
	{
		return starttime;	
	}
	
	public void down ()
	{
		listen = false;
	}
}