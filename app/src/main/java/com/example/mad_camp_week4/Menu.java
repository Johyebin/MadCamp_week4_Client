package com.example.mad_camp_week4;

public class Menu {
    private String name;
    private int img;

    public Menu(String name, int img){
        this.name = name;
        this.img = img;
    }

    public String getName(){ return name; }
    public void setName(String name) { this.name = name; }
    public int getImg(){ return img; }
    public void setImg(int img) { this.img = img; }

}
