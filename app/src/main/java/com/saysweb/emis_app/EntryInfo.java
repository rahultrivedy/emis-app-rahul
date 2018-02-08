package com.saysweb.emis_app;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.saysweb.emis_app.data.emisContract.EnrollmentByBoardingEntry;
import com.saysweb.emis_app.data.emisContract.EnrollmentByGradesEntry;
import com.saysweb.emis_app.data.emisContract.GradeClassCountEntry;
import com.saysweb.emis_app.data.emisContract.SchoolEntry;
import com.saysweb.emis_app.data.emisDBHelper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import static android.text.TextUtils.split;
import static com.saysweb.emis_app.R.id.school_search;
import static com.saysweb.emis_app.R.id.titleGCC;

public class EntryInfo extends AppCompatActivity {

    TableLayout tl;
    TableRow tr;
    TextView schoolIDTV,timeTV, gradeTV, censusTV, birthTV;
    ImageView syncIV;

    private emisDBHelper mDbHelper;
    AutoCompleteTextView textView;


    String[] school_name;
    String[] entry_time;

    String[] schlID_EBG;
    String[] updated_date_EBG;
    String[] school_name_EBG;
    String[] entryTime_EBG;
    String[] grade_code_EBG;
    String[] census_year_EBG;
    String[] birth_year_EBG;
    String[] sync_status_EBG;

    String[] schlID_GCC;
    String[] updated_date_GCC;
    String[] school_name_GCC;
    String[] entryTime_GCC;
    String[] grade_code_GCC;
    String[] census_year_GCC;
    String[] sync_status_GCC;

    String[] schlID_EB;
    String[] updated_date_EB;
    String[] school_name_EB;
    String[] entryTime_EB;
    String[] grade_code_EB;
    String[] census_year_EB;
    String[] sync_status_EB;
    HashMap<String,String> schlId_schoolName = new HashMap<String, String>();

    String school_id = null;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry_info);

        Intent intent = getIntent();

        mDbHelper = new emisDBHelper(this);

        tableEBG();
        tableGCC();
        tableEB();

        /*Implementing AutoComplete through ArrayAdapter*/
        Cursor schoolCodesCursor;

        schoolCodesCursor = mDbHelper.valueOfCursor(); //Getting school codes in array. Goes to emisDBHelper.java
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

        textView = findViewById(school_search);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, spinnerArray);
        textView.setThreshold(2);
        textView.setAdapter(adapter);

        textView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(arg1.getApplicationWindowToken(), 0);

                SQLiteDatabase db = mDbHelper.getReadableDatabase();
                String school_code_send;
                EditText text = findViewById(R.id.school_search);
                school_code_send = text.getText().toString();
                String[] myString = split(school_code_send, " - ");
                String school_code_send2 = myString[1];
                String school_code_send3 = school_code_send2.replaceAll("\\p{Z}", "");
//                TextView tv = (TextView) findViewById(R.id.sample_tv);
//                tv.setText(school_code_send3);

                String[] projection = {SchoolEntry.COLUMN_NAME_SCHL_ID};
                String selection = SchoolEntry.COLUMN_NAME_SCHOOL_CODE + "=?";
                String[] selectionArgs = {school_code_send3};

//      Cursor with all the rows from Columns - School Code and School Name
                Cursor cursor = db.query(SchoolEntry.TABLE_NAME, projection,
                        selection, selectionArgs, null, null, null);

                while (cursor.moveToNext()) {
                    school_id = cursor.getString(0);

                }
                cursor.close();

                tableEBG();
                tableGCC();
                tableEB();

            }
        });

    }



    public void tableEBG(){

        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        Cursor cursor;

        if(school_id != null){
            String[] projection = {EnrollmentByGradesEntry.COLUMN_NAME_SCHL_ID, EnrollmentByGradesEntry.COLUMN_NAME_UPDATED_DATE, EnrollmentByGradesEntry.COLUMN_NAME_GRADE_CODE, EnrollmentByGradesEntry.COLUMN_NAME_CENSUS_YEAR, EnrollmentByGradesEntry.COLUMN_NAME_BIRTH_YEAR, EnrollmentByGradesEntry.COLUMN_NAME_SYNC_STATUS};

            String selection = EnrollmentByGradesEntry.COLUMN_NAME_SCHL_ID + " = ?";

            String[] selectionArgs = {school_id};

            String sortOrder = EnrollmentByGradesEntry.COLUMN_NAME_UPDATED_DATE + " DESC";

            cursor = db.query(EnrollmentByGradesEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);


            TableLayout tableLayout = (TableLayout) findViewById(R.id.maintable);

            tableLayout.removeAllViews();


        }else {
            String[] projection = {EnrollmentByGradesEntry.COLUMN_NAME_SCHL_ID, EnrollmentByGradesEntry.COLUMN_NAME_UPDATED_DATE, EnrollmentByGradesEntry.COLUMN_NAME_GRADE_CODE, EnrollmentByGradesEntry.COLUMN_NAME_CENSUS_YEAR, EnrollmentByGradesEntry.COLUMN_NAME_BIRTH_YEAR, EnrollmentByGradesEntry.COLUMN_NAME_SYNC_STATUS};

            String sortOrder = EnrollmentByGradesEntry.COLUMN_NAME_UPDATED_DATE + " DESC";

            cursor = db.query(EnrollmentByGradesEntry.TABLE_NAME, projection, null, null, null, null, sortOrder);

        }



        int cursorLength = cursor.getCount();

        if(cursor != null || cursorLength != 0) {
            schlID_EBG = new String[cursorLength];
            updated_date_EBG = new String[cursorLength];
            grade_code_EBG = new String[cursorLength];
            census_year_EBG = new String[cursorLength];
            birth_year_EBG = new String[cursorLength];
            sync_status_EBG = new String[cursorLength];

            int i = 0;

            while (cursor.moveToNext()) {
                schlID_EBG[i] = cursor.getString(0);
                updated_date_EBG[i] = cursor.getString(1);
                grade_code_EBG[i] = cursor.getString(2);
                census_year_EBG[i] = cursor.getString(3);
                birth_year_EBG[i] = cursor.getString(4);
                sync_status_EBG[i] = cursor.getString(5);

                i++;
            }
            cursor.close();


// GETTING SCHOOL NAMES FROM SCHLIDS
            school_name_EBG = new String[schlID_EBG.length];
            school_name = new String[schlID_EBG.length];
            school_name_EBG = getSchoolNames(schlID_EBG);
            entryTime_EBG = new String[updated_date_EBG.length];
            entry_time = new String[updated_date_EBG.length];
            entryTime_EBG = getDateTime(updated_date_EBG);



            tl = (TableLayout) findViewById(R.id.maintable);
            addHeaders();
            addData();
        }

    }

    public void addHeaders(){


        /** Create a TableRow dynamically **/
        tr = new TableRow(this);
        tr.setLayoutParams(new TableLayout.LayoutParams(
                TableLayout.LayoutParams.WRAP_CONTENT,
                TableLayout.LayoutParams.WRAP_CONTENT,
                1.0f));

        /** Creating a TextView to add to the row **/
        TextView syncIDTV = new TextView(this);
        syncIDTV.setText("");
        syncIDTV.setTextSize(16);
        syncIDTV.setTextColor(Color.BLACK);
        syncIDTV.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        syncIDTV.setPadding(15, 15, 15, 0);
        tr.addView(syncIDTV);  // Adding textView to tablerow.


        /** Creating a TextView to add to the row **/
        TextView schoolIDTV = new TextView(this);
        schoolIDTV.setText("School");
        schoolIDTV.setTextSize(16);
        schoolIDTV.setTextColor(Color.BLACK);
        schoolIDTV.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        schoolIDTV.setPadding(15, 15, 15, 0);
        tr.addView(schoolIDTV);  // Adding textView to tablerow.

        /** Creating a TextView to add to the row **/
        TextView censusIDTV = new TextView(this);
        censusIDTV.setText("Census\nYear");
        censusIDTV.setTextSize(16);
        censusIDTV.setTextColor(Color.BLACK);
        censusIDTV.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        censusIDTV.setPadding(15, 15, 15, 0);
        tr.addView(censusIDTV);  // Adding textView to tablerow.

        /** Creating a TextView to add to the row **/
        TextView birthIDTV = new TextView(this);
        birthIDTV.setText("Birth\nYear");
        birthIDTV.setTextSize(16);
        birthIDTV.setTextColor(Color.BLACK);
        birthIDTV.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        birthIDTV.setPadding(15, 15, 15, 0);
        tr.addView(birthIDTV);  // Adding textView to tablerow.

        /** Creating a TextView to add to the row **/
        TextView gradeIDTV = new TextView(this);
        gradeIDTV.setText("Grade");
        gradeIDTV.setTextSize(16);
        gradeIDTV.setTextColor(Color.BLACK);
        gradeIDTV.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        gradeIDTV.setPadding(15, 15, 15, 0);
        tr.addView(gradeIDTV);  // Adding textView to tablerow.


        /** Creating another textview **/
        TextView timeTV = new TextView(this);
        timeTV.setText("Update\nTime");
        timeTV.setTextSize(16);
        timeTV.setTextColor(Color.BLACK);
        timeTV.setPadding(15, 15, 15, 0);
        timeTV.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        tr.addView(timeTV); // Adding textView to tablerow.

        // Add the TableRow to the TableLayout
        tl.addView(tr, new TableLayout.LayoutParams(
                TableLayout.LayoutParams.WRAP_CONTENT,
                TableLayout.LayoutParams.WRAP_CONTENT,
                1.0f));

        // we are adding two textviews for the divider because we have two columns
        tr = new TableRow(this);
        tr.setLayoutParams(new TableLayout.LayoutParams(
                TableLayout.LayoutParams.WRAP_CONTENT,
                TableLayout.LayoutParams.WRAP_CONTENT,
                1.0f));


        // Add the TableRow to the TableLayout
        tl.addView(tr, new TableLayout.LayoutParams(
                TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT,
                1.0f));
    }

//    row.addView(t, new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f));

    public void addData(){

        for (int i = 0; i < school_name_EBG.length; i++)
        {
            /** Create a TableRow dynamically **/
            tr = new TableRow(this);
            tr.setLayoutParams(new TableLayout.LayoutParams(
                    TableLayout.LayoutParams.WRAP_CONTENT,
                    TableLayout.LayoutParams.WRAP_CONTENT));

            if(sync_status_EBG[i].equals("1")) {

                /** Creating a TextView to add to the row **/
                syncIV = new ImageView(this);
                syncIV.setImageResource(R.drawable.ic_check_circle);
                syncIV.setPadding(0,0,10,0);
                syncIV.setPadding(15, 15, 15, 15);
                tr.addView(syncIV);  // Adding textView to tablerow.

            }else{

                /** Creating a TextView to add to the row **/
                syncIV = new ImageView(this);
                syncIV.setImageResource(R.drawable.ic_sync);
                syncIV.setPadding(15, 15, 15, 15);
                tr.addView(syncIV);  // Adding textView to tablerow.
            }
//

            /** Creating a TextView to add to the row **/
            schoolIDTV = new TextView(this);
            schoolIDTV.setText(school_name_EBG[i]);
            schoolIDTV.setMaxWidth(30);
            schoolIDTV.setTextColor(Color.parseColor("#1565C0"));
            schoolIDTV.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
            schoolIDTV.setPadding(15, 15, 15, 15);
            tr.addView(schoolIDTV);  // Adding textView to tablerow.
//
            /** Creating a TextView to add to the row **/
            censusTV = new TextView(this);
            censusTV.setText(census_year_EBG[i]);
            censusTV.setTextColor(Color.parseColor("#1565C0"));
            censusTV.setTypeface(Typeface.DEFAULT);
            censusTV.setPadding(15, 15, 15, 15);
            tr.addView(censusTV);  // Adding textView to tablerow.

            /** Creating a TextView to add to the row **/
            birthTV = new TextView(this);
            birthTV.setText(birth_year_EBG[i]);
            birthTV.setTextColor(Color.parseColor("#1565C0"));
            birthTV.setTypeface(Typeface.DEFAULT);
            birthTV.setPadding(15, 15, 15, 15);
            tr.addView(birthTV);  // Adding textView to tablerow.

            /** Creating a TextView to add to the row **/
            gradeTV = new TextView(this);
            gradeTV.setText(grade_code_EBG[i]);
            gradeTV.setTextColor(Color.parseColor("#1565C0"));
            gradeTV.setTypeface(Typeface.DEFAULT);
            gradeTV.setPadding(15, 15, 15, 15);
            tr.addView(gradeTV);  // Adding textView to tablerow.

            /** Creating another textview **/
            timeTV = new TextView(this);
            timeTV.setText(entryTime_EBG[i]);
            timeTV.setTextColor(Color.parseColor("#1565C0"));
            timeTV.setPadding(15, 15, 15, 15);
            timeTV.setTypeface(Typeface.DEFAULT);
            tr.addView(timeTV); // Adding textView to tablerow.

            // Add the TableRow to the TableLayout
            tl.addView(tr, new TableLayout.LayoutParams(
                    TableRow.LayoutParams.WRAP_CONTENT,
                    TableRow.LayoutParams.WRAP_CONTENT,
                    1f));
        }

    }


    public void tableGCC() {

        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        Cursor cursor;

        if(school_id != null) {

            String[] projection = {GradeClassCountEntry.COLUMN_NAME_SCHL_ID, GradeClassCountEntry.COLUMN_NAME_UPDATED_DATE, GradeClassCountEntry.COLUMN_NAME_GRADE_CODE, GradeClassCountEntry.COLUMN_NAME_CENSUS_YEAR, GradeClassCountEntry.COLUMN_NAME_SYNC_STATUS};

            String selection = GradeClassCountEntry.COLUMN_NAME_SCHL_ID + " = ?";

            String[] selectionArgs = {school_id};

            String sortOrder = GradeClassCountEntry.COLUMN_NAME_UPDATED_DATE + " DESC";

            cursor = db.query(GradeClassCountEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);


            TableLayout tableLayout = (TableLayout) findViewById(R.id.maintableGCC);

            tableLayout.removeAllViews();

        } else{

                String[] projection = {GradeClassCountEntry.COLUMN_NAME_SCHL_ID, GradeClassCountEntry.COLUMN_NAME_UPDATED_DATE, GradeClassCountEntry.COLUMN_NAME_GRADE_CODE, GradeClassCountEntry.COLUMN_NAME_CENSUS_YEAR, GradeClassCountEntry.COLUMN_NAME_SYNC_STATUS};


                String sortOrder = GradeClassCountEntry.COLUMN_NAME_UPDATED_DATE + " DESC";

                cursor = db.query(GradeClassCountEntry.TABLE_NAME, projection,
                        null, null, null, null, sortOrder);

            }

        int cursorLength = cursor.getCount();

        if(cursor != null || cursorLength != 0) {

            schlID_GCC = new String[cursorLength];
            updated_date_GCC = new String[cursorLength];
            grade_code_GCC = new String[cursorLength];
            census_year_GCC = new String[cursorLength];
            sync_status_GCC = new String[cursorLength];

            int i = 0;

            while (cursor.moveToNext()) {
                schlID_GCC[i] = cursor.getString(0);
                updated_date_GCC[i] = cursor.getString(1);
                grade_code_GCC[i] = cursor.getString(2);
                census_year_GCC[i] = cursor.getString(3);
                sync_status_GCC[i] = cursor.getString(4);

                i++;
            }
            cursor.close();


            // GETTING SCHOOL NAMES FROM SCHLIDS
            school_name_GCC = new String[schlID_GCC.length];
            school_name = new String[schlID_GCC.length];
            school_name_GCC = getSchoolNames(schlID_GCC);

            entryTime_GCC = new String[updated_date_GCC.length];
            entry_time = new String[updated_date_GCC.length];
            entryTime_GCC = getDateTime(updated_date_GCC);


            tl = (TableLayout) findViewById(R.id.maintableGCC);


            addHeadersGCC();
            addDataGCC();

        }
    }


    public void addHeadersGCC(){


        /** Create a TableRow dynamically **/
        tr = new TableRow(this);
        tr.setLayoutParams(new TableLayout.LayoutParams(
                TableLayout.LayoutParams.WRAP_CONTENT,
                TableLayout.LayoutParams.WRAP_CONTENT,
                1.0f));

        /** Creating a TextView to add to the row **/
        TextView syncIDTV = new TextView(this);
        syncIDTV.setText("");
        syncIDTV.setTextSize(16);
        syncIDTV.setTextColor(Color.BLACK);
        syncIDTV.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        syncIDTV.setPadding(15, 15, 15, 0);
        tr.addView(syncIDTV);  // Adding textView to tablerow.


        /** Creating a TextView to add to the row **/
        TextView schoolIDTV = new TextView(this);
        schoolIDTV.setText("School");
        schoolIDTV.setTextSize(16);
        schoolIDTV.setTextColor(Color.BLACK);
        schoolIDTV.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        schoolIDTV.setPadding(15, 15, 15, 0);
        tr.addView(schoolIDTV);  // Adding textView to tablerow.

        /** Creating a TextView to add to the row **/
        TextView censusIDTV = new TextView(this);
        censusIDTV.setText("Census\nYear");
        censusIDTV.setTextSize(16);
        censusIDTV.setTextColor(Color.BLACK);
        censusIDTV.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        censusIDTV.setPadding(15, 15, 15, 0);
        tr.addView(censusIDTV);  // Adding textView to tablerow.


        /** Creating a TextView to add to the row **/
        TextView gradeIDTV = new TextView(this);
        gradeIDTV.setText("Grade");
        gradeIDTV.setTextSize(16);
        gradeIDTV.setTextColor(Color.BLACK);
        gradeIDTV.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        gradeIDTV.setPadding(15, 15, 15, 0);
        tr.addView(gradeIDTV);  // Adding textView to tablerow.


        /** Creating another textview **/
        TextView timeTV = new TextView(this);
        timeTV.setText("Update\nTime");
        timeTV.setTextSize(16);
        timeTV.setTextColor(Color.BLACK);
        timeTV.setPadding(15, 15, 15, 0);
        timeTV.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        tr.addView(timeTV); // Adding textView to tablerow.

        // Add the TableRow to the TableLayout
        tl.addView(tr, new TableLayout.LayoutParams(
                TableLayout.LayoutParams.WRAP_CONTENT,
                TableLayout.LayoutParams.WRAP_CONTENT,
                1.0f));

        // we are adding two textviews for the divider because we have two columns
        tr = new TableRow(this);
        tr.setLayoutParams(new TableLayout.LayoutParams(
                TableLayout.LayoutParams.WRAP_CONTENT,
                TableLayout.LayoutParams.WRAP_CONTENT,
                1.0f));


        // Add the TableRow to the TableLayout
        tl.addView(tr, new TableLayout.LayoutParams(
                TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT,
                1.0f));
    }


    public void addDataGCC(){

        for (int i = 0; i < school_name_GCC.length; i++)
        {
            /** Create a TableRow dynamically **/
            tr = new TableRow(this);
            tr.setLayoutParams(new TableLayout.LayoutParams(
                    TableLayout.LayoutParams.WRAP_CONTENT,
                    TableLayout.LayoutParams.WRAP_CONTENT));

            if(sync_status_GCC[i].equals("1")) {

                /** Creating a TextView to add to the row **/
                ImageView syncIV = new ImageView(this);
                syncIV.setImageResource(R.drawable.ic_check_circle);
                syncIV.setPadding(0,0,10,0);
                syncIV.setPadding(15, 15, 15, 15);
                tr.addView(syncIV);  // Adding textView to tablerow.

            }else{

                /** Creating a TextView to add to the row **/
                ImageView syncIV = new ImageView(this);
                syncIV.setImageResource(R.drawable.ic_sync);
                syncIV.setPadding(15, 15, 15, 15);
                tr.addView(syncIV);  // Adding textView to tablerow.
            }
//

            /** Creating a TextView to add to the row **/
            TextView schoolIDTV = new TextView(this);
            schoolIDTV.setText(school_name_GCC[i]);
            schoolIDTV.setMaxWidth(30);
            schoolIDTV.setTextColor(Color.parseColor("#1565C0"));
            schoolIDTV.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
            schoolIDTV.setPadding(15, 15, 15, 15);
            tr.addView(schoolIDTV);  // Adding textView to tablerow.
//
            /** Creating a TextView to add to the row **/
            TextView censusTV = new TextView(this);
            censusTV.setText(census_year_GCC[i]);
            censusTV.setTextColor(Color.parseColor("#1565C0"));
            censusTV.setTypeface(Typeface.DEFAULT);
            censusTV.setPadding(15, 15, 15, 15);
            tr.addView(censusTV);  // Adding textView to tablerow.

            /** Creating a TextView to add to the row **/
            TextView gradeTV = new TextView(this);
            gradeTV.setText(grade_code_GCC[i]);
            gradeTV.setTextColor(Color.parseColor("#1565C0"));
            gradeTV.setTypeface(Typeface.DEFAULT);
            gradeTV.setPadding(15, 15, 15, 15);
            tr.addView(gradeTV);  // Adding textView to tablerow.

            /** Creating another textview **/
            TextView timeTV = new TextView(this);
            timeTV.setText(entryTime_GCC[i]);
            timeTV.setTextColor(Color.parseColor("#1565C0"));
            timeTV.setPadding(15, 15, 15, 15);
            timeTV.setTypeface(Typeface.DEFAULT);
            tr.addView(timeTV); // Adding textView to tablerow.

            // Add the TableRow to the TableLayout
            tl.addView(tr, new TableLayout.LayoutParams(
                    TableRow.LayoutParams.WRAP_CONTENT,
                    TableRow.LayoutParams.WRAP_CONTENT,
                    1f));
        }

    }



    public void tableEB(){

        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        Cursor cursor;

        if(school_id != null) {

            String[] projection = {EnrollmentByBoardingEntry.COLUMN_NAME_SCHL_ID, EnrollmentByBoardingEntry.COLUMN_NAME_UPDATED_DATE, EnrollmentByBoardingEntry.COLUMN_NAME_GRADE_CODE, EnrollmentByBoardingEntry.COLUMN_NAME_CENSUS_YEAR, EnrollmentByBoardingEntry.COLUMN_NAME_SYNC_STATUS};

            String selection = EnrollmentByBoardingEntry.COLUMN_NAME_SCHL_ID + " = ?";

            String[] selectionArgs = {school_id};

            String sortOrder = EnrollmentByBoardingEntry.COLUMN_NAME_UPDATED_DATE + " DESC";

            cursor = db.query(EnrollmentByBoardingEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);


            TableLayout tableLayout = (TableLayout) findViewById(R.id.maintableEB);

            tableLayout.removeAllViews();

        } else {

            String[] projection = {EnrollmentByBoardingEntry.COLUMN_NAME_SCHL_ID, EnrollmentByBoardingEntry.COLUMN_NAME_UPDATED_DATE, EnrollmentByBoardingEntry.COLUMN_NAME_GRADE_CODE, EnrollmentByBoardingEntry.COLUMN_NAME_CENSUS_YEAR, EnrollmentByBoardingEntry.COLUMN_NAME_SYNC_STATUS};


            String sortOrder = EnrollmentByBoardingEntry.COLUMN_NAME_UPDATED_DATE + " DESC";

            cursor = db.query(EnrollmentByBoardingEntry.TABLE_NAME, projection,
                    null, null, null, null, sortOrder);
        }

        int cursorLength = cursor.getCount();

        if(cursor != null || cursorLength != 0) {
            schlID_EB = new String[cursorLength];
            updated_date_EB = new String[cursorLength];
            grade_code_EB = new String[cursorLength];
            census_year_EB = new String[cursorLength];
            sync_status_EB = new String[cursorLength];

            int i = 0;

            while (cursor.moveToNext()) {
                schlID_EB[i] = cursor.getString(0);
                updated_date_EB[i] = cursor.getString(1);
                grade_code_EB[i] = cursor.getString(2);
                census_year_EB[i] = cursor.getString(3);
                sync_status_EB[i] = cursor.getString(4);

                i++;
            }
            cursor.close();


// GETTING SCHOOL NAMES FROM SCHLIDS
            school_name_EB = new String[schlID_EB.length];
            school_name = new String[schlID_EB.length];
            school_name_EB = getSchoolNames(schlID_EB);

            entryTime_EB = new String[updated_date_EB.length];
            entry_time = new String[updated_date_EB.length];
            entryTime_EB = getDateTime(updated_date_EB);


            tl = (TableLayout) findViewById(R.id.maintableEB);


            addHeadersEB();
            addDataEB();

        }
    }


    public void addHeadersEB(){


        /** Create a TableRow dynamically **/
        tr = new TableRow(this);
        tr.setLayoutParams(new TableLayout.LayoutParams(
                TableLayout.LayoutParams.WRAP_CONTENT,
                TableLayout.LayoutParams.WRAP_CONTENT,
                1.0f));

        /** Creating a TextView to add to the row **/
        TextView syncIDTV = new TextView(this);
        syncIDTV.setText("");
        syncIDTV.setTextSize(16);
        syncIDTV.setTextColor(Color.BLACK);
        syncIDTV.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        syncIDTV.setPadding(15, 15, 15, 0);
        tr.addView(syncIDTV);  // Adding textView to tablerow.


        /** Creating a TextView to add to the row **/
        TextView schoolIDTV = new TextView(this);
        schoolIDTV.setText("School");
        schoolIDTV.setTextSize(16);
        schoolIDTV.setTextColor(Color.BLACK);
        schoolIDTV.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        schoolIDTV.setPadding(15, 15, 15, 0);
        tr.addView(schoolIDTV);  // Adding textView to tablerow.

        /** Creating a TextView to add to the row **/
        TextView censusIDTV = new TextView(this);
        censusIDTV.setText("Census\nYear");
        censusIDTV.setTextSize(16);
        censusIDTV.setTextColor(Color.BLACK);
        censusIDTV.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        censusIDTV.setPadding(15, 15, 15, 0);
        tr.addView(censusIDTV);  // Adding textView to tablerow.


        /** Creating a TextView to add to the row **/
        TextView gradeIDTV = new TextView(this);
        gradeIDTV.setText("Grade");
        gradeIDTV.setTextSize(16);
        gradeIDTV.setTextColor(Color.BLACK);
        gradeIDTV.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        gradeIDTV.setPadding(15, 15, 15, 0);
        tr.addView(gradeIDTV);  // Adding textView to tablerow.


        /** Creating another textview **/
        TextView timeTV = new TextView(this);
        timeTV.setText("Update\nTime");
        timeTV.setTextSize(16);
        timeTV.setTextColor(Color.BLACK);
        timeTV.setPadding(15, 15, 15, 0);
        timeTV.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        tr.addView(timeTV); // Adding textView to tablerow.

        // Add the TableRow to the TableLayout
        tl.addView(tr, new TableLayout.LayoutParams(
                TableLayout.LayoutParams.WRAP_CONTENT,
                TableLayout.LayoutParams.WRAP_CONTENT,
                1.0f));

        // we are adding two textviews for the divider because we have two columns
        tr = new TableRow(this);
        tr.setLayoutParams(new TableLayout.LayoutParams(
                TableLayout.LayoutParams.WRAP_CONTENT,
                TableLayout.LayoutParams.WRAP_CONTENT,
                1.0f));


        // Add the TableRow to the TableLayout
        tl.addView(tr, new TableLayout.LayoutParams(
                TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT,
                1.0f));
    }


    public void addDataEB(){

        for (int i = 0; i < school_name_EB.length; i++)
        {
            /** Create a TableRow dynamically **/
            tr = new TableRow(this);
            tr.setLayoutParams(new TableLayout.LayoutParams(
                    TableLayout.LayoutParams.WRAP_CONTENT,
                    TableLayout.LayoutParams.WRAP_CONTENT));

            if(sync_status_EB[i].equals("1")) {

                /** Creating a TextView to add to the row **/
                ImageView syncIV = new ImageView(this);
                syncIV.setImageResource(R.drawable.ic_check_circle);
                syncIV.setPadding(0,0,10,0);
                syncIV.setPadding(15, 15, 15, 15);
                tr.addView(syncIV);  // Adding textView to tablerow.

            }else{

                /** Creating a TextView to add to the row **/
                ImageView syncIV = new ImageView(this);
                syncIV.setImageResource(R.drawable.ic_sync);
                syncIV.setPadding(15, 15, 15, 15);
                tr.addView(syncIV);  // Adding textView to tablerow.
            }
//

            /** Creating a TextView to add to the row **/
            TextView schoolIDTV = new TextView(this);
            schoolIDTV.setText(school_name_EB[i]);
            schoolIDTV.setMaxWidth(30);
            schoolIDTV.setTextColor(Color.parseColor("#1565C0"));
            schoolIDTV.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
            schoolIDTV.setPadding(15, 15, 15, 15);
            tr.addView(schoolIDTV);  // Adding textView to tablerow.
//
            /** Creating a TextView to add to the row **/
            TextView censusTV = new TextView(this);
            censusTV.setText(census_year_EB[i]);
            censusTV.setTextColor(Color.parseColor("#1565C0"));
            censusTV.setTypeface(Typeface.DEFAULT);
            censusTV.setPadding(15, 15, 15, 15);
            tr.addView(censusTV);  // Adding textView to tablerow.

            /** Creating a TextView to add to the row **/
            TextView gradeTV = new TextView(this);
            gradeTV.setText(grade_code_EB[i]);
            gradeTV.setTextColor(Color.parseColor("#1565C0"));
            gradeTV.setTypeface(Typeface.DEFAULT);
            gradeTV.setPadding(15, 15, 15, 15);
            tr.addView(gradeTV);  // Adding textView to tablerow.

            /** Creating another textview **/
            TextView timeTV = new TextView(this);
            timeTV.setText(entryTime_EB[i]);
            timeTV.setTextColor(Color.parseColor("#1565C0"));
            timeTV.setPadding(15, 15, 15, 15);
            timeTV.setTypeface(Typeface.DEFAULT);
            tr.addView(timeTV); // Adding textView to tablerow.

            // Add the TableRow to the TableLayout
            tl.addView(tr, new TableLayout.LayoutParams(
                    TableRow.LayoutParams.WRAP_CONTENT,
                    TableRow.LayoutParams.WRAP_CONTENT,
                    1f));
        }

    }





    public String[] getSchoolNames(String[] schlID_temp){

        Cursor cursor1 = null;
        String schoolNameString = null;
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        try {
            for (int j = 0; j < schlID_temp.length; j++) {

                String[] projection1 = {SchoolEntry.COLUMN_NAME_SCHOOL_NAME};
                String selection1 = SchoolEntry.COLUMN_NAME_SCHL_ID + " = ?";
                String[] selectionArgs1 = {schlID_temp[j]};

                cursor1 = db.query(SchoolEntry.TABLE_NAME, projection1,
                        selection1, selectionArgs1, null, null, null);

                while (cursor1.moveToNext()) {
                    schoolNameString = cursor1.getString(0);
                }

                school_name[j] = schoolNameString;

            }
        }finally {
            if (cursor1 != null){
                cursor1.close();
            }
        }
        return school_name;
    }


    public String[] getDateTime(String[] updated_date_temp){

        String entryDate_temp;
        String entryTime_temp;


        try{
            for (int i = 0; i < updated_date_temp.length; i++){
                Long updated_date = Long.parseLong(updated_date_temp[i]);
                long updated_date_milli = updated_date*1000L;
                Calendar cal = Calendar.getInstance();
                cal.setTimeInMillis(updated_date_milli);
                entryDate_temp = DateFormat.format("dd-MM-yyyy", cal).toString();
                entryTime_temp = DateFormat.format("HH:mm", cal).toString();
                entry_time[i] = entryDate_temp + " \n (" + entryTime_temp + ")";
            }

        }
        catch(Exception e){

        }
        return entry_time;
    }




    /*HIDING AND MAKING VISIBLE THE TABLES WHEN THEIR TITLES ARE CLICKED*/
    public void onClickEBG(View v) {


        TableLayout tableLayout = (TableLayout) findViewById(R.id.maintable);
        tableLayout.setVisibility(tableLayout.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);

    }

    public void onClickGCC(View v) {


        TableLayout tableLayout = (TableLayout) findViewById(R.id.maintableGCC);
        tableLayout.setVisibility(tableLayout.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);

    }

    public void onClickEB(View v) {


        TableLayout tableLayout = (TableLayout) findViewById(R.id.maintableEB);
        tableLayout.setVisibility(tableLayout.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);

    }

    public void onHome(View vHome){

        Intent intent_home = new Intent(this, SchoolSelectActivity.class);
        intent_home.putExtra("intentID", "Home");
        startActivity(intent_home);
        intent_home.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        finish();

    }




}
