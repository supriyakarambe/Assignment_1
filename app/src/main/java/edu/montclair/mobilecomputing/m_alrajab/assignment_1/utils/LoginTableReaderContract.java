package edu.montclair.mobilecomputing.m_alrajab.assignment_1.utils;

import android.provider.BaseColumns;

/**
 * Created by rahul on 2/28/2017.
 */

public final class LoginTableReaderContract {
    public LoginTableReaderContract() {
    }

    public static class logintableEntry implements BaseColumns{
        public static final String TABLE_NAME ="User Data";
        public static final String COL_USERNAME ="user_name";
        public static final String COL_NAME ="name";
        public static final String COL_EMAIL ="email";
        public static final String COL_PWD ="password";
        public static final String COL_MAJORS ="majors";

    }
}
