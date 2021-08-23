package view;

import business.UserBusiness;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.sql.SQLIntegrityConstraintViolationException;

public class SignUpController {
    @FXML
    private Button signupButton;
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;


    @FXML
    private Label usernameErrorLabel;
    @FXML
    private Label passwordErrorLabel;

    private UserBusiness userBusiness = new UserBusiness();

    private SceneController sceneController = SceneController.getInstance();

    @FXML
    protected void signupButtonOnClick(ActionEvent actionEvent) {
        String username = usernameField.getText();
        String password = passwordField.getText();

        try {
            userBusiness.create(username, password);
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Successfully registred!", ButtonType.OK);
            alert.setHeaderText("");
            alert.showAndWait();
        } catch (IllegalArgumentException ex) {
            if (ex.getMessage().equals("username too short")) {
                this.usernameErrorLabel.setText("Username must be longer than 7 characters");
            } else if (ex.getMessage().equals("password too short")) {
                this.passwordErrorLabel.setText("Password must be longer than 7 characters");
            }
        } catch (SQLIntegrityConstraintViolationException ex) {
            if(ex.getMessage().equals("Primary key constraint violated")) {
                this.usernameErrorLabel.setText("Username already exists");
            }
        } finally {
            this.resetInputFields();
        }
    }

    @FXML
    protected void backButtonOnClick(ActionEvent actionEvent) throws IOException {
        this.sceneController.switchToLogin(actionEvent);
    }

    @FXML
    protected void usernameFieldMouseClicked(MouseEvent mouseEvent) {
        this.resetErrorLabels();
    }

    @FXML
    protected void passwordFieldMouseClicked(MouseEvent mouseEvent) {
        this.resetErrorLabels();
    }

    private void resetErrorLabels() {
        this.usernameErrorLabel.setText("");
        this.passwordErrorLabel.setText("");
    }

    private void resetInputFields() {
        this.usernameField.setText("");
        this.passwordField.setText("");
    }
}
