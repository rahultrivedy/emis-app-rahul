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

import com.saysweb.emis_app.data.emisContract.EnrollmentByBoardingEntry;
import com.saysweb.emis_app.data.emisContract.SchoolEntry;
import com.saysweb.emis_app.data.emisDBHelper;

import java.util.ArrayList;
import java.util.List;

public class EditBoardingEnrollment extends AppCompatActivity {

    private RecyclerView recyclerViewEB;
    private RecyclerView.Adapter adapter;
    private List<ListItemEB> listItemsEB;
    private emisDBHelper mDbHelper;
    int censusYear = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_boarding_enrollment);

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

        recyclerViewEB = (RecyclerView) findViewById(R.id.recyclerViewEB);
        recyclerViewEB.setHasFixedSize(false);
        recyclerViewEB.setLayoutManager(new LinearLayoutManager(this));

        listItemsEB = new ArrayList<>();

        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        String[] projection = {EnrollmentByBoardingEntry.COLUMN_NAME_GRADE_CODE,EnrollmentByBoardingEntry.COLUMN_NAME_MALE_BOARDING_COUNT, EnrollmentByBoardingEntry.COLUMN_NAME_FEMALE_BOARDING_COUNT, EnrollmentByBoardingEntry.COLUMN_NAME_UPDATED_DATE };

        String selection = EnrollmentByBoardingEntry.COLUMN_NAME_SCHL_ID + " = ? AND " + EnrollmentByBoardingEntry.COLUMN_NAME_CENSUS_YEAR + " = ?";

        String[] selectionArgs = {schoolID, year};

        String sortOrder = EnrollmentByBoardingEntry.COLUMN_NAME_UPDATED_DATE + " DESC";

        //        Cursor with all the rows from Columns - School Code and School Name
        Cursor cursor = db.query(EnrollmentByBoardingEntry.TABLE_NAME, projection,
                selection, selectionArgs, null, null, sortOrder);

        int cursorLength = cursor.getCount();

        String[] grade_code = new String[cursor.getCount()];
        String[] males = new String[cursor.getCount()];
        String[] females= new String[cursor.getCount()];
        String[] date_updated = new String[cursor.getCount()];

        int i = 0;

        while(cursor.moveToNext()){

            grade_code[i] = cursor.getString(0);
            males[i] = cursor.getString(1);
            females[i] = cursor.getString(2);
            date_updated[i] = cursor.getString(3);
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

            ListItemEB listItemEB = new ListItemEB(

                    grade_code[i1],
                    females[i1],
                    males[i1],
                    date_updated[i1]

            );

            listItemsEB.add(listItemEB);

        }

        adapter = new RecycleViewAdapterEB(listItemsEB, this);
        recyclerViewEB.setAdapter(adapter);

    }

    public void onAddNew_EB(View vNew){
        Intent intent_add_new_EB = new Intent(this, BoardingEnrollment.class);
        intent_add_new_EB.putExtra("intentID" , "SchoolSelect");
        startActivity(intent_add_new_EB);

    }

    public void onHome_EB(View vHome){

        Intent intent_home = new Intent(this, SchoolSelectActivity.class);
        intent_home.putExtra("intentID", "Home");
        startActivity(intent_home);
        intent_home.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        finish();

    }



}
