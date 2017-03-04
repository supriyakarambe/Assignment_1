package edu.montclair.mobilecomputing.m_alrajab.assignment_1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import edu.montclair.mobilecomputing.m_alrajab.assignment_1.utils.UserDbOperation;
import edu.montclair.mobilecomputing.m_alrajab.assignment_1.utils.UserInfo;

import static edu.montclair.mobilecomputing.m_alrajab.assignment_1.utils.Util.MSG_KEY_INTENT;

public class ForgotPassword extends AppCompatActivity {

    Button chnagePwdBtn;
    EditText emailtext;
    EditText userNametext;
    String validEmail;
    String emailPattern = "[a-zA-Z0-9._-]+@montclair.edu";

    private UserDbOperation dboperation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        chnagePwdBtn = (Button) findViewById(R.id.btn_changepassword);
        emailtext = (EditText) findViewById(R.id.email_id_text);
        userNametext = (EditText) findViewById(R.id.editText_username);

        dboperation = new UserDbOperation(this);
        dboperation.open();

        emailtext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (emailtext.getText().toString() == null) {
                    emailtext.setError("Invalid email id");
                    validEmail = null;
                } else if (isEmailValid(emailtext.getText().toString()) == false) {
                    emailtext.setError("Invalid email id");
                    validEmail = null;
                } else
                    validEmail = emailtext.getText().toString();

            }

            boolean isEmailValid(CharSequence email) {
                if (email.toString().matches(emailPattern))
                    return true;
                else
                    return false;
            }
        });
    }

    public void getPassword(View view) {
        String userName,emailId;

        userName=userNametext.getText().toString();
        emailId=emailtext.getText().toString();

        //Check user already exists or not
        UserInfo result=dboperation.getUserDeatils(userName.toString());

        if(result==null){
            Toast.makeText(getApplicationContext(), " User Name Does not exist ", Toast.LENGTH_LONG).show();
        }

        else {

            Intent regIntent=new Intent(ForgotPassword.this,LandingScreen2.class);
            regIntent.putExtra(MSG_KEY_INTENT,userName.toString());
            regIntent.putExtra(MSG_KEY_INTENT,result.getPwd());
            startActivity(regIntent);
        }

    }
    }


    class changePwd implements View.OnClickListener {

        @Override
        public void onClick(View v) {
        }
    }

