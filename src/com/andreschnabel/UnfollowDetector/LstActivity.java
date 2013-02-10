package com.andreschnabel.UnfollowDetector;

import android.app.Activity;
import android.os.Bundle;
import com.andreschnabel.UnfollowDetector.tasks.FetchUnfollowersTask;

import java.io.File;

public class LstActivity extends Activity {
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.lview);

		String name = getIntent().getStringExtra("screenName");
		File f = new File(getFilesDir(), name + ".txt");
		FetchUnfollowersTask task = new FetchUnfollowersTask(this);
		task.execute(new FollowerFilePair(name, f));
	}
}