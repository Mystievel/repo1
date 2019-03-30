package com.tofersapps.ThePurplePot;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;

public class CannabisStrainDatabase_Helper extends SQLiteOpenHelper {
    // Database Version & Name
	private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "CannabisStrainDatabase";


    public CannabisStrainDatabase_Helper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}


    @Override
    public void onCreate(SQLiteDatabase db) {
        // SQL statement to create book table.
        String CREATE_DATABASE_TABLE = "CREATE TABLE " + DATABASE_NAME + " (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "StrainName TEXT, " +
				"StrainType TEXT, " +
				"MyStrains INTEGER, " +
				"FavoriteStrains INTEGER, " +
				"Happiness DOUBLE, " +
				"Euphoria DOUBLE, " +
				"Focus DOUBLE, " +
				"Energy DOUBLE, " +
				"Relaxation DOUBLE, " +
				"Sleepiness DOUBLE, " +
				"Sickness_Relief DOUBLE, " +
				"Pain_Relief DOUBLE, " +
				"Hunger DOUBLE, " +
				"Dehydration DOUBLE, " +
				"Anxiety DOUBLE)";

        // create books table
        db.execSQL(CREATE_DATABASE_TABLE);
		Log.d("onCreates1", "table created");
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
    private static final String CANNABIS_STRAIN_TABLE = "CannabisStrainTable";

    // Strain Database (Table) Columns names.
    // These values MUST match the Strings in the db definition file.
    private static final String COLUMN_0 = "id";
    private static final String COLUMN_1 = "StrainName";
    private static final String COLUMN_2 = "StrainType";
	private static final String COLUMN_3 = "MyStrains";
	private static final String COLUMN_4 = "FavoriteStrains";
	private static final String COLUMN_5 = "Happiness";
	private static final String COLUMN_6 = "Euphoria";
	private static final String COLUMN_7 = "Focus";
	private static final String COLUMN_8 = "Energy";
	private static final String COLUMN_9 = "Relaxation";
	private static final String COLUMN_10 = "Sleepiness";
	private static final String COLUMN_11 = "Sickness_Relief";
	private static final String COLUMN_12 = "Pain_Relief";
	private static final String COLUMN_13 = "Hunger";
	private static final String COLUMN_14 = "Dehydration";
	private static final String COLUMN_15 = "Anxiety";

	private static final String[] ALL_COLUMNS = {COLUMN_0, COLUMN_1, COLUMN_2, COLUMN_3, COLUMN_4, COLUMN_5, COLUMN_6, COLUMN_7, COLUMN_8, COLUMN_9, COLUMN_10, COLUMN_11, COLUMN_12, COLUMN_13, COLUMN_14, COLUMN_15};


/*    //**********************************************************************************************
    // Add a strain to the database.
    //**********************************************************************************************
    public long addToMyStrains(CannabisStrainDatabase_Definition strainInfo) {
        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        //values.put(STRAIN_ID, strainInfo.getStrainId()); 	// get strain name - says this line is not needed?
		values.put(COLUMN_3, strainInfo.getMyStrain()); 	// get strain name

        // 3. insert
        long id = db.insert(CANNABIS_STRAIN_TABLE,null, values);
        db.close();     // 4. close
        return id;      // Return newly inserted row id.
    } //********************************************************************************************

    // Delete a Strain.
    public int deleteStrain(CannabisStrainDatabase_Definition strainInfo) {
        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. delete
        int i = db.delete(CANNABIS_STRAIN_TABLE,COLUMN_0 + " = ?", new String[] { String.valueOf(strainInfo.getStrainId()) });

        // 3. close
        db.close();
        return i;
    }
*/

	//**********************************************************************************************
    // Get Strain Information by Database Row.
	//**********************************************************************************************
	public CannabisStrainDatabase_Definition getStrainData(int id){
        // 1. get reference to readable DB
        SQLiteDatabase db = this.getReadableDatabase();

        // 2. build query
        Cursor cursor = db.query(CANNABIS_STRAIN_TABLE,		// a. table
				ALL_COLUMNS,									// b. column names
                        " id = ?",						// c. selections
                        new String[] {String.valueOf(id)},     	// d. selections args
                        null,							// e. group by
                        null,							// f. having
                        null,							// g. order by
                        null);								// h. limit

        // 3. if we got results get the first one
        if (cursor != null) {
            cursor.moveToFirst();
        }

        // 4. build strainInfo object
        CannabisStrainDatabase_Definition strainInfo = new CannabisStrainDatabase_Definition();
        strainInfo.setStrainId(Integer.parseInt(cursor.getString(0)));
        strainInfo.setStrainName(cursor.getString(1));
		strainInfo.setStrainType(cursor.getString(2));
		strainInfo.setMyStrains(Integer.parseInt(cursor.getString(3)));
		strainInfo.setFavoriteStrains(Integer.parseInt(cursor.getString(4)));
		strainInfo.setEffectsHappiness(cursor.getDouble(5));
		strainInfo.setEffectsEuphoria(cursor.getDouble(6));
		strainInfo.setEffectsFocus(cursor.getDouble(7));
		strainInfo.setEffectsEnergy(cursor.getDouble(8));
		strainInfo.setEffectsRelaxation(cursor.getDouble(9));
		strainInfo.setEffectsSleepiness(cursor.getDouble(10));
		strainInfo.setEffectsSickness_Relief(cursor.getDouble(11));
		strainInfo.setEffectsPain_Relief(cursor.getDouble(12));
		strainInfo.setEffectsHunger(cursor.getDouble(13));
		strainInfo.setEffectsDehydration(cursor.getDouble(14));
		strainInfo.setEffectsAnxiety(cursor.getDouble(15));

        // 5. return strain information
		cursor.close();
		db.close();
		return strainInfo;
    } //********************************************************************************************


	//**********************************************************************************************
	// Updates a single entry in the MyStrains column.
	//**********************************************************************************************
	public int updateMyStrain(CannabisStrainDatabase_Definition strainInfo, int value) {
		// 1. get reference to writable DB
		SQLiteDatabase db = this.getWritableDatabase();

		// 2. create ContentValues to add key "column"/value
		ContentValues values = new ContentValues();
		values.put(COLUMN_3, value);

		// 3. updating row
		int i = db.update(CANNABIS_STRAIN_TABLE, values,COLUMN_0 + " = ?", new String[] {String.valueOf(strainInfo.getStrainId())});

		// 4. close and return
		db.close();
		return i;
	} //********************************************************************************************


	//**********************************************************************************************
	// Returns the number of rows in the Strain Database.
	//**********************************************************************************************
	public int getStrainDatabaseRows() {
        SQLiteDatabase db = this.getReadableDatabase();
        int count = (int) DatabaseUtils.queryNumEntries(db, CANNABIS_STRAIN_TABLE);
        db.close();
        return count;
    } //********************************************************************************************


	//**********************************************************************************************
	// Returns the titles of the Strain Database table.
	//**********************************************************************************************
	public String getStrainDatabaseColumnTitles(int i) {
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.query(CANNABIS_STRAIN_TABLE, null, null, null, null, null, null);
		String column[] = cursor.getColumnNames();

		// Replace underscores with space characters
		column[i] = column[i].replace("_", " ");

		// close & return
		cursor.close();
		db.close();
		return column[i];
	} //********************************************************************************************


	//**********************************************************************************************
	// Gets all integer values within a single column from the cannabis strain table.
	// todo: Medium Priority - polymorph these 3 routines in own java file
	//**********************************************************************************************
	public int[] getDatabaseValuesFromColumn_intArray(String columnName, int numberOfRows) {
		int i = 0;
		int[] columnValues = new int[numberOfRows];

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.query(CANNABIS_STRAIN_TABLE, null, null, null, null, null, null, null);

		if (cursor != null) {
			while (cursor.moveToNext()) {
				columnValues[i] = cursor.getInt(cursor.getColumnIndex("" + columnName));
				i++;
			}
		}

		// close & return
		cursor.close();
		db.close();
		return columnValues;
	} //********************************************************************************************

	//**********************************************************************************************
	// Gets all doubles values within a single column from the cannabis strain table.
	//**********************************************************************************************
	public double[] getDatabaseValuesFromColumn_doubleArray(String columnName, int numberOfRows) {
		int i = 0;
		double[] columnValues = new double[numberOfRows];

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.query(CANNABIS_STRAIN_TABLE, null, null, null, null, null, null, null);

		if (cursor != null) {
			while (cursor.moveToNext()) {
				columnValues[i] = cursor.getDouble(cursor.getColumnIndex("" + columnName));
				i++;
			}
		}

		// close & return
		cursor.close();
		db.close();
		return columnValues;
	} //********************************************************************************************

	//**********************************************************************************************
	// Gets all String values within a single column from the cannabis strain table.
	//**********************************************************************************************
	public String[] getDatabaseValuesFromColumn_stringArray(String columnName, int numberOfRows) {
		int i = 0;
		String[] columnValues = new String[numberOfRows];

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.query(CANNABIS_STRAIN_TABLE, null, null, null, null, null, null, null);

		if (cursor != null) {
			while (cursor.moveToNext()) {
				columnValues[i] = cursor.getString(cursor.getColumnIndex("" + columnName));
				i++;
			}
		}

		// close & return
		cursor.close();
		db.close();
		return columnValues;
	} //********************************************************************************************


	//**********************************************************************************************
	// Gets number of elements in the database from the MyStrains column: stores the # of "1"s found.
	//**********************************************************************************************
	public int getNumberOfMyStrains() {
		int i = 0;

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.query(CANNABIS_STRAIN_TABLE, null, null, null, null, null, null, null);

		if (cursor != null) {
			while (cursor.moveToNext()) {
				int value = cursor.getInt(cursor.getColumnIndex("" + COLUMN_3));
				if (value == 1) {
					i++;
				}
			}
		}

		// close & return
		cursor.close();
		db.close();
		return i;
	} //********************************************************************************************



	//**********************************************************************************************
	//                        Collect and Filter My Strains List
	//**********************************************************************************************
	public int[] collectAndFilterMyStrains(int numberOfRows) {
		// Start out with a list of all strains.
		int subtractor = 0;
		int[] finalArray = new int[numberOfRows];

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor;

		// Remove all entries not == 1.
		cursor = db.query(CANNABIS_STRAIN_TABLE, null, null, null, null, null, null, null);
		if (cursor != null) {
			while (cursor.moveToNext()) {
				int value = cursor.getInt(cursor.getColumnIndex("" + COLUMN_3));
				int cursorPosition = cursor.getPosition();
				if (value == 1) {
					finalArray[cursorPosition - subtractor] = getStrainData(cursorPosition).getStrainId();
				} else {
					subtractor++;
				}
			}
		}

		// close & return
		cursor.close();
		db.close();
		return finalArray;
	} //********************************************************************************************


	//**********************************************************************************************
	//			Get Index of item in database based on position (row) of item in database.
	//**********************************************************************************************
	public int[] getDatabaseItemValueByID_int(String columnName, int[] arrayOfIndexes, int arraySize) {
		int[] values = new int[arrayOfIndexes.length];
		int cursorPosition;
		int tempCursorPosition;
		int originalCursorPosition = 0;
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor;

		//Log.d("intVal", "array length: " + arrayOfIndexes.length);
		// Remove all entries not == 1.
		cursor = db.query(CANNABIS_STRAIN_TABLE, null, null, null, null, null, null, null);
		if (cursor != null) {
			for (int i = 0; i < arraySize; i ++) {
				cursor.moveToPosition(arrayOfIndexes[originalCursorPosition]);
				tempCursorPosition = cursor.getPosition();
				values[originalCursorPosition] = cursor.getInt(cursor.getColumnIndex("" + columnName));
				//Log.d("intVal", values[originalCursorPosition] + " at index " + originalCursorPosition + " at id " + tempCursorPosition + " == arrayval: " + arrayOfIndexes[originalCursorPosition]);

				// increase the original cursor position.
				originalCursorPosition++;
			}

			// close & return
			cursor.close();
			db.close();
			return values;
		}

		// close & return
		cursor.close();
		db.close();
		return null;
	} //********************************************************************************************


	//**********************************************************************************************
	//			Get Index of item in database based on position (row) of item in database.
	//**********************************************************************************************
	public String[] getDatabaseItemValueByID_string(String columnName, int[] arrayOfIndexes, int arraySize) {
		String[] values = new String[arrayOfIndexes.length];
		int cursorPosition;
		int tempCursorPosition;
		int originalCursorPosition = 0;
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor;

		//Log.d("stringVal", "array length: " + arrayOfIndexes.length);
		// Remove all entries not == 1.
		cursor = db.query(CANNABIS_STRAIN_TABLE, null, null, null, null, null, null, null);
		if (cursor != null) {
			for (int i = 0; i < arraySize; i ++) {
				cursor.moveToPosition(arrayOfIndexes[originalCursorPosition]);
				tempCursorPosition = cursor.getPosition();
				values[originalCursorPosition] = cursor.getString(cursor.getColumnIndex("" + columnName));
				//Log.d("stringVal", values[originalCursorPosition] + " at index " + originalCursorPosition + " at id " + tempCursorPosition + " == arrayval: " + arrayOfIndexes[originalCursorPosition]);

				// increase the original cursor position.
				originalCursorPosition++;
			}

			// close & return
			cursor.close();
			db.close();
			return values;
		}

		// close & return
		cursor.close();
		db.close();
		return null;
	} //********************************************************************************************


	//**********************************************************************************************
	//			Get Index of item in database based on position (row) of item in database.
	//**********************************************************************************************
	public double[] getDatabaseItemValueByID_double(String columnName, int[] arrayOfIndexes, int arraySize) {
		double[] values = new double[arrayOfIndexes.length];
		int cursorPosition;
		int tempCursorPosition;
		int originalCursorPosition = 0;
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor;

		//Log.d("stringVal", "array length: " + arrayOfIndexes.length);
		// Remove all entries not == 1.
		cursor = db.query(CANNABIS_STRAIN_TABLE, null, null, null, null, null, null, null);
		if (cursor != null) {
			for (int i = 0; i < arraySize; i ++) {
				cursor.moveToPosition(arrayOfIndexes[originalCursorPosition]);
				tempCursorPosition = cursor.getPosition();
				values[originalCursorPosition] = cursor.getDouble(cursor.getColumnIndex("" + columnName));
				//Log.d("stringVal", values[originalCursorPosition] + " at index " + originalCursorPosition + " at id " + tempCursorPosition + " == arrayval: " + arrayOfIndexes[originalCursorPosition]);

				// increase the original cursor position.
				originalCursorPosition++;
			}

			// close & return
			cursor.close();
			db.close();
			return values;
		}

		// close & return
		cursor.close();
		db.close();
		return null;
	} //********************************************************************************************
} //************************************************************************************************
