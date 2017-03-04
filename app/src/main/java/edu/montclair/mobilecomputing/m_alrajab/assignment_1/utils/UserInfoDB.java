package edu.montclair.mobilecomputing.m_alrajab.assignment_1.utils;

/**
 * Created by rahul on 2/28/2017.
 */
//Not used
public class UserInfoDB {
    public UserInfoDB() {
    }
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + LoginTableReaderContract.logintableEntry.TABLE_NAME + " (" +
                    LoginTableReaderContract.logintableEntry._ID + " INTEGER PRIMARY KEY," +
                    LoginTableReaderContract.logintableEntry.COL_USERNAME + " TEXT," +
                    LoginTableReaderContract.logintableEntry.COL_NAME + " TEXT," +
                    LoginTableReaderContract.logintableEntry.COL_PWD + " TEXT," +
                    LoginTableReaderContract.logintableEntry.COL_EMAIL + " TEXT," +
                    LoginTableReaderContract.logintableEntry.COL_MAJORS + " TEXT)";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + LoginTableReaderContract.logintableEntry.TABLE_NAME;
}

