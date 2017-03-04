package edu.montclair.mobilecomputing.m_alrajab.assignment_1.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by rahul on 2/28/2017.
 */

public class UserDbOperation {
     private UserDbHelper dbHelper;
    private String[] USER_TABLE_COLUMNS = { UserDbHelper.USER_ID,
            UserDbHelper.COL_USERNAME ,
            UserDbHelper.COL_NAME,
            UserDbHelper.COL_EMAIL,
            UserDbHelper.COL_PWD,
            UserDbHelper.COL_MAJORS};

    private SQLiteDatabase database;

    public UserDbOperation(Context context) {
        dbHelper = new UserDbHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }
    public void close() {
        dbHelper.close();
    }
// Add user Info into database
    public void addUser(UserInfo userInfo){
        ContentValues values=new ContentValues();
        values.put(UserDbHelper.COL_USERNAME,userInfo.getUserName());
        values.put(UserDbHelper.COL_NAME,userInfo.getName());
        values.put(UserDbHelper.COL_EMAIL,userInfo.getEmail());
        values.put(UserDbHelper.COL_PWD,userInfo.getPwd());
        values.put(UserDbHelper.COL_MAJORS,userInfo.getMajor());
        values.put(UserDbHelper.COL_DOB,userInfo.getMajor());

        long studId = database.insert(UserDbHelper.TABLE_NAME, null, values);
        System.out.print("Student added successfully");
    }

    //Before login check user info is valid or not
    public int checkValiduser(String userName,String pwd){
        int result=0;
        String savedName;
        String query="SELECT * FROM "+ UserDbHelper.TABLE_NAME+" WHERE "+ UserDbHelper.COL_USERNAME+" = '"+userName+"' and "+
                UserDbHelper.COL_PWD+"= '"+pwd+"'";
        Cursor cursor=database.rawQuery(query, null);
        int unameIndex = cursor.getColumnIndex(UserDbHelper.COL_USERNAME);

        if(cursor!=null && cursor.moveToFirst()){
            savedName=cursor.getString(unameIndex);

            if (savedName.equals(userName.toString())) {
                result=1;
            }
            else
                result=0;
            }


        return result;

    }


    //To get all user info
    public UserInfo getUserDeatils(String userName){
        UserInfo userInfo=null;
        String savedName,savedPwd;
        String query="SELECT * FROM "+ UserDbHelper.TABLE_NAME+" WHERE "+ UserDbHelper.COL_USERNAME+" = '"+userName+"'";
        Cursor cursor=database.rawQuery(query, null);
        int unameIndex = cursor.getColumnIndex(UserDbHelper.COL_USERNAME);
        int pwdIndex = cursor.getColumnIndex(UserDbHelper.COL_PWD);

        if(cursor!=null && cursor.moveToFirst()){
            savedName=cursor.getString(unameIndex);
            savedPwd=cursor.getString(pwdIndex);

            if (savedName.equals(userName.toString())) {
                userInfo=new UserInfo();
                userInfo.setPwd(savedPwd);
            }
            else
                userInfo=null;
        }


        return userInfo;

    }

    //To update user info
    public int updateUser(UserInfo userInfo){
        int result=0;

        ContentValues values=new ContentValues();
        //values.put(UserDbHelper.COL_USERNAME,userInfo.getUserName());
        values.put(UserDbHelper.COL_NAME,userInfo.getName());
        values.put(UserDbHelper.COL_EMAIL,userInfo.getEmail());
        values.put(UserDbHelper.COL_PWD,userInfo.getPwd());
        values.put(UserDbHelper.COL_MAJORS,userInfo.getMajor());
        values.put(UserDbHelper.COL_DOB,userInfo.getMajor());
        result= database.update(UserDbHelper.TABLE_NAME, values, UserDbHelper.COL_USERNAME + " LIKE ?",
                new String[] { String.valueOf(userInfo.getUserName()) });

        return result;
    }
}
