import java.io.*;
import java.net.*;
import javax.swing.*;

public class TestStream
{
	public static void main(String[] args)
    {   	
    	//JFrame.setDefaultLookAndFeelDecorated(true);
    	//JDialog.setDefaultLookAndFeelDecorated(true);
    	String configFile = new String ("");
    	
    	// params
    	if (args.length>0)
    	{
    		configFile = args[0];
    	}
    	   	
    	// server
    	Messages.addMessage ("main", ServerConfig.NAME+" version "+ServerConfig.VERSION);
    	Messages.addMessage ("main", "starting up...");
    	
    	// config
    	ServerConfig.loadServerConfig(configFile);
    	
    	// server
		if (ServerConfig.startServer.equalsIgnoreCase ("yes"))
		{
			Server server = new Server (ServerConfig.getClients().getPort ());
			ServerConfig.setServer(server);
			server.start ();
			WebServerConfig webserver = new WebServerConfig (ServerConfig.getConfigPort());
			ServerConfig.setWebServer (webserver);
			webserver.start ();
		}
		
		// interface
		if (ServerConfig.startConsole.equalsIgnoreCase("yes"))
		{
   			ServerGraphics servergraphics = new ServerGraphics ();
   		}
    	
   		// songs
   		//SongSource songs = new Mp3Songs ("radio9200", "Radio 9200");
   		//songs.addSongsFromLocation ("D:\\Muzica\\muzic");
   		//songs.saveSongs ("songs.r9l");
   		//songs.loadSongs ("songs.r9l");
   		//ServerConfig.addSongSource(songs);
   		
   		//songs.start();
   		//SongSource songs2 = new Mp3Songs ("songs2", "Un alt stream");
   		//songs2.addSongsFromLocation("e:\\temp\\songs");
   		//ServerConfig.addSongSource(songs2);
   		// songs2.start ();
    }
}
