package com.example.osanseviero.proyectofinalmoviles;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void goToHomeScreen(View v) {
        // Here, we need to check what type of user this is and send to the corresponding home
        // screen.
        Log.i("NAV", "Abriendo home screen del cliente.");
        Intent intent = new Intent(this, ClientHomeScreenActivity.class);
        startActivity(intent);
    }
}
