package login;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import mySQLConnect.SQLAccount;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    @FXML
    private Button QR;
    @FXML
    private Button T;
    @FXML
    private AnchorPane loginT;
    @FXML
    private AnchorPane loginQR;
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
          loginT.setVisible(true);
          loginQR.setVisible(false);
    }

    public void loginHandle(ActionEvent event) throws IOException {
        if(SQLAccount.checkAccount(usernameField.getText(), passwordField.getText())){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Login");
            alert.setHeaderText("Đăng Nhập thành công");
            if(alert.showAndWait().get() == ButtonType.OK){
                Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                stage.close();
                Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/quan_li_ban_hang/Form.fxml")));
                stage.setScene(new Scene(root));
                stage.show();
            }
        }else {
            Alert alert = new Alert(Alert.AlertType.NONE);
            ButtonType buttonType = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
            alert.getButtonTypes().add(buttonType);
            alert.setTitle("Login");
            alert.setHeaderText("Thông tin username hoặc password không chính xác");
            alert.setContentText("Hãy nhập lại thông tin chính xác");
            alert.showAndWait();
        }
    }
    public void switchLoginT(){
        loginT.setVisible(true);
        loginQR.setVisible(false);
        QR.setStyle("-fx-background-color: transparent");
        T.setStyle("-fx-background-color: #189bab");
    }

    public void switchLoginQR(){
        loginT.setVisible(false);
        loginQR.setVisible(true);
        T.setStyle("-fx-background-color: transparent");
        QR.setStyle("-fx-background-color: #189bab");
    }
}
