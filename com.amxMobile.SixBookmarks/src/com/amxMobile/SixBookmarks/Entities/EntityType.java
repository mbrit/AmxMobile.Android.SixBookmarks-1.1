package com.amxMobile.SixBookmarks.Entities;

import java.lang.reflect.*;
import java.util.*;

public class EntityType extends EntityItem
{
	private static Hashtable<String, EntityType> _entityTypes = new Hashtable<String, EntityType>();
	
	private ArrayList<EntityField> _fields = new ArrayList<EntityField>();
	private Class _instanceType = null;
	private Class _collectionType = null;
	
	public EntityType(Class instanceType, Class collectionType, String nativeName)
	{
		super(instanceType.getName(), nativeName);
		_instanceType = instanceType;
		_collectionType = collectionType;		
	}

	public ArrayList<EntityField> getFields()
	{
		return _fields;
	}
	
	public EntityField AddField(String name, String nativeName, DataType type, int size)
	{
		EntityField field = new EntityField(name, nativeName, type, size, _fields.size());
		_fields.add(field);
		
		// return...
		return field;
	}
	
	public Class getInstanceType()
	{
		return _instanceType;
	}
	
	public Class getCollectionType()
	{
		return _collectionType;
	}

	public Entity CreateInstance() throws Exception
	{
		return (Entity)getInstanceType().newInstance();
	}

	public ArrayList<Entity> CreateCollectionInstance() throws Exception
	{
		return (ArrayList<Entity>)getCollectionType().newInstance();
	}
	
	public EntityField GetField(String name, boolean throwIfNotFound) throws Exception	
	{
		for(EntityField field : _fields)
		{
			if(field.getName().compareToIgnoreCase(name) == 0)
				return field;
		}
		
		// throw...
		if(throwIfNotFound)
			throw new Exception(String.format("Failed to find a field with name '%s'.", name));
		else
			return null;
	}
	
	public EntityField GetKeyField() throws Exception
	{
		for(EntityField field : _fields)
		{
			if(field.getIsKey())
				return field;
		}
		
		// throw...
		throw new Exception("Failed to find a key field.");
	}
	
	public boolean IsField(String name) throws Exception
	{
		EntityField field = this.GetField(name, false);
		if(field != null)
			return true;
		else
			return false;
	}
	
	public static void RegisterEntityType(EntityType entityType)
	{
		_entityTypes.put(entityType.getInstanceType().getName(), entityType);
	}
	
	public static EntityType GetEntityType(Class type) throws Exception
	{
		String name = type.getName();
		for(String key : _entityTypes.keySet())
		{
			if(key.compareTo(name) == 0)
				return _entityTypes.get(key);
		}
		
		// throw...
		throw new Exception(String.format("Failed to get entity type for '%s'.", type));
	}
	
	public String getShortName()
	{
		String name = getName();
		int index = name.lastIndexOf((char)'.');
		return name.substring(index + 1);
	}
}
