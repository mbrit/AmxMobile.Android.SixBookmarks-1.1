package com.amxMobile.SixBookmarks.Runtime;

import java.util.*;
import com.amxMobile.SixBookmarks.Entities.*;
import android.database.*;
import android.database.sqlite.*;

public class SqlFilter implements ISqlStatementSource
{
	private EntityType _entityType;
	
	private ArrayList<SqlConstraint> _constraints = new ArrayList<SqlConstraint>(); 
	
	public SqlFilter(Class type) throws Exception
	{
		this(EntityType.GetEntityType(type));
	}
	
	public SqlFilter(EntityType et)
	{
		_entityType = et;
	}
	
	private EntityType getEntityType()
	{
		return _entityType;
	}
	
	public SqlStatement GetSqlStatement() throws Exception
	{
		SqlStatement sql = new SqlStatement();
		StringBuilder builder = new StringBuilder();
		
		// et...
		EntityType et = getEntityType();
		
		// columns...
		builder.append("SELECT ");
		ArrayList<EntityField> fields = et.getFields();
		for(int index = 0; index < fields.size(); index++)
		{
			if(index > 0)
				builder.append(", ");
			builder.append(fields.get(index).getNativeName());
		}
		
		// from...
		builder.append(" FROM ");
		builder.append(et.getNativeName());
		
		// where...
		ArrayList<SqlConstraint> constraints = getConstraints(); 
		if(constraints.size() > 0) 
		{
			builder.append(" WHERE ");
			for(int index = 0; index < constraints.size(); index++)
			{
				if(index > 0)
					builder.append(" AND ");
				constraints.get(index).Append(sql, builder);
			}
		}
		
		// return...
		sql.setCommandText(builder.toString());
		return sql;
	}


	public ArrayList ExecuteEntityCollection(IContextSource context) throws Exception 
	{
		// shortcut method - defer to the helper...
		DatabaseHelper db = new DatabaseHelper(context);
		return db.ExecuteEntityCollection(this, getEntityType());
	}
	
	private ArrayList<SqlConstraint> getConstraints()
	{
		return _constraints;
	}

	public void AddConstraint(String name, Object value) throws Exception 
	{
		EntityField field = getEntityType().GetField(name, true);
		AddConstraint(field, value);
	}
	
	public void AddConstraint(EntityField field, Object value)
	{
		getConstraints().add(new SqlFieldConstraint(field, value));
	}

	public void AddConstraint(String name, SqlOperator op, Object value) throws Exception 
	{
		EntityField field = getEntityType().GetField(name, true);
		AddConstraint(field, op, value);
	}
	
	public void AddConstraint(EntityField field, SqlOperator op,Object value)
	{
		getConstraints().add(new SqlFieldConstraint(field, op, value));
	}

	public Entity ExecuteEntity(IContextSource context) throws Exception
	{
		ArrayList items = ExecuteEntityCollection(context);
		if(items.size() > 0)
			return (Entity)items.get(0);
		else
			return null;
	}
}
