/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;
import domain.utils.Parser;
import domain.utils.UserSessionManager;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import repository.services.FeedServiceImpl;

/**
 * FXML Controller class
 *
 * @author boo
 */
public class SessionViewController implements Initializable {

    private ArrayList newsList;
    @FXML
    private JFXListView<String> listView;

    @FXML
    private JFXButton exitbutton;
    @FXML
    private JFXTextField addfeed;
    
    private FeedServiceImpl feedService;
    
    Parser parser;

    /**
     * Initializes the controller class.
     *
     * @param arg0
     */
    @FXML
    public void readInBrowser(MouseEvent arg0) {
        //String url = listView.getSelectionModel().getSelectedItem();
        //Desktop.browse(url);
    }

    @FXML
    void exitSession(ActionEvent event) throws IOException {

        Stage stage = (Stage) exitbutton.getScene().getWindow();
        AnchorPane LoginPane = FXMLLoader.load(getClass().getResource("/gui/FXML/LoginView.fxml"));
        Scene LoginScene = new Scene(LoginPane);
        stage.setScene(LoginScene);
        stage.show();

    }

    @FXML
    void addFeed(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            if (validURL()){
            parser = Parser.sharedInstance();
            parser.setURL(addfeed.getText());
            feedService.insertFeed(UserSessionManager.sharedInstance().getLoggedUserID(), parser.readFeed()); //Insertamos el feed leído con el id de usuario logueado.
            }
            
//test refactorizar..
//            Parser parser = Parser.sharedInstance();
//            parser.setURL(addfeed.getText());
//            List<FeedMessage> messages = parser.readFeed().getMessages();
//            messages.stream().forEach((fm) -> {
//                newsList.add(fm.getTitle() + fm.getDescription());
//            });
//            //Convertimos a ObservableList y la rellenamos.
//            ObservableList<String> feedList = FXCollections.observableArrayList(newsList);
//            listView.setItems(feedList);
        }
    }

    //Código a ejecutar al iniciar la vista.
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //TEST EXTRAER
        //
        //Inicializamos ArrayList
        feedService = new FeedServiceImpl();
        newsList = new ArrayList<>();
        

        //Devolvemos un parser para URL concreta.
        //parser = Parser.newInstance("http://rss.cnn.com/rss/edition_europe.rss");

        //Creamos Lista de mensajes tras parsear un feed, y llamar getMessages sobre el mismo.
        //List<FeedMessage> messages = parser.readFeed().getMessages();

        // Java 8 mechanics.
        //messages.stream().forEach((fm) -> {
        //    newsList.add(fm.getTitle() + fm.getDescription());
        //});

        //Convertimos a ObservableList.
        //ObservableList<String> feedList = FXCollections.observableArrayList(newsList);

        //Rellenamos la OList.
        //listView.setItems(feedList);

    }

    private boolean validURL() {
        return true;
    }

}
