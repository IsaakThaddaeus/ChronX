package test;

import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Slide extends Application {
    @Override public void start(Stage stage) {   
        Label  label  = new Label();
        Slider slider = new Slider(1, 11, 5);

        label.textProperty().bind(
            Bindings.format(
                "%.2f",
                slider.valueProperty()
            )
        );

        VBox layout = new VBox(10, label, slider);
        layout.setStyle("-fx-padding: 10px; -fx-alignment: baseline-right");

        stage.setScene(new Scene(layout));
        stage.setTitle("Goes to");
        stage.show();
    }

    public static void main(String[] args) { launch(args); }
 
    
}
