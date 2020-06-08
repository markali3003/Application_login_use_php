package com.example.applicationloginusephp;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;


 import com.android.volley.Response;
         import com.android.volley.toolbox.StringRequest;
         import java.util.HashMap;
         import java.util.Map;


public class SendData_registre extends StringRequest {
    private static final String SEND_DATA_URL = "http://192.168.1.112/appOnLine/registre.php";
    private Map<String, String> mapData;

    public SendData_registre(String name ,String email, String passwd, Response.Listener<String> reponseLestener) {
        super(Method.POST, SEND_DATA_URL, reponseLestener, null);
        mapData = new HashMap<>() ;
        mapData.put("name",name);
        mapData.put("password",passwd);
        mapData.put("email",email);



    }
    @Override
    public  Map<String,String>  getParams(){
        return mapData ;
    }
}