package tgi.KinectSandbox;


import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class DepthViewFX extends Application{


	private int width = 640;
	private int height = 480;
	private GraphicsContext g;

	public DepthViewFX(){
		width=640;
	}

	@Override
	public void start(Stage depthStage) throws Exception {
		depthStage.setTitle("Depth Map");
		Group root = new Group();
		Canvas canvas = new Canvas(width, height);
		this.g = canvas.getGraphicsContext2D();
		root.getChildren().add(canvas);
		GraphicsDevice[] gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices();
		if (gd.length>1) {
			depthStage.setX(gd[1].getDefaultConfiguration().getBounds().getX());
			depthStage.setY(gd[1].getDefaultConfiguration().getBounds().getY());
		}
		else{
			depthStage.setX(gd[0].getDefaultConfiguration().getBounds().getX());
			depthStage.setY(gd[0].getDefaultConfiguration().getBounds().getY());
		}
		depthStage.initStyle(StageStyle.UNDECORATED);
		depthStage.setScene(new Scene(root));
		depthStage.show();
	}

	public void drawDepth(Image img){
		g.drawImage(img, 0, 0, width, height);
	}

	public void setWidth(int pWidth){
		this.width = pWidth;
	}

	public void setHeight(int depthHeight) {
		this.height = depthHeight;

	}

	public int getHeight() {
		return height;
	}

	public int getWidth() {
		return width;
	}
}
