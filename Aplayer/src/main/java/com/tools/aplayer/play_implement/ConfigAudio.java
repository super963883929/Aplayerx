package com.tools.aplayer.play_implement;

import com.aplayer.APlayerAndroid;
import com.tools.aplayer.play_interface.IConfigAudio;
import com.tools.aplayer.utils.BoolUtil;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by admin on 2016/7/14.
 */
public class ConfigAudio implements IConfigAudio {
    private APlayerAndroid mAPlayerAndroid = null;

    public ConfigAudio(APlayerAndroid aPlayerAndroid) {
        mAPlayerAndroid = aPlayerAndroid;
    }

    @Override
    public List<String> getAudioTrack() {
        String strAudioList = mAPlayerAndroid.getConfig(APlayerAndroid.CONFIGID.AUDIO_TRACK_LIST);
        List<String> audioList = null;
        if(null != strAudioList)
        {   final String SPLIT_STR = ";";
            String[] audioTrackArray = strAudioList.split(SPLIT_STR);
            if(null != audioTrackArray){
                audioList = Arrays.asList(audioTrackArray);
            }
        }

        audioList = (null == audioList) ? new ArrayList<String>() : audioList;
        return audioList;
    }

    @Override
    public int getCurrentAudioTrackPos() {
        String strPos = mAPlayerAndroid.getConfig(APlayerAndroid.CONFIGID.AUDIO_TRACK_CURRENT);
        int pos = Integer.parseInt(strPos);
        return pos;
    }

    @Override
    public boolean setCurrentAudioTrack(int pos) {
        String strPos = String.format("%d", pos);
        int setReturnCode = mAPlayerAndroid.setConfig(APlayerAndroid.CONFIGID.AUDIO_TRACK_CURRENT, strPos);
        return BoolUtil.APlayerConfigValToBool(setReturnCode);
    }
}
