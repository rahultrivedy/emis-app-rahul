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

import static android.R.id.message;
import static com.saysweb.emis_app.R.id.school_code;

public class SchoolSelectActivity extends AppCompatActivity {

    /* Code for Auto Complete - Start*/

    String[] schoolCodes = new String[] {"a", "b","c","d"};

    MultiAutoCompleteTextView textView;

    /* Code for Auto Complete - END*/


    private emisDBHelper helper;
    private emisDBHelper helper2;

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
        String year = intent.getStringExtra("CensusYear");

        String userName = helper.getUserName(userId); // Goes to emisDBHelper.java

        // Capture the layout's TextView and set the string as its text
        TextView censusTextView = (TextView) findViewById(R.id.census_year);
        censusTextView.setText(year);

        // Capture the layout's TextView and set the string as its text
        TextView userTextView = (TextView) findViewById(R.id.user_id);
        userTextView.setText("Welcome " + userName);

        // -------------------




    /*Implementing AutoComplete through ArrayAdapter*/


        schoolCodes = helper.valueOfCursor(); //Getting school codes in array. Goes to emisDBHelper.java

        textView = (MultiAutoCompleteTextView) findViewById(school_code);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, schoolCodes);
        textView.setThreshold(2);
        textView.setAdapter(adapter);
        textView.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());

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

        switch (v.getId()){
            case R.id.enrollment_by_grade:
                Intent intent = new Intent(SchoolSelectActivity.this, EnrollmentByGrade.class);
//                intent.putExtra("UserName", userName);
//                intent.putExtra("CensusYear", censusYear);
                startActivity(intent);
                break;


            case R.id.grade_class_count:
                Intent intent2 = new Intent(SchoolSelectActivity.this, GradeClassCount.class);
//                intent.putExtra("UserName", userName);
//                intent.putExtra("CensusYear", censusYear);
                startActivity(intent2);
                break;

            case R.id.boarding_enrollment:
                Intent intent3 = new Intent(SchoolSelectActivity.this, BoardingEnrollment.class);
//                intent.putExtra("UserName", userName);
//                intent.putExtra("CensusYear", censusYear);
                startActivity(intent3);
                break;

        }
    }

}
