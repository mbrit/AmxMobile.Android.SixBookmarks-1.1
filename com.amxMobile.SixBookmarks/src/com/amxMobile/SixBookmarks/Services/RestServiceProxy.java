package com.amxMobile.SixBookmarks.Services;

import org.w3c.dom.*;
import com.amxMobile.SixBookmarks.Runtime.*;

public class RestServiceProxy extends ServiceProxy 
{
    protected RestServiceProxy(String serviceName) 
    {
		super(serviceName);
	}

	protected Element SendRequest(RestRequestArgs args) throws Exception
    {
        // ensure that we have an authenticated API...
        this.EnsureApiAuthenticated();

        // get the URL...
        String url = this.getResolvedServiceUrl();
        url = HttpHelper.BuildUrl(url, args);

        // download...
        Document doc = HttpHelper.DownloadXml(url, GetDownloadSettings());
		try
		{
			// find the response...
			Element root = doc.getDocumentElement();
			if(root.getNodeName().compareTo("AmxResponse") != 0)
				throw new Exception(String.format("The root element had an invalid name of '%s'.", root.getNodeName()));
			
			// get...
			boolean hasException = XmlHelper.GetElementBoolean(root, "HasException", true);
			if(!(hasException))
				return root;
			else 
			{
				// get the error...
				String error = XmlHelper.GetElementString(root, "Error", true);
				throw new Exception(String.format("The server returned an error: %s", error));
			}
		}
		catch(Exception ex)
		{
			throw new Exception(String.format("An error occurred when processing a response returned from a REST request.\nURL: %s", url), ex);
		}
    }
}
