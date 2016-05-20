package game.exceptions;

public class CouncilorNotAvailableException extends Exception{
	public CouncilorNotAvailableException(){
		super();
	}
	
	public CouncilorNotAvailableException(String errorMessage){
		super(errorMessage);
	}
}
