package com.example.applicationloginusephp;


import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import java.util.HashMap;
import java.util.Map;


class SendData_comment extends StringRequest {
    private static final String SEND_DATA_URL = "http://192.168.1.112/appOnLine/add_comment.php";
    private Map<String, String> mapData;

    public SendData_comment(String text ,String name, String movieId, Response.Listener<String> reponseLestener) {
        super(Method.POST, SEND_DATA_URL, reponseLestener, null);
        mapData = new HashMap<>() ;
        mapData.put("text_comment",text);
        mapData.put("name_user",name);
        mapData.put("movie_id",movieId);



    }



    @Override
    public  Map<String,String>  getParams(){
        return mapData ;
    }
}