package com.example.osanseviero.proyectofinalmoviles;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

public class ClientHomeScreenActivity extends AppCompatActivity {
    String token;
    final DBAdaptor adaptor = new DBAdaptor(this);

    //TODO: Change icons and names
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    Log.i("NAV", "Navigating to home.");
                    ClientCategoryListFragment clientCategoryListfragment = new ClientCategoryListFragment();
                    FragmentManager clfm = getSupportFragmentManager();
                    FragmentTransaction categoryListTransaction = clfm.beginTransaction();
                    categoryListTransaction.replace(R.id.contentFragment, clientCategoryListfragment);
                    categoryListTransaction.commit();
                    return true;
                case R.id.navigation_dashboard:
                    Log.i("NAV", "Navigating to dishes selected to order.");
                    ClientOrderFragment clientOrderFragment = new ClientOrderFragment();
                    FragmentManager ofm = getSupportFragmentManager();
                    FragmentTransaction orderTransaction = ofm.beginTransaction();
                    orderTransaction.replace(R.id.contentFragment, clientOrderFragment);
                    orderTransaction.commit();
                    return true;
                case R.id.navigation_notifications:
                    Log.i("NAV", "Navigating to list of orders.");
                    ClientOrderListFragment clientOrderListFragment = new ClientOrderListFragment();
                    FragmentManager olfm = getSupportFragmentManager();
                    FragmentTransaction orderListTransaction = olfm.beginTransaction();
                    orderListTransaction.replace(R.id.contentFragment, clientOrderListFragment);
                    orderListTransaction.commit();
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_home_screen);

        // Get token
        Bundle bundle = getIntent().getExtras();
        token = bundle.getString("token");
        Log.d("DBG", "token: " + token);

        // Default fragment
        ClientCategoryListFragment fragment = new ClientCategoryListFragment();
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.contentFragment, fragment);
        transaction.commit();

        // Initialize navigation bar
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    public void logout(View v)
    {
        JSONObject js = new JSONObject();
        try {
            js.put("token", token);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.d("DBG", js.toString());

        String url = "http://docker-azure.cloudapp.net/user/logout";

        final String requestBody = js.toString();
        StringRequest request = new StringRequest(
                Request.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        Log.d("DBG", "Abriendo home screen.");
                        Intent intent = new Intent(ClientHomeScreenActivity.this , HomeScreenActivity.class);

                        adaptor.open();
                        adaptor.dropDatabase();
                        adaptor.close();
                        Toast.makeText(getApplicationContext(), "Salió de su cuenta exitosamente", Toast.LENGTH_SHORT).show();

                        startActivity(intent);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError e) {
                        e.printStackTrace();
                        Log.e("ERROR", "Error code: " + e.networkResponse.statusCode);
                        Log.e("err", "Message:" + new String(e.networkResponse.data));

                    }
                }
        ) {
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            @Override
            public byte[] getBody() throws AuthFailureError {
                try {
                    return requestBody == null ? null : requestBody.getBytes("utf-8");
                } catch (UnsupportedEncodingException uee) {
                    VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", requestBody, "utf-8");
                    return null;
                }
            }

            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                String responseString = "";
                if (response != null) {
                    responseString = String.valueOf(response.statusCode);
                    // can get more details such as response.headers
                }
                return Response.success(responseString, HttpHeaderParser.parseCacheHeaders(response));
            }
        };

        Volley.newRequestQueue(this).add(request);
    }
}
