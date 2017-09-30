package com.saysweb.emis_app;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.saysweb.emis_app.data.emisContract.EnrollmentByGradesEntry;
import com.saysweb.emis_app.data.emisContract.SchoolEntry;
import com.saysweb.emis_app.data.emisDBHelper;

import java.util.ArrayList;
import java.util.List;

public class EditEnrollmentByGrade extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<ListItem> listItems;
    private emisDBHelper mDbHelper;
    int censusYear = 0;


//    private String grade_code;
//    private String school_id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_enrollment_by_grade);

//           /* Actionbar*/
//        /*Set the new toolbar as the Actionbar*/
//        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar3);
//        setSupportActionBar(myToolbar);
//        ActionBar actionBar1 = getSupportActionBar();
//        actionBar1.setCustomView(R.layout.action_bar);
//        actionBar1.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM
//                | ActionBar.DISPLAY_SHOW_HOME);
//        actionBar1.setDisplayHomeAsUpEnabled(true);

        MyApplication myApplication = (MyApplication) getApplication();
        String year = myApplication.getGlobal_censusYear();
        censusYear = Integer.parseInt(year);

        myApplication = (MyApplication) getApplication();
        String schoolID = myApplication.getGlobal_schlID();


        // Get the Intent that started this activity and extract the string

        Intent intent = getIntent();
        String intentID = intent.getStringExtra("IntentID");
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        mDbHelper = new emisDBHelper(this);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        listItems = new ArrayList<>();


//
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        String[] projection = {EnrollmentByGradesEntry.COLUMN_NAME_GRADE_CODE,EnrollmentByGradesEntry.COLUMN_NAME_BIRTH_YEAR, EnrollmentByGradesEntry.COLUMN_NAME_FEMALE_COUNT, EnrollmentByGradesEntry.COLUMN_NAME_MALE_COUNT, EnrollmentByGradesEntry.COLUMN_NAME_UPDATED_DATE};
        String selection = EnrollmentByGradesEntry.COLUMN_NAME_SCHL_ID + " = ? AND " + EnrollmentByGradesEntry.COLUMN_NAME_CENSUS_YEAR + " = ?";
        String[] selectionArgs = {schoolID, year};
        String sortOrder = EnrollmentByGradesEntry.COLUMN_NAME_UPDATED_DATE + " DESC";

//      Cursor with all the rows from Columns - School Code and School Name
        Cursor cursor = db.query(EnrollmentByGradesEntry.TABLE_NAME, projection,
                selection, selectionArgs, null, null, sortOrder);

        int cursorLength = cursor.getCount();
        String[] grade_code = new String[cursor.getCount()];
        String[] birth_year = new String[cursor.getCount()];
        String[] female_count = new String[cursor.getCount()];
        String[] male_count = new String[cursor.getCount()];
        String[] date_updated = new String[cursor.getCount()];

        int i = 0;
//        TextView textView = (TextView) findViewById(R.id.sample);
//        textView.setText("" + cursorLength);

        while(cursor.moveToNext()) {
            grade_code[i] = cursor.getString(0);
            birth_year[i] = cursor.getString(1);
            female_count[i] = cursor.getString(2);
            male_count[i] = cursor.getString(3);
            date_updated[i] = cursor.getString(4);
            i++;
        }
        cursor.close();

        // Getting School Name And School Code From Schools Table
        String[] projection1 = {SchoolEntry.COLUMN_NAME_SCHOOL_NAME, SchoolEntry.COLUMN_NAME_SCHOOL_CODE};
        String selection1 = SchoolEntry.COLUMN_NAME_SCHL_ID + "=?";
        String[] selectionArgs1 = {schoolID};

//      Cursor with all the rows from Columns - School Code and School Name
        Cursor SchoolNamecursor = db.query(SchoolEntry.TABLE_NAME, projection1,
                selection1, selectionArgs1, null, null, null);

        String schoolName = "School Not Found";
        String schoolCode = "School Not Found";

        if (SchoolNamecursor.moveToFirst()){
            do {
                schoolName = SchoolNamecursor.getString(0);
                schoolCode = SchoolNamecursor.getString(1);

            }while (SchoolNamecursor.moveToNext());
        }
        SchoolNamecursor.close();
        TextView textView = (TextView) findViewById(R.id.name);
        textView.setText(schoolName);

        TextView textView1 = (TextView) findViewById(R.id.code);
        textView1.setText(schoolCode);

        TextView textView2 = (TextView) findViewById(R.id.census);
        textView2.setText(""+ censusYear);

        for(int i1=0; i1<cursorLength; i1++ ){

            ListItem listItem = new ListItem(

                    grade_code[i1],
                    birth_year[i1],
                    female_count[i1],
                    male_count[i1],
                    date_updated[i1]
            );

            listItems.add(listItem);

        }

        adapter = new RecyclerViewAdapter(listItems, this);
        recyclerView.setAdapter(adapter);
    }

    public void onAddNew(View vNew){
        Intent intent_add_new = new Intent(this, EnrollmentByGrade.class);
        intent_add_new.putExtra("intentID" , "SchoolSelect");
        startActivity(intent_add_new);

    }

    public void onHome(View vHome){

        Intent intent_home = new Intent(this, SchoolSelectActivity.class);
        intent_home.putExtra("intentID", "Home");
        startActivity(intent_home);
        intent_home.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        finish();

    }

}
