package com.max.proyecto_parcial2;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class LoginActivity extends AppCompatActivity {

    private EditText user, password;
    private ProgressDialog pDialog;
    private String jsonResponse, jsonmessage, jsonRole, userID;

    private static String TAG = LoginActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        user = (EditText) findViewById(R.id.user_text);
        password = (EditText) findViewById(R.id.password_text);
        password.setTransformationMethod(new PasswordTransformationMethod());

        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(false);

        Button btn_access = (Button) findViewById(R.id.login_button);
        Button btn_reg = (Button) findViewById(R.id.register_button);

        btn_access.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeJSONObjectRequest();
            }
        });

        btn_reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent createUser = new Intent().setClass(LoginActivity.this, InsertClientEmployee.class);
                startActivity(createUser);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        user.getText().clear();
        password.getText().clear();
    }

    private void makeJSONObjectRequest(){
        showpDialog();

        String url = "http://ubiquitous.csf.itesm.mx/~pddm-1021817/content/parcial2/Proyecto_parcial_2/Servicios/login.php";
        url += "?user=" + user.getText().toString() + "&password=" + password.getText().toString();

        final JsonObjectRequest jsonrequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d ("response", response.toString());
                try {
                    jsonResponse = response.getString("Code");
                    jsonmessage = response.getString("Message");
                    if (response.has("rol")) {
                        jsonRole = response.getString("rol");
                    } else {
                        jsonRole = "cliente";
                    }
                    userID = response.getString("id");

                    Toast.makeText(getApplicationContext(), jsonmessage, Toast.LENGTH_LONG).show();
                    UserVariables.getInstance().setEmployeeID(userID);

                    if((jsonResponse.compareTo("01") == 0) && (jsonRole.compareTo("1") == 0)){
                        UserVariables.getInstance().setClientUser(false);
                        UserVariables.getInstance().setEmployeeID(userID);
                        UserVariables.getInstance().setStatus("1");
                        Intent mainIntent = new Intent().setClass(LoginActivity.this, OptionsAdministrator.class);
                        startActivity(mainIntent);
                    }
                    if((jsonResponse.compareTo("01") == 0) && (jsonRole.compareTo("2") == 0)){
                        UserVariables.getInstance().setClientUser(false);
                        UserVariables.getInstance().setEmployeeID(userID);
                        UserVariables.getInstance().setStatus("0");
                        Intent mainIntent = new Intent().setClass(LoginActivity.this, ChooseClient.class);
                        startActivity(mainIntent);
                    }
                    // If no role is set, then a user is connecting
                    if(jsonResponse.compareTo("01") == 0 && jsonRole.compareTo("cliente") == 0) {
                        UserVariables.getInstance().setClientUser(true);
                        UserVariables.getInstance().setEmployeeID("1");
                        UserVariables.getInstance().setClientID(userID);
                        UserVariables.getInstance().setStatus("0");
                        Intent mainIntent = new Intent().setClass(LoginActivity.this, MainActivity_Client.class);
                        startActivity(mainIntent);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }
                hidepDialog();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_SHORT).show();
                hidepDialog();
            }
        });

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(jsonrequest);
    }

    private void showpDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hidepDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }
}
