package com.example.osanseviero.proyectofinalmoviles.waiterFragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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

import com.example.osanseviero.proyectofinalmoviles.DBAdaptor;
import com.example.osanseviero.proyectofinalmoviles.HomeScreenActivity;
import com.example.osanseviero.proyectofinalmoviles.R;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

public class WaiterHomeScreenActivity extends AppCompatActivity {
    final DBAdaptor adaptor = new DBAdaptor(this);
    String token;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    Log.i("NAV", "Navigating to home.");
                    WaiterTablesFragment waiterTablesfragment = new WaiterTablesFragment();
                    FragmentManager wtfm = getSupportFragmentManager();
                    FragmentTransaction waiterTablesTransaction = wtfm.beginTransaction();
                    waiterTablesTransaction.replace(R.id.contentWaiterFragment, waiterTablesfragment);
                    waiterTablesTransaction.commit();
                    return true;
                case R.id.navigation_dashboard:
                    return true;
            }
            return false;
        }
    };

    DrawerLayout drawerLayout;
    NavigationView navigationView;

    private NavigationView.OnNavigationItemSelectedListener drawer_listener = new NavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()){
                case R.id.nav_logout:
                    logout(null);
                    drawerLayout.closeDrawers();
                    return true;
            }
            return false;
        }
    };

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waiter_home_screen);

        // Get token
        Bundle bundle = getIntent().getExtras();
        token = bundle.getString("token");
        Log.d("DBG", "token: " + token);

        // Toolbar stuff
        Toolbar toolbar = findViewById(R.id.toolbar_waiter);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu_black_24dp);

        // Drawer stuff
        drawerLayout = findViewById(R.id.drawer_container_waiter);
        navigationView = findViewById(R.id.nav_view_waiter);
        navigationView.setNavigationItemSelectedListener(drawer_listener);

        WaiterTablesFragment waiterTablesfragment = new WaiterTablesFragment();
        FragmentManager wtfm = getSupportFragmentManager();
        FragmentTransaction waiterTablesTransaction = wtfm.beginTransaction();
        waiterTablesTransaction.replace(R.id.contentWaiterFragment, waiterTablesfragment);
        waiterTablesTransaction.commit();

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigationWaiter);
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
                        Intent intent = new Intent(WaiterHomeScreenActivity.this , HomeScreenActivity.class);

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
