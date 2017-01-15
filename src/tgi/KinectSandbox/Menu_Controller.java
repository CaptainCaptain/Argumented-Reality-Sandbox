package tgi.KinectSandbox;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class Menu_Controller {

	@FXML
	private ColorPicker cpClose;
	@FXML
	private ColorPicker cpMiddle;
	@FXML
	private ColorPicker cpFar;
	@FXML
	private ChoiceBox<String> cbDisplay;
	@FXML
	private ColorPicker cpLine;
	@FXML
	private Spinner<Integer> spClose;
	@FXML
	private Spinner<Integer> spMiddle;
	@FXML
	private Spinner<Integer> spFar;
	@FXML
	private Spinner<Integer> spLineWidth;
	@FXML
	private Spinner<Integer> spLineDistance;
	@FXML
	private CheckBox cbLineActive;
	@FXML
	private VBox lineVbox;
	@FXML
	private CheckBox cbFullscreen;
	@FXML
	private ToggleButton tglbGradient;
	@FXML
	private ToggleButton tglbLayers;
	@FXML
	private VBox vBoxSteps;
	@FXML
	private VBox vBoxGradient;
	@FXML
	private Spinner<Integer> spGradientBeginning;
	@FXML
	private Spinner<Integer> spGradientEnd;

	private Control control;
	
	private final ToggleGroup tglbGroup = new ToggleGroup();

	public Menu_Controller(Control control) {
		this.control = control;
		control.setMenuController(this);
	}

	@FXML
	private void colorChanged(ActionEvent e) {
		Color[] color = new Color[3];
		color[0] = cpClose.getValue();
		color[1] = cpMiddle.getValue();
		color[2] = cpFar.getValue();
		control.ChangeColor2D(color);
	}

	@FXML
	private void lineColorChanged(ActionEvent e) {
		Color lineColor = cpLine.getValue();
		control.setLineColor(lineColor);
	}

	@FXML
	private void minDistancesChanged(ActionEvent e) {
		double[] distances = new double[3];
		distances[0] = spClose.getValue();
		distances[1] = spMiddle.getValue();
		distances[2] = spFar.getValue();
	}
	
	@FXML
	private void tglbGradientClicked(ActionEvent e){
		control.setDepthLayersActive(false);
	}
	@FXML
	private void tglbLayersClicked(ActionEvent e){
		control.setDepthLayersActive(true);
	}

	public void setCpBoxes(Color[] colors) {
		cpClose.setValue(colors[0]);
		cpMiddle.setValue(colors[1]);
		cpFar.setValue(colors[2]);
	}

	public void cbDisplayAddChoise(ObservableList<String> list) {
		cbDisplay.getItems().setAll(list);
	}

	private void sendMinDistances() {
		int[] distances = new int[3];
		distances[0] = spClose.getValue();
		distances[1] = spMiddle.getValue();
		distances[2] = spFar.getValue();
		control.setMinDistances(distances);

	}

	private void setVboxLine(Boolean active) {
		lineVbox.setDisable(!active);
		control.setLineActive(active);
	}

	public void setSpValues(int[] distances, int gradiantBeginning, int gradiantEnd) {
		spClose.setValueFactory(
				new SpinnerValueFactory.IntegerSpinnerValueFactory(80, spMiddle.getValue() - 1, distances[0]));
		spMiddle.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(spClose.getValue() + 1,
				spFar.getValue() - 1, distances[1]));
		spFar.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(spMiddle.getValue() + 1,
				Integer.MAX_VALUE, distances[2]));
		spGradientBeginning.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, gradiantEnd-1, gradiantBeginning));
		spGradientEnd.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(gradiantBeginning, Integer.MAX_VALUE, gradiantEnd));
	}

	public void setLine(Boolean lineActive, Color lineColor, float lineWidth, float lineDistance) {
		cbLineActive.setSelected(lineActive);
		cpLine.setValue(lineColor);
		int lineWidthInt = (int) (lineWidth*100);
		spLineWidth.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 200, lineWidthInt));
		int lineDistanceInt = (int) (lineDistance*100);
		spLineDistance.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 200, lineDistanceInt));
	}

	public void setCbFullscreenActive(Boolean fullscreen) {
		cbFullscreen.setSelected(fullscreen);
	}

	public void setDisplayChoise(int display) {
		cbDisplay.getSelectionModel().select(display);
	}
	
	public void setTogglebtns(Boolean layersActive){
		if(layersActive){
			tglbGroup.selectToggle(tglbLayers);
			vBoxGradient.setVisible(false);
			vBoxSteps.setVisible(true);
		} else{
			tglbGroup.selectToggle(tglbGradient);
			vBoxSteps.setVisible(false);
			vBoxGradient.setVisible(true);
		}
	}
	
	@FXML
	public void initialize() {
		spClose.valueProperty().addListener((obs, oldValue, newValue) -> {
			spMiddle.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(spClose.getValue() + 1,
					spFar.getValue() - 1, spMiddle.getValue()));
			sendMinDistances();
		});
		
		spMiddle.valueProperty().addListener((obs, oldValue, newValue) -> {
			spClose.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(80, spMiddle.getValue() - 1,
					spClose.getValue()));
			spFar.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(spMiddle.getValue() + 1,
					Integer.MAX_VALUE, spFar.getValue()));
			sendMinDistances();
		});
		
		spFar.valueProperty().addListener((obs, oldValue, newValue) -> {
			spMiddle.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(spClose.getValue() + 1,
					spFar.getValue() - 1, spMiddle.getValue()));
			sendMinDistances();
		});
		
		spLineWidth.valueProperty().addListener((obs, oldValue, newValue) -> {
			float floatValue = newValue/1000.0f;
			control.setLineWidth(floatValue);
		});
		
		spLineDistance.valueProperty().addListener((obs, oldValue, newValue) -> {
			float floatValue = newValue/1000.0f;
			control.setLineDistance(floatValue);
		});
		
		spGradientBeginning.valueProperty().addListener((obs, oldValue, newValue) -> {
			spGradientEnd.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(newValue+1, Integer.MAX_VALUE, spGradientEnd.getValue()));
			control.setGradientBeginning(newValue);
		});
		spGradientEnd.valueProperty().addListener((obs, oldValue, newValue) ->{
			spGradientBeginning.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, spGradientEnd.getValue()-1, spGradientBeginning.getValue()));
			control.setGradientEnd(newValue);
		});
		
		cbLineActive.selectedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				setVboxLine(newValue);
			}
		});
		
		cbDisplay.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>(){
			public void changed(ObservableValue<? extends Number> arg0, Number oldValue, Number newValue) {
				if (newValue.intValue() >= 0) {
					control.setDisplay(newValue.intValue());
				}
			}		
		});		cbDisplay.getSelectionModel().selectFirst();
		
		cbFullscreen.selectedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> ov, Boolean oldValue, Boolean newValue) {
				control.setFullscreen(newValue);			
			}
		});
		
		if(tglbGradient == null){
			System.out.println("button null");
		}
		tglbGradient.setToggleGroup(tglbGroup);
		tglbLayers.setToggleGroup(tglbGroup);
		
	}

}
