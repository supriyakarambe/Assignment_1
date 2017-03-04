package edu.montclair.mobilecomputing.m_alrajab.assignment_1;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

import static edu.montclair.mobilecomputing.m_alrajab.assignment_1.utils.Util.KEY_BODY;
import static edu.montclair.mobilecomputing.m_alrajab.assignment_1.utils.Util.KEY_PATH;
import static edu.montclair.mobilecomputing.m_alrajab.assignment_1.utils.Util.SHARED_PREF_FILENAME;
import static edu.montclair.mobilecomputing.m_alrajab.assignment_1.utils.Util.getListFromSP;


public class SharedPreData extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    private static final String EXTRA_PATH = "path";
    private String path;

    ArrayAdapter<String> mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shared_pre_data);

        if (savedInstanceState == null) {
            path = getPath();
        } else {
            path = savedInstanceState.getString(EXTRA_PATH, "");
        }

        sharedPreferences=getSharedPreferences(SHARED_PREF_FILENAME, Context.MODE_APPEND);
        editor=sharedPreferences.edit();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd/HH:mm:ss");
        String currentDateandTime = sdf.format(new Date());

        int count=1;
        editor.putString(KEY_PATH+count,path+" "+currentDateandTime  );
        editor.putString(KEY_BODY+count++,currentDateandTime );
        editor.commit();

        //Get All the browsing history of user and show it
        final ListView ls=(ListView)findViewById(R.id.list_frg);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, getListFromSP(this,"Title_"));
        ls.setAdapter(adapter);


        ls.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                // ListView Clicked item index
                int itemPosition     = position;

                // ListView Clicked item value
                String  itemValue    = (String) ls.getItemAtPosition(position);

                // Show Alert
                Toast.makeText(getApplicationContext(),
                        "Position :"+itemPosition+"  ListItem : " +itemValue , Toast.LENGTH_LONG)
                        .show();

            }
        });

    }

    public String getPath() {
        if (path == null) {
           path = this.getClass().getSimpleName();
        }
        return path;
    }
}
