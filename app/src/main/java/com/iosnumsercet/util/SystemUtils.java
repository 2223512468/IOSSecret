package com.iosnumsercet.util;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;


public class SystemUtils {

	public static int[] getSystemDisplay(Context context){
		DisplayMetrics dm = new DisplayMetrics();
		WindowManager wm =  (WindowManager) context.getSystemService(
				Context.WINDOW_SERVICE);
		wm.getDefaultDisplay().getMetrics(dm);
		int[] displays = new int[2];
		displays[0] = dm.widthPixels;
		displays[1] = dm.heightPixels;
		return displays;
	}
	
}
