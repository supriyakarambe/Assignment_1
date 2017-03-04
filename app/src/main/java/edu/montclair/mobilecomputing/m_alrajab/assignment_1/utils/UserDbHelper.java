package edu.montclair.mobilecomputing.m_alrajab.assignment_1.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by rahul on 2/28/2017.
 */

public class UserDbHelper extends SQLiteOpenHelper {

    public static final String TABLE_NAME ="User_Data";
    public static final String USER_ID ="_id";
    public static final String COL_USERNAME ="user_name";
    public static final String COL_NAME ="name";
    public static final String COL_EMAIL ="email";
    public static final String COL_DOB ="dob";
    public static final String COL_PWD ="password";
    public static final String COL_MAJORS ="majors";

        // If you change the database schema, you must increment the database version.
        public static final int DATABASE_VERSION = 1;
        public static final String DATABASE_NAME = "User.db";

    // creation SQLite statement

    private static final String DATABASE_CREATE = "create table " + TABLE_NAME
            + "(" + USER_ID + " integer primary key autoincrement, "
            + COL_USERNAME + " text not null" +","
            + COL_NAME + " text not null" +","
            + COL_EMAIL + " text not null" +","
            + COL_PWD + " text not null" +","
            + COL_DOB + " text not null" +","
            + COL_MAJORS + " text not null" +
            ");";


    public UserDbHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        System.out.print("inside UserDB");
        }
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(DATABASE_CREATE);
        }
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            // This database is only a cache for online data, so its upgrade policy is
            // to simply to discard the data and start over
            db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
            onCreate(db);
        }
        public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            onUpgrade(db, oldVersion, newVersion);
        }
    }

