package com.amxMobile.SixBookmarks.Entities;

public abstract class EntityItem 
{
	private String _name;
	private String _nativeName;
	
	protected EntityItem(String name, String nativeName)
	{
		_name = name;
		
		if(nativeName != null)
			_nativeName = nativeName;
		else
			_nativeName = name;
	}
	
	public String getName()
	{
		return _name;
	}

	public String getNativeName()
	{
		return _nativeName;
	}
}
