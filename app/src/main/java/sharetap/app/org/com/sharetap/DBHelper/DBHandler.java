package sharetap.app.org.com.sharetap.DBHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

import sharetap.app.org.com.sharetap.AppConstants;
import sharetap.app.org.com.sharetap.CustomJSONUtil;

public class DBHandler extends SQLiteOpenHelper {

    public final static String DATABASE_NAME = "ShareTap";
    public final static int DATABASE_VERSION = 1;
    public final static String TABLE_NAME = "OtherUserDetails";
    public final static String TABLE_PRIMARY_KEY = "DetailsId";
    public final static String TABLE_USER_MAIL = "UserMail";
    public final static String TABLE_TIMESTAMP = "LastUpdated";
    public final static String TABLE_DETAILS = "UserDetails";

    public DBHandler(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_MEETING_TABLE = "CREATE TABLE "   + TABLE_NAME + "("
                + TABLE_PRIMARY_KEY + " INTEGER PRIMARY KEY AUTOINCREMENT," + TABLE_USER_MAIL+ " TEXT," +TABLE_DETAILS+ " TEXT,"+
                TABLE_TIMESTAMP+ " TIMESTAMP DEFAULT CURRENT_TIMESTAMP)";
        sqLiteDatabase.execSQL(CREATE_MEETING_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
//        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS"+TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public long addOrUpdateScannedItem(ScannedUserDetails details) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TABLE_USER_MAIL, details.getUserMail());
        contentValues.put(TABLE_DETAILS, details.getUserDetails());
        long output;
        if (getUserDetailsByMail(details.getUserMail()).getUserMail().equalsIgnoreCase("a@a.com")) {
            output = sqLiteDatabase.insert(TABLE_NAME, null, contentValues);
            Log.i(AppConstants.LOGGER_CONSTANT, "DBHandler : Value is being inserted");
        } else {
            output = sqLiteDatabase.update(TABLE_NAME, contentValues, TABLE_USER_MAIL + " like '" + details.getUserMail() + "'", null);
            Log.i(AppConstants.LOGGER_CONSTANT, "DBHandler : Value is being updated");
        }
        sqLiteDatabase.close();
        return output;
    }

    public ArrayList<ScannedUserDetails> getAllUserDetails() {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cr = sqLiteDatabase.query(TABLE_NAME, new String[]{TABLE_DETAILS, TABLE_TIMESTAMP}, null, null, null, null, TABLE_TIMESTAMP + " DESC", null);
        ArrayList<ScannedUserDetails> myList = new ArrayList();
        cr.moveToFirst();
        do {
            String details = cr.getString(0);
            myList.add(new ScannedUserDetails(CustomJSONUtil.getUtil().getMail(details), details));
        } while (cr.moveToNext());
        cr.close();
        sqLiteDatabase.close();
        return myList;
    }

    public boolean isUserDetailsAvailable() {
        boolean result = false;
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cr = sqLiteDatabase.query(TABLE_NAME, new String[]{TABLE_DETAILS, TABLE_TIMESTAMP}, null, null, null, null, TABLE_TIMESTAMP, null);
        Log.i(AppConstants.LOGGER_CONSTANT, " The number of items in DB = " + cr.getCount());
        result = cr.getCount() > 0;
        cr.close();
        return result;
    }

    public ScannedUserDetails getUserDetailsByMail(String mail) {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cr = sqLiteDatabase.query(TABLE_NAME, new String[]{TABLE_DETAILS, TABLE_TIMESTAMP}, TABLE_USER_MAIL + " like  '" + mail + "'", null, null, null, TABLE_TIMESTAMP, null);
        if (cr.getCount() > 0) {
            cr.moveToFirst();
            String details = cr.getString(0);
            cr.close();
            return new ScannedUserDetails(CustomJSONUtil.getUtil().getMail(details), details);
        } else {
            cr.close();
            return new ScannedUserDetails("a@a.com", "Details go here");
        }
    }
}
