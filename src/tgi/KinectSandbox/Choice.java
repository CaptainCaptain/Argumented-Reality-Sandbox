package tgi.KinectSandbox;

public class Choice {

	private int idx;
	private String name;
	
	public Choice(int pIdx, String pName){
		this.idx = pIdx;
		this.name = pName;
	}
	
	public int getIndex(){
		return idx;
	}
	
	public String getName(){
		return name;
	}
}
