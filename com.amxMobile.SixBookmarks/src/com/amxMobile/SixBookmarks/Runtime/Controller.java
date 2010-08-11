package com.amxMobile.SixBookmarks.Runtime;

import android.app.*;
import android.content.*;

public abstract class Controller<T> implements IController
{
	private T _view;
	
	protected Controller(T view)
	{
		this._view = view;
	}
	
	public T getView()
	{
		return _view;
	}
	
	public Activity getActivity()
	{
		return (Activity)_view;
	}
	
	public Context getContext() 
	{
		return getActivity();
	}
}
