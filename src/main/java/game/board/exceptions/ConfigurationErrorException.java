package game.board.exceptions;

public class ConfigurationErrorException extends Exception{
	public ConfigurationErrorException(){
		super();
	}
	public ConfigurationErrorException(String errorMessage){
		super(errorMessage);
	}
	
	public ConfigurationErrorException(Exception e){
		super(e);
	}
}
