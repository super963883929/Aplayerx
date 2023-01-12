package com.tools.aplayer.model;

import java.util.ArrayList;
import java.util.List;


public class PopWndOptionsCategory {
    private String categoryTitle;
    private List<PopWndOptionsIteamParam> mIteamList;

    public PopWndOptionsCategory(){
        categoryTitle = "";
        mIteamList = new ArrayList<PopWndOptionsIteamParam>();
    }
    public PopWndOptionsCategory(String categoryTitle, List<PopWndOptionsIteamParam> mIteamList) {
        this.categoryTitle = categoryTitle;
        this.mIteamList = mIteamList;
    }

    public String getCategoryTitle() {
        return categoryTitle;
    }

    public void setCategoryTitle(String categoryTitle) {
        this.categoryTitle = categoryTitle;
    }

    public List<PopWndOptionsIteamParam> getmIteamList() {
        return mIteamList;
    }

    public void setmIteamList(List<PopWndOptionsIteamParam> mIteamList) {
        this.mIteamList = mIteamList;
    }

    public void setCategory(PopWndOptionsCategory popWndOptionsCategory)
    {
        if(null == popWndOptionsCategory)
        {
            return;
        }

        setCategoryTitle(popWndOptionsCategory.getCategoryTitle());
        setmIteamList(popWndOptionsCategory.getmIteamList());
    }

}
