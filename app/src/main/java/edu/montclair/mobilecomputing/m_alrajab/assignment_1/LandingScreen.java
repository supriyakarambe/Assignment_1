package edu.montclair.mobilecomputing.m_alrajab.assignment_1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import static edu.montclair.mobilecomputing.m_alrajab.assignment_1.utils.Util.MSG_KEY_INTENT;

public class LandingScreen extends AppCompatActivity {

    TextView txtuserName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_screen);

        txtuserName=(TextView)findViewById(R.id.userName);
      //  System.out.print("User NAme :"+getIntent().getStringExtra(MSG_KEY_INTENT));
        txtuserName.setText(getIntent().getStringExtra(MSG_KEY_INTENT).toString());
    }

    public void gotoLogin(View view) {
        Intent intent=new Intent(LandingScreen.this,MainActivity.class);
        startActivity(intent);
    }

}
