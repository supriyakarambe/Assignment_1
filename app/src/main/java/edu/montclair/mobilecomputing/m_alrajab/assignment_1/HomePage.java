package edu.montclair.mobilecomputing.m_alrajab.assignment_1;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import edu.montclair.mobilecomputing.m_alrajab.assignment_1.utils.UserInfo;

import static edu.montclair.mobilecomputing.m_alrajab.assignment_1.utils.Util.KEY_BODY;
import static edu.montclair.mobilecomputing.m_alrajab.assignment_1.utils.Util.KEY_PATH;
import static edu.montclair.mobilecomputing.m_alrajab.assignment_1.utils.Util.MSG_KEY_INTENT;
import static edu.montclair.mobilecomputing.m_alrajab.assignment_1.utils.Util.SHARED_PREF_FILENAME;

public class HomePage extends AppCompatActivity implements TileFragment.OnFragmentInteractionListener {
    TextView txtuserName;
    TextView tv;
    FragmentTransaction transaction;
    Button btnAddNote;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    private static final String EXTRA_PATH = "path";
    private String path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        if (savedInstanceState == null) {
            path = getPath();
        } else {
            path = savedInstanceState.getString(EXTRA_PATH, "");
        }


        sharedPreferences=getSharedPreferences(SHARED_PREF_FILENAME, Context.MODE_APPEND);
        editor=sharedPreferences.edit();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd/HH:mm:ss");
        String currentDateandTime = sdf.format(new Date());

        int count=2;
        editor.putString(KEY_PATH+count,path+" "+currentDateandTime  );
        editor.putString(KEY_BODY+count++,currentDateandTime );
        editor.commit();

        btnAddNote=(Button)findViewById(R.id.btn_addNote);


        txtuserName=(TextView)findViewById(R.id.userName);
        //  System.out.print("User NAme :"+getIntent().getStringExtra(MSG_KEY_INTENT));
             txtuserName.setText(getIntent().getStringExtra(MSG_KEY_INTENT).toString());


        transaction=getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.fragment_container,new TileFragment());
        transaction.commit();
//landscape mode
        if(findViewById(R.id.fragment_container_details)!=null){
            transaction=getSupportFragmentManager().beginTransaction();
            transaction.add(R.id.fragment_container_details,new DetailsFragment());
            transaction.commit();
        }
    }


//Go to update User Information
    public void goToUpdateInfo(View view) {
        Intent regIntent=new Intent(HomePage.this,UpdateUserInfo.class);
        regIntent.putExtra(MSG_KEY_INTENT,txtuserName.getText().toString());
        startActivity(regIntent);
    }

    //To get History of pages visisted by user
    public void registerUserHistory(View view) {

        Intent regIntent=new Intent(HomePage.this,SharedPreData.class);
        regIntent.putExtra(MSG_KEY_INTENT,txtuserName.getText().toString());
        startActivity(regIntent);

    }
//To store add note And store into File
    public void addNote( final View view) {

        View viewGrp=getLayoutInflater().inflate(R.layout.costum_dialog_layout,
                (ViewGroup) findViewById(R.id.activity_main), false);

        final EditText noteTitle=(EditText)viewGrp.findViewById(R.id.dialog_title_et);
        final EditText noteBody=(EditText)viewGrp.findViewById(R.id.dialog_body_et);
        AlertDialog.Builder alertBuilder=new AlertDialog.Builder(HomePage.this)
                .setTitle("Take a note").setView(viewGrp)

                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //tv.setText(noteTitle.getText());

                        try{

                            FileOutputStream outputStream=openFileOutput(txtuserName.getText().toString().replace(" ","")
                                    ,MODE_APPEND);

                            outputStream.write(noteTitle.getText().toString().getBytes());
                            outputStream.write(noteBody.getText().toString().getBytes());
                            outputStream.close();
                            Snackbar.make(view,"File Saved",Snackbar.LENGTH_SHORT).show();

                        }catch(Exception e){
                        }

                    }
                });
        alertBuilder.show();


                }

    @Override
    public void onFragmentInteraction(String uri) {
        transaction=getSupportFragmentManager().beginTransaction();
        TileFragment tf= new TileFragment();
        //here trying to send username to fragment to read particular user's file
        Bundle bundle=new Bundle();
        //bundle=new Bundle();
        bundle.putString("Name",txtuserName.getText().toString());
        tf.setArguments(bundle);
        transaction.replace(R.id.fragment_container,tf);
        transaction.commit();

        if(findViewById(R.id.fragment_container_details)!=null){
            transaction=getSupportFragmentManager().beginTransaction();
            DetailsFragment df=new DetailsFragment();
            Bundle b=new Bundle();
            b.putString("KEY",uri);
            b.putString("Name",txtuserName.getText().toString());
            df.setArguments(b);
            transaction.add(R.id.fragment_container_details,df);
            transaction.commit();
        }else{
            Intent i=new Intent(HomePage.this,LandingScreen3.class);
            i.putExtra("MSG",uri);
            i.putExtra("Title",txtuserName.getText().toString());
            startActivity(i);
        }
    }

    //To get Path of class
    public String getPath() {
        if (path == null) {
            path = this.getClass().getSimpleName();
        }
        return path;
    }
}
