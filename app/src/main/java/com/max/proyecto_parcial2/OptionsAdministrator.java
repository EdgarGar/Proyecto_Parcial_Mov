package com.max.proyecto_parcial2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class OptionsAdministrator extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options_administrator);

        Button clients = (Button) findViewById(R.id.btn_clients);
        Button products = (Button) findViewById(R.id.btn_products);
        Button employees = (Button) findViewById(R.id.btn_employees);
        Button report = (Button) findViewById(R.id.btn_report);

        clients.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent clientIntent = new Intent().setClass(OptionsAdministrator.this, ChooseClient.class);
                startActivity(clientIntent);
            }
        });

        products.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent productIntent = new Intent().setClass(OptionsAdministrator.this, OptionsProduct.class);
                startActivity(productIntent);
            }
        });

        employees.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent employeeIntent = new Intent().setClass(OptionsAdministrator.this, MainActivity_Administrator.class);
                startActivity(employeeIntent);
            }
        });

        report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent reportIntent = new Intent().setClass(OptionsAdministrator.this, GraphsReport_SoldProduct.class);
                startActivity(reportIntent);
            }
        });
    }
}
