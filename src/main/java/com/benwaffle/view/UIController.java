package com.benwaffle.view;

import com.benwaffle.model.Friend;
import im.tox.jtoxcore.FriendExistsException;
import im.tox.jtoxcore.JTox;
import im.tox.jtoxcore.ToxException;
import im.tox.jtoxcore.ToxFriend;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.controlsfx.dialog.Dialogs;

import java.util.Optional;

public class UIController {
    private static JTox<? extends ToxFriend> api;

    @FXML private ListView<Friend> friendsLV, groupchatsLV, frqLV;
    @FXML private TextArea messageTextfield;
    @FXML private TextField nameTextfield, statusTextfield;

    @FXML private void nameTextChanged(ActionEvent event){
        try {
            api.setName(nameTextfield.getText());
        } catch (ToxException e) {
            Dialogs
                    .create()
                    .title("Error")
                    .message("couldn't set name because:\n" + e.getMessage())
                    .showError();
        }
    }

    @FXML private void statusTextChanged(ActionEvent event){
        try {
            api.setStatusMessage(statusTextfield.getText());
        } catch (ToxException e) {
            Dialogs
                    .create()
                    .title("Error")
                    .message("couldn't set status because:\n" + e.getMessage())
                    .showError();
        }
    }

    @FXML private void addClicked(ActionEvent event){
        Optional<String> toxid = Dialogs
                .create()
                .title("Add")
                .message("Enter tox id of user to add:")
                .showTextInput();

        if (!toxid.isPresent()) return;

        Optional<String> frqmsg = Dialogs
                .create()
                .title("Add message")
                .message("Optional message in the friend request")
                .showTextInput();

        try {
            api.addFriend(toxid.get(), frqmsg.orElse(null));
        } catch (ToxException e) {
            Dialogs.create().message("can't add friend").showError();
        } catch (FriendExistsException e) {
            Dialogs.create().message("friend already exists").showWarning();
        }
    }

    @FXML private void newGroupchatClicked(ActionEvent event){

    }

    @FXML private void copyIDClicked(ActionEvent event){

    }

    @FXML private void sendClicked(ActionEvent event){
//        api.sendMessage(friendsLV)
    }

    public UIController(){

    }

    public static void setApi(JTox<? extends ToxFriend> api) {
        UIController.api = api;
    }
}
