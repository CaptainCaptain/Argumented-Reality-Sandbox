package tgi.KinectSandbox;

import edu.ufl.digitalworlds.j4k.DepthMap;
import edu.ufl.digitalworlds.j4k.J4KSDK;


public class Kinect extends J4KSDK{

	private Control control;
	private boolean selector;

	public Kinect(Control pControl){
        super();
		this.control = pControl;
		this.selector = false;
	}

	@Override
	public void onColorFrameEvent(byte[] data) {
		if(selector){
		control.sendColor(data);
		selector = !selector;
		}
	}
	@Override
	public void onDepthFrameEvent(short[] depth_frame, byte[] body_index, float[] xyz, float[] uv) {
		if(!selector){
        DepthMap map = new DepthMap(getDepthWidth(), getDepthHeight(), xyz);
		control.sendDepth(map.realZ);
		selector = !selector;
		}
    }

	@Override
	public void onSkeletonFrameEvent(boolean[] arg0, float[] arg1,
			float[] arg2, byte[] arg3) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onInfraredFrameEvent(short[] infrared){
		if(selector){
			control.sendInfared(infrared);
			selector = !selector;
		}
		
	}

}
