package quan_li_san_pham;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.Product;
import mySQLConnect.SQLProduct;
import quan_li_home.HomeController;

import java.io.File;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class UpdateProductController implements Initializable {
    @FXML
    private ImageView pImage;
    @FXML
    private TextField updateName;
    @FXML
    private TextField updatePrice;
    @FXML
    private TextField updateQuantity;
    @FXML
    private TextField updateNote;
    @FXML
    private DatePicker updateDate;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Product select = ProductController.instance.select;
        Image image = new Image(select.getProductImage());
        pImage.setImage(image);
        updateName.setText(select.getProductName());
        updatePrice.setText(String.valueOf(select.getProductPrice()));
        updateQuantity.setText(String.valueOf(select.getProductQuantity()));
        updateNote.setText(select.getProductNote());
        updateDate.setValue(select.getDate());
    }
    public void uploadHandle(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Chọn ảnh");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter( "Hình Ảnh","*.jpg", "*.jpeg", "*.png")
        );

        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            String imagePath = selectedFile.getAbsolutePath();
            Image image = new Image(imagePath);
            pImage.setImage(image);
        }
    }
    public void confirmHandle(ActionEvent event){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("ERROR");
        Product product = ProductController.instance.select;
        if(pImage.getImage().getUrl() == null){
            alert.setHeaderText("Chưa nhập ảnh");
            alert.showAndWait();
            return;
        }else
            product.setProductImage(pImage.getImage().getUrl());

        if(updateName.getText().equals("")) {
            alert.setHeaderText("Chưa nhập tên sản phẩm");
            alert.showAndWait();
            return;
        }else
            product.setProductName(updateName.getText());

        if(updatePrice.getText().equals("")){
            alert.setHeaderText("Chưa nhập giá");
            alert.showAndWait();
            return;
        }else {
            try{
                int price = Integer.parseInt(updatePrice.getText());
                if(price < 0){
                    alert.setHeaderText("Giá nhỏ hơn 0");
                    alert.showAndWait();
                    return;
                }else {
                    product.setProductPrice(price);
                }
            }catch (NumberFormatException e){
                alert.setHeaderText("Giá không hợp lệ");
                alert.showAndWait();
                return;
            }
        }
        if(updateQuantity.getText().equals("")){
            alert.setHeaderText("Chưa nhập giá");
            alert.showAndWait();
            return;
        }else {
            try{
                int quantity = Integer.parseInt(updateQuantity.getText());
                if(quantity < 0){
                    alert.setHeaderText("Số lượng nhỏ hơn 0");
                    alert.showAndWait();
                    return;
                }else {
                    product.setProductQuantity(quantity);
                }
            }catch (NumberFormatException e){
                alert.setHeaderText("Số lượng không hợp lệ");
                alert.showAndWait();
                return;
            }
        }
        product.setProductNote(updateNote.getText());
        if(updateDate.getValue() == null){
            alert.setHeaderText("Chưa chọn hạn");
            alert.showAndWait();
            return;
        }else{
            LocalDate date = updateDate.getValue();
            product.setDate(date);
        }
        SQLProduct.UpdateProduct(product);
        ProductController.instance.refresh();
        HomeController.instance.displayList();
        Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
        alert1.setTitle("Update");
        alert1.setHeaderText("Sửa sản phẩm thành công");
        alert1.showAndWait();
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.close();
    }
    public void cancelHandle(ActionEvent event){
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.close();
    }
}
