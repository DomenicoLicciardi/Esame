package com.example.d_odo.appunti.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.d_odo.appunti.models.Appunti;

import java.util.ArrayList;

/**
 * Created by d-odo on 13/03/2017.
 */

public class Database extends SQLiteOpenHelper {

    //Appunti Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_TITOLO = "titolo";
    private static final String KEY_TESTO = "testo";
    private static final String KEY_DATA = "data";
    private static final String KEY_CANCELLATO = "cancellato";
    //Database version
    private static final int DATABASE_VERSION = 1;

    //Database name
    private static final String DATABASE_NAME = "appunti";

    //Products table name
    private static final String TABLE_APPUNTI= "appunti";

    public Database(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_SHOP_DATABASE = "CREATE TABLE " + TABLE_APPUNTI + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_TITOLO + " TEXT,"
                + KEY_TESTO + " TEXT," + KEY_DATA + " TEXT" + " )";
        db.execSQL(CREATE_SHOP_DATABASE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        if(oldVersion == 1) {
            db.execSQL("ALTER TABLE " + TABLE_APPUNTI + " ADD " + KEY_CANCELLATO + " INTEGER");
        }
    }

    public long addAppunti(Appunti appunti) {

        // Log.d("Appena entrato in addDB "," entro");

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values= new ContentValues();
        values.put(KEY_TITOLO, appunti.getTitolo());
        values.put(KEY_TESTO, appunti.getTesto());
        values.put(KEY_DATA, appunti.getData());
        //Inserting Row e presa di long
        long id = db.insert(TABLE_APPUNTI, null, values);
        appunti.setId((int) id);

        db.close(); //Closing database connection
        return id;

    }

    //Getting all products
    public ArrayList<Appunti> getAllProducts() {
        ArrayList<Appunti> productList = new ArrayList<>();
        //Select All Query
        String selectQuery = "SELECT * FROM " + TABLE_APPUNTI;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        //looping through all row adding to list
        if(cursor.moveToFirst()) {
            do {
                Appunti appunti = new Appunti();
                appunti.setId(Integer.parseInt(cursor.getString(0)));
                appunti.setTitolo(cursor.getString(1));
                appunti.setTesto(cursor.getString(2));
                appunti.setData(cursor.getString(3));

                //Adding note to list
                productList.add(appunti);
            }while(cursor.moveToNext());
        }
        db.close();
        //return product list
        return productList;
    }

    public ArrayList<Appunti> getAllProductsOrderCresc() {
        ArrayList<Appunti> productList = new ArrayList<>();
        //Select All Query
        String selectQuery = "SELECT * FROM " + TABLE_APPUNTI + " ORDER BY " + KEY_TITOLO;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        //looping through all row adding to list
        if(cursor.moveToFirst()) {
            do {
                Appunti appunti = new Appunti();
                appunti.setId(Integer.parseInt(cursor.getString(0)));
                appunti.setTitolo(cursor.getString(1));
                appunti.setTesto(cursor.getString(2));
                appunti.setData(cursor.getString(3));

                //Adding note to list
                productList.add(appunti);
            }while(cursor.moveToNext());
        }
        db.close();
        //return product list
        return productList;
    }

    public ArrayList<Appunti> getAllProductsOrderDecresc() {
        ArrayList<Appunti> productList = new ArrayList<>();
        //Select All Query
        String selectQuery = "SELECT * FROM " + TABLE_APPUNTI + " ORDER BY " + KEY_TITOLO + " DESC ";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        //looping through all row adding to list
        if(cursor.moveToFirst()) {
            do {
                Appunti appunti = new Appunti();
                appunti.setId(Integer.parseInt(cursor.getString(0)));
                appunti.setTitolo(cursor.getString(1));
                appunti.setTesto(cursor.getString(2));
                appunti.setData(cursor.getString(3));

                //Adding note to list
                productList.add(appunti);
            }while(cursor.moveToNext());
        }
        db.close();
        //return product list
        return productList;
    }
    //Updating single product
    public int updateProduct(Appunti appunti) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_TITOLO, appunti.getTitolo());
        values.put(KEY_TESTO, appunti.getTesto());
        values.put(KEY_DATA, appunti.getData());
        //updating row
        return db.update(TABLE_APPUNTI, values, KEY_ID + " = ?",
                new String[]{String.valueOf(appunti.getId())});
    }

    // Deleting single note
    public void deleteProduct(Appunti appunti) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_APPUNTI, KEY_ID + " = ?",
                new String[]{String.valueOf(appunti.getId())});
        db.close();
    }
}
