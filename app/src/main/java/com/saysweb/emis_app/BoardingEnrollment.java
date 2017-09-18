package com.saysweb.emis_app;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.saysweb.emis_app.data.emisContract.SchoolEntry;
import com.saysweb.emis_app.data.emisContract.GradeEntry;
import com.saysweb.emis_app.data.emisContract.EnrollmentByBoardingEntry;
import com.saysweb.emis_app.data.emisDBHelper;

import java.util.HashMap;

import static com.saysweb.emis_app.R.id.grade;

public class BoardingEnrollment extends AppCompatActivity {

    Spinner spinner;
    HashMap<Integer,String> spinnerMap = new HashMap<Integer, String>();
    private emisDBHelper mDbHelper;
    int censusYear;
    String schoolCode;
    String grade_sent;
    String school_id;
    String year;
    TextView femaleTextView;
    TextView maleTextView;
    TextView totalTextView;
    String maleText;
    String femaleText;
    int male;
    int female;
    String grade_code;
    int flag = 0;
    String gradeName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boarding_enrollment);
        overridePendingTransition(R.anim.slide_in_bottom, R.anim.slide_out_bottom);

        mDbHelper = new emisDBHelper(this);

         /* Actionbar*/
        /*Set the new toolbar as the Actionbar*/
        Toolbar myToolbar = (Toolbar)findViewById(R.id.my_toolbar3);
        setSupportActionBar(myToolbar);
        ActionBar actionBar2 = getSupportActionBar();
        actionBar2.setCustomView(R.layout.action_bar);
        actionBar2.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM
                | ActionBar.DISPLAY_SHOW_HOME);

        MyApplication myApplication = (MyApplication) getApplication();
        year = myApplication.getGlobal_censusYear();
        censusYear = Integer.parseInt(year);

        MyApplication myApplication1 = (MyApplication) getApplication();
        schoolCode = myApplication1.getGlobal_schoolCode();

        /*Getting Intent from School Select Activity*/


        String intentID = getIntent().getStringExtra("intentID");
//         Checking intentID variable to check which class sent the intent
        if (intentID.equals("SchoolSelect")) {
            Intent intent = getIntent();
        }
        else if (intentID.equals("EB")){
            Intent intent = getIntent();
            String ebGradeString = intent.getStringExtra("Grade");
            String ebMalesString = intent.getStringExtra("Females");
            String ebFemalesString = intent.getStringExtra("Males");
////
//             QUERY TO GET GRADE NAME FROM GRADE CODE -SPINNER SET
            SQLiteDatabase db1 = mDbHelper.getReadableDatabase();
            String[] projection = {GradeEntry.COLUMN_NAME_GRADE_NAME};
            String selection = GradeEntry.COLUMN_NAME_GRADE_CODE + " = ?";
            String[] selectionArgs = {ebGradeString};

//            TextView tv = (TextView) findViewById(R.id.sample_eb);
//            tv.setText("" + ebGradeString);

//
            Cursor cursor = db1.query(GradeEntry.TABLE_NAME, projection,
                    selection, selectionArgs, null, null, null);

            while(cursor.moveToNext()) {
                gradeName = cursor.getString(0);
            }
            cursor.close();

//            spinner.setSelection(getIndex(spinner, gradeName));
//

//             END SPINNER SET
//
            TextView textView = (TextView) findViewById(R.id.male_be);
            textView.setText(ebMalesString);

            TextView textView1 = (TextView) findViewById(R.id.female_be);
            textView1.setText(ebFemalesString);
//
            int ebFemales = Integer.parseInt(ebFemalesString);
            int ebmales = Integer.parseInt(ebMalesString);
            int ebTotal = ebFemales + ebmales;

            if (ebTotal != 0) {
                TextView textView6 = (TextView) findViewById(R.id.total_be);
                textView6.setText("" + ebTotal);
            } else {
                TextView textView6 = (TextView) findViewById(R.id.total_be);
                textView6.setText("0");
            }
//
        }


        // INTENT code ends



        /* ADDING MALE AND FEMALE FEILDS */

        femaleTextView = (EditText) findViewById(R.id.female_be);
        femaleTextView.addTextChangedListener(femaleTextWatcher);

        maleTextView = (EditText) findViewById(R.id.male_be);
        maleTextView.addTextChangedListener(maleTextWatcher);


        totalTextView = (TextView)findViewById(R.id.total_be);


  /* GRADES SPINNER CODE - with data from Grades table*/

        Cursor grades_array;
        grades_array = find_grades(schoolCode); // Method call to find grades in a school - Method Below
        String[] grades = new String[grades_array.getCount()];
        String[] grade_codes = new String[grades_array.getCount()];
        int i = 0;
        while (grades_array.moveToNext()) {
            grades[i] = grades_array.getString(0);
            grade_codes[i] = grades_array.getString(1);
            i++;
        }
        grades_array.close(); // Closing the cursor Cursor1 from find_grades method returned as grades_array.

        // Preparing Key - Value pair for Spinner - Grade Name and Grade Code

        String[] spinnerArray = new String[grades.length];

        int j;
        for (j = 0; j < grades.length; j++)
        {
            spinnerMap.put(j,grade_codes[j]);
            spinnerArray[j] = grades[j];
        }


//        TextView textView = (TextView) findViewById(R.id.hello);
//        textView.setText("hello" +" "+ grades_array[0]+" " + grades_array[1]+ " " +grades_array[2]);

        spinner = (Spinner)findViewById(R.id.grade_be);
        ArrayAdapter<String> gradeAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, spinnerArray);
        gradeAdapter.setDropDownViewResource(R.layout.spinner_layout);
        spinner.setAdapter(gradeAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                grade_sent = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        }); /* GRADES SPINNER CODE END */



    }// ENd of OnCreate Method


  /*ONCLICK of EDIT BUTTON - GOTO EDIT Drade Class Count PAGE*/ //TODO add edit code for gradeclass count here
    public void onEditBE(View vEdit) { //TODO : Uncomment the edit code. changes have already been made

        if (vEdit.getId() == R.id.edit_button) {
            Intent intent = new Intent(this, EditBoardingEnrollment.class);
            intent.putExtra("SchoolID", school_id);
            startActivity(intent);
        }

    }



     /*ONCLICK of SAVE BUTTON - GOTO METHOD BELOW - EXTRACT & INSERT VALUES TO  GradeCLassCount TABLE*/

    public void onSaveBE(View vSave) {
        int females = 0;
        int males = 0;

        Spinner gradeEntrySpinner = (Spinner) findViewById(R.id.grade_be);

        String gradeSpinner = gradeEntrySpinner.getSelectedItem().toString();
        if (gradeSpinner.length() != 0) {
            grade_code = spinnerMap.get(spinner.getSelectedItemPosition());
        }

        String females_string = femaleTextView.getText().toString();
        if (females_string.length() != 0) {
            females = Integer.parseInt(females_string);
        }
//
        String males_string = maleTextView.getText().toString();
        if (males_string.length() != 0) {
            males = Integer.parseInt(males_string);
        }


        final SQLiteDatabase db_insert = mDbHelper.getWritableDatabase();


        SQLiteDatabase db_read = mDbHelper.getReadableDatabase();

        String[] projection = {EnrollmentByBoardingEntry._ID};
        String selection = EnrollmentByBoardingEntry.COLUMN_NAME_GRADE_CODE + "= ?";
        String[] selectionArgs = {grade_code};
////
        Cursor cursor_grade = db_read.query(EnrollmentByBoardingEntry.TABLE_NAME, projection, selection,
                selectionArgs, null, null, null);
//
//
        if(cursor_grade != null && cursor_grade.getCount()!=0){

            Cursor cursor = db_read.query(EnrollmentByBoardingEntry.TABLE_NAME, projection ,EnrollmentByBoardingEntry.COLUMN_NAME_CENSUS_YEAR +  " = " + censusYear + " AND " + EnrollmentByBoardingEntry.COLUMN_NAME_SCHL_ID + " = "+ Integer.parseInt(school_id), null,null,null,null);

            if(cursor != null && cursor.getCount() !=0){
                flag = 1;
            } //if cursor closes
            cursor.close();
            cursor_grade.close();
        }// if cursor_grade closes


        if(flag == 0) {

            ContentValues values = new ContentValues();
            values.put(EnrollmentByBoardingEntry.COLUMN_NAME_CENSUS_YEAR, censusYear);
            values.put(EnrollmentByBoardingEntry.COLUMN_NAME_GRADE_CODE, grade_code);
            values.put(EnrollmentByBoardingEntry.COLUMN_NAME_MALE_BOARDING_COUNT, females);
            values.put(EnrollmentByBoardingEntry.COLUMN_NAME_FEMALE_BOARDING_COUNT, males);
            values.put(EnrollmentByBoardingEntry.COLUMN_NAME_SCHL_ID, school_id);
//

//       Insert the new row, returning the primary key value of the new row
            long newRowId = db_insert.insert(EnrollmentByBoardingEntry.TABLE_NAME, null, values);

            Toast toast = Toast.makeText(this, "Form has been submitted Successfully", Toast.LENGTH_LONG);
            toast.show();

            Intent intent_refresh = new Intent(BoardingEnrollment.this, BoardingEnrollment.class);
            intent_refresh.putExtra("intentID", "SchoolActivity");
            intent_refresh.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            overridePendingTransition(0, 0);
            startActivity(intent_refresh);

        }else{

            AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
            builder1.setMessage("The form for this School (School Code - " + schoolCode + ") has alredy been filled. \n Do you want to update the data ?");
            builder1.setCancelable(true);

            final int final_females = females;
            final int final_males = males;

            builder1.setPositiveButton(
                    "UPDATE",  //TODO : When returning from edit form, if user changes the grade , record needs to be deleted and new record to be added. GEt grade code from intent and delete record
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            ContentValues values = new ContentValues();
                            values.put(EnrollmentByBoardingEntry.COLUMN_NAME_CENSUS_YEAR, censusYear);
                            values.put(EnrollmentByBoardingEntry.COLUMN_NAME_GRADE_CODE, grade_code);
                            values.put(EnrollmentByBoardingEntry.COLUMN_NAME_MALE_BOARDING_COUNT, final_males);
                            values.put(EnrollmentByBoardingEntry.COLUMN_NAME_FEMALE_BOARDING_COUNT, final_females);
                            values.put(EnrollmentByBoardingEntry.COLUMN_NAME_SCHL_ID, school_id);

                            String whereClause =  EnrollmentByBoardingEntry.COLUMN_NAME_CENSUS_YEAR +  " = ? AND " + EnrollmentByBoardingEntry.COLUMN_NAME_SCHL_ID + " = ? AND " + EnrollmentByBoardingEntry.COLUMN_NAME_GRADE_CODE + " = ?" ;
                            String[] whereArgs = {year, school_id, grade_code};

                            db_insert.update(EnrollmentByBoardingEntry.TABLE_NAME, values, whereClause, whereArgs );

                            Toast toast = Toast.makeText(BoardingEnrollment.this, "Form has been Updated Successfully", Toast.LENGTH_LONG);
                            toast.show();

                            Intent intent_refresh = new Intent(BoardingEnrollment.this, BoardingEnrollment.class);
                            intent_refresh.putExtra("intentID", "SchoolActivity");
                            intent_refresh.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            overridePendingTransition(0, 0);
                            startActivity(intent_refresh);
//                            //TODO : ADD VALIDATION TO FORMS && Check if form data has been submitted successfully before refreshing.

                        }
                    });

            builder1.setNegativeButton(
                    "CANCEL",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.dismiss();
                        }
                    });

            AlertDialog alert11 = builder1.create();
            alert11.show();
        }



    }

    public Cursor find_grades(String school_code2){/*------------------------------------------------------------------*/

        String sector_code = "not found";
//
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        String[] projection = {SchoolEntry.COLUMN_NAME_SECTOR_CODE, SchoolEntry.COLUMN_NAME_SCHL_ID};
        String selection = SchoolEntry.COLUMN_NAME_SCHOOL_CODE + "=?";
        String[] selectionArgs = {school_code2};

//      Cursor with all the rows from Columns - School Code and School Name
        Cursor cursor = db.query(SchoolEntry.TABLE_NAME, projection,
                selection, selectionArgs, null, null, null);

        while(cursor.moveToNext()) {
            sector_code = cursor.getString(0);
            school_id = cursor.getString(1);

        }
        cursor.close();

        MyApplication myApplication = (MyApplication) getApplication();
        myApplication.setGlobal_schlID(school_id); // SET GLOBAL VARIABLE schlID

        String[] projection1 = {GradeEntry.COLUMN_NAME_GRADE_NAME, GradeEntry.COLUMN_NAME_GRADE_CODE};
        String selection1 = GradeEntry.COLUMN_NAME_SECTOR_CODE + "=?";
        String[] selectionArgs1 = {sector_code};

//      Cursor with all the rows from Columns - School Code and School Name

        return db.query(GradeEntry.TABLE_NAME, projection1,
                selection1, selectionArgs1, null, null, null);
    }






    /*TEXTWATHCHER CODE TO TAKE ACTION ON MALE AND FEMALE COUNT CHANGE*/

    private final TextWatcher maleTextWatcher = new TextWatcher() {
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {


        }

        public void afterTextChanged(Editable s) {
            maleText = maleTextView.getText().toString();
            femaleText = femaleTextView.getText().toString();
//
            if(maleText.length() != 0 && femaleText.length() !=0) {

                female = Integer.parseInt(femaleText);
                male = Integer.parseInt(maleText);
                int sum = male + female;
                totalTextView.setText(""+ sum);

            }else if (maleText.length() == 0 && femaleText.length() !=0){

                female = Integer.parseInt(femaleText);
                int sum = female;
                totalTextView.setText(""+ sum);

            }else if (maleText.length() != 0 && femaleText.length() == 0) {

                male = Integer.parseInt(maleText);
                int sum = male;
                totalTextView.setText(""+ sum);

            }else if (maleText.length() ==0 && femaleText.length() == 0){

                totalTextView.setText("0");
            }
        }
    };

    private final TextWatcher femaleTextWatcher = new TextWatcher() {
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {


        }

        public void afterTextChanged(Editable s) {
            maleText = maleTextView.getText().toString();
            femaleText = femaleTextView.getText().toString();
//
            if(maleText.length() != 0 && femaleText.length() !=0) {

                female = Integer.parseInt(femaleText);
                male = Integer.parseInt(maleText);
                int sum = male + female;
                totalTextView.setText(""+ sum);

            }else if (maleText.length() == 0 && femaleText.length() !=0){

                female = Integer.parseInt(femaleText);
                int sum = female;
                totalTextView.setText(""+ sum);

            }else if (maleText.length() != 0 && femaleText.length() == 0) {

                male = Integer.parseInt(maleText);
                int sum = male;
                totalTextView.setText(""+ sum);

            }else if (maleText.length() ==0 && femaleText.length() == 0){

                totalTextView.setText("0");
            }
        }
    };


    private int getIndex(Spinner spinner, String myString)
    {
        int index = 0;

        for (int i=0;i<spinner.getCount();i++){
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(myString)){
                index = i;
                break;
            }
        }
        return index;
    }




}//End of Boarding Enrollment class
