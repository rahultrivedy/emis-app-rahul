package com.saysweb.emis_app;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateFormat;
import android.widget.TableLayout;
import android.widget.TableLayout.LayoutParams;
import android.widget.TableRow;
import android.widget.TextView;

import com.saysweb.emis_app.data.emisContract.EnrollmentByGradesEntry;
import com.saysweb.emis_app.data.emisContract.SchoolEntry;
import com.saysweb.emis_app.data.emisDBHelper;

import java.util.Calendar;
import java.util.Locale;

public class EntryInfo extends AppCompatActivity {

    TableLayout tl;
    TableRow tr;
    TextView schoolIDTV,timeTV;

    private emisDBHelper mDbHelper;

//
//    String companies[] = {"Google","Windows","iPhone","Nokia","Samsung",
//            "Google","Windows","iPhone","Nokia","Samsung",
//            "Google","Windows","iPhone","Nokia","Samsung"};
//    String os[]       =  {"Android","Mango","iOS","Symbian","Bada",
//            "Android","Mango","iOS","Symbian","Bada",
//            "Android","Mango","iOS","Symbian","Bada"};

    String[] schlID_EBG;
    String[] updated_date_EBG;
    String[] school_name_EBG;
    String[] entryTime_EBG;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry_info);

        Intent intent = getIntent();
//        String intentID = intent.getStringExtra("intentID");

        mDbHelper = new emisDBHelper(this);


        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        String[] projection = {EnrollmentByGradesEntry.COLUMN_NAME_SCHL_ID, EnrollmentByGradesEntry.COLUMN_NAME_UPDATED_DATE};
//        String selection = null;
//        String[] selectionArgs = null;
        String sortOrder = EnrollmentByGradesEntry.COLUMN_NAME_UPDATED_DATE + " DESC";

        Cursor cursor = db.query(EnrollmentByGradesEntry.TABLE_NAME, projection,
                null, null, null, null, sortOrder);

        int cursorLength = cursor.getCount();

        schlID_EBG = new String[cursorLength];
        updated_date_EBG = new String[cursorLength];

        int i = 0;

        while(cursor.moveToNext()) {
            schlID_EBG[i] = cursor.getString(0);
            updated_date_EBG[i] = cursor.getString(1);
            i++;
        }
        cursor.close();


// GETTING SCHOOL NAMES FROM SCHLIDS
        school_name_EBG = new String[schlID_EBG.length];
        school_name_EBG = getSchoolNames(schlID_EBG);

        entryTime_EBG = new String[updated_date_EBG.length];
        entryTime_EBG = getDateTime(updated_date_EBG);


        tl = (TableLayout) findViewById(R.id.maintable);
        addHeaders();
        addData();

    }

    public void addHeaders(){

        /** Create a TableRow dynamically **/
        tr = new TableRow(this);
        tr.setLayoutParams(new LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT));

        /** Creating a TextView to add to the row **/
        TextView schoolIDTV = new TextView(this);
        schoolIDTV.setText("School IDs");
        schoolIDTV.setTextSize(20);
        schoolIDTV.setTextColor(Color.BLACK);
        schoolIDTV.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        schoolIDTV.setPadding(5, 5, 5, 0);
        tr.addView(schoolIDTV);  // Adding textView to tablerow.

        /** Creating another textview **/
        TextView timeTV = new TextView(this);
        timeTV.setText("Entry Time");
        timeTV.setTextSize(20);
        timeTV.setTextColor(Color.BLACK);
        timeTV.setPadding(5, 5, 5, 0);
        timeTV.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        tr.addView(timeTV); // Adding textView to tablerow.

        // Add the TableRow to the TableLayout
        tl.addView(tr, new TableLayout.LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT));

        // we are adding two textviews for the divider because we have two columns
        tr = new TableRow(this);
        tr.setLayoutParams(new LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT));

//        /** Creating another textview **/
//        TextView divider = new TextView(this);
//        divider.setText("-----------------");
//        divider.setTextColor(Color.GREEN);
//        divider.setPadding(5, 0, 0, 0);
//        divider.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
//        tr.addView(divider); // Adding textView to tablerow.
//
//        TextView divider2 = new TextView(this);
//        divider2.setText("-------------------------");
//        divider2.setTextColor(Color.GREEN);
//        divider2.setPadding(5, 0, 0, 0);
//        divider2.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
//        tr.addView(divider2); // Adding textView to tablerow.

        // Add the TableRow to the TableLayout
        tl.addView(tr, new TableLayout.LayoutParams(
                TableLayout.LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT));
    }

    public void addData(){

        for (int i = 0; i < school_name_EBG.length; i++)
        {
            /** Create a TableRow dynamically **/
            tr = new TableRow(this);
            tr.setLayoutParams(new LayoutParams(
                    LayoutParams.MATCH_PARENT,
                    LayoutParams.WRAP_CONTENT));

            /** Creating a TextView to add to the row **/
            schoolIDTV = new TextView(this);
            schoolIDTV.setText(school_name_EBG[i]);
            schoolIDTV.setTextColor(Color.CYAN);
            schoolIDTV.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
            schoolIDTV.setPadding(5, 5, 5, 5);
            tr.addView(schoolIDTV);  // Adding textView to tablerow.

            /** Creating another textview **/
            timeTV = new TextView(this);
            timeTV.setText(entryTime_EBG[i]);
            timeTV.setTextColor(Color.BLUE);
            timeTV.setPadding(5, 5, 5, 5);
            timeTV.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
            tr.addView(timeTV); // Adding textView to tablerow.

            // Add the TableRow to the TableLayout
            tl.addView(tr, new LayoutParams(
                    LayoutParams.MATCH_PARENT,
                    LayoutParams.WRAP_CONTENT));
        }
    }


    public String[] getSchoolNames(String[] schlID_EBG){

        Cursor cursor1 = null;
        String schoolNameEbgString = null;
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        try {
            for (int j = 0; j < schlID_EBG.length; j++) {

                String[] projection1 = {SchoolEntry.COLUMN_NAME_SCHOOL_NAME};
                String selection1 = SchoolEntry.COLUMN_NAME_SCHL_ID + " = ?";
                String[] selectionArgs1 = {schlID_EBG[j]};

                cursor1 = db.query(SchoolEntry.TABLE_NAME, projection1,
                        selection1, selectionArgs1, null, null, null);

                while (cursor1.moveToNext()) {
                    schoolNameEbgString = cursor1.getString(0);
                }
                school_name_EBG[j] = schoolNameEbgString;

            }
        }finally {
            if (cursor1 != null){
                cursor1.close();
            }
        }
        return school_name_EBG;
    }


    public String[] getDateTime(String[] updated_date_EBG){

        String entryDate;
        String entryTime;


        try{
            for (int i = 0; i < updated_date_EBG.length; i++){
                Long updated_date = Long.parseLong(updated_date_EBG[i]);
                long updated_date_milli = updated_date*1000L;
                Calendar cal = Calendar.getInstance(Locale.ENGLISH);
                cal.setTimeInMillis(updated_date_milli);
                entryDate= DateFormat.format("dd-MM-yyyy", cal).toString();
                entryTime = DateFormat.format("HH-mm", cal).toString();
                entryTime_EBG[i] = entryDate + " (" + entryTime + ")";
            }

        }
        catch(Exception e){

        }
        return entryTime_EBG;
    }



}
