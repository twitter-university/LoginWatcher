package com.marakana.android.loginwatcher;

import android.app.Activity;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ToggleButton;

public class MainActivity extends Activity {
	private static final int ACTIVATION_REQUEST = 47;
	private static final String TAG = "LoginWatcher";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	/** Called when the toggle button is toggled. */
	public void onToggleClicked(View view) {
		// Is the toggle on?
		if (((ToggleButton) findViewById(R.id.toggleButton)).isChecked()) {
			// Activate device administrator
			Intent intent = new Intent(
					DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
			ComponentName deviceAdminComponentName = new ComponentName(this,
					LoginWatcherReceiver.class);
			intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN,
					deviceAdminComponentName);
			intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION,
					"We want to watch logins on this device.");
			startActivityForResult(intent, ACTIVATION_REQUEST);
			Log.d(TAG,
					"LoginWatcher requested to be activated as Device Administrator");
		} else {
			// Deactivate device administrator

			Log.d(TAG,
					"LoginWatcher requested to be deactivated as Device Administrator");
		}
	}

	/**
	 * Called when we come back to this activity from the system dialog to
	 * enable devices administration.
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		case ACTIVATION_REQUEST:
			if (resultCode == Activity.RESULT_OK) {
				Log.i(TAG, "Administration enabled!");
			} else {
				Log.i(TAG, "Administration enable FAILED!");
			}
			return;
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

}
