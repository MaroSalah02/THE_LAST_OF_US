package views;

public class VaccineGUI extends CollectiblesGUI{
	private double currX;
	private double currY;
	
	public void relocateVaccine(double x,double y){
		currX =	x*Main.blockSize;
		currY = y*Main.blockSize;
    	relocate(currX,currY);

	}
	public VaccineGUI(double x,double y) {
		super("Vaccine.png");
		relocateVaccine(x,y);

	}
}
