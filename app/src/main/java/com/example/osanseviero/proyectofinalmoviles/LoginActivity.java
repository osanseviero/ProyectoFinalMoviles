package com.example.osanseviero.proyectofinalmoviles;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.osanseviero.proyectofinalmoviles.userRequests;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {
    EditText username;
    EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = findViewById(R.id.loginUsername);
        password = findViewById(R.id.loginPassword);
    }

    public void login(View v) {
        final Intent clientIntent = new Intent(this, ClientHomeScreenActivity.class);
        final Intent waiterIntent = new Intent(this, WaiterHomeScreenActivity.class);

        JSONObject js = new JSONObject();
        try {
            // Build JSON from text fields
            js.put("username", username.getText().toString());
            js.put("password", password.getText().toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // Create JSON request
        String url = "http://docker-azure.cloudapp.net/user/login";
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(
                Request.Method.POST, url, js,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("DBG", response.toString());

                        //TODO: Check user type and send to corresponding screen

                        Log.i("NAV", "Abriendo home screen del cliente.");

                        //TODO: Add token to bundle
                        startActivity(clientIntent);
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                //TODO: Handle error
                Log.d("DBG", "Error: " + error.getMessage());
            }
        });

        Volley.newRequestQueue(this).add(jsonObjReq);
    }
}
