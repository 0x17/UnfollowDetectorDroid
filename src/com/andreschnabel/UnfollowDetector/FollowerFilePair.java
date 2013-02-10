package com.andreschnabel.UnfollowDetector;

import java.io.File;

public class FollowerFilePair {
	public String screenName;
	public File f;

	public FollowerFilePair(String screenName, File f) {
		this.screenName = screenName;
		this.f = f;
	}
}
