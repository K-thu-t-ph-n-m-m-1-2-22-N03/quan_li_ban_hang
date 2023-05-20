package model;

import java.time.LocalDate;

public class Product {
    private String productID;
    private String productName;
    private int productPrice;
    private int productQuantity;
    private String productNote;
    private String productImage;
    private LocalDate date;
    public Product(){}

    public Product(String productID, String productName, int productPrice, int productQuantity, String productNote, String image,LocalDate date) {
        this.productID = productID;
        this.productName = productName;
        this.productPrice = productPrice;
        this.productQuantity = productQuantity;
        this.productNote = productNote;
        this.productImage = image;
        this.date = date;
    }

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(int productPrice) {
        this.productPrice = productPrice;
    }

    public int getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(int productQuantity) {
        this.productQuantity = productQuantity;
    }

    public String getProductNote() {
        return productNote;
    }

    public void setProductNote(String productNote) {
        this.productNote = productNote;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
    public Product Copy(){
        return new Product(this.productID,this.productName,this.productPrice, this.productQuantity,this.productNote, this.productImage,this.date);
    }
}
