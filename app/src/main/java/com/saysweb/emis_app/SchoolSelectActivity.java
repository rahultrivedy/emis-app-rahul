package com.saysweb.emis_app;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class SchoolSelectActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_school_select);


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
    }
}
