package com.example.tofer.emptyproject_helloworld;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

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
        String CREATE_DATABASE_TABLE = "CREATE TABLE strainDatabase ( " +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "strainName TEXT, "+
                "effectsRelaxed TEXT)";

        // create books table
        db.execSQL(CREATE_DATABASE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older strainDB table if existed
        db.execSQL("DROP TABLE IF EXISTS strainDatabase");

        // create fresh books table
        this.onCreate(db);
    }
    //---------------------------------------------------------------------

    /**
     * CRUD operations (create "add", read "get", update, delete) book + get all books + delete all books
     */

    // Books table name
    private static final String TABLE_TITLE = "StrainDatabase";

    // Books Table Columns names
    private static final String STRAIN_ID = "id";
    private static final String COLUMN_1 = "strainName";
    private static final String COLUMN_2 = "effects_Relaxed";

    private static final String[] COLUMNS = {STRAIN_ID, COLUMN_1, COLUMN_2};

    // Add a strain to the database
    public void addStrain(CannabisStrainDatabase_Definition strainRow){
        Log.d("addStrainRow", strainRow.toString());

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put(COLUMN_1, strainRow.getStrainName()); // get strain name
        values.put(COLUMN_2, strainRow.getEffectsRelaxed()); // get effect - relaxed

        // 3. insert
        db.insert(TABLE_TITLE, // table
                null, //nullColumnHack
                values); // key/value -> keys = column names/ values = column values

        // 4. close
        db.close();
    }

    // Get Strain Information by Database Row
    public CannabisStrainDatabase_Definition getStrainData(int id){
        // 1. get reference to readable DB
        SQLiteDatabase db = this.getReadableDatabase();

        // 2. build query
        Cursor cursor =
                db.query(TABLE_TITLE,                           // a. table
                        COLUMNS,                                // b. column names
                        " id = ?",                      // c. selections
                        new String[] { String.valueOf(id) },    // d. selections args
                        null,                           // e. group by
                        null,                           // f. having
                        null,                           // g. order by
                        null);                              // h. limit

        // 3. if we got results get the first one
        if (cursor != null) {
            cursor.moveToFirst();
        }

        // 4. build book object
        CannabisStrainDatabase_Definition strainInfo = new CannabisStrainDatabase_Definition();
        strainInfo.setId(Integer.parseInt(cursor.getString(0)));
        strainInfo.setStrainName(cursor.getString(1));
        strainInfo.setEffectsRelaxed(cursor.getDouble(2));

        Log.d("getStrainData("+id+")", strainInfo.toString());

        // 5. return strain information
        return strainInfo;
    }

    // Update Strain Information
    public int updateStrain(CannabisStrainDatabase_Definition strainInfo) {
        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put("strainName", strainInfo.getStrainName());
        values.put("effectsRelaxed", strainInfo.getEffectsRelaxed());

        // 3. updating row
        int i = db.update(TABLE_TITLE, values,STRAIN_ID+" = ?", new String[] {String.valueOf(strainInfo.getId())});

        // 4. close
        db.close();
        return i;
    }

    // Delete a Strain
    public void deleteStrain(CannabisStrainDatabase_Definition book) {
        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. delete
        db.delete(TABLE_TITLE,STRAIN_ID+" = ?", new String[] { String.valueOf(book.getId()) });

        // 3. close
        db.close();
        Log.d("deleteBook", book.toString());
    }
}