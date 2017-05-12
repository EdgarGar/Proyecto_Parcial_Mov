package com.max.proyecto_parcial2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Cart extends AppCompatActivity {
    private RecyclerView lv;
    RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        UserVariables.getInstance().checkEmpty();

        queue = Volley.newRequestQueue(getApplicationContext());

        lv = (RecyclerView) findViewById(R.id.cart_recycler_view);
        lv.setLayoutManager(new LinearLayoutManager(this));

        Cardview_cartitem arrayAdapter = new Cardview_cartitem(getApplicationContext(), UserVariables.getInstance().cart);
        lv.setAdapter(arrayAdapter);

        arrayAdapter.notifyDataSetChanged();

        Button buy = (Button) findViewById(R.id.btn_client_buyall);

        buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserVariables.getInstance().checkEmpty();
                if(!UserVariables.getInstance().cart.isEmpty())
                    buyCart();
                else
                    Toast.makeText(getApplicationContext(), "No items in cart", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void buyCart() {
        UserVariables.getInstance().setEmployeeID("1");

        String url = "http://ubiquitous.csf.itesm.mx/~pddm-1021817/content/parcial2/Proyecto_parcial_2/Servicios/pedidos.c.php" +
                "?ide=" + UserVariables.getInstance().getEmployeeID() +
                "&idc=" + UserVariables.getInstance().getClientID() +
                "&status=1";
        Log.d("Req: ", url);

        JsonObjectRequest creaPedido = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    if (response.getString("Code").equals("01")) {
                        Log.d("Good Response 1", response.toString() + " " + response.getString("Code"));
                        UserVariables.getInstance().setOrderID(response.getString("IDPedido"));
                        buyProducts();
                    } else {
                        Toast.makeText(getApplicationContext(), "Could not create the order. Check internet connection or contact admin.", Toast.LENGTH_LONG).show();
                        //orderID = null;
                        //UserVariables.getInstance().setOrderID(orderID);
                        Log.d("Bad Response 1", response.toString() + " " + response.getString("Code"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(creaPedido);
    }

    int count;
    private void buyProducts() {
        count = 0;
        for (int i = 0; i < UserVariables.getInstance().cart.size(); i++) {
            String value = UserVariables.getInstance().cart.get(i).product.getid();
            String counter = String.valueOf(UserVariables.getInstance().cart.get(i).quantity);

            //--crear pedido_producto con counter como cantidad de producto,
            String url = "http://ubiquitous.csf.itesm.mx/~pddm-1021817/content/parcial2/Proyecto_parcial_2/Servicios/productos_pedidos.c.php" +
                    "?idpr=" + UserVariables.getInstance().getOrderID() +
                    "&idpe=" + value +
                    "&cant=" + counter +
                    "&ide=" + UserVariables.getInstance().getEmployeeID();
            Log.d("Request 2", url);

            JsonObjectRequest jsonrequest = new JsonObjectRequest(Request.Method.GET,url, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        if (response.getString("Code").equals("01")) {
                            count++;
                            Log.d("Good Response 2", response.toString() + " " + response.getString("Code"));
                        } else {
                            Log.d("Bad Response 2", response.toString() + " " + response.getString("Code"));
                            Toast.makeText(getApplicationContext(), "One or all items could not be bought.", Toast.LENGTH_LONG).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(getApplicationContext(), "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    VolleyLog.d("Error: " + error.getMessage());
                    Toast.makeText(getApplicationContext(),
                            error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
            queue.add(jsonrequest);
        }
        if (count == UserVariables.getInstance().cart.size()-1) {
            Toast.makeText(getApplicationContext(), "All items bought!", Toast.LENGTH_LONG).show();
            UserVariables.getInstance().cart.clear();
        }
    }
}
