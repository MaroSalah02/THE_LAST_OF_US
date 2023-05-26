package views;

public class TrapsGUI extends CCTGUI{
	private double currX;
	private double currY;
	
	public void relocateTrap(double x,double y){
		currX =	x*Main.blockSize;
		currY = y*Main.blockSize;
    	relocate(currX,currY);

	}
	public TrapsGUI(double x,double y) {
		super("Trap.png");
		relocateTrap(x,y);
	}
	
	
}
