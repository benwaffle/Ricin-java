package com.benwaffle.view;

import com.benwaffle.Ricin;
import com.benwaffle.model.Friend;
import im.tox.jtoxcore.FriendExistsException;
import im.tox.jtoxcore.JTox;
import im.tox.jtoxcore.ToxException;
import im.tox.jtoxcore.ToxFriend;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.controlsfx.dialog.Dialogs;

import java.util.Optional;

public class RicinController {
    private static JTox<? extends ToxFriend> api;

    @FXML private Button addButton;
    @FXML private Button groupchatButton;
    @FXML private Button settingsButton;

    @FXML private void handleAddButton(){
        // show add view
    }

    @FXML private void handleGroupchatButton(){
        // create groupchat
    }

    @FXML private void handleSettingsButton(){
        // show settings view
    }

    // get names from https://fortawesome.github.io/Font-Awesome/icons/
    @FXML private void initialize() {
        System.out.println(Ricin.fontAwesome.getGlyphs());
        addButton.setGraphic(Ricin.fontAwesome.create("PLUS"));
        groupchatButton.setGraphic(Ricin.fontAwesome.create("GROUP"));
        settingsButton.setGraphic(Ricin.fontAwesome.create("GEAR"));
    }

    public RicinController(){

    }

    public static void setApi(JTox<? extends ToxFriend> api) {
        RicinController.api = api;
    }
}
