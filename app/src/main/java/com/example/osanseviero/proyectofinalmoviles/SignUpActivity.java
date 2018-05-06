package com.example.osanseviero.proyectofinalmoviles;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

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

    public void signupcheck(View v) {

        if(TextUtils.isEmpty(username.getText())){
            Toast.makeText(getApplicationContext(),"Campo de Usuario vacío",Toast.LENGTH_SHORT).show();
        }

        else if (TextUtils.isEmpty(name.getText())){
            Toast.makeText(getApplicationContext(),"Campo de Nombre vacío",Toast.LENGTH_SHORT).show();
        }

        else if (TextUtils.isEmpty(email.getText())){
            Toast.makeText(getApplicationContext(),"Campo de Email vacío",Toast.LENGTH_SHORT).show();
        }

        else if (TextUtils.isEmpty(password.getText())){
            Toast.makeText(getApplicationContext(),"Campo de Contraseña vacío",Toast.LENGTH_SHORT).show();
        }

        else{
            createUser(v);
        }
    }

    public void createUser(View v) {
        final Intent intent = new Intent(this, ClientHomeScreenActivity.class);

        JSONObject js = new JSONObject();
        try {
            // Build JSON from text fields
            js.put("username", username.getText().toString());
            js.put("password", password.getText().toString());

            js.put("name", name.getText().toString());
            js.put("email", email.getText().toString());
            js.put("password", password.getText().toString());

            // This is for customers and no token is needed for this operation
            js.put("kind", 5);
            js.put("token", "");
            Log.d("DBG", js.toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }

        //TODO: Have a loading bar or something like that

        // Create JSON request for user creation
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
                            Toast.makeText(getApplicationContext(), "Usuario creado exitosamente", Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                //TODO: Handle error

                Toast.makeText(getApplicationContext(), new String(error.networkResponse.data), Toast.LENGTH_SHORT).show();
                Log.e("ERROR", "Error code: " + error.networkResponse.statusCode);
                Log.e("err", "Message:" + new String(error.networkResponse.data));
            }
        });

        Volley.newRequestQueue(this).add(jsonObjReq);

        //JSON request for login
        JSONObject js2 = new JSONObject();
        try {
            // Build JSON from text fields
            js2.put("username", username.getText().toString());
            js2.put("password", password.getText().toString());
            Log.d("DBG", js2.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String url2 = "http://docker-azure.cloudapp.net/user/login";
        JsonObjectRequest jsonObjReq2 = new JsonObjectRequest(
                Request.Method.POST, url2, js2,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String token = response.getString("token");
                            String kind = response.getString("kind");

                            Log.d("DBG", "token: " + token);
                            Log.d("DBG", "kind: " + kind);

                            Toast.makeText(getApplicationContext(), "Login Exitoso", Toast.LENGTH_SHORT).show();

                            //TODO: Check kind and send to corresponding screen

                            intent.putExtra("token", token);
                            Log.i("NAV", "Abriendo home screen del cliente.");
                            startActivity(intent);
                        }
                        catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                //TODO: Handle error

                Toast.makeText(getApplicationContext(), new String(error.networkResponse.data), Toast.LENGTH_SHORT).show();
                Log.e("ERROR", "Error code: " + error.networkResponse.statusCode);
                Log.e("err", "Message:" + new String(error.networkResponse.data));
            }
        });


        Volley.newRequestQueue(this).add(jsonObjReq2);
    }
}
