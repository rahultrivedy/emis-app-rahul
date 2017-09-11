package com.saysweb.emis_app.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.saysweb.emis_app.BoardingEnrollment;
import com.saysweb.emis_app.EnrollmentByGrade;
import com.saysweb.emis_app.data.emisContract.UserEntry;
import com.saysweb.emis_app.data.emisContract.ProvinceEntry;
import com.saysweb.emis_app.data.emisContract.DistrictEntry;
import com.saysweb.emis_app.data.emisContract.LlgvEntry;
import com.saysweb.emis_app.data.emisContract.SchoolEntry;
import com.saysweb.emis_app.data.emisContract.EnrollmentByBoardingEntry;
import com.saysweb.emis_app.data.emisContract.EnrollmentByGradesEntry;
import com.saysweb.emis_app.data.emisContract.GradeClassCountEntry;

import java.util.Random;

/**
 * Created by sukant on 05/09/17.
 * This class extends SQLiteOpenHelper which is used to create database.
 */
public class emisDBHelper extends SQLiteOpenHelper {

//    private emisDBHelper mDbHelper;

    /**
     * Name of the database file.
     */
    private static final String DATABASE_NAME = "emis.db";

    /**
     * DATABASE Version. Change the version in case you update the schema.
     */
    private static final int DATABASE_VERSION = 1;

    /**
     * Setting up the constructor.
     */
    public emisDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //Create string that contains the sql statement to create user table
        String SQL_CREATE_USER_TABLE = "CREATE TABLE " + UserEntry.TABLE_NAME + " ("
                + UserEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + UserEntry.COLUMN_NAME_USER_ID + " TEXT NOT NULL UNIQUE, "
                + UserEntry.COLUMN_NAME_USER_NAME + " TEXT NOT NULL, "
                + UserEntry.COLUMN_NAME_PASSWORD + " TEXT NOT NULL, "
                + UserEntry.COLUMN_NAME_EMP_CODE + " TEXT, "
                + UserEntry.COLUMN_NAME_EMP_NAME + " TEXT, "
                + UserEntry.COLUMN_NAME_EMAIL + " TEXT, "
                + UserEntry.COLUMN_NAME_MOBILE_NO + " TEXT);";

        db.execSQL(SQL_CREATE_USER_TABLE);

        //CREATING PROVINCE_TABLE TABLE
        String SQL_CREATE_PROVINCE_TABLE = "CREATE TABLE " + ProvinceEntry.TABLE_NAME + " ("
                + ProvinceEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + ProvinceEntry.COLUMN_NAME_PROVINCE_CODE + " TEXT NOT NULL, "
                + ProvinceEntry.COLUMN_NAME_PROVINCE_NAME + " TEXT NOT NULL, "
                + ProvinceEntry.COLUMN_NAME_OLD_PROVINCE_CODE + " TEXT, "
                + ProvinceEntry.COLUMN_NAME_OLD_PROVINCE_NAME + " TEXT);";

        db.execSQL(SQL_CREATE_PROVINCE_TABLE);

        //CREATING DISTRICT_TABLE TABLE
        String SQL_CREATE_DISTRICT_TABLE = "CREATE TABLE " + DistrictEntry.TABLE_NAME + " ("
                + DistrictEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + DistrictEntry.COLUMN_NAME_DISTRICT_CODE + " TEXT NOT NULL, "
                + DistrictEntry.COLUMN_NAME_DISTRICT_NAME + " TEXT NOT NULL, "
                + DistrictEntry.COLUMN_NAME_PROVINCE_CODE + " TEXT NOT NULL, "
                + DistrictEntry.COLUMN_NAME_OLD_DISTRICT_CODE + " TEXT, "
                + DistrictEntry.COLUMN_NAME_OLD_DISTRICT_NAME + " TEXT);";

        db.execSQL(SQL_CREATE_DISTRICT_TABLE);

        //CREATING LOCAL_GOVT_TABLE TABLE
        String SQL_CREATE_LLGV_TABLE = "CREATE TABLE " + LlgvEntry.TABLE_NAME + " ("
                + LlgvEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + LlgvEntry.COLUMN_NAME_LLGV_CODE + " TEXT NOT NULL, "
                + LlgvEntry.COLUMN_NAME_LLGV_NAME + " TEXT NOT NULL, "
                + LlgvEntry.COLUMN_NAME_PROVINCE_CODE + " TEXT NOT NULL, "
                + LlgvEntry.COLUMN_NAME_OLD_LLGV_CODE + " TEXT, "
                + LlgvEntry.COLUMN_NAME_OLD_LLGV_NAME + " TEXT);";

        db.execSQL(SQL_CREATE_LLGV_TABLE);

        //CREATING SCHOOLS_TABLE TABLE
        String SQL_CREATE_SCHOOLS_TABLE = "CREATE TABLE " + SchoolEntry.TABLE_NAME + " ("
                + SchoolEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + SchoolEntry.COLUMN_NAME_SCHL_ID + " INTEGER NOT NULL, "
                + SchoolEntry.COLUMN_NAME_SCHOOL_CODE + " TEXT NOT NULL, "
                + SchoolEntry.COLUMN_NAME_SCHOOL_NAME + " TEXT NOT NULL, "
                + SchoolEntry.COLUMN_NAME_SCHOOL_LEVEL + " TEXT, "
                + SchoolEntry.COLUMN_NAME_BOARDING_STATUS + " TEXT, "
                + SchoolEntry.COLUMN_NAME_SCHOOL_STATUS + " TEXT, "
                + SchoolEntry.COLUMN_NAME_LOCALITY + " TEXT, "
                + SchoolEntry.COLUMN_NAME_ADDRESS + " TEXT, "
                + SchoolEntry.COLUMN_NAME_PHONE + " TEXT, "
                + SchoolEntry.COLUMN_NAME_FAX + " TEXT, "
                + SchoolEntry.COLUMN_NAME_EMAIL + " TEXT, "
                + SchoolEntry.COLUMN_NAME_SECTOR_CODE + " TEXT, "
                + SchoolEntry.COLUMN_NAME_WARD_CODE + " TEXT, "
                + SchoolEntry.COLUMN_NAME_LLGV_CODE + " TEXT, "
                + SchoolEntry.COLUMN_NAME_DISTRICT_CODE + " TEXT, "
                + SchoolEntry.COLUMN_NAME_PROVINCE_CODE + " TEXT, "
                + SchoolEntry.COLUMN_NAME_SCHOOL_REGISTERED + " TEXT, "
                + SchoolEntry.COLUMN_NAME_POSITION_NORTH_LAT + " TEXT, "
                + SchoolEntry.COLUMN_NAME_POSITION_EAST_LONG + " TEXT, "
                + SchoolEntry.COLUMN_NAME_CENSUS_YEAR + " INTEGER); ";

        db.execSQL(SQL_CREATE_SCHOOLS_TABLE);

        //CREATING ENROLLMENT_BY_GRADE TABLE
        String SQL_CREATE_EBG_TABLE = "CREATE TABLE " + EnrollmentByGradesEntry.TABLE_NAME + " ("
                + EnrollmentByGradesEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + EnrollmentByGradesEntry.COLUMN_NAME_GRADE_ID + " INTEGER NOT NULL UNIQUE, "
                + EnrollmentByGradesEntry.COLUMN_NAME_GRADE_CODE+ " INTEGER NOT NULL, "
                + EnrollmentByGradesEntry.COLUMN_NAME_CENSUS_YEAR + " INTEGER NOT NULL, "
                + EnrollmentByGradesEntry.COLUMN_NAME_BIRTH_YEAR + " INTEGER, "
                + EnrollmentByGradesEntry.COLUMN_NAME_AGE + " INTEGER, "
                + EnrollmentByGradesEntry.COLUMN_NAME_FEMALE_COUNT + " INTEGER, "
                + EnrollmentByGradesEntry.COLUMN_NAME_MALE_COUNT + " INTEGER, "
                + EnrollmentByGradesEntry.COLUMN_NAME_SCHL_ID + " INTEGER);";

        db.execSQL(SQL_CREATE_EBG_TABLE);

        //CREATING GRADE CLASS COUNT TABLE
        String SQL_CREATE_GCC_TABLE = "CREATE TABLE " + GradeClassCountEntry.TABLE_NAME + " ("
                + GradeClassCountEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + GradeClassCountEntry.COLUMN_NAME_GCC_ID + " INTEGER NOT NULL UNIQUE, "
                + GradeClassCountEntry.COLUMN_NAME_CENSUS_YEAR + " INTEGER NOT NULL, "
                + GradeClassCountEntry.COLUMN_NAME_GRADE_CODE + " INTEGER  , "
                + GradeClassCountEntry.COLUMN_NAME_CLASS_COUNT + " INTEGER , "
                + GradeClassCountEntry.COLUMN_NAME_SCHL_ID + " INTEGER, "
                + GradeClassCountEntry.COLUMN_NAME_STUDENT_MALE_COUNT + " INTEGER, "
                + GradeClassCountEntry.COLUMN_NAME_STUDENT_FEMALE_COUNT + " INTEGER, "
                + GradeClassCountEntry.COLUMN_NAME_TEACHER_MALE_COUNT + " INTEGER, "
                + GradeClassCountEntry.COLUMN_NAME_TEACHER_FEMALE_COUNT + " INTEGER, "
                + GradeClassCountEntry.COLUMN_NAME_QTR + " INTEGER);";

        db.execSQL(SQL_CREATE_GCC_TABLE);
//
        //CREATING BOARDING ENROLLMENT TABLE
        String SQL_CREATE_BOARDING_ENROLLMENT_TABLE = "CREATE TABLE " + EnrollmentByBoardingEntry.TABLE_NAME + " ("
                + EnrollmentByBoardingEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + EnrollmentByBoardingEntry.COLUMN_NAME_ENROLLMENT_BOARDING_ID + " INTEGER NOT NULL UNIQUE, "
                + EnrollmentByBoardingEntry.COLUMN_NAME_CENSUS_YEAR + " INTEGER NOT NULL, "
                + EnrollmentByBoardingEntry.COLUMN_NAME_GRADE_CODE + " TEXT, "
                + EnrollmentByBoardingEntry.COLUMN_NAME_MALE_BOARDING_COUNT + " INTEGER , "
                + EnrollmentByBoardingEntry.COLUMN_NAME_FEMALE_BOARDING_COUNT + " INTEGER, "
                + EnrollmentByBoardingEntry.COLUMN_NAME_MALE_DAY_COUNT + " INTEGER, "
                + EnrollmentByBoardingEntry.COLUMN_NAME_FEMALE_DAY_COUNT + " INTEGER, "
                + EnrollmentByBoardingEntry.COLUMN_NAME_SCHL_ID + " INTEGER );";

        db.execSQL(SQL_CREATE_BOARDING_ENROLLMENT_TABLE);


        /*INSERT DATA INTO TABLES*/


        // INSERT DATA INTO USER TABLE
//
//
        int i;
        for(i = 1; i <= 5; i++){
            ContentValues values = new ContentValues();
            values.put(UserEntry.COLUMN_NAME_USER_ID, i);
            values.put(UserEntry.COLUMN_NAME_USER_NAME, "user" + i);
            values.put(UserEntry.COLUMN_NAME_PASSWORD, "pass" + i);
            values.put(UserEntry.COLUMN_NAME_EMP_CODE, "Emp" + i);
            values.put(UserEntry.COLUMN_NAME_EMP_NAME, "Name" + i);
            values.put(UserEntry.COLUMN_NAME_EMAIL, "rahultrivedy" + i + "@gmail.com");
            values.put(UserEntry.COLUMN_NAME_MOBILE_NO, "98343204" + i);

            db.insert(UserEntry.TABLE_NAME, null, values);

        }

        // INSERT DATA INTO PROVINCE TABLE

        int i1;
        for(i1 = 1; i1 <= 15; i1++){
            ContentValues values = new ContentValues();
            values.put(ProvinceEntry.COLUMN_NAME_PROVINCE_CODE, i1);
            values.put(ProvinceEntry.COLUMN_NAME_PROVINCE_NAME, "Province-" + i1);
            values.put(ProvinceEntry.COLUMN_NAME_OLD_PROVINCE_CODE, "Old-" + i1);
            values.put(ProvinceEntry.COLUMN_NAME_OLD_PROVINCE_NAME, "OldName-" + i1);

            // Insert the new row, returning the primary key value of the new row
            db.insert(ProvinceEntry.TABLE_NAME, null, values);

        }

//        INSERT DATA INTO DISTRICT TABLE

        int i2;
        for(i2 = 1; i2 <= 15; i2++) {
            ContentValues values = new ContentValues();
            values.put(DistrictEntry.COLUMN_NAME_DISTRICT_CODE, i2);
            values.put(DistrictEntry.COLUMN_NAME_DISTRICT_NAME, "District-" + i2);
            values.put(DistrictEntry.COLUMN_NAME_OLD_DISTRICT_CODE, "OldDist-" + i2);
            values.put(DistrictEntry.COLUMN_NAME_OLD_DISTRICT_NAME, "OldDistName-" + i2);
            Random r = new Random();
            int Low = 1;
            int High = 15;
            int Result = r.nextInt(High - Low) + Low;
            values.put(DistrictEntry.COLUMN_NAME_PROVINCE_CODE, Result);

            // Insert the new row, returning the primary key value of the new row
            db.insert(DistrictEntry.TABLE_NAME, null, values);

        }
//
//
        // INSERT DATA INTO SCHOOLS

        int i3;
        int Low = 1;
        int High = 15;
        final String[] census_yr = {"2015", "2016", "2017"};
        final String[] boarding_status = {"Yes", "No"};

        for(i3 = 1; i3 <= 15; i++){
            ContentValues values = new ContentValues();
            values.put(SchoolEntry.COLUMN_NAME_SCHL_ID, i3);
//            Random prov = new Random();
//            int provResult = prov.nextInt(High-Low) + Low;

//            Random dist = new Random();
//            int distResult = dist.nextInt(High-Low) + Low;
//            values.put(SchoolEntry.COLUMN_NAME_SCHOOL_CODE, "" + provResult +  distResult + i3);
//            values.put(SchoolEntry.COLUMN_NAME_SCHOOL_NAME, "School - " + i3);
//            values.put(SchoolEntry.COLUMN_NAME_ADDRESS, "Address - " + provResult +  distResult + i3);
//
//
//            Random random = new Random();
//            int index = random.nextInt(boarding_status.length);
//            values.put(SchoolEntry.COLUMN_NAME_BOARDING_STATUS, boarding_status[index]);
////
//            Random censusRandom = new Random();
//            int censusIndex = censusRandom.nextInt(census_yr.length);
//            values.put(SchoolEntry.COLUMN_NAME_CENSUS_YEAR, census_yr[censusIndex]);
//            values.put(SchoolEntry.COLUMN_NAME_DISTRICT_CODE, distResult);
//            values.put(SchoolEntry.COLUMN_NAME_PROVINCE_CODE, provResult);
//            values.put(SchoolEntry.COLUMN_NAME_LLGV_CODE, distResult);
//            values.put(SchoolEntry.COLUMN_NAME_EMAIL, "school." + i3 + "@emis.com");
//            values.put(SchoolEntry.COLUMN_NAME_FAX, "760906454" + i3);
//            values.put(SchoolEntry.COLUMN_NAME_LOCALITY, "Locality" + i3);
//            values.put(SchoolEntry.COLUMN_NAME_PHONE, "0652-725097" + distResult);
//            values.put(SchoolEntry.COLUMN_NAME_SECTOR_CODE, distResult);
//            values.put(SchoolEntry.COLUMN_NAME_WARD_CODE, provResult);;

            // Insert the new row, returning the primary key value of the new row
           db.insert(SchoolEntry.TABLE_NAME, null, values);

        }
    }



     /*
     * METHOD Call from MainActivity for returning password for Login check
     * */

    public String searchPass(String uName) {
        // Create and/or open a database to read from it
        SQLiteDatabase db = getReadableDatabase();

        String[] projection = {UserEntry.COLUMN_NAME_USER_NAME,
                UserEntry.COLUMN_NAME_PASSWORD};
//                String selection = UserEntry.COLUMN_PET_GENDER + “=?”;
//                String selectionArgs = new String[] { UserEntry.GENDER_FEMALE };

        Cursor cursor = db.query(UserEntry.TABLE_NAME, projection,
                null, null, null, null, null);

        String returnPass = "not found";
        if (cursor.moveToFirst()){
            do {
                String userName = cursor.getString(0);

                if (userName.equals(uName)){
                    returnPass = cursor.getString(1);
                    break;
                }

            }while (cursor.moveToNext());
        }
        cursor.close();
        return (returnPass);
    }

    /*
     * METHOD Call from SchoolSelectActivity for getting the UserName
     * */

    public String getUserName(String uid){
        SQLiteDatabase db1 = getReadableDatabase();
        String user = "Not Found";
        String[] projection = {UserEntry.COLUMN_NAME_EMP_NAME};
        String selection = UserEntry.COLUMN_NAME_USER_NAME + " = ?";
        String[] selectionArgs = { uid };

        Cursor cursor = db1.query(UserEntry.TABLE_NAME, projection,
                selection, selectionArgs, null, null, null);

        while(cursor.moveToNext()) {
            user = cursor.getString(0);
        }
        cursor.close();
        return (user);
    }


    /*
     * METHOD Call from SchoolSelectActivity for getting school codes in autoComplete
     * */

    public String[] valueOfCursor(){

        SQLiteDatabase db2 = getReadableDatabase();

        String[] projection = {SchoolEntry.COLUMN_NAME_SCHOOL_CODE,
                SchoolEntry.COLUMN_NAME_SCHOOL_NAME};
//                String selection = UserEntry.COLUMN_PET_GENDER + “=?”;
//                String selectionArgs = new String[] { UserEntry.GENDER_FEMALE };

//      Cursor with all the rows from Columns - School Code and School Name
        Cursor cursor = db2.query(SchoolEntry.TABLE_NAME, projection,
                null, null, null, null, null);

        String[] school_codes = new String[] {"a", "b","c","d"};
        int i = 0;
        if (cursor.moveToFirst()) {
            do {
                school_codes[i] = cursor.getString(1) + "," + " " + cursor.getString(0);
                i++;
            } while (cursor.moveToNext());
        }
        cursor.close();
        return school_codes;
    }


/* DATABASE VERSION UPGRADE*/
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
