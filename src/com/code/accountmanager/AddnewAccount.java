package com.code.accountmanager;

import java.util.ArrayList;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class AddnewAccount extends Activity implements OnClickListener {
	TextView account_number, customer_number, account_holder, bank_name,
			branch_name, branch_address, ifsc, micr, current_balance;
	EditText account_Number, customer_Number, account_Holder, bank_Name,
			branch_Name, branch_Address, Ifsc, Micr, current_Balance;
	ImageButton AddAccount;
	static String Account_no = "";
	DatabaseClass myDatabase;
	String txt;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		overridePendingTransition(R.anim.tansalate_in, R.anim.transalte_out);
		setContentView(R.layout.addaccount_new);
		initialiseVariables();
	}

	private void initialiseVariables() {
		// TODO Auto-generated method stub
		account_number = (TextView) findViewById(R.id.accountNumber);
		
		customer_number = (TextView) findViewById(R.id.customerNumber);
		account_holder = (TextView) findViewById(R.id.accountHolder);
		bank_name = (TextView) findViewById(R.id.bankName);
		branch_name = (TextView) findViewById(R.id.branchName);
		branch_address = (TextView) findViewById(R.id.branchAddress);
		ifsc = (TextView) findViewById(R.id.ifsc);// make the keyboard show only
													// numbers
		micr = (TextView) findViewById(R.id.micr);
		current_balance = (TextView) findViewById(R.id.currentBalance);

		account_Number = (EditText) findViewById(R.id.ACCOUNT_NUMBER);

		customer_Number = (EditText) findViewById(R.id.CUSTOMER_NUMBER);
		account_Holder = (EditText) findViewById(R.id.ACCOUNT_HOLDER);
		bank_Name = (EditText) findViewById(R.id.BANK_NAME);
		branch_Name = (EditText) findViewById(R.id.BRANCH_NAME);
		branch_Address = (EditText) findViewById(R.id.BRANCH_ADDRESS);
		Ifsc = (EditText) findViewById(R.id.IFSC);
		Micr = (EditText) findViewById(R.id.MICR);
		current_Balance = (EditText) findViewById(R.id.CURRENT_BALANCE);

		account_Number.setText(null);
		customer_Number.setText(null);
		account_Holder.setText(null);
		bank_Name.setText(null);
		branch_Name.setText(null);
		branch_Address.setText(null);
		Ifsc.setText(null);
		Micr.setText(null);
		current_Balance.setText(null);

		AddAccount = (ImageButton) findViewById(R.id.addAccount);
		AddAccount.setOnClickListener(this);

	}

	// only after all the fields have been filled, should the message data added
	// successfully be displayed.
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.addAccount:// try making the user enter in all the fields
			// boolean work = true;
			String accountNumber = account_Number.getText().toString();
//--------->			// now open the database
			// get all the account nos
			// store them in an array
			// match the account no entered with the , account nos stored in the array
			// if a match display already entered data
			
			//use DatabaseClass.Account_no here to retrieve the account details
			
			myDatabase = new DatabaseClass(AddnewAccount.this);
			try{
				myDatabase.open();
				
			}catch(Exception e)
			{
				e.printStackTrace();
				String error = e.toString();
				Dialog d = new Dialog(this);
				d.setTitle("Error");
				TextView tv = new TextView(this);
				tv.setText(error);
				d.setContentView(tv);
				d.show();	
			} finally {
                String txt = "";
				 boolean x = true;
				txt = account_Number.getText().toString();
				
				Cursor c = myDatabase.getAccountNo();
				ArrayList<String> columnArray1 = new ArrayList<String>();
				if(c != null)
				{
					while(c.moveToNext())
					{
						columnArray1.add(c.getString(c.getColumnIndex(DatabaseClass.KEY_ACCOUNT_NUMBER_ID)));
					}
					
				}
				String[] accno = columnArray1.toArray(new String[columnArray1.size()]);
			//	Toast.makeText(getApplicationContext(), "" +Arrays.toString(accno) , Toast.LENGTH_LONG).show();
				
			
				for(int j = 0 ; j < accno.length ; j++)
				    {
				    	if(txt.equals(accno[j]))
				    	{
				    		
				    		x = false;
				    	}
				    }
				if(x == false)
				{
					Toast message2 = Toast.makeText(AddnewAccount.this,
							"Account Already Exists", Toast.LENGTH_LONG);
					message2.show();
					myDatabase.close();
					finish();
					
				}
				
				
				
				else{
					String customerNumber = customer_Number.getText().toString();

					String accountHolder = account_Holder.getText().toString();

					String bankName = bank_Name.getText().toString();
					
					String branchName = branch_Name.getText().toString();
					String branchAddress = branch_Address.getText().toString();

					String ifsc = Ifsc.getText().toString();
					String micr = Micr.getText().toString();
					String currentBalance = current_Balance.getText().toString();
					if (accountNumber.matches("") || customerNumber.matches("")
							|| accountHolder.matches("") || bankName.matches("")
							|| branchAddress.matches("") || ifsc.matches("")
							|| micr.matches("") || currentBalance.matches("")) {
						Toast message = Toast.makeText(AddnewAccount.this,
								"Please Fill All The Fields", Toast.LENGTH_LONG);
						message.show();

					}

					else {
						try {
							DatabaseClass entry = new DatabaseClass(AddnewAccount.this);
							entry.open();
							entry.createEntry(accountNumber, customerNumber,
									accountHolder, bankName, branchName, branchAddress,
									ifsc, micr, currentBalance);
							entry.close();
						} catch (Exception e) {

							// work = false;
							Dialog message = new Dialog(this);
							message.setTitle("Error");
							String error = e.toString();
							TextView tv = new TextView(this);
							tv.setText(error);
							message.setContentView(tv);

							message.show();

						} finally {
							Toast message = Toast.makeText(AddnewAccount.this,
									"Data Added Successfully", Toast.LENGTH_SHORT);
							message.show();
							Intent goBackHome = new Intent(AddnewAccount.this,
									AccountManagerActivity.class);
							startActivity(goBackHome);
							finish();

						}
					}
					break;
				}

			}
				}
				/*
				Cursor cursor = myDatabase.getAccountNo();
				cursor.moveToFirst();
				long x = myDatabase.no_Of_Rows();
				
				String account_no[] = new String[(int) (x-1)];
				int i = 1;
				String txt = "";
				
				txt = account_Number.getText().toString();
				if(cursor != null)
				{
					for(cursor.moveToFirst();cursor.isAfterLast();cursor.moveToNext())
					{
					account_no[i] = cursor.getString(cursor.getColumnIndex(DatabaseClass.KEY_ACCOUNT_NUMBER_ID));
			
					}
					
					
				}
				
				for(int p = 0; p < x; p++)
				{
				if(txt.matches(account_no[p]))
					
				{
					account_Number.setText("");
					
					Toast message2 = Toast.makeText(AddnewAccount.this,
							"Account Already Exists", Toast.LENGTH_LONG);
					
					break;
					
				}
				}
				*/
				
				
			/*	myAdapter = new SimpleCursorAdapter(AddTransaction.this,
						R.layout.spinnerlayout, cursor,
						new String[] { DatabaseClass.KEY_BANK_NAME_ID },
						new int[] { R.id.bankName },
						CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
				mySpinner.setAdapter(myAdapter);
*/
			//	get_datafrom_spinner = cursor.getString(cursor
				//		.getColumnIndex(DatabaseClass.KEY_BANK_NAME_ID));
				// Toast.makeText(AddTransaction.this, get_datafrom_spinner,
				// Toast.LENGTH_LONG).show();

				// mySpinner.setOnItemSelectedListener(this);

				
			}
			
			

			
}
