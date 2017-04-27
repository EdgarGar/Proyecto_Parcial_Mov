package com.max.proyecto_parcial2;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class item_Employee extends AppCompatActivity {

    private String value = "";
    private TextView e_id, e_name, e_lastname, e_user, e_rol, e_passsword, e_phone, e_mail, e_salary;
    private int counter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item__employee);

        Bundle myIntent = getIntent().getExtras();

        if(myIntent != null) {value = myIntent.getString("idEmployee");}

        e_name = (TextView) findViewById(R.id.name_employe);
        e_lastname = (TextView) findViewById(R.id.lastname_employe);
        e_phone = (TextView) findViewById(R.id.phone_employe);
        e_mail = (TextView) findViewById(R.id.mail_employe);
        e_passsword = (TextView) findViewById(R.id.password_employe);
        e_mail = (TextView) findViewById(R.id.mail_employe);
        e_rol = (TextView) findViewById(R.id.role_employe);
        e_user = (TextView) findViewById(R.id.username_employe);
        e_salary = (TextView) findViewById(R.id.salary);


        String url = "http://ubiquitous.csf.itesm.mx/~pddm-1021817/content/parcial2/Proyecto_parcial_2/Servicios/empleado.r.php?id=" + value;

        JsonArrayRequest jsonrequest = new JsonArrayRequest(Request.Method.GET,url, null, new Response.Listener<JSONArray>(){
            @Override
            public void onResponse(JSONArray response) {
                try {
                    JSONObject employee = (JSONObject) response.get(0);

                    e_id.setText("id" + employee.getInt("id"));
                    e_name.setText("Name:" + employee.getString("nombre"));
                    e_lastname.setText("Price:"  + employee.getString("lapellido"));
                    e_phone.setText("Phone:" + employee.getInt("telefono"));
                    e_mail.setText("e-mail:" + employee.getString("mail"));
                    e_passsword.setText("Role:" + employee.getInt("password"));
                    e_user.setText("User:" + employee.getString("username"));
                    e_rol.setText("Role:" + employee.getInt("rol"));
                    e_user.setText("Role" + employee.getString("username"));

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("ERROR VOLLEY", "Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }) ;

        /////////////////TO DO HERE//////////////////
        //--Añadir textviews suficientes para cada campo del Empleado (nombre, apellido, usuario, rol, password, etc).
        //--Que cada textview despliegue informacion de acuerdo al campo.
        //// (Se puede obtener la informacion del Empleado directamente desde el servicio empleado.r.php?id=) Vease como se hace en item_Product.java
        //--Desplegar foto de Empleado. Vease como se hizo en item_Product.java con Bitmap.
        //--Boton de regresar a Pantalla anterior.
        ////////////////////////////////////////////


    }
}
