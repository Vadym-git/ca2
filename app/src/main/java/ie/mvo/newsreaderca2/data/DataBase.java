package ie.mvo.newsreaderca2.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DataBase extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "news_items.db";
    private static final int DATABASE_VERSION = 1;
    private static DataBase instance;
    public static final String TABLE_NAME = "news_items";
    private static final String ID = "id";
    private static final String TITLE = "title";
    private static final String DESCRIPTION = "description";
    private static final String CONTENT = "content";
    private static final String PUB_DATE = "pubDate";
    private static final String IMAGE_URL = "imageUrl";
    private static final String COUNTRY = "country";
    private static final String CATEGORY = "category";

    public static synchronized DataBase getInstance(Context context) {
        if (instance == null) {
            instance = new DataBase(context.getApplicationContext());
        }
        return instance;
    }

    private static final String SQL_CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    TITLE + " TEXT, " +
                    DESCRIPTION + " TEXT, " +
                    CONTENT + " TEXT, " +
                    PUB_DATE + " TEXT, " +
                    IMAGE_URL + " TEXT, " +
                    COUNTRY + " TEXT, " +
                    CATEGORY + " TEXT" +
                    ");";

    public DataBase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
