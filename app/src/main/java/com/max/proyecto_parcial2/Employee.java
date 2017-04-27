package com.max.proyecto_parcial2;

import java.io.Serializable;

public class Employee implements Serializable{

    private String _pname;
    private String _pid;
    private String _plastname;
    private String _pphone;
    private String _pmail;
    private String _ppassword;
    private String _prole;
    private String _pusername;
    private String _ppayment;

    public String getName() {return _pname;}
    public void setName(String Name) {
        this._pname = Name;
    }

    public String getid() {return _pid;}
    public void setid(String id) {this._pid = id;}

    public String getLastName() {return _plastname;}
    public void setLastName(String LastName) {this._plastname = LastName;}

    public String getPhone() {return _pphone;}
    public void setPhone(String Phone) {this._pphone = Phone;}

    public String getMail(){return _pmail;}
    public void setMail(String Mail) {this._pmail = Mail;}

    public String getPassword(){return _ppassword;}
    public void setPassword(String Password){this._ppassword = Password;}

    public String getRole(){return _prole;}
    public void setRole(String Role){this._prole = Role;}

    public String getUsername(){return _pusername;}
    public void setUsername(String Username){this._pusername = Username;}

    public String getPayment(){return _ppayment;}
    public void setPayment(String Payment){this._ppayment = Payment;}

}
