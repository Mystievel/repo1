package com.example.tofer.emptyproject_helloworld;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class CannabisStrainDatabase_Definition {
    //key identifiers / column names
    public static final String      KEY_ROWID       = "_id";        // First column title
    public static final String      KEY_NAME        = "name";       // Second column title
    public static final String      KEY_URI         = "uri";        // Third column title
    public static final String      TABLE_NAME      = "contacts";

    //useful stuff
    public static final String[]    TABLE_COLUMNS   = { KEY_ROWID, KEY_NAME, KEY_URI };     //public makes it more useful
    private static final String[]   TABLE_COLTYPES  = { "integer primary key autoincrement",
                                                        "text not null",
                                                        "text not null" }; // Type associated with each column types (column titles)

    // Database creation SQL statement in lazy-pretty version
    private static final String     TABLE_CREATE    = "create table " + TABLE_NAME + "("
                                                                    + TABLE_COLUMNS[0] + " " + TABLE_COLTYPES[0] + ","
                                                                    + TABLE_COLUMNS[1] + " " + TABLE_COLTYPES[1] + ","
                                                                    + TABLE_COLUMNS[2] + " " + TABLE_COLTYPES[2] + ");";

    private static final String     LOGTAG          = "ContactTable";



    // ------Note to self, continue review here------




    public CannabisStrainDatabase_Definition() {
    }

    public static void onCreate(SQLiteDatabase database) {
        database.execSQL(TABLE_CREATE);
    }

    public static void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        Log.w(LOGTAG, "Upgrading database from version " + oldVersion + " to " + newVersion + ", which will destroy all old data");
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(database);
    }

    public static void scratch(SQLiteDatabase database) {
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        database.execSQL(TABLE_CREATE);
    }
}