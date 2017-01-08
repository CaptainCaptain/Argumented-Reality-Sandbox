package tgi.KinectSandbox;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;


import javafx.scene.paint.Color;

public class FileIO {

	private File settingsFile;

	public FileIO(){
		settingsFile = new File("settings.kek");
		if (!settingsFile.exists()) {
			try {
				settingsFile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void save(SaveData data){
		Color[] color = data.getColor();
		int[] minDistance = data.getMinDistance();
		try {
			FileWriter fw = new FileWriter(settingsFile.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			for (int i = 0; i < color.length; i++) {
				bw.write(color[i]+";");
			}
			bw.newLine();
			for (int i = 0; i < minDistance.length; i++) {
				bw.write(minDistance[i]+";");
			}
			bw.newLine();
			bw.write(data.getLineColor()+";"+data.getLineDistance()+";"+data.getLineWidth()+";"+data.getLineActive()+";");
			bw.newLine();
			bw.write(data.getDisplay()+";"+data.getDisplayBoundX() +";"+data.getDisplayBoundY()+";"+data.getFullscreen()+";");
			bw.newLine();
			bw.write(data.getDepthLayersActive()+";");
			bw.newLine();
			bw.write(data.getGradientBeginning()+";");
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public SaveData load() throws IOException{
		try {
			BufferedReader br = new BufferedReader(new FileReader(settingsFile.getAbsoluteFile()));
			
			
			String result = br.readLine();
			
			if (result != null) {
				String[] values = result.split(";");
				Color color[] = new Color[values.length];
				for (int i = 0; i < color.length; i++) {
					color[i] = Color.valueOf(values[i]);	
				}
				
				
				result = br.readLine();
				
				values = result.split(";");
				String[] minDistanceString = result.split(";");
				int[] minDistance = new int[minDistanceString.length];
				for (int i = 0; i < values.length; i++) {
					minDistance[i] = Integer.valueOf(minDistanceString[i]);
				}
				
				
				result = br.readLine();
				
				values = result.split(";");
				Color lineColor = Color.valueOf(values[0]);
				float lineDistance = Float.valueOf(values[1]);
				float lineWidth = Float.valueOf(values[2]);
				boolean lineActive = Boolean.valueOf(values[3]);
				
				result = br.readLine();
				
				values = result.split(";");
				int display = Integer.valueOf(values[0]);
				double displayBoundX = Double.valueOf(values[1]);
				double displayBoundY = Double.valueOf(values[2]);
				boolean fullscreen = Boolean.valueOf(values[3]);
				
				result = br.readLine();
				
				values = result.split(";");
				
				Boolean layersActive = Boolean.valueOf(values[0]);
				
				result = br.readLine();
				
				values = result.split(";");
				
				float gradientBeginning = Float.valueOf(values[0]);
				
				br.close();
				SaveData loadedData = new SaveData(color, minDistance, lineColor, lineDistance, lineWidth, lineActive, display, displayBoundX, displayBoundY, fullscreen, layersActive, gradientBeginning);
				return loadedData;
			}
			br.close();
			return null;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return null;

	}
}
