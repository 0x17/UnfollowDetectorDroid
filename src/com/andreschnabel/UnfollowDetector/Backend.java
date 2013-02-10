package com.andreschnabel.UnfollowDetector;

import java.io.*;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class Backend {

	private Backend() {}

	public static String fetchUrl(String url) throws Exception {
		URL u = new URL(url);
		InputStreamReader isr = new InputStreamReader(u.openStream());
		StringBuilder builder = new StringBuilder();
		int c;
		while((c = isr.read()) != -1) {
			builder.append((char)c);
		}
		isr.close();
		return builder.toString();
	}

	public static List<Integer> fetchFollowingIds(int userId) throws Exception {
		return fetchAndParseFollowers("https://api.twitter.com/1/following/ids.json?user_id=" + userId);
	}

	public static List<Integer> fetchFollowingIds(String screenName) throws Exception {
		return fetchAndParseFollowers("https://api.twitter.com/1/following/ids.json?screen_name=" + screenName);
	}

	public static List<Integer> fetchAndParseFollowers(String reqUrl) throws Exception {
		String jsonResp = fetchUrl(reqUrl);
		List<Integer> followers = new LinkedList<Integer>();
		Matcher matcher = Pattern.compile("\\d{6,10}").matcher(jsonResp);
		while(matcher.find()) {
			followers.add(Integer.valueOf(matcher.group(0)));
		}
		return followers;
	}

	public static String id2name(int userId) throws Exception {
		String reqUrl = "https://api.twitter.com/1/users/show.json?user_id=" + userId + "&include_entities=false";
		String resp = fetchUrl(reqUrl);
		Matcher m = Pattern.compile("\"screen_name\":\"(.*?)\"").matcher(resp);
		m.find();
		return m.group(1);
	}

	public static int name2id(String screenName) throws Exception {
		String reqUrl = "https://api.twitter.com/1/users/show.json?screen_name=" + screenName + "&include_entities=false";
		String resp = fetchUrl(reqUrl);
		Matcher m = Pattern.compile("\"id\":(\\d{6,10})").matcher(resp);
		m.find();
		return Integer.valueOf(m.group(1));
	}

	public static int saveIdsToFile(List<Integer> ids, File f) throws Exception {
		StringBuilder outStr = new StringBuilder();
		for(Integer id : ids) {
			outStr.append(id + "\n");
		}

		// remove last newline
		outStr.deleteCharAt(outStr.length() -1);

		FileWriter fw = new FileWriter(f);
		fw.write(outStr.toString());
		fw.close();

		return ids.size();
	}

	public static List<Integer> loadIdsFromFile(File f) throws Exception {
		List<Integer> ids = new LinkedList<Integer>();
		FileReader fr = new FileReader(f);
		BufferedReader br = new BufferedReader(fr);
		while(br.ready()) {
			ids.add(Integer.valueOf(br.readLine()));
		}
		br.close();
		fr.close();
		return ids;
	}

	public static int saveFollowersToFile(String screenName, File f) throws Exception {
		return saveIdsToFile(fetchFollowingIds(screenName), f);
	}

	public static List<Integer> determineUnfollowers(String screenName, File lastLst) throws Exception {
		List<Integer> prevFollowers = loadIdsFromFile(lastLst);
		List<Integer> curFollowers = fetchFollowingIds(screenName);
		List<Integer> unfollowers = new LinkedList<Integer>();
		for(Integer prevFollower : prevFollowers) {
			if(!curFollowers.contains(prevFollower))
				unfollowers.add(prevFollower);
		}
		return unfollowers;
	}
}
