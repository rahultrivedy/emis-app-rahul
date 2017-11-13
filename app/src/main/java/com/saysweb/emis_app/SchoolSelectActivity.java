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
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
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

//    public static final String URL_SYNC_EBG = "http://192.168.0.3/SqliteSync/syncEBG.php";
    public static final String URL_SYNC_EBG = "http://devloved.com/SqliteSync/syncEBG.php";
//    public static final String URL_SYNC_GCC = "http://doedbs03.educationpng.gov.pg/SqliteSync/syncGCC.php";
    public static final String URL_SYNC_GCC = "http://devloved.com/SqliteSync/syncGCC.php";
    public static final String URL_SYNC_EB = "http://devloved.com/SqliteSync/syncEB.php";
    public static final String URL_GET_FROM_SQL = "http://devloved.com/SqliteSync/";
    public static final int SYNCED_WITH_SERVER = 1;
    public static final int NOT_SYNCED_WITH_SERVER = 0;
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


//        /* Actionbar*/
//        /*Set the new toolbar as the Actionbar*/
//        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar2);
//        setSupportActionBar(myToolbar);
//        ActionBar actionBar1 = getSupportActionBar();
//        actionBar1.setCustomView(R.layout.action_bar);
//        actionBar1.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM
//                | ActionBar.DISPLAY_SHOW_HOME);

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



 /* METHOD to Show Report -  Entry Info */

    public void onEntryInfo(MenuItem EntryInfoMenuItem){

        Intent intent = new Intent(SchoolSelectActivity.this, EntryInfo.class);
//        intent.putExtra("intentID", "SchoolSelect");
        startActivity(intent);
    }


/*Method called on SYNC BUTTON */

    public void onSync(MenuItem syncMenuitem){


        if(checkNetworkConnection()) {

           // syncEBGToServer();
            syncGCCToServer();
         //   syncEBToServer();


        }else {

            Toast toast = Toast.makeText(this, "No Internet Connection Available", Toast.LENGTH_LONG);
            toast.show();
        }
    }


// SYNC EBG to SERVER
    public void syncEBGToServer(){

        SQLiteDatabase db = helper.getReadableDatabase();

        Cursor cursor = emisDBHelper.readEBG(db);
        int cursor_length = cursor.getCount();

        while (cursor.moveToNext()) {
            // Goto Method - To send data to server
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

    }


    public void saveEBGtoServer(final String id_ebg, final String grade_code_ebg, final String census_year_ebg, final String birth_year_ebg, final String age_ebg, final String male_count_ebg, final String female_count_ebg, final String schl_id_ebg, final String created_date_ebg, final String created_by_ebg, final String  updated_date_ebg, final String updated_by_ebg){
//
//        final SQLiteDatabase db = helper.getReadableDatabase();
        final SQLiteDatabase db1 = helper.getWritableDatabase();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_SYNC_EBG,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                            JSONObject obj = new JSONObject(response);
                            if (!obj.getBoolean("error")) {
                                //updating the status in sqlite
                                emisDBHelper.updateEBG(id_ebg, SYNCED_WITH_SERVER, db1);
                                Toast toast = Toast.makeText(SchoolSelectActivity.this, "Sync Successfull !", Toast.LENGTH_LONG);
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
//                params.put("id", id_ebg);
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
                params.put("app_flag", "1");

                return params;
            }
        };

        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);

    }
    // END EBG SYNC

    public void syncGCCToServer(){

        SQLiteDatabase db = helper.getReadableDatabase();

        Cursor cursor = emisDBHelper.readGCC(db);
        int cursor_length = cursor.getCount();

        while (cursor.moveToNext()) {
            // Goto Method - To send data to server
            saveGCCtoServer(
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
                    cursor.getString(11),
                    cursor.getString(12),
                    cursor.getString(13),
                    cursor.getString(14)
            );

        }
        cursor.close();

    }


    public void saveGCCtoServer(final String id ,final String id_gcc, final String census_year_gcc, final String grade_code_gcc,  final String class_count_gcc, final String schl_id_gcc, final String student_male_count_gcc, final String student_female_count_gcc, final String teacher_male_count_gcc, final String teacher_female_count_gcc, final String qtr_gcc, final String created_date_gcc, final String created_by_gcc, final String  updated_date_gcc, final String updated_by_gcc){
//
//        final SQLiteDatabase db = helper.getReadableDatabase();
        final SQLiteDatabase db1 = helper.getWritableDatabase();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_SYNC_GCC,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                            JSONObject obj = new JSONObject(response);
                            if (!obj.getBoolean("error")) {
                                //updating the status in sqlite
                                emisDBHelper.updateGCC(id, SYNCED_WITH_SERVER, db1);
                                Toast toast = Toast.makeText(SchoolSelectActivity.this, "Sync Successfull !", Toast.LENGTH_LONG);
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
//                params.put("id", "2");
//                params.put("gcc_id", id);
                params.put("census_year", census_year_gcc);
                params.put("grade_code", grade_code_gcc);
                params.put("class_count", class_count_gcc);
                params.put("schl_id", schl_id_gcc);
                params.put("student_male_count", student_male_count_gcc);
                params.put("student_female_count", student_female_count_gcc);
                params.put("teacher_male_count", teacher_male_count_gcc);
                params.put("teacher_female_count", teacher_female_count_gcc);
//                params.put("qtr", qtr_gcc);
                params.put("created_date", created_date_gcc);
                params.put("created_by", created_by_gcc);
                params.put("updated_date", updated_date_gcc);
                params.put("updated_by", updated_by_gcc);
                params.put("app_flag", "1");

                return params;
            }


        };

        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);

    }
    // END GCC SYNC

    // SYNC EBB to SERVER
    public void syncEBToServer(){

        SQLiteDatabase db = helper.getReadableDatabase();

        Cursor cursor = emisDBHelper.readEB(db);
        int cursor_length = cursor.getCount();

        while (cursor.moveToNext()) {
            // Goto Method - To send data to server
            saveEBtoServer(
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
                    cursor.getString(11),
                    cursor.getString(12)
            );

        }
        cursor.close();

    }


    public void saveEBtoServer(final String id_local_eb ,final String id_eb, final String census_year_eb, final String grade_code_eb,  final String male_boarding_count_eb, final String female_boarding_count_eb, final String male_day_count_eb, final String female_day_count_eb, final String schl_id_eb, final String created_date_eb, final String created_by_eb, final String  updated_date_eb, final String updated_by_eb){
//
//        final SQLiteDatabase db = helper.getReadableDatabase();
        final SQLiteDatabase db1 = helper.getWritableDatabase();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_SYNC_EB,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                            JSONObject obj = new JSONObject(response);
                            if (!obj.getBoolean("error")) {
                                //updating the status in sqlite
                                emisDBHelper.updateEB(id_local_eb, SYNCED_WITH_SERVER, db1);
                                Toast toast = Toast.makeText(SchoolSelectActivity.this, "Sync Successful !", Toast.LENGTH_LONG);
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
//                params.put("id", "2");
//                params.put("eb_id", id_local_eb);
                params.put("census_year", census_year_eb);
                params.put("grade_code", grade_code_eb);
                params.put("male_boarding_count", male_boarding_count_eb);
                params.put("female_boarding_count", female_boarding_count_eb);
//                params.put("male_day_count", "1");
//                params.put("female_day_count", "1");
                params.put("schl_id", schl_id_eb);
                params.put("created_date", created_date_eb);
                params.put("created_by", created_by_eb);
                params.put("updated_date", updated_date_eb);
                params.put("updated_by", updated_by_eb);
                params.put("app_flag", "1");

                return params;
            }


        };

        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);

    }
    // END EB SYNC




/*END SYNC*/




    /* Fetching Data from SQL*/

    public void onGetFromSQL(MenuItem FromSQLMenuItem){

        if(checkNetworkConnection()) {

//            syncDistrictsTable();
            syncUsersTable();

        }else{

            Toast toast = Toast.makeText(this, "No Internet Connection Available", Toast.LENGTH_LONG);
            toast.show();
        }
    }

//     Method to Sync MySQL to SQLite DB
//    public void syncDistrictsTable() {
//
//        AsyncHttpClient client = new AsyncHttpClient();
//
//        // Http Request Params Object
//        RequestParams params = new RequestParams();
//        // Show ProgressBar
////        prgDialog.show();
//        // Make Http call to getusers.php
//        client.post("http://devloved.com/SqliteSync/getdistricts.php", params, new AsyncHttpResponseHandler() {
//            @Override
//            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
//
//
//                try {
//                    String str = new String(responseBody, "UTF-8");
//
//                    updateDistrictSQLite(str);
//
//                } catch (UnsupportedEncodingException e) {
//                    e.printStackTrace();
//                }
//
//            }
//
//            @Override
//            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
//                if (statusCode == 404) {
//                    Toast.makeText(getApplicationContext(), "Requested resource not found", Toast.LENGTH_LONG).show();
//                } else if (statusCode == 500) {
//                    Toast.makeText(getApplicationContext(), "Something went wrong at server end", Toast.LENGTH_LONG).show();
//                } else {
//                    Toast.makeText(getApplicationContext(), "Unexpected Error occcured! [Most common Error: Device might not be connected to Internet]",
//                            Toast.LENGTH_LONG).show();
//                }
//
//            }
//
//
//        });
//    }


    //     Method to Sync MySQL to SQLite DB
    public void syncUsersTable() {

        AsyncHttpClient client = new AsyncHttpClient();

        // Http Request Params Object
        RequestParams params = new RequestParams();
        // Show ProgressBar
//        prgDialog.show();
        // Make Http call to getusers.php
        client.post("http://devloved.com/SqliteSync/getusers.php", params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {


                try {
                    String str = new String(responseBody, "UTF-8");

                    updateUserSQLite(str);

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

//    public void updateDistrictSQLite(String response) {
//        ArrayList<HashMap<String, String>> usersynclist;
//        usersynclist = new ArrayList<HashMap<String, String>>();
//        SQLiteDatabase db = helper.getWritableDatabase();
//
//        // Create GSON object
//        Gson gson = new GsonBuilder().create();
//        try {
//            // Extract JSON array from the response
//            JSONArray arr = new JSONArray(response);
//            System.out.println(arr.length());
//            // If no of array elements is not zero
//            if (arr.length() != 0) {
//                // Loop through each array element, get JSON object which has userid and username
//                for (int i = 0; i < arr.length(); i++) {
//                    // Get JSON object
//                    JSONObject obj = (JSONObject) arr.get(i);
//
//
//                    // DB QueryValues Object to insert into SQLite
//                    HashMap<String, String> queryValues = new HashMap<String, String>();
//
//                    queryValues.put("Id", obj.get("Id").toString());
//                    queryValues.put("District_Code", obj.get("District_Code").toString());
//                    queryValues.put("District_Name", obj.get("District_Name").toString());
//                    queryValues.put("Province_Code", obj.get("Province_Code").toString());
//                    queryValues.put("Old_District_Code", obj.get("Old_District_Code").toString());
//                    queryValues.put("Old_District_Name", obj.get("Old_District_Name").toString());
//                    // Insert User into SQLite DB
//
//                    emisDBHelper.insertDistrict(queryValues, db);
//
////                    Toast toast = Toast.makeText(SchoolSelectActivity.this, "Downloaded data successfully", Toast.LENGTH_LONG);
////                    toast.show();
////                    HashMap<String, String> map = new HashMap<String, String>();
//
//// Add status for each User in Hashmap
////                    map.put("Id", obj.get("userId").toString());
////                    map.put("status", "1");
////                    usersynclist.add(map);
//                }
//                // Inform Remote MySQL DB about the completion of Sync activity by passing Sync status of Users
////                updateMySQLSyncSts(gson.toJson(usersynclist));
////                // Reload the Main Activity
////                reloadActivity();
//            }
//        } catch (JSONException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//    }

    public void updateUserSQLite(String response) {
        ArrayList<HashMap<String, String>> usersynclist;
        usersynclist = new ArrayList<HashMap<String, String>>();
        SQLiteDatabase db = helper.getWritableDatabase();

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

                    queryValues.put("User_Id", obj.get("User_Id").toString());
                    queryValues.put("User_Name", obj.get("User_Name").toString());
                    queryValues.put("Password", obj.get("Password").toString());
                    queryValues.put("Emp_Code", obj.get("Emp_Code").toString());
                    queryValues.put("Emp_Name", obj.get("Emp_Name").toString());
                    queryValues.put("Email", obj.get("Email").toString());
                    queryValues.put("Mobile_no", obj.get("Mobile_no").toString());
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

    // Check if Internet Connection is available

    public boolean checkNetworkConnection(){
        ConnectivityManager connectivityManager = (ConnectivityManager) this.getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null &&networkInfo.isConnected();
    }


    public void onSignOut(MenuItem logoutMenuItem){
        Intent intent = new Intent(SchoolSelectActivity.this, MainActivity.class);
        intent.putExtra("intentID", "SchoolSelect");
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();

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


     @Override
    public boolean onCreateOptionsMenu(Menu menu){

         getMenuInflater().inflate(R.menu.main, menu);

         return true;
     }



}




