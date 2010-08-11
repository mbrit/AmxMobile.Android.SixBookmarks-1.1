package com.amxMobile.SixBookmarks;

import android.widget.*;

public interface INavigatorView 
{
	public void ResetBookmarkButton(int ordinal) throws Exception;
	public void ConfigureBookmarkButton(int ordinal, String name) throws Exception;
} 
