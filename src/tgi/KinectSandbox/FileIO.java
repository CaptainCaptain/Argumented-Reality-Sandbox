package tgi.KinectSandbox;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.apache.log4j.net.SyslogAppender;

import javafx.scene.paint.Color;

public class FileIO {

	private File settingsFile;
	private int layers2D;

	public FileIO(int layers2D){
		this.layers2D = layers2D;
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
		double[] minDistance = data.getMinDistance();
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
				double[] minDistance = new double[minDistanceString.length];
				for (int i = 0; i < values.length; i++) {
					minDistance[i] = Double.valueOf(minDistanceString[i]);
				}
				br.close();
				SaveData loadedData = new SaveData(color, minDistance);
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
