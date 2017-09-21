package com.saysweb.emis_app;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.TypedValue;
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
    String ebGradeString = "empty";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boarding_enrollment);
        overridePendingTransition(R.anim.slide_in_bottom, R.anim.slide_out_bottom);

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
            ebGradeString = intent.getStringExtra("Grade");
            String ebMalesString = intent.getStringExtra("Females");
            String ebFemalesString = intent.getStringExtra("Males");
//
            TextView textView = (TextView) findViewById(R.id.male_be);
            textView.setText(ebMalesString);

            TextView textView1 = (TextView) findViewById(R.id.female_be);
            textView1.setText(ebFemalesString);
//
            int ebFemales = Integer.parseInt(ebFemalesString);
            int ebMales = Integer.parseInt(ebMalesString);
            int ebTotal = ebFemales + ebMales;

            if (ebTotal != 0) {
                TextView textView6 = (TextView) findViewById(R.id.total_be);
                textView6.setText("" + ebTotal);
            } else {
                TextView textView6 = (TextView) findViewById(R.id.total_be);
                textView6.setText("0");
            }

            Resources r = getResources();
            float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 100, r.getDisplayMetrics());

            TextView textView7 = (TextView) findViewById(R.id.save_entry);
            textView7.setTranslationX(px);

            TextView textView8 = (TextView) findViewById(R.id.delete_entry);
            textView8.setVisibility(View.VISIBLE);

//
        }


        // INTENT code ends

        mDbHelper = new emisDBHelper(this);

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

        String[] spinnerArray = new String[grades.length+1];

        int j;
        spinnerArray[0] = "--SELECT--";
        for (j = 0; j < grades.length; j++)
        {
            spinnerMap.put(j+1 , grade_codes[j]);
            spinnerArray[j+1] = grades[j];
        }


        int key = 0;
        if(!ebGradeString.equals("empty")) {
            Object key_obj = EnrollmentByGrade.getKeyFromValue(spinnerMap, ebGradeString);
            if (key_obj != null) {
                key = (Integer) key_obj;
            }
        }


        spinner = (Spinner)findViewById(R.id.grade_be);
        ArrayAdapter<String> gradeAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, spinnerArray);
        gradeAdapter.setDropDownViewResource(R.layout.spinner_layout);
        spinner.setAdapter(gradeAdapter);
        if(key != 0){
            spinner.setSelection(key);
        }
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

        Long tsLong = System.currentTimeMillis()/1000;
        final String ts = tsLong.toString();

        MyApplication myApplication = (MyApplication) getApplication();
        final String uid = myApplication.getGlobal_userID();


        if(flag == 0) {

            ContentValues values = new ContentValues();
            values.put(EnrollmentByBoardingEntry.COLUMN_NAME_CENSUS_YEAR, censusYear);
            values.put(EnrollmentByBoardingEntry.COLUMN_NAME_GRADE_CODE, grade_code);
            values.put(EnrollmentByBoardingEntry.COLUMN_NAME_MALE_BOARDING_COUNT, females);
            values.put(EnrollmentByBoardingEntry.COLUMN_NAME_FEMALE_BOARDING_COUNT, males);
            values.put(EnrollmentByBoardingEntry.COLUMN_NAME_SCHL_ID, school_id);
            values.put(EnrollmentByBoardingEntry.COLUMN_NAME_CREATED_BY, uid);
            values.put(EnrollmentByBoardingEntry.COLUMN_NAME_CREATED_DATE, ts);
            values.put(EnrollmentByBoardingEntry.COLUMN_NAME_UPDATED_BY, uid);
            values.put(EnrollmentByBoardingEntry.COLUMN_NAME_UPDATED_DATE, ts);
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
                            values.put(EnrollmentByBoardingEntry.COLUMN_NAME_UPDATED_BY, uid);
                            values.put(EnrollmentByBoardingEntry.COLUMN_NAME_UPDATED_DATE, ts);

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


    // DELETE the entry by DELETE BUTTON

    public void onDeleteBE(View vDelete){

        final SQLiteDatabase db_delete = mDbHelper.getReadableDatabase();

        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setMessage("Are you sure you want to delete this entry ? ");
        builder1.setCancelable(true);
        builder1.setPositiveButton(
                "YES",  //TODO : When returning from edit form, if user changes the grade , record needs to be deleted and new record to be added. GEt grade code from intent and delete record
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        String grade_id = null;
                        String[] projection = {EnrollmentByBoardingEntry._ID};
                        String selection = EnrollmentByBoardingEntry.COLUMN_NAME_CENSUS_YEAR +  " = ? AND " + EnrollmentByBoardingEntry.COLUMN_NAME_SCHL_ID + " = ? AND " + EnrollmentByBoardingEntry.COLUMN_NAME_GRADE_CODE + " = ?" ;
                        String[] selectionArgs = {year, school_id, ebGradeString};

                        Cursor cursor_grade_id = db_delete.query(EnrollmentByBoardingEntry.TABLE_NAME, projection, selection,
                                selectionArgs, null, null, null);

                        while(cursor_grade_id.moveToNext()) {
                            grade_id = cursor_grade_id.getString(0);
                        }
                        cursor_grade_id.close();

                        // CODE TO DELETE ITEM FROM DB

                        String selection1 = EnrollmentByBoardingEntry._ID + " LIKE ?";
                        String[] selectionArgs1 = {grade_id};
                        db_delete.delete(EnrollmentByBoardingEntry.TABLE_NAME, selection1, selectionArgs1);

                        Toast toast = Toast.makeText(BoardingEnrollment.this, "Entry has been DELETED Successfully", Toast.LENGTH_LONG);
                        toast.show();

                        Intent intent_refresh = new Intent(BoardingEnrollment.this, BoardingEnrollment.class);
                        intent_refresh.putExtra("intentID", "SchoolActivity");
                        intent_refresh.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent_refresh);

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





}//End of Boarding Enrollment class
