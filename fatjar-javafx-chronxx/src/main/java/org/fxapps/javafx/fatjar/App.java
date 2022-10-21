package org.fxapps.javafx.fatjar;

import java.io.IOException;

import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.effect.InnerShadow;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

public class App extends Application {

	public static void main(String[] args) {
		launch();
	}

	@Override
	public void start(Stage primaryStage) throws Exception {

		try {

			System.out.println("1");

			Parent root = FXMLLoader.load(getClass().getResource("Anmeldung.fxml"));
			System.out.println("2");
			Scene scene = new Scene(root);

			System.out.println("3");

			primaryStage.setTitle("ChronX - Zeiterfassung");
			primaryStage.setScene(scene);
			primaryStage.getIcons().add(new Image("/org/fxapps/javafx/fatjar/icon.png"));
			primaryStage.setResizable(false);
			primaryStage.show();

			System.out.println("4");

		} catch (IOException e) {
		}
	}

}
