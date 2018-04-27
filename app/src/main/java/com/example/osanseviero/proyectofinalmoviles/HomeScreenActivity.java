package com.example.osanseviero.proyectofinalmoviles;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class HomeScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
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
