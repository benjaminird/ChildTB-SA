/* Copyright(C) 2015 Interactive Health Solutions, Pvt. Ltd.

This program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License as
published by the Free Software Foundation; either version 3 of the License (GPLv3), or any later version.
This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.

See the GNU General Public License for more details. You should have received a copy of the GNU General Public License along with this program; if not, write to the Interactive Health Solutions, info@ihsinformatics.com
You can also access the license on the internet at the address: http://www.gnu.org/licenses/gpl-3.0.html

Interactive Health Solutions, hereby disclaims all copyright interest in this program written by the contributors.

Contributors: Tahira Niazi */
/**
 * Main menu Activity
 */

package com.ihsinformatics.tbr4mobile_pk;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.ihsinformatics.tbr4mobile_pk.model.OpenMrsObject;
import com.ihsinformatics.tbr4mobile_pk.shared.AlertType;
import com.ihsinformatics.tbr4mobile_pk.util.DatabaseUtil;
import com.ihsinformatics.tbr4mobile_pk.util.ServerService;

/**
 * @author owais.hussain@irdresearch.org
 * 
 */
public class MainMenuActivity extends Activity implements IActivity,
		OnClickListener, OnItemSelectedListener {
	private static final String TAG = "MainMenuActivity";
	private static final int LOCATIONS_DIALOG = 1;
	private static ServerService serverService;

	static ProgressDialog loading;
	TextView locationTextView;
	Button selectLocations;
	Button paediatricScreening;
	Button adultScreening;
	
	Button patientScreening;
	
	Button SpecimenCollection;
	Button TreatmentCompletion;
	Button paediatricContactTracing;
	Button adultReverseContactTracing;

	Button tbScreening;
	Button screening;
	Button nonPulmonarySuspect;
	Button DiagnosisButton;
	Button testIndication;
	Button bloodSugarTest;
	Button bloodSugarResults;
	Button clinicalEvaluation;
	Button drugDispersal;
	Button clinicalVisitBarriers;
	Button TreatmentInitiation;
	Button labresult;
	Button Diagnosis;
	
	Button patientGps;
	Button hctRecordCard;
	Animation alphaAnimation;

	OpenMrsObject[] locations;
	View[] views;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setTheme(App.getTheme());
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_menu);
		Configuration config = new Configuration();
		config.locale = App.getCurrentLocale();
		getApplicationContext().getResources()
				.updateConfiguration(config, null);
		serverService = new ServerService(getApplicationContext());
		loading = new ProgressDialog(this);
		 
// LOCATION SELECT 
// ADD SCREENER ID
		
		locationTextView = (TextView) findViewById(R.id.main_id_locationTextView);
		locationTextView.setTag("location");
		selectLocations = (Button) findViewById(R.id.main_id_selectLocationsButton);

		patientScreening= (Button) findViewById(R.id.main_id_patientscreeningButton);	
		SpecimenCollection = (Button) findViewById(R.id.main_id_Specimencollection);
		labresult= (Button) findViewById(R.id.main_Specimen_result);
		Diagnosis = (Button) findViewById(R.id.main_diagnostic_result);
		TreatmentInitiation = (Button) findViewById(R.id.main_id_TreatmentInitiation);
		TreatmentCompletion = (Button) findViewById(R.id.main_id_TreatmentCompletion);
		
		// patientGps = (Button) findViewById (R.main_id.patientGpsButton);
		alphaAnimation = AnimationUtils.loadAnimation(this,
				R.anim.alpha_animation);

		// Disable all forms that cannot be filled offline
		if (App.isOfflineMode()) {
			// DiagnosisButton.setEnabled (false);
			// testIndication.setEnabled (false);
			// bloodSugarTest.setEnabled (false);
			// bloodSugarResults.setEnabled (false);
			// clinicalEvaluation.setEnabled (false);
			// drugDispersal.setEnabled (false);
			// patientGps.setEnabled (false);
			// patientScreening.setEnabled(false);
		}
		views = new View[] { locationTextView, selectLocations,patientScreening,SpecimenCollection, 
				labresult,Diagnosis,
				TreatmentInitiation,
				TreatmentCompletion /*
										 * tbScreening , screening ,
										 * nonPulmonarySuspect ,
										 * DiagnosisButton , testIndication ,
										 * bloodSugarTest , bloodSugarResults ,
										 * clinicalEvaluation , drugDispersal ,
										 * // * patientGps , hctRecordCard ,
										 */};
		for (View v : views) {
			if (v instanceof Spinner) {
				((Spinner) v).setOnItemSelectedListener(this);
			} else if (v instanceof Button) {
				((Button) v).setOnClickListener(this);
			}
		}
		initView(views);
	}

	public void initView(View[] views) {
		if (App.getLocation() != null) {
			locationTextView.setText(App.getLocation());
		}
		// When online, check if there are offline forms for current user
		if (!App.isOfflineMode()) {
			int count = serverService.countSavedForms(App.getUsername());
			if (count > 0) {
				Toast.makeText(this, R.string.offline_forms, App.getDelay())
						.show();
				;
			}
		}
	}

	public void updateDisplay() {
	}

	public boolean validate() {
		boolean valid = true;
		StringBuffer message = new StringBuffer();
		// Validate mandatory controls
		if (locationTextView.getText().equals("")) {
			valid = false;
			message.append(locationTextView.getTag() + ":"
					+ getResources().getString(R.string.empty_selection));
		}
		if (!valid) {
			App.getAlertDialog(this, AlertType.ERROR, message.toString())
					.show();
		}
		return valid;
	}

	public boolean submit() {
		return false;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {

		case R.menu_id.searchPatientActivity:
			Intent patientSearchIntent = new Intent(this,
					PatientSearchActivity.class);
			startActivity(patientSearchIntent);
			break;
		// case R.menu_id.reportsActivity :
		// Intent reportsIntent = new Intent (this, ReportsActivity.class);
		// startActivity (reportsIntent);
		// break;
		case R.menu_id.formsActivity:
			Intent formsIntent = new Intent(this, SavedFormsActivity.class);
			startActivity(formsIntent);
			break;
		// case R.main_id.feedbackButton :
		// Intent feedbackIntent = new Intent (this, FeedbackActivity.class);
		// startActivity (feedbackIntent);
		// break;
		case R.menu_id.updateMetadataService:
			AlertDialog confirmationDialog = new AlertDialog.Builder(this)
					.create();
			confirmationDialog.setTitle("Update Primary data?");
			confirmationDialog.setMessage(getResources().getString(
					R.string.update_metadata));
			confirmationDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Yes",
					new AlertDialog.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							AsyncTask<String, String, String> updateTask = new AsyncTask<String, String, String>() {
								@Override
								protected String doInBackground(
										String... params) {
									try {
										if (!serverService
												.checkInternetConnection()) {
											AlertDialog alertDialog = App
													.getAlertDialog(
															MainMenuActivity.this,
															AlertType.ERROR,
															getResources()
																	.getString(
																			R.string.data_connection_error));
											alertDialog
													.setTitle(getResources()
															.getString(
																	R.string.error_title));
											alertDialog
													.setButton(
															AlertDialog.BUTTON_POSITIVE,
															"OK",
															new AlertDialog.OnClickListener() {
																@Override
																public void onClick(
																		DialogInterface dialog,
																		int which) {
																	finish();
																}
															});
											alertDialog.show();
										} else {
											// Operations on UI elements can be
											// performed only in UI threads.
											// Damn!
											// WHY?
											runOnUiThread(new Runnable() {
												@Override
												public void run() {
													loading.setIndeterminate(true);
													loading.setCancelable(false);
													loading.show();
												}
											});
											// Refresh database
											DatabaseUtil util = new DatabaseUtil(
													MainMenuActivity.this);
											publishProgress(getResources()
													.getString(
															R.string.loading_message));
											util.buildDatabase(true);
										}
									} catch (Exception e) {
										Log.e(TAG, e.getMessage());
									}
									return "SUCCESS";
								}

								@Override
								protected void onProgressUpdate(
										String... values) {
									loading.setMessage(values[0]);
								};

								@Override
								protected void onPostExecute(String result) {
									super.onPostExecute(result);
									if (result.equals("SUCCESS")) {
										loading.dismiss();
										App.getAlertDialog(
												MainMenuActivity.this,
												AlertType.INFO,
												"Phone data reset successfully.")
												.show();
										App.setLocation(null);
										locationTextView.setText("");
										initView(views);
									}
								}
							};
							updateTask.execute("");
						}
					});
			confirmationDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Cancel",
					new AlertDialog.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							// Do nothing
						}
					});
			confirmationDialog.show();
			break;
		}
		return true;
	}

	@Override
	protected Dialog onCreateDialog(int id) {
		Dialog dialog = super.onCreateDialog(id);
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		switch (id) {
		// Show a list of all locations to choose. This is to limit the
		// locations displayed on site spinner
		case LOCATIONS_DIALOG:
			builder.setTitle(getResources().getString(
					R.string.multi_select_hint));
			OpenMrsObject[] locationsList = serverService.getLocations();
			final ArrayList<CharSequence> locations = new ArrayList<CharSequence>();
			for (OpenMrsObject location : locationsList)
				locations.add(location.getName());
			final EditText locationText = new EditText(this);
			locationText.setTag("Location");
			locationText.setHint(R.string.location_hint);
			builder.setView(locationText);
			builder.setPositiveButton(R.string.save,
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialogInterface,
								int i) {
							final String selected = App.get(locationText);
							if (selected.equals("")) {
								Toast toast = Toast.makeText(
										MainMenuActivity.this, "",
										App.getDelay());
								toast.setText(R.string.empty_data);
								toast.setGravity(Gravity.CENTER, 0, 0);
								toast.show();
								return;
							}
							// Try to fetch from local DB or Server
							AsyncTask<String, String, String> updateTask = new AsyncTask<String, String, String>() {
								@Override
								protected String doInBackground(
										String... params) {
									try {
										if (!serverService
												.checkInternetConnection()) {
											AlertDialog alertDialog = App
													.getAlertDialog(
															MainMenuActivity.this,
															AlertType.ERROR,
															getResources()
																	.getString(
																			R.string.data_connection_error));
											alertDialog
													.setTitle(getResources()
															.getString(
																	R.string.error_title));
											alertDialog
													.setButton(
															AlertDialog.BUTTON_POSITIVE,
															"OK",
															new AlertDialog.OnClickListener() {
																@Override
																public void onClick(
																		DialogInterface dialog,
																		int which) {
																	finish();
																}
															});
											alertDialog.show();
										} else {
											runOnUiThread(new Runnable() {
												@Override
												public void run() {
													loading.setIndeterminate(true);
													loading.setCancelable(false);
													loading.show();
												}
											});
											// Update database
											publishProgress("Searching...");
											OpenMrsObject location = serverService
													.getLocation(selected);
											if (location != null) {
												App.setLocation(location
														.getName());
												// Save location in preferences
												SharedPreferences preferences = PreferenceManager
														.getDefaultSharedPreferences(MainMenuActivity.this);
												SharedPreferences.Editor editor = preferences
														.edit();
												editor.putString(
														Preferences.LOCATION,
														App.getLocation());
												editor.apply();
											} else {
												return "FAIL";
											}
										}
									} catch (Exception e) {
										Log.e(TAG, e.getMessage());
									}
									return "SUCCESS";
								}

								@Override
								protected void onProgressUpdate(
										String... values) {
									loading.setMessage(values[0]);
								};

								@Override
								protected void onPostExecute(String result) {
									super.onPostExecute(result);
									if (!result.equals("SUCCESS")) {
										App.getAlertDialog(
												MainMenuActivity.this,
												AlertType.ERROR,
												getResources()
														.getString(
																R.string.item_not_found))
												.show();
									}
									loading.dismiss();
									initView(views);
								}
							};
							updateTask.execute("");
						}
					});
			builder.setNegativeButton(R.string.cancel, null);
			dialog = builder.create();
			break;
		}
		return dialog;
	}

	/**
	 * Shows options to Exit and Log out
	 */
	@Override
	public void onBackPressed() {
		AlertDialog confirmationDialog = new AlertDialog.Builder(this).create();
		confirmationDialog.setTitle(getResources().getString(
				R.string.exit_application));
		confirmationDialog.setMessage(getResources().getString(
				R.string.exit_operation));
		confirmationDialog.setButton(AlertDialog.BUTTON_NEGATIVE,
				getResources().getString(R.string.exit),
				new AlertDialog.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						SharedPreferences preferences = PreferenceManager
								.getDefaultSharedPreferences(MainMenuActivity.this);
						SharedPreferences.Editor editor = preferences.edit();
						editor.putBoolean(Preferences.AUTO_LOGIN, true);
						editor.apply();
						finish();
					}
				});
		confirmationDialog.setButton(AlertDialog.BUTTON_POSITIVE,
				getResources().getString(R.string.logout),
				new AlertDialog.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						SharedPreferences preferences = PreferenceManager
								.getDefaultSharedPreferences(MainMenuActivity.this);
						SharedPreferences.Editor editor = preferences.edit();
						editor.putBoolean(Preferences.AUTO_LOGIN, false);
						editor.apply();
						finish();
					}
				});
		confirmationDialog.setButton(AlertDialog.BUTTON_NEUTRAL, getResources()
				.getString(R.string.cancel), new AlertDialog.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
			}
		});
		confirmationDialog.show();
	}

	@Override
	protected void onStop() {
		super.onStop();
		finish();
	}

	@Override
	public void onClick(View view) {
		// Return if trying to open any form without selecting location
	/*if (view.getId() != R.main_id.selectLocationsButton && !validate()) {
			return;
		}
		*/
		Toast toast = Toast.makeText(view.getContext(), "", App.getDelay());
		toast.setGravity(Gravity.CENTER, 0, 0);
		view.startAnimation(alphaAnimation);
		switch (view.getId()) {
		case R.main_id.selectLocationsButton:
			showDialog(LOCATIONS_DIALOG);
			break;
		
			
		case R.id.main_id_patientscreeningButton:
			Intent PatientScreeningActivityIntent = new Intent(this,
					PatientScreeningActivity.class);
			startActivity(PatientScreeningActivityIntent);
			break;
       
		case R.id.main_id_Specimencollection:
			Intent SpecimenCollectionIntent = new Intent(this,
					SpecimenCollectionActivity.class);
			startActivity(SpecimenCollectionIntent);
			break;
			
			
			case R.id.main_Specimen_result:
				Intent LabResultIntent = new Intent(this, LabResultActivity.class);
				startActivity(LabResultIntent);
				break;
				
			case R.id.main_diagnostic_result:
				
				Intent diagnosicIntent = new Intent(this, DiagnosisActivity.class);
				startActivity(diagnosicIntent);
				break;
				
				
			
		case R.id.main_id_TreatmentInitiation:
			Intent TreatmentInitiationIntent = new Intent(this,
					TreatmentInitiationActivity.class);
			startActivity(TreatmentInitiationIntent);
			break;
		  
		case R.id.main_id_TreatmentCompletion:
			Intent TreatmentCompletionIntent = new Intent(this,
			TreatmentCompletionActivity.class);
			startActivity(TreatmentCompletionIntent);
			break;
			          
	
	

		default:
			toast.setText(getResources().getString(R.string.form_unavailable));
			toast.show();
		}
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position,
			long id) {
		// Not implemented
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// Not implemented
	}
}
