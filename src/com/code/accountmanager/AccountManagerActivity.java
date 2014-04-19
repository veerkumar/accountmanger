package com.code.accountmanager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.LinearLayout;

public class AccountManagerActivity extends Activity implements OnClickListener {
	ImageButton addAccount, addTransaction, recentTransaction, viewAccount;
	public static final String ROW_ID = "row_id";
	static long row_passed;
//static String ip = "";
	LinearLayout l1;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.main_screen);
		initiliseVariables();
	}

	private void initiliseVariables() {
		// TODO Auto-generated method stub
		//Bundle b = getIntent().getExtras();
		//ip = b.getString("IP");
		
		l1 = (LinearLayout) findViewById(R.id.layout_containing_buttons);

		Animation a = AnimationUtils.loadAnimation(AccountManagerActivity.this,
				R.anim.menu_animation);
		l1.setAnimation(a);

		addAccount = (ImageButton) findViewById(R.id.addAccount);
		addTransaction = (ImageButton) findViewById(R.id.addTransaction);
		recentTransaction = (ImageButton) findViewById(R.id.recentTransaction);
		viewAccount = (ImageButton) findViewById(R.id.viewAccount);

		addAccount.setOnClickListener(this);

		addTransaction.setOnClickListener(this);
		recentTransaction.setOnClickListener(this);
		viewAccount.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.addAccount: {

			Intent goToAddAccount = new Intent(AccountManagerActivity.this,
					AddnewAccount.class);
			startActivity(goToAddAccount);
			break;
		}

		case R.id.addTransaction:
			Intent goToAddTransaction = new Intent(AccountManagerActivity.this,
					AddTransaction.class);
			startActivity(goToAddTransaction);
			break;
		case R.id.recentTransaction:
			//Bundle c = new Bundle();
		//	c.putString("key", ip);
			Intent gotorecentTransaction = new Intent(
					AccountManagerActivity.this, RecentTransactions.class);
			//gotorecentTransaction.putExtras(c);
			startActivity(gotorecentTransaction);
			
						break;
		case R.id.viewAccount:
			
			Intent gotoviewAccount = new Intent(AccountManagerActivity.this,
					ViewAccounts.class);
			startActivity(gotoviewAccount);
	
						break;
		}

	}

}