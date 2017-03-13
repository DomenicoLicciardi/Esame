package com.example.d_odo.appunti.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.example.d_odo.appunti.R;
import com.example.d_odo.appunti.adapters.AppuntiAdapter;
import com.example.d_odo.appunti.database.Database;
import com.example.d_odo.appunti.models.Appunti;

/**
 * Created by d-odo on 13/03/2017.
 */

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "Main: ";
    Appunti editingAppunti;

    public static final int PRODUCT_ADD_REQUEST = 1001;
    public static final int PRODUCT_EDIT_REQUEST = 1002;

    public static final String ACTION_MODE = "ACTION_MODE";
    public static final int EDITE_MODE = 1;
    public static final int CREATE_MODE = 2;
    public static final String APPUNTI_TITOLO_KEY = "APPUNTI_TITOLO_KEY";
    public static final String APPUNTI_TESTO_KEY = "APPUNTI_TESTO_KEY";
    public static final String APPUNTI_DATA_KEY = "APPUNTI_DATA_KEY";

    AppuntiAdapter adapter;
    RecyclerView appuntiRV;
    RecyclerView.LayoutManager layoutManager;

    Database dbHandler;

    private static final String LAYOUT_MANAGER_KEY = "LAYOUT_MANAGER_KEY";
    private int STAGGERED_LAYOUT = 20;
    private int LINEAR_LAYOUT = 21;
    private int layoutManagerType = LINEAR_LAYOUT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        appuntiRV = (RecyclerView) findViewById(R.id.appunti_rv);
        layoutManager = getSavedLayoutManager();

        adapter = new AppuntiAdapter(this);
        appuntiRV.setLayoutManager(layoutManager);
        appuntiRV.setAdapter(adapter);

        registerForContextMenu(appuntiRV);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.add_note);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(MainActivity.this, AppuntiAddActivity.class);
                i.putExtra(ACTION_MODE, CREATE_MODE);
                startActivityForResult(i, PRODUCT_ADD_REQUEST);
            }
        });
        
        if(getIntent() != null) {
            if (getIntent().getAction() != null) {
                if(getIntent().getAction().equals(Intent.ACTION_SEND)) {
                    Intent i = new Intent(MainActivity.this, AppuntiAddActivity.class);
                    i.putExtra(ACTION_MODE, CREATE_MODE);
                    i.putExtra(APPUNTI_TITOLO_KEY, getIntent().getStringExtra(Intent.EXTRA_TEXT));
                    startActivityForResult(i, PRODUCT_ADD_REQUEST);
                }
            }
        }
        dbHandler = new Database(this);
        adapter.setDataSet(dbHandler.getAllProducts());
    }

    //SharedPreferences
    private RecyclerView.LayoutManager getSavedLayoutManager() {
        SharedPreferences sharedPrefs = getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        int layoutManager = sharedPrefs.getInt(LAYOUT_MANAGER_KEY, -1);
        if (layoutManager == STAGGERED_LAYOUT) {
            setLayoutManagerType(layoutManager);
            return new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);

        }
        if (layoutManager == LINEAR_LAYOUT) {
            setLayoutManagerType(layoutManager);
            return new LinearLayoutManager(this);
        }
        return new LinearLayoutManager(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Log.d("menu","getLayoutManagerTyper "+getLayoutManagerTyper());
            if (getLayoutManagerTyper() == STAGGERED_LAYOUT) {
                setLayoutManagerType(LINEAR_LAYOUT);
                appuntiRV.setLayoutManager(new LinearLayoutManager(this));
                item.setIcon(getDrawable(R.drawable.view_quilt));


            }else{
                setLayoutManagerType(STAGGERED_LAYOUT);
                appuntiRV.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
                item.setIcon(getDrawable(R.drawable.view_list));
            }

        }
        if(id == R.id.action_ordina_cresc) {
            adapter.setDataSet(dbHandler.getAllProductsOrderCresc());

        }

        if(id == R.id.action_ordina_decresc) {
            adapter.setDataSet(dbHandler.getAllProductsOrderDecresc());

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SharedPreferences layoutPreferences = getSharedPreferences(getString(R.string.preference_file_key),Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = layoutPreferences.edit();
        editor.putInt(LAYOUT_MANAGER_KEY,getLayoutManagerTyper());
        editor.apply();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PRODUCT_ADD_REQUEST && resultCode == Activity.RESULT_OK) {
            Appunti appunti = new Appunti();
            appunti.setTitolo(data.getStringExtra(APPUNTI_TITOLO_KEY));
            appunti.setTesto(data.getStringExtra(APPUNTI_TESTO_KEY));
            appunti.setData(data.getStringExtra(APPUNTI_DATA_KEY));


            long addProductResult = dbHandler.addAppunti(appunti);
            if(addProductResult > -1) {

                //dbHandler.addAppunti(shop);
                adapter.addAppunto(appunti);
                adapter.notifyDataSetChanged();
            } else {
                Log.d(TAG," nessuna riga inserita!");
            }

        }

        if(requestCode == PRODUCT_EDIT_REQUEST && resultCode == RESULT_OK) {
            editingAppunti.setTitolo(data.getStringExtra(APPUNTI_TITOLO_KEY));
            editingAppunti.setTesto(data.getStringExtra(APPUNTI_TESTO_KEY));
            editingAppunti.setData(data.getStringExtra(APPUNTI_DATA_KEY));

            //update data in db
            dbHandler.updateProduct(editingAppunti);
            // update adapter
            adapter.updateAppunti(editingAppunti, adapter.getPosition());
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        int id= item.getItemId();
        switch (id) {
            case R.id.action_delete:
                //remove record
                dbHandler.deleteProduct(adapter.getAppunti(adapter.getPosition()));
                //remove from adapter
                adapter.removeAppunto(adapter.getPosition());
                break;

            case R.id.action_edit:
                editingAppunti = adapter.getAppunti(adapter.getPosition());
                Intent i = new Intent(this, AppuntiAddActivity.class);
                i.putExtra(ACTION_MODE, EDITE_MODE);
                i.putExtra(APPUNTI_TITOLO_KEY,editingAppunti.getTitolo());
                i.putExtra(APPUNTI_TESTO_KEY, editingAppunti.getTesto());
                startActivityForResult(i,PRODUCT_EDIT_REQUEST);
                break;


        }
        return super.onContextItemSelected(item);
    }


    public int getLayoutManagerTyper() {

        return layoutManagerType;
    }

    public void setLayoutManagerType(int layoutManagerType) {
        this.layoutManagerType = layoutManagerType;
        Log.d("setLayoutManagerType","type "+ this.layoutManagerType);
    }
}
