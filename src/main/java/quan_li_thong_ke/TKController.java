package quan_li_thong_ke;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import mySQLConnect.SQLOrder;

import java.net.URL;
import java.time.LocalDate;
import java.util.Map;
import java.util.ResourceBundle;

public class TKController implements Initializable {
    @FXML
    private ComboBox<String> loaiBD;
    private final ObservableList<String> l = FXCollections.observableArrayList(
            "Biểu Đồ Cột", "Biểu Đồ Tròn"
    );
    @FXML
    private BarChart<String,Double> barChar;
    @FXML
    private PieChart pieChart;
    @FXML
    private DatePicker date;
    @FXML
    private Label dt;
    @FXML
    private Label von;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        barChar.setVisible(true);
        pieChart.setVisible(false);
        loaiBD.setItems(l);
        loaiBD.getSelectionModel().select("Biểu Đồ Cột");
        XYChart.Series<String, Double> data = new XYChart.Series<>();
        data.setName("Doanh thu");

        Map<Integer,Double> list = SQLOrder.getRevenue(2023);

        for(int i = 1;i <= 12;i++){
            data.getData().add(new XYChart.Data<>(String.valueOf(i),list.get(i)));
        }
        barChar.getData().add(data);

        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
                new PieChart.Data("Đồ gia dụng 20%", 20),
                new PieChart.Data("Bánh kẹo 35%", 35),
                new PieChart.Data("Quần áo 45%", 45)

        );
        pieChart.setTitle("Sản phẩm");
        pieChart.setData(pieChartData);
        
    }
    public void TKHandle(){
        String select = loaiBD.getValue();
        LocalDate d = date.getValue();
        if(d != null){
            if(select.equals("Biểu Đồ Cột")){
                barChar.setVisible(true);
                pieChart.setVisible(false);
                XYChart.Series<String, Double> data = new XYChart.Series<>();
                data.setName("Doanh thu");
                int year = d.getYear();
                Map<Integer,Double> list = SQLOrder.getRevenue(year);

                for(int i = 1;i <= 12;i++){
                    data.getData().add(new XYChart.Data<>(String.valueOf(i),list.get(i)));
                }
                barChar.getData().clear();
                barChar.getData().add(data);

                int doanh_thu = SQLOrder.getAmount();
                dt.setText(String.valueOf(doanh_thu));
                int v = doanh_thu / 11;
                von.setText(String.valueOf((Integer)v));
            }
            else{
                pieChart.setVisible(true);
                barChar.setVisible(false);
            }
        }else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.showAndWait();
        }
    }
}
