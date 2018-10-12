package com.example.daiverandresdoria.lab04_cityworld.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.daiverandresdoria.lab04_cityworld.Models.City;
import com.example.daiverandresdoria.lab04_cityworld.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AdapterMain extends RecyclerView.Adapter<AdapterMain.ViewHolder> {
    private int layout;
    private List<City> listOfCity;
    private Context context;
    private onClickItemLisener onClickItemLisener;
    //private onButtonClickLisener onButtonClickLisener;

    public AdapterMain(int layout, List<City> listOfCity, Context context,onClickItemLisener onClickItemLisener /*onButtonClickLisener onButtonClickLisener*/) {
        this.layout = layout;
        this.listOfCity = listOfCity;
        this.context = context;
        this.onClickItemLisener = onClickItemLisener;
        //this.onButtonClickLisener = onButtonClickLisener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(layout,null);
        ViewHolder vh = new ViewHolder(v);
        context = parent.getContext();
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(listOfCity.get(position), onClickItemLisener);

    }

    @Override
    public int getItemCount() {
        return listOfCity.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView name;
        private TextView description;
        private ImageView imagebg;
        private TextView rating;
        private Button btn;

        public ViewHolder(View itemView) {
            super(itemView);
            this.name = (TextView)itemView.findViewById(R.id.textViewCityName);
            this.description = (TextView)itemView.findViewById(R.id.textViewCityDescription);
            this.imagebg = (ImageView)itemView.findViewById(R.id.imageView);
            this.rating = (TextView)itemView.findViewById(R.id.Rating);
            this.btn = (Button)itemView.findViewById(R.id.btnDelete);
        }
        public void bind(final City city, final  onClickItemLisener lisener /*final onButtonClickLisener btnlisener*/){
            name.setText(city.getName());
            description.setText(city.getDescription());
            String ratingpunt = city.getRating()+" raitng";
            rating.setText(ratingpunt);
            Picasso.get().load(city.getImagebg()).fit().into(imagebg);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    lisener.onitemclicklisener(city,getAdapterPosition());
                }
            });
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    lisener.onButtonclick(city,getAdapterPosition());
                }
            });
        }
    }
    public  interface onClickItemLisener{
        void onitemclicklisener(City city, int posicion);
        void onButtonclick(City city, int posicion);
    }

    public interface  onButtonClickLisener{
        void onButtonclick(City city, int posicion);
    }
}
