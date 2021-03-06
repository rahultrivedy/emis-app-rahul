package com.saysweb.emis_app;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
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

import com.saysweb.emis_app.data.emisContract.EnrollmentByGradesEntry;
import com.saysweb.emis_app.data.emisContract.GradeEntry;
import com.saysweb.emis_app.data.emisContract.SchoolEntry;
import com.saysweb.emis_app.data.emisDBHelper;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class EnrollmentByGrade extends AppCompatActivity {

    Spinner spinner;
    String grade_sent;
    private emisDBHelper mDbHelper;

    int numberMale=0;
    int numberFemale=0;
    EditText femaleTextView;
    EditText maleTextView;
    TextView totalTextView;
    String maleText;
    String femaleText;
    String school_code;
    String schoolCode;
    String grade_code;
    String cursor_id;
    String year;
    int censusYear = 0;
    String school_id;
    int flag = 0;
    HashMap<Integer,String> spinnerMap = new HashMap<Integer, String>();
    String ebgGradeString = "empty";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enrollment_by_grade);

        MyApplication myApplication = (MyApplication) getApplication();
        year = myApplication.getGlobal_censusYear();
        censusYear = Integer.parseInt(year);

        MyApplication myApplication1 = (MyApplication) getApplication();
        schoolCode = myApplication1.getGlobal_schoolCode();

        /* Actionbar*/
        /*Set the new toolbar as the Actionbar*/
        Toolbar myToolbar = (Toolbar)findViewById(R.id.my_toolbar3);
        setSupportActionBar(myToolbar);
        ActionBar actionBar2 = getSupportActionBar();
        actionBar2.setCustomView(R.layout.action_bar);
        actionBar2.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM
                | ActionBar.DISPLAY_SHOW_HOME);

        actionBar2.setDisplayHomeAsUpEnabled(true);

//        actionBar2.setTitle("EMIS - Enrollment By Grade");

        /*Getting INTENT from School Select Activity*/
        mDbHelper = new emisDBHelper(this);

        String intentID = getIntent().getStringExtra("intentID");
//         Checking intentID variable to check which class sent the intent
        if (intentID.equals("SchoolSelect")) {
            Intent intent = getIntent();
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
//            schoolCode = intent.getStringExtra("SchoolCode");
//            String year = intent.getStringExtra("CensusYear");
//            censusYear = Integer.parseInt(year);
        }else if (intentID.equals("EditEBG")){
            Intent intent = getIntent();
            overridePendingTransition(R.anim.slide_in_bottom, R.anim.slide_out_top);

            ebgGradeString = intent.getStringExtra("Grade");
            String ebgBirthYearString = intent.getStringExtra("Birth Year");
            String ebgFemaleCountString = intent.getStringExtra("Female Count");
            String ebgMaleCountString = intent.getStringExtra("Male Count");

            TextView textView = (TextView) findViewById(R.id.birth_year_entry);
            textView.setText(ebgBirthYearString );
            textView.setEnabled(false);

            TextView textView1 = (TextView) findViewById(R.id.female_entry);
            textView1.setText(ebgFemaleCountString);

            Spinner spinner = (Spinner)findViewById(R.id.grade);
            spinner.setEnabled(false);

            TextView textView2 = (TextView) findViewById(R.id.male_entry);
            textView2.setText(ebgMaleCountString);

            int ebgFemaleCount = Integer.parseInt(ebgFemaleCountString);
            int ebgMaleCount = Integer.parseInt(ebgMaleCountString);
            int ebgTotal = ebgFemaleCount + ebgMaleCount;
            if(ebgTotal != 0){
                TextView textView3 = (TextView) findViewById(R.id.total_entry);
                textView3.setText("" + ebgTotal);
            }else {
                TextView textView3 = (TextView) findViewById(R.id.total_entry);
                textView3.setText("0");
            }


            Resources r = getResources();
            float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 100, r.getDisplayMetrics());

            TextView textView4 = (TextView) findViewById(R.id.save_entry);
            textView4.setTranslationX(px);

            TextView textView5 = (TextView) findViewById(R.id.delete_entry);
            textView5.setVisibility(View.VISIBLE);

        }


        /* END INTENT CODE*/




        /* ADDING MALE AND FEMALE FEILDS */

        femaleTextView = (EditText) findViewById(R.id.female_entry);
        maleTextView = (EditText) findViewById(R.id.male_entry);

         /* Set Text Watcher listener */
        maleTextView.addTextChangedListener(maleTextWatcher);


         /* Set Text Watcher listener */
        femaleTextView.addTextChangedListener(femaleTextWatcher);



        totalTextView = (TextView) findViewById(R.id.total_entry);


//        Get School Code from AutoComplete EditText

         /*-----------------------------------------------------------*/


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

        spinnerArray[0] = "--SELECT--";
        int j;
        for (j = 0; j < grades.length; j++)
        {
            spinnerMap.put(j+1 , grade_codes[j]);
            spinnerArray[j+1] = grades[j];
        }

        int key = 0;
        if(!ebgGradeString.equals("empty")) {
            Object key_obj = getKeyFromValue(spinnerMap, ebgGradeString);
            if (key_obj != null) {
                key = (Integer) key_obj;
            }
        }





//        if(ebgGradeString != "empty"){
//            String compareValue = "some value";
//            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.select_state, android.R.layout.simple_spinner_item);
//            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//            mSpinner.setAdapter(adapter);
//            if (!compareValue.equals(null)) {
//                int spinnerPosition = adapter.getPosition(compareValue);
//                mSpinner.setSelection(spinnerPosition);
//            }
//        }

//        TextView textView = (TextView) findViewById(R.id.hello);
//        textView.setText("hello" +" "+ grades_array[0]+" " + grades_array[1]+ " " +grades_array[2]);

        spinner = (Spinner)findViewById(R.id.grade);
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
//
    } /* END OF ON-CREATE METHOD*/



    /*ONCLICK of EDIT BUTTON - GOTO EDIT ENROLLMENT PAGE*/

    public void onEditEbg(View vEdit) {

        if (vEdit.getId() == R.id.edit_button) {
            Intent intent = new Intent(this, EditEnrollmentByGrade.class);
            intent.putExtra("SchoolID", school_id);
            startActivity(intent);
        }

    }



    /*ONCLICK of SAVE BUTTON - GOTO METHOD BELOW - EXTRACT & INSERT VALUES TO  EnrollmentByGrades TABLE*/

    public void onSaveEbg(View vSave) {
        String birthString = new String();
        int birthYear = 0;
        int age = 0;
        String grade = new String();
        int males= 0;
        int females= 0;
        String total= null;

        EditText birthYearTexView = (EditText) findViewById(R.id.birth_year_entry);

        birthString = birthYearTexView.getText().toString();
        if (birthString.length() != 0) {
            birthYear = Integer.parseInt(birthString);
            Calendar calendar = Calendar.getInstance(); // to find the current year
            int currentYear = calendar.get(Calendar.YEAR);
            age = currentYear - birthYear;
        }else{
            EditText editText = (EditText) findViewById(R.id.birth_year_entry);
            editText.setError("Required");
            return;
        }
//
        Spinner gradeEntrySpinner = (Spinner) findViewById(R.id.grade);


        String gradeSpinner = gradeEntrySpinner.getSelectedItem().toString();
        if (!gradeSpinner.equals("--SELECT--")) {
            grade_code = spinnerMap.get(spinner.getSelectedItemPosition());
        }else if (gradeSpinner.equals("--SELECT--")){
            gradeEntrySpinner.requestFocus();
            TextView errorText = (TextView)gradeEntrySpinner.getSelectedView();
            errorText.setError("");
            errorText.setTextColor(Color.RED);//just to highlight that this is an error
            errorText.setText("Required");//changes the selected item text to this
            return;
        }


        String malesString = maleTextView.getText().toString();
        if(malesString.length() != 0) {
            males = Integer.parseInt(malesString);
        }

        String femalesString = femaleTextView.getText().toString();
        if(femalesString.length() != 0){
            females = Integer.parseInt(femalesString);
        }


        final SQLiteDatabase db_insert = mDbHelper.getWritableDatabase();


        SQLiteDatabase db_read = mDbHelper.getReadableDatabase();

        String[] projection = {EnrollmentByGradesEntry._ID};
        String selection = EnrollmentByGradesEntry.COLUMN_NAME_GRADE_CODE + "= ?";
        String[] selectionArgs = {grade_code};

        Cursor cursor_grade = db_read.query(EnrollmentByGradesEntry.TABLE_NAME, projection, selection,
                selectionArgs, null, null, null);


        if(cursor_grade != null && cursor_grade.getCount()!=0){

            Cursor cursor = db_read.query(EnrollmentByGradesEntry.TABLE_NAME, projection ,EnrollmentByGradesEntry.COLUMN_NAME_CENSUS_YEAR +  " = " + censusYear + " AND " + EnrollmentByGradesEntry.COLUMN_NAME_SCHL_ID + " = "+ Integer.parseInt(school_id) + " AND " + EnrollmentByGradesEntry.COLUMN_NAME_BIRTH_YEAR + " = " + birthYear, null,null,null,null);

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

        if(flag == 0) {

            ContentValues values = new ContentValues();
            values.put(EnrollmentByGradesEntry.COLUMN_NAME_BIRTH_YEAR, birthYear);
            values.put(EnrollmentByGradesEntry.COLUMN_NAME_AGE, age);
            values.put(EnrollmentByGradesEntry.COLUMN_NAME_GRADE_CODE, grade_code);
            values.put(EnrollmentByGradesEntry.COLUMN_NAME_MALE_COUNT, males);
            values.put(EnrollmentByGradesEntry.COLUMN_NAME_FEMALE_COUNT, females);
            values.put(EnrollmentByGradesEntry.COLUMN_NAME_SCHL_ID, school_id);
            values.put(EnrollmentByGradesEntry.COLUMN_NAME_CENSUS_YEAR, censusYear);
            values.put(EnrollmentByGradesEntry.COLUMN_NAME_CREATED_DATE, ts);
            values.put(EnrollmentByGradesEntry.COLUMN_NAME_CREATED_BY, uid );
            values.put(EnrollmentByGradesEntry.COLUMN_NAME_UPDATED_DATE, ts);
            values.put(EnrollmentByGradesEntry.COLUMN_NAME_UPDATED_BY, uid );

//

//       Insert the new row, returning the primary key value of the new row
            long newRowId = db_insert.insert(EnrollmentByGradesEntry.TABLE_NAME, null, values);

            Toast toast = Toast.makeText(this, "Form has been submitted Successfully", Toast.LENGTH_LONG);
            toast.show();

            Intent intent_refresh = new Intent(EnrollmentByGrade.this, EnrollmentByGrade.class);
            intent_refresh.putExtra("intentID", "SchoolActivity");
            intent_refresh.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            overridePendingTransition(0, 0);
            startActivity(intent_refresh);
        }
 else{

            AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
            builder1.setMessage("The form for this School (School Code - " + schoolCode + ") has alredy been filled. \n Do you want to update the data ?");
            builder1.setCancelable(true);

            final int finalBirthYear = birthYear;
            final int finalAge = age;
            final String finalGrade_code = grade_code;
            final int finalMales = males;
            final int finalFemales = females;
            builder1.setPositiveButton(
                    "UPDATE",  //TODO : When returning from edit form, if user changes the grade , record needs to be deleted and new record to be added. GEt grade code from intent and delete record
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            ContentValues values = new ContentValues();
                            values.put(EnrollmentByGradesEntry.COLUMN_NAME_BIRTH_YEAR, finalBirthYear);
                            values.put(EnrollmentByGradesEntry.COLUMN_NAME_AGE, finalAge);
                            values.put(EnrollmentByGradesEntry.COLUMN_NAME_GRADE_CODE, finalGrade_code);
                            values.put(EnrollmentByGradesEntry.COLUMN_NAME_MALE_COUNT, finalMales);
                            values.put(EnrollmentByGradesEntry.COLUMN_NAME_FEMALE_COUNT, finalFemales);
                            values.put(EnrollmentByGradesEntry.COLUMN_NAME_SCHL_ID, school_id);
                            values.put(EnrollmentByGradesEntry.COLUMN_NAME_CENSUS_YEAR, censusYear);
                            values.put(EnrollmentByGradesEntry.COLUMN_NAME_UPDATED_DATE, ts);
                            values.put(EnrollmentByGradesEntry.COLUMN_NAME_UPDATED_BY, uid);

                            String whereClause =  EnrollmentByGradesEntry.COLUMN_NAME_CENSUS_YEAR +  " = ? AND " + EnrollmentByGradesEntry.COLUMN_NAME_SCHL_ID + " = ? AND " + EnrollmentByGradesEntry.COLUMN_NAME_BIRTH_YEAR + " = ? AND " + EnrollmentByGradesEntry.COLUMN_NAME_GRADE_CODE + " = ?" ;
                            String[] whereArgs = {year,school_id, String.valueOf(finalBirthYear), grade_code};

                            db_insert.update(EnrollmentByGradesEntry.TABLE_NAME, values, whereClause, whereArgs );

                            Toast toast = Toast.makeText(EnrollmentByGrade.this, "Form has been Updated Successfully", Toast.LENGTH_LONG);
                            toast.show();

                            Intent intent_refresh = new Intent(EnrollmentByGrade.this, EnrollmentByGrade.class);
                            intent_refresh.putExtra("intentID", "SchoolActivity");
                            intent_refresh.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent_refresh);
                            //TODO : ADD VALIDATION TO FORMS && Check if form data has been submitted successfully before refreshing.

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
//
// DELETE the entry by DELETE BUTTON



    public void onDeleteEbg(View vDelete){

        EditText birthYearTexView = (EditText) findViewById(R.id.birth_year_entry);
        final String birthString;
        birthString = birthYearTexView.getText().toString();
        final SQLiteDatabase db_delete = mDbHelper.getReadableDatabase();

        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setMessage("Are you sure you want to delete this entry ? ");
        builder1.setCancelable(true);
        builder1.setPositiveButton(
                "YES",  //TODO : When returning from edit form, if user changes the grade , record needs to be deleted and new record to be added. GEt grade code from intent and delete record
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        String grade_id = null;
                        String[] projection = {EnrollmentByGradesEntry._ID};
                        String selection = EnrollmentByGradesEntry.COLUMN_NAME_CENSUS_YEAR +  " = ? AND " + EnrollmentByGradesEntry.COLUMN_NAME_SCHL_ID + " = ? AND " + EnrollmentByGradesEntry.COLUMN_NAME_BIRTH_YEAR + " = ? AND " + EnrollmentByGradesEntry.COLUMN_NAME_GRADE_CODE + " = ?" ;
                        String[] selectionArgs = {year,school_id, birthString, ebgGradeString};

                        Cursor cursor_grade_id = db_delete.query(EnrollmentByGradesEntry.TABLE_NAME, projection, selection,
                                selectionArgs, null, null, null);

                        while(cursor_grade_id.moveToNext()) {
                            grade_id = cursor_grade_id.getString(0);
                        }
                        cursor_grade_id.close();

                        // CODE TO DELETE ITEM FROM DB

                        String selection1 = EnrollmentByGradesEntry._ID + " LIKE ?";
                        String[] selectionArgs1 = {grade_id};
                        db_delete.delete(EnrollmentByGradesEntry.TABLE_NAME, selection1, selectionArgs1);

                        Toast toast = Toast.makeText(EnrollmentByGrade.this, "Entry has been DELETED Successfully", Toast.LENGTH_LONG);
                        toast.show();

                        Intent intent_refresh = new Intent(EnrollmentByGrade.this, EditEnrollmentByGrade.class);
                        intent_refresh.putExtra("SchoolID", school_id);
                        intent_refresh.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent_refresh);
                        finish();

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

    // Method to find grades in a school

    public Cursor find_grades(String school_code2){/*------------------------------------------------------------------*/

        String sector_code = "not found";
//
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        String[] projection = {SchoolEntry.COLUMN_NAME_SECTOR_CODE, SchoolEntry.COLUMN_NAME_SCHL_ID};
                String selection = SchoolEntry.COLUMN_NAME_SCHOOL_CODE + " =?";
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
                numberFemale = Integer.parseInt(femaleText);
                numberMale = Integer.parseInt(maleText);
                int sum = numberMale + numberFemale;
                totalTextView.setText(""+ sum);
            }else if (maleText.length() == 0 && femaleText.length() !=0){
                numberFemale = Integer.parseInt(femaleText);
                int sum = numberFemale;
                totalTextView.setText(""+ sum);
            }else if (maleText.length() != 0 && femaleText.length() == 0) {
                numberMale = Integer.parseInt(maleText);
                int sum = numberMale;
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

            femaleText = femaleTextView.getText().toString();
            maleText = maleTextView.getText().toString();

            if (femaleText.length() != 0 && maleText.length() != 0) {
                numberFemale = Integer.parseInt(femaleText);
                numberMale = Integer.parseInt(maleText);
                int sum = numberMale + numberFemale;
                totalTextView.setText("" + sum );
            }else if (femaleText.length() == 0 && maleText.length() !=0){
                numberMale = Integer.parseInt(maleText);
                int sum = numberMale;
                totalTextView.setText("" + sum );
            }else if(maleText.length() !=0 && femaleText.length() == 0) {
                numberFemale = Integer.parseInt(femaleText);
                int sum = numberFemale;
                totalTextView.setText(""+sum);
            }else if (femaleText.length() == 0 && maleText.length() == 0){
                totalTextView.setText("0");
            }
        }
    };


    public static Object getKeyFromValue(Map hm, Object value) {
        for (Object o : hm.keySet()) {
            if (hm.get(o).equals(value)) {
                return o;
            }
        }
        return null;
    }

    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_top, R.anim.slide_out_bottom);
    }
//

//    CODE to overwrite default up button on actionbar. DO NOT DELETE
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case android.R.id.home:
////                 todo: goto back activity from here
//
//                Intent intent = new Intent(EnrollmentByGrade.this, EditEnrollmentByGrade.class);
//                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
//                overridePendingTransition(R.anim.slide_in_top, R.anim.slide_out_bottom);
//                startActivity(intent);
//
//                finish();
//                return true;
//
//            default:
//                return super.onOptionsItemSelected(item);
//        }
//    }



}
