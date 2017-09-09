package com.saysweb.emis_app;

import android.content.ContentValues;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.TextView;

import com.saysweb.emis_app.data.emisContract.UserEntry;
import com.saysweb.emis_app.data.emisContract.ProvinceEntry;
import com.saysweb.emis_app.data.emisContract.DistrictEntry;
import com.saysweb.emis_app.data.emisContract.LlgvEntry;
import com.saysweb.emis_app.data.emisContract.SchoolEntry;
import com.saysweb.emis_app.data.emisDBHelper;



public class MainActivity extends AppCompatActivity {

    private emisDBHelper mDbHelper;

    @Override
    //--This is the main activity
    //Sukant

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDbHelper = new emisDBHelper(this);

        insertUser();
        displayDatabaseInfo();
    }

    /**
     * Temporary helper method to display information in the onscreen TextView about the state of
     * the emis database.
     */
    private void displayDatabaseInfo() {
        // To access our database, we instantiate our subclass of SQLiteOpenHelper
        // and pass the context, which is the current activity.


        // Create and/or open a database to read from it
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        // Perform this raw SQL query "SELECT * FROM emis"
        // to get a Cursor that contains all rows from the emis table.
        Cursor cursor = db.rawQuery("SELECT * FROM " + UserEntry.TABLE_NAME, null);
        try {
            // Display the number of rows in the Cursor (which reflects the number of rows in the
            // emis table in the database).
            TextView displayView = (TextView) findViewById(R.id.text_view_emis);
            displayView.setText("Number of rows in EMIS database table: " + cursor.getCount());
        } finally {
            // Always close the cursor when you're done reading from it. This releases all its
            // resources and makes it invalid.
            cursor.close();
        }


        // Inserting Dummy data in user table.
        // Create and/or open a database to write on it


    }

    public void insertUser(){
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(UserEntry.COLUMN_NAME_USER_ID, "1");
        values.put(UserEntry.COLUMN_NAME_USER_NAME, "user1");
        values.put(UserEntry.COLUMN_NAME_PASSWORD, "password1");
        values.put(UserEntry.COLUMN_NAME_EMP_CODE, "Emp1");
        values.put(UserEntry.COLUMN_NAME_EMP_NAME, "Rahul");
        values.put(UserEntry.COLUMN_NAME_EMAIL, "rahultrivedy@gmail.com");
        values.put(UserEntry.COLUMN_NAME_MOBILE_NO, "7544960673");

        // Insert the new row, returning the primary key value of the new row
        long newRowId = db.insert(UserEntry.TABLE_NAME, null, values);

        Log.v("MainActivity","New Row Id " + newRowId);


    }
}
