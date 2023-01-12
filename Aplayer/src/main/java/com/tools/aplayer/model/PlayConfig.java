package com.tools.aplayer.model;

import com.aplayer.APlayerAndroid;
import com.tools.aplayer.play_interface.IConfigAudio;
import com.tools.aplayer.play_interface.IConfigSubtitle;
import com.tools.aplayer.play_interface.IConfigVideo;
import com.tools.aplayer.play_interface.IRecord;

/**
 * Created by admin on 2016/7/14.
 */
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
