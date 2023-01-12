package com.tools.aplayer.utils;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;


public class UIUtil {

    public static class Size
    {
        public Size(int width, int height) {
            this.width = width;
            this.height = height;
        }

        public int width;
        public int height;
    }

    public static class Postion
    {
        public Postion(int x0ffset, int y0ffset) {
            this.x0ffset = x0ffset;
            this.y0ffset = y0ffset;
        }

        public int x0ffset;
        public int y0ffset;
    }

    public static class Location
    {
        public Size size;
        public Postion postion;

        public Location()
        {
        }

        public Location(Size size, Postion postion) {
            this.size = size;
            this.postion = postion;
        }
    }
    public static Size getScrrenResolutionSize(Context context)
    {
        WindowManager windowManager = (WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics systemDispalyMetrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(systemDispalyMetrics);

        int height = systemDispalyMetrics.heightPixels;
        int widht = systemDispalyMetrics.widthPixels;

        return new Size(widht, height);
    }
}
