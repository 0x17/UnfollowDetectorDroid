package com.andreschnabel.UnfollowDetector.tasks;

import android.app.Activity;
import android.os.AsyncTask;
import com.andreschnabel.UnfollowDetector.Backend;
import com.andreschnabel.UnfollowDetector.FollowerFilePair;
import com.andreschnabel.UnfollowDetector.Utils;

public class SaveFollowersTask extends AsyncTask<FollowerFilePair, Void, Integer> {

	private final Activity activity;
	private String screenName;

	public SaveFollowersTask(Activity activity) {
		this.activity = activity;
	}

	@Override
	protected Integer doInBackground(FollowerFilePair... pairs) {
		int numSaved = 0;

		for(FollowerFilePair pair : pairs) {
			screenName = pair.screenName;
			try {
				numSaved += Backend.saveFollowersToFile(pair.screenName, pair.f);
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		return numSaved;
	}

	@Override
	protected void onPostExecute(Integer numSaved) {
		super.onPostExecute(numSaved);
		Utils.showMsg("Saved " + numSaved + " followers of " + screenName + "!", activity.getApplicationContext());
	}
}
