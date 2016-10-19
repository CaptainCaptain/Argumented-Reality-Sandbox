package tgi.KinectSandbox.oldFiles;

import java.io.IOException;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class MenuOld extends Application {

	private Parent menuScene;
	private Scene mainMenuScene;
	private Stage menuStage;


	@Override
	public void start(Stage menuStage) throws Exception {
		this.menuStage = menuStage;
			try {
				System.out.println("count");
				menuScene = FXMLLoader.load(getClass().getResource("GUI_Menu.fxml"));
				System.out.println("count6");
				mainMenuScene = new Scene(menuScene, 600, 400);
				System.out.println("count1");
				menuStage.setScene(mainMenuScene);
				menuStage.setMinHeight(400);
				menuStage.setMinWidth(600);
				menuStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
					@Override
					public void handle(WindowEvent event) {
						menuStage.close();
					}
				});
				System.out.println("count3");
			} catch (IOException e) {
				e.printStackTrace();
			}
			System.out.println("count 4");
	}


	public void show(){
		menuStage.show();
	}





}
