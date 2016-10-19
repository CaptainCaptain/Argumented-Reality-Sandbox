package tgi.KinectSandbox;

import edu.ufl.digitalworlds.j4k.DepthMap;
import edu.ufl.digitalworlds.j4k.J4KSDK;


public class Kinect extends J4KSDK{

	private Control control;

	public Kinect(Control pControl){
        super();
		this.control = pControl;
	}

	@Override
	public void onColorFrameEvent(byte[] data) {
		control.sendColor(data);
	}
	@Override
	public void onDepthFrameEvent(short[] depth_frame, byte[] body_index, float[] xyz, float[] uv) {
        DepthMap map = new DepthMap(getDepthWidth(), getDepthHeight(), xyz);
		control.sendDepth(map.realZ);
    }

	@Override
	public void onSkeletonFrameEvent(boolean[] arg0, float[] arg1,
			float[] arg2, byte[] arg3) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onInfraredFrameEvent(short[] infrared){
		control.sendInfared(infrared);
	}

}
