package com.code.accountmanager;

import java.sql.SQLException;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;



public class ViewTransactionInformation  extends Activity implements
OnClickListener  {


	TextView Bank_Name, Account_Number, Transaction_Type, Date_Of_Entry,
			Amount, Cheque_Number, Cheque_Party, Cheque_Details;
	EditText Bank_Name_Edit, Account_Number_Edit, Transaction_Type_Edit,
			Date_Of_Entry_Edit, Amount_Edit, Cheque_Number_Edit,
			Cheque_Party_Edit, Cheque_Details_Edit;
	ImageButton EditTransactionInformation, UpdateTransactionInformation;
	Bundle extras;
	long idReceived;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		setContentView(R.layout.complete_transaction_information);
		super.onCreate(savedInstanceState);
		initialiseVariables();
	}

	private void initialiseVariables() {
		// TODO Auto-generated method stub
		extras = getIntent().getExtras();
		Bank_Name = (TextView) findViewById(R.id.bank_Name_frmo_SpinnerEntry_Editable);
		Account_Number = (TextView) findViewById(R.id.account_number_Editable);
		Transaction_Type = (TextView) findViewById(R.id.transaction__type_Editable);
		Date_Of_Entry = (TextView) findViewById(R.id.date_Editable);
		Amount = (TextView) findViewById(R.id.amount_Editable);
		Cheque_Number = (TextView) findViewById(R.id.cheque_number_Editable);
		Cheque_Party = (TextView) findViewById(R.id.cheque_party_Editable);
		Cheque_Details = (TextView) findViewById(R.id.cheque_details_Editable);

		Bank_Name_Edit = (EditText) findViewById(R.id.BANK_NAME_FROM_SPINNER_ENTRY_Editable);
		Account_Number_Edit = (EditText) findViewById(R.id.ACCOUNT_NUMBER_Editable);
		Transaction_Type_Edit = (EditText) findViewById(R.id.TRANSACTION_TYP_Editable);
		Date_Of_Entry_Edit = (EditText) findViewById(R.id.DATE_Editable);
		Amount_Edit = (EditText) findViewById(R.id.AMOUNT_Editable);
		Cheque_Number_Edit = (EditText) findViewById(R.id.CHEQUE_NUMBER_Editable);
		Cheque_Party_Edit = (EditText) findViewById(R.id.CHEQUE_PARTY_Editable);
		Cheque_Details_Edit = (EditText) findViewById(R.id.CHEQUE_DETAILS_Editable);

		idReceived = extras.getLong("ID");

		Bank_Name_Edit.setText(RecentTransactions.Spinner_Entry);
		Account_Number_Edit.setText(RecentTransactions.Account_Number);
		Transaction_Type_Edit.setText(RecentTransactions.Transaction_Type);
		Date_Of_Entry_Edit.setText(RecentTransactions.Date);
		Amount_Edit.setText(RecentTransactions.Amount);
		Cheque_Number_Edit.setText(RecentTransactions.Cheque_Number);
		Cheque_Party_Edit.setText(RecentTransactions.Cheque_Party);
		Cheque_Details_Edit.setText(RecentTransactions.Cheque_Details);

		EditTransactionInformation = (ImageButton) findViewById(R.id.editTransactionInformation_Editable);
		UpdateTransactionInformation = (ImageButton) findViewById(R.id.updateTransaction_Editable);

		EditTransactionInformation.setOnClickListener(this);
		UpdateTransactionInformation.setOnClickListener(this);

		Bank_Name_Edit.setEnabled(false);
		Account_Number_Edit.setEnabled(false);
		Transaction_Type_Edit.setEnabled(false);
		Amount_Edit.setEnabled(false);
		Cheque_Number_Edit.setEnabled(false);
		Cheque_Party_Edit.setEnabled(false);
		Cheque_Details_Edit.setEnabled(false);

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.editTransactionInformation_Editable:

			Bank_Name_Edit.setEnabled(true);
			Account_Number_Edit.setEnabled(true);
			Transaction_Type_Edit.setEnabled(true);
			Amount_Edit.setEnabled(true);
			Cheque_Number_Edit.setEnabled(true);
			Cheque_Party_Edit.setEnabled(true);
			Cheque_Details_Edit.setEnabled(true);
			break;

		case R.id.updateTransaction_Editable:
			String bank = Bank_Name_Edit.getText().toString();
			String acc_num = Account_Number_Edit.getText().toString();
			String trans_type = Transaction_Type_Edit.getText().toString();
			String date = Date_Of_Entry_Edit.getText().toString();
			String amount = Amount_Edit.getText().toString();
			String cheque_num = Cheque_Number_Edit.getText().toString();
			String cheque_party = Cheque_Party_Edit.getText().toString();
			String cheque_det = Cheque_Details_Edit.getText().toString();

			DatabaseClass myupdates = new DatabaseClass(
					ViewTransactionInformation.this);

			try {
				myupdates.open();

				myupdates.updateTransactionContent(idReceived, bank, acc_num,
						trans_type, date, amount, cheque_num, cheque_party,
						cheque_det);
				myupdates.close();

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				Toast message = Toast.makeText(getApplication(),
						"Updated Successfully", Toast.LENGTH_SHORT);
				message.show();
				Intent gotomain = new Intent(ViewTransactionInformation.this,
						AccountManagerActivity.class);
				startActivity(gotomain);
			}

			break;

		}

	}
}

