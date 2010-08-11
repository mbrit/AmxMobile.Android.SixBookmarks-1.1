package com.amxMobile.SixBookmarks.Database;

import java.util.*;
import android.util.*;

public class BookmarkCollection extends ArrayList<Bookmark> 
{
	public void SortByOrdinal()
	{
		Collections.sort(this, new OrdinalComparator());
	}
	
	private class OrdinalComparator implements Comparator<Bookmark>
	{
		public int compare(Bookmark x, Bookmark y)
		{
			try
			{
				int a = x.getOrdinal();
				int b = y.getOrdinal();
				if(a < b)
					return -1;
				else if(a > b)
					return 1;
				else
					return 0;
			}
			catch(Exception ex)
			{
				Log.e("Sort operation failed.", ex.toString());
				return 0;
			}
		}
	}
	
	public Bookmark GetByOrdinal(int ordinal) throws Exception
	{
		for(Bookmark bookmark : this)
		{
			if(bookmark.getOrdinal() == ordinal)
				return bookmark;
		}
		
		// nope...
		return null;
	}
}
