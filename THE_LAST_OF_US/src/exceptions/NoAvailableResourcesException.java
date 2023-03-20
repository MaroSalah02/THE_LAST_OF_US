package exceptions;

public class NoAvailableResourcesException extends GameActionException{
	public NoAvailableResourcesException() {
		super();
	}
	public NoAvailableResourcesException(String s) {
		super(s);
	}
}
