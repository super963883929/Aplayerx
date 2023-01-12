package com.tools.aplayer.model;


public class PopWndOptionsIteamParam
{
    private boolean mIsSelected;
    private boolean mCanSelected;
    private String mTitle;

    public PopWndOptionsIteamParam(boolean mIsSelected, boolean mCanSelected, String mTitle) {
        this.mIsSelected = mIsSelected;
        this.mCanSelected = mCanSelected;
        this.mTitle = mTitle;
    }

    public boolean isSelected() {
        return mIsSelected;
    }

    public void setIsSelected(boolean isSelected) {
        this.mIsSelected = isSelected;
    }

    public boolean isCanSelected() {
        return mCanSelected;
    }

    public void setCanSelected(boolean mCanSelected) {
        this.mCanSelected = mCanSelected;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String mTitle) {
        this.mTitle = mTitle;
    }
}
