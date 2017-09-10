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
}
