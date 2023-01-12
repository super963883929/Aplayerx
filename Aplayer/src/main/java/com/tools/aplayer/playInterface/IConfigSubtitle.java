package com.tools.aplayer.playInterface;

import java.util.List;


public interface IConfigSubtitle {
    boolean isUsable();
    boolean isShow();
    boolean show(boolean isShow);
    String getExternalSupportType(); //Get The Type of External Subtitlt,Which is Support
    String getExternalSubtitlePath();//Get External Subtitle Path
    boolean setExternalSubtitlePath(String externalSubtitlePath);//Get External Subtitle Path
    List<String> getSubtitleList();
    int getCurrentSubtitlePos(); //Get the current subtitle's  pos,order by 0
    boolean setCurrentSubtitle(int pos);
}
