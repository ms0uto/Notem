/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.DialogPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import repository.SQLiteManager;
import repository.services.UserServiceImpl;

/**
 * FXML Controller class
 *
 * @author EXTmsouto
 */
public class SignUpViewController implements Initializable {

    @FXML
    private JFXButton exitButton;
    @FXML
    private JFXTextField username;
    @FXML
    private JFXTextField password;
    @FXML
    private JFXTextField email;

    private ResultSet resultset;

    private UserServiceImpl userService;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @FXML
    private void signIn(ActionEvent event) throws IOException, SQLException {
        if (!userExistsCheck()) {
            if (!emailExistsCheck()) {
                userService.insertUser(username.getText(), password.getText(), email.getText());
                showConfirmationDialog();
                //Cambiamos de vista de nuevo tras crear el nuevo usuario.
                closeStage();

            }
            showEmailErrorDialog();

        } else {
            showUserErrorDialog();
        }
    }

    @FXML
    void exit(ActionEvent event) {
        closeStage();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        userService = new UserServiceImpl();
    }

    private boolean userExistsCheck() throws SQLException {

        Connection con = SQLiteManager.getInstance().getConnection();

        String userExistsQuery = "SELECT id FROM user WHERE username ='" + username.getText() + "'";// "' AND email='" + email.getText() + "'";
        resultset = con.createStatement().executeQuery(userExistsQuery);
        //Comprobamos si la query genera un resultado.
        if (resultset.next()) {
            resultset.close();
            return true;
        }
        return false;

    }

    private boolean emailExistsCheck() throws SQLException {

        Connection con = SQLiteManager.getInstance().getConnection();

        String emailExistsQuery = "SELECT id FROM user WHERE email ='" + email.getText() + "'";
        resultset = con.createStatement().executeQuery(emailExistsQuery);
        //Comprobamos si la query genera un resultado.
        if (resultset.next()) {
            resultset.close();
            return true;
        }
        return false;

    }

    public void closeStage() {
        Stage stage = (Stage) exitButton.getScene().getWindow(); //Devolvemos la Stage desde el botón ;)
        stage.close();
    }

    private void showConfirmationDialog() {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(null);
        alert.initStyle(StageStyle.UNDECORATED);
        alert.setHeaderText("New user registered");

        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add(getClass().getResource("/gui/sources/styles/Dialog.css").toExternalForm());

        alert.showAndWait();
    }

    private void showUserErrorDialog() {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(null);
        alert.initStyle(StageStyle.UNDECORATED);
        alert.setHeaderText("User already exists");

        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add(getClass().getResource("/gui/sources/styles/Dialog.css").toExternalForm());

        alert.showAndWait();
    }

    private void showEmailErrorDialog() {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(null);
        alert.initStyle(StageStyle.UNDECORATED);
        alert.setHeaderText("Email already exists");

        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add(getClass().getResource("/gui/sources/styles/Dialog.css").toExternalForm());

        alert.showAndWait();
    }

}
