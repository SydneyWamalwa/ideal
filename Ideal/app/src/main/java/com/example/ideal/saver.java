package com.example.ideal;

public class saver {
    private String where;
    private String when;
    private String what;

    public saver(String where,String when,String what ){
        this.where=where;
        this.what=what;
        this.when=when;
    }
    public saver(){

    }

    public String getWhere() {
        return where;
    }

    public void setWhere(String where) {
        this.where = where;
    }

    public String getWhen() {
        return when;
    }

    public void setWhen(String when) {
        this.when = when;
    }

    public String getWhat() {
        return what;
    }

    public void setWhat(String what) {
        this.what = what;
    }
}
