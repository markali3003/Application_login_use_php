package com.example.applicationloginusephp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;


public class AdpterMovie extends ArrayAdapter<Movie> {

    Context context ;
    int resource ;
    public AdpterMovie(@NonNull Context context, int resource, @NonNull ArrayList<Movie> objects) {
        super(context, resource, objects);
        this.context=context ;
        this.resource=resource ;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        convertView = LayoutInflater.from(context).inflate(resource,parent,false) ;
        // CODE
        TextView txt_title  = convertView.findViewById(R.id.id_titleMovie) ;
        TextView txt_info = convertView.findViewById(R.id.id_infoMovie);
        TextView txt_type = convertView.findViewById(R.id.id_typeMovie) ;
        ImageView imageView = convertView.findViewById(R.id.id_imgMovie) ;

        final Movie movie = getItem(position) ;
        String id = movie.getId().toString() ;
        String title = movie.getTitle().toString();
        String info = movie.getInfo().toString() ;
        String type = movie.getType().toString();
        String image= movie.getPhotolink().toString() ;
        txt_title.setText(title);
        txt_info.setText(info);
        txt_type.setText(type);

        Picasso.get().load("http://192.168.1.112/appOnLine/image/"+image).into(imageView);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,MoviesActivity.class) ;
                intent.putExtra("movie_id",movie.getId());
                intent.putExtra("title",movie.getTitle());
                intent.putExtra("info",movie.getInfo());
                intent.putExtra("type",movie.getType());
                intent.putExtra("link",movie.getLink());

                context.startActivity(intent);
            }
        });



        return convertView;


    }
}
