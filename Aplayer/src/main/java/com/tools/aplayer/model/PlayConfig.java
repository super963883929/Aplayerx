package com.tools.aplayer.model;

import com.aplayer.APlayerAndroid;
import com.tools.aplayer.playInterface.IConfigAudio;
import com.tools.aplayer.playInterface.IConfigSubtitle;
import com.tools.aplayer.playInterface.IConfigVideo;
import com.tools.aplayer.playInterface.IRecord;


public class PlayConfig {
    public static class PlayerListener {
        public APlayerAndroid.OnPlayCompleteListener playCompleteListener;
    }

    private IConfigVideo mIConfigVideo;
    private IConfigAudio mIConfigAudio;
    private IConfigSubtitle mISubtitle;
    private IRecord mIRecord;

    public PlayConfig(IConfigVideo configVideo, IConfigAudio configAudio, IConfigSubtitle configSubtitle, IRecord record) {
        this.mIConfigVideo = configVideo;
        this.mIConfigAudio = configAudio;
        this.mISubtitle = configSubtitle;
        this.mIRecord = record;
    }

    public IConfigVideo getConfigVideo() {
        return mIConfigVideo;
    }

    public IConfigAudio getConfigAudio() {
        return mIConfigAudio;
    }

    public IConfigSubtitle getSubtitle() {
        return mISubtitle;
    }

    public IRecord geRecord() {
        return mIRecord;
    }

}
