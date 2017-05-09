package com.max.proyecto_parcial2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class OptionsProduct extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options_product);

        Button listṔroducts = (Button) findViewById(R.id.btn_list_products);
        Button insertProducts = (Button) findViewById(R.id.btn_insert_product);

        listṔroducts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent listIntent = new Intent().setClass(OptionsProduct.this, MainActivity_Employee.class);
                startActivity(listIntent);
            }
        });

        insertProducts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent insertIntent = new Intent().setClass(OptionsProduct.this, InsertProduct.class);
                startActivity(insertIntent);
            }
        });
    }
}
