package application;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;


public class Main extends Application {

	/**
	 * Initiate the program.
	 */
	@Override
	public void start(Stage primaryStage) {
		try {
			Parent root = (Parent) FXMLLoader.load(getClass().getResource("Scan.fxml"));
			Scene scene = new Scene(root);
			primaryStage.setResizable(false);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
			primaryStage.setTitle("Project");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void ping (String entryIpAddress){
	    try{    
	         String command ="ping  "+entryIpAddress;
	         Process process = Runtime.getRuntime().exec(command);
	         process.getOutputStream();
	         System.out.println(process.toString());
	    }catch(Exception e){}
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