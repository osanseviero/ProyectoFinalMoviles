package com.example.osanseviero.proyectofinalmoviles;

import android.util.Log;

public class Resources {
    public static final String rootEndpoint = "http://docker-azure.cloudapp.net";
    private static String token = "";

    public synchronized static String getToken(){
        return token;
    }
    public synchronized static void setToken(String new_token){
        token = new_token;
    }
}
