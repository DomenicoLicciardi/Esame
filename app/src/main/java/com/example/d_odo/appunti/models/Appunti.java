package com.example.d_odo.appunti.models;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by d-odo on 13/03/2017.
 */

public class Appunti {
    private int id;
    String titolo,testo,data;


    DateFormat date = new SimpleDateFormat("MM/dd/yy");
    Date today = Calendar.getInstance().getTime();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitolo() {
        return titolo;
    }

    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    public String getTesto() {
        return testo;
    }

    public void setTesto(String testo) {
        this.testo = testo;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = date.format(today).toString();
    }
}
