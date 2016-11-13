package tgi.KinectSandbox;

import javafx.scene.paint.Color;

public class SaveData {
	
	Color color[];
	int[] minDistance;
	Color line;
	float lineDistance;
	float lineWidth;
	private Boolean lineActive;
	public double displayBoundX;
	public double displayBoundY;
	private int display;
	private boolean fullscreen;

	
	public SaveData(Color[] color2d, int[] minDistances, Color lineColor, float lineValue, float lineWidth, Boolean lineActive,int display, double displayBoundX, double displayBoundY, Boolean fullscreen) {
		this.color = color2d;
		this.minDistance = minDistances;
		this.line = lineColor;
		this.lineDistance = lineValue;
		this.lineWidth = lineWidth;
		this.lineActive = lineActive;
		this.display = display;
		this.displayBoundX = displayBoundX;
		this.displayBoundY = displayBoundY;
		this.fullscreen = fullscreen;
	}

	public void setColor(Color[] color){
		this.color = color;
	}
	
	public void setMinDistance(int[] minDistance){
		this.minDistance = minDistance;
	}
	
	public void setLineColor(Color lineColor){
		this.line = lineColor;
	}
	
	public void setLineDistance(float lineValue){
		this.lineDistance = lineValue;
	}
	
	public void setLineWidth(float lineWidth){
		this.lineWidth = lineWidth;
	}
	
	public void setLineActive(Boolean lineActive){
		this.lineActive = lineActive;
	}
	
	public void setDisplayBoundX(double displayBoundX) {
		this.displayBoundX = displayBoundX;
	}
	
	public void setDisplayBoundY(double displayBoundY) {
		this.displayBoundY = displayBoundY;
	}
	
	public void setDisplay(int display){
		this.display = display;
	}

	public void setFullscreen(boolean fullscreen){
		this.fullscreen = fullscreen;
	}
	
	public Color[] getColor(){
		return color;
	}
	
	public int[] getMinDistance(){
		return minDistance;
	}
	
	public Color getLineColor(){
		return line;
	}
	
	public float getLineDistance(){
		return lineDistance;
	}
	
	public float getLineWidth(){
		return lineWidth;
	}

	public Boolean getLineActive() {
		return lineActive;
	}
	
	public double getDisplayBoundX(){
		return displayBoundX;
	}

	public double getDisplayBoundY() {
		return displayBoundY;
	}

	public int getDisplay() {
		return display;
	}
	
	public boolean getFullscreen(){
		return fullscreen;
	}

}
