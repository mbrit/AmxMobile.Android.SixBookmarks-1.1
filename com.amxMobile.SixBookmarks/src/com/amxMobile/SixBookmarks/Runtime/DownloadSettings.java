package com.amxMobile.SixBookmarks.Runtime;

import java.util.*;

public class DownloadSettings 
{
	private Hashtable<String, String> _extraHeaders = new Hashtable<String, String>(); 
	
	public DownloadSettings()
	{
	}
	
	public Hashtable<String, String> getExtraHeaders()
	{
		return _extraHeaders;
	}
	
	public void AddHeader(String name, String value) 
	{
		if(value == null)
			value = "";
		
		getExtraHeaders().put(name, value);
	}
}
