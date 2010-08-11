package com.amxMobile.SixBookmarks.Runtime;

import android.app.*;
import android.content.*;
import android.content.*;
import android.util.Log;

public class MessageBox {

	public static void Show(IContextSource context, String message)
	{
		Show(context.getContext(), message);
	}
	
	public static void Show(Context owner, String message)
	{
		AlertDialog.Builder builder = new AlertDialog.Builder(owner);
		builder.setTitle("Six Bookmarks");
		builder.setMessage(message);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // do something here..
            }
        });
		
		// show...
		AlertDialog dialog = builder.create();
		dialog.show();
	}
	
	public static void Show(IContextSource context, Exception ex)
	{
		Show(context.getContext(), ex);
	}
	
	public static void Show(Context owner, Exception ex)
	{
		// debug...
		Log.e(owner.getClass().getName(), ex.toString());
		ex.printStackTrace();
		
		// show...
		String message = FormatException(ex);
		Show(owner, message);
	}
	
	public static String FormatException(Exception ex)
	{
		StringBuilder builder = new StringBuilder();
		Throwable walk = ex;
		while(walk != null)
		{
			if(builder.length() > 0)
				builder.append("\n");
			builder.append(walk.getMessage());
			walk = walk.getCause();
		}
		
		// return...
		return builder.toString();
	}
}
