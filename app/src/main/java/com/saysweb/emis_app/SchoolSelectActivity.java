package com.saysweb.emis_app;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.saysweb.emis_app.data.emisContract.SchoolEntry;
import com.saysweb.emis_app.data.emisDBHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import cz.msebera.android.httpclient.Header;

import static android.text.TextUtils.split;
import static com.saysweb.emis_app.R.id.school_code;

public class SchoolSelectActivity extends AppCompatActivity {

    /* Code for Auto Complete - Start*/


    AutoCompleteTextView textView;

    /*RELATED TO BROADCASTING DATA TO SERVER*/

    public static final String URL_SAVE_NAME = "http://192.168.0.3/SqliteSync/saveName.php";
    public static final String URL_GET_FROM_SQL = "http://192.168.0.3/SqliteSync/";
    public static final int NAME_SYNCED_WITH_SERVER = 1;
    public static final int NAME_NOT_SYNCED_WITH_SERVER = 0;
    ProgressDialog prgDialog;


    //Broadcast receiver to know the sync status
    private BroadcastReceiver broadcastReceiver;

//    public static final String DATA_SAVED_BROADCAST = "net.simplifiedcoding.datasaved";



    /*END - RELATED TO BROADCASTING DATA TO SERVER*/



    /* Code for Auto Complete - END*/


    private emisDBHelper helper;
//    private emisDBHelper helper2;
    String year;
    int schlID;
    HashMap<String,String> schlId_schoolName = new HashMap<String, String>();
    String school_id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_school_select);


        /* Actionbar*/
        /*Set the new toolbar as the Actionbar*/
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar2);
        setSupportActionBar(myToolbar);
        ActionBar actionBar1 = getSupportActionBar();
        actionBar1.setCustomView(R.layout.action_bar);
        actionBar1.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM
                | ActionBar.DISPLAY_SHOW_HOME);

//        actionBar1.setTitle("EMIS");
//        actionBar1.setSubtitle("Education Management Information System");


        helper = new emisDBHelper(this);


//
        /*GETTING THE INTENT from Main Activity*/

        // Get the Intent that started this activity and extract the string
        String intentID = getIntent().getStringExtra("intentID");
//         Checking intentID variable to check which class sent the intent
        if (intentID.equals("Home")) {
            Intent intent = getIntent();
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_righ);

            MyApplication myApplication = (MyApplication) getApplication();
            String autocompleteString = myApplication.getGlobal_autocompleteString();

            if(autocompleteString.length()!=0 ){
                AutoCompleteTextView autoCompleteTextView = (AutoCompleteTextView) findViewById(school_code);
                autoCompleteTextView.setText(autocompleteString);
                autoCompleteTextView.clearFocus();

                View view = findViewById(R.id.enrollment_by_grade);
                view.setVisibility(View.VISIBLE);


                View view1 = findViewById(R.id.boarding_enrollment);
                view1.setVisibility(View.VISIBLE);


                View view2 = findViewById(R.id.grade_class_count);
                view2.setVisibility(View.VISIBLE);


            }

        }else if (intentID.equals("MainActivity")){
            Intent intent = getIntent();
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        }

        MyApplication myApplication = (MyApplication) getApplication();
        year = myApplication.getGlobal_censusYear();

        myApplication = (MyApplication) getApplication();
        String userId = myApplication.getGlobal_userID();

        String userName = helper.getUserName(userId); // Goes to emisDBHelper.java

        // Capture the layout's TextView and set the string as its text
        TextView censusTextView = (TextView) findViewById(R.id.census_year);
        censusTextView.setText(year);

        // Capture the layout's TextView and set the string as its text
        TextView userTextView = (TextView) findViewById(R.id.user_id);
        userTextView.setText(userName);


        // -------------------




    /*Implementing AutoComplete through ArrayAdapter*/


        Cursor schoolCodesCursor;
        schoolCodesCursor = helper.valueOfCursor(); //Getting school codes in array. Goes to emisDBHelper.java
        String[] school_codes = new String[schoolCodesCursor.getCount()];
        String[] schlid = new String[schoolCodesCursor.getCount()];
        int i4 = 0;
        while (schoolCodesCursor.moveToNext()) {
            school_codes[i4] = schoolCodesCursor.getString(1) + " - " + schoolCodesCursor.getString(0);
            schlid[i4] = schoolCodesCursor.getString(2);
            i4++;
        }
        schoolCodesCursor.close();

        // Preparing Key - Value pair for AUTOCOMPLETE - SCHLID and SCHOOL CODE

        String[] spinnerArray = new String[school_codes.length];

        int j;
        for (j = 0; j < school_codes.length; j++) {
            schlId_schoolName.put(school_codes[j], schlid[j]);
            spinnerArray[j] = school_codes[j];
        }


        textView = (AutoCompleteTextView) findViewById(school_code);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, spinnerArray);
        textView.setThreshold(2);
        textView.setAdapter(adapter);
        textView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

                    InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(arg1.getApplicationWindowToken(), 0);


                    View view = findViewById(R.id.enrollment_by_grade);
                    view.setVisibility(View.VISIBLE);


                    View view1 = findViewById(R.id.boarding_enrollment);
                    view1.setVisibility(View.VISIBLE);


                    View view2 = findViewById(R.id.grade_class_count);
                    view2.setVisibility(View.VISIBLE);

            }
        });

    }


    /*
    * Method invoked when one of three buttons is clicked
    * takes autocomplete string as input breaks them and saves school code to send to next activity
    */
    public void onFormSelect(View v) {
        String school_code_send;
        EditText text = (EditText) findViewById(R.id.school_code);
        school_code_send = text.getText().toString();
        String[] myString = split(school_code_send," - ");
        String school_code_send2 = myString[1];
        String school_code_send3 = school_code_send2.replaceAll("\\p{Z}","");

        SQLiteDatabase db = helper.getReadableDatabase();

        String[] projection = {SchoolEntry.COLUMN_NAME_SCHL_ID};
        String selection = SchoolEntry.COLUMN_NAME_SCHOOL_CODE + "=?";
        String[] selectionArgs = {school_code_send3};

//      Cursor with all the rows from Columns - School Code and School Name
        Cursor cursor = db.query(SchoolEntry.TABLE_NAME, projection,
                selection, selectionArgs, null, null, null);

        while(cursor.moveToNext()) {
            school_id = cursor.getString(0);

        }
        cursor.close();

        MyApplication myApplication = (MyApplication) getApplication();
        myApplication.setGlobal_autocompleteString(school_code_send); // SET GLOBAL VARIABLE autocompleteString

        myApplication = (MyApplication) getApplication();
        myApplication.setGlobal_schlID(school_id); // SET GLOBAL VARIABLE schlID

        myApplication = (MyApplication) getApplication(); // Set Global variable setGlobal_CensusYear to school code
        myApplication.setGlobal_schoolCode(school_code_send3);


        switch (v.getId()){
            case R.id.enrollment_by_grade:
                Intent intent = new Intent(SchoolSelectActivity.this, EditEnrollmentByGrade.class);
                intent.putExtra("intentID", "SchoolSelect");
                startActivity(intent);
                break;


            case R.id.grade_class_count:
                Intent intent2 = new Intent(SchoolSelectActivity.this, EditGradeClassCount.class);
                intent2.putExtra("intentID", "SchoolSelect");
                startActivity(intent2);
                break;

            case R.id.boarding_enrollment:

                Intent intent3 = new Intent(SchoolSelectActivity.this, EditBoardingEnrollment.class);
                intent3.putExtra("intentID", "SchoolSelect");
                startActivity(intent3);
                break;

        }
    }

    public void onBackPressed()
    {

//        moveTaskToBack(true);

        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setTitle("Confirmation");
        builder1.setMessage("Are you sure you want to exit the App ?");
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "YES",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finish();
                        moveTaskToBack(true);
                        android.os.Process.killProcess(android.os.Process.myPid());

                    }
                });

        builder1.setNegativeButton(
                "NO",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();

    }

    public void onSignOut(View vLogout){
        Intent intent = new Intent(SchoolSelectActivity.this, MainActivity.class);
        intent.putExtra("intentID", "SchoolSelect");
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();

    }

    public void onEntryInfo(View vEntryInfo){

        Intent intent = new Intent(SchoolSelectActivity.this, EntryInfo.class);
//        intent.putExtra("intentID", "SchoolSelect");
        startActivity(intent);
    }

    public void onSync(View syncView){


        if(checkNetworkConnection()) {


            SQLiteDatabase db = helper.getReadableDatabase();
//            SQLiteDatabase db1 = helper.getWritableDatabase();

            Cursor cursor = emisDBHelper.readEBG(db);
            int cursor_length = cursor.getCount();
//

            while (cursor.moveToNext()) {
                saveEBGtoServer(
                    cursor.getString(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4),
                    cursor.getString(5),
                    cursor.getString(6),
                    cursor.getString(7),
                    cursor.getString(8),
                    cursor.getString(9),
                    cursor.getString(10),
                    cursor.getString(11)
                );

            }
            cursor.close();

        }else {

            Toast toast = Toast.makeText(this, "No Internet Connection Available", Toast.LENGTH_LONG);
            toast.show();
        }
    }

    public boolean checkNetworkConnection(){
        ConnectivityManager connectivityManager = (ConnectivityManager) this.getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null &&networkInfo.isConnected();
    }




    public void saveEBGtoServer(final String id_ebg, final String grade_code_ebg, final String census_year_ebg, final String birth_year_ebg, final String age_ebg, final String male_count_ebg, final String female_count_ebg, final String schl_id_ebg, final String created_date_ebg, final String created_by_ebg, final String  updated_date_ebg, final String updated_by_ebg){
//
//        final SQLiteDatabase db = helper.getReadableDatabase();
        final SQLiteDatabase db1 = helper.getWritableDatabase();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_SAVE_NAME,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                            JSONObject obj = new JSONObject(response);
                            if (!obj.getBoolean("error")) {


                                //updating the status in sqlite
                                emisDBHelper.updateEBG(id_ebg, NAME_SYNCED_WITH_SERVER, db1);
                                Toast toast = Toast.makeText(SchoolSelectActivity.this, "Success", Toast.LENGTH_LONG);
                                toast.show();


                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Context context = SchoolSelectActivity.this;

                        Toast toast = Toast.makeText(context, "ERROR", Toast.LENGTH_LONG);
                        toast.show();

                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("id", id_ebg);
                params.put("grade_code", grade_code_ebg);
                params.put("census_year", census_year_ebg);
                params.put("birth_year", birth_year_ebg);
                params.put("age", age_ebg);
                params.put("male_count", male_count_ebg);
                params.put("female_count", female_count_ebg);
                params.put("schl_id", schl_id_ebg);
                params.put("created_date", created_date_ebg);
                params.put("created_by", created_by_ebg);
                params.put("updated_date", updated_date_ebg);
                params.put("updated_by", updated_by_ebg);

                return params;
            }
        };

        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);

    }



    /* Fetching Data from SQL*/

    public void onGetFromSQL(View FromSQLView){

        if(checkNetworkConnection()) {

            syncSQLiteMySQLDB();

        }else{

            Toast toast = Toast.makeText(this, "No Internet Connection Available", Toast.LENGTH_LONG);
            toast.show();
        }
    }

    // Method to Sync MySQL to SQLite DB
    public void syncSQLiteMySQLDB() {

        AsyncHttpClient client = new AsyncHttpClient();

        // Http Request Params Object
        RequestParams params = new RequestParams();
        // Show ProgressBar
//        prgDialog.show();
        // Make Http call to getusers.php
        client.post("http://192.168.0.3/SqliteSync/getdistricts.php", params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {


                try {
                    String str = new String(responseBody, "UTF-8");

                    updateSQLite(str);

                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                if (statusCode == 404) {
                    Toast.makeText(getApplicationContext(), "Requested resource not found", Toast.LENGTH_LONG).show();
                } else if (statusCode == 500) {
                    Toast.makeText(getApplicationContext(), "Something went wrong at server end", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Unexpected Error occcured! [Most common Error: Device might not be connected to Internet]",
                            Toast.LENGTH_LONG).show();
                }

            }


        });
    }


    public void updateSQLite(String response) {
        ArrayList<HashMap<String, String>> usersynclist;
        usersynclist = new ArrayList<HashMap<String, String>>();
        SQLiteDatabase db = helper.getWritableDatabase();

//        TextView textview = (TextView) findViewById(R.id.sample);
//        textview.setText(response);

        // Create GSON object
        Gson gson = new GsonBuilder().create();
        try {
            // Extract JSON array from the response
            JSONArray arr = new JSONArray(response);
            System.out.println(arr.length());
            // If no of array elements is not zero
            if (arr.length() != 0) {
                // Loop through each array element, get JSON object which has userid and username
                for (int i = 0; i < arr.length(); i++) {
                    // Get JSON object
                    JSONObject obj = (JSONObject) arr.get(i);


                    // DB QueryValues Object to insert into SQLite
                    HashMap<String, String> queryValues = new HashMap<String, String>();

                    queryValues.put("Id", obj.get("Id").toString());
                    queryValues.put("District_Code", obj.get("District_Code").toString());
                    queryValues.put("District_Name", obj.get("District_Name").toString());
                    queryValues.put("Province_Code", obj.get("Province_Code").toString());
                    queryValues.put("Old_District_Code", obj.get("Old_District_Code").toString());
                    queryValues.put("Old_District_Name", obj.get("Old_District_Name").toString());
                    // Insert User into SQLite DB

                    emisDBHelper.insertUser(queryValues, db);

                    Toast toast = Toast.makeText(SchoolSelectActivity.this, "Downloaded data successfully", Toast.LENGTH_LONG);
                    toast.show();
//                    HashMap<String, String> map = new HashMap<String, String>();

// Add status for each User in Hashmap
//                    map.put("Id", obj.get("userId").toString());
//                    map.put("status", "1");
//                    usersynclist.add(map);
                }
                // Inform Remote MySQL DB about the completion of Sync activity by passing Sync status of Users
//                updateMySQLSyncSts(gson.toJson(usersynclist));
//                // Reload the Main Activity
//                reloadActivity();
            }
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}




