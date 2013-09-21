import java.io.*;
import java.util.*;

class SimpleStringTitle implements SongName
{	
	public String getSongName (SongTag tag) throws BadSongTagException, BadSongNameFormatException
	{
		return getSongName (tag, "%a - %s");
	}
	
	public String getSongName (SongTag tag, String format) throws BadSongTagException, BadSongNameFormatException
	{
		if (tag==null) throw new BadSongTagException ("null tag");
		if (format==null) throw new BadSongNameFormatException (format);
		String songname = new String ("");
		for (int i=0;i<format.length();i++)
		{
			if (format.charAt(i)=='%')
			{
				i++;
				if (i<format.length())
					switch (format.charAt(i))
					{
						case 't': songname = songname + tag.getTrack ();
							break;
						case 'a': songname = songname + tag.getArtist ();
							break;
						case 'T': songname = songname + tag.getTitle ();
							break;
						case 's': songname = songname + tag.getTitle ();
							break;
						case 'A': songname = songname + tag.getAlbum ();
							break;
						case 'c': songname = songname + tag.getComment();
							break;
						case 'y': songname = songname + tag.getYear ();
							break;
						case 'g': songname = songname + tag.getGenreString ();
							break;
						case '%': songname = songname + '%';
							break;
					}
			}
			else songname = songname + format.charAt (i);
		}
		return songname;
	}
}

class Id3Tag implements SongTag, Serializable
{

	public static final String[] GENRES = {
    "Blues", "Classic Rock", "Country", "Dance", "Disco", "Funk", "Grunge",
    "Hip-Hop", "Jazz", "Metal", "New Age", "Oldies", "Other", "Pop", "R&B",
    "Rap", "Reggae", "Rock", "Techno", "Industrial", "Alternative", "Ska",
    "Death Metal", "Pranks", "Soundtrack", "Euro-Techno", "Ambient",
    "Trip-Hop", "Vocal", "Jazz+Funk", "Fusion", "Trance", "Classical",
    "Instrumental", "Acid", "House", "Game", "Sound Clip", "Gospel",
    "Noise", "AlternRock", "Bass", "Soul", "Punk", "Space", "Meditative",
    "Instrumental Pop", "Instrumental Rock", "Ethnic", "Gothic",
    "Darkwave", "Techno-Industrial", "Electronic", "Pop-Folk",
    "Eurodance", "Dream", "Southern Rock", "Comedy", "Cult", "Gangsta",
    "Top 40", "Christian Rap", "Pop/Funk", "Jungle", "Native American",
    "Cabaret", "New Wave", "Psychadelic", "Rave", "Showtunes", "Trailer",
    "Lo-Fi", "Tribal", "Acid Punk", "Acid Jazz", "Polka", "Retro",
    "Musical", "Rock & Roll", "Hard Rock", "Folk", "Folk-Rock",
    "National Folk", "Swing", "Fast Fusion", "Bebob", "Latin", "Revival",
    "Celtic", "Bluegrass", "Avantgarde", "Gothic Rock", "Progressive Rock",
    "Psychedelic Rock", "Symphonic Rock", "Slow Rock", "Big Band",
    "Chorus", "Easy Listening", "Acoustic", "Humour", "Speech", "Chanson",
    "Opera", "Chamber Music", "Sonata", "Symphony", "Booty Bass", "Primus",
    "Porn Groove", "Satire", "Slow Jam", "Club", "Tango", "Samba",
    "Folklore", "Ballad", "Power Ballad", "Rhythmic Soul", "Freestyle",
    "Duet", "Punk Rock", "Drum Solo", "A Capella", "Euro-House", "Dance Hall",
    "Goa", "Drum & Bass", "Club-House ", "Hardcore", "Terror", "Indie ", "BritPop",
	"Negerpunk", "Polsk Punk", "Beat", "Christian Gangsta Rap", "Heavy Metal ",
	"Black Metal", "Crossover", "Contemporary Christian", "Christian Rock",
	"Merengue", "Salsa", "Trash Metal", "Anime", "Jpop", "Synthpop"
	};
	
	private String text;
	private int version=0;
	private byte id3=0;
	private byte track=-1;
	private String artist=new String("");
	private String song=new String("");
	private String album=new String("");
	private byte genre=-1;
	private short year=0;
	private String comment=new String("");
	private String filename;
	
	public Id3Tag (String filename) throws NoFileAccessException
	{
		version = 0;
		this.filename = filename;
		File f = new File (filename);
		text = f.getName();
		int punct = text.lastIndexOf(".");
		text=text.substring(0,punct);
		readTag (filename);
		if (artist.equals("") && song.equals("")) guessTag ();
		//System.out.println (toString());
	}
	
	public Id3Tag (String filename, byte track, String artist, String song, String album, short year, byte genre, String comment) throws NoFileAccessException
	{
		version = 11;
		this.filename = filename;
		File f = new File (filename);
		text = f.getName();
		int punct = text.lastIndexOf(".");
		text=text.substring(0,punct);
		if (!f.exists()) throw new NoFileAccessException (filename);
		this.track = track;
		this.album = album;
		this.song = song;
		this.year = year;
		this.genre = genre;
		this.comment = comment;
	}
	
	public void setTag (byte track, String artist, String song, String album, short year, byte genre, String comment)
	{
		if (track>0) version = 11; else version=1;
		this.track = track;
		this.album = album;
		this.song = song;
		this.year = year;
		this.genre = genre;
		this.comment = comment;
	}
	
	public String getOriginalFilename ()
	{
		return filename;
	}
	
	public void setVersion(int version) { this.version = version; }
	public void setId3(byte id3) { this.id3 = id3; }
	public void setTrack(byte track) { this.track = track; }
	public void setArtist(String artist) { this.artist = artist; }
	public void setSong(String song) { this.song = song; }
	public void setTitle(String song) { this.song = song; }
	public void setAlbum(String album) { this.album = album; }
	public void setGenre(byte genre) { this.genre = genre; }
	public void setYear(short year) { this.year = year; }
	public void setComment(String comment) { this.comment = comment; }
	
	public void setGenreString (String genreStr)
	{
		genre = -1;
		for (byte i=0; i<GENRES.length && genre<0; i++) 
			if (genreStr.equalsIgnoreCase(GENRES[i])) genre=i;
	}

	public int getVersion() { return (this.version); }
	public byte getId3() { return (this.id3); }
	public byte getTrack() { return (this.track); }
	public String getArtist() { return (this.artist); }
	public String getSong() { return (this.song); }
	public String getTitle() { return (this.song); }
	public String getAlbum() { return (this.album); }
	public byte getGenre() { return (this.genre); }
	public short getYear() { return (this.year); }
	public String getComment() { return (this.comment); }
	
	public String getGenreString() 
	{ 
		if (genre > -1 && genre < GENRES.length) return (GENRES[genre]);
			else return (""); 
	}
	
	private void guessTag ()
	{
		String artist=new String (this.artist);
		String song = new String (this.song);
		byte track = this.track;
		String album = new String (this.album);
		
		version = 0;
		
		String s;
		StringTokenizer st = new StringTokenizer (text,"-");
		switch (st.countTokens())
		{
			case 1: song=st.nextToken();
				break;
			case 3:
				s = st.nextToken();
				try 
				{
					track = (byte)Integer.parseInt(s);
					artist = st.nextToken();
					song = st.nextToken ();
			    }
			    catch (NumberFormatException ex) 
			    {
			    	StringTokenizer st2 = new StringTokenizer (s," .:");
			    	String s2 = st2.nextToken();
			    	try 
			    	{
			    		track = (byte)Integer.parseInt (s2);
			    		artist = st2.nextToken("-");
				    }
				    catch (NumberFormatException ex2) 
				    {
				    	artist = s;	
				    }
			    	album = st.nextToken();
					song = st.nextToken ();
			    }
				break;
			case 4:
				s = st.nextToken();
				try 
				{
					track = (byte)Integer.parseInt(s);
					artist = st.nextToken();
					album = st.nextToken();
					song = st.nextToken ();
			    }
			    catch (NumberFormatException ex) 
			    {
			    	StringTokenizer st2 = new StringTokenizer (s," .:");
			    	String s2 = st2.nextToken();
			    	try 
			    	{
			    		track = (byte)Integer.parseInt (s2);
			    		artist = st2.nextToken("-");
				    }
				    catch (NumberFormatException ex2) 
				    {
				    	artist = s;	
				    }
			    	album = st.nextToken();
					song = st.nextToken();
			    }
				break;
			default:
				if (st.countTokens()>1)
				{
					s = st.nextToken();
					StringTokenizer st2 = new StringTokenizer (s," .:");
			    	String s2 = st2.nextToken();
			    	try 
			    	{
			    		track = (byte)Integer.parseInt (s2);
			    		artist = st2.nextToken("-");
				    }
				    catch (NumberFormatException ex2) 
				    {
				    	artist = s;	
				    }
					song = st.nextToken ("-\0");
				}
				break;
		}
		
		this.track=track;
		this.artist=artist.trim();
		this.song=song.trim();
		this.album=album.trim();
	}
	
	public void readTag (String filename) throws NoFileAccessException
	{
		try
		{
			DataInputStream fin = new DataInputStream (new FileInputStream (filename));
			File fis = new File (filename);
			long pos = fis.length ()-128;
			if (pos>0)
			{
				fin.skip (pos);
				byte[] id3tag = new byte[128];
				if (fin.read (id3tag)==128);
				{
					//System.out.println (id3tag.length);
					//for (int i=0;i<128;i++) System.out.print(id3tag[i]+" ");
					//System.out.println ();
					//System.out.println ("\""+Utile.copyFromByte (id3tag, 0, 3)+"\"");
					if (Utile.copyFromByte (id3tag, 0, 3).equals ("TAG"))
					{
						version = 1;
						song = Utile.copyFromByte (id3tag, 3, 30).trim();
						artist = Utile.copyFromByte (id3tag, 33, 30).trim();
						album = Utile.copyFromByte (id3tag, 63, 30).trim();
						try
						{
							year = (short)Integer.parseInt (Utile.copyFromByte (id3tag, 93, 4));
						}
						catch (NumberFormatException ex)
						{
							year = 0;
						}
						comment = Utile.copyFromByte (id3tag, 97, 30).trim();
						if (id3tag[125]=='\0') 
						{
							version=11;
							track = id3tag[126];
						}
						else track=-1;
						genre = id3tag[127];
					}
				}
			}
			fin.close ();
		}
		catch (Exception ex)
		{
			throw new NoFileAccessException (filename);
		}
	}
	
	private byte[] packTag ()
	{
		byte[] id3tag = new byte[128];
		for (int i=0;i<128;i++) id3tag[i]='\0';
		id3tag[0]='T';
		id3tag[1]='A';
		id3tag[2]='G';
		for (int i=0;i<song.length() && i<30;i++) id3tag[3+i]=(byte)song.charAt (i);
		for (int i=0;i<artist.length() && i<30;i++) id3tag[33+i]=(byte)artist.charAt (i);
		for (int i=0;i<album.length() && i<30;i++) id3tag[63+i]=(byte)album.charAt (i);
		String years = String.valueOf(year);
		for (int i=0;i<years.length() && i<4;i++) id3tag[93+i]=(byte)years.charAt (i);
		for (int i=0;i<comment.length() && i<30;i++) id3tag[97+i]=(byte)comment.charAt (i);
		if (track > 0) 
		{
			id3tag [125]='\0';
			id3tag [126]=track;
		}
		id3tag[127]=genre;
		return id3tag;
	}
	
	public void deleteTag (String filename) throws NoFileAccessException
	{
		try
		{
			RandomAccessFile fout = new RandomAccessFile (filename, "rw");
			long pos = fout.length();
			if (hasTag (filename)) 
			{
				pos = pos - 128;
				//System.out.println (pos);
				if (pos>0) fout.setLength (pos);
			}
			fout.close ();
		}
		catch (Exception ex)
		{
			throw new NoFileAccessException (filename);
		}
	}
	
	public void writeTag (String filename) throws NoFileAccessException
	{
		try
		{
			RandomAccessFile fout = new RandomAccessFile (filename, "rw");
			long pos = fout.length();
			if (hasTag (filename)) pos = pos - 128;
			if (pos>0)
			{
				fout.skipBytes ((int)pos);
				byte[] id3tag = packTag ();
				//for (int i=0;i<128;i++) System.out.print ((char)id3tag[i]);
				//System.out.println ();
				fout.write (id3tag);
				fout.close ();
			}
		}
		catch (Exception ex)
		{
			//System.out.println (ex);
			throw new NoFileAccessException (filename);
		}
	}
	
	public boolean hasTag (String filename) throws NoFileAccessException
	{
		boolean hasTag = false;
		try
		{
			RandomAccessFile fout = new RandomAccessFile (filename, "r");
			long pos = fout.length()-128;
			if (pos>0)
			{
				fout.skipBytes((int)pos);
				byte[] id3tag = new byte[3];
				fout.read(id3tag);
				String tagid = Utile.copyFromByte(id3tag, 0, 3);
				if (tagid.equals ("TAG")) hasTag = true;
			}
			fout.close();
		}
		catch (Exception ex)
		{
			throw new NoFileAccessException (filename);
		}
		return hasTag;
	}
	
	public void editTag ()
	{
		new Id3TagEdit (this);
	}
	
	public String toString ()
	{
		return ""+((track>0)?track+" - ":"")+(!(artist.equals(""))?artist+" - ":"")+(!(song.equals(""))?song+"":"");
	}
}

class Mp3Message implements SongMessage, Serializable
{
	private String id;
	private String message;
	
	public Mp3Message (String id, String message)
	{
		this.id = id;
		this.message = message;
	}
	
	public String getID ()
	{
		return id;
	}
	
	public String getMessage ()
	{
		return message;
	}
	
	public String toString ()
	{
		return message;
	}
}

class Mp3Song implements Song, Serializable
{
	private String filename;
	private String id;
	private TreeMap messages = new TreeMap ();
	
	public String getSongType ()
	{
		return "audio/mpeg";
	}
	
	private Id3Tag id3;
	
	public Mp3Song (String filename) throws NoFileException, BadMpegFileException, NoFileAccessException
	{
		File f = new File (filename);
		if (!f.exists ()) throw new NoFileException ("filename");
		id3 = new Id3Tag (filename);
		this.filename = filename;
		id = Utile.makeID();
	}
	
	public Mp3Song (String filename, Id3Tag id3) throws NoFileException, BadMpegFileException, NoFileAccessException, BadId3TagException
	{
		File f = new File (filename);
		if (!f.exists ()) throw new NoFileException ("filename");
		this.filename = filename;
		if (id3==null) throw new BadId3TagException ("null tag");
		this.id3 = id3;
		id = Utile.makeID();
	}
	
	public Mp3Song (String filename, byte track, String artist, String song, String album, short year, byte genre, String comment) throws BadMpegFileException, NoFileAccessException
	{
		this.filename = filename;
		id3 = new Id3Tag (filename, track, artist, song, album, year, genre, comment);
		id = Utile.makeID();
	}
	
	public String getFilename()
	{
		return filename;
	}
	
	public String getID ()
	{
		return id;
	}
	
	private int getBitrate ()
	{
		// MAI VINE MODIFICAT
		return 128;
	}
	
	/*private void readId3Tag ()
	{
		File fis = new File (filename);
		long pos = fis.length ()-128;
		if (pos>0)
		try
		{
			DataInputStream fin = new DataInputStream (new FileInputStream (filename));
			System.out.println ("File Found - \""+filename+"\"");
			byte[] fh = new byte[4];
			fin.read (fh);
			System.out.println ("Frame Header");
			for (int i=0;i<4;i++) System.out.print(fh[i]+" ");
			System.out.println ();
			System.out.println ("Bits");
			for (int i=0;i<32;i++) 
			{
				System.out.print(((fh[i/8]>>(7-i%8)) & 1)+" ");
				if (i==11) System.out.println ();
			}
			System.out.println ();
			fin.skip (pos-4);
			byte[] id3tag = new byte[128];
			if (fin.read (id3tag)==128);
			{
				System.out.println (id3tag.length);
				for (int i=0;i<128;i++) System.out.print(id3tag[i]+" ");
				System.out.println ();
				System.out.println ("\""+Utile.copyFromByte (id3tag, 0, 3)+"\"");
				if (Utile.copyFromByte (id3tag, 0, 3).equals ("TAG"))
				{
					System.out.println ("Tag Found");
					artist = Utile.copyFromByte (id3tag, 3, 30);
					song = Utile.copyFromByte (id3tag, 33, 30);
					album = Utile.copyFromByte (id3tag, 63, 30);
					year = (short)Integer.parseInt (Utile.copyFromByte (id3tag, 93, 4));
					comment = Utile.copyFromByte (id3tag, 97, 30);
					genre = Utile.copyFromByte (id3tag, 127, 1);
				}
			}
			fin.close ();
		}
		catch (Exception ex)
		{
		}
		
	}*/
	
	public SongTag getTag ()
	{
		return id3;
	}
	
	public void deleteTag () throws NoFileAccessException
	{
		id3.writeTag (filename);
	}
	
	public void writeTag () throws NoFileAccessException
	{
		id3.writeTag (filename);
	}
	
	public String toString ()
	{
		return id3.toString();
	}
	
	// messages
	
	public void addMessage (String message)
	{
		if (messages.size() < ServerConfig.MSG_MAX)
		{
			if (message.length() > 160) message=message.substring (0, 160);
			message = Utile.allTrim(message);
			if (message!=null && message.length()>0) messages.put (Utile.makeID(), message);
		}
	}
	
	public String getMessage ()
	{
		String message = new String ("");
		try
		{
			String mkey = (String)messages.firstKey();
			if (mkey!=null)
			{
				message = (String)messages.get (mkey);
				messages.remove(mkey);
			}
		}
		catch (Exception ex)
		{
			message = new String ("");
		}
		return message;
	}
	
	public void deleteMessage (String id)
	{
		messages.remove(id);
	}
	
	public SongMessage[] getMessagesList ()
	{
		SongMessage[] sngmessages = new SongMessage[messages.size()];
		Iterator i = messages.entrySet().iterator();
		int l=0;
		while (i.hasNext())
		{
			String key = (String)((Map.Entry)i.next()).getKey();
			String message = (String)messages.get (key);
			sngmessages[l] = new Mp3Message (key, message);
			l++;
		}
		return sngmessages;
	}
}

public class Mp3Songs extends Thread implements SongSource
{	
	private static String types = "audio/mpeg";
	private String filename = null;
	private String id = "";
	private String name = "";
	private int kbits = 192;
	public byte BUF_MAX = 100;
	private Song currentSong;
	private int packetSize;
	Vector playlist = new Vector (0);
	Vector dataBuffer;
	Vector messagesBuffer;
	private int position=-1;
	private int quit = 0;
	private SongName songname;
	private String nameformat = "%a - %s";
	private String start_id;
	private boolean started = false;
	
	public Mp3Songs (String id, String name)
	{
		this.id = id;
		this.name = name;
		start_id = new String (id);
		packetSize = (1*(1024*kbits)/8);
		dataBuffer = new Vector (BUF_MAX);
		messagesBuffer = new Vector (BUF_MAX);
		for (int i=0;i<BUF_MAX;i++) 
		{
			dataBuffer.add (new SongPacket (new byte[0]));
			messagesBuffer.add (new String (""));
		}
		songname = new SimpleStringTitle ();
	}
	
	public Mp3Songs (String id, String name, int ps)
	{
		this (id, name);
		packetSize = ps;
	}
	
	public String getSourceID ()
	{
		return start_id;
	}
	
	public String getSourceName ()
	{
		return name;
	}
	
	public void setSourceID (String id)
	{
		this.id = id;
	}
	
	public void setSourceName (String name)
	{
		this.name = name;
	}
	
	public String getCurrentTitle ()
	{
		if (currentSong!=null)
		try
		{
			return songname.getSongName (currentSong.getTag(), nameformat);
		}
		catch (Exception ex)
		{
			return "";
		}
		else return "";
	}
	
	public Song getCurrentSong ()
	{
		return currentSong;
	}
	
	public byte[] getData (SongPosition pozitie)
	{
		int p=pozitie.getPosition ();
		long nts = pozitie.getTimeStamp ();
		if (p==-1) p = getPosition ();
		int i = (p+1) % BUF_MAX;
		SongPacket data = (SongPacket)dataBuffer.elementAt(i);
		long ts = data.getTimeStamp ();
		if (ts >= nts)
		{
			//System.out.println ("pozitia.p="+p+", pozitia.ts="+nts+", data.ts="+ts);
			pozitie.setPosition (i, ts);
			return data.getData ();
		}
		return new byte[0];
	}
	
	public String getMessage (SongPosition pozitie)
	{
		int p=pozitie.getPosition ();
		long nts = pozitie.getTimeStamp ();
		if (p==-1) p = getPosition ();
		int i = (p+1) % BUF_MAX;
		String message = (String)messagesBuffer.elementAt(i);
		SongPacket data = (SongPacket)dataBuffer.elementAt(i);
		long ts = data.getTimeStamp ();
		if (ts >= nts)
		{
			//System.out.println ("pozitia.p="+p+", pozitia.ts="+nts+", data.ts="+ts);
			return message;
		}
		return "";
	}
	
	public void setKBits (int kbits)
	{
		this.kbits = kbits;
	}
	
	public int getKBits ()
	{
		return kbits;
	}
	
	public int getPacketSize ()
	{
		return packetSize;
	}
	
	public String getSongType ()
	{
		return "audio/mpeg";
	}
	
	public int getPosition ()
	{
		return (BUF_MAX+position-3) % BUF_MAX;
	}
	
	public void quit()
	{
		quit = 1;
	}
	
	// songs & playlist
	
	public void addSong (Song song)
	{
		if (types.indexOf(song.getSongType())>-1) playlist.add (song);
	}
	
	public void addSong (String filename)
	{
		try 
		{
			Song song = new Mp3Song (filename);
			playlist.add (song);
			Messages.addMessage ("source", "Source: \""+id+"\", loading song "+filename);
	    }
	    catch (Exception ex) 
	    {
	    }
	}
	
	public void addSong (Song song, int p)
	{
		if (types.indexOf(song.getSongType())>-1) playlist.add (song);
		moveSong (playlist.size()-1, p);
	}
	
	public void addSong (String filename, int p)
	{
		try 
		{
			Song song = new Mp3Song (filename);
			playlist.add (song);
			moveSong (playlist.size()-1, p);
			Messages.addMessage ("source", "Source: \""+id+"\", loading song "+filename);
	    }
	    catch (Exception ex) 
	    {
	    }
	}
	
	public void addSongsFromLocation (String location)
	{
		File[] files = (new File (location)).listFiles();
		//playlist = new Vector (0);
		if (files!=null)
		{
			for (int i=0;i<files.length;i++) if (files[i].isFile()) 
			try
			{
				//playlist.add (new Mp3Song (files[i].toString()));
				addSong (files[i].toString());
			}
			catch (Exception ex)
			{
				
			}
		}
	}
	
	public void deleteSong (int p)
	{
		if (p >- 1 && p < playlist.size()) playlist.remove(p);
	}
	
	public void deleteSong (String filename)
	{
		for (int i=0;i<playlist.size();i++)
			if (((Song)playlist.elementAt(i)).getFilename().equalsIgnoreCase(filename)) playlist.remove(i);
	}
	
	public void deleteSong (Song song)
	{
		for (int i=0;i<playlist.size();i++)
			if ((Song)playlist.elementAt(i)==song) playlist.remove(i);
	}
	
	public void moveSong (int from, int to)
	{
		if (from > -1 && from < playlist.size() && to > -1)
		{
			Song song = (Song)playlist.elementAt(from);
			playlist.remove(from);
			if (to > playlist.size()) playlist.add(song);
				else playlist.insertElementAt(song, to);
		}
	}
	
	public void setFilename (String filename)
	{
		this.filename = filename;
	}
	
	public String getFilename ()
	{
		return filename;
	}
	
	public boolean loadSongs (String filename)
	{
		try
		{
			ObjectInputStream f = new ObjectInputStream (new FileInputStream (filename));
			id = (String)f.readObject ();
			start_id = new String (id);
			name = (String)f.readObject ();
			packetSize = f.readInt ();
			BUF_MAX = f.readByte ();
			kbits = f.readInt ();
			playlist = (Vector)f.readObject ();
			f.close ();
			this.filename = filename;
			Messages.addMessage ("source", "Source "+id+", playlist loaded from \""+filename+"\"");
		}
		catch (Exception ex)
		{
			Messages.addMessage ("source", "Source "+id+", couldn't load playlist from \""+filename+"\"");
			return false;
		}
		return true;
	}
	
	public void saveSongs ()
	{
		if (filename!=null) saveSongs (filename);
	}
	
	public void saveSongs (String filename)
	{
		try
		{
			ObjectOutputStream f = new ObjectOutputStream (new FileOutputStream (filename));
			f.writeObject (id);
			f.writeObject (name);
			f.writeInt (packetSize);
			f.writeByte (BUF_MAX);
			f.writeInt (kbits);
			f.writeObject (playlist);
			f.flush ();
			f.close ();
			Messages.addMessage ("source", "Source "+id+", playlist saved to \""+filename+"\"");
		}
		catch (Exception ex)
		{
			Messages.addMessage ("source", "Source "+id+", couldn't save playlist to \""+filename+"\"");
		}
	}
	
	public Song[] getSongsList ()
	{
		Song[] lista = new Song[playlist.size()];
		for (int i=0;i<playlist.size();i++) lista[i] = (Song)playlist.elementAt(i);
		return lista;
	}
	
	private void nextSong ()
	{
		int t = (int)(Math.random()*playlist.size());
		if (playlist.size()>0 && t<playlist.size()) currentSong = (Song)playlist.elementAt(t);
			else currentSong = null;
	}
	
	// thread functions
	
	public void start ()
	{
		start_id = new String (id);
		started = true;
		super.start ();
		Messages.addMessage ("source", "Source "+id+" started");
	}
	
	public void run()
	{
		int p = -1;
		yield();
		byte[] data;
		packetSize = (1*(1024*kbits)/8);
		int realPacketSize = packetSize;
		StringBuffer msgbuffer = new StringBuffer ("");
		StringBuffer text = new StringBuffer ("");;
		while (quit==0)
		{
			DataInputStream strin = null;
			try 
			{
				if (strin == null) 
				{
					nextSong ();
					if (currentSong!=null) strin = new DataInputStream (new FileInputStream (currentSong.getFilename()));
					if (strin!=null) 
					{
						Messages.addMessage ("source", "("+id+") Song title changed to \""+getCurrentTitle()+"\"");
						msgbuffer.append(" *** "+getCurrentTitle ());
						//strin.skip (3200000);
					}
				}
				data = new byte[packetSize];
				byte[] tempdata = new byte[0];
				int nbytes;
				while ((nbytes=strin.read (data, 0, realPacketSize))>0 && quit==0)
				{
					//System.out.println ("Read data ("+nbytes+"), p=" + p);
					/*if (nbytes<realPacketSize) 
					{
						byte[] temp = tempdata;
						tempdata = new byte[temp.length+nbytes];
						for (int l=0;l<temp.length;l++) tempdata[l] = temp[l];
						for (int l=0;l<nbytes;l++) tempdata[l+temp.length] = data[l];
						temp = null;
						realPacketSize = packetSize-nbytes;
						//System.out.println (tempdata.length);
						data = new byte[realPacketSize];
					}
					else*/
					{
						if (tempdata.length>0)
						{
							byte[] temp = tempdata;
							tempdata = new byte[temp.length+nbytes];
							for (int l=0;l<temp.length;l++) tempdata[l] = temp[l];
							for (int l=0;l<nbytes;l++) tempdata[l+nbytes] = data[l];
							temp = null;
							data = tempdata;
							tempdata = new byte[0];
						}
						realPacketSize = packetSize;
						p = (p+1) % BUF_MAX;
						dataBuffer.setElementAt(new SongPacket (data), p);
						//messages
						if (text.length()==0) 
						{
							text.append(msgbuffer);
							msgbuffer.delete (0, msgbuffer.length());
						}
						if (text.length()<45)
						{
							if (msgbuffer.length()>2)
							{
								int l = text.length();
								int l2 = 45-l;
								if (l2>msgbuffer.length()) l2 = msgbuffer.length();
								text.append(msgbuffer.substring(0, l2));
								msgbuffer.delete (0, l2);
								text.delete(0, 2);
							}
							else 
							{
								String ts = new String (" *** " + currentSong.getMessage());
								if (ts.length()<=5)	msgbuffer.append (" *** "+getCurrentTitle ());
									else msgbuffer.append(ts);
							}
						}
						else text.delete (0, 1);
						String mesaj = new String (text.toString());
						messagesBuffer.setElementAt(mesaj, p);
						//System.out.println ("data.length="+data.length);
						data = new byte[packetSize];
						position=p;
						sleep (1010);
					}
				}
				strin.close();
		    }
		    catch (Exception ex) 
		    {
		    	if (currentSong!=null) Messages.addMessage ("source", "Error in stream \""+currentSong.getFilename()+"\"");
		    	strin = null;
		    	try {sleep (100);} catch (Exception ex1) {}
		    }
		    if (quit==0) try { strin.close(); } catch (Exception ex) { };
		}
		started = false;
		Messages.addMessage ("source", "Source \""+id+" \"stopped");
	}
	
	public void down ()
	{
		saveSongs ();
		this.interrupt();
		quit = 1;
	}
}
