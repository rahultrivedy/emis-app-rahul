package com.saysweb.emis_app.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.saysweb.emis_app.MainActivity;
import com.saysweb.emis_app.data.emisDBHelper;
import com.saysweb.emis_app.data.emisContract.UserEntry;

/**
 * Created by sukant on 10/09/17.
 */
public class emisDBEntry {
    private emisDBHelper mDbHelper;

    public void insertUser(){
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        // Create a new map of values, where column names are the keys
        String strValI = new String();
        int i;
        for(i = 10; i <= 15; i++){
            strValI= Integer.toString(i);
            ContentValues values = new ContentValues();
            values.put(UserEntry.COLUMN_NAME_USER_ID, i);
            values.put(UserEntry.COLUMN_NAME_USER_NAME, "user" + i);
            values.put(UserEntry.COLUMN_NAME_PASSWORD, "password" + i);
            values.put(UserEntry.COLUMN_NAME_EMP_CODE, "Emp" + i);
            values.put(UserEntry.COLUMN_NAME_EMP_NAME, "Name" + i);
            values.put(UserEntry.COLUMN_NAME_EMAIL, "rahultrivedy" + i + "@gmail.com");
            values.put(UserEntry.COLUMN_NAME_MOBILE_NO, "98343204" + i);

            // Insert the new row, returning the primary key value of the new row
            long newRowId = db.insert(UserEntry.TABLE_NAME, null, values);

            Log.v("MainActivity","New Row Id " + newRowId);
        }

    }
}
