package com.example.osanseviero.proyectofinalmoviles;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ChefHomeScreenActivity extends AppCompatActivity {
    String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chef_home_screen);

        // Get token
        Bundle bundle = getIntent().getExtras();
        token = bundle.getString("token");
        Log.d("DBG", "token: " + token);

        ChefOrdersFragment chefOrdersFragment = new ChefOrdersFragment();
        FragmentManager cofm = getSupportFragmentManager();
        FragmentTransaction reportFragmentTransaction = cofm.beginTransaction();
        reportFragmentTransaction.replace(R.id.contentChefFragment, chefOrdersFragment);
        reportFragmentTransaction.commit();
    }

    public void logout(View v)
    {
        String url = "http://docker-azure.cloudapp.net/user/logout";

        JSONObject js = new JSONObject();
        try {
            js.put("token", token);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.d("DBG", js.toString());

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.POST,
                url,
                js,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
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

        Volley.newRequestQueue(this).add(request);

        Log.i("NAV", "Abriendo home screen.");
        Intent intent = new Intent(this, HomeScreenActivity.class);
        startActivity(intent);
    }



    public void chefConfig(View v) {
        ChefConfigFragment chefConfigFragment = new ChefConfigFragment();
        FragmentManager ccfm = getSupportFragmentManager();
        FragmentTransaction reportFragmentTransaction = ccfm.beginTransaction();
        reportFragmentTransaction.replace(R.id.contentChefFragment, chefConfigFragment);
        reportFragmentTransaction.commit();
    }

    public void chefHome(View v) {
        ChefOrdersFragment chefOrdersFragment = new ChefOrdersFragment();
        FragmentManager cofm = getSupportFragmentManager();
        FragmentTransaction reportFragmentTransaction = cofm.beginTransaction();
        reportFragmentTransaction.replace(R.id.contentChefFragment, chefOrdersFragment);
        reportFragmentTransaction.commit();
    }
}
