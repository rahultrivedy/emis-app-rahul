package com.saysweb.emis_app.data;

import android.provider.BaseColumns;

/**
 * Created by sukant on 05/09/17.
 * Creating DB contracts..
 */

public final class emisContract {
    /* Inner class that defines the table contents */

    public static abstract class UserEntry implements BaseColumns {
        public static final String TABLE_NAME = "USER_TABLE";
        public static final String COLUMN_NAME_USER_ID = "USER_ID";
        public static final String COLUMN_NAME_USER_NAME = "USER_NAME";
        public static final String COLUMN_NAME_PASSWORD = "PASSWORD";
        public static final String COLUMN_NAME_EMP_CODE = "EMP_CODE";
        public static final String COLUMN_NAME_EMP_NAME = "EMP_NAME";
        public static final String COLUMN_NAME_EMAIL = "EMAIL";
        public static final String COLUMN_NAME_MOBILE_NO = "MOBILE_NO";
    }

    public static abstract class ProvinceEntry implements BaseColumns {
        public static final String TABLE_NAME = "PROVINCE_TABLE";
        public static final String COLUMN_NAME_PROVINCE_CODE = "PROVINCE_CODE";
        public static final String COLUMN_NAME_PROVINCE_NAME = "PROVINCE_NAME";
        public static final String COLUMN_NAME_OLD_PROVINCE_CODE = "OLD_PROVINCE_CODE";
        public static final String COLUMN_NAME_OLD_PROVINCE_NAME = "OLD_PROVINCE_NAME";
    }

    public static abstract class DistrictEntry implements BaseColumns {
        public static final String TABLE_NAME = "DISTRICT_TABLE";
        public static final String COLUMN_NAME_DISTRICT_CODE = "DISTRICT_CODE";
        public static final String COLUMN_NAME_DISTRICT_NAME = "DISTRICT_NAME";
        public static final String COLUMN_NAME_PROVINCE_CODE = "PROVINCE_CODE";
        public static final String COLUMN_NAME_OLD_DISTRICT_CODE = "OLD_DISTRICT_CODE";
        public static final String COLUMN_NAME_OLD_DISTRICT_NAME = "OLD_DISTRICT_NAME";
    }

    public static abstract class LlgvEntry implements BaseColumns {
        public static final String TABLE_NAME = "LOCAL_GOVERNMENT_TABLE";
        public static final String COLUMN_NAME_LLGV_CODE = "LLGV_CODE";
        public static final String COLUMN_NAME_LLGV_NAME = "LLGV_NAME";
        public static final String COLUMN_NAME_PROVINCE_CODE = "PROVINCE_CODE";
        public static final String COLUMN_NAME_DISTRICT_CODE = "DISTRICT_CODE";
        public static final String COLUMN_NAME_OLD_LLGV_CODE = "OLD_LLGV_CODE";
        public static final String COLUMN_NAME_OLD_LLGV_NAME = "OLD_LLGV_NAME";
    }

    public static abstract class SchoolEntry implements BaseColumns {
        public static final String TABLE_NAME = "SCHOOLS_TABLE";
        public static final String COLUMN_NAME_SCHL_ID = "SCHL_ID";
        public static final String COLUMN_NAME_SCHOOL_CODE = "SCHOOL_CODE";
        public static final String COLUMN_NAME_SCHOOL_NAME = "SCHOOL_NAME";
        public static final String COLUMN_NAME_SCHOOL_LEVEL = "SCHOOL_LEVEL";
        public static final String COLUMN_NAME_BOARDING_STATUS = "BOARDING_STATUS";
        public static final String COLUMN_NAME_SCHOOL_STATUS = "SCHOOL_STATUS";
        public static final String COLUMN_NAME_LOCALITY = "LOCALITY";
        public static final String COLUMN_NAME_ADDRESS = "ADDRESS";
        public static final String COLUMN_NAME_PHONE = "PHONE";
        public static final String COLUMN_NAME_FAX = "FAX";
        public static final String COLUMN_NAME_EMAIL = "EMAIL";
        public static final String COLUMN_NAME_SECTOR_CODE = "SECTOR_CODE";
        public static final String COLUMN_NAME_WARD_CODE = "WARD_CODE";
        public static final String COLUMN_NAME_LLGV_CODE = "LLGV_CODE";
        public static final String COLUMN_NAME_DISTRICT_CODE = "DISTRICT_CODE";
        public static final String COLUMN_NAME_PROVINCE_CODE = "PROVINCE_CODE";
        public static final String COLUMN_NAME_SCHOOL_REGISTERED = "SCHOOL_REGISTERED";
        public static final String COLUMN_NAME_POSITION_NORTH_LAT = "POSITION_NORTH_LAT";
        public static final String COLUMN_NAME_POSITION_EAST_LONG = "POSITION_EAST_LONG";
        public static final String COLUMN_NAME_CENSUS_YEAR = "CENSUS_YEAR";
    }


    public static abstract class EnrollmentByGradesEntry implements BaseColumns {
        public static final String TABLE_NAME = "ENROLLMENT_BY_GRADE";
        public static final String COLUMN_NAME_GRADE_CODE = "GRADE_CODE";
        public static final String COLUMN_NAME_CENSUS_YEAR = "CENSUS_YEAR";
        public static final String COLUMN_NAME_BIRTH_YEAR = "BIRTH_YEAR";
        public static final String COLUMN_NAME_AGE = "AGE";
        public static final String COLUMN_NAME_MALE_COUNT = "MALE_COUNT";
        public static final String COLUMN_NAME_FEMALE_COUNT = "FEMALE_COUNT";
        public static final String COLUMN_NAME_SCHL_ID = "SCHL_ID";
        public static final String COLUMN_NAME_CREATED_DATE = "CREATED_DATE";
        public static final String COLUMN_NAME_CREATED_BY = "CREATED_BY";
        public static final String COLUMN_NAME_UPDATED_DATE = "UPDATED_DATE";
        public static final String COLUMN_NAME_UPDATED_BY = "UPDATED_BY ";
        public static final String COLUMN_NAME_SYNC_STATUS = "STATUS";


    }

    public static abstract class GradeClassCountEntry implements BaseColumns {
        public static final String TABLE_NAME = "GRADE_CLASS_COUNT";
        public static final String COLUMN_NAME_GCC_ID = "GCC_ID";
        public static final String COLUMN_NAME_CENSUS_YEAR = "CENSUS_YEAR";
        public static final String COLUMN_NAME_GRADE_CODE = "GRADE_CODE";
        public static final String COLUMN_NAME_CLASS_COUNT = "CLASS_COUNT";
        public static final String COLUMN_NAME_SCHL_ID = "SCHL_ID";
        public static final String COLUMN_NAME_STUDENT_MALE_COUNT = "STUDENT_MALE_COUNT";
        public static final String COLUMN_NAME_STUDENT_FEMALE_COUNT = "STUDENT_FEMALE_COUNT";
        public static final String COLUMN_NAME_TEACHER_MALE_COUNT = "TEACHER_MALE_COUNT";
        public static final String COLUMN_NAME_TEACHER_FEMALE_COUNT = "TEACHER_FEMALE_COUNT";
        public static final String COLUMN_NAME_QTR = "QTR";
        public static final String COLUMN_NAME_CREATED_DATE = "CREATED_DATE";
        public static final String COLUMN_NAME_CREATED_BY = "CREATED_BY";
        public static final String COLUMN_NAME_UPDATED_DATE = "UPDATED_DATE";
        public static final String COLUMN_NAME_UPDATED_BY = "UPDATED_BY ";
        public static final String COLUMN_NAME_SYNC_STATUS = "STATUS";
    }


    public static abstract class EnrollmentByBoardingEntry implements BaseColumns {
        public static final String TABLE_NAME = "ENROLLMENT_BY_BOARDING";
        public static final String COLUMN_NAME_ENROLLMENT_BOARDING_ID = "ENROLLMENT_BOARDING_ID";
        public static final String COLUMN_NAME_GRADE_CODE = "GRADE_CODE";
        public static final String COLUMN_NAME_CENSUS_YEAR = "CENSUS_YEAR";
        public static final String COLUMN_NAME_MALE_BOARDING_COUNT = "MALE_BOARDING_COUNT";
        public static final String COLUMN_NAME_MALE_DAY_COUNT = "MALE_DAY_COUNT";
        public static final String COLUMN_NAME_FEMALE_BOARDING_COUNT = "FEMALE_BOARDING_COUNT";
        public static final String COLUMN_NAME_FEMALE_DAY_COUNT = "FEMALE_DAY_COUNT";
        public static final String COLUMN_NAME_SCHL_ID = "SCHL_ID";
        public static final String COLUMN_NAME_CREATED_DATE = "CREATED_DATE";
        public static final String COLUMN_NAME_CREATED_BY = "CREATED_BY";
        public static final String COLUMN_NAME_UPDATED_DATE = "UPDATED_DATE";
        public static final String COLUMN_NAME_UPDATED_BY = "UPDATED_BY ";
        public static final String COLUMN_NAME_SYNC_STATUS = "STATUS";
    }

    public static abstract class GradeEntry implements BaseColumns {
        public static final String TABLE_NAME = "GRADE_TABLE";
        public static final String COLUMN_NAME_GRADE_CODE= "GRADE_CODE";
        public static final String COLUMN_NAME_GRADE_NAME = "GRADE_NAME";
        public static final String COLUMN_NAME_SECTOR_CODE = "SECTOR_CODE";
        public static final String COLUMN_NAME_GRADE_SORT = "GRADE_SORT";
        public static final String COLUMN_NAME_PK_CODE = "PK_CODE";
        public static final String COLUMN_NAME_ACTIVE = "ACTIVE";
    }


}
