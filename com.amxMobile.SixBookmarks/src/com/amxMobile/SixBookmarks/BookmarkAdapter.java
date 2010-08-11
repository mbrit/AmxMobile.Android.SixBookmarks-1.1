package com.amxMobile.SixBookmarks;

import com.amxMobile.SixBookmarks.Database.*;
import com.amxMobile.SixBookmarks.Runtime.*;
import android.app.*;
import android.util.*;
import android.view.*;
import android.widget.*;

public class BookmarkAdapter extends BaseAdapter
{
	private IContextSource _owner;
	private BookmarkCollection _bookmarks;
	
	public BookmarkAdapter(IContextSource owner) throws Exception 
	{
		// save the owner...
		_owner = owner;
		
		// load the bookmarks and sort by ordinal...
		_bookmarks = Bookmark.GetBookmarksForDisplay(owner);
		_bookmarks.SortByOrdinal();
	}
	
	private IContextSource getOwner()
	{
		return _owner;
	}
	
	BookmarkCollection getBookmarks()
	{
		return _bookmarks;
	}
	
	public int getCount()
	{
		return getBookmarks().size();
	}

	public Object getItem(int position)
	{
		return getBookmarks().get(position);
	}

	public long getItemId(int position)
	{
		try
		{
			Bookmark bookmark = (Bookmark)getItem(position);
			
			// return the *ordinal* - this is the easiest thing to dereference when 
// we need to configure the singleton...
			return (long)bookmark.getOrdinal();
		} 
		catch(Exception ex)
		{
			HandleException("getItemId", ex);
			return 0;
		}
	}

	private void HandleException(String method, Exception ex)
	{
		// log it...
		Log.e(String.format("Adapter operation '%s' failed.", method), MessageBox.FormatException (ex));
	}
	
	public View getView(int position, View convertView, ViewGroup parent)
	{
		try
		{
			// what item?
			Bookmark bookmark = (Bookmark)getItem(position);
			
			// use an inflater to get the layout...
			LayoutInflater inflater = ((Activity)getOwner().getContext()).getLayoutInflater();
			View itemView = inflater.inflate(R.layout.bookmarkitem, null);
			
			// set the text...
			TextView text = (TextView)itemView.findViewById(R.id.textName);
			text.setText(bookmark.getName());
			
			// return...
			return itemView;
		}
		catch(Exception ex)
		{
			HandleException("getView", ex);
			return null;
		}
	}

	public Bookmark GetItemByOrdinal(int ordinal) throws Exception
	{
		return getBookmarks().GetByOrdinal(ordinal);
	}
}
