package com.example.bookworm;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.MenuItem;
import android.widget.Button;

public class MentalHealthActivity extends AppCompatActivity {

    private Button Link;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mental_health);

        mToolbar = (Toolbar) findViewById(R.id.MentalHealth_Toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Mental Health Support");

        Link = findViewById(R.id.WebURL);
        Link.setText(Html.fromHtml("Then please try  <a href=\"https://www4.ntu.ac.uk/student_services/health_wellbeing/online-support/index.html\">Silver Cloud</a>"));
        Link.setMovementMethod(LinkMovementMethod.getInstance());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();

        if(id == android.R.id.home)
        {
            SendUserToHome();
        }
        return super.onOptionsItemSelected(item);
    }

    private void SendUserToHome()
    {
        Intent mainintent = new Intent(MentalHealthActivity.this, BlankActivity.class);
        startActivity(mainintent);
    }
}
