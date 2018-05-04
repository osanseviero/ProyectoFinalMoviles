package com.example.osanseviero.proyectofinalmoviles;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.EditText;

public class AdminHomeScreenActivity extends AppCompatActivity {
    EditText username;
    EditText name;
    EditText email;
    EditText password;
    EditText kind;
    String token;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    ReportFragment reportFragment = new ReportFragment();
                    FragmentManager rfm = getSupportFragmentManager();
                    FragmentTransaction reportFragmentTransaction = rfm.beginTransaction();
                    reportFragmentTransaction.replace(R.id.contentAdminFragment, reportFragment);
                    reportFragmentTransaction.commit();
                    return true;
                case R.id.navigation_dashboard:
                    AdminUsersFragment userManagementFragment = new AdminUsersFragment();
                    FragmentManager aufm = getSupportFragmentManager();
                    FragmentTransaction userCreationFragmentTransaction = aufm.beginTransaction();
                    userCreationFragmentTransaction.replace(R.id.contentAdminFragment, userManagementFragment);
                    userCreationFragmentTransaction.commit();
                    return true;
                case R.id.navigation_notifications:
                    AdminRestaurantManagementFragment restaurantManagementFragment = new AdminRestaurantManagementFragment();
                    FragmentManager armfm = getSupportFragmentManager();
                    FragmentTransaction restaurantManagementTransaction = armfm.beginTransaction();
                    restaurantManagementTransaction.replace(R.id.contentAdminFragment, restaurantManagementFragment);
                    restaurantManagementTransaction.commit();
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home_screen);

        // Get token
        Bundle bundle = getIntent().getExtras();
        token = bundle.getString("token");
        Log.d("DBG", "token: " + token);

        ReportFragment reportFragment = new ReportFragment();
        FragmentManager rfm = getSupportFragmentManager();
        FragmentTransaction reportFragmentTransaction = rfm.beginTransaction();
        reportFragmentTransaction.replace(R.id.contentAdminFragment, reportFragment);
        reportFragmentTransaction.commit();

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigationAdmin);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }
}
