package com.amxMobile.SixBookmarks.Database;

import android.content.*;
import com.amxMobile.SixBookmarks.Entities.*;
import com.amxMobile.SixBookmarks.Runtime.*;
import com.amxMobile.SixBookmarks.Services.*;

public class Sync 
{
	private IContextSource _contextSource;
	private DatabaseHelper _database;
	private EntityType _entityType;
	
	public void DoSync(IContextSource source) throws Exception
	{
		_contextSource = source; 
		_database = new DatabaseHelper(source);
	
		// make sure we have a table...
		_entityType = EntityType.GetEntityType(Bookmark.class);
		_database.EnsureTableExists(_entityType);
		
		// push changes, then get the latest...
		PushChanges();
		GetLatest();
	}

	private IContextSource getContextSource()
	{
		return _contextSource;
	}
	
	private EntityType getEntityType()
	{
		return _entityType;
	}
	
	private DatabaseHelper getDatabase()
	{
		return _database;
	}
	
	private void GetLatest() throws Exception
	{
		// clear the bookmarks table...
		getDatabase().ExecuteNonQuery(new SqlStatement("delete from bookmarks"), true);
		
		// get the bookmarks from the server...
		BookmarksService service = new BookmarksService();
		for(Bookmark bookmark : service.GetAll())
		{
			// the bookmarks we get from the server have server ids populated.  we need to
			// remove the server id and save locally by creating a new item...
			
			// create and copy all of the fields except the key field...
			Bookmark newBookmark = new Bookmark();
			for(EntityField field : getEntityType().getFields())
			{
				if(!(field.getIsKey()) && bookmark.getIsLoaded(field))
					newBookmark.SetValue(field, bookmark.GetValue(field), SetReason.UserSet);
			}
			
			// set local modified and deleted...
			newBookmark.setLocalModified(false);
			newBookmark.setLocalDeleted(false);
		
			// save...
			newBookmark.SaveChanges(this.getContextSource());
		}
	}
	
	private void PushChanges() throws Exception
	{
		BookmarkCollection updates = Bookmark.GetBookmarksForServerUpdate(getContextSource());
		BookmarkCollection deletes = Bookmark.GetBookmarksForServerDelete(this.getContextSource());
		if(updates.size() == 0 && deletes.size() == 0)
			return;

		// et...
		EntityType et = EntityType.GetEntityType(Bookmark.class);
		
		// get the server ones...
		BookmarksService service = new BookmarksService();
		BookmarkCollection fromServer = service.GetAll();

		// walk the locally updated items…
		for(Bookmark local : updates)
		{
			// find it in our server set...
			Bookmark toUpdate = null;
			for(Bookmark server : fromServer)
			{
				if(local.getOrdinal() == server.getOrdinal())
				{
					toUpdate = server;
					break;
				}
			}
			
			// did we have one to change?
			if(toUpdate != null)
			{
				// walk the fields...
				int serverId = 0;
				for(EntityField field : et.getFields())
				{
					if(!(field.getIsKey()))
						toUpdate.SetValue(field, local.GetValue(field), SetReason.UserSet);
					else
						serverId = toUpdate.getBookmarkId();
				}
				
				// send that up...
				service.PushUpdate( this.getContextSource(), toUpdate, serverId);
			}
			else
			{
				// we need to insert it...
				service.PushInsert(this.getContextSource(), local); 
			}
		}
		
		// what about ones to delete?
		for(Bookmark local : deletes)
		{
			// find a matching ordinal on the server...
			for(Bookmark server : fromServer) 
			{
				if(local.getOrdinal() ==  server.getOrdinal())
					service.PushDelete(this.getContextSource(), server, server.getBookmarkId());
			}
		}
	}
}
