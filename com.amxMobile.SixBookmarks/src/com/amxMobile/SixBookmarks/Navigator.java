package com.amxMobile.SixBookmarks;

import com.amxMobile.SixBookmarks.Runtime.*;
import android.app.Activity;
import android.os.Bundle;
import android.view.*;
import android.view.View.OnClickListener;
import android.widget.*;

public class Navigator extends Activity implements OnClickListener, INavigatorView
{
	private NavigatorController Controller;
	
	public Navigator()
	{
		this.Controller = new NavigatorController(this);
	}
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.navigator);
        
        // wire up the buttons...
        findViewById(R.id.buttonNavigate1).setOnClickListener(this);
        findViewById(R.id.buttonNavigate2).setOnClickListener(this);
        findViewById(R.id.buttonNavigate3).setOnClickListener(this);
        findViewById(R.id.buttonNavigate4).setOnClickListener(this);
        findViewById(R.id.buttonNavigate5).setOnClickListener(this);
        findViewById(R.id.buttonNavigate6).setOnClickListener(this);
        findViewById(R.id.buttonConfigure).setOnClickListener(this);
        findViewById(R.id.buttonLogoff).setOnClickListener(this);
        findViewById(R.id.buttonAbout).setOnClickListener(this);
        
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

	public void onClick(View v) 
	{
		if(v.getId() == R.id.buttonConfigure)
			this.Controller.HandleConfigure();
		else if(v.getId() == R.id.buttonLogoff)
			this.Controller.HandleLogoff();
		else if(v.getId() == R.id.buttonAbout)
			this.Controller.HandleAbout();
		else if(v.getId() == R.id.buttonNavigate1)
			this.Controller.HandleNavigate(0);
		else if(v.getId() == R.id.buttonNavigate2)
			this.Controller.HandleNavigate(1);
		else if(v.getId() == R.id.buttonNavigate3)
			this.Controller.HandleNavigate(2);
		else if(v.getId() == R.id.buttonNavigate4)
			this.Controller.HandleNavigate(3);
		else if(v.getId() == R.id.buttonNavigate5)
			this.Controller.HandleNavigate(4);
		else if(v.getId() == R.id.buttonNavigate6)
			this.Controller.HandleNavigate(5);
	}

	private Button GetBookmarkButton(int ordinal) throws Exception
	{
		int id = 0;
		if(ordinal == 0)
			id = R.id.buttonNavigate1;
		else if(ordinal == 1)
			id = R.id.buttonNavigate2;
		else if(ordinal == 2)
			id = R.id.buttonNavigate3;
		else if(ordinal == 3)
			id = R.id.buttonNavigate4;
		else if(ordinal == 4)
			id = R.id.buttonNavigate5;
		else if(ordinal == 5)
			id = R.id.buttonNavigate6;
		else
			throw new Exception(String.format("'%d' is an invalid ordinal.", ordinal));
		
		// return...
		return (Button) this.findViewById(id);
	}
	
	public void ResetBookmarkButton(int ordinal) throws Exception
	{
		Button button = GetBookmarkButton(ordinal);
		button.setText("...");
	}
	
	public void ConfigureBookmarkButton(int ordinal, String name) throws Exception
	{
		Button button = GetBookmarkButton(ordinal);
		button.setText(name);
	}
}
