package com.example.daiverandresdoria.lab04_cityworld.Activities;

import android.content.Intent;
import android.media.Rating;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Toast;

import com.example.daiverandresdoria.lab04_cityworld.Models.City;
import com.example.daiverandresdoria.lab04_cityworld.R;
import com.squareup.picasso.Picasso;

import io.realm.Realm;
import io.realm.RealmResults;

public class ActivityCreateEdit extends AppCompatActivity {
    private Realm realm;
    private RealmResults<City> citys;
    private EditText name, description, imagelink;
    private RatingBar rating;
    private Button saveButton,btuPrevieImage;
    private ImageView imagebg;
    private City city;
    private int cityID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_edit);
        realm = Realm.getDefaultInstance();
        if (getIntent().getExtras() != null)
            cityID = getIntent().getExtras().getInt("id");
        city = realm.where(City.class).equalTo("id", cityID).findFirst();

        name = findViewById(R.id.NameCity);
        description = findViewById(R.id.DescriptionCity);
        imagelink = findViewById(R.id.LinkImageCity);
        saveButton = findViewById(R.id.SaveCity);
        btuPrevieImage = findViewById(R.id.ButtonPreviewImageCity);
        imagebg = findViewById(R.id.imageViewCity);
        rating = findViewById(R.id.RatingBarCity);

        if (city != null){
            Picasso.get().load(city.getImagebg()).fit().into(imagebg);
            name.setText(city.getName());
            description.setText(city.getDescription());
            rating.setRating(city.getRating());
        }else {
           Picasso.get().load(R.drawable.ic_launcher_background).into(imagebg);
        }
        btuPrevieImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (imagelink.length()>0){
                    String url = imagelink.getText().toString().trim();
                    Picasso.get().load(url).into(imagebg);
                }else{
                    Toast.makeText(ActivityCreateEdit.this,"input a valid link",Toast.LENGTH_SHORT).show();
                }
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (city != null){
                    String Name = name.getText().toString();
                    String Description = description.getText().toString();
                    float RB = rating.getRating();
                    if (imagelink.length()>0){
                        String imLink = imagelink.getText().toString();
                        editCity(Name,Description,RB,imLink);
                        Intent intent = new Intent(ActivityCreateEdit.this,MainActivity.class);
                        startActivity(intent);
                    }else{
                        editCity(Name,Description,RB,city.getImagebg());
                        Intent intent = new Intent(ActivityCreateEdit.this,MainActivity.class);
                        startActivity(intent);
                    }
                }else{
                    String Name = name.getText().toString();
                    String Description = description.getText().toString();
                    float RB = rating.getRating();
                    String imLink = imagelink.getText().toString();
                    if (imagelink.length()>0){
                        creatCity(Name,Description,RB,imLink);
                        Intent intent = new Intent(ActivityCreateEdit.this,MainActivity.class);
                        startActivity(intent);
                    }else{
                        Toast.makeText(ActivityCreateEdit.this,"input a valid link",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private void editCity(String name,String description,Float Rb,String imLink) {
        realm.beginTransaction();
        city.setName(name);
        city.setDescription(description);
        city.setRating(Rb);
        city.setImagebg(imLink);
        realm.copyToRealmOrUpdate(city);
        realm.commitTransaction();
    }

    public void creatCity(final String Name,final String Description,final Float rb,final String imLink){
        realm.beginTransaction();
        City city = new City(Name,Description,rb,imLink);
        realm.copyToRealm(city);
        realm.commitTransaction();
    }
}
