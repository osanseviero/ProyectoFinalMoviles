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
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class SignUpActivity extends AppCompatActivity {
    EditText username;
    EditText name;
    EditText email;
    EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        username = findViewById(R.id.signupUsername);
        name = findViewById(R.id.signupName);
        email = findViewById(R.id.signupEmail);
        password = findViewById(R.id.signupPassword);
    }

    public void createUser(View v) {
        final Intent intent = new Intent(this, LoginActivity.class);

        JSONObject js = new JSONObject();
        try {
            // Build JSON from text fields
            js.put("username", username.getText().toString());
            js.put("password", password.getText().toString());

            js.put("name", name.getText().toString());
            js.put("email", email.getText().toString());
            js.put("password", password.getText().toString());

            // This is for customers and no token is needed for this oepration
            js.put("kind", 5);
            js.put("token", "");
            Log.d("DBG", js.toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }

        //TODO: Have a loading bar or something like that

        // Create JSON request
        String url = "http://docker-azure.cloudapp.net/user/create";
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(
                Request.Method.POST, url, js,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String message = response.getString("message");
                            String id = response.getString("id");
                            Log.d("DBG", "Message: " + message);
                            Log.d("DBG", "User id: " + id);

                            //TODO: Show a success message
                            startActivity(intent);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                //TODO: Handle error
                Log.e("ERROR", "Error code: " + error.networkResponse.statusCode);
                Log.e("err", "Message:" + new String(error.networkResponse.data));
            }
        });

        Volley.newRequestQueue(this).add(jsonObjReq);
    }
}
