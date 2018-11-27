package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainS extends Application {

	/**
	 * Initiate the program.
	 */
	@Override
	public void start(Stage primaryStage) {
		try {
			Parent root = (Parent) FXMLLoader.load(getClass().getResource("Report.fxml"));
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
			primaryStage.setTitle("Scanning Completed");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Run the program.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		launch(args);
	}

}
