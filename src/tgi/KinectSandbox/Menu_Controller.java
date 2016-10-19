package tgi.KinectSandbox;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Spinner;
import javafx.scene.paint.Color;

public class Menu_Controller {
	
	@FXML
	private ColorPicker cpClose;
	@FXML
	private ColorPicker cpMiddle;
	@FXML
	private ColorPicker cpFar;
	@FXML
	private ChoiceBox<Choice> cbDisplay;
	@FXML
	private Spinner<Integer> spClose;
	@FXML
	private Spinner<Integer> spMiddle;
	@FXML
	private Spinner<Integer> spFar;
	
	private Control control;
	


	public Menu_Controller(Control control) {
		this.control = control;
		control.setMenuController(this);
	}

	@FXML
	private void colorChanged(ActionEvent e){
		Color[] color = new Color[3];
		color[0] = cpClose.getValue();
		color[1] = cpMiddle.getValue();
		color[2] = cpFar.getValue();		
		control.ChangeColor2D(color);
	}
	
	@FXML
	private void minDistancesChanged(ActionEvent e){
		double[] distances = new double[3];
		distances[0] = spClose.getValue();
		distances[1] = spMiddle.getValue();
		distances[2] = spFar.getValue();
	}
	
	public void setCpBoxes(Color[] colors){
		if (cpClose == null) {
			System.out.println("jupp");
		}
		cpClose.setValue(colors[0]);
		cpMiddle.setValue(colors[1]);
		cpFar.setValue(colors[2]);
	}
	
	public void cbDisplayAddChoise(ObservableList<Choice> list){

		
//		cbDisplay.setItems(list);
	}
	
	@FXML
	private void cbDisplayEvent(){
		System.out.println("Changed");
	}
	
    @FXML
    public void initialize() {
        spClose.valueProperty().addListener((obs, oldValue, newValue) -> 
        	spMiddle.getValueFactory().valueProperty().set();
        );
    }

}
