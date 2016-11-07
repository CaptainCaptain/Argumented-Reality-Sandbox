package tgi.KinectSandbox;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Menu extends Application {

	private Parent menuScene;
	private Scene mainMenuScene;
	private Stage menuStage;
	private Control control;
	private Menu_Controller menuController;


	@Override
	public void start(Stage menuStage) throws Exception {
		this.menuStage = menuStage;
		menuController = new Menu_Controller(control);
			try {
				FXMLLoader loader = new FXMLLoader(getClass().getResource("GUI_Menu.fxml"));
				loader.setController(menuController);
				menuScene = loader.load();
				mainMenuScene = new Scene(menuScene, 550, 650);
				menuStage.setScene(mainMenuScene);
				menuStage.setMinHeight(650);
				menuStage.setMinWidth(550);
			} catch (IOException e) {
				e.printStackTrace();
			}
	}


	public void setControl(Control control){
		this.control = control;
	}
	public void show(){
		menuStage.show();
	}





}
