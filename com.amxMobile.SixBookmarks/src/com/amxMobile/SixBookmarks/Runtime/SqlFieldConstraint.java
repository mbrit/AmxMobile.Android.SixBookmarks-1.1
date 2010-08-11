package com.amxMobile.SixBookmarks.Runtime;

import com.amxMobile.SixBookmarks.Entities.*;

public class SqlFieldConstraint extends SqlConstraint
{
	private EntityField _field;
	private Object _value;
	private SqlOperator _operator;
	
	public SqlFieldConstraint(EntityField field, Object value)
	{
		this(field, SqlOperator.EqualTo, value);
	}
	
	public SqlFieldConstraint(EntityField field, SqlOperator op, Object value)
	{
		_field = field;
		_value = value;
		_operator = op;
	}
	
	public EntityField getField()
	{
		return _field;
	}
	
	public Object getValue()
	{
		return _value;
	}
	
	public SqlOperator getOperator()
	{
		return _operator;
	}
	
	@Override
	public void Append(SqlStatement sql, StringBuilder builder) throws Exception 
	{
		// add the snippet...
		EntityField field = getField();
		builder.append(field.getNativeName());
		
		// what operator?
		if(_operator == SqlOperator.EqualTo)
			builder.append("=");
		else if(_operator == SqlOperator.NotEqualTo)
			builder.append(" <> ");
		else
			throw new Exception(String.format("Cannot handle '%s'.", _operator));
		
		// add the parameter and its value value...
		builder.append("?");
		sql.AddParameterValue(getValue());
	}
}
