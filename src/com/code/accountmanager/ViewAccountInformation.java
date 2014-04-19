package com.code.accountmanager;


import java.sql.SQLException;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;



public class ViewAccountInformation extends Activity implements OnClickListener  {

	TextView account_number_editable, customer_number_editable,
	account_holder_editable, bank_name_editable, branch_name_editable,
	branch_address_editable, ifsc_editable, micr_editable,
	current_balance_editable;
    EditText account_Number_editable, account_Holder_editable,
	customer_Number_editable, bank_Name_editable, branch_Name_editable,
	branch_Address_editable, Ifsc_editable, Micr_editable,
	current_Balance_editable;
// TextView customer_Number_editable;
    ImageButton EditAccountInformation, UpdateAccountInformation;
    Bundle extras;
    long idReceived;

@Override
protected void onCreate(Bundle savedInstanceState) {
// TODO Auto-generated method stub
super.onCreate(savedInstanceState);
setContentView(R.layout.completeaccountinformation2);

initialiseVariables();

}

private void initialiseVariables() {
// TODO Auto-generated method stub
extras = getIntent().getExtras();

EditAccountInformation = (ImageButton) findViewById(R.id.editAccountInformation_Editable);
UpdateAccountInformation = (ImageButton) findViewById(R.id.updateAccount_Editable);
EditAccountInformation.setOnClickListener(this);
UpdateAccountInformation.setOnClickListener(this);

branch_address_editable = (TextView) findViewById(R.id.branchName_Editable);
ifsc_editable = (TextView) findViewById(R.id.ifsc_Editable);
micr_editable = (TextView) findViewById(R.id.micr_Editable);
current_balance_editable = (TextView) findViewById(R.id.currentBalance_Editable);

account_Number_editable = (EditText) findViewById(R.id.ACCOUNT_NUMBER_Editable);
account_Holder_editable = (EditText) findViewById(R.id.ACCOUNT_HOLDER_Editable);
branch_Address_editable = (EditText) findViewById(R.id.BRANCH_ADDRESS_Editable);

Ifsc_editable = (EditText) findViewById(R.id.IFSC_Editable);
Micr_editable = (EditText) findViewById(R.id.MICR_Editable);
current_Balance_editable = (EditText) findViewById(R.id.CURRENT_BALANCE_Editable);

idReceived = 0;// <-- make it work, id of the row which is clicked 
current_Balance_editable.setEnabled(false);

// add the update now and save button in xml as well as in this activity

}

@Override
public void onClick(View v) {
// TODO Auto-generated method stub
switch (v.getId()) {
case R.id.editAccountInformation_Editable:
	account_Number_editable.setEnabled(true);
	account_Holder_editable.setEnabled(true);
	branch_Name_editable.setEnabled(true);
	branch_Address_editable.setEnabled(true);
	Ifsc_editable.setEnabled(true);
	Micr_editable.setEnabled(true);
	current_Balance_editable.setEnabled(true);
	break;

case R.id.updateAccount_Editable:
	String account_number_updated = account_Number_editable.getText()
			.toString();
	String account_holder_updated = account_Holder_editable.getText()
			.toString();
	String customer_number_updated = customer_Number_editable.getText()
			.toString();
	String bank_name_updated = bank_Name_editable.getText().toString();
	String branch_name_updated = branch_Name_editable.getText()
			.toString();
	String branch_address_updated = branch_Address_editable.getText()
			.toString();
	String ifsc_updated = Ifsc_editable.getText().toString();
	String micr_updated = Micr_editable.getText().toString();
	String current_balance_updated = current_Balance_editable.getText()
			.toString();

	DatabaseClass myupdatedDatabase = new DatabaseClass(
			ViewAccountInformation.this);

	try {
		myupdatedDatabase.open();
		
		// get the row id and make
		// changes in that row of table
		
		myupdatedDatabase.close();
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} finally {
		
		
		
	}

	break;
}

}
}

