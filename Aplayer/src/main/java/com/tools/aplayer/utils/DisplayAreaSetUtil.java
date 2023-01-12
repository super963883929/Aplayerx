package com.tools.aplayer.utils;

import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.tools.aplayer.model.Content;



public class DisplayAreaSetUtil {

    private static final String ERROR_TAGE = Content.APLAYER_DEMO_LOG_PREF_TAG + DisplayAreaSetUtil.class.getSimpleName();
    private static final String SEMICOLON = ";";

    public static void adjustFullScreenDisplaySize(View displayView)
    {
        ViewGroup.LayoutParams layoutParams = displayView.getLayoutParams();
        UIUtil.Size size  = calcFullScreenDisplay(displayView);
        setSize(displayView, size.width, size.height);
    }

    public static void adjustMatchParentDisplaySize(View displayView)
    {
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) displayView.getLayoutParams();
        layoutParams.gravity = Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL;
        layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
        layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT;
        displayView.setLayoutParams(layoutParams);
    }

    public static void adjustCustomRatioDisplaySize(View displayView, double aspectRation)
    {
        UIUtil.Size size  = calcDisplayArea(displayView, aspectRation);
        setSize(displayView, size.width, size.height);
    }

    private static void setSize(View displayView, int displayAreaWidth, int displayAreaHeight)
    {
        UIUtil.Size scrrenSize = UIUtil.getScrrenResolutionSize(displayView.getContext());
        int scrrenWidth = scrrenSize.width;
        int scrrenHeight = scrrenSize.height;

        displayAreaWidth = displayAreaWidth > scrrenWidth ? scrrenWidth : displayAreaWidth;
        displayAreaHeight = displayAreaHeight > scrrenHeight ? scrrenHeight : displayAreaHeight;

        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) displayView.getLayoutParams();
        layoutParams.height = displayAreaHeight;
        layoutParams.width = displayAreaWidth;
        layoutParams.gravity = Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL;
        displayView.setLayoutParams(layoutParams);
//
//        int leftOffset = (scrrenWidth - displayAreaWidth) / 2;
//        int topOffset = (scrrenHeight - displayAreaHeight) / 2;
//        layoutParams.setMargins(leftOffset, topOffset, 0, 0);

    }

    private static UIUtil.Size calcFullScreenDisplay(View displayView)
    {
        UIUtil.Size scrrenSize = UIUtil.getScrrenResolutionSize(displayView.getContext());
        return scrrenSize;
    }
    //aspectRation = width / height
    private static UIUtil.Size calcDisplayArea(View displayView, double aspectRation)
    {
        UIUtil.Size scrrenSize = UIUtil.getScrrenResolutionSize(displayView.getContext());
        int scrrenWidth = scrrenSize.width;
        int scrrenHeight = scrrenSize.height;
        double screenAspectRatio = scrrenWidth * 1.0d / scrrenHeight;

        int displayAreaWidth = scrrenWidth;
        int displayAreaHeight = scrrenHeight;
        if(screenAspectRatio > aspectRation)
        {
            displayAreaWidth = (int)(displayAreaHeight * aspectRation);
        }

        if(screenAspectRatio < aspectRation)
        {
            displayAreaHeight = (int)(displayAreaWidth / aspectRation);
        }

        UIUtil.Size displaySize = new UIUtil.Size(displayAreaWidth, displayAreaHeight);
        return displaySize;
    }

    //return aspectRation = width / height, input string type is "height;width"
    public static double parseAspectRatio(String aspectRatio)
    {
        if(null == aspectRatio || aspectRatio.isEmpty())
        {
            return 0.0d;
        }

        String[] sizeArray = aspectRatio.split(SEMICOLON);
        if(2 != sizeArray.length  ||
           sizeArray[0].isEmpty() ||
           sizeArray[1].isEmpty() )
        {
            return 0.0d;
        }

        int widthCofficient = StringUtil.StringToInt(sizeArray[0]);
        int heightCoefficient = StringUtil.StringToInt(sizeArray[1]);
        if(0 == heightCoefficient)
        {
            return 0.0d;
        }

        return widthCofficient * 1.0d / heightCoefficient;
    }

    public static String makeAspectRatioString(int displayAreaWidth, int displayAreaHeight)
    {
        String aspectRatio = String.format("%d%s%d", displayAreaWidth, SEMICOLON, displayAreaHeight);
        return aspectRatio;
    }
}
