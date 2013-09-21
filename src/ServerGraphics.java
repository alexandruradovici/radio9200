import java.io.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.Timer;

class Id3TagEdit extends JFrame
{
	JLabel l0,l1,l2,l3,l4,l5,l6,l7;
	JTextField t0,t1,t2,t3,t4,t5,t7;
	JComboBox c;
	JCheckBox b1;
	JButton save, delete;
	public Id3TagEdit(final Id3Tag id3tag)
	{
		super("Edit Id3Tag");
		JPanel panou = new JPanel ();
		panou.setBorder(BorderFactory.createTitledBorder("ID3 Tag v1"));
		panou.setLayout (new BoxLayout (panou, BoxLayout.Y_AXIS));
		t0=new JTextField(id3tag.getOriginalFilename(), 20);
		t0.setEditable(false);
		l0=new JLabel("File");
		l0.setAlignmentX (JLabel.LEFT);
		t0.setAlignmentX (JTextField.RIGHT);
		t1=new JTextField(((id3tag.getTrack()>0)?""+id3tag.getTrack():""), 3);
		t1.setMaximumSize(new Dimension (3, 20));
		t1.setAlignmentX (JLabel.RIGHT);
		l1=new JLabel("Track");
		l1.setAlignmentX (JLabel.RIGHT);
		t2=new JTextField(id3tag.getTitle(), 18);
		l2=new JLabel("Title");
		l2.setAlignmentX (JLabel.LEFT);
		t3=new JTextField(id3tag.getArtist(), 18);
		t3.setAlignmentX (JLabel.RIGHT);
		l3=new JLabel("Artist");
		l3.setAlignmentX (JLabel.LEFT);
		t4=new JTextField(id3tag.getAlbum(), 18);
		t4.setAlignmentX (JLabel.RIGHT);
		l4=new JLabel("Album");
		l4.setAlignmentX (JLabel.LEFT);
		t5=new JTextField(""+id3tag.getYear(), 4);
		l5=new JLabel("Year");
		l6=new JLabel("Genre");
		c=new JComboBox();
		c.setAlignmentX(JComboBox.RIGHT_ALIGNMENT);
		c.addItem ("");
		for (int l=0;l<Id3Tag.GENRES.length;l++) c.addItem (Id3Tag.GENRES[l]);
		c.setSelectedIndex (id3tag.getGenre()+1);
		l7=new JLabel("Comments");
		t7=new JTextField(id3tag.getComment(), 18);
		JPanel p0=new JPanel();
		p0.setLayout (new BoxLayout (p0, BoxLayout.X_AXIS));
		panou.add (p0);
		p0.add (Box.createHorizontalGlue());
		p0.add(l0);
		p0.add (Box.createRigidArea(new Dimension (10, 0)));
		p0.add(t0);
		JPanel p1=new JPanel();
		p1.setLayout (new BoxLayout (p1, BoxLayout.X_AXIS));
		panou.add (p1);
		p1.add (Box.createHorizontalGlue());
		p1.add(l1);
		p1.add(t1);
		JPanel p2=new JPanel();
		p2.setLayout (new BorderLayout (10,2));
		panou.add(p2);
		p2.add(l2, BorderLayout.WEST);
		p2.add(t2, BorderLayout.EAST);
		JPanel p3=new JPanel(new BorderLayout(10,2));
		panou.add(p3);
		p3.add(l3, BorderLayout.WEST);
		p3.add(t3, BorderLayout.EAST);
		JPanel p4=new JPanel(new BorderLayout(10,2));
		panou.add(p4);
		p4.add(l4, BorderLayout.WEST);
		p4.add(t4, BorderLayout.EAST);
		JPanel p5=new JPanel(new FlowLayout(2,10,1));
		panou.add(p5);
		p5.add(l5);
		p5.add(t5);
		p5.add(l6);
		p5.add(c);
		JPanel p6=new JPanel(new BorderLayout(10,2));
		panou.add(p6);
		p6.add(l7, BorderLayout.WEST);
		p6.add(t7, BorderLayout.EAST);
		JPanel p7=new JPanel ();
		p7.setLayout (new BoxLayout (p7, BoxLayout.X_AXIS));
		save = new JButton ("Save");
		save.addActionListener(
			new ActionListener()
			{
				public void actionPerformed (ActionEvent ev)
				{
					try
					{
						try 
						{
							id3tag.setTrack ((byte)Integer.parseInt (t1.getText()));
					    }
					    catch (Exception ex) 
					    {
					    	id3tag.setTrack ((byte)0);
					    }
						id3tag.setTitle (t2.getText());
						id3tag.setArtist (t3.getText());
						id3tag.setAlbum (t4.getText());
						try 
						{
							id3tag.setYear ((short)Integer.parseInt (t5.getText()));
					    }
					    catch (Exception ex) 
					    {
					    	id3tag.setYear ((byte)0);
					    }
					    id3tag.setGenre ((byte)(c.getSelectedIndex()-1));
						id3tag.setComment (t7.getText());
						id3tag.writeTag (id3tag.getOriginalFilename());
					}
					catch (Exception ex)
					{
						JOptionPane.showMessageDialog(null, "Could'n save ID3 Tag v1...", "ID3 Tag v1 Editor", 0);
					}
					dispose ();
				}
			}
		);
		delete = new JButton ("Remove");
		delete.addActionListener(
			new ActionListener()
			{
				public void actionPerformed (ActionEvent ev)
				{
					try
					{
						id3tag.deleteTag (id3tag.getOriginalFilename());
					}
					catch (Exception ex)
					{
						JOptionPane.showMessageDialog(null, "Could'n delete ID3 Tag v1...", "ID3 Tag v1 Editor", 0);
					}
					dispose ();
				}
			}
		);
		p7.add (Box.createHorizontalGlue());
		p7.add (save);
		p7.add (Box.createRigidArea (new Dimension (10, 0)));
		p7.add (delete);
		panou.add (p7);
	
		setContentPane(panou);
		//setSize(300,200);
    	addWindowListener(
    	new WindowAdapter()
    	{
    		public void windowClosing(WindowEvent e)
	    	{
    			dispose ();
    		}
    	});
    	setResizable(false);
    	pack ();
    	show();	
	}
}

class MessagesList extends JPanel
{
	private JScrollPane scrollList;
	private JList list;
	
	public MessagesList ()
	{
		setLayout (new BorderLayout (7, 7));
		list = new JList ();
		scrollList = new JScrollPane (list, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		add (scrollList, BorderLayout.CENTER);
		Timer msgTimer = new Timer (1000, messagesListTimer ());
		msgTimer.start();
	}
	
	private Action messagesListTimer ()
	{
		return new AbstractAction ("Messages List Timer")
		{
			public void actionPerformed(ActionEvent e)
			{
				boolean scroll = false;
				if (list.getSelectedIndex()<0 || list.getSelectedIndex()==list.getModel().getSize()-1) scroll = true;
				int i = list.getSelectedIndex();
				list.setListData (Messages.getMessages());
				if (scroll) list.setSelectedIndex (list.getModel().getSize()-1);
					else list.setSelectedIndex(i);
				list.ensureIndexIsVisible(list.getSelectedIndex());
			}
		};
	}
}

class SongMessagesList extends JFrame
{
	private JScrollPane scrollList;
	private JList list;
	private JTextField mesaj;
	private JButton addMsg, delMsg;
	private Song mysong;
	
	public SongMessagesList (Song song)
	{
		mysong = song;
		
		setTitle ("Edit Song Messages");
		setSize (400, 300);
		Container panou = getContentPane ();
		panou.setLayout (new BorderLayout (7, 7));
		panou.add (new JLabel ("Messages for \""+song.getTag()+"\""), BorderLayout.NORTH);
		list = new JList ();
		scrollList = new JScrollPane (list, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		panou.add (scrollList, BorderLayout.CENTER);
		
		JPanel jos = new JPanel ();
		jos.setLayout (new BoxLayout (jos, BoxLayout.X_AXIS));
		panou.add (jos, BorderLayout.SOUTH);
		
		mesaj = new JTextField ("");
		addMsg = new JButton ("Add");
		addMsg.addActionListener(new ActionListener()
			{
				public void actionPerformed (ActionEvent ev)
				{
					String s = Utile.allTrim (mesaj.getText ());
					mysong.addMessage(s);
					mesaj.setText ("");
				}
			}
		);
		
		delMsg = new JButton ("Delete");
		delMsg.addActionListener(new ActionListener()
			{
				public void actionPerformed (ActionEvent ev)
				{
					int i = list.getSelectedIndex();
					if (i>0)
					{
						mysong.deleteMessage(((SongMessage)list.getSelectedValue()).getID());
					}
				}
			}
		);
		
		jos.add (mesaj);
		jos.add (Box.createRigidArea(new Dimension (5, 0)));
		jos.add (addMsg);
		jos.add (Box.createRigidArea(new Dimension (5, 0)));
		jos.add (delMsg);
		
		Timer msgTimer = new Timer (1000, messagesListTimer ());
		msgTimer.start();
		
		show ();
	}
	
	private Action messagesListTimer ()
	{
		return new AbstractAction ("Songs Messages List Timer")
		{
			public void actionPerformed(ActionEvent e)
			{
				int i = list.getSelectedIndex ();
				list.setListData (mysong.getMessagesList());
				list.setSelectedIndex (i);
//				list.setSelectedIndex (list.getModel().getSize()-1);
//				list.ensureIndexIsVisible(list.getModel().getSize()-1);
			}
		};
	}
}

class SongSourceEdit extends JDialog
{
	private JTextField name;
	private JTextField id;
	private JComboBox kbitslist;
	private String names = null;
	private String ids = null;
	private static final int[] kbits = {24, 32, 56, 64, 96, 128, 160, 192, 256};
	private SongSource song = null;
	
	public SongSourceEdit ()
	{
		
		Container p = getContentPane ();
		setSize (200,130);
		setResizable (false);
		setTitle ("New Song Source (Playlist)");
		addWindowListener (new WindowAdapter()
			{
				public void windowClosing(WindowEvent we)
				{
					dispose();
				}
			}
		);
		p.setLayout (new GridLayout (4,2,2,2));
		p.add (new JLabel ("Name"));
		name = new JTextField (15);
		p.add (name);
		p.add (new JLabel ("Register As"));
		id = new JTextField (15);
		p.add (id);
		p.add (new JLabel ("Bitrate"));
		String[] kbitsstr = {"24 Kbits", "32 KBits", "56 KBits", "64 KBits", "96 KBits", "128 KBits", "160 KBits", "192 KBits", "256 KBits"};
		kbitslist = new JComboBox (kbitsstr);
		p.add (kbitslist);
		JButton b_ok = new JButton ("OK");
		b_ok.addActionListener(new ActionListener()
			{
				public void actionPerformed (ActionEvent ev)
				{
					names = name.getText();
					StringBuffer sb = new StringBuffer (id.getText());
					for (int l=0;l<sb.length();l++)
					{
						if (sb.charAt(l)==' ') 
						{
							sb.delete(l,l+1);
							l--;
						}
					}
					ids = sb.toString();
					if (song!=null && names!=null && ids!=null)
					{
						song.setSourceName (names);
						song.setSourceID (ids);
						song.setKBits (kbits[kbitslist.getSelectedIndex()]);
					}
					hide();
				}
			}
		);
		p.add (b_ok);
		JButton b_cancel = new JButton ("Cancel");
		b_cancel.addActionListener(new ActionListener()
			{
				public void actionPerformed (ActionEvent ev)
				{
					hide();
				}
			}
		);
		p.add (b_cancel);
		setModal(true);
	}
	
	public SongSourceEdit (SongSource s)
	{
		this ();
		song = s;
		name.setText (song.getSourceName());
		id.setText (song.getSourceID());
		int idx=0;
		int skbits = song.getKBits();
		for (int i=0;i<kbits.length;i++) if (kbits[i]==skbits) idx=i;
		kbitslist.setSelectedIndex(idx);
	}
	
	public String getName ()
	{
		return names;
	}
	
	public String getID ()
	{
		return ids;
	}
	
	public int getKBits ()
	{
		return kbits[kbitslist.getSelectedIndex()];
	}
	
}

public class ServerGraphics
{
	private JFrame main;
	private JPanel server;
	private JPanel sources;
	private JTextField port;
	private JTextField maxclients;
	private JTextField configPort;
	private JPasswordField adminPassword;
	private JButton serverButton;
	private JTabbedPane notebook;
	private JLabel sourcetag;
	private JList sourcesList, songsList;
	private JButton songsAdd, songsAddFolder, songsDel, songsTag, songsMsg;
	private JButton listEdit, listSave, listSaveAs;
	private ServerStart serverStart = new ServerStart();
	private String[] songsIDList;
	private String[] songIDList;
	private SongSource songsource;
	
	public ServerGraphics ()
	{
		main = new JFrame ("Radio 9200 Server");
		main.getContentPane().setLayout (new BorderLayout (5, 5));
		
		mainMenu ();
		
		serverPanel ();
		sourcesPanel ();
		//main.setContentPane (server);
		main.setSize (640, 400);
		main.addWindowListener (new WindowAdapter ()
			{
				public void windowClosing (WindowEvent e)
				{
					ServerConfig.shutdown ();
				}
			}
		);
		
		makeNotebook ();
		
		main.show ();
	}
	
	private void mainMenu ()
	{
		JMenuBar menuBar=new JMenuBar();
		JMenu menu=new JMenu("Server");
		menuBar.add(menu);
		JMenuItem menuItem=new JMenuItem("Start/Stop");
		menu.add(menuItem);
		menuItem.addActionListener(serverStart);
		menu.addSeparator();
		menuItem=new JMenuItem("Exit");
		menuItem.addActionListener (new ActionListener ()
			{
				public void actionPerformed (ActionEvent e)
				{
					ServerConfig.shutdown ();
				}
			}
		);
		menu.add(menuItem);		
		menu=new JMenu("Playlists");
		menuBar.add(menu);
		menuItem=new JMenuItem("New");
		menuItem.addActionListener(new ActionListener ()
			{
				public void actionPerformed (ActionEvent ev)
				{
					JFileChooser fc = new JFileChooser ();
					fc.setDialogTitle("Select a song source (playlist) file for the new source");
					int res = fc.showSaveDialog(null);
					if (res == JFileChooser.APPROVE_OPTION)
					{
						SongSourceEdit se = new SongSourceEdit ();
						se.show();
						try 
						{
							SongSource song = new Mp3Songs (se.getID(), se.getName());
							song.setKBits (se.getKBits());
							String filename = fc.getSelectedFile().getAbsolutePath();
							if (fc.getSelectedFile().getName().indexOf(".")<0) filename=filename+".9pl";
							song.setFilename(filename);
							ServerConfig.addSongSource(song);
							song.start();
							se.dispose();
				    	}
					    catch (Exception ex) 
					    {
					    }
					}
				}
			}
		);
		menu.add(menuItem);
		menuItem=new JMenuItem("Open (Register)");
		menuItem.addActionListener(new ActionListener ()
			{
				public void actionPerformed (ActionEvent ev)
				{
					JFileChooser fc = new JFileChooser ();
					fc.setDialogTitle("Select song sourcees (playlist) to open (register)");
					fc.setMultiSelectionEnabled(true);
					int res = fc.showOpenDialog(null);
					if (res == JFileChooser.APPROVE_OPTION)
					{
						File[] files = fc.getSelectedFiles();
						for (int l=0;l<files.length;l++)
						{
							SongSource sc = new Mp3Songs ("","");
							if (sc.loadSongs(files[l].getAbsolutePath()))
							{
								if (ServerConfig.addSongSource(sc)) sc.start();
							}
						}
					}
				}
			}
		);
		menu.add(menuItem);
		menuItem=new JMenuItem("Close (Unregister)");
		menuItem.addActionListener(new ActionListener ()
			{
				public void actionPerformed (ActionEvent ev)
				{
					if (songsource!=null)
					{
						songsource.down();
						ServerConfig.deleteSoundSource(songsource.getSourceID());
						songsource = null;
						reloadSongsList ();
					}
				}
			}
		);
		menu.add(menuItem);
		
		main.setJMenuBar(menuBar);
	}
	
	private void makeNotebook ()
	{
		notebook=new JTabbedPane();
		notebook.addTab("Server",new ImageIcon("images/server.jpg"),server,"Server Settings");
		notebook.addTab("Playlists",new ImageIcon("images/playlists.jpg"),sources,"Playlists Settings");
		main.getContentPane().add(notebook, BorderLayout.CENTER);
	}
	
	private Action serverTimer ()
	{
		return new AbstractAction ("Server Timer")
		{
			public void actionPerformed(ActionEvent e)
			{
				Server server = ServerConfig.getSterver();
				if (server!=null && server.started())
				{
					port.setEditable(false);
					maxclients.setEditable(false);
					configPort.setEditable(false);
					serverButton.setText ("Stop Server");
				}
				else
				{
					port.setEditable(true);
					maxclients.setEditable(true);
					configPort.setEditable(true);
					serverButton.setText ("Start Server");
				}
			}
		};
	}
	
	private void serverPanel ()
	{
		server = new JPanel (new BorderLayout (10, 10));
		server.setBorder (BorderFactory.createTitledBorder (ServerConfig.NAME+" "+ServerConfig.VERSION));
		JPanel susdublu = new JPanel (new BorderLayout (3, 3));
		JPanel sus1 = new JPanel (new FlowLayout(FlowLayout.LEFT, 3, 3));
		JPanel sus2 = new JPanel (new FlowLayout(FlowLayout.LEFT, 3, 3));
		susdublu.add (sus1, BorderLayout.NORTH);
		susdublu.add (sus2, BorderLayout.SOUTH);
		
		//port
		sus1.add (new JLabel ("Port"));
		port = new JTextField (""+ServerConfig.getClients().getPort(), 5);
		sus1.add (port);
		//max clients
		sus1.add (new JLabel ("Maximum Clients"));
		maxclients = new JTextField (""+ServerConfig.getClients().getMaxClients(), 3);
		sus1.add (maxclients);
		//web port
		sus2.add (new JLabel ("Config Port"));
		configPort = new JTextField (""+ServerConfig.getConfigPort(), 5);
		sus2.add (configPort);
		//admin password
		sus2.add (new JLabel ("Admin Password"));
		adminPassword = new JPasswordField (ServerConfig.getAdminPassword(), 10);
		sus2.add (adminPassword);
		//butoane
		serverButton = new JButton ("Start Server");
		serverButton.addActionListener (serverStart);
		sus2.add (serverButton);
		
		server.add (susdublu, BorderLayout.NORTH);
		//server.add (new JLabel (ServerConfig.NAME+" "+ServerConfig.VERSION), BorderLayout.NORTH);
		server.add (new MessagesList (), BorderLayout.CENTER);
		
		Timer st = new Timer (100, serverTimer());
		st.start ();
	}
	
	public void sourcesPanel ()
	{
		sources = new JPanel (new BorderLayout (10, 10));
		sourcesList = new JList ();
		JScrollPane scrollscrlist = new JScrollPane (sourcesList, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollscrlist.setPreferredSize(new Dimension (150, 10));
		sources.add (scrollscrlist, BorderLayout.WEST);
		sourcesList.setListData(ServerConfig.getSongSources());
		sourcesList.addListSelectionListener(new ListSelectionListener ()
			{
				public void valueChanged (ListSelectionEvent ls)
				{
					int i = sourcesList.getSelectedIndex();
					if (i>-1)
					{
						songsource = ServerConfig.getSoundSource(songsIDList[i]);
						reloadSongsList ();
					}
				}
			}
		);
		Timer srcListTimer = new Timer (1000, new ActionListener()
			{
				public void actionPerformed (ActionEvent ev)
				{
					Vector songs = ServerConfig.getSongSources();
					songsIDList = new String[songs.size()];
					String[] songsNameList = new String [songs.size()];
					for (int i=0;i<songs.size();i++)
					{
						SongSource song = (SongSource)songs.elementAt (i);
						if (song!=null)
						{
							songsIDList[i] = song.getSourceID();
							songsNameList[i] = song.getSourceName();
						}
					}
					sourcesList.setListData(songsNameList);
				}
			}
		);
		srcListTimer.start ();
		// songs list
		JPanel songspanel = new JPanel (new BorderLayout (3, 3));
		sources.add (songspanel, BorderLayout.CENTER);
		songsList = new JList ();
		JScrollPane scrollsnglist = new JScrollPane (songsList, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		songspanel.add (scrollsnglist, BorderLayout.CENTER);
		// List Buttons
		JPanel listbuttons = new JPanel ();
		listbuttons.setLayout (new BoxLayout (listbuttons, BoxLayout.X_AXIS));
		sourcetag = new JLabel ("(select a source to display)");
		listbuttons.add (sourcetag, BorderLayout.NORTH);
		listbuttons.add (Box.createHorizontalGlue());
		// Edit List
		listEdit = new JButton ("Edit");
		listEdit.addActionListener(new ActionListener ()
			{
				public void actionPerformed (ActionEvent ev)
				{
					if (songsource!=null)
					{
						SongSourceEdit sedit = new SongSourceEdit (songsource);
						sedit.show ();
						reloadSongsList ();
					}
				}
			}
		);
		listbuttons.add (listEdit);
		listbuttons.add (Box.createRigidArea(new Dimension (5, 0)));
		// Save List
		listSave = new JButton ("Save");
		listSave.addActionListener(new ActionListener ()
			{
				public void actionPerformed (ActionEvent ev)
				{
					if (songsource!=null) songsource.saveSongs();
				}
			}
		);
		listbuttons.add (listSave);
		listbuttons.add (Box.createRigidArea(new Dimension (5, 0)));
		// Save As List
		listSaveAs = new JButton ("Save As");
		listSaveAs.addActionListener(new ActionListener ()
			{
				public void actionPerformed (ActionEvent ev)
				{
					if (songsource!=null)
					{
						JFileChooser fc = new JFileChooser ();
						fc.setDialogTitle("Save song source (playlist) as");
						int res = fc.showSaveDialog(null);
						if (res == JFileChooser.APPROVE_OPTION)
						{
							String filename = fc.getSelectedFile().getAbsolutePath();
							if (fc.getSelectedFile().getName().indexOf(".")<0) filename=filename+".9pl";
							songsource.saveSongs(filename);
						}
					}
				}
			}
		);
		listbuttons.add (listSaveAs);
		songspanel.add (listbuttons, BorderLayout.NORTH);

		// Song Buttons
		JPanel songsbuttons = new JPanel ();
		songsbuttons.setLayout (new BoxLayout (songsbuttons, BoxLayout.X_AXIS));
		// Add Song
		songsAdd = new JButton ("Add");
		songsAdd.addActionListener(new ActionListener ()
			{
				public void actionPerformed (ActionEvent ev)
				{
					if (songsource!=null)
					{
						JFileChooser fc = new JFileChooser ();
						fc.setDialogTitle("Select songs to add");
						fc.setMultiSelectionEnabled(true);
						int res = fc.showOpenDialog(null);
						if (res == JFileChooser.APPROVE_OPTION)
						{
							File[] files = fc.getSelectedFiles();
							for (int l=0;l<files.length;l++)
							{
								songsource.addSong(files[l].getAbsolutePath());
							}
							reloadSongsList ();
						}
					}
				}
			}
		);
		songsbuttons.add (songsAdd);
		songsbuttons.add (Box.createRigidArea(new Dimension (10, 0)));
		// Add Song Folder
		songsAddFolder = new JButton ("Add Folder");
		songsAddFolder.addActionListener(new ActionListener ()
			{
				public void actionPerformed (ActionEvent ev)
				{
					if (songsource!=null)
					{
						JFileChooser fc = new JFileChooser ();
						fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
						fc.setDialogTitle("Select a folder with songs to add");
						int res = fc.showOpenDialog(null);
						if (res == JFileChooser.APPROVE_OPTION)
						{
							songsource.addSongsFromLocation(fc.getSelectedFile().getAbsolutePath());
							reloadSongsList ();
						}
					}
				}
			}
		);
		songsbuttons.add (songsAddFolder);
		songsbuttons.add (Box.createRigidArea(new Dimension (10, 0)));
		// Delete Song
		songsDel = new JButton ("Delete");
		songsDel.addActionListener(new ActionListener ()
			{
				public void actionPerformed (ActionEvent ev)
				{
					int i = songsList.getSelectedIndex();
					if (i>-1 && songsource!=null)
					{
						Song currentSong = null;
						boolean found = false;
						Song[] songs = songsource.getSongsList();
						for (int l=0;l<songs.length && !found;l++)
							if (songIDList[i].equalsIgnoreCase(songs[l].getID()))
							{
								found = true;
								currentSong = songs[l];
							}
						if (currentSong!=null) songsource.deleteSong(currentSong);
						reloadSongsList ();
					}
				}
			}
		);
		songsbuttons.add (songsDel);
		songsbuttons.add (Box.createRigidArea(new Dimension (10, 0)));
		// Tag
		songsTag = new JButton ("Edit Tag");
		songsTag.addActionListener(new ActionListener ()
			{
				public void actionPerformed (ActionEvent ev)
				{
					int i = songsList.getSelectedIndex();
					if (i>-1 && songsource!=null)
					{
						Song currentSong = null;
						boolean found = false;
						Song[] songs = songsource.getSongsList();
						for (int l=0;l<songs.length && !found;l++)
							if (songIDList[i].equalsIgnoreCase(songs[l].getID()))
							{
								found = true;
								currentSong = songs[l];
							}
						if (currentSong!=null) currentSong.getTag().editTag();
						reloadSongsList ();
					}
				}
			}
		);
		songsbuttons.add (songsTag);
		songsbuttons.add (Box.createRigidArea(new Dimension (10, 0)));
		// Messages
		songsMsg = new JButton ("Messages");
		songsMsg.addActionListener(new ActionListener ()
			{
				public void actionPerformed (ActionEvent ev)
				{
					int i = songsList.getSelectedIndex();
					if (i>-1 && songsource!=null)
					{
						Song currentSong = null;
						boolean found = false;
						Song[] songs = songsource.getSongsList();
						for (int l=0;l<songs.length && !found;l++)
							if (songIDList[i].equalsIgnoreCase(songs[l].getID()))
							{
								found = true;
								currentSong = songs[l];
							}
						if (currentSong!=null) new SongMessagesList (currentSong);
						reloadSongsList ();
					}
				}
			}
		);
		songsbuttons.add (songsMsg);
		songsbuttons.add (Box.createRigidArea(new Dimension (10, 0)));
		songspanel.add (songsbuttons, BorderLayout.SOUTH);
	}
	
	private void reloadSongsList ()
	{
		sourcetag.setText ("(select a source to display)");
		songsList.setListData(new Vector (0));
		if (songsource!=null)
		{
			sourcetag.setText (songsource.getSourceName()+" (ID: "+songsource.getSourceID()+", KBits: "+songsource.getKBits()+", Songs: "+songsource.getSongsList().length+")");
			Song[] songslist = songsource.getSongsList();
			songIDList = new String[songslist.length];
			String[] songNameList = new String [songslist.length];
			for (int l=0;l<songslist.length;l++)
			{
				songIDList[l] = songslist[l].getID();
				songNameList[l] = songslist[l].toString();
			}
			songsList.setListData(songNameList);
		}
	}
	
	class ServerStart implements ActionListener
	{
		public void actionPerformed (ActionEvent ev)
		{
			Server server = ServerConfig.getSterver();
			WebServerConfig webserver = ServerConfig.getWebSterver();
			if (server!=null && server.started() && webserver!=null && webserver.started()) 
			{
				server.down();
				webserver.down ();
			}
			else 
			{
				try {ServerConfig.getClients().setMaxClients(Integer.parseInt (maxclients.getText()));}
				catch (Exception ex) {maxclients.setText (""+ServerConfig.getClients().getMaxClients());}
				try {ServerConfig.getClients().setPort(Integer.parseInt (port.getText()));}
				catch (Exception ex) {port.setText (""+ServerConfig.getClients().getPort());}
				try {ServerConfig.setConfigPort(Integer.parseInt (configPort.getText()));}
				catch (Exception ex) {configPort.setText (""+ServerConfig.getConfigPort());}
				server = new Server (ServerConfig.getClients().getPort());
				webserver = new WebServerConfig (ServerConfig.getConfigPort());
				ServerConfig.setServer (server);
				ServerConfig.setWebServer (webserver);
				server.start ();
				webserver.start ();
			}
		}
	}
	
}
