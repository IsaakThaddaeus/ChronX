module JavaFX4 {
	requires javafx.controls;
	requires javafx.fxml;
	requires com.fasterxml.jackson.annotation;
	requires com.fasterxml.jackson.databind;
	requires jollyday;
	requires javafx.graphics;
	
	opens application to javafx.graphics, javafx.fxml;
}
