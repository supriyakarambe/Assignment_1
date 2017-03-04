package edu.montclair.mobilecomputing.m_alrajab.assignment_1;



import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import edu.montclair.mobilecomputing.m_alrajab.assignment_1.utils.UserDbOperation;
import edu.montclair.mobilecomputing.m_alrajab.assignment_1.utils.UserInfo;

import static edu.montclair.mobilecomputing.m_alrajab.assignment_1.utils.Util.KEY_BODY;
import static edu.montclair.mobilecomputing.m_alrajab.assignment_1.utils.Util.KEY_PATH;
import static edu.montclair.mobilecomputing.m_alrajab.assignment_1.utils.Util.MSG_KEY_INTENT;
import static edu.montclair.mobilecomputing.m_alrajab.assignment_1.utils.Util.SHARED_PREF_FILENAME;

public class RegistrationPage extends AppCompatActivity {

    EditText name,userName,dob;
    private DatePicker datePicker;
    private Calendar calendar;
    private int year, month, day;
    private EditText emailcheck,major;
    private String validEmail;
    String emailPattern = "[a-zA-Z0-9._-]+@montclair.edu";
    private EditText pwd,confirmPwd;
    private Button regBtn;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    private static final String EXTRA_PATH = "path";
    private String path;

    private UserDbOperation dboperation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_page);
//to store path of activity in shared pref
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

//for database operations
        dboperation = new UserDbOperation(this);
        dboperation.open();

        name=(EditText)findViewById(R.id.Reg_SName);
        userName=(EditText)findViewById(R.id.Reg_UserName);
        major=(EditText)findViewById(R.id.Reg_SMajor) ;

        emailcheck=(EditText)findViewById(R.id.Reg_Email) ;
        dob=(EditText)findViewById(R.id.DoB);

        pwd=(EditText)findViewById(R.id.Reg_pass1) ;
        confirmPwd=(EditText)findViewById(R.id.Reg_pass2);
        regBtn=(Button)findViewById(R.id.btn_Reg) ;
        regBtn.setOnClickListener(new RegLstr());
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);

        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);

        //To check email validation
        emailcheck.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(emailcheck.getText().toString()==null){
                    emailcheck.setError("Invalid email id");
                    validEmail=null;
                }else if(isEmailValid(emailcheck.getText().toString())==false){
                    emailcheck.setError("Invalid email id");
                    validEmail=null;
                }else
                    validEmail=emailcheck.getText().toString();

            }

            boolean isEmailValid(CharSequence email){
                if(email.toString().matches(emailPattern))
                    return true;
                else
                    return false;
            }

        });

//Email validation- chaeck email length is atleast 6

        pwd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(pwd.getText().toString().length()<6){
                    pwd.setError("Password length should be minimum 6");
                }
            }
        });

        //Email validation for confirm password- check length and charcter are same as email
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


//To get dailog for date picker
    public void setDate(View view) {
        showDialog(999);
        Toast.makeText(getApplicationContext(), " ",
                Toast.LENGTH_SHORT)
                .show();
    }

    protected Dialog onCreateDialog(int id) {
        // TODO Auto-generated method stub
        if (id == 999) {
            return new DatePickerDialog(this,
                    myDateListener, year, month, day);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener myDateListener = new
            DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker arg0,
                                      int arg1, int arg2, int arg3) {

                    showDate(arg1, arg2, arg3);
                }
            };
    private void showDate(int year, int i, int day) {
        dob.setText(new StringBuilder().append(day).append("/")
                .append(i+1).append("/").append(year));
    }

    class RegLstr implements View.OnClickListener{

        @Override
        public void onClick(View v) {

            UserInfo userInfo=new UserInfo();
            userInfo.setUserName(userName.getText().toString());
            userInfo.setName(name.getText().toString());
            userInfo.setEmail(emailcheck.getText().toString());
            userInfo.setPwd(pwd.getText().toString());
            userInfo.setMajor(major.getText().toString());
            userInfo.setDoB(dob.getText().toString());
            // Check user Name is already exists or not
            UserInfo result=dboperation.getUserDeatils(userName.getText().toString());

            if(result==null) {
                //Add User Details
                dboperation.addUser(userInfo);
            }else{
                Toast.makeText(getApplicationContext(), " User Name Already exists ", Toast.LENGTH_LONG).show();
            }

            Intent regIntent=new Intent(RegistrationPage.this,LandingScreen.class);
            regIntent.putExtra(MSG_KEY_INTENT,userName.getText().toString());
            startActivity(regIntent);
        }
    }

    public String getPath() {
        if (path == null) {
            // path = getIntent().getStringExtra(EXTRA_PATH);
            //path = path == null ? "" : path += "/";
            path = this.getClass().getSimpleName();
        }
        return path;
    }
}
