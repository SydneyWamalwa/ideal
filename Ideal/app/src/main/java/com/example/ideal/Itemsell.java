package com.example.ideal;

public class Itemsell {
    private String name;
    private String item;
    private String place;

    public Itemsell(String name, String item, String place) {
        this.name=name;
        this.place=place;
        this.item=item;
    }

    public void Itemsell(){

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }
}
