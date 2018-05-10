package com.example.osanseviero.proyectofinalmoviles;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class HomeScreenActivity extends AppCompatActivity {
    DBAdaptor adaptor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
    }

    protected void onResume()
    {
        super.onResume();

        final Intent clientIntent = new Intent(this, ClientHomeScreenActivity.class);
        final Intent adminIntent = new Intent(this, AdminHomeScreenActivity.class);
        final Intent waiterIntent = new Intent(this, WaiterHomeScreenActivity.class);
        final Intent chefIntent = new Intent(this, ChefHomeScreenActivity.class);

        Log.d("DBG","creating adaptor");

        adaptor = new DBAdaptor(this);
        adaptor.open();
        Cursor cur;
        cur = adaptor.getToken();

        Log.d("DBG", String.valueOf(cur.getCount()));
        if(cur.getCount() == 0 ){
            Log.d("DBG", "token no encontrado");
        } else {
            cur.moveToFirst();
            Log.d("DBG", cur.getString(0));

            JSONObject js = new JSONObject();
            try {
                // Build JSON from text fields
                js.put("token", cur.getString(0));
                Log.d("DBG", js.toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }


            String url = "http://docker-azure.cloudapp.net/user/validate";
            final String token = cur.getString(0);
            JsonObjectRequest jsonObjReq = new JsonObjectRequest(
                    Request.Method.POST, url, js,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                String kind = response.getString("kind");

                                Log.d("DBG", "token: " + token);
                                Log.d("DBG", "kind: " + kind);

                                Resources.setToken(token);

                                Toast.makeText(getApplicationContext(), "Bienvenido de nuevo", Toast.LENGTH_SHORT).show();

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
        adaptor.close();
    }

    public void loginScreen(View v) {
        Log.i("NAV", "Abriendo login screen.");
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    public void signUpScreen(View v) {
        Log.i("NAV", "Abriendo sign up screen.");
        Intent intent = new Intent(this, SignUpActivity.class);
        startActivity(intent);
    }
}
