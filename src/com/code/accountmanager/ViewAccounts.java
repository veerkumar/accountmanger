package com.code.accountmanager;

import java.sql.SQLException;

import android.app.Activity;
import android.app.Dialog;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;


public class ViewAccounts extends Activity implements OnItemClickListener  {
	

	ListView list;
	public static int position_item;
	static long id_item_clicked;
	SimpleCursorAdapter myAdapter;
	DatabaseClass myDataHolder;
	static String AccountNumber = "";
	static String CustomerNumber = "";
	static String AccountHolder = "";
	static String BankName = "";
	static String BranchName = "";
	static String BranchAddress = "";
	static String Ifsc = "";
	static String Micr = "";
	static String CurrentBalance = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		//overridePendingTransition(R.anim.fadein, R.anim.fadeout);
		setContentView(R.layout.viewaccounts);
		initialiseVariables();
	}

	private void initialiseVariables() {
		// TODO Auto-generated method stub
		list = (ListView) findViewById(R.id.customerDetails);
		try {
			myDataHolder = new DatabaseClass(ViewAccounts.this);
		} catch (Exception e1) {
			e1.printStackTrace();
		}

		try {
			myDataHolder.open();
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
			
			// display the accounts
			// in a customised list view
			
			Cursor c = myDataHolder.getAllAccountDetalis();
			c.moveToFirst();
			myAdapter = new SimpleCursorAdapter(ViewAccounts.this,
					R.layout.detalholder, c, new String[] {
							DatabaseClass.KEY_BANK_NAME_ID,
							DatabaseClass.KEY_ACCOUNT_HOLDER_ID,
							DatabaseClass.KEY_CURRENT_BALANCE_ID }, new int[] {
							R.id.bankName, R.id.customerNameTag,
							R.id.customerBalanceTag },
					CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
			list.setAdapter(myAdapter);

			list.setOnItemClickListener(this);

			myDataHolder.close();

		}

	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub

		
		
		// each part on getting clicked 
		// should display the relevant 
		// account information
		

	}

}

