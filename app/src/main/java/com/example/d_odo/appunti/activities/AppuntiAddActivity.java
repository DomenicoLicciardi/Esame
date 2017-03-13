package com.example.d_odo.appunti.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import com.example.d_odo.appunti.R;

/**
 * Created by d-odo on 13/03/2017.
 */

public class AppuntiAddActivity extends AppCompatActivity{

    TextView titoloET, testoET;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appunti_add);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        titoloET = (EditText) findViewById(R.id.add_title);
        testoET = (EditText) findViewById(R.id.add_testo);

        intent = getIntent();

        if(intent!=null) {
            if (intent.getIntExtra(MainActivity.ACTION_MODE, 0) == MainActivity.PRODUCT_EDIT_REQUEST) {
                titoloET.setText(intent.getStringExtra(MainActivity.APPUNTI_TITOLO_KEY));
                testoET.setText(intent.getStringExtra(MainActivity.APPUNTI_TESTO_KEY));
            } else if(intent.getStringExtra(MainActivity.APPUNTI_TITOLO_KEY) != null) {
                titoloET.setText(intent.getStringExtra(MainActivity.APPUNTI_TITOLO_KEY));
                testoET.setText(intent.getStringExtra(MainActivity.APPUNTI_TESTO_KEY));
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_edit, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if(id==R.id.action_confirm) {
            confirmProduct();
            return true;
        }
        if(id == android.R.id.home) {
            setResult(Activity.RESULT_CANCELED);
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
    private void confirmProduct() {Intent i = new Intent();
        i.putExtra(MainActivity.APPUNTI_TITOLO_KEY,titoloET.getText().toString());
        i.putExtra(MainActivity.APPUNTI_TESTO_KEY,testoET.getText().toString());
        setResult(Activity.RESULT_OK,i);
        finish();

    }
}
