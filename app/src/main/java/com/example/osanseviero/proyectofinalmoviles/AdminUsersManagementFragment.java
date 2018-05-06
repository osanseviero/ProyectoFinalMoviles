package com.example.osanseviero.proyectofinalmoviles;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class AdminUsersManagementFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_admin_users_management, container, false);

        String urlFirstQuery = "http://docker-azure.cloudapp.net/user/query/3";
        String urlSecondQuery = "http://docker-azure.cloudapp.net/user/query/4";

        JSONObject js = new JSONObject();
        try {
            js.put("token", ((AdminHomeScreenActivity) rootView.getContext()).token );
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.d("DBG", js.toString());


        // Put this in a listView
        // TODO: Add filters to do according to user type
        // TODO: Have a loading bar or something like that
        MyJsonArrayRequest request = new MyJsonArrayRequest(
                Request.Method.POST,
                urlFirstQuery,
                js,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d("DBG", response.toString());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("ERROR", "Error code: " + error.networkResponse.statusCode);
                        Log.e("err", "Message:" + new String(error.networkResponse.data));
                    }
                }
        );

        Volley.newRequestQueue(rootView.getContext()).add(request);
        // Inflate the layout for this fragment
        return rootView;
    }
}
