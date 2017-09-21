package com.saysweb.emis_app;

/**
 * Created by rahultrivedy on 14/09/17.
 */

public class ListItem {
    private String grade;
    private String birthYr;
    private String fCount;
    private String mCount;
    private String date;

    public String getGrade() {
        return grade;
    }

    public String getBirthYr() {
        return birthYr;
    }

    public String getfCount() {
        return fCount;
    }

    public String getmCount() {
        return mCount;
    }

    public String getDate() {
        return date;
    }

    public ListItem(String grade, String birthyr, String fcount, String mcount, String date) {
        this.grade = grade;
        this.birthYr = birthyr;
        this.fCount = fcount;
        this.mCount = mcount;
        this.date = date;

    }
}
