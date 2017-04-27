package com.max.proyecto_parcial2;

import java.io.Serializable;

public class Product implements Serializable{

    private String _pname;
    private String _pid;
    private String _pimage;
    private String _pprice;
    private String _pstock;
    private String _pcategory;

    public String getName() {return _pname;}
    public void setName(String Name) {
        this._pname = Name;
    }

    public String getid() {return _pid;}
    public void setid(String id) {this._pid = id;}

    public String getImage() {return _pimage;}
    public void setImage(String Image) {this._pimage = Image;}

    public String getPrice() {return _pprice;}
    public void setPrice(String Price) {this._pprice = Price;}

    public String getStock(){return _pstock;}
    public void setStock(String Stock) {this._pstock = Stock;}

    public String getCategory(){return _pcategory;}
    public void setCategory(String Category){this._pcategory = Category;}
}
