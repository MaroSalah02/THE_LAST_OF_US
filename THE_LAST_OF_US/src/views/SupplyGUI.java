package views;

public class SupplyGUI extends CollectiblesGUI{
	private double currX;
	private double currY;
	
	public void relocateSupply(double x,double y){
		currX =	x*Main.blockSize;
		currY = y*Main.blockSize;
    	relocate(currX,currY);

	}
	public SupplyGUI(double x,double y) {
		super("Supply.png");
		relocateSupply(x,y);
		
	}

}
