package com.max.proyecto_parcial2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ChooseClient extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_client);

        Button listClients = (Button) findViewById(R.id.btn_list_clients);
        Button insertClient = (Button) findViewById(R.id.btn_insert_client);
        Button seeHistory = (Button) findViewById(R.id.btn_hist_employee);

        listClients.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent listIntent = new Intent().setClass(ChooseClient.this, ListClients.class);
                startActivity(listIntent);
            }
        });

        insertClient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent insertIntent = new Intent().setClass(ChooseClient.this, InsertClientEmployee.class);
                startActivity(insertIntent);
            }
        });

        seeHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent insertIntent = new Intent().setClass(ChooseClient.this, GraphsReport_Comission.class);
                startActivity(insertIntent);
            }
        });
    }
}
