package com.tools.aplayer.playImplement;

import com.aplayer.APlayerAndroid;
import com.aplayer.Log;
import com.tools.aplayer.playInterface.IConfigSubtitle;
import com.tools.aplayer.utils.BoolUtil;
import com.tools.aplayer.utils.StringUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class ConfigSubtitle implements IConfigSubtitle {

    private APlayerAndroid mAPlayerAndroid = null;

    public ConfigSubtitle(APlayerAndroid aPlayerAndroid) {
        mAPlayerAndroid = aPlayerAndroid;
    }

    @Override
    public boolean isUsable() {
        String strUsable = mAPlayerAndroid.getConfig(APlayerAndroid.CONFIGID.SUBTITLE_USABLE);
        return BoolUtil.APlayerConfigValToBool(strUsable);
    }

    @Override
    public boolean isShow() {
        //String strIsShow = mAPlayerAndroid.getConfig(APlayerAndroid.CONFIGID.SUBTITLE_SHOW);
        //return BoolUtil.APlayerConfigValToBool(strIsShow);
        return true;
    }

    @Override
    public boolean show(boolean isShow) {
        //String strIsShow = BoolUtil.BoolToAPlayerConfigBool(isShow);
        //int  setShowRetCode = mAPlayerAndroid.setConfig(APlayerAndroid.CONFIGID.SUBTITLE_SHOW, strIsShow);
        //return BoolUtil.APlayerConfigValToBool(setShowRetCode);
        return true;
    }

    @Override
    public String getExternalSupportType() {
        String subtitlSupportExternalType = mAPlayerAndroid.getConfig(APlayerAndroid.CONFIGID.SUBTITLE_EXT_NAME);
        return subtitlSupportExternalType;
    }

    @Override
    public String getExternalSubtitlePath() {
        String externalSubtitlePath = mAPlayerAndroid.getConfig(APlayerAndroid.CONFIGID.SUBTITLE_FILE_NAME);
        return externalSubtitlePath;
    }

    @Override
    public boolean setExternalSubtitlePath(String externalSubtitlePath) {
        int setRetCode = mAPlayerAndroid.setConfig(APlayerAndroid.CONFIGID.SUBTITLE_FILE_NAME, externalSubtitlePath);
        Log.i("APlayerAndroid","setExternalSubtitlePath ret = " + setRetCode);
        return BoolUtil.APlayerConfigValToBool(setRetCode);
    }

    @Override
    public List<String> getSubtitleList() {
        String strSubtitLanguage = mAPlayerAndroid.getConfig(APlayerAndroid.CONFIGID.SUBTITLE_LANGLIST);
        return SplitLanguageList(strSubtitLanguage);
    }

    @Override
    public int getCurrentSubtitlePos() {
        String strSubtitlePos = mAPlayerAndroid.getConfig(APlayerAndroid.CONFIGID.SUBTITLE_CURLANG);
        return (int) StringUtil.StringToLong(strSubtitlePos);
    }

    @Override
    public boolean setCurrentSubtitle(int pos) {
        String strPos = Integer.toString(pos);
        int setSubtitleRetCode = mAPlayerAndroid.setConfig(APlayerAndroid.CONFIGID.SUBTITLE_CURLANG, strPos);
        return BoolUtil.APlayerConfigValToBool(setSubtitleRetCode);
    }

    private static List<String> SplitLanguageList(String subtitLanguage) {
        List<String> subtitleLanguageList = null;
        do {
            //subtitLanguage.isEmpty()
            if(null == subtitLanguage || subtitLanguage.isEmpty())
                break;

            final String SPLIT_STR = ";";
            String[] subtitleLanguageArray = subtitLanguage.split(SPLIT_STR);
            if(null == subtitleLanguageArray)
                break;

            subtitleLanguageList = Arrays.asList(subtitleLanguageArray);
        }while (false);

        subtitleLanguageList = (null == subtitleLanguageList) ? new ArrayList<String>() : subtitleLanguageList;
        return subtitleLanguageList;
    }
}
