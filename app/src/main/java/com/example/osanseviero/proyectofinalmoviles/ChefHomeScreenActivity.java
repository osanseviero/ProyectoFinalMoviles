package com.example.osanseviero.proyectofinalmoviles;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class ChefHomeScreenActivity extends AppCompatActivity {
    String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chef_home_screen);

        // Get token
        Bundle bundle = getIntent().getExtras();
        token = bundle.getString("token");
        Log.d("DBG", "token: " + token);

        ChefOrdersFragment chefOrdersFragment = new ChefOrdersFragment();
        FragmentManager cofm = getSupportFragmentManager();
        FragmentTransaction reportFragmentTransaction = cofm.beginTransaction();
        reportFragmentTransaction.replace(R.id.contentChefFragment, chefOrdersFragment);
        reportFragmentTransaction.commit();
    }

    public void chefConfig(View v) {
        ChefConfigFragment chefConfigFragment = new ChefConfigFragment();
        FragmentManager ccfm = getSupportFragmentManager();
        FragmentTransaction reportFragmentTransaction = ccfm.beginTransaction();
        reportFragmentTransaction.replace(R.id.contentChefFragment, chefConfigFragment);
        reportFragmentTransaction.commit();
    }
}
