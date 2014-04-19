package com.code.accountmanager;

import java.sql.SQLException;
import java.util.Calendar;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.CursorAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @author shekhartanwar
 * 
 */
public class AddTransaction extends Activity implements OnClickListener {
	int Year;
	int Month;
	int Day;

	TextView account, account_number, amount, type, date, cheque_number,
			cheque_party, cheque_details, date_displayer;

	String amount_got = "";
	String accountNumber = "";
	String chequeNumber = "";
	String chequePary = "";
	String chequeDetails = "";
	String checkSpinnerentry = "";
	static int updated_balance = 0;
	static int amount_Stored = 0;
	Spinner mySpinner, mySpinner_for_account;
	static String getDate_from_date_setter = "";
	static String account_for_spiiner_account = "";
	static String balance_in_account = "";
	static String balance_for_spiner_entry = "";
	int amount_Entered = 0;
	static String account_number_selected = "";
	static String bank_name = "";
	static boolean flag = false;
	private static String get_datafrom_spinner = "";

	private static String WHERE = "";
	EditText Amount, AccountNumber, ChequeNumber, ChequeParty, ChequeDetails;
	ImageButton AddTransaction, setDate;
	RadioButton radioButtonDeposit;
	RadioButton radioButtonWithdraw;
	DatabaseClass myDatabase;

	SimpleCursorAdapter myAdapter;
	SimpleCursorAdapter myAdapter_for_account;

	DatePicker datePickerWidget;
	String transactionType = "";
	String item_selected_from_spinner = "";
	static final int DATE_DIALOG_ID = 999;
	String account_number_from_add_account = "";
	String amount_from_add_account = "";
	String amount_from_add_transaction = "";

	int amount_from_add_account_number = 0;
	int amount_from_add_transaction_number = 0;
	String account_number_from_add_transaction = "";
	int amount_to_update = 0;
	int item = 0;

	static String row_id = "";// <=-------
	static String balance_updated = "";
	static String account_holder = "";
	static String customer_number = "";
	static String branch_name = "";
	static String branch_address = "";
	static String ifsc = "";
	static String micr = "";
	static long row_id_updated = 0;
	TextView bankTag, acc_num_tag, date_Tag, cash_Tag, type_Tag;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		// overridePendingTransition(R.anim.fadein, R.anim.fadeout);
		setContentView(R.layout.add_transaction_new);
		getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		initialiseVariables();
		setDate();
	}

	private void initialiseVariables() {
		// TODO Auto-generated method stub
		// datePickerWidget = (DatePicker) findViewById(R.id.datePicker);

		type_Tag = (TextView) findViewById(R.id.TYPE);
		bankTag = (TextView) findViewById(R.id.BANK);
		acc_num_tag = (TextView) findViewById(R.id.ACCOUNT_NUMBER);
		date_Tag = (TextView) findViewById(R.id.DATE);
		cash_Tag = (TextView) findViewById(R.id.CHEQUE_CASH_DETAILS);

		account = (TextView) findViewById(R.id.account);
		account_number = (TextView) findViewById(R.id.account_number);
		type = (TextView) findViewById(R.id.accountType);
		date = (TextView) findViewById(R.id.showsDate);
		amount = (TextView) findViewById(R.id.amount);
		cheque_number = (TextView) findViewById(R.id.chequeNumber);
		cheque_party = (TextView) findViewById(R.id.chequeParty);
		cheque_details = (TextView) findViewById(R.id.chequeDetails);
		date_displayer = (TextView) findViewById(R.id.showsCurrentDate);
		setDate = (ImageButton) findViewById(R.id.dateSetter);

		mySpinner = (Spinner) findViewById(R.id.spinnershowDetails);
		mySpinner_for_account = (Spinner) findViewById(R.id.spinner_ACCOUNT_NUMBER);

		AddTransaction = (ImageButton) findViewById(R.id.addTransaction);

		Amount = (EditText) findViewById(R.id.AMOUNT);

		// AccountNumber = (EditText) findViewById(R.id.ACCOUNT_NUMBER);
		ChequeNumber = (EditText) findViewById(R.id.CHEQUE_NUMBER);
		ChequeParty = (EditText) findViewById(R.id.CHEQUE_PARTY);
		ChequeDetails = (EditText) findViewById(R.id.CHEQUE_DETAILS);
		radioButtonDeposit = (RadioButton) findViewById(R.id.deposit);
		radioButtonWithdraw = (RadioButton) findViewById(R.id.withdraw);
		// set the current date in the date displayer

		myDatabase = new DatabaseClass(AddTransaction.this);

		try {
			myDatabase.open();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			String error = e.toString();
			Dialog d = new Dialog(this);
			d.setTitle("Error");
			TextView tv = new TextView(this);
			tv.setText(error);
			d.setContentView(tv);
			d.show();

		} finally {
			Cursor cursor = myDatabase.getData();
			cursor.moveToFirst();
			myAdapter = new SimpleCursorAdapter(AddTransaction.this,
					R.layout.spinnerlayout, cursor,
					new String[] { DatabaseClass.KEY_BANK_NAME_ID },
					new int[] { R.id.bankName },
					CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
			mySpinner.setAdapter(myAdapter);

			get_datafrom_spinner = cursor.getString(cursor
					.getColumnIndex(DatabaseClass.KEY_BANK_NAME_ID));
			// Toast.makeText(AddTransaction.this, get_datafrom_spinner,
			// Toast.LENGTH_LONG).show();

			// mySpinner.setOnItemSelectedListener(this);

			myDatabase.close();
		}
		mySpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				try {
					myDatabase.open();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				int pos = position;
				Cursor cursor = (Cursor) myAdapter.getItem(pos);
				bank_name = cursor.getString(cursor
						.getColumnIndex(DatabaseClass.KEY_BANK_NAME_ID));
				Cursor c = myDatabase.getAccountData(bank_name);
				
				c.moveToFirst();

				myAdapter_for_account = new SimpleCursorAdapter(
						AddTransaction.this, R.layout.account_number_spinner,
						c,
						new String[] { DatabaseClass.KEY_ACCOUNT_NUMBER_ID },
						new int[] { R.id.accNum },
						CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);

				mySpinner_for_account.setAdapter(myAdapter_for_account);
				myDatabase.close();

			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}
		});

		mySpinner_for_account
				.setOnItemSelectedListener(new OnItemSelectedListener() {

					@Override
					public void onItemSelected(AdapterView<?> arg0, View arg1,
							int position, long arg3) {
						try {
							myDatabase.open();
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						int position_account = mySpinner_for_account
								.getSelectedItemPosition();

						Cursor cursor2 = (Cursor) myAdapter_for_account
								.getItem(position_account);

						// holds the account number for the spinner item
						// selected
						account_number_selected = cursor2.getString(cursor2
								.getColumnIndex(DatabaseClass.KEY_ACCOUNT_NUMBER_ID));

						// Toast.makeText(AddTransaction.this,
						// account_number_selected,
						// Toast.LENGTH_LONG).show();
						Cursor balance = myDatabase
								.getAmountfor_Account(account_number_selected);
						balance.moveToFirst();

						// get the balance as a string

						balance_in_account = balance.getString(balance // <--
								.getColumnIndex(DatabaseClass.KEY_CURRENT_BALANCE_ID));

						// Toast.makeText(AddTransaction.this,
						// balance_in_account,
						// Toast.LENGTH_SHORT).show();
						myDatabase.close();
					}

					@Override
					public void onNothingSelected(AdapterView<?> arg0) {
						// TODO Auto-generated method stub

					}
				});

		AddTransaction.setOnClickListener(this);
		setDate.setOnClickListener(this);

	}

	private void setDate() {
		// TODO Auto-generated method stub
		Calendar c = Calendar.getInstance();
		Day = c.get(Calendar.DAY_OF_MONTH);
		Month = c.get(Calendar.MONTH) + 1;
		Year = c.get(Calendar.YEAR);
		date_displayer.setText(String.valueOf(Day) + "/"
				+ String.valueOf(Month) + "/" + String.valueOf(Year));

	}

	@SuppressWarnings("deprecation")
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {

		case R.id.dateSetter: {

			showDialog(DATE_DIALOG_ID);
			break;

		}
		case R.id.addTransaction: {
			if (radioButtonDeposit.isChecked()) {
				transactionType = "Deposit";

			}
			if (radioButtonWithdraw.isChecked()) {
				transactionType = "Withdrawl";
			}

			// WHERE = get_datafrom_spinner;

			// improve this part <-- improved

			try {
				myDatabase.open();
				String amount_entered_in_EditTExt = Amount.getText().toString();
				amount_Entered = Integer.parseInt(amount_entered_in_EditTExt);
				amount_Stored = Integer.parseInt(balance_in_account);

				if (transactionType.matches("Withdrawl"))// <---- go here
				{

					if (amount_Entered < amount_Stored) {
						updated_balance = amount_Stored - amount_Entered;

					}
					if (amount_Entered > amount_Stored) {

						flag = true;

					}

				}
				if (transactionType.matches("Deposit")) {// correct
					updated_balance = amount_Entered + amount_Stored;

				}
				balance_updated = String.valueOf(updated_balance);

				Cursor all_details = myDatabase
						.getAllAccountDetalis_for_account(account_number_selected);
				all_details.moveToFirst();
				row_id = all_details.getString(all_details
						.getColumnIndex(DatabaseClass.KEY_ROW_ID));
				account_holder = all_details.getString(all_details
						.getColumnIndex(DatabaseClass.KEY_ACCOUNT_HOLDER_ID));
				customer_number = all_details.getString(all_details
						.getColumnIndex(DatabaseClass.KEY_CUSTOMER_NUMBER_ID));
				branch_name = all_details.getString(all_details
						.getColumnIndex(DatabaseClass.KEY_BRANCH_NAME_ID));
				branch_address = all_details.getString(all_details
						.getColumnIndex(DatabaseClass.KEY_BRANCH_ADDRESS_ID));
				ifsc = all_details.getString(all_details
						.getColumnIndex(DatabaseClass.KEY_IFSC_ID));
				micr = all_details.getString(all_details
						.getColumnIndex(DatabaseClass.KEY_MICR_ID));

				row_id_updated = Long.parseLong(row_id);

				amount_got = Amount.getText().toString();
				// accountNumber = AccountNumber.getText().toString();

				// Toast.makeText(AddTransaction.this, accountNumber,
				// Toast.LENGTH_SHORT).show();

				chequeNumber = ChequeNumber.getText().toString();
				chequePary = ChequeParty.getText().toString();
				chequeDetails = ChequeDetails.getText().toString();

				getDate_from_date_setter = date_displayer.getText().toString();

			} catch (Exception e) {

				
				e.printStackTrace();


			} finally {

				if (amount_got.matches("") || chequeNumber.matches("")
						|| chequePary.matches("")) {
					Toast.makeText(AddTransaction.this,
							"Please Fill All The Fields", Toast.LENGTH_LONG)
							.show();

				}

				if (flag) {

					Toast.makeText(AddTransaction.this,
							"Insufficient balance in Account",

							Toast.LENGTH_LONG).show();
					Amount.setText("");
				}

				else {

					myDatabase.updateContent(row_id_updated,
							account_number_selected, account_holder,
							customer_number, bank_name, branch_name,
							branch_address, ifsc, micr, balance_updated);

					myDatabase.createTransaction(bank_name,
							account_number_selected, transactionType,
							getDate_from_date_setter, amount_got, chequeNumber,
							chequePary, chequeDetails);
					myDatabase.close();
					Toast message2 = Toast.makeText(AddTransaction.this,
							"Transaction Successfull", Toast.LENGTH_SHORT);
					message2.show();

					Intent goBackHome = new Intent(AddTransaction.this,
							AccountManagerActivity.class);
					startActivity(goBackHome);
					finish();
				}

			}

		}
		}
	}

	@Override
	protected Dialog onCreateDialog(int id) { // TODO Auto-generated method
		switch (id) {
		case DATE_DIALOG_ID: // set date picker as current date
			return new DatePickerDialog(this, mDateSetListener, Year, Month,
					Day);

		}
		return null;
	}

	protected DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {

		@Override
		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {
			Year = year;
			Month = monthOfYear;
			Day = dayOfMonth;
			date_displayer.setText(new String(Day + "/" + (Month + 1) + "/"
					+ Year));

		}

	};
};
