package com.tools.aplayer.model;


import com.aplayer.APlayerAndroid;

import java.util.Comparator;

public class FileItemInfo {
	public String name;
	public String path;
	public boolean isDirectory;
	public APlayerAndroid.MediaInfo mediaInfo = null;

	public static class SortComparator implements Comparator {
		@Override
		public int compare(Object o, Object t1) {
			FileItemInfo fileItemInfo0 = (FileItemInfo) o;
			FileItemInfo fileItemInfo1 = (FileItemInfo) t1;

			return fileItemInfo0.name.compareTo(fileItemInfo1.name);
		}
	}
}
