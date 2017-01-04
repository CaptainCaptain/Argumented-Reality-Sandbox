package tgi.KinectSandbox;

public class VirtualKinect extends Thread{

	private Control control;

	public VirtualKinect(Control control){
		this.control = control;
	}

	@Override
	public void run(){
		float[][] virtualImage = new float[480][640];
		float[] virtualImage1d = new float[640*480];
		float height=0.8f;
		for (int i = 0; i < 640; i++) {
			for (int j = 0; j < 480; j++) {
				virtualImage[j][i] = height;
			}
			height=height+0.0009f;
			int k = 0;
			for (int l = 0; l < virtualImage.length; l++) {
				for (int m = 0; m < virtualImage[l].length; m++) {
					virtualImage1d[k++] = virtualImage[l][m];
				}
			}
			control.sendDepth(virtualImage1d);
			
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		while(true){
			control.sendDepth(virtualImage1d);
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
