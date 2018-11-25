package com.example.tofer.emptyproject_helloworld;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class CannabisStrainDatabase_Helper extends SQLiteOpenHelper {
    // Database Version & Name
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "CannabisStrainDatabase";

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
        // Drop older database table if existed
        db.execSQL("DROP TABLE IF EXISTS " + DATABASE_NAME);
        // Create new table
        onCreate(db);
    }

    //------------------------------------------------------------------------------------------------------------------------------------------
    //------------------------------------------------------------------------------------------------------------------------------------------
    //------------------------------------------------------------------------------------------------------------------------------------------

    // Database table name
    private static final String ALL_STRAINS_TABLE_TITLE = "CannabisStrainDatabase";
    private static final String MY_STRAINS_TABLE_TITLE = "MyStrainsDatabase";

    // Strain Database (Table) Columns names
    // These values MUST match the Strings in the db definition file.
    private static final String _ID = "id";
    private static final String COLUMN_1 = "StrainName";
    private static final String COLUMN_T = "StrainType"; // Hybrid/sativa/indica
    private static final String COLUMN_2 = "Relaxed";
    private static final String COLUMN_3 = "Happy";
    private static final String COLUMN_4 = "Hungry";
    private static final String[] ALL_COLUMNS = {_ID, COLUMN_1, COLUMN_2, COLUMN_3, COLUMN_4};
	private static final String[] MY_COLUMNS = {_ID, COLUMN_1};

	//----------------------------------------------------------------------------------------------
	//----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------
    //      MY STRAINS TABLE                MY STRAINS TABLE                MY STRAINS TABLE
	//----------------------------------------------------------------------------------------------
	//----------------------------------------------------------------------------------------------
	//----------------------------------------------------------------------------------------------

    //**********************************************************************************************
    // Add a strain to the database.
    //**********************************************************************************************
    public long addToMyStrains(CannabisStrainDatabase_Definition strainInfo) {
        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        //values.put(STRAIN_ID, strainInfo.getStrainId()); 	// get strain name - says this line is not needed?
		values.put(_ID, strainInfo.getStrainId());			// get strain name
		values.put(COLUMN_1, strainInfo.getStrainName()); 	// get strain name

        // 3. insert
        long id = db.insert(MY_STRAINS_TABLE_TITLE,null, values);
        db.close();     // 4. close
        return id;      // Return newly inserted row id.
    } //********************************************************************************************


    //**********************************************************************************************
    // Get Strain Information by Database Row.
    //**********************************************************************************************
    public CannabisStrainDatabase_Definition getMyStrainsData(int id){
        // 1. get reference to readable DB
        SQLiteDatabase db = this.getReadableDatabase();

        // 2. build query
        Cursor cursor = db.query(MY_STRAINS_TABLE_TITLE,                    // a. table
				MY_COLUMNS,                                // b. column names
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

        // 5. return strain information
        return strainInfo;
    } //********************************************************************************************


	//----------------------------------------------------------------------------------------------
	//----------------------------------------------------------------------------------------------
	//----------------------------------------------------------------------------------------------
    //      ALL STRAINS TABLE               ALL STRAINS TABLE           	    ALL STRAINS TABLE
	//----------------------------------------------------------------------------------------------
	//----------------------------------------------------------------------------------------------
	//----------------------------------------------------------------------------------------------

    // Get Strain Information by Database Row.
    public CannabisStrainDatabase_Definition getStrainData(int id){
        // 1. get reference to readable DB
        SQLiteDatabase db = this.getReadableDatabase();

        // 2. build query
        Cursor cursor = db.query(ALL_STRAINS_TABLE_TITLE,                    // a. table
				ALL_COLUMNS,                                // b. column names
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
        strainInfo.setEffectsHappy(cursor.getDouble(3));
        strainInfo.setEffectsHungry(cursor.getDouble(4));

        // 5. return strain information
        return strainInfo;
    }

    // Update Strain Information.
    public int updateStrain(CannabisStrainDatabase_Definition strainInfo) {
        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put(COLUMN_1, strainInfo.getStrainName());
        values.put(COLUMN_2, strainInfo.getEffectsRelaxed());

        // 3. updating row
        int i = db.update(ALL_STRAINS_TABLE_TITLE, values,_ID + " = ?", new String[] {String.valueOf(strainInfo.getStrainId())});

        // 4. close
        db.close();
        return i;
    }

    // Delete a Strain.
    public int deleteStrain(CannabisStrainDatabase_Definition strainInfo) {
        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. delete
        int i = db.delete(ALL_STRAINS_TABLE_TITLE,_ID + " = ?", new String[] { String.valueOf(strainInfo.getStrainId()) });

        // 3. close
        db.close();
        return i;
    }

    // Returns the number of rows in the Strain Database.
    public long getStrainDatabaseRows() {
        SQLiteDatabase db = this.getReadableDatabase();
        long count = DatabaseUtils.queryNumEntries(db, ALL_STRAINS_TABLE_TITLE);
        db.close();
        return count;
    }

    // Returns the number of columns in the Strain Database.
    public int getStrainDatabaseColumns() {
        int count = 13;
        return count;
    }

    // Returns the titles of the Strain Database table.
	public String getStrainDatabaseColumnTitles(int i) {
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.query(ALL_STRAINS_TABLE_TITLE, null, null, null, null, null, null);
		String column[] = cursor.getColumnNames();
		return column[i];
	}
}