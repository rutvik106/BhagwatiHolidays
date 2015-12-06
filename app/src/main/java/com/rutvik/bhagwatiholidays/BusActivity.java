package com.rutvik.bhagwatiholidays;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.EditText;

public class BusActivity extends AppCompatActivity {

    private Toolbar mToolbar;

    private EditText etMobilePhone, etFrom, etTo, etDateOfTravel;

    private FloatingActionButton fabDone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        etMobilePhone = (EditText) findViewById(R.id.et_mobilePhone);

        etFrom = (EditText) findViewById(R.id.et_from);

        etTo = (EditText) findViewById(R.id.et_to);

        etDateOfTravel = (EditText) findViewById(R.id.et_dateOfTravel);

        fabDone = (FloatingActionButton) findViewById(R.id.done);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                startActivity(new Intent(BusActivity.this, SwipeTabActivity.class));
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
