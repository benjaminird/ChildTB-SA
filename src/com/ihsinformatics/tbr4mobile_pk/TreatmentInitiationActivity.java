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
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnLongClickListener;
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

public class TreatmentInitiationActivity extends AbstractFragmentActivity implements
		OnEditorActionListener {
	// Views displayed in pages, sorted w.r.t. appearance on pager
	MyTextView formDateTextView;
	MyButton formDateButton;
	
	MyTextView typeTreatmentTextView;
	MySpinner typeTreatment;
	
	MyTextView patientIdTextView;
	MyEditText patientId;

	MyTextView locationTreatmentTextView;
	MyEditText locationTreatment;

	
	
	MyButton scanBarcode;
	MyTextView Labelnext12;	
	MyButton submitButton;


	/**
	 * Subclass representing Fragment for Pediatric-screeening suspect form
	 * 
	 * @author owais.hussain@ihsinformatics.com
	 * 
	 */
	@SuppressLint("ValidFragment")
	class TreatmentInitiationFragment extends Fragment {
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
	 * Subclass for Pager Adapter. Uses PediatricScreeningSuspect subclass as
	 * items
	 * 
	 * @author owais.hussain@irdresearch.org
	 * 
	 */
	class TreatmentInitiationFragmentPagerAdapter extends
			FragmentPagerAdapter {
		/** Constructor of the class */
		public TreatmentInitiationFragmentPagerAdapter(
				FragmentManager fragmentManager) {
			super(fragmentManager);
		}

		/** This method will be invoked when a page is requested to create */
		@Override
		public Fragment getItem(int arg0) {
			TreatmentInitiationFragment fragment = new TreatmentInitiationFragment();
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
		TAG = "TreatmentInitiationActivity";
		PAGE_COUNT = 2;
		pager = (ViewPager) findViewById(R.template_id.pager);
		navigationSeekbar.setMax(PAGE_COUNT - 1);
		navigatorLayout = (LinearLayout) findViewById(R.template_id.navigatorLayout);
		// If the form consists only of single page, then hide the
		// navigatorLayout
		if (PAGE_COUNT < 2) {
			navigatorLayout.setVisibility(View.GONE);
		}
		FragmentManager fragmentManager = getSupportFragmentManager();
		TreatmentInitiationFragmentPagerAdapter pagerAdapter = new TreatmentInitiationFragmentPagerAdapter(
				fragmentManager);
		pager.setAdapter(pagerAdapter);
		pager.setOffscreenPageLimit(PAGE_COUNT);
		// Create views for pages
		formDateTextView = new MyTextView(context, R.style.text,
				R.string.date_initiation);
		formDateButton = new MyButton(context, R.style.button,
				R.drawable.custom_button_beige, R.string.form_date,
				R.string.form_date);
	
		

		typeTreatmentTextView = new MyTextView(context, R.style.text,
				R.string.type_treatment);
		typeTreatment = new MySpinner(context, getResources()
				.getStringArray(R.array.type_treatment_option),
				R.string.type_treatment, R.string.option_hint);


		patientIdTextView = new MyTextView(context, R.style.text,
				R.string.patient_id);
		patientId = new MyEditText(context, R.string.patient_id,
				R.string.patient_id_hint, InputType.TYPE_CLASS_TEXT,
				R.style.edit, 12, false);

		locationTreatmentTextView = new MyTextView(context, R.style.text,
				R.string.loc_treatment);
		locationTreatment = new MyEditText(context, R.string.loc_treatment,
				R.string.loc_treatment_hint,
				InputType.TYPE_TEXT_VARIATION_PERSON_NAME, R.style.edit, 20,
				false);

		

		

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
					patientId, scanBarcode			
						 },
				{ typeTreatmentTextView,
							typeTreatment ,locationTreatmentTextView, locationTreatment
							,Labelnext12, submitButton},
		};

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
		formDateButton.setOnClickListener(this);
		firstButton.setOnClickListener(this);
		lastButton.setOnClickListener(this);
		clearButton.setOnClickListener(this);
		saveButton.setOnClickListener(this);
		scanBarcode.setOnClickListener(this);

		submitButton.setOnClickListener(this);
		navigationSeekbar.setOnSeekBarChangeListener(this);
		
		views = new View[] { typeTreatment,
				patientId, locationTreatment };

		for (View v : views) {
			if (v instanceof Spinner) {
				((Spinner) v).setOnItemSelectedListener(this);
			} else if (v instanceof CheckBox) {
				((CheckBox) v).setOnCheckedChangeListener(this);
			}
		}
		pager.setOnPageChangeListener(this);

		patientId.setOnLongClickListener(new OnLongClickListener() {
			public boolean onLongClick(View view) {
				// Intent intent = new Intent (context,
				// PatientSearchActivity.class);
				// startActivity (intent);
				return false;
			}
		});
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

		locationTreatmentTextView.setVisibility(View.GONE);
		locationTreatment.setVisibility(View.GONE);

		// totalNumberMembersWithSymptomsTextView.setEnabled(false);
		// totalNumberMembersWithSymptoms.setEnabled(false);
		// namesSymptomaticMembersTextView.setEnabled(false);
		// namesSymptomaticMembers.setEnabled(false);
		updateDisplay();
	}

	@Override
	public void onClick(View view) {
		if (view == formDateButton) {
			showDialog(DATE_DIALOG_ID);
		} 
		
		
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
		
if (parent == typeTreatment) {
			
			
			if ((typeTreatment.getSelectedItem().toString().equals("DSTB"))
				|| (typeTreatment.getSelectedItem().toString().equals("DRTB"))
				|| (typeTreatment.getSelectedItem().toString().equals("IPT"))
				)
			{
				locationTreatmentTextView.setVisibility(View.VISIBLE);
				locationTreatment.setVisibility(View.VISIBLE);
				
				
				
				updateDisplay();
			}
			
			else if (typeTreatment.getSelectedItem().toString().equals("-Please Select-"))
			{
				locationTreatmentTextView.setVisibility(View.GONE);
				locationTreatment.setVisibility(View.GONE);
						
				
				
				updateDisplay();
			}
			
			}
		updateDisplay();
	}

	@Override
	public void onCheckedChanged(CompoundButton arg0, boolean arg1) {

	}

	@Override
	public boolean onLongClick(View arg0) {
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
		View[] mandatory = { };
		for (View v : mandatory) {
			if (App.get(v).equals("")) {
				valid = false;
				message.append(v.getTag().toString() + ". ");
				((EditText) v).setHintTextColor(getResources().getColor(
						R.color.Red));
			}
		}
		// if(!"".equals(App.get(totalNumberMembers)))
		// {
		// if (Integer.parseInt(App.get(totalNumberMembers)) > 0
		// && App.get(totalNumberMembersWithSymptoms).equals(""))
		// {
		// valid = false;
		// message.append(totalNumberMembersWithSymptoms.getTag().toString() +
		// ". ");
		// totalNumberMembersWithSymptoms.setHintTextColor(getResources().getColor(R.color.Red));
		// }
		// }

		
		if (!valid) {
			message.append(getResources().getString(R.string.empty_data) + "\n");
		}

		// // Validate data
		if (valid) {
			if (RegexUtil.matchId(App.get(patientId))) {
				if (!RegexUtil.isValidId(App.get(patientId))) {
					valid = false;
					message.append(patientId.getTag().toString() + ": "
							+ getResources().getString(R.string.invalid_data)
							+ "\n");
					patientId.setTextColor(getResources().getColor(
							R.color.Red));
				}
			} else {
				valid = false;
				message.append(patientId.getTag().toString() + ": "
						+ getResources().getString(R.string.invalid_data)
						+ "\n");
				patientId.setTextColor(getResources().getColor(R.color.Red));
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
			final ContentValues values = new ContentValues();
			values.put("formDate", App.getSqlDate(formDate));
			values.put("location", App.getLocation());
			// values.put ("age", ageInYears);
			values.put("patientId", App.get(patientId));
			final ArrayList<String[]> observations = new ArrayList<String[]>();

			// if(App.get(typeTreatment).equals(getResources().getString(R.string.tbr4pk_patient)))
			// {
			//
			// }
			observations.add(new String[] { "Treatment Location", App.get(locationTreatment) });

			

			// if (Integer.parseInt(App.get(totalNumberMembersWithSymptoms)) >
			// 0)
			// {
			// observations.add(new String[] {
			// "Symptomatic Household Members Names",
			// App.get(namesSymptomaticMembers) });
			// }

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
					result = serverService.saveReverseContact(
							FormType.REVERSE_CONTACT_TRACING, values,
							observations.toArray(new String[][] {}));
					return result;
				}

				@Override
				protected void onProgressUpdate(String... values) {
				};

				@Override
				protected void onPostExecute(String result) {
					super.onPostExecute(result);
					loading.dismiss();
					if (result.equals("SUCCESS")) {
						App.getAlertDialog(TreatmentInitiationActivity.this,
								AlertType.INFO,
								getResources().getString(R.string.inserted))
								.show();
						initView(views);
					} else {
						App.getAlertDialog(TreatmentInitiationActivity.this,
								AlertType.ERROR, result).show();
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
	public boolean onEditorAction(TextView v, int arg1, KeyEvent arg2) {
		return false;
	}
}
