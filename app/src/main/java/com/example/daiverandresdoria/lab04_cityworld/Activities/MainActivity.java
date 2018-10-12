package com.example.daiverandresdoria.lab04_cityworld.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;

import com.example.daiverandresdoria.lab04_cityworld.Adapters.AdapterMain;
import com.example.daiverandresdoria.lab04_cityworld.Models.City;
import com.example.daiverandresdoria.lab04_cityworld.R;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity implements RealmChangeListener<RealmResults<City>>{
    private Realm realm;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapterMain;
    private RecyclerView.LayoutManager lManager;
    private FloatingActionButton fabbutton;
    private RealmResults<City> citys;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        realm = Realm.getDefaultInstance();

        //realm.beginTransaction();
        //realm.deleteAll();
        //realm.commitTransaction();

        citys = realm.where(City.class).findAll();
        citys.addChangeListener(this);



        recyclerView=findViewById(R.id.RecyclerView);
        fabbutton=findViewById(R.id.fabAddCity);

        lManager = new LinearLayoutManager(this);
        adapterMain = new AdapterMain(R.layout.recicled_view_item, citys, MainActivity.this, new AdapterMain.onClickItemLisener() {
            @Override
            public void onitemclicklisener(City city, int posicion) {
                Intent intent = new Intent(MainActivity.this,ActivityCreateEdit.class);
                intent.putExtra("id",city.getId());
                startActivity(intent);
            }
            @Override
            public void onButtonclick(City city, int posicion) {
                showAlertDelete("Delete city",citys.get(posicion));
            }
        });
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(lManager);
        recyclerView.setAdapter(adapterMain);
        fabbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this , ActivityCreateEdit.class);
                startActivity(intent);
            }
        });
    }

    public void showAlertDelete(String title, final City city){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        if (title !=null) builder.setTitle(title);

        View v = LayoutInflater.from(this).inflate(R.layout.alert_create_new_board,null);
        builder.setView(v);
        builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                deleteCity(city);
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void deleteCity(City city) {
        realm.beginTransaction();
        city.deleteFromRealm();
        realm.commitTransaction();
    }

    @Override
    public void onChange(RealmResults<City> cities) {
        adapterMain.notifyDataSetChanged();
    }
}
