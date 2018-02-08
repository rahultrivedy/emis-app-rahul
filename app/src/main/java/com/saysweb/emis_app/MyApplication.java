package com.saysweb.emis_app;

import android.app.Application;

/**
 * Created by rahultrivedy on 14/09/17.
 */

public class MyApplication extends Application{



    private String global_censusYear;
    private String global_autocompleteString = "";
    private String global_schoolCode;
    private String global_schlID;
    private String global_userID;



    public String getGlobal_censusYear() {
        return global_censusYear;
    }

    public String getGlobal_schoolCode() {
        return global_schoolCode;
    }

    public String getGlobal_schlID() {
        return global_schlID;
    }

    public String getGlobal_userID() {
        return global_userID;
    }

    public String getGlobal_autocompleteString() {
        return global_autocompleteString;
    }




    public void setGlobal_censusYear(String str) {
        global_censusYear = str;
    }

    public void setGlobal_schoolCode(String str) {
        global_schoolCode = str;
    }

    public void setGlobal_schlID(String str) {
        global_schlID = str;
    }

    public void setGlobal_userID(String str) {
        global_userID = str;
    }

    public void setGlobal_autocompleteString(String str) {
        global_autocompleteString = str;
    }


}


