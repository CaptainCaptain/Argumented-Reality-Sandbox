package tgi.KinectSandbox;

import java.io.IOException;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;


public class GUI extends Application{

	private Stage primaryStage;
	private Parent rootScene;
	private Scene mainScene;

	public static void main(String[] args){
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("Kinect Sandbox");
		loadGUI();
	}

	private void loadGUI() {
		try {
			rootScene = FXMLLoader.load(getClass().getResource("GUI.fxml"));
			mainScene = new Scene(rootScene, 600, 400);
			primaryStage.setScene(mainScene);
			primaryStage.setMinHeight(400);
			primaryStage.setMinWidth(600);
			primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
				@Override
				public void handle(WindowEvent event) {
					Platform.exit();
					System.exit(0);
				}
			});
			primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}



}
