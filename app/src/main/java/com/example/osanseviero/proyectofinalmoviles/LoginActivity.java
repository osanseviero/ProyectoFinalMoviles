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
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

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

    public void logincheck(View v) {

        if(TextUtils.isEmpty(username.getText())){
            Toast.makeText(getApplicationContext(),"Campo de Usuario vacío",Toast.LENGTH_SHORT).show();
        }

        else if (TextUtils.isEmpty(password.getText())){
            Toast.makeText(getApplicationContext(),"Campo de Contraseña vacío",Toast.LENGTH_SHORT).show();
        }

        else{
            login(v);
        }
    }

    public void login(View v) {
        final Intent clientIntent = new Intent(this, ClientHomeScreenActivity.class);
        final Intent adminIntent = new Intent(this, AdminHomeScreenActivity.class);
        final Intent waiterIntent = new Intent(this, WaiterHomeScreenActivity.class);
        final Intent chefIntent = new Intent(this, ChefHomeScreenActivity.class);

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
                        try {
                            String token = response.getString("token");
                            String kind = response.getString("kind");

                            Log.d("DBG", "token: " + token);
                            Log.d("DBG", "kind: " + kind);

                            Toast.makeText(getApplicationContext(), "Login Exitoso", Toast.LENGTH_SHORT).show();

                            //TODO: Check kind and send to corresponding screen
                            if(kind.equals("1")) {
                                adminIntent.putExtra("token", token);
                                Log.i("NAV", "Abriendo home screen del admin.");
                                startActivity(adminIntent);
                            } else if(kind.equals("3")) {
                                chefIntent.putExtra("token", token);
                                Log.i("NAV", "Abriendo home screen del chef.");
                                startActivity(chefIntent);
                            } else if(kind.equals("4")) {
                                waiterIntent.putExtra("token", token);
                                Log.i("NAV", "Abriendo home screen del mesero.");
                                startActivity(waiterIntent);
                            }
                            else if(kind.equals("5")) {
                                clientIntent.putExtra("token", token);
                                Log.i("NAV", "Abriendo home screen del cliente.");
                                startActivity(clientIntent);
                            }
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
    }
}
