package com.saysweb.emis_app;

/**
 * Created by rahultrivedy on 17/09/17.
 */

public class ListItemGcc {
    private String grade;
    private String noOfClasses;
    private String fStudCount;
    private String mStudCount;
    private String fTeachCount;
    private String mTeachCount;
    private String date;


    public String getGrade() {
        return grade;
    }

    public String getNoOfClasses() {
        return noOfClasses;
    }

    public String getfStudCount() {
        return fStudCount;
    }

    public String getmStudCount() {
        return mStudCount;
    }

    public String getfTeachCount() {
        return fTeachCount;
    }

    public String getmTeachCount() {
        return mTeachCount;
    }

    public String getDate() {
        return date;
    }


    public ListItemGcc(String grade, String noofclasses, String fstudcount, String mstudcount, String fteachcount, String mteachcount, String date) {
        this.grade = grade;
        this.noOfClasses = noofclasses;
        this.fStudCount = fstudcount;
        this.mStudCount = mstudcount;
        this.fTeachCount = fteachcount;
        this.mTeachCount = mteachcount;
        this.date = date;
    }
}

