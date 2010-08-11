package com.amxMobile.SixBookmarks.Services;

import com.amxMobile.SixBookmarks.Runtime.*;

public abstract class ServiceProxy 
{
	private String _serviceName;
	private static String _token;
	
	private final String RootUrl = "http://services.multimobiledevelopment.com/services/";
	
	// YOU MUST CHANGE THESE VALUES IN ORDER TO USE THIS SAMPLE...
	protected final String ApiUsername = "amxmobile";
	private final String ApiPassword = "password";
	
	protected ServiceProxy(String serviceName)
	{
		_serviceName = serviceName;
	}
	
	public String getServiceName()
	{
		return _serviceName;
	}
	
	public String getResolvedServiceUrl()
	{
		return RootUrl + getServiceName();
	}
	
	protected static String getToken()
	{
		return _token;
	}
	
	protected void setToken(String token)
	{
		_token = token;
	}
	
    protected DownloadSettings GetDownloadSettings()
    {
        DownloadSettings settings = new DownloadSettings();
        settings.AddHeader("x-amx-apiusername", ApiUsername);
        settings.AddHeader("x-amx-token", getToken());
        
        // return...
        return settings;
    }
    
    protected void EnsureApiAuthenticated() throws Exception
    {
        // check that we've authenticated...
        String asString = getToken();
        if (asString == null || asString.length() == 0)
        {
            // call up to the API service...
            ApiService service = new ApiService();
            LogonResponse response = service.Logon(ApiPassword);
            if (response == null)
                throw new Exception("'response' is null.");

            // can we?
            if (response.getResult() == LogonResult.LogonOk)
                this.setToken(response.getToken());
            else 
            {
                throw new Exception(String.format("The server request failed with the error '%s'.  Ensure that you have set the values of the ApiUsername and ApiPassword constants to the credentials of your Six Bookmarks service account at %s.",
                    response.getResult(), RootUrl));
            }
        }
    }

}
