package com.amxMobile.SixBookmarks.Services;

import java.util.*;

public class RestRequestArgs extends Hashtable<String, Object> 
{
	public RestRequestArgs(String operation)
	{
		this.put("operation", operation);
	}
}
