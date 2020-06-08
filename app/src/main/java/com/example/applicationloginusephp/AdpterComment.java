package com.example.applicationloginusephp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class AdpterComment extends ArrayAdapter<Comment> {
    Context context ;
    int resource ;

    public AdpterComment(@NonNull Context context, int resource, @NonNull ArrayList<Comment> objects) {
        super(context, resource, objects);
        this.context=context ;
        this.resource=resource ;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        convertView = LayoutInflater.from(context).inflate(resource,parent,false) ;
        // CODE
        TextView txt_username = convertView.findViewById(R.id.id_item_comment_username) ;
        TextView txt_text = convertView.findViewById(R.id.id_item_comment_text) ;
        TextView txt_date = convertView.findViewById(R.id.id_item_comment_date) ;

        Comment comment = getItem(position) ;
        String username = comment.getName() ;
        String text = comment.getText() ;
        String date_time = comment.getDate_time() ;

        txt_username.setText(username);
        txt_text.setText(text);
        txt_date.setText(date_time);
        return convertView;


    }
}
