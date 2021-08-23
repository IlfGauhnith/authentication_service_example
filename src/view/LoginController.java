package view;

import business.UserBusiness;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import java.io.IOException;


public class LoginController {

    @FXML
    private Label passwordErrorLabel;
    @FXML
    private Label usernameErrorLabel;
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Button enterButton;
    @FXML
    private Button signUpButton;

    private UserBusiness userBusiness = new UserBusiness();

    private SceneController sceneController = SceneController.getInstance();

    public LoginController(){ }

    @FXML
    protected void enterButtonOnClick(ActionEvent actionEvent) {
        String username = usernameField.getText();
        String password = passwordField.getText();

        try {
            boolean logon = this.userBusiness.login(username, password);
            if (!logon) {
                ButtonType closeAlertBttn = new ButtonType("Close");
                Alert alert = new Alert(Alert.AlertType.ERROR, "Incorrect username or password", closeAlertBttn);
                alert.setHeaderText("");
                alert.showAndWait();
            } else {
                ButtonType closeAlertBttn = new ButtonType("Close");
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Successfully logged in", closeAlertBttn);
                alert.setHeaderText("");
                alert.showAndWait();
            }
        } catch (IllegalArgumentException ex) {////
            if (ex.getMessage().equals("username too short")) {
                this.usernameErrorLabel.setText("Username must be longer than 7 characters");
            } else if (ex.getMessage().equals("password too short")) {
                this.passwordErrorLabel.setText("Password must be longer than 7 characters");
            }
        } finally {
            this.resetFields();
        }
    }

    @FXML
    protected void signUpButtonOnClick(ActionEvent actionEvent) throws IOException {
        this.sceneController.switchToSignUp(actionEvent);
    }

    private void resetFields() {
        this.passwordField.setText("");
        this.usernameField.setText("");
    }

    private void resetErrorLabels() {
        this.passwordErrorLabel.setText("");
        this.usernameErrorLabel.setText("");
    }

    @FXML
    protected void usernameFieldOnClick(MouseEvent mouseEvent) {
        this.resetErrorLabels();
    }

    @FXML
    protected void passwordFieldOnClick(MouseEvent mouseEvent) {
        this.resetErrorLabels();
    }
}
