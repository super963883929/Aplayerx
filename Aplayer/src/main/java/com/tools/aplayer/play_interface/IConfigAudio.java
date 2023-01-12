package com.tools.aplayer.play_interface;

import java.util.List;

/**
 * Created by admin on 2016/7/14.
 */
public interface IConfigAudio {
    List<String> getAudioTrack();
    int getCurrentAudioTrackPos();
    boolean setCurrentAudioTrack(int pos);
}
