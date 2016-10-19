package tgi.KinectSandbox;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.canvas.*;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;

public class GUI_Controller {


	private Control control;
	@FXML
	private Button btnStart;
	@FXML
	private Button btnRgbView;
	@FXML
	private Button btnCali;
	@FXML
	private Button btn2dView;
	@FXML
	private Button btn3dDiag;
	@FXML
	private MenuItem menuClose;
	@FXML
	private Button btnPlusCont;
	@FXML
	private Button btnMinusCont;
	@FXML
	private Canvas canvas;
	@FXML
	private MenuItem menuSettings;
	@FXML
	private MenuItem menuAbout;


	@FXML
	private void close(ActionEvent e){
		Platform.exit();
		System.exit(0);
	}
	@FXML
	private void start(ActionEvent e){
		control.start();
	}
	@FXML
	private void view2d(ActionEvent e){
		control.setCanvesMode(2);
	}
	@FXML
	private void rgbView(ActionEvent e){
		control.setCanvesMode(1);
	}
	@FXML
	private void calliView(ActionEvent e){
		control.setCanvesMode(0);
	}
	@FXML
	private void contPlus(ActionEvent e){
		control.setIrContrast(control.getIrContrast()+5);
	}
	@FXML
	private void contMinus(ActionEvent e){
		control.setIrContrast(control.getIrContrast()-5);
	}
	@FXML
	private void settings(ActionEvent e){
		control.showSettings();
	}
	@FXML
	private void about(ActionEvent e){
		
	}
	

	public void drawImg(Image img){
		GraphicsContext g = canvas.getGraphicsContext2D();
		g.drawImage(img, 0, 0, canvas.getWidth() ,canvas.getHeight());
	}
	
	
	public GUI_Controller(){
		this.control = new Control(this);
	}
}
