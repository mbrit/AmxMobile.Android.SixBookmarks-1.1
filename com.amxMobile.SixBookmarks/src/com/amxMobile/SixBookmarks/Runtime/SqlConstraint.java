package com.amxMobile.SixBookmarks.Runtime;

public abstract class SqlConstraint 
{
	public SqlConstraint()
	{
	}
	
	public abstract void Append(SqlStatement sql, StringBuilder builder) throws Exception;
}
