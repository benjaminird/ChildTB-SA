/* Copyright(C) 2015 Interactive Health Solutions, Pvt. Ltd.

This program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License as
published by the Free Software Foundation; either version 3 of the License (GPLv3), or any later version.
This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.

See the GNU General Public License for more details. You should have received a copy of the GNU General Public License along with this program; if not, write to the Interactive Health Solutions, info@ihsinformatics.com
You can also access the license on the internet at the address: http://www.gnu.org/licenses/gpl-3.0.html

Interactive Health Solutions, hereby disclaims all copyright interest in this program written by the contributors.

Contributors: Tahira Niazi */
package com.ihsinformatics.tbr4mobile_pk;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Locale;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.InputType;
import android.text.format.DateFormat;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

import com.ihsinformatics.tbr4mobile_pk.custom.MyButton;
import com.ihsinformatics.tbr4mobile_pk.custom.MyEditText;
import com.ihsinformatics.tbr4mobile_pk.custom.MySpinner;
import com.ihsinformatics.tbr4mobile_pk.custom.MyTextView;
import com.ihsinformatics.tbr4mobile_pk.shared.AlertType;
import com.ihsinformatics.tbr4mobile_pk.shared.FormType;
import com.ihsinformatics.tbr4mobile_pk.util.RegexUtil;

public class SpecimenCollectionActivity extends AbstractFragmentActivity
		implements OnEditorActionListener, OnFocusChangeListener {

	MyTextView formDateTextView;
	MyButton formDateButton;

	MyTextView patientIdTextView;
	MyEditText patientId;

	
	MyTextView SpecimenNumberTextView;
	MySpinner SpecimenNumber;
	
	MyTextView MethodSpecimenTextView;
	MySpinner MethodSpecimen;

	MyTextView LabTestIdTextView;
	MyEditText LabTestId;

	MyTextView DiagnosticTestTextView;
	MySpinner DiagnosticTest;

	MyTextView PeriodSpecimenTextView;
	MySpinner PeriodSpecimen;




	MyButton validatePatientID;

	MyButton scanBarcode;

	String patientID;
	
	MyTextView Labelnext12;	
	MyButton submitButton;

	// patientID as a passed variable

	/**
	 * Subclass representing Fragment for adult-screeening suspect form
	 * 
	 * @author owais.hussain@ihsinformatics.com
	 * 
	 */
	@SuppressLint("ValidFragment")
	class SpecimenCollectionFragment extends Fragment {
		int currentPage;

		@Override
		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			Bundle data = getArguments();
			currentPage = data.getInt("current_page", 0);
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			// Return a layout of views from pre-filled ArrayList of groups
			if (currentPage != 0 && groups.size() != 0)
				return groups.get(currentPage - 1);
			else
				return null;
		}
	}

	/**
	 * Subclass for Pager Adapter. Uses SpecimenCollection subclass as items
	 * 
	 * @author owais.hussain@irdresearch.org
	 * 
	 */
	class SpecimenCollectionFragmentPagerAdapter extends
			FragmentPagerAdapter {
		/** Constructor of the class */
		public SpecimenCollectionFragmentPagerAdapter(
				FragmentManager fragmentManager) {
			super(fragmentManager);
		}

		/** This method will be invoked when a page is requested to create */
		@Override
		public Fragment getItem(int arg0) {
			SpecimenCollectionFragment fragment = new SpecimenCollectionFragment();
			Bundle data = new Bundle();
			data.putInt("current_page", arg0 + 1);
			fragment.setArguments(data);
			return fragment;
		}

		/** Returns the number of pages */
		@Override
		public int getCount() {
			return PAGE_COUNT;
		}
	}

	@Override
	public void createViews(Context context) {
		TAG = "PaediatricScreeningActivity";
		PAGE_COUNT = 4;
		pager = (ViewPager) findViewById(R.template_id.pager);
		navigationSeekbar.setMax(PAGE_COUNT - 1);
		navigatorLayout = (LinearLayout) findViewById(R.template_id.navigatorLayout);
		// If the form consists only of single page, then hide the
		// navigatorLayout
		if (PAGE_COUNT < 2) {
			navigatorLayout.setVisibility(View.GONE);
		}
		FragmentManager fragmentManager = getSupportFragmentManager();
		SpecimenCollectionFragmentPagerAdapter pagerAdapter = new SpecimenCollectionFragmentPagerAdapter(
				fragmentManager);
		pager.setAdapter(pagerAdapter);
		pager.setOffscreenPageLimit(PAGE_COUNT);
		// Create views for pages
		formDateTextView = new MyTextView(context, R.style.text,
				R.string.form_date_collection);
		formDateButton = new MyButton(context, R.style.button,
				R.drawable.custom_button_beige, R.string.form_date_collection,
				R.string.form_date);

		patientIdTextView = new MyTextView(context, R.style.text,
				R.string.patient_id);
		patientId = new MyEditText(context, R.string.patient_id,
				R.string.patient_id_hint, InputType.TYPE_CLASS_TEXT,
				R.style.edit, RegexUtil.idLength, false);

		// validatePatientID = new MyButton(context, R.style.button,
		// R.drawable.custom_button_beige, R.string.validateID,
		// R.string.validateID);
		
		SpecimenNumberTextView = new MyTextView(context, R.style.text,
				R.string.specimen_number);
		SpecimenNumber =  new MySpinner(context, getResources().getStringArray(
				R.array.specimen_number_options), R.string.specimen_number,
				R.string.option_hint);

		MethodSpecimenTextView = new MyTextView(context, R.style.text,
				R.string.method_specimen);
		MethodSpecimen =  new MySpinner(context, getResources().getStringArray(
				R.array.method_specimen_options), R.string.method_specimen,
				R.string.option_hint);
		
		LabTestIdTextView = new MyTextView(context, R.style.text,
				R.string.lab_test_id);
		LabTestId = new MyEditText(context,
				R.string.lab_test_id, R.string.lab_test_id_hint,
				InputType.TYPE_CLASS_TEXT, R.style.edit, 20, false);
		
		DiagnosticTestTextView = new MyTextView(context, R.style.text,
				R.string.diagnostic_test);
		DiagnosticTest = new MySpinner(context, getResources().getStringArray(
				R.array.diagnostic_test_options), R.string.diagnostic_test,
				R.string.option_hint);
		
		
		PeriodSpecimenTextView = new MyTextView(context, R.style.text,
				R.string.period_specimen);
		PeriodSpecimen = new MySpinner(context, getResources().getStringArray(
				R.array.period_specimen_options), R.string.period_specimen,
				R.string.option_hint);
		
		
		// houseNumberTextView = new MyTextView(context, R.style.text,
		// R.string.house_number);
		// houseNumber = new MyEditText(context, R.string.house_number,
		// R.string.house_number_hint, InputType.TYPE_CLASS_TEXT, R.style.edit,
		// 5, false);
		// streetNameTextView = new MyTextView(context, R.style.text,
		// R.string.street_name);
		// streetName = new MyEditText(context, R.string.street_name,
		// R.string.street_name_hint, InputType.TYPE_CLASS_TEXT, R.style.edit,
		// 15, false);
		// colonyNameTextView = new MyTextView(context, R.style.text,
		// R.string.colony_name);
		// colonyName = new MyEditText(context, R.string.colony_name,
		// R.string.colony_name_hint, InputType.TYPE_CLASS_TEXT, R.style.edit,
		// 15, false);
		// townTextView = new MyTextView(context, R.style.text, R.string.town);
		// town = new MyEditText(context, R.string.town, R.string.town_hint,
		// InputType.TYPE_CLASS_TEXT, R.style.edit, 15, false);
	
		scanBarcode = new MyButton(context, R.style.button,
				R.drawable.custom_button_beige, R.string.scan_barcode,
				R.string.scan_barcode);
		Labelnext12= new MyTextView(context, R.style.text,
				R.string.label_submit);
		submitButton = new MyButton(context, R.style.button,
				R.drawable.custom_button_beige, R.string.form_submit,
				R.string.form_submit);
		
		View[][] viewGroups = {
				{ formDateTextView, formDateButton, patientIdTextView,
						patientId, scanBarcode },
				{ SpecimenNumberTextView, SpecimenNumber,MethodSpecimenTextView, MethodSpecimen },
				{ LabTestIdTextView, LabTestId, DiagnosticTestTextView, DiagnosticTest },
				{ PeriodSpecimenTextView,
					PeriodSpecimen, Labelnext12, submitButton} };

		// Create layouts and store in ArrayList
		groups = new ArrayList<ViewGroup>();
		for (int i = 0; i < PAGE_COUNT; i++) {
			LinearLayout layout = new LinearLayout(context);
			layout.setOrientation(LinearLayout.VERTICAL);
			for (int j = 0; j < viewGroups[i].length; j++) {
				layout.addView(viewGroups[i][j]);
			}
			ScrollView scrollView = new ScrollView(context);
			scrollView.setLayoutParams(new LayoutParams(
					LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
			scrollView.addView(layout);
			groups.add(scrollView);
		}

		// Set event listeners
		// validatePatientID.setOnClickListener(this);
		formDateButton.setOnClickListener(this);
		firstButton.setOnClickListener(this);
		lastButton.setOnClickListener(this);
		clearButton.setOnClickListener(this);
		scanBarcode.setOnClickListener(this);
		saveButton.setOnClickListener(this);

		submitButton.setOnClickListener(this);
		navigationSeekbar.setOnSeekBarChangeListener(this);
		views = new View[] { patientId, SpecimenNumber,MethodSpecimen,
				LabTestId, DiagnosticTest, PeriodSpecimen };
		for (View v : views) {
			if (v instanceof Spinner) {
				((Spinner) v).setOnItemSelectedListener(this);
			} 
			else if (v instanceof CheckBox) {
				((CheckBox) v).setOnCheckedChangeListener(this);
			}
		}
		pager.setOnPageChangeListener(this);

		// Detect RTL language
		if (App.isLanguageRTL()) {
			Collections.reverse(groups);
			for (ViewGroup g : groups) {
				LinearLayout linearLayout = (LinearLayout) g.getChildAt(0);
				linearLayout.setGravity(Gravity.RIGHT);
			}
			for (View v : views) {
				if (v instanceof EditText) {
					((EditText) v).setGravity(Gravity.RIGHT);
				}
			}
		}
	}

	@Override
	public void initView(View[] views) {
		super.initView(views);
		formDate = Calendar.getInstance();
		updateDisplay();
		// address.setVisibility(View.GONE);

		MethodSpecimenTextView.setVisibility(View.GONE);
		MethodSpecimen.setVisibility(View.GONE);
		LabTestIdTextView.setVisibility(View.GONE);
		LabTestId.setVisibility(View.GONE);
		
		/*
 editText.setFocusable(false);
    editText.setVisibility(View.GONE);
    editText.setCursorVisible(false);
    editText.setKeyListener(null);
    editText.setBackgroundColor(Color.TRANSPARENT);
		 */
		
		DiagnosticTestTextView.setVisibility(View.GONE);
		DiagnosticTest.setVisibility(View.GONE);
		PeriodSpecimenTextView.setVisibility(View.GONE);
		PeriodSpecimen.setVisibility(View.GONE);
		
	}

	@Override
	public void onClick(View view) {
		if (view == formDateButton) {
			showDialog(DATE_DIALOG_ID);
		}

		// else if(view == validatePatientID)
		// {
		// patientId.setTextColor (getResources ().getColor
		// (R.color.Chocolate));
		// final String id = App.get(patientId);
		// if(validatePatientId())
		// {
		// if(!"".equals(id))
		// {
		// AsyncTask<String, String, Object> searchTask = new AsyncTask<String,
		// String, Object> ()
		// {
		//
		// @Override
		// protected Object doInBackground(String... params)
		// {
		// runOnUiThread(new Runnable()
		// {
		//
		// @Override
		// public void run()
		// {
		// loading.setIndeterminate(true);
		// loading.setCancelable(false);
		// loading.setMessage(getResources().getString(R.string.loading_message));
		// loading.show();
		// }
		// });
		// String[][] personDetail = serverService.getPersonDetail(id);
		// return personDetail;
		// }
		//
		// @Override
		// protected void onPostExecute(Object result)
		// {
		// super.onPostExecute(result);
		// loading.dismiss();
		//
		// try
		// {
		// String[][] personDetails = (String[][]) result;
		// // Display a message if no details were found
		// if(personDetails == null)
		// {
		// App.getAlertDialog(PatientRegistrationActivity.this, AlertType.INFO,
		// getResources().getString(R.string.patients_not_found)).show();
		// }
		// else
		// {
		// String message = "Patient ID is valid.";
		// App.getAlertDialog(PatientRegistrationActivity.this, AlertType.INFO,
		// message).show();
		// }
		// }
		// catch (Exception e)
		// {
		// Log.e (TAG, e.getMessage ());
		// App.getAlertDialog (PatientRegistrationActivity.this,
		// AlertType.ERROR, getResources ().getString
		// (R.string.parsing_error)).show ();
		// }
		// }
		// };
		// searchTask.execute("");
		// }
		// }
		// }

		else if (view == firstButton) {
			gotoFirstPage();
		} else if (view == lastButton) {
			gotoLastPage();
		} else if (view == clearButton) {
			initView(views);
		} else if (view == saveButton) {
			submit();
		}
			else if (view == submitButton) {
				submit();
		} else if (view == scanBarcode) {
			Intent intent = new Intent(Barcode.BARCODE_INTENT);
			intent.putExtra(Barcode.SCAN_MODE, Barcode.QR_MODE);
			startActivityForResult(intent, Barcode.BARCODE_RESULT);
		}
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position,
			long id) {
		MySpinner spinner = (MySpinner) parent;
		boolean visible = spinner.getSelectedItemPosition() == 1;
		
		if (parent == SpecimenNumber) {
			
			
			if ((SpecimenNumber.getSelectedItem().toString().equals("1"))
				|| (SpecimenNumber.getSelectedItem().toString().equals("2")))
			{
				MethodSpecimenTextView.setVisibility(View.VISIBLE);
				MethodSpecimen.setVisibility(View.VISIBLE);
				LabTestIdTextView.setVisibility(View.VISIBLE);
				LabTestId.setVisibility(View.VISIBLE);
				
				DiagnosticTestTextView.setVisibility(View.VISIBLE);
				DiagnosticTest.setVisibility(View.VISIBLE);
				PeriodSpecimenTextView.setVisibility(View.VISIBLE);
				PeriodSpecimen.setVisibility(View.VISIBLE);
				
				
				updateDisplay();
			}
			
			else if (SpecimenNumber.getSelectedItem().toString().equals("-Please Select-"))
			{
				MethodSpecimenTextView.setVisibility(View.GONE);
				MethodSpecimen.setVisibility(View.GONE);
				LabTestIdTextView.setVisibility(View.GONE);
				LabTestId.setVisibility(View.GONE);
				
				DiagnosticTestTextView.setVisibility(View.GONE);
				DiagnosticTest.setVisibility(View.GONE);
				PeriodSpecimenTextView.setVisibility(View.GONE);
				PeriodSpecimen.setVisibility(View.GONE);
				
				updateDisplay();
			}
			
			}
		
		
	}

	@Override
	public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean onLongClick(View arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void updateDisplay() {
		formDateButton.setText(DateFormat.format("dd-MMM-yyyy", formDate));
	}

	@Override
	public boolean validate() {
		boolean valid = true;
		StringBuffer message = new StringBuffer();
		// Validate mandatory controls
		View[] mandatory = { SpecimenNumber,MethodSpecimen, LabTestId};
		for (View v : mandatory) {
			if (App.get(v).equals("")) {
				valid = false;
				message.append(v.getTag().toString() + ". ");
				((EditText) v).setHintTextColor(getResources().getColor(
						R.color.Red));
			}
		}
		if (!valid) {
			message.append(getResources().getString(R.string.empty_data) + "\n");
		}
		// Validate data
		if (valid) {
			
			/*if (!RegexUtil.isWord(App.get(MethodSpecimen))) {
			
				valid = false;
				message.append(MethodSpecimen.getTag().toString() + ": "
						+ getResources().getString(R.string.invalid_data)
						+ "\n");
				MethodSpecimen.setTextColor(getResources().getColor(
						R.color.Red));
			}*/

			if (!RegexUtil.isWord(App.get(LabTestId))) {
				valid = false;
				message.append(LabTestId.getTag().toString() + ": "
						+ getResources().getString(R.string.invalid_data)
						+ "\n");
				LabTestId.setTextColor(getResources().getColor(
						R.color.Red));
			}
		
			
			if ("".equals(App.get(patientId))) {
				message.append(patientId.getTag().toString() + ". ");
				patientId
						.setHintTextColor(getResources().getColor(R.color.Red));
				message.append(getResources().getString(R.string.empty_data)
						+ "\n");
				valid = false;
			} else {
				if (RegexUtil.matchId(App.get(patientId))) {
					if (!RegexUtil.isValidId(App.get(patientId))) {
						valid = false;
						message.append(patientId.getTag().toString()
								+ ": "
								+ getResources().getString(
										R.string.invalid_data) + "\n");
						patientId.setTextColor(getResources().getColor(
								R.color.Red));
					}
				} else {
					valid = false;
					message.append(patientId.getTag().toString() + ": "
							+ getResources().getString(R.string.invalid_data)
							+ "\n");
					patientId
							.setTextColor(getResources().getColor(R.color.Red));
				}
			}

		}
		// Validate ranges
		try {
			// Form date range
			if (formDate.getTime().after(Calendar.getInstance().getTime())) {
				valid = false;
				message.append(formDateButton.getTag()
						+ ": "
						+ getResources().getString(
								R.string.invalid_date_or_time) + "\n");
			}

			// Age range
		} catch (NumberFormatException e) {
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
			final ContentValues values = new ContentValues();
			values.put("patientId", App.get(patientId));
			values.put("DateSpecimen", App.getSqlDate(formDate));
			values.put("location", App.getLocation());
			values.put("SpecimenNumber",App.get(SpecimenNumber));
			values.put("Method Specimen",App.get(MethodSpecimen));
			values.put("LabTestId", App.get(LabTestId));	
			values.put("DiagnosticTest", App.get(DiagnosticTest));
			
			values.put("PeriodSpecimenTaken", App.get(PeriodSpecimen));
			
			

			// final ArrayList<String[]> observations = new ArrayList<String[]>
			// ();

			AsyncTask<String, String, String> updateTask = new AsyncTask<String, String, String>() {
				@Override
				protected String doInBackground(String... params) {
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

					String result = "";
					result = serverService.savePatientRegistration(
							FormType.PATIENT_REGISTRATION, values);

					return result;
				}

				@Override
				protected void onProgressUpdate(String... values) {
				}

				@Override
				protected void onPostExecute(String result) {
					super.onPostExecute(result);
					loading.dismiss();
					if (result.equals("SUCCESS")) {
						App.getAlertDialog(SpecimenCollectionActivity.this,
								AlertType.INFO,
								getResources().getString(R.string.inserted))
								.show();
						initView(views);
					} else {
						App.getAlertDialog(SpecimenCollectionActivity.this,
								AlertType.ERROR, result).show();
						patientId.setTextColor(getResources().getColor(
								R.color.Red));
					}
				}
			};
			updateTask.execute("");
		}
		return true;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		// Retrieve barcode scan results
		if (requestCode == Barcode.BARCODE_RESULT) {
			if (resultCode == RESULT_OK) {
				String str = data.getStringExtra(Barcode.SCAN_RESULT);
				// Check for valid Id
				if (RegexUtil.isValidId(str)
						&& !RegexUtil.isNumeric(str, false)) {
					patientId.setText(str);
				} else {
					App.getAlertDialog(
							this,
							AlertType.ERROR,
							patientId.getTag().toString()
									+ ": "
									+ getResources().getString(
											R.string.invalid_data)).show();
				}
			} else if (resultCode == RESULT_CANCELED) {
				// Handle cancel
				App.getAlertDialog(this, AlertType.ERROR,
						getResources().getString(R.string.operation_cancelled))
						.show();
			}
			// Set the locale again, since the Barcode app restores system's
			// locale because of orientation
			Locale.setDefault(App.getCurrentLocale());
			Configuration config = new Configuration();
			config.locale = App.getCurrentLocale();
			getApplicationContext().getResources().updateConfiguration(config,
					null);
		}
	};

	@Override
	public boolean onEditorAction(TextView arg0, int arg1, KeyEvent arg2) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onFocusChange(View view, boolean hasFocus) {
		
		/*if (view == landmark) {
			if (!hasFocus) {
				if (!landmark.getText().toString().equals("")) {
					StringBuilder completeAddressBuilder = new StringBuilder();
					completeAddressBuilder.append(App.get(address) + ", ");
					completeAddressBuilder.append(App.get(city) + ", "
							+ App.get(landmark));
  
					// address.setVisibility(View.VISIBLE);
					// address.setText(completeAddressBuilder.toString());
					// address.setVisibility(View.GONE);

				}
			}
		}*/
		updateDisplay();
	}

	// public boolean validatePatientId()
	// {
	// StringBuffer message = new StringBuffer ();
	// boolean valid = true;
	// if("".equals(App.get(patientId)))
	// {
	// message.append(patientId.getTag().toString() + ". ");
	// patientId.setHintTextColor(getResources().getColor(R.color.Red));
	// message.append (getResources ().getString (R.string.empty_data) + "\n");
	// valid = false;
	// }
	// else
	// {
	// if(RegexUtil.matchId(App.get(patientId)))
	// {
	// if (!RegexUtil.isValidId (App.get (patientId)))
	// {
	// valid = false;
	// message.append (patientId.getTag ().toString () + ": " + getResources
	// ().getString (R.string.invalid_data) + "\n");
	// patientId.setTextColor (getResources ().getColor (R.color.Red));
	// }
	// }
	// else
	// {
	// valid = false;
	// message.append (patientId.getTag ().toString () + ": " + getResources
	// ().getString (R.string.invalid_data) + "\n");
	// patientId.setTextColor (getResources ().getColor (R.color.Red));
	// }
	// }
	// if(!valid)
	// {
	// App.getAlertDialog (this, AlertType.ERROR, message.toString ()).show ();
	// }
	// return valid;
	// }
}
