package com.andreschnabel.UnfollowDetector.tasks;

import android.os.AsyncTask;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import com.andreschnabel.UnfollowDetector.*;

import java.util.LinkedList;
import java.util.List;

public class FetchUnfollowersTask extends AsyncTask<FollowerFilePair, Void, List<String>> {

	private final LstActivity lactivity;
	private String screenName;

	public FetchUnfollowersTask(LstActivity lactivity) {
		super();
		this.lactivity = lactivity;
	}

	@Override
	protected List<String> doInBackground(FollowerFilePair... pairs) {
		List<String> result = new LinkedList<String>();
		for(FollowerFilePair pair : pairs) {
			screenName = pair.screenName;

			try {
				if(!pair.f.exists()) {
					Backend.saveFollowersToFile(pair.screenName, pair.f);
				}

				for(Integer id : Backend.determineUnfollowers(pair.screenName, pair.f)) {
					result.add(Backend.id2name(id));
				}

			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	@Override
	protected void onPostExecute(List<String> unfollowerNames) {
		super.onPostExecute(unfollowerNames);

		ListView lview = (ListView)lactivity.findViewById(R.id.listView);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(lactivity.getApplicationContext(), android.R.layout.simple_list_item_1, android.R.id.text1, unfollowerNames);
		lview.setAdapter(adapter);

		int numUnfollowers = unfollowerNames.size();

		Utils.showMsg("Found " + numUnfollowers + " Unfollowers!", lactivity.getApplicationContext());

		TextView tview = (TextView) lactivity.findViewById(R.id.topText);
		tview.setText((numUnfollowers > 0 ? numUnfollowers : "Nobody") + " unfollowed " + screenName);
	}
}
