package com.andreschnabel.UnfollowDetector;

import android.content.Context;
import android.widget.Toast;

public final class Utils {

	private Utils() {}

	public static void showMsg(String msg, Context ctx) {
		Toast toast = Toast.makeText(ctx, msg, Toast.LENGTH_SHORT);
		toast.show();
	}

	public static boolean validScreenName(String screenName) {
		return screenName.length() > 0 && !containsWhitespace(screenName);
	}

	public static boolean containsWhitespace(String s) {
		return containsOneOf(s, "\n", " ", "\r");
	}

	public static boolean containsOneOf(String s, String... strs) {
		for(String substring : strs) {
			if(s.contains(substring)) return true;
		}
		return false;
	}
}
