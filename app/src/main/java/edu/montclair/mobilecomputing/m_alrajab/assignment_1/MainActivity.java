package edu.montclair.mobilecomputing.m_alrajab.assignment_1;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

import edu.montclair.mobilecomputing.m_alrajab.assignment_1.utils.UserDbOperation;
import edu.montclair.mobilecomputing.m_alrajab.assignment_1.utils.UserInfo;

import static edu.montclair.mobilecomputing.m_alrajab.assignment_1.utils.Util.KEY_BODY;
import static edu.montclair.mobilecomputing.m_alrajab.assignment_1.utils.Util.KEY_PATH;
import static edu.montclair.mobilecomputing.m_alrajab.assignment_1.utils.Util.MSG_KEY_INTENT;
import static edu.montclair.mobilecomputing.m_alrajab.assignment_1.utils.Util.SHARED_PREF_FILENAME;

public class MainActivity extends AppCompatActivity {

    Button forgetBtn;
    Button regBtn;
    EditText userName;
    EditText pwd;

    //To get path in shared pre
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    private static final String EXTRA_PATH = "path";
    private String path;
    private UserDbOperation dboperation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //To store path of activity in shared Pref
        if (savedInstanceState == null) {
            path = getPath();
        } else {
            path = savedInstanceState.getString(EXTRA_PATH, "");
        }

        sharedPreferences=getSharedPreferences(SHARED_PREF_FILENAME, Context.MODE_APPEND);
        editor=sharedPreferences.edit();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd/HH:mm:ss");
        String currentDateandTime = sdf.format(new Date());

        int count=0;
        editor.putString(KEY_PATH+count,path+" "+currentDateandTime  );
        editor.putString(KEY_BODY+count++,currentDateandTime );
        editor.commit();

        dboperation = new UserDbOperation(this);
        dboperation.open();

        userName=(EditText)findViewById(R.id.editText_name_MA1) ;
        pwd=(EditText)findViewById(R.id.editText_name_MA2) ;

        forgetBtn=(Button)findViewById(R.id.btn_forgetpassword_MA);
        forgetBtn.setOnClickListener(new forgetPwLstr());
        regBtn=(Button)findViewById(R.id.btn_register_MA);
        regBtn.setOnClickListener(new regLstr());
    }
//User will login And go to Home Page
    public void gotoHomePage(View view) {

        //Check user is exists or not
        int result=dboperation.checkValiduser(userName.getText().toString(),pwd.getText().toString());

        if(result==0) {
            //Add User Details
            Toast.makeText(getApplicationContext(), " Enter Valid User Name ", Toast.LENGTH_LONG).show();
        }else{
            Intent regIntent= new Intent(MainActivity.this,HomePage.class);
            regIntent.putExtra(MSG_KEY_INTENT,userName.getText().toString());
            startActivity(regIntent);
        }


    }
//User will get browser History and It will store into shared pref
    class regLstr implements View.OnClickListener{

        @Override
        public void onClick(View v) {

            Intent regIntent= new Intent(MainActivity.this,RegistrationPage.class);
            startActivity(regIntent);
        }
    }

    class forgetPwLstr implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            Intent intent=new Intent(MainActivity.this,ForgotPassword.class);
            startActivity(intent);

        }
    }
//To get path of class
    public String getPath() {
        if (path == null) {
            path = this.getClass().getSimpleName();
        }
        return path;
    }
}
