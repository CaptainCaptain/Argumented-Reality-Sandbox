package tgi.KinectSandbox;

import javafx.scene.paint.Color;

public class SaveData {
	
	Color color[];
	int[] minDistance;

	public SaveData(Color[] color, int[] minDistance) {
		this.color = color;
		this.minDistance = minDistance;
	}
	
	public void setColor(Color[] color){
		this.color = color;
	}
	
	public void setMinDistance(int[] minDistance){
		this.minDistance = minDistance;
	}

	public Color[] getColor(){
		return color;
	}
	
	public int[] getMinDistance(){
		return minDistance;
	}

}
