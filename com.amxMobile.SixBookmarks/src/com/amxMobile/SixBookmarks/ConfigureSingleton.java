package com.amxMobile.SixBookmarks;

import com.amxMobile.SixBookmarks.Runtime.*;
import android.app.*;
import android.os.*;
import android.view.*;
import android.view.View.*;
import android.widget.*;

public class ConfigureSingleton extends Activity implements OnClickListener, IConfigureSingletonView 
{
	private ConfigureSingletonController Controller;

	private int _ordinal;
	
	public ConfigureSingleton()
	{
		this.Controller = new ConfigureSingletonController(this);
	}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.configuresingleton);
        
        // wire up the buttons...
        findViewById(R.id.buttonSave).setOnClickListener(this);
        
        // tell the controller to load...
        try
        {
        	Controller.HandleLoad();
        }
        catch(Exception ex)
        {
        	MessageBox.Show(this, ex);
        }
    }
    
	public int getOrdinal() 
	{
		return _ordinal;
	}

	private EditText getNameTextBox()
	{
		return (EditText)findViewById(R.id.textName);
	}
	
	private EditText getUrlTextBox()
	{
		return (EditText)findViewById(R.id.textUrl);
	}
	
	public String getName() 
	{
		return getNameTextBox().getText().toString().trim();
	}

	public String getUrl() 
	{
		return getUrlTextBox().getText().toString().trim();
	}

	public void setOrdinal(int ordinal) 
	{
		_ordinal = ordinal;
	}

	public void setName(String name) 
	{
		getNameTextBox().setText(name);
	}

	public void setUrl(String url) 
	{
		getUrlTextBox().setText(url);
	}

	public void onClick(View v) 
	{
		try
		{
			int id = v.getId();
			if(id == R.id.buttonSave)
				Controller.HandleSave();
		}
		catch(Exception ex)
		{
			MessageBox.Show(this, ex);
		}
	}
}
