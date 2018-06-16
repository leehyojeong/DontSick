package com.example.hyoju.dontsick;

public class Item {
    protected String name="";
    protected String sym="";
    protected String mean="";

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

}
