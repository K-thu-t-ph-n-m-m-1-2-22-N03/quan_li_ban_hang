package quan_li_san_pham;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class AddProductController {
    @FXML
    private ImageView pImage;
    @FXML
    private TextField addName;
    @FXML
    private TextField addPrice;
    @FXML
    private TextField addQuantity;
    @FXML
    private TextField addNote;
    @FXML
    private DatePicker addDate;
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
    public void confirmHandle(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("ERROR");
        Product product = new Product();
        if(pImage.getImage().getUrl() == null){
            alert.setHeaderText("Chưa nhập ảnh");
            alert.showAndWait();
            return;
        }else
            product.setProductImage(pImage.getImage().getUrl());

        if(addName.getText().equals("")) {
            alert.setHeaderText("Chưa nhập tên sản phẩm");
            alert.showAndWait();
            return;
        }else
            product.setProductName(addName.getText());

        if(addPrice.getText().equals("")){
            alert.setHeaderText("Chưa nhập giá");
            alert.showAndWait();
            return;
        }else {
            try{
               int price = Integer.parseInt(addPrice.getText());
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
        if(addQuantity.getText().equals("")){
            alert.setHeaderText("Chưa nhập giá");
            alert.showAndWait();
            return;
        }else {
            try{
                int quantity = Integer.parseInt(addQuantity.getText());
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
        product.setProductNote(addNote.getText());
        if(addDate.getValue() == null){
            alert.setHeaderText("Chưa chọn hạn");
            alert.showAndWait();
            return;
        }else{
            LocalDate date = addDate.getValue();
            product.setDate(date);
        }

        LocalDateTime now = LocalDateTime.now(ZoneId.of("Asia/Ho_Chi_Minh"));
        String formattedDate = now.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        String product_id = "PID" + formattedDate;
        product.setProductID(product_id);

        SQLProduct.insertProduct(product);

        Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
        alert1.setTitle("Add");
        alert1.setHeaderText("Thêm sản phẩm thành công");
        alert1.showAndWait();
        ProductController.instance.refresh();
        HomeController.instance.displayList();
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.close();
    }
    public void cancelHandle(ActionEvent event){
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.close();
    }
}
