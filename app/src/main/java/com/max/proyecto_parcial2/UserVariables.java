package com.max.proyecto_parcial2;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by artalves07 on 2/05/17.
 */
public class UserVariables {
    private static UserVariables ourInstance = new UserVariables();
    protected static String status;  //para saber si esta en modo administrador o empleado
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

    public static String getStatus() { return status; }
    public static void setStatus(String newValue) { status = newValue; }

    public static class CartItem {
        public Product product;
        public int quantity;

        CartItem(Product p, int q) {
            product = p;
            quantity = q;
        }

        public void increment () {
            if (quantity < Integer.parseInt(product.getStock())) {
                quantity++;
            }
        }

        public void decrement () {
            if (quantity > 0) {
                quantity--;
            }
        }
    }

    public void checkEmpty() {
        ArrayList<Integer> delPos = new ArrayList<>();

        for (int i = 0; i < cart.size(); i++) {
            if (cart.get(i).quantity == 0) {
                delPos.add(i);
            }
        }
        for (Integer item: delPos) {
            cart.remove((int) item);
        }
    }
}
