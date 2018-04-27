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
import android.widget.TextView;

public class ClientHomeScreenActivity extends AppCompatActivity {

    private TextView mTextMessage;

    //TODO: Change icons and names
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    //mTextMessage.setText("Menú");
                    Log.i("NAV", "Navigating to home.");
                    CategoryListFragment fragment = new CategoryListFragment();
                    FragmentManager fm = getSupportFragmentManager();
                    FragmentTransaction transaction = fm.beginTransaction();
                    transaction.replace(R.id.contentFragment, fragment);
                    transaction.commit();
                    return true;
                case R.id.navigation_dashboard:
                    //mTextMessage.setText("Ordenar");
                    Log.i("NAV", "Navigating to dishes selected to order.");
                    OrderFragment orderFragment = new OrderFragment();
                    FragmentManager ofm = getSupportFragmentManager();
                    FragmentTransaction orderTransaction = ofm.beginTransaction();
                    orderTransaction.replace(R.id.contentFragment, orderFragment);
                    orderTransaction.commit();
                    return true;
                case R.id.navigation_notifications:
                    //mTextMessage.setText("Ordenados");
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_home_screen);
        mTextMessage = (TextView) findViewById(R.id.message);

        CategoryListFragment fragment = new CategoryListFragment();
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.contentFragment, fragment);
        transaction.commit();

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    public void goToDishList(View v) {
        //TODO: Extract category being selected
        Log.i("NAV", "Navigating to category");
        DishListFragment fragment = new DishListFragment();
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.contentFragment, fragment);
        transaction.commit();
    }

    public void goToDishRecipe(View v) {
        //TODO: Extract recipe being selected
        Log.i("NAV", "Navigating to specific dish recipe.");
        DishFragment fragment = new DishFragment();
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.contentFragment, fragment);
        transaction.commit();
    }

}
