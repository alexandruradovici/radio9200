import java.io.*;

class Utile
{
	public static String copyFromByte (byte[] data, int from, int n)
	{
		String s = new String ("");
		if (from+n<=data.length)
		{
			for (int i=from;i<from+n && data[i]!=0;i++) s=s+(char)data[i];
		}
		return s;
	}
	
	public static byte[] copyBytes (byte[] data, int from, int n)
	{
		byte[] data2 = new byte[0];
		if (from+n<=data.length)
		{
			data2 = new byte[n];
			for (int i=from;i<from+n;i++) data2[i-from] = data[i];
		}
		return data2;
	}
	
	public static void copyBytes (byte[] dest, byte[] src, int st)
	{
		for (int i=st;i<src.length && (i-st)<dest.length;i++) dest[i] = src[i-st];
	}

	/*static String copyFromByteToString (byte[] data, int from, int n)
	{
		String s = new String ("");
		if (from+n<=data.length)
		{
			for (int i=from;i<=n && data[i]!='\0';i++) s=s+(char)data[i];
		}
		return s;
	}*/
	
	public static String makeID ()
	{
		String s = String.valueOf (System.currentTimeMillis());
		for (int i=0;i<16;i++)
		{
			int nr = (int)(16*(16*Math.random ()/16));
			if (nr<10) s=s+String.valueOf (nr);
			else
			{
				switch (nr)
				{
					case 10:s=s+'A';
						break;
					case 11:s=s+'B';
						break;
					case 12:s=s+'C';
						break;
					case 13:s=s+'D';
						break;
					case 14:s=s+'E';
						break;
					case 15:s=s+'F';
						break;
				}
			}
		}
		return s;
	}
	
	public static String allTrim (String s)
	{
		String ns = null;
		try 
		{
			StringBuffer sb = new StringBuffer (s);
			while (sb.length()>0 && sb.charAt(0)==' ') sb.delete(0,1);
			while (sb.length()>0 && sb.charAt(sb.length()-1)==' ') sb.delete(sb.length()-1, sb.length());
			ns=sb.toString();
	    }
	    catch (Exception ex) 
	    {
	    }
	    return ns;
	}
	
	public static int strToInt (String s, int n)
	{
		int nr=n;
		try
		{
			nr = Integer.parseInt(s);
		}
		catch (Exception ex)
		{
			nr=n;
		}
		return nr;
	}
}

class FilterMp3 implements FilenameFilter
{
	public boolean accept (File f, String s)
	{
		if (f.isFile() && ((s.substring (s.length()-4)).equalsIgnoreCase ("mp3"))) return true;
		return false;
	}
}

interface SongTag
{
	public String getOriginalFilename ();
	
	public void setTrack(byte track);
	public void setArtist(String artist);
	public void setTitle(String title);
	public void setAlbum(String album);
	public void setYear(short year);
	public void setComment(String comment);
	public void setGenreString(String genre);
	
	public byte getTrack();
	public String getArtist();
	public String getTitle();
	public String getAlbum();
	public short getYear();
	public String getComment();
	public String getGenreString();
	
	public void readTag (String filename) throws NoFileAccessException;
	public void deleteTag (String filename) throws NoFileAccessException;
	public void writeTag (String filename) throws NoFileAccessException;
	
	public void editTag ();
}

class SongPosition
{
	private int p;
	private long ts;
	
	public SongPosition ()
	{
		ts = 0;
		p=-1;
	}
	
	public int getPosition ()
	{
		return p;
	}
	
	public long getTimeStamp ()
	{
		return ts;
	}
	
	public void setPosition (int np, long nts)
	{
		p = np;
		ts = nts;
	}
}

class SongPacket 
{
	private byte data[];
	private long ts;
	
	public SongPacket (byte[] d)
	{
		data = d;
		ts = System.currentTimeMillis();
	}
	
	public long getTimeStamp ()
	{
		return ts;
	}
	
	public byte[] getData ()
	{
		return data;
	}
}

interface SongName
{
	public String getSongName (SongTag tag) throws BadSongTagException, BadSongNameFormatException;
	public String getSongName (SongTag tag, String format) throws BadSongTagException, BadSongNameFormatException;
}

interface SongMessage
{
	public String getID();
	public String getMessage();
}

interface Song
{
	// song data & type
	public String getSongType ();
	public String getFilename ();
	public String getID ();
	public SongTag getTag ();
	
	// messages
	public void addMessage (String message);
	public String getMessage ();
	public void deleteMessage (String id);
	public SongMessage[] getMessagesList ();
}

interface SongSource
{
	public String getSourceID ();
	public String getSourceName ();
	public void setSourceID (String id);
	public void setSourceName (String name);
	public int getPosition ();
	public String getCurrentTitle ();
	public Song getCurrentSong ();
	public byte[] getData (SongPosition pozitie);
	public String getMessage (SongPosition pozitie);
	public void setKBits (int kbits);
	public int getKBits ();
	public int getPacketSize ();
	public String getSongType ();
	// songs & playlist
	public void setFilename (String filename);
	public String getFilename ();
	public void addSong (Song song);
	public void addSong (Song song, int p);
	public void addSong (String filename);
	public void addSong (String filename, int p);
	public void addSongsFromLocation (String location);
	public void deleteSong (int p);
	public void deleteSong (Song song);
	public void deleteSong (String filename);
	public boolean loadSongs (String filename);
	public void saveSongs ();
	public void saveSongs (String filename);
	public Song[] getSongsList ();
	public void moveSong (int from, int to);
	// start & quit
	public void start ();
	public void down ();
}

interface WebPage
{	
	public void setCookies (String cookies);
	public void setData (String data);
	public String getPage ();
}
