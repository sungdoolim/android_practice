package com.example.mysqltest;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class Register extends StringRequest {
    final static private String URL="https://192.168.56.1/register.php";
    private Map<String,String> parameters;

    public Register(String id, String name, String address, Response.Listener<String> listener){
super(Method.POST,URL,listener,null);
parameters=new HashMap<>();
        parameters.put("id",id);
        parameters.put("name",name);
        parameters.put("address",address);

    }

    public Map<String, String> getParameters() {
        return parameters;
    }
}
