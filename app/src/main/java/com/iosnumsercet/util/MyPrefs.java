package com.iosnumsercet.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;


public class MyPrefs {
	private static MyPrefs myPrefs;
	private SharedPreferences sp;
	private MyPrefs(){}

	public static MyPrefs getInstance(){
		if(myPrefs == null){
			myPrefs = new MyPrefs();
		}
		return myPrefs;
	}
	

	public MyPrefs initSharedPreferences(Context context){
		if(sp == null){
			sp = context.getSharedPreferences(Consts.PREF_NAME, 
					Context.MODE_PRIVATE);
		}
		return myPrefs;
	}

	public void writeString(String key, String value){
		Editor editor = sp.edit();
		editor.putString(key, value);
		editor.commit();
	}
	

	public String readString(String key){
		return sp.getString(key, "");
	}
}
