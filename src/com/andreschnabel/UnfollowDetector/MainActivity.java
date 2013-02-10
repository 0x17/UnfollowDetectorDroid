package com.andreschnabel.UnfollowDetector;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import com.andreschnabel.UnfollowDetector.tasks.SaveFollowersTask;

import java.io.File;

public class MainActivity extends Activity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
	}

	public void saveFollowersClick(View v) {
		Utils.showMsg("Save followers!", getApplicationContext());
		EditText et = (EditText)findViewById(R.id.snameEditTxt);
		String name = et.getText().toString();
		if(!Utils.validScreenName(name)) {
			Utils.showMsg("Screen name invalid!", getApplicationContext());
			return;
		}
		File f = new File(getFilesDir(), name + ".txt");
		new SaveFollowersTask(this).execute(new FollowerFilePair(name, f));
	}

	public void showUnfollowersClick(View v) {
		Utils.showMsg("Show unfollowers!", getApplicationContext());
		Intent intent = new Intent(this, LstActivity.class);
		EditText et = (EditText)findViewById(R.id.snameEditTxt);
		String name = et.getText().toString();
		if(!Utils.validScreenName(name)) {
			Utils.showMsg("Screen name invalid!", getApplicationContext());
			return;
		}
		intent.putExtra("screenName", name);
		startActivity(intent);
	}



}
