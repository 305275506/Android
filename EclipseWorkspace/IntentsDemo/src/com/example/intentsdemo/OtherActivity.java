package com.example.intentsdemo;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.widget.Toast;

public class OtherActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_other);
		Intent intent = getIntent();
		String name1 = intent.getStringExtra("name1");
		int value1 = intent.getIntExtra("value1", 0);
		
		Bundle bundle = intent.getExtras();
		String name2 = bundle.getString("name2");
		int value2 = bundle.getInt("value2");
		
		Toast.makeText(this, name1+" "+value1+" "+name2+" "+value2, Toast.LENGTH_LONG).show();
	}


}
