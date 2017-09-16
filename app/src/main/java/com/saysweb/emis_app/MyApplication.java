package com.saysweb.emis_app;

import android.app.Application;

/**
 * Created by rahultrivedy on 14/09/17.
 */

public class MyApplication extends Application{



    private String global_censusYear;
    private String global_schoolCode;
    private String global_schlID;



    public String getGlobal_censusYear() {
        return global_censusYear;
    }


    public String getGlobal_schoolCode() {
        return global_schoolCode;
    }

    public String getGlobal_schlID() {
        return global_schlID;
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


}
