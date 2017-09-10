package com.saysweb.emis_app;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.saysweb.emis_app.data.emisContract.UserEntry;
import com.saysweb.emis_app.data.emisContract.SchoolEntry;
import com.saysweb.emis_app.data.emisDBHelper;

import static com.saysweb.emis_app.R.id.school_code;

public class SchoolSelectActivity extends AppCompatActivity {

    /* Code for Auto Complete - Start*/

    String[] schoolCodes = new String[] {"a", "b","c","d"};

    AutoCompleteTextView textView;

    /* Code for Auto Complete - END*/


    private emisDBHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_school_select);

        /*GETTING THE INTENT from Main Activity*/

        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();
        String message = intent.getStringExtra("UserName");
        String year = intent.getStringExtra("CensusYear");

        // Capture the layout's TextView and set the string as its text
        TextView censusTextView = (TextView) findViewById(R.id.census_year);
        censusTextView.setText(year);

        // Capture the layout's TextView and set the string as its text
        TextView userTextView = (TextView) findViewById(R.id.user_id);
        userTextView.setText(message);

        // -------------------

        /*Implementing AutoComplete through ArrayAdapter*/

        helper = new emisDBHelper(this);
        schoolCodes = helper.valueOfCursor();

        textView = (AutoCompleteTextView) findViewById(school_code);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, schoolCodes);
        textView.setThreshold(2);
        textView.setAdapter(adapter);



    }

//    public String[] valueOfCursor(){
//
//        SQLiteDatabase db = helper.getReadableDatabase();
//
//        String[] projection = {SchoolEntry.COLUMN_NAME_SCHOOL_CODE,
//                SchoolEntry.COLUMN_NAME_SCHOOL_NAME};
////                String selection = UserEntry.COLUMN_PET_GENDER + “=?”;
////                String selectionArgs = new String[] { UserEntry.GENDER_FEMALE };
//
////      Cursor with all the rows from Columns - School Code and School Name
//        Cursor cursor = db.query(SchoolEntry.TABLE_NAME, projection,
//                null, null, null, null, null);
//
//        int i = 0;
//        if (cursor.moveToFirst()) {
//            do {
//                schoolCodes[i] = cursor.getString(0);
//                i++;
//            } while (cursor.moveToNext());
//        }
//        cursor.close();
//
//        return schoolCodes;
//    }

}
