package com.tools.aplayer.utils;


import com.tools.aplayer.model.Content;

public class BoolUtil {

    private static final String ERROR_TAG = Content.APLAYER_DEMO_LOG_PREF_TAG + BoolUtil.class.getSimpleName();
    public static final String APLYER_STR_BOOL_TRUE = "1";
    public static final String APLYER_STR_BOOL_FALSE = "0";

    public static String BoolToAPlayerConfigBool(boolean bval)
    {
        return bval ? APLYER_STR_BOOL_TRUE : APLYER_STR_BOOL_FALSE;
    }

    public static boolean APlayerOperationRetCodeToBool(int operationRetCode)
    {
        return (Content.APLYER_SUCCESS == operationRetCode);
    }

    public static boolean APlayerConfigValToBool(int aPlayerInt)
    {
        return aPlayerInt == Content.CONFIG_SUCCESS;
    }

    public static boolean APlayerConfigValToBool(String aPlayerStr)
    {
        int aPlayerInt = StringUtil.StringToInt(aPlayerStr);
        return APlayerConfigValToBool(aPlayerInt);
    }
}
