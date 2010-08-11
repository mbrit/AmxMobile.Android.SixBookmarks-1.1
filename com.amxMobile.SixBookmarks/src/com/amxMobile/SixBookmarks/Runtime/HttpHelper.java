package com.amxMobile.SixBookmarks.Runtime;

import java.io.*;
import java.util.*;

import org.apache.http.*;
import org.apache.http.client.*;
import org.apache.http.client.methods.*;
import org.apache.http.impl.client.*;
import org.w3c.dom.*;

public class HttpHelper {

	
	public static String Download(String url, DownloadSettings settings) throws Exception
	{
		if(settings == null)
			settings  = new DownloadSettings();
		
		// create the request...
		HttpClient client = new DefaultHttpClient();
		HttpGet request = new HttpGet(url);

		// additional headers...
		Hashtable<String, String> extraHeaders = settings.getExtraHeaders();
		for(String name : extraHeaders.keySet())
			request.addHeader(name, extraHeaders.get(name));

		// get the response...
		HttpResponse response = client.execute(request);
		
		// not a 200?
		int code = response.getStatusLine().getStatusCode(); 
		if(code != 200)
			throw new Exception(String.format("Download of '%s' returned HTTP '%d' (%s).", url, code, response.getStatusLine().getReasonPhrase()));
		// get...
		HttpEntity entity = response.getEntity();
		String result = GetHttpEntityContent(entity);
		return result;
	}
	
	public static String GetHttpEntityContent(HttpEntity entity) throws Exception
	{
		// get...
		InputStreamReader stream = new InputStreamReader(entity.getContent());
		StringBuilder builder = new StringBuilder();
		try
		{
			BufferedReader reader = new BufferedReader(stream);
			while(true)
			{
				String buf = reader.readLine();
				if(buf == null)
					break;
				
				// add...
				builder.append(buf);
			}
		}
		finally
		{
			if(stream != null)
				stream.close();
		}
		
		// return...
		return builder.toString();
	}

	public static Document DownloadXml(String url, DownloadSettings settings) throws Exception 
	{
		// get the plain content...
		String xml = Download(url, settings);

		// turn that into some XML...
		Document doc = XmlHelper.LoadXml(xml);
		return doc;
	}

	public static String BuildQueryString(Hashtable<String, Object> values)
	{
		StringBuilder builder = new StringBuilder();
		for(Object key : values.keySet())
		{
			if(builder.length() > 0)
				builder.append("&");
			builder.append(key);
			builder.append("=");
			builder.append(values.get(key));
		}
		
		// return...
		String qs = builder.toString();
		return qs;
	}

	public static String BuildUrl(String url, Hashtable<String, Object> values)
	{
		int index = url.indexOf("?");
		if(index != -1) 
			url = url.substring(0, index);
		
		// add...
		url = url + "?" + BuildQueryString(values);
		return url;
	}

}
