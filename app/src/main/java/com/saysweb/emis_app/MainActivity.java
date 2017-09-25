package com.saysweb.emis_app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.saysweb.emis_app.data.emisDBHelper;

public class MainActivity extends AppCompatActivity {

    Spinner spinner;
    ArrayAdapter<CharSequence> adapter;

    //use CursorAdapter to add data from DB.
    private emisDBHelper mDbHelper;
    String censusYear;

    @Override
    //--This is the main activity



    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        String intentId = getIntent().getStringExtra("Intent ID");
        if (intentId != null && intentId.length() != 0 && intentId.equals("SchoolSelect")){

            Intent intent = getIntent();
            Toast toast = Toast.makeText(this, "You have been Logged Out", Toast.LENGTH_LONG);
            toast.show();
        }


//        String intentId = intent.getStringExtra("Intent ID");

        /*Set the new toolbar as the Actionbar*/
        Toolbar myToolbar = (Toolbar)findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        ActionBar actionBar = getSupportActionBar();
//        actionBar.setTitle("EMIS--");
        actionBar.setCustomView(R.layout.action_bar);
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM
                | ActionBar.DISPLAY_SHOW_HOME);


        ImageButton imageButton = (ImageButton) findViewById(R.id.show_pwd);
        final EditText editText = (EditText) findViewById(R.id.password);
        imageButton.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {

                switch ( event.getAction() ) {
                    case MotionEvent.ACTION_DOWN:
                        editText.setInputType(InputType.TYPE_CLASS_TEXT);
                        break;
                    case MotionEvent.ACTION_UP:
                        editText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                        break;
                }
                return true;
            }
        });




        mDbHelper = new emisDBHelper(this);

    /* Census Spinner Code*/

        spinner = (Spinner) findViewById(R.id.census_year);
        adapter = ArrayAdapter.createFromResource(this, R.array.census_year, R.layout.spinner_layout); //note : census_year linked to strings.xml
        adapter.setDropDownViewResource(R.layout.spinner_layout);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                censusYear = adapterView.getItemAtPosition(i).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    /* Census Spinner Code End */

//        displayDatabaseInfo();
    }


    /*
    * Method invoked when Login button is clicked
    * take user inputs in a variable and call function to return password for the username - searchPass
    */
    public void onButtonClick(View v) {
        if (v.getId() == R.id.button_login) {

            EditText user = (EditText) findViewById(R.id.username);
            String userName = user.getText().toString();

            EditText pwd = (EditText) findViewById(R.id.password);
            String password = pwd.getText().toString();

            String entered_password = mDbHelper.searchPass(userName); // Method call to return password for the username provided -Goes to emisDBHelper.java

            // Check if password matches the username

            if (password.equals(entered_password)) {

                MyApplication myApplication = (MyApplication) getApplication();
                myApplication.setGlobal_userID(userName);

                myApplication = (MyApplication) getApplication();
                myApplication.setGlobal_censusYear(censusYear);

                Intent intent = new Intent(MainActivity.this, SchoolSelectActivity.class);
                intent.putExtra("UserName", userName);
                intent.putExtra("intentID", "MainActivity");

//                intent.putExtra("CensusYear", censusYear);

                startActivity(intent);
                finish();

            } else {
                Toast toast_fail = Toast.makeText(this, "User Name and Password do not match", Toast.LENGTH_SHORT);
                toast_fail.show();
            }
        }
    }

    public void onBackPressed()
    {

        finish();

        moveTaskToBack(true);
        android.os.Process.killProcess(android.os.Process.myPid());


    }


}
