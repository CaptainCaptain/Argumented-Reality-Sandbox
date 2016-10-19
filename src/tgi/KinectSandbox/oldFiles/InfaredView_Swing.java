package tgi.KinectSandbox.oldFiles;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JSlider;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;


public class InfaredView_Swing extends SwingWorker<Void, Void>{

	private JFrame frame;
	private JSlider slider = new JSlider();
	private int width = 640;
	private int height = 480;
	private short[] infrared;
	private boolean active;


	public InfaredView_Swing(){
		infrared = new short[307200];
				for (int i = 0; i < infrared.length; i++) {
					infrared[i]=2000;
				}

		active = true;
		frame = new JFrame("Calibration");
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				createAndShowGUI();
			}
		});
	}

	private void createAndShowGUI(){
		frame.setSize(640, 510);
		frame.setLocation(600, 0);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		slider.setMaximum(10000);
		slider.setMinimum(1000);
		slider.setValue(5000);
		frame.add(slider, BorderLayout.SOUTH);
	}


	@Override
	protected Void doInBackground(){
		frame.setVisible(true);
		Graphics g = frame.getGraphics();
		while(active){
			for (int i = 0; i < height; i++) {
				for (int j = 0; j < width; j++) {
					int idx = i*width+j;
					g.setColor(Color.getHSBColor(0.0f, 0.0f, Float.valueOf((infrared[idx]/slider.getValue()))));
					g.fillRect(j, i, 1, 1);
				}
			}
		}

		return null;
	}


	public void setActive(boolean pActive){
		active = pActive;
	}

	public void setWidth(int pWidth) {
		width = pWidth;
	}

	public void setHeight(int pHeight){
		height = pHeight;
	}

	public void setInfrared(short[] infraredFrame) {
		infrared = infraredFrame;

	}

}
