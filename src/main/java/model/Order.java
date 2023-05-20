package model;

import java.time.LocalDate;
import java.util.ArrayList;

public class Order {
    private String orderID;
    private ArrayList<Product> listP;
    private LocalDate orderDate;
    private int total_quantity;
    private int total_amount;
    private String note;
    public Order(){}

    public Order(String orderID, ArrayList<Product> listP, LocalDate orderDate, int total_quantity, int total_amount, String note) {
        this.orderID = orderID;
        this.listP = listP;
        this.orderDate = orderDate;
        this.total_quantity = total_quantity;
        this.total_amount = total_amount;
        this.note = note;
    }

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public ArrayList<Product> getListP() {
        return listP;
    }

    public void setListP(ArrayList<Product> listP) {
        this.listP = listP;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }

    public int getTotal_quantity() {
        return total_quantity;
    }

    public void setTotal_quantity(int total_quantity) {
        this.total_quantity = total_quantity;
    }

    public int getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(int total_amount) {
        this.total_amount = total_amount;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
