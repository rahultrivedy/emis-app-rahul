package com.saysweb.emis_app;

/**
 * Created by rahultrivedy on 2/13/18.
 */

public class GradesData {

    private String grade_code;
    private String grade_name;
    private String sector_code;
    private int grade_sort;
    private int pk_code;
    private String active;

    public String getGrade_code() {
        return grade_code;
    }

    public void setGrade_code(String grade_code) {
        this.grade_code = grade_code;
    }

    public String getGrade_name() {
        return grade_name;
    }

    public void setGrade_name(String grade_name) {
        this.grade_name = grade_name;
    }

    public String getSector_code() {
        return sector_code;
    }

    public void setSector_code(String sector_code) {
        this.sector_code = sector_code;
    }

    public int getGrade_sort() {
        return grade_sort;
    }

    public void setGrade_sort(int grade_sort) {
        this.grade_sort = grade_sort;
    }

    public int getPk_code() {
        return pk_code;
    }

    public void setPk_code(int pk_code) {
        this.pk_code = pk_code;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }


    @Override
    public String toString() {
        return "GradesData{" +
                "grade_code='" + grade_code + '\'' +
                ", grade_name='" + grade_name + '\'' +
                ", sector_code='" + sector_code + '\'' +
                ", grade_sort=" + grade_sort +
                ", pk_code=" + pk_code +
                ", active='" + active + '\'' +
                '}';
    }
}
