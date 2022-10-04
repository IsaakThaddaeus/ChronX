package application;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

 
public class App extends Application {

public static void main(String[] args) {launch(args);}

 @Override
public void start(Stage primaryStage) {
       
try {
	
	System.out.println("1");
    
    Parent root = FXMLLoader.load(getClass().getResource("/application/Anmeldung.fxml"));
    
    System.out.println("2");
    
    Scene scene = new Scene(root);
    System.out.println("3");
  
        primaryStage.setTitle("ChronX - Zeiterfassung");
        primaryStage.setScene(scene);
        primaryStage.getIcons().add(new Image("/application/icon.png"));
        primaryStage.setResizable(false);
        primaryStage.show(); 

} catch (IOException e) {}
  
    }
 
}
