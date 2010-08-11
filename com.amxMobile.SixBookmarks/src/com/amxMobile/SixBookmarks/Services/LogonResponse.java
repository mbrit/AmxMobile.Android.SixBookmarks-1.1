package com.amxMobile.SixBookmarks.Services;

public class LogonResponse 
{
	private String _token;
	private String _message;
	private LogonResult _result;
	
	public LogonResponse(LogonResult result, String message, String token)
	{
		_result = result;
		_message = message;
		_token = token;
	}
	
	public String getMessage()
	{		
		return _message;
	}
	
	public String getToken()
	{		
		return _token;
	}
	
	public LogonResult getResult()
	{
		return _result;
	}
	
	public static LogonResult ParseLogonResult(String asString) throws Exception
	{
		return LogonResult.valueOf(asString);
	}
}
