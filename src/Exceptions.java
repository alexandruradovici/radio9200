class NoFileException extends Exception
{
	private String filename;
	
	public NoFileException (String filename)
	{
		this.filename=filename;
	}
	
	public String toString ()
	{
		return "Could not find file ("+filename+")";
	}
}

class NoFileAccessException extends Exception
{
	private String filename;
	
	public NoFileAccessException (String filename)
	{
		this.filename=filename;
	}
	
	public String toString ()
	{
		return "Could not access file ("+filename+")";
	}
}

class BadMpegFileException extends Exception
{
	private String filename;
	
	public BadMpegFileException (String filename)
	{
		this.filename=filename;
	}
	
	public String toString ()
	{
		return "Bad or not an MPEG File ("+filename+")";
	}
}

class BadSongFileException extends Exception
{
	private String filename;
	
	public BadSongFileException (String filename)
	{
		this.filename=filename;
	}
	
	public String toString ()
	{
		return "Bad or not an Song File ("+filename+")";
	}
}

class BadSongTagException extends Exception
{
	private String reason;
	
	public BadSongTagException (String reason)
	{
		this.reason=reason;
	}
	
	public String toString ()
	{
		return "The song's tag object is not valid ("+reason+")";
	}
}

class BadId3TagException extends Exception
{
	private String reason;
	
	public BadId3TagException (String reason)
	{
		this.reason=reason;
	}
	
	public String toString ()
	{
		return "The Id3Tag object is not valid ("+reason+")";
	}
}

class BadSongNameFormatException extends Exception
{
	private String format;
	
	public BadSongNameFormatException (String format)
	{
		this.format=format;
	}
	
	public String toString ()
	{
		return "Bad song name format ("+format+")";
	}
}

class BadMessageException extends Exception
{
	private String reason;
	
	public BadMessageException (String reason)
	{
		this.reason=reason;
	}
	
	public String toString ()
	{
		return "The message is not valid ("+reason+")";
	}
}