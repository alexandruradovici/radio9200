import java.io.*;
import java.util.*;
import java.text.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

class Message
{
	private long timestamp;
	GregorianCalendar date;
	private String from;
	private String message;
	
	public Message (String from, String message) throws BadMessageException
	{
		if (from!=null && message!=null)
		{
			timestamp = System.currentTimeMillis();
			date = new GregorianCalendar ();
			this.from = from;
			this.message = message;
		}
		else throw new BadMessageException ("null refrences");
	}
	
	public long getTimeStamp ()
	{
		return timestamp;
	}
	
	public String toString ()
	{
		int zi = date.get (Calendar.DATE);
		String szi = ((zi>9)?"":"0")+zi;
		int luna = date.get (Calendar.MONTH);
		String sluna = ((luna>9)?"":"0")+luna;
		int an = date.get (Calendar.YEAR);
		String san = ((an>9)?"":"0")+an;
		int ora = date.get (Calendar.HOUR_OF_DAY);
		String sora = ((ora>9)?"":"0")+ora;
		int min = date.get (Calendar.MINUTE);
		String smin = ((min>9)?"":"0")+min;
		int sec = date.get (Calendar.SECOND);
		String ssec = ((sec>9)?"":"0")+sec;
		
		
		return szi+"/"+sluna+"/"+san+" @ "+sora+":"+smin+":"+ssec+" ["+from+"]   "+message;
	}
}

public class Messages
{
	private static Vector messages = new Vector (0);
	
	public static void addMessage (String from, String message)
	{
		try
		{
			messages.add (new Message (from, message));
		}
		catch (Exception ex)
		{
		}
	}
	
	public static String[] getMessages ()
	{
		int nrmsg = messages.size();
		String lista[] = new String[nrmsg];
		try
		{
			for (int i=0;i<nrmsg;i++) lista[i] = ((Message)messages.elementAt(i)).toString ();
		}
		catch (Exception ex)
		{
		}
		return lista;
	}
	
	public static void deleteMessages ()
	{
		messages.clear();
	}
	
	public static void saveMessagesToFile (String filename)
	{
		try
		{
			int nrmsg = messages.size();
			PrintWriter f = new PrintWriter (new FileWriter (filename));
			for (int i=0;i<nrmsg;i++) f.println ((String)(messages.elementAt(i)).toString());
			f.flush ();
			f.close ();
		}
		catch (Exception ex)
		{
		}
	}
}
