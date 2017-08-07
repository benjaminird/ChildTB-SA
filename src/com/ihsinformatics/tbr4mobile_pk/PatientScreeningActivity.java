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
import java.util.Date;
import java.util.Locale;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.AlertDialog.Builder;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
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
import android.text.format.DateFormat;
import android.text.method.KeyListener;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnLongClickListener;
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
import android.widget.TextView.OnEditorActionListener;

import com.ihsinformatics.tbr4mobile_pk.custom.MyButton;
import com.ihsinformatics.tbr4mobile_pk.custom.MyCheckBox;
import com.ihsinformatics.tbr4mobile_pk.custom.MyEditText;
import com.ihsinformatics.tbr4mobile_pk.custom.MyRadioButton;
import com.ihsinformatics.tbr4mobile_pk.custom.MyRadioGroup;
import com.ihsinformatics.tbr4mobile_pk.custom.MySpinner;
import com.ihsinformatics.tbr4mobile_pk.custom.MyTextView;
import com.ihsinformatics.tbr4mobile_pk.shared.AlertType;
import com.ihsinformatics.tbr4mobile_pk.shared.FormType;
import com.ihsinformatics.tbr4mobile_pk.util.RegexUtil;

public class PatientScreeningActivity extends AbstractFragmentActivity
		implements KeyListener, OnEditorActionListener {
	// Views displayed in pages, sorted w.r.t. appearance on pager
	
// IMPORT  FORM PATIENT SCE
	
	MyTextView formDateTextView;
	MyButton formDateButton;
	
	MyTextView Label_demogr;
	MyTextView Labelsymp_adult;
	MyTextView Labelsymp_child;
	MyTextView Labelrisk_factor;
	MyTextView Labelhiv;
	
	MyTextView Labelcontact;
	MyTextView Labeladdress;
	MyTextView firstNameTextView;
	MyEditText firstName;
	
	MyTextView lastNameTextView;
	MyEditText lastName;
	
	MyTextView govermentIDTextView;
	MyEditText govermentID;

	MyTextView genderTextView;
	MyRadioGroup gender;
	MyRadioButton male;
	MyRadioButton female;

	MyTextView ageTextView;
	MyEditText age;
	MySpinner ageModifier;

	Calendar dob;
	
	MyButton btnDatePicker;
	MyTextView dobTextView;
// ADULT
	MyTextView PregnantStatusTextView;
	MySpinner PregnantStatus;
	
	MyTextView DateDeliveryTextView;
	MyButton DateDelivery;
	
	MyTextView coughDurationTextView;
	MySpinner coughDuration;

	MyTextView PersistantFeverTextView;
	MySpinner PersistantFever;

	MyTextView adultWeightLossTextView;
	MySpinner adultWeightLoss;

	MyTextView adultWeightGainTextView;
	MySpinner adultWeightGain;

	MyTextView adultChestPainTextView;
	MySpinner adultChestPain;

	MyTextView adultNightSweatsTextView;
	MySpinner adultNightSweats;

	
// CHILD DATA 
	
	MyTextView coughTextView;
	MySpinner cough;


	MyTextView feverTextView;
	MySpinner fever;

	MyTextView weightLossTextView;
	MySpinner weightLoss;
	
	MyTextView childFatigueTextView;
	MySpinner childFatigue;
	
	
// TB FACTOR AND HIV 

	MyTextView riskContactTextView;
	MySpinner riskContact;

	MyTextView riskPreviousTextView;
	MySpinner riskPrevious;

	MyTextView riskPreviousMonthTextView;
	MyEditText riskPreviousMonth;

	MyTextView riskEpisodeTextView;
	MyEditText riskEpisode;

	MyTextView riskDiabeteTextView;
	MySpinner riskDiabete;

	MyTextView hivBeforeTextView;
	MySpinner hivBefore;

	MyTextView hivBefore_yesTextView;
	MySpinner hivBefore_yes;

	MyTextView hivDiscloseTextView;
	MySpinner hivDisclose;

	MyTextView hivResultTextView;
	MySpinner hivResult;

	MyTextView hivArtTextView;
	MySpinner hivArt;

	MyTextView hivTestTextView;
	MySpinner hivTest;

	
// CONTACT TB
	
	MyTextView phoneNumber1TextView;
	MyEditText phoneNumber1;

	MyTextView Phone1OwnerTextView;
	MySpinner  Phone1Owner;

	MyTextView Phone1SomeoneTextView;
	MyEditText Phone1Someone;

	MyTextView phoneNumber2TextView;
	MyEditText phoneNumber2;

	MyTextView Phone2OwnerTextView;
	MySpinner  Phone2Owner;

	MyTextView Phone2SomeoneTextView;
	MyEditText Phone2Someone;

	MyTextView resAddressTextView;
	MyEditText resAddress;

	MyTextView landmarkNearTextView;
	MyEditText landmarkNear;
	
//
	

	

// SUSPECT 
	
	MyTextView tbSuspectTextView;
	MyCheckBox tbSuspect;

// INPORT 
	
	
	

    /*
	MyTextView PregnantStatusTextView;
	MySpinner PregnantStatus;
    */



	


	MyTextView patientIdTextView;
	MyEditText patientId;

	MyButton scanBarcodeIndexId;
	MyButton scanBarcode;
	MyButton validatePatientId;


	MyTextView Lblankspace;
	MyButton submitButton;
	View[] sourceCaseQuestions;

	boolean isSuspect;

	/**
	 * Subclass representing Fragment for Pediatric-screeening suspect form
	 * 
	 * @author owais.hussain@ihsinformatics.com
	 * 
	 */
	@SuppressLint("ValidFragment")
	class PatientScreeningFragment extends Fragment {
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
	class PatientScreeningFragmentPagerAdapter extends
			FragmentPagerAdapter {
		/** Constructor of the class */
		public PatientScreeningFragmentPagerAdapter(
				FragmentManager fragmentManager) {
			super(fragmentManager);
		}

		/** This method will be invoked when a page is requested to create */
		@Override
		public Fragment getItem(int arg0) {
			PatientScreeningFragment fragment = new PatientScreeningFragment();
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
		TAG = "PatientScreeningActivity";
		PAGE_COUNT = 11;
		pager = (ViewPager) findViewById(R.template_id.pager);
		navigationSeekbar.setMax(PAGE_COUNT - 1);
		navigatorLayout = (LinearLayout) findViewById(R.template_id.navigatorLayout);
		// If the form consists only of single page, then hide the
		// navigatorLayout
		if (PAGE_COUNT < 2) {
			navigatorLayout.setVisibility(View.GONE);
		}
		FragmentManager fragmentManager = getSupportFragmentManager();
		PatientScreeningFragmentPagerAdapter pagerAdapter = new PatientScreeningFragmentPagerAdapter(
				fragmentManager);
		pager.setAdapter(pagerAdapter);
		pager.setOffscreenPageLimit(PAGE_COUNT);

		formDateTextView = new MyTextView(context, R.style.text,
				R.string.form_date);
		formDateButton = new MyButton(context, R.style.button,
				R.drawable.custom_button_beige, R.string.form_date,
				R.string.form_date);
		
		DateDeliveryTextView = new MyTextView(context, R.style.text,
				R.string.adult_date_delivery);
		DateDelivery = new MyButton(context, R.style.button,
				R.drawable.custom_button_beige, R.string.form_date,
				R.string.delivery_hint);
		//
		Label_demogr= new MyTextView(context, R.style.text,
				R.string.label_demographic_id);
		Labelsymp_adult= new MyTextView(context, R.style.text,
				R.string.label_adult_symtom_id);
		Labelsymp_child= new MyTextView(context, R.style.text,
				R.string.label_child_symptom_id);
		
		Labelrisk_factor= new MyTextView(context, R.style.text,
				R.string.label_contact_inforisk_factor);
		
		Labelhiv= new MyTextView(context, R.style.text,
				R.string.label_hiv_question);
		
		Labelcontact= new MyTextView(context, R.style.text,
				R.string.label_contact);
		Labeladdress= new MyTextView(context, R.style.text,
				R.string.label_address);
	
		Lblankspace= new MyTextView(context, R.style.text,
				R.string.label_submit);
		
		
		govermentIDTextView = new MyTextView(context, R.style.text,
				R.string.government_id);
		govermentID = new MyEditText(context, R.string.government_id,
				R.string.government_id_hint,
				InputType.TYPE_TEXT_VARIATION_PERSON_NAME, R.style.edit, 20,
				false);

		firstNameTextView = new MyTextView(context, R.style.text,
				R.string.first_name);
		
		firstName = new MyEditText(context, R.string.first_name,
				R.string.first_name_hint,
				InputType.TYPE_TEXT_VARIATION_PERSON_NAME, R.style.edit, 20,
				false);
		lastNameTextView = new MyTextView(context, R.style.text,
				R.string.last_name);
		lastName = new MyEditText(context, R.string.last_name,
				R.string.last_name_hint,
				InputType.TYPE_TEXT_VARIATION_PERSON_NAME, R.style.edit, 20,
				false);

		genderTextView = new MyTextView(context, R.style.text, R.string.gender);
	
		female = new MyRadioButton(context, R.string.female, R.style.radio,
				R.string.female);
		male = new MyRadioButton(context, R.string.male, R.style.radio,
				R.string.male);
		gender = new MyRadioGroup(context,
				new MyRadioButton[] {female, male}, R.string.gender,
				R.style.radio, App.isLanguageRTL());
		dobTextView= new MyTextView(context, R.style.text, R.string.dob);
		btnDatePicker = new MyButton(context, R.style.button,
				R.drawable.custom_button_beige, R.string.form_date,
				R.string.dob_hint);
		

		ageTextView = new MyTextView(context, R.style.text, R.string.age);
		age = new MyEditText(context, R.string.age, R.string.age_hint,
				InputType.TYPE_CLASS_NUMBER, R.style.edit, 2, false);
		ageModifier = new MySpinner(context, getResources().getStringArray(
				R.array.duration_modifier_tb), R.string.age, R.string.option_hint);
	
		
		dob = Calendar.getInstance();

//ADULT IDENTIF
		
		PregnantStatusTextView = new MyTextView(context, R.style.text,
				R.string.adult_pregnant_status);
		PregnantStatus = new MySpinner(context, getResources().getStringArray(
				R.array.four_options), R.string.adult_pregnant_status,
				R.string.option_hint);
	
// ADULT SYMP
		coughDurationTextView = new MyTextView(context, R.style.text,
				R.string.adult_cough);
		coughDuration = new MySpinner(context, getResources().getStringArray(
				R.array.four_options), R.string.adult_cough, R.string.option_hint);

		PersistantFeverTextView = new MyTextView(context, R.style.text,
				R.string.adult_persistant_fever);
		PersistantFever = new MySpinner(context, getResources().getStringArray(
				R.array.three_options_childtb), R.string.adult_persistant_fever,
				R.string.option_hint);

		adultWeightLossTextView = new MyTextView(context, R.style.text,
				R.string.adult_weight_loss);
		adultWeightLoss = new MySpinner(context, getResources().getStringArray(
				R.array.three_options_childtb), R.string.adult_weight_loss,
				R.string.option_hint);
		
		adultWeightGainTextView = new MyTextView(context, R.style.text,
				R.string.adult_weight_gain);
		adultWeightGain = new MySpinner(context, getResources().getStringArray(
				R.array.three_options_childtb), R.string.adult_weight_gain,
				R.string.option_hint);

		adultChestPainTextView = new MyTextView(context, R.style.text,
				R.string.adult_chest_pain);
		adultChestPain = new MySpinner(context, getResources().getStringArray(
				R.array.three_options_childtb), R.string.adult_chest_pain,
				R.string.option_hint);

		adultNightSweatsTextView = new MyTextView(context, R.style.text,
				R.string.adult_night_sweats);
		adultNightSweats = new MySpinner(context, getResources().getStringArray(
				R.array.three_options_childtb), R.string.adult_night_sweats,
				R.string.option_hint);
 // CHILD SYMPT 
		
		coughTextView = new MyTextView(context, R.style.text,
				R.string.child_cough);
		cough = new MySpinner(context, getResources().getStringArray(
				R.array.three_options_childtb), R.string.child_cough,
				R.string.option_hint);
	
		feverTextView = new MyTextView(context, R.style.text,
				R.string.child_fever);
		fever = new MySpinner(context, getResources().getStringArray(
				R.array.three_options_childtb), R.string.child_fever,
				R.string.option_hint);
		
		weightLossTextView = new MyTextView(context, R.style.text,
				R.string.child_weight_loss);
		weightLoss = new MySpinner(context, getResources().getStringArray(
				R.array.three_options_childtb), R.string.child_weight_loss,
				R.string.option_hint);
		
		childFatigueTextView = new MyTextView(context, R.style.text,
				R.string.child_fatigue);
		childFatigue = new MySpinner(context, getResources().getStringArray(
				R.array.three_options_childtb), R.string.child_fatigue,
				R.string.option_hint);
	
		riskContactTextView = new MyTextView(context, R.style.text,
				R.string.risk_contact);
		riskContact = new MySpinner(context, getResources()
				.getStringArray(R.array.three_options_unknown),
				R.string.risk_contact, R.string.option_hint);
		
		riskPreviousTextView = new MyTextView(context, R.style.text,
				R.string.risk_previous);	
		riskPrevious =new MySpinner(context, getResources()
				.getStringArray(R.array.three_options_childtb),
				R.string.risk_previous, R.string.option_hint);
		
		
		riskPreviousMonthTextView = new MyTextView(context, R.style.text,
				R.string.risk_previous_month);
		riskPreviousMonth = new MyEditText(context, R.string.risk_previous_month, R.string.risk_month_hint,
				InputType.TYPE_CLASS_NUMBER, R.style.edit, 2, false);			
		riskEpisodeTextView = new MyTextView(context, R.style.text,
				R.string.risk_episode);
		riskEpisode = new MyEditText(context, R.string.risk_episode, R.string.risk_episode_hint,
				InputType.TYPE_CLASS_NUMBER, R.style.edit, 2, false);
					
		riskDiabeteTextView = new MyTextView(context, R.style.text,
				R.string.risk_diabete);
		riskDiabete = new MySpinner(context, getResources()
				.getStringArray(R.array.three_options_unknown),
				R.string.risk_diabete, R.string.option_hint);
		

		
		hivBeforeTextView = new MyTextView(context, R.style.text,
				R.string.hiv_before);
		hivBefore = new MySpinner(context, getResources()
				.getStringArray(R.array.three_options_childtb),
				R.string.hiv_before, R.string.option_hint);

		hivBefore_yesTextView = new MyTextView(context, R.style.text,
				R.string.hiv_before_yes);
		hivBefore_yes = new MySpinner(context, getResources()
				.getStringArray(R.array.hiv_before_options),
				R.string.hiv_before_yes, R.string.option_hint);
		

		hivDiscloseTextView = new MyTextView(context, R.style.text,
				R.string.hiv_disclose);
		hivDisclose = new MySpinner(context, getResources().getStringArray(
				R.array.two_options), R.string.hiv_disclose,
				R.string.option_hint);
		

		hivResultTextView = new MyTextView(context, R.style.text,
				R.string.hiv_result);
		hivResult = new MySpinner(context, getResources()
				.getStringArray(R.array.three_options_hiv),
				R.string.hiv_result, R.string.option_hint);

		hivArtTextView = new MyTextView(context, R.style.text,
				R.string.hiv_art_);
		hivArt = new MySpinner(context, getResources().getStringArray(
				R.array.three_options_childtb), R.string.hiv_art_,
				R.string.option_hint);

		hivTestTextView = new MyTextView(context, R.style.text,
				R.string.hiv_test);
		hivTest = new MySpinner(context, getResources().getStringArray(
				R.array.two_options), R.string.hiv_test,
				R.string.option_hint);
		
		phoneNumber1TextView = new MyTextView(context, R.style.text,
				R.string.phonenumber1);
		phoneNumber1 = new MyEditText(context, R.string.phonenumber1, R.string.phone_hint,
				InputType.TYPE_CLASS_NUMBER, R.style.edit, 10, false);
		
		Phone1OwnerTextView = new MyTextView(context,
				R.style.text, R.string.phone1_owner);
		Phone1Owner = new MySpinner(context, getResources()
				.getStringArray(R.array.phone_owner_option),
				R.string.phone1_owner, R.string.option_hint);
	
		Phone1SomeoneTextView = new MyTextView(context, R.style.text,
				R.string.phone1_someone);
		Phone1Someone = new MyEditText(context, R.string.phone1_someone,
				R.string.phone_someone_hint,
				InputType.TYPE_TEXT_VARIATION_PERSON_NAME, R.style.edit, 20, false);
		
		phoneNumber2TextView = new MyTextView(context, R.style.text,
				R.string.phonenumber2);
		phoneNumber2 = new MyEditText(context, R.string.phonenumber2, R.string.phone_hint,
				InputType.TYPE_CLASS_NUMBER, R.style.edit, 10, false);
		
		Phone2OwnerTextView = new MyTextView(context,
				R.style.text, R.string.phone2_owner);
		Phone2Owner = new MySpinner(context, getResources()
				.getStringArray(R.array.phone_owner_option),
				R.string.phone2_owner, R.string.option_hint);
	
		Phone2SomeoneTextView = new MyTextView(context, R.style.text,
				R.string.phone2_someone);
		Phone2Someone = new MyEditText(context, R.string.phone1_someone,
				R.string.phone_someone_hint,
				InputType.TYPE_TEXT_VARIATION_PERSON_NAME, R.style.edit, 20, false);
		
		resAddressTextView = new MyTextView(context, R.style.text,
				R.string.res_address);
		resAddress = new MyEditText(context, R.string.res_address,
				R.string.res_address_hint,
				InputType.TYPE_TEXT_VARIATION_PERSON_NAME, R.style.edit, 20, false);
		
		landmarkNearTextView = new MyTextView(context, R.style.text,
				R.string.landmark_near);
		landmarkNear = new MyEditText(context, R.string.landmark_near,
				R.string.landmark_near_hint,
				InputType.TYPE_TEXT_VARIATION_PERSON_NAME, R.style.edit, 20, false);
		
	

		tbSuspect = new MyCheckBox(context, R.string.client_suspect,
				R.style.edit, R.string.client_suspect, false);
		tbSuspect.setClickable(false);

		patientIdTextView = new MyTextView(context, R.style.text,
				R.string.patient_id);
		patientId = new MyEditText(context, R.string.patient_id,
				R.string.patient_id_hint, InputType.TYPE_CLASS_TEXT,
				R.style.edit, RegexUtil.idLength, false);

		scanBarcodeIndexId = new MyButton(context, R.style.button,
				R.drawable.custom_button_beige, R.string.scan_barcode,
				R.string.scan_barcode);
		scanBarcode = new MyButton(context, R.style.button,
				R.drawable.custom_button_beige, R.string.scan_barcode,
				R.string.scan_barcode);
		
		submitButton = new MyButton(context, R.style.button,
				R.drawable.custom_button_beige, R.string.form_submit,
				R.string.form_submit);
		
		validatePatientId = new MyButton(context, R.style.button,
				R.drawable.custom_button_beige, R.string.validateID,
				R.string.validateID);

		sourceCaseQuestions = new View[] { resAddress,
				landmarkNear};

		// TODO: complete it and remove the fields from the Pediatiric Screening
		// forms

		View[][] viewGroups = {
					
				{ formDateTextView, formDateButton, firstNameTextView, firstName, lastNameTextView,
						lastName, govermentIDTextView,
						govermentID},
				
				{ dobTextView,btnDatePicker,ageTextView, age, /*ageModifier,*/
							genderTextView, gender/*dobTextView, dobPicker,*/
							 },
				{ PregnantStatusTextView, PregnantStatus,DateDeliveryTextView, DateDelivery,
					Labelsymp_child, coughTextView, cough,feverTextView, fever},
				
				{ Labelsymp_adult ,
					coughDurationTextView, coughDuration,PersistantFeverTextView, PersistantFever,		
					 adultWeightLossTextView, adultWeightLoss,
					 
						weightLossTextView, weightLoss
				 },
				 
				{ adultWeightGainTextView,
					adultWeightGain,adultChestPainTextView,adultChestPain , 
					adultNightSweatsTextView, adultNightSweats,
					
					childFatigueTextView, childFatigue
					},
				
										
				{Labelrisk_factor, 
							riskContactTextView, riskContact,
							riskPreviousTextView, riskPrevious,
							riskPreviousMonthTextView,riskPreviousMonth,
							riskEpisodeTextView, riskEpisode},		
						
				{
					riskDiabeteTextView, riskDiabete,
					Labelhiv,
					hivBeforeTextView, hivBefore,hivBefore_yesTextView, hivBefore_yes},
				
				{ 	hivDiscloseTextView, hivDisclose, 					
					hivResultTextView, hivResult,
					hivArtTextView, hivArt,
					hivTestTextView, hivTest },
				
				{ phoneNumber1TextView, phoneNumber1,
					Phone1OwnerTextView,
					Phone1Owner,
					Phone1SomeoneTextView, Phone1Someone 		
					,phoneNumber2TextView, phoneNumber2,
					Labelcontact
					},
				{ 
						Phone2OwnerTextView,
						Phone2Owner,
						Phone2SomeoneTextView, Phone2Someone,
						 resAddressTextView, resAddress,
							landmarkNearTextView,
							landmarkNear
							,Labeladdress},
				
				{ tbSuspect, patientIdTextView,
						patientId, scanBarcode ,Lblankspace, submitButton} };

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
		DateDelivery.setOnClickListener(this);
		firstButton.setOnClickListener(this);
		lastButton.setOnClickListener(this);
		clearButton.setOnClickListener(this);
		saveButton.setOnClickListener(this);
		submitButton.setOnClickListener(this);
		scanBarcode.setOnClickListener(this);
		scanBarcodeIndexId.setOnClickListener(this);
		validatePatientId.setOnClickListener(this);
		navigationSeekbar.setOnSeekBarChangeListener(this);
		female.setOnCheckedChangeListener(this);
		male.setOnCheckedChangeListener(this);
		
		btnDatePicker.setOnClickListener(this);
		
		
// AGE ON EDITOR 
		age.setOnEditorActionListener(this);
		
		views = new View[] { age, ageModifier, govermentID,
				coughDuration, adultWeightLoss, PersistantFever, DateDelivery,
				adultWeightGain, PregnantStatus, cough, adultChestPain,
				cough, coughDuration, fever, childFatigue, weightLoss,
				adultNightSweats, riskContact, riskPrevious,	
				riskPreviousMonth, riskEpisode, riskDiabete,
				hivBefore, hivBefore_yes, hivDisclose,
				hivResult, hivArt, hivTest,
				phoneNumber1, Phone1Owner, Phone1Someone,
				resAddress, landmarkNear,
				firstName, lastName, tbSuspect, patientId, phoneNumber2 };

		for (View v : views) {
			if (v instanceof Spinner) {
				((Spinner) v).setOnItemSelectedListener(this);
			} else if (v instanceof CheckBox) {
				((CheckBox) v).setOnCheckedChangeListener(this);
			}
		}
		pager.setOnPageChangeListener(this);
		age.setOnFocusChangeListener(new OnFocusChangeListener() {
			public void onFocusChange(View view, boolean state) {
				if (!state) {
					updateDob();
				}
			}
		});
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
		//updateDisplay();
		dob.setTime(new Date());	
		//male.setChecked(true);
		tbSuspect.setChecked(false); 
		male.setChecked(false);
    	female.setChecked(false);
// ADULT	
		PregnantStatusTextView.setVisibility(View.GONE);
		PregnantStatus.setVisibility(View.GONE);
		DateDeliveryTextView.setVisibility(View.GONE);		
		DateDelivery.setVisibility(View.GONE);
		age.setFocusable(false);
		
	adult_visible_not();	
	child_visible_not();	
	contact_visible_not();
 

riskContactTextView.setVisibility(View.VISIBLE);
riskContact.setVisibility(View.VISIBLE);
riskPreviousTextView.setVisibility(View.VISIBLE);
riskPrevious.setVisibility(View.VISIBLE);

riskPreviousMonthTextView.setVisibility(View.GONE);
riskPreviousMonth.setVisibility(View.GONE);
riskPreviousMonth.setVisibility(View.GONE);
riskEpisodeTextView.setVisibility(View.GONE);
riskEpisode.setVisibility(View.GONE);
riskEpisode.setVisibility(View.GONE);
riskDiabeteTextView.setVisibility(View.VISIBLE);

riskDiabete.setVisibility(View.VISIBLE);
hivBeforeTextView.setVisibility(View.VISIBLE);
hivBefore.setVisibility(View.VISIBLE);

hivBefore_yesTextView.setVisibility(View.GONE);
hivBefore_yes.setVisibility(View.GONE);

 hivDiscloseTextView.setVisibility(View.GONE);
 hivDisclose.setVisibility(View.GONE);
 hivResultTextView.setVisibility(View.GONE);
 hivResult.setVisibility(View.GONE);
  hivArtTextView.setVisibility(View.GONE);
  hivArt.setVisibility(View.GONE);
 hivTestTextView.setVisibility(View.GONE);
 hivTest.setVisibility(View.GONE);

		
//CONTACT
 

	}

	@Override
	public void onClick(View view) {
		if (view == formDateButton) {
			showDialog(DATE_DIALOG_ID);
		}
		
// DOB 
	if (view == btnDatePicker) {
			
			final int mYear;
			int mMonth, mDay, mHour, mMinute;
            // Get Current Date
            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);
            final int agec;

            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {
                    
                          //age.setText(mYear - year);

                      	age.setText((mYear - year) + "");
                          
                            //txtDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                      	
                      
              			male.setChecked(false);
              			female.setChecked(false);
              		    child_visible_not();
              		    adult_visible_not();
              			
                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
          
   		updateDisplay();
		}
		
		
		if (view == DateDelivery) {
				showDialog(DATE_DIALOG_ID);
			
		} else if (view == firstButton) {
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
		} 
		else if (view == scanBarcode) {
			Intent intent = new Intent(Barcode.BARCODE_INTENT);
			intent.putExtra(Barcode.SCAN_MODE, Barcode.QR_MODE);
			startActivityForResult(intent, Barcode.BARCODE_RESULT);
			
		} else if (view == scanBarcodeIndexId) {
			Intent intent = new Intent(Barcode.BARCODE_INTENT);
			intent.putExtra(Barcode.SCAN_MODE, Barcode.QR_MODE);
			startActivityForResult(intent, Barcode.BARCODE_RESULT_INDEX_ID);
		} else if (view == validatePatientId) {
			final String govermentIDId = App.get(DateDelivery);
			final String[] conceptArray = { "",	"" };
			if (!govermentIDId.equals("")) {
				AsyncTask<String, String, String[][]> getTask = new AsyncTask<String, String, String[][]>() {
					@Override
					protected String[][] doInBackground(String... params) {
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
						String[][] patientObs = serverService
								.getSpecificPatientObs(govermentIDId,
										conceptArray,
										FormType.REVERSE_CONTACT_TRACING);
						return patientObs;
					}

					@Override
					protected void onProgressUpdate(String... values) {
					};

				/*	@Override
					protected void onPostExecute(String[][] result) {
						super.onPostExecute(result);
						loading.dismiss();
						adultWeightGain.setText("");

						if (result != null && result.length > 0) {
							for (int i = 0; i < result.length; i++) {
								if (result[i][0].equals("Index Case PregnantStatus")) {
									if (result[i][1].equals("Pulmonary"))
										PregnantStatus.setSelection(0);
									else
										PregnantStatus.setSelection(1);

									PregnantStatus.setClickable(false);
								} else {
									adultWeightGain
											.setTextColor(getResources()
													.getColor(R.color.Chocolate));

									adultWeightGain.setText(result[i][1]);
								}
							}
						} else {
							App.getAlertDialog(
									PatientScreeningActivity.this,
									AlertType.ERROR,
									DateDelivery.getTag().toString()
											+ ": "
											+ getResources().getString(
													R.string.item_not_found))
									.show();
						}
					}
					
					*/
				};
				getTask.execute("");
			} else {
				App.getAlertDialog(
						this,
						AlertType.ERROR,
						DateDelivery.getTag().toString() + ": "
								+ getResources().getString(R.string.empty_data))
						.show();
			}
		}
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position,
			long id) {
		MySpinner spinner = (MySpinner) parent;
		
		boolean visible = spinner.getSelectedItemPosition() == 1;
		
		
		if (parent == PregnantStatus) {
			
		
		if (PregnantStatus.getSelectedItem().toString().equals("Yes")) 
		{
			DateDeliveryTextView.setVisibility(View.VISIBLE);
			DateDelivery.setVisibility(View.VISIBLE);
			
			updateDisplay();
		}
		else if ((PregnantStatus.getSelectedItem().toString().equals("No")) 
			||(PregnantStatus.getSelectedItem().toString().equals("Unknown")))
		
		{
			DateDeliveryTextView.setVisibility(View.GONE);
			DateDelivery.setVisibility(View.GONE);	
			
			
// close queationnaire if woman not pregnant 
			
	 		String message = "Only Pregnant Women are subjected to be screened ";
				AlertDialog dialog;
				AlertDialog.Builder builder = new Builder(this);
				builder.setMessage(message);
				builder.setIcon(R.drawable.info);
				dialog = builder.create();
				dialog.setTitle("Information");
				OnClickListener listener = new OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
						finish();
						Intent mainMenuIntent = new Intent(
								PatientScreeningActivity.this,
								MainMenuActivity.class);
						startActivity(mainMenuIntent);
						
					}
				};
				dialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK", listener);
				dialog.show();
				
				updateDisplay();
			
		}
		else if ((PregnantStatus.getSelectedItem().toString().equals("-Please Select-")))
			
			{
				DateDeliveryTextView.setVisibility(View.GONE);
				DateDelivery.setVisibility(View.GONE);	
				updateDisplay();
			}
		
		}
		
		
		else if (parent == fever) {
		} 
		
		

	// Treated TB
		if (parent == riskPrevious) {
			
			if (riskPrevious.getSelectedItem().toString().equals("Yes")) 
			{
		
			riskPreviousMonthTextView.setVisibility(View.VISIBLE);
			riskPreviousMonth.setVisibility(View.VISIBLE);
			riskPreviousMonth.setVisibility(View.VISIBLE);
			
			riskEpisodeTextView.setVisibility(View.VISIBLE);
			riskEpisode.setVisibility(View.VISIBLE);
			riskEpisode.setVisibility(View.VISIBLE);
			
			contact_visible();
			updateDisplay();
			
			}
			else if ((riskPrevious.getSelectedItem().toString().equals("No")) 
				||(riskPrevious.getSelectedItem().toString().equals("-Please Select-")))
			{
		   
			riskPreviousMonthTextView.setVisibility(View.GONE);
			riskPreviousMonth.setVisibility(View.GONE);
			riskPreviousMonth.setVisibility(View.GONE);
			
			riskEpisodeTextView.setVisibility(View.GONE);
			riskEpisode.setVisibility(View.GONE);
			riskEpisode.setVisibility(View.GONE);
			
			contact_visible_not();
			updateDisplay();
			}
			
			
		}	
		
	// HIV
		
		if (parent == hivBefore) {
				
			if (hivBefore.getSelectedItem().toString().equals("Yes")) 
			{
					
					hivBefore_yesTextView.setVisibility(View.VISIBLE);
					hivBefore_yes.setVisibility(View.VISIBLE);
				    hivDiscloseTextView.setVisibility(View.VISIBLE);
					hivDisclose.setVisibility(View.VISIBLE);
					
					hivTestTextView.setVisibility(View.GONE);
					hivTest.setVisibility(View.GONE);
					updateDisplay();
			}	
			
			else if ((hivBefore.getSelectedItem().toString().equals("No")) 
				   ||(hivBefore.getSelectedItem().toString().equals("Decline"))
				   )
				
			{
					
					hivBefore_yesTextView.setVisibility(View.GONE);
					hivBefore_yes.setVisibility(View.GONE);
				    hivDiscloseTextView.setVisibility(View.GONE);
					hivDisclose.setVisibility(View.GONE);
					
					hivTestTextView.setVisibility(View.VISIBLE);
					hivTest.setVisibility(View.VISIBLE);
					updateDisplay();
			}
			else if (hivBefore.getSelectedItem().toString().equals("-Please Select-"))
					   
					
				{
						
						hivBefore_yesTextView.setVisibility(View.GONE);
						hivBefore_yes.setVisibility(View.GONE);
					    hivDiscloseTextView.setVisibility(View.GONE);
						hivDisclose.setVisibility(View.GONE);
						
						hivTestTextView.setVisibility(View.GONE);
						hivTest.setVisibility(View.GONE);
			
						updateDisplay();
				}

			   
			
		}

		if (parent == hivBefore_yes) {
		  if (hivBefore_yes.getSelectedItem().toString().equals("Less than 3 months")) 
			{
			hivTestTextView.setVisibility(View.GONE);
			hivTest.setVisibility(View.GONE);
			 updateDisplay();
			}
		  
		  else if ((hivBefore_yes.getSelectedItem().toString().equals("3 months and above")) 
			  ||(hivBefore_yes.getSelectedItem().toString().equals("Not Remember")))
			{
			hivTestTextView.setVisibility(View.VISIBLE);
			hivTest.setVisibility(View.VISIBLE);
			 updateDisplay();
			}
		  
		  else if ((hivBefore_yes.getSelectedItem().toString().equals("-Please Select-")))
				{
				hivTestTextView.setVisibility(View.GONE);
				hivTest.setVisibility(View.GONE);
				 updateDisplay();
				}
  
		}
		
		
		if (parent == hivDisclose) {
		if (hivDisclose.getSelectedItem().toString().equals("Yes")) 
			{
				 hivResultTextView.setVisibility(View.VISIBLE);
				 hivResult.setVisibility(View.VISIBLE);
				 updateDisplay();
			} 
		else if ((hivDisclose.getSelectedItem().toString().equals("No"))
			||(hivDisclose.getSelectedItem().toString().equals("-Please Select-"))) 
		{
			 hivResultTextView.setVisibility(View.GONE);
			 hivResult.setVisibility(View.GONE);
			 updateDisplay();
		} 
		
		
	}
		
		if (parent == hivResult) {
		if (hivResult.getSelectedItem().toString().equals("Pos")) 
			{    
			      
				     hivArtTextView.setVisibility(View.VISIBLE);
					hivArt.setVisibility(View.VISIBLE);
					
					contact_visible();
				    updateDisplay();
			}
		else if ((hivResult.getSelectedItem().toString().equals("Neg"))
				||(hivResult.getSelectedItem().toString().equals("Unknown"))
				||(hivResult.getSelectedItem().toString().equals("-Please Select-")))
		{    
		      
			     hivArtTextView.setVisibility(View.GONE);
				 hivArt.setVisibility(View.GONE);
				 
				 contact_visible_not();
					  updateDisplay();
		}
		
		}
	
// CONTACT
		
if (parent == Phone1Owner) {
			
			if (Phone1Owner.getSelectedItem().toString().equals("Someone Else")) 
			{
				 Phone1SomeoneTextView.setVisibility(View.VISIBLE);
				 Phone1Someone.setVisibility(View.VISIBLE);
			
			contact_visible();
			updateDisplay();
			
			}
			else if ((Phone1Owner.getSelectedItem().toString().equals("Myself")) 
				||(Phone1Owner.getSelectedItem().toString().equals("-Please Select-")))
			{   
				 Phone1SomeoneTextView.setVisibility(View.GONE);
				 Phone1Someone.setVisibility(View.GONE);
			
			contact_visible_not();
			updateDisplay();
			}
				
		}	

if (parent == Phone2Owner) {
	
	if (Phone2Owner.getSelectedItem().toString().equals("Someone Else")) 
	{
		 Phone2SomeoneTextView.setVisibility(View.VISIBLE);
		 Phone2Someone.setVisibility(View.VISIBLE);
	
	contact_visible();
	updateDisplay();
	
	}
	else if ((Phone2Owner.getSelectedItem().toString().equals("Myself")) 
		||(Phone2Owner.getSelectedItem().toString().equals("-Please Select-")))
	{   
		 Phone2SomeoneTextView.setVisibility(View.GONE);
		 Phone2Someone.setVisibility(View.GONE);
	
	contact_visible_not();
	updateDisplay();
	}
	
	
}
		
			
			 
//DIALOGE IF HIV TEST ACCEPTED

		
			
		
		
// IMPORTANT FO
		/*else if (parent == hivResult) {
			hivArtTextView.setEnabled(visible);
			hivArt.setEnabled(visible);
			hivBeforeTextView.setEnabled(visible);
			hivBefore.setEnabled(visible);
			phoneNumber1TextView.setEnabled(visible);
			phoneNumber1.setEnabled(visible);
			Phone1OwnerTextView.setEnabled(visible);
			Phone1Owner.setEnabled(visible);
			
		} 
		*/
	
		//updateDisplay();
	}

	@Override
	public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
		
// IF FEMALE CHECKED 
		//
		
    if (female.isChecked())
    {

		male.setChecked(false);
      if(App.get(age).equals(""))
      {
    		String message = "Please Chose the DoB before";
			AlertDialog dialog;
			AlertDialog.Builder builder = new Builder(this);
			builder.setMessage(message);
			builder.setIcon(R.drawable.info);
			dialog = builder.create();
			dialog.setTitle("Information");
			OnClickListener listener = new OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					dialog.dismiss();
					/*finish();
					Intent mainMenuIntent = new Intent(
							PatientScreeningActivity.this,
							MainMenuActivity.class);
					startActivity(mainMenuIntent);
					*/
				}
			};
			dialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK", listener);
			dialog.show();
			
			female.setChecked(false);

			updateDisplay();
      }
      else {
    	int ageInDays;
		
		ageInDays = Integer.parseInt(App.get(age)) * 365;
						
		if (ageInDays > 365 * 15)
		  {		
			
			
			PregnantStatusTextView.setVisibility(View.VISIBLE);
			PregnantStatus.setVisibility(View.VISIBLE);
			
			adult_visible();
			child_visible_not();
			
			ageInDays = 0;
			updateDisplay();
			}
		
		else if (ageInDays < 365 * 15)
		  {	
			
			
			PregnantStatusTextView.setVisibility(View.GONE);
			PregnantStatus.setVisibility(View.GONE);
			
			child_visible();
			adult_visible_not();
			
			ageInDays = 0;
			updateDisplay();
		  }
		
		
		
// MOVE TO MALE
		
// ADULT MALE
		
		
		
	/*	
else if ((ageModifier.getSelectedItem().toString().equals("Week(s)")) 
	||(ageModifier.getSelectedItem().toString().equals("Month(s)")))
{
	adult=false;
	child=true;
	
	PregnantStatusTextView.setVisibility(View.GONE);
	PregnantStatus.setVisibility(View.GONE);	
	Labelnext1.setVisibility(View.VISIBLE);
	Labelnext2.setVisibility(View.VISIBLE);
	Labelnext3.setVisibility(View.VISIBLE);
	Labelnext4.setVisibility(View.GONE);
	
	child_visible();
	adult_visible_not();
	updateDisplay();
}
		
else if (ageModifier.getSelectedItem().toString().equals("-Please Select-"))
{

	String message = "Please Type The Age and Period";
	AlertDialog dialog;
	AlertDialog.Builder builder = new Builder(this);
	builder.setMessage(message);
	builder.setIcon(R.drawable.info);
	dialog = builder.create();
	dialog.setTitle("Information");
	OnClickListener listener = new OnClickListener() {
		@Override
		public void onClick(DialogInterface dialog, int which) {
			dialog.dismiss();
			
			
		}
	};
	dialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK", listener);
	dialog.show();
	
	female.setChecked(false);
//	updateDisplay();
}
   */
		
    }
    }
   
  // IF MALE is checked 

    else if (male.isChecked())
    {
    	female.setChecked(false);
    	if(App.get(age).equals(""))
        {
      		String message = "Please Chose the DoB before";
  			AlertDialog dialog;
  			AlertDialog.Builder builder = new Builder(this);
  			builder.setMessage(message);
  			builder.setIcon(R.drawable.info);
  			dialog = builder.create();
  			dialog.setTitle("Information");
  			OnClickListener listener = new OnClickListener() {
  				@Override
  				public void onClick(DialogInterface dialog, int which) {
  					dialog.dismiss();
  					/*finish();
  					Intent mainMenuIntent = new Intent(
  							PatientScreeningActivity.this,
  							MainMenuActivity.class);
  					startActivity(mainMenuIntent);
  					*/
  				}
  			};
  			dialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK", listener);
  			dialog.show();
  			
  			male.setChecked(false);
  			updateDisplay();
        }
        else {
    	int ageInDays ;
		ageInDays = Integer.parseInt(App.get(age)) * 365;
						
		if (ageInDays > 365 * 15)
		  {		
				

			String message = "The screened suspect is Adult gender MALE. - Not Applicable-";
			AlertDialog dialog;
			AlertDialog.Builder builder = new Builder(this);
			builder.setMessage(message);
			builder.setIcon(R.drawable.info);
			dialog = builder.create();
			dialog.setTitle("Information");
			OnClickListener listener = new OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					dialog.dismiss();
					/*finish();
					Intent mainMenuIntent = new Intent(
							PatientScreeningActivity.this,
							MainMenuActivity.class);
					startActivity(mainMenuIntent);
					*/
				}
			};
			dialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK", listener);
			dialog.show();
			
			male.setChecked(false);
			
			ageInDays = 0;
			updateDisplay();
			}
		
		else if (ageInDays < 365 * 15)
		  {	
			
			PregnantStatusTextView.setVisibility(View.GONE);
			PregnantStatus.setVisibility(View.GONE);
			child_visible();
			adult_visible_not();
			
			ageInDays = 0;
			updateDisplay();
		  }
		
		
		
        }
    	
    }

    
  
	}

	@Override
	public boolean onLongClick(View arg0) {
		return false;
	}

	@Override
	public void updateDisplay() 
	
	{
		
		
		formDateButton.setText(DateFormat.format("dd-MMM-yyyy", formDate));	
		DateDelivery.setText(DateFormat.format("dd-MMM-yyyy", formDate));	

		
		if ((coughDuration.getSelectedItem().toString().equals("Yes"))
			||(PersistantFever.getSelectedItem().toString().equals("Yes"))
			||(adultWeightLoss.getSelectedItem().toString().equals("Yes"))
			||(adultWeightGain.getSelectedItem().toString().equals("Yes"))
			||(adultChestPain.getSelectedItem().toString().equals("Yes"))
			||(adultNightSweats.getSelectedItem().toString().equals("Yes"))
			||(cough.getSelectedItem().toString().equals("Yes"))
			||(fever.getSelectedItem().toString().equals("Yes"))
			||(weightLoss.getSelectedItem().toString().equals("Yes"))
			||(childFatigue.getSelectedItem().toString().equals("Yes"))
			)
			
		 { 
		 		contact_visible();
				
			}
		else if((coughDuration.getSelectedItem().toString().equals("No"))
				||(PersistantFever.getSelectedItem().toString().equals("No"))
				||(adultWeightLoss.getSelectedItem().toString().equals("No"))
				||(adultWeightGain.getSelectedItem().toString().equals("No"))
				||(adultChestPain.getSelectedItem().toString().equals("No"))
				||(adultNightSweats.getSelectedItem().toString().equals("No"))
				||(cough.getSelectedItem().toString().equals("No"))
				||(fever.getSelectedItem().toString().equals("No"))
				||(weightLoss.getSelectedItem().toString().equals("No"))
				||(childFatigue.getSelectedItem().toString().equals("No"))
				
				)
			
			 { 
			 		contact_visible_not();
					
				}
	
	}

	/**
	 * Updates the DOB picker date
	 */
	
	
	private void updateDob() {
		// Calculate dob by subtracting age in days from dob object
		if (!"".equals(App.get(age))) {

			int index = ageModifier.getSelectedItemPosition();
			int multiplier = index == 0 ? 1 : index == 1 ? 7 : index == 2 ? 30
					: index == 3 ? 365 : 0;
			int a = Integer.parseInt(App.get(age)) * multiplier;
			
			dob = Calendar.getInstance();
			dob.add(Calendar.DAY_OF_YEAR, -a);
/*
			dobPicker.updateDate(dob.get(Calendar.YEAR),
					dob.get(Calendar.MONTH), dob.get(Calendar.DAY_OF_MONTH));
*/
		}
	}

// Void for messages alert 
	public void alertmsg() {
		if ((tbSuspect.isChecked())&&(PregnantStatus.getSelectedItem().toString().equals("Yes")))
		  {
			 
		  String message = "Instruct the pregnant Woman/family " +App.get(firstName) + " " + App.get(lastName) + "to contact IRD SA when in labour \n";
				AlertDialog dialog;
				AlertDialog.Builder builder = new Builder(this);
				builder.setMessage(message);
				builder.setIcon(R.drawable.info);
				dialog = builder.create();
				dialog.setTitle("Information");
				OnClickListener listener = new OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
						/*finish();
						Intent mainMenuIntent = new Intent(
								PatientScreeningActivity.this,
								MainMenuActivity.class);
						startActivity(mainMenuIntent);
						*/
					}
				};
				dialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK", listener);
				dialog.show();

				
			}
		if (hivTest.getSelectedItem().toString().equals("Yes"))
		{
			String message = "Refer client to nearest facility for HIV testing\n";
			AlertDialog dialog;
			AlertDialog.Builder builder = new Builder(this);
			builder.setMessage(message);
			builder.setIcon(R.drawable.info);
			dialog = builder.create();
			dialog.setTitle("Information");
			OnClickListener listener = new OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					dialog.dismiss();
					/*finish();
					Intent mainMenuIntent = new Intent(
							PatientScreeningActivity.this,
							MainMenuActivity.class);
					startActivity(mainMenuIntent);
					*/
				}
			};
			dialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK", listener);
			dialog.show();	
		}
	}
	
	@Override
	public boolean validate() {
			
		boolean valid = true;
		StringBuffer message = new StringBuffer();
// Validate mandatory controls
		View[] mandatory = { firstName, lastName,govermentID , age };
		for (View v : mandatory) {
			if (App.get(v).equals("")) {
				valid = false;
				message.append(v.getTag().toString() + ". ");
				((EditText) v).setHintTextColor(getResources().getColor(
						R.color.Red));
			}
		}
		if (tbSuspect.isChecked() && App.get(patientId).equals("")) {
			valid = false;
			message.append(patientId.getTag().toString() + ". ");
			patientId.setHintTextColor(getResources().getColor(R.color.Red));
		}
		
	
		
		if (!valid) {
			message.append(getResources().getString(R.string.empty_data) + "\n");
		}
		// Validate data
		if (valid) {
			if (!RegexUtil.isWord(App.get(firstName))) {
				valid = false;
				message.append(firstName.getTag().toString() + ": "
						+ getResources().getString(R.string.invalid_data)
						+ "\n");
				firstName.setTextColor(getResources().getColor(R.color.Red));
			}
			if (!RegexUtil.isWord(App.get(lastName))) {
				valid = false;
				message.append(lastName.getTag().toString() + ": "
						+ getResources().getString(R.string.invalid_data)
						+ "\n");
				lastName.setTextColor(getResources().getColor(R.color.Red));
			}



			if (tbSuspect.isChecked()) {
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
			
			
// AGE VALIDATION
			
			if (!RegexUtil.isNumeric(App.get(age), false)) {
				valid = false;
				message.append(age.getTag().toString() + ": "
						+ getResources().getString(R.string.invalid_data)
						+ "\n");
				age.setTextColor(getResources().getColor(R.color.Red));
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

			
			// // Age range
			// if (Calendar.getInstance ().get (Calendar.YEAR) - dob.get
			// (Calendar.YEAR) > 99)
			// {
			// valid = false;
			// message.append (age.getTag ().toString () + ": " + getResources
			// ().getString (R.string.out_of_range) + "\n");
			// }

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
		updateDob();
		if (validate()) {
			 alertmsg();
			final ContentValues values = new ContentValues();
			values.put("formDate", App.getSqlDate(formDate));
			values.put("location", App.getLocation());
			values.put("firstName", App.get(firstName));
			values.put("lastName", App.get(lastName));
			values.put("govermentID", App.get(govermentID));
			
			// values.put ("age", ageInYears);
			values.put("dob", App.getSqlDate(dob));
			values.put("gender", male.isChecked() ? "M" : "F");
			values.put("patientId", App.get(patientId));
			
			
			final ArrayList<String[]> observations = new ArrayList<String[]>();


			observations.add(new String[] { "Age", App.get(age) });
			observations.add(new String[] { "Age Modifier",App.get(ageModifier) });
	// ADULT
			
	      observations.add(new String[] { "Pregnant Status", App.get(PregnantStatus) });
			
			if (PregnantStatus.getSelectedItem().toString()
					.equals(getResources().getString(R.string.yes))) {
				observations.add(new String[] { "Date of Delivery",App.get(DateDelivery) });	
			}
			
		
			observations.add(new String[] { "Adult Cough ",App.get(coughDuration) });		
			observations.add(new String[] { "Adult WeightLoss ", App.get(adultWeightLoss) });
			observations.add(new String[] { "Adult Weight gain", App.get(adultWeightGain) });	
			observations.add(new String[] { "Adult Persistant Fever",App.get(PersistantFever) });
			observations.add(new String[] { "Adult Night Sweats",App.get(adultNightSweats) });
			observations.add(new String[] { "Adult Chest Pain",App.get(adultChestPain) });
			
	// CHILD
		
			observations.add(new String[] { "Child Cough", App.get(cough) });
			observations.add(new String[] { "child Fever", App.get(fever) });
			observations.add(new String[] { "Child Weight Loss", App.get(weightLoss) });		
			observations.add(new String[] { "Child Fatigue",App.get(childFatigue) });
			
			observations.add(new String[] { "Contact tb",App.get(riskContact) });
			observations.add(new String[] { "Previous treated",App.get(riskPrevious) });
			observations.add(new String[] { "Previous treated Months",App.get(riskPreviousMonth) });
			observations.add(new String[] { "Previous treated Episode",App.get(riskEpisode) });
			observations.add(new String[] { "Diabete", App.get(riskDiabete) });
			
			
			observations.add(new String[] { "Hiv before",App.get(hivBefore) });
			observations.add(new String[] { "Hiv period", App.get(hivBefore_yes) });
			observations.add(new String[] { "Hiv disclose", App.get(hivDisclose) });
			/*if (hivResult.getSelectedItem().toString()
					.equals(getResources().getString(R.string.yes))) {
			
			 */
			observations.add(new String[] { "Hiv result",App.get(hivResult) });
			observations.add(new String[] { "Under medication ",App.get(hivArt) });
			observations.add(new String[] { "Hiv test accept",App.get( hivTest) });
				
				
		// IF TB	
			observations.add(new String[] { "Phone1",App.get(phoneNumber1) });
			observations.add(new String[] { "Phone1 Owner",App.get(Phone1Owner) });
		// IF SOME ONE ELSE
			observations.add(new String[] { "Phone1 Owner",App.get(Phone1Someone) });
		
			observations.add(new String[] { "Phone2",App.get(phoneNumber2) });
		    observations.add(new String[] { "Phone2 Owner",App.get(Phone2Owner) });
		// IF SOME ONE ELSE
			observations.add(new String[] { "Phone2 Owner",App.get(Phone2Someone) });
			observations.add(new String[] { "Res address", App.get(resAddress) });
			observations.add(new String[] { "Land mark ",App.get(landmarkNear) });
			
			observations.add(new String[] { "TB Suspect",
					tbSuspect.isChecked() ? "Yes" : "No" });

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
					if (tbSuspect.isChecked())
						result = serverService.savePatientScreening(
								FormType.PAEDIATRIC_CONTACT_TRACING, values,
								observations.toArray(new String[][] {}));
					else
						result = serverService.saveNonSuspect(
								FormType.NON_SUSPECT, values);

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
						App.getAlertDialog(
								PatientScreeningActivity.this,
								AlertType.INFO,
								getResources().getString(R.string.inserted))
								.show();
						initView(views);
					} else {
						App.getAlertDialog(
								PatientScreeningActivity.this,
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
		} else if (requestCode == Barcode.BARCODE_RESULT_INDEX_ID) {
			if (resultCode == RESULT_OK) {
				String str = data.getStringExtra(Barcode.SCAN_RESULT);
				// Check for valid Id
				/*
				if (RegexUtil.isValidId(str)
						&& !RegexUtil.isNumeric(str, false)) {
					DateDelivery.setText(str);
				} else {
					App.getAlertDialog(
							this,
							AlertType.ERROR,
							DateDelivery.getTag().toString()
									+ ": "
									+ getResources().getString(
											R.string.invalid_data)).show();
				}
				
				*/
				
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

	
public void adult_visible()
{
	Labelsymp_adult.setVisibility(View.VISIBLE);
	coughDurationTextView.setVisibility(View.VISIBLE);
	coughDuration.setVisibility(View.VISIBLE);
	PersistantFeverTextView.setVisibility(View.VISIBLE);
	PersistantFever.setVisibility(View.VISIBLE);	
	adultWeightLossTextView.setVisibility(View.VISIBLE);
	adultWeightLoss.setVisibility(View.VISIBLE);
	adultWeightGainTextView.setVisibility(View.VISIBLE);
	adultWeightGain.setVisibility(View.VISIBLE);		
	adultChestPainTextView.setVisibility(View.VISIBLE);
	adultChestPain.setVisibility(View.VISIBLE);
	adultNightSweatsTextView.setVisibility(View.VISIBLE);
	adultNightSweats.setVisibility(View.VISIBLE);
}

public void adult_visible_not()
{
	Labelsymp_adult.setVisibility(View.GONE);
	coughDurationTextView.setVisibility(View.GONE);
	coughDuration.setVisibility(View.GONE);
	PersistantFeverTextView.setVisibility(View.GONE);
	PersistantFever.setVisibility(View.GONE);	
	adultWeightLossTextView.setVisibility(View.GONE);
	adultWeightLoss.setVisibility(View.GONE);
	adultWeightGainTextView.setVisibility(View.GONE);
	adultWeightGain.setVisibility(View.GONE);		
	adultChestPainTextView.setVisibility(View.GONE);
	adultChestPain.setVisibility(View.GONE);
	adultNightSweatsTextView.setVisibility(View.GONE);
	adultNightSweats.setVisibility(View.GONE);
}

	
public void child_visible()
{
	Labelsymp_child.setVisibility(View.VISIBLE);
	coughTextView.setVisibility(View.VISIBLE);
	cough.setVisibility(View.VISIBLE);
	feverTextView.setVisibility(View.VISIBLE);
	fever.setVisibility(View.VISIBLE);		
	weightLossTextView.setVisibility(View.VISIBLE);
	weightLoss.setVisibility(View.VISIBLE);
	childFatigueTextView.setVisibility(View.VISIBLE);
	childFatigue.setVisibility(View.VISIBLE);	
}

public void child_visible_not()
{
	Labelsymp_child.setVisibility(View.GONE);
	coughTextView.setVisibility(View.GONE);
	cough.setVisibility(View.GONE);
	feverTextView.setVisibility(View.GONE);
	fever.setVisibility(View.GONE);		
	weightLossTextView.setVisibility(View.GONE);
	weightLoss.setVisibility(View.GONE);
	childFatigueTextView.setVisibility(View.GONE);
	childFatigue.setVisibility(View.GONE);	
}
// Conctact Active
	
	
	public void contact_visible() {
		
		phoneNumber1TextView.setVisibility(View.VISIBLE);
		phoneNumber1.setVisibility(View.VISIBLE);
		 phoneNumber1.setVisibility(View.VISIBLE);
		 Phone1OwnerTextView.setVisibility(View.VISIBLE);
		 Phone1Owner.setVisibility(View.VISIBLE);
		// Phone1SomeoneTextView.setVisibility(View.VISIBLE);
		// Phone1Someone.setVisibility(View.VISIBLE);
		// Phone1Someone.setVisibility(View.VISIBLE);
		 
		 phoneNumber2TextView.setVisibility(View.VISIBLE);
		 phoneNumber2.setVisibility(View.VISIBLE);
		 phoneNumber2.setVisibility(View.VISIBLE);
		 Phone2OwnerTextView.setVisibility(View.VISIBLE);
		 Phone2Owner.setVisibility(View.VISIBLE);
		// Phone2SomeoneTextView.setVisibility(View.VISIBLE);
		// Phone2Someone.setVisibility(View.VISIBLE);
		// Phone2Someone.setVisibility(View.VISIBLE);
		 
		 resAddressTextView.setVisibility(View.VISIBLE);
		 resAddress.setVisibility(View.VISIBLE);
		 resAddress.setVisibility(View.VISIBLE);
		 landmarkNearTextView.setVisibility(View.VISIBLE);
		 landmarkNear.setVisibility(View.VISIBLE);
		 landmarkNear.setVisibility(View.VISIBLE);
		 
		 Labelcontact.setVisibility(View.GONE); 
		 Labeladdress.setVisibility(View.GONE);
		// TB SUSPECT
		 
		 tbSuspect.setChecked(true);
			patientIdTextView.setEnabled(true);
			patientId.setEnabled(true);
			scanBarcode.setEnabled(true);
		
	}
	
	public void contact_visible_not() {
		 phoneNumber1TextView.setVisibility(View.GONE);
			phoneNumber1.setVisibility(View.GONE);
			 phoneNumber1.setVisibility(View.GONE);
			 Phone1OwnerTextView.setVisibility(View.GONE);
			 Phone1Owner.setVisibility(View.GONE);
			 Phone1SomeoneTextView.setVisibility(View.GONE);
			 Phone1Someone.setVisibility(View.GONE);
			 Phone1Someone.setVisibility(View.GONE);
			 
			 phoneNumber2TextView.setVisibility(View.GONE);
			 phoneNumber2.setVisibility(View.GONE);
			 phoneNumber2.setVisibility(View.GONE);
			 Phone2OwnerTextView.setVisibility(View.GONE);
			 Phone2Owner.setVisibility(View.GONE);
			 Phone2SomeoneTextView.setVisibility(View.GONE);
			 Phone2Someone.setVisibility(View.GONE);
			 Phone2Someone.setVisibility(View.GONE);
			 
			 resAddressTextView.setVisibility(View.GONE);
			 resAddress.setVisibility(View.GONE);
			 resAddress.setVisibility(View.GONE);
			 landmarkNearTextView.setVisibility(View.GONE);
			 landmarkNear.setVisibility(View.GONE);
			 landmarkNear.setVisibility(View.GONE);
			 
			// TB SUSPECT
			 Labelcontact.setVisibility(View.VISIBLE);
			 Labeladdress.setVisibility(View.VISIBLE);
			 
			 tbSuspect.setChecked(false);
				patientIdTextView.setEnabled(false);
				patientId.setEnabled(false);
				scanBarcode.setEnabled(false);
	}
	@Override
	public boolean onEditorAction(TextView v, int arg1, KeyEvent arg2) {
		
		StringBuffer message = new StringBuffer();
		
		if (v == age) {
	

		}
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.text.method.KeyListener#clearMetaKeyState(android.view.View,
	 * android.text.Editable, int)
	 */
	@Override
	public void clearMetaKeyState(View view, Editable content, int states) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.text.method.KeyListener#getInputType()
	 */
	@Override
	public int getInputType() {
		// TODO Auto-generated method stub
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.text.method.KeyListener#onKeyDown(android.view.View,
	 * android.text.Editable, int, android.view.KeyEvent)
	 */
	@Override
	public boolean onKeyDown(View view, Editable text, int keyCode,
			KeyEvent event) {
		// TODO Auto-generated method stub
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.text.method.KeyListener#onKeyOther(android.view.View,
	 * android.text.Editable, android.view.KeyEvent)
	 */
	@Override
	public boolean onKeyOther(View view, Editable text, KeyEvent event) {
		// TODO Auto-generated method stub
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.text.method.KeyListener#onKeyUp(android.view.View,
	 * android.text.Editable, int, android.view.KeyEvent)
	 */
	@Override
	public boolean onKeyUp(View view, Editable text, int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		return false;
	}

}
