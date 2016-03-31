package com.lajiaolc.admodule.yibiaopan.utils;

import android.app.Activity;
import android.view.View;

public final class UI {

	@SuppressWarnings("unchecked")
	public static <T> T findViewById(View parent, int id) {
		return (T) parent.findViewById(id);
	}

	@SuppressWarnings("unchecked")
	public static <T> T findViewById(Activity activity, int id) {
		return (T) activity.findViewById(id);
	}

}
