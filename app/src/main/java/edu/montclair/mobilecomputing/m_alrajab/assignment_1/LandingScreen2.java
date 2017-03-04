package edu.montclair.mobilecomputing.m_alrajab.assignment_1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import static edu.montclair.mobilecomputing.m_alrajab.assignment_1.utils.Util.MSG_KEY_INTENT;

public class LandingScreen2 extends AppCompatActivity {
    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_screen2);
        tv=(TextView)findViewById(R.id.textView);

        tv.setText(getIntent().getStringExtra(MSG_KEY_INTENT).toString());


    }

    public void goToLogin(View view) {

        Intent regIntent=new Intent(LandingScreen2.this,MainActivity.class);
     //   regIntent.putExtra(MSG_KEY_INTENT,userName.getText().toString());
        startActivity(regIntent);
    }
}
