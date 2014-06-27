package com.benwaffle;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Ricin extends Application {
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("ricin.fxml"));
		stage.setTitle("Ricin");
		stage.setScene(new Scene(root, 600, 400));
		stage.show();
	}
}
