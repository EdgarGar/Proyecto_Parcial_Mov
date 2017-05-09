package com.max.proyecto_parcial2;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by artalves07 on 2/05/17.
 */
public class UserVariables {
    private static UserVariables ourInstance = new UserVariables();
    protected static String employeeID;
    protected static String clientID;
    protected static String orderID = null;
    protected static boolean clientUser;
    public static ArrayList<CartItem> cart = new ArrayList<>();

    public Product tempProduct = null;

    public static UserVariables getInstance() {
        return ourInstance;
    }

    private UserVariables() { }

    public static boolean isClientUser() { return clientUser; }
    public static void setClientUser(boolean newValue) { clientUser = newValue; }

    public static String getEmployeeID() { return employeeID; }
    public static void setEmployeeID(String newValue) { employeeID = newValue; }

    public static String getClientID() { return clientID; }
    public static void setClientID(String newValue) { clientID = newValue; }

    public static String getOrderID() { return orderID; }
    public static void setOrderID(String newValue) { orderID = newValue; }

    public static class CartItem {
        public Product product;
        public int quantity;

        CartItem(Product p, int q) {
            product = p;
            quantity = q;
        }
    }
}
