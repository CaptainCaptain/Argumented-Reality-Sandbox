package tgi.KinectSandbox;


import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class DepthViewFX extends Application{


	private int kinectWidth;
	private int kinectHeight;
	private int displayHeight;
	private int displayWidth;
	private GraphicsContext g;
	private Stage depthStage;
	private double boundX;
	private double boundY;
	private boolean fullscreen;
	private Canvas canvas;

	public DepthViewFX(double boundX, double boundY, boolean fullscreen, double displayWidth, double displayHeight, int kinectWidth, int kinectHeight){
		this.boundX = boundX;
		this.boundY = boundY;
		this.fullscreen = fullscreen;
		this.displayHeight = (int) displayHeight;
		this.displayWidth = (int) displayWidth;
		this.kinectWidth = kinectWidth;
		this.kinectHeight = kinectHeight;
		System.out.println(kinectWidth);
	}

	@Override
	public void start(Stage depthStage) throws Exception {
		this.depthStage = depthStage;
		depthStage.setTitle("Depth Map");
		Group root = new Group();
		if (fullscreen) {
			canvas = new Canvas(displayWidth, displayHeight);
		}else{
			canvas = new Canvas(kinectWidth, kinectHeight);
		}
		this.g = canvas.getGraphicsContext2D();
		root.getChildren().add(canvas);
		depthStage.setX(boundX);
		depthStage.setY(boundY);
		depthStage.initStyle(StageStyle.UNDECORATED);
		depthStage.setScene(new Scene(root));
		if (fullscreen) {
			g.setFill(Color.BLACK);
			g.fillRect(0, 0, displayWidth, displayHeight);
		}
		depthStage.show();
	}

	public void drawDepth(Image img){
		g.drawImage(img, 0, 0, kinectWidth, kinectHeight);
	}

	public void setKinWidth(int pWidth){
		this.kinectWidth = pWidth;
	}

	public void setKinHeight(int depthHeight) {
		this.kinectHeight = depthHeight;

	}

	public int getHeight() {
		return kinectHeight;
	}

	public int getWidth() {
		return kinectWidth;
	}
	
	public void setDisplay(double boundX, double boundY, double displayWidth, double displayHeight){
		this.boundX = boundX;
		this.boundY = boundY;
		this.displayWidth = (int) displayWidth;
		this.displayHeight = (int) displayHeight;
		depthStage.setX(boundX);
		depthStage.setY(boundY);
	}
	
	public void setFullscreen(boolean fullscreen){
		this.fullscreen = fullscreen;
		if (fullscreen) {
			canvas.setWidth(displayWidth);
			canvas.setHeight(displayHeight);
			depthStage.setWidth(displayWidth);
			depthStage.setHeight(displayWidth);
			g.setFill(Color.BLACK);
			g.fillRect(0, 0, displayWidth, displayHeight);
		}else{
			canvas.setWidth(kinectWidth);
			canvas.setHeight(kinectHeight);
			depthStage.setWidth(kinectWidth);
			depthStage.setHeight(kinectWidth);
			System.out.println(kinectWidth);
		}
	}
}
