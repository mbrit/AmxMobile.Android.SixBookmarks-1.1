package com.amxMobile.SixBookmarks;

import android.content.*;
import android.net.*;
import com.amxMobile.SixBookmarks.Database.*;
import com.amxMobile.SixBookmarks.Runtime.*;

public class NavigatorController extends Controller<INavigatorView> 
{
	private String[] _urls = null;
	
	public NavigatorController(INavigatorView view)
	{
		super(view);
	}

	public void HandleConfigure() 
	{
		Intent newIntent = new Intent(getActivity(), ConfigureList.class);
		getActivity().startActivityForResult(newIntent, 0);	
	}

	public void HandleLogoff() 
	{
		MessageBox.Show(this, "TBD");
	}
	
	public void HandleAbout() 
	{
		Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.multimobiledevelopment.com/"));
		getActivity().startActivity(myIntent);
	}

	public void HandleNavigate(int ordinal) 
	{
		// get the url...
		String url = getUrls()[ordinal];
		
		// did we actually click a null button?
		if(url == null || url.length() == 0)
			HandleConfigure();
		else
		{
			// create an intent to show the URL...
			Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
			getActivity().startActivity(myIntent);
		}
	}
	
	private String[] getUrls()
	{
		return _urls;
	}
	
    public void ShowBookmarks(BookmarkCollection bookmarks) throws Exception
    {
    	// store the bookmarks collection...
    	final int maxBookmarks = 6;
    	_urls = new String[maxBookmarks];
    	
    	// reset the bookmarks...
    	for(int index = 0; index < maxBookmarks; index++)
    		ResetBookmark(index);
    	
    	// walk and set...
    	for(Bookmark bookmark : bookmarks)
    		ShowBookmark(bookmark);
    }

    private void ResetBookmark(int ordinal) throws Exception
    {
    	// reset the view...
    	getView().ResetBookmarkButton(ordinal);
    	
    	// reset the url...
    	getUrls()[ordinal] = null; 
    }
    
    private void ShowBookmark(Bookmark bookmark) throws Exception
    {
    	int ordinal = bookmark.getOrdinal();
    	
    	// set the view...
    	getView().ConfigureBookmarkButton(ordinal, bookmark.getName());
    	
    	// set the url...
    	getUrls()[ordinal] = bookmark.getUrl();
    }
    
	public void HandleLoad() throws Exception 
	{
		// show the bookmarks...
		ShowBookmarks(Bookmark.GetBookmarksForDisplay(this));
	}
	
    private void AddBookmark(BookmarkCollection bookmarks, String name, String url, int ordinal) throws Exception
    {
    	// create...
    	Bookmark bookmark = new Bookmark();
    	bookmark.setName(name);
    	bookmark.setUrl(url);
    	bookmark.setOrdinal(ordinal);
    	
    	// add...
    	bookmarks.add(bookmark);
    }
}
