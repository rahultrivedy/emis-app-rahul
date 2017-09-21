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

import com.saysweb.emis_app.data.emisContract;
import com.saysweb.emis_app.data.emisContract.SchoolEntry;
import com.saysweb.emis_app.data.emisContract.GradeEntry;
import com.saysweb.emis_app.data.emisContract.GradeClassCountEntry;
import com.saysweb.emis_app.data.emisDBHelper;

import java.util.HashMap;

import static android.R.string.no;
import static com.saysweb.emis_app.R.id.grade;

public class GradeClassCount extends AppCompatActivity {

    Spinner spinner;
    HashMap<Integer,String> spinnerMap = new HashMap<Integer, String>();
    private emisDBHelper mDbHelper;
    int censusYear;
    String schoolCode;
    String grade_sent;
    String school_id;
    EditText maleStudentsTextView;
    EditText femaleStudentsTextView;
    EditText maleTeachersTextView;
    EditText femaleTeachersTextView;
    TextView totalStudentsTextView;
    TextView totalTeachersTextView;
    String grade_code;
    String maleStudentText;
    String femaleStudentText;
    String maleTeachersText;
    String femaleTeachersText;
    int numberMaleStudents;
    int numberFemaleStudents;
    int numberMaleTeachers;
    int numberFemaleTeachers;
    int flag = 0 ;
    String year;
    String gccGradeString = "empty";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grade_class_count);
        overridePendingTransition(R.anim.slide_in_bottom, R.anim.slide_out_bottom);

        MyApplication myApplication = (MyApplication) getApplication();
        year = myApplication.getGlobal_censusYear();
        censusYear = Integer.parseInt(year);

        MyApplication myApplication1 = (MyApplication) getApplication();
        schoolCode = myApplication1.getGlobal_schoolCode();

        /*Getting Intent from School Select Activity*/

        overridePendingTransition(R.anim.slide_in_bottom, R.anim.slide_out_bottom);
        /* Actionbar*/
        /*Set the new toolbar as the Actionbar*/
        Toolbar myToolbar = (Toolbar)findViewById(R.id.my_toolbar3);
        setSupportActionBar(myToolbar);
        ActionBar actionBar2 = getSupportActionBar();
        actionBar2.setCustomView(R.layout.action_bar);
        actionBar2.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM
                | ActionBar.DISPLAY_SHOW_HOME);

        /*Getting INTENT from School Select Activity*/

        String intentID = getIntent().getStringExtra("intentID");
//         Checking intentID variable to check which class sent the intent
        if (intentID.equals("SchoolSelect")) {
            Intent intent = getIntent();
        }else if (intentID.equals("GCC")) {
            Intent intent = getIntent();
            gccGradeString = intent.getStringExtra("Grade");
            String gccNoOfClassesString = intent.getStringExtra("No of Classes");
            String gccFemaleStudentCountString = intent.getStringExtra("Female SCount");
            String gccMaleStudentCountString = intent.getStringExtra("Male SCount");
            String gccFemaleTeacherCountString = intent.getStringExtra("Female TCount");
            String gccMaleTeacherCountString = intent.getStringExtra("Male TCount");

            TextView textView = (TextView) findViewById(R.id.no_of_classes);
            textView.setText(gccNoOfClassesString);

            TextView textView1 = (TextView) findViewById(R.id.female_students);
            textView1.setText(gccFemaleStudentCountString);

            TextView textView2 = (TextView) findViewById(R.id.male_students);
            textView2.setText(gccMaleStudentCountString);

            TextView textView3 = (TextView) findViewById(R.id.female_teachers);
            textView3.setText(gccFemaleTeacherCountString);

            TextView textView4 = (TextView) findViewById(R.id.male_teachers);
            textView4.setText(gccMaleTeacherCountString);

            int gccFemaleStudentCount = Integer.parseInt(gccFemaleStudentCountString);
            int gccMaleStudentCount = Integer.parseInt(gccMaleStudentCountString);
            int gccFemaleTeacherCount = Integer.parseInt(gccFemaleTeacherCountString);
            int gccMaleTeacherCount = Integer.parseInt(gccMaleTeacherCountString);
            int gccStudentTotal = gccFemaleStudentCount + gccMaleStudentCount;
            if (gccStudentTotal != 0) {
                TextView textView5 = (TextView) findViewById(R.id.student_total);
                textView5.setText("" + gccStudentTotal);
            } else {
                TextView textView5 = (TextView) findViewById(R.id.student_total);
                textView5.setText("0");
            }

            int gccTeacherTotal = gccFemaleTeacherCount + gccMaleTeacherCount;

            if (gccTeacherTotal != 0) {
                TextView textView5 = (TextView) findViewById(R.id.teacher_total);
                textView5.setText("" + gccStudentTotal);
            } else {
                TextView textView5 = (TextView) findViewById(R.id.teacher_total);
                textView5.setText("0");
            }

            Resources r = getResources();
            float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 100, r.getDisplayMetrics());

            TextView textView6 = (TextView) findViewById(R.id.save_entry);
            textView6.setTranslationX(px);

            TextView textView7 = (TextView) findViewById(R.id.delete_entry);
            textView7.setVisibility(View.VISIBLE);


        }

        /* END INTENT CODE*/

        mDbHelper = new emisDBHelper(this);

        /* ADDING MALE AND FEMALE FEILDS */

        femaleStudentsTextView = (EditText) findViewById(R.id.female_students);
        femaleStudentsTextView.addTextChangedListener(femaleStudentsTextWatcher);

        maleStudentsTextView = (EditText) findViewById(R.id.male_students);
        maleStudentsTextView.addTextChangedListener(maleStudentsTextWatcher);

        femaleTeachersTextView = (EditText) findViewById(R.id.female_teachers);
        femaleTeachersTextView.addTextChangedListener(femaleTeachersTextWatcher);

        maleTeachersTextView = (EditText) findViewById(R.id.male_teachers);
        maleTeachersTextView.addTextChangedListener(maleTeachersTextWatcher);

        totalStudentsTextView = (TextView)findViewById(R.id.student_total);
        totalTeachersTextView = (TextView)findViewById(R.id.teacher_total);

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

        String[] spinnerArray = new String[grades.length + 1];

        int j;
        spinnerArray[0] = "--SELECT--";
        for (j = 0; j < grades.length; j++)
        {
            spinnerMap.put(j+1,grade_codes[j]);
            spinnerArray[j+1] = grades[j];
        }

        int key = 0;
        if(!gccGradeString.equals("empty")) {
            Object key_obj = EnrollmentByGrade.getKeyFromValue(spinnerMap, gccGradeString);
            if (key_obj != null) {
                key = (Integer) key_obj;
            }
        }


        spinner = (Spinner)findViewById(R.id.grade_gcc);
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


    }// Closes Main Activity

    /*ONCLICK of EDIT BUTTON - GOTO EDIT GradeClassCount PAGE*/

    public void onEditGcc(View vEdit) {

        if (vEdit.getId() == R.id.edit_button) {
            Intent intent = new Intent(this, EditGradeClassCount.class);
            intent.putExtra("SchoolID", school_id);
            startActivity(intent);
        }

    }


    /*ONCLICK of SAVE BUTTON - GOTO METHOD BELOW - EXTRACT & INSERT VALUES TO  GradeCLassCount TABLE*/

    public void onSaveGcc(View vSave){
        int noOfClasses = 0;
        int femaleStudents = 0;
        int maleStudents = 0;
        int femaleTeachers = 0;
        int maleTeachers = 0;


        Spinner gradeEntrySpinner = (Spinner) findViewById(R.id.grade_gcc);


        String gradeSpinner = gradeEntrySpinner.getSelectedItem().toString();
        if (gradeSpinner.length() != 0){
            grade_code = spinnerMap.get(spinner.getSelectedItemPosition());
        }


        EditText classesText = (EditText) findViewById(R.id.no_of_classes);
        String classesString = classesText.getText().toString();
        if (classesString.length() != 0){
            noOfClasses = Integer.parseInt(classesString);
        }

//
        String femaleStudents_string = femaleStudentsTextView.getText().toString();
        if(femaleStudents_string.length() != 0){
            femaleStudents = Integer.parseInt(femaleStudents_string);
        }
//
        String maleStudents_string = maleStudentsTextView.getText().toString();
        if(maleStudents_string.length() != 0){
            maleStudents = Integer.parseInt(maleStudents_string);
        }
//
        String femaleTeachers_string = femaleTeachersTextView.getText().toString();
        if(femaleTeachers_string.length() != 0){
            femaleTeachers = Integer.parseInt(femaleTeachers_string);
        }
        String maleTeachers_string = maleTeachersTextView.getText().toString();
        if(maleTeachers_string.length() !=0){
            maleTeachers = Integer.parseInt(maleTeachers_string);
        }


        final SQLiteDatabase db_insert = mDbHelper.getWritableDatabase();


        SQLiteDatabase db_read = mDbHelper.getReadableDatabase();

        String[] projection = {GradeClassCountEntry._ID};
        String selection = GradeClassCountEntry.COLUMN_NAME_GRADE_CODE + "= ?";
        String[] selectionArgs = {grade_code};
////
        Cursor cursor_grade = db_read.query(GradeClassCountEntry.TABLE_NAME, projection, selection,
                selectionArgs, null, null, null);
//
//
        if(cursor_grade != null && cursor_grade.getCount()!=0){

            Cursor cursor = db_read.query(GradeClassCountEntry.TABLE_NAME, projection ,GradeClassCountEntry.COLUMN_NAME_CENSUS_YEAR +  " = " + censusYear + " AND " + GradeClassCountEntry.COLUMN_NAME_SCHL_ID + " = "+ Integer.parseInt(school_id), null,null,null,null);

            if(cursor != null && cursor.getCount() !=0){
                flag = 1;
            }
            cursor.close();
            cursor_grade.close();
        }

        Long tsLong = System.currentTimeMillis()/1000;
        final String ts = tsLong.toString();

        MyApplication myApplication = (MyApplication) getApplication();
        final String uid = myApplication.getGlobal_userID();
//
        if(flag == 0) {

            ContentValues values = new ContentValues();
            values.put(GradeClassCountEntry.COLUMN_NAME_CENSUS_YEAR,censusYear);
            values.put(GradeClassCountEntry.COLUMN_NAME_CLASS_COUNT, noOfClasses);
            values.put(GradeClassCountEntry.COLUMN_NAME_GRADE_CODE, grade_code);
            values.put(GradeClassCountEntry.COLUMN_NAME_STUDENT_FEMALE_COUNT, femaleStudents);
            values.put(GradeClassCountEntry.COLUMN_NAME_STUDENT_MALE_COUNT, maleStudents);
            values.put(GradeClassCountEntry.COLUMN_NAME_TEACHER_FEMALE_COUNT, femaleTeachers);
            values.put(GradeClassCountEntry.COLUMN_NAME_TEACHER_MALE_COUNT, maleTeachers);
            values.put(GradeClassCountEntry.COLUMN_NAME_SCHL_ID, school_id);
            values.put(GradeClassCountEntry.COLUMN_NAME_CREATED_BY, uid);
            values.put(GradeClassCountEntry.COLUMN_NAME_CREATED_DATE, ts);
            values.put(GradeClassCountEntry.COLUMN_NAME_UPDATED_BY, uid);
            values.put(GradeClassCountEntry.COLUMN_NAME_UPDATED_DATE, ts);
//

//       Insert the new row, returning the primary key value of the new row
            long newRowId = db_insert.insert(GradeClassCountEntry.TABLE_NAME, null, values);

            Toast toast = Toast.makeText(this, "Form has been submitted Successfully", Toast.LENGTH_LONG);
            toast.show();

            Intent intent_refresh = new Intent(GradeClassCount.this, GradeClassCount.class);
            intent_refresh.putExtra("intentID", "SchoolActivity");
            intent_refresh.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            overridePendingTransition(0, 0);
            startActivity(intent_refresh);
        }else{

            AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
            builder1.setMessage("The form for this School (School Code - " + schoolCode + ") has alredy been filled. \n Do you want to update the data ?");
            builder1.setCancelable(true);

            final int final_noOfClasses = noOfClasses;
            final int final_femaleStudents = femaleStudents;
            final int final_maleStudents = maleStudents;
            final int final_femaleTeachers = femaleTeachers;
            final int final_maleTeachers = maleTeachers;


            builder1.setPositiveButton(
                    "UPDATE",  //TODO : When returning from edit form, if user changes the grade , record needs to be deleted and new record to be added. GEt grade code from intent and delete record
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            ContentValues values = new ContentValues();
                            values.put(GradeClassCountEntry.COLUMN_NAME_CENSUS_YEAR,censusYear);
                            values.put(GradeClassCountEntry.COLUMN_NAME_CLASS_COUNT, final_noOfClasses);
                            values.put(GradeClassCountEntry.COLUMN_NAME_GRADE_CODE, grade_code);
                            values.put(GradeClassCountEntry.COLUMN_NAME_STUDENT_FEMALE_COUNT, final_femaleStudents);
                            values.put(GradeClassCountEntry.COLUMN_NAME_STUDENT_MALE_COUNT, final_maleStudents);
                            values.put(GradeClassCountEntry.COLUMN_NAME_TEACHER_FEMALE_COUNT, final_femaleTeachers);
                            values.put(GradeClassCountEntry.COLUMN_NAME_TEACHER_MALE_COUNT, final_maleTeachers);
                            values.put(GradeClassCountEntry.COLUMN_NAME_SCHL_ID, school_id);
                            values.put(GradeClassCountEntry.COLUMN_NAME_UPDATED_BY, uid);
                            values.put(GradeClassCountEntry.COLUMN_NAME_UPDATED_DATE, ts);

                            String whereClause =  GradeClassCountEntry.COLUMN_NAME_CENSUS_YEAR +  " = ? AND " + GradeClassCountEntry.COLUMN_NAME_SCHL_ID + " = ? AND " + GradeClassCountEntry.COLUMN_NAME_GRADE_CODE + " = ?" ;
                            String[] whereArgs = {year, school_id, grade_code};

                            db_insert.update(GradeClassCountEntry.TABLE_NAME, values, whereClause, whereArgs );

                            Toast toast = Toast.makeText(GradeClassCount.this, "Form has been Updated Successfully", Toast.LENGTH_LONG);
                            toast.show();

                            Intent intent_refresh = new Intent(GradeClassCount.this, GradeClassCount.class);
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

    }/*END OF OnSaveGcc*/


    public void onDeleteGcc(View vDelete){

        final SQLiteDatabase db_delete = mDbHelper.getReadableDatabase();

        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setMessage("Are you sure you want to delete this entry ? ");
        builder1.setCancelable(true);
        builder1.setPositiveButton(
                "YES",  //TODO : When returning from edit form, if user changes the grade , record needs to be deleted and new record to be added. GEt grade code from intent and delete record
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        String grade_id = null;
                        String[] projection = {GradeClassCountEntry._ID};
                        String selection = GradeClassCountEntry.COLUMN_NAME_CENSUS_YEAR +  " = ? AND " + GradeClassCountEntry.COLUMN_NAME_SCHL_ID + " = ? AND " + GradeClassCountEntry.COLUMN_NAME_GRADE_CODE + " = ?" ;
                        String[] selectionArgs = {year, school_id, gccGradeString};

                        Cursor cursor_grade_id = db_delete.query(GradeClassCountEntry.TABLE_NAME, projection, selection,
                                selectionArgs, null, null, null);

                        while(cursor_grade_id.moveToNext()) {
                            grade_id = cursor_grade_id.getString(0);
                        }
                        cursor_grade_id.close();

                        // CODE TO DELETE ITEM FROM DB

                        String selection1 = GradeClassCountEntry._ID + " LIKE ?";
                        String[] selectionArgs1 = {grade_id};
                        db_delete.delete(GradeClassCountEntry.TABLE_NAME, selection1, selectionArgs1);

                        Toast toast = Toast.makeText(GradeClassCount.this, "Entry has been DELETED Successfully", Toast.LENGTH_LONG);
                        toast.show();

                        Intent intent_refresh = new Intent(GradeClassCount.this, GradeClassCount.class);
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

    }// END of onDeleteGcc

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

    private final TextWatcher maleStudentsTextWatcher = new TextWatcher() {
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {


        }

        public void afterTextChanged(Editable s) {
            maleStudentText = maleStudentsTextView.getText().toString();
            femaleStudentText = femaleStudentsTextView.getText().toString();
//
            if(maleStudentText.length() != 0 && femaleStudentText.length() !=0) {

                numberFemaleStudents = Integer.parseInt(femaleStudentText);
                numberMaleStudents = Integer.parseInt(maleStudentText);
                int sum = numberMaleStudents + numberFemaleStudents;
                totalStudentsTextView.setText(""+ sum);

            }else if (maleStudentText.length() == 0 && femaleStudentText.length() !=0){

                numberFemaleStudents = Integer.parseInt(femaleStudentText);
                int sum = numberFemaleStudents;
                totalStudentsTextView.setText(""+ sum);

            }else if (maleStudentText.length() != 0 && femaleStudentText.length() == 0) {

                numberMaleStudents = Integer.parseInt(maleStudentText);
                int sum = numberMaleStudents;
                totalStudentsTextView.setText(""+ sum);

            }else if (maleStudentText.length() ==0 && femaleStudentText.length() == 0){

                totalStudentsTextView.setText("0");
            }
        }
    };

    private final TextWatcher femaleStudentsTextWatcher = new TextWatcher() {
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {


        }

        public void afterTextChanged(Editable s) {
            maleStudentText = maleStudentsTextView.getText().toString();
            femaleStudentText = femaleStudentsTextView.getText().toString();
//
            if(maleStudentText.length() != 0 && femaleStudentText.length() !=0) {

                numberFemaleStudents = Integer.parseInt(femaleStudentText);
                numberMaleStudents = Integer.parseInt(maleStudentText);
                int sum = numberMaleStudents + numberFemaleStudents;
                totalStudentsTextView.setText(""+ sum);

            }else if (maleStudentText.length() == 0 && femaleStudentText.length() !=0){

                numberFemaleStudents = Integer.parseInt(femaleStudentText);
                int sum = numberFemaleStudents;
                totalStudentsTextView.setText(""+ sum);

            }else if (maleStudentText.length() != 0 && femaleStudentText.length() == 0) {

                numberMaleStudents = Integer.parseInt(maleStudentText);
                int sum = numberMaleStudents;
                totalStudentsTextView.setText(""+ sum);

            }else if (maleStudentText.length() ==0 && femaleStudentText.length() == 0){

                totalStudentsTextView.setText("0");
            }
        }
    };



    // TEACHERS TEXTWATCHER

    private final TextWatcher maleTeachersTextWatcher = new TextWatcher() {
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {


        }

        public void afterTextChanged(Editable s) {
            maleTeachersText = maleTeachersTextView.getText().toString();
            femaleTeachersText = femaleTeachersTextView.getText().toString();
//
            if(maleTeachersText.length() != 0 && femaleTeachersText.length() !=0) {

                numberFemaleTeachers = Integer.parseInt(femaleTeachersText);
                numberMaleTeachers = Integer.parseInt(maleTeachersText);
                int sum = numberMaleTeachers + numberFemaleTeachers;
                totalTeachersTextView.setText(""+ sum);

            }else if (maleTeachersText.length() == 0 && femaleTeachersText.length() !=0){

                numberFemaleTeachers = Integer.parseInt(femaleTeachersText);
                int sum = numberFemaleTeachers;
                totalTeachersTextView.setText(""+ sum);

            }else if (maleTeachersText.length() != 0 && femaleTeachersText.length() == 0) {

                numberMaleTeachers = Integer.parseInt(maleTeachersText);
                int sum = numberMaleTeachers;
                totalTeachersTextView.setText(""+ sum);

            }else if (maleTeachersText.length() ==0 && femaleTeachersText.length() == 0){

                totalTeachersTextView.setText("0");
            }
        }
    };


    private final TextWatcher femaleTeachersTextWatcher = new TextWatcher() {
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {


        }

        public void afterTextChanged(Editable s) {
            maleTeachersText = maleTeachersTextView.getText().toString();
            femaleTeachersText = femaleTeachersTextView.getText().toString();
//
            if(maleTeachersText.length() != 0 && femaleTeachersText.length() !=0) {

                numberFemaleTeachers = Integer.parseInt(femaleTeachersText);
                numberMaleTeachers = Integer.parseInt(maleTeachersText);
                int sum = numberMaleTeachers + numberFemaleTeachers;
                totalTeachersTextView.setText(""+ sum);

            }else if (maleTeachersText.length() == 0 && femaleTeachersText.length() !=0){

                numberFemaleTeachers = Integer.parseInt(femaleTeachersText);
                int sum = numberFemaleTeachers;
                totalTeachersTextView.setText(""+ sum);

            }else if (maleTeachersText.length() != 0 && femaleTeachersText.length() == 0) {

                numberMaleTeachers = Integer.parseInt(maleTeachersText);
                int sum = numberMaleTeachers;
                totalTeachersTextView.setText(""+ sum);

            }else if (maleTeachersText.length() ==0 && femaleTeachersText.length() == 0){

                totalTeachersTextView.setText("0");
            }
        }
    };



}// Closes GradeCLassCount


