package com.amxMobile.SixBookmarks.Runtime;

import com.amxMobile.SixBookmarks.Database.*;
import com.amxMobile.SixBookmarks.Entities.*;

public class SixBookmarksRuntime 
{
	public static void Start()
	{
		// create the entity type...
		EntityType bookmark = new EntityType(Bookmark.class, BookmarkCollection.class, "Bookmarks");
		bookmark.AddField(Bookmark.BookmarkIdKey, Bookmark.BookmarkIdKey, DataType.Int32, -1).setIsKey(true);
		bookmark.AddField(Bookmark.OrdinalKey, Bookmark.OrdinalKey, DataType.Int32, -1);
		bookmark.AddField(Bookmark.NameKey, Bookmark.NameKey, DataType.String, 128);
		bookmark.AddField(Bookmark.UrlKey, Bookmark.UrlKey, DataType.String, 256);
		bookmark.AddField(Bookmark.LocalModifiedKey, Bookmark.LocalModifiedKey, DataType.Int32, -1).setIsOnServer(false);;
		bookmark.AddField(Bookmark.LocalDeletedKey, Bookmark.LocalDeletedKey, DataType.Int32, -1).setIsOnServer(false);;
		
		// register it...
		EntityType.RegisterEntityType(bookmark);
	}
}
