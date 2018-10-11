package com.example.tofer.emptyproject_helloworld;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import java.sql.SQLException;

import static com.example.tofer.emptyproject_helloworld.CannabisStrainDatabase_Definition.TABLE_NAME;

public class CannabisStrainDatabase_Adapter {

    private Context context;
    private SQLiteDatabase db;
    private ContactDBHelper dbHelper;

    public ContactDBAdapter(Context context)
    {
        this.context = context;
    }

    public synchronized ContactDBAdapter open() throws SQLException
    {
        dbHelper = new ContactDBHelper(context);
        db = dbHelper.getWritableDatabase();
        return this;
    }

    public synchronized void close()
    {
        dbHelper.close();
    }

    /**
     * Create a new Contact entry. If the entry is successfully created return the new
     * rowId for that note, otherwise return a -1 to indicate failure.
     */
    public long createRow(Contact contact)
    {
        ContentValues values = createContentValue(contact);
        return db.insert(TABLE_NAME, null, values);
    }

    /**
     * Update a row / entry
     */
    public boolean updateRow(long rowIndex, Contact contact)
    {
        ContentValues values = createContentValue(contact);

        return db.update(TABLE_NAME, values, KEY_ROWID + "=" + rowIndex, null) > 0;
    }

    /**
     * Deletes a row
     */
    public boolean deleteRow(long rowIndex)
    {
        return db.delete(TABLE_NAME, KEY_ROWID + "=" + rowIndex, null) > 0;
    }

    public void deleteAllRows()
    {
        for(int i = 0; i < fetchAllEntries().getCount(); i++)
            deleteRow(i);
    }

    /**
     * Return a Cursor over the list of all Contacts in the database
     *
     * @return Cursor over all contacts
     */
    public Cursor fetchAllEntries()
    {
        return db.query(TABLE_NAME, TABLE_COLUMNS, null, null, null, null, null);
    }

    /**
     * Return a Cursor positioned at the defined Contact
     */
    public Cursor fetchEntry(long rowIndex) throws SQLException
    {
        Cursor mCursor = db.query(true, TABLE_NAME, TABLE_COLUMNS, KEY_ROWID + "=" + rowIndex, null, null, null, null, null);
        if (mCursor != null)
        {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    /**
     * Fetch all entries and rebuild them as Contact objects in an ArrayList.
     * If no results are found, an empty list is returned.
     *
     * @return ArrayList of Contacts
     */
    public ArrayList<Contact> fetchAllContacts()
    {
        ArrayList<Contact> res = new ArrayList<Contact>();

        Cursor resultSet = fetchAllEntries();

        if (resultSet.moveToFirst() != false)
            for(int i = 0; i < resultSet.getCount(); i++)
            {
                String name = resultSet.getString(resultSet.getColumnIndex(KEY_NAME));
                String URI = resultSet.getString(resultSet.getColumnIndex(KEY_URI));

                Contact c = new Contact(name, URI);

                res.add(c);
                if(resultSet.moveToNext() == false)
                    break;
            }
        resultSet.close();
        return res;
    }

    public synchronized void reflectWith(ArrayList<Contact> contacts)
    {
        //      deleteAllRows();
        dbHelper.scratch(db);
        contacts.trimToSize();
        //empty contact
        Contact empty = new Contact();
        empty.empty();

        for(Contact c : contacts)
        {
            if(!c.getName().equals(empty.getName()))
                createRow(c);   //if not empty, add it
        }
    }

    private ContentValues createContentValue(Contact contact)
    {
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, contact.getName());
        values.put(KEY_URI, contact.getURI());
        return values;
    }

}