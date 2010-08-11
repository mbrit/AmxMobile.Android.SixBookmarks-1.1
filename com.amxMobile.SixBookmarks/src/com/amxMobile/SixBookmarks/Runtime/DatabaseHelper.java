package com.amxMobile.SixBookmarks.Runtime;

import java.util.*;

import com.amxMobile.SixBookmarks.Entities.*;
import com.amxMobile.SixBookmarks.Runtime.*;
import android.content.*;
import android.database.Cursor;
import android.database.sqlite.*;

public class DatabaseHelper extends SQLiteOpenHelper 
{
	private static ArrayList<String> _loadMap = new ArrayList<String>();
	
	private static String DatabaseName = null;
	
	public DatabaseHelper(IContextSource context)
	{
		this(context.getContext());
	}

	public DatabaseHelper(Context context)
	{
		// base...
		super(context, DatabaseName, null, 1);
	}

	public static void setUser(String username)
	{
		DatabaseName = "SixBookmarks-" + username;
	}

	@Override
	public void onCreate(SQLiteDatabase db) 
	{
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) 
	{
	}
	
	private SqlStatement GetCreateScript(EntityType et) throws Exception
	{
		StringBuilder builder = new StringBuilder();
		builder.append("CREATE TABLE IF NOT EXISTS ");
		builder.append(et.getNativeName());	// now the columns...
		builder.append(" (");
		ArrayList<EntityField> fields = et.getFields(); 
		for(int index = 0; index < fields.size(); index++)
		{
			if(index > 0)
				builder.append(", ");
			
			// defer...
			AppendCreateSnippet(builder, fields.get(index)); 
		}
		builder.append(")");
		
		// return...
		return new SqlStatement(builder.toString());
	}
	
	private void AppendCreateSnippet(StringBuilder builder, EntityField field) throws Exception
	{
		builder.append(field.getNativeName());
		builder.append(" ");
		
		// switch...
		DataType type = field.getDataType();
		if(type == DataType.String)
		{
			builder.append("varchar(");
			builder.append(field.getSize());
			builder.append(")");
		}
		else if(type == DataType.Int32)
		{
			builder.append("integer");
			
			// key?
			if(field.getIsKey())
				builder.append(" primary key autoincrement");
		}
		else
			throw new Exception(String.format("Cannot handle '%s'.", field.getDataType()));
	}

	public void EnsureTableExists(EntityType et) throws Exception
	{
		// have we already called it?
		String name = et.getName();
		if(_loadMap.contains(name))
			return;
		
		// create...
		SqlStatement sql = GetCreateScript(et);
		ExecuteNonQuery(sql, true);
		
		// add...
		_loadMap.add(name);
	}
	
	public void ExecuteNonQuery(ISqlStatementSource sql, boolean writable) throws Exception
	{
		SqlStatement statement = sql.GetSqlStatement();
		
		// open...
		SQLiteDatabase db = null;
		if(writable)
			db = this.getWritableDatabase();
		else
			db = this.getReadableDatabase();
		try
		{
			db.execSQL(statement.getCommandText(), statement.getParameterValues());
		}
		finally
		{
			if(db != null)
				db.close();
		}
	}	

	public ArrayList<Entity> ExecuteEntityCollection(ISqlStatementSource sql, EntityType et) throws Exception
	{
		// get...
		SqlStatement realSql = sql.GetSqlStatement();
		SQLiteDatabase db = getReadableDatabase();
		Cursor c = null;
		try
		{
			// execute a cursor...
			ArrayList<Entity> results = et.CreateCollectionInstance();
			
			// cursor...
			c = db.rawQuery(realSql.getCommandText(), MangleParameterValues(realSql.getParameterValues()));
			while(c.moveToNext())
			{
				// load...
				Entity entity = LoadEntity(c, et);
				results.add(entity);
			}
			
			// return...
			return results;
		}
		finally
		{
			if(c != null)
				c.close();
			if(db != null)
				db.close();
		}
	}
	
	private String[] MangleParameterValues(Object[] values)
	{
		String[] args = new String[values.length];
		for(int index = 0; index < values.length; index++)
			args[index] = values[index].toString();
		
		// return...
		return args;
	}
	
	private Entity LoadEntity(Cursor c, EntityType et) throws Exception
	{
		// create a new instance...
		Entity entity = et.CreateInstance();
		
		// load data...
		for(EntityField field : et.getFields())
		{
			DataType type = field.getDataType();
			if(type == DataType.String)
				entity.SetValue(field, c.getString(field.getOrdinal()), SetReason.Load);
			else if(type == DataType.Int32)
				entity.SetValue(field, c.getInt(field.getOrdinal()), SetReason.Load);
			else
				throw new Exception(String.format("Cannot handle '%s'.", field.getDataType())); 
		}
		
		// return...
		return entity;
	}
}
