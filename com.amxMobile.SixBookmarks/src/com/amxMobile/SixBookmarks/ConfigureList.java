package com.amxMobile.SixBookmarks;

import com.amxMobile.SixBookmarks.Runtime.*;

import android.os.*;
import android.app.*;
import android.content.*;
import android.view.*;
import android.view.ContextMenu.*;
import android.view.View.*;
import android.widget.*;
import android.widget.AdapterView.*;

public class ConfigureList extends ListActivity implements IConfigureListView, OnItemClickListener
{
	private ConfigureListController Controller = new ConfigureListController(this);
	
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        
        // caption...
        setTitle("Configure Bookmarks");
        
        // listen for normal clicks...
        ListView list = getListView();
        list.setOnItemClickListener(this);
        
        // listen for long clicks...
        list.setLongClickable(true);
        this.registerForContextMenu(list);
        
        // defer to the controller...
        try
        {
        	Controller.HandleLoad();
        }
        catch(Exception ex)
        {
        	MessageBox.Show((Context)this, ex);
        }
    }

	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long id) 
	{
		Controller.HandleListItemClick(id);	
	}
	
    @Override
    public boolean onCreateOptionsMenu(Menu menu) 
    {
    	Controller.HandleCreateOptionsMenu(menu);
    	return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) 
    {
    	return Controller.HandleMenuItemClick(item);
    }
    
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo)
    {
    	Controller.HandleCreateContextMenu(menu, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) 
    {
    	return Controller.HandleMenuItemClick(item);
    }
    
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		Controller.HandleActivityResult(requestCode, resultCode, data);
	}
}
