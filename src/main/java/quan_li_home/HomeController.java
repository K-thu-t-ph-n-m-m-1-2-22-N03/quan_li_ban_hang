package quan_li_home;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Product;
import mySQLConnect.SQLOrder;
import mySQLConnect.SQLProduct;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class HomeController implements Initializable {
    public static HomeController instance;
    @FXML
    private GridPane gridPane;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
            instance = this;
            displayList();
        }

    public void displayList(){
        gridPane.getChildren().clear();
        int columnCount = 3;
        int rowIndex = 0;
        int columnIndex = 0;
        ObservableList<Product> list = SQLProduct.getDatabase();
        gridPane.setVgap(20);
        gridPane.setHgap(40);
        gridPane.setPadding(new Insets(20));
        for (Product product : list) {
            HBox productBox = new HBox();
            productBox.setStyle("-fx-background-radius: 5px;-fx-background-color: #fff;" +
                    "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.5),5,0,0,0);");
            productBox.setAlignment(Pos.CENTER_LEFT);
            ImageView productImage = new ImageView(product.getProductImage());
            productImage.setFitWidth(100);
            productImage.setFitHeight(100);

            Label productPrice = new Label("Price: " + product.getProductPrice() +"Đ");
            VBox productDetails1 = new VBox();
            productDetails1.setSpacing(20);
            VBox.setVgrow(productImage, Priority.ALWAYS);
            VBox.setVgrow(productPrice,Priority.SOMETIMES);
            productDetails1.getChildren().addAll(productImage,productPrice);

            VBox productDetails2 = new VBox();
            Label productName = new Label(product.getProductName());
            TextField quantity = new TextField();
            Button orderButton = new Button("Thêm hàng");
            orderButton.setOnAction(event -> {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("ERROR");
                if(quantity.getText().equals("")){
                    alert.setHeaderText("Chưa nhập số lượng");
                    alert.showAndWait();
                }else{
                    try{
                        int quantityValue = Integer.parseInt(quantity.getText());
                        if(quantityValue <= 0){
                            alert.setHeaderText("Số lượng phải lớn > 0");
                            alert.showAndWait();
                        }else {
                            Product p = product.Copy();
                            p.setProductQuantity(quantityValue);
                            int price =  p.getProductPrice();
                            p.setProductPrice(price);
                            SQLOrder.insertProductOrder(p);
                            Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                            alert1.setTitle("Add");
                            alert1.setHeaderText("Thêm vào giỏ hàng thành công");
                            alert1.showAndWait();
                        }
                    }catch (NumberFormatException e){
                        alert.setHeaderText("Số lượng nhập không hợp lệ");
                        alert.showAndWait();
                    }
                }

            });
            productDetails2.getChildren().addAll(productName,quantity, orderButton);
            productDetails2.setSpacing(10);

            productDetails2.setPrefWidth(100);
            productDetails2.setPrefHeight(100);
            productDetails2.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

            productBox.getChildren().addAll(productDetails1, productDetails2);
            productBox.setSpacing(30);
            gridPane.add(productBox, columnIndex, rowIndex);
            columnIndex++;

            if (columnIndex >= columnCount) {
                columnIndex = 0;
                rowIndex++;
            }
        }
    }
    public void switchOrder()throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("../quan_li_don_hang/OrderForm.fxml"));
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setTitle("Order");
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    private TextField searchField;

    public void refresh(){
        displayList();
        searchField.setText("");
        searchField.setPromptText("Sản phẩm...");
    }
    public void searchHandle(){
        gridPane.getChildren().clear();
        String key = searchField.getText().toLowerCase();
        ObservableList<Product> list = SQLProduct.getDatabase();
        ObservableList<Product> tmp = list.filtered(product ->
                product.getProductName().toLowerCase().contains(key)
        );

        int columnCount = 3;
        int rowIndex = 0;
        int columnIndex = 0;
        gridPane.setVgap(20);
        gridPane.setHgap(40);
        gridPane.setPadding(new Insets(20));
        for (Product product : tmp) {
            HBox productBox = new HBox();
            productBox.setStyle("-fx-background-radius: 5px;-fx-background-color: #fff;" +
                    "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.5),5,0,0,0);");
            productBox.setAlignment(Pos.CENTER_LEFT);
            ImageView productImage = new ImageView(product.getProductImage());
            productImage.setFitWidth(100);
            productImage.setFitHeight(100);

            Label productPrice = new Label("Price: " + product.getProductPrice() +"Đ");
            VBox productDetails1 = new VBox();
            productDetails1.setSpacing(20);
            VBox.setVgrow(productImage, Priority.ALWAYS);
            VBox.setVgrow(productPrice,Priority.SOMETIMES);
            productDetails1.getChildren().addAll(productImage,productPrice);

            VBox productDetails2 = new VBox();
            Label productName = new Label(product.getProductName());
            TextField quantity = new TextField();
            Button orderButton = new Button("Thêm hàng");
            orderButton.setOnAction(event -> {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("ERROR");
                if(quantity.getText().equals("")){
                    alert.setHeaderText("Chưa nhập số lượng");
                    alert.showAndWait();
                }else{
                    try{
                        int quantityValue = Integer.parseInt(quantity.getText());
                        if(quantityValue <= 0){
                            alert.setHeaderText("Số lượng phải lớn > 0");
                            alert.showAndWait();
                        }else {
                            Product p = product.Copy();
                            p.setProductQuantity(quantityValue);
                            int price =  p.getProductPrice();
                            p.setProductPrice(price);
                            SQLOrder.insertProductOrder(p);
                            Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                            alert1.setTitle("Add");
                            alert1.setHeaderText("Thêm vào giỏ hàng thành công");
                            alert1.showAndWait();
                        }
                    }catch (NumberFormatException e){
                        alert.setHeaderText("Số lượng nhập không hợp lệ");
                        alert.showAndWait();
                    }
                }

            });
            productDetails2.getChildren().addAll(productName,quantity, orderButton);
            productDetails2.setSpacing(10);

            productDetails2.setPrefWidth(100);
            productDetails2.setPrefHeight(100);
            productDetails2.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

            productBox.getChildren().addAll(productDetails1, productDetails2);
            productBox.setSpacing(30);
            gridPane.add(productBox, columnIndex, rowIndex);
            columnIndex++;

            if (columnIndex >= columnCount) {
                columnIndex = 0;
                rowIndex++;
            }
        }
    }
}
