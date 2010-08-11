package com.amxMobile.SixBookmarks.Services;

import java.io.*;

import org.apache.http.*;
import org.apache.http.message.BasicHeader;

public class ODataEntity implements HttpEntity
{
	private static final String Encoding = "UTF-8"; 
	
	private byte[] _bytes;
	
	public ODataEntity(String xml) throws Exception
	{
		_bytes = xml.getBytes(Encoding);
	}

	public void consumeContent() throws IOException
	{
	}

	public InputStream getContent() throws IOException, IllegalStateException
	{
		return null;
	}

	public Header getContentEncoding()
	{
		return new BasicHeader("content-encoding", Encoding);
	}

	public long getContentLength()
	{
		return _bytes.length;
	}

	public Header getContentType()
	{
		return new BasicHeader("content-type", "application/atom+xml");
	}

	public boolean isChunked()
	{
		return false;
	}

	public boolean isRepeatable()
	{
		return false;
	}

	public boolean isStreaming()
	{
		return false;
	}

	public void writeTo(OutputStream outstream) throws IOException
	{
		outstream.write(_bytes);
	}
}
