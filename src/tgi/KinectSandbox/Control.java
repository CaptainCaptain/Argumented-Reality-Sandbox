package tgi.KinectSandbox;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.image.BufferedImage;
import java.io.IOException;

import edu.ufl.digitalworlds.j4k.J4KSDK;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

public class Control {
	private GUI_Controller guiControl;
	private DepthViewFX depthMap;
	private Kinect kin;
	private Menu settingsMenu;
	private FileIO fileIO;
	private Menu_Controller menuController;
	private final int layers2D = 3;
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

	public Control(GUI_Controller gui_Controller) {
		this.guiControl = gui_Controller;
		this.kin = new Kinect(this);
		this.settingsMenu = new Menu();
		this.settingsMenu.setControl(this);
		this.fileIO = new FileIO(layers2D);
		this.irContrastValue = 10;
		this.rgbOn = false;
		this.menuActivated = false;
		this.lineDistance = 0.1f;
		this.lineWidth = 0.2f;
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
			if (this.lineDistance == 0) {
				this.lineDistance = 0.5f;
			}
			this.lineWidth = recivedData.getLineWidth();
			if (this.lineWidth == 0) {
				this.lineWidth = 0.02f;
			}
			this.lineActive = recivedData.getLineActive();
			if (this.lineActive == null) {
				this.lineActive = true;
			}
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
		
	}

	public void start() {
		kin.setDepthResolution(640, 480);
		kin.setColorResolution(640, 480);
		kin.start(J4KSDK.DEPTH | J4KSDK.INFRARED | J4KSDK.XYZ);
		kin.setElevationAngle(0);

		Stage depthStage = new Stage();
		this.depthMap = new DepthViewFX(displayBoundX, displayBoundY, fullscreen, displayWidth, displayHeight, kin.getDepthWidth(), kin.getDepthHeight());
		try {
			this.depthMap.start(depthStage);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void fillArray() {

	}

	public void sendDepth(float[] depth) {
		int idx;
		int dWidth = kin.getDepthWidth();
		int dHeight = kin.getDepthHeight();
		BufferedImage img = new BufferedImage(dWidth, dHeight, BufferedImage.TYPE_INT_RGB);
		for (int i = 0; i < dHeight; i++) {
			for (int j = 8; j < dWidth; j++) {
				int[] color = new int[3];
				idx = i * dWidth + j;
				if (depth[idx] % 0.5f <0.05f && depth[idx] % 0.5f >-0.05f && depth[idx]!= 0 && lineActive) {
					color[0] = (int) (lineColor.getRed() * 255);
					color[1] = (int) (lineColor.getGreen() * 255);
					color[2] = (int) (lineColor.getBlue() * 255);
				}else if ( depth[idx] < minDistances[0]/1000.0f) {					
					color[0] = 0;
					color[1] = 0;
					color[2] = 0;
				} else if (depth[idx] < minDistances[1]/1000.0f) {
					color[0] = (int) (color2D[0].getRed() * 255);
					color[1] = (int) (color2D[0].getGreen() * 255);
					color[2] = (int) (color2D[0].getBlue() * 255);
				} else if (depth[idx] < minDistances[2]/1000.0f) {
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
			menuController.setSpValues(minDistances);
			menuController.setLine(lineActive, lineColor, lineWidth, lineDistance);
			menuController.cbDisplayAddChoise(monitorChoice);
			menuController.setDisplayChoise(display);
			menuController.setCbFullscreenActive(fullscreen);
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
		SaveData dataToSave = new SaveData(color2D, minDistances, lineColor, lineDistance, lineWidth, lineActive, display, displayBoundX, displayBoundY, fullscreen);
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
}
