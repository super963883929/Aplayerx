package com.tools.aplayer.utils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class VideoFileHelper {
	public static Set audioSet = new HashSet();
	public static Set videoSet = new HashSet();
	public static Set subtitleSet = new HashSet();

	static{
		init();
	}
	private static void init()
	{
		initVideoType();
		initAudioType();
		initSubtitleType();
	}

	private static void initVideoType()
	{
		ArrayList<String> stringArrayList = new ArrayList<String>();

		// Windows 媒体
		stringArrayList.add("asf");
		stringArrayList.add("avi");
		stringArrayList.add("wm");
		stringArrayList.add("wmp");
		stringArrayList.add("wmv");

		// Real 媒体
		stringArrayList.add("ram");
		stringArrayList.add("rm");
		stringArrayList.add("rmvb");
		stringArrayList.add("rp");
		stringArrayList.add("rpm");
		stringArrayList.add("rt");
		stringArrayList.add("smil");
		stringArrayList.add("scm");

		// MPEG1/2 媒体
		stringArrayList.add("dat");
		stringArrayList.add("m1v");
		stringArrayList.add("m2v");
		stringArrayList.add("m2p");
		stringArrayList.add("m2ts");
		stringArrayList.add("mp2v");
		stringArrayList.add("mpe");
		stringArrayList.add("mpeg");
		stringArrayList.add("mpeg1");
		stringArrayList.add("mpeg2");
		stringArrayList.add("mpv2");
		stringArrayList.add("tp");
		stringArrayList.add("pva");
		stringArrayList.add("tpr");
		stringArrayList.add("mpg");
		stringArrayList.add("pss");
		stringArrayList.add("tpr");
		stringArrayList.add("ts");

		// MPEG4 媒体
		stringArrayList.add("m4b");
		stringArrayList.add("m4r");
		stringArrayList.add("m4p");
		stringArrayList.add("m4v");
		stringArrayList.add("mp4");

		// 3GPP 媒体
		stringArrayList.add("3g2");
		stringArrayList.add("3gp");
		stringArrayList.add("3gp2");
		stringArrayList.add("3gpp");

		// APPLE 媒体
		stringArrayList.add("mov");
		stringArrayList.add("qt");

		// Flash 媒体
		stringArrayList.add("flv");
		stringArrayList.add("f4v");
		stringArrayList.add("swf");
		stringArrayList.add("hlv");

		// CD/DVD 媒体
		stringArrayList.add("ifo");
		stringArrayList.add("vob");

		// 其他视频
		stringArrayList.add("amv");
		stringArrayList.add("csf");
		stringArrayList.add("divx");
		stringArrayList.add("evo");
		stringArrayList.add("mkv");
		stringArrayList.add("mod");
		stringArrayList.add("pmp");
		stringArrayList.add("vp6");
		stringArrayList.add("bik");
		stringArrayList.add("mts");
		stringArrayList.add("xvx");
		stringArrayList.add("xv");
		stringArrayList.add("xlmv");
		stringArrayList.add("ogm");
		stringArrayList.add("ogv");
		stringArrayList.add("ogx");

		// 多媒体光碟
		stringArrayList.add("dvd");

		for (String strType : stringArrayList){
			videoSet.add(strType.toLowerCase());
		}
	}

	// 音频文件
	private static void initAudioType()
	{
		ArrayList<String> stringArrayList = new ArrayList<String>();
		stringArrayList.add("aac");
		stringArrayList.add("ac3");
		stringArrayList.add("acc");
		stringArrayList.add("aiff");
		stringArrayList.add("amr");
		stringArrayList.add("ape");
		stringArrayList.add("au");
		stringArrayList.add("cda");
		stringArrayList.add("aac");
		stringArrayList.add("ac3");
		stringArrayList.add("acc");
		stringArrayList.add("aiff");
		stringArrayList.add("amr");
		stringArrayList.add("ape");
		stringArrayList.add("au");
		stringArrayList.add("cda");
		stringArrayList.add("dts");
		stringArrayList.add("flac");
		stringArrayList.add("m1a");
		stringArrayList.add("m2a");
		stringArrayList.add("m4a");
		stringArrayList.add("mka");
		stringArrayList.add("mp2");
		stringArrayList.add("mp3");
		stringArrayList.add("mpa");
		stringArrayList.add("mpc");
		stringArrayList.add("ra");
		stringArrayList.add("tta");
		stringArrayList.add("wav");
		stringArrayList.add("wma");
		stringArrayList.add("wv");
		stringArrayList.add("mid");
		stringArrayList.add("midi");
		stringArrayList.add("ogg");
		stringArrayList.add("oga");

		for (String strType : stringArrayList){
			audioSet.add(strType.toLowerCase());
		}
	}

	private static void initSubtitleType()
	{
		ArrayList<String> stringArrayList = new ArrayList<String>();

		stringArrayList.add("srt");
		stringArrayList.add("ass");
		stringArrayList.add("ssa");
		stringArrayList.add("smi");
		stringArrayList.add("idx");
		stringArrayList.add("sub");
		stringArrayList.add("psb");
		stringArrayList.add("usf");
		stringArrayList.add("ssf");

		for (String strType : stringArrayList){
			subtitleSet.add(strType.toLowerCase());
		}
	}

	public static boolean isSupportedVideoFileExtension(String ext) {
		String  extLower = ext.toLowerCase();
		return videoSet.contains(extLower);
	}
	
	public static boolean isSupportedVideoFileExtensionScanMode(String ext) {
		if (ext.toLowerCase().equals("dat")) {
			return false;
		}
		return isSupportedVideoFileExtension(ext);
	}

	public static boolean isSupportedAudioFileExtension(String ext) {
		String  extLower = ext.toLowerCase();
		return audioSet.contains(extLower);
	}

	public static boolean isSupportedSubtitleFileExtension(String ext) {
		String extLower = ext.toLowerCase();
		return subtitleSet.contains(extLower);
		
	}
}
