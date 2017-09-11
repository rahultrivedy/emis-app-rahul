package com.saysweb.emis_app;

import android.content.ContentValues;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.saysweb.emis_app.data.emisContract.UserEntry;
import com.saysweb.emis_app.data.emisContract.ProvinceEntry;
import com.saysweb.emis_app.data.emisContract.DistrictEntry;
import com.saysweb.emis_app.data.emisContract.LlgvEntry;
import com.saysweb.emis_app.data.emisContract.SchoolEntry;
import com.saysweb.emis_app.data.emisDBHelper;

import java.util.Random;

import static android.R.attr.duration;
import static android.R.attr.enabled;

public class MainActivity extends AppCompatActivity {

    Spinner spinner;
    ArrayAdapter<CharSequence> adapter;

    //use CursorAdapter to add data from DB.
    private emisDBHelper mDbHelper;
    String censusYear;

    @Override
    //--This is the main activity



    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        /*Set the new toolbar as the Actionbar*/
        Toolbar myToolbar = (Toolbar)findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        ActionBar actionBar = getSupportActionBar();
//        actionBar.setTitle("EMIS--");
        actionBar.setCustomView(R.layout.action_bar);
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM
                | ActionBar.DISPLAY_SHOW_HOME);




        mDbHelper = new emisDBHelper(this);

    /* Census Spinner Code*/

        spinner = (Spinner) findViewById(R.id.census_year);
        adapter = ArrayAdapter.createFromResource(this, R.array.census_year, R.layout.spinner_layout);
        adapter.setDropDownViewResource(R.layout.spinner_layout);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                censusYear = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    /* Census Spinner Code End */
        insertUser();
        insertProvince();
        insertDistrict();
        insertSchools();
//        displayDatabaseInfo();
    }

    //Code to insert user
    public void insertUser(){
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        // Create a new map of values, where column names are the keys
        String strValI = new String();
        int i;
        for(i = 1; i <= 15; i++){
            strValI= Integer.toString(i);
            ContentValues values = new ContentValues();
            values.put(UserEntry.COLUMN_NAME_USER_ID, i);
            values.put(UserEntry.COLUMN_NAME_USER_NAME, "user" + i);
            values.put(UserEntry.COLUMN_NAME_PASSWORD, "password" + i);
            values.put(UserEntry.COLUMN_NAME_EMP_CODE, "Emp" + i);
            values.put(UserEntry.COLUMN_NAME_EMP_NAME, "Name" + i);
            values.put(UserEntry.COLUMN_NAME_EMAIL, "rahultrivedy" + i + "@gmail.com");
            values.put(UserEntry.COLUMN_NAME_MOBILE_NO, "98343204" + i);

            // Insert the new row, returning the primary key value of the new row
            long newRowId = db.insert(UserEntry.TABLE_NAME, null, values);

            Log.v("MainActivity","New User Id " + newRowId);
        }

    }

    //Code to insert Province
    public void insertProvince(){
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        // Create a new map of values, where column names are the keys
        String strValI = new String();
        int i;
        for(i = 1; i <= 15; i++){
            ContentValues values = new ContentValues();
            values.put(ProvinceEntry.COLUMN_NAME_PROVINCE_CODE, i);
            values.put(ProvinceEntry.COLUMN_NAME_PROVINCE_NAME, "Province-" + i);
            values.put(ProvinceEntry.COLUMN_NAME_OLD_PROVINCE_CODE, "Old-" + i);
            values.put(ProvinceEntry.COLUMN_NAME_OLD_PROVINCE_NAME, "OldName-" + i);

            // Insert the new row, returning the primary key value of the new row
            long newRowId = db.insert(ProvinceEntry.TABLE_NAME, null, values);

            Log.v("MainActivity","New Prov Id " + newRowId);
        }
    }

    //Code to insert Province
    public void insertDistrict(){
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        // Create a new map of values, where column names are the keys
        String strValI = new String();
        int i;
        for(i = 1; i <= 15; i++){
            ContentValues values = new ContentValues();
            values.put(DistrictEntry.COLUMN_NAME_DISTRICT_CODE, i);
            values.put(DistrictEntry.COLUMN_NAME_DISTRICT_NAME, "District-" + i);
            values.put(DistrictEntry.COLUMN_NAME_OLD_DISTRICT_CODE, "OldDist-" + i);
            values.put(DistrictEntry.COLUMN_NAME_OLD_DISTRICT_NAME, "OldDistName-" + i);
            Random r = new Random();
            int Low = 1;
            int High = 15;
            int Result = r.nextInt(High-Low) + Low;
            values.put(DistrictEntry.COLUMN_NAME_PROVINCE_CODE, Result);

            // Insert the new row, returning the primary key value of the new row
            long newRowId = db.insert(DistrictEntry.TABLE_NAME, null, values);

            Log.v("MainActivity","New Dist Id " + newRowId);
        }
    }

    //Code to insert Schools
    public void insertSchools(){
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        // Create a new map of values, where column names are the keys
        int i;
        int Low = 1;
        int High = 15;

        for(i = 1; i <= 15; i++){
            ContentValues values = new ContentValues();
            values.put(SchoolEntry.COLUMN_NAME_SCHL_ID, i);
            Random prov = new Random();
            int provResult = prov.nextInt(High-Low) + Low;

            Random dist = new Random();
            int distResult = dist.nextInt(High-Low) + Low;
            values.put(SchoolEntry.COLUMN_NAME_SCHOOL_CODE, "" + provResult + + distResult + i);
            values.put(SchoolEntry.COLUMN_NAME_SCHOOL_NAME, "School - " + i);
            values.put(SchoolEntry.COLUMN_NAME_ADDRESS, "Address - " + provResult + + distResult + i);

            final String[] boarding_status = {"Yes", "No"};
            Random random = new Random();
            int index = random.nextInt(boarding_status.length);
            values.put(SchoolEntry.COLUMN_NAME_BOARDING_STATUS, boarding_status[index]);

            final String[] census_yr = {"2015", "2016", "2017"};
            Random censusRandom = new Random();
            int censusIndex = censusRandom.nextInt(census_yr.length);
            values.put(SchoolEntry.COLUMN_NAME_CENSUS_YEAR, census_yr[censusIndex]);

            values.put(SchoolEntry.COLUMN_NAME_DISTRICT_CODE, distResult);
            values.put(SchoolEntry.COLUMN_NAME_PROVINCE_CODE, provResult);
            values.put(SchoolEntry.COLUMN_NAME_LLGV_CODE, distResult);
            values.put(SchoolEntry.COLUMN_NAME_EMAIL, "school." + i + "@emis.com");
            values.put(SchoolEntry.COLUMN_NAME_FAX, "760906454" + i);
            values.put(SchoolEntry.COLUMN_NAME_LOCALITY, "Locality" + i);
            values.put(SchoolEntry.COLUMN_NAME_PHONE, "0652-725097" + distResult);
            values.put(SchoolEntry.COLUMN_NAME_SECTOR_CODE, distResult);
            values.put(SchoolEntry.COLUMN_NAME_WARD_CODE, provResult);;

            // Insert the new row, returning the primary key value of the new row
            long newRowId = db.insert(SchoolEntry.TABLE_NAME, null, values);

            Log.v("MainActivity","New School Id " + newRowId);
        }
    }
    /*
    * Method invoked when Login button is clicked
    * take user inputs in a variable and call function to return password for the username - searchPass
    */
    public void onButtonClick(View v) {
        if (v.getId() == R.id.button_login) {

            EditText user = (EditText) findViewById(R.id.username);
            String userName = user.getText().toString();

            EditText pwd = (EditText) findViewById(R.id.password);
            String password = pwd.getText().toString();

            String entered_password = mDbHelper.searchPass(userName); // Method call to return password for the username provided -Goes to emisDBHelper.java

            // Check if password matches the username

            if (password.equals(entered_password)) {

                Intent intent = new Intent(MainActivity.this, SchoolSelectActivity.class);
                intent.putExtra("UserName", userName);
                intent.putExtra("CensusYear", censusYear);
                startActivity(intent);

            } else {
                Toast toast_fail = Toast.makeText(this, "User Name and Password do not match", Toast.LENGTH_SHORT);
                toast_fail.show();
            }
        }
    }




//    /**
//     * Temporary helper method to display information in the onscreen TextView about the state of
//     * the emis database.
//     */
//    private void displayDatabaseInfo() {
//
//        // To access our database, we instantiate our subclass of SQLiteOpenHelper
//        // and pass the context, which is the current activity.
//
//
//
//        TextView displayView = (TextView) findViewById(R.id.text_view_emis);
//        displayView.setText("Number of rows in EMIS database table: " + cursor.getColumnName(0));
//
//        // Perform this raw SQL query "SELECT * FROM emis"
//        // to get a Cursor that contains all rows from the emis table.
//        Cursor cursor = db.rawQuery("SELECT * FROM " + UserEntry.TABLE_NAME, null);
//        try {
//            // Display the number of rows in the Cursor (which reflects the number of rows in the
//            // emis table in the database).
//            TextView displayView = (TextView) findViewById(R.id.text_view_emis);
//            displayView.setText("Number of rows in EMIS database table: " + cursor.getCount());
//        } finally {
//            // Always close the cursor when you're done reading from it. This releases all its
//            // resources and makes it invalid.
//            cursor.close();
//        }
//
//
////         ********************TEST ******************//
//
////         Inserting Dummy data in user table.
////         Create and/or open a database to write on it
//
//
//    }

}
