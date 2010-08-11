package com.amxMobile.SixBookmarks.Runtime;

import java.net.URI;

import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;

public class HttpMerge extends HttpEntityEnclosingRequestBase
{
	public HttpMerge(String url) throws Exception
	{
		this(new URI(url));
	}
	
	public HttpMerge(URI uri)
	{
		this.setURI(uri);
	}

	@Override
	public String getMethod()
	{
		return "MERGE";
	}
}
