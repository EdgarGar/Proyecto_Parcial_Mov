package com.max.proyecto_parcial2;

/**
 * Created by jacke310 on 7/05/17.
 */

public class Client {
    //set the atributes
    private String id;
    private String nombre;
    private String apellido;
    private String mail;
    private String direccion;

    //constructor
    public Client(String _id, String _nombre, String _apellido, String _mail, String _direccion){
        id = _id;
        nombre = _nombre;
        apellido = _apellido;
        mail = _mail;
        direccion = _direccion;
    }

    public Client(){};

    //getters
    public String getIdClient(){
        return id;
    }
    public String getNombreClient(){
        return nombre;
    }
    public String getApellidoClient(){
        return apellido;
    }
    public String getMailClient(){
        return mail;
    }
    public String getDireccionClient(){
        return direccion;
    }
}
