/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;
import domain.entities.Feed;
import domain.entities.FeedMessage;
import domain.utils.Parser;
import domain.utils.UserSessionManager;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
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

    @FXML
    private JFXListView<String> listView;

    @FXML
    private Label username;

    @FXML
    private JFXButton exitbutton;
    @FXML
    private JFXTextField addfeed;

    private FeedServiceImpl feedService;

    private List<FeedMessage> allFeedsMessages;

    Parser parser;

    /**
     * Initializes the controller class.
     *
     * @param arg0
     */
    @FXML
    public void readInBrowser(MouseEvent arg0) {
        //String url = listView.getSelectionModel().getSelectedItem();
        //Desktop.browse((url));
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
    void addFeed(KeyEvent event) throws IOException {
        if (event.getCode() == KeyCode.ENTER) {
            if (validURL()) {
                parser = Parser.sharedInstance();
                parser.setURL(addfeed.getText());
                feedService.insertFeed(UserSessionManager.sharedInstance().getLoggedUserID(), parser.readFeed()); //Insertamos el feed leído con el id de usuario logueado.
                
            }
        }
    }

    //Código a ejecutar al iniciar la vista.
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        setUsername();
        allFeedsMessages = new ArrayList<>();
        feedService = new FeedServiceImpl();
        ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
        service.scheduleAtFixedRate(refreshRunnable, 0, 5, TimeUnit.MINUTES);
       
    }

    private boolean validURL() {
        return true;
    }

    Runnable refreshRunnable = () -> {

        List<Feed> userFeedList = feedService.getFeedList(UserSessionManager.sharedInstance().getLoggedUserID()); //devuelvo lista de FEED de usuario por id.

        userFeedList.forEach((feed) -> {
            List<FeedMessage> eachfeedMessages = feed.getMessages();
            allFeedsMessages.addAll(eachfeedMessages);//Todos los objetos FeedMessage devueltos por la Lista de Feed.

        });

        ObservableList<String> AllMessagesObservable = FXCollections.observableArrayList(getAllFeedsMessagesStrings());
        listView.setItems(AllMessagesObservable);

    };

    private void setUsername() {
        username.setText(UserSessionManager.sharedInstance().getUser().getName());
    }

    private List<String> getAllFeedsMessagesStrings() {
        List<String> resultList = new ArrayList();
        allFeedsMessages.forEach((fm) -> {
            resultList.add(fm.toString());
        });

        return resultList;

    }
}
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