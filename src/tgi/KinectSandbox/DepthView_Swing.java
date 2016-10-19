package tgi.KinectSandbox;

import java.awt.Graphics;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class DepthView_Swing {

	private JFrame frame;
	private int width = 640;
	private int height = 480;
	private boolean active;
	private short[] depth;

	public DepthView_Swing(){
		frame = new JFrame("Kektus");
		depth = new short[307200];
		active = true;
		for (int i = 0; i < depth.length; i++) {
			depth[i]=25000;
		}
		width=640;
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				createAndShowGUI();
			}
		});
	}

	private void createAndShowGUI(){

		GraphicsDevice[] gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices();
		System.out.println(gd.length);
		if (gd.length>1) {

			frame.setLocation(gd[1].getDefaultConfiguration().getBounds().x, gd[1].getDefaultConfiguration().getBounds().y);
			frame.setSize(640, 480);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setUndecorated(true);

			frame.setVisible(true);

		}
		else{

			frame.setLocation(gd[0].getDefaultConfiguration().getBounds().x, gd[0].getDefaultConfiguration().getBounds().y);
			frame.setSize(640, 480);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setUndecorated(true);

			frame.setVisible(true);

		}
	}

	public void drawDepth(float[] depth){
		int idx;
		Graphics g = frame.getGraphics();
		BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		for (int i = 0; i < 480; i++) {
			for (int j = 8; j < width; j++) {
				int[] color = new int[3];
				idx = i*width+j;
//				if (depth[idx]*255/Float.MAX_VALUE>25) {
//					color[0] = (int) (depth[idx]*255/Float.MAX_VALUE); color[1]=74; color[2]=43;
//				} else{
//					color[0] = (int) (depth[idx]*255/Float.MAX_VALUE); color[1]=60; color[2]=130;
//				}
				if(depth[idx]<0.5f){
					color[0] = 0; color[1]=0; color[2]=0;
				}else if(depth[idx]<1.0f){
					color[0] = 0; color[1]=0; color[2]=255;
				}else if (depth[idx]<1.5f) {
					color[0] = 0; color[1]=255; color[2]=0;
				} else{
					color[0] = 255; color[1]=0; color[2]=0;
				}

				img.getRaster().setPixel(j, i, color);
			}
		}
		g.clearRect(0, 0, width, height);
		g.drawImage(img, 0, 0, null);
		img.flush();
	}


	public void setWidth(int pWidth){
		this.width = pWidth;
	}

	public void setHeight(int depthHeight) {
		this.height = depthHeight;

	}

}
