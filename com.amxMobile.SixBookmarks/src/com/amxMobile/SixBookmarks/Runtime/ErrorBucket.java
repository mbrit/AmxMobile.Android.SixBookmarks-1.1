package com.amxMobile.SixBookmarks.Runtime;

import java.util.*;

public class ErrorBucket 
{
	private ArrayList<String> _errors = new ArrayList<String>();
	
	public ErrorBucket()
	{
	}
	
	public void AddError(String error)
	{
		this._errors.add(error);
	}
	
	public boolean getHasErrors()
	{
		if(this._errors.size() > 0)
			return true;
		else
			return false;
	}
	
	public String GetAllErrors()
	{
		StringBuilder builder = new StringBuilder();
		for(String error : _errors)
		{
			if(builder.length() > 0)
				builder.append("\n");
			builder.append(error);
		}
		
		// return...
		return builder.toString();
	}
}
