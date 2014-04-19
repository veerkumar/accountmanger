package com.code.accountmanager;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.SQLException;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CursorAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class RecentTransactions extends Activity implements OnItemClickListener, OnClickListener
{
	String s;
	ListView listofTransactions;
	ImageButton sync;
	public static int position_item;
	static long id_item_clicked;
	SimpleCursorAdapter myAdapter;
	DatabaseClass myTransactionHolder;
	DatabaseClass myserver;
	static String Spinner_Entry = "";
	static String Account_Number = "";
	static String Transaction_Type = "";
	static String Date = "";
	static String Amount = "";
	static String Cheque_Number = "";
	static String Cheque_Party = "";
	static String Cheque_Details = "";
	static String received_ip;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		// overridePendingTransition(R.anim.fadein, R.anim.fadeout);
		setContentView(R.layout.recenttransactions);
		initialiseVariables();
		/*
		 * Bundle c = getIntent().getExtras();
		 * //Toast.makeText(RecentTransactions.this,c,
		 * Toast.LENGTH_LONG).show(); received_ip =c.getString("key");
		 * Toast.makeText(RecentTransactions.this,received_ip,
		 * Toast.LENGTH_LONG).show();
		 */}

	private void initialiseVariables()
	{
		// TODO Auto-generated method stub

		sync = (ImageButton) findViewById(R.id.syncTransaction);
		listofTransactions = (ListView) findViewById(R.id.listofTransactionsMade);
		// Toast.makeText(RecentTransactions.this,received_ip,
		// Toast.LENGTH_LONG).show();
		myTransactionHolder = new DatabaseClass(RecentTransactions.this);
		try
		{
			myTransactionHolder.open();
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			String error = e.toString();
			Dialog d = new Dialog(this);
			d.setTitle("Error");
			TextView tv = new TextView(this);
			tv.setText(error);
			d.setContentView(tv);
			d.show();
		}
		finally
		{
			Cursor c = myTransactionHolder.getAllTransactionDetails();
			c.moveToFirst();
			myAdapter = new SimpleCursorAdapter(RecentTransactions.this,
					R.layout.data_for_recent_transactions, c, new String[] {
							DatabaseClass.KEY_SPINNER_ENTRY_ID, DatabaseClass.KEY_DATE_ENTERED_ID,
							DatabaseClass.KEY_TRANSACTION_TYPE_ID }, new int[] {
							R.id.banknamefromDataBase, R.id.transactiondateFromDataBase,
							R.id.transactiontype },
					CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
			listofTransactions.setAdapter(myAdapter);
			sync.setOnClickListener(this);
			listofTransactions.setOnItemClickListener(this);
			myTransactionHolder.close();

		}

	}

	/*
	 * @Override public void onCreateContextMenu(ContextMenu menu, View v,
	 * ContextMenuInfo menuInfo) { // TODO Auto-generated method stub
	 * 
	 * super.onCreateContextMenu(menu, v, menuInfo); menu.add("DELETE"); }
	 * 
	 * @Override public boolean onContextItemSelected(MenuItem item) { // TODO
	 * Auto-generated method stub
	 * 
	 * if (item.getTitle() == "DELETE") { try { myTransactionHolder.open(); }
	 * catch (SQLException e) { // TODO Auto-generated catch block
	 * e.printStackTrace(); } AdapterView.AdapterContextMenuInfo info; try {
	 * info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo(); } catch
	 * (ClassCastException e) { return false; }
	 * 
	 * int rowid = 0; Cursor c = (Cursor) listofTransactions
	 * .getItemAtPosition(position_item); rowid =
	 * c.getInt(c.getColumnIndex(DatabaseClass.KEY_ROW_ID));
	 * myTransactionHolder.deleteContent(rowid);
	 * 
	 * myAdapter.notifyDataSetChanged(); myTransactionHolder.close();
	 * 
	 * Intent intent = null;
	 * 
	 * try { intent = new Intent(RecentTransactions.this,
	 * Class.forName("com.code.accountmanager"));
	 * intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK |
	 * Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NO_HISTORY);
	 * 
	 * } catch (ClassNotFoundException e) { // TODO Auto-generated catch block
	 * e.printStackTrace(); } finally { startActivity(intent); }
	 * 
	 * }
	 * 
	 * return super.onContextItemSelected(item); }
	 */
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3)
	{
		// TODO Auto-generated method stub

		int rowIdpassed = 0;
		Intent i = null;
		Cursor c = (Cursor) arg0.getItemAtPosition(arg2);
		rowIdpassed = c.getInt(c.getColumnIndex(DatabaseClass.KEY_ROW_ID));

		Spinner_Entry = c.getString(c.getColumnIndex(DatabaseClass.KEY_SPINNER_ENTRY_ID));

		Account_Number = c.getString(c.getColumnIndex(DatabaseClass.KEY_ACCOUNT_NUMBER_ID));
		Transaction_Type = c.getString(c.getColumnIndex(DatabaseClass.KEY_TRANSACTION_TYPE_ID));
		Date = c.getString(c.getColumnIndex(DatabaseClass.KEY_DATE_ENTERED_ID));
		Amount = c.getString(c.getColumnIndex(DatabaseClass.KEY_AMOUNT_ID));
		Cheque_Number = c.getString(c.getColumnIndex(DatabaseClass.KEY_CHEQUE_NUMBER_ID));
		Cheque_Party = c.getString(c.getColumnIndex(DatabaseClass.KEY_CHEQUE_PARTY_ID));
		Cheque_Details = c.getString(c.getColumnIndex(DatabaseClass.KEY_CHEQUE_DETAILS_ID));

		Bundle bundle = new Bundle();
		bundle.putLong("ID", rowIdpassed);

		try
		{
			i = new Intent(RecentTransactions.this, ViewTransactionInformation.class);
			i.putExtras(bundle);

		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			startActivity(i);
		}

	}

	@Override
	public void onClick(View v)
	{
		// TODO Auto-generated method stub
		switch (v.getId())
		{
			case R.id.syncTransaction:
				// postData();
				//Bundle b = getIntent().getExtras();
				//received_ip = b.getString("key");
				postData();
				// Toast.makeText(RecentTransactions.this, received_ip ,
				// Toast.LENGTH_LONG).show();
				break;
		}
	}

	public void postData()
	{
		String url = "http://www.krishnastockbrokers.com/shekhar/receive_request.php";
		NetworkAsyncTask obj = new NetworkAsyncTask();
		obj.execute(url);

	}

	private String convertStreamToString(InputStream inputStream)
	{
		// TODO Auto-generated method stub
		String line = "";
		StringBuilder total = new StringBuilder();
		BufferedReader rd = new BufferedReader(new InputStreamReader(inputStream));
		try
		{
			while ((line = rd.readLine()) != null)
			{
				total.append(line);
			}
		}
		catch (Exception e)
		{
			Toast.makeText(this, "Stream Exception", Toast.LENGTH_SHORT).show();
		}
		return total.toString();
	}

	class NetworkAsyncTask extends AsyncTask<String, Void, String>
	{

		@Override
		protected void onPostExecute(String result)
		{
			String array[] = result.split(":");
			updateList(array);
			//Toast.makeText(getApplicationContext(), array[0], Toast.LENGTH_LONG).show();
		}

		private void updateList(String[] array) {
			// TODO Auto-generated method stub
			Intent i= null;
			try {
				myTransactionHolder.open();
				myTransactionHolder.createTransaction(array[0], array[1], array[2],array[3],array[4],array[5],array[6], array[7]);
				Toast.makeText(getApplicationContext(), "updaste list ", Toast.LENGTH_SHORT).show();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally{
				
					try {
						 i = new Intent(RecentTransactions.this,Class.forName("com.code.accountmanager.RecentTransactions"));
					} catch (ClassNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}finally{
						startActivity(i);
					}
				
			}
			
			
		}

		@Override
		protected String doInBackground(String... params)
		{
			String url = params[0];
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost http = new HttpPost(url);

			try
			{
				HttpResponse response = httpclient.execute(http);

				if (response != null)
				{

					String line = "";
					InputStream inputStream = response.getEntity().getContent();
					line = convertStreamToString(inputStream);
					return line;
				}
			}
			catch (Exception e)
			{

			}
			return null;
		}

	}

}
