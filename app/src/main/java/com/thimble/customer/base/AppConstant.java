package com.thimble.customer.base;

/**
 * Created by satyabrata on 4/7/18.
 */

public class AppConstant {

    public static final int SPLASH_TIME=3000;
    public static final int CAMERA_PERMISSION_REQUEST_CODE=1000;
    public static final int EXTERNAL_STORAGE_PERMISSION_REQUEST_CODE=2000;
    public static final int REQUEST_LOCATION = 0;
    public static final int REQUEST_CHECK_SETTINGS_GPS = 4000;
    public static final int UPDATE_INTERVAL = 3000; // 3 sec
    public static final int FATEST_INTERVAL = 1000; // 1 sec
    public static final int DISPLACEMENT =5;
    public static final int PAGINATION_LIMIT = 10;

    // PREFERENCE KEYS
    public static final String PREFS_NAME="AppCustomerAdd";
    public static final String ACCESS_TOKEN="access-token";
    public static final String IS_LOGIN="loggedIn";
    public static final String USER_TYPE="loggedin_user_type";
    public static final String LOGIN_PREF="loggedin_user_info";
    public static final String SEARCH_SUGGESTION="previous_search_items";


    public static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+" +
            "(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";


    public static final String OUTSIDE_PIC = "image_type_outside";
    public static final String INSIDE_PIC = "image_type_inside";
    public static final String SECTION_PIC = "image_type_section";




}
