package com.example.hyoju.dontsick;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Item {
    String hospital = "";
    protected String name="";
    protected String sym="";
    protected String mean="";
    public Button mapping;

    Item(String name, String sym, String mean,String hospital){
        this.name = name;
        this.sym = sym;
        this.mean = mean;
        this.hospital = hospital;
    }
    public void setName(String name){
        this.name = name;
    }
    public void setSym(String sym){
        this.sym = sym;
    }
    public void setMean(String mean){
        this.mean = mean;
    }

    public String getMean() {
        return mean;
    }

    public String getName() {
        return name;
    }

    public String getSym() {
        return sym;
    }
    String getHospital(){return hospital;}

}
