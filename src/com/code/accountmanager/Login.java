package com.code.accountmanager;


import java.util.UUID;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Login extends Activity{
	private EditText  username;
	   private EditText  password;
	 
	   private TextView attempts;
	   private Button login;
	   int counter = 3;
	   static String key="";
	   DatabaseClass myDatabase;
	   @Override
	   protected void onCreate(Bundle savedInstanceState) {
	      super.onCreate(savedInstanceState);
	      setContentView(R.layout.login);
	      username = (EditText)findViewById(R.id.editText1);
	      password = (EditText)findViewById(R.id.editText2);
	    //  serverip_field = (EditText)findViewById(R.id.editText3);
	      
	    //  serverip = (TextView)findViewById(R.id.serverip);
	      attempts = (TextView)findViewById(R.id.textView5);
	      attempts.setText(Integer.toString(counter));
	      login = (Button)findViewById(R.id.button1);
	      myDatabase = new DatabaseClass(Login.this);
	   }
	   
	   
	   public void login(View view){
		      if(username.getText().toString().equals("") && 
		      password.getText().toString().equals("")){
		    	//  String x = serverip_field.getText().toString();
		    	  Intent i = null;
		    	  Bundle bundle = new Bundle();
		  						try {
					i = new Intent(Login.this,
							ThirdParty.class);
					

				} catch (Exception e) {
					e.printStackTrace();
				} finally {

					Toast.makeText(getApplicationContext(), "Generating key", 
						    Toast.LENGTH_SHORT).show();
					key = generateKey();
					bundle.putString("KEY", key);
					i.putExtras(bundle);
					Thread thread = new Thread(new Runnable(){
					    @Override
					    public void run() {
					        try {
					            //Your code goes here
					        	GMailSender sender = new GMailSender("pramodkumar2011@vit.ac.in", "password");
			                    sender.sendMail("ACCOUNT MANAGER:Your Key To Login",   
			                            key,   
			                            "pramodkumar2011@vit.ac.in",   
			                            "pramodkumar2011@vit.ac.in");   
					        } catch (Exception e) {
					        	Log.e("SendMail", e.getMessage(), e);
					        }
					    }
					});

					thread.start(); 
					
					
					Toast.makeText(getApplicationContext(), "Redirecting...", 
						    Toast.LENGTH_SHORT).show();
								     
					startActivity(i);
				}

		    	  
		    	  
		    	  //Toast.makeText(getApplicationContext(), x,Toast.LENGTH_LONG).show();
		      
		     
		      
		   }	
		   else{
		      Toast.makeText(getApplicationContext(), "Wrong Credentials",
		      Toast.LENGTH_SHORT).show();
		      attempts.setBackgroundColor(Color.RED);	
		      counter--;
		      attempts.setText(Integer.toString(counter));
		      if(counter==0){
		         login.setEnabled(false);
		      }

		   }
	   }


	private String generateKey() {
		// TODO Auto-generated method stub
		String uuid = UUID.randomUUID().toString();
		key = uuid.substring(0,6);
		return key;
	}


}



