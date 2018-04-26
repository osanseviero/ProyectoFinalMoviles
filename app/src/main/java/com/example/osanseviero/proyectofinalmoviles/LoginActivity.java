package com.example.osanseviero.proyectofinalmoviles;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("DBG", "Abriendo actividad en el login");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void goToHomeScreen(View v) {
        // Here, we need to check what type of user this is and send to the corresponding home
        // screen.
        Log.d("DBG", "Abriendo actividad");
        Intent intent = new Intent(this, ClientHomeScreenActivity.class);
        startActivity(intent);
    }
}
