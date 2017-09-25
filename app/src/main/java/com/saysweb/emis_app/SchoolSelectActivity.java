package com.saysweb.emis_app;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;

import com.saysweb.emis_app.data.emisContract.SchoolEntry;
import com.saysweb.emis_app.data.emisDBHelper;

import java.util.HashMap;

import static android.text.TextUtils.split;
import static com.saysweb.emis_app.R.id.school_code;

public class SchoolSelectActivity extends AppCompatActivity {

    /* Code for Auto Complete - Start*/


    AutoCompleteTextView textView;

    /* Code for Auto Complete - END*/


    private emisDBHelper helper;
//    private emisDBHelper helper2;
    String year;
    int schlID;
    HashMap<String,String> schlId_schoolName = new HashMap<String, String>();
    String school_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_school_select);

        /* Actionbar*/
        /*Set the new toolbar as the Actionbar*/
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar2);
        setSupportActionBar(myToolbar);
        ActionBar actionBar1 = getSupportActionBar();
        actionBar1.setCustomView(R.layout.action_bar);
        actionBar1.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM
                | ActionBar.DISPLAY_SHOW_HOME);

//        actionBar1.setTitle("EMIS");
//        actionBar1.setSubtitle("Education Management Information System");


        helper = new emisDBHelper(this);


//
        /*GETTING THE INTENT from Main Activity*/

        // Get the Intent that started this activity and extract the string
        String intentID = getIntent().getStringExtra("intentID");
//         Checking intentID variable to check which class sent the intent
        if (intentID.equals("Home")) {
            Intent intent = getIntent();
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_righ);

            MyApplication myApplication = (MyApplication) getApplication();
            String autocompleteString = myApplication.getGlobal_autocompleteString();

            if(autocompleteString.length()!=0 ){
                AutoCompleteTextView autoCompleteTextView = (AutoCompleteTextView) findViewById(school_code);
                autoCompleteTextView.setText(autocompleteString);
                autoCompleteTextView.clearFocus();

                View view = findViewById(R.id.enrollment_by_grade);
                view.setVisibility(View.VISIBLE);


                View view1 = findViewById(R.id.boarding_enrollment);
                view1.setVisibility(View.VISIBLE);


                View view2 = findViewById(R.id.grade_class_count);
                view2.setVisibility(View.VISIBLE);


            }

        }else if (intentID.equals("MainActivity")){
            Intent intent = getIntent();
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        }

        MyApplication myApplication = (MyApplication) getApplication();
        year = myApplication.getGlobal_censusYear();

        myApplication = (MyApplication) getApplication();
        String userId = myApplication.getGlobal_userID();

        String userName = helper.getUserName(userId); // Goes to emisDBHelper.java

        // Capture the layout's TextView and set the string as its text
        TextView censusTextView = (TextView) findViewById(R.id.census_year);
        censusTextView.setText(year);

        // Capture the layout's TextView and set the string as its text
        TextView userTextView = (TextView) findViewById(R.id.user_id);
        userTextView.setText(userName);


        // -------------------




    /*Implementing AutoComplete through ArrayAdapter*/


        Cursor schoolCodesCursor;
        schoolCodesCursor = helper.valueOfCursor(); //Getting school codes in array. Goes to emisDBHelper.java
        String[] school_codes = new String[schoolCodesCursor.getCount()];
        String[] schlid = new String[schoolCodesCursor.getCount()];
        int i4 = 0;
        while (schoolCodesCursor.moveToNext()) {
            school_codes[i4] = schoolCodesCursor.getString(1) + " - " + schoolCodesCursor.getString(0);
            schlid[i4] = schoolCodesCursor.getString(2);
            i4++;
        }
        schoolCodesCursor.close();

        // Preparing Key - Value pair for AUTOCOMPLETE - SCHLID and SCHOOL CODE

        String[] spinnerArray = new String[school_codes.length];

        int j;
        for (j = 0; j < school_codes.length; j++) {
            schlId_schoolName.put(school_codes[j], schlid[j]);
            spinnerArray[j] = school_codes[j];
        }


        textView = (AutoCompleteTextView) findViewById(school_code);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, spinnerArray);
        textView.setThreshold(2);
        textView.setAdapter(adapter);
        textView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

                    InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(arg1.getApplicationWindowToken(), 0);


                    View view = findViewById(R.id.enrollment_by_grade);
                    view.setVisibility(View.VISIBLE);


                    View view1 = findViewById(R.id.boarding_enrollment);
                    view1.setVisibility(View.VISIBLE);


                    View view2 = findViewById(R.id.grade_class_count);
                    view2.setVisibility(View.VISIBLE);

            }
        });

    }


    /*
    * Method invoked when one of three buttons is clicked
    * takes autocomplete string as input breaks them and saves school code to send to next activity
    */
    public void onFormSelect(View v) {
        String school_code_send;
        EditText text = (EditText) findViewById(R.id.school_code);
        school_code_send = text.getText().toString();
        String[] myString = split(school_code_send," - ");
        String school_code_send2 = myString[1];
        String school_code_send3 = school_code_send2.replaceAll("\\p{Z}","");

        SQLiteDatabase db = helper.getReadableDatabase();

        String[] projection = {SchoolEntry.COLUMN_NAME_SCHL_ID};
        String selection = SchoolEntry.COLUMN_NAME_SCHOOL_CODE + "=?";
        String[] selectionArgs = {school_code_send3};

//      Cursor with all the rows from Columns - School Code and School Name
        Cursor cursor = db.query(SchoolEntry.TABLE_NAME, projection,
                selection, selectionArgs, null, null, null);

        while(cursor.moveToNext()) {
            school_id = cursor.getString(0);

        }
        cursor.close();

        MyApplication myApplication = (MyApplication) getApplication();
        myApplication.setGlobal_autocompleteString(school_code_send); // SET GLOBAL VARIABLE autocompleteString

        myApplication = (MyApplication) getApplication();
        myApplication.setGlobal_schlID(school_id); // SET GLOBAL VARIABLE schlID

        myApplication = (MyApplication) getApplication(); // Set Global variable setGlobal_CensusYear to school code
        myApplication.setGlobal_schoolCode(school_code_send3);


        switch (v.getId()){
            case R.id.enrollment_by_grade:
                Intent intent = new Intent(SchoolSelectActivity.this, EditEnrollmentByGrade.class);
                intent.putExtra("intentID", "SchoolSelect");
                startActivity(intent);
                finish();
                break;


            case R.id.grade_class_count:
                Intent intent2 = new Intent(SchoolSelectActivity.this, EditGradeClassCount.class);
                intent2.putExtra("intentID", "SchoolSelect");
                startActivity(intent2);
                finish();
                break;

            case R.id.boarding_enrollment:

                Intent intent3 = new Intent(SchoolSelectActivity.this, EditBoardingEnrollment.class);
                intent3.putExtra("intentID", "SchoolSelect");
                startActivity(intent3);
                finish();
                break;

        }
    }

    public void onBackPressed()
    {

//        moveTaskToBack(true);

        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setTitle("Confirmation");
        builder1.setMessage("Are you sure you want to exit the App ?");
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "YES",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finish();
                        moveTaskToBack(true);

                    }
                });

        builder1.setNegativeButton(
                "NO",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();

    }

    public void onSignOut(View vLogout){
        Intent intent = new Intent(SchoolSelectActivity.this, MainActivity.class);
        intent.putExtra("Intent ID", "SchoolSelect");
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }


}

