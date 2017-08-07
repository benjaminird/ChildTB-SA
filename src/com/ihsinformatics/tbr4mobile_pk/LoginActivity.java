/* Copyright(C) 2015 Interactive Health Solutions, Pvt. Ltd.

This program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License as
published by the Free Software Foundation; either version 3 of the License (GPLv3), or any later version.
This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.

See the GNU General Public License for more details. You should have received a copy of the GNU General Public License along with this program; if not, write to the Interactive Health Solutions, info@ihsinformatics.com
You can also access the license on the internet at the address: http://www.gnu.org/licenses/gpl-3.0.html

Interactive Health Solutions, hereby disclaims all copyright interest in this program written by the contributors.

Contributors: Tahira Niazi */
/**
 * 
 */

package com.ihsinformatics.tbr4mobile_pk;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.ihsinformatics.tbr4mobile_pk.shared.AlertType;
import com.ihsinformatics.tbr4mobile_pk.util.ServerService;

/**
 * @author owais.hussain@irdresearch.org
 * 
 */
public class LoginActivity extends Activity implements IActivity,
		OnClickListener {
	private ServerService serverService;
	protected static ProgressDialog loading;
	EditText username;
	EditText password;
	Button login;
	CheckBox offline;
	
	View[] views;
	Animation alphaAnimation;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setTheme(App.getTheme());
		setContentView(R.layout.login);
		serverService = new ServerService(getApplicationContext());
		loading = new ProgressDialog(this);
		username = (EditText) findViewById(R.id.login_id_usernameEditText);
		password = (EditText) findViewById(R.id.login_id_passwordEditText);
		login = (Button) findViewById(R.id.login_id_loginButton);
		offline = (CheckBox) findViewById(R.id.login_id_offlineCheckBox);
		alphaAnimation = AnimationUtils.loadAnimation(this,
				R.anim.alpha_animation);
		login.setOnClickListener(this);
		views = new View[] { username, password, login };
		super.onCreate(savedInstanceState);
		initView(views);
	}

	@Override
	public void initView(View[] views) {
		if (App.isAutoLogin()) {
			serverService.setCurrentUser(App.get(username));
			Intent intent = new Intent(this, MainMenuActivity.class);
			startActivity(intent);
			finish();
		}
		username.setText(App.getUsername());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.menu_id.itemPreferences:
			startActivity(new Intent(this, Preferences.class));
			break;
		}
		return true;
	}

	@Override
	protected void onStop() {
		super.onStop();
		finish();
	}

	@Override
	public void updateDisplay() {
	}

	@Override
	public boolean validate() {
		boolean valid = true;
		StringBuffer message = new StringBuffer();
		// Validate mandatory controls
		if (App.get(username).equals("")) {
			valid = false;
			message.append(username.getTag() + ". ");
			((EditText) username).setHintTextColor(getResources().getColor(
					R.color.Red));
		}
		if (App.get(password).equals("")) {
			valid = false;
			message.append(password.getTag() + ". ");
			((EditText) password).setHintTextColor(getResources().getColor(
					R.color.Red));
		}
		if (!valid) {
			message.append(getResources().getString(R.string.empty_data) + "\n");
		}
		// For offline mode, match current username and password with saved
		
		if (App.isOfflineMode()) {
			if (!App.get(username).equals(App.getUsername())) {
				if (!App.get(password).equals(App.getPassword())) {
					valid = false;
					message.append(getResources().getString(
							R.string.authentication_error));
				}
			}
		}
		
		
		if (!valid) {
			App.getAlertDialog(this, AlertType.ERROR, message.toString())
					.show();
		}
		return valid;
	}

	@Override
	public boolean submit() {
		if (validate()) {
			// Authenticate from server
			AsyncTask<String, String, Boolean> authenticationTask = new AsyncTask<String, String, Boolean>() {
				@Override
				protected Boolean doInBackground(String... params) {
					runOnUiThread(new Runnable() {
						@Override
						public void run() {
							loading.setIndeterminate(true);
							loading.setCancelable(false);
							loading.setMessage(getResources().getString(
									R.string.loading_message));
							loading.show();
						}
					});
					if (App.isOfflineMode()) {
						if (App.getUsername().equalsIgnoreCase(
								App.get(username))
								&& App.getPassword().equals(App.get(password))) {
							return true;
						}
						return false;
					}
					App.setUsername(App.get(username));
					App.setPassword(App.get(password));
					boolean exists = serverService.authenticate();
					return exists;
				}

				@Override
				protected void onProgressUpdate(String... values) {
				};

				@Override
				protected void onPostExecute(Boolean result) {
					super.onPostExecute(result);
					loading.dismiss();
					if (result) {
						serverService.setCurrentUser(App.get(username));
						// Save username and password in preferences
						SharedPreferences preferences = PreferenceManager
								.getDefaultSharedPreferences(LoginActivity.this);
						SharedPreferences.Editor editor = preferences.edit();
						editor.putString(Preferences.USERNAME,
								App.getUsername());
						editor.putString(Preferences.PASSWORD,
								App.getPassword());
						editor.apply();
						Intent intent = new Intent(LoginActivity.this,
								MainMenuActivity.class);
						startActivity(intent);
						finish();
					} else {
						App.setUsername("");
						App.setPassword("");
						Toast toast = Toast.makeText(
								LoginActivity.this,
								getResources().getString(
										R.string.authentication_error),
								App.getDelay());
						toast.setGravity(Gravity.CENTER, 0, 0);
						toast.show();
					}
				}
			};
			authenticationTask.execute("");
		}
		return false;
	}

	@Override
	public void onClick(View view) {
		Intent browserIntent;
		view.startAnimation(alphaAnimation);
		switch (view.getId()) {
		case R.id.login_id_loginButton:
			// Check connection with server. Committed because won't work
			// for offline mode
			if (!offline.isChecked()
					&& !serverService.checkInternetConnection()) {
				AlertDialog alertDialog = App.getAlertDialog(this,
						AlertType.ERROR,
						getResources()
								.getString(R.string.data_connection_error));
				alertDialog.setTitle(getResources().getString(
						R.string.error_title));
				alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK",
						new AlertDialog.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								finish();
							}
						});
				alertDialog.show();
			} else {
				App.setOfflineMode(offline.isChecked());
				submit();
			}
			break;
		
		}
	}
}
