package com.example.eatit.HelperClasses.HomeAdapter;

public class main_menuHelperClass {

    int image;
    String title, desc;

    public main_menuHelperClass(int image, String title, String desc) {
        this.image = image;
        this.title = title;
        this.desc = desc;
    }

    public int getImage() {
        return image;
    }

    public String getTitle() {
        return title;
    }

    public String getDesc() {
        return desc;
    }
}
