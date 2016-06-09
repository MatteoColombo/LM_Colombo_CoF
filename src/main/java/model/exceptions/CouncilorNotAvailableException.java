package model.exceptions;

public class CouncilorNotAvailableException extends Exception{
	
	private static final long serialVersionUID = 3650486171167008218L;

	public CouncilorNotAvailableException(){
		super();
	}
	
	public CouncilorNotAvailableException(String errorMessage){
		super(errorMessage);
	}
}
