package com.max.proyecto_parcial2;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class parserJSON_employee {

    public static ArrayList<Employee> _array_employee = new ArrayList<>();

    public static ArrayList<Employee> parseaArreglo(JSONArray arr) {

        JSONObject obj;
        Employee employee;
        _array_employee.clear();

        try {
            for(int i = 0;i<arr.length();i++) {

                obj = arr.getJSONObject(i);
                employee = new Employee();

                employee.setid(obj.getString("id"));
                employee.setName(obj.getString("nombre"));
                employee.setLastName(obj.getString("apellido"));
                employee.setPhone(obj.getString("telefono"));
                employee.setMail(obj.getString("mail"));
                employee.setPassword(obj.getString("password"));
                employee.setRole(obj.getString("rol"));
                employee.setUsername(obj.getString("username"));
                employee.setPayment(obj.getString("sueldo"));

                _array_employee.add(employee);
            }
            return _array_employee;

        } catch (JSONException e1) {
            e1.printStackTrace();
            return null;
        }
    }
}

