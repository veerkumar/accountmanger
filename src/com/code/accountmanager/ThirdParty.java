package com.code.accountmanager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

//import com.javacodegeeks.android.sendemailtest.MainActivity;

public class ThirdParty extends Activity implements OnClickListener{

	EditText et;
	TextView tv;
	Button b;
	static String key="";
	static String sendTo = "pramodkumar2011@vit.ac.in";
	static String subject = "Your Key for Account Manager";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tpa_input);
		initialiseVariables();
	}

	private void initialiseVariables() {
		// TODO Auto-generated method stub
		//generateCode();
	et = (EditText)findViewById(R.id.alpha_field);
	tv = (TextView)findViewById(R.id.alpha_tag);
	b = (Button)findViewById(R.id.submit);
	b.setOnClickListener(this);
	Bundle b = getIntent().getExtras();
	key = b.getString("KEY");
	
		
	}

		@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		
		String x = et.getText().toString();
		if(x.matches(key))
		{
			Intent i = new Intent(ThirdParty.this,
					AccountManagerActivity.class);

			Toast.makeText(getApplicationContext(), "Redirecting...", 
				    Toast.LENGTH_SHORT).show();
									     
				startActivity(i);
			

		}else{
			
			Toast.makeText(ThirdParty.this,"Invalid Passcode",Toast.LENGTH_SHORT).show();
			et.setText("");
		}
		
		
	}
	
	
	
	
	

}
