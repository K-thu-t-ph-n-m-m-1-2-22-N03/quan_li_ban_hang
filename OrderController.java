package quan_li_don_hang;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Order;
import model.Product;
import mySQLConnect.SQLOrder;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class OrderController implements Initializable {
    public static OrderController instance;
    @FXML
    private Label total_quantity;
    @FXML
    private Label total_amount;
    @FXML
    private TextField note;
    @FXML
    private DatePicker date;
    @FXML
    private TableView<Product> oTable;
    @FXML
    private TableColumn<Product,String> ten;
    @FXML
    private TableColumn<Product,Integer> sl;
    @FXML
    private TableColumn<Product,Integer> gia;
    @FXML
    private TableColumn<Product,Integer> tong;
    @FXML
    private ComboBox<String> pv;
    private final ObservableList<String> l = FXCollections.observableArrayList(
            "Nguyễn Văn A", "Nguyễn Văn B", "Nguyễn Văn C", "Nguyễn Văn D"
    );
    private final ObservableList<Product> list = SQLOrder.getListProductOrder();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        instance = this;
        oTable.setItems(list);
        ten.setCellValueFactory(new PropertyValueFactory<>("productName"));
        sl.setCellValueFactory(new PropertyValueFactory<>("productQuantity"));
        gia.setCellValueFactory(new PropertyValueFactory<>("productPrice"));
        tong.setCellValueFactory(cellData -> {
            Product product = cellData.getValue();
            int total = product.getProductPrice() * product.getProductQuantity();
            return new SimpleIntegerProperty(total).asObject();
        });
        pv.setItems(l);
        displayOrder();
    }
    public void displayOrder(){
        int tong_tien = 0;
        int tong_sl = 0;
        for(Product product : oTable.getItems()){
            tong_tien += product.getProductPrice();
            tong_sl += product.getProductQuantity();
        }
        total_quantity.setText(String.valueOf(tong_sl));
        total_amount.setText(String.valueOf(tong_tien));
    }
    public void AddDandle(ActionEvent event){
        Order newOrder = new Order();
        LocalDateTime now = LocalDateTime.now(ZoneId.of("Asia/Ho_Chi_Minh"));
        String formattedDate = now.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        String order_id = "ORD" + formattedDate;
        newOrder.setOrderID(order_id);

        LocalDate d = date.getValue();
        if(date.getValue() == null){
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setTitle("ERROR");
            a.setHeaderText("Chưa nhập ngày đặt");
            a.showAndWait();
            return;
        }else
            newOrder.setOrderDate(d);

        newOrder.setNote(note.getText());
        newOrder.setTotal_amount(Integer.parseInt(total_amount.getText()));
        newOrder.setTotal_quantity(Integer.parseInt(total_quantity.getText()));

        SQLOrder.insertOrder(newOrder);
        for(Product product : oTable.getItems()){
            SQLOrder.deleteOrderDetail(product.getProductID());
        }
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("ADD");
        alert.setHeaderText("Thanh toán thành công");
        alert.showAndWait();
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.close();
    }

    public void deleteHandle(){
          Product select = oTable.getSelectionModel().getSelectedItem();
          if(select != null){
              SQLOrder.deleteOrderDetail(select.getProductID());
              oTable.getItems().remove(select);
              oTable.refresh();
          }
    }
}
