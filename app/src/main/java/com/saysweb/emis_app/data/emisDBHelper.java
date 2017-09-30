package com.saysweb.emis_app.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.saysweb.emis_app.data.emisContract.DistrictEntry;
import com.saysweb.emis_app.data.emisContract.EnrollmentByBoardingEntry;
import com.saysweb.emis_app.data.emisContract.EnrollmentByGradesEntry;
import com.saysweb.emis_app.data.emisContract.GradeClassCountEntry;
import com.saysweb.emis_app.data.emisContract.GradeEntry;
import com.saysweb.emis_app.data.emisContract.LlgvEntry;
import com.saysweb.emis_app.data.emisContract.ProvinceEntry;
import com.saysweb.emis_app.data.emisContract.SchoolEntry;
import com.saysweb.emis_app.data.emisContract.UserEntry;

import java.util.ArrayList;
import java.util.HashMap;

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
                + EnrollmentByGradesEntry.COLUMN_NAME_GRADE_CODE + " TEXT NOT NULL, "
                + EnrollmentByGradesEntry.COLUMN_NAME_CENSUS_YEAR + " INTEGER NOT NULL, "
                + EnrollmentByGradesEntry.COLUMN_NAME_BIRTH_YEAR + " INTEGER NOT NULL, "
                + EnrollmentByGradesEntry.COLUMN_NAME_AGE + " INTEGER, "
                + EnrollmentByGradesEntry.COLUMN_NAME_FEMALE_COUNT + " INTEGER, "
                + EnrollmentByGradesEntry.COLUMN_NAME_MALE_COUNT + " INTEGER, "
                + EnrollmentByGradesEntry.COLUMN_NAME_SCHL_ID + " INTEGER NOT NULL,"
                + EnrollmentByGradesEntry.COLUMN_NAME_CREATED_DATE + " INTEGER, "
                + EnrollmentByGradesEntry.COLUMN_NAME_CREATED_BY + " TEXT, "
                + EnrollmentByGradesEntry.COLUMN_NAME_UPDATED_DATE + " INTEGER, "
                + EnrollmentByGradesEntry.COLUMN_NAME_UPDATED_BY + " TEXT, "
                + EnrollmentByGradesEntry.COLUMN_NAME_SYNC_STATUS + " INTEGER DEFAULT 0); ";

        db.execSQL(SQL_CREATE_EBG_TABLE);

        //CREATING GRADE CLASS COUNT TABLE
        String SQL_CREATE_GCC_TABLE = "CREATE TABLE " + GradeClassCountEntry.TABLE_NAME + " ("
                + GradeClassCountEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + GradeClassCountEntry.COLUMN_NAME_GCC_ID + " INTEGER , "
                + GradeClassCountEntry.COLUMN_NAME_CENSUS_YEAR + " INTEGER NOT NULL, "
                + GradeClassCountEntry.COLUMN_NAME_GRADE_CODE + " TEXT NOT NULL  , "
                + GradeClassCountEntry.COLUMN_NAME_CLASS_COUNT + " INTEGER  NOT NULL, "
                + GradeClassCountEntry.COLUMN_NAME_SCHL_ID + " INTEGER, "
                + GradeClassCountEntry.COLUMN_NAME_STUDENT_MALE_COUNT + " INTEGER, "
                + GradeClassCountEntry.COLUMN_NAME_STUDENT_FEMALE_COUNT + " INTEGER, "
                + GradeClassCountEntry.COLUMN_NAME_TEACHER_MALE_COUNT + " INTEGER, "
                + GradeClassCountEntry.COLUMN_NAME_TEACHER_FEMALE_COUNT + " INTEGER, "
                + GradeClassCountEntry.COLUMN_NAME_QTR + " INTEGER, "
                + GradeClassCountEntry.COLUMN_NAME_CREATED_DATE + " INTEGER, "
                + GradeClassCountEntry.COLUMN_NAME_CREATED_BY + " TEXT, "
                + GradeClassCountEntry.COLUMN_NAME_UPDATED_DATE + " INTEGER, "
                + GradeClassCountEntry.COLUMN_NAME_UPDATED_BY + " TEXT, "
                + GradeClassCountEntry.COLUMN_NAME_SYNC_STATUS + " INTEGER DEFAULT 0); ";


        db.execSQL(SQL_CREATE_GCC_TABLE);

        //CREATING BOARDING ENROLLMENT TABLE
        String SQL_CREATE_BOARDING_ENROLLMENT_TABLE = "CREATE TABLE " + EnrollmentByBoardingEntry.TABLE_NAME + " ("
                + EnrollmentByBoardingEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + EnrollmentByBoardingEntry.COLUMN_NAME_ENROLLMENT_BOARDING_ID + " INTEGER , "
                + EnrollmentByBoardingEntry.COLUMN_NAME_CENSUS_YEAR + " INTEGER NOT NULL, "
                + EnrollmentByBoardingEntry.COLUMN_NAME_GRADE_CODE + " TEXT, "
                + EnrollmentByBoardingEntry.COLUMN_NAME_MALE_BOARDING_COUNT + " INTEGER , "
                + EnrollmentByBoardingEntry.COLUMN_NAME_FEMALE_BOARDING_COUNT + " INTEGER, "
                + EnrollmentByBoardingEntry.COLUMN_NAME_MALE_DAY_COUNT + " INTEGER, "
                + EnrollmentByBoardingEntry.COLUMN_NAME_FEMALE_DAY_COUNT + " INTEGER, "
                + EnrollmentByBoardingEntry.COLUMN_NAME_SCHL_ID + " INTEGER ,"
                + EnrollmentByBoardingEntry.COLUMN_NAME_CREATED_DATE + " INTEGER, "
                + EnrollmentByBoardingEntry.COLUMN_NAME_CREATED_BY + " TEXT, "
                + EnrollmentByBoardingEntry.COLUMN_NAME_UPDATED_DATE + " INTEGER, "
                + EnrollmentByBoardingEntry.COLUMN_NAME_UPDATED_BY + " TEXT, "
                + EnrollmentByBoardingEntry.COLUMN_NAME_SYNC_STATUS + " INTEGER DEFAULT 0); ";


        db.execSQL(SQL_CREATE_BOARDING_ENROLLMENT_TABLE);

        //CREATING GRADE TABLE
        String SQL_CREATE_GRADE_TABLE = "CREATE TABLE " + GradeEntry.TABLE_NAME + " ("
                + GradeEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + GradeEntry.COLUMN_NAME_GRADE_CODE + " TEXT NOT NULL, "
                + GradeEntry.COLUMN_NAME_GRADE_NAME + " TEXT, "
                + GradeEntry.COLUMN_NAME_SECTOR_CODE + " TEXT, "
                + GradeEntry.COLUMN_NAME_GRADE_SORT + " INTEGER , "
                + GradeEntry.COLUMN_NAME_PK_CODE + " INTEGER, "
                + GradeEntry.COLUMN_NAME_ACTIVE + " TEXT); ";

        db.execSQL(SQL_CREATE_GRADE_TABLE);


/*INSERT DATA IN DATABASE*/

      /*INSERT DATA INTO TABLES*/


        // INSERT DATA INTO USER TABLE
        int i;
        for (i = 1; i <= 5; i++) {
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
        //---------------------------------------Province Entry - 01-------------------------------
        ContentValues values = new ContentValues();
        values.put(ProvinceEntry.COLUMN_NAME_PROVINCE_CODE, 11);
        values.put(ProvinceEntry.COLUMN_NAME_PROVINCE_NAME, "Central");
        values.put(ProvinceEntry.COLUMN_NAME_OLD_PROVINCE_CODE, 11);
        values.put(ProvinceEntry.COLUMN_NAME_OLD_PROVINCE_NAME, "Central");

        // Insert the new row, returning the primary key value of the new row
        db.insert(ProvinceEntry.TABLE_NAME, null, values);

        //---------------------------------------Province Entry - 02-------------------------------
        values = new ContentValues();
        values.put(ProvinceEntry.COLUMN_NAME_PROVINCE_CODE, 12);
        values.put(ProvinceEntry.COLUMN_NAME_PROVINCE_NAME, "Eastern Highlands");
        values.put(ProvinceEntry.COLUMN_NAME_OLD_PROVINCE_CODE, 12);
        values.put(ProvinceEntry.COLUMN_NAME_OLD_PROVINCE_NAME, "Eastern Highlands");

        // Insert the new row, returning the primary key value of the new row
        db.insert(ProvinceEntry.TABLE_NAME, null, values);

        //---------------------------------------Province Entry - 03-------------------------------
        values = new ContentValues();
        values.put(ProvinceEntry.COLUMN_NAME_PROVINCE_CODE, 13);
        values.put(ProvinceEntry.COLUMN_NAME_PROVINCE_NAME, "Southern Highlands");
        values.put(ProvinceEntry.COLUMN_NAME_OLD_PROVINCE_CODE, 53);
        values.put(ProvinceEntry.COLUMN_NAME_OLD_PROVINCE_NAME, "Highlands");

        // Insert the new row, returning the primary key value of the new row
        db.insert(ProvinceEntry.TABLE_NAME, null, values);

//        INSERT DATA INTO DISTRICT TABLE
//        //---------------------------------------District Entry - 01-------------------------------
//        values = new ContentValues();
//        values.put(DistrictEntry.COLUMN_NAME_DISTRICT_CODE, 1101);
//        values.put(DistrictEntry.COLUMN_NAME_DISTRICT_NAME, "Abau District");
//        values.put(DistrictEntry.COLUMN_NAME_OLD_DISTRICT_CODE, 1101);
//        values.put(DistrictEntry.COLUMN_NAME_OLD_DISTRICT_NAME, "Abau District");
//        values.put(DistrictEntry.COLUMN_NAME_PROVINCE_CODE, 11);
//
//        // Insert the new row, returning the primary key value of the new row
//        db.insert(DistrictEntry.TABLE_NAME, null, values);
//
//        //---------------------------------------District Entry - 02-------------------------------
//        values = new ContentValues();
//        values.put(DistrictEntry.COLUMN_NAME_DISTRICT_CODE, 1102);
//        values.put(DistrictEntry.COLUMN_NAME_DISTRICT_NAME, "Goilala District");
//        values.put(DistrictEntry.COLUMN_NAME_OLD_DISTRICT_CODE, 1102);
//        values.put(DistrictEntry.COLUMN_NAME_OLD_DISTRICT_NAME, "Goilala District");
//        values.put(DistrictEntry.COLUMN_NAME_PROVINCE_CODE, 11);
//
//        // Insert the new row, returning the primary key value of the new row
//        db.insert(DistrictEntry.TABLE_NAME, null, values);
//
//        //---------------------------------------District Entry - 03-------------------------------
//        values = new ContentValues();
//        values.put(DistrictEntry.COLUMN_NAME_DISTRICT_CODE, 1109);
//        values.put(DistrictEntry.COLUMN_NAME_DISTRICT_NAME, "Kairuku-Hiri District");
//        values.put(DistrictEntry.COLUMN_NAME_OLD_DISTRICT_CODE, 1103);
//        values.put(DistrictEntry.COLUMN_NAME_OLD_DISTRICT_NAME, "Kairuku District");
//        values.put(DistrictEntry.COLUMN_NAME_PROVINCE_CODE, 11);
//
//        //---------------------------------------District Entry - 04-------------------------------
//        // Insert the new row, returning the primary key value of the new row
//        db.insert(DistrictEntry.TABLE_NAME, null, values);
//
//        values = new ContentValues();
//        values.put(DistrictEntry.COLUMN_NAME_DISTRICT_CODE, 1201);
//        values.put(DistrictEntry.COLUMN_NAME_DISTRICT_NAME, "Daulo District");
//        values.put(DistrictEntry.COLUMN_NAME_OLD_DISTRICT_CODE, 1201);
//        values.put(DistrictEntry.COLUMN_NAME_OLD_DISTRICT_NAME, "Daulo District");
//        values.put(DistrictEntry.COLUMN_NAME_PROVINCE_CODE, 12);
//
//        // Insert the new row, returning the primary key value of the new row
//        db.insert(DistrictEntry.TABLE_NAME, null, values);
//
//        //---------------------------------------District Entry - 05-------------------------------
//        values = new ContentValues();
//        values.put(DistrictEntry.COLUMN_NAME_DISTRICT_CODE, 1202);
//        values.put(DistrictEntry.COLUMN_NAME_DISTRICT_NAME, "Goroka District");
//        values.put(DistrictEntry.COLUMN_NAME_OLD_DISTRICT_CODE, 1203);
//        values.put(DistrictEntry.COLUMN_NAME_OLD_DISTRICT_NAME, "Goroka District");
//        values.put(DistrictEntry.COLUMN_NAME_PROVINCE_CODE, 12);
//
//        // Insert the new row, returning the primary key value of the new row
//        db.insert(DistrictEntry.TABLE_NAME, null, values);
//
//        //---------------------------------------District Entry - 06-------------------------------
//        values = new ContentValues();
//        values.put(DistrictEntry.COLUMN_NAME_DISTRICT_CODE, 1301);
//        values.put(DistrictEntry.COLUMN_NAME_DISTRICT_NAME, "Imbonggu District");
//        values.put(DistrictEntry.COLUMN_NAME_OLD_DISTRICT_CODE, 1301);
//        values.put(DistrictEntry.COLUMN_NAME_OLD_DISTRICT_NAME, "Imbonggu District");
//        values.put(DistrictEntry.COLUMN_NAME_PROVINCE_CODE, 13);
//
//        // Insert the new row, returning the primary key value of the new row
//        db.insert(DistrictEntry.TABLE_NAME, null, values);
//
//        //---------------------------------------District Entry - 07-------------------------------
//        values = new ContentValues();
//        values.put(DistrictEntry.COLUMN_NAME_DISTRICT_CODE, 1311);
//        values.put(DistrictEntry.COLUMN_NAME_DISTRICT_NAME, "Mendi-Munihu District");
//        values.put(DistrictEntry.COLUMN_NAME_OLD_DISTRICT_CODE, 1311);
//        values.put(DistrictEntry.COLUMN_NAME_OLD_DISTRICT_NAME, "Mendi District");
//        values.put(DistrictEntry.COLUMN_NAME_PROVINCE_CODE, 13);
//
//        // Insert the new row, returning the primary key value of the new row
//        db.insert(DistrictEntry.TABLE_NAME, null, values);

        // INSERT DATA INTO GRADES TABLE
        values = new ContentValues();
        values.put(GradeEntry.COLUMN_NAME_GRADE_CODE, "ELE-1");
        values.put(GradeEntry.COLUMN_NAME_GRADE_NAME, "Elementary - 1");
        values.put(GradeEntry.COLUMN_NAME_ACTIVE, "Y");
        values.put(GradeEntry.COLUMN_NAME_GRADE_SORT, 1);
        values.put(GradeEntry.COLUMN_NAME_PK_CODE, 1);
        values.put(GradeEntry.COLUMN_NAME_SECTOR_CODE, "ELE");

        // Insert the new row, returning the primary key value of the new row
        db.insert(GradeEntry.TABLE_NAME, null, values);

        values = new ContentValues();
        values.put(GradeEntry.COLUMN_NAME_GRADE_CODE, "ELE-2");
        values.put(GradeEntry.COLUMN_NAME_GRADE_NAME, "Elementary - 2");
        values.put(GradeEntry.COLUMN_NAME_ACTIVE, "Y");
        values.put(GradeEntry.COLUMN_NAME_GRADE_SORT, 1);
        values.put(GradeEntry.COLUMN_NAME_PK_CODE, 1);
        values.put(GradeEntry.COLUMN_NAME_SECTOR_CODE, "ELE");
        // Insert the new row, returning the primary key value of the new row
        db.insert(GradeEntry.TABLE_NAME, null, values);

        values = new ContentValues();
        values.put(GradeEntry.COLUMN_NAME_GRADE_CODE, "ELE-3");
        values.put(GradeEntry.COLUMN_NAME_GRADE_NAME, "Elementary - 3");
        values.put(GradeEntry.COLUMN_NAME_ACTIVE, "N");
        values.put(GradeEntry.COLUMN_NAME_GRADE_SORT, 1);
        values.put(GradeEntry.COLUMN_NAME_PK_CODE, 1);
        values.put(GradeEntry.COLUMN_NAME_SECTOR_CODE, "ELE");
        // Insert the new row, returning the primary key value of the new row
        db.insert(GradeEntry.TABLE_NAME, null, values);

        values = new ContentValues();
        values.put(GradeEntry.COLUMN_NAME_GRADE_CODE, "GR-1");
        values.put(GradeEntry.COLUMN_NAME_GRADE_NAME, "GRADE I");
        values.put(GradeEntry.COLUMN_NAME_ACTIVE, "Y");
        values.put(GradeEntry.COLUMN_NAME_GRADE_SORT, 1);
        values.put(GradeEntry.COLUMN_NAME_PK_CODE, 1);
        values.put(GradeEntry.COLUMN_NAME_SECTOR_CODE, "PRI");
        // Insert the new row, returning the primary key value of the new row
        db.insert(GradeEntry.TABLE_NAME, null, values);

        values = new ContentValues();
        values.put(GradeEntry.COLUMN_NAME_GRADE_CODE, "GR-2");
        values.put(GradeEntry.COLUMN_NAME_GRADE_NAME, "GRADE II");
        values.put(GradeEntry.COLUMN_NAME_ACTIVE, "Y");
        values.put(GradeEntry.COLUMN_NAME_GRADE_SORT, 1);
        values.put(GradeEntry.COLUMN_NAME_PK_CODE, 1);
        values.put(GradeEntry.COLUMN_NAME_SECTOR_CODE, "PRI");
        // Insert the new row, returning the primary key value of the new row
        db.insert(GradeEntry.TABLE_NAME, null, values);


        values = new ContentValues();
        values.put(GradeEntry.COLUMN_NAME_GRADE_CODE, "GR-3");
        values.put(GradeEntry.COLUMN_NAME_GRADE_NAME, "GRADE III");
        values.put(GradeEntry.COLUMN_NAME_ACTIVE, "Y");
        values.put(GradeEntry.COLUMN_NAME_GRADE_SORT, 1);
        values.put(GradeEntry.COLUMN_NAME_PK_CODE, 1);
        values.put(GradeEntry.COLUMN_NAME_SECTOR_CODE, "PRI");
        // Insert the new row, returning the primary key value of the new row
        db.insert(GradeEntry.TABLE_NAME, null, values);

        values = new ContentValues();
        values.put(GradeEntry.COLUMN_NAME_GRADE_CODE, "GR-4");
        values.put(GradeEntry.COLUMN_NAME_GRADE_NAME, "GRADE IV");
        values.put(GradeEntry.COLUMN_NAME_ACTIVE, "Y");
        values.put(GradeEntry.COLUMN_NAME_GRADE_SORT, 1);
        values.put(GradeEntry.COLUMN_NAME_PK_CODE, 1);
        values.put(GradeEntry.COLUMN_NAME_SECTOR_CODE, "PRI");
        // Insert the new row, returning the primary key value of the new row
        db.insert(GradeEntry.TABLE_NAME, null, values);

        values = new ContentValues();
        values.put(GradeEntry.COLUMN_NAME_GRADE_CODE, "GR-5");
        values.put(GradeEntry.COLUMN_NAME_GRADE_NAME, "GRADE V");
        values.put(GradeEntry.COLUMN_NAME_ACTIVE, "Y");
        values.put(GradeEntry.COLUMN_NAME_GRADE_SORT, 1);
        values.put(GradeEntry.COLUMN_NAME_PK_CODE, 1);
        values.put(GradeEntry.COLUMN_NAME_SECTOR_CODE, "PRI");
        // Insert the new row, returning the primary key value of the new row
        db.insert(GradeEntry.TABLE_NAME, null, values);


        // INSERT DATA INTO SCHOOLS
        //---------------------------------------School Entry - 01-------------------------------
        values = new ContentValues();
        values.put(SchoolEntry.COLUMN_NAME_SCHL_ID, 101);
        values.put(SchoolEntry.COLUMN_NAME_SCHOOL_CODE, 110101);
        values.put(SchoolEntry.COLUMN_NAME_SCHOOL_NAME, "Abau Island Primary School");
        values.put(SchoolEntry.COLUMN_NAME_ADDRESS, "Address");
        values.put(SchoolEntry.COLUMN_NAME_BOARDING_STATUS, "Yes");
        values.put(SchoolEntry.COLUMN_NAME_CENSUS_YEAR, 2017);
        values.put(SchoolEntry.COLUMN_NAME_DISTRICT_CODE, "1101");
        values.put(SchoolEntry.COLUMN_NAME_PROVINCE_CODE, "11");
        values.put(SchoolEntry.COLUMN_NAME_LLGV_CODE, 110103);
        values.put(SchoolEntry.COLUMN_NAME_EMAIL, "aips@emis.com");
        values.put(SchoolEntry.COLUMN_NAME_FAX, "760906454");
        values.put(SchoolEntry.COLUMN_NAME_LOCALITY, "Locality");
        values.put(SchoolEntry.COLUMN_NAME_PHONE, "0652-72509");
        values.put(SchoolEntry.COLUMN_NAME_SECTOR_CODE, "PRI");
        values.put(SchoolEntry.COLUMN_NAME_WARD_CODE, "ward-code");

        // Insert the new row, returning the primary key value of the new row
        db.insert(SchoolEntry.TABLE_NAME, null, values);

        //---------------------------------------School Entry - 02-------------------------------
        values = new ContentValues();
        values.put(SchoolEntry.COLUMN_NAME_SCHL_ID, 102);
        values.put(SchoolEntry.COLUMN_NAME_SCHOOL_CODE, 120202);
        values.put(SchoolEntry.COLUMN_NAME_SCHOOL_NAME, "Oriropetana Elementary School");
        values.put(SchoolEntry.COLUMN_NAME_ADDRESS, "Address");
        values.put(SchoolEntry.COLUMN_NAME_BOARDING_STATUS, "Yes");
        values.put(SchoolEntry.COLUMN_NAME_CENSUS_YEAR, 2017);
        values.put(SchoolEntry.COLUMN_NAME_DISTRICT_CODE, "1202");
        values.put(SchoolEntry.COLUMN_NAME_PROVINCE_CODE, "12");
        values.put(SchoolEntry.COLUMN_NAME_LLGV_CODE, 120203);
        values.put(SchoolEntry.COLUMN_NAME_EMAIL, "oes@emis.com");
        values.put(SchoolEntry.COLUMN_NAME_FAX, "760906454");
        values.put(SchoolEntry.COLUMN_NAME_LOCALITY, "Locality");
        values.put(SchoolEntry.COLUMN_NAME_PHONE, "0652-72509");
        values.put(SchoolEntry.COLUMN_NAME_SECTOR_CODE, "ELE");
        values.put(SchoolEntry.COLUMN_NAME_WARD_CODE, "ward-code");
        // Insert the new row, returning the primary key value of the new row
        db.insert(SchoolEntry.TABLE_NAME, null, values);


        //---------------------------------------School Entry - 03-------------------------------
        values = new ContentValues();
        values.put(SchoolEntry.COLUMN_NAME_SCHL_ID, 103);
        values.put(SchoolEntry.COLUMN_NAME_SCHOOL_CODE, 130104);
        values.put(SchoolEntry.COLUMN_NAME_SCHOOL_NAME, "Waro Elementary School");
        values.put(SchoolEntry.COLUMN_NAME_ADDRESS, "Address");
        values.put(SchoolEntry.COLUMN_NAME_BOARDING_STATUS, "Yes");
        values.put(SchoolEntry.COLUMN_NAME_CENSUS_YEAR, 2017);
        values.put(SchoolEntry.COLUMN_NAME_DISTRICT_CODE, "1301");
        values.put(SchoolEntry.COLUMN_NAME_PROVINCE_CODE, "12");
        values.put(SchoolEntry.COLUMN_NAME_LLGV_CODE, 130103);
        values.put(SchoolEntry.COLUMN_NAME_EMAIL, "wps@emis.com");
        values.put(SchoolEntry.COLUMN_NAME_FAX, "760906454");
        values.put(SchoolEntry.COLUMN_NAME_LOCALITY, "Locality");
        values.put(SchoolEntry.COLUMN_NAME_PHONE, "0652-72509");
        values.put(SchoolEntry.COLUMN_NAME_SECTOR_CODE, "ELE");
        values.put(SchoolEntry.COLUMN_NAME_WARD_CODE, "ward-code");
        // Insert the new row, returning the primary key value of the new row
        db.insert(SchoolEntry.TABLE_NAME, null, values);


        //---------------------------------------School Entry - 04-------------------------------
        values = new ContentValues();
        values.put(SchoolEntry.COLUMN_NAME_SCHL_ID, 104);
        values.put(SchoolEntry.COLUMN_NAME_SCHOOL_CODE, 110204);
        values.put(SchoolEntry.COLUMN_NAME_SCHOOL_NAME, "Woitape Primary School");
        values.put(SchoolEntry.COLUMN_NAME_ADDRESS, "Address");
        values.put(SchoolEntry.COLUMN_NAME_BOARDING_STATUS, "Yes");
        values.put(SchoolEntry.COLUMN_NAME_CENSUS_YEAR, 2017);
        values.put(SchoolEntry.COLUMN_NAME_DISTRICT_CODE, "1102");
        values.put(SchoolEntry.COLUMN_NAME_PROVINCE_CODE, "11");
        values.put(SchoolEntry.COLUMN_NAME_LLGV_CODE, 110203);
        values.put(SchoolEntry.COLUMN_NAME_EMAIL, "wtps@emis.com");
        values.put(SchoolEntry.COLUMN_NAME_FAX, "760906454");
        values.put(SchoolEntry.COLUMN_NAME_LOCALITY, "Locality");
        values.put(SchoolEntry.COLUMN_NAME_PHONE, "0652-72509");
        values.put(SchoolEntry.COLUMN_NAME_SECTOR_CODE, "PRI");
        values.put(SchoolEntry.COLUMN_NAME_WARD_CODE, "ward-code");
        // Insert the new row, returning the primary key value of the new row
        db.insert(SchoolEntry.TABLE_NAME, null, values);

        //---------------------------------------School Entry - 05-------------------------------
        values = new ContentValues();
        values.put(SchoolEntry.COLUMN_NAME_SCHL_ID, 105);
        values.put(SchoolEntry.COLUMN_NAME_SCHOOL_CODE, 110904);
        values.put(SchoolEntry.COLUMN_NAME_SCHOOL_NAME, "Sirinumu Primary School");
        values.put(SchoolEntry.COLUMN_NAME_ADDRESS, "Address");
        values.put(SchoolEntry.COLUMN_NAME_BOARDING_STATUS, "Yes");
        values.put(SchoolEntry.COLUMN_NAME_CENSUS_YEAR, 2017);
        values.put(SchoolEntry.COLUMN_NAME_DISTRICT_CODE, "1102");
        values.put(SchoolEntry.COLUMN_NAME_PROVINCE_CODE, "11");
        values.put(SchoolEntry.COLUMN_NAME_LLGV_CODE, 110903);
        values.put(SchoolEntry.COLUMN_NAME_EMAIL, "wtps@emis.com");
        values.put(SchoolEntry.COLUMN_NAME_FAX, "760906454");
        values.put(SchoolEntry.COLUMN_NAME_LOCALITY, "Locality");
        values.put(SchoolEntry.COLUMN_NAME_PHONE, "0652-72509");
        values.put(SchoolEntry.COLUMN_NAME_SECTOR_CODE, "PRI");
        values.put(SchoolEntry.COLUMN_NAME_WARD_CODE, "ward-code");
        // Insert the new row, returning the primary key value of the new row
        db.insert(SchoolEntry.TABLE_NAME, null, values);

        //---------------------------------------School Entry - 06-------------------------------
        values = new ContentValues();
        values.put(SchoolEntry.COLUMN_NAME_SCHL_ID, 106);
        values.put(SchoolEntry.COLUMN_NAME_SCHOOL_CODE, 131106);
        values.put(SchoolEntry.COLUMN_NAME_SCHOOL_NAME, "Sogeri Elementary School");
        values.put(SchoolEntry.COLUMN_NAME_ADDRESS, "Address");
        values.put(SchoolEntry.COLUMN_NAME_BOARDING_STATUS, "Yes");
        values.put(SchoolEntry.COLUMN_NAME_CENSUS_YEAR, 2017);
        values.put(SchoolEntry.COLUMN_NAME_DISTRICT_CODE, "1311");
        values.put(SchoolEntry.COLUMN_NAME_PROVINCE_CODE, "12");
        values.put(SchoolEntry.COLUMN_NAME_LLGV_CODE, 131106);
        values.put(SchoolEntry.COLUMN_NAME_EMAIL, "ses@emis.com");
        values.put(SchoolEntry.COLUMN_NAME_FAX, "760906454");
        values.put(SchoolEntry.COLUMN_NAME_LOCALITY, "Locality");
        values.put(SchoolEntry.COLUMN_NAME_PHONE, "0652-72509");
        values.put(SchoolEntry.COLUMN_NAME_SECTOR_CODE, "ELE");
        values.put(SchoolEntry.COLUMN_NAME_WARD_CODE, "ward-code");
        // Insert the new row, returning the primary key value of the new row
        db.insert(SchoolEntry.TABLE_NAME, null, values);


        //---------------------------------------School Entry - 07-------------------------------
        values = new ContentValues();
        values.put(SchoolEntry.COLUMN_NAME_SCHL_ID, 107);
        values.put(SchoolEntry.COLUMN_NAME_SCHOOL_CODE, 131107);
        values.put(SchoolEntry.COLUMN_NAME_SCHOOL_NAME, "Sogeri Primary School");
        values.put(SchoolEntry.COLUMN_NAME_ADDRESS, "Address");
        values.put(SchoolEntry.COLUMN_NAME_BOARDING_STATUS, "Yes");
        values.put(SchoolEntry.COLUMN_NAME_CENSUS_YEAR, 2017);
        values.put(SchoolEntry.COLUMN_NAME_DISTRICT_CODE, "1311");
        values.put(SchoolEntry.COLUMN_NAME_PROVINCE_CODE, "12");
        values.put(SchoolEntry.COLUMN_NAME_LLGV_CODE, 131106);
        values.put(SchoolEntry.COLUMN_NAME_EMAIL, "sps@emis.com");
        values.put(SchoolEntry.COLUMN_NAME_FAX, "760906454");
        values.put(SchoolEntry.COLUMN_NAME_LOCALITY, "Locality");
        values.put(SchoolEntry.COLUMN_NAME_PHONE, "0652-72509");
        values.put(SchoolEntry.COLUMN_NAME_SECTOR_CODE, "PRI");
        values.put(SchoolEntry.COLUMN_NAME_WARD_CODE, "ward-code");
        // Insert the new row, returning the primary key value of the new row
        db.insert(SchoolEntry.TABLE_NAME, null, values);

        //---------------------------------------School Entry - 08-------------------------------
        values = new ContentValues();
        values.put(SchoolEntry.COLUMN_NAME_SCHL_ID, 108);
        values.put(SchoolEntry.COLUMN_NAME_SCHOOL_CODE, 110303);
        values.put(SchoolEntry.COLUMN_NAME_SCHOOL_NAME, "St John's Primary School");
        values.put(SchoolEntry.COLUMN_NAME_ADDRESS, "Address");
        values.put(SchoolEntry.COLUMN_NAME_BOARDING_STATUS, "Yes");
        values.put(SchoolEntry.COLUMN_NAME_CENSUS_YEAR, 2017);
        values.put(SchoolEntry.COLUMN_NAME_DISTRICT_CODE, "1103");
        values.put(SchoolEntry.COLUMN_NAME_PROVINCE_CODE, "11");
        values.put(SchoolEntry.COLUMN_NAME_LLGV_CODE, 131106);
        values.put(SchoolEntry.COLUMN_NAME_EMAIL, "stjps@emis.com");
        values.put(SchoolEntry.COLUMN_NAME_FAX, "760906454");
        values.put(SchoolEntry.COLUMN_NAME_LOCALITY, "Locality");
        values.put(SchoolEntry.COLUMN_NAME_PHONE, "0652-72509");
        values.put(SchoolEntry.COLUMN_NAME_SECTOR_CODE, "PRI");
        values.put(SchoolEntry.COLUMN_NAME_WARD_CODE, "ward-code");
        // Insert the new row, returning the primary key value of the new row
        db.insert(SchoolEntry.TABLE_NAME, null, values);

        //---------------------------------------School Entry - 09-------------------------------
        values = new ContentValues();
        values.put(SchoolEntry.COLUMN_NAME_SCHL_ID, 109);
        values.put(SchoolEntry.COLUMN_NAME_SCHOOL_CODE, 120106);
        values.put(SchoolEntry.COLUMN_NAME_SCHOOL_NAME, "Tagana Elementary School");
        values.put(SchoolEntry.COLUMN_NAME_ADDRESS, "Address");
        values.put(SchoolEntry.COLUMN_NAME_BOARDING_STATUS, "Yes");
        values.put(SchoolEntry.COLUMN_NAME_CENSUS_YEAR, 2017);
        values.put(SchoolEntry.COLUMN_NAME_DISTRICT_CODE, "1201");
        values.put(SchoolEntry.COLUMN_NAME_PROVINCE_CODE, "12");
        values.put(SchoolEntry.COLUMN_NAME_LLGV_CODE, 131106);
        values.put(SchoolEntry.COLUMN_NAME_EMAIL, "tes@emis.com");
        values.put(SchoolEntry.COLUMN_NAME_FAX, "760906454");
        values.put(SchoolEntry.COLUMN_NAME_LOCALITY, "Locality");
        values.put(SchoolEntry.COLUMN_NAME_PHONE, "0652-72509");
        values.put(SchoolEntry.COLUMN_NAME_SECTOR_CODE, "ELE");
        values.put(SchoolEntry.COLUMN_NAME_WARD_CODE, "ward-code");
        // Insert the new row, returning the primary key value of the new row
        db.insert(SchoolEntry.TABLE_NAME, null, values);

        //---------------------------------------School Entry - 10-------------------------------
        values = new ContentValues();
        values.put(SchoolEntry.COLUMN_NAME_SCHL_ID, 110);
        values.put(SchoolEntry.COLUMN_NAME_SCHOOL_CODE, 120246);
        values.put(SchoolEntry.COLUMN_NAME_SCHOOL_NAME, "St Theresa Primary School");
        values.put(SchoolEntry.COLUMN_NAME_ADDRESS, "Address");
        values.put(SchoolEntry.COLUMN_NAME_BOARDING_STATUS, "Yes");
        values.put(SchoolEntry.COLUMN_NAME_CENSUS_YEAR, 2017);
        values.put(SchoolEntry.COLUMN_NAME_DISTRICT_CODE, "1202");
        values.put(SchoolEntry.COLUMN_NAME_PROVINCE_CODE, "12");
        values.put(SchoolEntry.COLUMN_NAME_LLGV_CODE, 131106);
        values.put(SchoolEntry.COLUMN_NAME_EMAIL, "tes@emis.com");
        values.put(SchoolEntry.COLUMN_NAME_FAX, "760906454");
        values.put(SchoolEntry.COLUMN_NAME_LOCALITY, "Locality");
        values.put(SchoolEntry.COLUMN_NAME_PHONE, "0652-72509");
        values.put(SchoolEntry.COLUMN_NAME_SECTOR_CODE, "PRI");
        values.put(SchoolEntry.COLUMN_NAME_WARD_CODE, "ward-code");
        // Insert the new row, returning the primary key value of the new row
        db.insert(SchoolEntry.TABLE_NAME, null, values);

        //---------------------------------------School Entry - 11-------------------------------
        values = new ContentValues();
        values.put(SchoolEntry.COLUMN_NAME_SCHL_ID, 111);
        values.put(SchoolEntry.COLUMN_NAME_SCHOOL_CODE, 120129);
        values.put(SchoolEntry.COLUMN_NAME_SCHOOL_NAME, "Tabunomu Elementary School");
        values.put(SchoolEntry.COLUMN_NAME_ADDRESS, "Address");
        values.put(SchoolEntry.COLUMN_NAME_BOARDING_STATUS, "Yes");
        values.put(SchoolEntry.COLUMN_NAME_CENSUS_YEAR, 2017);
        values.put(SchoolEntry.COLUMN_NAME_DISTRICT_CODE, "1201");
        values.put(SchoolEntry.COLUMN_NAME_PROVINCE_CODE, "12");
        values.put(SchoolEntry.COLUMN_NAME_LLGV_CODE, 131106);
        values.put(SchoolEntry.COLUMN_NAME_EMAIL, "tbes@emis.com");
        values.put(SchoolEntry.COLUMN_NAME_FAX, "760906454");
        values.put(SchoolEntry.COLUMN_NAME_LOCALITY, "Locality");
        values.put(SchoolEntry.COLUMN_NAME_PHONE, "0652-72509");
        values.put(SchoolEntry.COLUMN_NAME_SECTOR_CODE, "ELE");
        values.put(SchoolEntry.COLUMN_NAME_WARD_CODE, "ward-code");
        // Insert the new row, returning the primary key value of the new row
        db.insert(SchoolEntry.TABLE_NAME, null, values);

        //---------------------------------------School Entry - 12-------------------------------
        values = new ContentValues();
        values.put(SchoolEntry.COLUMN_NAME_SCHL_ID, 112);
        values.put(SchoolEntry.COLUMN_NAME_SCHOOL_CODE, 131102);
        values.put(SchoolEntry.COLUMN_NAME_SCHOOL_NAME, "St Rock Maipa Primary School");
        values.put(SchoolEntry.COLUMN_NAME_ADDRESS, "Address");
        values.put(SchoolEntry.COLUMN_NAME_BOARDING_STATUS, "Yes");
        values.put(SchoolEntry.COLUMN_NAME_CENSUS_YEAR, 2017);
        values.put(SchoolEntry.COLUMN_NAME_DISTRICT_CODE, "1311");
        values.put(SchoolEntry.COLUMN_NAME_PROVINCE_CODE, "13");
        values.put(SchoolEntry.COLUMN_NAME_LLGV_CODE, 131106);
        values.put(SchoolEntry.COLUMN_NAME_EMAIL, "strmps@emis.com");
        values.put(SchoolEntry.COLUMN_NAME_FAX, "760906454");
        values.put(SchoolEntry.COLUMN_NAME_LOCALITY, "Locality");
        values.put(SchoolEntry.COLUMN_NAME_PHONE, "0652-72509");
        values.put(SchoolEntry.COLUMN_NAME_SECTOR_CODE, "PRI");
        values.put(SchoolEntry.COLUMN_NAME_WARD_CODE, "ward-code");
        // Insert the new row, returning the primary key value of the new row
        db.insert(SchoolEntry.TABLE_NAME, null, values);


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

    public Cursor valueOfCursor(){

        SQLiteDatabase db2 = getReadableDatabase();

        String[] projection = {SchoolEntry.COLUMN_NAME_SCHOOL_CODE,
                SchoolEntry.COLUMN_NAME_SCHOOL_NAME, SchoolEntry.COLUMN_NAME_SCHL_ID};
//                String selection = UserEntry.COLUMN_PET_GENDER + “=?”;
//                String selectionArgs = new String[] { UserEntry.GENDER_FEMALE };

//      Cursor with all the rows from Columns - School Code and School Name
        Cursor cursor = db2.query(SchoolEntry.TABLE_NAME, projection,
                null, null, null, null, null);
//
//        String[] school_codes = new String[cursor.getCount()];
//
//        int i4 = 0;
//        while (cursor.moveToNext()) {
//            school_codes[i4] = cursor.getString(1) + " , " + cursor.getString(0);
//            i4++;
//        }
        return cursor;
    }


    //        INSERT ENROLLMENT BY GRADE FORM IN EnrollmentByGrade Table

    public void insertEnrollmentByGrade(){

    }





/* DATABASE VERSION UPGRADE*/
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


    public static Cursor readEBG(SQLiteDatabase db){

//        String selection = EnrollmentByGradesEntry.COLUMN_NAME_SYNC_STATUS;
//        String[] selectionArgs = {"0"};
        String sortOrder = EnrollmentByGradesEntry._ID + " ASC";

        return db.query(EnrollmentByGradesEntry.TABLE_NAME, null, EnrollmentByGradesEntry.COLUMN_NAME_SYNC_STATUS +  " = " + "0", null, null, null, sortOrder);

    }


    public static void updateEBG(String EBGid, int sync_status, SQLiteDatabase db){

        ContentValues values = new ContentValues();
        values.put(EnrollmentByGradesEntry.COLUMN_NAME_SYNC_STATUS, sync_status);

        String whereClause = EnrollmentByGradesEntry._ID +  " = ?";
        String[] whereArgs = {EBGid};


        db.update(EnrollmentByGradesEntry.TABLE_NAME, values, whereClause, whereArgs);

    }


    /**
     * Inserts User into SQLite DB
     * @param queryValues
     */
    public static void insertUser(HashMap<String, String> queryValues, SQLiteDatabase db) {
        ContentValues values = new ContentValues();
        values.put(DistrictEntry._ID, queryValues.get("Id"));
        values.put(DistrictEntry.COLUMN_NAME_DISTRICT_CODE, queryValues.get("District_Code"));
        values.put(DistrictEntry.COLUMN_NAME_DISTRICT_NAME, queryValues.get("District_Name"));
        values.put(DistrictEntry.COLUMN_NAME_PROVINCE_CODE, queryValues.get("Province_Code"));
        values.put(DistrictEntry.COLUMN_NAME_OLD_DISTRICT_CODE, queryValues.get("Old_District_Code"));
        values.put(DistrictEntry.COLUMN_NAME_OLD_DISTRICT_NAME, queryValues.get("Old_District_Name"));
        db.insert(DistrictEntry.TABLE_NAME, null, values);
    }

    /**
     * Get list of Users from SQLite DB as Array List
     * @return
     */
    public ArrayList<HashMap<String, String>> getAllUsers() {
        ArrayList<HashMap<String, String>> usersList;
        usersList = new ArrayList<HashMap<String, String>>();
        String selectQuery = "SELECT  * FROM users";
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> map = new HashMap<String, String>();
                map.put("userId", cursor.getString(0));
                map.put("userName", cursor.getString(1));
                usersList.add(map);
            } while (cursor.moveToNext());
        }
        cursor.close();
        database.close();
        return usersList;
    }


}
