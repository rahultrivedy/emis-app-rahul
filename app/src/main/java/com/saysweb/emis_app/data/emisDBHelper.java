package com.saysweb.emis_app.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.saysweb.emis_app.data.emisContract.UserEntry;
import com.saysweb.emis_app.data.emisContract.ProvinceEntry;
import com.saysweb.emis_app.data.emisContract.DistrictEntry;
import com.saysweb.emis_app.data.emisContract.LlgvEntry;
import com.saysweb.emis_app.data.emisContract.SchoolEntry;
/**
 * Created by sukant on 05/09/17.
 * This class extends SQLiteOpenHelper which is used to create database.
 */
public class emisDBHelper extends SQLiteOpenHelper{

    /** Name of the database file. */
    private static final String DATABASE_NAME = "emis.db";

    /** DATABASE Version. Change the version in case you update the schema. */
    private static final int DATABASE_VERSION = 1;

    /** Setting up the constructor. */
    public emisDBHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //Create string that contains the sql statement to create user table
        String SQL_CREATE_USER_TABLE = "CREATE TABLE " + UserEntry.TABLE_NAME + " ("
                + UserEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + UserEntry.COLUMN_NAME_USER_ID + " INTEGER NOT NULL, "
                + UserEntry.COLUMN_NAME_USER_NAME + " TEXT NOT NULL, "
                + UserEntry.COLUMN_NAME_PASSWORD + " TEXT NOT NULL, "
                + UserEntry.COLUMN_NAME_EMP_CODE + " TEXT, "
                + UserEntry.COLUMN_NAME_EMP_NAME + " TEXT, "
                + UserEntry.COLUMN_NAME_EMAIL + " TEXT, "
                + UserEntry.COLUMN_NAME_MOBILE_NO + " TEXT);";

        db.execSQL(SQL_CREATE_USER_TABLE);

        //CREATING PROVICE_TABLE TABLE
        String SQL_CREATE_PROVINCE_TABLE = "CREATE TABLE " + ProvinceEntry.TABLE_NAME + " ("
                + ProvinceEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + ProvinceEntry.COLUMN_NAME_PROVINCE_CODE+ " TEXT NOT NULL, "
                + ProvinceEntry.COLUMN_NAME_PROVINCE_NAME + " TEXT NOT NULL, "
                + ProvinceEntry.COLUMN_NAME_OLD_PROVINCE_CODE + " TEXT, "
                + ProvinceEntry.COLUMN_NAME_OLD_PROVINCE_NAME + " TEXT);";

        db.execSQL(SQL_CREATE_PROVINCE_TABLE);

        //CREATING DISTRICT_TABLE TABLE
        String SQL_CREATE_DISTRICT_TABLE = "CREATE TABLE " + DistrictEntry.TABLE_NAME + " ("
                + DistrictEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + DistrictEntry.COLUMN_NAME_DISTRICT_CODE+ " TEXT NOT NULL, "
                + DistrictEntry.COLUMN_NAME_DISTRICT_NAME + " TEXT NOT NULL, "
                + DistrictEntry.COLUMN_NAME_PROVINCE_CODE + " TEXT NOT NULL, "
                + DistrictEntry.COLUMN_NAME_OLD_DISTRICT_CODE + " TEXT, "
                + DistrictEntry.COLUMN_NAME_OLD_DISTRICT_NAME + " TEXT);";

        db.execSQL(SQL_CREATE_DISTRICT_TABLE);

        //CREATING LOCAL_GOVT_TABLE TABLE
        String SQL_CREATE_LLGV_TABLE = "CREATE TABLE " + LlgvEntry.TABLE_NAME + " ("
                + LlgvEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + LlgvEntry.COLUMN_NAME_LLGV_CODE+ " TEXT NOT NULL, "
                + LlgvEntry.COLUMN_NAME_LLGV_NAME + " TEXT NOT NULL, "
                + LlgvEntry.COLUMN_NAME_PROVINCE_CODE + " TEXT NOT NULL, "
                + LlgvEntry.COLUMN_NAME_OLD_LLGV_CODE + " TEXT, "
                + LlgvEntry.COLUMN_NAME_OLD_LLGV_NAME + " TEXT);";

        db.execSQL(SQL_CREATE_LLGV_TABLE);

        //CREATING SCHOOLS_TABLE TABLE
        String SQL_CREATE_SCHOOLS_TABLE = "CREATE TABLE " + SchoolEntry.TABLE_NAME + " ("
                + SchoolEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + SchoolEntry.COLUMN_NAME_SCHL_ID + " INTEGER NOT NULL, "
                + SchoolEntry.COLUMN_NAME_SCHOOL_CODE+ " TEXT NOT NULL, "
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
                + SchoolEntry.COLUMN_NAME_LLGV_CODE + " TEXT);"
                + SchoolEntry.COLUMN_NAME_DISTRICT_CODE + " TEXT);"
                + SchoolEntry.COLUMN_NAME_PROVINCE_CODE + " TEXT);"
                + SchoolEntry.COLUMN_NAME_SCHOOL_REGISTERED + " TEXT);"
                + SchoolEntry.COLUMN_NAME_POSITION_NORTH_LAT + " TEXT);"
                + SchoolEntry.COLUMN_NAME_POSITION_EAST_LONG + " TEXT);"
                + SchoolEntry.COLUMN_NAME_CENSUS_YEAR + " TEXT);";

        db.execSQL(SQL_CREATE_SCHOOLS_TABLE);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
