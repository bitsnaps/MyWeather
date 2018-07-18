package com.ssheetz.myweather.fragments;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.TypedValue;

class FragmentUtils {

    private FragmentUtils() {
    }

    public static int dpToPx(Context context, float valueInDp) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, valueInDp, metrics);
    }
}
