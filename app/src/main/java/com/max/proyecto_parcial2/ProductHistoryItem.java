package com.max.proyecto_parcial2;

/**
 * Created by artalves07 on 9/05/17.
 */

public class ProductHistoryItem {
    private String name;
    private int price;
    private int quantity;
    private String day = "--";
    private String month = "--";
    private String year = "----";

    String getName() {return name;}
    void setName(String n) {name = n;}

    int getPrice() {return quantity;}
    void setQuantity(int n) {quantity = n;}

    int getQuantity() {return price;}
    void setPrice(int n) {price = n;}

    String getDay() {return day;}
    void setDay(String n) {day = n;}

    String getMonth() {return month;}
    void setMonth(String n) {month = n;}

    String getYear() {return year;}
    void setYear(String n) {year = n;}
}
