package com.amxMobile.SixBookmarks;

import com.amxMobile.SixBookmarks.Database.*;
import com.amxMobile.SixBookmarks.Runtime.*;

public class ConfigureSingletonController extends Controller<IConfigureSingletonView> 
{
	private Bookmark _bookmark;
	
	public ConfigureSingletonController(IConfigureSingletonView view)
	{
		super(view);
	}
	
	public void HandleLoad() throws Exception
	{
		// get the ordinal from the intent...
		int ordinal = getActivity().getIntent().getExtras().getInt("ordinal");
		
		// load the bookmark...
		Bookmark bookmark = Bookmark.GetByOrdinal(this, ordinal);
		
		// if we didn't load one, create one...
		if(bookmark == null)
		{
			// create a new one...
			bookmark = new Bookmark();
			bookmark.setOrdinal(ordinal);
		}
		
		// set...
		IConfigureSingletonView view = getView();
		view.setOrdinal(ordinal);
		view.setName(bookmark.getName());
		view.setUrl(bookmark.getUrl());
		
		// store...
		_bookmark = bookmark;
	}
	
	private Bookmark getBookmark()
	{
		return _bookmark;
	}

	public void HandleSave() throws Exception 
	{
		// get data...
		String name = getView().getName();
		String url = getView().getUrl();
		
		// both?
		Bookmark bookmark = getBookmark();
		if(name != null && name.length() > 0 && url != null && url.length() > 0)
		{
			// set...
			bookmark.setName(name);
			bookmark.setUrl(url);
			bookmark.setLocalModified(true);
			bookmark.setLocalDeleted(false);
			
			// save...
			bookmark.SaveChanges(this);
			getActivity().finish(); 
		}
		else
			MessageBox.Show(this, "Both name and URL must be provided.");
	}
}
