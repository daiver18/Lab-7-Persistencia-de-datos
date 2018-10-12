package com.example.daiverandresdoria.lab04_cityworld.Models;

import com.example.daiverandresdoria.lab04_cityworld.App.MyApplication;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

public class City extends RealmObject {
    @PrimaryKey
    private int id;
    @Required
    private String imagebg;
    @Required
    private String name;
    @Required
    private String description;
    @Required
    private Float rating;

    public City() {
    }

    public City(String name, String description, Float rating, String imagebg) {
        this.id = MyApplication.CityID.incrementAndGet();
        this.imagebg = imagebg;
        this.name = name;
        this.description = description;
        this.rating = rating;
    }

    public int getId() {
        return id;
    }

    public String getImagebg() {
        return imagebg;
    }

    public void setImagebg(String imagebg) {
        this.imagebg = imagebg;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Float getRating() {
        return rating;
    }

    public void setRating(Float rating) {
        this.rating = rating;
    }
}
