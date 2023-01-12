package com.tools.aplayer.playInterface;

import java.util.List;


public interface IConfigAudio {
    List<String> getAudioTrack();
    int getCurrentAudioTrackPos();
    boolean setCurrentAudioTrack(int pos);  
}
