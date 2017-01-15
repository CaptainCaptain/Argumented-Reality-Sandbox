package tgi.KinectSandbox;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.imageio.ImageIO;

import edu.ufl.digitalworlds.j4k.J4KSDK;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.stage.WindowEvent;

public class Control {
	private GUI_Controller guiControl;
	private DepthViewFX depthMap;
	private Kinect kin;
	private Menu settingsMenu;
	private FileIO fileIO;
	private Menu_Controller menuController;
	private int mainCanvesMode; // 0 = Kalli; 1 = RGB; 2 = Depth; 3=3D
	private int irContrastValue;
	private boolean rgbOn;
	private boolean menuActivated;
	private ObservableList<String> monitorChoice = FXCollections.observableArrayList();
	private Color[] color2D;
	private Color lineColor;
	private int[] minDistances;
	private float lineDistance;
	private float lineWidth;
	private Boolean lineActive;
	private double displayBoundX;
	private double displayBoundY;
	private GraphicsDevice[] gd;
	private Boolean fullscreen;
	private int display;
	private double displayWidth;
	private double displayHeight;
	private Boolean depthLayersActive;
	private VirtualKinect virtKin;
	private float[] depth;
	private boolean saveRGB;
	private boolean canvasSensorModeActive;
	private float gradientBeginning;
	private float gradientEnd;
	private float[] gradientEndValues = new float[4];
	private Image logoSplashScreen;
	private float gradientColorMultipicator;
	private boolean kinActive;

	public Control(GUI_Controller gui_Controller) {
		this.guiControl = gui_Controller;
		this.kin = new Kinect(this);
		this.settingsMenu = new Menu();
		this.settingsMenu.setControl(this);
		this.fileIO = new FileIO();
		this.irContrastValue = 10;
		this.rgbOn = false;
		this.menuActivated = false;
		this.canvasSensorModeActive=false;
		File splashScreen = new File("ARBox.jpg");
		this.logoSplashScreen = new Image(splashScreen.toURI().toString());
		this.kinActive=false;
		try {
			SaveData recivedData = fileIO.load();
			this.color2D = recivedData.getColor();
			if (this.color2D == null || color2D.length < 3) {
				this.color2D = new Color[3];
				this.color2D[0] = Color.GREEN;
				this.color2D[1] = Color.BLUE;
				this.color2D[2] = Color.RED;
			}
			this.minDistances = recivedData.getMinDistance();
			if (this.minDistances == null) {
				this.minDistances[0] = 500;
				this.minDistances[1] = 1000;
				this.minDistances[2] = 1500;
			}
			this.lineColor = recivedData.getLineColor();
			if (this.lineColor == null) {
				this.lineColor = new Color(0, 0, 0, 1);
			}
			this.lineDistance = recivedData.getLineDistance();
			if (this.lineDistance == 0.0) {
				this.lineDistance = 0.5f;
			}
			this.lineWidth = recivedData.getLineWidth();
			if (this.lineWidth == 0.0) {
				this.lineWidth = 0.02f;
			}
			this.lineActive = recivedData.getLineActive();
			if (this.lineActive == null) {
				this.lineActive = true;
			}
			this.depthLayersActive = recivedData.getDepthLayersActive();
			if (this.depthLayersActive == null){
				this.depthLayersActive = true;
			}
			this.gradientBeginning = recivedData.getGradientBeginning();
			if (this.gradientBeginning == 0.0f){
				this.gradientBeginning = 0.8f;
			}
			this.gradientEnd = recivedData.getGradientEnd();
			if(this.gradientEnd == 0.0f){
				this.gradientEnd = 0.9f;
			}
			calculateGradientEndValuesAndMultiplicator();
			this.display = recivedData.getDisplay();
			this.displayBoundX = recivedData.getDisplayBoundX();
			this.displayBoundY = recivedData.getDisplayBoundY();
			this.fullscreen = recivedData.getFullscreen();

		} catch (IOException e) {
			e.printStackTrace();
		}
		this.mainCanvesMode = 0;
		gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices();
		for (int i = 0; i < gd.length; i++) {
			monitorChoice.add(gd[i].getIDstring());
		}
		this.displayWidth = gd[display].getDefaultConfiguration().getBounds().getWidth();
		this.displayHeight = gd[display].getDefaultConfiguration().getBounds().getHeight();

		//		 this.virtKin = new VirtualKinect(this); //Virtuelle Kinect, wenn Felix mal wieder die Kinect daheim hat 
	}

	public void start() {
		if(!kinActive){
			kin.setDepthResolution(640, 480);
			kin.setColorResolution(640, 480);
			kin.start(J4KSDK.DEPTH | J4KSDK.INFRARED | J4KSDK.XYZ);
//			kin.setElevationAngle(0);

			Stage depthStage = new Stage();
			this.depthMap = new DepthViewFX(displayBoundX, displayBoundY, fullscreen, displayWidth, displayHeight, kin.getDepthWidth(), kin.getDepthHeight());
			try {
				this.depthMap.start(depthStage);
			} catch (Exception e) {
				e.printStackTrace();
			}
			this.canvasSensorModeActive = true;
			//		this.virtKin.start(); //starten der Virtuellen Kinect
			guiControl.btnStartSetText("Stop");
			guiControl.setBtn2DActive(false);
			guiControl.setBtnCalliActive(false);
			guiControl.setBtnRGBActive(false);
			guiControl.setVboxDisabled(false);
			kinActive = true;
		}else{
			kin.stop();
			guiControl.btnStartSetText("Start");
			guiControl.setBtn2DActive(true);
			guiControl.setBtnCalliActive(true);
			guiControl.setBtnRGBActive(true);
			guiControl.setVboxDisabled(true);
		}
	}

	public void fillArray() {

	}

	public void sendDepth(float[] depth) {
		this.depth = depth;
		int idx;
		int dWidth = kin.getDepthWidth();
		int dHeight = kin.getDepthHeight();
		BufferedImage img = new BufferedImage(dWidth, dHeight, BufferedImage.TYPE_INT_RGB);
		if(depthLayersActive){
			for (int i = 0; i < dHeight; i++) {
				for (int j = 8; j < dWidth; j++) {
					int[] color = new int[3];
					idx = i * dWidth + j;
					if (depth[idx] % lineDistance < lineWidth && depth[idx] % lineDistance > -lineWidth && depth[idx]!= 0 && lineActive) {
						color[0] = (int) (lineColor.getRed() * 255);
						color[1] = (int) (lineColor.getGreen() * 255);
						color[2] = (int) (lineColor.getBlue() * 255);
					}else if ( depth[idx] < minDistances[0]/100.0f) {					
						color[0] = 0;
						color[1] = 0;
						color[2] = 0;
					} else if (depth[idx] < minDistances[1]/100.0f) {
						color[0] = (int) (color2D[0].getRed() * 255);
						color[1] = (int) (color2D[0].getGreen() * 255);
						color[2] = (int) (color2D[0].getBlue() * 255);
					} else if (depth[idx] < minDistances[2]/100.0f) {
						color[0] = (int) (color2D[1].getRed() * 255);
						color[1] = (int) (color2D[1].getGreen() * 255);
						color[2] = (int) (color2D[1].getBlue() * 255);
					} else {
						color[0] = (int) (color2D[2].getRed() * 255);
						color[1] = (int) (color2D[2].getGreen() * 255);
						color[2] = (int) (color2D[2].getBlue() * 255);
					}
					img.getRaster().setPixel(j, i, color);
				}
			}
		}else{
			for (int i = 0; i < dHeight; i++) {
				for (int j = 8; j < dWidth; j++) {
					int[] color = new int[3];
					idx = i * dWidth + j;
					if (depth[idx] % lineDistance < lineWidth && depth[idx] % lineDistance > -lineWidth && depth[idx]!= 0 && lineActive && depth[idx] < gradientBeginning + gradientEndValues[3]) {
						color[0] = (int) (lineColor.getRed() * 255);
						color[1] = (int) (lineColor.getGreen() * 255);
						color[2] = (int) (lineColor.getBlue() * 255);
					}
					else if(depth[idx]>= gradientBeginning + gradientEndValues[3]){
						color[0]=0;
						color[1]=0;
						color[2]=0;
					}else if(depth[idx]>= gradientBeginning + gradientEndValues[2]){
						color[0]=0;
						color[1]=Math.min(255, (int) (255-(depth[idx] - gradientBeginning +gradientEndValues[2])*gradientColorMultipicator));
						color[2]=255;
					}else if ( depth[idx] >= gradientBeginning + gradientEndValues[1]) {					
						color[0]=0;
						color[1]=255;
						color[2]=Math.max(0, (int) (((depth[idx] -gradientBeginning + gradientEndValues[1])*gradientColorMultipicator)));
					}
					else if ( depth[idx] >= gradientBeginning + gradientEndValues[0]) {					
						color[0]=Math.min(255, (int) (255-(depth[idx] - gradientBeginning + gradientEndValues[0])*gradientColorMultipicator));
						color[1]=255;
						color[2]=0;				}
					else if ( depth[idx] >= gradientBeginning) {					
						color[0]=255;
						color[1]=Math.max(0, (int) (((depth[idx] -gradientBeginning)*gradientColorMultipicator)));
						color[2]=0;
					}
					img.getRaster().setPixel(j, i, color);
				}
			}
		}
		Image imgFX = SwingFXUtils.toFXImage(img, null);
		if (imgFX == null) {
			System.out.println("2D Img null");
		}
		depthMap.drawDepth(imgFX);
		if (mainCanvesMode == 2) {
			guiControl.drawImg(imgFX);
		}
		img.flush();
	}

	public void sendInfared(short[] infrared) {
		if (mainCanvesMode == 0) {
			int irWidth = kin.getInfraredWidth();
			int irHeight = kin.getInfraredHeight();
			BufferedImage bufImg = new BufferedImage(irWidth, irHeight, BufferedImage.TYPE_INT_RGB);
			for (int i = 0; i < irHeight; i++) {
				for (int j = 0; j < irWidth; j++) {
					int idx = i * irWidth + j;
					int[] color = new int[3];
					color[0] = Integer.valueOf(infrared[idx] / irContrastValue);
					color[1] = Integer.valueOf(infrared[idx] / irContrastValue);
					color[2] = Integer.valueOf(infrared[idx] / irContrastValue);
					bufImg.getRaster().setPixel(j, i, color);
				}
			}
			Image imgFX = SwingFXUtils.toFXImage(bufImg, null);
			guiControl.drawImg(imgFX);
		}
	}

	public void sendColor(byte[] frame) {
		if (mainCanvesMode == 1) {
			int fWidth = kin.getColorWidth();
			int fHeight = kin.getColorHeight();
			int color[] = new int[fWidth * fHeight];

			for (int i = 0, j = 0; i < frame.length; i = i + 4, j++) {
				int b, gr, r;

				b = frame[i] & 0xFF;
				gr = frame[i + 1] & 0xFF;
				r = frame[i + 2] & 0xFF;

				color[j] = (r << 16) | (gr << 8) | b;
			}
			BufferedImage bufImg = new BufferedImage(fWidth, fHeight, BufferedImage.TYPE_INT_RGB);
			bufImg.getRaster().setDataElements(0, 0, fWidth, fHeight, color);
			bufImg.setRGB(0, 0, fWidth, fHeight, color, 0, fWidth);
			Image imgFX = SwingFXUtils.toFXImage(bufImg, null);
			guiControl.drawImg(imgFX);
			if(saveRGB){
				try{
					String timestamp = new SimpleDateFormat("yyyy MM dd-HH mm ss").format(new Date());
					String fileName = System.getProperty("user.home")+"\\Pictures\\VR Box Screenshots\\"+timestamp;
					File fileOut = new File(fileName+".png");
					fileOut.getParentFile().mkdirs();
					ImageIO.write(bufImg, "png", fileOut);
					guiControl.txaWrite("Datei Gespeichert unter "+fileName+".png");
				}catch(Exception e){
					guiControl.txaWrite("Fehler beim Speichern des Screenshots!");
					e.printStackTrace();
				}
				saveRGB = false;
			}
			if (mainCanvesMode != 1) {
				kin.stop();
				kin.start(J4KSDK.DEPTH | J4KSDK.INFRARED | J4KSDK.XYZ);
			}
		}
	}

	public void setCanvesMode(int mode) {
		this.mainCanvesMode = mode;
		this.guiControl.setVboxContent(mainCanvesMode);
		if (!rgbOn && mode == 1) {
			rgbOn = true;
			kin.stop();
			kin.start(J4KSDK.DEPTH | J4KSDK.COLOR | J4KSDK.XYZ);
		} else {
			if (rgbOn) {
				rgbOn = false;
				kin.stop();
				kin.start(J4KSDK.DEPTH | J4KSDK.INFRARED | J4KSDK.XYZ);
			}
		}
	}

	public void setIrContrast(int pCont) {
		if (pCont <= 0) {
			this.irContrastValue = 1;
		} else {
			this.irContrastValue = pCont;
		}
	}

	public void resetIrContrast(){
		this.irContrastValue = 10;
	}

	public int getIrContrast() {
		return irContrastValue;
	}

	public void showSettings() {
		if (!menuActivated) {
			try {
				Stage settingsMenuStage = new Stage();
				this.settingsMenu.start(settingsMenuStage);
				settingsMenuStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
					@Override
					public void handle(WindowEvent event) {
						settingsMenuStage.close();
						saveSettings();
					}
				});
				menuActivated = true;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if (menuController != null) {
			menuController.setCpBoxes(color2D);
			menuController.setSpValues(minDistances, (int) (gradientBeginning*1000), (int) (gradientEnd*1000));
			menuController.setLine(lineActive, lineColor, lineWidth*10, lineDistance);
			menuController.cbDisplayAddChoise(monitorChoice);
			menuController.setDisplayChoise(display);
			menuController.setCbFullscreenActive(fullscreen);
			menuController.setTogglebtns(depthLayersActive);
		}
		settingsMenu.show();

	}

	public void ChangeColor2D(Color[] color) {
		color2D = color;
	}

	public void setMinDistances(int[] minDistances){
		this.minDistances = minDistances;
	}

	private void saveSettings() {
		SaveData dataToSave = new SaveData(color2D, minDistances, lineColor, lineDistance, lineWidth, lineActive, display, displayBoundX, displayBoundY, fullscreen, depthLayersActive, gradientBeginning, gradientEnd);
		fileIO.save(dataToSave);
	}

	public void setMenuController(Menu_Controller mc) {
		this.menuController = mc;
	}

	public void setLineColor(Color lineColor) {
		this.lineColor = lineColor;

	}

	public void setLineActive(Boolean active) {
		this.lineActive = active;		
	}

	public void setLineWidth(float lineWidth) {
		this.lineWidth = lineWidth;
	}

	public void setLineDistance(float lineDistance) {
		this.lineDistance = lineDistance;	
	}

	public void setDisplay(int display) {
		this.display = display;
		displayBoundX = gd[display].getDefaultConfiguration().getBounds().getX();
		displayBoundY = gd[display].getDefaultConfiguration().getBounds().getY();
		if (depthMap != null) {
			depthMap.setDisplay(displayBoundX, displayBoundY, displayWidth, displayHeight);
		}
	}

	public void setFullscreen(Boolean fullscreen) {
		this.fullscreen = fullscreen;
		if (depthMap != null) {
			depthMap.setFullscreen(fullscreen);
		}	
	}

	public void showAbout() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Über Kinect Sandbox");
		alert.setHeaderText("Kinect Sandbox");
		alert.setContentText("Ein Projekt von Marcel Heda und Felix Dittrich im Rahmen des Hafner Projekts der Gewerblichen Schule Waiblingen.\n\n\n Verwendete APIs:	\"J4KSDK\"");	
		alert.show();

	}

	public void canvasClicked(double x, double y, double canvasWidth, double canvasHeight){//Fixed
		if(canvasSensorModeActive){
			int xi = (int) (kin.getDepthWidth()*(x/canvasWidth));
			int yi = (int) (kin.getDepthHeight()*(y/canvasHeight));
			int i = yi*kin.getDepthWidth()+xi;
			guiControl.txaWrite("Entfernung zum Sensor: "+depth[i]*100+" cm");
		}

	}

	public void saveRGB() {
		this.saveRGB = true;

	}

	public void setDepthLayersActive(Boolean b) {
		this.depthLayersActive = b;
		if(menuController!=null){
			menuController.setTogglebtns(b);		
		}

	}

	public void setGradientBeginning(int value) {
		this.gradientBeginning = value/1000.0f;
		calculateGradientEndValuesAndMultiplicator();
	}

	public void canvasSizeChanged() {
		if(!canvasSensorModeActive){
			guiControl.drawImg(logoSplashScreen);
		}
	}

	public void setGradientEnd(int value) {
		this.gradientEnd = value/1000.0f;	
		calculateGradientEndValuesAndMultiplicator();
	}

	private void calculateGradientEndValuesAndMultiplicator(){
		for (int i = 1; i < gradientEndValues.length+1; i++) {
			this.gradientEndValues[i-1] = ((gradientEnd-gradientBeginning)/4.0f)*i;
		}
		this.gradientColorMultipicator = 255/((gradientEnd-gradientBeginning)/4);
	}

	public void stop() {
		kin.stop();
	}
}
