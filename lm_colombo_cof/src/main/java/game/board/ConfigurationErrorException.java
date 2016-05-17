package game.board;

public class ConfigurationErrorException extends Exception{
	public ConfigurationErrorException(){
		super();
	}
	public ConfigurationErrorException(String errorMessage){
		super(errorMessage);
	}
}
