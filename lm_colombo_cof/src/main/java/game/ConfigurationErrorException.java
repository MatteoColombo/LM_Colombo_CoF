package game;

public class ConfigurationErrorException extends Exception{
	public ConfigurationErrorException(){
		super();
	}
	public ConfigurationErrorException(String errorMessage){
		super(errorMessage);
	}
}
