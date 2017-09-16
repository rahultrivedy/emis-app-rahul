package com.saysweb.emis_app;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Spinner;

public class GradeClassCount extends AppCompatActivity {

    Spinner spinner;
    int censusYear;
    String schoolCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grade_class_count);
        overridePendingTransition(R.anim.slide_in_bottom, R.anim.slide_out_bottom);

        MyApplication myApplication = (MyApplication) getApplication();
        String year = myApplication.getGlobal_censusYear();
        censusYear = Integer.parseInt(year);

        MyApplication myApplication1 = (MyApplication) getApplication();
        schoolCode = myApplication1.getGlobal_schoolCode();
        /*Getting Intent from School Select Activity*/

        Intent intent = getIntent();



    }
}
