package com.amxMobile.SixBookmarks;

import android.content.Intent;
import android.view.*;
import android.view.ContextMenu.*;
import android.widget.*;
import android.widget.AdapterView.*;

import com.amxMobile.SixBookmarks.Database.*;
import com.amxMobile.SixBookmarks.Runtime.*;

public class ConfigureListController extends Controller<IConfigureListView>
{
	private static final int MENUID_ADD = 10001;
	private static final int MENUID_SYNC = 10002;
	private static final int MENUID_DELETE = 10003;
	
	private long _lastClicked = -1;
	private BookmarkAdapter _adapter;
	
	protected ConfigureListController(IConfigureListView view) 
	{
		super(view);
	}

	public void HandleLoad() throws Exception
	{
		this.RefreshView();
	}
	
	private void RefreshView() throws Exception
	{
		// get the list view and set the adapter... 
		ListView list = getView().getListView();
		_adapter = new BookmarkAdapter(this);
		list.setAdapter(_adapter);
	}
	
	private BookmarkAdapter getAdapter()
	{
		return _adapter;
	}
		
	public void HandleListItemClick(long itemId)
	{
		try
		{
			HandleEdit((int)itemId);
		} 
		catch (Exception ex)
		{
			MessageBox.Show(this, ex);
		}
	}

	public void HandleEdit(int ordinal) throws Exception
	{
		// create an intent...
		Intent newIntent = new Intent(getActivity(), ConfigureSingleton.class);
		
		// set...
		newIntent.putExtra("ordinal", ordinal);
		
		// run a sub-activity that will tell us when it's done...
		getActivity().startActivityForResult(newIntent, ordinal);
	}
	
	public void HandleCreateOptionsMenu(Menu menu)
	{
		// create an item and set the icon to be one of the android standard ones...
		// http://androiddrawableexplorer.appspot.com/
		MenuItem add = menu.add(0, MENUID_ADD, 0, "Add");
		add.setIcon(android.R.drawable.ic_menu_add);
		
		// now do a sync method...
		MenuItem sync = menu.add(0, MENUID_SYNC, 0, "Sync Now");
		sync.setIcon(android.R.drawable.ic_menu_save);
	}
	
	public boolean HandleMenuItemClick(MenuItem item)
	{
		try
		{
			int id = item.getItemId(); 
			if(id == MENUID_ADD)
			{
				HandleAdd();
				return true;
			}
			else if(id == MENUID_SYNC)
			{
				HandleSync();
				return true;
			}
			else if(id == MENUID_DELETE)
			{
				HandleDelete();
				return true;
			}
			else
				throw new Exception(String.format("Cannot handle '%d'.", id));
		}
		catch(Exception ex)
		{
			MessageBox.Show(this, ex);
			return false;
		}
	}

	private void HandleSync() throws Exception
	{
		Sync sync = new Sync();
		sync.DoSync(this);

		// update...
		RefreshView();
		
		// show...
		Intent intent = new Intent(getContext(), Navigator.class);
		getActivity().startActivity(intent);
	}
	
	private void HandleAdd() throws Exception
	{
		// how many bookmarks do we have?
		if(getAdapter().getCount() < 6)
		{
			// find the next slot...
			Boolean[] defined = new Boolean[6];
			for(int index = 0; index < defined.length; index++)
				defined[index] = false;
			for(Bookmark bookmark : getAdapter().getBookmarks())
				defined[bookmark.getOrdinal()] = true;
			
			// walk..
			for(int index = 0; index < defined.length; index++)
			{
				if(!(defined[index]))
				{
					// first slot found...
					HandleEdit(index);
					return;
				}
			}
		}
		else
			MessageBox.Show(this, "The maximum number of bookmarks that you can have is six.");
	}
	
	public void HandleCreateContextMenu(ContextMenu menu, ContextMenuInfo menuInfo)
	{
		// the menu info will tell us the ID of the item that was clicked, 
		// so store that for later use...
		_lastClicked = ((AdapterContextMenuInfo)menuInfo).id;
		
		// add...
		menu.add(0, MENUID_DELETE, 0, "Delete");
	}
	
	private void HandleDelete() throws Exception
	{
		// get the item...
		Bookmark bookmark = getAdapter().GetItemByOrdinal((int)_lastClicked);
		if(bookmark != null)
		{
			// mark it as locally deleted...
			bookmark.setLocalDeleted(true);
			bookmark.SaveChanges(this);
			
			// update...
			this.RefreshView();
		}
		
		// reset...
		_lastClicked = -1;
	}
	
	public void HandleActivityResult(int requestCode, int resultCode, Intent data)
	{
		// update...
		try
		{
			RefreshView();
		} 
		catch (Exception ex)
		{
			MessageBox.Show(this, ex);
		}
	}
}
