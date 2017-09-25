package com.saysweb.emis_app;

/**
 * Created by rahultrivedy on 17/09/17.
 */

public class ListItemEB {
    private String grade;
    private String female;
    private String male;
    private String date;


    public String getGrade() {
        return grade;
    }

    public String getFemale() {
        return female;
    }

    public String getMale() {
        return male;
    }

    public String getDate() {
        return date;
    }



    public ListItemEB(String grade,  String female, String male, String date) {
        this.grade = grade;
        this.female = female;
        this.male = male;
        this.date = date;
    }
}

