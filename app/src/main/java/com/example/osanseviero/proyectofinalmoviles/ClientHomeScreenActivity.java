package com.example.osanseviero.proyectofinalmoviles;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

public class ClientHomeScreenActivity extends AppCompatActivity {
    String token;

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
}
