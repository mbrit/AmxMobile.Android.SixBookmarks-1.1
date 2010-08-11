package com.amxMobile.SixBookmarks.Runtime;

import java.io.*;
import javax.xml.parsers.*;
import org.w3c.dom.*;

public class XmlHelper 
{
	public static Document LoadXml(String xml) throws Exception
	{
		// create...
		ByteArrayInputStream stream = new ByteArrayInputStream(xml.getBytes());
		try
		{
			return LoadXml(stream);
		}
		finally
		{
			if(stream != null)
				stream.close();
		}
	}
	
	private static Document LoadXml(InputStream stream) throws Exception
	{
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setNamespaceAware(true);
		
		// builder...
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document doc = builder.parse(stream);
		
		// return...
		return doc;
	}

	public static String GetElementString(Element element, String name, boolean throwIfNotFound) throws Exception
	{
		return (String)GetElementValue(element, name, XmlDataType.String, throwIfNotFound);
	}
	
	public static boolean GetElementBoolean(Element element, String name, boolean throwIfNotFound) throws Exception
	{
		return ((Boolean)GetElementValue(element, name, XmlDataType.Boolean, throwIfNotFound)).booleanValue();
	}
	
	public static int GetElementInt32(Element element, String name, boolean throwIfNotFound) throws Exception
	{
		return ((Number)GetElementValue(element, name, XmlDataType.Int32, throwIfNotFound)).intValue();
	}
	
	private static Object GetElementValue(Element element, String name, XmlDataType dt, boolean throwIfNotFound) throws Exception
	{
		// find it...
		NodeList nodes = element.getElementsByTagName(name);
		if(nodes.getLength() == 1)
		{
			if(dt == XmlDataType.String)
				return GetStringValue(nodes.item(0));
			else if(dt == XmlDataType.Boolean)
				return (Boolean)GetBooleanValue(nodes.item(0));
			else if(dt == XmlDataType.Int32)
				return (Number)GetInt32Value(nodes.item(0));
			else
				throw new Exception(String.format("Cannot handle '%s'.", dt));
		}
		else if(nodes.getLength() == 0)
		{
			if(throwIfNotFound)
				throw new Exception(String.format("An element with name '%s' was not found within an element with name '%s'.", name, element.getNodeName()));
			else
				return null;
		}
		else
			throw new Exception(String.format("Too many (%d) child elements were found.", nodes.getLength()));
	}
	
	public static String GetStringValue(Node item) throws Exception
	{
		if(item instanceof Element)
		{
			Node node = item.getFirstChild();
			if(node != null)
				return node.getNodeValue();
			else
				return "";
		}
		else
			throw new Exception(String.format("Cannot handle '%s'.", item.getClass()));
	}

	public static int GetInt32Value(Node item) throws Exception
	{
		String asString = GetStringValue(item);
		return Integer.parseInt(asString);
	}

	public static boolean GetBooleanValue(Node item) throws Exception
	{
		String asString = GetStringValue(item);
		if(asString.compareTo("0") == 0 || asString.compareToIgnoreCase("false") == 0)
			return false;
		else if(asString.compareTo("1") == 0 || asString.compareToIgnoreCase("true") == 0)
			return true;
		else
			throw new Exception(String.format("The value '%s' could not be recognised as valid Boolean value.", asString));
	}
	
	public static Element GetElement(Element root, String namespaceUri, String name, boolean throwIfNotFound) throws Exception
	{
		NodeList nodes = root.getElementsByTagNameNS(namespaceUri, name);
		if(nodes.getLength() > 0)
			return (Element)nodes.item(0);
		else
		{
			if(throwIfNotFound)
				throw new Exception(String.format("A node with name '%s' in namespace '%s' was not found.", name, namespaceUri));
			else
				return null;
		}
	}
}
