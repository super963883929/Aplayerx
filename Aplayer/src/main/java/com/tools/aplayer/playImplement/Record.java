package com.tools.aplayer.playImplement;

import com.aplayer.APlayerAndroid;
import com.tools.aplayer.playInterface.IRecord;


/**
 * Created by LZ on 2016/9/24.
 */
public class Record implements IRecord
{
    private APlayerAndroid mAPlayerAndroid = null;

    public Record(APlayerAndroid aPlayerAndroid) {
        mAPlayerAndroid = aPlayerAndroid;
    }

    @Override
    public boolean isSupportRecord() {
        return mAPlayerAndroid.isSupportRecord();
    }

    @Override
    public boolean startRecord(String mediaOutPath) {
       // mAPlayerAndroid.setConfig(APlayerAndroid.CONFIGID.RECORD_MODE,"1");
        return mAPlayerAndroid.startRecord(mediaOutPath);
    }

    @Override
    public boolean isRecording() {
        return mAPlayerAndroid.isRecording();
    }

    @Override
    public void endRecord() {
    	mAPlayerAndroid.endRecord();
    }
}
