package com.max.proyecto_parcial2;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity_Client extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_client);

        Button btn_catalog = (Button) findViewById(R.id.client_btn_catalog);

        btn_catalog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent().setClass(MainActivity_Client.this, Client_Catalog.class);
                startActivity(intent);
            }
        });

    }
}
