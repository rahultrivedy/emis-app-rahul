package com.saysweb.emis_app;

import android.content.ContentValues;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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

import static android.R.attr.duration;

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

        mDbHelper = new emisDBHelper(this);

    /* Census Spinner Code*/

        spinner = (Spinner) findViewById(R.id.census_year);
        adapter = ArrayAdapter.createFromResource(this, R.array.census_year, android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getBaseContext(), adapterView.getItemAtPosition(i) + " is selected", Toast.LENGTH_LONG).show();
                censusYear = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    /* Census Spinner Code End */

//        displayDatabaseInfo();
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

            String entered_password = searchPass(userName); // Method call to return password for the username provided

            // Check if password matches the username

            if (password.equals(entered_password)) {

                Intent intent = new Intent(MainActivity.this, SchoolSelectActivity.class);
                intent.putExtra("UserName", userName);
                intent.putExtra("CensusYear", censusYear);
                startActivity(intent);


                Toast toast_success = Toast.makeText(this, "Move on to Next Page", Toast.LENGTH_SHORT);
                toast_success.show();
            } else {
                Toast toast_fail = Toast.makeText(this, "User Name and Password do not match", Toast.LENGTH_SHORT);
                toast_fail.show();
            }
        }
    }

    // Method to Search password for the username provided by user

    public String searchPass(String uName) {
        // Create and/or open a database to read from it
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        String[] projection = {UserEntry.COLUMN_NAME_USER_NAME,
                UserEntry.COLUMN_NAME_PASSWORD};
//                String selection = UserEntry.COLUMN_PET_GENDER + “=?”;
//                String selectionArgs = new String[] { UserEntry.GENDER_FEMALE };

        Cursor cursor = db.query(UserEntry.TABLE_NAME, projection,
                null, null, null, null, null);

        String returnPass = "not found";
        if (cursor.moveToFirst()) {
            do {
                String userName = cursor.getString(0);

                if (userName.equals(uName)) {
                    returnPass = cursor.getString(1);
                    break;
                }

            } while (cursor.moveToNext());
        }
        cursor.close();
        return (returnPass);
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
