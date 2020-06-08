package com.example.applicationloginusephp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DownloadManager;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {
     TextView numberMovies , showUser ;
     ListView listItemMovie ;
     ArrayList<Movie> listMovies ;
     String url = "http://192.168.1.112/appOnLine/all_movies.php" ;
     RequestQueue mrequestQueue ;
     Globalv globalv ;
     AdpterMovie adpterMovie ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        numberMovies = findViewById(R.id.id_nombremovie);
        showUser = findViewById(R.id.id_showNameUser);
        listItemMovie = findViewById(R.id.id_listmovie);
        listMovies = new ArrayList<>();
        mrequestQueue = Volley.newRequestQueue(this);
        globalv = (Globalv) getApplicationContext();
        assert showUser != null;
        showUser.setText("" + globalv.getUser_name());
        fetchJsonResponse();
        Intent intent = new Intent(HomeActivity.this,MyService.class) ;
        stopService(intent) ;

    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(HomeActivity.this,MyService.class) ;
        startService(intent) ;
        Toast.makeText(this,"exit",Toast.LENGTH_LONG).show();
        super.onBackPressed();


    }
    public  void fetchJsonResponse(){
        JsonObjectRequest req =  new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray =  response.getJSONArray("all_movies") ;
                    numberMovies.setText("" + jsonArray.length());

                    for(int i = 0 ; i<jsonArray.length();i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String id = jsonObject.getString("id");
                        String title = jsonObject.getString("title");
                        String link = jsonObject.getString("link");
                        String info = jsonObject.getString("info");
                        String photolink = jsonObject.getString("photolink");
                        String type = jsonObject.getString("type");
                        listMovies.add(new Movie(id, title, link, info, photolink, type));
                    }
                   JSONObject jsonObject1 = jsonArray.getJSONObject(0) ;
                    globalv.setTotal_thread(Integer.parseInt(jsonObject1.getString("id")));
                    Toast.makeText(HomeActivity.this,"("+Integer.parseInt(jsonObject1.getString("id"))+")",Toast.LENGTH_LONG).show();

                    adpterMovie = new AdpterMovie(HomeActivity.this, R.layout.item_movie, listMovies);
                    listItemMovie.setAdapter(adpterMovie);



                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e("Error: ", error.getMessage());
            }
        });

        mrequestQueue.add(req) ;
    }
}
