package com.amxMobile.SixBookmarks;

import android.content.*;
import com.amxMobile.SixBookmarks.Runtime.*;
import com.amxMobile.SixBookmarks.Database.*;
import com.amxMobile.SixBookmarks.Services.*;

public class LogonController extends Controller<ILogonView> 
{
	private final static String PreferencesFilename = "UsernamePreferences";
	private final static String UsernameKey = "Username";
	private final static String PasswordKey = "Password";

	public LogonController(ILogonView view)
	{
		super(view);
	}
	
	public void HandleLoad() throws Exception
	{
		SharedPreferences prefs = getContext().getSharedPreferences(PreferencesFilename, 0);
		String username = prefs.getString(UsernameKey, null);
		String password = prefs.getString(PasswordKey, null);
		
		// can we automate the logon?
		if(username != null && username.length() > 0)
		{
			// try - if we fail clear the credentials and prompt the user...
			if(!(DoLogon(username, password, true)))
				ClearCredentials();
		}
	}
	
	public void HandleLogon() throws Exception
	{
		ILogonView view = getView();
		
		ErrorBucket bucket = new ErrorBucket();
		String username = view.getUsername();
		if(username == null || username.length() == 0)
			bucket.AddError("Username not specified");
		String password = view.getPassword();
		if(password == null || password.length() == 0)
			bucket.AddError("Password not specified");
		
		// errors?
		if(!(bucket.getHasErrors()))
			DoLogon(username, password, view.getRememberMe());
		else
			MessageBox.Show(this, bucket.GetAllErrors());
	}	

	private boolean DoLogon(String username, String password, boolean rememberMe) throws Exception
	{
		UsersService users = new UsersService();
		LogonResponse response = users.Logon(username, password);
		
		// now what?
		if(response.getResult() == LogonResult.LogonOk)
		{			
			// store the credentials?
			if(rememberMe)
				StoreCredentials(username, password);
			else
				ClearCredentials();
			
			// set the user...
			DatabaseHelper.setUser(username);
			
			// do a sync...
			Sync sync = new Sync();
			sync.DoSync(this);
			
			// create an intent and show the navigator...
			Intent newIntent = new Intent(getActivity(), Navigator.class);
			getActivity().startActivity(newIntent);
			
			// return...
			return true;
		}
		else
		{
			MessageBox.Show(this, response.getMessage());
			return false;
		}
	}
	
	private void StoreCredentials(String username, String password)
	{
		// get the prefs...
		SharedPreferences prefs = getContext().getSharedPreferences(PreferencesFilename, 0);
		SharedPreferences.Editor editor = prefs.edit();
		editor.putString(UsernameKey, username);
		editor.putString(PasswordKey, password);
		
		// save...
		editor.commit();
	}
	
	private void ClearCredentials()
	{
		// store a null string...
		this.StoreCredentials(null, null);
	}
}
