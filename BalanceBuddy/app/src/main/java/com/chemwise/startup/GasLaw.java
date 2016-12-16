package com.chemwise.startup;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class GasLaw extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gas_law);
    }

    public void returnToHome(View view) {

        Intent returnHome = new Intent(this, MainActivity.class);
        startActivity(returnHome);
    }
}
