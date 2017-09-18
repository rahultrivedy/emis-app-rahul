package com.saysweb.emis_app;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.saysweb.emis_app.data.emisContract;
import com.saysweb.emis_app.data.emisContract.GradeClassCountEntry;
import com.saysweb.emis_app.data.emisContract.SchoolEntry;
import com.saysweb.emis_app.data.emisDBHelper;

import java.util.ArrayList;
import java.util.List;

import static com.saysweb.emis_app.R.id.recyclerView;
import static com.saysweb.emis_app.R.id.recyclerViewGcc;
public class EditGradeClassCount extends AppCompatActivity {

    private RecyclerView recyclerViewGcc;
    private RecyclerView.Adapter adapter;
    private List<ListItemGcc> listItemsGcc;
    private emisDBHelper mDbHelper;
    int censusYear = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_grade_class_count);

        MyApplication myApplication = (MyApplication) getApplication();
        String year = myApplication.getGlobal_censusYear();
        censusYear = Integer.parseInt(year);

        // Get the Intent that started this activity and extract the string

        Intent intent = getIntent();
        String schoolID = intent.getStringExtra("SchoolID");
        mDbHelper = new emisDBHelper(this);

        recyclerViewGcc = (RecyclerView) findViewById(R.id.recyclerViewGcc);
        recyclerViewGcc.setHasFixedSize(false);
        recyclerViewGcc.setLayoutManager(new LinearLayoutManager(this));

        listItemsGcc = new ArrayList<>();

        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        String[] projection = {GradeClassCountEntry.COLUMN_NAME_GRADE_CODE,GradeClassCountEntry.COLUMN_NAME_CLASS_COUNT, GradeClassCountEntry.COLUMN_NAME_STUDENT_MALE_COUNT, GradeClassCountEntry.COLUMN_NAME_STUDENT_FEMALE_COUNT, GradeClassCountEntry.COLUMN_NAME_TEACHER_FEMALE_COUNT, GradeClassCountEntry.COLUMN_NAME_TEACHER_MALE_COUNT};

        String selection = GradeClassCountEntry.COLUMN_NAME_SCHL_ID + " = ? AND " + GradeClassCountEntry.COLUMN_NAME_CENSUS_YEAR + " = ?";

        String[] selectionArgs = {schoolID, year};

//        Cursor with all the rows from Columns - School Code and School Name
        Cursor cursor = db.query(GradeClassCountEntry.TABLE_NAME, projection,
                selection, selectionArgs, null, null, null);

        int cursorLength = cursor.getCount();

        String[] grade_code = new String[cursor.getCount()];
        String[] no_of_classes = new String[cursor.getCount()];
        String[] female_student_count = new String[cursor.getCount()];
        String[] male_student_count = new String[cursor.getCount()];
        String[] female_teacher_count = new String[cursor.getCount()];
        String[] male_teacher_count = new String[cursor.getCount()];

        int i = 0;

        while(cursor.moveToNext()){

            grade_code[i] = cursor.getString(0);
            no_of_classes[i] = cursor.getString(1);
            male_student_count[i] = cursor.getString(2);
            female_student_count[i] = cursor.getString(3);
            female_teacher_count[i] = cursor.getString(4);
            male_teacher_count[i] = cursor.getString(5);
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

            ListItemGcc listItemGcc = new ListItemGcc(

                    grade_code[i1],
                    no_of_classes[i1],
                    female_student_count[i1],
                    male_student_count[i1],
                    female_teacher_count[i1],
                    male_teacher_count[i1]
            );

            listItemsGcc.add(listItemGcc);

        }

        adapter = new RecycleViewAdapterGcc(listItemsGcc, this);
        recyclerViewGcc.setAdapter(adapter);

    }
}
