package com.saysweb.emis_app;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class EnrollmentByGrade extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enrollment_by_grade);


        /*Getting Intent from School Select Activity*/

        Intent intent = getIntent();
    }
}
