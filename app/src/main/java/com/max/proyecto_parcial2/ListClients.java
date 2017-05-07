package com.max.proyecto_parcial2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ListClients extends AppCompatActivity {

    private ArrayList<Client> clients = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.client_list);


        final ListView listView = (ListView) findViewById(R.id.list);

        String url = "http://ubiquitous.csf.itesm.mx/~pddm-1021817/content/parcial2/Proyecto_parcial_2/Servicios/cliente.r.php";

        JsonArrayRequest product_json_array = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        JSONObject obj = null;
                        Client client;
                        clients.clear();

                        try {
                            for (int i = 0; i < response.length(); i++) {
                                obj = response.getJSONObject(i);

                                String id = obj.getString("id");
                                String nombre = obj.getString("nombre");
                                String apellido = obj.getString("apellido");
                                String mail = obj.getString("mail");
                                String direccion = obj.getString("direccion");

                                clients.add(new Client(id, nombre, apellido, mail, direccion));


                            }
                        }catch(JSONException e)
                        {
                            e.printStackTrace();
                        }

                        ClientAdapter adapter = new ClientAdapter(getApplicationContext(),clients);

                        listView.setAdapter(adapter);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("Error en: " + error.getMessage());
            }
        });
        Volley.newRequestQueue(getApplicationContext()).add(product_json_array);

        /*clients.add(new Client("1", "hola", "como", "tas@bien.com", "tu?"));
        clients.add(new Client("2", "hola", "como", "tas@bien.com", "tu?"));
        clients.add(new Client("3", "hola", "como", "tas@bien.com", "tu?"));
        clients.add(new Client("4", "hola", "como", "tas@bien.com", "tu?"));*/

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Client client = clients.get(position);
                UserVariables.getInstance().setClientID(client.getIdClient());

                Intent employeeIntent = new Intent().setClass(ListClients.this, MainActivity_Employee.class);
                startActivity(employeeIntent);
            }
        });
    }
}
