package com.tbi_id;

import java.util.HashMap;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class Step1Cause2 extends Activity {
	protected static int questionNum;
	protected static int count;
	final Context context = this;
	private EditText enterCause;
	String cause;

	private boolean click = true;
	private PopupWindow popupWindow;
	private View popupView;
	private View mainlayout;
	private View footer;
	private View header;
	private SharedPreferences sharedPrefs;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//remove action bar and notification bar
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		//set view from xml
		setContentView(R.layout.activity_step1_cause2);
		
		footer = findViewById(R.id.footer);
		header = findViewById(R.id.header);
		mainlayout = findViewById(R.id.main);
		
		final LayoutInflater layoutInflater = (LayoutInflater)getBaseContext()
				.getSystemService(LAYOUT_INFLATER_SERVICE);
		
		//About Button
		ImageButton aboutButton = (ImageButton) findViewById(R.id.about_button);
		aboutButton.setOnClickListener(new View.OnClickListener() {
			//open up the start interview activity if clicked
			@Override
			public void onClick(View v) {
				AlertDialog.Builder builder = new AlertDialog.Builder(context);
				builder.setTitle("Are you sure?");
				builder.setMessage("Are you sure you want to leave the interview (all progress will be lost)?");
				builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.cancel();
						Intent i = new Intent(getApplicationContext(), com.tbi_id.AboutActivity.class);
						startActivity(i);
					}
				});
				builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.cancel();
					}
				});
				AlertDialog alert = builder.create();
				alert.show();
			}
		});

		//Help Button
		final ImageButton helpButton = (ImageButton) findViewById(R.id.help_button);
		helpButton.setOnClickListener(new View.OnClickListener() {
			//open up the start interview activity if clicked
			@Override
			public void onClick(View v) {

				popupView = layoutInflater.inflate(R.layout.step1_help_popup_window, null);
				
				//check if the pop up settings window
				//is already being displayed
				IsClicked(helpButton);

				ImageButton btnDismiss = (ImageButton)popupView.findViewById(R.id.Quit_help_button);
				btnDismiss.setOnClickListener(new View.OnClickListener(){

					@Override
					public void onClick(View v) {
						click = true;
						popupWindow.dismiss();
					}});

				View stepOnelayout = findViewById(R.id.main);

				stepOnelayout.setOnClickListener(new View.OnClickListener(){

					@Override
					public void onClick(View v) {
						click = true;
						popupWindow.dismiss();
					}});

			}
		});	
		
		sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
		
		//Settings button
		ImageButton settingsButton = (ImageButton) findViewById(R.id.settings_button);
		//open up settings activity if the settings button is clicked
		settingsButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				popupView = layoutInflater.inflate(R.layout.settings_popup_window, null);

				//check if the pop up settings window
				//is already being displayed
				IsClicked(helpButton);

				//get checkbox
				final CheckBox checkBoxHipaa = (CheckBox) popupView.findViewById(R.id.hippaCompliance); 

				//a text block that tells user to enter their email address
				final TextView emailNotif = (TextView) popupView.findViewById(R.id.enterEmailNotif);

				//the input field for entering the email address
				final EditText enterEmailHipaa = (EditText) popupView.findViewById(R.id.emailEnterHipaa);

				UploadSavedSettings (enterEmailHipaa, emailNotif, checkBoxHipaa);

				checkBoxHipaa.setOnCheckedChangeListener(new OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

						SharedPreferences.Editor editor = sharedPrefs.edit();
						boolean checked = isChecked;
						editor.putBoolean("checkboxHipaa", checked);
						editor.apply();

						if (checked)
						{
							enterEmailHipaa.setVisibility(View.VISIBLE);
							emailNotif.setVisibility(View.VISIBLE);
						}
						else 
						{
							enterEmailHipaa.setVisibility(View.GONE);
							emailNotif.setVisibility(View.GONE);
						}	
					}
				});


				//get save button
				ImageButton saveButton = (ImageButton) popupView.findViewById(R.id.save_settings);
				saveButton.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {

						SharedPreferences.Editor editor = sharedPrefs.edit();
						boolean checked = checkBoxHipaa.isChecked();
						String email = enterEmailHipaa.getText().toString();
						editor.putString("emailHipaa", email);
						editor.putBoolean("checkboxHipaa", checked);
						editor.apply();

						WasEmailEnter (enterEmailHipaa);
					}
				});

				//get the X quit setting button
				ImageButton btnDismiss = (ImageButton)popupView.findViewById(R.id.Quit_help_button);
				btnDismiss.setOnClickListener(new View.OnClickListener(){

					@Override
					public void onClick(View v) {
						click = true;
						popupWindow.dismiss();
					}
				});
			}
		});
		
		//Home Button
		ImageButton homeButton = (ImageButton) findViewById(R.id.home_button_main_screen);
		//if the home button is clicked, send the user back to the home screen
		homeButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				AlertDialog.Builder builder = new AlertDialog.Builder(context);
				builder.setTitle("Are you sure?");
				builder.setMessage("Are you sure you want to leave the interview (all progress will be lost)?");
				builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.cancel();
						Intent i = new Intent(getApplicationContext(), com.tbi_id.MainActivity.class);
						startActivity(i);
					}
				});
				builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.cancel();
					}
				});
				AlertDialog alert = builder.create();
				alert.show();
			}
		});		
		
		//get passed data to save the cause with the appopriate question for later use
		Intent intent = getIntent();
		final Bundle b = intent.getExtras();
		questionNum = (Integer) b.get("questionNum");
		count = (Integer) b.get("causeCount");
		String causeN = "";
		StringBuilder causeappender = new StringBuilder();
		TextView causeValues = (TextView) findViewById(R.id.causeList);
		@SuppressWarnings("unchecked")
		final HashMap<String, String> data = (HashMap<String, String>) b.getSerializable("patientData");
		
		if (count > 0) {
			//for cause 1 to causeCount, append "cause" to "i"
			for (int i=1; i<=count; i++) {
				//data.get each causeN			
				causeN = data.get("cause" + i);	
				
				causeappender.append(causeN + "\n");
			}			
		}
		
		ImageButton add = (ImageButton) findViewById(R.id.addAnother);
		ImageButton done = (ImageButton) findViewById(R.id.addDone);
		
		done.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(getApplicationContext(),com.tbi_id.Step1Activity.class);
				b.putSerializable("patientData", data);
				b.putSerializable("questionNum", questionNum);
				b.putSerializable("causeCount", count);
				i.putExtras(b);
				startActivity(i);
		
			}
			
		});
		
		add.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(getApplicationContext(),com.tbi_id.AddAnother.class);
				b.putSerializable("patientData", data);
				b.putSerializable("questionNum", questionNum);
				b.putSerializable("causeCount", count);
				i.putExtras(b);
				startActivity(i);
				
			}
		});
		
		causeValues.setText(causeappender);
	}
	
	/*
	 * 
	 */
	private void IsClicked (View helpButton){
		
		if (click)
		{
			//calculate the space between the footer and header in the screen
			int heightSpace = mainlayout.getHeight() - (footer.getHeight() + header.getHeight());
			
			//get the screen width
			int widthSpace =  footer.getWidth(); 
			
			//x offset from the view helpButton left edge
			int xoff = header.getHeight()/4;
			//y offset from the view helpButton left edge
			int yoff =  header.getHeight()/3;
			
			//instantiate popupWindow
			popupWindow = new PopupWindow(
					popupView, android.view.ViewGroup.LayoutParams.WRAP_CONTENT, android.view.ViewGroup.LayoutParams.WRAP_CONTENT, true);
			popupWindow.showAsDropDown(helpButton, 50, -30);
			popupWindow.update(helpButton, xoff, yoff, widthSpace - 2*xoff, heightSpace - 2*yoff);
			popupWindow.setFocusable(true);
			
			click = false;
		}
		else {
			click = true;
			popupWindow.dismiss();
		}
	}

	
	
	/*
	 * Get and upload the settings saved as default.
	 */
	private void UploadSavedSettings (EditText enterEmailHipaa, TextView emailNotif, CheckBox checkBoxHipaa){
		
		//set the boolean false equal to the value of the checkbox when it was when previously run, if not found, set it to false
		Boolean checked = sharedPrefs.getBoolean("checkboxHipaa", false);
		// if the value was false, then they are not free from hipaa and cannot send the data so email is turned off
		if (checked == false) {
			enterEmailHipaa.setVisibility(View.GONE);
			emailNotif.setVisibility(View.GONE);
			checkBoxHipaa.setChecked(false);
		}
		else {
			String email = sharedPrefs.getString("emailHipaa", "Enter Email Here");
			enterEmailHipaa.setText(email);
			checkBoxHipaa.setChecked(true);
		}		
	}
	
	/*
	 * 
	 */
	private void WasEmailEnter (final EditText emailText){
		
		if((emailText.getText().length()==0) && (emailText.isShown()))
		{
			AlertDialog.Builder builder = new AlertDialog.Builder(context);
			builder.setTitle("Error");
			builder.setMessage("Please enter a valid email");
			builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					dialog.cancel();
					emailText.requestFocus();
				}
			});
			AlertDialog alert = builder.create();
			alert.show();
		}
		else{
			click = true;
			popupWindow.dismiss();
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.step1_cause, menu);
		return true;
	}

}
