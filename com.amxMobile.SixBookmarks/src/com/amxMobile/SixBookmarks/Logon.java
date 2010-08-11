package com.amxMobile.SixBookmarks;

import com.amxMobile.SixBookmarks.R;
import com.amxMobile.SixBookmarks.Runtime.*;
import android.app.*;
import android.os.*;
import android.view.*;
import android.view.View.OnClickListener;
import android.widget.*;

public class Logon extends Activity implements OnClickListener, ILogonView 
{
	private LogonController Controller;
	
	public Logon()
	{
		this.Controller = new LogonController(this);
	}
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.logon);
        
        // start the runtime...
        SixBookmarksRuntime.Start();
        
        // subscribe...
        getLogonButton().setOnClickListener(this);
        
        // load...
        try
        {
        	Controller.HandleLoad();
        }
        catch(Exception ex)
        {
        	MessageBox.Show(this, ex);
        }
    }
    
    private EditText getUsernameTextBox()
    {
    	return (EditText)this.findViewById(R.id.textUsername);
    }

    private EditText getPasswordTextBox()
    {
    	return (EditText)this.findViewById(R.id.textPassword);
    }

    private CheckBox getRememberMeCheckBox()
    {
    	return (CheckBox)this.findViewById(R.id.checkRememberMe);
    }
    
    private Button getLogonButton()
    {
    	return (Button)this.findViewById(R.id.buttonLogon);
    }
    
    public String getUsername()
    {
    	return getUsernameTextBox().getText().toString().trim();
    }
    
    public String getPassword()
    {
    	return getPasswordTextBox().getText().toString().trim();
    }
    
    public boolean getRememberMe()
    {
    	return getRememberMeCheckBox().isChecked();
    }
    
	public void onClick(View view) 
	{
		try
		{
			if(view.getId() == R.id.buttonLogon)
				this.Controller.HandleLogon();
		}
		catch(Exception ex)
		{
			MessageBox.Show(this, ex);
		}
	}
}
