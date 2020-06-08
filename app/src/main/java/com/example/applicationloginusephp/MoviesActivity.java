package com.example.applicationloginusephp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

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

import java.net.URL;
import java.util.ArrayList;

public class MoviesActivity extends AppCompatActivity {
    Globalv globalv;
    String movie_id ;
    String link ;
    String title ;
    String info ;
    String type ;
    RequestQueue mrequestQueue;
    //step 1
    VideoView videoView;
    ProgressBar progressBar;
    TextView  txt_title_movie ;
    //step 2
    LinearLayout layout_show_comment;
    EditText txt_comment;
    Button bu_send, bu_close;
    //step 3
    ArrayList<Comment> list_comments;
    ListView list_item_comment;
    AdpterComment adpterComment;
    //step 4
    Button bu_add_comment, bu_add_like;
    TextView txt_number_comment, txt_number_like;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies);
        globalv = (Globalv) getApplicationContext();
        movie_id = getIntent().getStringExtra("movie_id") ;
        link = getIntent().getStringExtra("link") ;
        title = getIntent().getStringExtra("title");
        info = getIntent().getStringExtra("info");
        type = getIntent().getStringExtra("type");
        mrequestQueue = Volley.newRequestQueue(this);
        String u = "android.resource://com.example.applicationloginusephp/"+R.raw.quran  ;
        Uri uri = Uri.parse(u) ;
        //step 1
        txt_title_movie = findViewById(R.id.id_title_movie) ;
        videoView = findViewById(R.id.id_videoView) ;
        progressBar = findViewById(R.id.id_progressBar) ;
        assert  progressBar!= null ;
        progressBar.bringToFront();
        txt_title_movie.setText(title);
        MediaController mediaController = new MediaController(this) ;
        videoView.setVideoURI(uri);
        videoView.setMediaController(new MediaController(this));
        mediaController.setAnchorView(videoView);
        videoView.requestFocus() ;
       // videoView.start();
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                progressBar.setVisibility(View.INVISIBLE);
            }
        });




        //step 2
        layout_show_comment = findViewById(R.id.id_ly_show_comment);
        txt_comment = findViewById(R.id.id_comment);
        bu_send = findViewById(R.id.id_button_send_comment);
        bu_close = findViewById(R.id.id_button_close_comment);
        //step 3
        list_comments = new ArrayList<>();
        list_item_comment = findViewById(R.id.id_list_comment);
        mrequestQueue = Volley.newRequestQueue(this);

        //step 4
        bu_add_comment = findViewById(R.id.id_button_add_comment);
        bu_add_like = findViewById(R.id.id_button_add_like);
        txt_number_comment = findViewById(R.id.id_numberComment);
        txt_number_like = findViewById(R.id.id_numberLike);
        //les fonction
        showComment();
        showNumberLike();
    }

    public void showNumberLike(){

           String url = "http://192.168.1.112/appOnLine/show_mlike.php?movie_id=" + movie_id;

           JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
               @Override
               public void onResponse(JSONObject response) {
                   StringBuilder text = new StringBuilder();//tous les utilisateur qui ajouter un like dans var text
                   try {
                       JSONArray jsonArray = response.getJSONArray("mlikes");
                       txt_number_like.setText(""+jsonArray.length());
                       for(int i=0;i<jsonArray.length();i++){
                           JSONObject jsonObject = jsonArray.getJSONObject(i) ;
                           String name = jsonObject.getString("name") ;
                           text.append(name);//on peut utlise taxtView et show tous les utilisateur
                       }



                   } catch (JSONException e) {
                       e.printStackTrace();
                   }
                   if(text.toString().contains(globalv.getUser_name())){
                       bu_add_like.setEnabled(false);
                       bu_add_like.setBackgroundResource(R.mipmap.ic_like_a);

                   }
               }
           }, new Response.ErrorListener() {
               @Override
               public void onErrorResponse(VolleyError error) {
                   VolleyLog.e("Error: ", error.getMessage());
               }
           });



           mrequestQueue.add(req);
       }

    public void showComment() {


        String url = "http://192.168.1.112/appOnLine/show_coment.php?movie_id=" + movie_id;
        list_comments.clear();
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("mcomments");
                    txt_number_comment.setText(""+jsonArray.length());

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String id = jsonObject.getString("id");
                        String name = jsonObject.getString("name");
                        String text = jsonObject.getString("text");
                        String date_time = jsonObject.getString("date_time");
                        String movie_id = jsonObject.getString("movie_id");
                        list_comments.add(new Comment(id, name, text, date_time, movie_id));
                    }
                    adpterComment = new AdpterComment(MoviesActivity.this, R.layout.item_comment, list_comments);
                    list_item_comment.setAdapter(adpterComment);


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

        mrequestQueue.add(req);
    }

    public void sendComment(View view) {
        String text = txt_comment.getText().toString().trim();
        String name = globalv.getUser_name();
              if(text.equals("")){
                  txt_comment.setError("commantaire vide");
                  txt_comment.requestFocus();
              }else {
                  Response.Listener<String> reponseLestener = new Response.Listener<String>() {
                      @Override
                      public void onResponse(String response) {
                          try {
                              JSONObject jsonReponse = new JSONObject(response);
                              boolean result = jsonReponse.getBoolean("result");
                              if (result) {
                                  Toast.makeText(getApplicationContext(), "you are adding comment succssufly now", Toast.LENGTH_LONG).show();
                                  txt_comment.setText("");
                                  layout_show_comment.setVisibility(View.GONE);
                                  list_item_comment.setVisibility(View.VISIBLE);
                                  showComment();


                              } else {
                                  Toast.makeText(getApplicationContext(), "your comment not sending", Toast.LENGTH_LONG).show();

                              }
                          } catch (JSONException e) {
                              e.printStackTrace();
                          }
                      }
                  };
                  SendData_comment sendDataComment = new SendData_comment(text, name, movie_id, reponseLestener);
                  mrequestQueue.add(sendDataComment);
              }
    }

    public void addComment(View view) {
        list_item_comment.setVisibility(View.GONE);
        layout_show_comment.setVisibility(View.VISIBLE);

    }

    public void closeInsertComment(View view) {
        txt_comment.setText("");
        layout_show_comment.setVisibility(View.GONE);
        list_item_comment.setVisibility(View.VISIBLE);
    }

    public void addLike(View view) {

        String name = globalv.getUser_name() ;
String url ="http://192.168.1.112/appOnLine/add_like.php?name_user="+name+"&movie_id="+movie_id  ;
        JsonObjectRequest req =  new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    boolean result =  response.getBoolean("result");
                    if(result){
                        bu_add_like.setEnabled(false);
                      bu_add_like.setBackgroundResource(R.mipmap.ic_like_a);
                     // showNumberLike();
                    }else{
                        Toast.makeText(MoviesActivity.this,"not liked ",Toast.LENGTH_LONG).show();

                    }
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
      RequestQueue requestQueue = Volley.newRequestQueue(this) ;
        requestQueue.add(req) ;
    }


    public void fullScreen(View view) {
        Intent intent  = new Intent(MoviesActivity.this,FullScreenActivity.class) ;
        intent.putExtra("link",link) ;
        startActivity(intent);
    }
}
