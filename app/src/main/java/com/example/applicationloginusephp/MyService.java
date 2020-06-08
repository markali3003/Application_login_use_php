package com.example.applicationloginusephp;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

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

public class MyService extends Service {
    public MyService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
       // return super.onStartCommand(intent, flags, startId);
        Toast.makeText(MyService.this,"start my service",Toast.LENGTH_LONG).show();
          run_loop() ;
        return  START_STICKY ;
    }

    public void run_loop(){
        RequestQueue mrequestQueue ;
        String url = "http://192.168.1.112/appOnLine/all_movies.php" ;
        mrequestQueue = Volley.newRequestQueue(this) ;
        final  Globalv globalv =(Globalv) getApplicationContext() ;

        JsonObjectRequest req =  new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray =  response.getJSONArray("all_movies") ;
                    JSONObject jsonObject = jsonArray.getJSONObject(0) ;
                        int lastthread = Integer.parseInt(jsonObject.getString("id"));
                        Toast.makeText(MyService.this,lastthread+" : "+globalv.getTotal_thread(),Toast.LENGTH_LONG).show();
                        if(globalv.getTotal_thread() < lastthread){
                            globalv.setTotal_thread(lastthread);
                            NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext()) ;
                            builder.setContentTitle("app online");
                            builder.setContentText("new movie") ;
                            builder.setSmallIcon(R.mipmap.ic_like_a);
                            builder.setAutoCancel(true);
                            builder.setNumber(1);
                            Notification notification = builder.build() ;
                            NotificationManager mm = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE) ;
                            mm.cancel(1);
                            mm.notify(1,notification);


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

        mrequestQueue.add(req) ;


        Handler handler = new Handler() ;
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                run_loop();
            }
        },29000) ;


    }

    @Override
    public void onDestroy() {
        Toast.makeText(this,"stop service",Toast.LENGTH_LONG).show();
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return  null ;
    }
}
