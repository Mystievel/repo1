package com.example.tofer.emptyproject_helloworld;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
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
        String CREATE_DATABASE_TABLE = "CREATE TABLE " + DATABASE_NAME + "( " +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "strainName TEXT, "+
                "effectsRelaxed DOUBLE)";

        // create books table
        db.execSQL(CREATE_DATABASE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w("onUpgrade", "Upgrading database from version " + oldVersion + " to " + newVersion + ", which will destroy all old data");
        // Drop older strainDB table if existed
        db.execSQL("DROP TABLE IF EXISTS " + DATABASE_NAME);
        // Create new table
        onCreate(db);
    }
    //---------------------------------------------------------------------

    // Books table name
    private static final String TABLE_TITLE = "StrainDatabase";

    // Strain Database (Table) Columns names
    // These values MUST match the Strings in the db definition file.
    private static final String STRAIN_ID = "id";
    private static final String COLUMN_1 = "strainName";
    private static final String COLUMN_2 = "effectsRelaxed";

    private static final String[] COLUMNS = {STRAIN_ID, COLUMN_1, COLUMN_2};

    // Add a strain to the database
    // TODO: Why does this method return -1 all the time?
    public long addStrain(CannabisStrainDatabase_Definition strainInfo) {
        //Log.d("addStrainRow()", strainInfo.toString());   // Display all incoming data.
        //Log.d("strainName", strainInfo.getStrainName());
        //Log.d("effectsRelaxed", strainInfo.getEffectsRelaxed());

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        //values.put(STRAIN_ID, strainInfo.getStrainId()); // get strain name - says this line is not needed?
        values.put(COLUMN_1, strainInfo.getStrainName()); // get strain name
        values.put(COLUMN_2, strainInfo.getEffectsRelaxed()); // get effect - relaxed

        // 3. insert
        long id = db.insert(TABLE_TITLE,null, values);

        // 4. close
        db.close();

        // Return newly inserted row id.
        return id;
    }

    // Get Strain Information by Database Row
    public CannabisStrainDatabase_Definition getStrainData(int id){
        // 1. get reference to readable DB
        SQLiteDatabase db = this.getReadableDatabase();

        // 2. build query
        Cursor cursor = db.query(TABLE_TITLE,                    // a. table
                        COLUMNS,                                // b. column names
                        " id = ?",                   // c. selections
                        new String[] {String.valueOf(id)},      // d. selections args
                        null,                           // e. group by
                        null,                           // f. having
                        null,                           // g. order by
                        null);                              // h. limit

        // 3. if we got results get the first one
        if (cursor != null) {
            cursor.moveToFirst();
        }

        // 4. build strainInfo object
        CannabisStrainDatabase_Definition strainInfo = new CannabisStrainDatabase_Definition();
        strainInfo.setStrainId(Integer.parseInt(cursor.getString(0)));
        strainInfo.setStrainName(cursor.getString(1));
        strainInfo.setEffectsRelaxed(cursor.getDouble(2));

        //Log.d("getStrainData("+id+")", strainInfo.toString());

        // 5. return strain information
        return strainInfo;
    }

    // Update Strain Information
    public int updateStrain(CannabisStrainDatabase_Definition strainInfo) {
        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put(COLUMN_1, strainInfo.getStrainName());
        values.put(COLUMN_2, strainInfo.getEffectsRelaxed());

        // 3. updating row
        int i = db.update(TABLE_TITLE, values,STRAIN_ID + " = ?", new String[] {String.valueOf(strainInfo.getStrainId())});

        // 4. close
        db.close();
        return i;
    }

    // Delete a Strain
    public void deleteStrain(CannabisStrainDatabase_Definition strainInfo) {
        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. delete
        db.delete(TABLE_TITLE,STRAIN_ID+" = ?", new String[] { String.valueOf(strainInfo.getStrainId()) });

        // 3. close
        db.close();
        //Log.d("delete strainInfo", strainInfo.toString());
    }
}