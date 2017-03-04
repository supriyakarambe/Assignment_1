package edu.montclair.mobilecomputing.m_alrajab.assignment_1.utils;

import android.content.Context;
import android.content.SharedPreferences;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Map;

/**
 * Created by rahul on 2/9/2017.
 */

public class Util {
    public final static String MSG_KEY_INTENT="Msg_key";

    public static String[] getListFromFile(Context context, String key){
        ArrayList<String> lst= new ArrayList<>();
        ArrayList<String> lstBody= new ArrayList<>();
        String tenpStr="";
        try {

            File fileDir=context.getFilesDir();
            File[] files=fileDir.listFiles();
            for (File file: files){
                //if(file.getName().equals(key))
                lst.add(file.getName().toString());
            }

            FileInputStream inputStream=context.openFileInput(key.toString().replace(" ",""));
            int c;
            while ((c=inputStream.read())!=-1){
                tenpStr+=Character.toString((char) c);
            }
            lstBody.add(tenpStr);
            inputStream.close();

        }catch (Exception e){}
        if(key.equals("Title"))
            return lst.toArray(new String[lst.size()]);
        else
            return lstBody.toArray(new String[lst.size()]);
    }


    public static final String SHARED_PREF_FILENAME="edu.montclair.mobilecomputing.m_alrajab.lec10_sharedpref_org.SHAREDFILE1";
    public static final String KEY_PATH="Title_";
    public static final String KEY_BODY="Body_";

    //Get list of visited pages from Shared pref
    public static String[] getListFromSP(Context context, String key){
        SharedPreferences sharedPreferences=context.getSharedPreferences(SHARED_PREF_FILENAME,
                Context.MODE_APPEND);
        // value datatype in map is unknown
        Map<String, ?> map=sharedPreferences.getAll();
        ArrayList<String> lst= new ArrayList<>();
        for(String str:map.keySet()){
            if(str.startsWith(key))
                lst.add((String)map.get(str));
        }
        return lst.toArray(new String[lst.size()]);
    }

}
