package com.amxMobile.SixBookmarks.Runtime;

import java.util.*;

public class SqlStatement implements ISqlStatementSource 
{
	private String _commandText;
	private ArrayList<Object> _parameterValues = new ArrayList<Object>();
	
	public SqlStatement()
	{
	}
	
	public SqlStatement(String commandText)
	{
		_commandText = commandText;
	}
	
	public String getCommandText()
	{
		return _commandText;
	}
	
	public void setCommandText(String commandText)
	{
		_commandText = commandText;
	}
	
	public SqlStatement GetSqlStatement()
	{
		return this;
	}
	
	public void AddParameterValue(Object value)
	{
		_parameterValues.add(value);
	}
	
	public Object[] getParameterValues()
	{
		return _parameterValues.toArray(); 
	}
}
