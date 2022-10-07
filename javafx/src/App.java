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
    
    Parent root = FXMLLoader.load(getClass().getResource("Anmeldung.fxml"));
    Scene scene = new Scene(root);
  
        primaryStage.setTitle("ChronX - Zeiterfassung");
        primaryStage.setScene(scene);
        primaryStage.getIcons().add(new Image("icon.png"));
        primaryStage.setResizable(false);
        primaryStage.show(); 

} catch (IOException e) {}
  
    }
 
}
