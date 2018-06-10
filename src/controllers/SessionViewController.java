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

    ScheduledExecutorService service;

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
        service.shutdown();

    }

    @FXML
    void addFeed(KeyEvent event) throws IOException {
        if (event.getCode() == KeyCode.ENTER) {
            if (validURL()) {
                parser = Parser.sharedInstance();
                parser.setURL(addfeed.getText());
                System.out.println(addfeed.getText());
                feedService.insertFeed(UserSessionManager.sharedInstance().getLoggedUserID(), parser.readFeed());
                service.submit(refreshRunnable);
            }
        }
    }

    //Código a ejecutar al iniciar la vista.
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        setUsername();

        feedService = new FeedServiceImpl();
        service = Executors.newSingleThreadScheduledExecutor();
        service.scheduleAtFixedRate(refreshRunnable, 0, 1, TimeUnit.MINUTES);

    }

    private boolean validURL() {
        return true;
    }

    Runnable refreshRunnable = new Runnable() {
        @Override
        public void run() {
            allFeedsMessages = new ArrayList<>();
            List<Feed> userFeedList = feedService.getFeedList(UserSessionManager.sharedInstance().getLoggedUserID()); //devuelvo lista de FEED de usuario por id.
            if (!userFeedList.isEmpty()) {

                parser = Parser.sharedInstance();

                userFeedList.forEach((Feed feed) -> {

                    //parser.setURL(feed.getLink());
                    // Leemos todos los mensajes del feed seteado.
                    List<FeedMessage> eachfeedMessages = parser.readFeed().getMessages();
                    //Todos los objetos FeedMessage devueltos por la Lista de Feeds.
                    allFeedsMessages.addAll(eachfeedMessages);

                });

                ObservableList<String> AllMessagesObservable = FXCollections.observableArrayList(getAllFeedsMessagesStrings());
                listView.setItems(AllMessagesObservable);

            }
        }

    };

    private void setUsername() {
        username.setText(UserSessionManager.sharedInstance().getUser().getName());
    }

    private List<String> getAllFeedsMessagesStrings() {
        List<String> resultList = new ArrayList();
        allFeedsMessages.forEach((FeedMessage) -> {
            resultList.add(FeedMessage.toString());
        });

        return resultList;

    }
}

// TEST RSS = http://rss.cnn.com/rss/edition_europe.rss

