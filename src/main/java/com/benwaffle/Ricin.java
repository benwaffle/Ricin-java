package com.benwaffle;

import com.benwaffle.model.Friend;
import com.benwaffle.model.FriendsList;
import com.benwaffle.model.ServerList;
import com.benwaffle.util.NetJSON;
import com.benwaffle.view.RicinCallbacks;
import im.tox.jtoxcore.JTox;
import im.tox.jtoxcore.ToxException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.controlsfx.glyphfont.GlyphFont;
import org.controlsfx.glyphfont.GlyphFontRegistry;

import java.net.UnknownHostException;

public class Ricin extends Application {
    private JTox<Friend> toxcore;
    private FriendsList friends;
    private boolean running = false;

    public static GlyphFont fontAwesome;

    public Ricin() throws Exception {
        friends = new FriendsList();
        toxcore = new JTox<>(friends, new RicinCallbacks(friends).get());

        ServerList svs = NetJSON.getServersFromNet();
        for (int i = 0; i < svs.length(); ++i){
            ServerList.Server candidate = svs.get(i);
            try {
                toxcore.bootstrap(candidate.ipv4, candidate.port, candidate.pubkey);
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }
        }
        if (!toxcore.isConnected())
            System.err.println("ctor() could not bootstrap with any server");
//            throw new Exception("Could not bootstrap with any server");
    }

    public void run(){
        if (!running) {
            running = true;

            new Thread(() -> {
                try {
                    while (running) {
                        toxcore.doTox();
                        Thread.sleep(toxcore.doToxInterval());
                    }
                } catch (ToxException | InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }

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

    public static void main(String... args) {
        Ricin app = null;

        fontAwesome = GlyphFontRegistry.font("FontAwesome");

        try {
            app = new Ricin();
            app.run();
        } catch (Exception e) {
            // fatal error
            e.printStackTrace();
            System.exit(1);
        }

        launch(args); // show UI
    }
}
