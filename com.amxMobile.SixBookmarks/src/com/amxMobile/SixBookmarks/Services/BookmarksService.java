package com.amxMobile.SixBookmarks.Services;

import org.w3c.dom.*;
import com.amxMobile.SixBookmarks.Database.*;
import com.amxMobile.SixBookmarks.Entities.*;
import com.amxMobile.SixBookmarks.Runtime.*;

public class BookmarksService extends ODataServiceProxy 
{
	public BookmarksService()
	{
		super("Bookmarks.svc");
	}

	public BookmarkCollection GetAll() throws Exception
	{
		EntityType et = EntityType.GetEntityType(Bookmark.class);
		
		// run...
		String url = GetServiceUrl(et);
        Document doc = HttpHelper.DownloadXml(url, GetDownloadSettings());
        
        // load...
        return (BookmarkCollection)LoadEntities(doc, et);
	}
}
