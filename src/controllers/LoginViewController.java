/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import domain.entities.User;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import repository.SQLiteManager;
import repository.services.UserServiceImpl;

/**
 *
 * @author EXTmsouto
 */
public class LoginViewController implements Initializable {

    @FXML
    private AnchorPane root;
    @FXML
    private JFXTextField username;
    @FXML
    private JFXPasswordField password;
    @FXML
    private JFXButton exitbutton;

    private ResultSet resultset;
    private UserServiceImpl userService;

    @FXML
    void exitApplication(ActionEvent event) {
        Platform.exit();
    }

    @FXML
    private void login(ActionEvent event) throws IOException, SQLException {
        if (loginCheck()) {
            //Cambiamos de vista e iniciamos session?
            AnchorPane appPane = FXMLLoader.load(getClass().getResource("/gui/FXML/SessionView.fxml"));
            root.getChildren().setAll(appPane);
        } else {
            loginError();
            
        }
        //error login.
    }

    @FXML
    private void signUp(ActionEvent event) throws IOException {
        //Abrir pantalla de registro.
        AnchorPane signUp = FXMLLoader.load(getClass().getResource("/gui/FXML/SignUpView.fxml"));
        Scene signUpScene = new Scene(signUp);
        Stage stage = new Stage();
        stage.setScene(signUpScene);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        userService = new UserServiceImpl();
    }

    private boolean loginCheck() throws SQLException {
        User user = userService.findUserByName(username.getText(), password.getText());
        if (user != null) {
            System.out.print("Session Started");
            return true;
        } else {
            System.out.print("ERROR. User not exists.");
            return false;
        }
    }

    private void loginError() {
       System.out.print("Invalid Credentials");
    }
}

