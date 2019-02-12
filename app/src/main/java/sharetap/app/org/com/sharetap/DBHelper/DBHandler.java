package sharetap.app.org.com.sharetap.DBHelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

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
}
