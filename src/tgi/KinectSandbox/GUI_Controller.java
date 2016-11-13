package tgi.KinectSandbox;

import java.util.Collection;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.Node;
import javafx.scene.canvas.*;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;

public class GUI_Controller {


	private Control control;
	private Button[][] buttonArrays;
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
	private VBox vBoxRight;


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
	private void settings(ActionEvent e){
		control.showSettings();
	}
	@FXML
	private void about(ActionEvent e){
		control.showAbout();
	}
	

	public void drawImg(Image img){
		GraphicsContext g = canvas.getGraphicsContext2D();
		g.drawImage(img, 0, 0, canvas.getWidth() ,canvas.getHeight());
	}
	
	public void setVboxContent(int canvesMode){
		if (vBoxRight == null) {
			System.out.println("mfw");
		}
		vBoxRight.getChildren().clear();
		for (int i = 0; i < buttonArrays[canvesMode].length; i++) {
			vBoxRight.getChildren().add(buttonArrays[canvesMode][i]);
		}
	}
	
	
	private void contPlus(){
		control.setIrContrast(control.getIrContrast()+5);
	}
	
	private void contMinus(){
		control.setIrContrast(control.getIrContrast()-5);
	}
	
	private void contReset(){
		control.resetIrContrast();
	}
	
	@FXML
	public void initialize() {
		
		String[] btnCalliText = {"+ Kontrast","- Kontrast", "Reset"};
		Button[] btnCallibration = new Button[3];
		for (int i = 0; i < btnCallibration.length; i++) {
			btnCallibration[i] = new Button(btnCalliText[i]);
			btnCallibration[i].setUserData(i);
			btnCallibration[i].setPrefWidth(120.0);
			btnCallibration[i].setOnMouseClicked((MouseEvent e) ->{handleVariableButtons( (int) (( Node ) e.getSource()).getUserData());});
		}
		String[] btnRGBText = {"--Placeholder--","--Placeholder--", "--Placeholder--"};
		Button[] btnRGB = new Button[3];
		for (int i = 0; i < btnRGB.length; i++) {
			btnRGB[i] = new Button(btnRGBText[i]);
			btnRGB[i].setUserData(10+i);
			btnRGB[i].setPrefWidth(120.0);
		}
		String[] btn2DText = {"--Placeholder--","--Placeholder--", "--Placeholder--"};
		Button[] btn2D = new Button [3];
		for (int i = 0; i < btn2D.length; i++) {
			btn2D[i] = new Button(btn2DText[i]);
			btn2D[i].setUserData(20+i);
			btn2D[i].setPrefWidth(120.0);
		}
		String[] btn3DText = {"--Placeholder--","--Placeholder--", "--Placeholder--"};
		Button[] btn3D = new Button[3];
		for (int i = 0; i < btn3D.length; i++) {
			btn3D[i] = new Button(btn3DText[i]);
			btn3D[i].setUserData(30+i);
			btn3D[i].setPrefWidth(120.0);
		}
		buttonArrays = new Button[4][];
		buttonArrays[0] = btnCallibration; buttonArrays[1] = btnRGB; buttonArrays[2] = btn2D; buttonArrays[3] = btn3D;
		
		setVboxContent(0);
	}
	
	private void handleVariableButtons(int buttonID) {
		switch (buttonID) {
		case 0:
			contPlus();
			break;
		case 1:
			contMinus();
			break;
		case 2:
			contReset();
			break;
		default:
			System.out.println("Fehler im Switch Case Button Handeling!");
			break;
		}
		
	}
	public GUI_Controller(){
		this.control = new Control(this);
	}
}
