package com.tools.aplayer.playInterface;

/**
 * Created by LZ on 2016/9/24.
 */
public interface IRecord {
    boolean isSupportRecord();
    boolean startRecord(String mediaOutPath);
    boolean isRecording();
    void endRecord();
}
