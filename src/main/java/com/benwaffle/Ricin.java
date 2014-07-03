package com.benwaffle;

import com.benwaffle.model.Friend;
import com.benwaffle.model.FriendsList;
import im.tox.jtoxcore.JTox;
import im.tox.jtoxcore.ToxException;
import im.tox.jtoxcore.callbacks.CallbackHandler;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.controlsfx.glyphfont.GlyphFont;
import org.controlsfx.glyphfont.GlyphFontRegistry;

public class Ricin extends Application {
    private JTox<Friend> toxcore;
    private FriendsList friends;
    private CallbackHandler<Friend> cbhandler;

    public static GlyphFont fontAwesome;

    public Ricin() throws ToxException {
        friends = new FriendsList();
        cbhandler = new CallbackHandler<>(friends);
        toxcore = new JTox<>(friends, cbhandler);
    }

    public static void main(String... args) {
        Ricin app = null;

        fontAwesome = GlyphFontRegistry.font("FontAwesome");

        try {
            app = new Ricin();
        } catch (ToxException e) {
            // fatal error
            System.exit(1);
        }

        launch(args); // show UI
    }

	@Override
	public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Ricin.class.getResource("view/ricin.fxml"));
        Parent root = loader.load();
		stage.setTitle("Ricin");
		stage.setScene(new Scene(root, 600, 400));
		stage.show();
	}
}
