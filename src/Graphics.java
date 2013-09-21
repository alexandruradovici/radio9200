import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

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
	private JList list;
	
	public MessagesList ()
	{
		setLayout (new BorderLayout (7, 7));
		list = new JList ();
		add (list, BorderLayout.CENTER);
		Timer msgTimer = new Timer (1000, messagesListTimer ());
	}
	
	private Action messagesListTimer ()
	{
		return new AbstractAction ("Messages List Timer")
		{
			public void actionPerformed(ActionEvent e)
			{
				list.setListData (Messages.getMessages());
			}
		};
	}
}

public class ServerGraphics
{
	
}