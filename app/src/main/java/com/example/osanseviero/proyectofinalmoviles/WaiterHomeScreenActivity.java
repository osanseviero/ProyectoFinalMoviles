package com.example.osanseviero.proyectofinalmoviles;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

public class WaiterHomeScreenActivity extends AppCompatActivity {

    private TextView mTextMessage;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waiter_home_screen);

        WaiterTablesFragment waiterTablesfragment = new WaiterTablesFragment();
        FragmentManager wtfm = getSupportFragmentManager();
        FragmentTransaction waiterTablesTransaction = wtfm.beginTransaction();
        waiterTablesTransaction.replace(R.id.contentWaiterFragment, waiterTablesfragment);
        waiterTablesTransaction.commit();

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigationWaiter);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

}
