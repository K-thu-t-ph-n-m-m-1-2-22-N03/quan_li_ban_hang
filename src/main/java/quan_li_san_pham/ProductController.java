package quan_li_san_pham;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Product;
import mySQLConnect.SQLProduct;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class ProductController implements Initializable {
    public static ProductController instance;
    @FXML
    private TextField productSearchField;
    /////////////////////////////////////////////////////////
    @FXML
    private TableView<Product> pTable;
    @FXML
    private TableColumn<Product,String> productID;
    @FXML
    private TableColumn<Product,String> productName;
    @FXML
    private TableColumn<Product,Integer> productPrice;
    @FXML
    private TableColumn<Product,Integer> productQuantity;
    @FXML
    private TableColumn<Product,String> productNote;
    private ObservableList<Product> list = SQLProduct.getDatabase();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        instance = this;
        pTable.setItems(list);
        productID.setCellValueFactory(new PropertyValueFactory<>("productID"));
        productName.setCellValueFactory(new PropertyValueFactory<>("productName"));
        productPrice.setCellValueFactory(new PropertyValueFactory<>("productPrice"));
        productQuantity.setCellValueFactory(new PropertyValueFactory<>("productQuantity"));
        productNote.setCellValueFactory(new PropertyValueFactory<>("productNote"));

    }
    public void addHandle() throws Exception{
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("addProduct.fxml")));
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Add Product");
        stage.show();
    }
    public Product select;
    public void updateHandle() throws Exception{
        select = pTable.getSelectionModel().getSelectedItem();
        if(select != null){
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("updateProduct.fxml")));
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Update Product");
            stage.show();
        }
    }
    public void deleteHandle(){
        select = pTable.getSelectionModel().getSelectedItem();
        if(select != null){
            pTable.getItems().remove(select);
            SQLProduct.deteteProduct(select);
            pTable.refresh();
        }
    }
    public void searchHandle(){
        String key = productSearchField.getText().toLowerCase();
        ObservableList<Product> tmp = list.filtered(product ->
                product.getProductName().toLowerCase().contains(key)
        );
        pTable.setItems(tmp);
        pTable.refresh();
    }
    public void refresh(){
        list = SQLProduct.getDatabase();
        pTable.setItems(list);
        pTable.refresh();
    }
}
