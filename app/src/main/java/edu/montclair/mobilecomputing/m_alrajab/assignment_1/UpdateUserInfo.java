package edu.montclair.mobilecomputing.m_alrajab.assignment_1;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
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

public class UpdateUserInfo extends AppCompatActivity {
    private String userName;
    private EditText regName,email,pwd,confirmPwd,major;
    private UserDbOperation dboperation;
    private String validEmail;
    String emailPattern = "[a-zA-Z0-9._-]+@montclair.edu";

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    private static final String EXTRA_PATH = "path";
    private String path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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

        setContentView(R.layout.activity_update_user_info);
        userName= getIntent().getStringExtra(MSG_KEY_INTENT).toString();

        regName=(EditText)findViewById(R.id.Reg_SName);
        email=(EditText)findViewById(R.id.Reg_Email);
        pwd=(EditText)findViewById(R.id.Reg_pass1);
        confirmPwd=(EditText)findViewById(R.id.Reg_pass2);
        major=(EditText)findViewById(R.id.Reg_SMajor);

        dboperation = new UserDbOperation(this);
        dboperation.open();

        //check email validation
        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(email.getText().toString()==null){
                    email.setError("Invalid email id");
                    validEmail=null;
                }else if(isEmailValid(email.getText().toString())==false){
                    email.setError("Invalid email id");
                    validEmail=null;
                }else
                    validEmail=email.getText().toString();

            }

            boolean isEmailValid(CharSequence email){
                if(email.toString().matches(emailPattern))
                    return true;
                else
                    return false;
            }
        });

        //Password validation
        pwd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {  }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void afterTextChanged(Editable s) {
                if(pwd.getText().toString().length()<6){
                    pwd.setError("Password length should be minimum 6");
                }
            }
        });

        confirmPwd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(pwd.getText().toString().length()==confirmPwd.getText().length()){
                    if(pwd.getText().toString().equals(confirmPwd.getText().toString())){
                        String validpwd="Password is correct";
                    }
                }else {
                    confirmPwd.setError("Password Does't match");
                }

            }
        });


    }


//Update user info
    public void updateUserInfo(View view) {
        UserInfo userInfo=new UserInfo();
        userInfo.setUserName(userName);
        userInfo.setName(regName.getText().toString());
        userInfo.setEmail(email.getText().toString());
        userInfo.setPwd(pwd.getText().toString());
        userInfo.setMajor(major.getText().toString());

        int result=dboperation.updateUser(userInfo);
         if(result!=0){
             Toast.makeText(getApplicationContext(), " User Info Upadated Successfully ", Toast.LENGTH_LONG).show();

         }else {
             Toast.makeText(getApplicationContext(), " User Info Unable to update", Toast.LENGTH_LONG).show();
         }

        Intent regIntent=new Intent(UpdateUserInfo.this,MainActivity.class);
        startActivity(regIntent);
    }
//get path of class
    public String getPath() {
        if (path == null) {
            path = this.getClass().getSimpleName();
        }
        return path;
    }
}
