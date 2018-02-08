package com.saysweb.emis_app;

/**
 * Created by rahultrivedy on 1/19/18.
 */

public class SchoolData {
    private int schl_id;
    private String school_code;
    private String school_name;
    private String province_code;
    private String province_name;
    private String district_code;
    private String district_name;
    private String llgv_code;
    private String llgv_name;
    private String sector_code;

    public int getSchl_id() {
        return schl_id;
    }

    public void setSchl_id(int schl_id) {
        this.schl_id = schl_id;
    }

    public String getSchool_code() {
        return school_code;
    }

    public void setSchool_code(String school_code) {
        this.school_code = school_code;
    }

    public String getSchool_name() {
        return school_name;
    }

    public void setSchool_name(String school_name) {
        this.school_name = school_name;
    }

    public String getProvince_code() {
        return province_code;
    }

    public void setProvince_code(String province_code) {
        this.province_code = province_code;
    }

    public String getProvince_name() {
        return province_name;
    }

    public void setProvince_name(String province_name) {
        this.province_name = province_name;
    }

    public String getDistrict_code() {
        return district_code;
    }

    public void setDistrict_code(String district_code) {
        this.district_code = district_code;
    }

    public String getDistrict_name() {
        return district_name;
    }

    public void setDistrict_name(String district_name) {
        this.district_name = district_name;
    }

    public String getLlgv_code() {
        return llgv_code;
    }

    public void setLlgv_code(String llgv_code) {
        this.llgv_code = llgv_code;
    }

    public String getLlgv_name() {
        return llgv_name;
    }

    public void setLlgv_name(String llgv_name) {
        this.llgv_name = llgv_name;
    }

    public String getSector_code() {
        return sector_code;
    }

    public void setSector_code(String sector_code) {
        this.sector_code = sector_code;
    }

    @Override
    public String toString() {
        return "SchoolData{" +
                "schl_id=" + schl_id +
                ", school_code='" + school_code + '\'' +
                ", school_name='" + school_name + '\'' +
                ", province_code='" + province_code + '\'' +
                ", province_name='" + province_name + '\'' +
                ", district_code='" + district_code + '\'' +
                ", district_name='" + district_name + '\'' +
                ", llgv_code='" + llgv_code + '\'' +
                ", llgv_name='" + llgv_name + '\'' +
                ", sector_code='" + sector_code + '\'' +
                '}';
    }


}
