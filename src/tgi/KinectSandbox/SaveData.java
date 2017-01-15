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
	private Boolean depthLayersActive;
	private float gradientBeginning;
	private float gradientEnd;

	
	public SaveData(Color[] color2d, int[] minDistances, Color lineColor, float lineDistance, float lineWidth, Boolean lineActive,int display, double displayBoundX, double displayBoundY, Boolean fullscreen, Boolean layersActive, float gradientBeginning, float gradientEnd) {
		this.color = color2d;
		this.minDistance = minDistances;
		this.line = lineColor;
		this.lineDistance = lineDistance;
		this.lineWidth = lineWidth;
		this.lineActive = lineActive;
		this.display = display;
		this.displayBoundX = displayBoundX;
		this.displayBoundY = displayBoundY;
		this.fullscreen = fullscreen;
		this.depthLayersActive = layersActive;
		this.gradientBeginning = gradientBeginning;
		this.gradientEnd = gradientEnd;
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
	
	public void setLineDistance(float lineDistance){
		this.lineDistance = lineDistance;
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
	
	public void setDepthLayersActive(Boolean layersActive){
		this.depthLayersActive = layersActive;
	}
	
	public void setGradientBeginning(float gradientBeginning){
		this.gradientBeginning = gradientBeginning;
	}
	
	public void setGradientEnd(float gradientEnd){
		this.gradientEnd = gradientEnd;
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

	public Boolean getDepthLayersActive() {
		return depthLayersActive;
	}

	public float getGradientBeginning() {
		return gradientBeginning;
	}

	public float getGradientEnd() {
		return gradientEnd;
	}

}
