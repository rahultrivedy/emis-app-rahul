package com.saysweb.emis_app;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.saysweb.emis_app.data.emisContract;
import com.saysweb.emis_app.data.emisDBHelper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

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

        getSupportActionBar().hide();

        String intentId = getIntent().getStringExtra("intentID");
        if (intentId != null && intentId.length() != 0 && intentId.equals("SchoolSelect")){

            Intent intent = getIntent();
            Toast toast = Toast.makeText(this, "You have been Logged Out", Toast.LENGTH_LONG);
            toast.show();
        }



//        String intentId = intent.getStringExtra("Intent ID");

        /*Set the new toolbar as the Actionbar*/
//        Toolbar myToolbar = (Toolbar)findViewById(R.id.my_toolbar);
//        setSupportActionBar(myToolbar);
//
//        ActionBar actionBar = getSupportActionBar();
////        actionBar.setTitle("EMIS--");
//        actionBar.setCustomView(R.layout.action_bar);
//        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM
//                | ActionBar.DISPLAY_SHOW_HOME);


        ImageButton imageButton = (ImageButton) findViewById(R.id.show_pwd);
        final EditText editText = (EditText) findViewById(R.id.password);
        imageButton.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {

                switch ( event.getAction() ) {
                    case MotionEvent.ACTION_DOWN:
                        editText.setInputType(InputType.TYPE_CLASS_TEXT);
                        break;
                    case MotionEvent.ACTION_UP:
                        editText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                        break;
                }
                return true;
            }
        });




        mDbHelper = new emisDBHelper(this);

    /* Census Spinner Code*/

        spinner = (Spinner) findViewById(R.id.census_year);
        adapter = ArrayAdapter.createFromResource(this, R.array.census_year, R.layout.spinner_layout); //note : census_year linked to strings.xml
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

//        displayDatabaseInfo();
    }


    /*
    * Method invoked when Login button is clicked
    * take user inputs in a variable and call function to return password for the username - searchPass
    */
    public void onButtonClick(View v) {
        if (v.getId() == R.id.button_login) {
//            InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
//            imm.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);

            EditText user = (EditText) findViewById(R.id.username);
            String userName = user.getText().toString();

            EditText pwd = (EditText) findViewById(R.id.password);
            String password = pwd.getText().toString();

            String entered_password = mDbHelper.searchPass(userName); // Method call to return password for the username provided -Goes to emisDBHelper.java

            // Check if password matches the username

            if (password.equals(entered_password)) {

                MyApplication myApplication = (MyApplication) getApplication();
                myApplication.setGlobal_userID(userName);

                myApplication = (MyApplication) getApplication();
                myApplication.setGlobal_censusYear(censusYear);

                Intent intent = new Intent(MainActivity.this, SchoolSelectActivity.class);
                intent.putExtra("UserName", userName);
                intent.putExtra("intentID", "MainActivity");

//                intent.putExtra("CensusYear", censusYear);

//  Import school data from csv ... only once using shared preferences

                boolean mboolean = false;

                SharedPreferences settings = getSharedPreferences("PREFS_NAME", 0);
                mboolean = settings.getBoolean("FIRST_RUN", false);
                if (!mboolean) {
                    // do the thing for the first time

//                    ProgressBar progressBar = (ProgressBar)findViewById(R.id.indeterminateBar);
//                    progressBar.setVisibility(View.VISIBLE);


                    List<SchoolData> schoolDatas = new ArrayList<>();


                    InputStream is = getResources().openRawResource(R.raw.schools);
                    BufferedReader reader = new BufferedReader(
                            new InputStreamReader(is, Charset.forName("UTF-8"))
                    );
                    SQLiteDatabase db_insert = mDbHelper.getWritableDatabase();
                    String line = "";
                    try {
                        reader.readLine();
                        while ((line = reader.readLine()) != null){
                            // Split by ","

                            String[] tokens = line.split(",");

                            //Read the data

                            SchoolData data = new SchoolData();
                            data.setSchl_id(Integer.parseInt(tokens[0]));
                            data.setSchool_code(tokens[1]);
                            data.setSchool_name(tokens[2]);
                            data.setProvince_code(tokens[3]);
                            data.setProvince_name(tokens[4]);
                            data.setDistrict_code(tokens[5]);
                            data.setDistrict_name(tokens[6]);
                            data.setLlgv_code(tokens[7]);
                            data.setLlgv_name(tokens[8]);
                            data.setSector_code(tokens[9]);
                            schoolDatas.add(data);

                            ContentValues values = new ContentValues();
                            values.put(emisContract.SchoolEntry.COLUMN_NAME_SCHL_ID, tokens[0]);
                            values.put(emisContract.SchoolEntry.COLUMN_NAME_SCHOOL_CODE, tokens[1]
                            );
                            values.put(emisContract.SchoolEntry.COLUMN_NAME_SCHOOL_NAME, tokens[2]);
//                values.put(SchoolEntry.COLUMN_NAME_ADDRESS, "Address");
//                values.put(SchoolEntry.COLUMN_NAME_BOARDING_STATUS, "Yes");
//                values.put(SchoolEntry.COLUMN_NAME_CENSUS_YEAR, 2017);
                            values.put(emisContract.SchoolEntry.COLUMN_NAME_DISTRICT_CODE, tokens[5]);
                            values.put(emisContract.SchoolEntry.COLUMN_NAME_PROVINCE_CODE, tokens[3]);
                            values.put(emisContract.SchoolEntry.COLUMN_NAME_LLGV_CODE, tokens[7]);
//                values.put(SchoolEntry.COLUMN_NAME_EMAIL, data.get);
//                values.put(SchoolEntry.COLUMN_NAME_FAX, "760906454");
//                values.put(SchoolEntry.COLUMN_NAME_LOCALITY, "Locality");
//                values.put(SchoolEntry.COLUMN_NAME_PHONE, "0652-72509");
                            values.put(emisContract.SchoolEntry.COLUMN_NAME_SECTOR_CODE, tokens[9]);
//                values.put(SchoolEntry.COLUMN_NAME_WARD_CODE, "ward-code");
                            // Insert the new row, returning the primary key value of the new row
//                db.insert(SchoolEntry.TABLE_NAME, null, values);
                            long newRowId = db_insert.insert(emisContract.SchoolEntry.TABLE_NAME, null, values);

                            Log.d("EntryInfo", "Just Created: " + data);


                        }
                        db_insert.close();
                    } catch (IOException e) {
                        Log.wtf("EntryInfo", "Error reading data file on line" + line, e);
                        e.printStackTrace();
                    }


                    settings = getSharedPreferences("PREFS_NAME", 0);
                    SharedPreferences.Editor editor = settings.edit();
                    editor.putBoolean("FIRST_RUN", true);
                    editor.apply();
                }

                //  Import school data from csv ... only once using shared preferences ----- END

                startActivity(intent);
                finish();

            } else {
                Toast toast_fail = Toast.makeText(this, "User Name and Password do not match", Toast.LENGTH_SHORT);
                toast_fail.show();
            }
        }
    }

    public void onBackPressed()
    {

        finish();

        moveTaskToBack(true);
        android.os.Process.killProcess(android.os.Process.myPid());


    }


}
