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
import java.awt.Desktop;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
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
import javafx.scene.control.Alert;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
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
     * @throws IOException
     * @throws URISyntaxException
     *
     */
    @FXML
    public void readInBrowser(MouseEvent arg0) throws IOException, URISyntaxException {

        int clickedCellIndex = listView.getSelectionModel().getSelectedIndex();
        FeedMessage fm = allFeedsMessages.get(clickedCellIndex);
        Desktop.getDesktop().browse(new URI(fm.getLink()));
       
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
            if (validURL(addfeed.getText())) {
                parser = Parser.sharedInstance();
                parser.setURL(addfeed.getText());
                System.out.println(addfeed.getText());
                feedService.insertFeed(UserSessionManager.sharedInstance().getLoggedUserID(), parser.readFeed());
                service.submit(refreshRunnable);
            } else showURLErrorDialog();
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

    private boolean validURL(String stringURL) {
        URL url;
        try {
            url = new URL(stringURL);
            return true;
        } catch (MalformedURLException ex) {
        }
        return false;
    }

    Runnable refreshRunnable =  () -> {
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

                Collections.reverse(allFeedsMessages);
                ObservableList<String> AllMessagesObservable = FXCollections.observableArrayList(getAllFeedsMessagesStrings());
                listView.setItems(AllMessagesObservable);

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

    private void showURLErrorDialog() {
       Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(null);
        alert.initStyle(StageStyle.UNDECORATED);
        alert.setHeaderText("URL is not valid");

        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add(getClass().getResource("/gui/sources/styles/Dialog.css").toExternalForm());

        alert.showAndWait();
    }
}

// TEST RSS = http://rss.cnn.com/rss/edition_europe.rss

