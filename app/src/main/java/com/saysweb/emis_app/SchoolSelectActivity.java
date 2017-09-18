package com.saysweb.emis_app;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.MultiAutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;

import com.saysweb.emis_app.data.emisContract.UserEntry;
import com.saysweb.emis_app.data.emisContract.SchoolEntry;
import com.saysweb.emis_app.data.emisDBHelper;
import com.saysweb.emis_app.MainActivity;

import java.util.HashMap;

import static android.R.id.message;
import static android.support.constraint.R.id.parent;
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
        Toolbar myToolbar = (Toolbar)findViewById(R.id.my_toolbar2);
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
        Intent intent = getIntent();
        String userId = intent.getStringExtra("UserName");
//        year = intent.getStringExtra("CensusYear");

        MyApplication myApplication = (MyApplication) getApplication();
        year=myApplication.getGlobal_censusYear();

        String userName = helper.getUserName(userId); // Goes to emisDBHelper.java

        // Capture the layout's TextView and set the string as its text
        TextView censusTextView = (TextView) findViewById(R.id.census_year);
        censusTextView.setText(year);

        // Capture the layout's TextView and set the string as its text
        TextView userTextView = (TextView) findViewById(R.id.user_id);
        userTextView.setText(userName);

        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

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
        for (j = 0; j < school_codes.length; j++)
        {
            schlId_schoolName.put(school_codes[j],schlid[j]);
            spinnerArray[j] = school_codes[j];
        }


        textView = (AutoCompleteTextView) findViewById(school_code);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, spinnerArray);
        textView.setThreshold(2);
        textView.setAdapter(adapter);
//        textView.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());

    }


     /*
    * Method invoked when Select button is clicked
    * Make the buttons for forms visible
    */

     public void onSchoolSelect (View v){


         View view = findViewById(R.id.enrollment_by_grade);
         view.setVisibility(View.VISIBLE);


         View view1 = findViewById(R.id.boarding_enrollment);
         view1.setVisibility(View.VISIBLE);


         View view2 = findViewById(R.id.grade_class_count);
         view2.setVisibility(View.VISIBLE);

    }


    /*
    * Method invoked when Login button is clicked
    * take user inputs in a variable and call function to return password for the username - searchPass
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
        myApplication.setGlobal_schlID(school_id); // SET GLOBAL VARIABLE schlID

        myApplication = (MyApplication) getApplication(); // Set Global variable setGlobal_CensusYear to school code
        myApplication.setGlobal_schoolCode(school_code_send3);


        switch (v.getId()){
            case R.id.enrollment_by_grade:
                Intent intent = new Intent(SchoolSelectActivity.this, EditEnrollmentByGrade.class);
                intent.putExtra("intentID", "SchoolSelect");
//                intent.putExtra("SchoolCode", school_code_send3);
//                intent.putExtra("CensusYear", year);
                startActivity(intent);
                break;


            case R.id.grade_class_count:
                Intent intent2 = new Intent(SchoolSelectActivity.this, EditGradeClassCount.class);
                intent2.putExtra("intentID", "SchoolSelect");
//                intent2.putExtra("SchoolCode", school_code_send3);
//                intent2.putExtra("CensusYear", year);
                startActivity(intent2);
                break;

            case R.id.boarding_enrollment:

                Intent intent3 = new Intent(SchoolSelectActivity.this, EditBoardingEnrollment.class);
                intent3.putExtra("intentID", "SchoolSelect");
//                gettent3.putExtra("CensusYear", year);
                startActivity(intent3);
                break;

        }
    }

}
