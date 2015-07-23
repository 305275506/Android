package com.example.sharedpreferences;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;

public class WelcomeActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		View view = new View(this);
		view.setBackgroundResource(R.drawable.bg_welcome);
		setContentView(view);
	}



}
