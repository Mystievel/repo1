package com.example.tofer.emptyproject_helloworld;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class CannabisStrainDatabase_Helper extends SQLiteOpenHelper {

    // Database Version & Name
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "StrainDB";

    public CannabisStrainDatabase_Helper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // SQL statement to create book table
        String CREATE_BOOK_TABLE = "CREATE TABLE DATABASE_NAME ( " +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "title TEXT, "+
                "author TEXT )";

        // create books table
        db.execSQL(CREATE_BOOK_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older books table if existed
        db.execSQL("DROP TABLE IF EXISTS books");

        // create fresh books table
        this.onCreate(db);
    }
    //---------------------------------------------------------------------

    /**
     * CRUD operations (create "add", read "get", update, delete) book + get all books + delete all books
     */

    // Books table name
    private static final String TABLE_TITLE = "Strain Database";

    // Books Table Columns names
    private static final String STRAIN_ID = "id";
    private static final String COLUMN_1 = "title";
    private static final String COLUMN_2 = "author";

    private static final String[] COLUMNS = {STRAIN_ID, COLUMN_1, COLUMN_2};

    public void addStrainRow(CannabisStrainDatabase_Definition strainRow){
        Log.d("addStrainRow", strainRow.toString());
        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put(COLUMN_1, strainRow.getTitle()); // get title
        values.put(COLUMN_2, strainRow.getAuthor()); // get author
        // 3. insert
        db.insert(TABLE_TITLE, // table
                null, //nullColumnHack
                values); // key/value -> keys = column names/ values = column values
        // 4. close
        db.close();
    }

    public CannabisStrainDatabase_Definition getStrainData(int id){

        // 1. get reference to readable DB
        SQLiteDatabase db = this.getReadableDatabase();

        // 2. build query
        Cursor cursor =
                db.query(TABLE_TITLE, // a. table
                        COLUMNS, // b. column names
                        " id = ?", // c. selections
                        new String[] { String.valueOf(id) }, // d. selections args
                        null, // e. group by
                        null, // f. having
                        null, // g. order by
                        null); // h. limit

        // 3. if we got results get the first one
        if (cursor != null) {
            cursor.moveToFirst();
        }

        // 4. build book object
        CannabisStrainDatabase_Definition strainDatabase = new CannabisStrainDatabase_Definition();
        strainDatabase.setId(Integer.parseInt(cursor.getString(0)));
        strainDatabase.setTitle(cursor.getString(1));
        strainDatabase.setAuthor(cursor.getString(2));

        Log.d("getStrainData("+id+")", strainDatabase.toString());

        // 5. return book
        return strainDatabase;
    }
}