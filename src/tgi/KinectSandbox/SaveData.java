package tgi.KinectSandbox;

import javafx.scene.paint.Color;

public class SaveData {
	
	Color color[];
	int[] minDistance;
	Color line;
	float lineDistance;
	float lineWidth;
	private Boolean lineActive;

	
	public SaveData(Color[] color2d, int[] minDistances, Color lineColor, float lineValue, float lineWidth, Boolean lineActive) {
		this.color = color2d;
		this.minDistance = minDistances;
		this.line = lineColor;
		this.lineDistance = lineValue;
		this.lineWidth = lineWidth;
		this.lineActive = lineActive;
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

}
