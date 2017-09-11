package com.saysweb.emis_app;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class BoardingEnrollment extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boarding_enrollment);
        overridePendingTransition(R.anim.slide_in_bottom, R.anim.slide_out_bottom);


        /*Getting Intent from School Select Activity*/

        Intent intent = getIntent();
    }
}
