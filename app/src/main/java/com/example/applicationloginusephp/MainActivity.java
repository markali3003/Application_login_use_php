package com.example.applicationloginusephp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.VideoView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.applicationloginusephp.Mail.Config;
import com.example.applicationloginusephp.Mail.SendMail;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    Globalv globalv;
    DataBaseApp dataBaseApp ;
    RequestQueue mrequestQueue;
    EditText txt_nameLoginIn, txt_passwordLoginIn;
    Button buLoginIn, buSignUp;
    CheckBox checkBox_login ;
    String check_login ;
    String savedata_login ;
    //view de registre
    EditText txt_nameSignUp, txt_emailSignUp, txt_passwordSignUp, txt_password2SignUp;
    Button buConserve;
    CheckBox checkBox;
    CheckBox checkBox_registre ;
    String savedata_registre ="0";
    //view de registre
    EditText txt_email_recover ;
    //DECLARER LES LAYOUT
    LinearLayout layout_login;
    LinearLayout layout_registre;
    LinearLayout layout_recover ;
    //test
    VideoView videoView ;
    ProgressBar progressBar ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        globalv = (Globalv) getApplicationContext();
        dataBaseApp = new DataBaseApp(this);
        //declarer les view login
        txt_nameLoginIn = findViewById(R.id.nameLoginIn);
        txt_passwordLoginIn = findViewById(R.id.passwordLoginIn);
        buLoginIn = findViewById(R.id.buttonLoginIn);
        buSignUp = findViewById(R.id.buttonSignUp);
        checkBox_login = findViewById(R.id.id_checkbox) ;
        //declarer les view registre
        txt_nameSignUp = findViewById(R.id.nameSignUp);
        txt_emailSignUp = findViewById(R.id.emailSignUp);
        txt_passwordSignUp = findViewById(R.id.passwordSignUp);
        txt_password2SignUp = findViewById(R.id.password2SignUp);
        checkBox = findViewById(R.id.id_checked);
        buConserve = findViewById(R.id.buttonconserverinfo);
        checkBox_registre = findViewById(R.id.id_checkbox_rregistre) ;
        //view de registre
         txt_email_recover = findViewById(R.id.id_edittext_recover_email);
        //DECLARER LES LAYOUT
        layout_login = findViewById(R.id.ly_login);
        layout_registre = findViewById(R.id.ly_registre);
        layout_recover = findViewById(R.id.id_ly_recover_email);
        mrequestQueue = Volley.newRequestQueue(MainActivity.this);


        if(dataBaseApp.getChcked() != null){
               check_login = dataBaseApp.getChcked() ;
              if(check_login.equals("1")){

                  txt_nameLoginIn.setText(dataBaseApp.getUserName());
                  txt_passwordLoginIn.setText(dataBaseApp.getPassword());
                  checkBox_login.setChecked(true);
                      savedata_login = "1" ;
                      startActivity(new Intent(MainActivity.this,HomeActivity.class));
                      globalv.setUser_name(dataBaseApp.getUserName());
                      finish();

              }else{
                  checkBox_login.setChecked(false);
                  savedata_login="0";
              }

        }
        checkBox_login.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                   if(isChecked){
                       savedata_login="1" ;
                       Toast.makeText(MainActivity.this,"checked",Toast.LENGTH_LONG).show();
                   }else{
                       savedata_login="0" ;
                       Toast.makeText(MainActivity.this,"checked",Toast.LENGTH_LONG).show();
                   }
            }
        });
        checkBox_registre.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    savedata_registre="1" ;
                    Toast.makeText(MainActivity.this,"checked",Toast.LENGTH_LONG).show();
                }else{
                    savedata_registre="0" ;
                    Toast.makeText(MainActivity.this,"Unchecked",Toast.LENGTH_LONG).show();
                }
            }
        });
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked){
                    txt_password2SignUp.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    Toast.makeText(MainActivity.this,"checked",Toast.LENGTH_LONG).show();
                }else{
                    txt_password2SignUp.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    Toast.makeText(MainActivity.this,"Unchecked",Toast.LENGTH_LONG).show();
                }
            }
        });
        //TEST
          videoView= findViewById(R.id.id_videoView2);
        progressBar = findViewById(R.id.progressBar) ;
        assert  progressBar!= null ;
        progressBar.bringToFront();
          String ui="rtsp://r1---sn-4g5e6nlk.googlevideo.com/Cj0LENy73wIaNAlTUZCWgXyKKBMYESARFC1x2GBeMOCoAUIASARgzbbFnebwwaZeigELVFI0U09SOWJOYjAM/E0500C53363147958576F5F0C842074829FEA357.9B5F7223B05D6B862963338584CF357253AD7F91/yt8/1/video.3gp";
        String u = "android.resource://com.example.applicationloginusephp/"+R.raw.quran  ;
        Uri uri = Uri.parse(ui) ;
       // MediaController mediaController = new MediaController(this) ;
       // videoView.setVideoURI(uri);
       // videoView.setMediaController(new MediaController(this));
       // mediaController.setAnchorView(videoView);
        //videoView.requestFocus() ;
        //videoView.start();
       /* videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                progressBar.setVisibility(View.INVISIBLE);
            }
        });*/






    }



    public void loginIn(View view) {
        final String name = txt_nameLoginIn.getText().toString().trim();
        final String password = txt_passwordLoginIn.getText().toString().trim();



        Response.Listener<String> reponseLestener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonReponse = new JSONObject(response);
                    boolean result = jsonReponse.getBoolean("success");
                    if (result) {
                        Toast.makeText(getApplicationContext(), "you are login now", Toast.LENGTH_LONG).show();
                        startActivity(new Intent(MainActivity.this, HomeActivity.class));
                        globalv.setUser_name(name);
                        dataBaseApp.updateData_Login(name,password,savedata_login) ;
                        MainActivity.this.finish();

                    } else {
                        Toast.makeText(getApplicationContext(), "NOM OU PASSWORD PAS VALIDE", Toast.LENGTH_LONG).show();

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };


        if (name.isEmpty()) {
            txt_nameLoginIn.setError("entrer votre nom");
            txt_nameLoginIn.requestFocus();
        } else if (password.isEmpty()) {
            txt_passwordLoginIn.setError("enter votre password");
        } else if (name.isEmpty() && password.isEmpty()) {
            Toast.makeText(MainActivity.this, "colone vide", Toast.LENGTH_LONG).show();

        } else if (!(name.isEmpty() && password.isEmpty())) {
            SendData_login sendDataLogin = new SendData_login(name, password, reponseLestener);
            mrequestQueue.add(sendDataLogin);

        }

    }


    public void conserverData(View view) {
        final String name = txt_nameSignUp.getText().toString().trim();
        final String email = txt_emailSignUp.getText().toString().trim();
        final String password = txt_passwordSignUp.getText().toString().trim();
        String password2 = txt_password2SignUp.getText().toString().trim();
        Response.Listener<String> reponseLestener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonReponse = new JSONObject(response);
                    boolean result = jsonReponse.getBoolean("success");
                    if (result) {
                        Toast.makeText(getApplicationContext(), "you are login now", Toast.LENGTH_LONG).show();
                        startActivity(new Intent(MainActivity.this, HomeActivity.class));
                        globalv.setUser_name(name);
                        dataBaseApp.updateData_Registre(name,email,password,savedata_registre);
                        MainActivity.this.finish();

                    } else {
                        Toast.makeText(getApplicationContext(), "name existe deja choiser autre nom", Toast.LENGTH_LONG).show();
                        txt_nameSignUp.setError("name pas valide");
                        txt_nameSignUp.requestFocus() ;

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };


        if (name.isEmpty()) {
            txt_nameSignUp.setError("entrer votre nom");
            txt_nameSignUp.requestFocus();
        } else if (email.isEmpty()) {
            txt_emailSignUp.setError("enter votre email");
            txt_emailSignUp.requestFocus();
        } else if (password.isEmpty()) {
            txt_passwordSignUp.setError("enter votre password");
            txt_passwordSignUp.requestFocus();
        } else if (password2.isEmpty()) {
            txt_password2SignUp.setError("encore enter votre password");
            txt_passwordSignUp.requestFocus();
        } else if (!password2.equalsIgnoreCase(password)) {
            txt_password2SignUp.setError("enter le meme password");
            txt_password2SignUp.requestFocus();
        } else if (name.isEmpty() && email.isEmpty() && password.isEmpty() && password2.isEmpty()) {
            Toast.makeText(MainActivity.this, "colone vide", Toast.LENGTH_LONG).show();

        } else if (!(name.isEmpty() && email.isEmpty() && password.isEmpty() && password2.isEmpty())) {
            SendData_registre sendDataLogin = new SendData_registre(name, email, password, reponseLestener);
            mrequestQueue.add(sendDataLogin);

        }

    }

    public void signUp(View view) {
        layout_login.setVisibility(View.GONE);
        layout_registre.setVisibility(View.VISIBLE);
    }


    public void rendPassword(View view){
        final String email = txt_email_recover.getText().toString().trim() ;
        String url = "http://192.168.1.112/appOnLine/forget_password.php?email=" + email;

        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

               try{
                    boolean result = response.getBoolean("result") ;
                    if(result){
                    }else{
                       Toast.makeText(MainActivity.this,"Adresse introuvable ",Toast.LENGTH_LONG).show();
                    }

                }catch (JSONException e) {
                    e.printStackTrace();
                }
                try {

                    JSONArray jsonArray = response.getJSONArray("info") ;
                        JSONObject jsonObject = jsonArray.getJSONObject(0) ;
                        String name = jsonObject.getString("name");
                        String password = jsonObject.getString("password");

                        layout_recover.setVisibility(View.GONE);
                        layout_login.setVisibility(View.VISIBLE);

                        SendMail sm =new SendMail(MainActivity.this,email,"recover mot de passe ","hello i am admin,your nam is :"+name+",your password is:"+password);
                        sm.execute() ;



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
         if(email.isEmpty()){
             txt_email_recover.setError("vide");
             txt_email_recover.requestFocus();
         }else{
             mrequestQueue.add(req);
         }

    }




    public void recoverEmail(View view) {
        layout_login.setVisibility(View.GONE);
        layout_recover.setVisibility(View.VISIBLE);

    }
}