package com.amxMobile.SixBookmarks.Database;

import java.util.*;
import com.amxMobile.SixBookmarks.Entities.*;
import com.amxMobile.SixBookmarks.Runtime.*;

public class Bookmark extends Entity 
{
	public final static String BookmarkIdKey = "BookmarkId";
	public final static String OrdinalKey = "Ordinal";
	public final static String NameKey = "Name";
	public final static String UrlKey = "Url";
	public final static String LocalModifiedKey = "LocalModified";
	public final static String LocalDeletedKey = "LocalDeleted";

	public Bookmark() throws Exception
	{
	}
	
	public Bookmark(Hashtable<String, Object> values) throws Exception
	{	
		super(values);
	}
	
	public int getBookmarkId() throws Exception
	{
		return GetInt32Value(BookmarkIdKey);
	}
	
	public void setBookmarkId(int value) throws Exception
	{
		SetValue(BookmarkIdKey, value, SetReason.UserSet);
	}
	
	public int getOrdinal() throws Exception
	{
		return GetInt32Value(OrdinalKey);
	}
	
	public void setOrdinal(int value) throws Exception
	{
		SetValue(OrdinalKey, value, SetReason.UserSet);
	}

	public String getName() throws Exception
	{
		return GetStringValue(NameKey);
	}
	
	public void setName(String value) throws Exception
	{
		SetValue(NameKey, value, SetReason.UserSet);
	}
	
	public String getUrl() throws Exception
	{
		return GetStringValue(UrlKey);
	}
	
	public void setUrl(String value) throws Exception
	{
		SetValue(UrlKey, value, SetReason.UserSet);
	}

	public boolean getLocalModified() throws Exception
	{
		return GetBooleanValue(LocalModifiedKey);
	}
	
	public void setLocalModified(boolean value) throws Exception
	{
		SetValue(LocalModifiedKey, value, SetReason.UserSet);
	}

	public boolean getLocalDeleted() throws Exception
	{
		return GetBooleanValue(LocalDeletedKey);
	}
	
	public void setLocalDeleted(boolean b) throws Exception
	{
		SetValue(LocalDeletedKey, b, SetReason.UserSet);
	}
	
	public static BookmarkCollection GetBookmarksForDisplay(IContextSource context) throws Exception
	{
		// get those that are flagged as modified and deleted...
		SqlFilter filter = new SqlFilter(Bookmark.class);
		filter.AddConstraint("LocalDeleted", 0);
		
		// return...
		return (BookmarkCollection)filter.ExecuteEntityCollection(context); 
	}
	
	public static Bookmark GetByOrdinal(IContextSource context, int ordinal) throws Exception
	{
		SqlFilter filter = new SqlFilter(Bookmark.class);
		filter.AddConstraint("Ordinal", ordinal);
		filter.AddConstraint("LocalDeleted", 0);
		
		// return...
		return (Bookmark)filter.ExecuteEntity(context);
	}
	
	public static BookmarkCollection GetBookmarksForServerUpdate(IContextSource context) throws Exception
	{
		// get those that are flagged as modified and deleted...
		SqlFilter filter = new SqlFilter(Bookmark.class);
		filter.AddConstraint("LocalModified", 1);
		filter.AddConstraint("LocalDeleted", 0);
		
		// return...
		return (BookmarkCollection)filter.ExecuteEntityCollection(context); 
	}

	public static BookmarkCollection GetBookmarksForServerDelete(IContextSource context) throws Exception
	{
		// get those that are flagged as modified and deleted...
		SqlFilter filter = new SqlFilter(Bookmark.class);
		filter.AddConstraint("LocalDeleted", 1);
		
		// return...
		return (BookmarkCollection)filter.ExecuteEntityCollection(context); 
	}
}
