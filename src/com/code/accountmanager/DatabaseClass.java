package com.code.accountmanager;

import java.sql.SQLException;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseClass {
	public static final String KEY_ROW_ID = "_id";
	public static final String KEY_ACCOUNT_NUMBER_ID = "database_account_number";
	public static final String KEY_CUSTOMER_NUMBER_ID = "database_customer_number";
	public static final String KEY_ACCOUNT_HOLDER_ID = "database_account_holder";
	public static final String KEY_BANK_NAME_ID = "database_bank_name";
	public static final String KEY_BRANCH_NAME_ID = "database_branch_name";
	public static final String KEY_BRANCH_ADDRESS_ID = "database_branch_address";
	public static final String KEY_IFSC_ID = "database_ifsc";
	public static final String KEY_MICR_ID = "database_micr";
	public static final String KEY_CURRENT_BALANCE_ID = "database_current_balance";

	public static final String KEY_SPINNER_ENTRY_ID = "spinner_entries";
	public static final String KEY_TRANSACTION_TYPE_ID = "transaction_entry";
	public static final String KEY_DATE_ENTERED_ID = "date_entry";
	public static final String KEY_AMOUNT_ID = "amount_entered";
	public static final String KEY_CHEQUE_NUMBER_ID = "cheque_number_entered";
	public static final String KEY_CHEQUE_PARTY_ID = "cheque_party_entered";
	public static final String KEY_CHEQUE_DETAILS_ID = "cheque_details_entered";
	
	//public static final String KEY_SERVER_IP = "server_ip";

	private static final String DATABASE_NAME = "DataHolder";
	private static final String DATABASE_TABLE_ACCOUNT = "AccountManager_Table";
	private static final String DATABASE_TABLE_TRANSACTION = "TransationManager_Table";
	//private static final String DATABASE_TABLE_SERVER_IP = "Server_IP_Table";

	private static final int DATABASE_VERSION = 1;

	private static String WHERE = "KEY_BANK_NAME_ID like ?";

	private HoldData myData;

	private final Context myContext;
	private SQLiteDatabase myDataBase;
	private SQLiteDatabase myDatabaseforTransactions;

	private static class HoldData extends SQLiteOpenHelper {

		public HoldData(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
			// TODO Auto-generated constructor stub
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			// TODO Auto-generated method stub
			db.execSQL("CREATE TABLE " + DATABASE_TABLE_ACCOUNT + " ("
					+ KEY_ROW_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
					+ KEY_ACCOUNT_NUMBER_ID + " TEXT, "
					+ KEY_CUSTOMER_NUMBER_ID + " TEXT, "
					+ KEY_ACCOUNT_HOLDER_ID + " TEXT, " + KEY_BANK_NAME_ID
					+ " TEXT, " + KEY_BRANCH_NAME_ID + " TEXT, "
					+ KEY_BRANCH_ADDRESS_ID + " TEXT, " + KEY_IFSC_ID
					+ " TEXT, " + KEY_MICR_ID + " TEXT, "
					+ KEY_CURRENT_BALANCE_ID + " TEXT, " + KEY_SPINNER_ENTRY_ID
					+ " TEXT, " + KEY_TRANSACTION_TYPE_ID + " TEXT, "
					+ KEY_DATE_ENTERED_ID + " TEXT, " + KEY_AMOUNT_ID
					+ " TEXT, " + KEY_CHEQUE_NUMBER_ID + " TEXT, "
					+ KEY_CHEQUE_PARTY_ID + " TEXT, " + KEY_CHEQUE_DETAILS_ID
					+ " TEXT);");

			db.execSQL("CREATE TABLE " + DATABASE_TABLE_TRANSACTION + " ("
					+ KEY_ROW_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
					+ KEY_SPINNER_ENTRY_ID + " TEXT, " + KEY_ACCOUNT_NUMBER_ID
					+ " TEXT, " + KEY_TRANSACTION_TYPE_ID + " TEXT, "
					+ KEY_DATE_ENTERED_ID + " TEXT, " + KEY_AMOUNT_ID
					+ " TEXT, " + KEY_CHEQUE_NUMBER_ID + " TEXT, "
					+ KEY_CHEQUE_PARTY_ID + " TEXT, " + KEY_CHEQUE_DETAILS_ID
					+ " TEXT);");
			
		//	db.execSQL("CREATE TABLE " + DATABASE_TABLE_SERVER_IP + " (" + KEY_SERVER_IP + " TEXT);");
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// TODO Auto-generated method stub
			db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE_ACCOUNT);
			db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE_TRANSACTION);
			//db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE_SERVER_IP);
			onCreate(db);

		}
	}

	public DatabaseClass(Context c) {
		myContext = c;
	}

	public DatabaseClass open() throws SQLException {
		myData = new HoldData(myContext);
		myDataBase = myData.getWritableDatabase();
		return this;

	}

	public void close() {
		myData.close();
	}

	// create entry next
	public long createEntry(String accountNumber, String customerNumber,
			String accountHolder, String bankName, String branchName,
			String branchAddress, String ifsc, String micr,
			String currentBalance) {
		ContentValues cv = new ContentValues();
		cv.put(KEY_ACCOUNT_NUMBER_ID, accountNumber);
		cv.put(KEY_CUSTOMER_NUMBER_ID, customerNumber);
		cv.put(KEY_ACCOUNT_HOLDER_ID, accountHolder);
		cv.put(KEY_BANK_NAME_ID, bankName);
		cv.put(KEY_BRANCH_NAME_ID, branchName);
		cv.put(KEY_BRANCH_ADDRESS_ID, branchAddress);
		cv.put(KEY_IFSC_ID, ifsc);
		cv.put(KEY_MICR_ID, micr);
		cv.put(KEY_CURRENT_BALANCE_ID, currentBalance);
		return myDataBase.insert(DATABASE_TABLE_ACCOUNT, null, cv);

	}

	public long createTransaction(String checkSpinnerentry,
			String accountNumber, String transactionType,
			String getDate_from_date_setter, String amount,
			String chequeNumber, String chequePary, String chequeDetails) {

		// TODO Auto-generated method stub
		ContentValues insertTransaction = new ContentValues();
		insertTransaction.put(KEY_SPINNER_ENTRY_ID, checkSpinnerentry);
		insertTransaction.put(KEY_ACCOUNT_NUMBER_ID, accountNumber);
		insertTransaction.put(KEY_TRANSACTION_TYPE_ID, transactionType);
		insertTransaction.put(KEY_DATE_ENTERED_ID, getDate_from_date_setter);
		insertTransaction.put(KEY_AMOUNT_ID, amount);
		insertTransaction.put(KEY_CHEQUE_NUMBER_ID, chequeNumber);
		insertTransaction.put(KEY_CHEQUE_PARTY_ID, chequePary);
		insertTransaction.put(KEY_CHEQUE_DETAILS_ID, chequeDetails);
		return myDataBase.insert(DATABASE_TABLE_TRANSACTION, null,
				insertTransaction);
	}

/*	public long saveIP(String ip)
	{
		ContentValues insertIP = new ContentValues();
		insertIP.put(KEY_SERVER_IP,ip);
		return myDataBase.insert(DATABASE_TABLE_SERVER_IP, null, insertIP);
	}*/
	public void deleteContent(long id) {
		try {
			open();
			myDataBase.delete(DATABASE_TABLE_ACCOUNT, KEY_ROW_ID + "=" + id,
					null);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			close();
		}

	}

	public void updateContent(long rowId, String accountNumber,
			String accountHolder, String customerNumber, String bankName,
			String branchName, String branchAddress, String ifsc, String micr,
			String updatedBalance) {
		ContentValues contentUpdate = new ContentValues();
		contentUpdate.put(KEY_ACCOUNT_NUMBER_ID, accountNumber);
		contentUpdate.put(KEY_ACCOUNT_HOLDER_ID, accountHolder);
		contentUpdate.put(KEY_CUSTOMER_NUMBER_ID, customerNumber);
		contentUpdate.put(KEY_BANK_NAME_ID, bankName);
		contentUpdate.put(KEY_BRANCH_NAME_ID, branchName);
		contentUpdate.put(KEY_BRANCH_ADDRESS_ID, branchAddress);
		contentUpdate.put(KEY_IFSC_ID, ifsc);
		contentUpdate.put(KEY_MICR_ID, micr);
		contentUpdate.put(KEY_CURRENT_BALANCE_ID, updatedBalance);
		myDataBase.update(DATABASE_TABLE_ACCOUNT, contentUpdate, KEY_ROW_ID
				+ "=" + rowId, null);
	}

	public void updateTransactionContent(long rowId, String spinnerEntry,
			String accountNumber, String transactionType, String dateEntered,
			String amountEntered, String chequeNumber, String chequeParty,
			String chequeDetails) {
		ContentValues contentUpdate = new ContentValues();
		contentUpdate.put(KEY_SPINNER_ENTRY_ID, spinnerEntry);
		contentUpdate.put(KEY_ACCOUNT_NUMBER_ID, accountNumber);
		contentUpdate.put(KEY_TRANSACTION_TYPE_ID, transactionType);
		contentUpdate.put(KEY_DATE_ENTERED_ID, dateEntered);
		contentUpdate.put(KEY_AMOUNT_ID, amountEntered);
		contentUpdate.put(KEY_CHEQUE_NUMBER_ID, chequeNumber);
		contentUpdate.put(KEY_CHEQUE_PARTY_ID, chequeParty);
		contentUpdate.put(KEY_CHEQUE_DETAILS_ID, chequeDetails);

		myDataBase.update(DATABASE_TABLE_TRANSACTION, contentUpdate, KEY_ROW_ID
				+ "=" + rowId, null);
	}

	// data is returned for list view containing accounts
	public Cursor getAllAccountDetalis() {
		// TODO Auto-generated method stub

		String[] coloumns = new String[] { KEY_ROW_ID, KEY_ACCOUNT_NUMBER_ID,
				KEY_CUSTOMER_NUMBER_ID, KEY_ACCOUNT_HOLDER_ID,
				KEY_BANK_NAME_ID, KEY_BRANCH_NAME_ID, KEY_BRANCH_ADDRESS_ID,
				KEY_IFSC_ID, KEY_MICR_ID, KEY_CURRENT_BALANCE_ID,
				KEY_SPINNER_ENTRY_ID, KEY_TRANSACTION_TYPE_ID,
				KEY_DATE_ENTERED_ID, KEY_AMOUNT_ID, KEY_CHEQUE_NUMBER_ID,
				KEY_CHEQUE_PARTY_ID, KEY_CHEQUE_DETAILS_ID };

		Cursor c = myDataBase.query(DATABASE_TABLE_ACCOUNT, coloumns, null,
				null, null, null, null);

		c.moveToFirst();

		return c;
	}

	public Cursor getAllTransactionDetails() {
		String[] coloumns = new String[] { DatabaseClass.KEY_ROW_ID,
				DatabaseClass.KEY_SPINNER_ENTRY_ID,
				DatabaseClass.KEY_ACCOUNT_NUMBER_ID,
				DatabaseClass.KEY_TRANSACTION_TYPE_ID,
				DatabaseClass.KEY_DATE_ENTERED_ID, DatabaseClass.KEY_AMOUNT_ID,
				DatabaseClass.KEY_CHEQUE_NUMBER_ID,
				DatabaseClass.KEY_CHEQUE_PARTY_ID,
				DatabaseClass.KEY_CHEQUE_DETAILS_ID };
		Cursor c = myDataBase.query(DATABASE_TABLE_TRANSACTION, coloumns, null,
				null, null, null, null);
		
		
		
		
		c.moveToFirst();
		return c;
		// TODO Auto-generated method stub

	}

	// data is returned for spinner here
	// get the data from here, pass it to spinner and then put it into
	// transaction table
	// along with other values
	
	public Cursor getAccountNo(){
		return myDataBase.query(DATABASE_TABLE_ACCOUNT, new String[]{DatabaseClass.KEY_ACCOUNT_NUMBER_ID}, null, null, null, null, null);
	}
	
	public Cursor getData() 
	{
	return myDataBase.query(DATABASE_TABLE_ACCOUNT, new String[] {
	DatabaseClass.KEY_ROW_ID, DatabaseClass.KEY_BANK_NAME_ID },
	null,null, DatabaseClass.KEY_BANK_NAME_ID, null, null);
	}
	
	public Cursor getAccountData(String bank) {

		return myDataBase.query(DATABASE_TABLE_ACCOUNT, new String[] {
				DatabaseClass.KEY_ROW_ID, DatabaseClass.KEY_BANK_NAME_ID,
				DatabaseClass.KEY_ACCOUNT_NUMBER_ID }, KEY_BANK_NAME_ID + "=?",
				new String[] { bank }, null, null, null);

	}

	public Cursor getAmountfor_Account(String account_for_spiiner_account) {
		// Log.d("cgf", account_for_spiiner_account);
		return myDataBase.query(DATABASE_TABLE_ACCOUNT, new String[] {
				DatabaseClass.KEY_ROW_ID, DatabaseClass.KEY_ACCOUNT_NUMBER_ID,
				DatabaseClass.KEY_AMOUNT_ID, KEY_CURRENT_BALANCE_ID },
				KEY_ACCOUNT_NUMBER_ID + "=?",
				new String[] { account_for_spiiner_account }, null, null, null);

	}

	/*
	 * contentUpdate.put(KEY_ACCOUNT_HOLDER_ID, accountHolder);
	 * contentUpdate.put(KEY_CUSTOMER_NUMBER_ID, customerNumber);
	 * contentUpdate.put(KEY_BANK_NAME_ID, bankName);
	 * contentUpdate.put(KEY_BRANCH_NAME_ID, branchName);
	 * contentUpdate.put(KEY_BRANCH_ADDRESS_ID, branchAddress);
	 * contentUpdate.put(KEY_IFSC_ID, ifsc); contentUpdate.put(KEY_MICR_ID,
	 * micr); contentUpdate.put(KEY_CURRENT_BALANCE_ID, currentBalace);
	 */
	public Cursor getAllAccountDetalis_for_account(
			String account_number_selected) {
		// TODO Auto-generated method stub
		return myDataBase.query(DATABASE_TABLE_ACCOUNT, new String[] {
				DatabaseClass.KEY_ROW_ID, DatabaseClass.KEY_ACCOUNT_NUMBER_ID,
				DatabaseClass.KEY_ACCOUNT_HOLDER_ID,
				DatabaseClass.KEY_CUSTOMER_NUMBER_ID,
				DatabaseClass.KEY_BANK_NAME_ID,
				DatabaseClass.KEY_BRANCH_NAME_ID,
				DatabaseClass.KEY_BRANCH_ADDRESS_ID, DatabaseClass.KEY_IFSC_ID,
				DatabaseClass.KEY_MICR_ID, KEY_CURRENT_BALANCE_ID },
				KEY_ACCOUNT_NUMBER_ID + "=?",
				new String[] { account_number_selected }, null, null, null);
	}
}
