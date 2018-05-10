package com.example.osanseviero.proyectofinalmoviles.requests;

import android.util.Log;
import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.osanseviero.proyectofinalmoviles.Resources;
import org.json.JSONException;
import org.json.JSONObject;

public class RequestHelper {
    public static JSONObject makeJsonToken(){
        Log.v("MAKING JSON", "-> called!");
        try {
            Log.v("MAKING JSON", Resources.getToken());
            return new JSONObject().put("token", Resources.getToken());
        }
        catch(JSONException ex){
            ex.printStackTrace();
            return null;
        }
    }

    public static JsonObjectRequest makeJsonPOST(String endpoint, JSONObject jsonObject,
                                                 final CallbackSuccess success,
                                                 final CallbackError error){
        return new JsonObjectRequest(Request.Method.POST, endpoint, jsonObject,
                response -> success.onSuccess(response),
                volleyError -> error.onError(volleyError)
        );
    }
}
