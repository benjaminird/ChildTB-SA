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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Locale;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
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
import android.telephony.SmsManager;
import android.text.InputType;
import android.text.format.DateFormat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

import com.ihsinformatics.tbr4mobile_pk.custom.MyButton;
import com.ihsinformatics.tbr4mobile_pk.custom.MyEditText;
import com.ihsinformatics.tbr4mobile_pk.custom.MySpinner;
import com.ihsinformatics.tbr4mobile_pk.custom.MyTextView;
import com.ihsinformatics.tbr4mobile_pk.shared.AlertType;
import com.ihsinformatics.tbr4mobile_pk.shared.FormType;
import com.ihsinformatics.tbr4mobile_pk.util.RegexUtil;

/**
 * @author owais.hussain@irdresearch.org
 * 
 */
public class LabResultActivity extends AbstractFragmentActivity {
	// Views displayed in pages, sorted w.r.t. appearance on pager
	
	MyTextView formDateTextView;
	MyButton formDateButton;

	MyTextView patientIdTextView;
	MyEditText patientId;
	
	MyTextView resultTypeTextView;
	MySpinner resultType;
	MyTextView resultOtherTextView;
	MyEditText resultOther;
	
	MyButton validatePatientID;
	MyButton scanBarcode;

	String patientID;
	
	MyButton btnDatePicker;
	DatePickerDialog fromDatePickerDialog;
	
	MyEditText txtDate;

	MyTextView Labelnext12;	
	MyButton submitButton;

    

	
	/**
	 * Subclass representing Fragment for LabResult form
	 * 
	 * @author owais.hussain@irdresearch.org
	 * 
	 */
	@SuppressLint("ValidFragment")
	class LabResultFragment extends Fragment {
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
	 * Subclass for Pager Adapter. Uses LabResultFragment subclass as items
	 * 
	 * @author owais.hussain@irdresearch.org
	 * 
	 */
	class LabResultFragmentPagerAdapter extends FragmentPagerAdapter {
		/** Constructor of the class */
		public LabResultFragmentPagerAdapter(FragmentManager fragmentManager) {
			super(fragmentManager);
		}

		/** This method will be invoked when a page is requested to create */
		@Override
		public Fragment getItem(int arg0) {
			LabResultFragment fragment = new LabResultFragment();
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
	public void createViews(final Context context) {
		FORM_NAME = FormType.LABRESULT;
		TAG = "LabResultActivity";
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
		LabResultFragmentPagerAdapter pagerAdapter = new LabResultFragmentPagerAdapter(
				fragmentManager);
		pager.setAdapter(pagerAdapter);
		pager.setOffscreenPageLimit(PAGE_COUNT);
		// Create views for pages
		formDateTextView = new MyTextView(context, R.style.text,
				R.string.date_result);
		formDateButton = new MyButton(context, R.style.button,
				R.drawable.custom_button_beige, R.string.date_result,
				R.string.form_date);
		
		btnDatePicker = new MyButton(context, R.style.button,
				R.drawable.custom_button_beige, R.string.form_date_collection,
				R.string.form_date);
		
		
		txtDate= new MyEditText(context, R.string.patient_id,
				R.string.patient_id_hint, InputType.TYPE_CLASS_TEXT,
				R.style.edit, RegexUtil.idLength, false);
		
		patientIdTextView = new MyTextView(context, R.style.text,
				R.string.patient_id);
		patientId = new MyEditText(context, R.string.patient_id,
				R.string.lab_id_hint, InputType.TYPE_CLASS_TEXT,
				R.style.edit, RegexUtil.idLength, false);
		
		resultTypeTextView = new MyTextView(context, R.style.text,
				R.string.lab_result);
		resultType = new MySpinner(context, getResources().getStringArray(
				R.array.lab_result_options), R.string.lab_result,
				R.string.option_hint);
				
		
		resultOtherTextView = new MyTextView(context, R.style.text,
				R.string.result_other);
		
		resultOther = new MyEditText(context, R.string.result_other,
				R.string.result_other_hint, InputType.TYPE_CLASS_TEXT,
				R.style.edit, RegexUtil.idLength, false);
		
		scanBarcode = new MyButton(context, R.style.button,
				R.drawable.custom_button_beige, R.string.scan_barcode,
				R.string.scan_barcode);
		Labelnext12= new MyTextView(context, R.style.text,
				R.string.label_submit);
		submitButton = new MyButton(context, R.style.button,
				R.drawable.custom_button_beige, R.string.form_submit,
				R.string.form_submit);
		
		View[][] viewGroups = { { formDateTextView, formDateButton, patientIdTextView,
			patientId, scanBarcode},
		{resultTypeTextView, resultType, resultOther ,Labelnext12, submitButton}
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
		formDateButton.setOnClickListener(this);
		firstButton.setOnClickListener(this);
		lastButton.setOnClickListener(this);
		clearButton.setOnClickListener(this);
		scanBarcode.setOnClickListener(this);
		saveButton.setOnClickListener(this);

		submitButton.setOnClickListener(this);
		navigationSeekbar.setOnSeekBarChangeListener(this);
		views = new View[] { patientId,resultType, resultOther};
		
		 btnDatePicker.setOnClickListener(this);
		// Set event listeners
		
		for (View v : views) {
			if (v instanceof Spinner) {
				((Spinner) v).setOnItemSelectedListener(this);
			} else if (v instanceof CheckBox) {
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
		resultOtherTextView.setVisibility(View.GONE);
		resultOther.setVisibility(View.GONE);
		
	}

	@Override
	public void updateDisplay() {
		// Not implemented
		formDateButton.setText(DateFormat.format("dd-MMM-yyyy", formDate));
	}

	@Override
	public boolean validate() {
		boolean valid = true;
		StringBuffer message = new StringBuffer();
		// Validate mandatory controls
		View[] mandatory = { resultType };
		for (View view : mandatory) {
			if (App.get(view).equals("-Please Select-")) {
				valid = false;
				message.append(view.getTag() + ". ");
			// Turn hint color Red
				
			} 
		}
		if (!valid) {
			message.append(getResources().getString(R.string.empty_data) + "\n");
		}
		// Validate data
		// Validate range
		if (!valid) {
			
			App.getAlertDialog(this, AlertType.ERROR, message.toString())
					.show();
		}
		
if (valid) {
			
			/*if (!RegexUtil.isWord(App.get(MethodSpecimen))) {
			
				valid = false;
				message.append(MethodSpecimen.getTag().toString() + ": "
						+ getResources().getString(R.string.invalid_data)
						+ "\n");
				MethodSpecimen.setTextColor(getResources().getColor(
						R.color.Red));
			}*/

		
			
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

	public boolean submit() {
		if (validate()) {
			
			final ContentValues values = new ContentValues();
		
			values.put("patientId", App.get(patientId));
			values.put("DateResult", App.getSqlDate(formDate));
			values.put("location", App.getLocation());
			
			values.put("LabResult", App.get(resultType));
			values.put("OtherResults", App.get(resultOther));
			
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
					// Send an SMS to support phone number
					String smsText = App.getUsername() + " "
							+ App.get(resultType) + " \"" + App.get(resultOther)
							+ "\"";
					SmsManager sms = SmsManager.getDefault();
					sms.sendTextMessage(App.getSupportContact(), null, smsText,
							null, null);
					String result = serverService.saveLabresult(FORM_NAME,
							values);
					return result;
				}

				@Override
				protected void onProgressUpdate(String... values) {
				};

				@Override
				protected void onPostExecute(String result) {
					super.onPostExecute(result);
					App.getAlertDialog(LabResultActivity.this, AlertType.INFO,
							getResources().getString(R.string.inserted)).show();
					loading.dismiss();
					if (result.equals("SUCCESS")) {
						App.getAlertDialog(LabResultActivity.this,
								AlertType.INFO,
								getResources().getString(R.string.inserted))
								.show();
						initView(views);
					} else {
						App.getAlertDialog(LabResultActivity.this,
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
	public void onClick(View view) {
		
	
		if (view == btnDatePicker) {
			
			final int mYear;
			int mMonth, mDay, mHour, mMinute;
            // Get Current Date
            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {
                        	
                        	//txtDate.setText(year-1993);
                        	txtDate.setText((mYear - year) + "");
                            //txtDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + (mYear - year));

                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
		}
		
		
		
		
		
		
		
		if (view == formDateButton) {
			showDialog(DATE_DIALOG_ID);
		}
		
		if (view == firstButton) {
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
		}else if (view == scanBarcode) {
			Intent intent = new Intent(Barcode.BARCODE_INTENT);
			intent.putExtra(Barcode.SCAN_MODE, Barcode.QR_MODE);
			startActivityForResult(intent, Barcode.BARCODE_RESULT);
		}
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
	public void onCheckedChanged(CompoundButton button, boolean state) {
		// Not implemented
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position,
			long id) {
		// Not implemented
		MySpinner spinner = (MySpinner) parent;
		boolean visible = spinner.getSelectedItemPosition() == 1;
		
		if (parent == resultType) {
			
			
			if (resultType.getSelectedItem().toString().equals("Other"))
			{
				resultOtherTextView.setVisibility(View.VISIBLE);
				resultOther.setVisibility(View.VISIBLE);
				
				updateDisplay();
			}
			
			else if (resultType.getSelectedItem().toString().equals("-Please Select-"))
			{
				resultOtherTextView.setVisibility(View.GONE);
				resultOther.setVisibility(View.GONE);
				
				
				updateDisplay();
			}
			
			}
	}

	@Override
	public boolean onLongClick(View v) {
		return false;
	}
}
