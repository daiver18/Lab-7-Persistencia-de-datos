package com.example.daiverandresdoria.lab04_cityworld.App;

import android.app.Application;

import com.example.daiverandresdoria.lab04_cityworld.Models.City;

import java.util.concurrent.atomic.AtomicInteger;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmObject;
import io.realm.RealmResults;

public class MyApplication extends Application {
    public static AtomicInteger CityID = new AtomicInteger();

    @Override
    public void onCreate() {
        super.onCreate();
        setRealmConfig();

        Realm realm = Realm.getDefaultInstance();
        CityID = getIdTable(realm,City.class);
    }

    private void setRealmConfig(){
        Realm.init(getApplicationContext());
        RealmConfiguration config = new RealmConfiguration.Builder().deleteRealmIfMigrationNeeded().build();
        Realm.setDefaultConfiguration(config);
    }

    private <T extends RealmObject> AtomicInteger getIdTable(Realm realm, Class<T> AnyClass){
        RealmResults<T> results = realm.where(AnyClass).findAll();
        return (results.size()>0) ? new AtomicInteger(results.max("id").intValue()):new AtomicInteger();
    }
}
